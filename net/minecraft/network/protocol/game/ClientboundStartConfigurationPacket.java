/*    */ package net.minecraft.network.protocol.game;
/*    */ import net.minecraft.network.ConnectionProtocol;
/*    */ import net.minecraft.network.PacketListener;
/*    */ 
/*    */ public final class ClientboundStartConfigurationPacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   public ClientboundStartConfigurationPacket() {}
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundStartConfigurationPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundStartConfigurationPacket;
/*    */   }
/*    */   
/*    */   public ClientboundStartConfigurationPacket(FriendlyByteBuf $$0) {
/* 13 */     this();
/*    */   }
/*    */ 
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundStartConfigurationPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundStartConfigurationPacket;
/*    */   }
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 22 */     $$0.handleConfigurationStart(this);
/*    */   } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundStartConfigurationPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundStartConfigurationPacket;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   } public void write(FriendlyByteBuf $$0) {}
/*    */   public ConnectionProtocol nextProtocol() {
/* 27 */     return ConnectionProtocol.CONFIGURATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundStartConfigurationPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */