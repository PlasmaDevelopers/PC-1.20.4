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
/*    */ 
/*    */ 
/*    */ enum null
/*    */ {
/*    */   private final int CENTER_SUPPORT_WIDTH = 1;
/*    */   private final VoxelShape CENTER_SUPPORT_SHAPE;
/*    */   
/*    */   null() {
/* 19 */     this.CENTER_SUPPORT_WIDTH = 1;
/* 20 */     this.CENTER_SUPPORT_SHAPE = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 10.0D, 9.0D);
/*    */   }
/*    */   
/*    */   public boolean isSupporting(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 24 */     return !Shapes.joinIsNotEmpty($$0.getBlockSupportShape($$1, $$2).getFaceShape($$3), this.CENTER_SUPPORT_SHAPE, BooleanOp.ONLY_SECOND);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SupportType$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */