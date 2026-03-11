/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class EnderChestBlockEntity
/*    */   extends BlockEntity implements LidBlockEntity {
/* 14 */   private final ChestLidController chestLidController = new ChestLidController();
/* 15 */   private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter()
/*    */     {
/*    */       protected void onOpen(Level $$0, BlockPos $$1, BlockState $$2) {
/* 18 */         $$0.playSound(null, $$1.getX() + 0.5D, $$1.getY() + 0.5D, $$1.getZ() + 0.5D, SoundEvents.ENDER_CHEST_OPEN, SoundSource.BLOCKS, 0.5F, $$0.random.nextFloat() * 0.1F + 0.9F);
/*    */       }
/*    */ 
/*    */       
/*    */       protected void onClose(Level $$0, BlockPos $$1, BlockState $$2) {
/* 23 */         $$0.playSound(null, $$1.getX() + 0.5D, $$1.getY() + 0.5D, $$1.getZ() + 0.5D, SoundEvents.ENDER_CHEST_CLOSE, SoundSource.BLOCKS, 0.5F, $$0.random.nextFloat() * 0.1F + 0.9F);
/*    */       }
/*    */ 
/*    */       
/*    */       protected void openerCountChanged(Level $$0, BlockPos $$1, BlockState $$2, int $$3, int $$4) {
/* 28 */         $$0.blockEvent(EnderChestBlockEntity.this.worldPosition, Blocks.ENDER_CHEST, 1, $$4);
/*    */       }
/*    */ 
/*    */       
/*    */       protected boolean isOwnContainer(Player $$0) {
/* 33 */         return $$0.getEnderChestInventory().isActiveChest(EnderChestBlockEntity.this);
/*    */       }
/*    */     };
/*    */   
/*    */   public EnderChestBlockEntity(BlockPos $$0, BlockState $$1) {
/* 38 */     super(BlockEntityType.ENDER_CHEST, $$0, $$1);
/*    */   }
/*    */   
/*    */   public static void lidAnimateTick(Level $$0, BlockPos $$1, BlockState $$2, EnderChestBlockEntity $$3) {
/* 42 */     $$3.chestLidController.tickLid();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean triggerEvent(int $$0, int $$1) {
/* 47 */     if ($$0 == 1) {
/* 48 */       this.chestLidController.shouldBeOpen(($$1 > 0));
/* 49 */       return true;
/*    */     } 
/* 51 */     return super.triggerEvent($$0, $$1);
/*    */   }
/*    */   
/*    */   public void startOpen(Player $$0) {
/* 55 */     if (!this.remove && !$$0.isSpectator()) {
/* 56 */       this.openersCounter.incrementOpeners($$0, getLevel(), getBlockPos(), getBlockState());
/*    */     }
/*    */   }
/*    */   
/*    */   public void stopOpen(Player $$0) {
/* 61 */     if (!this.remove && !$$0.isSpectator()) {
/* 62 */       this.openersCounter.decrementOpeners($$0, getLevel(), getBlockPos(), getBlockState());
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean stillValid(Player $$0) {
/* 67 */     return Container.stillValidBlockEntity(this, $$0);
/*    */   }
/*    */   
/*    */   public void recheckOpen() {
/* 71 */     if (!this.remove) {
/* 72 */       this.openersCounter.recheckOpeners(getLevel(), getBlockPos(), getBlockState());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public float getOpenNess(float $$0) {
/* 78 */     return this.chestLidController.getOpenness($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\EnderChestBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */