/*    */ package net.minecraft.client.gui.components.debugchart;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.util.SampleLogger;
/*    */ import net.minecraft.util.TimeUtil;
/*    */ 
/*    */ 
/*    */ public class TpsDebugChart
/*    */   extends AbstractDebugChart
/*    */ {
/*    */   private static final int RED = -65536;
/*    */   private static final int YELLOW = -256;
/*    */   private static final int GREEN = -16711936;
/*    */   private final Supplier<Float> msptSupplier;
/*    */   
/*    */   public TpsDebugChart(Font $$0, SampleLogger $$1, Supplier<Float> $$2) {
/* 20 */     super($$0, $$1);
/* 21 */     this.msptSupplier = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderAdditionalLinesAndLabels(GuiGraphics $$0, int $$1, int $$2, int $$3) {
/* 26 */     float $$4 = (float)TimeUtil.MILLISECONDS_PER_SECOND / ((Float)this.msptSupplier.get()).floatValue();
/* 27 */     drawStringWithShade($$0, String.format("%.1f TPS", new Object[] { Float.valueOf($$4) }), $$1 + 1, $$3 - 60 + 1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String toDisplayString(double $$0) {
/* 32 */     return String.format(Locale.ROOT, "%d ms", new Object[] { Integer.valueOf((int)Math.round(toMilliseconds($$0))) });
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSampleHeight(double $$0) {
/* 37 */     return (int)Math.round(toMilliseconds($$0) * 60.0D / ((Float)this.msptSupplier.get()).floatValue());
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSampleColor(long $$0) {
/* 42 */     float $$1 = ((Float)this.msptSupplier.get()).floatValue();
/* 43 */     return getSampleColor(toMilliseconds($$0), 0.0D, -16711936, $$1 / 2.0D, -256, $$1, -65536);
/*    */   }
/*    */   
/*    */   private static double toMilliseconds(double $$0) {
/* 47 */     return $$0 / 1000000.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\debugchart\TpsDebugChart.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */