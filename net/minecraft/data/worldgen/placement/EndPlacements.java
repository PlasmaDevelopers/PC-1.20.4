/*    */ package net.minecraft.data.worldgen.placement;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.worldgen.BootstapContext;
/*    */ import net.minecraft.data.worldgen.features.EndFeatures;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.util.valueproviders.UniformInt;
/*    */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*    */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*    */ import net.minecraft.world.level.levelgen.placement.BiomeFilter;
/*    */ import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
/*    */ import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacementModifier;
/*    */ import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
/*    */ import net.minecraft.world.level.levelgen.placement.RarityFilter;
/*    */ 
/*    */ public class EndPlacements {
/* 21 */   public static final ResourceKey<PlacedFeature> END_SPIKE = PlacementUtils.createKey("end_spike");
/* 22 */   public static final ResourceKey<PlacedFeature> END_GATEWAY_RETURN = PlacementUtils.createKey("end_gateway_return");
/* 23 */   public static final ResourceKey<PlacedFeature> CHORUS_PLANT = PlacementUtils.createKey("chorus_plant");
/* 24 */   public static final ResourceKey<PlacedFeature> END_ISLAND_DECORATED = PlacementUtils.createKey("end_island_decorated");
/*    */   
/*    */   public static void bootstrap(BootstapContext<PlacedFeature> $$0) {
/* 27 */     HolderGetter<ConfiguredFeature<?, ?>> $$1 = $$0.lookup(Registries.CONFIGURED_FEATURE);
/* 28 */     Holder.Reference reference1 = $$1.getOrThrow(EndFeatures.END_SPIKE);
/* 29 */     Holder.Reference reference2 = $$1.getOrThrow(EndFeatures.END_GATEWAY_RETURN);
/* 30 */     Holder.Reference reference3 = $$1.getOrThrow(EndFeatures.CHORUS_PLANT);
/* 31 */     Holder.Reference reference4 = $$1.getOrThrow(EndFeatures.END_ISLAND);
/*    */     
/* 33 */     PlacementUtils.register($$0, END_SPIKE, (Holder<ConfiguredFeature<?, ?>>)reference1, new PlacementModifier[] {
/* 34 */           (PlacementModifier)BiomeFilter.biome()
/*    */         });
/* 36 */     PlacementUtils.register($$0, END_GATEWAY_RETURN, (Holder<ConfiguredFeature<?, ?>>)reference2, new PlacementModifier[] {
/* 37 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(700), 
/* 38 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*    */           
/* 40 */           (PlacementModifier)RandomOffsetPlacement.vertical((IntProvider)UniformInt.of(3, 9)), 
/* 41 */           (PlacementModifier)BiomeFilter.biome()
/*    */         });
/* 43 */     PlacementUtils.register($$0, CHORUS_PLANT, (Holder<ConfiguredFeature<?, ?>>)reference3, new PlacementModifier[] {
/* 44 */           (PlacementModifier)CountPlacement.of((IntProvider)UniformInt.of(0, 4)), 
/* 45 */           (PlacementModifier)InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, 
/*    */           
/* 47 */           (PlacementModifier)BiomeFilter.biome()
/*    */         });
/* 49 */     PlacementUtils.register($$0, END_ISLAND_DECORATED, (Holder<ConfiguredFeature<?, ?>>)reference4, new PlacementModifier[] {
/* 50 */           (PlacementModifier)RarityFilter.onAverageOnceEvery(14), 
/* 51 */           PlacementUtils.countExtra(1, 0.25F, 1), 
/* 52 */           (PlacementModifier)InSquarePlacement.spread(), 
/* 53 */           (PlacementModifier)HeightRangePlacement.uniform(VerticalAnchor.absolute(55), VerticalAnchor.absolute(70)), 
/* 54 */           (PlacementModifier)BiomeFilter.biome()
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\placement\EndPlacements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */