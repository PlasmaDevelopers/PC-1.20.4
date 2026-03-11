/*    */ package net.minecraft.data.worldgen.biome;
/*    */ 
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.data.worldgen.BiomeDefaultFeatures;
/*    */ import net.minecraft.data.worldgen.placement.EndPlacements;
/*    */ import net.minecraft.world.level.biome.AmbientMoodSettings;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.biome.BiomeGenerationSettings;
/*    */ import net.minecraft.world.level.biome.BiomeSpecialEffects;
/*    */ import net.minecraft.world.level.biome.MobSpawnSettings;
/*    */ import net.minecraft.world.level.levelgen.GenerationStep;
/*    */ import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ public class EndBiomes {
/*    */   private static Biome baseEndBiome(BiomeGenerationSettings.Builder $$0) {
/* 17 */     MobSpawnSettings.Builder $$1 = new MobSpawnSettings.Builder();
/* 18 */     BiomeDefaultFeatures.endSpawns($$1);
/*    */     
/* 20 */     return (new Biome.BiomeBuilder())
/* 21 */       .hasPrecipitation(false)
/* 22 */       .temperature(0.5F)
/* 23 */       .downfall(0.5F)
/* 24 */       .specialEffects((new BiomeSpecialEffects.Builder())
/* 25 */         .waterColor(4159204)
/* 26 */         .waterFogColor(329011)
/* 27 */         .fogColor(10518688)
/* 28 */         .skyColor(0)
/* 29 */         .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
/* 30 */         .build())
/*    */       
/* 32 */       .mobSpawnSettings($$1.build())
/* 33 */       .generationSettings($$0.build())
/* 34 */       .build();
/*    */   }
/*    */   
/*    */   public static Biome endBarrens(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 38 */     BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder($$0, $$1);
/* 39 */     return baseEndBiome($$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Biome theEnd(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 44 */     BiomeGenerationSettings.Builder $$2 = (new BiomeGenerationSettings.Builder($$0, $$1)).addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, EndPlacements.END_SPIKE);
/* 45 */     return baseEndBiome($$2);
/*    */   }
/*    */   
/*    */   public static Biome endMidlands(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 49 */     BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder($$0, $$1);
/* 50 */     return baseEndBiome($$2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Biome endHighlands(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 56 */     BiomeGenerationSettings.Builder $$2 = (new BiomeGenerationSettings.Builder($$0, $$1)).addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EndPlacements.CHORUS_PLANT);
/* 57 */     return baseEndBiome($$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Biome smallEndIslands(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 62 */     BiomeGenerationSettings.Builder $$2 = (new BiomeGenerationSettings.Builder($$0, $$1)).addFeature(GenerationStep.Decoration.RAW_GENERATION, EndPlacements.END_ISLAND_DECORATED);
/* 63 */     return baseEndBiome($$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\biome\EndBiomes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */