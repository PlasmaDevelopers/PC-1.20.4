/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ 
/*    */ public final class ServerboundChatCommandPacket extends Record implements Packet<ServerGamePacketListener> {
/*    */   private final String command;
/*    */   private final Instant timeStamp;
/*    */   private final long salt;
/*    */   private final ArgumentSignatures argumentSignatures;
/*    */   private final LastSeenMessages.Update lastSeenMessages;
/*    */   
/* 11 */   public ServerboundChatCommandPacket(String $$0, Instant $$1, long $$2, ArgumentSignatures $$3, LastSeenMessages.Update $$4) { this.command = $$0; this.timeStamp = $$1; this.salt = $$2; this.argumentSignatures = $$3; this.lastSeenMessages = $$4; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ServerboundChatCommandPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ServerboundChatCommandPacket; } public String command() { return this.command; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ServerboundChatCommandPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ServerboundChatCommandPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ServerboundChatCommandPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ServerboundChatCommandPacket;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public Instant timeStamp() { return this.timeStamp; } public long salt() { return this.salt; } public ArgumentSignatures argumentSignatures() { return this.argumentSignatures; } public LastSeenMessages.Update lastSeenMessages() { return this.lastSeenMessages; }
/*    */    public ServerboundChatCommandPacket(FriendlyByteBuf $$0) {
/* 13 */     this($$0
/* 14 */         .readUtf(256), $$0
/* 15 */         .readInstant(), $$0
/* 16 */         .readLong(), new ArgumentSignatures($$0), new LastSeenMessages.Update($$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 24 */     $$0.writeUtf(this.command, 256);
/* 25 */     $$0.writeInstant(this.timeStamp);
/* 26 */     $$0.writeLong(this.salt);
/* 27 */     this.argumentSignatures.write($$0);
/* 28 */     this.lastSeenMessages.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 33 */     $$0.handleChatCommand(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundChatCommandPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */