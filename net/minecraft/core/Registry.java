/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.Keyable;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.RandomSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Registry<T>
/*     */   extends Keyable, IdMap<T>
/*     */ {
/*     */   default Codec<T> byNameCodec() {
/*  33 */     Codec<T> $$0 = ResourceLocation.CODEC.flatXmap($$0 -> (DataResult)Optional.<T>ofNullable(get($$0)).map(DataResult::success).orElseGet(()), $$0 -> (DataResult)getResourceKey((T)$$0).map(ResourceKey::location).map(DataResult::success).orElseGet(()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  38 */     Codec<T> $$1 = ExtraCodecs.idResolverCodec($$0 -> getResourceKey((T)$$0).isPresent() ? getId((T)$$0) : -1, this::byId, -1);
/*     */     
/*  40 */     return ExtraCodecs.overrideLifecycle(ExtraCodecs.orCompressed($$0, $$1), this::lifecycle, this::lifecycle);
/*     */   }
/*     */   
/*     */   default Codec<Holder<T>> holderByNameCodec() {
/*  44 */     Codec<Holder<T>> $$0 = ResourceLocation.CODEC.flatXmap($$0 -> (DataResult)getHolder(ResourceKey.create(key(), $$0)).<DataResult>map(DataResult::success).orElseGet(()), $$0 -> (DataResult)$$0.unwrapKey().map(ResourceKey::location).map(DataResult::success).orElseGet(()));
/*     */ 
/*     */ 
/*     */     
/*  48 */     return ExtraCodecs.overrideLifecycle($$0, $$0 -> lifecycle($$0.value()), $$0 -> lifecycle($$0.value()));
/*     */   }
/*     */ 
/*     */   
/*     */   default <U> Stream<U> keys(DynamicOps<U> $$0) {
/*  53 */     return keySet().stream().map($$1 -> $$0.createString($$1.toString()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Optional<T> getOptional(@Nullable ResourceLocation $$0) {
/*  78 */     return Optional.ofNullable(get($$0));
/*     */   }
/*     */   
/*     */   default Optional<T> getOptional(@Nullable ResourceKey<T> $$0) {
/*  82 */     return Optional.ofNullable(get($$0));
/*     */   }
/*     */   
/*     */   default T getOrThrow(ResourceKey<T> $$0) {
/*  86 */     T $$1 = get($$0);
/*  87 */     if ($$1 == null) {
/*  88 */       throw new IllegalStateException("Missing key in " + key() + ": " + $$0);
/*     */     }
/*  90 */     return $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Stream<T> stream() {
/* 102 */     return StreamSupport.stream(spliterator(), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T> T register(Registry<? super T> $$0, String $$1, T $$2) {
/* 110 */     return register($$0, new ResourceLocation($$1), $$2);
/*     */   }
/*     */   
/*     */   static <V, T extends V> T register(Registry<V> $$0, ResourceLocation $$1, T $$2) {
/* 114 */     return register($$0, ResourceKey.create($$0.key(), $$1), $$2);
/*     */   }
/*     */   
/*     */   static <V, T extends V> T register(Registry<V> $$0, ResourceKey<V> $$1, T $$2) {
/* 118 */     ((WritableRegistry<V>)$$0).register($$1, (V)$$2, Lifecycle.stable());
/* 119 */     return $$2;
/*     */   }
/*     */   
/*     */   static <T> Holder.Reference<T> registerForHolder(Registry<T> $$0, ResourceKey<T> $$1, T $$2) {
/* 123 */     return ((WritableRegistry<T>)$$0).register($$1, $$2, Lifecycle.stable());
/*     */   }
/*     */   
/*     */   static <T> Holder.Reference<T> registerForHolder(Registry<T> $$0, ResourceLocation $$1, T $$2) {
/* 127 */     return registerForHolder($$0, ResourceKey.create($$0.key(), $$1), $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Holder.Reference<T> getHolderOrThrow(ResourceKey<T> $$0) {
/* 146 */     return getHolder($$0).<Throwable>orElseThrow(() -> new IllegalStateException("Missing key in " + key() + ": " + $$0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Iterable<Holder<T>> getTagOrEmpty(TagKey<T> $$0) {
/* 154 */     return (Iterable<Holder<T>>)DataFixUtils.orElse(getTag($$0), List.of());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default IdMap<Holder<T>> asHolderIdMap() {
/* 168 */     return (IdMap)new IdMap<Holder<Holder<T>>>()
/*     */       {
/*     */         public int getId(Holder<T> $$0) {
/* 171 */           return Registry.this.getId($$0.value());
/*     */         }
/*     */ 
/*     */         
/*     */         @Nullable
/*     */         public Holder<T> byId(int $$0) {
/* 177 */           return Registry.this.getHolder($$0).orElse(null);
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 182 */           return Registry.this.size();
/*     */         }
/*     */ 
/*     */         
/*     */         public Iterator<Holder<T>> iterator() {
/* 187 */           return Registry.this.holders().map($$0 -> $$0).iterator();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default HolderLookup.RegistryLookup<T> asTagAddingLookup() {
/* 197 */     return new HolderLookup.RegistryLookup.Delegate<T>()
/*     */       {
/*     */         protected HolderLookup.RegistryLookup<T> parent() {
/* 200 */           return Registry.this.asLookup();
/*     */         }
/*     */ 
/*     */         
/*     */         public Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
/* 205 */           return Optional.of(getOrThrow($$0));
/*     */         }
/*     */ 
/*     */         
/*     */         public HolderSet.Named<T> getOrThrow(TagKey<T> $$0) {
/* 210 */           return Registry.this.getOrCreateTag($$0);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   ResourceKey<? extends Registry<T>> key();
/*     */   
/*     */   @Nullable
/*     */   ResourceLocation getKey(T paramT);
/*     */   
/*     */   Optional<ResourceKey<T>> getResourceKey(T paramT);
/*     */   
/*     */   int getId(@Nullable T paramT);
/*     */   
/*     */   @Nullable
/*     */   T get(@Nullable ResourceKey<T> paramResourceKey);
/*     */   
/*     */   @Nullable
/*     */   T get(@Nullable ResourceLocation paramResourceLocation);
/*     */   
/*     */   Lifecycle lifecycle(T paramT);
/*     */   
/*     */   Lifecycle registryLifecycle();
/*     */   
/*     */   Set<ResourceLocation> keySet();
/*     */   
/*     */   Set<Map.Entry<ResourceKey<T>, T>> entrySet();
/*     */   
/*     */   Set<ResourceKey<T>> registryKeySet();
/*     */   
/*     */   Optional<Holder.Reference<T>> getRandom(RandomSource paramRandomSource);
/*     */   
/*     */   boolean containsKey(ResourceLocation paramResourceLocation);
/*     */   
/*     */   boolean containsKey(ResourceKey<T> paramResourceKey);
/*     */   
/*     */   Registry<T> freeze();
/*     */   
/*     */   Holder.Reference<T> createIntrusiveHolder(T paramT);
/*     */   
/*     */   Optional<Holder.Reference<T>> getHolder(int paramInt);
/*     */   
/*     */   Optional<Holder.Reference<T>> getHolder(ResourceKey<T> paramResourceKey);
/*     */   
/*     */   Holder<T> wrapAsHolder(T paramT);
/*     */   
/*     */   Stream<Holder.Reference<T>> holders();
/*     */   
/*     */   Optional<HolderSet.Named<T>> getTag(TagKey<T> paramTagKey);
/*     */   
/*     */   HolderSet.Named<T> getOrCreateTag(TagKey<T> paramTagKey);
/*     */   
/*     */   Stream<Pair<TagKey<T>, HolderSet.Named<T>>> getTags();
/*     */   
/*     */   Stream<TagKey<T>> getTagNames();
/*     */   
/*     */   void resetTags();
/*     */   
/*     */   void bindTags(Map<TagKey<T>, List<Holder<T>>> paramMap);
/*     */   
/*     */   HolderOwner<T> holderOwner();
/*     */   
/*     */   HolderLookup.RegistryLookup<T> asLookup();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\Registry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */