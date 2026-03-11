/*    */ package net.minecraft.data.worldgen.placement;
/*    */ 
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.worldgen.BootstapContext;
/*    */ import net.minecraft.data.worldgen.features.PileFeatures;
/*    */ import net.minecraft.data.worldgen.features.TreeFeatures;
/*    */ import net.minecraft.data.worldgen.features.VegetationFeatures;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacementModifier;
/*    */ 
/*    */ 
/*    */ public class VillagePlacements
/*    */ {
/* 19 */   public static final ResourceKey<PlacedFeature> PILE_HAY_VILLAGE = PlacementUtils.createKey("pile_hay");
/* 20 */   public static final ResourceKey<PlacedFeature> PILE_MELON_VILLAGE = PlacementUtils.createKey("pile_melon");
/* 21 */   public static final ResourceKey<PlacedFeature> PILE_SNOW_VILLAGE = PlacementUtils.createKey("pile_snow");
/* 22 */   public static final ResourceKey<PlacedFeature> PILE_ICE_VILLAGE = PlacementUtils.createKey("pile_ice");
/* 23 */   public static final ResourceKey<PlacedFeature> PILE_PUMPKIN_VILLAGE = PlacementUtils.createKey("pile_pumpkin");
/*    */   
/* 25 */   public static final ResourceKey<PlacedFeature> OAK_VILLAGE = PlacementUtils.createKey("oak");
/* 26 */   public static final ResourceKey<PlacedFeature> ACACIA_VILLAGE = PlacementUtils.createKey("acacia");
/* 27 */   public static final ResourceKey<PlacedFeature> SPRUCE_VILLAGE = PlacementUtils.createKey("spruce");
/* 28 */   public static final ResourceKey<PlacedFeature> PINE_VILLAGE = PlacementUtils.createKey("pine");
/*    */   
/* 30 */   public static final ResourceKey<PlacedFeature> PATCH_CACTUS_VILLAGE = PlacementUtils.createKey("patch_cactus");
/* 31 */   public static final ResourceKey<PlacedFeature> FLOWER_PLAIN_VILLAGE = PlacementUtils.createKey("flower_plain");
/* 32 */   public static final ResourceKey<PlacedFeature> PATCH_TAIGA_GRASS_VILLAGE = PlacementUtils.createKey("patch_taiga_grass");
/* 33 */   public static final ResourceKey<PlacedFeature> PATCH_BERRY_BUSH_VILLAGE = PlacementUtils.createKey("patch_berry_bush");
/*    */   
/*    */   public static void bootstrap(BootstapContext<PlacedFeature> $$0) {
/* 36 */     HolderGetter<ConfiguredFeature<?, ?>> $$1 = $$0.lookup(Registries.CONFIGURED_FEATURE);
/* 37 */     Holder.Reference reference1 = $$1.getOrThrow(PileFeatures.PILE_HAY);
/* 38 */     Holder.Reference reference2 = $$1.getOrThrow(PileFeatures.PILE_MELON);
/* 39 */     Holder.Reference reference3 = $$1.getOrThrow(PileFeatures.PILE_SNOW);
/* 40 */     Holder.Reference reference4 = $$1.getOrThrow(PileFeatures.PILE_ICE);
/* 41 */     Holder.Reference reference5 = $$1.getOrThrow(PileFeatures.PILE_PUMPKIN);
/* 42 */     Holder.Reference reference6 = $$1.getOrThrow(TreeFeatures.OAK);
/* 43 */     Holder.Reference reference7 = $$1.getOrThrow(TreeFeatures.ACACIA);
/* 44 */     Holder.Reference reference8 = $$1.getOrThrow(TreeFeatures.SPRUCE);
/* 45 */     Holder.Reference reference9 = $$1.getOrThrow(TreeFeatures.PINE);
/* 46 */     Holder.Reference reference10 = $$1.getOrThrow(VegetationFeatures.PATCH_CACTUS);
/* 47 */     Holder.Reference reference11 = $$1.getOrThrow(VegetationFeatures.FLOWER_PLAIN);
/* 48 */     Holder.Reference reference12 = $$1.getOrThrow(VegetationFeatures.PATCH_TAIGA_GRASS);
/* 49 */     Holder.Reference reference13 = $$1.getOrThrow(VegetationFeatures.PATCH_BERRY_BUSH);
/*    */     
/* 51 */     PlacementUtils.register($$0, PILE_HAY_VILLAGE, (Holder<ConfiguredFeature<?, ?>>)reference1, new PlacementModifier[0]);
/* 52 */     PlacementUtils.register($$0, PILE_MELON_VILLAGE, (Holder<ConfiguredFeature<?, ?>>)reference2, new PlacementModifier[0]);
/* 53 */     PlacementUtils.register($$0, PILE_SNOW_VILLAGE, (Holder<ConfiguredFeature<?, ?>>)reference3, new PlacementModifier[0]);
/* 54 */     PlacementUtils.register($$0, PILE_ICE_VILLAGE, (Holder<ConfiguredFeature<?, ?>>)reference4, new PlacementModifier[0]);
/* 55 */     PlacementUtils.register($$0, PILE_PUMPKIN_VILLAGE, (Holder<ConfiguredFeature<?, ?>>)reference5, new PlacementModifier[0]);
/*    */     
/* 57 */     PlacementUtils.register($$0, OAK_VILLAGE, (Holder<ConfiguredFeature<?, ?>>)reference6, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING) });
/* 58 */     PlacementUtils.register($$0, ACACIA_VILLAGE, (Holder<ConfiguredFeature<?, ?>>)reference7, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.ACACIA_SAPLING) });
/* 59 */     PlacementUtils.register($$0, SPRUCE_VILLAGE, (Holder<ConfiguredFeature<?, ?>>)reference8, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING) });
/* 60 */     PlacementUtils.register($$0, PINE_VILLAGE, (Holder<ConfiguredFeature<?, ?>>)reference9, new PlacementModifier[] { (PlacementModifier)PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING) });
/*    */     
/* 62 */     PlacementUtils.register($$0, PATCH_CACTUS_VILLAGE, (Holder<ConfiguredFeature<?, ?>>)reference10, new PlacementModifier[0]);
/* 63 */     PlacementUtils.register($$0, FLOWER_PLAIN_VILLAGE, (Holder<ConfiguredFeature<?, ?>>)reference11, new PlacementModifier[0]);
/* 64 */     PlacementUtils.register($$0, PATCH_TAIGA_GRASS_VILLAGE, (Holder<ConfiguredFeature<?, ?>>)reference12, new PlacementModifier[0]);
/* 65 */     PlacementUtils.register($$0, PATCH_BERRY_BUSH_VILLAGE, (Holder<ConfiguredFeature<?, ?>>)reference13, new PlacementModifier[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\placement\VillagePlacements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */