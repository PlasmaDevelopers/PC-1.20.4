/*    */ package net.minecraft.core;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.Lifecycle;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import com.mojang.serialization.codecs.UnboundedMapCodec;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ 
/*    */ public class RegistryCodecs {
/*    */   private static <T> MapCodec<RegistryEntry<T>> withNameAndId(ResourceKey<? extends Registry<T>> $$0, MapCodec<T> $$1) {
/* 18 */     return RecordCodecBuilder.mapCodec($$2 -> $$2.group((App)ResourceKey.codec($$0).fieldOf("name").forGetter(RegistryEntry::key), (App)Codec.INT.fieldOf("id").forGetter(RegistryEntry::id), (App)$$1.forGetter(RegistryEntry::value)).apply((Applicative)$$2, RegistryEntry::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> Codec<Registry<T>> networkCodec(ResourceKey<? extends Registry<T>> $$0, Lifecycle $$1, Codec<T> $$2) {
/* 26 */     return withNameAndId($$0, $$2.fieldOf("element")).codec().listOf().xmap($$2 -> {
/*    */           MappedRegistry<T> $$3 = new MappedRegistry<>($$0, $$1);
/*    */           for (RegistryEntry<T> $$4 : (Iterable<RegistryEntry<T>>)$$2) {
/*    */             $$3.registerMapping($$4.id(), $$4.key(), $$4.value(), $$1);
/*    */           }
/*    */           return $$3;
/*    */         }$$0 -> {
/*    */           ImmutableList.Builder<RegistryEntry<T>> $$1 = ImmutableList.builder();
/*    */           for (T $$2 : $$0) {
/*    */             $$1.add(new RegistryEntry<>($$0.getResourceKey($$2).get(), $$0.getId($$2), $$2));
/*    */           }
/*    */           return (List)$$1.build();
/*    */         });
/*    */   }
/*    */   
/*    */   public static <E> Codec<Registry<E>> fullCodec(ResourceKey<? extends Registry<E>> $$0, Lifecycle $$1, Codec<E> $$2) {
/* 42 */     UnboundedMapCodec unboundedMapCodec = Codec.unboundedMap(ResourceKey.codec($$0), $$2);
/* 43 */     return unboundedMapCodec.xmap($$2 -> {
/*    */           WritableRegistry<E> $$3 = new MappedRegistry<>($$0, $$1);
/*    */           $$2.forEach(());
/*    */           return $$3.freeze();
/*    */         }$$0 -> ImmutableMap.copyOf($$0.entrySet()));
/*    */   }
/*    */ 
/*    */   
/*    */   public static <E> Codec<HolderSet<E>> homogeneousList(ResourceKey<? extends Registry<E>> $$0, Codec<E> $$1) {
/* 52 */     return homogeneousList($$0, $$1, false);
/*    */   }
/*    */   
/*    */   public static <E> Codec<HolderSet<E>> homogeneousList(ResourceKey<? extends Registry<E>> $$0, Codec<E> $$1, boolean $$2) {
/* 56 */     return HolderSetCodec.create($$0, (Codec)RegistryFileCodec.create($$0, $$1), $$2);
/*    */   }
/*    */   
/*    */   public static <E> Codec<HolderSet<E>> homogeneousList(ResourceKey<? extends Registry<E>> $$0) {
/* 60 */     return homogeneousList($$0, false);
/*    */   }
/*    */   
/*    */   public static <E> Codec<HolderSet<E>> homogeneousList(ResourceKey<? extends Registry<E>> $$0, boolean $$1) {
/* 64 */     return HolderSetCodec.create($$0, (Codec)RegistryFixedCodec.create($$0), $$1);
/*    */   }
/*    */   private static final class RegistryEntry<T> extends Record { private final ResourceKey<T> key; private final int id; private final T value;
/* 67 */     RegistryEntry(ResourceKey<T> $$0, int $$1, T $$2) { this.key = $$0; this.id = $$1; this.value = $$2; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/core/RegistryCodecs$RegistryEntry;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #67	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/core/RegistryCodecs$RegistryEntry;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 67 */       //   0	7	0	this	Lnet/minecraft/core/RegistryCodecs$RegistryEntry<TT;>; } public ResourceKey<T> key() { return this.key; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/RegistryCodecs$RegistryEntry;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #67	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/core/RegistryCodecs$RegistryEntry;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/*    */       //   0	7	0	this	Lnet/minecraft/core/RegistryCodecs$RegistryEntry<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/core/RegistryCodecs$RegistryEntry;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #67	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/core/RegistryCodecs$RegistryEntry;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 67 */       //   0	8	0	this	Lnet/minecraft/core/RegistryCodecs$RegistryEntry<TT;>; } public int id() { return this.id; } public T value() { return this.value; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistryCodecs.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */