/*    */ package net.minecraft.util;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import java.util.Arrays;
/*    */ import java.util.Objects;
/*    */ import java.util.function.IntFunction;
/*    */ import java.util.function.ToIntFunction;
/*    */ 
/*    */ public class ByIdMap
/*    */ {
/*    */   public enum OutOfBoundsStrategy
/*    */   {
/* 13 */     ZERO,
/* 14 */     WRAP,
/* 15 */     CLAMP;
/*    */   }
/*    */   
/*    */   private static <T> IntFunction<T> createMap(ToIntFunction<T> $$0, T[] $$1) {
/* 19 */     if ($$1.length == 0) {
/* 20 */       throw new IllegalArgumentException("Empty value list");
/*    */     }
/*    */     
/* 23 */     Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap();
/* 24 */     for (T $$3 : $$1) {
/* 25 */       int $$4 = $$0.applyAsInt($$3);
/* 26 */       T $$5 = (T)int2ObjectOpenHashMap.put($$4, $$3);
/* 27 */       if ($$5 != null) {
/* 28 */         throw new IllegalArgumentException("Duplicate entry on id " + $$4 + ": current=" + $$3 + ", previous=" + $$5);
/*    */       }
/*    */     } 
/* 31 */     return (IntFunction<T>)int2ObjectOpenHashMap;
/*    */   }
/*    */   
/*    */   public static <T> IntFunction<T> sparse(ToIntFunction<T> $$0, T[] $$1, T $$2) {
/* 35 */     IntFunction<T> $$3 = createMap($$0, $$1);
/* 36 */     return $$2 -> Objects.requireNonNullElse($$0.apply($$2), $$1);
/*    */   }
/*    */   
/*    */   private static <T> T[] createSortedArray(ToIntFunction<T> $$0, T[] $$1) {
/* 40 */     int $$2 = $$1.length;
/* 41 */     if ($$2 == 0) {
/* 42 */       throw new IllegalArgumentException("Empty value list");
/*    */     }
/*    */     
/* 45 */     T[] $$3 = (T[])$$1.clone();
/* 46 */     Arrays.fill((Object[])$$3, (Object)null);
/*    */     
/* 48 */     for (T $$4 : $$1) {
/* 49 */       int $$5 = $$0.applyAsInt($$4);
/* 50 */       if ($$5 < 0 || $$5 >= $$2) {
/* 51 */         throw new IllegalArgumentException("Values are not continous, found index " + $$5 + " for value " + $$4);
/*    */       }
/* 53 */       T $$6 = $$3[$$5];
/* 54 */       if ($$6 != null) {
/* 55 */         throw new IllegalArgumentException("Duplicate entry on id " + $$5 + ": current=" + $$4 + ", previous=" + $$6);
/*    */       }
/* 57 */       $$3[$$5] = $$4;
/*    */     } 
/*    */     
/* 60 */     for (int $$7 = 0; $$7 < $$2; $$7++) {
/* 61 */       if ($$3[$$7] == null) {
/* 62 */         throw new IllegalArgumentException("Missing value at index: " + $$7);
/*    */       }
/*    */     } 
/*    */     
/* 66 */     return $$3;
/*    */   }
/*    */   
/*    */   public static <T> IntFunction<T> continuous(ToIntFunction<T> $$0, T[] $$1, OutOfBoundsStrategy $$2) {
/* 70 */     T $$5, $$3[] = createSortedArray($$0, $$1);
/* 71 */     int $$4 = $$3.length;
/* 72 */     switch ($$2) { default: throw new IncompatibleClassChangeError();
/*    */       case ZERO:
/* 74 */         $$5 = $$3[0];
/*    */       case WRAP:
/*    */       
/*    */       case CLAMP:
/*    */         break; }
/*    */     
/*    */     return $$2 -> $$0[Mth.clamp($$2, 0, $$1 - 1)];
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ByIdMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */