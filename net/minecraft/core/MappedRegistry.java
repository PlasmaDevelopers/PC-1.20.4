/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class MappedRegistry<T>
/*     */   implements WritableRegistry<T> {
/*  36 */   private static final Logger LOGGER = LogUtils.getLogger(); final ResourceKey<? extends Registry<T>> key; private final Reference2IntMap<T> toId; private final Map<ResourceLocation, Holder.Reference<T>> byLocation; private final Map<ResourceKey<T>, Holder.Reference<T>> byKey;
/*     */   private final Map<T, Holder.Reference<T>> byValue;
/*     */   private final Map<T, Lifecycle> lifecycles;
/*     */   private Lifecycle registryLifecycle;
/*  40 */   private final ObjectList<Holder.Reference<T>> byId = (ObjectList<Holder.Reference<T>>)new ObjectArrayList(256); private volatile Map<TagKey<T>, HolderSet.Named<T>> tags; private boolean frozen; @Nullable
/*  41 */   private Map<T, Holder.Reference<T>> unregisteredIntrusiveHolders; public MappedRegistry(ResourceKey<? extends Registry<T>> $$0, Lifecycle $$1, boolean $$2) { this.toId = (Reference2IntMap<T>)Util.make(new Reference2IntOpenHashMap(), $$0 -> $$0.defaultReturnValue(-1));
/*     */     
/*  43 */     this.byLocation = new HashMap<>();
/*  44 */     this.byKey = new HashMap<>();
/*  45 */     this.byValue = new IdentityHashMap<>();
/*     */     
/*  47 */     this.lifecycles = new IdentityHashMap<>();
/*     */     
/*  49 */     this.tags = new IdentityHashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     this.lookup = new HolderLookup.RegistryLookup<T>()
/*     */       {
/*     */         public ResourceKey<? extends Registry<? extends T>> key() {
/*  67 */           return MappedRegistry.this.key;
/*     */         }
/*     */ 
/*     */         
/*     */         public Lifecycle registryLifecycle() {
/*  72 */           return MappedRegistry.this.registryLifecycle();
/*     */         }
/*     */ 
/*     */         
/*     */         public Optional<Holder.Reference<T>> get(ResourceKey<T> $$0) {
/*  77 */           return MappedRegistry.this.getHolder($$0);
/*     */         }
/*     */ 
/*     */         
/*     */         public Stream<Holder.Reference<T>> listElements() {
/*  82 */           return MappedRegistry.this.holders();
/*     */         }
/*     */ 
/*     */         
/*     */         public Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
/*  87 */           return MappedRegistry.this.getTag($$0);
/*     */         }
/*     */ 
/*     */         
/*     */         public Stream<HolderSet.Named<T>> listTags() {
/*  92 */           return MappedRegistry.this.getTags().map(Pair::getSecond);
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     this.key = $$0;
/* 102 */     this.registryLifecycle = $$1;
/* 103 */     if ($$2)
/* 104 */       this.unregisteredIntrusiveHolders = new IdentityHashMap<>();  }
/*     */    @Nullable
/*     */   private List<Holder.Reference<T>> holdersInOrder; private int nextId; private final HolderLookup.RegistryLookup<T> lookup; public MappedRegistry(ResourceKey<? extends Registry<T>> $$0, Lifecycle $$1) {
/*     */     this($$0, $$1, false);
/*     */   }
/*     */   public ResourceKey<? extends Registry<T>> key() {
/* 110 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 115 */     return "Registry[" + this.key + " (" + this.registryLifecycle + ")]";
/*     */   }
/*     */   
/*     */   private List<Holder.Reference<T>> holdersInOrder() {
/* 119 */     if (this.holdersInOrder == null) {
/* 120 */       this.holdersInOrder = this.byId.stream().filter(Objects::nonNull).toList();
/*     */     }
/* 122 */     return this.holdersInOrder;
/*     */   }
/*     */   
/*     */   private void validateWrite() {
/* 126 */     if (this.frozen) {
/* 127 */       throw new IllegalStateException("Registry is already frozen");
/*     */     }
/*     */   }
/*     */   
/*     */   private void validateWrite(ResourceKey<T> $$0) {
/* 132 */     if (this.frozen)
/* 133 */       throw new IllegalStateException("Registry is already frozen (trying to add key " + $$0 + ")"); 
/*     */   }
/*     */   
/*     */   public Holder.Reference<T> registerMapping(int $$0, ResourceKey<T> $$1, T $$2, Lifecycle $$3) {
/*     */     Holder.Reference<T> $$5;
/* 138 */     validateWrite($$1);
/* 139 */     Validate.notNull($$1);
/* 140 */     Validate.notNull($$2);
/*     */     
/* 142 */     if (this.byLocation.containsKey($$1.location())) {
/* 143 */       Util.pauseInIde(new IllegalStateException("Adding duplicate key '" + $$1 + "' to registry"));
/*     */     }
/*     */     
/* 146 */     if (this.byValue.containsKey($$2)) {
/* 147 */       Util.pauseInIde(new IllegalStateException("Adding duplicate value '" + $$2 + "' to registry"));
/*     */     }
/*     */ 
/*     */     
/* 151 */     if (this.unregisteredIntrusiveHolders != null) {
/*     */       
/* 153 */       Holder.Reference<T> $$4 = this.unregisteredIntrusiveHolders.remove($$2);
/* 154 */       if ($$4 == null) {
/* 155 */         throw new AssertionError("Missing intrusive holder for " + $$1 + ":" + $$2);
/*     */       }
/* 157 */       $$4.bindKey($$1);
/*     */     } else {
/*     */       
/* 160 */       $$5 = this.byKey.computeIfAbsent($$1, $$0 -> Holder.Reference.createStandAlone(holderOwner(), $$0));
/*     */     } 
/*     */     
/* 163 */     this.byKey.put($$1, $$5);
/* 164 */     this.byLocation.put($$1.location(), $$5);
/* 165 */     this.byValue.put($$2, $$5);
/*     */     
/* 167 */     this.byId.size(Math.max(this.byId.size(), $$0 + 1));
/* 168 */     this.byId.set($$0, $$5);
/* 169 */     this.toId.put($$2, $$0);
/* 170 */     if (this.nextId <= $$0) {
/* 171 */       this.nextId = $$0 + 1;
/*     */     }
/*     */     
/* 174 */     this.lifecycles.put($$2, $$3);
/* 175 */     this.registryLifecycle = this.registryLifecycle.add($$3);
/*     */     
/* 177 */     this.holdersInOrder = null;
/* 178 */     return $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   public Holder.Reference<T> register(ResourceKey<T> $$0, T $$1, Lifecycle $$2) {
/* 183 */     return registerMapping(this.nextId, $$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getKey(T $$0) {
/* 189 */     Holder.Reference<T> $$1 = this.byValue.get($$0);
/* 190 */     return ($$1 != null) ? $$1.key().location() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<ResourceKey<T>> getResourceKey(T $$0) {
/* 195 */     return Optional.<Holder.Reference>ofNullable(this.byValue.get($$0)).map(Holder.Reference::key);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId(@Nullable T $$0) {
/* 200 */     return this.toId.getInt($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T get(@Nullable ResourceKey<T> $$0) {
/* 206 */     return getValueFromNullable(this.byKey.get($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T byId(int $$0) {
/* 212 */     if ($$0 < 0 || $$0 >= this.byId.size()) {
/* 213 */       return null;
/*     */     }
/* 215 */     return getValueFromNullable((Holder.Reference<T>)this.byId.get($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<Holder.Reference<T>> getHolder(int $$0) {
/* 220 */     if ($$0 < 0 || $$0 >= this.byId.size()) {
/* 221 */       return Optional.empty();
/*     */     }
/* 223 */     return Optional.ofNullable((Holder.Reference<T>)this.byId.get($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<Holder.Reference<T>> getHolder(ResourceKey<T> $$0) {
/* 228 */     return Optional.ofNullable(this.byKey.get($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public Holder<T> wrapAsHolder(T $$0) {
/* 233 */     Holder.Reference<T> $$1 = this.byValue.get($$0);
/* 234 */     return ($$1 != null) ? $$1 : Holder.<T>direct($$0);
/*     */   }
/*     */   
/*     */   Holder.Reference<T> getOrCreateHolderOrThrow(ResourceKey<T> $$0) {
/* 238 */     return this.byKey.computeIfAbsent($$0, $$0 -> {
/*     */           if (this.unregisteredIntrusiveHolders != null) {
/*     */             throw new IllegalStateException("This registry can't create new holders without value");
/*     */           }
/*     */           validateWrite($$0);
/*     */           return Holder.Reference.createStandAlone(holderOwner(), $$0);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 249 */     return this.byKey.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Lifecycle lifecycle(T $$0) {
/* 254 */     return this.lifecycles.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Lifecycle registryLifecycle() {
/* 259 */     return this.registryLifecycle;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<T> iterator() {
/* 264 */     return Iterators.transform(holdersInOrder().iterator(), Holder::value);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T get(@Nullable ResourceLocation $$0) {
/* 270 */     Holder.Reference<T> $$1 = this.byLocation.get($$0);
/* 271 */     return getValueFromNullable($$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static <T> T getValueFromNullable(@Nullable Holder.Reference<T> $$0) {
/* 276 */     return ($$0 != null) ? $$0.value() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ResourceLocation> keySet() {
/* 281 */     return Collections.unmodifiableSet(this.byLocation.keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ResourceKey<T>> registryKeySet() {
/* 286 */     return Collections.unmodifiableSet(this.byKey.keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<ResourceKey<T>, T>> entrySet() {
/* 291 */     return Collections.unmodifiableSet(Maps.transformValues(this.byKey, Holder::value).entrySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<Holder.Reference<T>> holders() {
/* 296 */     return holdersInOrder().stream();
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<Pair<TagKey<T>, HolderSet.Named<T>>> getTags() {
/* 301 */     return this.tags.entrySet().stream().map($$0 -> Pair.of($$0.getKey(), $$0.getValue()));
/*     */   }
/*     */ 
/*     */   
/*     */   public HolderSet.Named<T> getOrCreateTag(TagKey<T> $$0) {
/* 306 */     HolderSet.Named<T> $$1 = this.tags.get($$0);
/* 307 */     if ($$1 == null) {
/* 308 */       $$1 = createTag($$0);
/*     */ 
/*     */       
/* 311 */       Map<TagKey<T>, HolderSet.Named<T>> $$2 = new IdentityHashMap<>(this.tags);
/* 312 */       $$2.put($$0, $$1);
/* 313 */       this.tags = $$2;
/*     */     } 
/* 315 */     return $$1;
/*     */   }
/*     */   
/*     */   private HolderSet.Named<T> createTag(TagKey<T> $$0) {
/* 319 */     return new HolderSet.Named<>(holderOwner(), $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<TagKey<T>> getTagNames() {
/* 324 */     return this.tags.keySet().stream();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 329 */     return this.byKey.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<Holder.Reference<T>> getRandom(RandomSource $$0) {
/* 334 */     return Util.getRandomSafe(holdersInOrder(), $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(ResourceLocation $$0) {
/* 339 */     return this.byLocation.containsKey($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(ResourceKey<T> $$0) {
/* 344 */     return this.byKey.containsKey($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Registry<T> freeze() {
/* 349 */     if (this.frozen) {
/* 350 */       return this;
/*     */     }
/* 352 */     this.frozen = true;
/* 353 */     this.byValue.forEach(($$0, $$1) -> $$1.bindValue($$0));
/*     */     
/* 355 */     List<ResourceLocation> $$0 = this.byKey.entrySet().stream().filter($$0 -> !((Holder.Reference)$$0.getValue()).isBound()).map($$0 -> ((ResourceKey)$$0.getKey()).location()).sorted().toList();
/* 356 */     if (!$$0.isEmpty()) {
/* 357 */       throw new IllegalStateException("Unbound values in registry " + key() + ": " + $$0);
/*     */     }
/* 359 */     if (this.unregisteredIntrusiveHolders != null) {
/* 360 */       if (!this.unregisteredIntrusiveHolders.isEmpty()) {
/* 361 */         throw new IllegalStateException("Some intrusive holders were not registered: " + this.unregisteredIntrusiveHolders.values());
/*     */       }
/* 363 */       this.unregisteredIntrusiveHolders = null;
/*     */     } 
/* 365 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Holder.Reference<T> createIntrusiveHolder(T $$0) {
/* 370 */     if (this.unregisteredIntrusiveHolders == null) {
/* 371 */       throw new IllegalStateException("This registry can't create intrusive holders");
/*     */     }
/* 373 */     validateWrite();
/* 374 */     return this.unregisteredIntrusiveHolders.computeIfAbsent($$0, $$0 -> Holder.Reference.createIntrusive(asLookup(), (T)$$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<HolderSet.Named<T>> getTag(TagKey<T> $$0) {
/* 379 */     return Optional.ofNullable(this.tags.get($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindTags(Map<TagKey<T>, List<Holder<T>>> $$0) {
/* 384 */     Map<Holder.Reference<T>, List<TagKey<T>>> $$1 = new IdentityHashMap<>();
/* 385 */     this.byKey.values().forEach($$1 -> $$0.put($$1, new ArrayList()));
/*     */     
/* 387 */     $$0.forEach(($$1, $$2) -> {
/*     */           for (Holder<T> $$3 : (Iterable<Holder<T>>)$$2) {
/*     */             if (!$$3.canSerializeIn(asLookup())) {
/*     */               throw new IllegalStateException("Can't create named set " + $$1 + " containing value " + $$3 + " from outside registry " + this);
/*     */             }
/*     */             
/*     */             if ($$3 instanceof Holder.Reference) {
/*     */               Holder.Reference<T> $$4 = (Holder.Reference<T>)$$3;
/*     */               
/*     */               ((List<TagKey>)$$0.get($$4)).add($$1);
/*     */               
/*     */               continue;
/*     */             } 
/*     */             throw new IllegalStateException("Found direct holder " + $$3 + " value in tag " + $$1);
/*     */           } 
/*     */         });
/* 403 */     Sets.SetView setView = Sets.difference(this.tags.keySet(), $$0.keySet());
/* 404 */     if (!setView.isEmpty()) {
/* 405 */       LOGGER.warn("Not all defined tags for registry {} are present in data pack: {}", key(), setView.stream().map($$0 -> $$0.location().toString()).sorted().collect(Collectors.joining(", ")));
/*     */     }
/*     */     
/* 408 */     Map<TagKey<T>, HolderSet.Named<T>> $$3 = new IdentityHashMap<>(this.tags);
/* 409 */     $$0.forEach(($$1, $$2) -> ((HolderSet.Named)$$0.computeIfAbsent($$1, this::createTag)).bind($$2));
/* 410 */     $$1.forEach(Holder.Reference::bindTags);
/* 411 */     this.tags = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetTags() {
/* 416 */     this.tags.values().forEach($$0 -> $$0.bind(List.of()));
/* 417 */     this.byKey.values().forEach($$0 -> $$0.bindTags(Set.of()));
/*     */   }
/*     */ 
/*     */   
/*     */   public HolderGetter<T> createRegistrationLookup() {
/* 422 */     validateWrite();
/* 423 */     return new HolderGetter<T>()
/*     */       {
/*     */         public Optional<Holder.Reference<T>> get(ResourceKey<T> $$0) {
/* 426 */           return Optional.of(getOrThrow($$0));
/*     */         }
/*     */ 
/*     */         
/*     */         public Holder.Reference<T> getOrThrow(ResourceKey<T> $$0) {
/* 431 */           return MappedRegistry.this.getOrCreateHolderOrThrow($$0);
/*     */         }
/*     */ 
/*     */         
/*     */         public Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
/* 436 */           return Optional.of(getOrThrow($$0));
/*     */         }
/*     */ 
/*     */         
/*     */         public HolderSet.Named<T> getOrThrow(TagKey<T> $$0) {
/* 441 */           return MappedRegistry.this.getOrCreateTag($$0);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public HolderOwner<T> holderOwner() {
/* 448 */     return this.lookup;
/*     */   }
/*     */ 
/*     */   
/*     */   public HolderLookup.RegistryLookup<T> asLookup() {
/* 453 */     return this.lookup;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\MappedRegistry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */