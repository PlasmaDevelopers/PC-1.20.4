/*    */ package net.minecraft.client.resources;public final class PlayerSkin extends Record { private final ResourceLocation texture; @Nullable
/*    */   private final String textureUrl; @Nullable
/*    */   private final ResourceLocation capeTexture; @Nullable
/*    */   private final ResourceLocation elytraTexture; private final Model model;
/*    */   private final boolean secure;
/*    */   
/*  7 */   public PlayerSkin(ResourceLocation $$0, @Nullable String $$1, @Nullable ResourceLocation $$2, @Nullable ResourceLocation $$3, Model $$4, boolean $$5) { this.texture = $$0; this.textureUrl = $$1; this.capeTexture = $$2; this.elytraTexture = $$3; this.model = $$4; this.secure = $$5; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/PlayerSkin;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/client/resources/PlayerSkin; } public ResourceLocation texture() { return this.texture; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/PlayerSkin;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/resources/PlayerSkin; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/PlayerSkin;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/resources/PlayerSkin;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } @Nullable public String textureUrl() { return this.textureUrl; } @Nullable public ResourceLocation capeTexture() { return this.capeTexture; } @Nullable public ResourceLocation elytraTexture() { return this.elytraTexture; } public Model model() { return this.model; } public boolean secure() { return this.secure; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum Model
/*    */   {
/* 16 */     SLIM("slim"),
/* 17 */     WIDE("default");
/*    */     
/*    */     private final String id;
/*    */     
/*    */     Model(String $$0) {
/* 22 */       this.id = $$0;
/*    */     }
/*    */     
/*    */     public static Model byName(@Nullable String $$0) {
/* 26 */       if ($$0 == null)
/*    */       {
/* 28 */         return WIDE;
/*    */       }
/* 30 */       switch ($$0) { case "slim":  }  return 
/*    */         
/* 32 */         WIDE;
/*    */     }
/*    */ 
/*    */     
/*    */     public String id() {
/* 37 */       return this.id;
/*    */     }
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\PlayerSkin.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */