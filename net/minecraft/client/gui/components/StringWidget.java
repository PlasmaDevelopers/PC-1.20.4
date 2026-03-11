/*    */ package net.minecraft.client.gui.components;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.locale.Language;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.util.FormattedCharSequence;
/*    */ 
/*    */ public class StringWidget extends AbstractStringWidget {
/* 12 */   private float alignX = 0.5F;
/*    */   
/*    */   public StringWidget(Component $$0, Font $$1) {
/* 15 */     this(0, 0, $$1.width($$0.getVisualOrderText()), 9, $$0, $$1);
/*    */   }
/*    */   
/*    */   public StringWidget(int $$0, int $$1, Component $$2, Font $$3) {
/* 19 */     this(0, 0, $$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public StringWidget(int $$0, int $$1, int $$2, int $$3, Component $$4, Font $$5) {
/* 23 */     super($$0, $$1, $$2, $$3, $$4, $$5);
/* 24 */     this.active = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public StringWidget setColor(int $$0) {
/* 29 */     super.setColor($$0);
/* 30 */     return this;
/*    */   }
/*    */   
/*    */   private StringWidget horizontalAlignment(float $$0) {
/* 34 */     this.alignX = $$0;
/* 35 */     return this;
/*    */   }
/*    */   
/*    */   public StringWidget alignLeft() {
/* 39 */     return horizontalAlignment(0.0F);
/*    */   }
/*    */   
/*    */   public StringWidget alignCenter() {
/* 43 */     return horizontalAlignment(0.5F);
/*    */   }
/*    */   
/*    */   public StringWidget alignRight() {
/* 47 */     return horizontalAlignment(1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 52 */     Component $$4 = getMessage();
/* 53 */     Font $$5 = getFont();
/* 54 */     int $$6 = getWidth();
/* 55 */     int $$7 = $$5.width((FormattedText)$$4);
/* 56 */     int $$8 = getX() + Math.round(this.alignX * ($$6 - $$7));
/* 57 */     Objects.requireNonNull($$5); int $$9 = getY() + (getHeight() - 9) / 2;
/*    */     
/* 59 */     FormattedCharSequence $$10 = ($$7 > $$6) ? clipText($$4, $$6) : $$4.getVisualOrderText();
/* 60 */     $$0.drawString($$5, $$10, $$8, $$9, getColor());
/*    */   }
/*    */   
/*    */   private FormattedCharSequence clipText(Component $$0, int $$1) {
/* 64 */     Font $$2 = getFont();
/* 65 */     FormattedText $$3 = $$2.substrByWidth((FormattedText)$$0, $$1 - $$2.width((FormattedText)CommonComponents.ELLIPSIS));
/* 66 */     return Language.getInstance().getVisualOrder(FormattedText.composite(new FormattedText[] { $$3, (FormattedText)CommonComponents.ELLIPSIS }));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\StringWidget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */