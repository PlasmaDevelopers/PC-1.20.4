/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.util.SignatureValidator;
/*    */ import net.minecraft.world.entity.player.ProfilePublicKey;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Data
/*    */   extends Record
/*    */ {
/*    */   private final UUID sessionId;
/*    */   private final ProfilePublicKey.Data profilePublicKey;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/RemoteChatSession$Data;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #31	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/RemoteChatSession$Data;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/RemoteChatSession$Data;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #31	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/RemoteChatSession$Data;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/RemoteChatSession$Data;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #31	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/chat/RemoteChatSession$Data;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public Data(UUID $$0, ProfilePublicKey.Data $$1) {
/* 31 */     this.sessionId = $$0; this.profilePublicKey = $$1; } public UUID sessionId() { return this.sessionId; } public ProfilePublicKey.Data profilePublicKey() { return this.profilePublicKey; }
/*    */    public static Data read(FriendlyByteBuf $$0) {
/* 33 */     return new Data($$0.readUUID(), new ProfilePublicKey.Data($$0));
/*    */   }
/*    */   
/*    */   public static void write(FriendlyByteBuf $$0, Data $$1) {
/* 37 */     $$0.writeUUID($$1.sessionId);
/* 38 */     $$1.profilePublicKey.write($$0);
/*    */   }
/*    */   
/*    */   public RemoteChatSession validate(GameProfile $$0, SignatureValidator $$1) throws ProfilePublicKey.ValidationException {
/* 42 */     return new RemoteChatSession(this.sessionId, ProfilePublicKey.createValidated($$1, $$0.getId(), this.profilePublicKey));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\RemoteChatSession$Data.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */