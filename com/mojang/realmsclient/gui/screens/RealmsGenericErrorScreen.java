/*    */ package com.mojang.realmsclient.gui.screens;
/*    */ 
/*    */ import com.mojang.realmsclient.client.RealmsError;
/*    */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.client.GameNarrator;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.MultiLineLabel;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.realms.RealmsScreen;
/*    */ 
/*    */ public class RealmsGenericErrorScreen extends RealmsScreen {
/*    */   private final Screen nextScreen;
/*    */   private final ErrorMessage lines;
/* 20 */   private MultiLineLabel line2Split = MultiLineLabel.EMPTY;
/*    */   
/*    */   public RealmsGenericErrorScreen(RealmsServiceException $$0, Screen $$1) {
/* 23 */     super(GameNarrator.NO_TITLE);
/* 24 */     this.nextScreen = $$1;
/* 25 */     this.lines = errorMessage($$0);
/*    */   }
/*    */   
/*    */   public RealmsGenericErrorScreen(Component $$0, Screen $$1) {
/* 29 */     super(GameNarrator.NO_TITLE);
/* 30 */     this.nextScreen = $$1;
/* 31 */     this.lines = errorMessage($$0);
/*    */   }
/*    */   
/*    */   public RealmsGenericErrorScreen(Component $$0, Component $$1, Screen $$2) {
/* 35 */     super(GameNarrator.NO_TITLE);
/* 36 */     this.nextScreen = $$2;
/* 37 */     this.lines = errorMessage($$0, $$1);
/*    */   }
/*    */   
/*    */   private static ErrorMessage errorMessage(RealmsServiceException $$0) {
/* 41 */     RealmsError $$1 = $$0.realmsError;
/* 42 */     return errorMessage(
/* 43 */         (Component)Component.translatable("mco.errorMessage.realmsService.realmsError", new Object[] { Integer.valueOf($$1.errorCode()) }), $$1
/* 44 */         .errorMessage());
/*    */   }
/*    */ 
/*    */   
/*    */   private static ErrorMessage errorMessage(Component $$0) {
/* 49 */     return errorMessage(
/* 50 */         (Component)Component.translatable("mco.errorMessage.generic"), $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static ErrorMessage errorMessage(Component $$0, Component $$1) {
/* 56 */     return new ErrorMessage($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 61 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_OK, $$0 -> this.minecraft.setScreen(this.nextScreen)).bounds(this.width / 2 - 100, this.height - 52, 200, 20).build());
/* 62 */     this.line2Split = MultiLineLabel.create(this.font, (FormattedText)this.lines.detail, this.width * 3 / 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getNarrationMessage() {
/* 67 */     return (Component)Component.empty().append(this.lines.title).append(": ").append(this.lines.detail);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 72 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 74 */     $$0.drawCenteredString(this.font, this.lines.title, this.width / 2, 80, -1);
/* 75 */     Objects.requireNonNull(this.minecraft.font); this.line2Split.renderCentered($$0, this.width / 2, 100, 9, -2142128);
/*    */   }
/*    */   private static final class ErrorMessage extends Record { final Component title; final Component detail;
/* 78 */     ErrorMessage(Component $$0, Component $$1) { this.title = $$0; this.detail = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/gui/screens/RealmsGenericErrorScreen$ErrorMessage;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #78	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 78 */       //   0	7	0	this	Lcom/mojang/realmsclient/gui/screens/RealmsGenericErrorScreen$ErrorMessage; } public Component title() { return this.title; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/gui/screens/RealmsGenericErrorScreen$ErrorMessage;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #78	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lcom/mojang/realmsclient/gui/screens/RealmsGenericErrorScreen$ErrorMessage; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/gui/screens/RealmsGenericErrorScreen$ErrorMessage;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #78	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lcom/mojang/realmsclient/gui/screens/RealmsGenericErrorScreen$ErrorMessage;
/* 78 */       //   0	8	1	$$0	Ljava/lang/Object; } public Component detail() { return this.detail; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsGenericErrorScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */