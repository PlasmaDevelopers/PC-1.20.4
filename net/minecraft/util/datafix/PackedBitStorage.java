/*    */ package net.minecraft.util.datafix;
/*    */ 
/*    */ import net.minecraft.util.Mth;
/*    */ import org.apache.commons.lang3.Validate;
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
/*    */ public class PackedBitStorage
/*    */ {
/*    */   private static final int BIT_TO_LONG_SHIFT = 6;
/*    */   private final long[] data;
/*    */   private final int bits;
/*    */   private final long mask;
/*    */   private final int size;
/*    */   
/*    */   public PackedBitStorage(int $$0, int $$1) {
/* 26 */     this($$0, $$1, new long[Mth.roundToward($$1 * $$0, 64) / 64]);
/*    */   }
/*    */   
/*    */   public PackedBitStorage(int $$0, int $$1, long[] $$2) {
/* 30 */     Validate.inclusiveBetween(1L, 32L, $$0);
/*    */     
/* 32 */     this.size = $$1;
/* 33 */     this.bits = $$0;
/* 34 */     this.data = $$2;
/* 35 */     this.mask = (1L << $$0) - 1L;
/*    */     
/* 37 */     int $$3 = Mth.roundToward($$1 * $$0, 64) / 64;
/* 38 */     if ($$2.length != $$3) {
/* 39 */       throw new IllegalArgumentException("Invalid length given for storage, got: " + $$2.length + " but expected: " + $$3);
/*    */     }
/*    */   }
/*    */   
/*    */   public void set(int $$0, int $$1) {
/* 44 */     Validate.inclusiveBetween(0L, (this.size - 1), $$0);
/* 45 */     Validate.inclusiveBetween(0L, this.mask, $$1);
/*    */     
/* 47 */     int $$2 = $$0 * this.bits;
/* 48 */     int $$3 = $$2 >> 6;
/* 49 */     int $$4 = ($$0 + 1) * this.bits - 1 >> 6;
/* 50 */     int $$5 = $$2 ^ $$3 << 6;
/*    */     
/* 52 */     this.data[$$3] = this.data[$$3] & (this.mask << $$5 ^ 0xFFFFFFFFFFFFFFFFL) | ($$1 & this.mask) << $$5;
/* 53 */     if ($$3 != $$4) {
/* 54 */       int $$6 = 64 - $$5;
/* 55 */       int $$7 = this.bits - $$6;
/* 56 */       this.data[$$4] = this.data[$$4] >>> $$7 << $$7 | ($$1 & this.mask) >> $$6;
/*    */     } 
/*    */   }
/*    */   
/*    */   public int get(int $$0) {
/* 61 */     Validate.inclusiveBetween(0L, (this.size - 1), $$0);
/*    */     
/* 63 */     int $$1 = $$0 * this.bits;
/* 64 */     int $$2 = $$1 >> 6;
/* 65 */     int $$3 = ($$0 + 1) * this.bits - 1 >> 6;
/* 66 */     int $$4 = $$1 ^ $$2 << 6;
/*    */     
/* 68 */     if ($$2 == $$3) {
/* 69 */       return (int)(this.data[$$2] >>> $$4 & this.mask);
/*    */     }
/* 71 */     int $$5 = 64 - $$4;
/* 72 */     return (int)((this.data[$$2] >>> $$4 | this.data[$$3] << $$5) & this.mask);
/*    */   }
/*    */ 
/*    */   
/*    */   public long[] getRaw() {
/* 77 */     return this.data;
/*    */   }
/*    */   
/*    */   public int getBits() {
/* 81 */     return this.bits;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\PackedBitStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */