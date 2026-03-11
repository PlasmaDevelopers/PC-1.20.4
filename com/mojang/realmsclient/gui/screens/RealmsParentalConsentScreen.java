/*    */ package com.mojang.realmsclient.gui.screens;
/*    */ import net.minecraft.client.GameNarrator;
/*    */ import net.minecraft.client.gui.components.AbstractWidget;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.MultiLineTextWidget;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.layouts.FrameLayout;
/*    */ import net.minecraft.client.gui.layouts.LayoutElement;
/*    */ import net.minecraft.client.gui.layouts.LinearLayout;
/*    */ import net.minecraft.client.gui.screens.ConfirmLinkScreen;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.realms.RealmsScreen;
/*    */ 
/*    */ public class RealmsParentalConsentScreen extends RealmsScreen {
/* 18 */   private static final Component MESSAGE = (Component)Component.translatable("mco.account.privacy.information");
/*    */   
/*    */   private static final int SPACING = 15;
/* 21 */   private final LinearLayout layout = LinearLayout.vertical();
/*    */   private final Screen lastScreen;
/*    */   @Nullable
/*    */   private MultiLineTextWidget textWidget;
/*    */   
/*    */   public RealmsParentalConsentScreen(Screen $$0) {
/* 27 */     super(GameNarrator.NO_TITLE);
/* 28 */     this.lastScreen = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 33 */     this.layout.spacing(15).defaultCellSetting().alignHorizontallyCenter();
/* 34 */     this.textWidget = (new MultiLineTextWidget(MESSAGE, this.font)).setCentered(true);
/* 35 */     this.layout.addChild((LayoutElement)this.textWidget);
/* 36 */     LinearLayout $$0 = (LinearLayout)this.layout.addChild((LayoutElement)LinearLayout.horizontal().spacing(8));
/* 37 */     MutableComponent mutableComponent = Component.translatable("mco.account.privacy.info.button");
/* 38 */     $$0.addChild((LayoutElement)Button.builder((Component)mutableComponent, 
/* 39 */           ConfirmLinkScreen.confirmLink((Screen)this, "https://aka.ms/MinecraftGDPR"))
/*    */ 
/*    */         
/* 42 */         .build());
/*    */     
/* 44 */     $$0.addChild((LayoutElement)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose()).build());
/* 45 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/* 46 */     repositionElements();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClose() {
/* 51 */     this.minecraft.setScreen(this.lastScreen);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void repositionElements() {
/* 56 */     if (this.textWidget != null) {
/* 57 */       this.textWidget.setMaxWidth(this.width - 15);
/*    */     }
/* 59 */     this.layout.arrangeElements();
/* 60 */     FrameLayout.centerInRectangle((LayoutElement)this.layout, getRectangle());
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getNarrationMessage() {
/* 65 */     return MESSAGE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsParentalConsentScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */