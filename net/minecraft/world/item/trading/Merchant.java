/*    */ package net.minecraft.world.item.trading;
/*    */ 
/*    */ import java.util.OptionalInt;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.world.MenuProvider;
/*    */ import net.minecraft.world.SimpleMenuProvider;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.inventory.MerchantMenu;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Merchant
/*    */ {
/*    */   void setTradingPlayer(@Nullable Player paramPlayer);
/*    */   
/*    */   @Nullable
/*    */   Player getTradingPlayer();
/*    */   
/*    */   MerchantOffers getOffers();
/*    */   
/*    */   void overrideOffers(MerchantOffers paramMerchantOffers);
/*    */   
/*    */   void notifyTrade(MerchantOffer paramMerchantOffer);
/*    */   
/*    */   default boolean canRestock() {
/* 36 */     return false;
/*    */   } void notifyTradeUpdated(ItemStack paramItemStack); int getVillagerXp(); void overrideXp(int paramInt); boolean showProgressBar();
/*    */   SoundEvent getNotifyTradeSound();
/*    */   default void openTradingScreen(Player $$0, Component $$1, int $$2) {
/* 40 */     OptionalInt $$3 = $$0.openMenu((MenuProvider)new SimpleMenuProvider(($$0, $$1, $$2) -> new MerchantMenu($$0, $$1, this), $$1));
/*    */     
/* 42 */     if ($$3.isPresent()) {
/* 43 */       MerchantOffers $$4 = getOffers();
/* 44 */       if (!$$4.isEmpty())
/* 45 */         $$0.sendMerchantOffers($$3.getAsInt(), $$4, $$2, getVillagerXp(), showProgressBar(), canRestock()); 
/*    */     } 
/*    */   }
/*    */   
/*    */   boolean isClientSide();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\trading\Merchant.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */