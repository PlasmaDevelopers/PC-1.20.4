/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.time.Instant;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.util.SignatureUpdater;
/*    */ 
/*    */ public final class SignedMessageBody extends Record {
/*    */   private final String content;
/*    */   private final Instant timeStamp;
/*    */   private final long salt;
/*    */   private final LastSeenMessages lastSeen;
/*    */   public static final MapCodec<SignedMessageBody> MAP_CODEC;
/*    */   
/* 18 */   public SignedMessageBody(String $$0, Instant $$1, long $$2, LastSeenMessages $$3) { this.content = $$0; this.timeStamp = $$1; this.salt = $$2; this.lastSeen = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/SignedMessageBody;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 18 */     //   0	7	0	this	Lnet/minecraft/network/chat/SignedMessageBody; } public String content() { return this.content; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/SignedMessageBody;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/SignedMessageBody; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/SignedMessageBody;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/chat/SignedMessageBody;
/* 18 */     //   0	8	1	$$0	Ljava/lang/Object; } public Instant timeStamp() { return this.timeStamp; } public long salt() { return this.salt; } public LastSeenMessages lastSeen() { return this.lastSeen; } static {
/* 19 */     MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.STRING.fieldOf("content").forGetter(SignedMessageBody::content), (App)ExtraCodecs.INSTANT_ISO8601.fieldOf("time_stamp").forGetter(SignedMessageBody::timeStamp), (App)Codec.LONG.fieldOf("salt").forGetter(SignedMessageBody::salt), (App)LastSeenMessages.CODEC.optionalFieldOf("last_seen", LastSeenMessages.EMPTY).forGetter(SignedMessageBody::lastSeen)).apply((Applicative)$$0, SignedMessageBody::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SignedMessageBody unsigned(String $$0) {
/* 27 */     return new SignedMessageBody($$0, Instant.now(), 0L, LastSeenMessages.EMPTY);
/*    */   }
/*    */   
/*    */   public void updateSignature(SignatureUpdater.Output $$0) throws SignatureException {
/* 31 */     $$0.update(Longs.toByteArray(this.salt));
/* 32 */     $$0.update(Longs.toByteArray(this.timeStamp.getEpochSecond()));
/* 33 */     byte[] $$1 = this.content.getBytes(StandardCharsets.UTF_8);
/* 34 */     $$0.update(Ints.toByteArray($$1.length));
/* 35 */     $$0.update($$1);
/* 36 */     this.lastSeen.updateSignature($$0);
/*    */   }
/*    */   
/*    */   public Packed pack(MessageSignatureCache $$0) {
/* 40 */     return new Packed(this.content, this.timeStamp, this.salt, this.lastSeen.pack($$0));
/*    */   }
/*    */   public static final class Packed extends Record { private final String content; private final Instant timeStamp; private final long salt; private final LastSeenMessages.Packed lastSeen;
/* 43 */     public Packed(String $$0, Instant $$1, long $$2, LastSeenMessages.Packed $$3) { this.content = $$0; this.timeStamp = $$1; this.salt = $$2; this.lastSeen = $$3; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/SignedMessageBody$Packed;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #43	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/SignedMessageBody$Packed; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/SignedMessageBody$Packed;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #43	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/SignedMessageBody$Packed; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/SignedMessageBody$Packed;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #43	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/chat/SignedMessageBody$Packed;
/* 43 */       //   0	8	1	$$0	Ljava/lang/Object; } public String content() { return this.content; } public Instant timeStamp() { return this.timeStamp; } public long salt() { return this.salt; } public LastSeenMessages.Packed lastSeen() { return this.lastSeen; }
/*    */      public Packed(FriendlyByteBuf $$0) {
/* 45 */       this($$0.readUtf(256), $$0.readInstant(), $$0.readLong(), new LastSeenMessages.Packed($$0));
/*    */     }
/*    */     
/*    */     public void write(FriendlyByteBuf $$0) {
/* 49 */       $$0.writeUtf(this.content, 256);
/* 50 */       $$0.writeInstant(this.timeStamp);
/* 51 */       $$0.writeLong(this.salt);
/* 52 */       this.lastSeen.write($$0);
/*    */     }
/*    */     
/*    */     public Optional<SignedMessageBody> unpack(MessageSignatureCache $$0) {
/* 56 */       return this.lastSeen.unpack($$0).map($$0 -> new SignedMessageBody(this.content, this.timeStamp, this.salt, $$0));
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\SignedMessageBody.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */