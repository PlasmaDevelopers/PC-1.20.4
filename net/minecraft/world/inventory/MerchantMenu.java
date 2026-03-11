/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.npc.ClientSideMerchant;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.trading.Merchant;
/*     */ import net.minecraft.world.item.trading.MerchantOffer;
/*     */ import net.minecraft.world.item.trading.MerchantOffers;
/*     */ 
/*     */ 
/*     */ public class MerchantMenu
/*     */   extends AbstractContainerMenu
/*     */ {
/*     */   protected static final int PAYMENT1_SLOT = 0;
/*     */   protected static final int PAYMENT2_SLOT = 1;
/*     */   protected static final int RESULT_SLOT = 2;
/*     */   private static final int INV_SLOT_START = 3;
/*     */   private static final int INV_SLOT_END = 30;
/*     */   private static final int USE_ROW_SLOT_START = 30;
/*     */   private static final int USE_ROW_SLOT_END = 39;
/*     */   private static final int SELLSLOT1_X = 136;
/*     */   private static final int SELLSLOT2_X = 162;
/*     */   private static final int BUYSLOT_X = 220;
/*     */   private static final int ROW_Y = 37;
/*     */   private final Merchant trader;
/*     */   private final MerchantContainer tradeContainer;
/*     */   private int merchantLevel;
/*     */   private boolean showProgressBar;
/*     */   private boolean canRestock;
/*     */   
/*     */   public MerchantMenu(int $$0, Inventory $$1) {
/*  37 */     this($$0, $$1, (Merchant)new ClientSideMerchant($$1.player));
/*     */   }
/*     */   
/*     */   public MerchantMenu(int $$0, Inventory $$1, Merchant $$2) {
/*  41 */     super(MenuType.MERCHANT, $$0);
/*  42 */     this.trader = $$2;
/*     */     
/*  44 */     this.tradeContainer = new MerchantContainer($$2);
/*  45 */     addSlot(new Slot(this.tradeContainer, 0, 136, 37));
/*  46 */     addSlot(new Slot(this.tradeContainer, 1, 162, 37));
/*  47 */     addSlot(new MerchantResultSlot($$1.player, $$2, this.tradeContainer, 2, 220, 37));
/*     */     
/*  49 */     for (int $$3 = 0; $$3 < 3; $$3++) {
/*  50 */       for (int $$4 = 0; $$4 < 9; $$4++) {
/*  51 */         addSlot(new Slot((Container)$$1, $$4 + $$3 * 9 + 9, 108 + $$4 * 18, 84 + $$3 * 18));
/*     */       }
/*     */     } 
/*  54 */     for (int $$5 = 0; $$5 < 9; $$5++) {
/*  55 */       addSlot(new Slot((Container)$$1, $$5, 108 + $$5 * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setShowProgressBar(boolean $$0) {
/*  60 */     this.showProgressBar = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void slotsChanged(Container $$0) {
/*  65 */     this.tradeContainer.updateSellItem();
/*  66 */     super.slotsChanged($$0);
/*     */   }
/*     */   
/*     */   public void setSelectionHint(int $$0) {
/*  70 */     this.tradeContainer.setSelectionHint($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/*  75 */     return (this.trader.getTradingPlayer() == $$0);
/*     */   }
/*     */   
/*     */   public int getTraderXp() {
/*  79 */     return this.trader.getVillagerXp();
/*     */   }
/*     */   
/*     */   public int getFutureTraderXp() {
/*  83 */     return this.tradeContainer.getFutureXp();
/*     */   }
/*     */   
/*     */   public void setXp(int $$0) {
/*  87 */     this.trader.overrideXp($$0);
/*     */   }
/*     */   
/*     */   public int getTraderLevel() {
/*  91 */     return this.merchantLevel;
/*     */   }
/*     */   
/*     */   public void setMerchantLevel(int $$0) {
/*  95 */     this.merchantLevel = $$0;
/*     */   }
/*     */   
/*     */   public void setCanRestock(boolean $$0) {
/*  99 */     this.canRestock = $$0;
/*     */   }
/*     */   
/*     */   public boolean canRestock() {
/* 103 */     return this.canRestock;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeItemForPickAll(ItemStack $$0, Slot $$1) {
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 113 */     ItemStack $$2 = ItemStack.EMPTY;
/* 114 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 115 */     if ($$3 != null && $$3.hasItem()) {
/* 116 */       ItemStack $$4 = $$3.getItem();
/* 117 */       $$2 = $$4.copy();
/*     */       
/* 119 */       if ($$1 == 2) {
/* 120 */         if (!moveItemStackTo($$4, 3, 39, true)) {
/* 121 */           return ItemStack.EMPTY;
/*     */         }
/* 123 */         $$3.onQuickCraft($$4, $$2);
/*     */         
/* 125 */         playTradeSound();
/* 126 */       } else if ($$1 == 0 || $$1 == 1) {
/* 127 */         if (!moveItemStackTo($$4, 3, 39, false)) {
/* 128 */           return ItemStack.EMPTY;
/*     */         }
/* 130 */       } else if ($$1 >= 3 && $$1 < 30) {
/* 131 */         if (!moveItemStackTo($$4, 30, 39, false)) {
/* 132 */           return ItemStack.EMPTY;
/*     */         }
/* 134 */       } else if ($$1 >= 30 && $$1 < 39 && 
/* 135 */         !moveItemStackTo($$4, 3, 30, false)) {
/* 136 */         return ItemStack.EMPTY;
/*     */       } 
/*     */       
/* 139 */       if ($$4.isEmpty()) {
/* 140 */         $$3.setByPlayer(ItemStack.EMPTY);
/*     */       } else {
/* 142 */         $$3.setChanged();
/*     */       } 
/* 144 */       if ($$4.getCount() == $$2.getCount()) {
/* 145 */         return ItemStack.EMPTY;
/*     */       }
/* 147 */       $$3.onTake($$0, $$4);
/*     */     } 
/*     */     
/* 150 */     return $$2;
/*     */   }
/*     */   
/*     */   private void playTradeSound() {
/* 154 */     if (!this.trader.isClientSide()) {
/* 155 */       Entity $$0 = (Entity)this.trader;
/* 156 */       $$0.level().playLocalSound($$0.getX(), $$0.getY(), $$0.getZ(), this.trader.getNotifyTradeSound(), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Player $$0) {
/* 162 */     super.removed($$0);
/* 163 */     this.trader.setTradingPlayer(null);
/*     */     
/* 165 */     if (this.trader.isClientSide()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 170 */     if (!$$0.isAlive() || ($$0 instanceof ServerPlayer && ((ServerPlayer)$$0).hasDisconnected())) {
/* 171 */       ItemStack $$1 = this.tradeContainer.removeItemNoUpdate(0);
/* 172 */       if (!$$1.isEmpty()) {
/* 173 */         $$0.drop($$1, false);
/*     */       }
/* 175 */       $$1 = this.tradeContainer.removeItemNoUpdate(1);
/* 176 */       if (!$$1.isEmpty()) {
/* 177 */         $$0.drop($$1, false);
/*     */       }
/*     */     }
/* 180 */     else if ($$0 instanceof ServerPlayer) {
/* 181 */       $$0.getInventory().placeItemBackInInventory(this.tradeContainer.removeItemNoUpdate(0));
/* 182 */       $$0.getInventory().placeItemBackInInventory(this.tradeContainer.removeItemNoUpdate(1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tryMoveItems(int $$0) {
/* 188 */     if ($$0 < 0 || getOffers().size() <= $$0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 193 */     ItemStack $$1 = this.tradeContainer.getItem(0);
/* 194 */     if (!$$1.isEmpty()) {
/* 195 */       if (!moveItemStackTo($$1, 3, 39, true)) {
/*     */         return;
/*     */       }
/*     */       
/* 199 */       this.tradeContainer.setItem(0, $$1);
/*     */     } 
/*     */     
/* 202 */     ItemStack $$2 = this.tradeContainer.getItem(1);
/* 203 */     if (!$$2.isEmpty()) {
/* 204 */       if (!moveItemStackTo($$2, 3, 39, true)) {
/*     */         return;
/*     */       }
/*     */       
/* 208 */       this.tradeContainer.setItem(1, $$2);
/*     */     } 
/*     */ 
/*     */     
/* 212 */     if (this.tradeContainer.getItem(0).isEmpty() && this.tradeContainer.getItem(1).isEmpty()) {
/* 213 */       ItemStack $$3 = ((MerchantOffer)getOffers().get($$0)).getCostA();
/* 214 */       moveFromInventoryToPaymentSlot(0, $$3);
/*     */       
/* 216 */       ItemStack $$4 = ((MerchantOffer)getOffers().get($$0)).getCostB();
/* 217 */       moveFromInventoryToPaymentSlot(1, $$4);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void moveFromInventoryToPaymentSlot(int $$0, ItemStack $$1) {
/* 222 */     if (!$$1.isEmpty()) {
/* 223 */       for (int $$2 = 3; $$2 < 39; $$2++) {
/* 224 */         ItemStack $$3 = ((Slot)this.slots.get($$2)).getItem();
/* 225 */         if (!$$3.isEmpty() && ItemStack.isSameItemSameTags($$1, $$3)) {
/* 226 */           ItemStack $$4 = this.tradeContainer.getItem($$0);
/* 227 */           int $$5 = $$4.isEmpty() ? 0 : $$4.getCount();
/* 228 */           int $$6 = Math.min($$1.getMaxStackSize() - $$5, $$3.getCount());
/*     */           
/* 230 */           ItemStack $$7 = $$3.copy();
/* 231 */           int $$8 = $$5 + $$6;
/*     */           
/* 233 */           $$3.shrink($$6);
/*     */           
/* 235 */           $$7.setCount($$8);
/* 236 */           this.tradeContainer.setItem($$0, $$7);
/*     */           
/* 238 */           if ($$8 >= $$1.getMaxStackSize()) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void setOffers(MerchantOffers $$0) {
/* 247 */     this.trader.overrideOffers($$0);
/*     */   }
/*     */   
/*     */   public MerchantOffers getOffers() {
/* 251 */     return this.trader.getOffers();
/*     */   }
/*     */   
/*     */   public boolean showProgressBar() {
/* 255 */     return this.showProgressBar;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\MerchantMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */