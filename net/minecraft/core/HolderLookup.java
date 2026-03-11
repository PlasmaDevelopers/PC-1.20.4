/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.flag.FeatureElement;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface HolderLookup<T>
/*     */   extends HolderGetter<T>
/*     */ {
/*     */   Stream<Holder.Reference<T>> listElements();
/*     */   
/*     */   default Stream<ResourceKey<T>> listElementIds() {
/*  23 */     return listElements().map(Holder.Reference::key);
/*     */   }
/*     */   
/*     */   Stream<HolderSet.Named<T>> listTags();
/*     */   
/*     */   default Stream<TagKey<T>> listTagIds() {
/*  29 */     return listTags().map(HolderSet.Named::key);
/*     */   }
/*     */   
/*     */   public static interface RegistryLookup<T> extends HolderLookup<T>, HolderOwner<T> {
/*     */     ResourceKey<? extends Registry<? extends T>> key();
/*     */     
/*     */     Lifecycle registryLifecycle();
/*     */     
/*     */     default HolderLookup<T> filterFeatures(FeatureFlagSet $$0) {
/*  38 */       if (FeatureElement.FILTERED_REGISTRIES.contains(key())) {
/*  39 */         return filterElements($$1 -> ((FeatureElement)$$1).isEnabled($$0));
/*     */       }
/*     */       
/*  42 */       return this;
/*     */     }
/*     */     
/*     */     public static abstract class Delegate<T>
/*     */       implements RegistryLookup<T> {
/*     */       protected abstract HolderLookup.RegistryLookup<T> parent();
/*     */       
/*     */       public ResourceKey<? extends Registry<? extends T>> key() {
/*  50 */         return parent().key();
/*     */       }
/*     */ 
/*     */       
/*     */       public Lifecycle registryLifecycle() {
/*  55 */         return parent().registryLifecycle();
/*     */       }
/*     */ 
/*     */       
/*     */       public Optional<Holder.Reference<T>> get(ResourceKey<T> $$0) {
/*  60 */         return parent().get($$0);
/*     */       }
/*     */ 
/*     */       
/*     */       public Stream<Holder.Reference<T>> listElements() {
/*  65 */         return parent().listElements();
/*     */       }
/*     */ 
/*     */       
/*     */       public Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
/*  70 */         return parent().get($$0);
/*     */       }
/*     */       
/*     */       public Stream<HolderSet.Named<T>> listTags()
/*     */       {
/*  75 */         return parent().listTags(); } } } public static abstract class Delegate<T> implements RegistryLookup<T> { protected abstract HolderLookup.RegistryLookup<T> parent(); public ResourceKey<? extends Registry<? extends T>> key() { return parent().key(); } public Lifecycle registryLifecycle() { return parent().registryLifecycle(); } public Stream<HolderSet.Named<T>> listTags() { return parent().listTags(); }
/*     */      public Optional<Holder.Reference<T>> get(ResourceKey<T> $$0) {
/*     */       return parent().get($$0);
/*     */     } public Stream<Holder.Reference<T>> listElements() {
/*     */       return parent().listElements();
/*     */     } public Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
/*     */       return parent().get($$0);
/*     */     } } public static class Delegate<T> implements HolderLookup<T> { protected final HolderLookup<T> parent;
/*     */     public Delegate(HolderLookup<T> $$0) {
/*  84 */       this.parent = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Optional<Holder.Reference<T>> get(ResourceKey<T> $$0) {
/*  89 */       return this.parent.get($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<Holder.Reference<T>> listElements() {
/*  94 */       return this.parent.listElements();
/*     */     }
/*     */ 
/*     */     
/*     */     public Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
/*  99 */       return this.parent.get($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<HolderSet.Named<T>> listTags() {
/* 104 */       return this.parent.listTags();
/*     */     } }
/*     */ 
/*     */   
/*     */   default HolderLookup<T> filterElements(final Predicate<T> filter) {
/* 109 */     return new Delegate<T>(this)
/*     */       {
/*     */         public Optional<Holder.Reference<T>> get(ResourceKey<T> $$0) {
/* 112 */           return this.parent.get($$0).filter($$1 -> $$0.test($$1.value()));
/*     */         }
/*     */ 
/*     */         
/*     */         public Stream<Holder.Reference<T>> listElements() {
/* 117 */           return this.parent.listElements().filter($$1 -> $$0.test($$1.value()));
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Provider
/*     */   {
/*     */     default <T> HolderLookup.RegistryLookup<T> lookupOrThrow(ResourceKey<? extends Registry<? extends T>> $$0) {
/* 128 */       return (HolderLookup.RegistryLookup<T>)lookup($$0).orElseThrow(() -> new IllegalStateException("Registry " + $$0.location() + " not found"));
/*     */     }
/*     */     
/*     */     default HolderGetter.Provider asGetterLookup() {
/* 132 */       return new HolderGetter.Provider()
/*     */         {
/*     */           public <T> Optional<HolderGetter<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$0) {
/* 135 */             return HolderLookup.Provider.this.<T>lookup($$0).map($$0 -> $$0);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     static Provider create(Stream<HolderLookup.RegistryLookup<?>> $$0) {
/* 141 */       final Map<ResourceKey<? extends Registry<?>>, HolderLookup.RegistryLookup<?>> map = $$0.collect(Collectors.toUnmodifiableMap(HolderLookup.RegistryLookup::key, $$0 -> $$0));
/* 142 */       return new Provider()
/*     */         {
/*     */           public Stream<ResourceKey<? extends Registry<?>>> listRegistries() {
/* 145 */             return map.keySet().stream();
/*     */           }
/*     */ 
/*     */           
/*     */           public <T> Optional<HolderLookup.RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$0)
/*     */           {
/* 151 */             return Optional.ofNullable((HolderLookup.RegistryLookup<T>)map.get($$0)); } }; } Stream<ResourceKey<? extends Registry<?>>> listRegistries(); <T> Optional<HolderLookup.RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> param1ResourceKey); } class null implements HolderGetter.Provider { public <T> Optional<HolderGetter<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$0) { return HolderLookup.Provider.this.<T>lookup($$0).map($$0 -> $$0); } } class null implements Provider { public <T> Optional<HolderLookup.RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$0) { return Optional.ofNullable((HolderLookup.RegistryLookup<T>)map.get($$0)); }
/*     */ 
/*     */     
/*     */     public Stream<ResourceKey<? extends Registry<?>>> listRegistries() {
/*     */       return map.keySet().stream();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\HolderLookup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */