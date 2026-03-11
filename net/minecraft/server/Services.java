/*    */ package net.minecraft.server;
/*    */ 
/*    */ import com.mojang.authlib.GameProfileRepository;
/*    */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*    */ import net.minecraft.server.players.GameProfileCache;
/*    */ 
/*    */ public final class Services extends Record {
/*    */   private final MinecraftSessionService sessionService;
/*    */   private final ServicesKeySet servicesKeySet;
/*    */   private final GameProfileRepository profileRepository;
/*    */   private final GameProfileCache profileCache;
/*    */   private static final String USERID_CACHE_FILE = "usercache.json";
/*    */   
/* 14 */   public Services(MinecraftSessionService $$0, ServicesKeySet $$1, GameProfileRepository $$2, GameProfileCache $$3) { this.sessionService = $$0; this.servicesKeySet = $$1; this.profileRepository = $$2; this.profileCache = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/server/Services;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 14 */     //   0	7	0	this	Lnet/minecraft/server/Services; } public MinecraftSessionService sessionService() { return this.sessionService; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/Services;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/server/Services; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/Services;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/server/Services;
/* 14 */     //   0	8	1	$$0	Ljava/lang/Object; } public ServicesKeySet servicesKeySet() { return this.servicesKeySet; } public GameProfileRepository profileRepository() { return this.profileRepository; } public GameProfileCache profileCache() { return this.profileCache; }
/*    */ 
/*    */   
/*    */   public static Services create(YggdrasilAuthenticationService $$0, File $$1) {
/* 18 */     MinecraftSessionService $$2 = $$0.createMinecraftSessionService();
/* 19 */     GameProfileRepository $$3 = $$0.createProfileRepository();
/* 20 */     GameProfileCache $$4 = new GameProfileCache($$3, new File($$1, "usercache.json"));
/* 21 */     return new Services($$2, $$0.getServicesKeySet(), $$3, $$4);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public SignatureValidator profileKeySignatureValidator() {
/* 26 */     return SignatureValidator.from(this.servicesKeySet, ServicesKeyType.PROFILE_KEY);
/*    */   }
/*    */   
/*    */   public boolean canValidateProfileKeys() {
/* 30 */     return !this.servicesKeySet.keys(ServicesKeyType.PROFILE_KEY).isEmpty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\Services.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */