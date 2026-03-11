/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.BooleanOp;
/*    */ import net.minecraft.world.phys.shapes.Shapes;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public enum SupportType {
/* 12 */   FULL
/*    */   {
/*    */     public boolean isSupporting(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 15 */       return Block.isFaceFull($$0.getBlockSupportShape($$1, $$2), $$3);
/*    */     }
/*    */   },
/* 18 */   CENTER {
/* 19 */     private final int CENTER_SUPPORT_WIDTH = 1;
/* 20 */     private final VoxelShape CENTER_SUPPORT_SHAPE = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 10.0D, 9.0D);
/*    */ 
/*    */     
/*    */     public boolean isSupporting(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 24 */       return !Shapes.joinIsNotEmpty($$0.getBlockSupportShape($$1, $$2).getFaceShape($$3), this.CENTER_SUPPORT_SHAPE, BooleanOp.ONLY_SECOND);
/*    */     }
/*    */   },
/* 27 */   RIGID {
/* 28 */     private final int RIGID_SUPPORT_WIDTH = 2;
/* 29 */     private final VoxelShape RIGID_SUPPORT_SHAPE = Shapes.join(
/* 30 */         Shapes.block(), 
/* 31 */         Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D), BooleanOp.ONLY_FIRST);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public boolean isSupporting(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 37 */       return !Shapes.joinIsNotEmpty($$0.getBlockSupportShape($$1, $$2).getFaceShape($$3), this.RIGID_SUPPORT_SHAPE, BooleanOp.ONLY_SECOND);
/*    */     }
/*    */   };
/*    */   
/*    */   public abstract boolean isSupporting(BlockState paramBlockState, BlockGetter paramBlockGetter, BlockPos paramBlockPos, Direction paramDirection);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SupportType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */