/*    */ package net.minecraft.data.registries;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.core.RegistrySetBuilder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.worldgen.Carvers;
/*    */ import net.minecraft.data.worldgen.DimensionTypes;
/*    */ import net.minecraft.data.worldgen.NoiseData;
/*    */ import net.minecraft.data.worldgen.Pools;
/*    */ import net.minecraft.data.worldgen.ProcessorLists;
/*    */ import net.minecraft.data.worldgen.StructureSets;
/*    */ import net.minecraft.data.worldgen.Structures;
/*    */ import net.minecraft.data.worldgen.biome.BiomeData;
/*    */ import net.minecraft.data.worldgen.features.FeatureUtils;
/*    */ import net.minecraft.data.worldgen.placement.PlacementUtils;
/*    */ import net.minecraft.network.chat.ChatType;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.damagesource.DamageTypes;
/*    */ import net.minecraft.world.item.armortrim.TrimMaterials;
/*    */ import net.minecraft.world.item.armortrim.TrimPatterns;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
/*    */ import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
/*    */ import net.minecraft.world.level.levelgen.NoiseRouterData;
/*    */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPresets;
/*    */ import net.minecraft.world.level.levelgen.placement.BiomeFilter;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ import net.minecraft.world.level.levelgen.presets.WorldPresets;
/*    */ 
/*    */ public class VanillaRegistries {
/* 39 */   private static final RegistrySetBuilder BUILDER = (new RegistrySetBuilder())
/* 40 */     .add(Registries.DIMENSION_TYPE, DimensionTypes::bootstrap)
/* 41 */     .add(Registries.CONFIGURED_CARVER, Carvers::bootstrap)
/* 42 */     .add(Registries.CONFIGURED_FEATURE, FeatureUtils::bootstrap)
/* 43 */     .add(Registries.PLACED_FEATURE, PlacementUtils::bootstrap)
/* 44 */     .add(Registries.STRUCTURE, Structures::bootstrap)
/* 45 */     .add(Registries.STRUCTURE_SET, StructureSets::bootstrap)
/* 46 */     .add(Registries.PROCESSOR_LIST, ProcessorLists::bootstrap)
/* 47 */     .add(Registries.TEMPLATE_POOL, Pools::bootstrap)
/* 48 */     .add(Registries.BIOME, BiomeData::bootstrap)
/* 49 */     .add(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, MultiNoiseBiomeSourceParameterLists::bootstrap)
/* 50 */     .add(Registries.NOISE, NoiseData::bootstrap)
/* 51 */     .add(Registries.DENSITY_FUNCTION, NoiseRouterData::bootstrap)
/* 52 */     .add(Registries.NOISE_SETTINGS, NoiseGeneratorSettings::bootstrap)
/* 53 */     .add(Registries.WORLD_PRESET, WorldPresets::bootstrap)
/* 54 */     .add(Registries.FLAT_LEVEL_GENERATOR_PRESET, FlatLevelGeneratorPresets::bootstrap)
/* 55 */     .add(Registries.CHAT_TYPE, ChatType::bootstrap)
/* 56 */     .add(Registries.TRIM_PATTERN, TrimPatterns::bootstrap)
/* 57 */     .add(Registries.TRIM_MATERIAL, TrimMaterials::bootstrap)
/* 58 */     .add(Registries.DAMAGE_TYPE, DamageTypes::bootstrap);
/*    */   
/*    */   private static void validateThatAllBiomeFeaturesHaveBiomeFilter(HolderLookup.Provider $$0) {
/* 61 */     validateThatAllBiomeFeaturesHaveBiomeFilter((HolderGetter<PlacedFeature>)$$0.lookupOrThrow(Registries.PLACED_FEATURE), (HolderLookup<Biome>)$$0.lookupOrThrow(Registries.BIOME));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void validateThatAllBiomeFeaturesHaveBiomeFilter(HolderGetter<PlacedFeature> $$0, HolderLookup<Biome> $$1) {
/* 70 */     $$1.listElements().forEach($$1 -> {
/*    */           ResourceLocation $$2 = $$1.key().location();
/*    */           List<HolderSet<PlacedFeature>> $$3 = ((Biome)$$1.value()).getGenerationSettings().features();
/*    */           $$3.stream().flatMap(HolderSet::stream).forEach(());
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean validatePlacedFeature(PlacedFeature $$0) {
/* 91 */     return $$0.placement().contains(BiomeFilter.biome());
/*    */   }
/*    */   
/*    */   public static HolderLookup.Provider createLookup() {
/* 95 */     RegistryAccess.Frozen $$0 = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
/* 96 */     HolderLookup.Provider $$1 = BUILDER.build((RegistryAccess)$$0);
/* 97 */     validateThatAllBiomeFeaturesHaveBiomeFilter($$1);
/* 98 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\registries\VanillaRegistries.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */