/*     */ package net.minecraft.core;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Provider
/*     */ {
/*     */   default <T> HolderLookup.RegistryLookup<T> lookupOrThrow(ResourceKey<? extends Registry<? extends T>> $$0) {
/* 128 */     return (HolderLookup.RegistryLookup<T>)lookup($$0).orElseThrow(() -> new IllegalStateException("Registry " + $$0.location() + " not found"));
/*     */   }
/*     */   
/*     */   default HolderGetter.Provider asGetterLookup() {
/* 132 */     return new HolderGetter.Provider()
/*     */       {
/*     */         public <T> Optional<HolderGetter<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$0) {
/* 135 */           return HolderLookup.Provider.this.<T>lookup($$0).map($$0 -> $$0);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   static Provider create(Stream<HolderLookup.RegistryLookup<?>> $$0) {
/* 141 */     final Map<ResourceKey<? extends Registry<?>>, HolderLookup.RegistryLookup<?>> map = $$0.collect(Collectors.toUnmodifiableMap(HolderLookup.RegistryLookup::key, $$0 -> $$0));
/* 142 */     return new Provider()
/*     */       {
/*     */         public Stream<ResourceKey<? extends Registry<?>>> listRegistries() {
/* 145 */           return map.keySet().stream();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public <T> Optional<HolderLookup.RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$0) {
/* 151 */           return Optional.ofNullable((HolderLookup.RegistryLookup<T>)map.get($$0));
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   Stream<ResourceKey<? extends Registry<?>>> listRegistries();
/*     */   
/*     */   <T> Optional<HolderLookup.RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> paramResourceKey);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\HolderLookup$Provider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */