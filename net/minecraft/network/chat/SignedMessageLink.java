/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.core.UUIDUtil;
/*    */ import net.minecraft.util.SignatureUpdater;
/*    */ 
/*    */ public final class SignedMessageLink extends Record {
/*    */   private final int index;
/*    */   private final UUID sender;
/*    */   private final UUID sessionId;
/*    */   public static final Codec<SignedMessageLink> CODEC;
/*    */   
/* 15 */   public SignedMessageLink(int $$0, UUID $$1, UUID $$2) { this.index = $$0; this.sender = $$1; this.sessionId = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/SignedMessageLink;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 15 */     //   0	7	0	this	Lnet/minecraft/network/chat/SignedMessageLink; } public int index() { return this.index; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/SignedMessageLink;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/SignedMessageLink; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/SignedMessageLink;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/chat/SignedMessageLink;
/* 15 */     //   0	8	1	$$0	Ljava/lang/Object; } public UUID sender() { return this.sender; } public UUID sessionId() { return this.sessionId; } static {
/* 16 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("index").forGetter(SignedMessageLink::index), (App)UUIDUtil.CODEC.fieldOf("sender").forGetter(SignedMessageLink::sender), (App)UUIDUtil.CODEC.fieldOf("session_id").forGetter(SignedMessageLink::sessionId)).apply((Applicative)$$0, SignedMessageLink::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SignedMessageLink unsigned(UUID $$0) {
/* 23 */     return root($$0, Util.NIL_UUID);
/*    */   }
/*    */   
/*    */   public static SignedMessageLink root(UUID $$0, UUID $$1) {
/* 27 */     return new SignedMessageLink(0, $$0, $$1);
/*    */   }
/*    */   
/*    */   public void updateSignature(SignatureUpdater.Output $$0) throws SignatureException {
/* 31 */     $$0.update(UUIDUtil.uuidToByteArray(this.sender));
/* 32 */     $$0.update(UUIDUtil.uuidToByteArray(this.sessionId));
/* 33 */     $$0.update(Ints.toByteArray(this.index));
/*    */   }
/*    */   
/*    */   public boolean isDescendantOf(SignedMessageLink $$0) {
/* 37 */     return (this.index > $$0.index() && this.sender.equals($$0.sender()) && this.sessionId.equals($$0.sessionId()));
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public SignedMessageLink advance() {
/* 42 */     if (this.index == Integer.MAX_VALUE) {
/* 43 */       return null;
/*    */     }
/* 45 */     return new SignedMessageLink(this.index + 1, this.sender, this.sessionId);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\SignedMessageLink.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */