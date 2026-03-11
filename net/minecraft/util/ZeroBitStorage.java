/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.function.IntConsumer;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class ZeroBitStorage
/*    */   implements BitStorage {
/*  9 */   public static final long[] RAW = new long[0];
/*    */   
/*    */   private final int size;
/*    */   
/*    */   public ZeroBitStorage(int $$0) {
/* 14 */     this.size = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAndSet(int $$0, int $$1) {
/* 19 */     Validate.inclusiveBetween(0L, (this.size - 1), $$0);
/* 20 */     Validate.inclusiveBetween(0L, 0L, $$1);
/* 21 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(int $$0, int $$1) {
/* 26 */     Validate.inclusiveBetween(0L, (this.size - 1), $$0);
/* 27 */     Validate.inclusiveBetween(0L, 0L, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int get(int $$0) {
/* 32 */     Validate.inclusiveBetween(0L, (this.size - 1), $$0);
/* 33 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public long[] getRaw() {
/* 38 */     return RAW;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSize() {
/* 43 */     return this.size;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBits() {
/* 48 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void getAll(IntConsumer $$0) {
/* 53 */     for (int $$1 = 0; $$1 < this.size; $$1++) {
/* 54 */       $$0.accept(0);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void unpack(int[] $$0) {
/* 60 */     Arrays.fill($$0, 0, this.size, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public BitStorage copy() {
/* 65 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ZeroBitStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */