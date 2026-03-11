/*     */ package net.minecraft.world.level.levelgen.presets;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeSource;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.biome.FixedBiomeSource;
/*     */ import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
/*     */ import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
/*     */ import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
/*     */ import net.minecraft.world.level.biome.TheEndBiomeSource;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.dimension.LevelStem;
/*     */ import net.minecraft.world.level.levelgen.DebugLevelSource;
/*     */ import net.minecraft.world.level.levelgen.FlatLevelSource;
/*     */ import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
/*     */ import net.minecraft.world.level.levelgen.WorldDimensions;
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureSet;
/*     */ 
/*     */ public class WorldPresets
/*     */ {
/*  36 */   public static final ResourceKey<WorldPreset> NORMAL = register("normal");
/*  37 */   public static final ResourceKey<WorldPreset> FLAT = register("flat");
/*  38 */   public static final ResourceKey<WorldPreset> LARGE_BIOMES = register("large_biomes");
/*  39 */   public static final ResourceKey<WorldPreset> AMPLIFIED = register("amplified");
/*  40 */   public static final ResourceKey<WorldPreset> SINGLE_BIOME_SURFACE = register("single_biome_surface");
/*  41 */   public static final ResourceKey<WorldPreset> DEBUG = register("debug_all_block_states");
/*     */ 
/*     */   
/*     */   private static class Bootstrap
/*     */   {
/*     */     private final BootstapContext<WorldPreset> context;
/*     */     private final HolderGetter<NoiseGeneratorSettings> noiseSettings;
/*     */     private final HolderGetter<Biome> biomes;
/*     */     private final HolderGetter<PlacedFeature> placedFeatures;
/*     */     private final HolderGetter<StructureSet> structureSets;
/*     */     private final HolderGetter<MultiNoiseBiomeSourceParameterList> multiNoiseBiomeSourceParameterLists;
/*     */     private final Holder<DimensionType> overworldDimensionType;
/*     */     private final LevelStem netherStem;
/*     */     private final LevelStem endStem;
/*     */     
/*     */     Bootstrap(BootstapContext<WorldPreset> $$0) {
/*  57 */       this.context = $$0;
/*     */       
/*  59 */       HolderGetter<DimensionType> $$1 = $$0.lookup(Registries.DIMENSION_TYPE);
/*     */       
/*  61 */       this.noiseSettings = $$0.lookup(Registries.NOISE_SETTINGS);
/*  62 */       this.biomes = $$0.lookup(Registries.BIOME);
/*  63 */       this.placedFeatures = $$0.lookup(Registries.PLACED_FEATURE);
/*  64 */       this.structureSets = $$0.lookup(Registries.STRUCTURE_SET);
/*  65 */       this.multiNoiseBiomeSourceParameterLists = $$0.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);
/*     */       
/*  67 */       this.overworldDimensionType = (Holder<DimensionType>)$$1.getOrThrow(BuiltinDimensionTypes.OVERWORLD);
/*     */       
/*  69 */       Holder.Reference reference1 = $$1.getOrThrow(BuiltinDimensionTypes.NETHER);
/*  70 */       Holder.Reference reference2 = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.NETHER);
/*     */       
/*  72 */       Holder.Reference<MultiNoiseBiomeSourceParameterList> $$4 = this.multiNoiseBiomeSourceParameterLists.getOrThrow(MultiNoiseBiomeSourceParameterLists.NETHER);
/*     */       
/*  74 */       this.netherStem = new LevelStem((Holder)reference1, (ChunkGenerator)new NoiseBasedChunkGenerator((BiomeSource)MultiNoiseBiomeSource.createFromPreset((Holder)$$4), (Holder)reference2));
/*     */       
/*  76 */       Holder.Reference reference3 = $$1.getOrThrow(BuiltinDimensionTypes.END);
/*  77 */       Holder.Reference reference4 = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.END);
/*     */       
/*  79 */       this.endStem = new LevelStem((Holder)reference3, (ChunkGenerator)new NoiseBasedChunkGenerator((BiomeSource)TheEndBiomeSource.create(this.biomes), (Holder)reference4));
/*     */     }
/*     */     
/*     */     private LevelStem makeOverworld(ChunkGenerator $$0) {
/*  83 */       return new LevelStem(this.overworldDimensionType, $$0);
/*     */     }
/*     */     
/*     */     private LevelStem makeNoiseBasedOverworld(BiomeSource $$0, Holder<NoiseGeneratorSettings> $$1) {
/*  87 */       return makeOverworld((ChunkGenerator)new NoiseBasedChunkGenerator($$0, $$1));
/*     */     }
/*     */     
/*     */     private WorldPreset createPresetWithCustomOverworld(LevelStem $$0) {
/*  91 */       return new WorldPreset(
/*  92 */           Map.of(LevelStem.OVERWORLD, $$0, LevelStem.NETHER, this.netherStem, LevelStem.END, this.endStem));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void registerCustomOverworldPreset(ResourceKey<WorldPreset> $$0, LevelStem $$1) {
/* 101 */       this.context.register($$0, createPresetWithCustomOverworld($$1));
/*     */     }
/*     */     
/*     */     private void registerOverworlds(BiomeSource $$0) {
/* 105 */       Holder.Reference reference1 = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD);
/* 106 */       registerCustomOverworldPreset(WorldPresets.NORMAL, makeNoiseBasedOverworld($$0, (Holder<NoiseGeneratorSettings>)reference1));
/*     */       
/* 108 */       Holder.Reference reference2 = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.LARGE_BIOMES);
/* 109 */       registerCustomOverworldPreset(WorldPresets.LARGE_BIOMES, makeNoiseBasedOverworld($$0, (Holder<NoiseGeneratorSettings>)reference2));
/*     */       
/* 111 */       Holder.Reference reference3 = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.AMPLIFIED);
/* 112 */       registerCustomOverworldPreset(WorldPresets.AMPLIFIED, makeNoiseBasedOverworld($$0, (Holder<NoiseGeneratorSettings>)reference3));
/*     */     }
/*     */     
/*     */     public void bootstrap() {
/* 116 */       Holder.Reference<MultiNoiseBiomeSourceParameterList> $$0 = this.multiNoiseBiomeSourceParameterLists.getOrThrow(MultiNoiseBiomeSourceParameterLists.OVERWORLD);
/* 117 */       registerOverworlds((BiomeSource)MultiNoiseBiomeSource.createFromPreset((Holder)$$0));
/*     */       
/* 119 */       Holder.Reference reference = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD);
/* 120 */       Holder.Reference<Biome> $$2 = this.biomes.getOrThrow(Biomes.PLAINS);
/* 121 */       registerCustomOverworldPreset(WorldPresets.SINGLE_BIOME_SURFACE, makeNoiseBasedOverworld((BiomeSource)new FixedBiomeSource((Holder)$$2), (Holder<NoiseGeneratorSettings>)reference));
/*     */       
/* 123 */       registerCustomOverworldPreset(WorldPresets.FLAT, makeOverworld((ChunkGenerator)new FlatLevelSource(FlatLevelGeneratorSettings.getDefault(this.biomes, this.structureSets, this.placedFeatures))));
/*     */       
/* 125 */       registerCustomOverworldPreset(WorldPresets.DEBUG, makeOverworld((ChunkGenerator)new DebugLevelSource($$2)));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void bootstrap(BootstapContext<WorldPreset> $$0) {
/* 130 */     (new Bootstrap($$0)).bootstrap();
/*     */   }
/*     */   
/*     */   private static ResourceKey<WorldPreset> register(String $$0) {
/* 134 */     return ResourceKey.create(Registries.WORLD_PRESET, new ResourceLocation($$0));
/*     */   }
/*     */   
/*     */   public static Optional<ResourceKey<WorldPreset>> fromSettings(Registry<LevelStem> $$0) {
/* 138 */     return $$0.getOptional(LevelStem.OVERWORLD).flatMap($$0 -> {
/*     */           ChunkGenerator $$1 = $$0.generator();
/*     */           return ($$1 instanceof FlatLevelSource) ? Optional.of(FLAT) : (($$1 instanceof DebugLevelSource) ? Optional.of(DEBUG) : Optional.empty());
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WorldDimensions createNormalWorldDimensions(RegistryAccess $$0) {
/* 151 */     return ((WorldPreset)$$0.registryOrThrow(Registries.WORLD_PRESET).getHolderOrThrow(NORMAL).value()).createWorldDimensions();
/*     */   }
/*     */   
/*     */   public static LevelStem getNormalOverworld(RegistryAccess $$0) {
/* 155 */     return ((WorldPreset)$$0.registryOrThrow(Registries.WORLD_PRESET).getHolderOrThrow(NORMAL).value()).overworld().orElseThrow();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\presets\WorldPresets.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */