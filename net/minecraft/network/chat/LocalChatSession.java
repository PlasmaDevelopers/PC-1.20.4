/*    */ package net.minecraft.network.chat;
/*    */ public final class LocalChatSession extends Record { private final UUID sessionId; private final ProfileKeyPair keyPair;
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/LocalChatSession;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/LocalChatSession;
/*    */   }
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/LocalChatSession;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/LocalChatSession;
/*    */   }
/*  9 */   public LocalChatSession(UUID $$0, ProfileKeyPair $$1) { this.sessionId = $$0; this.keyPair = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/LocalChatSession;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/chat/LocalChatSession;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public UUID sessionId() { return this.sessionId; } public ProfileKeyPair keyPair() { return this.keyPair; }
/*    */    public static LocalChatSession create(ProfileKeyPair $$0) {
/* 11 */     return new LocalChatSession(UUID.randomUUID(), $$0);
/*    */   }
/*    */   
/*    */   public SignedMessageChain.Encoder createMessageEncoder(UUID $$0) {
/* 15 */     return (new SignedMessageChain($$0, this.sessionId)).encoder(Signer.from(this.keyPair.privateKey(), "SHA256withRSA"));
/*    */   }
/*    */   
/*    */   public RemoteChatSession asRemote() {
/* 19 */     return new RemoteChatSession(this.sessionId, this.keyPair.publicKey());
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\LocalChatSession.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */