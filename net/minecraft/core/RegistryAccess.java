/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public interface RegistryAccess
/*     */   extends HolderLookup.Provider {
/*  16 */   public static final Logger LOGGER = LogUtils.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default <T> Optional<HolderLookup.RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$0) {
/*  22 */     return registry($$0).map(Registry::asLookup);
/*     */   }
/*     */   
/*     */   default <E> Registry<E> registryOrThrow(ResourceKey<? extends Registry<? extends E>> $$0) {
/*  26 */     return (Registry<E>)registry($$0).orElseThrow(() -> new IllegalStateException("Missing registry: " + $$0));
/*     */   }
/*     */   public static final class RegistryEntry<T> extends Record { private final ResourceKey<? extends Registry<T>> key; final Registry<T> value;
/*  29 */     public RegistryEntry(ResourceKey<? extends Registry<T>> $$0, Registry<T> $$1) { this.key = $$0; this.value = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/core/RegistryAccess$RegistryEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #29	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistryAccess$RegistryEntry;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*  29 */       //   0	7	0	this	Lnet/minecraft/core/RegistryAccess$RegistryEntry<TT;>; } public ResourceKey<? extends Registry<T>> key() { return this.key; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/RegistryAccess$RegistryEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #29	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistryAccess$RegistryEntry;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistryAccess$RegistryEntry<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/core/RegistryAccess$RegistryEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #29	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/core/RegistryAccess$RegistryEntry;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*  29 */       //   0	8	0	this	Lnet/minecraft/core/RegistryAccess$RegistryEntry<TT;>; } public Registry<T> value() { return this.value; }
/*     */      private static <T, R extends Registry<? extends T>> RegistryEntry<T> fromMapEntry(Map.Entry<? extends ResourceKey<? extends Registry<?>>, R> $$0) {
/*  31 */       return fromUntyped($$0.getKey(), (Registry)$$0.getValue());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private static <T> RegistryEntry<T> fromUntyped(ResourceKey<? extends Registry<?>> $$0, Registry<?> $$1) {
/*  37 */       return new RegistryEntry<>((ResourceKey)$$0, (Registry)$$1);
/*     */     }
/*     */     
/*     */     private RegistryEntry<T> freeze() {
/*  41 */       return new RegistryEntry(this.key, this.value.freeze());
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Stream<ResourceKey<? extends Registry<?>>> listRegistries() {
/*  49 */     return registries().map(RegistryEntry::key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ImmutableRegistryAccess
/*     */     implements RegistryAccess
/*     */   {
/*     */     private final Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> registries;
/*     */ 
/*     */     
/*     */     public ImmutableRegistryAccess(List<? extends Registry<?>> $$0) {
/*  61 */       this.registries = (Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>>)$$0.stream().collect(Collectors.toUnmodifiableMap(Registry::key, $$0 -> $$0));
/*     */     }
/*     */     
/*     */     public ImmutableRegistryAccess(Map<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> $$0) {
/*  65 */       this.registries = Map.copyOf($$0);
/*     */     }
/*     */     
/*     */     public ImmutableRegistryAccess(Stream<RegistryAccess.RegistryEntry<?>> $$0) {
/*  69 */       this.registries = $$0.collect(ImmutableMap.toImmutableMap(RegistryAccess.RegistryEntry::key, RegistryAccess.RegistryEntry::value));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <E> Optional<Registry<E>> registry(ResourceKey<? extends Registry<? extends E>> $$0) {
/*  75 */       return Optional.<Registry>ofNullable(this.registries.get($$0)).map($$0 -> $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<RegistryAccess.RegistryEntry<?>> registries() {
/*  80 */       return this.registries.entrySet().stream().map(RegistryAccess.RegistryEntry::fromMapEntry);
/*     */     }
/*     */   }
/*     */   
/*     */   static Frozen fromRegistryOfRegistries(final Registry<? extends Registry<?>> registries) {
/*  85 */     return new Frozen()
/*     */       {
/*     */         public <T> Optional<Registry<T>> registry(ResourceKey<? extends Registry<? extends T>> $$0)
/*     */         {
/*  89 */           Registry<Registry<T>> $$1 = registries;
/*  90 */           return (Optional)$$1.getOptional($$0);
/*     */         }
/*     */ 
/*     */         
/*     */         public Stream<RegistryAccess.RegistryEntry<?>> registries() {
/*  95 */           return registries.entrySet().stream().map(RegistryAccess.RegistryEntry::fromMapEntry);
/*     */         }
/*     */ 
/*     */         
/*     */         public RegistryAccess.Frozen freeze() {
/* 100 */           return this;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/* 105 */   public static final Frozen EMPTY = (new ImmutableRegistryAccess(Map.of())).freeze();
/*     */   
/*     */   default Frozen freeze() {
/*     */     class FrozenAccess extends ImmutableRegistryAccess implements Frozen {
/*     */       protected FrozenAccess(Stream<RegistryAccess.RegistryEntry<?>> $$1) {
/* 110 */         super($$1);
/*     */       }
/*     */     };
/*     */     
/* 114 */     return new FrozenAccess(registries().map(RegistryEntry::freeze));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default Lifecycle allRegistriesLifecycle() {
/* 124 */     return registries().<Lifecycle>map($$0 -> $$0.value.registryLifecycle()).reduce(Lifecycle.stable(), Lifecycle::add);
/*     */   }
/*     */   
/*     */   <E> Optional<Registry<E>> registry(ResourceKey<? extends Registry<? extends E>> paramResourceKey);
/*     */   
/*     */   Stream<RegistryEntry<?>> registries();
/*     */   
/*     */   public static interface Frozen extends RegistryAccess {}
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistryAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */