/*    */ package net.minecraft.world.entity.player;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ 
/*    */ public final class ProfileKeyPair extends Record {
/*    */   private final PrivateKey privateKey;
/*    */   private final ProfilePublicKey publicKey;
/*    */   private final Instant refreshedAfter;
/*    */   public static final Codec<ProfileKeyPair> CODEC;
/*    */   
/* 11 */   public ProfileKeyPair(PrivateKey $$0, ProfilePublicKey $$1, Instant $$2) { this.privateKey = $$0; this.publicKey = $$1; this.refreshedAfter = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/player/ProfileKeyPair;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/world/entity/player/ProfileKeyPair; } public PrivateKey privateKey() { return this.privateKey; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/player/ProfileKeyPair;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/entity/player/ProfileKeyPair; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/player/ProfileKeyPair;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/entity/player/ProfileKeyPair;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public ProfilePublicKey publicKey() { return this.publicKey; } public Instant refreshedAfter() { return this.refreshedAfter; } static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Crypt.PRIVATE_KEY_CODEC.fieldOf("private_key").forGetter(ProfileKeyPair::privateKey), (App)ProfilePublicKey.TRUSTED_CODEC.fieldOf("public_key").forGetter(ProfileKeyPair::publicKey), (App)ExtraCodecs.INSTANT_ISO8601.fieldOf("refreshed_after").forGetter(ProfileKeyPair::refreshedAfter)).apply((Applicative)$$0, ProfileKeyPair::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean dueRefresh() {
/* 19 */     return this.refreshedAfter.isBefore(Instant.now());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\player\ProfileKeyPair.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */