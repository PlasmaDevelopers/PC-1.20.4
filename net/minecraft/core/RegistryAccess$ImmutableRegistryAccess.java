/*    */ package net.minecraft.core;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImmutableRegistryAccess
/*    */   implements RegistryAccess
/*    */ {
/*    */   private final Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> registries;
/*    */   
/*    */   public ImmutableRegistryAccess(List<? extends Registry<?>> $$0) {
/* 61 */     this.registries = (Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>>)$$0.stream().collect(Collectors.toUnmodifiableMap(Registry::key, $$0 -> $$0));
/*    */   }
/*    */   
/*    */   public ImmutableRegistryAccess(Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> $$0) {
/* 65 */     this.registries = Map.copyOf($$0);
/*    */   }
/*    */   
/*    */   public ImmutableRegistryAccess(Stream<RegistryAccess.RegistryEntry<?>> $$0) {
/* 69 */     this.registries = $$0.collect(ImmutableMap.toImmutableMap(RegistryAccess.RegistryEntry::key, RegistryAccess.RegistryEntry::value));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <E> Optional<Registry<E>> registry(ResourceKey<? extends Registry<? extends E>> $$0) {
/* 75 */     return Optional.<Registry>ofNullable(this.registries.get($$0)).map($$0 -> $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<RegistryAccess.RegistryEntry<?>> registries() {
/* 80 */     return this.registries.entrySet().stream().map(RegistryAccess.RegistryEntry::fromMapEntry);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistryAccess$ImmutableRegistryAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */