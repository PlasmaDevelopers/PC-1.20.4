/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Player
/*    */   extends Record
/*    */   implements OutgoingChatMessage
/*    */ {
/*    */   private final PlayerChatMessage message;
/*    */   
/*    */   public Player(PlayerChatMessage $$0) {
/* 17 */     this.message = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/OutgoingChatMessage$Player;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 17 */     //   0	7	0	this	Lnet/minecraft/network/chat/OutgoingChatMessage$Player; } public PlayerChatMessage message() { return this.message; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/OutgoingChatMessage$Player;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/OutgoingChatMessage$Player; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/OutgoingChatMessage$Player;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/chat/OutgoingChatMessage$Player;
/*    */     //   0	8	1	$$0	Ljava/lang/Object; } public Component content() {
/* 20 */     return this.message.decoratedContent();
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendToPlayer(ServerPlayer $$0, boolean $$1, ChatType.Bound $$2) {
/* 25 */     PlayerChatMessage $$3 = this.message.filter($$1);
/* 26 */     if (!$$3.isFullyFiltered())
/* 27 */       $$0.connection.sendPlayerChatMessage($$3, $$2); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\OutgoingChatMessage$Player.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */