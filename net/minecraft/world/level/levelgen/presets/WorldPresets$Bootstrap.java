/*     */ package net.minecraft.world.level.levelgen.presets;
/*     */ 
/*     */ import java.util.Map;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ import net.minecraft.resources.ResourceKey;
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
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Bootstrap
/*     */ {
/*     */   private final BootstapContext<WorldPreset> context;
/*     */   private final HolderGetter<NoiseGeneratorSettings> noiseSettings;
/*     */   private final HolderGetter<Biome> biomes;
/*     */   private final HolderGetter<PlacedFeature> placedFeatures;
/*     */   private final HolderGetter<StructureSet> structureSets;
/*     */   private final HolderGetter<MultiNoiseBiomeSourceParameterList> multiNoiseBiomeSourceParameterLists;
/*     */   private final Holder<DimensionType> overworldDimensionType;
/*     */   private final LevelStem netherStem;
/*     */   private final LevelStem endStem;
/*     */   
/*     */   Bootstrap(BootstapContext<WorldPreset> $$0) {
/*  57 */     this.context = $$0;
/*     */     
/*  59 */     HolderGetter<DimensionType> $$1 = $$0.lookup(Registries.DIMENSION_TYPE);
/*     */     
/*  61 */     this.noiseSettings = $$0.lookup(Registries.NOISE_SETTINGS);
/*  62 */     this.biomes = $$0.lookup(Registries.BIOME);
/*  63 */     this.placedFeatures = $$0.lookup(Registries.PLACED_FEATURE);
/*  64 */     this.structureSets = $$0.lookup(Registries.STRUCTURE_SET);
/*  65 */     this.multiNoiseBiomeSourceParameterLists = $$0.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST);
/*     */     
/*  67 */     this.overworldDimensionType = (Holder<DimensionType>)$$1.getOrThrow(BuiltinDimensionTypes.OVERWORLD);
/*     */     
/*  69 */     Holder.Reference reference1 = $$1.getOrThrow(BuiltinDimensionTypes.NETHER);
/*  70 */     Holder.Reference reference2 = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.NETHER);
/*     */     
/*  72 */     Holder.Reference<MultiNoiseBiomeSourceParameterList> $$4 = this.multiNoiseBiomeSourceParameterLists.getOrThrow(MultiNoiseBiomeSourceParameterLists.NETHER);
/*     */     
/*  74 */     this.netherStem = new LevelStem((Holder)reference1, (ChunkGenerator)new NoiseBasedChunkGenerator((BiomeSource)MultiNoiseBiomeSource.createFromPreset((Holder)$$4), (Holder)reference2));
/*     */     
/*  76 */     Holder.Reference reference3 = $$1.getOrThrow(BuiltinDimensionTypes.END);
/*  77 */     Holder.Reference reference4 = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.END);
/*     */     
/*  79 */     this.endStem = new LevelStem((Holder)reference3, (ChunkGenerator)new NoiseBasedChunkGenerator((BiomeSource)TheEndBiomeSource.create(this.biomes), (Holder)reference4));
/*     */   }
/*     */   
/*     */   private LevelStem makeOverworld(ChunkGenerator $$0) {
/*  83 */     return new LevelStem(this.overworldDimensionType, $$0);
/*     */   }
/*     */   
/*     */   private LevelStem makeNoiseBasedOverworld(BiomeSource $$0, Holder<NoiseGeneratorSettings> $$1) {
/*  87 */     return makeOverworld((ChunkGenerator)new NoiseBasedChunkGenerator($$0, $$1));
/*     */   }
/*     */   
/*     */   private WorldPreset createPresetWithCustomOverworld(LevelStem $$0) {
/*  91 */     return new WorldPreset(
/*  92 */         Map.of(LevelStem.OVERWORLD, $$0, LevelStem.NETHER, this.netherStem, LevelStem.END, this.endStem));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerCustomOverworldPreset(ResourceKey<WorldPreset> $$0, LevelStem $$1) {
/* 101 */     this.context.register($$0, createPresetWithCustomOverworld($$1));
/*     */   }
/*     */   
/*     */   private void registerOverworlds(BiomeSource $$0) {
/* 105 */     Holder.Reference reference1 = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD);
/* 106 */     registerCustomOverworldPreset(WorldPresets.NORMAL, makeNoiseBasedOverworld($$0, (Holder<NoiseGeneratorSettings>)reference1));
/*     */     
/* 108 */     Holder.Reference reference2 = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.LARGE_BIOMES);
/* 109 */     registerCustomOverworldPreset(WorldPresets.LARGE_BIOMES, makeNoiseBasedOverworld($$0, (Holder<NoiseGeneratorSettings>)reference2));
/*     */     
/* 111 */     Holder.Reference reference3 = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.AMPLIFIED);
/* 112 */     registerCustomOverworldPreset(WorldPresets.AMPLIFIED, makeNoiseBasedOverworld($$0, (Holder<NoiseGeneratorSettings>)reference3));
/*     */   }
/*     */   
/*     */   public void bootstrap() {
/* 116 */     Holder.Reference<MultiNoiseBiomeSourceParameterList> $$0 = this.multiNoiseBiomeSourceParameterLists.getOrThrow(MultiNoiseBiomeSourceParameterLists.OVERWORLD);
/* 117 */     registerOverworlds((BiomeSource)MultiNoiseBiomeSource.createFromPreset((Holder)$$0));
/*     */     
/* 119 */     Holder.Reference reference = this.noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD);
/* 120 */     Holder.Reference<Biome> $$2 = this.biomes.getOrThrow(Biomes.PLAINS);
/* 121 */     registerCustomOverworldPreset(WorldPresets.SINGLE_BIOME_SURFACE, makeNoiseBasedOverworld((BiomeSource)new FixedBiomeSource((Holder)$$2), (Holder<NoiseGeneratorSettings>)reference));
/*     */     
/* 123 */     registerCustomOverworldPreset(WorldPresets.FLAT, makeOverworld((ChunkGenerator)new FlatLevelSource(FlatLevelGeneratorSettings.getDefault(this.biomes, this.structureSets, this.placedFeatures))));
/*     */     
/* 125 */     registerCustomOverworldPreset(WorldPresets.DEBUG, makeOverworld((ChunkGenerator)new DebugLevelSource($$2)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\presets\WorldPresets$Bootstrap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */