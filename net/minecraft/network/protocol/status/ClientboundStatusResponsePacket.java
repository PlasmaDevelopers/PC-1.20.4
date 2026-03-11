/*    */ package net.minecraft.network.protocol.status;
/*    */ 
/*    */ public final class ClientboundStatusResponsePacket extends Record implements Packet<ClientStatusPacketListener> {
/*    */   private final ServerStatus status;
/*    */   
/*  6 */   public ClientboundStatusResponsePacket(ServerStatus $$0) { this.status = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/status/ClientboundStatusResponsePacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  6 */     //   0	7	0	this	Lnet/minecraft/network/protocol/status/ClientboundStatusResponsePacket; } public ServerStatus status() { return this.status; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/status/ClientboundStatusResponsePacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/status/ClientboundStatusResponsePacket; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/status/ClientboundStatusResponsePacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/status/ClientboundStatusResponsePacket;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } public ClientboundStatusResponsePacket(FriendlyByteBuf $$0) { this((ServerStatus)$$0.readJsonWithCodec(ServerStatus.CODEC)); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 13 */     $$0.writeJsonWithCodec(ServerStatus.CODEC, this.status);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientStatusPacketListener $$0) {
/* 18 */     $$0.handleStatusResponse(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\status\ClientboundStatusResponsePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */