/*    */ package net.minecraft.resources;
/*    */ 
/*    */ import com.google.common.collect.MapMaker;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ 
/*    */ public class ResourceKey<T> {
/*    */   private static final class InternKey extends Record {
/*    */     final ResourceLocation registry;
/*    */     final ResourceLocation location;
/*    */     
/*    */     InternKey(ResourceLocation $$0, ResourceLocation $$1) {
/* 16 */       this.registry = $$0; this.location = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/resources/ResourceKey$InternKey;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #16	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/resources/ResourceKey$InternKey; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/resources/ResourceKey$InternKey;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #16	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/resources/ResourceKey$InternKey; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/resources/ResourceKey$InternKey;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #16	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/resources/ResourceKey$InternKey;
/* 16 */       //   0	8	1	$$0	Ljava/lang/Object; } public ResourceLocation registry() { return this.registry; } public ResourceLocation location() { return this.location; }
/*    */      }
/* 18 */   private static final ConcurrentMap<InternKey, ResourceKey<?>> VALUES = (new MapMaker()).weakValues().makeMap();
/*    */   
/*    */   private final ResourceLocation registryName;
/*    */   private final ResourceLocation location;
/*    */   
/*    */   public static <T> Codec<ResourceKey<T>> codec(ResourceKey<? extends Registry<T>> $$0) {
/* 24 */     return ResourceLocation.CODEC.xmap($$1 -> create($$0, $$1), ResourceKey::location);
/*    */   }
/*    */   
/*    */   public static <T> ResourceKey<T> create(ResourceKey<? extends Registry<T>> $$0, ResourceLocation $$1) {
/* 28 */     return create($$0.location, $$1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> ResourceKey<Registry<T>> createRegistryKey(ResourceLocation $$0) {
/* 35 */     return create(Registries.ROOT_REGISTRY_NAME, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T> ResourceKey<T> create(ResourceLocation $$0, ResourceLocation $$1) {
/* 40 */     return (ResourceKey<T>)VALUES.computeIfAbsent(new InternKey($$0, $$1), $$0 -> new ResourceKey($$0.registry, $$0.location));
/*    */   }
/*    */   
/*    */   private ResourceKey(ResourceLocation $$0, ResourceLocation $$1) {
/* 44 */     this.registryName = $$0;
/* 45 */     this.location = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 50 */     return "ResourceKey[" + this.registryName + " / " + this.location + "]";
/*    */   }
/*    */   
/*    */   public boolean isFor(ResourceKey<? extends Registry<?>> $$0) {
/* 54 */     return this.registryName.equals($$0.location());
/*    */   }
/*    */ 
/*    */   
/*    */   public <E> Optional<ResourceKey<E>> cast(ResourceKey<? extends Registry<E>> $$0) {
/* 59 */     return isFor($$0) ? Optional.<ResourceKey<E>>of(this) : Optional.<ResourceKey<E>>empty();
/*    */   }
/*    */   
/*    */   public ResourceLocation location() {
/* 63 */     return this.location;
/*    */   }
/*    */   
/*    */   public ResourceLocation registry() {
/* 67 */     return this.registryName;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\ResourceKey.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */