/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.CollisionGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.entity.BedBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BedPart;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class BedBlock extends HorizontalDirectionalBlock implements EntityBlock {
/*     */   static {
/*  46 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)DyeColor.CODEC.fieldOf("color").forGetter(BedBlock::getColor), (App)propertiesCodec()).apply((Applicative)$$0, BedBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<BedBlock> CODEC;
/*     */   
/*     */   public MapCodec<BedBlock> codec() {
/*  53 */     return CODEC;
/*     */   }
/*     */   
/*  56 */   public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
/*  57 */   public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
/*     */   
/*     */   protected static final int HEIGHT = 9;
/*  60 */   protected static final VoxelShape BASE = Block.box(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D);
/*     */   
/*     */   private static final int LEG_WIDTH = 3;
/*     */   
/*  64 */   protected static final VoxelShape LEG_NORTH_WEST = Block.box(0.0D, 0.0D, 0.0D, 3.0D, 3.0D, 3.0D);
/*  65 */   protected static final VoxelShape LEG_SOUTH_WEST = Block.box(0.0D, 0.0D, 13.0D, 3.0D, 3.0D, 16.0D);
/*  66 */   protected static final VoxelShape LEG_NORTH_EAST = Block.box(13.0D, 0.0D, 0.0D, 16.0D, 3.0D, 3.0D);
/*  67 */   protected static final VoxelShape LEG_SOUTH_EAST = Block.box(13.0D, 0.0D, 13.0D, 16.0D, 3.0D, 16.0D);
/*     */   
/*  69 */   protected static final VoxelShape NORTH_SHAPE = Shapes.or(BASE, new VoxelShape[] { LEG_NORTH_WEST, LEG_NORTH_EAST });
/*  70 */   protected static final VoxelShape SOUTH_SHAPE = Shapes.or(BASE, new VoxelShape[] { LEG_SOUTH_WEST, LEG_SOUTH_EAST });
/*  71 */   protected static final VoxelShape WEST_SHAPE = Shapes.or(BASE, new VoxelShape[] { LEG_NORTH_WEST, LEG_SOUTH_WEST });
/*  72 */   protected static final VoxelShape EAST_SHAPE = Shapes.or(BASE, new VoxelShape[] { LEG_NORTH_EAST, LEG_SOUTH_EAST });
/*     */   
/*     */   private final DyeColor color;
/*     */   
/*     */   public BedBlock(DyeColor $$0, BlockBehaviour.Properties $$1) {
/*  77 */     super($$1);
/*  78 */     this.color = $$0;
/*  79 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)PART, (Comparable)BedPart.FOOT)).setValue((Property)OCCUPIED, Boolean.valueOf(false)));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Direction getBedOrientation(BlockGetter $$0, BlockPos $$1) {
/*  84 */     BlockState $$2 = $$0.getBlockState($$1);
/*  85 */     return ($$2.getBlock() instanceof BedBlock) ? (Direction)$$2.getValue((Property)FACING) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  90 */     if ($$1.isClientSide) {
/*  91 */       return InteractionResult.CONSUME;
/*     */     }
/*     */     
/*  94 */     if ($$0.getValue((Property)PART) != BedPart.HEAD) {
/*     */       
/*  96 */       $$2 = $$2.relative((Direction)$$0.getValue((Property)FACING));
/*  97 */       $$0 = $$1.getBlockState($$2);
/*  98 */       if (!$$0.is(this)) {
/*  99 */         return InteractionResult.CONSUME;
/*     */       }
/*     */     } 
/*     */     
/* 103 */     if (!canSetSpawn($$1)) {
/*     */       
/* 105 */       $$1.removeBlock($$2, false);
/*     */ 
/*     */       
/* 108 */       BlockPos $$6 = $$2.relative(((Direction)$$0.getValue((Property)FACING)).getOpposite());
/* 109 */       if ($$1.getBlockState($$6).is(this)) {
/* 110 */         $$1.removeBlock($$6, false);
/*     */       }
/*     */       
/* 113 */       Vec3 $$7 = $$2.getCenter();
/* 114 */       $$1.explode(null, $$1.damageSources().badRespawnPointExplosion($$7), null, $$7, 5.0F, true, Level.ExplosionInteraction.BLOCK);
/* 115 */       return InteractionResult.SUCCESS;
/*     */     } 
/*     */     
/* 118 */     if (((Boolean)$$0.getValue((Property)OCCUPIED)).booleanValue()) {
/* 119 */       if (!kickVillagerOutOfBed($$1, $$2)) {
/* 120 */         $$3.displayClientMessage((Component)Component.translatable("block.minecraft.bed.occupied"), true);
/*     */       }
/* 122 */       return InteractionResult.SUCCESS;
/*     */     } 
/*     */     
/* 125 */     $$3.startSleepInBed($$2)
/* 126 */       .ifLeft($$1 -> {
/*     */           if ($$1.getMessage() != null) {
/*     */             $$0.displayClientMessage($$1.getMessage(), true);
/*     */           }
/*     */         });
/* 131 */     return InteractionResult.SUCCESS;
/*     */   }
/*     */   
/*     */   public static boolean canSetSpawn(Level $$0) {
/* 135 */     return $$0.dimensionType().bedWorks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean kickVillagerOutOfBed(Level $$0, BlockPos $$1) {
/* 142 */     List<Villager> $$2 = $$0.getEntitiesOfClass(Villager.class, new AABB($$1), LivingEntity::isSleeping);
/* 143 */     if ($$2.isEmpty()) {
/* 144 */       return false;
/*     */     }
/* 146 */     ((Villager)$$2.get(0)).stopSleeping();
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fallOn(Level $$0, BlockState $$1, BlockPos $$2, Entity $$3, float $$4) {
/* 152 */     super.fallOn($$0, $$1, $$2, $$3, $$4 * 0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEntityAfterFallOn(BlockGetter $$0, Entity $$1) {
/* 157 */     if ($$1.isSuppressingBounce()) {
/* 158 */       super.updateEntityAfterFallOn($$0, $$1);
/*     */     } else {
/* 160 */       bounceUp($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void bounceUp(Entity $$0) {
/* 165 */     Vec3 $$1 = $$0.getDeltaMovement();
/* 166 */     if ($$1.y < 0.0D) {
/*     */       
/* 168 */       double $$2 = ($$0 instanceof LivingEntity) ? 1.0D : 0.8D;
/* 169 */       $$0.setDeltaMovement($$1.x, -$$1.y * 0.6600000262260437D * $$2, $$1.z);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 179 */     if ($$1 == getNeighbourDirection((BedPart)$$0.getValue((Property)PART), (Direction)$$0.getValue((Property)FACING))) {
/* 180 */       if ($$2.is(this) && $$2.getValue((Property)PART) != $$0.getValue((Property)PART)) {
/* 181 */         return (BlockState)$$0.setValue((Property)OCCUPIED, $$2.getValue((Property)OCCUPIED));
/*     */       }
/* 183 */       return Blocks.AIR.defaultBlockState();
/*     */     } 
/*     */ 
/*     */     
/* 187 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   private static Direction getNeighbourDirection(BedPart $$0, Direction $$1) {
/* 191 */     return ($$0 == BedPart.FOOT) ? $$1 : $$1.getOpposite();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState playerWillDestroy(Level $$0, BlockPos $$1, BlockState $$2, Player $$3) {
/* 196 */     if (!$$0.isClientSide && $$3.isCreative()) {
/* 197 */       BedPart $$4 = (BedPart)$$2.getValue((Property)PART);
/* 198 */       if ($$4 == BedPart.FOOT) {
/* 199 */         BlockPos $$5 = $$1.relative(getNeighbourDirection($$4, (Direction)$$2.getValue((Property)FACING)));
/* 200 */         BlockState $$6 = $$0.getBlockState($$5);
/* 201 */         if ($$6.is(this) && $$6.getValue((Property)PART) == BedPart.HEAD) {
/*     */           
/* 203 */           $$0.setBlock($$5, Blocks.AIR.defaultBlockState(), 35);
/* 204 */           $$0.levelEvent($$3, 2001, $$5, Block.getId($$6));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     return super.playerWillDestroy($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 215 */     Direction $$1 = $$0.getHorizontalDirection();
/*     */     
/* 217 */     BlockPos $$2 = $$0.getClickedPos();
/* 218 */     BlockPos $$3 = $$2.relative($$1);
/* 219 */     Level $$4 = $$0.getLevel();
/* 220 */     if ($$4.getBlockState($$3).canBeReplaced($$0) && $$4.getWorldBorder().isWithinBounds($$3)) {
/* 221 */       return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$1);
/*     */     }
/*     */     
/* 224 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 229 */     Direction $$4 = getConnectedDirection($$0).getOpposite();
/* 230 */     switch ($$4) {
/*     */       case NORTH:
/* 232 */         return NORTH_SHAPE;
/*     */       case SOUTH:
/* 234 */         return SOUTH_SHAPE;
/*     */       case WEST:
/* 236 */         return WEST_SHAPE;
/*     */     } 
/* 238 */     return EAST_SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Direction getConnectedDirection(BlockState $$0) {
/* 243 */     Direction $$1 = (Direction)$$0.getValue((Property)FACING);
/* 244 */     return ($$0.getValue((Property)PART) == BedPart.HEAD) ? $$1.getOpposite() : $$1;
/*     */   }
/*     */   
/*     */   public static DoubleBlockCombiner.BlockType getBlockType(BlockState $$0) {
/* 248 */     BedPart $$1 = (BedPart)$$0.getValue((Property)PART);
/* 249 */     if ($$1 == BedPart.HEAD) {
/* 250 */       return DoubleBlockCombiner.BlockType.FIRST;
/*     */     }
/* 252 */     return DoubleBlockCombiner.BlockType.SECOND;
/*     */   }
/*     */   
/*     */   private static boolean isBunkBed(BlockGetter $$0, BlockPos $$1) {
/* 256 */     return $$0.getBlockState($$1.below()).getBlock() instanceof BedBlock;
/*     */   }
/*     */   
/*     */   public static Optional<Vec3> findStandUpPosition(EntityType<?> $$0, CollisionGetter $$1, BlockPos $$2, Direction $$3, float $$4) {
/* 260 */     Direction $$5 = $$3.getClockWise();
/* 261 */     Direction $$6 = $$5.isFacingAngle($$4) ? $$5.getOpposite() : $$5;
/*     */     
/* 263 */     if (isBunkBed((BlockGetter)$$1, $$2)) {
/* 264 */       return findBunkBedStandUpPosition($$0, $$1, $$2, $$3, $$6);
/*     */     }
/*     */     
/* 267 */     int[][] $$7 = bedStandUpOffsets($$3, $$6);
/*     */     
/* 269 */     Optional<Vec3> $$8 = findStandUpPositionAtOffset($$0, $$1, $$2, $$7, true);
/* 270 */     if ($$8.isPresent()) {
/* 271 */       return $$8;
/*     */     }
/* 273 */     return findStandUpPositionAtOffset($$0, $$1, $$2, $$7, false);
/*     */   }
/*     */   
/*     */   private static Optional<Vec3> findBunkBedStandUpPosition(EntityType<?> $$0, CollisionGetter $$1, BlockPos $$2, Direction $$3, Direction $$4) {
/* 277 */     int[][] $$5 = bedSurroundStandUpOffsets($$3, $$4);
/*     */     
/* 279 */     Optional<Vec3> $$6 = findStandUpPositionAtOffset($$0, $$1, $$2, $$5, true);
/* 280 */     if ($$6.isPresent()) {
/* 281 */       return $$6;
/*     */     }
/*     */     
/* 284 */     BlockPos $$7 = $$2.below();
/*     */     
/* 286 */     Optional<Vec3> $$8 = findStandUpPositionAtOffset($$0, $$1, $$7, $$5, true);
/* 287 */     if ($$8.isPresent()) {
/* 288 */       return $$8;
/*     */     }
/*     */     
/* 291 */     int[][] $$9 = bedAboveStandUpOffsets($$3);
/*     */     
/* 293 */     Optional<Vec3> $$10 = findStandUpPositionAtOffset($$0, $$1, $$2, $$9, true);
/* 294 */     if ($$10.isPresent()) {
/* 295 */       return $$10;
/*     */     }
/*     */     
/* 298 */     Optional<Vec3> $$11 = findStandUpPositionAtOffset($$0, $$1, $$2, $$5, false);
/* 299 */     if ($$11.isPresent()) {
/* 300 */       return $$11;
/*     */     }
/*     */     
/* 303 */     Optional<Vec3> $$12 = findStandUpPositionAtOffset($$0, $$1, $$7, $$5, false);
/* 304 */     if ($$12.isPresent()) {
/* 305 */       return $$12;
/*     */     }
/*     */     
/* 308 */     return findStandUpPositionAtOffset($$0, $$1, $$2, $$9, false);
/*     */   }
/*     */   
/*     */   private static Optional<Vec3> findStandUpPositionAtOffset(EntityType<?> $$0, CollisionGetter $$1, BlockPos $$2, int[][] $$3, boolean $$4) {
/* 312 */     BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
/* 313 */     for (int[] $$6 : $$3) {
/* 314 */       $$5.set($$2.getX() + $$6[0], $$2.getY(), $$2.getZ() + $$6[1]);
/*     */       
/* 316 */       Vec3 $$7 = DismountHelper.findSafeDismountLocation($$0, $$1, (BlockPos)$$5, $$4);
/* 317 */       if ($$7 != null) {
/* 318 */         return Optional.of($$7);
/*     */       }
/*     */     } 
/* 321 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 326 */     return RenderShape.ENTITYBLOCK_ANIMATED;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 331 */     $$0.add(new Property[] { (Property)FACING, (Property)PART, (Property)OCCUPIED });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 336 */     return (BlockEntity)new BedBlockEntity($$0, $$1, this.color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, @Nullable LivingEntity $$3, ItemStack $$4) {
/* 341 */     super.setPlacedBy($$0, $$1, $$2, $$3, $$4);
/*     */ 
/*     */     
/* 344 */     if (!$$0.isClientSide) {
/* 345 */       BlockPos $$5 = $$1.relative((Direction)$$2.getValue((Property)FACING));
/* 346 */       $$0.setBlock($$5, (BlockState)$$2.setValue((Property)PART, (Comparable)BedPart.HEAD), 3);
/*     */       
/* 348 */       $$0.blockUpdated($$1, Blocks.AIR);
/* 349 */       $$2.updateNeighbourShapes((LevelAccessor)$$0, $$1, 3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public DyeColor getColor() {
/* 354 */     return this.color;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getSeed(BlockState $$0, BlockPos $$1) {
/* 359 */     BlockPos $$2 = $$1.relative((Direction)$$0.getValue((Property)FACING), ($$0.getValue((Property)PART) == BedPart.HEAD) ? 0 : 1);
/* 360 */     return Mth.getSeed($$2.getX(), $$1.getY(), $$2.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 365 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[][] bedStandUpOffsets(Direction $$0, Direction $$1) {
/* 372 */     return (int[][])ArrayUtils.addAll((Object[])bedSurroundStandUpOffsets($$0, $$1), (Object[])bedAboveStandUpOffsets($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[][] bedSurroundStandUpOffsets(Direction $$0, Direction $$1) {
/* 380 */     return new int[][] { { $$1
/* 381 */           .getStepX(), $$1.getStepZ() }, { $$1
/* 382 */           .getStepX() - $$0.getStepX(), $$1.getStepZ() - $$0.getStepZ() }, { $$1
/* 383 */           .getStepX() - $$0.getStepX() * 2, $$1.getStepZ() - $$0.getStepZ() * 2
/* 384 */         }, { -$$0.getStepX() * 2, -$$0.getStepZ() * 2
/* 385 */         }, { -$$1.getStepX() - $$0.getStepX() * 2, -$$1.getStepZ() - $$0.getStepZ() * 2
/* 386 */         }, { -$$1.getStepX() - $$0.getStepX(), -$$1.getStepZ() - $$0.getStepZ()
/* 387 */         }, { -$$1.getStepX(), -$$1.getStepZ()
/* 388 */         }, { -$$1.getStepX() + $$0.getStepX(), -$$1.getStepZ() + $$0.getStepZ() }, { $$0
/* 389 */           .getStepX(), $$0.getStepZ() }, { $$1
/* 390 */           .getStepX() + $$0.getStepX(), $$1.getStepZ() + $$0.getStepZ() } };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[][] bedAboveStandUpOffsets(Direction $$0) {
/* 396 */     return new int[][] { { 0, 0
/*     */         },
/* 398 */         { -$$0.getStepX(), -$$0.getStepZ() } };
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BedBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */