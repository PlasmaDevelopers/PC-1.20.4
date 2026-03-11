/*    */ package net.minecraft.util;
/*    */ 
/*    */ public class SampleLogger
/*    */ {
/*    */   public static final int CAPACITY = 240;
/*  6 */   private final long[] samples = new long[240];
/*    */   private int start;
/*    */   private int size;
/*    */   
/*    */   public void logSample(long $$0) {
/* 11 */     int $$1 = wrapIndex(this.start + this.size);
/* 12 */     this.samples[$$1] = $$0;
/*    */     
/* 14 */     if (this.size < 240) {
/* 15 */       this.size++;
/*    */     } else {
/* 17 */       this.start = wrapIndex(this.start + 1);
/*    */     } 
/*    */   }
/*    */   
/*    */   public int capacity() {
/* 22 */     return this.samples.length;
/*    */   }
/*    */   
/*    */   public int size() {
/* 26 */     return this.size;
/*    */   }
/*    */   
/*    */   public long get(int $$0) {
/* 30 */     if ($$0 < 0 || $$0 >= this.size) {
/* 31 */       throw new IndexOutOfBoundsException("" + $$0 + " out of bounds for length " + $$0);
/*    */     }
/* 33 */     return this.samples[wrapIndex(this.start + $$0)];
/*    */   }
/*    */   
/*    */   private int wrapIndex(int $$0) {
/* 37 */     return $$0 % 240;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 41 */     this.start = 0;
/* 42 */     this.size = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\SampleLogger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */