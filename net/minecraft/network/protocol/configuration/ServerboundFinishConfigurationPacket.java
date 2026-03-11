/*    */ package net.minecraft.network.protocol.configuration;
/*    */ import net.minecraft.network.ConnectionProtocol;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ 
/*    */ public final class ServerboundFinishConfigurationPacket extends Record implements Packet<ServerConfigurationPacketListener> {
/*    */   public ServerboundFinishConfigurationPacket() {}
/*    */   
/*    */   public ServerboundFinishConfigurationPacket(FriendlyByteBuf $$0) {
/* 10 */     this();
/*    */   } public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/configuration/ServerboundFinishConfigurationPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/configuration/ServerboundFinishConfigurationPacket;
/*    */   } public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/configuration/ServerboundFinishConfigurationPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/configuration/ServerboundFinishConfigurationPacket;
/*    */   } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/configuration/ServerboundFinishConfigurationPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/configuration/ServerboundFinishConfigurationPacket;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   public void write(FriendlyByteBuf $$0) {}
/*    */   public void handle(ServerConfigurationPacketListener $$0) {
/* 20 */     $$0.handleConfigurationFinished(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ConnectionProtocol nextProtocol() {
/* 25 */     return ConnectionProtocol.PLAY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\configuration\ServerboundFinishConfigurationPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */