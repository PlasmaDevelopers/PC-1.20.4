/*     */ package net.minecraft.world.level.levelgen.flat;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function8;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.RegistryCodecs;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
/*     */ import net.minecraft.resources.RegistryOps;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeGenerationSettings;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.feature.Feature;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureSet;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class FlatLevelGeneratorSettings {
/*  36 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */ 
/*     */   
/*     */   public static final Codec<FlatLevelGeneratorSettings> CODEC;
/*     */ 
/*     */   
/*     */   private final Optional<HolderSet<StructureSet>> structureOverrides;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  47 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)RegistryCodecs.homogeneousList(Registries.STRUCTURE_SET).optionalFieldOf("structure_overrides").forGetter(()), (App)FlatLayerInfo.CODEC.listOf().fieldOf("layers").forGetter(FlatLevelGeneratorSettings::getLayersInfo), (App)Codec.BOOL.fieldOf("lakes").orElse(Boolean.valueOf(false)).forGetter(()), (App)Codec.BOOL.fieldOf("features").orElse(Boolean.valueOf(false)).forGetter(()), (App)Biome.CODEC.optionalFieldOf("biome").orElseGet(Optional::empty).forGetter(()), (App)RegistryOps.retrieveElement(Biomes.PLAINS), (App)RegistryOps.retrieveElement(MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND), (App)RegistryOps.retrieveElement(MiscOverworldPlacements.LAKE_LAVA_SURFACE)).apply((Applicative)$$0, FlatLevelGeneratorSettings::new)).comapFlatMap(FlatLevelGeneratorSettings::validateHeight, Function.identity()).stable();
/*     */   }
/*     */   private static DataResult<FlatLevelGeneratorSettings> validateHeight(FlatLevelGeneratorSettings $$0) {
/*  50 */     int $$1 = $$0.layersInfo.stream().mapToInt(FlatLayerInfo::getHeight).sum();
/*     */     
/*  52 */     if ($$1 > DimensionType.Y_SIZE) {
/*  53 */       return DataResult.error(() -> "Sum of layer heights is > " + DimensionType.Y_SIZE, $$0);
/*     */     }
/*  55 */     return DataResult.success($$0);
/*     */   }
/*     */ 
/*     */   
/*  59 */   private final List<FlatLayerInfo> layersInfo = Lists.newArrayList();
/*     */   private final Holder<Biome> biome;
/*     */   private final List<BlockState> layers;
/*     */   private boolean voidGen;
/*     */   private boolean decoration;
/*     */   private boolean addLakes;
/*     */   private final List<Holder<PlacedFeature>> lakes;
/*     */   
/*     */   private FlatLevelGeneratorSettings(Optional<HolderSet<StructureSet>> $$0, List<FlatLayerInfo> $$1, boolean $$2, boolean $$3, Optional<Holder<Biome>> $$4, Holder.Reference<Biome> $$5, Holder<PlacedFeature> $$6, Holder<PlacedFeature> $$7) {
/*  68 */     this($$0, getBiome($$4, (Holder<Biome>)$$5), List.of($$6, $$7));
/*  69 */     if ($$2) {
/*  70 */       setAddLakes();
/*     */     }
/*  72 */     if ($$3) {
/*  73 */       setDecoration();
/*     */     }
/*  75 */     this.layersInfo.addAll($$1);
/*  76 */     updateLayers();
/*     */   }
/*     */   
/*     */   private static Holder<Biome> getBiome(Optional<? extends Holder<Biome>> $$0, Holder<Biome> $$1) {
/*  80 */     if ($$0.isEmpty()) {
/*  81 */       LOGGER.error("Unknown biome, defaulting to plains");
/*  82 */       return $$1;
/*     */     } 
/*  84 */     return $$0.get();
/*     */   }
/*     */   
/*     */   public FlatLevelGeneratorSettings(Optional<HolderSet<StructureSet>> $$0, Holder<Biome> $$1, List<Holder<PlacedFeature>> $$2) {
/*  88 */     this.structureOverrides = $$0;
/*  89 */     this.biome = $$1;
/*  90 */     this.layers = Lists.newArrayList();
/*  91 */     this.lakes = $$2;
/*     */   }
/*     */   
/*     */   public FlatLevelGeneratorSettings withBiomeAndLayers(List<FlatLayerInfo> $$0, Optional<HolderSet<StructureSet>> $$1, Holder<Biome> $$2) {
/*  95 */     FlatLevelGeneratorSettings $$3 = new FlatLevelGeneratorSettings($$1, $$2, this.lakes);
/*  96 */     for (FlatLayerInfo $$4 : $$0) {
/*  97 */       $$3.layersInfo.add(new FlatLayerInfo($$4.getHeight(), $$4.getBlockState().getBlock()));
/*  98 */       $$3.updateLayers();
/*     */     } 
/* 100 */     if (this.decoration) {
/* 101 */       $$3.setDecoration();
/*     */     }
/* 103 */     if (this.addLakes) {
/* 104 */       $$3.setAddLakes();
/*     */     }
/* 106 */     return $$3;
/*     */   }
/*     */   
/*     */   public void setDecoration() {
/* 110 */     this.decoration = true;
/*     */   }
/*     */   
/*     */   public void setAddLakes() {
/* 114 */     this.addLakes = true;
/*     */   }
/*     */   
/*     */   public BiomeGenerationSettings adjustGenerationSettings(Holder<Biome> $$0) {
/* 118 */     if (!$$0.equals(this.biome)) {
/* 119 */       return ((Biome)$$0.value()).getGenerationSettings();
/*     */     }
/* 121 */     BiomeGenerationSettings $$1 = ((Biome)getBiome().value()).getGenerationSettings();
/*     */     
/* 123 */     BiomeGenerationSettings.PlainBuilder $$2 = new BiomeGenerationSettings.PlainBuilder();
/*     */     
/* 125 */     if (this.addLakes) {
/* 126 */       for (Holder<PlacedFeature> $$3 : this.lakes) {
/* 127 */         $$2.addFeature(GenerationStep.Decoration.LAKES, $$3);
/*     */       }
/*     */     }
/*     */     
/* 131 */     boolean $$4 = ((!this.voidGen || $$0.is(Biomes.THE_VOID)) && this.decoration);
/*     */     
/* 133 */     if ($$4) {
/* 134 */       List<HolderSet<PlacedFeature>> $$5 = $$1.features();
/* 135 */       for (int $$6 = 0; $$6 < $$5.size(); $$6++) {
/* 136 */         if ($$6 != GenerationStep.Decoration.UNDERGROUND_STRUCTURES.ordinal() && $$6 != GenerationStep.Decoration.SURFACE_STRUCTURES
/* 137 */           .ordinal() && (!this.addLakes || $$6 != GenerationStep.Decoration.LAKES
/* 138 */           .ordinal())) {
/*     */ 
/*     */ 
/*     */           
/* 142 */           HolderSet<PlacedFeature> $$7 = $$5.get($$6);
/* 143 */           for (Holder<PlacedFeature> $$8 : $$7) {
/* 144 */             $$2.addFeature($$6, $$8);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 149 */     List<BlockState> $$9 = getLayers();
/* 150 */     for (int $$10 = 0; $$10 < $$9.size(); $$10++) {
/* 151 */       BlockState $$11 = $$9.get($$10);
/*     */ 
/*     */       
/* 154 */       if (!Heightmap.Types.MOTION_BLOCKING.isOpaque().test($$11)) {
/* 155 */         $$9.set($$10, null);
/* 156 */         $$2.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacementUtils.inlinePlaced(Feature.FILL_LAYER, (FeatureConfiguration)new LayerConfiguration($$10, $$11), new net.minecraft.world.level.levelgen.placement.PlacementModifier[0]));
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     return $$2.build();
/*     */   }
/*     */   
/*     */   public Optional<HolderSet<StructureSet>> structureOverrides() {
/* 164 */     return this.structureOverrides;
/*     */   }
/*     */   
/*     */   public Holder<Biome> getBiome() {
/* 168 */     return this.biome;
/*     */   }
/*     */   
/*     */   public List<FlatLayerInfo> getLayersInfo() {
/* 172 */     return this.layersInfo;
/*     */   }
/*     */   
/*     */   public List<BlockState> getLayers() {
/* 176 */     return this.layers;
/*     */   }
/*     */   
/*     */   public void updateLayers() {
/* 180 */     this.layers.clear();
/*     */     
/* 182 */     for (FlatLayerInfo $$0 : this.layersInfo) {
/* 183 */       for (int $$1 = 0; $$1 < $$0.getHeight(); $$1++) {
/* 184 */         this.layers.add($$0.getBlockState());
/*     */       }
/*     */     } 
/*     */     
/* 188 */     this.voidGen = this.layers.stream().allMatch($$0 -> $$0.is(Blocks.AIR));
/*     */   }
/*     */   
/*     */   public static FlatLevelGeneratorSettings getDefault(HolderGetter<Biome> $$0, HolderGetter<StructureSet> $$1, HolderGetter<PlacedFeature> $$2) {
/* 192 */     HolderSet.Direct direct = HolderSet.direct(new Holder[] { (Holder)$$1
/* 193 */           .getOrThrow(BuiltinStructureSets.STRONGHOLDS), (Holder)$$1
/* 194 */           .getOrThrow(BuiltinStructureSets.VILLAGES) });
/*     */ 
/*     */     
/* 197 */     FlatLevelGeneratorSettings $$4 = new FlatLevelGeneratorSettings((Optional)Optional.of(direct), getDefaultBiome($$0), createLakesList($$2));
/* 198 */     $$4.getLayersInfo().add(new FlatLayerInfo(1, Blocks.BEDROCK));
/* 199 */     $$4.getLayersInfo().add(new FlatLayerInfo(2, Blocks.DIRT));
/* 200 */     $$4.getLayersInfo().add(new FlatLayerInfo(1, Blocks.GRASS_BLOCK));
/* 201 */     $$4.updateLayers();
/*     */     
/* 203 */     return $$4;
/*     */   }
/*     */   
/*     */   public static Holder<Biome> getDefaultBiome(HolderGetter<Biome> $$0) {
/* 207 */     return (Holder<Biome>)$$0.getOrThrow(Biomes.PLAINS);
/*     */   }
/*     */   
/*     */   public static List<Holder<PlacedFeature>> createLakesList(HolderGetter<PlacedFeature> $$0) {
/* 211 */     return (List)List.of($$0
/* 212 */         .getOrThrow(MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND), $$0
/* 213 */         .getOrThrow(MiscOverworldPlacements.LAKE_LAVA_SURFACE));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\flat\FlatLevelGeneratorSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */