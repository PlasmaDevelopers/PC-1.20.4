/*    */ package net.minecraft.util;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements ToFloatFunction<Float>
/*    */ {
/*    */   public float apply(Float $$0) {
/* 18 */     return ((Float)function.apply($$0)).floatValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public float minValue() {
/* 23 */     return Float.NEGATIVE_INFINITY;
/*    */   }
/*    */ 
/*    */   
/*    */   public float maxValue() {
/* 28 */     return Float.POSITIVE_INFINITY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ToFloatFunction$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */