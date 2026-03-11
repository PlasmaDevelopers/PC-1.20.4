/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ 
/*    */ public final class ServerboundChatPacket extends Record implements Packet<ServerGamePacketListener> {
/*    */   private final String message;
/*    */   private final Instant timeStamp;
/*    */   private final long salt;
/*    */   @Nullable
/*    */   private final MessageSignature signature;
/*    */   private final LastSeenMessages.Update lastSeenMessages;
/*    */   
/* 12 */   public ServerboundChatPacket(String $$0, Instant $$1, long $$2, @Nullable MessageSignature $$3, LastSeenMessages.Update $$4) { this.message = $$0; this.timeStamp = $$1; this.salt = $$2; this.signature = $$3; this.lastSeenMessages = $$4; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ServerboundChatPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 12 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ServerboundChatPacket; } public String message() { return this.message; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ServerboundChatPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ServerboundChatPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ServerboundChatPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ServerboundChatPacket;
/* 12 */     //   0	8	1	$$0	Ljava/lang/Object; } public Instant timeStamp() { return this.timeStamp; } public long salt() { return this.salt; } @Nullable public MessageSignature signature() { return this.signature; } public LastSeenMessages.Update lastSeenMessages() { return this.lastSeenMessages; }
/*    */    public ServerboundChatPacket(FriendlyByteBuf $$0) {
/* 14 */     this($$0
/* 15 */         .readUtf(256), $$0
/* 16 */         .readInstant(), $$0
/* 17 */         .readLong(), (MessageSignature)$$0
/* 18 */         .readNullable(MessageSignature::read), new LastSeenMessages.Update($$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 25 */     $$0.writeUtf(this.message, 256);
/* 26 */     $$0.writeInstant(this.timeStamp);
/* 27 */     $$0.writeLong(this.salt);
/* 28 */     $$0.writeNullable(this.signature, MessageSignature::write);
/* 29 */     this.lastSeenMessages.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 34 */     $$0.handleChat(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundChatPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */