/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ 
/*    */ public interface OutgoingChatMessage {
/*    */   Component content();
/*    */   
/*    */   void sendToPlayer(ServerPlayer paramServerPlayer, boolean paramBoolean, ChatType.Bound paramBound);
/*    */   
/*    */   static OutgoingChatMessage create(PlayerChatMessage $$0) {
/* 11 */     if ($$0.isSystem()) {
/* 12 */       return new Disguised($$0.decoratedContent());
/*    */     }
/* 14 */     return new Player($$0);
/*    */   }
/*    */   public static final class Player extends Record implements OutgoingChatMessage { private final PlayerChatMessage message;
/* 17 */     public Player(PlayerChatMessage $$0) { this.message = $$0; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/OutgoingChatMessage$Player;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #17	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 17 */       //   0	7	0	this	Lnet/minecraft/network/chat/OutgoingChatMessage$Player; } public PlayerChatMessage message() { return this.message; }
/*    */     public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/OutgoingChatMessage$Player;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #17	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/OutgoingChatMessage$Player; }
/*    */     public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/OutgoingChatMessage$Player;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #17	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/chat/OutgoingChatMessage$Player;
/*    */       //   0	8	1	$$0	Ljava/lang/Object; } public Component content() {
/* 20 */       return this.message.decoratedContent();
/*    */     }
/*    */ 
/*    */     
/*    */     public void sendToPlayer(ServerPlayer $$0, boolean $$1, ChatType.Bound $$2) {
/* 25 */       PlayerChatMessage $$3 = this.message.filter($$1);
/* 26 */       if (!$$3.isFullyFiltered())
/* 27 */         $$0.connection.sendPlayerChatMessage($$3, $$2); 
/*    */     } }
/*    */   
/*    */   public static final class Disguised extends Record implements OutgoingChatMessage { private final Component content;
/*    */     
/* 32 */     public Disguised(Component $$0) { this.content = $$0; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/OutgoingChatMessage$Disguised;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #32	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/OutgoingChatMessage$Disguised; }
/*    */     public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/OutgoingChatMessage$Disguised;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #32	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/OutgoingChatMessage$Disguised; }
/*    */     public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/OutgoingChatMessage$Disguised;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #32	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/chat/OutgoingChatMessage$Disguised;
/*    */       //   0	8	1	$$0	Ljava/lang/Object; }
/* 35 */     public Component content() { return this.content; }
/*    */ 
/*    */ 
/*    */     
/*    */     public void sendToPlayer(ServerPlayer $$0, boolean $$1, ChatType.Bound $$2) {
/* 40 */       $$0.connection.sendDisguisedChatMessage(this.content, $$2);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\OutgoingChatMessage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */