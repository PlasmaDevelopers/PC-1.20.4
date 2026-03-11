/*    */ package net.minecraft.core;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.UnboundedMapCodec;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.network.chat.ChatType;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.server.RegistryLayer;
/*    */ import net.minecraft.world.damagesource.DamageType;
/*    */ import net.minecraft.world.item.armortrim.TrimMaterial;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.dimension.DimensionType;
/*    */ 
/*    */ public class RegistrySynchronization {
/*    */   private static final Map<ResourceKey<? extends Registry<?>>, NetworkedRegistryData<?>> NETWORKABLE_REGISTRIES;
/*    */   
/*    */   private static final class NetworkedRegistryData<E> extends Record {
/*    */     private final ResourceKey<? extends Registry<E>> key;
/*    */     private final Codec<E> networkCodec;
/*    */     
/* 25 */     NetworkedRegistryData(ResourceKey<? extends Registry<E>> $$0, Codec<E> $$1) { this.key = $$0; this.networkCodec = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #25	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 25 */       //   0	7	0	this	Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData<TE;>; } public ResourceKey<? extends Registry<E>> key() { return this.key; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #25	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/*    */       //   0	7	0	this	Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData<TE;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #25	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 25 */       //   0	8	0	this	Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData<TE;>; } public Codec<E> networkCodec() { return this.networkCodec; }
/*    */   
/*    */   }
/*    */   
/*    */   static {
/* 30 */     NETWORKABLE_REGISTRIES = (Map<ResourceKey<? extends Registry<?>>, NetworkedRegistryData<?>>)Util.make(() -> {
/*    */           ImmutableMap.Builder<ResourceKey<? extends Registry<?>>, NetworkedRegistryData<?>> $$0 = ImmutableMap.builder();
/*    */           put($$0, Registries.BIOME, Biome.NETWORK_CODEC);
/*    */           put($$0, Registries.CHAT_TYPE, ChatType.CODEC);
/*    */           put($$0, Registries.TRIM_PATTERN, TrimPattern.DIRECT_CODEC);
/*    */           put($$0, Registries.TRIM_MATERIAL, TrimMaterial.DIRECT_CODEC);
/*    */           put($$0, Registries.DIMENSION_TYPE, DimensionType.DIRECT_CODEC);
/*    */           put($$0, Registries.DAMAGE_TYPE, DamageType.CODEC);
/*    */           return $$0.build();
/*    */         });
/*    */   }
/*    */   private static <E> void put(ImmutableMap.Builder<ResourceKey<? extends Registry<?>>, NetworkedRegistryData<?>> $$0, ResourceKey<? extends Registry<E>> $$1, Codec<E> $$2) {
/* 42 */     $$0.put($$1, new NetworkedRegistryData<>($$1, $$2));
/*    */   }
/*    */   
/*    */   private static Stream<RegistryAccess.RegistryEntry<?>> ownedNetworkableRegistries(RegistryAccess $$0) {
/* 46 */     return $$0.registries().filter($$0 -> NETWORKABLE_REGISTRIES.containsKey($$0.key()));
/*    */   }
/*    */ 
/*    */   
/*    */   private static <E> DataResult<? extends Codec<E>> getNetworkCodec(ResourceKey<? extends Registry<E>> $$0) {
/* 51 */     return Optional.<NetworkedRegistryData>ofNullable(NETWORKABLE_REGISTRIES.get($$0))
/* 52 */       .map($$0 -> $$0.networkCodec())
/* 53 */       .map(DataResult::success)
/* 54 */       .orElseGet(() -> DataResult.error(()));
/*    */   }
/*    */   
/*    */   private static <E> Codec<RegistryAccess> makeNetworkCodec() {
/* 58 */     Codec<ResourceKey<? extends Registry<E>>> $$0 = ResourceLocation.CODEC.xmap(ResourceKey::createRegistryKey, ResourceKey::location);
/* 59 */     Codec<Registry<E>> $$1 = $$0.partialDispatch("type", $$0 -> DataResult.success($$0.key()), $$0 -> getNetworkCodec($$0).map(()));
/* 60 */     UnboundedMapCodec<? extends ResourceKey<? extends Registry<?>>, ? extends Registry<?>> $$2 = Codec.unboundedMap($$0, $$1);
/* 61 */     return captureMap($$2);
/*    */   }
/*    */ 
/*    */   
/*    */   private static <K extends ResourceKey<? extends Registry<?>>, V extends Registry<?>> Codec<RegistryAccess> captureMap(UnboundedMapCodec<K, V> $$0) {
/* 66 */     return $$0.xmap(ImmutableRegistryAccess::new, $$0 -> (Map)ownedNetworkableRegistries($$0).collect(ImmutableMap.toImmutableMap((), ())));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Stream<RegistryAccess.RegistryEntry<?>> networkedRegistries(LayeredRegistryAccess<RegistryLayer> $$0) {
/* 73 */     return ownedNetworkableRegistries($$0.getAccessFrom(RegistryLayer.WORLDGEN));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Stream<RegistryAccess.RegistryEntry<?>> networkSafeRegistries(LayeredRegistryAccess<RegistryLayer> $$0) {
/* 81 */     Stream<RegistryAccess.RegistryEntry<?>> $$1 = $$0.getLayer(RegistryLayer.STATIC).registries();
/* 82 */     Stream<RegistryAccess.RegistryEntry<?>> $$2 = networkedRegistries($$0);
/* 83 */     return Stream.concat($$2, $$1);
/*    */   }
/*    */   
/* 86 */   public static final Codec<RegistryAccess> NETWORK_CODEC = makeNetworkCodec();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistrySynchronization.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */