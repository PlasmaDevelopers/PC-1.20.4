/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ public final class DiskConfiguration extends Record implements FeatureConfiguration {
/*    */   private final RuleBasedBlockStateProvider stateProvider;
/*    */   private final BlockPredicate target;
/*    */   private final IntProvider radius;
/*    */   private final int halfHeight;
/*    */   public static final Codec<DiskConfiguration> CODEC;
/*    */   
/*  9 */   public DiskConfiguration(RuleBasedBlockStateProvider $$0, BlockPredicate $$1, IntProvider $$2, int $$3) { this.stateProvider = $$0; this.target = $$1; this.radius = $$2; this.halfHeight = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration; } public RuleBasedBlockStateProvider stateProvider() { return this.stateProvider; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/DiskConfiguration;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public BlockPredicate target() { return this.target; } public IntProvider radius() { return this.radius; } public int halfHeight() { return this.halfHeight; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)RuleBasedBlockStateProvider.CODEC.fieldOf("state_provider").forGetter(DiskConfiguration::stateProvider), (App)BlockPredicate.CODEC.fieldOf("target").forGetter(DiskConfiguration::target), (App)IntProvider.codec(0, 8).fieldOf("radius").forGetter(DiskConfiguration::radius), (App)Codec.intRange(0, 4).fieldOf("half_height").forGetter(DiskConfiguration::halfHeight)).apply((Applicative)$$0, DiskConfiguration::new));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\DiskConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */