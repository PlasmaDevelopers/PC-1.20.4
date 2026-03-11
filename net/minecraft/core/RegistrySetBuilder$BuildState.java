/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class BuildState
/*     */   extends Record
/*     */ {
/*     */   final RegistrySetBuilder.CompositeOwner owner;
/*     */   final RegistrySetBuilder.UniversalLookup lookup;
/*     */   final Map<ResourceLocation, HolderGetter<?>> registries;
/*     */   final Map<ResourceKey<?>, RegistrySetBuilder.RegisteredValue<?>> registeredValues;
/*     */   final List<RuntimeException> errors;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/core/RegistrySetBuilder$BuildState;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #115	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$BuildState;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/RegistrySetBuilder$BuildState;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #115	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$BuildState;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/core/RegistrySetBuilder$BuildState;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #115	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/core/RegistrySetBuilder$BuildState;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   private BuildState(RegistrySetBuilder.CompositeOwner $$0, RegistrySetBuilder.UniversalLookup $$1, Map<ResourceLocation, HolderGetter<?>> $$2, Map<ResourceKey<?>, RegistrySetBuilder.RegisteredValue<?>> $$3, List<RuntimeException> $$4) {
/* 115 */     this.owner = $$0; this.lookup = $$1; this.registries = $$2; this.registeredValues = $$3; this.errors = $$4; } public RegistrySetBuilder.CompositeOwner owner() { return this.owner; } public RegistrySetBuilder.UniversalLookup lookup() { return this.lookup; } public Map<ResourceLocation, HolderGetter<?>> registries() { return this.registries; } public Map<ResourceKey<?>, RegistrySetBuilder.RegisteredValue<?>> registeredValues() { return this.registeredValues; } public List<RuntimeException> errors() { return this.errors; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BuildState create(RegistryAccess $$0, Stream<ResourceKey<? extends Registry<?>>> $$1) {
/* 123 */     RegistrySetBuilder.CompositeOwner $$2 = new RegistrySetBuilder.CompositeOwner();
/* 124 */     List<RuntimeException> $$3 = new ArrayList<>();
/* 125 */     RegistrySetBuilder.UniversalLookup $$4 = new RegistrySetBuilder.UniversalLookup($$2);
/*     */     
/* 127 */     ImmutableMap.Builder<ResourceLocation, HolderGetter<?>> $$5 = ImmutableMap.builder();
/* 128 */     $$0.registries().forEach($$1 -> $$0.put($$1.key().location(), RegistrySetBuilder.wrapContextLookup($$1.value().asLookup())));
/* 129 */     $$1.forEach($$2 -> $$0.put($$2.location(), $$1));
/*     */     
/* 131 */     return new BuildState($$2, $$4, (Map<ResourceLocation, HolderGetter<?>>)$$5
/*     */ 
/*     */         
/* 134 */         .build(), new HashMap<>(), $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> BootstapContext<T> bootstapContext() {
/* 141 */     return new BootstapContext<T>()
/*     */       {
/*     */         public Holder.Reference<T> register(ResourceKey<T> $$0, T $$1, Lifecycle $$2) {
/* 144 */           RegistrySetBuilder.RegisteredValue<?> $$3 = RegistrySetBuilder.BuildState.this.registeredValues.put($$0, new RegistrySetBuilder.RegisteredValue($$1, $$2));
/* 145 */           if ($$3 != null) {
/* 146 */             RegistrySetBuilder.BuildState.this.errors.add(new IllegalStateException("Duplicate registration for " + $$0 + ", new=" + $$1 + ", old=" + $$3.value));
/*     */           }
/* 148 */           return RegistrySetBuilder.BuildState.this.lookup.getOrCreate($$0);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> $$0) {
/* 154 */           return (HolderGetter<S>)RegistrySetBuilder.BuildState.this.registries.getOrDefault($$0.location(), RegistrySetBuilder.BuildState.this.lookup);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public void reportUnclaimedRegisteredValues() {
/* 160 */     this.registeredValues.forEach(($$0, $$1) -> this.errors.add(new IllegalStateException("Orpaned value " + $$1.value + " for key " + $$0)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reportNotCollectedHolders() {
/* 166 */     for (ResourceKey<Object> $$0 : this.lookup.holders.keySet()) {
/* 167 */       this.errors.add(new IllegalStateException("Unreferenced key: " + $$0));
/*     */     }
/*     */   }
/*     */   
/*     */   public void throwOnError() {
/* 172 */     if (!this.errors.isEmpty()) {
/* 173 */       IllegalStateException $$0 = new IllegalStateException("Errors during registry creation");
/* 174 */       for (RuntimeException $$1 : this.errors) {
/* 175 */         $$0.addSuppressed($$1);
/*     */       }
/* 177 */       throw $$0;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistrySetBuilder$BuildState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */