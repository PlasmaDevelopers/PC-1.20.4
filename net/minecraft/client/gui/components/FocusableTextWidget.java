/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.narration.NarratedElementType;
/*    */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*    */ import net.minecraft.client.sounds.SoundManager;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class FocusableTextWidget
/*    */   extends MultiLineTextWidget
/*    */ {
/*    */   private static final int BACKGROUND_COLOR = 1426063360;
/*    */   private static final int PADDING = 4;
/*    */   private final boolean alwaysShowBorder;
/*    */   
/*    */   public FocusableTextWidget(int $$0, Component $$1, Font $$2) {
/* 18 */     this($$0, $$1, $$2, true);
/*    */   }
/*    */   
/*    */   public FocusableTextWidget(int $$0, Component $$1, Font $$2, boolean $$3) {
/* 22 */     super($$1, $$2);
/* 23 */     setMaxWidth($$0);
/* 24 */     setCentered(true);
/* 25 */     this.active = true;
/* 26 */     this.alwaysShowBorder = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void updateWidgetNarration(NarrationElementOutput $$0) {
/* 31 */     $$0.add(NarratedElementType.TITLE, getMessage());
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 36 */     if (isFocused() || this.alwaysShowBorder) {
/* 37 */       int $$4 = getX() - 4;
/* 38 */       int $$5 = getY() - 4;
/* 39 */       int $$6 = getWidth() + 8;
/* 40 */       int $$7 = getHeight() + 8;
/* 41 */       int $$8 = this.alwaysShowBorder ? (isFocused() ? -1 : -6250336) : -1;
/* 42 */       $$0.fill($$4 + 1, $$5, $$4 + $$6, $$5 + $$7, 1426063360);
/* 43 */       $$0.renderOutline($$4, $$5, $$6, $$7, $$8);
/*    */     } 
/*    */     
/* 46 */     super.renderWidget($$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public void playDownSound(SoundManager $$0) {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\FocusableTextWidget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */