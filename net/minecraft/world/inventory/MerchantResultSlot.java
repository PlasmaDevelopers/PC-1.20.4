/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.trading.Merchant;
/*    */ import net.minecraft.world.item.trading.MerchantOffer;
/*    */ 
/*    */ public class MerchantResultSlot extends Slot {
/*    */   private final MerchantContainer slots;
/*    */   private final Player player;
/*    */   private int removeCount;
/*    */   private final Merchant merchant;
/*    */   
/*    */   public MerchantResultSlot(Player $$0, Merchant $$1, MerchantContainer $$2, int $$3, int $$4, int $$5) {
/* 16 */     super($$2, $$3, $$4, $$5);
/* 17 */     this.player = $$0;
/* 18 */     this.merchant = $$1;
/* 19 */     this.slots = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack $$0) {
/* 24 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack remove(int $$0) {
/* 29 */     if (hasItem()) {
/* 30 */       this.removeCount += Math.min($$0, getItem().getCount());
/*    */     }
/* 32 */     return super.remove($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onQuickCraft(ItemStack $$0, int $$1) {
/* 37 */     this.removeCount += $$1;
/* 38 */     checkTakeAchievements($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void checkTakeAchievements(ItemStack $$0) {
/* 43 */     $$0.onCraftedBy(this.player.level(), this.player, this.removeCount);
/* 44 */     this.removeCount = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTake(Player $$0, ItemStack $$1) {
/* 49 */     checkTakeAchievements($$1);
/*    */     
/* 51 */     MerchantOffer $$2 = this.slots.getActiveOffer();
/*    */     
/* 53 */     if ($$2 != null) {
/* 54 */       ItemStack $$3 = this.slots.getItem(0);
/* 55 */       ItemStack $$4 = this.slots.getItem(1);
/*    */ 
/*    */       
/* 58 */       if ($$2.take($$3, $$4) || $$2.take($$4, $$3)) {
/* 59 */         this.merchant.notifyTrade($$2);
/* 60 */         $$0.awardStat(Stats.TRADED_WITH_VILLAGER);
/*    */         
/* 62 */         this.slots.setItem(0, $$3);
/* 63 */         this.slots.setItem(1, $$4);
/*    */       } 
/* 65 */       this.merchant.overrideXp(this.merchant.getVillagerXp() + $$2.getXp());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\MerchantResultSlot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */