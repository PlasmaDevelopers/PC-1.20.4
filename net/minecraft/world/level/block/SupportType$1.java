/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ enum null
/*    */ {
/*    */   public boolean isSupporting(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 15 */     return Block.isFaceFull($$0.getBlockSupportShape($$1, $$2), $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SupportType$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */