/*    */ package net.minecraft.network.protocol.configuration;
/*    */ 
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.core.RegistrySynchronization;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.resources.RegistryOps;
/*    */ 
/*    */ public final class ClientboundRegistryDataPacket extends Record implements Packet<ClientConfigurationPacketListener> {
/*    */   private final RegistryAccess.Frozen registryHolder;
/*    */   
/* 13 */   public ClientboundRegistryDataPacket(RegistryAccess.Frozen $$0) { this.registryHolder = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/configuration/ClientboundRegistryDataPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 13 */     //   0	7	0	this	Lnet/minecraft/network/protocol/configuration/ClientboundRegistryDataPacket; } public RegistryAccess.Frozen registryHolder() { return this.registryHolder; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/configuration/ClientboundRegistryDataPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/configuration/ClientboundRegistryDataPacket; } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/configuration/ClientboundRegistryDataPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/configuration/ClientboundRegistryDataPacket;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/* 16 */   } private static final RegistryOps<Tag> BUILTIN_CONTEXT_OPS = RegistryOps.create((DynamicOps)NbtOps.INSTANCE, (HolderLookup.Provider)RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY));
/*    */   
/*    */   public ClientboundRegistryDataPacket(FriendlyByteBuf $$0) {
/* 19 */     this(((RegistryAccess)$$0
/* 20 */         .readWithCodecTrusted((DynamicOps)BUILTIN_CONTEXT_OPS, RegistrySynchronization.NETWORK_CODEC)).freeze());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 26 */     $$0.writeWithCodec((DynamicOps)BUILTIN_CONTEXT_OPS, RegistrySynchronization.NETWORK_CODEC, this.registryHolder);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientConfigurationPacketListener $$0) {
/* 31 */     $$0.handleRegistryData(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\configuration\ClientboundRegistryDataPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */