/*    */ package net.minecraft.client.gui.components.debugchart;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.util.FastColor;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.SampleLogger;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractDebugChart
/*    */ {
/*    */   protected static final int COLOR_GREY = 14737632;
/*    */   protected static final int CHART_HEIGHT = 60;
/*    */   
/*    */   protected AbstractDebugChart(Font $$0, SampleLogger $$1) {
/* 18 */     this.font = $$0;
/* 19 */     this.logger = $$1;
/*    */   }
/*    */   protected static final int LINE_WIDTH = 1; protected final Font font; protected final SampleLogger logger;
/*    */   public int getWidth(int $$0) {
/* 23 */     return Math.min(this.logger.capacity() + 2, $$0);
/*    */   }
/*    */   
/*    */   public void drawChart(GuiGraphics $$0, int $$1, int $$2) {
/* 27 */     int $$3 = $$0.guiHeight();
/* 28 */     $$0.fill(RenderType.guiOverlay(), $$1, $$3 - 60, $$1 + $$2, $$3, -1873784752);
/*    */     
/* 30 */     long $$4 = 0L;
/* 31 */     long $$5 = 2147483647L;
/* 32 */     long $$6 = -2147483648L;
/* 33 */     int $$7 = Math.max(0, this.logger.capacity() - $$2 - 2);
/* 34 */     int $$8 = this.logger.size() - $$7;
/* 35 */     for (int $$9 = 0; $$9 < $$8; $$9++) {
/* 36 */       int $$10 = $$1 + $$9 + 1;
/* 37 */       long $$11 = this.logger.get($$7 + $$9);
/* 38 */       $$5 = Math.min($$5, $$11);
/* 39 */       $$6 = Math.max($$6, $$11);
/* 40 */       $$4 += $$11;
/* 41 */       int $$12 = getSampleHeight($$11);
/* 42 */       int $$13 = getSampleColor($$11);
/* 43 */       $$0.fill(RenderType.guiOverlay(), $$10, $$3 - $$12, $$10 + 1, $$3, $$13);
/*    */     } 
/*    */     
/* 46 */     $$0.hLine(RenderType.guiOverlay(), $$1, $$1 + $$2 - 1, $$3 - 60, -1);
/* 47 */     $$0.hLine(RenderType.guiOverlay(), $$1, $$1 + $$2 - 1, $$3 - 1, -1);
/* 48 */     $$0.vLine(RenderType.guiOverlay(), $$1, $$3 - 60, $$3, -1);
/* 49 */     $$0.vLine(RenderType.guiOverlay(), $$1 + $$2 - 1, $$3 - 60, $$3, -1);
/*    */     
/* 51 */     if ($$8 > 0) {
/* 52 */       String $$14 = toDisplayString($$5) + " min";
/* 53 */       String $$15 = toDisplayString($$4 / $$8) + " avg";
/* 54 */       String $$16 = toDisplayString($$6) + " max";
/*    */       
/* 56 */       Objects.requireNonNull(this.font); $$0.drawString(this.font, $$14, $$1 + 2, $$3 - 60 - 9, 14737632);
/* 57 */       Objects.requireNonNull(this.font); $$0.drawCenteredString(this.font, $$15, $$1 + $$2 / 2, $$3 - 60 - 9, 14737632);
/* 58 */       Objects.requireNonNull(this.font); $$0.drawString(this.font, $$16, $$1 + $$2 - this.font.width($$16) - 2, $$3 - 60 - 9, 14737632);
/*    */     } 
/*    */     
/* 61 */     renderAdditionalLinesAndLabels($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderAdditionalLinesAndLabels(GuiGraphics $$0, int $$1, int $$2, int $$3) {}
/*    */   
/*    */   protected void drawStringWithShade(GuiGraphics $$0, String $$1, int $$2, int $$3) {
/* 68 */     Objects.requireNonNull(this.font); $$0.fill(RenderType.guiOverlay(), $$2, $$3, $$2 + this.font.width($$1) + 1, $$3 + 9, -1873784752);
/* 69 */     $$0.drawString(this.font, $$1, $$2 + 1, $$3 + 1, 14737632, false);
/*    */   }
/*    */   
/*    */   protected abstract String toDisplayString(double paramDouble);
/*    */   
/*    */   protected abstract int getSampleHeight(double paramDouble);
/*    */   
/*    */   protected abstract int getSampleColor(long paramLong);
/*    */   
/*    */   protected int getSampleColor(double $$0, double $$1, int $$2, double $$3, int $$4, double $$5, int $$6) {
/* 79 */     $$0 = Mth.clamp($$0, $$1, $$5);
/* 80 */     if ($$0 < $$3) {
/* 81 */       return FastColor.ARGB32.lerp((float)($$0 / ($$3 - $$1)), $$2, $$4);
/*    */     }
/* 83 */     return FastColor.ARGB32.lerp((float)(($$0 - $$3) / ($$5 - $$3)), $$4, $$6);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\debugchart\AbstractDebugChart.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */