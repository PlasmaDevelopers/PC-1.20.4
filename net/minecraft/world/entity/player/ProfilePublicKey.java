/*     */ package net.minecraft.world.entity.player;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.PublicKey;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.Crypt;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.SignatureValidator;
/*     */ 
/*     */ public final class ProfilePublicKey extends Record {
/*     */   private final Data data;
/*     */   
/*  22 */   public ProfilePublicKey(Data $$0) { this.data = $$0; } public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/player/ProfilePublicKey;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #22	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  22 */     //   0	7	0	this	Lnet/minecraft/world/entity/player/ProfilePublicKey; } public Data data() { return this.data; }
/*     */   public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/player/ProfilePublicKey;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #22	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/entity/player/ProfilePublicKey; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/player/ProfilePublicKey;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #22	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/world/entity/player/ProfilePublicKey;
/*  23 */     //   0	8	1	$$0	Ljava/lang/Object; } public static final Component EXPIRED_PROFILE_PUBLIC_KEY = (Component)Component.translatable("multiplayer.disconnect.expired_public_key");
/*  24 */   private static final Component INVALID_SIGNATURE = (Component)Component.translatable("multiplayer.disconnect.invalid_public_key_signature.new");
/*  25 */   public static final Duration EXPIRY_GRACE_PERIOD = Duration.ofHours(8L);
/*     */   
/*  27 */   public static final Codec<ProfilePublicKey> TRUSTED_CODEC = Data.CODEC.xmap(ProfilePublicKey::new, ProfilePublicKey::data);
/*     */   
/*     */   public static ProfilePublicKey createValidated(SignatureValidator $$0, UUID $$1, Data $$2) throws ValidationException {
/*  30 */     if (!$$2.validateSignature($$0, $$1)) {
/*  31 */       throw new ValidationException(INVALID_SIGNATURE);
/*     */     }
/*     */     
/*  34 */     return new ProfilePublicKey($$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureValidator createSignatureValidator() {
/*  46 */     return SignatureValidator.from(this.data.key, "SHA256withRSA");
/*     */   }
/*     */   public static final class Data extends Record { private final Instant expiresAt; final PublicKey key; private final byte[] keySignature; private static final int MAX_KEY_SIGNATURE_SIZE = 4096; public static final Codec<Data> CODEC;
/*  49 */     public Data(Instant $$0, PublicKey $$1, byte[] $$2) { this.expiresAt = $$0; this.key = $$1; this.keySignature = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/entity/player/ProfilePublicKey$Data; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  49 */       //   0	7	0	this	Lnet/minecraft/world/entity/player/ProfilePublicKey$Data; } public Instant expiresAt() { return this.expiresAt; } public PublicKey key() { return this.key; } public byte[] keySignature() { return this.keySignature; }
/*     */ 
/*     */     
/*     */     static {
/*  53 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.INSTANT_ISO8601.fieldOf("expires_at").forGetter(Data::expiresAt), (App)Crypt.PUBLIC_KEY_CODEC.fieldOf("key").forGetter(Data::key), (App)ExtraCodecs.BASE64_STRING.fieldOf("signature_v2").forGetter(Data::keySignature)).apply((Applicative)$$0, Data::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Data(FriendlyByteBuf $$0) {
/*  60 */       this($$0
/*  61 */           .readInstant(), $$0
/*  62 */           .readPublicKey(), $$0
/*  63 */           .readByteArray(4096));
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/*  68 */       $$0.writeInstant(this.expiresAt);
/*  69 */       $$0.writePublicKey(this.key);
/*  70 */       $$0.writeByteArray(this.keySignature);
/*     */     }
/*     */     
/*     */     boolean validateSignature(SignatureValidator $$0, UUID $$1) {
/*  74 */       return $$0.validate(signedPayload($$1), this.keySignature);
/*     */     }
/*     */     
/*     */     private byte[] signedPayload(UUID $$0) {
/*  78 */       byte[] $$1 = this.key.getEncoded();
/*  79 */       byte[] $$2 = new byte[24 + $$1.length];
/*     */       
/*  81 */       ByteBuffer $$3 = ByteBuffer.wrap($$2).order(ByteOrder.BIG_ENDIAN);
/*  82 */       $$3.putLong($$0.getMostSignificantBits())
/*  83 */         .putLong($$0.getLeastSignificantBits())
/*  84 */         .putLong(this.expiresAt.toEpochMilli())
/*  85 */         .put($$1);
/*     */       
/*  87 */       return $$2;
/*     */     }
/*     */     
/*     */     public boolean hasExpired() {
/*  91 */       return this.expiresAt.isBefore(Instant.now());
/*     */     }
/*     */     
/*     */     public boolean hasExpired(Duration $$0) {
/*  95 */       return this.expiresAt.plus($$0).isBefore(Instant.now());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/* 100 */       if ($$0 instanceof Data) { Data $$1 = (Data)$$0;
/* 101 */         return (this.expiresAt.equals($$1.expiresAt) && this.key.equals($$1.key) && Arrays.equals(this.keySignature, $$1.keySignature)); }
/*     */       
/* 103 */       return false;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class ValidationException extends ThrowingComponent {
/*     */     public ValidationException(Component $$0) {
/* 109 */       super($$0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\player\ProfilePublicKey.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */