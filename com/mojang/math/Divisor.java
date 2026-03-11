/*    */ package com.mojang.math;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Divisor
/*    */   implements IntIterator
/*    */ {
/*    */   private final int denominator;
/*    */   private final int quotient;
/*    */   private final int mod;
/*    */   private int returnedParts;
/*    */   private int remainder;
/*    */   
/*    */   public Divisor(int $$0, int $$1) {
/* 35 */     this.denominator = $$1;
/* 36 */     if ($$1 > 0) {
/* 37 */       this.quotient = $$0 / $$1;
/* 38 */       this.mod = $$0 % $$1;
/*    */     } else {
/* 40 */       this.quotient = 0;
/* 41 */       this.mod = 0;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 47 */     return (this.returnedParts < this.denominator);
/*    */   }
/*    */ 
/*    */   
/*    */   public int nextInt() {
/* 52 */     if (!hasNext()) {
/* 53 */       throw new NoSuchElementException();
/*    */     }
/* 55 */     int $$0 = this.quotient;
/* 56 */     this.remainder += this.mod;
/* 57 */     if (this.remainder >= this.denominator) {
/* 58 */       this.remainder -= this.denominator;
/* 59 */       $$0++;
/*    */     } 
/* 61 */     this.returnedParts++;
/* 62 */     return $$0;
/*    */   }
/*    */   
/*    */   @VisibleForTesting
/*    */   public static Iterable<Integer> asIterable(int $$0, int $$1) {
/* 67 */     return () -> new Divisor($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\math\Divisor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */