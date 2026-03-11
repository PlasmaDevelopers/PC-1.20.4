/*    */ package net.minecraft.client.gui.components.debugchart;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.util.SampleLogger;
/*    */ 
/*    */ public class FpsDebugChart
/*    */   extends AbstractDebugChart
/*    */ {
/*    */   private static final int RED = -65536;
/*    */   private static final int YELLOW = -256;
/*    */   private static final int GREEN = -16711936;
/*    */   private static final int CHART_TOP_FPS = 30;
/*    */   private static final double CHART_TOP_VALUE = 33.333333333333336D;
/*    */   
/*    */   public FpsDebugChart(Font $$0, SampleLogger $$1) {
/* 20 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderAdditionalLinesAndLabels(GuiGraphics $$0, int $$1, int $$2, int $$3) {
/* 25 */     drawStringWithShade($$0, "30 FPS", $$1 + 1, $$3 - 60 + 1);
/* 26 */     drawStringWithShade($$0, "60 FPS", $$1 + 1, $$3 - 30 + 1);
/* 27 */     $$0.hLine(RenderType.guiOverlay(), $$1, $$1 + $$2 - 1, $$3 - 30, -1);
/*    */     
/* 29 */     int $$4 = ((Integer)(Minecraft.getInstance()).options.framerateLimit().get()).intValue();
/* 30 */     if ($$4 > 0 && $$4 <= 250) {
/* 31 */       $$0.hLine(RenderType.guiOverlay(), $$1, $$1 + $$2 - 1, $$3 - getSampleHeight(1.0E9D / $$4) - 1, -16711681);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected String toDisplayString(double $$0) {
/* 37 */     return String.format(Locale.ROOT, "%d ms", new Object[] { Integer.valueOf((int)Math.round(toMilliseconds($$0))) });
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSampleHeight(double $$0) {
/* 42 */     return (int)Math.round(toMilliseconds($$0) * 60.0D / 33.333333333333336D);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSampleColor(long $$0) {
/* 47 */     return getSampleColor(toMilliseconds($$0), 0.0D, -16711936, 28.0D, -256, 56.0D, -65536);
/*    */   }
/*    */   
/*    */   private static double toMilliseconds(double $$0) {
/* 51 */     return $$0 / 1000000.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\debugchart\FpsDebugChart.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */