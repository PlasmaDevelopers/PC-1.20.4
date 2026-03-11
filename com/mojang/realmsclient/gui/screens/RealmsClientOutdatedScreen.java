/*    */ package com.mojang.realmsclient.gui.screens;
/*    */ 
/*    */ import net.minecraft.SharedConstants;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.realms.RealmsScreen;
/*    */ 
/*    */ public class RealmsClientOutdatedScreen
/*    */   extends RealmsScreen {
/* 14 */   private static final Component INCOMPATIBLE_TITLE = (Component)Component.translatable("mco.client.incompatible.title");
/* 15 */   private static final Component[] INCOMPATIBLE_MESSAGES_SNAPSHOT = new Component[] {
/* 16 */       (Component)Component.translatable("mco.client.incompatible.msg.line1"), 
/* 17 */       (Component)Component.translatable("mco.client.incompatible.msg.line2"), 
/* 18 */       (Component)Component.translatable("mco.client.incompatible.msg.line3")
/*    */     };
/* 20 */   private static final Component[] INCOMPATIBLE_MESSAGES = new Component[] {
/* 21 */       (Component)Component.translatable("mco.client.incompatible.msg.line1"), 
/* 22 */       (Component)Component.translatable("mco.client.incompatible.msg.line2")
/*    */     };
/*    */   
/*    */   private final Screen lastScreen;
/*    */   
/*    */   public RealmsClientOutdatedScreen(Screen $$0) {
/* 28 */     super(INCOMPATIBLE_TITLE);
/* 29 */     this.lastScreen = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 34 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> this.minecraft.setScreen(this.lastScreen)).bounds(this.width / 2 - 100, row(12), 200, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 39 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 41 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, row(3), -65536);
/* 42 */     Component[] $$4 = getMessages();
/* 43 */     for (int $$5 = 0; $$5 < $$4.length; $$5++) {
/* 44 */       $$0.drawCenteredString(this.font, $$4[$$5], this.width / 2, row(5) + $$5 * 12, -1);
/*    */     }
/*    */   }
/*    */   
/*    */   private Component[] getMessages() {
/* 49 */     if (SharedConstants.getCurrentVersion().isStable()) {
/* 50 */       return INCOMPATIBLE_MESSAGES;
/*    */     }
/* 52 */     return INCOMPATIBLE_MESSAGES_SNAPSHOT;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 57 */     if ($$0 == 257 || $$0 == 335 || $$0 == 256) {
/* 58 */       this.minecraft.setScreen(this.lastScreen);
/* 59 */       return true;
/*    */     } 
/* 61 */     return super.keyPressed($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsClientOutdatedScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */