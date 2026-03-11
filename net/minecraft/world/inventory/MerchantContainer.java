/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.trading.Merchant;
/*     */ import net.minecraft.world.item.trading.MerchantOffer;
/*     */ import net.minecraft.world.item.trading.MerchantOffers;
/*     */ 
/*     */ public class MerchantContainer implements Container {
/*     */   private final Merchant merchant;
/*  16 */   private final NonNullList<ItemStack> itemStacks = NonNullList.withSize(3, ItemStack.EMPTY);
/*     */   @Nullable
/*     */   private MerchantOffer activeOffer;
/*     */   private int selectionHint;
/*     */   private int futureXp;
/*     */   
/*     */   public MerchantContainer(Merchant $$0) {
/*  23 */     this.merchant = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/*  28 */     return this.itemStacks.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  33 */     for (ItemStack $$0 : this.itemStacks) {
/*  34 */       if (!$$0.isEmpty()) {
/*  35 */         return false;
/*     */       }
/*     */     } 
/*  38 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int $$0) {
/*  43 */     return (ItemStack)this.itemStacks.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItem(int $$0, int $$1) {
/*  48 */     ItemStack $$2 = (ItemStack)this.itemStacks.get($$0);
/*  49 */     if ($$0 == 2 && !$$2.isEmpty()) {
/*  50 */       return ContainerHelper.removeItem((List)this.itemStacks, $$0, $$2.getCount());
/*     */     }
/*     */     
/*  53 */     ItemStack $$3 = ContainerHelper.removeItem((List)this.itemStacks, $$0, $$1);
/*  54 */     if (!$$3.isEmpty() && isPaymentSlot($$0)) {
/*  55 */       updateSellItem();
/*     */     }
/*  57 */     return $$3;
/*     */   }
/*     */   
/*     */   private boolean isPaymentSlot(int $$0) {
/*  61 */     return ($$0 == 0 || $$0 == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItemNoUpdate(int $$0) {
/*  66 */     return ContainerHelper.takeItem((List)this.itemStacks, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int $$0, ItemStack $$1) {
/*  71 */     this.itemStacks.set($$0, $$1);
/*  72 */     if (!$$1.isEmpty() && $$1.getCount() > getMaxStackSize()) {
/*  73 */       $$1.setCount(getMaxStackSize());
/*     */     }
/*  75 */     if (isPaymentSlot($$0)) {
/*  76 */       updateSellItem();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/*  82 */     return (this.merchant.getTradingPlayer() == $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged() {
/*  87 */     updateSellItem();
/*     */   }
/*     */   public void updateSellItem() {
/*     */     ItemStack $$2, $$3;
/*  91 */     this.activeOffer = null;
/*     */ 
/*     */ 
/*     */     
/*  95 */     if (((ItemStack)this.itemStacks.get(0)).isEmpty()) {
/*  96 */       ItemStack $$0 = (ItemStack)this.itemStacks.get(1);
/*  97 */       ItemStack $$1 = ItemStack.EMPTY;
/*     */     } else {
/*  99 */       $$2 = (ItemStack)this.itemStacks.get(0);
/* 100 */       $$3 = (ItemStack)this.itemStacks.get(1);
/*     */     } 
/*     */     
/* 103 */     if ($$2.isEmpty()) {
/* 104 */       setItem(2, ItemStack.EMPTY);
/* 105 */       this.futureXp = 0;
/*     */       
/*     */       return;
/*     */     } 
/* 109 */     MerchantOffers $$4 = this.merchant.getOffers();
/* 110 */     if (!$$4.isEmpty()) {
/* 111 */       MerchantOffer $$5 = $$4.getRecipeFor($$2, $$3, this.selectionHint);
/* 112 */       if ($$5 == null || $$5.isOutOfStock()) {
/*     */         
/* 114 */         this.activeOffer = $$5;
/* 115 */         $$5 = $$4.getRecipeFor($$3, $$2, this.selectionHint);
/*     */       } 
/*     */       
/* 118 */       if ($$5 != null && !$$5.isOutOfStock()) {
/* 119 */         this.activeOffer = $$5;
/* 120 */         setItem(2, $$5.assemble());
/* 121 */         this.futureXp = $$5.getXp();
/*     */       } else {
/* 123 */         setItem(2, ItemStack.EMPTY);
/* 124 */         this.futureXp = 0;
/*     */       } 
/*     */     } 
/* 127 */     this.merchant.notifyTradeUpdated(getItem(2));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MerchantOffer getActiveOffer() {
/* 132 */     return this.activeOffer;
/*     */   }
/*     */   
/*     */   public void setSelectionHint(int $$0) {
/* 136 */     this.selectionHint = $$0;
/* 137 */     updateSellItem();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearContent() {
/* 142 */     this.itemStacks.clear();
/*     */   }
/*     */   
/*     */   public int getFutureXp() {
/* 146 */     return this.futureXp;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\MerchantContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */