/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ public class RunningTrimmedMean {
/*    */   private final long[] values;
/*    */   private int count;
/*    */   private int cursor;
/*    */   
/*    */   public RunningTrimmedMean(int $$0) {
/*  9 */     this.values = new long[$$0];
/*    */   }
/*    */   
/*    */   public long registerValueAndGetMean(long $$0) {
/* 13 */     if (this.count < this.values.length) {
/* 14 */       this.count++;
/*    */     }
/* 16 */     this.values[this.cursor] = $$0;
/* 17 */     this.cursor = (this.cursor + 1) % this.values.length;
/*    */     
/* 19 */     long $$1 = Long.MAX_VALUE;
/* 20 */     long $$2 = Long.MIN_VALUE;
/*    */     
/* 22 */     long $$3 = 0L;
/* 23 */     for (int $$4 = 0; $$4 < this.count; $$4++) {
/* 24 */       long $$5 = this.values[$$4];
/* 25 */       $$3 += $$5;
/* 26 */       $$1 = Math.min($$1, $$5);
/* 27 */       $$2 = Math.max($$2, $$5);
/*    */     } 
/*    */     
/* 30 */     if (this.count > 2) {
/* 31 */       $$3 -= $$1 + $$2;
/* 32 */       return $$3 / (this.count - 2);
/*    */     } 
/*    */     
/* 35 */     if ($$3 > 0L) {
/* 36 */       return this.count / $$3;
/*    */     }
/* 38 */     return 0L;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\RunningTrimmedMean.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */