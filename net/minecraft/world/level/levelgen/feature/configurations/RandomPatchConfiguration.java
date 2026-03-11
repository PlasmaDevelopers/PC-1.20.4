/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ public final class RandomPatchConfiguration extends Record implements FeatureConfiguration {
/*    */   private final int tries;
/*    */   private final int xzSpread;
/*    */   private final int ySpread;
/*    */   private final Holder<PlacedFeature> feature;
/*    */   public static final Codec<RandomPatchConfiguration> CODEC;
/*    */   
/*  9 */   public RandomPatchConfiguration(int $$0, int $$1, int $$2, Holder<PlacedFeature> $$3) { this.tries = $$0; this.xzSpread = $$1; this.ySpread = $$2; this.feature = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration; } public int tries() { return this.tries; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/RandomPatchConfiguration;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public int xzSpread() { return this.xzSpread; } public int ySpread() { return this.ySpread; } public Holder<PlacedFeature> feature() { return this.feature; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(Integer.valueOf(128)).forGetter(RandomPatchConfiguration::tries), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").orElse(Integer.valueOf(7)).forGetter(RandomPatchConfiguration::xzSpread), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").orElse(Integer.valueOf(3)).forGetter(RandomPatchConfiguration::ySpread), (App)PlacedFeature.CODEC.fieldOf("feature").forGetter(RandomPatchConfiguration::feature)).apply((Applicative)$$0, RandomPatchConfiguration::new));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\RandomPatchConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */