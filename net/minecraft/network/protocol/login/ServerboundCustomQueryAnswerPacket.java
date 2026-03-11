/*    */ package net.minecraft.network.protocol.login;
/*    */ public final class ServerboundCustomQueryAnswerPacket extends Record implements Packet<ServerLoginPacketListener> { private final int transactionId; @Nullable
/*    */   private final CustomQueryAnswerPayload payload;
/*    */   private static final int MAX_PAYLOAD_SIZE = 1048576;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/login/ServerboundCustomQueryAnswerPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/login/ServerboundCustomQueryAnswerPacket;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/login/ServerboundCustomQueryAnswerPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/login/ServerboundCustomQueryAnswerPacket;
/*    */   }
/*    */   
/* 14 */   public ServerboundCustomQueryAnswerPacket(int $$0, @Nullable CustomQueryAnswerPayload $$1) { this.transactionId = $$0; this.payload = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/login/ServerboundCustomQueryAnswerPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/login/ServerboundCustomQueryAnswerPacket;
/* 14 */     //   0	8	1	$$0	Ljava/lang/Object; } public int transactionId() { return this.transactionId; } @Nullable public CustomQueryAnswerPayload payload() { return this.payload; }
/*    */ 
/*    */   
/*    */   public static ServerboundCustomQueryAnswerPacket read(FriendlyByteBuf $$0) {
/* 18 */     int $$1 = $$0.readVarInt();
/* 19 */     return new ServerboundCustomQueryAnswerPacket($$1, 
/*    */         
/* 21 */         readPayload($$1, $$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static CustomQueryAnswerPayload readPayload(int $$0, FriendlyByteBuf $$1) {
/* 31 */     return readUnknownPayload($$1);
/*    */   }
/*    */   
/*    */   private static CustomQueryAnswerPayload readUnknownPayload(FriendlyByteBuf $$0) {
/* 35 */     int $$1 = $$0.readableBytes();
/* 36 */     if ($$1 < 0 || $$1 > 1048576) {
/* 37 */       throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
/*    */     }
/* 39 */     $$0.skipBytes($$1);
/* 40 */     return (CustomQueryAnswerPayload)DiscardedQueryAnswerPayload.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 45 */     $$0.writeVarInt(this.transactionId);
/* 46 */     $$0.writeNullable(this.payload, ($$0, $$1) -> $$1.write($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerLoginPacketListener $$0) {
/* 51 */     $$0.handleCustomQueryPacket(this);
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\login\ServerboundCustomQueryAnswerPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */