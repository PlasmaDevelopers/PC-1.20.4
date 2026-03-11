/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.entity.player.ProfilePublicKey;
/*    */ 
/*    */ public final class RemoteChatSession extends Record {
/*    */   private final UUID sessionId;
/*    */   private final ProfilePublicKey profilePublicKey;
/*    */   
/* 11 */   public RemoteChatSession(UUID $$0, ProfilePublicKey $$1) { this.sessionId = $$0; this.profilePublicKey = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/RemoteChatSession;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/network/chat/RemoteChatSession; } public UUID sessionId() { return this.sessionId; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/RemoteChatSession;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/RemoteChatSession; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/RemoteChatSession;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/chat/RemoteChatSession;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public ProfilePublicKey profilePublicKey() { return this.profilePublicKey; }
/*    */    public SignedMessageValidator createMessageValidator(Duration $$0) {
/* 13 */     return new SignedMessageValidator.KeyBased(this.profilePublicKey
/* 14 */         .createSignatureValidator(), () -> this.profilePublicKey.data().hasExpired($$0));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public SignedMessageChain.Decoder createMessageDecoder(UUID $$0) {
/* 20 */     return (new SignedMessageChain($$0, this.sessionId)).decoder(this.profilePublicKey);
/*    */   }
/*    */   
/*    */   public Data asData() {
/* 24 */     return new Data(this.sessionId, this.profilePublicKey.data());
/*    */   }
/*    */   
/*    */   public boolean hasExpired() {
/* 28 */     return this.profilePublicKey.data().hasExpired();
/*    */   }
/*    */   public static final class Data extends Record { private final UUID sessionId; private final ProfilePublicKey.Data profilePublicKey;
/* 31 */     public Data(UUID $$0, ProfilePublicKey.Data $$1) { this.sessionId = $$0; this.profilePublicKey = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/RemoteChatSession$Data;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #31	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/RemoteChatSession$Data; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/RemoteChatSession$Data;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #31	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/RemoteChatSession$Data; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/RemoteChatSession$Data;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #31	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/chat/RemoteChatSession$Data;
/* 31 */       //   0	8	1	$$0	Ljava/lang/Object; } public UUID sessionId() { return this.sessionId; } public ProfilePublicKey.Data profilePublicKey() { return this.profilePublicKey; }
/*    */      public static Data read(FriendlyByteBuf $$0) {
/* 33 */       return new Data($$0.readUUID(), new ProfilePublicKey.Data($$0));
/*    */     }
/*    */     
/*    */     public static void write(FriendlyByteBuf $$0, Data $$1) {
/* 37 */       $$0.writeUUID($$1.sessionId);
/* 38 */       $$1.profilePublicKey.write($$0);
/*    */     }
/*    */     
/*    */     public RemoteChatSession validate(GameProfile $$0, SignatureValidator $$1) throws ProfilePublicKey.ValidationException {
/* 42 */       return new RemoteChatSession(this.sessionId, ProfilePublicKey.createValidated($$1, $$0.getId(), this.profilePublicKey));
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\RemoteChatSession.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */