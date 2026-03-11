/*    */ package net.minecraft.world.level;
/*    */ public final class WorldDataConfiguration extends Record {
/*    */   private final DataPackConfig dataPacks;
/*    */   private final FeatureFlagSet enabledFeatures;
/*    */   public static final String ENABLED_FEATURES_ID = "enabled_features";
/*    */   public static final Codec<WorldDataConfiguration> CODEC;
/*    */   
/*  8 */   public WorldDataConfiguration(DataPackConfig $$0, FeatureFlagSet $$1) { this.dataPacks = $$0; this.enabledFeatures = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/WorldDataConfiguration;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  8 */     //   0	7	0	this	Lnet/minecraft/world/level/WorldDataConfiguration; } public DataPackConfig dataPacks() { return this.dataPacks; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/WorldDataConfiguration;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/WorldDataConfiguration; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/WorldDataConfiguration;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/WorldDataConfiguration;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } public FeatureFlagSet enabledFeatures() { return this.enabledFeatures; }
/*    */   
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)DataPackConfig.CODEC.optionalFieldOf("DataPacks", DataPackConfig.DEFAULT).forGetter(WorldDataConfiguration::dataPacks), (App)FeatureFlags.CODEC.optionalFieldOf("enabled_features", FeatureFlags.DEFAULT_FLAGS).forGetter(WorldDataConfiguration::enabledFeatures)).apply((Applicative)$$0, WorldDataConfiguration::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 16 */   public static final WorldDataConfiguration DEFAULT = new WorldDataConfiguration(DataPackConfig.DEFAULT, FeatureFlags.DEFAULT_FLAGS);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldDataConfiguration expandFeatures(FeatureFlagSet $$0) {
/* 22 */     return new WorldDataConfiguration(this.dataPacks, this.enabledFeatures.join($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\WorldDataConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */