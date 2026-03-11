/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ 
/*    */ public final class ClientboundDeleteChatPacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final MessageSignature.Packed messageSignature;
/*    */   
/*  7 */   public ClientboundDeleteChatPacket(MessageSignature.Packed $$0) { this.messageSignature = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundDeleteChatPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundDeleteChatPacket; } public MessageSignature.Packed messageSignature() { return this.messageSignature; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundDeleteChatPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundDeleteChatPacket; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundDeleteChatPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundDeleteChatPacket;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public ClientboundDeleteChatPacket(FriendlyByteBuf $$0) { this(MessageSignature.Packed.read($$0)); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 14 */     MessageSignature.Packed.write($$0, this.messageSignature);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 19 */     $$0.handleDeleteChat(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundDeleteChatPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */