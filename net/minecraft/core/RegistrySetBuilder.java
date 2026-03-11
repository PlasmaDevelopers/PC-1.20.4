/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ 
/*     */ 
/*     */ public class RegistrySetBuilder
/*     */ {
/*     */   private static class LazyHolder<T>
/*     */     extends Holder.Reference<T>
/*     */   {
/*     */     @Nullable
/*     */     Supplier<T> supplier;
/*     */     
/*     */     protected LazyHolder(HolderOwner<T> $$0, @Nullable ResourceKey<T> $$1) {
/*  35 */       super(Holder.Reference.Type.STAND_ALONE, $$0, $$1, null);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void bindValue(T $$0) {
/*  40 */       super.bindValue($$0);
/*  41 */       this.supplier = null;
/*     */     }
/*     */ 
/*     */     
/*     */     public T value() {
/*  46 */       if (this.supplier != null) {
/*  47 */         bindValue(this.supplier.get());
/*     */       }
/*  49 */       return super.value();
/*     */     }
/*     */   }
/*     */   
/*     */   private static abstract class EmptyTagLookup<T> implements HolderGetter<T> {
/*     */     protected final HolderOwner<T> owner;
/*     */     
/*     */     protected EmptyTagLookup(HolderOwner<T> $$0) {
/*  57 */       this.owner = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
/*  62 */       return Optional.of(HolderSet.emptyNamed(this.owner, $$0));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CompositeOwner
/*     */     implements HolderOwner<Object>
/*     */   {
/*  69 */     private final Set<HolderOwner<?>> owners = Sets.newIdentityHashSet();
/*     */ 
/*     */     
/*     */     public boolean canSerializeIn(HolderOwner<Object> $$0) {
/*  73 */       return this.owners.contains($$0);
/*     */     }
/*     */     
/*     */     public void add(HolderOwner<?> $$0) {
/*  77 */       this.owners.add($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> HolderOwner<T> cast() {
/*  82 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class UniversalLookup extends EmptyTagLookup<Object> {
/*  87 */     final Map<ResourceKey<Object>, Holder.Reference<Object>> holders = new HashMap<>();
/*     */     
/*     */     public UniversalLookup(HolderOwner<Object> $$0) {
/*  90 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Optional<Holder.Reference<Object>> get(ResourceKey<Object> $$0) {
/*  95 */       return Optional.of(getOrCreate($$0));
/*     */     }
/*     */ 
/*     */     
/*     */     <T> Holder.Reference<T> getOrCreate(ResourceKey<T> $$0) {
/* 100 */       return (Holder.Reference<T>)this.holders.computeIfAbsent($$0, $$0 -> Holder.Reference.createStandAlone(this.owner, $$0));
/*     */     }
/*     */   }
/*     */   
/*     */   static <T> HolderGetter<T> wrapContextLookup(final HolderLookup.RegistryLookup<T> original) {
/* 105 */     return new EmptyTagLookup<T>(original)
/*     */       {
/*     */         public Optional<Holder.Reference<T>> get(ResourceKey<T> $$0) {
/* 108 */           return original.get($$0);
/*     */         }
/*     */       };
/*     */   }
/*     */   private static final class RegisteredValue<T> extends Record { final T value; private final Lifecycle lifecycle;
/* 113 */     RegisteredValue(T $$0, Lifecycle $$1) { this.value = $$0; this.lifecycle = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/core/RegistrySetBuilder$RegisteredValue;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #113	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegisteredValue;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegisteredValue<TT;>; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/RegistrySetBuilder$RegisteredValue;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #113	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegisteredValue;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegisteredValue<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/core/RegistrySetBuilder$RegisteredValue;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #113	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegisteredValue;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 113 */       //   0	8	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegisteredValue<TT;>; } public T value() { return this.value; } public Lifecycle lifecycle() { return this.lifecycle; }
/*     */      } private static final class BuildState extends Record { final RegistrySetBuilder.CompositeOwner owner; final RegistrySetBuilder.UniversalLookup lookup; final Map<ResourceLocation, HolderGetter<?>> registries; final Map<ResourceKey<?>, RegistrySetBuilder.RegisteredValue<?>> registeredValues; final List<RuntimeException> errors;
/* 115 */     private BuildState(RegistrySetBuilder.CompositeOwner $$0, RegistrySetBuilder.UniversalLookup $$1, Map<ResourceLocation, HolderGetter<?>> $$2, Map<ResourceKey<?>, RegistrySetBuilder.RegisteredValue<?>> $$3, List<RuntimeException> $$4) { this.owner = $$0; this.lookup = $$1; this.registries = $$2; this.registeredValues = $$3; this.errors = $$4; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/core/RegistrySetBuilder$BuildState;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #115	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$BuildState; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/RegistrySetBuilder$BuildState;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #115	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$BuildState; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/core/RegistrySetBuilder$BuildState;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #115	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/core/RegistrySetBuilder$BuildState;
/* 115 */       //   0	8	1	$$0	Ljava/lang/Object; } public RegistrySetBuilder.CompositeOwner owner() { return this.owner; } public RegistrySetBuilder.UniversalLookup lookup() { return this.lookup; } public Map<ResourceLocation, HolderGetter<?>> registries() { return this.registries; } public Map<ResourceKey<?>, RegistrySetBuilder.RegisteredValue<?>> registeredValues() { return this.registeredValues; } public List<RuntimeException> errors() { return this.errors; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static BuildState create(RegistryAccess $$0, Stream<ResourceKey<? extends Registry<?>>> $$1) {
/* 123 */       RegistrySetBuilder.CompositeOwner $$2 = new RegistrySetBuilder.CompositeOwner();
/* 124 */       List<RuntimeException> $$3 = new ArrayList<>();
/* 125 */       RegistrySetBuilder.UniversalLookup $$4 = new RegistrySetBuilder.UniversalLookup($$2);
/*     */       
/* 127 */       ImmutableMap.Builder<ResourceLocation, HolderGetter<?>> $$5 = ImmutableMap.builder();
/* 128 */       $$0.registries().forEach($$1 -> $$0.put($$1.key().location(), RegistrySetBuilder.wrapContextLookup($$1.value().asLookup())));
/* 129 */       $$1.forEach($$2 -> $$0.put($$2.location(), $$1));
/*     */       
/* 131 */       return new BuildState($$2, $$4, (Map<ResourceLocation, HolderGetter<?>>)$$5
/*     */ 
/*     */           
/* 134 */           .build(), new HashMap<>(), $$3);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> BootstapContext<T> bootstapContext() {
/* 141 */       return new BootstapContext<T>()
/*     */         {
/*     */           public Holder.Reference<T> register(ResourceKey<T> $$0, T $$1, Lifecycle $$2) {
/* 144 */             RegistrySetBuilder.RegisteredValue<?> $$3 = RegistrySetBuilder.BuildState.this.registeredValues.put($$0, new RegistrySetBuilder.RegisteredValue($$1, $$2));
/* 145 */             if ($$3 != null) {
/* 146 */               RegistrySetBuilder.BuildState.this.errors.add(new IllegalStateException("Duplicate registration for " + $$0 + ", new=" + $$1 + ", old=" + $$3.value));
/*     */             }
/* 148 */             return RegistrySetBuilder.BuildState.this.lookup.getOrCreate($$0);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> $$0) {
/* 154 */             return (HolderGetter<S>)RegistrySetBuilder.BuildState.this.registries.getOrDefault($$0.location(), RegistrySetBuilder.BuildState.this.lookup);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public void reportUnclaimedRegisteredValues() {
/* 160 */       this.registeredValues.forEach(($$0, $$1) -> this.errors.add(new IllegalStateException("Orpaned value " + $$1.value + " for key " + $$0)));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void reportNotCollectedHolders() {
/* 166 */       for (ResourceKey<Object> $$0 : this.lookup.holders.keySet()) {
/* 167 */         this.errors.add(new IllegalStateException("Unreferenced key: " + $$0));
/*     */       }
/*     */     }
/*     */     
/*     */     public void throwOnError() {
/* 172 */       if (!this.errors.isEmpty()) {
/* 173 */         IllegalStateException $$0 = new IllegalStateException("Errors during registry creation");
/* 174 */         for (RuntimeException $$1 : this.errors) {
/* 175 */           $$0.addSuppressed($$1);
/*     */         }
/* 177 */         throw $$0;
/*     */       }  } }
/*     */    class null implements BootstapContext<T> {
/*     */     public Holder.Reference<T> register(ResourceKey<T> $$0, T $$1, Lifecycle $$2) { RegistrySetBuilder.RegisteredValue<?> $$3 = RegistrySetBuilder.BuildState.this.registeredValues.put($$0, new RegistrySetBuilder.RegisteredValue($$1, $$2)); if ($$3 != null)
/*     */         RegistrySetBuilder.BuildState.this.errors.add(new IllegalStateException("Duplicate registration for " + $$0 + ", new=" + $$1 + ", old=" + $$3.value));  return RegistrySetBuilder.BuildState.this.lookup.getOrCreate($$0); } public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> $$0) { return (HolderGetter<S>)RegistrySetBuilder.BuildState.this.registries.getOrDefault($$0.location(), RegistrySetBuilder.BuildState.this.lookup); }
/* 182 */   } private static final class ValueAndHolder<T> extends Record { private final RegistrySetBuilder.RegisteredValue<T> value; ValueAndHolder(RegistrySetBuilder.RegisteredValue<T> $$0, Optional<Holder.Reference<T>> $$1) { this.value = $$0; this.holder = $$1; } private final Optional<Holder.Reference<T>> holder; public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/core/RegistrySetBuilder$ValueAndHolder;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #182	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$ValueAndHolder;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$ValueAndHolder<TT;>; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/RegistrySetBuilder$ValueAndHolder;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #182	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$ValueAndHolder;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$ValueAndHolder<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/core/RegistrySetBuilder$ValueAndHolder;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #182	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/core/RegistrySetBuilder$ValueAndHolder;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 182 */       //   0	8	0	this	Lnet/minecraft/core/RegistrySetBuilder$ValueAndHolder<TT;>; } public RegistrySetBuilder.RegisteredValue<T> value() { return this.value; } public Optional<Holder.Reference<T>> holder() { return this.holder; }
/*     */      } private static final class RegistryStub<T> extends Record { private final ResourceKey<? extends Registry<T>> key; private final Lifecycle lifecycle; private final RegistrySetBuilder.RegistryBootstrap<T> bootstrap;
/* 184 */     RegistryStub(ResourceKey<? extends Registry<T>> $$0, Lifecycle $$1, RegistrySetBuilder.RegistryBootstrap<T> $$2) { this.key = $$0; this.lifecycle = $$1; this.bootstrap = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/core/RegistrySetBuilder$RegistryStub;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #184	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryStub;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryStub<TT;>; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/RegistrySetBuilder$RegistryStub;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #184	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryStub;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryStub<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/core/RegistrySetBuilder$RegistryStub;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #184	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryStub;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 184 */       //   0	8	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryStub<TT;>; } public ResourceKey<? extends Registry<T>> key() { return this.key; } public Lifecycle lifecycle() { return this.lifecycle; } public RegistrySetBuilder.RegistryBootstrap<T> bootstrap() { return this.bootstrap; }
/*     */      void apply(RegistrySetBuilder.BuildState $$0) {
/* 186 */       this.bootstrap.run($$0.bootstapContext());
/*     */     }
/*     */     
/*     */     public RegistrySetBuilder.RegistryContents<T> collectRegisteredValues(RegistrySetBuilder.BuildState $$0) {
/* 190 */       Map<ResourceKey<T>, RegistrySetBuilder.ValueAndHolder<T>> $$1 = new HashMap<>();
/*     */       
/* 192 */       Iterator<Map.Entry<ResourceKey<?>, RegistrySetBuilder.RegisteredValue<?>>> $$2 = $$0.registeredValues.entrySet().iterator();
/* 193 */       while ($$2.hasNext()) {
/* 194 */         Map.Entry<ResourceKey<?>, RegistrySetBuilder.RegisteredValue<?>> $$3 = $$2.next();
/* 195 */         ResourceKey<?> $$4 = $$3.getKey();
/* 196 */         if ($$4.isFor(this.key)) {
/* 197 */           ResourceKey<T> $$5 = (ResourceKey)$$4;
/* 198 */           RegistrySetBuilder.RegisteredValue<T> $$6 = (RegistrySetBuilder.RegisteredValue<T>)$$3.getValue();
/* 199 */           Holder.Reference<T> $$7 = (Holder.Reference<T>)$$0.lookup.holders.remove($$4);
/* 200 */           $$1.put($$5, new RegistrySetBuilder.ValueAndHolder<>($$6, Optional.ofNullable($$7)));
/*     */           
/* 202 */           $$2.remove();
/*     */         } 
/*     */       } 
/* 205 */       return new RegistrySetBuilder.RegistryContents<>(this.key, this.lifecycle, $$1);
/*     */     } }
/*     */ 
/*     */   
/*     */   static <T> HolderLookup.RegistryLookup<T> lookupFromMap(final ResourceKey<? extends Registry<? extends T>> key, final Lifecycle lifecycle, final Map<ResourceKey<T>, Holder.Reference<T>> entries) {
/* 210 */     return new HolderLookup.RegistryLookup<T>()
/*     */       {
/*     */         public ResourceKey<? extends Registry<? extends T>> key() {
/* 213 */           return key;
/*     */         }
/*     */ 
/*     */         
/*     */         public Lifecycle registryLifecycle() {
/* 218 */           return lifecycle;
/*     */         }
/*     */ 
/*     */         
/*     */         public Optional<Holder.Reference<T>> get(ResourceKey<T> $$0) {
/* 223 */           return Optional.ofNullable((Holder.Reference<T>)entries.get($$0));
/*     */         }
/*     */ 
/*     */         
/*     */         public Stream<Holder.Reference<T>> listElements() {
/* 228 */           return entries.values().stream();
/*     */         }
/*     */ 
/*     */         
/*     */         public Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
/* 233 */           return Optional.empty();
/*     */         }
/*     */ 
/*     */         
/*     */         public Stream<HolderSet.Named<T>> listTags() {
/* 238 */           return Stream.empty();
/*     */         }
/*     */       };
/*     */   }
/*     */   private static final class RegistryContents<T> extends Record { final ResourceKey<? extends Registry<? extends T>> key; private final Lifecycle lifecycle; private final Map<ResourceKey<T>, RegistrySetBuilder.ValueAndHolder<T>> values;
/* 243 */     RegistryContents(ResourceKey<? extends Registry<? extends T>> $$0, Lifecycle $$1, Map<ResourceKey<T>, RegistrySetBuilder.ValueAndHolder<T>> $$2) { this.key = $$0; this.lifecycle = $$1; this.values = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/core/RegistrySetBuilder$RegistryContents;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #243	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryContents;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryContents<TT;>; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/RegistrySetBuilder$RegistryContents;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #243	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryContents;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryContents<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/core/RegistrySetBuilder$RegistryContents;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #243	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryContents;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 243 */       //   0	8	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryContents<TT;>; } public ResourceKey<? extends Registry<? extends T>> key() { return this.key; } public Lifecycle lifecycle() { return this.lifecycle; } public Map<ResourceKey<T>, RegistrySetBuilder.ValueAndHolder<T>> values() { return this.values; }
/*     */      public HolderLookup.RegistryLookup<T> buildAsLookup(RegistrySetBuilder.CompositeOwner $$0) {
/* 245 */       Map<ResourceKey<T>, Holder.Reference<T>> $$1 = (Map<ResourceKey<T>, Holder.Reference<T>>)this.values.entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, $$1 -> {
/*     */               RegistrySetBuilder.ValueAndHolder<T> $$2 = (RegistrySetBuilder.ValueAndHolder<T>)$$1.getValue();
/*     */               
/*     */               Holder.Reference<T> $$3 = $$2.holder().orElseGet(());
/*     */               
/*     */               $$3.bindValue($$2.value().value());
/*     */               
/*     */               return $$3;
/*     */             }));
/*     */       
/* 255 */       HolderLookup.RegistryLookup<T> $$2 = RegistrySetBuilder.lookupFromMap(this.key, this.lifecycle, $$1);
/* 256 */       $$0.add($$2);
/* 257 */       return $$2;
/*     */     } }
/*     */ 
/*     */   
/* 261 */   private final List<RegistryStub<?>> entries = new ArrayList<>();
/*     */   
/*     */   public <T> RegistrySetBuilder add(ResourceKey<? extends Registry<T>> $$0, Lifecycle $$1, RegistryBootstrap<T> $$2) {
/* 264 */     this.entries.add(new RegistryStub($$0, $$1, $$2));
/* 265 */     return this;
/*     */   }
/*     */   
/*     */   public <T> RegistrySetBuilder add(ResourceKey<? extends Registry<T>> $$0, RegistryBootstrap<T> $$1) {
/* 269 */     return add($$0, Lifecycle.stable(), $$1);
/*     */   }
/*     */   
/*     */   private BuildState createState(RegistryAccess $$0) {
/* 273 */     BuildState $$1 = BuildState.create($$0, this.entries.stream().map(RegistryStub::key));
/* 274 */     this.entries.forEach($$1 -> $$1.apply($$0));
/* 275 */     return $$1;
/*     */   }
/*     */   
/*     */   private static HolderLookup.Provider buildProviderWithContext(RegistryAccess $$0, Stream<HolderLookup.RegistryLookup<?>> $$1) {
/* 279 */     Stream<HolderLookup.RegistryLookup<?>> $$2 = $$0.registries().map($$0 -> $$0.value().asLookup());
/* 280 */     return HolderLookup.Provider.create(Stream.concat($$2, $$1));
/*     */   }
/*     */   
/*     */   public HolderLookup.Provider build(RegistryAccess $$0) {
/* 284 */     BuildState $$1 = createState($$0);
/*     */     
/* 286 */     Stream<HolderLookup.RegistryLookup<?>> $$2 = this.entries.stream().map($$1 -> $$1.collectRegisteredValues($$0).buildAsLookup($$0.owner));
/* 287 */     HolderLookup.Provider $$3 = buildProviderWithContext($$0, $$2);
/*     */     
/* 289 */     $$1.reportNotCollectedHolders();
/* 290 */     $$1.reportUnclaimedRegisteredValues();
/* 291 */     $$1.throwOnError();
/*     */     
/* 293 */     return $$3;
/*     */   }
/*     */   
/*     */   public static final class PatchedRegistries extends Record {
/*     */     private final HolderLookup.Provider full;
/*     */     private final HolderLookup.Provider patches;
/*     */     
/* 300 */     public PatchedRegistries(HolderLookup.Provider $$0, HolderLookup.Provider $$1) { this.full = $$0; this.patches = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/core/RegistrySetBuilder$PatchedRegistries;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #300	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$PatchedRegistries; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/RegistrySetBuilder$PatchedRegistries;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #300	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$PatchedRegistries; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/core/RegistrySetBuilder$PatchedRegistries;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #300	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/core/RegistrySetBuilder$PatchedRegistries;
/* 300 */       //   0	8	1	$$0	Ljava/lang/Object; } public HolderLookup.Provider full() { return this.full; } public HolderLookup.Provider patches() { return this.patches; }
/*     */   
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
/*     */   private HolderLookup.Provider createLazyFullPatchedRegistries(RegistryAccess $$0, HolderLookup.Provider $$1, Cloner.Factory $$2, Map<ResourceKey<? extends Registry<?>>, RegistryContents<?>> $$3, HolderLookup.Provider $$4) {
/* 313 */     CompositeOwner $$5 = new CompositeOwner();
/* 314 */     MutableObject<HolderLookup.Provider> $$6 = new MutableObject();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     Objects.requireNonNull($$5);
/* 320 */     List<HolderLookup.RegistryLookup<?>> $$7 = (List<HolderLookup.RegistryLookup<?>>)$$3.keySet().stream().map($$5 -> createLazyFullPatchedRegistries($$0, $$1, $$5, $$2, $$3, $$4)).peek($$5::add).collect(Collectors.toUnmodifiableList());
/*     */     
/* 322 */     HolderLookup.Provider $$8 = buildProviderWithContext($$0, $$7.stream());
/* 323 */     $$6.setValue($$8);
/* 324 */     return $$8;
/*     */   }
/*     */   
/*     */   private <T> HolderLookup.RegistryLookup<T> createLazyFullPatchedRegistries(HolderOwner<T> $$0, Cloner.Factory $$1, ResourceKey<? extends Registry<? extends T>> $$2, HolderLookup.Provider $$3, HolderLookup.Provider $$4, MutableObject<HolderLookup.Provider> $$5) {
/* 328 */     Cloner<T> $$6 = $$1.cloner($$2);
/* 329 */     if ($$6 == null) {
/* 330 */       throw new NullPointerException("No cloner for " + $$2.location());
/*     */     }
/*     */     
/* 333 */     Map<ResourceKey<T>, Holder.Reference<T>> $$7 = new HashMap<>();
/*     */     
/* 335 */     HolderLookup.RegistryLookup<T> $$8 = $$3.lookupOrThrow($$2);
/* 336 */     $$8.listElements().forEach($$5 -> {
/*     */           ResourceKey<T> $$6 = $$5.key();
/*     */           
/*     */           LazyHolder<T> $$7 = new LazyHolder<>($$0, $$6);
/*     */           
/*     */           $$7.supplier = (());
/*     */           $$4.put($$6, $$7);
/*     */         });
/* 344 */     HolderLookup.RegistryLookup<T> $$9 = $$4.lookupOrThrow($$2);
/* 345 */     $$9.listElements().forEach($$5 -> {
/*     */           ResourceKey<T> $$6 = $$5.key();
/*     */ 
/*     */ 
/*     */           
/*     */           $$0.computeIfAbsent($$6, ());
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 355 */     Lifecycle $$10 = $$8.registryLifecycle().add($$9.registryLifecycle());
/*     */     
/* 357 */     return lookupFromMap($$2, $$10, $$7);
/*     */   }
/*     */   
/*     */   public PatchedRegistries buildPatch(RegistryAccess $$0, HolderLookup.Provider $$1, Cloner.Factory $$2) {
/* 361 */     BuildState $$3 = createState($$0);
/*     */     
/* 363 */     Map<ResourceKey<? extends Registry<?>>, RegistryContents<?>> $$4 = new HashMap<>();
/*     */ 
/*     */     
/* 366 */     this.entries.stream()
/* 367 */       .map($$1 -> $$1.collectRegisteredValues($$0))
/* 368 */       .forEach($$1 -> $$0.put($$1.key, $$1));
/*     */ 
/*     */     
/* 371 */     Set<ResourceKey<? extends Registry<?>>> $$5 = $$0.listRegistries().collect((Collector)Collectors.toUnmodifiableSet());
/* 372 */     $$1
/* 373 */       .listRegistries()
/* 374 */       .filter($$1 -> !$$0.contains($$1))
/* 375 */       .forEach($$1 -> $$0.putIfAbsent($$1, new RegistryContents($$1, Lifecycle.stable(), Map.of())));
/*     */     
/* 377 */     Stream<HolderLookup.RegistryLookup<?>> $$6 = $$4.values().stream().map($$1 -> $$1.buildAsLookup($$0.owner));
/* 378 */     HolderLookup.Provider $$7 = buildProviderWithContext($$0, $$6);
/*     */     
/* 380 */     $$3.reportUnclaimedRegisteredValues();
/* 381 */     $$3.throwOnError();
/*     */     
/* 383 */     HolderLookup.Provider $$8 = createLazyFullPatchedRegistries($$0, $$1, $$2, $$4, $$7);
/*     */     
/* 385 */     return new PatchedRegistries($$8, $$7);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface RegistryBootstrap<T> {
/*     */     void run(BootstapContext<T> param1BootstapContext);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistrySetBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */