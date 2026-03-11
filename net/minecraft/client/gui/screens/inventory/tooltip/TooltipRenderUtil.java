/*    */ package net.minecraft.client.gui.screens.inventory.tooltip;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TooltipRenderUtil
/*    */ {
/*    */   public static final int MOUSE_OFFSET = 12;
/*    */   private static final int PADDING = 3;
/*    */   public static final int PADDING_LEFT = 3;
/*    */   public static final int PADDING_RIGHT = 3;
/*    */   public static final int PADDING_TOP = 3;
/*    */   public static final int PADDING_BOTTOM = 3;
/*    */   private static final int BACKGROUND_COLOR = -267386864;
/*    */   private static final int BORDER_COLOR_TOP = 1347420415;
/*    */   private static final int BORDER_COLOR_BOTTOM = 1344798847;
/*    */   
/*    */   public static void renderTooltipBackground(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 26 */     int $$6 = $$1 - 3;
/* 27 */     int $$7 = $$2 - 3;
/* 28 */     int $$8 = $$3 + 3 + 3;
/* 29 */     int $$9 = $$4 + 3 + 3;
/*    */ 
/*    */     
/* 32 */     renderHorizontalLine($$0, $$6, $$7 - 1, $$8, $$5, -267386864);
/* 33 */     renderHorizontalLine($$0, $$6, $$7 + $$9, $$8, $$5, -267386864);
/* 34 */     renderRectangle($$0, $$6, $$7, $$8, $$9, $$5, -267386864);
/* 35 */     renderVerticalLine($$0, $$6 - 1, $$7, $$9, $$5, -267386864);
/* 36 */     renderVerticalLine($$0, $$6 + $$8, $$7, $$9, $$5, -267386864);
/*    */ 
/*    */     
/* 39 */     renderFrameGradient($$0, $$6, $$7 + 1, $$8, $$9, $$5, 1347420415, 1344798847);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void renderFrameGradient(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7) {
/* 51 */     renderVerticalLineGradient($$0, $$1, $$2, $$4 - 2, $$5, $$6, $$7);
/* 52 */     renderVerticalLineGradient($$0, $$1 + $$3 - 1, $$2, $$4 - 2, $$5, $$6, $$7);
/* 53 */     renderHorizontalLine($$0, $$1, $$2 - 1, $$3, $$5, $$6);
/* 54 */     renderHorizontalLine($$0, $$1, $$2 - 1 + $$4 - 1, $$3, $$5, $$7);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void renderVerticalLine(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 64 */     $$0.fill($$1, $$2, $$1 + 1, $$2 + $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void renderVerticalLineGradient(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
/* 75 */     $$0.fillGradient($$1, $$2, $$1 + 1, $$2 + $$3, $$4, $$5, $$6);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void renderHorizontalLine(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 85 */     $$0.fill($$1, $$2, $$1 + $$3, $$2 + 1, $$4, $$5);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void renderRectangle(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6) {
/* 96 */     $$0.fill($$1, $$2, $$1 + $$3, $$2 + $$4, $$5, $$6);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\tooltip\TooltipRenderUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */