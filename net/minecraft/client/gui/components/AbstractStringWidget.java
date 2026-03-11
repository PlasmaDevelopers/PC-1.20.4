/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public abstract class AbstractStringWidget extends AbstractWidget {
/*    */   private final Font font;
/*  9 */   private int color = 16777215;
/*    */   
/*    */   public AbstractStringWidget(int $$0, int $$1, int $$2, int $$3, Component $$4, Font $$5) {
/* 12 */     super($$0, $$1, $$2, $$3, $$4);
/* 13 */     this.font = $$5;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void updateWidgetNarration(NarrationElementOutput $$0) {}
/*    */ 
/*    */   
/*    */   public AbstractStringWidget setColor(int $$0) {
/* 21 */     this.color = $$0;
/* 22 */     return this;
/*    */   }
/*    */   
/*    */   protected final Font getFont() {
/* 26 */     return this.font;
/*    */   }
/*    */   
/*    */   protected final int getColor() {
/* 30 */     return this.color;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\AbstractStringWidget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */