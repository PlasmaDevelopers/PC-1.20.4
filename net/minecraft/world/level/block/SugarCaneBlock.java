/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class SugarCaneBlock extends Block {
/*  22 */   public static final MapCodec<SugarCaneBlock> CODEC = simpleCodec(SugarCaneBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<SugarCaneBlock> codec() {
/*  26 */     return CODEC;
/*     */   }
/*     */   
/*  29 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
/*     */   
/*     */   protected static final float AABB_OFFSET = 6.0F;
/*  32 */   protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
/*     */   
/*     */   protected SugarCaneBlock(BlockBehaviour.Properties $$0) {
/*  35 */     super($$0);
/*  36 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)AGE, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  41 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  46 */     if (!$$0.canSurvive((LevelReader)$$1, $$2)) {
/*  47 */       $$1.destroyBlock($$2, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  53 */     if ($$1.isEmptyBlock($$2.above())) {
/*  54 */       int $$4 = 1;
/*  55 */       while ($$1.getBlockState($$2.below($$4)).is(this)) {
/*  56 */         $$4++;
/*     */       }
/*  58 */       if ($$4 < 3) {
/*  59 */         int $$5 = ((Integer)$$0.getValue((Property)AGE)).intValue();
/*  60 */         if ($$5 == 15) {
/*  61 */           $$1.setBlockAndUpdate($$2.above(), defaultBlockState());
/*  62 */           $$1.setBlock($$2, (BlockState)$$0.setValue((Property)AGE, Integer.valueOf(0)), 4);
/*     */         } else {
/*  64 */           $$1.setBlock($$2, (BlockState)$$0.setValue((Property)AGE, Integer.valueOf($$5 + 1)), 4);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  72 */     if (!$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  73 */       $$3.scheduleTick($$4, this, 1);
/*     */     }
/*     */     
/*  76 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  81 */     BlockState $$3 = $$1.getBlockState($$2.below());
/*  82 */     if ($$3.is(this)) {
/*  83 */       return true;
/*     */     }
/*     */     
/*  86 */     if ($$3.is(BlockTags.DIRT) || $$3.is(BlockTags.SAND)) {
/*  87 */       BlockPos $$4 = $$2.below();
/*  88 */       for (Direction $$5 : Direction.Plane.HORIZONTAL) {
/*  89 */         BlockState $$6 = $$1.getBlockState($$4.relative($$5));
/*  90 */         FluidState $$7 = $$1.getFluidState($$4.relative($$5));
/*  91 */         if ($$7.is(FluidTags.WATER) || $$6.is(Blocks.FROSTED_ICE)) {
/*  92 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 102 */     $$0.add(new Property[] { (Property)AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SugarCaneBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */