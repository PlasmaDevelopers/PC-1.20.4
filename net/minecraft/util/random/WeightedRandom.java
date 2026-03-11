/*    */ package net.minecraft.util.random;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeightedRandom
/*    */ {
/*    */   public static int getTotalWeight(List<? extends WeightedEntry> $$0) {
/* 14 */     long $$1 = 0L;
/* 15 */     for (WeightedEntry $$2 : $$0) {
/* 16 */       $$1 += $$2.getWeight().asInt();
/*    */     }
/*    */     
/* 19 */     if ($$1 > 2147483647L) {
/* 20 */       throw new IllegalArgumentException("Sum of weights must be <= 2147483647");
/*    */     }
/* 22 */     return (int)$$1;
/*    */   }
/*    */   
/*    */   public static <T extends WeightedEntry> Optional<T> getRandomItem(RandomSource $$0, List<T> $$1, int $$2) {
/* 26 */     if ($$2 < 0) {
/* 27 */       throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException("Negative total weight in getRandomItem"));
/*    */     }
/*    */     
/* 30 */     if ($$2 == 0) {
/* 31 */       return Optional.empty();
/*    */     }
/*    */     
/* 34 */     int $$3 = $$0.nextInt($$2);
/* 35 */     return getWeightedItem($$1, $$3);
/*    */   }
/*    */   
/*    */   public static <T extends WeightedEntry> Optional<T> getWeightedItem(List<T> $$0, int $$1) {
/* 39 */     for (WeightedEntry weightedEntry : $$0) {
/* 40 */       $$1 -= weightedEntry.getWeight().asInt();
/* 41 */       if ($$1 < 0) {
/* 42 */         return Optional.of((T)weightedEntry);
/*    */       }
/*    */     } 
/* 45 */     return Optional.empty();
/*    */   }
/*    */   
/*    */   public static <T extends WeightedEntry> Optional<T> getRandomItem(RandomSource $$0, List<T> $$1) {
/* 49 */     return getRandomItem($$0, $$1, getTotalWeight($$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\random\WeightedRandom.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */