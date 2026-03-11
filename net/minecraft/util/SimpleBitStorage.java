/*     */ package net.minecraft.util;
/*     */ 
/*     */ import java.util.function.IntConsumer;
/*     */ import javax.annotation.Nullable;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ public class SimpleBitStorage
/*     */   implements BitStorage {
/*     */   public static class InitializationException extends RuntimeException {
/*     */     InitializationException(String $$0) {
/*  11 */       super($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  18 */   private static final int[] MAGIC = new int[] { -1, -1, 0, Integer.MIN_VALUE, 0, 0, 1431655765, 1431655765, 0, Integer.MIN_VALUE, 0, 1, 858993459, 858993459, 0, 715827882, 715827882, 0, 613566756, 613566756, 0, Integer.MIN_VALUE, 0, 2, 477218588, 477218588, 0, 429496729, 429496729, 0, 390451572, 390451572, 0, 357913941, 357913941, 0, 330382099, 330382099, 0, 306783378, 306783378, 0, 286331153, 286331153, 0, Integer.MIN_VALUE, 0, 3, 252645135, 252645135, 0, 238609294, 238609294, 0, 226050910, 226050910, 0, 214748364, 214748364, 0, 204522252, 204522252, 0, 195225786, 195225786, 0, 186737708, 186737708, 0, 178956970, 178956970, 0, 171798691, 171798691, 0, 165191049, 165191049, 0, 159072862, 159072862, 0, 153391689, 153391689, 0, 148102320, 148102320, 0, 143165576, 143165576, 0, 138547332, 138547332, 0, Integer.MIN_VALUE, 0, 4, 130150524, 130150524, 0, 126322567, 126322567, 0, 122713351, 122713351, 0, 119304647, 119304647, 0, 116080197, 116080197, 0, 113025455, 113025455, 0, 110127366, 110127366, 0, 107374182, 107374182, 0, 104755299, 104755299, 0, 102261126, 102261126, 0, 99882960, 99882960, 0, 97612893, 97612893, 0, 95443717, 95443717, 0, 93368854, 93368854, 0, 91382282, 91382282, 0, 89478485, 89478485, 0, 87652393, 87652393, 0, 85899345, 85899345, 0, 84215045, 84215045, 0, 82595524, 82595524, 0, 81037118, 81037118, 0, 79536431, 79536431, 0, 78090314, 78090314, 0, 76695844, 76695844, 0, 75350303, 75350303, 0, 74051160, 74051160, 0, 72796055, 72796055, 0, 71582788, 71582788, 0, 70409299, 70409299, 0, 69273666, 69273666, 0, 68174084, 68174084, 0, Integer.MIN_VALUE, 0, 5 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long[] data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int bits;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long mask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int size;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int valuesPerLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int divideMul;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int divideAdd;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int divideShift;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleBitStorage(int $$0, int $$1, int[] $$2) {
/*  97 */     this($$0, $$1);
/*     */     
/*  99 */     int $$3 = 0;
/*     */     int $$4;
/* 101 */     for ($$4 = 0; $$4 <= $$1 - this.valuesPerLong; $$4 += this.valuesPerLong) {
/* 102 */       long $$5 = 0L;
/* 103 */       for (int $$6 = this.valuesPerLong - 1; $$6 >= 0; $$6--) {
/* 104 */         $$5 <<= $$0;
/* 105 */         $$5 |= $$2[$$4 + $$6] & this.mask;
/*     */       } 
/* 107 */       this.data[$$3++] = $$5;
/*     */     } 
/*     */     
/* 110 */     int $$7 = $$1 - $$4;
/* 111 */     if ($$7 > 0) {
/* 112 */       long $$8 = 0L;
/* 113 */       for (int $$9 = $$7 - 1; $$9 >= 0; $$9--) {
/* 114 */         $$8 <<= $$0;
/* 115 */         $$8 |= $$2[$$4 + $$9] & this.mask;
/*     */       } 
/* 117 */       this.data[$$3] = $$8;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SimpleBitStorage(int $$0, int $$1) {
/* 122 */     this($$0, $$1, (long[])null);
/*     */   }
/*     */   
/*     */   public SimpleBitStorage(int $$0, int $$1, @Nullable long[] $$2) {
/* 126 */     Validate.inclusiveBetween(1L, 32L, $$0);
/*     */     
/* 128 */     this.size = $$1;
/* 129 */     this.bits = $$0;
/* 130 */     this.mask = (1L << $$0) - 1L;
/* 131 */     this.valuesPerLong = (char)(64 / $$0);
/*     */     
/* 133 */     int $$3 = 3 * (this.valuesPerLong - 1);
/* 134 */     this.divideMul = MAGIC[$$3 + 0];
/* 135 */     this.divideAdd = MAGIC[$$3 + 1];
/* 136 */     this.divideShift = MAGIC[$$3 + 2];
/*     */     
/* 138 */     int $$4 = ($$1 + this.valuesPerLong - 1) / this.valuesPerLong;
/* 139 */     if ($$2 != null) {
/* 140 */       if ($$2.length != $$4) {
/* 141 */         throw new InitializationException("Invalid length given for storage, got: " + $$2.length + " but expected: " + $$4);
/*     */       }
/* 143 */       this.data = $$2;
/*     */     } else {
/* 145 */       this.data = new long[$$4];
/*     */     } 
/*     */   }
/*     */   
/*     */   private int cellIndex(int $$0) {
/* 150 */     long $$1 = Integer.toUnsignedLong(this.divideMul);
/* 151 */     long $$2 = Integer.toUnsignedLong(this.divideAdd);
/* 152 */     return (int)($$0 * $$1 + $$2 >> 32L >> this.divideShift);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAndSet(int $$0, int $$1) {
/* 157 */     Validate.inclusiveBetween(0L, (this.size - 1), $$0);
/* 158 */     Validate.inclusiveBetween(0L, this.mask, $$1);
/*     */     
/* 160 */     int $$2 = cellIndex($$0);
/* 161 */     long $$3 = this.data[$$2];
/* 162 */     int $$4 = ($$0 - $$2 * this.valuesPerLong) * this.bits;
/*     */     
/* 164 */     int $$5 = (int)($$3 >> $$4 & this.mask);
/* 165 */     this.data[$$2] = $$3 & (this.mask << $$4 ^ 0xFFFFFFFFFFFFFFFFL) | ($$1 & this.mask) << $$4;
/*     */     
/* 167 */     return $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int $$0, int $$1) {
/* 172 */     Validate.inclusiveBetween(0L, (this.size - 1), $$0);
/* 173 */     Validate.inclusiveBetween(0L, this.mask, $$1);
/*     */     
/* 175 */     int $$2 = cellIndex($$0);
/* 176 */     long $$3 = this.data[$$2];
/* 177 */     int $$4 = ($$0 - $$2 * this.valuesPerLong) * this.bits;
/*     */     
/* 179 */     this.data[$$2] = $$3 & (this.mask << $$4 ^ 0xFFFFFFFFFFFFFFFFL) | ($$1 & this.mask) << $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int get(int $$0) {
/* 184 */     Validate.inclusiveBetween(0L, (this.size - 1), $$0);
/*     */     
/* 186 */     int $$1 = cellIndex($$0);
/* 187 */     long $$2 = this.data[$$1];
/* 188 */     int $$3 = ($$0 - $$1 * this.valuesPerLong) * this.bits;
/*     */     
/* 190 */     return (int)($$2 >> $$3 & this.mask);
/*     */   }
/*     */ 
/*     */   
/*     */   public long[] getRaw() {
/* 195 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 200 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBits() {
/* 205 */     return this.bits;
/*     */   }
/*     */ 
/*     */   
/*     */   public void getAll(IntConsumer $$0) {
/* 210 */     int $$1 = 0;
/* 211 */     for (long $$2 : this.data) {
/* 212 */       for (int $$3 = 0; $$3 < this.valuesPerLong; $$3++) {
/* 213 */         $$0.accept((int)($$2 & this.mask));
/* 214 */         $$2 >>= this.bits;
/* 215 */         if (++$$1 >= this.size) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void unpack(int[] $$0) {
/* 224 */     int $$1 = this.data.length;
/*     */     
/* 226 */     int $$2 = 0;
/* 227 */     for (int $$3 = 0; $$3 < $$1 - 1; $$3++) {
/* 228 */       long $$4 = this.data[$$3];
/* 229 */       for (int $$5 = 0; $$5 < this.valuesPerLong; $$5++) {
/* 230 */         $$0[$$2 + $$5] = (int)($$4 & this.mask);
/* 231 */         $$4 >>= this.bits;
/*     */       } 
/* 233 */       $$2 += this.valuesPerLong;
/*     */     } 
/*     */     
/* 236 */     int $$6 = this.size - $$2;
/* 237 */     if ($$6 > 0) {
/* 238 */       long $$7 = this.data[$$1 - 1];
/* 239 */       for (int $$8 = 0; $$8 < $$6; $$8++) {
/* 240 */         $$0[$$2 + $$8] = (int)($$7 & this.mask);
/* 241 */         $$7 >>= this.bits;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BitStorage copy() {
/* 248 */     return new SimpleBitStorage(this.bits, this.size, (long[])this.data.clone());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\SimpleBitStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */