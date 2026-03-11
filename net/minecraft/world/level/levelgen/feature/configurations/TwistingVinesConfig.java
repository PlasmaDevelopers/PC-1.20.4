/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ public final class TwistingVinesConfig extends Record implements FeatureConfiguration { private final int spreadWidth;
/*    */   private final int spreadHeight;
/*    */   private final int maxHeight;
/*    */   public static final Codec<TwistingVinesConfig> CODEC;
/*    */   
/*  7 */   public TwistingVinesConfig(int $$0, int $$1, int $$2) { this.spreadWidth = $$0; this.spreadHeight = $$1; this.maxHeight = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig; } public int spreadWidth() { return this.spreadWidth; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/TwistingVinesConfig;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public int spreadHeight() { return this.spreadHeight; } public int maxHeight() { return this.maxHeight; }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.POSITIVE_INT.fieldOf("spread_width").forGetter(TwistingVinesConfig::spreadWidth), (App)ExtraCodecs.POSITIVE_INT.fieldOf("spread_height").forGetter(TwistingVinesConfig::spreadHeight), (App)ExtraCodecs.POSITIVE_INT.fieldOf("max_height").forGetter(TwistingVinesConfig::maxHeight)).apply((Applicative)$$0, TwistingVinesConfig::new));
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\TwistingVinesConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */