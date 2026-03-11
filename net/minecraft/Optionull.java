/*    */ package net.minecraft;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class Optionull {
/*    */   @Nullable
/*    */   public static <T, R> R map(@Nullable T $$0, Function<T, R> $$1) {
/* 12 */     return ($$0 == null) ? null : $$1.apply($$0);
/*    */   }
/*    */   
/*    */   public static <T, R> R mapOrDefault(@Nullable T $$0, Function<T, R> $$1, R $$2) {
/* 16 */     return ($$0 == null) ? $$2 : $$1.apply($$0);
/*    */   }
/*    */   
/*    */   public static <T, R> R mapOrElse(@Nullable T $$0, Function<T, R> $$1, Supplier<R> $$2) {
/* 20 */     return ($$0 == null) ? $$2.get() : $$1.apply($$0);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static <T> T first(Collection<T> $$0) {
/* 25 */     Iterator<T> $$1 = $$0.iterator();
/* 26 */     return $$1.hasNext() ? $$1.next() : null;
/*    */   }
/*    */   
/*    */   public static <T> T firstOrDefault(Collection<T> $$0, T $$1) {
/* 30 */     Iterator<T> $$2 = $$0.iterator();
/* 31 */     return $$2.hasNext() ? $$2.next() : $$1;
/*    */   }
/*    */   
/*    */   public static <T> T firstOrElse(Collection<T> $$0, Supplier<T> $$1) {
/* 35 */     Iterator<T> $$2 = $$0.iterator();
/* 36 */     return $$2.hasNext() ? $$2.next() : $$1.get();
/*    */   }
/*    */   
/*    */   public static <T> boolean isNullOrEmpty(@Nullable T[] $$0) {
/* 40 */     return ($$0 == null || $$0.length == 0);
/*    */   }
/*    */   
/*    */   public static boolean isNullOrEmpty(@Nullable boolean[] $$0) {
/* 44 */     return ($$0 == null || $$0.length == 0);
/*    */   }
/*    */   
/*    */   public static boolean isNullOrEmpty(@Nullable byte[] $$0) {
/* 48 */     return ($$0 == null || $$0.length == 0);
/*    */   }
/*    */   
/*    */   public static boolean isNullOrEmpty(@Nullable char[] $$0) {
/* 52 */     return ($$0 == null || $$0.length == 0);
/*    */   }
/*    */   
/*    */   public static boolean isNullOrEmpty(@Nullable short[] $$0) {
/* 56 */     return ($$0 == null || $$0.length == 0);
/*    */   }
/*    */   
/*    */   public static boolean isNullOrEmpty(@Nullable int[] $$0) {
/* 60 */     return ($$0 == null || $$0.length == 0);
/*    */   }
/*    */   
/*    */   public static boolean isNullOrEmpty(@Nullable long[] $$0) {
/* 64 */     return ($$0 == null || $$0.length == 0);
/*    */   }
/*    */   
/*    */   public static boolean isNullOrEmpty(@Nullable float[] $$0) {
/* 68 */     return ($$0 == null || $$0.length == 0);
/*    */   }
/*    */   
/*    */   public static boolean isNullOrEmpty(@Nullable double[] $$0) {
/* 72 */     return ($$0 == null || $$0.length == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\Optionull.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */