/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
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
/*     */ 
/*     */ 
/*     */ public final class PoorMansPalettedContainer
/*     */ {
/*     */   private static final long SIZE_BITS = 4L;
/*     */   private final List<? extends Dynamic<?>> palette;
/*     */   private final long[] data;
/*     */   private final int bits;
/*     */   private final long mask;
/*     */   private final int valuesPerLong;
/*     */   
/*     */   public PoorMansPalettedContainer(List<? extends Dynamic<?>> $$0, long[] $$1) {
/* 168 */     this.palette = $$0;
/* 169 */     this.data = $$1;
/*     */     
/* 171 */     this.bits = Math.max(4, ChunkHeightAndBiomeFix.ceillog2($$0.size()));
/* 172 */     this.mask = (1L << this.bits) - 1L;
/* 173 */     this.valuesPerLong = (char)(64 / this.bits);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Dynamic<?> get(int $$0, int $$1, int $$2) {
/* 178 */     int $$3 = this.palette.size();
/* 179 */     if ($$3 < 1) {
/* 180 */       return null;
/*     */     }
/* 182 */     if ($$3 == 1) {
/* 183 */       return this.palette.get(0);
/*     */     }
/*     */     
/* 186 */     int $$4 = getIndex($$0, $$1, $$2);
/* 187 */     int $$5 = $$4 / this.valuesPerLong;
/* 188 */     if ($$5 < 0 || $$5 >= this.data.length) {
/* 189 */       return null;
/*     */     }
/* 191 */     long $$6 = this.data[$$5];
/* 192 */     int $$7 = ($$4 - $$5 * this.valuesPerLong) * this.bits;
/* 193 */     int $$8 = (int)($$6 >> $$7 & this.mask);
/* 194 */     if ($$8 < 0 || $$8 >= $$3) {
/* 195 */       return null;
/*     */     }
/* 197 */     return this.palette.get($$8);
/*     */   }
/*     */   
/*     */   private int getIndex(int $$0, int $$1, int $$2) {
/* 201 */     return ($$1 << 4 | $$2) << 4 | $$0;
/*     */   }
/*     */   
/*     */   public List<? extends Dynamic<?>> palette() {
/* 205 */     return this.palette;
/*     */   }
/*     */   
/*     */   public long[] data() {
/* 209 */     return this.data;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ChunkProtoTickListFix$PoorMansPalettedContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */