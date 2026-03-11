/*    */ package net.minecraft.client.gui.components.debugchart;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.util.SampleLogger;
/*    */ 
/*    */ public class PingDebugChart
/*    */   extends AbstractDebugChart {
/*    */   private static final int RED = -65536;
/*    */   private static final int YELLOW = -256;
/*    */   private static final int GREEN = -16711936;
/*    */   private static final int CHART_TOP_VALUE = 500;
/*    */   
/*    */   public PingDebugChart(Font $$0, SampleLogger $$1) {
/* 16 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderAdditionalLinesAndLabels(GuiGraphics $$0, int $$1, int $$2, int $$3) {
/* 21 */     drawStringWithShade($$0, "500 ms", $$1 + 1, $$3 - 60 + 1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String toDisplayString(double $$0) {
/* 26 */     return String.format(Locale.ROOT, "%d ms", new Object[] { Integer.valueOf((int)Math.round($$0)) });
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSampleHeight(double $$0) {
/* 31 */     return (int)Math.round($$0 * 60.0D / 500.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSampleColor(long $$0) {
/* 36 */     return getSampleColor($$0, 0.0D, -16711936, 250.0D, -256, 500.0D, -65536);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\debugchart\PingDebugChart.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */