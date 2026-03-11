/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ 
/*     */ public final class NoiseGeneratorSettings extends Record {
/*     */   private final NoiseSettings noiseSettings;
/*     */   private final BlockState defaultBlock;
/*     */   private final BlockState defaultFluid;
/*     */   private final NoiseRouter noiseRouter;
/*     */   private final SurfaceRules.RuleSource surfaceRule;
/*     */   private final List<Climate.ParameterPoint> spawnTarget;
/*     */   private final int seaLevel;
/*     */   private final boolean disableMobGeneration;
/*     */   private final boolean aquifersEnabled;
/*     */   private final boolean oreVeinsEnabled;
/*     */   private final boolean useLegacyRandomSource;
/*     */   public static final Codec<NoiseGeneratorSettings> DIRECT_CODEC;
/*     */   
/*  21 */   public NoiseGeneratorSettings(NoiseSettings $$0, BlockState $$1, BlockState $$2, NoiseRouter $$3, SurfaceRules.RuleSource $$4, List<Climate.ParameterPoint> $$5, int $$6, boolean $$7, boolean $$8, boolean $$9, boolean $$10) { this.noiseSettings = $$0; this.defaultBlock = $$1; this.defaultFluid = $$2; this.noiseRouter = $$3; this.surfaceRule = $$4; this.spawnTarget = $$5; this.seaLevel = $$6; this.disableMobGeneration = $$7; this.aquifersEnabled = $$8; this.oreVeinsEnabled = $$9; this.useLegacyRandomSource = $$10; } public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #21	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  21 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings; } public NoiseSettings noiseSettings() { return this.noiseSettings; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #21	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #21	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;
/*  21 */     //   0	8	1	$$0	Ljava/lang/Object; } public BlockState defaultBlock() { return this.defaultBlock; } public BlockState defaultFluid() { return this.defaultFluid; } public NoiseRouter noiseRouter() { return this.noiseRouter; } public SurfaceRules.RuleSource surfaceRule() { return this.surfaceRule; } public List<Climate.ParameterPoint> spawnTarget() { return this.spawnTarget; } public int seaLevel() { return this.seaLevel; } public boolean aquifersEnabled() { return this.aquifersEnabled; } public boolean useLegacyRandomSource() { return this.useLegacyRandomSource; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  34 */     DIRECT_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)NoiseSettings.CODEC.fieldOf("noise").forGetter(NoiseGeneratorSettings::noiseSettings), (App)BlockState.CODEC.fieldOf("default_block").forGetter(NoiseGeneratorSettings::defaultBlock), (App)BlockState.CODEC.fieldOf("default_fluid").forGetter(NoiseGeneratorSettings::defaultFluid), (App)NoiseRouter.CODEC.fieldOf("noise_router").forGetter(NoiseGeneratorSettings::noiseRouter), (App)SurfaceRules.RuleSource.CODEC.fieldOf("surface_rule").forGetter(NoiseGeneratorSettings::surfaceRule), (App)Climate.ParameterPoint.CODEC.listOf().fieldOf("spawn_target").forGetter(NoiseGeneratorSettings::spawnTarget), (App)Codec.INT.fieldOf("sea_level").forGetter(NoiseGeneratorSettings::seaLevel), (App)Codec.BOOL.fieldOf("disable_mob_generation").forGetter(NoiseGeneratorSettings::disableMobGeneration), (App)Codec.BOOL.fieldOf("aquifers_enabled").forGetter(NoiseGeneratorSettings::isAquifersEnabled), (App)Codec.BOOL.fieldOf("ore_veins_enabled").forGetter(NoiseGeneratorSettings::oreVeinsEnabled), (App)Codec.BOOL.fieldOf("legacy_random_source").forGetter(NoiseGeneratorSettings::useLegacyRandomSource)).apply((Applicative)$$0, NoiseGeneratorSettings::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public static final Codec<Holder<NoiseGeneratorSettings>> CODEC = (Codec<Holder<NoiseGeneratorSettings>>)RegistryFileCodec.create(Registries.NOISE_SETTINGS, DIRECT_CODEC);
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean disableMobGeneration() {
/*  53 */     return this.disableMobGeneration;
/*     */   }
/*     */   
/*     */   public boolean isAquifersEnabled() {
/*  57 */     return this.aquifersEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean oreVeinsEnabled() {
/*  62 */     return this.oreVeinsEnabled;
/*     */   }
/*     */   
/*     */   public WorldgenRandom.Algorithm getRandomSource() {
/*  66 */     return this.useLegacyRandomSource ? WorldgenRandom.Algorithm.LEGACY : WorldgenRandom.Algorithm.XOROSHIRO;
/*     */   }
/*     */   
/*  69 */   public static final ResourceKey<NoiseGeneratorSettings> OVERWORLD = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation("overworld"));
/*  70 */   public static final ResourceKey<NoiseGeneratorSettings> LARGE_BIOMES = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation("large_biomes"));
/*  71 */   public static final ResourceKey<NoiseGeneratorSettings> AMPLIFIED = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation("amplified"));
/*  72 */   public static final ResourceKey<NoiseGeneratorSettings> NETHER = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation("nether"));
/*  73 */   public static final ResourceKey<NoiseGeneratorSettings> END = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation("end"));
/*  74 */   public static final ResourceKey<NoiseGeneratorSettings> CAVES = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation("caves"));
/*  75 */   public static final ResourceKey<NoiseGeneratorSettings> FLOATING_ISLANDS = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation("floating_islands"));
/*     */   
/*     */   public static void bootstrap(BootstapContext<NoiseGeneratorSettings> $$0) {
/*  78 */     $$0.register(OVERWORLD, overworld($$0, false, false));
/*  79 */     $$0.register(LARGE_BIOMES, overworld($$0, false, true));
/*  80 */     $$0.register(AMPLIFIED, overworld($$0, true, false));
/*  81 */     $$0.register(NETHER, nether($$0));
/*  82 */     $$0.register(END, end($$0));
/*  83 */     $$0.register(CAVES, caves($$0));
/*  84 */     $$0.register(FLOATING_ISLANDS, floatingIslands($$0));
/*     */   }
/*     */   
/*     */   private static NoiseGeneratorSettings end(BootstapContext<?> $$0) {
/*  88 */     return new NoiseGeneratorSettings(NoiseSettings.END_NOISE_SETTINGS, Blocks.END_STONE
/*     */         
/*  90 */         .defaultBlockState(), Blocks.AIR
/*  91 */         .defaultBlockState(), 
/*  92 */         NoiseRouterData.end($$0.lookup(Registries.DENSITY_FUNCTION)), 
/*  93 */         SurfaceRuleData.end(), 
/*  94 */         List.of(), 0, true, false, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static NoiseGeneratorSettings nether(BootstapContext<?> $$0) {
/* 104 */     return new NoiseGeneratorSettings(NoiseSettings.NETHER_NOISE_SETTINGS, Blocks.NETHERRACK
/*     */         
/* 106 */         .defaultBlockState(), Blocks.LAVA
/* 107 */         .defaultBlockState(), 
/* 108 */         NoiseRouterData.nether($$0.lookup(Registries.DENSITY_FUNCTION), $$0.lookup(Registries.NOISE)), 
/* 109 */         SurfaceRuleData.nether(), 
/* 110 */         List.of(), 32, false, false, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static NoiseGeneratorSettings overworld(BootstapContext<?> $$0, boolean $$1, boolean $$2) {
/* 120 */     return new NoiseGeneratorSettings(NoiseSettings.OVERWORLD_NOISE_SETTINGS, Blocks.STONE
/*     */         
/* 122 */         .defaultBlockState(), Blocks.WATER
/* 123 */         .defaultBlockState(), 
/* 124 */         NoiseRouterData.overworld($$0.lookup(Registries.DENSITY_FUNCTION), $$0.lookup(Registries.NOISE), $$2, $$1), 
/* 125 */         SurfaceRuleData.overworld(), (new OverworldBiomeBuilder())
/* 126 */         .spawnTarget(), 63, false, true, true, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static NoiseGeneratorSettings caves(BootstapContext<?> $$0) {
/* 136 */     return new NoiseGeneratorSettings(NoiseSettings.CAVES_NOISE_SETTINGS, Blocks.STONE
/*     */         
/* 138 */         .defaultBlockState(), Blocks.WATER
/* 139 */         .defaultBlockState(), 
/* 140 */         NoiseRouterData.caves($$0.lookup(Registries.DENSITY_FUNCTION), $$0.lookup(Registries.NOISE)), 
/* 141 */         SurfaceRuleData.overworldLike(false, true, true), 
/* 142 */         List.of(), 32, false, false, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static NoiseGeneratorSettings floatingIslands(BootstapContext<?> $$0) {
/* 152 */     return new NoiseGeneratorSettings(NoiseSettings.FLOATING_ISLANDS_NOISE_SETTINGS, Blocks.STONE
/*     */         
/* 154 */         .defaultBlockState(), Blocks.WATER
/* 155 */         .defaultBlockState(), 
/* 156 */         NoiseRouterData.floatingIslands($$0.lookup(Registries.DENSITY_FUNCTION), $$0.lookup(Registries.NOISE)), 
/* 157 */         SurfaceRuleData.overworldLike(false, false, false), 
/* 158 */         List.of(), -64, false, false, false, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NoiseGeneratorSettings dummy() {
/* 168 */     return new NoiseGeneratorSettings(NoiseSettings.OVERWORLD_NOISE_SETTINGS, Blocks.STONE
/*     */         
/* 170 */         .defaultBlockState(), Blocks.AIR
/* 171 */         .defaultBlockState(), 
/* 172 */         NoiseRouterData.none(), 
/* 173 */         SurfaceRuleData.air(), 
/* 174 */         List.of(), 63, true, false, false, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\NoiseGeneratorSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */