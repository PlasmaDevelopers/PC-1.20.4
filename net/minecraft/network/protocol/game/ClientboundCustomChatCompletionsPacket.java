/*    */ package net.minecraft.network.protocol.game;
/*    */ public final class ClientboundCustomChatCompletionsPacket extends Record implements Packet<ClientGamePacketListener> { private final Action action; private final List<String> entries;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundCustomChatCompletionsPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundCustomChatCompletionsPacket;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundCustomChatCompletionsPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundCustomChatCompletionsPacket;
/*    */   }
/*    */   
/* 12 */   public ClientboundCustomChatCompletionsPacket(Action $$0, List<String> $$1) { this.action = $$0; this.entries = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundCustomChatCompletionsPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundCustomChatCompletionsPacket;
/* 12 */     //   0	8	1	$$0	Ljava/lang/Object; } public Action action() { return this.action; } public List<String> entries() { return this.entries; }
/*    */   
/* 14 */   public enum Action { ADD,
/* 15 */     REMOVE,
/* 16 */     SET; }
/*    */ 
/*    */   
/*    */   public ClientboundCustomChatCompletionsPacket(FriendlyByteBuf $$0) {
/* 20 */     this((Action)$$0
/* 21 */         .readEnum(Action.class), $$0
/* 22 */         .readList(FriendlyByteBuf::readUtf));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 28 */     $$0.writeEnum(this.action);
/* 29 */     $$0.writeCollection(this.entries, FriendlyByteBuf::writeUtf);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 34 */     $$0.handleCustomChatCompletions(this);
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundCustomChatCompletionsPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */