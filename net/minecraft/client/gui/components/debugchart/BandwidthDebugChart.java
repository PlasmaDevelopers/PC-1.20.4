/*    */ package net.minecraft.client.gui.components.debugchart;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.SampleLogger;
/*    */ 
/*    */ public class BandwidthDebugChart
/*    */   extends AbstractDebugChart
/*    */ {
/*    */   private static final int MIN_COLOR = -16711681;
/*    */   private static final int MID_COLOR = -6250241;
/*    */   private static final int MAX_COLOR = -65536;
/*    */   private static final int KILOBYTE = 1024;
/*    */   private static final int MEGABYTE = 1048576;
/*    */   private static final int CHART_TOP_VALUE = 1048576;
/*    */   
/*    */   public BandwidthDebugChart(Font $$0, SampleLogger $$1) {
/* 21 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderAdditionalLinesAndLabels(GuiGraphics $$0, int $$1, int $$2, int $$3) {
/* 26 */     drawLabeledLineAtValue($$0, $$1, $$2, $$3, 64);
/* 27 */     drawLabeledLineAtValue($$0, $$1, $$2, $$3, 1024);
/* 28 */     drawLabeledLineAtValue($$0, $$1, $$2, $$3, 16384);
/* 29 */     drawStringWithShade($$0, toDisplayStringInternal(1048576.0D), $$1 + 1, $$3 - getSampleHeightInternal(1048576.0D) + 1);
/*    */   }
/*    */   
/*    */   private void drawLabeledLineAtValue(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 33 */     drawLineWithLabel($$0, $$1, $$2, $$3 - getSampleHeightInternal($$4), toDisplayStringInternal($$4));
/*    */   }
/*    */   
/*    */   private void drawLineWithLabel(GuiGraphics $$0, int $$1, int $$2, int $$3, String $$4) {
/* 37 */     drawStringWithShade($$0, $$4, $$1 + 1, $$3 + 1);
/* 38 */     $$0.hLine(RenderType.guiOverlay(), $$1, $$1 + $$2 - 1, $$3, -1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String toDisplayString(double $$0) {
/* 43 */     return toDisplayStringInternal(toBytesPerSecond($$0));
/*    */   }
/*    */   
/*    */   private static String toDisplayStringInternal(double $$0) {
/* 47 */     if ($$0 >= 1048576.0D) {
/* 48 */       return String.format(Locale.ROOT, "%.1f MiB/s", new Object[] { Double.valueOf($$0 / 1048576.0D) });
/*    */     }
/* 50 */     if ($$0 >= 1024.0D) {
/* 51 */       return String.format(Locale.ROOT, "%.1f KiB/s", new Object[] { Double.valueOf($$0 / 1024.0D) });
/*    */     }
/* 53 */     return String.format(Locale.ROOT, "%d B/s", new Object[] { Integer.valueOf(Mth.floor($$0)) });
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSampleHeight(double $$0) {
/* 58 */     return getSampleHeightInternal(toBytesPerSecond($$0));
/*    */   }
/*    */   
/*    */   private static int getSampleHeightInternal(double $$0) {
/* 62 */     return (int)Math.round(Math.log($$0 + 1.0D) * 60.0D / Math.log(1048576.0D));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSampleColor(long $$0) {
/* 67 */     return getSampleColor(toBytesPerSecond($$0), 0.0D, -16711681, 8192.0D, -6250241, 1.048576E7D, -65536);
/*    */   }
/*    */   
/*    */   private static double toBytesPerSecond(double $$0) {
/* 71 */     return $$0 * 20.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\debugchart\BandwidthDebugChart.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */