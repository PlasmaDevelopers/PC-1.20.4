/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import com.mojang.serialization.MapLike;
/*     */ import com.mojang.serialization.codecs.BaseMapCodec;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StrictUnboundedMapCodec<K, V>
/*     */   extends Record
/*     */   implements Codec<Map<K, V>>, BaseMapCodec<K, V>
/*     */ {
/*     */   private final Codec<K> keyCodec;
/*     */   private final Codec<V> elementCodec;
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #358	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	7	0	this	Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec<TK;TV;>;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #358	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	8	0	this	Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec<TK;TV;>;
/*     */   }
/*     */   
/*     */   public StrictUnboundedMapCodec(Codec<K> $$0, Codec<V> $$1) {
/* 358 */     this.keyCodec = $$0; this.elementCodec = $$1; } public Codec<K> keyCodec() { return this.keyCodec; } public Codec<V> elementCodec() { return this.elementCodec; }
/*     */   
/*     */   public <T> DataResult<Map<K, V>> decode(DynamicOps<T> $$0, MapLike<T> $$1) {
/* 361 */     ImmutableMap.Builder<K, V> $$2 = ImmutableMap.builder();
/*     */     
/* 363 */     for (Pair<T, T> $$3 : (Iterable<Pair<T, T>>)$$1.entries().toList()) {
/* 364 */       DataResult<K> $$4 = keyCodec().parse($$0, $$3.getFirst());
/* 365 */       DataResult<V> $$5 = elementCodec().parse($$0, $$3.getSecond());
/* 366 */       DataResult<Pair<K, V>> $$6 = $$4.apply2stable(Pair::of, $$5);
/* 367 */       if ($$6.error().isPresent()) {
/* 368 */         return DataResult.error(() -> {
/*     */               String $$4;
/*     */               DataResult.PartialResult<Pair<K, V>> $$2 = $$0.error().get();
/*     */               if ($$1.result().isPresent()) {
/*     */                 String $$3 = "Map entry '" + $$1.result().get() + "' : " + $$2.message();
/*     */               } else {
/*     */                 $$4 = $$2.message();
/*     */               } 
/*     */               return $$4;
/*     */             });
/*     */       }
/* 379 */       if ($$6.result().isPresent()) {
/* 380 */         Pair<K, V> $$7 = $$6.result().get();
/* 381 */         $$2.put($$7.getFirst(), $$7.getSecond()); continue;
/*     */       } 
/* 383 */       return DataResult.error(() -> "Empty or invalid map contents are not allowed");
/*     */     } 
/*     */     
/* 386 */     ImmutableMap immutableMap = $$2.build();
/* 387 */     return DataResult.success(immutableMap);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> DataResult<Pair<Map<K, V>, T>> decode(DynamicOps<T> $$0, T $$1) {
/* 392 */     return $$0.getMap($$1).setLifecycle(Lifecycle.stable()).flatMap($$1 -> decode($$0, $$1)).map($$1 -> Pair.of($$1, $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> DataResult<T> encode(Map<K, V> $$0, DynamicOps<T> $$1, T $$2) {
/* 397 */     return encode($$0, $$1, $$1.mapBuilder()).build($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 402 */     return "StrictUnboundedMapCodec[" + this.keyCodec + " -> " + this.elementCodec + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ExtraCodecs$StrictUnboundedMapCodec.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */