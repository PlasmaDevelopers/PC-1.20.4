/*     */ package net.minecraft.world.level.lighting;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.HashCommon;
/*     */ import it.unimi.dsi.fastutil.longs.Long2LongLinkedOpenHashMap;
/*     */ import java.util.NoSuchElementException;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InternalMap
/*     */   extends Long2LongLinkedOpenHashMap
/*     */ {
/*  20 */   private static final int X_BITS = Mth.log2(60000000);
/*  21 */   private static final int Z_BITS = Mth.log2(60000000);
/*  22 */   private static final int Y_BITS = 64 - X_BITS - Z_BITS;
/*     */   
/*     */   private static final int Y_OFFSET = 0;
/*  25 */   private static final int Z_OFFSET = Y_BITS;
/*  26 */   private static final int X_OFFSET = Y_BITS + Z_BITS;
/*  27 */   private static final long OUTER_MASK = 3L << X_OFFSET | 0x3L | 3L << Z_OFFSET;
/*     */   
/*  29 */   private int lastPos = -1;
/*     */   private long lastOuterKey;
/*     */   private final int minSize;
/*     */   
/*     */   public InternalMap(int $$0, float $$1) {
/*  34 */     super($$0, $$1);
/*  35 */     this.minSize = $$0;
/*     */   }
/*     */   
/*     */   static long getOuterKey(long $$0) {
/*  39 */     return $$0 & (OUTER_MASK ^ 0xFFFFFFFFFFFFFFFFL);
/*     */   }
/*     */   
/*     */   static int getInnerKey(long $$0) {
/*  43 */     int $$1 = (int)($$0 >>> X_OFFSET & 0x3L);
/*  44 */     int $$2 = (int)($$0 >>> 0L & 0x3L);
/*  45 */     int $$3 = (int)($$0 >>> Z_OFFSET & 0x3L);
/*  46 */     return $$1 << 4 | $$3 << 2 | $$2;
/*     */   }
/*     */   
/*     */   static long getFullKey(long $$0, int $$1) {
/*  50 */     $$0 |= ($$1 >>> 4 & 0x3) << X_OFFSET;
/*  51 */     $$0 |= ($$1 >>> 2 & 0x3) << Z_OFFSET;
/*  52 */     $$0 |= ($$1 >>> 0 & 0x3) << 0L;
/*  53 */     return $$0;
/*     */   }
/*     */   public boolean addBit(long $$0) {
/*     */     int $$6;
/*  57 */     long $$1 = getOuterKey($$0);
/*  58 */     int $$2 = getInnerKey($$0);
/*  59 */     long $$3 = 1L << $$2;
/*     */     
/*  61 */     if ($$1 == 0L) {
/*  62 */       if (this.containsNullKey) {
/*  63 */         return replaceBit(this.n, $$3);
/*     */       }
/*  65 */       this.containsNullKey = true;
/*  66 */       int $$4 = this.n;
/*     */     } else {
/*  68 */       if (this.lastPos != -1 && $$1 == this.lastOuterKey) {
/*  69 */         return replaceBit(this.lastPos, $$3);
/*     */       }
/*  71 */       long[] $$5 = this.key;
/*  72 */       $$6 = (int)HashCommon.mix($$1) & this.mask;
/*  73 */       long $$7 = $$5[$$6];
/*  74 */       while ($$7 != 0L) {
/*  75 */         if ($$7 == $$1) {
/*  76 */           this.lastPos = $$6;
/*  77 */           this.lastOuterKey = $$1;
/*  78 */           return replaceBit($$6, $$3);
/*     */         } 
/*  80 */         $$6 = $$6 + 1 & this.mask;
/*  81 */         $$7 = $$5[$$6];
/*     */       } 
/*     */     } 
/*  84 */     this.key[$$6] = $$1;
/*  85 */     this.value[$$6] = $$3;
/*  86 */     if (this.size == 0) {
/*  87 */       this.first = this.last = $$6;
/*     */       
/*  89 */       this.link[$$6] = -1L;
/*     */     } else {
/*  91 */       this.link[this.last] = this.link[this.last] ^ (this.link[this.last] ^ $$6 & 0xFFFFFFFFL) & 0xFFFFFFFFL;
/*  92 */       this.link[$$6] = (this.last & 0xFFFFFFFFL) << 32L | 0xFFFFFFFFL;
/*  93 */       this.last = $$6;
/*     */     } 
/*  95 */     if (this.size++ >= this.maxFill) {
/*  96 */       rehash(HashCommon.arraySize(this.size + 1, this.f));
/*     */     }
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   private boolean replaceBit(int $$0, long $$1) {
/* 102 */     boolean $$2 = ((this.value[$$0] & $$1) != 0L);
/* 103 */     this.value[$$0] = this.value[$$0] | $$1;
/* 104 */     return $$2;
/*     */   }
/*     */   
/*     */   public boolean removeBit(long $$0) {
/* 108 */     long $$1 = getOuterKey($$0);
/* 109 */     int $$2 = getInnerKey($$0);
/* 110 */     long $$3 = 1L << $$2;
/* 111 */     if ($$1 == 0L) {
/* 112 */       if (this.containsNullKey) {
/* 113 */         return removeFromNullEntry($$3);
/*     */       }
/* 115 */       return false;
/*     */     } 
/* 117 */     if (this.lastPos != -1 && $$1 == this.lastOuterKey) {
/* 118 */       return removeFromEntry(this.lastPos, $$3);
/*     */     }
/* 120 */     long[] $$4 = this.key;
/* 121 */     int $$5 = (int)HashCommon.mix($$1) & this.mask;
/* 122 */     long $$6 = $$4[$$5];
/*     */     while (true) {
/* 124 */       if ($$6 == 0L) {
/* 125 */         return false;
/*     */       }
/* 127 */       if ($$1 == $$6) {
/* 128 */         this.lastPos = $$5;
/* 129 */         this.lastOuterKey = $$1;
/* 130 */         return removeFromEntry($$5, $$3);
/*     */       } 
/* 132 */       $$5 = $$5 + 1 & this.mask;
/* 133 */       $$6 = $$4[$$5];
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean removeFromNullEntry(long $$0) {
/* 138 */     if ((this.value[this.n] & $$0) == 0L) {
/* 139 */       return false;
/*     */     }
/* 141 */     this.value[this.n] = this.value[this.n] & ($$0 ^ 0xFFFFFFFFFFFFFFFFL);
/* 142 */     if (this.value[this.n] != 0L) {
/* 143 */       return true;
/*     */     }
/* 145 */     this.containsNullKey = false;
/* 146 */     this.size--;
/* 147 */     fixPointers(this.n);
/* 148 */     if (this.size < this.maxFill / 4 && this.n > 16) {
/* 149 */       rehash(this.n / 2);
/*     */     }
/* 151 */     return true;
/*     */   }
/*     */   
/*     */   private boolean removeFromEntry(int $$0, long $$1) {
/* 155 */     if ((this.value[$$0] & $$1) == 0L) {
/* 156 */       return false;
/*     */     }
/* 158 */     this.value[$$0] = this.value[$$0] & ($$1 ^ 0xFFFFFFFFFFFFFFFFL);
/* 159 */     if (this.value[$$0] != 0L) {
/* 160 */       return true;
/*     */     }
/* 162 */     this.lastPos = -1;
/* 163 */     this.size--;
/* 164 */     fixPointers($$0);
/* 165 */     shiftKeys($$0);
/* 166 */     if (this.size < this.maxFill / 4 && this.n > 16) {
/* 167 */       rehash(this.n / 2);
/*     */     }
/* 169 */     return true;
/*     */   }
/*     */   
/*     */   public long removeFirstBit() {
/* 173 */     if (this.size == 0) {
/* 174 */       throw new NoSuchElementException();
/*     */     }
/* 176 */     int $$0 = this.first;
/* 177 */     long $$1 = this.key[$$0];
/* 178 */     int $$2 = Long.numberOfTrailingZeros(this.value[$$0]);
/* 179 */     this.value[$$0] = this.value[$$0] & (1L << $$2 ^ 0xFFFFFFFFFFFFFFFFL);
/* 180 */     if (this.value[$$0] == 0L) {
/* 181 */       removeFirstLong();
/* 182 */       this.lastPos = -1;
/*     */     } 
/* 184 */     return getFullKey($$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rehash(int $$0) {
/* 189 */     if ($$0 > this.minSize)
/* 190 */       super.rehash($$0); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\SpatialLongSet$InternalMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */