/*    */ package net.minecraft.resources;
/*    */ 
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.Decoder;
/*    */ import com.mojang.serialization.Lifecycle;
/*    */ import java.util.Map;
/*    */ import java.util.function.BiConsumer;
/*    */ import net.minecraft.core.MappedRegistry;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.WritableRegistry;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
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
/*    */ public final class RegistryData<T>
/*    */   extends Record
/*    */ {
/*    */   private final ResourceKey<? extends Registry<T>> key;
/*    */   private final Codec<T> elementCodec;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/resources/RegistryDataLoader$RegistryData;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #59	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/resources/RegistryDataLoader$RegistryData;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/resources/RegistryDataLoader$RegistryData<TT;>;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/resources/RegistryDataLoader$RegistryData;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #59	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/resources/RegistryDataLoader$RegistryData;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/resources/RegistryDataLoader$RegistryData<TT;>;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/resources/RegistryDataLoader$RegistryData;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #59	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/resources/RegistryDataLoader$RegistryData;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	8	0	this	Lnet/minecraft/resources/RegistryDataLoader$RegistryData<TT;>;
/*    */   }
/*    */   
/*    */   public RegistryData(ResourceKey<? extends Registry<T>> $$0, Codec<T> $$1) {
/* 59 */     this.key = $$0; this.elementCodec = $$1; } public ResourceKey<? extends Registry<T>> key() { return this.key; } public Codec<T> elementCodec() { return this.elementCodec; }
/*    */    Pair<WritableRegistry<?>, RegistryDataLoader.Loader> create(Lifecycle $$0, Map<ResourceKey<?>, Exception> $$1) {
/* 61 */     MappedRegistry mappedRegistry = new MappedRegistry(this.key, $$0);
/* 62 */     RegistryDataLoader.Loader $$3 = ($$2, $$3) -> RegistryDataLoader.loadRegistryContents($$3, $$2, this.key, $$0, (Decoder<T>)this.elementCodec, $$1);
/* 63 */     return Pair.of(mappedRegistry, $$3);
/*    */   }
/*    */   
/*    */   public void runWithArguments(BiConsumer<ResourceKey<? extends Registry<T>>, Codec<T>> $$0) {
/* 67 */     $$0.accept(this.key, this.elementCodec);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\RegistryDataLoader$RegistryData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */