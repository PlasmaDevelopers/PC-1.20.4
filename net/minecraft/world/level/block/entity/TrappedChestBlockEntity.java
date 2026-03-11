/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class TrappedChestBlockEntity extends ChestBlockEntity {
/*    */   public TrappedChestBlockEntity(BlockPos $$0, BlockState $$1) {
/* 10 */     super(BlockEntityType.TRAPPED_CHEST, $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void signalOpenCount(Level $$0, BlockPos $$1, BlockState $$2, int $$3, int $$4) {
/* 15 */     super.signalOpenCount($$0, $$1, $$2, $$3, $$4);
/* 16 */     if ($$3 != $$4) {
/* 17 */       Block $$5 = $$2.getBlock();
/* 18 */       $$0.updateNeighborsAt($$1, $$5);
/* 19 */       $$0.updateNeighborsAt($$1.below(), $$5);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\TrappedChestBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */