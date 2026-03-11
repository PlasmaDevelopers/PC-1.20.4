/*    */ package net.minecraft.world.level.levelgen.synth;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseUtils
/*    */ {
/*    */   public static double biasTowardsExtreme(double $$0, double $$1) {
/* 14 */     return $$0 + Math.sin(Math.PI * $$0) * $$1 / Math.PI;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void parityNoiseOctaveConfigString(StringBuilder $$0, double $$1, double $$2, double $$3, byte[] $$4) {
/* 19 */     $$0.append(String.format(Locale.ROOT, "xo=%.3f, yo=%.3f, zo=%.3f, p0=%d, p255=%d", new Object[] { Float.valueOf((float)$$1), Float.valueOf((float)$$2), Float.valueOf((float)$$3), Byte.valueOf($$4[0]), Byte.valueOf($$4[255]) }));
/*    */   }
/*    */ 
/*    */   
/*    */   public static void parityNoiseOctaveConfigString(StringBuilder $$0, double $$1, double $$2, double $$3, int[] $$4) {
/* 24 */     $$0.append(String.format(Locale.ROOT, "xo=%.3f, yo=%.3f, zo=%.3f, p0=%d, p255=%d", new Object[] { Float.valueOf((float)$$1), Float.valueOf((float)$$2), Float.valueOf((float)$$3), Integer.valueOf($$4[0]), Integer.valueOf($$4[255]) }));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\synth\NoiseUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */