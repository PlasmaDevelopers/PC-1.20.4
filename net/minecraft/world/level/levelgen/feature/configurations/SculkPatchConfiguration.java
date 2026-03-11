/*   */ package net.minecraft.world.level.levelgen.feature.configurations;public final class SculkPatchConfiguration extends Record implements FeatureConfiguration { private final int chargeCount; private final int amountPerCharge; private final int spreadAttempts; private final int growthRounds;
/*   */   private final int spreadRounds;
/*   */   private final IntProvider extraRareGrowths;
/*   */   private final float catalystChance;
/*   */   public static final Codec<SculkPatchConfiguration> CODEC;
/*   */   
/* 7 */   public SculkPatchConfiguration(int $$0, int $$1, int $$2, int $$3, int $$4, IntProvider $$5, float $$6) { this.chargeCount = $$0; this.amountPerCharge = $$1; this.spreadAttempts = $$2; this.growthRounds = $$3; this.spreadRounds = $$4; this.extraRareGrowths = $$5; this.catalystChance = $$6; } public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #7	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/* 7 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration; } public int chargeCount() { return this.chargeCount; } public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #7	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration; } public final boolean equals(Object $$0) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #7	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/SculkPatchConfiguration;
/* 7 */     //   0	8	1	$$0	Ljava/lang/Object; } public int amountPerCharge() { return this.amountPerCharge; } public int spreadAttempts() { return this.spreadAttempts; } public int growthRounds() { return this.growthRounds; } public int spreadRounds() { return this.spreadRounds; } public IntProvider extraRareGrowths() { return this.extraRareGrowths; } public float catalystChance() { return this.catalystChance; } static {
/* 8 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.intRange(1, 32).fieldOf("charge_count").forGetter(SculkPatchConfiguration::chargeCount), (App)Codec.intRange(1, 500).fieldOf("amount_per_charge").forGetter(SculkPatchConfiguration::amountPerCharge), (App)Codec.intRange(1, 64).fieldOf("spread_attempts").forGetter(SculkPatchConfiguration::spreadAttempts), (App)Codec.intRange(0, 8).fieldOf("growth_rounds").forGetter(SculkPatchConfiguration::growthRounds), (App)Codec.intRange(0, 8).fieldOf("spread_rounds").forGetter(SculkPatchConfiguration::spreadRounds), (App)IntProvider.CODEC.fieldOf("extra_rare_growths").forGetter(SculkPatchConfiguration::extraRareGrowths), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("catalyst_chance").forGetter(SculkPatchConfiguration::catalystChance)).apply((Applicative)$$0, SculkPatchConfiguration::new));
/*   */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\SculkPatchConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */