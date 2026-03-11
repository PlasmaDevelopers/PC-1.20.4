/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public interface BitRandomSource
/*    */   extends RandomSource {
/*    */   public static final float FLOAT_MULTIPLIER = 5.9604645E-8F;
/*    */   public static final double DOUBLE_MULTIPLIER = 1.1102230246251565E-16D;
/*    */   
/*    */   int next(int paramInt);
/*    */   
/*    */   default int nextInt() {
/* 13 */     return next(32);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default int nextInt(int $$0) {
/* 21 */     if ($$0 <= 0) {
/* 22 */       throw new IllegalArgumentException("Bound must be positive");
/*    */     }
/*    */     
/* 25 */     if (($$0 & $$0 - 1) == 0)
/*    */     {
/* 27 */       return (int)($$0 * next(31) >> 31L);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     while (true) {
/* 33 */       int $$1 = next(31);
/* 34 */       int $$2 = $$1 % $$0;
/* 35 */       if ($$1 - $$2 + $$0 - 1 >= 0) {
/* 36 */         return $$2;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   default long nextLong() {
/* 43 */     int $$0 = next(32);
/* 44 */     int $$1 = next(32);
/* 45 */     long $$2 = $$0 << 32L;
/* 46 */     return $$2 + $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   default boolean nextBoolean() {
/* 51 */     return (next(1) != 0);
/*    */   }
/*    */ 
/*    */   
/*    */   default float nextFloat() {
/* 56 */     return next(24) * 5.9604645E-8F;
/*    */   }
/*    */ 
/*    */   
/*    */   default double nextDouble() {
/* 61 */     int $$0 = next(26);
/* 62 */     int $$1 = next(27);
/* 63 */     long $$2 = ($$0 << 27L) + $$1;
/* 64 */     return $$2 * 1.1102230246251565E-16D;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\BitRandomSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */