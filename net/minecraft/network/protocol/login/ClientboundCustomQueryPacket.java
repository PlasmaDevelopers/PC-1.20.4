/*    */ package net.minecraft.network.protocol.login;
/*    */ 
/*    */ 
/*    */ public final class ClientboundCustomQueryPacket extends Record implements Packet<ClientLoginPacketListener> {
/*    */   private final int transactionId;
/*    */   private final CustomQueryPayload payload;
/*    */   private static final int MAX_PAYLOAD_SIZE = 1048576;
/*    */   
/*  9 */   public ClientboundCustomQueryPacket(int $$0, CustomQueryPayload $$1) { this.transactionId = $$0; this.payload = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/login/ClientboundCustomQueryPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/network/protocol/login/ClientboundCustomQueryPacket; } public int transactionId() { return this.transactionId; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/login/ClientboundCustomQueryPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/login/ClientboundCustomQueryPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/login/ClientboundCustomQueryPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/login/ClientboundCustomQueryPacket;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public CustomQueryPayload payload() { return this.payload; }
/*    */ 
/*    */   
/*    */   public ClientboundCustomQueryPacket(FriendlyByteBuf $$0) {
/* 13 */     this($$0
/* 14 */         .readVarInt(), 
/* 15 */         readPayload($$0
/* 16 */           .readResourceLocation(), $$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static CustomQueryPayload readPayload(ResourceLocation $$0, FriendlyByteBuf $$1) {
/* 24 */     return (CustomQueryPayload)readUnknownPayload($$0, $$1);
/*    */   }
/*    */   
/*    */   private static DiscardedQueryPayload readUnknownPayload(ResourceLocation $$0, FriendlyByteBuf $$1) {
/* 28 */     int $$2 = $$1.readableBytes();
/* 29 */     if ($$2 < 0 || $$2 > 1048576) {
/* 30 */       throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
/*    */     }
/* 32 */     $$1.skipBytes($$2);
/* 33 */     return new DiscardedQueryPayload($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 38 */     $$0.writeVarInt(this.transactionId);
/* 39 */     $$0.writeResourceLocation(this.payload.id());
/* 40 */     this.payload.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientLoginPacketListener $$0) {
/* 45 */     $$0.handleCustomQuery(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\login\ClientboundCustomQueryPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */