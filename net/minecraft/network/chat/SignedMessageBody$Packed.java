/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import java.time.Instant;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Packed
/*    */   extends Record
/*    */ {
/*    */   private final String content;
/*    */   private final Instant timeStamp;
/*    */   private final long salt;
/*    */   private final LastSeenMessages.Packed lastSeen;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/SignedMessageBody$Packed;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #43	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/SignedMessageBody$Packed;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/SignedMessageBody$Packed;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #43	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/SignedMessageBody$Packed;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/SignedMessageBody$Packed;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #43	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/chat/SignedMessageBody$Packed;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public Packed(String $$0, Instant $$1, long $$2, LastSeenMessages.Packed $$3) {
/* 43 */     this.content = $$0; this.timeStamp = $$1; this.salt = $$2; this.lastSeen = $$3; } public String content() { return this.content; } public Instant timeStamp() { return this.timeStamp; } public long salt() { return this.salt; } public LastSeenMessages.Packed lastSeen() { return this.lastSeen; }
/*    */    public Packed(FriendlyByteBuf $$0) {
/* 45 */     this($$0.readUtf(256), $$0.readInstant(), $$0.readLong(), new LastSeenMessages.Packed($$0));
/*    */   }
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 49 */     $$0.writeUtf(this.content, 256);
/* 50 */     $$0.writeInstant(this.timeStamp);
/* 51 */     $$0.writeLong(this.salt);
/* 52 */     this.lastSeen.write($$0);
/*    */   }
/*    */   
/*    */   public Optional<SignedMessageBody> unpack(MessageSignatureCache $$0) {
/* 56 */     return this.lastSeen.unpack($$0).map($$0 -> new SignedMessageBody(this.content, this.timeStamp, this.salt, $$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\SignedMessageBody$Packed.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */