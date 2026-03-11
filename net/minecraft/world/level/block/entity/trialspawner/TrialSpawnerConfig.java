/*    */ package net.minecraft.world.level.block.entity.trialspawner;public final class TrialSpawnerConfig extends Record { private final int requiredPlayerRange;
/*    */   private final int spawnRange;
/*    */   private final float totalMobs;
/*    */   private final float simultaneousMobs;
/*    */   private final float totalMobsAddedPerPlayer;
/*    */   private final float simultaneousMobsAddedPerPlayer;
/*    */   private final int ticksBetweenSpawn;
/*    */   private final int targetCooldownLength;
/*    */   private final SimpleWeightedRandomList<SpawnData> spawnPotentialsDefinition;
/*    */   private final SimpleWeightedRandomList<ResourceLocation> lootTablesToEject;
/*    */   
/* 12 */   public TrialSpawnerConfig(int $$0, int $$1, float $$2, float $$3, float $$4, float $$5, int $$6, int $$7, SimpleWeightedRandomList<SpawnData> $$8, SimpleWeightedRandomList<ResourceLocation> $$9) { this.requiredPlayerRange = $$0; this.spawnRange = $$1; this.totalMobs = $$2; this.simultaneousMobs = $$3; this.totalMobsAddedPerPlayer = $$4; this.simultaneousMobsAddedPerPlayer = $$5; this.ticksBetweenSpawn = $$6; this.targetCooldownLength = $$7; this.spawnPotentialsDefinition = $$8; this.lootTablesToEject = $$9; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/block/entity/trialspawner/TrialSpawnerConfig;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 12 */     //   0	7	0	this	Lnet/minecraft/world/level/block/entity/trialspawner/TrialSpawnerConfig; } public int requiredPlayerRange() { return this.requiredPlayerRange; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/block/entity/trialspawner/TrialSpawnerConfig;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/block/entity/trialspawner/TrialSpawnerConfig; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/block/entity/trialspawner/TrialSpawnerConfig;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/block/entity/trialspawner/TrialSpawnerConfig;
/* 12 */     //   0	8	1	$$0	Ljava/lang/Object; } public int spawnRange() { return this.spawnRange; } public float totalMobs() { return this.totalMobs; } public float simultaneousMobs() { return this.simultaneousMobs; } public float totalMobsAddedPerPlayer() { return this.totalMobsAddedPerPlayer; } public float simultaneousMobsAddedPerPlayer() { return this.simultaneousMobsAddedPerPlayer; } public int ticksBetweenSpawn() { return this.ticksBetweenSpawn; } public int targetCooldownLength() { return this.targetCooldownLength; } public SimpleWeightedRandomList<SpawnData> spawnPotentialsDefinition() { return this.spawnPotentialsDefinition; } public SimpleWeightedRandomList<ResourceLocation> lootTablesToEject() { return this.lootTablesToEject; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public static TrialSpawnerConfig DEFAULT = new TrialSpawnerConfig(14, 4, 6.0F, 2.0F, 2.0F, 1.0F, 40, 36000, 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 32 */       SimpleWeightedRandomList.empty(), 
/* 33 */       SimpleWeightedRandomList.empty()); public static MapCodec<TrialSpawnerConfig> MAP_CODEC;
/*    */   
/*    */   static {
/* 36 */     MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.intRange(1, 128).optionalFieldOf("required_player_range", Integer.valueOf(DEFAULT.requiredPlayerRange)).forGetter(TrialSpawnerConfig::requiredPlayerRange), (App)Codec.intRange(1, 128).optionalFieldOf("spawn_range", Integer.valueOf(DEFAULT.spawnRange)).forGetter(TrialSpawnerConfig::spawnRange), (App)Codec.floatRange(0.0F, Float.MAX_VALUE).optionalFieldOf("total_mobs", Float.valueOf(DEFAULT.totalMobs)).forGetter(TrialSpawnerConfig::totalMobs), (App)Codec.floatRange(0.0F, Float.MAX_VALUE).optionalFieldOf("simultaneous_mobs", Float.valueOf(DEFAULT.simultaneousMobs)).forGetter(TrialSpawnerConfig::simultaneousMobs), (App)Codec.floatRange(0.0F, Float.MAX_VALUE).optionalFieldOf("total_mobs_added_per_player", Float.valueOf(DEFAULT.totalMobsAddedPerPlayer)).forGetter(TrialSpawnerConfig::totalMobsAddedPerPlayer), (App)Codec.floatRange(0.0F, Float.MAX_VALUE).optionalFieldOf("simultaneous_mobs_added_per_player", Float.valueOf(DEFAULT.simultaneousMobsAddedPerPlayer)).forGetter(TrialSpawnerConfig::simultaneousMobsAddedPerPlayer), (App)Codec.intRange(0, 2147483647).optionalFieldOf("ticks_between_spawn", Integer.valueOf(DEFAULT.ticksBetweenSpawn)).forGetter(TrialSpawnerConfig::ticksBetweenSpawn), (App)Codec.intRange(0, 2147483647).optionalFieldOf("target_cooldown_length", Integer.valueOf(DEFAULT.targetCooldownLength)).forGetter(TrialSpawnerConfig::targetCooldownLength), (App)SpawnData.LIST_CODEC.optionalFieldOf("spawn_potentials", SimpleWeightedRandomList.empty()).forGetter(TrialSpawnerConfig::spawnPotentialsDefinition), (App)SimpleWeightedRandomList.wrappedCodecAllowingEmpty(ResourceLocation.CODEC).optionalFieldOf("loot_tables_to_eject", SimpleWeightedRandomList.empty()).forGetter(TrialSpawnerConfig::lootTablesToEject)).apply((Applicative)$$0, TrialSpawnerConfig::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int calculateTargetTotalMobs(int $$0) {
/* 50 */     return (int)Math.floor((this.totalMobs + this.totalMobsAddedPerPlayer * $$0));
/*    */   }
/*    */   
/*    */   public int calculateTargetSimultaneousMobs(int $$0) {
/* 54 */     return (int)Math.floor((this.simultaneousMobs + this.simultaneousMobsAddedPerPlayer * $$0));
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\trialspawner\TrialSpawnerConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */