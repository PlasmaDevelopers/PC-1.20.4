/*    */ package net.minecraft.util;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ToFloatFunction<C>
/*    */ {
/*    */   public static final ToFloatFunction<Float> IDENTITY;
/*    */   
/*    */   static ToFloatFunction<Float> createUnlimited(final Float2FloatFunction function) {
/* 15 */     return new ToFloatFunction<Float>()
/*    */       {
/*    */         public float apply(Float $$0) {
/* 18 */           return ((Float)function.apply($$0)).floatValue();
/*    */         }
/*    */ 
/*    */         
/*    */         public float minValue() {
/* 23 */           return Float.NEGATIVE_INFINITY;
/*    */         }
/*    */ 
/*    */         
/*    */         public float maxValue() {
/* 28 */           return Float.POSITIVE_INFINITY;
/*    */         }
/*    */       };
/*    */   }
/*    */   static {
/* 33 */     IDENTITY = createUnlimited($$0 -> $$0);
/*    */   }
/*    */   default <C2> ToFloatFunction<C2> comap(final Function<C2, C> function) {
/* 36 */     final ToFloatFunction<C> outer = this;
/* 37 */     return new ToFloatFunction<C2>()
/*    */       {
/*    */         public float apply(C2 $$0) {
/* 40 */           return outer.apply(function.apply($$0));
/*    */         }
/*    */ 
/*    */         
/*    */         public float minValue() {
/* 45 */           return outer.minValue();
/*    */         }
/*    */ 
/*    */         
/*    */         public float maxValue() {
/* 50 */           return outer.maxValue();
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   float apply(C paramC);
/*    */   
/*    */   float minValue();
/*    */   
/*    */   float maxValue();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ToFloatFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */