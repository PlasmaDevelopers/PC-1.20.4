/*     */ package net.minecraft.data.worldgen.placement;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ import net.minecraft.data.worldgen.features.TreeFeatures;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.level.levelgen.placement.BiomeFilter;
/*     */ import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
/*     */ import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacementModifier;
/*     */ 
/*     */ public class TreePlacements {
/*  23 */   public static final ResourceKey<PlacedFeature> CRIMSON_FUNGI = PlacementUtils.createKey("crimson_fungi");
/*  24 */   public static final ResourceKey<PlacedFeature> WARPED_FUNGI = PlacementUtils.createKey("warped_fungi");
/*     */   
/*  26 */   public static final ResourceKey<PlacedFeature> OAK_CHECKED = PlacementUtils.createKey("oak_checked");
/*  27 */   public static final ResourceKey<PlacedFeature> DARK_OAK_CHECKED = PlacementUtils.createKey("dark_oak_checked");
/*  28 */   public static final ResourceKey<PlacedFeature> BIRCH_CHECKED = PlacementUtils.createKey("birch_checked");
/*  29 */   public static final ResourceKey<PlacedFeature> ACACIA_CHECKED = PlacementUtils.createKey("acacia_checked");
/*  30 */   public static final ResourceKey<PlacedFeature> SPRUCE_CHECKED = PlacementUtils.createKey("spruce_checked");
/*  31 */   public static final ResourceKey<PlacedFeature> MANGROVE_CHECKED = PlacementUtils.createKey("mangrove_checked");
/*  32 */   public static final ResourceKey<PlacedFeature> CHERRY_CHECKED = PlacementUtils.createKey("cherry_checked");
/*     */   
/*  34 */   public static final ResourceKey<PlacedFeature> PINE_ON_SNOW = PlacementUtils.createKey("pine_on_snow");
/*  35 */   public static final ResourceKey<PlacedFeature> SPRUCE_ON_SNOW = PlacementUtils.createKey("spruce_on_snow");
/*     */   
/*  37 */   public static final ResourceKey<PlacedFeature> PINE_CHECKED = PlacementUtils.createKey("pine_checked");
/*  38 */   public static final ResourceKey<PlacedFeature> JUNGLE_TREE_CHECKED = PlacementUtils.createKey("jungle_tree");
/*  39 */   public static final ResourceKey<PlacedFeature> FANCY_OAK_CHECKED = PlacementUtils.createKey("fancy_oak_checked");
/*  40 */   public static final ResourceKey<PlacedFeature> MEGA_JUNGLE_TREE_CHECKED = PlacementUtils.createKey("mega_jungle_tree_checked");
/*  41 */   public static final ResourceKey<PlacedFeature> MEGA_SPRUCE_CHECKED = PlacementUtils.createKey("mega_spruce_checked");
/*  42 */   public static final ResourceKey<PlacedFeature> MEGA_PINE_CHECKED = PlacementUtils.createKey("mega_pine_checked");
/*  43 */   public static final ResourceKey<PlacedFeature> TALL_MANGROVE_CHECKED = PlacementUtils.createKey("tall_mangrove_checked");
/*     */   
/*  45 */   public static final ResourceKey<PlacedFeature> JUNGLE_BUSH = PlacementUtils.createKey("jungle_bush");
/*     */   
/*  47 */   public static final ResourceKey<PlacedFeature> SUPER_BIRCH_BEES_0002 = PlacementUtils.createKey("super_birch_bees_0002");
/*  48 */   public static final ResourceKey<PlacedFeature> SUPER_BIRCH_BEES = PlacementUtils.createKey("super_birch_bees");
/*     */   
/*  50 */   public static final ResourceKey<PlacedFeature> OAK_BEES_0002 = PlacementUtils.createKey("oak_bees_0002");
/*  51 */   public static final ResourceKey<PlacedFeature> OAK_BEES_002 = PlacementUtils.createKey("oak_bees_002");
/*  52 */   public static final ResourceKey<PlacedFeature> BIRCH_BEES_0002_PLACED = PlacementUtils.createKey("birch_bees_0002");
/*  53 */   public static final ResourceKey<PlacedFeature> BIRCH_BEES_002 = PlacementUtils.createKey("birch_bees_002");
/*  54 */   public static final ResourceKey<PlacedFeature> FANCY_OAK_BEES_0002 = PlacementUtils.createKey("fancy_oak_bees_0002");
/*  55 */   public static final ResourceKey<PlacedFeature> FANCY_OAK_BEES_002 = PlacementUtils.createKey("fancy_oak_bees_002");
/*  56 */   public static final ResourceKey<PlacedFeature> FANCY_OAK_BEES = PlacementUtils.createKey("fancy_oak_bees");
/*  57 */   public static final ResourceKey<PlacedFeature> CHERRY_BEES_005 = PlacementUtils.createKey("cherry_bees_005");
/*     */   
/*     */   public static void bootstrap(BootstapContext<PlacedFeature> $$0) {
/*  60 */     HolderGetter<ConfiguredFeature<?, ?>> $$1 = $$0.lookup(Registries.CONFIGURED_FEATURE);
/*  61 */     Holder.Reference reference1 = $$1.getOrThrow(TreeFeatures.CRIMSON_FUNGUS);
/*  62 */     Holder.Reference reference2 = $$1.getOrThrow(TreeFeatures.WARPED_FUNGUS);
/*  63 */     Holder.Reference reference3 = $$1.getOrThrow(TreeFeatures.OAK);
/*  64 */     Holder.Reference reference4 = $$1.getOrThrow(TreeFeatures.DARK_OAK);
/*  65 */     Holder.Reference reference5 = $$1.getOrThrow(TreeFeatures.BIRCH);
/*  66 */     Holder.Reference reference6 = $$1.getOrThrow(TreeFeatures.ACACIA);
/*  67 */     Holder.Reference reference7 = $$1.getOrThrow(TreeFeatures.SPRUCE);
/*  68 */     Holder.Reference reference8 = $$1.getOrThrow(TreeFeatures.MANGROVE);
/*  69 */     Holder.Reference reference9 = $$1.getOrThrow(TreeFeatures.CHERRY);
/*  70 */     Holder.Reference reference10 = $$1.getOrThrow(TreeFeatures.PINE);
/*  71 */     Holder.Reference reference11 = $$1.getOrThrow(TreeFeatures.JUNGLE_TREE);
/*  72 */     Holder.Reference reference12 = $$1.getOrThrow(TreeFeatures.FANCY_OAK);
/*  73 */     Holder.Reference reference13 = $$1.getOrThrow(TreeFeatures.MEGA_JUNGLE_TREE);
/*  74 */     Holder.Reference reference14 = $$1.getOrThrow(TreeFeatures.MEGA_SPRUCE);
/*  75 */     Holder.Reference reference15 = $$1.getOrThrow(TreeFeatures.MEGA_PINE);
/*  76 */     Holder.Reference reference16 = $$1.getOrThrow(TreeFeatures.TALL_MANGROVE);
/*  77 */     Holder.Reference reference17 = $$1.getOrThrow(TreeFeatures.JUNGLE_BUSH);
/*  78 */     Holder.Reference reference18 = $$1.getOrThrow(TreeFeatures.SUPER_BIRCH_BEES_0002);
/*  79 */     Holder.Reference reference19 = $$1.getOrThrow(TreeFeatures.SUPER_BIRCH_BEES);
/*  80 */     Holder.Reference reference20 = $$1.getOrThrow(TreeFeatures.OAK_BEES_0002);
/*  81 */     Holder.Reference reference21 = $$1.getOrThrow(TreeFeatures.OAK_BEES_002);
/*  82 */     Holder.Reference reference22 = $$1.getOrThrow(TreeFeatures.BIRCH_BEES_0002);
/*  83 */     Holder.Reference reference23 = $$1.getOrThrow(TreeFeatures.BIRCH_BEES_002);
/*  84 */     Holder.Reference reference24 = $$1.getOrThrow(TreeFeatures.FANCY_OAK_BEES_0002);
/*  85 */     Holder.Reference reference25 = $$1.getOrThrow(TreeFeatures.FANCY_OAK_BEES_002);
/*  86 */     Holder.Reference reference26 = $$1.getOrThrow(TreeFeatures.FANCY_OAK_BEES);
/*  87 */     Holder.Reference reference27 = $$1.getOrThrow(TreeFeatures.CHERRY_BEES_005);
/*     */     
/*  89 */     PlacementUtils.register($$0, CRIMSON_FUNGI, (Holder<ConfiguredFeature<?, ?>>)reference1, new PlacementModifier[] {
/*  90 */           (PlacementModifier)CountOnEveryLayerPlacement.of(8), 
/*  91 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*  93 */     PlacementUtils.register($$0, WARPED_FUNGI, (Holder<ConfiguredFeature<?, ?>>)reference2, new PlacementModifier[] {
/*  94 */           (PlacementModifier)CountOnEveryLayerPlacement.of(8), 
/*  95 */           (PlacementModifier)BiomeFilter.biome()
/*     */         });
/*     */     
/*  98 */     PlacementUtils.register($$0, OAK_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference3, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING) });
/*  99 */     PlacementUtils.register($$0, DARK_OAK_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference4, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.DARK_OAK_SAPLING) });
/* 100 */     PlacementUtils.register($$0, BIRCH_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference5, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING) });
/* 101 */     PlacementUtils.register($$0, ACACIA_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference6, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.ACACIA_SAPLING) });
/* 102 */     PlacementUtils.register($$0, SPRUCE_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference7, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING) });
/* 103 */     PlacementUtils.register($$0, MANGROVE_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference8, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE) });
/* 104 */     PlacementUtils.register($$0, CHERRY_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference9, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.CHERRY_SAPLING) });
/*     */     
/* 106 */     BlockPredicate $$29 = BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), new Block[] { Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW });
/* 107 */     List<PlacementModifier> $$30 = (List)List.of(
/* 108 */         EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(new Block[] { Blocks.POWDER_SNOW }, )), 8), 
/* 109 */         BlockPredicateFilter.forPredicate($$29));
/*     */     
/* 111 */     PlacementUtils.register($$0, PINE_ON_SNOW, (Holder<ConfiguredFeature<?, ?>>)reference10, $$30);
/* 112 */     PlacementUtils.register($$0, SPRUCE_ON_SNOW, (Holder<ConfiguredFeature<?, ?>>)reference7, $$30);
/*     */     
/* 114 */     PlacementUtils.register($$0, PINE_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference10, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING) });
/* 115 */     PlacementUtils.register($$0, JUNGLE_TREE_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference11, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.JUNGLE_SAPLING) });
/* 116 */     PlacementUtils.register($$0, FANCY_OAK_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference12, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING) });
/* 117 */     PlacementUtils.register($$0, MEGA_JUNGLE_TREE_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference13, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.JUNGLE_SAPLING) });
/* 118 */     PlacementUtils.register($$0, MEGA_SPRUCE_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference14, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING) });
/* 119 */     PlacementUtils.register($$0, MEGA_PINE_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference15, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING) });
/* 120 */     PlacementUtils.register($$0, TALL_MANGROVE_CHECKED, (Holder<ConfiguredFeature<?, ?>>)reference16, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE) });
/*     */     
/* 122 */     PlacementUtils.register($$0, JUNGLE_BUSH, (Holder<ConfiguredFeature<?, ?>>)reference17, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING) });
/*     */     
/* 124 */     PlacementUtils.register($$0, SUPER_BIRCH_BEES_0002, (Holder<ConfiguredFeature<?, ?>>)reference18, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING) });
/* 125 */     PlacementUtils.register($$0, SUPER_BIRCH_BEES, (Holder<ConfiguredFeature<?, ?>>)reference19, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING) });
/*     */     
/* 127 */     PlacementUtils.register($$0, OAK_BEES_0002, (Holder<ConfiguredFeature<?, ?>>)reference20, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING) });
/* 128 */     PlacementUtils.register($$0, OAK_BEES_002, (Holder<ConfiguredFeature<?, ?>>)reference21, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING) });
/* 129 */     PlacementUtils.register($$0, BIRCH_BEES_0002_PLACED, (Holder<ConfiguredFeature<?, ?>>)reference22, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING) });
/* 130 */     PlacementUtils.register($$0, BIRCH_BEES_002, (Holder<ConfiguredFeature<?, ?>>)reference23, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING) });
/* 131 */     PlacementUtils.register($$0, FANCY_OAK_BEES_0002, (Holder<ConfiguredFeature<?, ?>>)reference24, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING) });
/* 132 */     PlacementUtils.register($$0, FANCY_OAK_BEES_002, (Holder<ConfiguredFeature<?, ?>>)reference25, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING) });
/* 133 */     PlacementUtils.register($$0, FANCY_OAK_BEES, (Holder<ConfiguredFeature<?, ?>>)reference26, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING) });
/* 134 */     PlacementUtils.register($$0, CHERRY_BEES_005, (Holder<ConfiguredFeature<?, ?>>)reference27, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.CHERRY_SAPLING) });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\placement\TreePlacements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */