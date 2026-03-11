/*    */ package net.minecraft.world.level.redstone;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class InstantNeighborUpdater
/*    */   implements NeighborUpdater {
/*    */   public InstantNeighborUpdater(Level $$0) {
/* 13 */     this.level = $$0;
/*    */   }
/*    */   private final Level level;
/*    */   
/*    */   public void shapeUpdate(Direction $$0, BlockState $$1, BlockPos $$2, BlockPos $$3, int $$4, int $$5) {
/* 18 */     NeighborUpdater.executeShapeUpdate((LevelAccessor)this.level, $$0, $$1, $$2, $$3, $$4, $$5 - 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void neighborChanged(BlockPos $$0, Block $$1, BlockPos $$2) {
/* 23 */     BlockState $$3 = this.level.getBlockState($$0);
/* 24 */     neighborChanged($$3, $$0, $$1, $$2, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void neighborChanged(BlockState $$0, BlockPos $$1, Block $$2, BlockPos $$3, boolean $$4) {
/* 29 */     NeighborUpdater.executeUpdate(this.level, $$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\redstone\InstantNeighborUpdater.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */