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
/*    */ public class RealmsLongConfirmationScreen
/*    */   extends RealmsScreen {
/* 14 */   static final Component WARNING = (Component)Component.translatable("mco.warning");
/* 15 */   static final Component INFO = (Component)Component.translatable("mco.info");
/*    */   
/*    */   private final Type type;
/*    */   
/*    */   private final Component line2;
/*    */   private final Component line3;
/*    */   protected final BooleanConsumer callback;
/*    */   private final boolean yesNoQuestion;
/*    */   
/*    */   public RealmsLongConfirmationScreen(BooleanConsumer $$0, Type $$1, Component $$2, Component $$3, boolean $$4) {
/* 25 */     super(GameNarrator.NO_TITLE);
/* 26 */     this.callback = $$0;
/* 27 */     this.type = $$1;
/* 28 */     this.line2 = $$2;
/* 29 */     this.line3 = $$3;
/* 30 */     this.yesNoQuestion = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 35 */     if (this.yesNoQuestion) {
/* 36 */       addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_YES, $$0 -> this.callback.accept(true)).bounds(this.width / 2 - 105, row(8), 100, 20).build());
/* 37 */       addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_NO, $$0 -> this.callback.accept(false)).bounds(this.width / 2 + 5, row(8), 100, 20).build());
/*    */     } else {
/* 39 */       addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_OK, $$0 -> this.callback.accept(true)).bounds(this.width / 2 - 50, row(8), 100, 20).build());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getNarrationMessage() {
/* 45 */     return CommonComponents.joinLines(new Component[] { this.type.text, this.line2, this.line3 });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 50 */     if ($$0 == 256) {
/* 51 */       this.callback.accept(false);
/* 52 */       return true;
/*    */     } 
/* 54 */     return super.keyPressed($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 59 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 61 */     $$0.drawCenteredString(this.font, this.type.text, this.width / 2, row(2), this.type.colorCode);
/* 62 */     $$0.drawCenteredString(this.font, this.line2, this.width / 2, row(4), -1);
/*    */     
/* 64 */     $$0.drawCenteredString(this.font, this.line3, this.width / 2, row(6), -1);
/*    */   }
/*    */   
/*    */   public enum Type {
/* 68 */     WARNING((String)RealmsLongConfirmationScreen.WARNING, -65536),
/* 69 */     INFO((String)RealmsLongConfirmationScreen.INFO, 8226750);
/*    */     
/*    */     Type(Component $$0, int $$1) {
/* 72 */       this.text = $$0;
/* 73 */       this.colorCode = $$1;
/*    */     }
/*    */     
/*    */     public final int colorCode;
/*    */     public final Component text;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsLongConfirmationScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */