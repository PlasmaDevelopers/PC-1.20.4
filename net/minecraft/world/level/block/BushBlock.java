/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*    */ 
/*    */ public abstract class BushBlock extends Block {
/*    */   protected BushBlock(BlockBehaviour.Properties $$0) {
/* 15 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract MapCodec<? extends BushBlock> codec();
/*    */   
/*    */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 22 */     return ($$0.is(BlockTags.DIRT) || $$0.is(Blocks.FARMLAND));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 27 */     if (!$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 28 */       return Blocks.AIR.defaultBlockState();
/*    */     }
/* 30 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 35 */     BlockPos $$3 = $$2.below();
/* 36 */     return mayPlaceOn($$1.getBlockState($$3), (BlockGetter)$$1, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean propagatesSkylightDown(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 41 */     return $$0.getFluidState().isEmpty();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 46 */     if ($$3 == PathComputationType.AIR && !this.hasCollision) {
/* 47 */       return true;
/*    */     }
/* 49 */     return super.isPathfindable($$0, $$1, $$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BushBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */