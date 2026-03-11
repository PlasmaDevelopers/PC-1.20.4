/*    */ package net.minecraft.world.entity.animal;
/*    */ 
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ 
/*    */ public final class CatVariant extends Record {
/*    */   private final ResourceLocation texture;
/*    */   
/*  8 */   public CatVariant(ResourceLocation $$0) { this.texture = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/animal/CatVariant;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  8 */     //   0	7	0	this	Lnet/minecraft/world/entity/animal/CatVariant; } public ResourceLocation texture() { return this.texture; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/animal/CatVariant;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/entity/animal/CatVariant; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/animal/CatVariant;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/entity/animal/CatVariant;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public static final ResourceKey<CatVariant> TABBY = createKey("tabby");
/* 10 */   public static final ResourceKey<CatVariant> BLACK = createKey("black");
/* 11 */   public static final ResourceKey<CatVariant> RED = createKey("red");
/* 12 */   public static final ResourceKey<CatVariant> SIAMESE = createKey("siamese");
/* 13 */   public static final ResourceKey<CatVariant> BRITISH_SHORTHAIR = createKey("british_shorthair");
/* 14 */   public static final ResourceKey<CatVariant> CALICO = createKey("calico");
/* 15 */   public static final ResourceKey<CatVariant> PERSIAN = createKey("persian");
/* 16 */   public static final ResourceKey<CatVariant> RAGDOLL = createKey("ragdoll");
/* 17 */   public static final ResourceKey<CatVariant> WHITE = createKey("white");
/* 18 */   public static final ResourceKey<CatVariant> JELLIE = createKey("jellie");
/* 19 */   public static final ResourceKey<CatVariant> ALL_BLACK = createKey("all_black");
/*    */   
/*    */   private static ResourceKey<CatVariant> createKey(String $$0) {
/* 22 */     return ResourceKey.create(Registries.CAT_VARIANT, new ResourceLocation($$0));
/*    */   }
/*    */   
/*    */   public static CatVariant bootstrap(Registry<CatVariant> $$0) {
/* 26 */     register($$0, TABBY, "textures/entity/cat/tabby.png");
/* 27 */     register($$0, BLACK, "textures/entity/cat/black.png");
/* 28 */     register($$0, RED, "textures/entity/cat/red.png");
/* 29 */     register($$0, SIAMESE, "textures/entity/cat/siamese.png");
/* 30 */     register($$0, BRITISH_SHORTHAIR, "textures/entity/cat/british_shorthair.png");
/* 31 */     register($$0, CALICO, "textures/entity/cat/calico.png");
/* 32 */     register($$0, PERSIAN, "textures/entity/cat/persian.png");
/* 33 */     register($$0, RAGDOLL, "textures/entity/cat/ragdoll.png");
/* 34 */     register($$0, WHITE, "textures/entity/cat/white.png");
/* 35 */     register($$0, JELLIE, "textures/entity/cat/jellie.png");
/* 36 */     return register($$0, ALL_BLACK, "textures/entity/cat/all_black.png");
/*    */   }
/*    */   
/*    */   private static CatVariant register(Registry<CatVariant> $$0, ResourceKey<CatVariant> $$1, String $$2) {
/* 40 */     return (CatVariant)Registry.register($$0, $$1, new CatVariant(new ResourceLocation($$2)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\CatVariant.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */