/*     */ package net.minecraft.data.worldgen.features;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ import net.minecraft.data.worldgen.placement.PlacementUtils;
/*     */ import net.minecraft.data.worldgen.placement.TreePlacements;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.util.InclusiveRange;
/*     */ import net.minecraft.util.random.SimpleWeightedRandomList;
/*     */ import net.minecraft.util.valueproviders.BiasedToBottomInt;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.PinkPetalsBlock;
/*     */ import net.minecraft.world.level.block.SweetBerryBushBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.level.levelgen.feature.Feature;
/*     */ import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*     */ import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
/*     */ import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
/*     */ import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
/*     */ import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacementModifier;
/*     */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ 
/*     */ public class VegetationFeatures {
/*  47 */   public static final ResourceKey<ConfiguredFeature<?, ?>> BAMBOO_NO_PODZOL = FeatureUtils.createKey("bamboo_no_podzol");
/*  48 */   public static final ResourceKey<ConfiguredFeature<?, ?>> BAMBOO_SOME_PODZOL = FeatureUtils.createKey("bamboo_some_podzol");
/*  49 */   public static final ResourceKey<ConfiguredFeature<?, ?>> VINES = FeatureUtils.createKey("vines");
/*     */ 
/*     */ 
/*     */   
/*  53 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BROWN_MUSHROOM = FeatureUtils.createKey("patch_brown_mushroom");
/*  54 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_RED_MUSHROOM = FeatureUtils.createKey("patch_red_mushroom");
/*  55 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SUNFLOWER = FeatureUtils.createKey("patch_sunflower");
/*  56 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_PUMPKIN = FeatureUtils.createKey("patch_pumpkin");
/*     */   
/*  58 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BERRY_BUSH = FeatureUtils.createKey("patch_berry_bush");
/*     */ 
/*     */ 
/*     */   
/*     */   private static RandomPatchConfiguration grassPatch(BlockStateProvider $$0, int $$1) {
/*  63 */     return FeatureUtils.simpleRandomPatchConfiguration($$1, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, (FeatureConfiguration)new SimpleBlockConfiguration($$0)));
/*     */   }
/*     */   
/*  66 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TAIGA_GRASS = FeatureUtils.createKey("patch_taiga_grass");
/*     */   
/*  68 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GRASS = FeatureUtils.createKey("patch_grass");
/*  69 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GRASS_JUNGLE = FeatureUtils.createKey("patch_grass_jungle");
/*  70 */   public static final ResourceKey<ConfiguredFeature<?, ?>> SINGLE_PIECE_OF_GRASS = FeatureUtils.createKey("single_piece_of_grass");
/*     */ 
/*     */ 
/*     */   
/*  74 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DEAD_BUSH = FeatureUtils.createKey("patch_dead_bush");
/*  75 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MELON = FeatureUtils.createKey("patch_melon");
/*  76 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WATERLILY = FeatureUtils.createKey("patch_waterlily");
/*  77 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_GRASS = FeatureUtils.createKey("patch_tall_grass");
/*  78 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LARGE_FERN = FeatureUtils.createKey("patch_large_fern");
/*     */ 
/*     */ 
/*     */   
/*  82 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CACTUS = FeatureUtils.createKey("patch_cactus");
/*  83 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SUGAR_CANE = FeatureUtils.createKey("patch_sugar_cane");
/*     */ 
/*     */ 
/*     */   
/*  87 */   public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_DEFAULT = FeatureUtils.createKey("flower_default");
/*  88 */   public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_FLOWER_FOREST = FeatureUtils.createKey("flower_flower_forest");
/*  89 */   public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_SWAMP = FeatureUtils.createKey("flower_swamp");
/*  90 */   public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_PLAIN = FeatureUtils.createKey("flower_plain");
/*  91 */   public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_MEADOW = FeatureUtils.createKey("flower_meadow");
/*  92 */   public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_CHERRY = FeatureUtils.createKey("flower_cherry");
/*     */ 
/*     */ 
/*     */   
/*  96 */   public static final ResourceKey<ConfiguredFeature<?, ?>> FOREST_FLOWERS = FeatureUtils.createKey("forest_flowers");
/*     */   
/*  98 */   public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_FOREST_VEGETATION = FeatureUtils.createKey("dark_forest_vegetation");
/*     */   
/* 100 */   public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_FLOWER_FOREST = FeatureUtils.createKey("trees_flower_forest");
/*     */   
/* 102 */   public static final ResourceKey<ConfiguredFeature<?, ?>> MEADOW_TREES = FeatureUtils.createKey("meadow_trees");
/*     */   
/* 104 */   public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_TAIGA = FeatureUtils.createKey("trees_taiga");
/*     */   
/* 106 */   public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_GROVE = FeatureUtils.createKey("trees_grove");
/*     */   
/* 108 */   public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_SAVANNA = FeatureUtils.createKey("trees_savanna");
/*     */   
/* 110 */   public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_TALL = FeatureUtils.createKey("birch_tall");
/*     */   
/* 112 */   public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_WINDSWEPT_HILLS = FeatureUtils.createKey("trees_windswept_hills");
/*     */   
/* 114 */   public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_WATER = FeatureUtils.createKey("trees_water");
/*     */   
/* 116 */   public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH_AND_OAK = FeatureUtils.createKey("trees_birch_and_oak");
/*     */   
/* 118 */   public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_PLAINS = FeatureUtils.createKey("trees_plains");
/*     */   
/* 120 */   public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_SPARSE_JUNGLE = FeatureUtils.createKey("trees_sparse_jungle");
/*     */   
/* 122 */   public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_SPRUCE_TAIGA = FeatureUtils.createKey("trees_old_growth_spruce_taiga");
/*     */   
/* 124 */   public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_PINE_TAIGA = FeatureUtils.createKey("trees_old_growth_pine_taiga");
/*     */   
/* 126 */   public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_JUNGLE = FeatureUtils.createKey("trees_jungle");
/*     */   
/* 128 */   public static final ResourceKey<ConfiguredFeature<?, ?>> BAMBOO_VEGETATION = FeatureUtils.createKey("bamboo_vegetation");
/*     */   
/* 130 */   public static final ResourceKey<ConfiguredFeature<?, ?>> MUSHROOM_ISLAND_VEGETATION = FeatureUtils.createKey("mushroom_island_vegetation");
/*     */   
/* 132 */   public static final ResourceKey<ConfiguredFeature<?, ?>> MANGROVE_VEGETATION = FeatureUtils.createKey("mangrove_vegetation");
/*     */   
/*     */   public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> $$0) {
/* 135 */     HolderGetter<ConfiguredFeature<?, ?>> $$1 = $$0.lookup(Registries.CONFIGURED_FEATURE);
/* 136 */     Holder.Reference reference1 = $$1.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM);
/* 137 */     Holder.Reference reference2 = $$1.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM);
/* 138 */     Holder.Reference reference3 = $$1.getOrThrow(TreeFeatures.FANCY_OAK_BEES_005);
/* 139 */     Holder.Reference reference4 = $$1.getOrThrow(TreeFeatures.OAK_BEES_005);
/* 140 */     Holder.Reference reference5 = $$1.getOrThrow(PATCH_GRASS_JUNGLE);
/*     */     
/* 142 */     HolderGetter<PlacedFeature> $$7 = $$0.lookup(Registries.PLACED_FEATURE);
/* 143 */     Holder.Reference reference6 = $$7.getOrThrow(TreePlacements.DARK_OAK_CHECKED);
/* 144 */     Holder.Reference reference7 = $$7.getOrThrow(TreePlacements.BIRCH_CHECKED);
/* 145 */     Holder.Reference reference8 = $$7.getOrThrow(TreePlacements.FANCY_OAK_CHECKED);
/* 146 */     Holder.Reference reference9 = $$7.getOrThrow(TreePlacements.BIRCH_BEES_002);
/* 147 */     Holder.Reference reference10 = $$7.getOrThrow(TreePlacements.FANCY_OAK_BEES_002);
/* 148 */     Holder.Reference reference11 = $$7.getOrThrow(TreePlacements.FANCY_OAK_BEES);
/* 149 */     Holder.Reference reference12 = $$7.getOrThrow(TreePlacements.PINE_CHECKED);
/* 150 */     Holder.Reference reference13 = $$7.getOrThrow(TreePlacements.SPRUCE_CHECKED);
/* 151 */     Holder.Reference reference14 = $$7.getOrThrow(TreePlacements.PINE_ON_SNOW);
/* 152 */     Holder.Reference reference15 = $$7.getOrThrow(TreePlacements.ACACIA_CHECKED);
/* 153 */     Holder.Reference reference16 = $$7.getOrThrow(TreePlacements.SUPER_BIRCH_BEES_0002);
/* 154 */     Holder.Reference reference17 = $$7.getOrThrow(TreePlacements.BIRCH_BEES_0002_PLACED);
/* 155 */     Holder.Reference reference18 = $$7.getOrThrow(TreePlacements.FANCY_OAK_BEES_0002);
/* 156 */     Holder.Reference reference19 = $$7.getOrThrow(TreePlacements.JUNGLE_BUSH);
/* 157 */     Holder.Reference reference20 = $$7.getOrThrow(TreePlacements.MEGA_SPRUCE_CHECKED);
/* 158 */     Holder.Reference reference21 = $$7.getOrThrow(TreePlacements.MEGA_PINE_CHECKED);
/* 159 */     Holder.Reference reference22 = $$7.getOrThrow(TreePlacements.MEGA_JUNGLE_TREE_CHECKED);
/* 160 */     Holder.Reference reference23 = $$7.getOrThrow(TreePlacements.TALL_MANGROVE_CHECKED);
/* 161 */     Holder.Reference reference24 = $$7.getOrThrow(TreePlacements.OAK_CHECKED);
/* 162 */     Holder.Reference reference25 = $$7.getOrThrow(TreePlacements.OAK_BEES_002);
/* 163 */     Holder.Reference reference26 = $$7.getOrThrow(TreePlacements.SUPER_BIRCH_BEES);
/* 164 */     Holder.Reference reference27 = $$7.getOrThrow(TreePlacements.SPRUCE_ON_SNOW);
/* 165 */     Holder.Reference reference28 = $$7.getOrThrow(TreePlacements.OAK_BEES_0002);
/* 166 */     Holder.Reference reference29 = $$7.getOrThrow(TreePlacements.JUNGLE_TREE_CHECKED);
/* 167 */     Holder.Reference reference30 = $$7.getOrThrow(TreePlacements.MANGROVE_CHECKED);
/*     */ 
/*     */ 
/*     */     
/* 171 */     FeatureUtils.register($$0, BAMBOO_NO_PODZOL, Feature.BAMBOO, new ProbabilityFeatureConfiguration(0.0F));
/*     */ 
/*     */     
/* 174 */     FeatureUtils.register($$0, BAMBOO_SOME_PODZOL, Feature.BAMBOO, new ProbabilityFeatureConfiguration(0.2F));
/*     */ 
/*     */     
/* 177 */     FeatureUtils.register($$0, VINES, Feature.VINES);
/*     */ 
/*     */ 
/*     */     
/* 181 */     FeatureUtils.register($$0, PATCH_BROWN_MUSHROOM, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
/* 182 */             (BlockStateProvider)BlockStateProvider.simple(Blocks.BROWN_MUSHROOM))));
/*     */     
/* 184 */     FeatureUtils.register($$0, PATCH_RED_MUSHROOM, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
/* 185 */             (BlockStateProvider)BlockStateProvider.simple(Blocks.RED_MUSHROOM))));
/*     */     
/* 187 */     FeatureUtils.register($$0, PATCH_SUNFLOWER, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
/* 188 */             (BlockStateProvider)BlockStateProvider.simple(Blocks.SUNFLOWER))));
/*     */     
/* 190 */     FeatureUtils.register($$0, PATCH_PUMPKIN, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
/* 191 */             (BlockStateProvider)BlockStateProvider.simple(Blocks.PUMPKIN)), 
/* 192 */           List.of(Blocks.GRASS_BLOCK)));
/*     */ 
/*     */     
/* 195 */     FeatureUtils.register($$0, PATCH_BERRY_BUSH, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
/*     */             
/* 197 */             (BlockStateProvider)BlockStateProvider.simple((BlockState)Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue((Property)SweetBerryBushBlock.AGE, Integer.valueOf(3)))), 
/*     */           
/* 199 */           List.of(Blocks.GRASS_BLOCK)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     FeatureUtils.register($$0, PATCH_TAIGA_GRASS, Feature.RANDOM_PATCH, 
/* 205 */         grassPatch((BlockStateProvider)new WeightedStateProvider(
/* 206 */             SimpleWeightedRandomList.builder().add(Blocks.SHORT_GRASS.defaultBlockState(), 1).add(Blocks.FERN.defaultBlockState(), 4)), 32));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     FeatureUtils.register($$0, PATCH_GRASS, Feature.RANDOM_PATCH, 
/* 212 */         grassPatch(
/* 213 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.SHORT_GRASS), 32));
/*     */ 
/*     */ 
/*     */     
/* 217 */     FeatureUtils.register($$0, PATCH_GRASS_JUNGLE, Feature.RANDOM_PATCH, new RandomPatchConfiguration(32, 7, 3, 
/*     */ 
/*     */ 
/*     */           
/* 221 */           PlacementUtils.filtered(Feature.SIMPLE_BLOCK, (FeatureConfiguration)new SimpleBlockConfiguration((BlockStateProvider)new WeightedStateProvider(
/*     */ 
/*     */                 
/* 224 */                 SimpleWeightedRandomList.builder().add(Blocks.SHORT_GRASS.defaultBlockState(), 3).add(Blocks.FERN.defaultBlockState(), 1))), 
/*     */             
/* 226 */             BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, 
/*     */               
/* 228 */               BlockPredicate.not(
/* 229 */                 BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), new Block[] { Blocks.PODZOL }))))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     FeatureUtils.register($$0, SINGLE_PIECE_OF_GRASS, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
/* 235 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.SHORT_GRASS.defaultBlockState())));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     FeatureUtils.register($$0, PATCH_DEAD_BUSH, Feature.RANDOM_PATCH, 
/* 241 */         grassPatch(
/* 242 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.DEAD_BUSH), 4));
/*     */ 
/*     */ 
/*     */     
/* 246 */     FeatureUtils.register($$0, PATCH_MELON, Feature.RANDOM_PATCH, new RandomPatchConfiguration(64, 7, 3, 
/*     */ 
/*     */ 
/*     */           
/* 250 */           PlacementUtils.filtered(Feature.SIMPLE_BLOCK, (FeatureConfiguration)new SimpleBlockConfiguration(
/*     */ 
/*     */               
/* 253 */               (BlockStateProvider)BlockStateProvider.simple(Blocks.MELON)), 
/*     */             
/* 255 */             BlockPredicate.allOf(new BlockPredicate[] {
/* 256 */                 BlockPredicate.replaceable(), 
/* 257 */                 BlockPredicate.noFluid(), 
/* 258 */                 BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), new Block[] { Blocks.GRASS_BLOCK })
/*     */               }))));
/*     */ 
/*     */     
/* 262 */     FeatureUtils.register($$0, PATCH_WATERLILY, Feature.RANDOM_PATCH, new RandomPatchConfiguration(10, 7, 3, 
/*     */ 
/*     */ 
/*     */           
/* 266 */           PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, (FeatureConfiguration)new SimpleBlockConfiguration((BlockStateProvider)BlockStateProvider.simple(Blocks.LILY_PAD)))));
/*     */     
/* 268 */     FeatureUtils.register($$0, PATCH_TALL_GRASS, Feature.RANDOM_PATCH, 
/* 269 */         FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
/* 270 */             (BlockStateProvider)BlockStateProvider.simple(Blocks.TALL_GRASS))));
/*     */ 
/*     */     
/* 273 */     FeatureUtils.register($$0, PATCH_LARGE_FERN, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
/*     */             
/* 275 */             (BlockStateProvider)BlockStateProvider.simple(Blocks.LARGE_FERN))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     FeatureUtils.register($$0, PATCH_CACTUS, Feature.RANDOM_PATCH, 
/* 282 */         FeatureUtils.simpleRandomPatchConfiguration(10, 
/*     */           
/* 284 */           PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, 
/*     */             
/* 286 */             (FeatureConfiguration)BlockColumnConfiguration.simple(
/* 287 */               (IntProvider)BiasedToBottomInt.of(1, 3), 
/* 288 */               (BlockStateProvider)BlockStateProvider.simple(Blocks.CACTUS)), new PlacementModifier[] {
/*     */               
/* 290 */               (PlacementModifier)BlockPredicateFilter.forPredicate(
/* 291 */                 BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, 
/*     */                   
/* 293 */                   BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), (Vec3i)BlockPos.ZERO)))
/*     */             })));
/*     */ 
/*     */ 
/*     */     
/* 298 */     FeatureUtils.register($$0, PATCH_SUGAR_CANE, Feature.RANDOM_PATCH, new RandomPatchConfiguration(20, 4, 0, 
/*     */ 
/*     */ 
/*     */           
/* 302 */           PlacementUtils.inlinePlaced(Feature.BLOCK_COLUMN, 
/*     */             
/* 304 */             (FeatureConfiguration)BlockColumnConfiguration.simple(
/* 305 */               (IntProvider)BiasedToBottomInt.of(2, 4), 
/* 306 */               (BlockStateProvider)BlockStateProvider.simple(Blocks.SUGAR_CANE)), new PlacementModifier[] {
/*     */               
/* 308 */               (PlacementModifier)BlockPredicateFilter.forPredicate(
/* 309 */                 BlockPredicate.allOf(new BlockPredicate[] {
/*     */                     
/* 311 */                     BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.wouldSurvive(Blocks.SUGAR_CANE.defaultBlockState(), (Vec3i)BlockPos.ZERO), 
/* 312 */                     BlockPredicate.anyOf(new BlockPredicate[] {
/* 313 */                         BlockPredicate.matchesFluids((Vec3i)new BlockPos(1, -1, 0), new Fluid[] { (Fluid)Fluids.WATER, (Fluid)Fluids.FLOWING_WATER
/* 314 */                           }), BlockPredicate.matchesFluids((Vec3i)new BlockPos(-1, -1, 0), new Fluid[] { (Fluid)Fluids.WATER, (Fluid)Fluids.FLOWING_WATER
/* 315 */                           }), BlockPredicate.matchesFluids((Vec3i)new BlockPos(0, -1, 1), new Fluid[] { (Fluid)Fluids.WATER, (Fluid)Fluids.FLOWING_WATER
/* 316 */                           }), BlockPredicate.matchesFluids((Vec3i)new BlockPos(0, -1, -1), new Fluid[] { (Fluid)Fluids.WATER, (Fluid)Fluids.FLOWING_WATER })
/*     */                       })
/*     */                   }))
/*     */             })));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     FeatureUtils.register($$0, FLOWER_DEFAULT, Feature.FLOWER, 
/* 326 */         grassPatch((BlockStateProvider)new WeightedStateProvider(
/* 327 */             SimpleWeightedRandomList.builder()
/* 328 */             .add(Blocks.POPPY.defaultBlockState(), 2)
/* 329 */             .add(Blocks.DANDELION.defaultBlockState(), 1)), 64));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 334 */     FeatureUtils.register($$0, FLOWER_FLOWER_FOREST, Feature.FLOWER, new RandomPatchConfiguration(96, 6, 2, 
/*     */ 
/*     */ 
/*     */           
/* 338 */           PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, (FeatureConfiguration)new SimpleBlockConfiguration((BlockStateProvider)new NoiseProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0D, new double[0]), 0.020833334F, 
/*     */ 
/*     */                 
/* 341 */                 List.of(new BlockState[] { 
/* 342 */                     Blocks.DANDELION.defaultBlockState(), Blocks.POPPY
/* 343 */                     .defaultBlockState(), Blocks.ALLIUM
/* 344 */                     .defaultBlockState(), Blocks.AZURE_BLUET
/* 345 */                     .defaultBlockState(), Blocks.RED_TULIP
/* 346 */                     .defaultBlockState(), Blocks.ORANGE_TULIP
/* 347 */                     .defaultBlockState(), Blocks.WHITE_TULIP
/* 348 */                     .defaultBlockState(), Blocks.PINK_TULIP
/* 349 */                     .defaultBlockState(), Blocks.OXEYE_DAISY
/* 350 */                     .defaultBlockState(), Blocks.CORNFLOWER
/* 351 */                     .defaultBlockState(), Blocks.LILY_OF_THE_VALLEY
/* 352 */                     .defaultBlockState() }))))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 357 */     FeatureUtils.register($$0, FLOWER_SWAMP, Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, 
/*     */ 
/*     */ 
/*     */           
/* 361 */           PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, (FeatureConfiguration)new SimpleBlockConfiguration((BlockStateProvider)BlockStateProvider.simple(Blocks.BLUE_ORCHID)))));
/*     */     
/* 363 */     FeatureUtils.register($$0, FLOWER_PLAIN, Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, 
/*     */ 
/*     */ 
/*     */           
/* 367 */           PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, (FeatureConfiguration)new SimpleBlockConfiguration((BlockStateProvider)new NoiseThresholdProvider(2345L, new NormalNoise.NoiseParameters(0, 1.0D, new double[0]), 0.005F, -0.8F, 0.33333334F, Blocks.DANDELION
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 374 */                 .defaultBlockState(), 
/* 375 */                 List.of(Blocks.ORANGE_TULIP
/* 376 */                   .defaultBlockState(), Blocks.RED_TULIP
/* 377 */                   .defaultBlockState(), Blocks.PINK_TULIP
/* 378 */                   .defaultBlockState(), Blocks.WHITE_TULIP
/* 379 */                   .defaultBlockState()), 
/*     */                 
/* 381 */                 List.of(Blocks.POPPY
/* 382 */                   .defaultBlockState(), Blocks.AZURE_BLUET
/* 383 */                   .defaultBlockState(), Blocks.OXEYE_DAISY
/* 384 */                   .defaultBlockState(), Blocks.CORNFLOWER
/* 385 */                   .defaultBlockState()))))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 391 */     FeatureUtils.register($$0, FLOWER_MEADOW, Feature.FLOWER, new RandomPatchConfiguration(96, 6, 2, 
/*     */ 
/*     */ 
/*     */           
/* 395 */           PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, (FeatureConfiguration)new SimpleBlockConfiguration((BlockStateProvider)new DualNoiseProvider(new InclusiveRange(
/*     */ 
/*     */                   
/* 398 */                   Integer.valueOf(1), Integer.valueOf(3)), new NormalNoise.NoiseParameters(-10, 1.0D, new double[0]), 1.0F, 2345L, new NormalNoise.NoiseParameters(-3, 1.0D, new double[0]), 1.0F, List.of(Blocks.TALL_GRASS
/* 399 */                   .defaultBlockState(), Blocks.ALLIUM
/* 400 */                   .defaultBlockState(), Blocks.POPPY
/* 401 */                   .defaultBlockState(), Blocks.AZURE_BLUET
/* 402 */                   .defaultBlockState(), Blocks.DANDELION
/* 403 */                   .defaultBlockState(), Blocks.CORNFLOWER
/* 404 */                   .defaultBlockState(), Blocks.OXEYE_DAISY
/* 405 */                   .defaultBlockState(), Blocks.SHORT_GRASS
/* 406 */                   .defaultBlockState()))))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 411 */     SimpleWeightedRandomList.Builder<BlockState> $$33 = SimpleWeightedRandomList.builder();
/* 412 */     for (int $$34 = 1; $$34 <= 4; $$34++) {
/* 413 */       for (Direction $$35 : Direction.Plane.HORIZONTAL) {
/* 414 */         $$33.add(((BlockState)Blocks.PINK_PETALS.defaultBlockState().setValue((Property)PinkPetalsBlock.AMOUNT, Integer.valueOf($$34))).setValue((Property)PinkPetalsBlock.FACING, (Comparable)$$35), 1);
/*     */       }
/*     */     } 
/* 417 */     FeatureUtils.register($$0, FLOWER_CHERRY, Feature.FLOWER, new RandomPatchConfiguration(96, 6, 2, 
/*     */ 
/*     */ 
/*     */           
/* 421 */           PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, (FeatureConfiguration)new SimpleBlockConfiguration((BlockStateProvider)new WeightedStateProvider($$33)))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 428 */     FeatureUtils.register($$0, FOREST_FLOWERS, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(
/* 429 */           (HolderSet)HolderSet.direct(new Holder[] {
/* 430 */               PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, 
/*     */                 
/* 432 */                 (FeatureConfiguration)FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
/* 433 */                     (BlockStateProvider)BlockStateProvider.simple(Blocks.LILAC))), new PlacementModifier[0]), 
/*     */ 
/*     */               
/* 436 */               PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, 
/*     */                 
/* 438 */                 (FeatureConfiguration)FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
/* 439 */                     (BlockStateProvider)BlockStateProvider.simple(Blocks.ROSE_BUSH))), new PlacementModifier[0]), 
/*     */ 
/*     */               
/* 442 */               PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, 
/*     */                 
/* 444 */                 (FeatureConfiguration)FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
/* 445 */                     (BlockStateProvider)BlockStateProvider.simple(Blocks.PEONY))), new PlacementModifier[0]), 
/*     */ 
/*     */               
/* 448 */               PlacementUtils.inlinePlaced(Feature.NO_BONEMEAL_FLOWER, 
/*     */                 
/* 450 */                 (FeatureConfiguration)FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
/* 451 */                     (BlockStateProvider)BlockStateProvider.simple(Blocks.LILY_OF_THE_VALLEY))), new PlacementModifier[0])
/*     */             })));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 457 */     FeatureUtils.register($$0, DARK_FOREST_VEGETATION, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 458 */           List.of(new WeightedPlacedFeature(
/* 459 */               PlacementUtils.inlinePlaced((Holder)reference1, new PlacementModifier[0]), 0.025F), new WeightedPlacedFeature(
/* 460 */               PlacementUtils.inlinePlaced((Holder)reference2, new PlacementModifier[0]), 0.05F), new WeightedPlacedFeature((Holder)reference6, 0.6666667F), new WeightedPlacedFeature((Holder)reference7, 0.2F), new WeightedPlacedFeature((Holder)reference8, 0.1F)), (Holder)reference24));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 468 */     FeatureUtils.register($$0, TREES_FLOWER_FOREST, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 469 */           List.of(new WeightedPlacedFeature((Holder)reference9, 0.2F), new WeightedPlacedFeature((Holder)reference10, 0.1F)), (Holder)reference25));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 476 */     FeatureUtils.register($$0, MEADOW_TREES, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 477 */           List.of(new WeightedPlacedFeature((Holder)reference11, 0.5F)), (Holder)reference26));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 483 */     FeatureUtils.register($$0, TREES_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 484 */           List.of(new WeightedPlacedFeature((Holder)reference12, 0.33333334F)), (Holder)reference13));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 490 */     FeatureUtils.register($$0, TREES_GROVE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 491 */           List.of(new WeightedPlacedFeature((Holder)reference14, 0.33333334F)), (Holder)reference27));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 497 */     FeatureUtils.register($$0, TREES_SAVANNA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 498 */           List.of(new WeightedPlacedFeature((Holder)reference15, 0.8F)), (Holder)reference24));
/*     */ 
/*     */ 
/*     */     
/* 502 */     FeatureUtils.register($$0, BIRCH_TALL, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 503 */           List.of(new WeightedPlacedFeature((Holder)reference16, 0.5F)), (Holder)reference17));
/*     */ 
/*     */ 
/*     */     
/* 507 */     FeatureUtils.register($$0, TREES_WINDSWEPT_HILLS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 508 */           List.of(new WeightedPlacedFeature((Holder)reference13, 0.666F), new WeightedPlacedFeature((Holder)reference8, 0.1F)), (Holder)reference24));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 515 */     FeatureUtils.register($$0, TREES_WATER, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 516 */           List.of(new WeightedPlacedFeature((Holder)reference8, 0.1F)), (Holder)reference24));
/*     */ 
/*     */ 
/*     */     
/* 520 */     FeatureUtils.register($$0, TREES_BIRCH_AND_OAK, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 521 */           List.of(new WeightedPlacedFeature((Holder)reference17, 0.2F), new WeightedPlacedFeature((Holder)reference18, 0.1F)), (Holder)reference28));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 528 */     FeatureUtils.register($$0, TREES_PLAINS, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 529 */           List.of(new WeightedPlacedFeature(PlacementUtils.inlinePlaced((Holder)reference3, new PlacementModifier[0]), 0.33333334F)), 
/* 530 */           PlacementUtils.inlinePlaced((Holder)reference4, new PlacementModifier[0])));
/*     */ 
/*     */     
/* 533 */     FeatureUtils.register($$0, TREES_SPARSE_JUNGLE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 534 */           List.of(new WeightedPlacedFeature((Holder)reference8, 0.1F), new WeightedPlacedFeature((Holder)reference19, 0.5F)), (Holder)reference29));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 541 */     FeatureUtils.register($$0, TREES_OLD_GROWTH_SPRUCE_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 542 */           List.of(new WeightedPlacedFeature((Holder)reference20, 0.33333334F), new WeightedPlacedFeature((Holder)reference12, 0.33333334F)), (Holder)reference13));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 549 */     FeatureUtils.register($$0, TREES_OLD_GROWTH_PINE_TAIGA, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 550 */           List.of(new WeightedPlacedFeature((Holder)reference20, 0.025641026F), new WeightedPlacedFeature((Holder)reference21, 0.30769232F), new WeightedPlacedFeature((Holder)reference12, 0.33333334F)), (Holder)reference13));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 558 */     FeatureUtils.register($$0, TREES_JUNGLE, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 559 */           List.of(new WeightedPlacedFeature((Holder)reference8, 0.1F), new WeightedPlacedFeature((Holder)reference19, 0.5F), new WeightedPlacedFeature((Holder)reference22, 0.33333334F)), (Holder)reference29));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 567 */     FeatureUtils.register($$0, BAMBOO_VEGETATION, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 568 */           List.of(new WeightedPlacedFeature((Holder)reference8, 0.05F), new WeightedPlacedFeature((Holder)reference19, 0.15F), new WeightedPlacedFeature((Holder)reference22, 0.7F)), 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 573 */           PlacementUtils.inlinePlaced((Holder)reference5, new PlacementModifier[0])));
/*     */ 
/*     */     
/* 576 */     FeatureUtils.register($$0, MUSHROOM_ISLAND_VEGETATION, Feature.RANDOM_BOOLEAN_SELECTOR, new RandomBooleanFeatureConfiguration(
/* 577 */           PlacementUtils.inlinePlaced((Holder)reference2, new PlacementModifier[0]), 
/* 578 */           PlacementUtils.inlinePlaced((Holder)reference1, new PlacementModifier[0])));
/*     */ 
/*     */     
/* 581 */     FeatureUtils.register($$0, MANGROVE_VEGETATION, Feature.RANDOM_SELECTOR, new RandomFeatureConfiguration(
/* 582 */           List.of(new WeightedPlacedFeature((Holder)reference23, 0.85F)), (Holder)reference30));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\features\VegetationFeatures.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */