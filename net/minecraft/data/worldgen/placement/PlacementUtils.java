/*     */ package net.minecraft.data.worldgen.placement;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.random.SimpleWeightedRandomList;
/*     */ import net.minecraft.util.valueproviders.ConstantInt;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.util.valueproviders.WeightedListInt;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*     */ import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.level.levelgen.feature.Feature;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
/*     */ import net.minecraft.world.level.levelgen.placement.CountPlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacementFilter;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacementModifier;
/*     */ 
/*     */ public class PlacementUtils
/*     */ {
/*     */   public static void bootstrap(BootstapContext<PlacedFeature> $$0) {
/*  33 */     AquaticPlacements.bootstrap($$0);
/*  34 */     CavePlacements.bootstrap($$0);
/*  35 */     EndPlacements.bootstrap($$0);
/*  36 */     MiscOverworldPlacements.bootstrap($$0);
/*  37 */     NetherPlacements.bootstrap($$0);
/*  38 */     OrePlacements.bootstrap($$0);
/*  39 */     TreePlacements.bootstrap($$0);
/*  40 */     VegetationPlacements.bootstrap($$0);
/*  41 */     VillagePlacements.bootstrap($$0);
/*     */   }
/*     */   
/*  44 */   public static final PlacementModifier HEIGHTMAP = (PlacementModifier)HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING);
/*  45 */   public static final PlacementModifier HEIGHTMAP_TOP_SOLID = (PlacementModifier)HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG);
/*  46 */   public static final PlacementModifier HEIGHTMAP_WORLD_SURFACE = (PlacementModifier)HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG);
/*  47 */   public static final PlacementModifier HEIGHTMAP_OCEAN_FLOOR = (PlacementModifier)HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR);
/*     */   
/*  49 */   public static final PlacementModifier FULL_RANGE = (PlacementModifier)HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top());
/*  50 */   public static final PlacementModifier RANGE_10_10 = (PlacementModifier)HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(10), VerticalAnchor.belowTop(10));
/*  51 */   public static final PlacementModifier RANGE_8_8 = (PlacementModifier)HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(8), VerticalAnchor.belowTop(8));
/*  52 */   public static final PlacementModifier RANGE_4_4 = (PlacementModifier)HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(4), VerticalAnchor.belowTop(4));
/*  53 */   public static final PlacementModifier RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT = (PlacementModifier)HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(256));
/*     */   
/*     */   public static ResourceKey<PlacedFeature> createKey(String $$0) {
/*  56 */     return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation($$0));
/*     */   }
/*     */   
/*     */   public static void register(BootstapContext<PlacedFeature> $$0, ResourceKey<PlacedFeature> $$1, Holder<ConfiguredFeature<?, ?>> $$2, List<PlacementModifier> $$3) {
/*  60 */     $$0.register($$1, new PlacedFeature($$2, List.copyOf($$3)));
/*     */   }
/*     */   
/*     */   public static void register(BootstapContext<PlacedFeature> $$0, ResourceKey<PlacedFeature> $$1, Holder<ConfiguredFeature<?, ?>> $$2, PlacementModifier... $$3) {
/*  64 */     register($$0, $$1, $$2, List.of($$3));
/*     */   }
/*     */   
/*     */   public static PlacementModifier countExtra(int $$0, float $$1, int $$2) {
/*  68 */     float $$3 = 1.0F / $$1;
/*  69 */     if (Math.abs($$3 - (int)$$3) > 1.0E-5F) {
/*  70 */       throw new IllegalStateException("Chance data cannot be represented as list weight");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  75 */     SimpleWeightedRandomList<IntProvider> $$4 = SimpleWeightedRandomList.builder().add(ConstantInt.of($$0), (int)$$3 - 1).add(ConstantInt.of($$0 + $$2), 1).build();
/*  76 */     return (PlacementModifier)CountPlacement.of((IntProvider)new WeightedListInt($$4));
/*     */   }
/*     */   
/*     */   public static PlacementFilter isEmpty() {
/*  80 */     return (PlacementFilter)BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE);
/*     */   }
/*     */   
/*     */   public static BlockPredicateFilter filteredByBlockSurvival(Block $$0) {
/*  84 */     return BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive($$0.defaultBlockState(), (Vec3i)BlockPos.ZERO));
/*     */   }
/*     */   
/*     */   public static Holder<PlacedFeature> inlinePlaced(Holder<ConfiguredFeature<?, ?>> $$0, PlacementModifier... $$1) {
/*  88 */     return Holder.direct(new PlacedFeature($$0, List.of($$1)));
/*     */   }
/*     */   
/*     */   public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<PlacedFeature> inlinePlaced(F $$0, FC $$1, PlacementModifier... $$2) {
/*  92 */     return inlinePlaced(Holder.direct(new ConfiguredFeature((Feature)$$0, (FeatureConfiguration)$$1)), $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<PlacedFeature> onlyWhenEmpty(F $$0, FC $$1) {
/*  99 */     return filtered($$0, $$1, BlockPredicate.ONLY_IN_AIR_PREDICATE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<PlacedFeature> filtered(F $$0, FC $$1, BlockPredicate $$2) {
/* 106 */     return inlinePlaced($$0, $$1, new PlacementModifier[] { (PlacementModifier)BlockPredicateFilter.forPredicate($$2) });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\placement\PlacementUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */