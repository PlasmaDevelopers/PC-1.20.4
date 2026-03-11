/*    */ package com.mojang.realmsclient.gui.screens;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*    */ import net.minecraft.client.GameNarrator;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.realms.RealmsScreen;
/*    */ 
/*    */ public class RealmsConfirmScreen extends RealmsScreen {
/*    */   protected BooleanConsumer callback;
/*    */   private final Component title1;
/*    */   private final Component title2;
/*    */   
/*    */   public RealmsConfirmScreen(BooleanConsumer $$0, Component $$1, Component $$2) {
/* 18 */     super(GameNarrator.NO_TITLE);
/* 19 */     this.callback = $$0;
/* 20 */     this.title1 = $$1;
/* 21 */     this.title2 = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 26 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_YES, $$0 -> this.callback.accept(true)).bounds(this.width / 2 - 105, row(9), 100, 20).build());
/* 27 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_NO, $$0 -> this.callback.accept(false)).bounds(this.width / 2 + 5, row(9), 100, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 32 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 34 */     $$0.drawCenteredString(this.font, this.title1, this.width / 2, row(3), -1);
/* 35 */     $$0.drawCenteredString(this.font, this.title2, this.width / 2, row(5), -1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsConfirmScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */