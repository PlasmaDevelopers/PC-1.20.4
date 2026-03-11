/*    */ package net.minecraft.core;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ 
/*    */ final class NetworkedRegistryData<E>
/*    */   extends Record
/*    */ {
/*    */   private final ResourceKey<? extends Registry<E>> key;
/*    */   private final Codec<E> networkCodec;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #25	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData<TE;>;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #25	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData<TE;>;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #25	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	8	0	this	Lnet/minecraft/core/RegistrySynchronization$NetworkedRegistryData<TE;>;
/*    */   }
/*    */   
/*    */   NetworkedRegistryData(ResourceKey<? extends Registry<E>> $$0, Codec<E> $$1) {
/* 25 */     this.key = $$0; this.networkCodec = $$1; } public ResourceKey<? extends Registry<E>> key() { return this.key; } public Codec<E> networkCodec() { return this.networkCodec; }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistrySynchronization$NetworkedRegistryData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */