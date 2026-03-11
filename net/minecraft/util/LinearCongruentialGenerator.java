/*    */ package net.minecraft.util;
/*    */ 
/*    */ public class LinearCongruentialGenerator {
/*    */   private static final long MULTIPLIER = 6364136223846793005L;
/*    */   private static final long INCREMENT = 1442695040888963407L;
/*    */   
/*    */   public static long next(long $$0, long $$1) {
/*  8 */     $$0 *= $$0 * 6364136223846793005L + 1442695040888963407L;
/*  9 */     $$0 += $$1;
/* 10 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\LinearCongruentialGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */