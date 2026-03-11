/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class CactusBlock extends Block {
/*  24 */   public static final MapCodec<CactusBlock> CODEC = simpleCodec(CactusBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<CactusBlock> codec() {
/*  28 */     return CODEC;
/*     */   }
/*     */   
/*  31 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
/*     */   
/*     */   public static final int MAX_AGE = 15;
/*     */   protected static final int AABB_OFFSET = 1;
/*  35 */   protected static final VoxelShape COLLISION_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
/*  36 */   protected static final VoxelShape OUTLINE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
/*     */   
/*     */   protected CactusBlock(BlockBehaviour.Properties $$0) {
/*  39 */     super($$0);
/*  40 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)AGE, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  45 */     if (!$$0.canSurvive((LevelReader)$$1, $$2)) {
/*  46 */       $$1.destroyBlock($$2, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  52 */     BlockPos $$4 = $$2.above();
/*  53 */     if (!$$1.isEmptyBlock($$4)) {
/*     */       return;
/*     */     }
/*     */     
/*  57 */     int $$5 = 1;
/*  58 */     while ($$1.getBlockState($$2.below($$5)).is(this)) {
/*  59 */       $$5++;
/*     */     }
/*     */ 
/*     */     
/*  63 */     if ($$5 >= 3) {
/*     */       return;
/*     */     }
/*     */     
/*  67 */     int $$6 = ((Integer)$$0.getValue((Property)AGE)).intValue();
/*  68 */     if ($$6 == 15) {
/*  69 */       $$1.setBlockAndUpdate($$4, defaultBlockState());
/*  70 */       BlockState $$7 = (BlockState)$$0.setValue((Property)AGE, Integer.valueOf(0));
/*  71 */       $$1.setBlock($$2, $$7, 4);
/*  72 */       $$1.neighborChanged($$7, $$4, this, $$2, false);
/*     */     } else {
/*  74 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)AGE, Integer.valueOf($$6 + 1)), 4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  80 */     return COLLISION_SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  85 */     return OUTLINE_SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  90 */     if (!$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  91 */       $$3.scheduleTick($$4, this, 1);
/*     */     }
/*     */     
/*  94 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  99 */     for (Direction $$3 : Direction.Plane.HORIZONTAL) {
/* 100 */       BlockState $$4 = $$1.getBlockState($$2.relative($$3));
/*     */       
/* 102 */       if ($$4.isSolid() || $$1.getFluidState($$2.relative($$3)).is(FluidTags.LAVA)) {
/* 103 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 107 */     BlockState $$5 = $$1.getBlockState($$2.below());
/* 108 */     return (($$5.is(Blocks.CACTUS) || $$5.is(BlockTags.SAND)) && !$$1.getBlockState($$2.above()).liquid());
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 113 */     $$3.hurt($$1.damageSources().cactus(), 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 118 */     $$0.add(new Property[] { (Property)AGE });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 123 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CactusBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */