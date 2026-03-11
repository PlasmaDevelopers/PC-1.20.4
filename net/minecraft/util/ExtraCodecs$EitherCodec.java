/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Objects;
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
/*     */ public final class EitherCodec<F, S>
/*     */   implements Codec<Either<F, S>>
/*     */ {
/*     */   private final Codec<F> first;
/*     */   private final Codec<S> second;
/*     */   
/*     */   public EitherCodec(Codec<F> $$0, Codec<S> $$1) {
/* 301 */     this.first = $$0;
/* 302 */     this.second = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> DataResult<Pair<Either<F, S>, T>> decode(DynamicOps<T> $$0, T $$1) {
/* 307 */     DataResult<Pair<Either<F, S>, T>> $$2 = this.first.decode($$0, $$1).map($$0 -> $$0.mapFirst(Either::left));
/* 308 */     if ($$2.error().isEmpty()) {
/* 309 */       return $$2;
/*     */     }
/* 311 */     DataResult<Pair<Either<F, S>, T>> $$3 = this.second.decode($$0, $$1).map($$0 -> $$0.mapFirst(Either::right));
/* 312 */     if ($$3.error().isEmpty()) {
/* 313 */       return $$3;
/*     */     }
/*     */     
/* 316 */     return $$2.apply2(($$0, $$1) -> $$1, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> DataResult<T> encode(Either<F, S> $$0, DynamicOps<T> $$1, T $$2) {
/* 321 */     return (DataResult<T>)$$0.map($$2 -> this.first.encode($$2, $$0, $$1), $$2 -> this.second.encode($$2, $$0, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 329 */     if (this == $$0) {
/* 330 */       return true;
/*     */     }
/* 332 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 333 */       return false;
/*     */     }
/* 335 */     EitherCodec<?, ?> $$1 = (EitherCodec<?, ?>)$$0;
/* 336 */     return (Objects.equals(this.first, $$1.first) && Objects.equals(this.second, $$1.second));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 341 */     return Objects.hash(new Object[] { this.first, this.second });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 346 */     return "EitherCodec[" + this.first + ", " + this.second + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ExtraCodecs$EitherCodec.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */