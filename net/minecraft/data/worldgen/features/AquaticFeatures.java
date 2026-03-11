/*    */ package net.minecraft.data.worldgen.features;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.data.worldgen.BootstapContext;
/*    */ import net.minecraft.data.worldgen.placement.PlacementUtils;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*    */ import net.minecraft.world.level.levelgen.feature.Feature;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*    */ 
/*    */ public class AquaticFeatures {
/* 18 */   public static final ResourceKey<ConfiguredFeature<?, ?>> SEAGRASS_SHORT = FeatureUtils.createKey("seagrass_short");
/* 19 */   public static final ResourceKey<ConfiguredFeature<?, ?>> SEAGRASS_SLIGHTLY_LESS_SHORT = FeatureUtils.createKey("seagrass_slightly_less_short");
/* 20 */   public static final ResourceKey<ConfiguredFeature<?, ?>> SEAGRASS_MID = FeatureUtils.createKey("seagrass_mid");
/* 21 */   public static final ResourceKey<ConfiguredFeature<?, ?>> SEAGRASS_TALL = FeatureUtils.createKey("seagrass_tall");
/*    */   
/* 23 */   public static final ResourceKey<ConfiguredFeature<?, ?>> SEA_PICKLE = FeatureUtils.createKey("sea_pickle");
/*    */   
/* 25 */   public static final ResourceKey<ConfiguredFeature<?, ?>> SEAGRASS_SIMPLE = FeatureUtils.createKey("seagrass_simple");
/*    */   
/* 27 */   public static final ResourceKey<ConfiguredFeature<?, ?>> KELP = FeatureUtils.createKey("kelp");
/*    */   
/* 29 */   public static final ResourceKey<ConfiguredFeature<?, ?>> WARM_OCEAN_VEGETATION = FeatureUtils.createKey("warm_ocean_vegetation");
/*    */ 
/*    */ 
/*    */   
/*    */   public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> $$0) {
/* 34 */     FeatureUtils.register($$0, SEAGRASS_SHORT, Feature.SEAGRASS, new ProbabilityFeatureConfiguration(0.3F));
/* 35 */     FeatureUtils.register($$0, SEAGRASS_SLIGHTLY_LESS_SHORT, Feature.SEAGRASS, new ProbabilityFeatureConfiguration(0.4F));
/* 36 */     FeatureUtils.register($$0, SEAGRASS_MID, Feature.SEAGRASS, new ProbabilityFeatureConfiguration(0.6F));
/* 37 */     FeatureUtils.register($$0, SEAGRASS_TALL, Feature.SEAGRASS, new ProbabilityFeatureConfiguration(0.8F));
/*    */     
/* 39 */     FeatureUtils.register($$0, SEA_PICKLE, Feature.SEA_PICKLE, new CountConfiguration(20));
/*    */     
/* 41 */     FeatureUtils.register($$0, SEAGRASS_SIMPLE, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
/* 42 */           (BlockStateProvider)BlockStateProvider.simple(Blocks.SEAGRASS)));
/*    */ 
/*    */     
/* 45 */     FeatureUtils.register($$0, KELP, Feature.KELP);
/*    */     
/* 47 */     FeatureUtils.register($$0, WARM_OCEAN_VEGETATION, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(
/* 48 */           (HolderSet)HolderSet.direct(new Holder[] {
/* 49 */               PlacementUtils.inlinePlaced(Feature.CORAL_TREE, (FeatureConfiguration)FeatureConfiguration.NONE, new net.minecraft.world.level.levelgen.placement.PlacementModifier[0]), 
/* 50 */               PlacementUtils.inlinePlaced(Feature.CORAL_CLAW, (FeatureConfiguration)FeatureConfiguration.NONE, new net.minecraft.world.level.levelgen.placement.PlacementModifier[0]), 
/* 51 */               PlacementUtils.inlinePlaced(Feature.CORAL_MUSHROOM, (FeatureConfiguration)FeatureConfiguration.NONE, new net.minecraft.world.level.levelgen.placement.PlacementModifier[0])
/*    */             })));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\features\AquaticFeatures.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */