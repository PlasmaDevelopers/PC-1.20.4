/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends ContainerOpenersCounter
/*    */ {
/*    */   protected void onOpen(Level $$0, BlockPos $$1, BlockState $$2) {
/* 18 */     $$0.playSound(null, $$1.getX() + 0.5D, $$1.getY() + 0.5D, $$1.getZ() + 0.5D, SoundEvents.ENDER_CHEST_OPEN, SoundSource.BLOCKS, 0.5F, $$0.random.nextFloat() * 0.1F + 0.9F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onClose(Level $$0, BlockPos $$1, BlockState $$2) {
/* 23 */     $$0.playSound(null, $$1.getX() + 0.5D, $$1.getY() + 0.5D, $$1.getZ() + 0.5D, SoundEvents.ENDER_CHEST_CLOSE, SoundSource.BLOCKS, 0.5F, $$0.random.nextFloat() * 0.1F + 0.9F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void openerCountChanged(Level $$0, BlockPos $$1, BlockState $$2, int $$3, int $$4) {
/* 28 */     $$0.blockEvent(EnderChestBlockEntity.this.worldPosition, Blocks.ENDER_CHEST, 1, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isOwnContainer(Player $$0) {
/* 33 */     return $$0.getEnderChestInventory().isActiveChest(EnderChestBlockEntity.this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\EnderChestBlockEntity$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */