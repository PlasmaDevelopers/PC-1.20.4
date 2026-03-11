/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.SignatureUpdater;
/*     */ 
/*     */ public final class PlayerChatMessage extends Record {
/*     */   private final SignedMessageLink link;
/*     */   @Nullable
/*     */   private final MessageSignature signature;
/*     */   private final SignedMessageBody signedBody;
/*     */   @Nullable
/*     */   private final Component unsignedContent;
/*     */   private final FilterMask filterMask;
/*     */   public static final MapCodec<PlayerChatMessage> MAP_CODEC;
/*     */   
/*  19 */   public PlayerChatMessage(SignedMessageLink $$0, @Nullable MessageSignature $$1, SignedMessageBody $$2, @Nullable Component $$3, FilterMask $$4) { this.link = $$0; this.signature = $$1; this.signedBody = $$2; this.unsignedContent = $$3; this.filterMask = $$4; } public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/PlayerChatMessage;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #19	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  19 */     //   0	7	0	this	Lnet/minecraft/network/chat/PlayerChatMessage; } public SignedMessageLink link() { return this.link; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/PlayerChatMessage;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #19	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/network/chat/PlayerChatMessage; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/PlayerChatMessage;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #19	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/network/chat/PlayerChatMessage;
/*  19 */     //   0	8	1	$$0	Ljava/lang/Object; } @Nullable public MessageSignature signature() { return this.signature; } public SignedMessageBody signedBody() { return this.signedBody; } @Nullable public Component unsignedContent() { return this.unsignedContent; } public FilterMask filterMask() { return this.filterMask; } static {
/*  20 */     MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)SignedMessageLink.CODEC.fieldOf("link").forGetter(PlayerChatMessage::link), (App)MessageSignature.CODEC.optionalFieldOf("signature").forGetter(()), (App)SignedMessageBody.MAP_CODEC.forGetter(PlayerChatMessage::signedBody), (App)ComponentSerialization.CODEC.optionalFieldOf("unsigned_content").forGetter(()), (App)FilterMask.CODEC.optionalFieldOf("filter_mask", FilterMask.PASS_THROUGH).forGetter(PlayerChatMessage::filterMask)).apply((Applicative)$$0, ()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   private static final UUID SYSTEM_SENDER = Util.NIL_UUID;
/*  29 */   public static final Duration MESSAGE_EXPIRES_AFTER_SERVER = Duration.ofMinutes(5L);
/*  30 */   public static final Duration MESSAGE_EXPIRES_AFTER_CLIENT = MESSAGE_EXPIRES_AFTER_SERVER.plus(Duration.ofMinutes(2L));
/*     */   
/*     */   public static PlayerChatMessage system(String $$0) {
/*  33 */     return unsigned(SYSTEM_SENDER, $$0);
/*     */   }
/*     */   
/*     */   public static PlayerChatMessage unsigned(UUID $$0, String $$1) {
/*  37 */     SignedMessageBody $$2 = SignedMessageBody.unsigned($$1);
/*  38 */     SignedMessageLink $$3 = SignedMessageLink.unsigned($$0);
/*  39 */     return new PlayerChatMessage($$3, null, $$2, null, FilterMask.PASS_THROUGH);
/*     */   }
/*     */   
/*     */   public PlayerChatMessage withUnsignedContent(Component $$0) {
/*  43 */     Component $$1 = !$$0.equals(Component.literal(signedContent())) ? $$0 : null;
/*  44 */     return new PlayerChatMessage(this.link, this.signature, this.signedBody, $$1, this.filterMask);
/*     */   }
/*     */   
/*     */   public PlayerChatMessage removeUnsignedContent() {
/*  48 */     if (this.unsignedContent != null) {
/*  49 */       return new PlayerChatMessage(this.link, this.signature, this.signedBody, null, this.filterMask);
/*     */     }
/*  51 */     return this;
/*     */   }
/*     */   
/*     */   public PlayerChatMessage filter(FilterMask $$0) {
/*  55 */     if (this.filterMask.equals($$0)) {
/*  56 */       return this;
/*     */     }
/*  58 */     return new PlayerChatMessage(this.link, this.signature, this.signedBody, this.unsignedContent, $$0);
/*     */   }
/*     */   
/*     */   public PlayerChatMessage filter(boolean $$0) {
/*  62 */     return filter($$0 ? this.filterMask : FilterMask.PASS_THROUGH);
/*     */   }
/*     */   
/*     */   public PlayerChatMessage removeSignature() {
/*  66 */     SignedMessageBody $$0 = SignedMessageBody.unsigned(signedContent());
/*  67 */     SignedMessageLink $$1 = SignedMessageLink.unsigned(sender());
/*  68 */     return new PlayerChatMessage($$1, null, $$0, this.unsignedContent, this.filterMask);
/*     */   }
/*     */   
/*     */   public static void updateSignature(SignatureUpdater.Output $$0, SignedMessageLink $$1, SignedMessageBody $$2) throws SignatureException {
/*  72 */     $$0.update(Ints.toByteArray(1));
/*  73 */     $$1.updateSignature($$0);
/*  74 */     $$2.updateSignature($$0);
/*     */   }
/*     */   
/*     */   public boolean verify(SignatureValidator $$0) {
/*  78 */     return (this.signature != null && this.signature.verify($$0, $$0 -> updateSignature($$0, this.link, this.signedBody)));
/*     */   }
/*     */   
/*     */   public String signedContent() {
/*  82 */     return this.signedBody.content();
/*     */   }
/*     */   
/*     */   public Component decoratedContent() {
/*  86 */     return Objects.<Component>requireNonNullElseGet(this.unsignedContent, () -> Component.literal(signedContent()));
/*     */   }
/*     */   
/*     */   public Instant timeStamp() {
/*  90 */     return this.signedBody.timeStamp();
/*     */   }
/*     */   
/*     */   public long salt() {
/*  94 */     return this.signedBody.salt();
/*     */   }
/*     */   
/*     */   public boolean hasExpiredServer(Instant $$0) {
/*  98 */     return $$0.isAfter(timeStamp().plus(MESSAGE_EXPIRES_AFTER_SERVER));
/*     */   }
/*     */   
/*     */   public boolean hasExpiredClient(Instant $$0) {
/* 102 */     return $$0.isAfter(timeStamp().plus(MESSAGE_EXPIRES_AFTER_CLIENT));
/*     */   }
/*     */   
/*     */   public UUID sender() {
/* 106 */     return this.link.sender();
/*     */   }
/*     */   
/*     */   public boolean isSystem() {
/* 110 */     return sender().equals(SYSTEM_SENDER);
/*     */   }
/*     */   
/*     */   public boolean hasSignature() {
/* 114 */     return (this.signature != null);
/*     */   }
/*     */   
/*     */   public boolean hasSignatureFrom(UUID $$0) {
/* 118 */     return (hasSignature() && this.link.sender().equals($$0));
/*     */   }
/*     */   
/*     */   public boolean isFullyFiltered() {
/* 122 */     return this.filterMask.isFullyFiltered();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\PlayerChatMessage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */