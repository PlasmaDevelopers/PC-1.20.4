/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.MapDecoder;
/*     */ import com.mojang.serialization.MapEncoder;
/*     */ import com.mojang.serialization.MapLike;
/*     */ import com.mojang.serialization.RecordBuilder;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FuzzyCodec<T>
/*     */   extends MapCodec<T>
/*     */ {
/*     */   private final List<MapCodec<? extends T>> codecs;
/*     */   private final Function<T, MapEncoder<? extends T>> encoderGetter;
/*     */   
/*     */   public FuzzyCodec(List<MapCodec<? extends T>> $$0, Function<T, MapEncoder<? extends T>> $$1) {
/*  81 */     this.codecs = $$0;
/*  82 */     this.encoderGetter = $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <S> DataResult<T> decode(DynamicOps<S> $$0, MapLike<S> $$1) {
/*  88 */     for (MapDecoder<? extends T> $$2 : this.codecs) {
/*  89 */       DataResult<? extends T> $$3 = $$2.decode($$0, $$1);
/*  90 */       if ($$3.result().isPresent()) {
/*  91 */         return (DataResult)$$3;
/*     */       }
/*     */     } 
/*     */     
/*  95 */     return DataResult.error(() -> "No matching codec found");
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> RecordBuilder<S> encode(T $$0, DynamicOps<S> $$1, RecordBuilder<S> $$2) {
/* 100 */     MapEncoder<T> $$3 = (MapEncoder<T>)this.encoderGetter.apply($$0);
/* 101 */     return $$3.encode($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <S> Stream<S> keys(DynamicOps<S> $$0) {
/* 108 */     return this.codecs.stream().flatMap($$1 -> $$1.keys($$0)).distinct();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 113 */     return "FuzzyCodec[" + this.codecs + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ComponentSerialization$FuzzyCodec.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */