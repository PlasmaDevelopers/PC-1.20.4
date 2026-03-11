/*    */ package net.minecraft.world.entity.animal;
/*    */ 
/*    */ 
/*    */ public final class FrogVariant extends Record {
/*    */   private final ResourceLocation texture;
/*    */   
/*  7 */   public FrogVariant(ResourceLocation $$0) { this.texture = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/animal/FrogVariant;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/world/entity/animal/FrogVariant; } public ResourceLocation texture() { return this.texture; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/animal/FrogVariant;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/entity/animal/FrogVariant; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/animal/FrogVariant;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/entity/animal/FrogVariant;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } public static final FrogVariant TEMPERATE = register("temperate", "textures/entity/frog/temperate_frog.png");
/*  9 */   public static final FrogVariant WARM = register("warm", "textures/entity/frog/warm_frog.png");
/* 10 */   public static final FrogVariant COLD = register("cold", "textures/entity/frog/cold_frog.png");
/*    */   
/*    */   private static FrogVariant register(String $$0, String $$1) {
/* 13 */     return (FrogVariant)Registry.register(BuiltInRegistries.FROG_VARIANT, $$0, new FrogVariant(new ResourceLocation($$1)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\FrogVariant.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */