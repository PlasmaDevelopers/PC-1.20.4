/*    */ package net.minecraft.network.protocol.configuration;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public final class ClientboundUpdateEnabledFeaturesPacket extends Record implements Packet<ClientConfigurationPacketListener> {
/*    */   private final Set<ResourceLocation> features;
/*    */   
/* 10 */   public ClientboundUpdateEnabledFeaturesPacket(Set<ResourceLocation> $$0) { this.features = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/configuration/ClientboundUpdateEnabledFeaturesPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 10 */     //   0	7	0	this	Lnet/minecraft/network/protocol/configuration/ClientboundUpdateEnabledFeaturesPacket; } public Set<ResourceLocation> features() { return this.features; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/configuration/ClientboundUpdateEnabledFeaturesPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/configuration/ClientboundUpdateEnabledFeaturesPacket; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/configuration/ClientboundUpdateEnabledFeaturesPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/configuration/ClientboundUpdateEnabledFeaturesPacket;
/*    */     //   0	8	1	$$0	Ljava/lang/Object; } public ClientboundUpdateEnabledFeaturesPacket(FriendlyByteBuf $$0) {
/* 13 */     this((Set<ResourceLocation>)$$0.readCollection(java.util.HashSet::new, FriendlyByteBuf::readResourceLocation));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 18 */     $$0.writeCollection(this.features, FriendlyByteBuf::writeResourceLocation);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientConfigurationPacketListener $$0) {
/* 23 */     $$0.handleEnabledFeatures(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\configuration\ClientboundUpdateEnabledFeaturesPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */