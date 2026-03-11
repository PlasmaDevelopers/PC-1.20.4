/*     */ package net.minecraft.data.worldgen.placement;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ import net.minecraft.data.worldgen.features.TreeFeatures;
/*     */ import net.minecraft.data.worldgen.features.VegetationFeatures;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.util.valueproviders.ClampedInt;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*     */ import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.level.levelgen.placement.BiomeFilter;
/*     */ import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
/*     */ import net.minecraft.world.level.levelgen.placement.CountPlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacementModifier;
/*     */ import net.minecraft.world.level.levelgen.placement.RarityFilter;
/*     */ import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
/*     */ 
/*     */ public class VegetationPlacements {
/*  35 */   public static final ResourceKey<PlacedFeature> BAMBOO_LIGHT = PlacementUtils.createKey("bamboo_light");
/*  36 */   public static final ResourceKey<PlacedFeature> BAMBOO = PlacementUtils.createKey("bamboo");
/*  37 */   public static final ResourceKey<PlacedFeature> VINES = PlacementUtils.createKey("vines");
/*     */   
/*  39 */   public static final ResourceKey<PlacedFeature> PATCH_SUNFLOWER = PlacementUtils.createKey("patch_sunflower");
/*  40 */   public static final ResourceKey<PlacedFeature> PATCH_PUMPKIN = PlacementUtils.createKey("patch_pumpkin");
/*     */   
/*  42 */   public static final ResourceKey<PlacedFeature> PATCH_GRASS_PLAIN = PlacementUtils.createKey("patch_grass_plain");
/*  43 */   public static final ResourceKey<PlacedFeature> PATCH_GRASS_FOREST = PlacementUtils.createKey("patch_grass_forest");
/*  44 */   public static final ResourceKey<PlacedFeature> PATCH_GRASS_BADLANDS = PlacementUtils.createKey("patch_grass_badlands");
/*  45 */   public static final ResourceKey<PlacedFeature> PATCH_GRASS_SAVANNA = PlacementUtils.createKey("patch_grass_savanna");
/*  46 */   public static final ResourceKey<PlacedFeature> PATCH_GRASS_NORMAL = PlacementUtils.createKey("patch_grass_normal");
/*  47 */   public static final ResourceKey<PlacedFeature> PATCH_GRASS_TAIGA_2 = PlacementUtils.createKey("patch_grass_taiga_2");
/*  48 */   public static final ResourceKey<PlacedFeature> PATCH_GRASS_TAIGA = PlacementUtils.createKey("patch_grass_taiga");
/*  49 */   public static final ResourceKey<PlacedFeature> PATCH_GRASS_JUNGLE = PlacementUtils.createKey("patch_grass_jungle");
/*     */   
/*  51 */   public static final ResourceKey<PlacedFeature> GRASS_BONEMEAL = PlacementUtils.createKey("grass_bonemeal");
/*     */   
/*  53 */   public static final ResourceKey<PlacedFeature> PATCH_DEAD_BUSH_2 = PlacementUtils.createKey("patch_dead_bush_2");
/*  54 */   public static final ResourceKey<PlacedFeature> PATCH_DEAD_BUSH = PlacementUtils.createKey("patch_dead_bush");
/*  55 */   public static final ResourceKey<PlacedFeature> PATCH_DEAD_BUSH_BADLANDS = PlacementUtils.createKey("patch_dead_bush_badlands");
/*     */   
/*  57 */   public static final ResourceKey<PlacedFeature> PATCH_MELON = PlacementUtils.createKey("patch_melon");
/*     */   
/*  59 */   public static final ResourceKey<PlacedFeature> PATCH_MELON_SPARSE = PlacementUtils.createKey("patch_melon_sparse");
/*     */   
/*  61 */   public static final ResourceKey<PlacedFeature> PATCH_BERRY_COMMON = PlacementUtils.createKey("patch_berry_common");
/*  62 */   public static final ResourceKey<PlacedFeature> PATCH_BERRY_RARE = PlacementUtils.createKey("patch_berry_rare");
/*     */   
/*  64 */   public static final ResourceKey<PlacedFeature> PATCH_WATERLILY = PlacementUtils.createKey("patch_waterlily");
/*     */   
/*  66 */   public static final ResourceKey<PlacedFeature> PATCH_TALL_GRASS_2 = PlacementUtils.createKey("patch_tall_grass_2");
/*  67 */   public static final ResourceKey<PlacedFeature> PATCH_TALL_GRASS = PlacementUtils.createKey("patch_tall_grass");
/*  68 */   public static final ResourceKey<PlacedFeature> PATCH_LARGE_FERN = PlacementUtils.createKey("patch_large_fern");
/*     */   
/*  70 */   public static final ResourceKey<PlacedFeature> PATCH_CACTUS_DESERT = PlacementUtils.createKey("patch_cactus_desert");
/*  71 */   public static final ResourceKey<PlacedFeature> PATCH_CACTUS_DECORATED = PlacementUtils.createKey("patch_cactus_decorated");
/*     */   
/*  73 */   public static final ResourceKey<PlacedFeature> PATCH_SUGAR_CANE_SWAMP = PlacementUtils.createKey("patch_sugar_cane_swamp");
/*  74 */   public static final ResourceKey<PlacedFeature> PATCH_SUGAR_CANE_DESERT = PlacementUtils.createKey("patch_sugar_cane_desert");
/*  75 */   public static final ResourceKey<PlacedFeature> PATCH_SUGAR_CANE_BADLANDS = PlacementUtils.createKey("patch_sugar_cane_badlands");
/*  76 */   public static final ResourceKey<PlacedFeature> PATCH_SUGAR_CANE = PlacementUtils.createKey("patch_sugar_cane");
/*     */   
/*  78 */   public static final ResourceKey<PlacedFeature> BROWN_MUSHROOM_NETHER = PlacementUtils.createKey("brown_mushroom_nether");
/*  79 */   public static final ResourceKey<PlacedFeature> RED_MUSHROOM_NETHER = PlacementUtils.createKey("red_mushroom_nether");
/*  80 */   public static final ResourceKey<PlacedFeature> BROWN_MUSHROOM_NORMAL = PlacementUtils.createKey("brown_mushroom_normal");
/*  81 */   public static final ResourceKey<PlacedFeature> RED_MUSHROOM_NORMAL = PlacementUtils.createKey("red_mushroom_normal");
/*  82 */   public static final ResourceKey<PlacedFeature> BROWN_MUSHROOM_TAIGA = PlacementUtils.createKey("brown_mushroom_taiga");
/*  83 */   public static final ResourceKey<PlacedFeature> RED_MUSHROOM_TAIGA = PlacementUtils.createKey("red_mushroom_taiga");
/*  84 */   public static final ResourceKey<PlacedFeature> BROWN_MUSHROOM_OLD_GROWTH = PlacementUtils.createKey("brown_mushroom_old_growth");
/*  85 */   public static final ResourceKey<PlacedFeature> RED_MUSHROOM_OLD_GROWTH = PlacementUtils.createKey("red_mushroom_old_growth");
/*  86 */   public static final ResourceKey<PlacedFeature> BROWN_MUSHROOM_SWAMP = PlacementUtils.createKey("brown_mushroom_swamp");
/*  87 */   public static final ResourceKey<PlacedFeature> RED_MUSHROOM_SWAMP = PlacementUtils.createKey("red_mushroom_swamp");
/*     */   
/*  89 */   public static final ResourceKey<PlacedFeature> FLOWER_WARM = PlacementUtils.createKey("flower_warm");
/*  90 */   public static final ResourceKey<PlacedFeature> FLOWER_DEFAULT = PlacementUtils.createKey("flower_default");
/*  91 */   public static final ResourceKey<PlacedFeature> FLOWER_FLOWER_FOREST = PlacementUtils.createKey("flower_flower_forest");
/*  92 */   public static final ResourceKey<PlacedFeature> FLOWER_SWAMP = PlacementUtils.createKey("flower_swamp");
/*  93 */   public static final ResourceKey<PlacedFeature> FLOWER_PLAINS = PlacementUtils.createKey("flower_plains");
/*  94 */   public static final ResourceKey<PlacedFeature> FLOWER_MEADOW = PlacementUtils.createKey("flower_meadow");
/*  95 */   public static final ResourceKey<PlacedFeature> FLOWER_CHERRY = PlacementUtils.createKey("flower_cherry");
/*     */   
/*  97 */   public static final ResourceKey<PlacedFeature> TREES_PLAINS = PlacementUtils.createKey("trees_plains");
/*  98 */   public static final ResourceKey<PlacedFeature> DARK_FOREST_VEGETATION = PlacementUtils.createKey("dark_forest_vegetation");
/*  99 */   public static final ResourceKey<PlacedFeature> FLOWER_FOREST_FLOWERS = PlacementUtils.createKey("flower_forest_flowers");
/* 100 */   public static final ResourceKey<PlacedFeature> FOREST_FLOWERS = PlacementUtils.createKey("forest_flowers");
/*     */   
/* 102 */   public static final ResourceKey<PlacedFeature> TREES_FLOWER_FOREST = PlacementUtils.createKey("trees_flower_forest");
/* 103 */   public static final ResourceKey<PlacedFeature> TREES_MEADOW = PlacementUtils.createKey("trees_meadow");
/* 104 */   public static final ResourceKey<PlacedFeature> TREES_CHERRY = PlacementUtils.createKey("trees_cherry");
/* 105 */   public static final ResourceKey<PlacedFeature> TREES_TAIGA = PlacementUtils.createKey("trees_taiga");
/* 106 */   public static final ResourceKey<PlacedFeature> TREES_GROVE = PlacementUtils.createKey("trees_grove");
/* 107 */   public static final ResourceKey<PlacedFeature> TREES_BADLANDS = PlacementUtils.createKey("trees_badlands");
/* 108 */   public static final ResourceKey<PlacedFeature> TREES_SNOWY = PlacementUtils.createKey("trees_snowy");
/* 109 */   public static final ResourceKey<PlacedFeature> TREES_SWAMP = PlacementUtils.createKey("trees_swamp");
/* 110 */   public static final ResourceKey<PlacedFeature> TREES_WINDSWEPT_SAVANNA = PlacementUtils.createKey("trees_windswept_savanna");
/* 111 */   public static final ResourceKey<PlacedFeature> TREES_SAVANNA = PlacementUtils.createKey("trees_savanna");
/* 112 */   public static final ResourceKey<PlacedFeature> BIRCH_TALL = PlacementUtils.createKey("birch_tall");
/* 113 */   public static final ResourceKey<PlacedFeature> TREES_BIRCH = PlacementUtils.createKey("trees_birch");
/* 114 */   public static final ResourceKey<PlacedFeature> TREES_WINDSWEPT_FOREST = PlacementUtils.createKey("trees_windswept_forest");
/* 115 */   public static final ResourceKey<PlacedFeature> TREES_WINDSWEPT_HILLS = PlacementUtils.createKey("trees_windswept_hills");
/* 116 */   public static final ResourceKey<PlacedFeature> TREES_WATER = PlacementUtils.createKey("trees_water");
/* 117 */   public static final ResourceKey<PlacedFeature> TREES_BIRCH_AND_OAK = PlacementUtils.createKey("trees_birch_and_oak");
/* 118 */   public static final ResourceKey<PlacedFeature> TREES_SPARSE_JUNGLE = PlacementUtils.createKey("trees_sparse_jungle");
/* 119 */   public static final ResourceKey<PlacedFeature> TREES_OLD_GROWTH_SPRUCE_TAIGA = PlacementUtils.createKey("trees_old_growth_spruce_taiga");
/* 120 */   public static final ResourceKey<PlacedFeature> TREES_OLD_GROWTH_PINE_TAIGA = PlacementUtils.createKey("trees_old_growth_pine_taiga");
/* 121 */   public static final ResourceKey<PlacedFeature> TREES_JUNGLE = PlacementUtils.createKey("trees_jungle");
/* 122 */   public static final ResourceKey<PlacedFeature> BAMBOO_VEGETATION = PlacementUtils.createKey("bamboo_vegetation");
/*     */   
/* 124 */   public static final ResourceKey<PlacedFeature> MUSHROOM_ISLAND_VEGETATION = PlacementUtils.createKey("mushroom_island_vegetation");
/*     */   
/* 126 */   public static final ResourceKey<PlacedFeature> TREES_MANGROVE = PlacementUtils.createKey("trees_mangrove");
/*     */   
/* 128 */   private static final PlacementModifier TREE_THRESHOLD = (PlacementModifier)SurfaceWaterDepthFilter.forMaxDepth(0);
/*     */   
/*     */   public static List<PlacementModifier> worldSurfaceSquaredWithCount(int $$0) {
/* 131 */     return (List)List.of(
/* 132 */         CountPlacement.of($$0), 
/* 133 */         InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, 
/*     */         
/* 135 */         BiomeFilter.biome());
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<PlacementModifier> getMushroomPlacement(int $$0, @Nullable PlacementModifier $$1) {
/* 140 */     ImmutableList.Builder<PlacementModifier> $$2 = ImmutableList.builder();
/* 141 */     if ($$1 != null) {
/* 142 */       $$2.add($$1);
/*     */     }
/* 144 */     if ($$0 != 0) {
/* 145 */       $$2.add(RarityFilter.onAverageOnceEvery($$0));
/*     */     }
/* 147 */     $$2.add(InSquarePlacement.spread());
/* 148 */     $$2.add(PlacementUtils.HEIGHTMAP);
/* 149 */     $$2.add(BiomeFilter.biome());
/* 150 */     return (List<PlacementModifier>)$$2.build();
/*     */   }
/*     */   
/*     */   private static ImmutableList.Builder<PlacementModifier> treePlacementBase(PlacementModifier $$0) {
/* 154 */     return ImmutableList.builder()
/* 155 */       .add($$0)
/* 156 */       .add(InSquarePlacement.spread())
/* 157 */       .add(TREE_THRESHOLD)
/* 158 */       .add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR)
/* 159 */       .add(BiomeFilter.biome());
/*     */   }
/*     */   
/*     */   public static List<PlacementModifier> treePlacement(PlacementModifier $$0) {
/* 163 */     return (List<PlacementModifier>)treePlacementBase($$0).build();
/*     */   }
/*     */   
/*     */   public static List<PlacementModifier> treePlacement(PlacementModifier $$0, Block $$1) {
/* 167 */     return (List<PlacementModifier>)treePlacementBase($$0)
/* 168 */       .add(BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive($$1.defaultBlockState(), (Vec3i)BlockPos.ZERO)))
/* 169 */       .build();
/*     */   }
/*     */   
/*     */   public static void bootstrap(BootstapContext<PlacedFeature> $$0) {
/* 173 */     HolderGetter<ConfiguredFeature<?, ?>> $$1 = $$0.lookup(Registries.CONFIGURED_FEATURE);
/* 174 */     Holder.Reference reference1 = $$1.getOrThrow(VegetationFeatures.BAMBOO_NO_PODZOL);
/* 175 */     Holder.Reference reference2 = $$1.getOrThrow(VegetationFeatures.BAMBOO_SOME_PODZOL);
/* 176 */     Holder.Reference reference3 = $$1.getOrThrow(VegetationFeatures.VINES);
/* 177 */     Holder.Reference reference4 = $$1.getOrThrow(VegetationFeatures.PATCH_SUNFLOWER);
/* 178 */     Holder.Reference reference5 = $$1.getOrThrow(VegetationFeatures.PATCH_PUMPKIN);
/* 179 */     Holder.Reference reference6 = $$1.getOrThrow(VegetationFeatures.PATCH_GRASS);
/* 180 */     Holder.Reference reference7 = $$1.getOrThrow(VegetationFeatures.PATCH_TAIGA_GRASS);
/* 181 */     Holder.Reference reference8 = $$1.getOrThrow(VegetationFeatures.PATCH_GRASS_JUNGLE);
/* 182 */     Holder.Reference reference9 = $$1.getOrThrow(VegetationFeatures.SINGLE_PIECE_OF_GRASS);
/* 183 */     Holder.Reference reference10 = $$1.getOrThrow(VegetationFeatures.PATCH_DEAD_BUSH);
/* 184 */     Holder.Reference reference11 = $$1.getOrThrow(VegetationFeatures.PATCH_MELON);
/* 185 */     Holder.Reference reference12 = $$1.getOrThrow(VegetationFeatures.PATCH_BERRY_BUSH);
/* 186 */     Holder.Reference reference13 = $$1.getOrThrow(VegetationFeatures.PATCH_WATERLILY);
/* 187 */     Holder.Reference reference14 = $$1.getOrThrow(VegetationFeatures.PATCH_TALL_GRASS);
/* 188 */     Holder.Reference reference15 = $$1.getOrThrow(VegetationFeatures.PATCH_LARGE_FERN);
/* 189 */     Holder.Reference reference16 = $$1.getOrThrow(VegetationFeatures.PATCH_CACTUS);
/* 190 */     Holder.Reference reference17 = $$1.getOrThrow(VegetationFeatures.PATCH_SUGAR_CANE);
/* 191 */     Holder.Reference reference18 = $$1.getOrThrow(VegetationFeatures.PATCH_BROWN_MUSHROOM);
/* 192 */     Holder.Reference reference19 = $$1.getOrThrow(VegetationFeatures.PATCH_RED_MUSHROOM);
/* 193 */     Holder.Reference reference20 = $$1.getOrThrow(VegetationFeatures.FLOWER_DEFAULT);
/* 194 */     Holder.Reference reference21 = $$1.getOrThrow(VegetationFeatures.FLOWER_FLOWER_FOREST);
/* 195 */     Holder.Reference reference22 = $$1.getOrThrow(VegetationFeatures.FLOWER_SWAMP);
/* 196 */     Holder.Reference reference23 = $$1.getOrThrow(VegetationFeatures.FLOWER_PLAIN);
/* 197 */     Holder.Reference reference24 = $$1.getOrThrow(VegetationFeatures.FLOWER_MEADOW);
/* 198 */     Holder.Reference reference25 = $$1.getOrThrow(VegetationFeatures.FLOWER_CHERRY);
/* 199 */     Holder.Reference reference26 = $$1.getOrThrow(VegetationFeatures.TREES_PLAINS);
/* 200 */     Holder.Reference reference27 = $$1.getOrThrow(VegetationFeatures.DARK_FOREST_VEGETATION);
/* 201 */     Holder.Reference reference28 = $$1.getOrThrow(VegetationFeatures.FOREST_FLOWERS);
/* 202 */     Holder.Reference reference29 = $$1.getOrThrow(VegetationFeatures.TREES_FLOWER_FOREST);
/* 203 */     Holder.Reference reference30 = $$1.getOrThrow(VegetationFeatures.MEADOW_TREES);
/* 204 */     Holder.Reference reference31 = $$1.getOrThrow(VegetationFeatures.TREES_TAIGA);
/* 205 */     Holder.Reference reference32 = $$1.getOrThrow(VegetationFeatures.TREES_GROVE);
/* 206 */     Holder.Reference reference33 = $$1.getOrThrow(TreeFeatures.OAK);
/* 207 */     Holder.Reference reference34 = $$1.getOrThrow(TreeFeatures.SPRUCE);
/* 208 */     Holder.Reference reference35 = $$1.getOrThrow(TreeFeatures.CHERRY_BEES_005);
/* 209 */     Holder.Reference reference36 = $$1.getOrThrow(TreeFeatures.SWAMP_OAK);
/* 210 */     Holder.Reference reference37 = $$1.getOrThrow(VegetationFeatures.TREES_SAVANNA);
/* 211 */     Holder.Reference reference38 = $$1.getOrThrow(VegetationFeatures.BIRCH_TALL);
/* 212 */     Holder.Reference reference39 = $$1.getOrThrow(TreeFeatures.BIRCH_BEES_0002);
/* 213 */     Holder.Reference reference40 = $$1.getOrThrow(VegetationFeatures.TREES_WINDSWEPT_HILLS);
/* 214 */     Holder.Reference reference41 = $$1.getOrThrow(VegetationFeatures.TREES_WATER);
/* 215 */     Holder.Reference reference42 = $$1.getOrThrow(VegetationFeatures.TREES_BIRCH_AND_OAK);
/* 216 */     Holder.Reference reference43 = $$1.getOrThrow(VegetationFeatures.TREES_SPARSE_JUNGLE);
/* 217 */     Holder.Reference reference44 = $$1.getOrThrow(VegetationFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA);
/* 218 */     Holder.Reference reference45 = $$1.getOrThrow(VegetationFeatures.TREES_OLD_GROWTH_PINE_TAIGA);
/* 219 */     Holder.Reference reference46 = $$1.getOrThrow(VegetationFeatures.TREES_JUNGLE);
/* 220 */     Holder.Reference reference47 = $$1.getOrThrow(VegetationFeatures.BAMBOO_VEGETATION);
/* 221 */     Holder.Reference reference48 = $$1.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION);
/* 222 */     Holder.Reference reference49 = $$1.getOrThrow(VegetationFeatures.MANGROVE_VEGETATION);
/*     */     
/* 224 */     PlacementUtils.register($$0, BAMBOO_LIGHT, (Holder<ConfiguredFeature<?, ?>>)reference1, new PlacementModifier[] {
/* 225 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(4), 
/* 226 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 228 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 230 */     PlacementUtils.register($$0, BAMBOO, (Holder<ConfiguredFeature<?, ?>>)reference2, new PlacementModifier[] {
/* 231 */           (PlacementModifier)NoiseBasedCountPlacement.of(160, 80.0D, 0.3D), 
/* 232 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, 
/*     */           
/* 234 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 236 */     PlacementUtils.register($$0, VINES, (Holder<ConfiguredFeature<?, ?>>)reference3, new PlacementModifier[] {
/* 237 */           (PlacementModifier)CountPlacement.of(127), 
/* 238 */           (PlacementModifier)InSquarePlacement.spread(), 
/* 239 */           (PlacementModifier)HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(100)), 
/* 240 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/* 243 */     PlacementUtils.register($$0, PATCH_SUNFLOWER, (Holder<ConfiguredFeature<?, ?>>)reference4, new PlacementModifier[] {
/* 244 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(3), 
/* 245 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 247 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 249 */     PlacementUtils.register($$0, PATCH_PUMPKIN, (Holder<ConfiguredFeature<?, ?>>)reference5, new PlacementModifier[] {
/* 250 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(300), 
/* 251 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 253 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/* 256 */     PlacementUtils.register($$0, PATCH_GRASS_PLAIN, (Holder<ConfiguredFeature<?, ?>>)reference6, new PlacementModifier[] {
/* 257 */           (PlacementModifier)NoiseThresholdCountPlacement.of(-0.8D, 5, 10), 
/* 258 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, 
/*     */           
/* 260 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 262 */     PlacementUtils.register($$0, PATCH_GRASS_FOREST, (Holder<ConfiguredFeature<?, ?>>)reference6, 
/* 263 */         worldSurfaceSquaredWithCount(2));
/*     */     
/* 265 */     PlacementUtils.register($$0, PATCH_GRASS_BADLANDS, (Holder<ConfiguredFeature<?, ?>>)reference6, new PlacementModifier[] {
/* 266 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, 
/*     */           
/* 268 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 270 */     PlacementUtils.register($$0, PATCH_GRASS_SAVANNA, (Holder<ConfiguredFeature<?, ?>>)reference6, 
/* 271 */         worldSurfaceSquaredWithCount(20));
/*     */     
/* 273 */     PlacementUtils.register($$0, PATCH_GRASS_NORMAL, (Holder<ConfiguredFeature<?, ?>>)reference6, 
/* 274 */         worldSurfaceSquaredWithCount(5));
/*     */     
/* 276 */     PlacementUtils.register($$0, PATCH_GRASS_TAIGA_2, (Holder<ConfiguredFeature<?, ?>>)reference7, new PlacementModifier[] {
/* 277 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, 
/*     */           
/* 279 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 281 */     PlacementUtils.register($$0, PATCH_GRASS_TAIGA, (Holder<ConfiguredFeature<?, ?>>)reference7, 
/* 282 */         worldSurfaceSquaredWithCount(7));
/*     */     
/* 284 */     PlacementUtils.register($$0, PATCH_GRASS_JUNGLE, (Holder<ConfiguredFeature<?, ?>>)reference8, 
/* 285 */         worldSurfaceSquaredWithCount(25));
/*     */ 
/*     */     
/* 288 */     PlacementUtils.register($$0, GRASS_BONEMEAL, (Holder<ConfiguredFeature<?, ?>>)reference9, new PlacementModifier[] { (PlacementModifier)PlacementUtils.isEmpty() });
/*     */     
/* 290 */     PlacementUtils.register($$0, PATCH_DEAD_BUSH_2, (Holder<ConfiguredFeature<?, ?>>)reference10, 
/* 291 */         worldSurfaceSquaredWithCount(2));
/*     */     
/* 293 */     PlacementUtils.register($$0, PATCH_DEAD_BUSH, (Holder<ConfiguredFeature<?, ?>>)reference10, new PlacementModifier[] {
/* 294 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, 
/*     */           
/* 296 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 298 */     PlacementUtils.register($$0, PATCH_DEAD_BUSH_BADLANDS, (Holder<ConfiguredFeature<?, ?>>)reference10, 
/* 299 */         worldSurfaceSquaredWithCount(20));
/*     */ 
/*     */     
/* 302 */     PlacementUtils.register($$0, PATCH_MELON, (Holder<ConfiguredFeature<?, ?>>)reference11, new PlacementModifier[] {
/* 303 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(6), 
/* 304 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 306 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/* 309 */     PlacementUtils.register($$0, PATCH_MELON_SPARSE, (Holder<ConfiguredFeature<?, ?>>)reference11, new PlacementModifier[] {
/* 310 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(64), 
/* 311 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 313 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/* 316 */     PlacementUtils.register($$0, PATCH_BERRY_COMMON, (Holder<ConfiguredFeature<?, ?>>)reference12, new PlacementModifier[] {
/* 317 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(32), 
/* 318 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, 
/*     */           
/* 320 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 322 */     PlacementUtils.register($$0, PATCH_BERRY_RARE, (Holder<ConfiguredFeature<?, ?>>)reference12, new PlacementModifier[] {
/* 323 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(384), 
/* 324 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, 
/*     */           
/* 326 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/* 329 */     PlacementUtils.register($$0, PATCH_WATERLILY, (Holder<ConfiguredFeature<?, ?>>)reference13, 
/* 330 */         worldSurfaceSquaredWithCount(4));
/*     */ 
/*     */     
/* 333 */     PlacementUtils.register($$0, PATCH_TALL_GRASS_2, (Holder<ConfiguredFeature<?, ?>>)reference14, new PlacementModifier[] {
/* 334 */           (PlacementModifier)NoiseThresholdCountPlacement.of(-0.8D, 0, 7), 
/* 335 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(32), 
/* 336 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 338 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 340 */     PlacementUtils.register($$0, PATCH_TALL_GRASS, (Holder<ConfiguredFeature<?, ?>>)reference14, new PlacementModifier[] {
/* 341 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(5), 
/* 342 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 344 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 346 */     PlacementUtils.register($$0, PATCH_LARGE_FERN, (Holder<ConfiguredFeature<?, ?>>)reference15, new PlacementModifier[] {
/* 347 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(5), 
/* 348 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 350 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/* 353 */     PlacementUtils.register($$0, PATCH_CACTUS_DESERT, (Holder<ConfiguredFeature<?, ?>>)reference16, new PlacementModifier[] {
/* 354 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(6), 
/* 355 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 357 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 359 */     PlacementUtils.register($$0, PATCH_CACTUS_DECORATED, (Holder<ConfiguredFeature<?, ?>>)reference16, new PlacementModifier[] {
/* 360 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(13), 
/* 361 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 363 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/* 366 */     PlacementUtils.register($$0, PATCH_SUGAR_CANE_SWAMP, (Holder<ConfiguredFeature<?, ?>>)reference17, new PlacementModifier[] {
/* 367 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(3), 
/* 368 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 370 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 372 */     PlacementUtils.register($$0, PATCH_SUGAR_CANE_DESERT, (Holder<ConfiguredFeature<?, ?>>)reference17, new PlacementModifier[] {
/* 373 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 375 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 377 */     PlacementUtils.register($$0, PATCH_SUGAR_CANE_BADLANDS, (Holder<ConfiguredFeature<?, ?>>)reference17, new PlacementModifier[] {
/* 378 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(5), 
/* 379 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 381 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 383 */     PlacementUtils.register($$0, PATCH_SUGAR_CANE, (Holder<ConfiguredFeature<?, ?>>)reference17, new PlacementModifier[] {
/* 384 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(6), 
/* 385 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 387 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/* 390 */     PlacementUtils.register($$0, BROWN_MUSHROOM_NETHER, (Holder<ConfiguredFeature<?, ?>>)reference18, new PlacementModifier[] {
/* 391 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(2), 
/* 392 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, 
/*     */           
/* 394 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 396 */     PlacementUtils.register($$0, RED_MUSHROOM_NETHER, (Holder<ConfiguredFeature<?, ?>>)reference19, new PlacementModifier[] {
/* 397 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(2), 
/* 398 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, 
/*     */           
/* 400 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 402 */     PlacementUtils.register($$0, BROWN_MUSHROOM_NORMAL, (Holder<ConfiguredFeature<?, ?>>)reference18, 
/* 403 */         getMushroomPlacement(256, null));
/*     */     
/* 405 */     PlacementUtils.register($$0, RED_MUSHROOM_NORMAL, (Holder<ConfiguredFeature<?, ?>>)reference19, 
/* 406 */         getMushroomPlacement(512, null));
/*     */     
/* 408 */     PlacementUtils.register($$0, BROWN_MUSHROOM_TAIGA, (Holder<ConfiguredFeature<?, ?>>)reference18, 
/* 409 */         getMushroomPlacement(4, null));
/*     */     
/* 411 */     PlacementUtils.register($$0, RED_MUSHROOM_TAIGA, (Holder<ConfiguredFeature<?, ?>>)reference19, 
/* 412 */         getMushroomPlacement(256, null));
/*     */     
/* 414 */     PlacementUtils.register($$0, BROWN_MUSHROOM_OLD_GROWTH, (Holder<ConfiguredFeature<?, ?>>)reference18, 
/* 415 */         getMushroomPlacement(4, (PlacementModifier)CountPlacement.of(3)));
/*     */     
/* 417 */     PlacementUtils.register($$0, RED_MUSHROOM_OLD_GROWTH, (Holder<ConfiguredFeature<?, ?>>)reference19, 
/* 418 */         getMushroomPlacement(171, null));
/*     */     
/* 420 */     PlacementUtils.register($$0, BROWN_MUSHROOM_SWAMP, (Holder<ConfiguredFeature<?, ?>>)reference18, 
/* 421 */         getMushroomPlacement(0, (PlacementModifier)CountPlacement.of(2)));
/*     */     
/* 423 */     PlacementUtils.register($$0, RED_MUSHROOM_SWAMP, (Holder<ConfiguredFeature<?, ?>>)reference19, 
/* 424 */         getMushroomPlacement(64, null));
/*     */ 
/*     */     
/* 427 */     PlacementUtils.register($$0, FLOWER_WARM, (Holder<ConfiguredFeature<?, ?>>)reference20, new PlacementModifier[] {
/* 428 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(16), 
/* 429 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 431 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 433 */     PlacementUtils.register($$0, FLOWER_DEFAULT, (Holder<ConfiguredFeature<?, ?>>)reference20, new PlacementModifier[] {
/* 434 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(32), 
/* 435 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 437 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 439 */     PlacementUtils.register($$0, FLOWER_FLOWER_FOREST, (Holder<ConfiguredFeature<?, ?>>)reference21, new PlacementModifier[] {
/* 440 */           (PlacementModifier)CountPlacement.of(3), 
/* 441 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(2), 
/* 442 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 444 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 446 */     PlacementUtils.register($$0, FLOWER_SWAMP, (Holder<ConfiguredFeature<?, ?>>)reference22, new PlacementModifier[] {
/* 447 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(32), 
/* 448 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 450 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 452 */     PlacementUtils.register($$0, FLOWER_PLAINS, (Holder<ConfiguredFeature<?, ?>>)reference23, new PlacementModifier[] {
/* 453 */           (PlacementModifier)NoiseThresholdCountPlacement.of(-0.8D, 15, 4), 
/* 454 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(32), 
/* 455 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 457 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 459 */     PlacementUtils.register($$0, FLOWER_CHERRY, (Holder<ConfiguredFeature<?, ?>>)reference25, new PlacementModifier[] {
/* 460 */           (PlacementModifier)NoiseThresholdCountPlacement.of(-0.8D, 5, 10), 
/* 461 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 463 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 465 */     PlacementUtils.register($$0, FLOWER_MEADOW, (Holder<ConfiguredFeature<?, ?>>)reference24, new PlacementModifier[] {
/* 466 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 468 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/* 471 */     SurfaceWaterDepthFilter surfaceWaterDepthFilter = SurfaceWaterDepthFilter.forMaxDepth(0);
/* 472 */     PlacementUtils.register($$0, TREES_PLAINS, (Holder<ConfiguredFeature<?, ?>>)reference26, new PlacementModifier[] {
/* 473 */           PlacementUtils.countExtra(0, 0.05F, 1), 
/* 474 */           (PlacementModifier)InSquarePlacement.spread(), (PlacementModifier)surfaceWaterDepthFilter, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, 
/*     */ 
/*     */           
/* 477 */           (PlacementModifier)BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), (Vec3i)BlockPos.ZERO)), 
/* 478 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 480 */     PlacementUtils.register($$0, DARK_FOREST_VEGETATION, (Holder<ConfiguredFeature<?, ?>>)reference27, new PlacementModifier[] {
/* 481 */           (PlacementModifier)CountPlacement.of(16), 
/* 482 */           (PlacementModifier)InSquarePlacement.spread(), (PlacementModifier)surfaceWaterDepthFilter, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, 
/*     */ 
/*     */           
/* 485 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 487 */     PlacementUtils.register($$0, FLOWER_FOREST_FLOWERS, (Holder<ConfiguredFeature<?, ?>>)reference28, new PlacementModifier[] {
/* 488 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(7), 
/* 489 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 491 */           (PlacementModifier)CountPlacement.of((IntProvider)ClampedInt.of((IntProvider)UniformInt.of(-1, 3), 0, 3)), 
/* 492 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/* 494 */     PlacementUtils.register($$0, FOREST_FLOWERS, (Holder<ConfiguredFeature<?, ?>>)reference28, new PlacementModifier[] {
/* 495 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(7), 
/* 496 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 498 */           (PlacementModifier)CountPlacement.of((IntProvider)ClampedInt.of((IntProvider)UniformInt.of(-3, 1), 0, 1)), 
/* 499 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/* 502 */     PlacementUtils.register($$0, TREES_FLOWER_FOREST, (Holder<ConfiguredFeature<?, ?>>)reference29, 
/* 503 */         treePlacement(PlacementUtils.countExtra(6, 0.1F, 1)));
/*     */     
/* 505 */     PlacementUtils.register($$0, TREES_MEADOW, (Holder<ConfiguredFeature<?, ?>>)reference30, 
/* 506 */         treePlacement((PlacementModifier)RarityFilter.onAverageOnceEvery(100)));
/*     */     
/* 508 */     PlacementUtils.register($$0, TREES_CHERRY, (Holder<ConfiguredFeature<?, ?>>)reference35, 
/* 509 */         treePlacement(PlacementUtils.countExtra(10, 0.1F, 1), Blocks.CHERRY_SAPLING));
/*     */     
/* 511 */     PlacementUtils.register($$0, TREES_TAIGA, (Holder<ConfiguredFeature<?, ?>>)reference31, 
/* 512 */         treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
/*     */     
/* 514 */     PlacementUtils.register($$0, TREES_GROVE, (Holder<ConfiguredFeature<?, ?>>)reference32, 
/* 515 */         treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
/*     */     
/* 517 */     PlacementUtils.register($$0, TREES_BADLANDS, (Holder<ConfiguredFeature<?, ?>>)reference33, 
/* 518 */         treePlacement(PlacementUtils.countExtra(5, 0.1F, 1), Blocks.OAK_SAPLING));
/*     */     
/* 520 */     PlacementUtils.register($$0, TREES_SNOWY, (Holder<ConfiguredFeature<?, ?>>)reference34, 
/* 521 */         treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.SPRUCE_SAPLING));
/*     */     
/* 523 */     PlacementUtils.register($$0, TREES_SWAMP, (Holder<ConfiguredFeature<?, ?>>)reference36, new PlacementModifier[] {
/* 524 */           PlacementUtils.countExtra(2, 0.1F, 1), 
/* 525 */           (PlacementModifier)InSquarePlacement.spread(), 
/* 526 */           (PlacementModifier)SurfaceWaterDepthFilter.forMaxDepth(2), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, 
/*     */           
/* 528 */           (PlacementModifier)BiomeFilter.biome(), 
/* 529 */           (PlacementModifier)BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), (Vec3i)BlockPos.ZERO))
/*     */         });
/* 531 */     PlacementUtils.register($$0, TREES_WINDSWEPT_SAVANNA, (Holder<ConfiguredFeature<?, ?>>)reference37, 
/* 532 */         treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));
/*     */     
/* 534 */     PlacementUtils.register($$0, TREES_SAVANNA, (Holder<ConfiguredFeature<?, ?>>)reference37, 
/* 535 */         treePlacement(PlacementUtils.countExtra(1, 0.1F, 1)));
/*     */     
/* 537 */     PlacementUtils.register($$0, BIRCH_TALL, (Holder<ConfiguredFeature<?, ?>>)reference38, 
/* 538 */         treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
/*     */     
/* 540 */     PlacementUtils.register($$0, TREES_BIRCH, (Holder<ConfiguredFeature<?, ?>>)reference39, 
/* 541 */         treePlacement(PlacementUtils.countExtra(10, 0.1F, 1), Blocks.BIRCH_SAPLING));
/*     */     
/* 543 */     PlacementUtils.register($$0, TREES_WINDSWEPT_FOREST, (Holder<ConfiguredFeature<?, ?>>)reference40, 
/* 544 */         treePlacement(PlacementUtils.countExtra(3, 0.1F, 1)));
/*     */     
/* 546 */     PlacementUtils.register($$0, TREES_WINDSWEPT_HILLS, (Holder<ConfiguredFeature<?, ?>>)reference40, 
/* 547 */         treePlacement(PlacementUtils.countExtra(0, 0.1F, 1)));
/*     */     
/* 549 */     PlacementUtils.register($$0, TREES_WATER, (Holder<ConfiguredFeature<?, ?>>)reference41, 
/* 550 */         treePlacement(PlacementUtils.countExtra(0, 0.1F, 1)));
/*     */     
/* 552 */     PlacementUtils.register($$0, TREES_BIRCH_AND_OAK, (Holder<ConfiguredFeature<?, ?>>)reference42, 
/* 553 */         treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
/*     */     
/* 555 */     PlacementUtils.register($$0, TREES_SPARSE_JUNGLE, (Holder<ConfiguredFeature<?, ?>>)reference43, 
/* 556 */         treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));
/*     */     
/* 558 */     PlacementUtils.register($$0, TREES_OLD_GROWTH_SPRUCE_TAIGA, (Holder<ConfiguredFeature<?, ?>>)reference44, 
/* 559 */         treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
/*     */     
/* 561 */     PlacementUtils.register($$0, TREES_OLD_GROWTH_PINE_TAIGA, (Holder<ConfiguredFeature<?, ?>>)reference45, 
/* 562 */         treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));
/*     */     
/* 564 */     PlacementUtils.register($$0, TREES_JUNGLE, (Holder<ConfiguredFeature<?, ?>>)reference46, 
/* 565 */         treePlacement(PlacementUtils.countExtra(50, 0.1F, 1)));
/*     */     
/* 567 */     PlacementUtils.register($$0, BAMBOO_VEGETATION, (Holder<ConfiguredFeature<?, ?>>)reference47, 
/* 568 */         treePlacement(PlacementUtils.countExtra(30, 0.1F, 1)));
/*     */ 
/*     */     
/* 571 */     PlacementUtils.register($$0, MUSHROOM_ISLAND_VEGETATION, (Holder<ConfiguredFeature<?, ?>>)reference48, new PlacementModifier[] {
/* 572 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*     */           
/* 574 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/* 577 */     PlacementUtils.register($$0, TREES_MANGROVE, (Holder<ConfiguredFeature<?, ?>>)reference49, new PlacementModifier[] {
/* 578 */           (PlacementModifier)CountPlacement.of(25), 
/* 579 */           (PlacementModifier)InSquarePlacement.spread(), 
/* 580 */           (PlacementModifier)SurfaceWaterDepthFilter.forMaxDepth(5), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, 
/*     */           
/* 582 */           (PlacementModifier)BiomeFilter.biome(), 
/* 583 */           (PlacementModifier)BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.MANGROVE_PROPAGULE.defaultBlockState(), (Vec3i)BlockPos.ZERO))
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\placement\VegetationPlacements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */