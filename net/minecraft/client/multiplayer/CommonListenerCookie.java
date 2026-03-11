/*    */ package net.minecraft.client.multiplayer;public final class CommonListenerCookie extends Record { private final GameProfile localGameProfile; private final WorldSessionTelemetryManager telemetryManager;
/*    */   private final RegistryAccess.Frozen receivedRegistries;
/*    */   private final FeatureFlagSet enabledFeatures;
/*    */   @Nullable
/*    */   private final String serverBrand;
/*    */   @Nullable
/*    */   private final ServerData serverData;
/*    */   @Nullable
/*    */   private final Screen postDisconnectScreen;
/*    */   
/* 11 */   public CommonListenerCookie(GameProfile $$0, WorldSessionTelemetryManager $$1, RegistryAccess.Frozen $$2, FeatureFlagSet $$3, @Nullable String $$4, @Nullable ServerData $$5, @Nullable Screen $$6) { this.localGameProfile = $$0; this.telemetryManager = $$1; this.receivedRegistries = $$2; this.enabledFeatures = $$3; this.serverBrand = $$4; this.serverData = $$5; this.postDisconnectScreen = $$6; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/CommonListenerCookie;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/client/multiplayer/CommonListenerCookie; } public GameProfile localGameProfile() { return this.localGameProfile; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/CommonListenerCookie;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/multiplayer/CommonListenerCookie; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/CommonListenerCookie;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/multiplayer/CommonListenerCookie;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public WorldSessionTelemetryManager telemetryManager() { return this.telemetryManager; } public RegistryAccess.Frozen receivedRegistries() { return this.receivedRegistries; } public FeatureFlagSet enabledFeatures() { return this.enabledFeatures; } @Nullable public String serverBrand() { return this.serverBrand; } @Nullable public ServerData serverData() { return this.serverData; } @Nullable public Screen postDisconnectScreen() { return this.postDisconnectScreen; }
/*    */    }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\CommonListenerCookie.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */