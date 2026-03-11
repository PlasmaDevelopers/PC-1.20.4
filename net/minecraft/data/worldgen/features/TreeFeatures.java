/*     */ package net.minecraft.data.worldgen.features;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalInt;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.random.SimpleWeightedRandomList;
/*     */ import net.minecraft.util.valueproviders.ConstantInt;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.util.valueproviders.WeightedListInt;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.HugeMushroomBlock;
/*     */ import net.minecraft.world.level.block.MangrovePropaguleBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.level.levelgen.feature.Feature;
/*     */ import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
/*     */ import net.minecraft.world.level.levelgen.feature.featuresize.ThreeLayersFeatureSize;
/*     */ import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
/*     */ import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement;
/*     */ import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*     */ import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
/*     */ import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
/*     */ import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
/*     */ import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
/*     */ import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
/*     */ import net.minecraft.world.level.levelgen.feature.treedecorators.CocoaDecorator;
/*     */ import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
/*     */ import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;
/*     */ import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.trunkplacers.DarkOakTrunkPlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.trunkplacers.MegaJungleTrunkPlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
/*     */ import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;
/*     */ 
/*     */ public class TreeFeatures {
/*  69 */   public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_FUNGUS = FeatureUtils.createKey("crimson_fungus");
/*  70 */   public static final ResourceKey<ConfiguredFeature<?, ?>> CRIMSON_FUNGUS_PLANTED = FeatureUtils.createKey("crimson_fungus_planted");
/*  71 */   public static final ResourceKey<ConfiguredFeature<?, ?>> WARPED_FUNGUS = FeatureUtils.createKey("warped_fungus");
/*  72 */   public static final ResourceKey<ConfiguredFeature<?, ?>> WARPED_FUNGUS_PLANTED = FeatureUtils.createKey("warped_fungus_planted");
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_BROWN_MUSHROOM = FeatureUtils.createKey("huge_brown_mushroom");
/*  77 */   public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_RED_MUSHROOM = FeatureUtils.createKey("huge_red_mushroom");
/*     */ 
/*     */ 
/*     */   
/*     */   private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block $$0, Block $$1, int $$2, int $$3, int $$4, int $$5) {
/*  82 */     return new TreeConfiguration.TreeConfigurationBuilder(
/*  83 */         (BlockStateProvider)BlockStateProvider.simple($$0), (TrunkPlacer)new StraightTrunkPlacer($$2, $$3, $$4), 
/*     */         
/*  85 */         (BlockStateProvider)BlockStateProvider.simple($$1), (FoliagePlacer)new BlobFoliagePlacer(
/*  86 */           (IntProvider)ConstantInt.of($$5), (IntProvider)ConstantInt.of(0), 3), (FeatureSize)new TwoLayersFeatureSize(1, 0, 1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static TreeConfiguration.TreeConfigurationBuilder createOak() {
/*  92 */     return createStraightBlobTree(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 4, 2, 0, 2).ignoreVines();
/*     */   }
/*     */   
/*     */   private static TreeConfiguration.TreeConfigurationBuilder createBirch() {
/*  96 */     return createStraightBlobTree(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 5, 2, 0, 2).ignoreVines();
/*     */   }
/*     */   
/*     */   private static TreeConfiguration.TreeConfigurationBuilder createSuperBirch() {
/* 100 */     return createStraightBlobTree(Blocks.BIRCH_LOG, Blocks.BIRCH_LEAVES, 5, 2, 6, 2).ignoreVines();
/*     */   }
/*     */   
/*     */   private static TreeConfiguration.TreeConfigurationBuilder createJungleTree() {
/* 104 */     return createStraightBlobTree(Blocks.JUNGLE_LOG, Blocks.JUNGLE_LEAVES, 4, 8, 0, 2);
/*     */   }
/*     */   
/*     */   private static TreeConfiguration.TreeConfigurationBuilder createFancyOak() {
/* 108 */     return (new TreeConfiguration.TreeConfigurationBuilder(
/* 109 */         (BlockStateProvider)BlockStateProvider.simple(Blocks.OAK_LOG), (TrunkPlacer)new FancyTrunkPlacer(3, 11, 0), 
/*     */         
/* 111 */         (BlockStateProvider)BlockStateProvider.simple(Blocks.OAK_LEAVES), (FoliagePlacer)new FancyFoliagePlacer(
/* 112 */           (IntProvider)ConstantInt.of(2), (IntProvider)ConstantInt.of(4), 4), (FeatureSize)new TwoLayersFeatureSize(0, 0, 0, 
/* 113 */           OptionalInt.of(4))))
/* 114 */       .ignoreVines();
/*     */   }
/*     */   
/*     */   private static TreeConfiguration.TreeConfigurationBuilder cherry() {
/* 118 */     return (new TreeConfiguration.TreeConfigurationBuilder(
/* 119 */         (BlockStateProvider)BlockStateProvider.simple(Blocks.CHERRY_LOG), (TrunkPlacer)new CherryTrunkPlacer(7, 1, 0, (IntProvider)new WeightedListInt(
/*     */             
/* 121 */             SimpleWeightedRandomList.builder()
/* 122 */             .add(ConstantInt.of(1), 1)
/* 123 */             .add(ConstantInt.of(2), 1)
/* 124 */             .add(ConstantInt.of(3), 1)
/* 125 */             .build()), 
/* 126 */           (IntProvider)UniformInt.of(2, 4), 
/* 127 */           UniformInt.of(-4, -3), 
/* 128 */           (IntProvider)UniformInt.of(-1, 0)), 
/*     */         
/* 130 */         (BlockStateProvider)BlockStateProvider.simple(Blocks.CHERRY_LEAVES), (FoliagePlacer)new CherryFoliagePlacer(
/* 131 */           (IntProvider)ConstantInt.of(4), (IntProvider)ConstantInt.of(0), (IntProvider)ConstantInt.of(5), 0.25F, 0.5F, 0.16666667F, 0.33333334F), (FeatureSize)new TwoLayersFeatureSize(1, 0, 2)))
/*     */       
/* 133 */       .ignoreVines();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 138 */   public static final ResourceKey<ConfiguredFeature<?, ?>> OAK = FeatureUtils.createKey("oak");
/* 139 */   public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_OAK = FeatureUtils.createKey("dark_oak");
/* 140 */   public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH = FeatureUtils.createKey("birch");
/*     */   
/* 142 */   public static final ResourceKey<ConfiguredFeature<?, ?>> ACACIA = FeatureUtils.createKey("acacia");
/* 143 */   public static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE = FeatureUtils.createKey("spruce");
/* 144 */   public static final ResourceKey<ConfiguredFeature<?, ?>> PINE = FeatureUtils.createKey("pine");
/* 145 */   public static final ResourceKey<ConfiguredFeature<?, ?>> JUNGLE_TREE = FeatureUtils.createKey("jungle_tree");
/* 146 */   public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK = FeatureUtils.createKey("fancy_oak");
/*     */   
/* 148 */   public static final ResourceKey<ConfiguredFeature<?, ?>> JUNGLE_TREE_NO_VINE = FeatureUtils.createKey("jungle_tree_no_vine");
/*     */   
/* 150 */   public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_JUNGLE_TREE = FeatureUtils.createKey("mega_jungle_tree");
/* 151 */   public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_SPRUCE = FeatureUtils.createKey("mega_spruce");
/* 152 */   public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_PINE = FeatureUtils.createKey("mega_pine");
/* 153 */   public static final ResourceKey<ConfiguredFeature<?, ?>> SUPER_BIRCH_BEES_0002 = FeatureUtils.createKey("super_birch_bees_0002");
/* 154 */   public static final ResourceKey<ConfiguredFeature<?, ?>> SUPER_BIRCH_BEES = FeatureUtils.createKey("super_birch_bees");
/*     */   
/* 156 */   public static final ResourceKey<ConfiguredFeature<?, ?>> SWAMP_OAK = FeatureUtils.createKey("swamp_oak");
/* 157 */   public static final ResourceKey<ConfiguredFeature<?, ?>> JUNGLE_BUSH = FeatureUtils.createKey("jungle_bush");
/* 158 */   public static final ResourceKey<ConfiguredFeature<?, ?>> AZALEA_TREE = FeatureUtils.createKey("azalea_tree");
/*     */   
/* 160 */   public static final ResourceKey<ConfiguredFeature<?, ?>> MANGROVE = FeatureUtils.createKey("mangrove");
/*     */   
/* 162 */   public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_MANGROVE = FeatureUtils.createKey("tall_mangrove");
/*     */   
/* 164 */   public static final ResourceKey<ConfiguredFeature<?, ?>> CHERRY = FeatureUtils.createKey("cherry");
/*     */   
/* 166 */   public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_BEES_0002 = FeatureUtils.createKey("oak_bees_0002");
/* 167 */   public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_BEES_002 = FeatureUtils.createKey("oak_bees_002");
/* 168 */   public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_BEES_005 = FeatureUtils.createKey("oak_bees_005");
/* 169 */   public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_BEES_0002 = FeatureUtils.createKey("birch_bees_0002");
/* 170 */   public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_BEES_002 = FeatureUtils.createKey("birch_bees_002");
/* 171 */   public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_BEES_005 = FeatureUtils.createKey("birch_bees_005");
/* 172 */   public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK_BEES_0002 = FeatureUtils.createKey("fancy_oak_bees_0002");
/* 173 */   public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK_BEES_002 = FeatureUtils.createKey("fancy_oak_bees_002");
/* 174 */   public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK_BEES_005 = FeatureUtils.createKey("fancy_oak_bees_005");
/* 175 */   public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_OAK_BEES = FeatureUtils.createKey("fancy_oak_bees");
/* 176 */   public static final ResourceKey<ConfiguredFeature<?, ?>> CHERRY_BEES_005 = FeatureUtils.createKey("cherry_bees_005");
/*     */   
/*     */   public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> $$0) {
/* 179 */     HolderGetter<Block> $$1 = $$0.lookup(Registries.BLOCK);
/*     */ 
/*     */     
/* 182 */     BlockPredicate $$2 = BlockPredicate.matchesBlocks(new Block[] { Blocks.OAK_SAPLING, Blocks.SPRUCE_SAPLING, Blocks.BIRCH_SAPLING, Blocks.JUNGLE_SAPLING, Blocks.ACACIA_SAPLING, Blocks.CHERRY_SAPLING, Blocks.DARK_OAK_SAPLING, Blocks.MANGROVE_PROPAGULE, Blocks.DANDELION, Blocks.TORCHFLOWER, Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP, Blocks.OXEYE_DAISY, Blocks.CORNFLOWER, Blocks.WITHER_ROSE, Blocks.LILY_OF_THE_VALLEY, Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, Blocks.WHEAT, Blocks.SUGAR_CANE, Blocks.ATTACHED_PUMPKIN_STEM, Blocks.ATTACHED_MELON_STEM, Blocks.PUMPKIN_STEM, Blocks.MELON_STEM, Blocks.LILY_PAD, Blocks.NETHER_WART, Blocks.COCOA, Blocks.CARROTS, Blocks.POTATOES, Blocks.CHORUS_PLANT, Blocks.CHORUS_FLOWER, Blocks.TORCHFLOWER_CROP, Blocks.PITCHER_CROP, Blocks.BEETROOTS, Blocks.SWEET_BERRY_BUSH, Blocks.WARPED_FUNGUS, Blocks.CRIMSON_FUNGUS, Blocks.WEEPING_VINES, Blocks.WEEPING_VINES_PLANT, Blocks.TWISTING_VINES, Blocks.TWISTING_VINES_PLANT, Blocks.CAVE_VINES, Blocks.CAVE_VINES_PLANT, Blocks.SPORE_BLOSSOM, Blocks.AZALEA, Blocks.FLOWERING_AZALEA, Blocks.MOSS_CARPET, Blocks.PINK_PETALS, Blocks.BIG_DRIPLEAF, Blocks.BIG_DRIPLEAF_STEM, Blocks.SMALL_DRIPLEAF });
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
/* 242 */     FeatureUtils.register($$0, CRIMSON_FUNGUS, Feature.HUGE_FUNGUS, new HugeFungusConfiguration(Blocks.CRIMSON_NYLIUM
/* 243 */           .defaultBlockState(), Blocks.CRIMSON_STEM
/* 244 */           .defaultBlockState(), Blocks.NETHER_WART_BLOCK
/* 245 */           .defaultBlockState(), Blocks.SHROOMLIGHT
/* 246 */           .defaultBlockState(), $$2, false));
/*     */ 
/*     */ 
/*     */     
/* 250 */     FeatureUtils.register($$0, CRIMSON_FUNGUS_PLANTED, Feature.HUGE_FUNGUS, new HugeFungusConfiguration(Blocks.CRIMSON_NYLIUM
/* 251 */           .defaultBlockState(), Blocks.CRIMSON_STEM
/* 252 */           .defaultBlockState(), Blocks.NETHER_WART_BLOCK
/* 253 */           .defaultBlockState(), Blocks.SHROOMLIGHT
/* 254 */           .defaultBlockState(), $$2, true));
/*     */ 
/*     */ 
/*     */     
/* 258 */     FeatureUtils.register($$0, WARPED_FUNGUS, Feature.HUGE_FUNGUS, new HugeFungusConfiguration(Blocks.WARPED_NYLIUM
/* 259 */           .defaultBlockState(), Blocks.WARPED_STEM
/* 260 */           .defaultBlockState(), Blocks.WARPED_WART_BLOCK
/* 261 */           .defaultBlockState(), Blocks.SHROOMLIGHT
/* 262 */           .defaultBlockState(), $$2, false));
/*     */ 
/*     */ 
/*     */     
/* 266 */     FeatureUtils.register($$0, WARPED_FUNGUS_PLANTED, Feature.HUGE_FUNGUS, new HugeFungusConfiguration(Blocks.WARPED_NYLIUM
/* 267 */           .defaultBlockState(), Blocks.WARPED_STEM
/* 268 */           .defaultBlockState(), Blocks.WARPED_WART_BLOCK
/* 269 */           .defaultBlockState(), Blocks.SHROOMLIGHT
/* 270 */           .defaultBlockState(), $$2, true));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     FeatureUtils.register($$0, HUGE_BROWN_MUSHROOM, Feature.HUGE_BROWN_MUSHROOM, new HugeMushroomFeatureConfiguration(
/* 278 */           (BlockStateProvider)BlockStateProvider.simple((BlockState)((BlockState)Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState().setValue((Property)HugeMushroomBlock.UP, Boolean.valueOf(true))).setValue((Property)HugeMushroomBlock.DOWN, Boolean.valueOf(false))), 
/* 279 */           (BlockStateProvider)BlockStateProvider.simple((BlockState)((BlockState)Blocks.MUSHROOM_STEM.defaultBlockState().setValue((Property)HugeMushroomBlock.UP, Boolean.valueOf(false))).setValue((Property)HugeMushroomBlock.DOWN, Boolean.valueOf(false))), 3));
/*     */ 
/*     */     
/* 282 */     FeatureUtils.register($$0, HUGE_RED_MUSHROOM, Feature.HUGE_RED_MUSHROOM, new HugeMushroomFeatureConfiguration(
/* 283 */           (BlockStateProvider)BlockStateProvider.simple((BlockState)Blocks.RED_MUSHROOM_BLOCK.defaultBlockState().setValue((Property)HugeMushroomBlock.DOWN, Boolean.valueOf(false))), 
/* 284 */           (BlockStateProvider)BlockStateProvider.simple((BlockState)((BlockState)Blocks.MUSHROOM_STEM.defaultBlockState().setValue((Property)HugeMushroomBlock.UP, Boolean.valueOf(false))).setValue((Property)HugeMushroomBlock.DOWN, Boolean.valueOf(false))), 2));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 290 */     BeehiveDecorator $$3 = new BeehiveDecorator(0.002F);
/* 291 */     BeehiveDecorator $$4 = new BeehiveDecorator(0.01F);
/* 292 */     BeehiveDecorator $$5 = new BeehiveDecorator(0.02F);
/* 293 */     BeehiveDecorator $$6 = new BeehiveDecorator(0.05F);
/* 294 */     BeehiveDecorator $$7 = new BeehiveDecorator(1.0F);
/*     */ 
/*     */ 
/*     */     
/* 298 */     FeatureUtils.register($$0, OAK, Feature.TREE, 
/* 299 */         createOak()
/* 300 */         .build());
/*     */     
/* 302 */     FeatureUtils.register($$0, DARK_OAK, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(
/* 303 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.DARK_OAK_LOG), (TrunkPlacer)new DarkOakTrunkPlacer(6, 2, 1), 
/*     */           
/* 305 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES), (FoliagePlacer)new DarkOakFoliagePlacer(
/* 306 */             (IntProvider)ConstantInt.of(0), (IntProvider)ConstantInt.of(0)), (FeatureSize)new ThreeLayersFeatureSize(1, 1, 0, 1, 2, 
/* 307 */             OptionalInt.empty())))
/*     */         
/* 309 */         .ignoreVines()
/* 310 */         .build());
/* 311 */     FeatureUtils.register($$0, BIRCH, Feature.TREE, 
/* 312 */         createBirch()
/* 313 */         .build());
/*     */ 
/*     */     
/* 316 */     FeatureUtils.register($$0, ACACIA, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(
/* 317 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.ACACIA_LOG), (TrunkPlacer)new ForkingTrunkPlacer(5, 2, 2), 
/*     */           
/* 319 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.ACACIA_LEAVES), (FoliagePlacer)new AcaciaFoliagePlacer(
/* 320 */             (IntProvider)ConstantInt.of(2), (IntProvider)ConstantInt.of(0)), (FeatureSize)new TwoLayersFeatureSize(1, 0, 2)))
/*     */ 
/*     */         
/* 323 */         .ignoreVines()
/* 324 */         .build());
/*     */     
/* 326 */     FeatureUtils.register($$0, CHERRY, Feature.TREE, cherry()
/* 327 */         .build());
/*     */     
/* 329 */     FeatureUtils.register($$0, CHERRY_BEES_005, Feature.TREE, cherry()
/* 330 */         .decorators(List.of($$6))
/* 331 */         .build());
/*     */     
/* 333 */     FeatureUtils.register($$0, SPRUCE, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(
/* 334 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.SPRUCE_LOG), (TrunkPlacer)new StraightTrunkPlacer(5, 2, 1), 
/*     */           
/* 336 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), (FoliagePlacer)new SpruceFoliagePlacer(
/* 337 */             (IntProvider)UniformInt.of(2, 3), (IntProvider)UniformInt.of(0, 2), (IntProvider)UniformInt.of(1, 2)), (FeatureSize)new TwoLayersFeatureSize(2, 0, 2)))
/*     */ 
/*     */         
/* 340 */         .ignoreVines()
/* 341 */         .build());
/*     */     
/* 343 */     FeatureUtils.register($$0, PINE, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(
/* 344 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.SPRUCE_LOG), (TrunkPlacer)new StraightTrunkPlacer(6, 4, 0), 
/*     */           
/* 346 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), (FoliagePlacer)new PineFoliagePlacer(
/* 347 */             (IntProvider)ConstantInt.of(1), (IntProvider)ConstantInt.of(1), (IntProvider)UniformInt.of(3, 4)), (FeatureSize)new TwoLayersFeatureSize(2, 0, 2)))
/*     */ 
/*     */         
/* 350 */         .ignoreVines()
/* 351 */         .build());
/*     */     
/* 353 */     FeatureUtils.register($$0, JUNGLE_TREE, Feature.TREE, 
/* 354 */         createJungleTree()
/* 355 */         .decorators((List)ImmutableList.of(new CocoaDecorator(0.2F), TrunkVineDecorator.INSTANCE, new LeaveVineDecorator(0.25F)))
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 360 */         .ignoreVines()
/* 361 */         .build());
/*     */     
/* 363 */     FeatureUtils.register($$0, FANCY_OAK, Feature.TREE, 
/* 364 */         createFancyOak()
/* 365 */         .build());
/*     */ 
/*     */     
/* 368 */     FeatureUtils.register($$0, JUNGLE_TREE_NO_VINE, Feature.TREE, 
/* 369 */         createJungleTree()
/* 370 */         .ignoreVines()
/* 371 */         .build());
/*     */ 
/*     */     
/* 374 */     FeatureUtils.register($$0, MEGA_JUNGLE_TREE, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(
/* 375 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.JUNGLE_LOG), (TrunkPlacer)new MegaJungleTrunkPlacer(10, 2, 19), 
/*     */           
/* 377 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.JUNGLE_LEAVES), (FoliagePlacer)new MegaJungleFoliagePlacer(
/* 378 */             (IntProvider)ConstantInt.of(2), (IntProvider)ConstantInt.of(0), 2), (FeatureSize)new TwoLayersFeatureSize(1, 1, 2)))
/*     */ 
/*     */         
/* 381 */         .decorators((List)ImmutableList.of(TrunkVineDecorator.INSTANCE, new LeaveVineDecorator(0.25F)))
/*     */ 
/*     */ 
/*     */         
/* 385 */         .build());
/*     */     
/* 387 */     FeatureUtils.register($$0, MEGA_SPRUCE, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(
/* 388 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.SPRUCE_LOG), (TrunkPlacer)new GiantTrunkPlacer(13, 2, 14), 
/*     */           
/* 390 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), (FoliagePlacer)new MegaPineFoliagePlacer(
/* 391 */             (IntProvider)ConstantInt.of(0), (IntProvider)ConstantInt.of(0), (IntProvider)UniformInt.of(13, 17)), (FeatureSize)new TwoLayersFeatureSize(1, 1, 2)))
/*     */ 
/*     */         
/* 394 */         .decorators((List)ImmutableList.of(new AlterGroundDecorator(
/* 395 */               (BlockStateProvider)BlockStateProvider.simple(Blocks.PODZOL))))
/*     */         
/* 397 */         .build());
/*     */     
/* 399 */     FeatureUtils.register($$0, MEGA_PINE, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(
/* 400 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.SPRUCE_LOG), (TrunkPlacer)new GiantTrunkPlacer(13, 2, 14), 
/*     */           
/* 402 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.SPRUCE_LEAVES), (FoliagePlacer)new MegaPineFoliagePlacer(
/* 403 */             (IntProvider)ConstantInt.of(0), (IntProvider)ConstantInt.of(0), (IntProvider)UniformInt.of(3, 7)), (FeatureSize)new TwoLayersFeatureSize(1, 1, 2)))
/*     */ 
/*     */         
/* 406 */         .decorators((List)ImmutableList.of(new AlterGroundDecorator(
/* 407 */               (BlockStateProvider)BlockStateProvider.simple(Blocks.PODZOL))))
/*     */         
/* 409 */         .build());
/*     */     
/* 411 */     FeatureUtils.register($$0, SUPER_BIRCH_BEES_0002, Feature.TREE, 
/* 412 */         createSuperBirch()
/* 413 */         .decorators((List)ImmutableList.of($$3))
/* 414 */         .build());
/*     */     
/* 416 */     FeatureUtils.register($$0, SUPER_BIRCH_BEES, Feature.TREE, 
/* 417 */         createSuperBirch()
/* 418 */         .decorators((List)ImmutableList.of($$7))
/* 419 */         .build());
/*     */ 
/*     */     
/* 422 */     FeatureUtils.register($$0, SWAMP_OAK, Feature.TREE, 
/* 423 */         createStraightBlobTree(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 5, 3, 0, 3)
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 431 */         .decorators((List)ImmutableList.of(new LeaveVineDecorator(0.25F)))
/* 432 */         .build());
/*     */     
/* 434 */     FeatureUtils.register($$0, JUNGLE_BUSH, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(
/* 435 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.JUNGLE_LOG), (TrunkPlacer)new StraightTrunkPlacer(1, 0, 0), 
/*     */           
/* 437 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.OAK_LEAVES), (FoliagePlacer)new BushFoliagePlacer(
/* 438 */             (IntProvider)ConstantInt.of(2), (IntProvider)ConstantInt.of(1), 2), (FeatureSize)new TwoLayersFeatureSize(0, 0, 0)))
/*     */ 
/*     */         
/* 441 */         .build());
/*     */     
/* 443 */     FeatureUtils.register($$0, AZALEA_TREE, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(
/* 444 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.OAK_LOG), (TrunkPlacer)new BendingTrunkPlacer(4, 2, 0, 3, 
/* 445 */             (IntProvider)UniformInt.of(1, 2)), (BlockStateProvider)new WeightedStateProvider(
/* 446 */             SimpleWeightedRandomList.builder().add(Blocks.AZALEA_LEAVES.defaultBlockState(), 3).add(Blocks.FLOWERING_AZALEA_LEAVES.defaultBlockState(), 1)), (FoliagePlacer)new RandomSpreadFoliagePlacer(
/* 447 */             (IntProvider)ConstantInt.of(3), (IntProvider)ConstantInt.of(0), (IntProvider)ConstantInt.of(2), 50), (FeatureSize)new TwoLayersFeatureSize(1, 0, 1)))
/*     */ 
/*     */         
/* 450 */         .dirt((BlockStateProvider)BlockStateProvider.simple(Blocks.ROOTED_DIRT))
/* 451 */         .forceDirt()
/* 452 */         .build());
/*     */ 
/*     */     
/* 455 */     FeatureUtils.register($$0, MANGROVE, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(
/* 456 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.MANGROVE_LOG), (TrunkPlacer)new UpwardsBranchingTrunkPlacer(2, 1, 4, 
/* 457 */             (IntProvider)UniformInt.of(1, 4), 0.5F, (IntProvider)UniformInt.of(0, 1), (HolderSet)$$1.getOrThrow(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)), 
/* 458 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.MANGROVE_LEAVES), (FoliagePlacer)new RandomSpreadFoliagePlacer(
/* 459 */             (IntProvider)ConstantInt.of(3), (IntProvider)ConstantInt.of(0), (IntProvider)ConstantInt.of(2), 70), 
/* 460 */           Optional.of(new MangroveRootPlacer(
/* 461 */               (IntProvider)UniformInt.of(1, 3), 
/* 462 */               (BlockStateProvider)BlockStateProvider.simple(Blocks.MANGROVE_ROOTS), 
/* 463 */               Optional.of(new AboveRootPlacement(
/* 464 */                   (BlockStateProvider)BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.5F)), new MangroveRootPlacement((HolderSet)$$1
/*     */ 
/*     */ 
/*     */                 
/* 468 */                 .getOrThrow(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH), 
/* 469 */                 (HolderSet)HolderSet.direct(Block::builtInRegistryHolder, (Object[])new Block[] { Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS
/* 470 */                   }), (BlockStateProvider)BlockStateProvider.simple(Blocks.MUDDY_MANGROVE_ROOTS), 8, 15, 0.2F))), (FeatureSize)new TwoLayersFeatureSize(2, 0, 2)))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 478 */         .decorators(List.of(new LeaveVineDecorator(0.125F), new AttachedToLeavesDecorator(0.14F, 1, 0, (BlockStateProvider)new RandomizedIntStateProvider(
/*     */ 
/*     */ 
/*     */                 
/* 482 */                 (BlockStateProvider)BlockStateProvider.simple((BlockState)Blocks.MANGROVE_PROPAGULE.defaultBlockState().setValue((Property)MangrovePropaguleBlock.HANGING, Boolean.valueOf(true))), MangrovePropaguleBlock.AGE, 
/*     */                 
/* 484 */                 (IntProvider)UniformInt.of(0, 4)), 2, 
/* 485 */               List.of(Direction.DOWN)), $$4))
/*     */ 
/*     */         
/* 488 */         .ignoreVines()
/* 489 */         .build());
/*     */ 
/*     */     
/* 492 */     FeatureUtils.register($$0, TALL_MANGROVE, Feature.TREE, (new TreeConfiguration.TreeConfigurationBuilder(
/* 493 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.MANGROVE_LOG), (TrunkPlacer)new UpwardsBranchingTrunkPlacer(4, 1, 9, 
/* 494 */             (IntProvider)UniformInt.of(1, 6), 0.5F, (IntProvider)UniformInt.of(0, 1), (HolderSet)$$1.getOrThrow(BlockTags.MANGROVE_LOGS_CAN_GROW_THROUGH)), 
/* 495 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.MANGROVE_LEAVES), (FoliagePlacer)new RandomSpreadFoliagePlacer(
/* 496 */             (IntProvider)ConstantInt.of(3), (IntProvider)ConstantInt.of(0), (IntProvider)ConstantInt.of(2), 70), 
/* 497 */           Optional.of(new MangroveRootPlacer(
/* 498 */               (IntProvider)UniformInt.of(3, 7), 
/* 499 */               (BlockStateProvider)BlockStateProvider.simple(Blocks.MANGROVE_ROOTS), 
/* 500 */               Optional.of(new AboveRootPlacement(
/* 501 */                   (BlockStateProvider)BlockStateProvider.simple(Blocks.MOSS_CARPET), 0.5F)), new MangroveRootPlacement((HolderSet)$$1
/*     */ 
/*     */ 
/*     */                 
/* 505 */                 .getOrThrow(BlockTags.MANGROVE_ROOTS_CAN_GROW_THROUGH), 
/* 506 */                 (HolderSet)HolderSet.direct(Block::builtInRegistryHolder, (Object[])new Block[] { Blocks.MUD, Blocks.MUDDY_MANGROVE_ROOTS
/* 507 */                   }), (BlockStateProvider)BlockStateProvider.simple(Blocks.MUDDY_MANGROVE_ROOTS), 8, 15, 0.2F))), (FeatureSize)new TwoLayersFeatureSize(3, 0, 2)))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 515 */         .decorators(List.of(new LeaveVineDecorator(0.125F), new AttachedToLeavesDecorator(0.14F, 1, 0, (BlockStateProvider)new RandomizedIntStateProvider(
/*     */ 
/*     */ 
/*     */                 
/* 519 */                 (BlockStateProvider)BlockStateProvider.simple((BlockState)Blocks.MANGROVE_PROPAGULE.defaultBlockState().setValue((Property)MangrovePropaguleBlock.HANGING, Boolean.valueOf(true))), MangrovePropaguleBlock.AGE, 
/*     */                 
/* 521 */                 (IntProvider)UniformInt.of(0, 4)), 2, 
/* 522 */               List.of(Direction.DOWN)), $$4))
/*     */ 
/*     */         
/* 525 */         .ignoreVines()
/* 526 */         .build());
/*     */ 
/*     */     
/* 529 */     FeatureUtils.register($$0, OAK_BEES_0002, Feature.TREE, 
/* 530 */         createOak()
/* 531 */         .decorators(List.of($$3))
/* 532 */         .build());
/*     */     
/* 534 */     FeatureUtils.register($$0, OAK_BEES_002, Feature.TREE, 
/* 535 */         createOak()
/* 536 */         .decorators(List.of($$5))
/* 537 */         .build());
/*     */     
/* 539 */     FeatureUtils.register($$0, OAK_BEES_005, Feature.TREE, 
/* 540 */         createOak()
/* 541 */         .decorators(List.of($$6))
/* 542 */         .build());
/*     */     
/* 544 */     FeatureUtils.register($$0, BIRCH_BEES_0002, Feature.TREE, 
/* 545 */         createBirch()
/* 546 */         .decorators(List.of($$3))
/* 547 */         .build());
/*     */     
/* 549 */     FeatureUtils.register($$0, BIRCH_BEES_002, Feature.TREE, 
/* 550 */         createBirch()
/* 551 */         .decorators(List.of($$5))
/* 552 */         .build());
/*     */     
/* 554 */     FeatureUtils.register($$0, BIRCH_BEES_005, Feature.TREE, 
/* 555 */         createBirch()
/* 556 */         .decorators(List.of($$6))
/* 557 */         .build());
/*     */     
/* 559 */     FeatureUtils.register($$0, FANCY_OAK_BEES_0002, Feature.TREE, 
/* 560 */         createFancyOak()
/* 561 */         .decorators(List.of($$3))
/* 562 */         .build());
/*     */     
/* 564 */     FeatureUtils.register($$0, FANCY_OAK_BEES_002, Feature.TREE, 
/* 565 */         createFancyOak()
/* 566 */         .decorators(List.of($$5))
/* 567 */         .build());
/*     */     
/* 569 */     FeatureUtils.register($$0, FANCY_OAK_BEES_005, Feature.TREE, 
/* 570 */         createFancyOak()
/* 571 */         .decorators(List.of($$6))
/* 572 */         .build());
/*     */     
/* 574 */     FeatureUtils.register($$0, FANCY_OAK_BEES, Feature.TREE, 
/* 575 */         createFancyOak()
/* 576 */         .decorators(List.of($$7))
/* 577 */         .build());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\features\TreeFeatures.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */