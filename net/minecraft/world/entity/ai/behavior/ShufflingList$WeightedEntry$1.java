/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Dynamic;
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
/*     */ class null
/*     */   implements Codec<ShufflingList.WeightedEntry<E>>
/*     */ {
/*     */   public <T> DataResult<Pair<ShufflingList.WeightedEntry<E>, T>> decode(DynamicOps<T> $$0, T $$1) {
/*  94 */     Dynamic<T> $$2 = new Dynamic($$0, $$1);
/*     */     
/*  96 */     Objects.requireNonNull(elementCodec); return $$2.get("data").flatMap(elementCodec::parse)
/*  97 */       .map($$1 -> new ShufflingList.WeightedEntry($$1, $$0.get("weight").asInt(1)))
/*  98 */       .map($$1 -> Pair.of($$1, $$0.empty()));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> DataResult<T> encode(ShufflingList.WeightedEntry<E> $$0, DynamicOps<T> $$1, T $$2) {
/* 103 */     return $$1.mapBuilder()
/* 104 */       .add("weight", $$1.createInt($$0.weight))
/* 105 */       .add("data", elementCodec.encodeStart($$1, $$0.data))
/* 106 */       .build($$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\ShufflingList$WeightedEntry$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */