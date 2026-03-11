/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.item.FallingBlockEntity;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DripstoneThickness;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.material.FlowingFluid;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class PointedDripstoneBlock extends Block implements Fallable, SimpleWaterloggedBlock {
/*  48 */   public static final MapCodec<PointedDripstoneBlock> CODEC = simpleCodec(PointedDripstoneBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<PointedDripstoneBlock> codec() {
/*  52 */     return CODEC;
/*     */   }
/*     */   
/*  55 */   public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
/*  56 */   public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
/*  57 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   
/*     */   private static final int MAX_SEARCH_LENGTH_WHEN_CHECKING_DRIP_TYPE = 11;
/*     */   
/*     */   private static final int DELAY_BEFORE_FALLING = 2;
/*     */   
/*     */   private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK = 0.02F;
/*     */   
/*     */   private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK_IF_UNDER_LIQUID_SOURCE = 0.12F;
/*     */   
/*     */   private static final int MAX_SEARCH_LENGTH_BETWEEN_STALACTITE_TIP_AND_CAULDRON = 11;
/*     */   
/*     */   private static final float WATER_TRANSFER_PROBABILITY_PER_RANDOM_TICK = 0.17578125F;
/*     */   
/*     */   private static final float LAVA_TRANSFER_PROBABILITY_PER_RANDOM_TICK = 0.05859375F;
/*     */   
/*     */   private static final double MIN_TRIDENT_VELOCITY_TO_BREAK_DRIPSTONE = 0.6D;
/*     */   
/*     */   private static final float STALACTITE_DAMAGE_PER_FALL_DISTANCE_AND_SIZE = 1.0F;
/*     */   
/*     */   private static final int STALACTITE_MAX_DAMAGE = 40;
/*     */   
/*     */   private static final int MAX_STALACTITE_HEIGHT_FOR_DAMAGE_CALCULATION = 6;
/*     */   private static final float STALAGMITE_FALL_DISTANCE_OFFSET = 2.0F;
/*     */   private static final int STALAGMITE_FALL_DAMAGE_MODIFIER = 2;
/*     */   private static final float AVERAGE_DAYS_PER_GROWTH = 5.0F;
/*     */   private static final float GROWTH_PROBABILITY_PER_RANDOM_TICK = 0.011377778F;
/*     */   private static final int MAX_GROWTH_LENGTH = 7;
/*     */   private static final int MAX_STALAGMITE_SEARCH_RANGE_WHEN_GROWING = 10;
/*     */   private static final float STALACTITE_DRIP_START_PIXEL = 0.6875F;
/*  87 */   private static final VoxelShape TIP_MERGE_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
/*  88 */   private static final VoxelShape TIP_SHAPE_UP = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 11.0D, 11.0D);
/*  89 */   private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 16.0D, 11.0D);
/*  90 */   private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
/*  91 */   private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
/*  92 */   private static final VoxelShape BASE_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
/*     */   
/*     */   private static final float MAX_HORIZONTAL_OFFSET = 0.125F;
/*     */   
/*  96 */   private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
/*     */   
/*     */   public PointedDripstoneBlock(BlockBehaviour.Properties $$0) {
/*  99 */     super($$0);
/* 100 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any())
/* 101 */         .setValue((Property)TIP_DIRECTION, (Comparable)Direction.UP))
/* 102 */         .setValue((Property)THICKNESS, (Comparable)DripstoneThickness.TIP))
/* 103 */         .setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 109 */     $$0.add(new Property[] { (Property)TIP_DIRECTION, (Property)THICKNESS, (Property)WATERLOGGED });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 114 */     return isValidPointedDripstonePlacement($$1, $$2, (Direction)$$0.getValue((Property)TIP_DIRECTION));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 125 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 126 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/* 129 */     if ($$1 != Direction.UP && $$1 != Direction.DOWN) {
/* 130 */       return $$0;
/*     */     }
/*     */     
/* 133 */     Direction $$6 = (Direction)$$0.getValue((Property)TIP_DIRECTION);
/* 134 */     if ($$6 == Direction.DOWN && $$3.getBlockTicks().hasScheduledTick($$4, this))
/*     */     {
/* 136 */       return $$0;
/*     */     }
/*     */     
/* 139 */     if ($$1 == $$6.getOpposite() && !canSurvive($$0, (LevelReader)$$3, $$4)) {
/* 140 */       if ($$6 == Direction.DOWN) {
/*     */         
/* 142 */         $$3.scheduleTick($$4, this, 2);
/*     */       } else {
/*     */         
/* 145 */         $$3.scheduleTick($$4, this, 1);
/*     */       } 
/* 147 */       return $$0;
/*     */     } 
/*     */     
/* 150 */     boolean $$7 = ($$0.getValue((Property)THICKNESS) == DripstoneThickness.TIP_MERGE);
/* 151 */     DripstoneThickness $$8 = calculateDripstoneThickness((LevelReader)$$3, $$4, $$6, $$7);
/*     */     
/* 153 */     return (BlockState)$$0.setValue((Property)THICKNESS, (Comparable)$$8);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {
/* 158 */     if ($$0.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 162 */     BlockPos $$4 = $$2.getBlockPos();
/* 163 */     if ($$3.mayInteract($$0, $$4) && $$3.mayBreak($$0) && $$3 instanceof net.minecraft.world.entity.projectile.ThrownTrident && $$3.getDeltaMovement().length() > 0.6D) {
/* 164 */       $$0.destroyBlock($$4, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fallOn(Level $$0, BlockState $$1, BlockPos $$2, Entity $$3, float $$4) {
/* 182 */     if ($$1.getValue((Property)TIP_DIRECTION) == Direction.UP && $$1.getValue((Property)THICKNESS) == DripstoneThickness.TIP) {
/* 183 */       $$3.causeFallDamage($$4 + 2.0F, 2.0F, $$0.damageSources().stalagmite());
/*     */     } else {
/* 185 */       super.fallOn($$0, $$1, $$2, $$3, $$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 191 */     if (!canDrip($$0)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 196 */     float $$4 = $$3.nextFloat();
/* 197 */     if ($$4 > 0.12F) {
/*     */       return;
/*     */     }
/*     */     
/* 201 */     getFluidAboveStalactite($$1, $$2, $$0)
/*     */       
/* 203 */       .filter($$1 -> ($$0 < 0.02F || canFillCauldron($$1.fluid)))
/* 204 */       .ifPresent($$3 -> spawnDripParticle($$0, $$1, $$2, $$3.fluid));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 209 */     if (isStalagmite($$0) && !canSurvive($$0, (LevelReader)$$1, $$2)) {
/* 210 */       $$1.destroyBlock($$2, true);
/*     */     } else {
/* 212 */       spawnFallingStalactite($$0, $$1, $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 218 */     maybeTransferFluid($$0, $$1, $$2, $$3.nextFloat());
/*     */     
/* 220 */     if ($$3.nextFloat() < 0.011377778F && isStalactiteStartPos($$0, (LevelReader)$$1, $$2))
/* 221 */       growStalactiteOrStalagmiteIfPossible($$0, $$1, $$2, $$3); 
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public static void maybeTransferFluid(BlockState $$0, ServerLevel $$1, BlockPos $$2, float $$3) {
/*     */     float $$7;
/* 227 */     if ($$3 > 0.17578125F && $$3 > 0.05859375F) {
/*     */       return;
/*     */     }
/*     */     
/* 231 */     if (!isStalactiteStartPos($$0, (LevelReader)$$1, $$2)) {
/*     */       return;
/*     */     }
/*     */     
/* 235 */     Optional<FluidInfo> $$4 = getFluidAboveStalactite((Level)$$1, $$2, $$0);
/* 236 */     if ($$4.isEmpty()) {
/*     */       return;
/*     */     }
/* 239 */     Fluid $$5 = ((FluidInfo)$$4.get()).fluid;
/*     */ 
/*     */     
/* 242 */     if ($$5 == Fluids.WATER) {
/* 243 */       float $$6 = 0.17578125F;
/* 244 */     } else if ($$5 == Fluids.LAVA) {
/* 245 */       $$7 = 0.05859375F;
/*     */     } else {
/*     */       return;
/*     */     } 
/* 249 */     if ($$3 >= $$7) {
/*     */       return;
/*     */     }
/*     */     
/* 253 */     BlockPos $$9 = findTip($$0, (LevelAccessor)$$1, $$2, 11, false);
/* 254 */     if ($$9 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 258 */     if (((FluidInfo)$$4.get()).sourceState.is(Blocks.MUD) && $$5 == Fluids.WATER) {
/* 259 */       BlockState $$10 = Blocks.CLAY.defaultBlockState();
/* 260 */       $$1.setBlockAndUpdate(((FluidInfo)$$4.get()).pos, $$10);
/* 261 */       Block.pushEntitiesUp(((FluidInfo)$$4.get()).sourceState, $$10, (LevelAccessor)$$1, ((FluidInfo)$$4.get()).pos);
/* 262 */       $$1.gameEvent(GameEvent.BLOCK_CHANGE, ((FluidInfo)$$4.get()).pos, GameEvent.Context.of($$10));
/* 263 */       $$1.levelEvent(1504, $$9, 0);
/*     */       
/*     */       return;
/*     */     } 
/* 267 */     BlockPos $$11 = findFillableCauldronBelowStalactiteTip((Level)$$1, $$9, $$5);
/* 268 */     if ($$11 == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 273 */     $$1.levelEvent(1504, $$9, 0);
/*     */ 
/*     */     
/* 276 */     int $$12 = $$9.getY() - $$11.getY();
/* 277 */     int $$13 = 50 + $$12;
/* 278 */     BlockState $$14 = $$1.getBlockState($$11);
/* 279 */     $$1.scheduleTick($$11, $$14.getBlock(), $$13);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 285 */     Level level = $$0.getLevel();
/* 286 */     BlockPos $$2 = $$0.getClickedPos();
/* 287 */     Direction $$3 = $$0.getNearestLookingVerticalDirection().getOpposite();
/* 288 */     Direction $$4 = calculateTipDirection((LevelReader)level, $$2, $$3);
/* 289 */     if ($$4 == null) {
/* 290 */       return null;
/*     */     }
/*     */     
/* 293 */     boolean $$5 = !$$0.isSecondaryUseActive();
/* 294 */     DripstoneThickness $$6 = calculateDripstoneThickness((LevelReader)level, $$2, $$4, $$5);
/* 295 */     if ($$6 == null) {
/* 296 */       return null;
/*     */     }
/*     */     
/* 299 */     return (BlockState)((BlockState)((BlockState)defaultBlockState()
/* 300 */       .setValue((Property)TIP_DIRECTION, (Comparable)$$4))
/* 301 */       .setValue((Property)THICKNESS, (Comparable)$$6))
/* 302 */       .setValue((Property)WATERLOGGED, Boolean.valueOf((level.getFluidState($$2).getType() == Fluids.WATER)));
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 307 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 308 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 310 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getOcclusionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 315 */     return Shapes.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*     */     VoxelShape $$10;
/* 321 */     DripstoneThickness $$4 = (DripstoneThickness)$$0.getValue((Property)THICKNESS);
/* 322 */     if ($$4 == DripstoneThickness.TIP_MERGE) {
/* 323 */       VoxelShape $$5 = TIP_MERGE_SHAPE;
/* 324 */     } else if ($$4 == DripstoneThickness.TIP) {
/* 325 */       if ($$0.getValue((Property)TIP_DIRECTION) == Direction.DOWN) {
/* 326 */         VoxelShape $$6 = TIP_SHAPE_DOWN;
/*     */       } else {
/* 328 */         VoxelShape $$7 = TIP_SHAPE_UP;
/*     */       } 
/* 330 */     } else if ($$4 == DripstoneThickness.FRUSTUM) {
/* 331 */       VoxelShape $$8 = FRUSTUM_SHAPE;
/* 332 */     } else if ($$4 == DripstoneThickness.MIDDLE) {
/* 333 */       VoxelShape $$9 = MIDDLE_SHAPE;
/*     */     } else {
/* 335 */       $$10 = BASE_SHAPE;
/*     */     } 
/* 337 */     Vec3 $$11 = $$0.getOffset($$1, $$2);
/* 338 */     return $$10.move($$11.x, 0.0D, $$11.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCollisionShapeFullBlock(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 343 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getMaxHorizontalOffset() {
/* 348 */     return 0.125F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBrokenAfterFall(Level $$0, BlockPos $$1, FallingBlockEntity $$2) {
/* 353 */     if (!$$2.isSilent()) {
/* 354 */       $$0.levelEvent(1045, $$1, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public DamageSource getFallDamageSource(Entity $$0) {
/* 360 */     return $$0.damageSources().fallingStalactite($$0);
/*     */   }
/*     */   
/*     */   private static void spawnFallingStalactite(BlockState $$0, ServerLevel $$1, BlockPos $$2) {
/* 364 */     BlockPos.MutableBlockPos $$3 = $$2.mutable();
/* 365 */     BlockState $$4 = $$0;
/*     */     
/* 367 */     while (isStalactite($$4)) {
/* 368 */       FallingBlockEntity $$5 = FallingBlockEntity.fall((Level)$$1, (BlockPos)$$3, $$4);
/* 369 */       if (isTip($$4, true)) {
/*     */ 
/*     */         
/* 372 */         int $$6 = Math.max(1 + $$2.getY() - $$3.getY(), 6);
/* 373 */         float $$7 = 1.0F * $$6;
/*     */         
/* 375 */         $$5.setHurtsEntities($$7, 40);
/*     */         break;
/*     */       } 
/* 378 */       $$3.move(Direction.DOWN);
/* 379 */       $$4 = $$1.getBlockState((BlockPos)$$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public static void growStalactiteOrStalagmiteIfPossible(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 385 */     BlockState $$4 = $$1.getBlockState($$2.above(1));
/* 386 */     BlockState $$5 = $$1.getBlockState($$2.above(2));
/*     */     
/* 388 */     if (!canGrow($$4, $$5)) {
/*     */       return;
/*     */     }
/*     */     
/* 392 */     BlockPos $$6 = findTip($$0, (LevelAccessor)$$1, $$2, 7, false);
/* 393 */     if ($$6 == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 398 */     BlockState $$7 = $$1.getBlockState($$6);
/* 399 */     if (!canDrip($$7) || !canTipGrow($$7, $$1, $$6)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 404 */     if ($$3.nextBoolean()) {
/* 405 */       grow($$1, $$6, Direction.DOWN);
/*     */     } else {
/* 407 */       growStalagmiteBelow($$1, $$6);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void growStalagmiteBelow(ServerLevel $$0, BlockPos $$1) {
/* 415 */     BlockPos.MutableBlockPos $$2 = $$1.mutable();
/* 416 */     for (int $$3 = 0; $$3 < 10; $$3++) {
/* 417 */       $$2.move(Direction.DOWN);
/* 418 */       BlockState $$4 = $$0.getBlockState((BlockPos)$$2);
/* 419 */       if (!$$4.getFluidState().isEmpty()) {
/*     */         return;
/*     */       }
/*     */       
/* 423 */       if (isUnmergedTipWithDirection($$4, Direction.UP) && canTipGrow($$4, $$0, (BlockPos)$$2)) {
/*     */         
/* 425 */         grow($$0, (BlockPos)$$2, Direction.UP);
/*     */         return;
/*     */       } 
/* 428 */       if (isValidPointedDripstonePlacement((LevelReader)$$0, (BlockPos)$$2, Direction.UP) && !$$0.isWaterAt($$2.below())) {
/*     */         
/* 430 */         grow($$0, $$2.below(), Direction.UP);
/*     */         return;
/*     */       } 
/* 433 */       if (!canDripThrough((BlockGetter)$$0, (BlockPos)$$2, $$4)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void grow(ServerLevel $$0, BlockPos $$1, Direction $$2) {
/* 440 */     BlockPos $$3 = $$1.relative($$2);
/* 441 */     BlockState $$4 = $$0.getBlockState($$3);
/* 442 */     if (isUnmergedTipWithDirection($$4, $$2.getOpposite())) {
/* 443 */       createMergedTips($$4, (LevelAccessor)$$0, $$3);
/* 444 */     } else if ($$4.isAir() || $$4.is(Blocks.WATER)) {
/* 445 */       createDripstone((LevelAccessor)$$0, $$3, $$2, DripstoneThickness.TIP);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void createDripstone(LevelAccessor $$0, BlockPos $$1, Direction $$2, DripstoneThickness $$3) {
/* 453 */     BlockState $$4 = (BlockState)((BlockState)((BlockState)Blocks.POINTED_DRIPSTONE.defaultBlockState().setValue((Property)TIP_DIRECTION, (Comparable)$$2)).setValue((Property)THICKNESS, (Comparable)$$3)).setValue((Property)WATERLOGGED, Boolean.valueOf(($$0.getFluidState($$1).getType() == Fluids.WATER)));
/* 454 */     $$0.setBlock($$1, $$4, 3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void createMergedTips(BlockState $$0, LevelAccessor $$1, BlockPos $$2) {
/*     */     BlockPos $$5, $$6;
/* 463 */     if ($$0.getValue((Property)TIP_DIRECTION) == Direction.UP) {
/* 464 */       BlockPos $$3 = $$2;
/* 465 */       BlockPos $$4 = $$2.above();
/*     */     } else {
/* 467 */       $$5 = $$2;
/* 468 */       $$6 = $$2.below();
/*     */     } 
/*     */     
/* 471 */     createDripstone($$1, $$5, Direction.DOWN, DripstoneThickness.TIP_MERGE);
/* 472 */     createDripstone($$1, $$6, Direction.UP, DripstoneThickness.TIP_MERGE);
/*     */   }
/*     */   
/*     */   public static void spawnDripParticle(Level $$0, BlockPos $$1, BlockState $$2) {
/* 476 */     getFluidAboveStalactite($$0, $$1, $$2).ifPresent($$3 -> spawnDripParticle($$0, $$1, $$2, $$3.fluid));
/*     */   }
/*     */   
/*     */   private static void spawnDripParticle(Level $$0, BlockPos $$1, BlockState $$2, Fluid $$3) {
/* 480 */     Vec3 $$4 = $$2.getOffset((BlockGetter)$$0, $$1);
/* 481 */     double $$5 = 0.0625D;
/* 482 */     double $$6 = $$1.getX() + 0.5D + $$4.x;
/* 483 */     double $$7 = (($$1.getY() + 1) - 0.6875F) - 0.0625D;
/* 484 */     double $$8 = $$1.getZ() + 0.5D + $$4.z;
/*     */     
/* 486 */     Fluid $$9 = getDripFluid($$0, $$3);
/* 487 */     SimpleParticleType simpleParticleType = $$9.is(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
/*     */     
/* 489 */     $$0.addParticle((ParticleOptions)simpleParticleType, $$6, $$7, $$8, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static BlockPos findTip(BlockState $$0, LevelAccessor $$1, BlockPos $$2, int $$3, boolean $$4) {
/* 494 */     if (isTip($$0, $$4)) {
/* 495 */       return $$2;
/*     */     }
/* 497 */     Direction $$5 = (Direction)$$0.getValue((Property)TIP_DIRECTION);
/* 498 */     BiPredicate<BlockPos, BlockState> $$6 = ($$1, $$2) -> ($$2.is(Blocks.POINTED_DRIPSTONE) && $$2.getValue((Property)TIP_DIRECTION) == $$0);
/* 499 */     return findBlockVertical($$1, $$2, $$5.getAxisDirection(), $$6, $$1 -> isTip($$1, $$0), $$3).orElse(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static Direction calculateTipDirection(LevelReader $$0, BlockPos $$1, Direction $$2) {
/*     */     Direction $$4;
/* 508 */     if (isValidPointedDripstonePlacement($$0, $$1, $$2)) {
/* 509 */       Direction $$3 = $$2;
/* 510 */     } else if (isValidPointedDripstonePlacement($$0, $$1, $$2.getOpposite())) {
/* 511 */       $$4 = $$2.getOpposite();
/*     */     } else {
/* 513 */       return null;
/*     */     } 
/* 515 */     return $$4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DripstoneThickness calculateDripstoneThickness(LevelReader $$0, BlockPos $$1, Direction $$2, boolean $$3) {
/* 522 */     Direction $$4 = $$2.getOpposite();
/* 523 */     BlockState $$5 = $$0.getBlockState($$1.relative($$2));
/*     */     
/* 525 */     if (isPointedDripstoneWithDirection($$5, $$4)) {
/*     */       
/* 527 */       if ($$3 || $$5.getValue((Property)THICKNESS) == DripstoneThickness.TIP_MERGE) {
/* 528 */         return DripstoneThickness.TIP_MERGE;
/*     */       }
/* 530 */       return DripstoneThickness.TIP;
/*     */     } 
/*     */ 
/*     */     
/* 534 */     if (!isPointedDripstoneWithDirection($$5, $$2)) {
/* 535 */       return DripstoneThickness.TIP;
/*     */     }
/*     */ 
/*     */     
/* 539 */     DripstoneThickness $$6 = (DripstoneThickness)$$5.getValue((Property)THICKNESS);
/* 540 */     if ($$6 == DripstoneThickness.TIP || $$6 == DripstoneThickness.TIP_MERGE) {
/* 541 */       return DripstoneThickness.FRUSTUM;
/*     */     }
/*     */     
/* 544 */     BlockState $$7 = $$0.getBlockState($$1.relative($$4));
/* 545 */     if (!isPointedDripstoneWithDirection($$7, $$2)) {
/* 546 */       return DripstoneThickness.BASE;
/*     */     }
/* 548 */     return DripstoneThickness.MIDDLE;
/*     */   }
/*     */   
/*     */   public static boolean canDrip(BlockState $$0) {
/* 552 */     return (isStalactite($$0) && $$0.getValue((Property)THICKNESS) == DripstoneThickness.TIP && !((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue());
/*     */   }
/*     */   
/*     */   private static boolean canTipGrow(BlockState $$0, ServerLevel $$1, BlockPos $$2) {
/* 556 */     Direction $$3 = (Direction)$$0.getValue((Property)TIP_DIRECTION);
/* 557 */     BlockPos $$4 = $$2.relative($$3);
/* 558 */     BlockState $$5 = $$1.getBlockState($$4);
/*     */     
/* 560 */     if (!$$5.getFluidState().isEmpty()) {
/* 561 */       return false;
/*     */     }
/* 563 */     if ($$5.isAir()) {
/* 564 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 568 */     return isUnmergedTipWithDirection($$5, $$3.getOpposite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Optional<BlockPos> findRootBlock(Level $$0, BlockPos $$1, BlockState $$2, int $$3) {
/* 576 */     Direction $$4 = (Direction)$$2.getValue((Property)TIP_DIRECTION);
/* 577 */     BiPredicate<BlockPos, BlockState> $$5 = ($$1, $$2) -> ($$2.is(Blocks.POINTED_DRIPSTONE) && $$2.getValue((Property)TIP_DIRECTION) == $$0);
/* 578 */     return findBlockVertical((LevelAccessor)$$0, $$1, $$4.getOpposite().getAxisDirection(), $$5, $$0 -> !$$0.is(Blocks.POINTED_DRIPSTONE), $$3);
/*     */   }
/*     */   
/*     */   private static boolean isValidPointedDripstonePlacement(LevelReader $$0, BlockPos $$1, Direction $$2) {
/* 582 */     BlockPos $$3 = $$1.relative($$2.getOpposite());
/* 583 */     BlockState $$4 = $$0.getBlockState($$3);
/*     */     
/* 585 */     return ($$4.isFaceSturdy((BlockGetter)$$0, $$3, $$2) || isPointedDripstoneWithDirection($$4, $$2));
/*     */   }
/*     */   
/*     */   private static boolean isTip(BlockState $$0, boolean $$1) {
/* 589 */     if (!$$0.is(Blocks.POINTED_DRIPSTONE)) {
/* 590 */       return false;
/*     */     }
/* 592 */     DripstoneThickness $$2 = (DripstoneThickness)$$0.getValue((Property)THICKNESS);
/* 593 */     return ($$2 == DripstoneThickness.TIP || ($$1 && $$2 == DripstoneThickness.TIP_MERGE));
/*     */   }
/*     */   
/*     */   private static boolean isUnmergedTipWithDirection(BlockState $$0, Direction $$1) {
/* 597 */     return (isTip($$0, false) && $$0.getValue((Property)TIP_DIRECTION) == $$1);
/*     */   }
/*     */   
/*     */   private static boolean isStalactite(BlockState $$0) {
/* 601 */     return isPointedDripstoneWithDirection($$0, Direction.DOWN);
/*     */   }
/*     */   
/*     */   private static boolean isStalagmite(BlockState $$0) {
/* 605 */     return isPointedDripstoneWithDirection($$0, Direction.UP);
/*     */   }
/*     */   
/*     */   private static boolean isStalactiteStartPos(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 609 */     return (isStalactite($$0) && !$$1.getBlockState($$2.above()).is(Blocks.POINTED_DRIPSTONE));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 614 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean isPointedDripstoneWithDirection(BlockState $$0, Direction $$1) {
/* 618 */     return ($$0.is(Blocks.POINTED_DRIPSTONE) && $$0.getValue((Property)TIP_DIRECTION) == $$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static BlockPos findFillableCauldronBelowStalactiteTip(Level $$0, BlockPos $$1, Fluid $$2) {
/* 623 */     Predicate<BlockState> $$3 = $$1 -> ($$1.getBlock() instanceof AbstractCauldronBlock && ((AbstractCauldronBlock)$$1.getBlock()).canReceiveStalactiteDrip($$0));
/* 624 */     BiPredicate<BlockPos, BlockState> $$4 = ($$1, $$2) -> canDripThrough((BlockGetter)$$0, $$1, $$2);
/* 625 */     return findBlockVertical((LevelAccessor)$$0, $$1, Direction.DOWN.getAxisDirection(), $$4, $$3, 11).orElse(null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BlockPos findStalactiteTipAboveCauldron(Level $$0, BlockPos $$1) {
/* 630 */     BiPredicate<BlockPos, BlockState> $$2 = ($$1, $$2) -> canDripThrough((BlockGetter)$$0, $$1, $$2);
/* 631 */     return findBlockVertical((LevelAccessor)$$0, $$1, Direction.UP.getAxisDirection(), $$2, PointedDripstoneBlock::canDrip, 11).orElse(null);
/*     */   }
/*     */   
/*     */   public static Fluid getCauldronFillFluidType(ServerLevel $$0, BlockPos $$1) {
/* 635 */     return getFluidAboveStalactite((Level)$$0, $$1, $$0.getBlockState($$1))
/* 636 */       .<Fluid>map($$0 -> $$0.fluid)
/* 637 */       .filter(PointedDripstoneBlock::canFillCauldron)
/* 638 */       .orElse(Fluids.EMPTY);
/*     */   }
/*     */   
/*     */   private static Optional<FluidInfo> getFluidAboveStalactite(Level $$0, BlockPos $$1, BlockState $$2) {
/* 642 */     if (!isStalactite($$2)) {
/* 643 */       return Optional.empty();
/*     */     }
/*     */     
/* 646 */     return findRootBlock($$0, $$1, $$2, 11).map($$1 -> {
/*     */           Fluid $$5;
/*     */           BlockPos $$2 = $$1.above();
/*     */           BlockState $$3 = $$0.getBlockState($$2);
/*     */           if ($$3.is(Blocks.MUD) && !$$0.dimensionType().ultraWarm()) {
/*     */             FlowingFluid flowingFluid = Fluids.WATER;
/*     */           } else {
/*     */             $$5 = $$0.getFluidState($$2).getType();
/*     */           } 
/*     */           return new FluidInfo($$2, $$5, $$3);
/*     */         });
/*     */   }
/*     */   
/*     */   private static boolean canFillCauldron(Fluid $$0) {
/* 660 */     return ($$0 == Fluids.LAVA || $$0 == Fluids.WATER);
/*     */   }
/*     */   
/*     */   private static boolean canGrow(BlockState $$0, BlockState $$1) {
/* 664 */     return ($$0.is(Blocks.DRIPSTONE_BLOCK) && $$1.is(Blocks.WATER) && $$1.getFluidState().isSource());
/*     */   }
/*     */   
/*     */   private static Fluid getDripFluid(Level $$0, Fluid $$1) {
/* 668 */     if ($$1.isSame(Fluids.EMPTY)) {
/* 669 */       return $$0.dimensionType().ultraWarm() ? (Fluid)Fluids.LAVA : (Fluid)Fluids.WATER;
/*     */     }
/* 671 */     return $$1;
/*     */   }
/*     */   
/*     */   private static Optional<BlockPos> findBlockVertical(LevelAccessor $$0, BlockPos $$1, Direction.AxisDirection $$2, BiPredicate<BlockPos, BlockState> $$3, Predicate<BlockState> $$4, int $$5) {
/* 675 */     Direction $$6 = Direction.get($$2, Direction.Axis.Y);
/* 676 */     BlockPos.MutableBlockPos $$7 = $$1.mutable();
/*     */     
/* 678 */     for (int $$8 = 1; $$8 < $$5; $$8++) {
/* 679 */       $$7.move($$6);
/* 680 */       BlockState $$9 = $$0.getBlockState((BlockPos)$$7);
/* 681 */       if ($$4.test($$9)) {
/* 682 */         return Optional.of($$7.immutable());
/*     */       }
/* 684 */       if ($$0.isOutsideBuildHeight($$7.getY()) || !$$3.test($$7, $$9)) {
/* 685 */         return Optional.empty();
/*     */       }
/*     */     } 
/* 688 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canDripThrough(BlockGetter $$0, BlockPos $$1, BlockState $$2) {
/* 697 */     if ($$2.isAir()) {
/* 698 */       return true;
/*     */     }
/* 700 */     if ($$2.isSolidRender($$0, $$1)) {
/* 701 */       return false;
/*     */     }
/* 703 */     if (!$$2.getFluidState().isEmpty()) {
/* 704 */       return false;
/*     */     }
/* 706 */     VoxelShape $$3 = $$2.getCollisionShape($$0, $$1);
/* 707 */     return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, $$3, BooleanOp.AND);
/*     */   }
/*     */   static final class FluidInfo extends Record { final BlockPos pos; final Fluid fluid; final BlockState sourceState;
/* 710 */     FluidInfo(BlockPos $$0, Fluid $$1, BlockState $$2) { this.pos = $$0; this.fluid = $$1; this.sourceState = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/block/PointedDripstoneBlock$FluidInfo;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #710	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 710 */       //   0	7	0	this	Lnet/minecraft/world/level/block/PointedDripstoneBlock$FluidInfo; } public BlockPos pos() { return this.pos; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/block/PointedDripstoneBlock$FluidInfo;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #710	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/block/PointedDripstoneBlock$FluidInfo; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/block/PointedDripstoneBlock$FluidInfo;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #710	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/block/PointedDripstoneBlock$FluidInfo;
/* 710 */       //   0	8	1	$$0	Ljava/lang/Object; } public Fluid fluid() { return this.fluid; } public BlockState sourceState() { return this.sourceState; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\PointedDripstoneBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */