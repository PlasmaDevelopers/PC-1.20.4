/*    */ package net.minecraft.world.entity.npc;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.trading.Merchant;
/*    */ import net.minecraft.world.item.trading.MerchantOffer;
/*    */ import net.minecraft.world.item.trading.MerchantOffers;
/*    */ 
/*    */ public class ClientSideMerchant
/*    */   implements Merchant {
/*    */   private final Player source;
/* 15 */   private MerchantOffers offers = new MerchantOffers();
/*    */   private int xp;
/*    */   
/*    */   public ClientSideMerchant(Player $$0) {
/* 19 */     this.source = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Player getTradingPlayer() {
/* 24 */     return this.source;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTradingPlayer(@Nullable Player $$0) {}
/*    */ 
/*    */   
/*    */   public MerchantOffers getOffers() {
/* 33 */     return this.offers;
/*    */   }
/*    */ 
/*    */   
/*    */   public void overrideOffers(MerchantOffers $$0) {
/* 38 */     this.offers = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void notifyTrade(MerchantOffer $$0) {
/* 43 */     $$0.increaseUses();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void notifyTradeUpdated(ItemStack $$0) {}
/*    */ 
/*    */   
/*    */   public boolean isClientSide() {
/* 52 */     return (this.source.level()).isClientSide;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getVillagerXp() {
/* 57 */     return this.xp;
/*    */   }
/*    */ 
/*    */   
/*    */   public void overrideXp(int $$0) {
/* 62 */     this.xp = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean showProgressBar() {
/* 67 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundEvent getNotifyTradeSound() {
/* 72 */     return SoundEvents.VILLAGER_YES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\ClientSideMerchant.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */