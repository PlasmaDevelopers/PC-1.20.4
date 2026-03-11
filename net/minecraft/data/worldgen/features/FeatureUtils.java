/*    */ package net.minecraft.data.worldgen.features;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.worldgen.BootstapContext;
/*    */ import net.minecraft.data.worldgen.placement.PlacementUtils;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
/*    */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*    */ import net.minecraft.world.level.levelgen.feature.Feature;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ 
/*    */ public class FeatureUtils
/*    */ {
/*    */   public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> $$0) {
/* 24 */     AquaticFeatures.bootstrap($$0);
/* 25 */     CaveFeatures.bootstrap($$0);
/* 26 */     EndFeatures.bootstrap($$0);
/* 27 */     MiscOverworldFeatures.bootstrap($$0);
/* 28 */     NetherFeatures.bootstrap($$0);
/* 29 */     OreFeatures.bootstrap($$0);
/* 30 */     PileFeatures.bootstrap($$0);
/* 31 */     TreeFeatures.bootstrap($$0);
/* 32 */     VegetationFeatures.bootstrap($$0);
/*    */   }
/*    */   
/*    */   private static BlockPredicate simplePatchPredicate(List<Block> $$0) {
/*    */     BlockPredicate $$2;
/* 37 */     if (!$$0.isEmpty()) {
/* 38 */       BlockPredicate $$1 = BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), $$0));
/*    */     } else {
/* 40 */       $$2 = BlockPredicate.ONLY_IN_AIR_PREDICATE;
/*    */     } 
/* 42 */     return $$2;
/*    */   }
/*    */   
/*    */   public static RandomPatchConfiguration simpleRandomPatchConfiguration(int $$0, Holder<PlacedFeature> $$1) {
/* 46 */     return new RandomPatchConfiguration($$0, 7, 3, $$1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <FC extends FeatureConfiguration, F extends Feature<FC>> RandomPatchConfiguration simplePatchConfiguration(F $$0, FC $$1, List<Block> $$2, int $$3) {
/* 55 */     return simpleRandomPatchConfiguration($$3, PlacementUtils.filtered((Feature)$$0, (FeatureConfiguration)$$1, simplePatchPredicate($$2)));
/*    */   }
/*    */   
/*    */   public static <FC extends FeatureConfiguration, F extends Feature<FC>> RandomPatchConfiguration simplePatchConfiguration(F $$0, FC $$1, List<Block> $$2) {
/* 59 */     return simplePatchConfiguration($$0, $$1, $$2, 96);
/*    */   }
/*    */   
/*    */   public static <FC extends FeatureConfiguration, F extends Feature<FC>> RandomPatchConfiguration simplePatchConfiguration(F $$0, FC $$1) {
/* 63 */     return simplePatchConfiguration($$0, $$1, List.of(), 96);
/*    */   }
/*    */   
/*    */   public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String $$0) {
/* 67 */     return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation($$0));
/*    */   }
/*    */   
/*    */   public static void register(BootstapContext<ConfiguredFeature<?, ?>> $$0, ResourceKey<ConfiguredFeature<?, ?>> $$1, Feature<NoneFeatureConfiguration> $$2) {
/* 71 */     register($$0, $$1, $$2, FeatureConfiguration.NONE);
/*    */   }
/*    */   
/*    */   public static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> $$0, ResourceKey<ConfiguredFeature<?, ?>> $$1, F $$2, FC $$3) {
/* 75 */     $$0.register($$1, new ConfiguredFeature((Feature)$$2, (FeatureConfiguration)$$3));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\features\FeatureUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */