/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ 
/*    */ public final class ClientboundDisguisedChatPacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final Component message;
/*    */   private final ChatType.BoundNetwork chatType;
/*    */   
/*  8 */   public ClientboundDisguisedChatPacket(Component $$0, ChatType.BoundNetwork $$1) { this.message = $$0; this.chatType = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundDisguisedChatPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  8 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundDisguisedChatPacket; } public Component message() { return this.message; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundDisguisedChatPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundDisguisedChatPacket; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundDisguisedChatPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundDisguisedChatPacket;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } public ChatType.BoundNetwork chatType() { return this.chatType; }
/*    */    public ClientboundDisguisedChatPacket(FriendlyByteBuf $$0) {
/* 10 */     this($$0.readComponentTrusted(), new ChatType.BoundNetwork($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 15 */     $$0.writeComponent(this.message);
/* 16 */     this.chatType.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 21 */     $$0.handleDisguisedChat(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSkippable() {
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundDisguisedChatPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */