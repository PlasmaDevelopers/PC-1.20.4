/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ public final class ClientboundPlayerChatPacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final UUID sender;
/*    */   private final int index;
/*    */   @Nullable
/*    */   private final MessageSignature signature;
/*    */   private final SignedMessageBody.Packed body;
/*    */   @Nullable
/*    */   private final Component unsignedContent;
/*    */   private final FilterMask filterMask;
/*    */   private final ChatType.BoundNetwork chatType;
/*    */   
/* 14 */   public ClientboundPlayerChatPacket(UUID $$0, int $$1, @Nullable MessageSignature $$2, SignedMessageBody.Packed $$3, @Nullable Component $$4, FilterMask $$5, ChatType.BoundNetwork $$6) { this.sender = $$0; this.index = $$1; this.signature = $$2; this.body = $$3; this.unsignedContent = $$4; this.filterMask = $$5; this.chatType = $$6; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundPlayerChatPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 14 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundPlayerChatPacket; } public UUID sender() { return this.sender; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundPlayerChatPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundPlayerChatPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundPlayerChatPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundPlayerChatPacket;
/* 14 */     //   0	8	1	$$0	Ljava/lang/Object; } public int index() { return this.index; } @Nullable public MessageSignature signature() { return this.signature; } public SignedMessageBody.Packed body() { return this.body; } @Nullable public Component unsignedContent() { return this.unsignedContent; } public FilterMask filterMask() { return this.filterMask; } public ChatType.BoundNetwork chatType() { return this.chatType; }
/*    */    public ClientboundPlayerChatPacket(FriendlyByteBuf $$0) {
/* 16 */     this($$0
/* 17 */         .readUUID(), $$0
/* 18 */         .readVarInt(), (MessageSignature)$$0
/* 19 */         .readNullable(MessageSignature::read), new SignedMessageBody.Packed($$0), (Component)$$0
/*    */         
/* 21 */         .readNullable(FriendlyByteBuf::readComponentTrusted), 
/* 22 */         FilterMask.read($$0), new ChatType.BoundNetwork($$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 29 */     $$0.writeUUID(this.sender);
/* 30 */     $$0.writeVarInt(this.index);
/* 31 */     $$0.writeNullable(this.signature, MessageSignature::write);
/* 32 */     this.body.write($$0);
/* 33 */     $$0.writeNullable(this.unsignedContent, FriendlyByteBuf::writeComponent);
/* 34 */     FilterMask.write($$0, this.filterMask);
/* 35 */     this.chatType.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 40 */     $$0.handlePlayerChat(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSkippable() {
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundPlayerChatPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */