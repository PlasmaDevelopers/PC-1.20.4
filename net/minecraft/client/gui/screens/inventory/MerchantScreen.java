/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.npc.VillagerData;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.inventory.MerchantMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.trading.MerchantOffer;
/*     */ import net.minecraft.world.item.trading.MerchantOffers;
/*     */ 
/*     */ public class MerchantScreen extends AbstractContainerScreen<MerchantMenu> {
/*  19 */   private static final ResourceLocation OUT_OF_STOCK_SPRITE = new ResourceLocation("container/villager/out_of_stock");
/*  20 */   private static final ResourceLocation EXPERIENCE_BAR_BACKGROUND_SPRITE = new ResourceLocation("container/villager/experience_bar_background");
/*  21 */   private static final ResourceLocation EXPERIENCE_BAR_CURRENT_SPRITE = new ResourceLocation("container/villager/experience_bar_current");
/*  22 */   private static final ResourceLocation EXPERIENCE_BAR_RESULT_SPRITE = new ResourceLocation("container/villager/experience_bar_result");
/*  23 */   private static final ResourceLocation SCROLLER_SPRITE = new ResourceLocation("container/villager/scroller");
/*  24 */   private static final ResourceLocation SCROLLER_DISABLED_SPRITE = new ResourceLocation("container/villager/scroller_disabled");
/*  25 */   private static final ResourceLocation TRADE_ARROW_OUT_OF_STOCK_SPRITE = new ResourceLocation("container/villager/trade_arrow_out_of_stock");
/*  26 */   private static final ResourceLocation TRADE_ARROW_SPRITE = new ResourceLocation("container/villager/trade_arrow");
/*  27 */   private static final ResourceLocation DISCOUNT_STRIKETHRUOGH_SPRITE = new ResourceLocation("container/villager/discount_strikethrough");
/*  28 */   private static final ResourceLocation VILLAGER_LOCATION = new ResourceLocation("textures/gui/container/villager.png");
/*     */   
/*     */   private static final int TEXTURE_WIDTH = 512;
/*     */   
/*     */   private static final int TEXTURE_HEIGHT = 256;
/*     */   
/*     */   private static final int MERCHANT_MENU_PART_X = 99;
/*     */   
/*     */   private static final int PROGRESS_BAR_X = 136;
/*     */   
/*     */   private static final int PROGRESS_BAR_Y = 16;
/*     */   private static final int SELL_ITEM_1_X = 5;
/*     */   private static final int SELL_ITEM_2_X = 35;
/*     */   private static final int BUY_ITEM_X = 68;
/*     */   private static final int LABEL_Y = 6;
/*     */   private static final int NUMBER_OF_OFFER_BUTTONS = 7;
/*     */   private static final int TRADE_BUTTON_X = 5;
/*     */   private static final int TRADE_BUTTON_HEIGHT = 20;
/*     */   private static final int TRADE_BUTTON_WIDTH = 88;
/*     */   private static final int SCROLLER_HEIGHT = 27;
/*     */   private static final int SCROLLER_WIDTH = 6;
/*     */   private static final int SCROLL_BAR_HEIGHT = 139;
/*     */   private static final int SCROLL_BAR_TOP_POS_Y = 18;
/*     */   private static final int SCROLL_BAR_START_X = 94;
/*  52 */   private static final Component TRADES_LABEL = (Component)Component.translatable("merchant.trades");
/*  53 */   private static final Component DEPRECATED_TOOLTIP = (Component)Component.translatable("merchant.deprecated");
/*     */   
/*     */   private int shopItem;
/*  56 */   private final TradeOfferButton[] tradeOfferButtons = new TradeOfferButton[7];
/*     */   int scrollOff;
/*     */   private boolean isDragging;
/*     */   
/*     */   public MerchantScreen(MerchantMenu $$0, Inventory $$1, Component $$2) {
/*  61 */     super($$0, $$1, $$2);
/*  62 */     this.imageWidth = 276;
/*  63 */     this.inventoryLabelX = 107;
/*     */   }
/*     */   
/*     */   private void postButtonClick() {
/*  67 */     this.menu.setSelectionHint(this.shopItem);
/*  68 */     this.menu.tryMoveItems(this.shopItem);
/*  69 */     this.minecraft.getConnection().send((Packet)new ServerboundSelectTradePacket(this.shopItem));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  74 */     super.init();
/*     */     
/*  76 */     int $$0 = (this.width - this.imageWidth) / 2;
/*  77 */     int $$1 = (this.height - this.imageHeight) / 2;
/*     */     
/*  79 */     int $$2 = $$1 + 16 + 2;
/*  80 */     for (int $$3 = 0; $$3 < 7; $$3++) {
/*  81 */       this.tradeOfferButtons[$$3] = (TradeOfferButton)addRenderableWidget((GuiEventListener)new TradeOfferButton($$0 + 5, $$2, $$3, $$0 -> {
/*     */               if ($$0 instanceof TradeOfferButton) {
/*     */                 this.shopItem = ((TradeOfferButton)$$0).getIndex() + this.scrollOff;
/*     */                 
/*     */                 postButtonClick();
/*     */               } 
/*     */             }));
/*  88 */       $$2 += 20;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderLabels(GuiGraphics $$0, int $$1, int $$2) {
/*  94 */     int $$3 = this.menu.getTraderLevel();
/*  95 */     if ($$3 > 0 && $$3 <= 5 && this.menu.showProgressBar()) {
/*  96 */       MutableComponent mutableComponent = Component.translatable("merchant.title", new Object[] { this.title, Component.translatable("merchant.level." + $$3) });
/*  97 */       int $$5 = this.font.width((FormattedText)mutableComponent);
/*  98 */       int $$6 = 49 + this.imageWidth / 2 - $$5 / 2;
/*  99 */       $$0.drawString(this.font, (Component)mutableComponent, $$6, 6, 4210752, false);
/*     */     } else {
/* 101 */       $$0.drawString(this.font, this.title, 49 + this.imageWidth / 2 - this.font.width((FormattedText)this.title) / 2, 6, 4210752, false);
/*     */     } 
/* 103 */     $$0.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
/*     */     
/* 105 */     int $$7 = this.font.width((FormattedText)TRADES_LABEL);
/* 106 */     $$0.drawString(this.font, TRADES_LABEL, 5 - $$7 / 2 + 48, 6, 4210752, false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/* 111 */     int $$4 = (this.width - this.imageWidth) / 2;
/* 112 */     int $$5 = (this.height - this.imageHeight) / 2;
/*     */     
/* 114 */     $$0.blit(VILLAGER_LOCATION, $$4, $$5, 0, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 512, 256);
/*     */     
/* 116 */     MerchantOffers $$6 = this.menu.getOffers();
/* 117 */     if (!$$6.isEmpty()) {
/* 118 */       int $$7 = this.shopItem;
/* 119 */       if ($$7 < 0 || $$7 >= $$6.size()) {
/*     */         return;
/*     */       }
/*     */       
/* 123 */       MerchantOffer $$8 = (MerchantOffer)$$6.get($$7);
/* 124 */       if ($$8.isOutOfStock()) {
/* 125 */         $$0.blitSprite(OUT_OF_STOCK_SPRITE, this.leftPos + 83 + 99, this.topPos + 35, 0, 28, 21);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderProgressBar(GuiGraphics $$0, int $$1, int $$2, MerchantOffer $$3) {
/* 131 */     int $$4 = this.menu.getTraderLevel();
/* 132 */     int $$5 = this.menu.getTraderXp();
/*     */     
/* 134 */     if ($$4 >= 5) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 139 */     $$0.blitSprite(EXPERIENCE_BAR_BACKGROUND_SPRITE, $$1 + 136, $$2 + 16, 0, 102, 5);
/*     */     
/* 141 */     int $$6 = VillagerData.getMinXpPerLevel($$4);
/* 142 */     if ($$5 < $$6 || !VillagerData.canLevelUp($$4)) {
/*     */       return;
/*     */     }
/*     */     
/* 146 */     int $$7 = 102;
/* 147 */     float $$8 = 102.0F / (VillagerData.getMaxXpPerLevel($$4) - $$6);
/* 148 */     int $$9 = Math.min(Mth.floor($$8 * ($$5 - $$6)), 102);
/*     */ 
/*     */     
/* 151 */     $$0.blitSprite(EXPERIENCE_BAR_CURRENT_SPRITE, 102, 5, 0, 0, $$1 + 136, $$2 + 16, 0, $$9, 5);
/* 152 */     int $$10 = this.menu.getFutureTraderXp();
/* 153 */     if ($$10 > 0) {
/* 154 */       int $$11 = Math.min(Mth.floor($$10 * $$8), 102 - $$9);
/* 155 */       $$0.blitSprite(EXPERIENCE_BAR_RESULT_SPRITE, 102, 5, $$9, 0, $$1 + 136 + $$9, $$2 + 16, 0, $$11, 5);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderScroller(GuiGraphics $$0, int $$1, int $$2, MerchantOffers $$3) {
/* 160 */     int $$4 = $$3.size() + 1 - 7;
/* 161 */     if ($$4 > 1) {
/* 162 */       int $$5 = 139 - 27 + ($$4 - 1) * 139 / $$4;
/* 163 */       int $$6 = 1 + $$5 / $$4 + 139 / $$4;
/* 164 */       int $$7 = 113;
/* 165 */       int $$8 = Math.min(113, this.scrollOff * $$6);
/* 166 */       if (this.scrollOff == $$4 - 1)
/*     */       {
/* 168 */         $$8 = 113;
/*     */       }
/* 170 */       $$0.blitSprite(SCROLLER_SPRITE, $$1 + 94, $$2 + 18 + $$8, 0, 6, 27);
/*     */     } else {
/*     */       
/* 173 */       $$0.blitSprite(SCROLLER_DISABLED_SPRITE, $$1 + 94, $$2 + 18, 0, 6, 27);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 179 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 181 */     MerchantOffers $$4 = this.menu.getOffers();
/* 182 */     if (!$$4.isEmpty()) {
/* 183 */       int $$5 = (this.width - this.imageWidth) / 2;
/* 184 */       int $$6 = (this.height - this.imageHeight) / 2;
/*     */       
/* 186 */       int $$7 = $$6 + 16 + 1;
/* 187 */       int $$8 = $$5 + 5 + 5;
/*     */       
/* 189 */       renderScroller($$0, $$5, $$6, $$4);
/*     */       
/* 191 */       int $$9 = 0;
/* 192 */       for (MerchantOffer $$10 : $$4) {
/* 193 */         if (canScroll($$4.size()) && ($$9 < this.scrollOff || $$9 >= 7 + this.scrollOff)) {
/* 194 */           $$9++;
/*     */           
/*     */           continue;
/*     */         } 
/* 198 */         ItemStack $$11 = $$10.getBaseCostA();
/* 199 */         ItemStack $$12 = $$10.getCostA();
/* 200 */         ItemStack $$13 = $$10.getCostB();
/* 201 */         ItemStack $$14 = $$10.getResult();
/*     */         
/* 203 */         $$0.pose().pushPose();
/* 204 */         $$0.pose().translate(0.0F, 0.0F, 100.0F);
/*     */         
/* 206 */         int $$15 = $$7 + 2;
/*     */         
/* 208 */         renderAndDecorateCostA($$0, $$12, $$11, $$8, $$15);
/*     */         
/* 210 */         if (!$$13.isEmpty()) {
/* 211 */           $$0.renderFakeItem($$13, $$5 + 5 + 35, $$15);
/* 212 */           $$0.renderItemDecorations(this.font, $$13, $$5 + 5 + 35, $$15);
/*     */         } 
/*     */         
/* 215 */         renderButtonArrows($$0, $$10, $$5, $$15);
/*     */         
/* 217 */         $$0.renderFakeItem($$14, $$5 + 5 + 68, $$15);
/* 218 */         $$0.renderItemDecorations(this.font, $$14, $$5 + 5 + 68, $$15);
/* 219 */         $$0.pose().popPose();
/*     */         
/* 221 */         $$7 += 20;
/* 222 */         $$9++;
/*     */       } 
/*     */       
/* 225 */       int $$16 = this.shopItem;
/* 226 */       MerchantOffer $$17 = (MerchantOffer)$$4.get($$16);
/*     */       
/* 228 */       if (this.menu.showProgressBar()) {
/* 229 */         renderProgressBar($$0, $$5, $$6, $$17);
/*     */       }
/*     */       
/* 232 */       if ($$17.isOutOfStock() && isHovering(186, 35, 22, 21, $$1, $$2) && this.menu.canRestock()) {
/* 233 */         $$0.renderTooltip(this.font, DEPRECATED_TOOLTIP, $$1, $$2);
/*     */       }
/*     */       
/* 236 */       for (TradeOfferButton $$18 : this.tradeOfferButtons) {
/* 237 */         if ($$18.isHoveredOrFocused()) {
/* 238 */           $$18.renderToolTip($$0, $$1, $$2);
/*     */         }
/* 240 */         $$18.visible = ($$18.index < this.menu.getOffers().size());
/*     */       } 
/*     */       
/* 243 */       RenderSystem.enableDepthTest();
/*     */     } 
/*     */     
/* 246 */     renderTooltip($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private void renderButtonArrows(GuiGraphics $$0, MerchantOffer $$1, int $$2, int $$3) {
/* 250 */     RenderSystem.enableBlend();
/* 251 */     if ($$1.isOutOfStock()) {
/*     */       
/* 253 */       $$0.blitSprite(TRADE_ARROW_OUT_OF_STOCK_SPRITE, $$2 + 5 + 35 + 20, $$3 + 3, 0, 10, 9);
/*     */     } else {
/*     */       
/* 256 */       $$0.blitSprite(TRADE_ARROW_SPRITE, $$2 + 5 + 35 + 20, $$3 + 3, 0, 10, 9);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderAndDecorateCostA(GuiGraphics $$0, ItemStack $$1, ItemStack $$2, int $$3, int $$4) {
/* 261 */     $$0.renderFakeItem($$1, $$3, $$4);
/* 262 */     if ($$2.getCount() == $$1.getCount()) {
/* 263 */       $$0.renderItemDecorations(this.font, $$1, $$3, $$4);
/*     */     } else {
/* 265 */       $$0.renderItemDecorations(this.font, $$2, $$3, $$4, ($$2.getCount() == 1) ? "1" : null);
/* 266 */       $$0.renderItemDecorations(this.font, $$1, $$3 + 14, $$4, ($$1.getCount() == 1) ? "1" : null);
/* 267 */       $$0.pose().pushPose();
/* 268 */       $$0.pose().translate(0.0F, 0.0F, 300.0F);
/*     */       
/* 270 */       $$0.blitSprite(DISCOUNT_STRIKETHRUOGH_SPRITE, $$3 + 7, $$4 + 12, 0, 9, 2);
/* 271 */       $$0.pose().popPose();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean canScroll(int $$0) {
/* 276 */     return ($$0 > 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/* 281 */     int $$4 = this.menu.getOffers().size();
/* 282 */     if (canScroll($$4)) {
/* 283 */       int $$5 = $$4 - 7;
/* 284 */       this.scrollOff = Mth.clamp((int)(this.scrollOff - $$3), 0, $$5);
/*     */     } 
/* 286 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/* 291 */     int $$5 = this.menu.getOffers().size();
/*     */     
/* 293 */     if (this.isDragging) {
/* 294 */       int $$6 = this.topPos + 18;
/* 295 */       int $$7 = $$6 + 139;
/* 296 */       int $$8 = $$5 - 7;
/*     */       
/* 298 */       float $$9 = ((float)$$1 - $$6 - 13.5F) / (($$7 - $$6) - 27.0F);
/* 299 */       $$9 = $$9 * $$8 + 0.5F;
/*     */       
/* 301 */       this.scrollOff = Mth.clamp((int)$$9, 0, $$8);
/*     */       
/* 303 */       return true;
/*     */     } 
/* 305 */     return super.mouseDragged($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 310 */     this.isDragging = false;
/* 311 */     int $$3 = (this.width - this.imageWidth) / 2;
/* 312 */     int $$4 = (this.height - this.imageHeight) / 2;
/*     */     
/* 314 */     if (canScroll(this.menu.getOffers().size()) && $$0 > ($$3 + 94) && $$0 < ($$3 + 94 + 6) && $$1 > ($$4 + 18) && $$1 <= ($$4 + 18 + 139 + 1))
/*     */     {
/*     */ 
/*     */       
/* 318 */       this.isDragging = true;
/*     */     }
/* 320 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private class TradeOfferButton extends Button {
/*     */     final int index;
/*     */     
/*     */     public TradeOfferButton(int $$0, int $$1, int $$2, Button.OnPress $$3) {
/* 327 */       super($$0, $$1, 88, 20, CommonComponents.EMPTY, $$3, DEFAULT_NARRATION);
/* 328 */       this.index = $$2;
/* 329 */       this.visible = false;
/*     */     }
/*     */     
/*     */     public int getIndex() {
/* 333 */       return this.index;
/*     */     }
/*     */     
/*     */     public void renderToolTip(GuiGraphics $$0, int $$1, int $$2) {
/* 337 */       if (this.isHovered && MerchantScreen.this.menu.getOffers().size() > this.index + MerchantScreen.this.scrollOff)
/* 338 */         if ($$1 < getX() + 20) {
/* 339 */           ItemStack $$3 = ((MerchantOffer)MerchantScreen.this.menu.getOffers().get(this.index + MerchantScreen.this.scrollOff)).getCostA();
/* 340 */           $$0.renderTooltip(MerchantScreen.this.font, $$3, $$1, $$2);
/* 341 */         } else if ($$1 < getX() + 50 && $$1 > getX() + 30) {
/* 342 */           ItemStack $$4 = ((MerchantOffer)MerchantScreen.this.menu.getOffers().get(this.index + MerchantScreen.this.scrollOff)).getCostB();
/* 343 */           if (!$$4.isEmpty()) {
/* 344 */             $$0.renderTooltip(MerchantScreen.this.font, $$4, $$1, $$2);
/*     */           }
/* 346 */         } else if ($$1 > getX() + 65) {
/* 347 */           ItemStack $$5 = ((MerchantOffer)MerchantScreen.this.menu.getOffers().get(this.index + MerchantScreen.this.scrollOff)).getResult();
/* 348 */           $$0.renderTooltip(MerchantScreen.this.font, $$5, $$1, $$2);
/*     */         }  
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\MerchantScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */