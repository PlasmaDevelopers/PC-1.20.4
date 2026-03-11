/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.Tilt;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class BigDripleafBlock extends HorizontalDirectionalBlock implements BonemealableBlock, SimpleWaterloggedBlock {
/*  44 */   public static final MapCodec<BigDripleafBlock> CODEC = simpleCodec(BigDripleafBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<BigDripleafBlock> codec() {
/*  48 */     return CODEC;
/*     */   }
/*     */   
/*  51 */   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*  52 */   private static final EnumProperty<Tilt> TILT = BlockStateProperties.TILT; private static final int NO_TICK = -1; private static final Object2IntMap<Tilt> DELAY_UNTIL_NEXT_TILT_STATE; private static final int MAX_GEN_HEIGHT = 5;
/*     */   
/*     */   static {
/*  55 */     DELAY_UNTIL_NEXT_TILT_STATE = (Object2IntMap<Tilt>)Util.make(new Object2IntArrayMap(), $$0 -> {
/*     */           $$0.defaultReturnValue(-1);
/*     */           $$0.put(Tilt.UNSTABLE, 10);
/*     */           $$0.put(Tilt.PARTIAL, 10);
/*     */           $$0.put(Tilt.FULL, 100);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static final int STEM_WIDTH = 6;
/*     */   
/*     */   private static final int ENTITY_DETECTION_MIN_Y = 11;
/*     */   private static final int LOWEST_LEAF_TOP = 13;
/*  68 */   private static final Map<Tilt, VoxelShape> LEAF_SHAPES = (Map<Tilt, VoxelShape>)ImmutableMap.of(Tilt.NONE, 
/*     */       
/*  70 */       Block.box(0.0D, 11.0D, 0.0D, 16.0D, 15.0D, 16.0D), Tilt.UNSTABLE, 
/*  71 */       Block.box(0.0D, 11.0D, 0.0D, 16.0D, 15.0D, 16.0D), Tilt.PARTIAL, 
/*  72 */       Block.box(0.0D, 11.0D, 0.0D, 16.0D, 13.0D, 16.0D), Tilt.FULL, 
/*  73 */       Shapes.empty());
/*     */ 
/*     */   
/*  76 */   private static final VoxelShape STEM_SLICER = Block.box(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  77 */   private static final Map<Direction, VoxelShape> STEM_SHAPES = (Map<Direction, VoxelShape>)ImmutableMap.of(Direction.NORTH, 
/*  78 */       Shapes.joinUnoptimized(BigDripleafStemBlock.NORTH_SHAPE, STEM_SLICER, BooleanOp.ONLY_FIRST), Direction.SOUTH, 
/*  79 */       Shapes.joinUnoptimized(BigDripleafStemBlock.SOUTH_SHAPE, STEM_SLICER, BooleanOp.ONLY_FIRST), Direction.EAST, 
/*  80 */       Shapes.joinUnoptimized(BigDripleafStemBlock.EAST_SHAPE, STEM_SLICER, BooleanOp.ONLY_FIRST), Direction.WEST, 
/*  81 */       Shapes.joinUnoptimized(BigDripleafStemBlock.WEST_SHAPE, STEM_SLICER, BooleanOp.ONLY_FIRST));
/*     */   
/*     */   private final Map<BlockState, VoxelShape> shapesCache;
/*     */ 
/*     */   
/*     */   protected BigDripleafBlock(BlockBehaviour.Properties $$0) {
/*  87 */     super($$0);
/*  88 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any())
/*  89 */         .setValue((Property)WATERLOGGED, Boolean.valueOf(false)))
/*  90 */         .setValue((Property)FACING, (Comparable)Direction.NORTH))
/*  91 */         .setValue((Property)TILT, (Comparable)Tilt.NONE));
/*     */     
/*  93 */     this.shapesCache = (Map<BlockState, VoxelShape>)getShapeForEachState(BigDripleafBlock::calculateShape);
/*     */   }
/*     */   
/*     */   private static VoxelShape calculateShape(BlockState $$0) {
/*  97 */     return Shapes.or(LEAF_SHAPES.get($$0.getValue((Property)TILT)), STEM_SHAPES.get($$0.getValue((Property)FACING)));
/*     */   }
/*     */   
/*     */   public static void placeWithRandomHeight(LevelAccessor $$0, RandomSource $$1, BlockPos $$2, Direction $$3) {
/* 101 */     int $$4 = Mth.nextInt($$1, 2, 5);
/*     */     
/* 103 */     BlockPos.MutableBlockPos $$5 = $$2.mutable();
/*     */ 
/*     */     
/* 106 */     int $$6 = 0;
/* 107 */     while ($$6 < $$4 && canPlaceAt((LevelHeightAccessor)$$0, (BlockPos)$$5, $$0.getBlockState((BlockPos)$$5))) {
/* 108 */       $$6++;
/* 109 */       $$5.move(Direction.UP);
/*     */     } 
/* 111 */     int $$7 = $$2.getY() + $$6 - 1;
/*     */ 
/*     */     
/* 114 */     $$5.setY($$2.getY());
/* 115 */     while ($$5.getY() < $$7) {
/* 116 */       BigDripleafStemBlock.place($$0, (BlockPos)$$5, $$0.getFluidState((BlockPos)$$5), $$3);
/* 117 */       $$5.move(Direction.UP);
/*     */     } 
/*     */ 
/*     */     
/* 121 */     place($$0, (BlockPos)$$5, $$0.getFluidState((BlockPos)$$5), $$3);
/*     */   }
/*     */   
/*     */   private static boolean canReplace(BlockState $$0) {
/* 125 */     return ($$0.isAir() || $$0.is(Blocks.WATER) || $$0.is(Blocks.SMALL_DRIPLEAF));
/*     */   }
/*     */   
/*     */   protected static boolean canPlaceAt(LevelHeightAccessor $$0, BlockPos $$1, BlockState $$2) {
/* 129 */     return (!$$0.isOutsideBuildHeight($$1) && canReplace($$2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean place(LevelAccessor $$0, BlockPos $$1, FluidState $$2, Direction $$3) {
/* 135 */     BlockState $$4 = (BlockState)((BlockState)Blocks.BIG_DRIPLEAF.defaultBlockState().setValue((Property)WATERLOGGED, Boolean.valueOf($$2.isSourceOfType((Fluid)Fluids.WATER)))).setValue((Property)FACING, (Comparable)$$3);
/* 136 */     return $$0.setBlock($$1, $$4, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {
/* 141 */     setTiltAndScheduleTick($$1, $$0, $$2.getBlockPos(), Tilt.FULL, SoundEvents.BIG_DRIPLEAF_TILT_DOWN);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 146 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 147 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 149 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 154 */     BlockPos $$3 = $$2.below();
/* 155 */     BlockState $$4 = $$1.getBlockState($$3);
/* 156 */     return ($$4.is(this) || $$4.is(Blocks.BIG_DRIPLEAF_STEM) || $$4.is(BlockTags.BIG_DRIPLEAF_PLACEABLE));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 161 */     if ($$1 == Direction.DOWN && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 162 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/* 164 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 165 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/* 168 */     if ($$1 == Direction.UP && $$2.is(this)) {
/* 169 */       return Blocks.BIG_DRIPLEAF_STEM.withPropertiesOf($$0);
/*     */     }
/*     */     
/* 172 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 177 */     BlockState $$3 = $$0.getBlockState($$1.above());
/* 178 */     return canReplace($$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 183 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 188 */     BlockPos $$4 = $$2.above();
/* 189 */     BlockState $$5 = $$0.getBlockState($$4);
/* 190 */     if (canPlaceAt((LevelHeightAccessor)$$0, $$4, $$5)) {
/* 191 */       Direction $$6 = (Direction)$$3.getValue((Property)FACING);
/* 192 */       BigDripleafStemBlock.place((LevelAccessor)$$0, $$2, $$3.getFluidState(), $$6);
/* 193 */       place((LevelAccessor)$$0, $$4, $$5.getFluidState(), $$6);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 199 */     if ($$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 203 */     if ($$0.getValue((Property)TILT) == Tilt.NONE && canEntityTilt($$2, $$3) && !$$1.hasNeighborSignal($$2)) {
/* 204 */       setTiltAndScheduleTick($$0, $$1, $$2, Tilt.UNSTABLE, (SoundEvent)null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 210 */     if ($$1.hasNeighborSignal($$2)) {
/* 211 */       resetTilt($$0, (Level)$$1, $$2);
/*     */       
/*     */       return;
/*     */     } 
/* 215 */     Tilt $$4 = (Tilt)$$0.getValue((Property)TILT);
/*     */     
/* 217 */     if ($$4 == Tilt.UNSTABLE) {
/* 218 */       setTiltAndScheduleTick($$0, (Level)$$1, $$2, Tilt.PARTIAL, SoundEvents.BIG_DRIPLEAF_TILT_DOWN);
/* 219 */     } else if ($$4 == Tilt.PARTIAL) {
/* 220 */       setTiltAndScheduleTick($$0, (Level)$$1, $$2, Tilt.FULL, SoundEvents.BIG_DRIPLEAF_TILT_DOWN);
/* 221 */     } else if ($$4 == Tilt.FULL) {
/* 222 */       resetTilt($$0, (Level)$$1, $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 228 */     if ($$1.hasNeighborSignal($$2)) {
/* 229 */       resetTilt($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void playTiltSound(Level $$0, BlockPos $$1, SoundEvent $$2) {
/* 234 */     float $$3 = Mth.randomBetween($$0.random, 0.8F, 1.2F);
/* 235 */     $$0.playSound(null, $$1, $$2, SoundSource.BLOCKS, 1.0F, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean canEntityTilt(BlockPos $$0, Entity $$1) {
/* 240 */     return ($$1.onGround() && ($$1.position()).y > ($$0.getY() + 0.6875F));
/*     */   }
/*     */   
/*     */   private void setTiltAndScheduleTick(BlockState $$0, Level $$1, BlockPos $$2, Tilt $$3, @Nullable SoundEvent $$4) {
/* 244 */     setTilt($$0, $$1, $$2, $$3);
/* 245 */     if ($$4 != null) {
/* 246 */       playTiltSound($$1, $$2, $$4);
/*     */     }
/* 248 */     int $$5 = DELAY_UNTIL_NEXT_TILT_STATE.getInt($$3);
/* 249 */     if ($$5 != -1) {
/* 250 */       $$1.scheduleTick($$2, this, $$5);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void resetTilt(BlockState $$0, Level $$1, BlockPos $$2) {
/* 255 */     setTilt($$0, $$1, $$2, Tilt.NONE);
/* 256 */     if ($$0.getValue((Property)TILT) != Tilt.NONE) {
/* 257 */       playTiltSound($$1, $$2, SoundEvents.BIG_DRIPLEAF_TILT_UP);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void setTilt(BlockState $$0, Level $$1, BlockPos $$2, Tilt $$3) {
/* 262 */     Tilt $$4 = (Tilt)$$0.getValue((Property)TILT);
/* 263 */     $$1.setBlock($$2, (BlockState)$$0.setValue((Property)TILT, (Comparable)$$3), 2);
/* 264 */     if ($$3.causesVibration() && $$3 != $$4) {
/* 265 */       $$1.gameEvent(null, GameEvent.BLOCK_CHANGE, $$2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 271 */     return LEAF_SHAPES.get($$0.getValue((Property)TILT));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 276 */     return this.shapesCache.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 281 */     BlockState $$1 = $$0.getLevel().getBlockState($$0.getClickedPos().below());
/* 282 */     FluidState $$2 = $$0.getLevel().getFluidState($$0.getClickedPos());
/* 283 */     boolean $$3 = ($$1.is(Blocks.BIG_DRIPLEAF) || $$1.is(Blocks.BIG_DRIPLEAF_STEM));
/*     */     
/* 285 */     return (BlockState)((BlockState)defaultBlockState()
/* 286 */       .setValue((Property)WATERLOGGED, Boolean.valueOf($$2.isSourceOfType((Fluid)Fluids.WATER))))
/* 287 */       .setValue((Property)FACING, $$3 ? $$1.getValue((Property)FACING) : (Comparable)$$0.getHorizontalDirection().getOpposite());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 292 */     $$0.add(new Property[] { (Property)WATERLOGGED, (Property)FACING, (Property)TILT });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BigDripleafBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */