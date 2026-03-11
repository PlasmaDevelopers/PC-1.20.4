/*    */ package net.minecraft.network.protocol.handshake;
/*    */ 
/*    */ 
/*    */ public final class ClientIntentionPacket extends Record implements Packet<ServerHandshakePacketListener> {
/*    */   private final int protocolVersion;
/*    */   private final String hostName;
/*    */   private final int port;
/*    */   private final ClientIntent intention;
/*    */   
/* 10 */   public int protocolVersion() { return this.protocolVersion; } private static final int MAX_HOST_LENGTH = 255; public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/handshake/ClientIntentionPacket;
/* 10 */     //   0	8	1	$$0	Ljava/lang/Object; } public String hostName() { return this.hostName; } public int port() { return this.port; } public ClientIntent intention() { return this.intention; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public ClientIntentionPacket(int $$0, String $$1, int $$2, ClientIntent $$3) {
/* 22 */     this.protocolVersion = $$0; this.hostName = $$1; this.port = $$2; this.intention = $$3;
/*    */   }
/*    */   
/*    */   public ClientIntentionPacket(FriendlyByteBuf $$0) {
/* 26 */     this($$0
/* 27 */         .readVarInt(), $$0
/* 28 */         .readUtf(255), $$0
/* 29 */         .readUnsignedShort(), 
/* 30 */         ClientIntent.byId($$0.readVarInt()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 36 */     $$0.writeVarInt(this.protocolVersion);
/* 37 */     $$0.writeUtf(this.hostName);
/* 38 */     $$0.writeShort(this.port);
/* 39 */     $$0.writeVarInt(this.intention.id());
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerHandshakePacketListener $$0) {
/* 44 */     $$0.handleIntention(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ConnectionProtocol nextProtocol() {
/* 49 */     return this.intention.protocol();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\handshake\ClientIntentionPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */