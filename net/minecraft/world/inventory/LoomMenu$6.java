/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   extends Slot
/*     */ {
/*     */   null(Container $$1, int $$2, int $$3, int $$4) {
/*  95 */     super($$1, $$2, $$3, $$4);
/*     */   }
/*     */   public boolean mayPlace(ItemStack $$0) {
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTake(Player $$0, ItemStack $$1) {
/* 103 */     LoomMenu.this.bannerSlot.remove(1);
/* 104 */     LoomMenu.this.dyeSlot.remove(1);
/* 105 */     if (!LoomMenu.this.bannerSlot.hasItem() || !LoomMenu.this.dyeSlot.hasItem()) {
/* 106 */       LoomMenu.this.selectedBannerPatternIndex.set(-1);
/*     */     }
/* 108 */     access.execute(($$0, $$1) -> {
/*     */           long $$2 = $$0.getGameTime();
/*     */           
/*     */           if (LoomMenu.this.lastSoundTime != $$2) {
/*     */             $$0.playSound(null, $$1, SoundEvents.UI_LOOM_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */             
/*     */             LoomMenu.this.lastSoundTime = $$2;
/*     */           } 
/*     */         });
/*     */     
/* 118 */     super.onTake($$0, $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\LoomMenu$6.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */