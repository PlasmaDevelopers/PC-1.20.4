/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.narration.NarratedElementType;
/*    */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class FittingMultiLineTextWidget extends AbstractScrollWidget {
/*    */   private final Font font;
/*    */   
/*    */   public FittingMultiLineTextWidget(int $$0, int $$1, int $$2, int $$3, Component $$4, Font $$5) {
/* 14 */     super($$0, $$1, $$2, $$3, $$4);
/* 15 */     this.font = $$5;
/* 16 */     this.multilineWidget = (new MultiLineTextWidget($$4, $$5)).setMaxWidth(getWidth() - totalInnerPadding());
/*    */   }
/*    */   private final MultiLineTextWidget multilineWidget;
/*    */   public FittingMultiLineTextWidget setColor(int $$0) {
/* 20 */     this.multilineWidget.setColor($$0);
/* 21 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setWidth(int $$0) {
/* 26 */     super.setWidth($$0);
/* 27 */     this.multilineWidget.setMaxWidth(getWidth() - totalInnerPadding());
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getInnerHeight() {
/* 32 */     return this.multilineWidget.getHeight();
/*    */   }
/*    */ 
/*    */   
/*    */   protected double scrollRate() {
/* 37 */     Objects.requireNonNull(this.font); return 9.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderBackground(GuiGraphics $$0) {
/* 42 */     if (scrollbarVisible()) {
/* 43 */       super.renderBackground($$0);
/* 44 */     } else if (isFocused()) {
/* 45 */       renderBorder($$0, getX() - innerPadding(), getY() - innerPadding(), getWidth() + totalInnerPadding(), getHeight() + totalInnerPadding());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 51 */     if (!this.visible) {
/*    */       return;
/*    */     }
/* 54 */     if (!scrollbarVisible()) {
/* 55 */       renderBackground($$0);
/* 56 */       $$0.pose().pushPose();
/* 57 */       $$0.pose().translate(getX(), getY(), 0.0F);
/* 58 */       this.multilineWidget.render($$0, $$1, $$2, $$3);
/* 59 */       $$0.pose().popPose();
/*    */     } else {
/* 61 */       super.renderWidget($$0, $$1, $$2, $$3);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean showingScrollBar() {
/* 66 */     return scrollbarVisible();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderContents(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 71 */     $$0.pose().pushPose();
/* 72 */     $$0.pose().translate((getX() + innerPadding()), (getY() + innerPadding()), 0.0F);
/* 73 */     this.multilineWidget.render($$0, $$1, $$2, $$3);
/* 74 */     $$0.pose().popPose();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void updateWidgetNarration(NarrationElementOutput $$0) {
/* 79 */     $$0.add(NarratedElementType.TITLE, getMessage());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\FittingMultiLineTextWidget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */