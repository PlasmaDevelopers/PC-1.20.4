/*     */ package net.minecraft.data.worldgen.biome;
/*     */ 
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.data.worldgen.BiomeDefaultFeatures;
/*     */ import net.minecraft.data.worldgen.Carvers;
/*     */ import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
/*     */ import net.minecraft.data.worldgen.placement.NetherPlacements;
/*     */ import net.minecraft.data.worldgen.placement.OrePlacements;
/*     */ import net.minecraft.data.worldgen.placement.TreePlacements;
/*     */ import net.minecraft.data.worldgen.placement.VegetationPlacements;
/*     */ import net.minecraft.sounds.Musics;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobCategory;
/*     */ import net.minecraft.world.level.biome.AmbientAdditionsSettings;
/*     */ import net.minecraft.world.level.biome.AmbientMoodSettings;
/*     */ import net.minecraft.world.level.biome.AmbientParticleSettings;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeGenerationSettings;
/*     */ import net.minecraft.world.level.biome.BiomeSpecialEffects;
/*     */ import net.minecraft.world.level.biome.MobSpawnSettings;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetherBiomes
/*     */ {
/*     */   public static Biome netherWastes(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/*  37 */     MobSpawnSettings $$2 = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 50, 4, 4)).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4)).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 2, 4, 4)).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 4, 4)).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 15, 4, 4)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2)).build();
/*     */ 
/*     */ 
/*     */     
/*  41 */     BiomeGenerationSettings.Builder $$3 = (new BiomeGenerationSettings.Builder($$0, $$1)).addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA);
/*     */     
/*  43 */     BiomeDefaultFeatures.addDefaultMushrooms($$3);
/*  44 */     $$3
/*  45 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_OPEN)
/*  46 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE)
/*  47 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE)
/*  48 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA)
/*  49 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE)
/*  50 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.BROWN_MUSHROOM_NETHER)
/*  51 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.RED_MUSHROOM_NETHER)
/*  52 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA)
/*  53 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED);
/*     */     
/*  55 */     BiomeDefaultFeatures.addNetherDefaultOres($$3);
/*     */     
/*  57 */     return (new Biome.BiomeBuilder())
/*  58 */       .hasPrecipitation(false)
/*  59 */       .temperature(2.0F)
/*  60 */       .downfall(0.0F)
/*  61 */       .specialEffects((new BiomeSpecialEffects.Builder())
/*  62 */         .waterColor(4159204)
/*  63 */         .waterFogColor(329011)
/*  64 */         .fogColor(3344392)
/*  65 */         .skyColor(OverworldBiomes.calculateSkyColor(2.0F))
/*  66 */         .ambientLoopSound((Holder)SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
/*  67 */         .ambientMoodSound(new AmbientMoodSettings((Holder)SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0D))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  73 */         .ambientAdditionsSound(new AmbientAdditionsSettings((Holder)SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS, 0.0111D))
/*     */ 
/*     */ 
/*     */         
/*  77 */         .backgroundMusic(Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_NETHER_WASTES))
/*  78 */         .build())
/*     */       
/*  80 */       .mobSpawnSettings($$2)
/*  81 */       .generationSettings($$3.build())
/*  82 */       .build();
/*     */   }
/*     */   
/*     */   public static Biome soulSandValley(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/*  86 */     double $$2 = 0.7D;
/*  87 */     double $$3 = 0.15D;
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
/*  98 */     MobSpawnSettings $$4 = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 20, 5, 5)).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 50, 4, 4)).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 4, 4)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2)).addMobCharge(EntityType.SKELETON, 0.7D, 0.15D).addMobCharge(EntityType.GHAST, 0.7D, 0.15D).addMobCharge(EntityType.ENDERMAN, 0.7D, 0.15D).addMobCharge(EntityType.STRIDER, 0.7D, 0.15D).build();
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
/* 113 */     BiomeGenerationSettings.Builder $$5 = (new BiomeGenerationSettings.Builder($$0, $$1)).addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA).addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, NetherPlacements.BASALT_PILLAR).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_OPEN).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_CRIMSON_ROOTS).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_SOUL_SAND);
/*     */     
/* 115 */     BiomeDefaultFeatures.addNetherDefaultOres($$5);
/*     */     
/* 117 */     return (new Biome.BiomeBuilder())
/* 118 */       .hasPrecipitation(false)
/* 119 */       .temperature(2.0F)
/* 120 */       .downfall(0.0F)
/* 121 */       .specialEffects((new BiomeSpecialEffects.Builder())
/* 122 */         .waterColor(4159204)
/* 123 */         .waterFogColor(329011)
/* 124 */         .fogColor(1787717)
/* 125 */         .skyColor(OverworldBiomes.calculateSkyColor(2.0F))
/* 126 */         .ambientParticle(new AmbientParticleSettings((ParticleOptions)ParticleTypes.ASH, 0.00625F))
/* 127 */         .ambientLoopSound((Holder)SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
/* 128 */         .ambientMoodSound(new AmbientMoodSettings((Holder)SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2.0D))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 134 */         .ambientAdditionsSound(new AmbientAdditionsSettings((Holder)SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111D))
/*     */ 
/*     */ 
/*     */         
/* 138 */         .backgroundMusic(Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY))
/* 139 */         .build())
/*     */       
/* 141 */       .mobSpawnSettings($$4)
/* 142 */       .generationSettings($$5.build())
/* 143 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Biome basaltDeltas(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 151 */     MobSpawnSettings $$2 = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 40, 1, 1)).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 100, 2, 5)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2)).build();
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
/* 170 */     BiomeGenerationSettings.Builder $$3 = (new BiomeGenerationSettings.Builder($$0, $$1)).addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE).addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.DELTA).addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.SMALL_BASALT_COLUMNS).addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.LARGE_BASALT_COLUMNS).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BASALT_BLOBS).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BLACKSTONE_BLOBS).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_DELTA).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.BROWN_MUSHROOM_NETHER).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, VegetationPlacements.RED_MUSHROOM_NETHER).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED_DOUBLE).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_GOLD_DELTAS).addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_QUARTZ_DELTAS);
/* 171 */     BiomeDefaultFeatures.addAncientDebris($$3);
/*     */     
/* 173 */     return (new Biome.BiomeBuilder())
/* 174 */       .hasPrecipitation(false)
/* 175 */       .temperature(2.0F)
/* 176 */       .downfall(0.0F)
/* 177 */       .specialEffects((new BiomeSpecialEffects.Builder())
/* 178 */         .waterColor(4159204)
/* 179 */         .waterFogColor(329011)
/* 180 */         .fogColor(6840176)
/* 181 */         .skyColor(OverworldBiomes.calculateSkyColor(2.0F))
/* 182 */         .ambientParticle(new AmbientParticleSettings((ParticleOptions)ParticleTypes.WHITE_ASH, 0.118093334F))
/* 183 */         .ambientLoopSound((Holder)SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
/* 184 */         .ambientMoodSound(new AmbientMoodSettings((Holder)SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0D))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 190 */         .ambientAdditionsSound(new AmbientAdditionsSettings((Holder)SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111D))
/*     */ 
/*     */ 
/*     */         
/* 194 */         .backgroundMusic(Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_BASALT_DELTAS))
/* 195 */         .build())
/*     */       
/* 197 */       .mobSpawnSettings($$2)
/* 198 */       .generationSettings($$3.build())
/* 199 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Biome crimsonForest(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 208 */     MobSpawnSettings $$2 = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 1, 2, 4)).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HOGLIN, 9, 3, 4)).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 5, 3, 4)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2)).build();
/*     */ 
/*     */ 
/*     */     
/* 212 */     BiomeGenerationSettings.Builder $$3 = (new BiomeGenerationSettings.Builder($$0, $$1)).addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA);
/*     */     
/* 214 */     BiomeDefaultFeatures.addDefaultMushrooms($$3);
/* 215 */     $$3
/* 216 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_OPEN)
/* 217 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE)
/* 218 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA)
/* 219 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE)
/* 220 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA)
/* 221 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED)
/* 222 */       .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.WEEPING_VINES)
/* 223 */       .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.CRIMSON_FUNGI)
/* 224 */       .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.CRIMSON_FOREST_VEGETATION);
/*     */     
/* 226 */     BiomeDefaultFeatures.addNetherDefaultOres($$3);
/*     */     
/* 228 */     return (new Biome.BiomeBuilder())
/* 229 */       .hasPrecipitation(false)
/* 230 */       .temperature(2.0F)
/* 231 */       .downfall(0.0F)
/* 232 */       .specialEffects((new BiomeSpecialEffects.Builder())
/* 233 */         .waterColor(4159204)
/* 234 */         .waterFogColor(329011)
/* 235 */         .fogColor(3343107)
/* 236 */         .skyColor(OverworldBiomes.calculateSkyColor(2.0F))
/* 237 */         .ambientParticle(new AmbientParticleSettings((ParticleOptions)ParticleTypes.CRIMSON_SPORE, 0.025F))
/* 238 */         .ambientLoopSound((Holder)SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
/* 239 */         .ambientMoodSound(new AmbientMoodSettings((Holder)SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2.0D))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 245 */         .ambientAdditionsSound(new AmbientAdditionsSettings((Holder)SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.0111D))
/*     */ 
/*     */ 
/*     */         
/* 249 */         .backgroundMusic(Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_CRIMSON_FOREST))
/* 250 */         .build())
/*     */       
/* 252 */       .mobSpawnSettings($$2)
/* 253 */       .generationSettings($$3.build())
/* 254 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Biome warpedForest(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 263 */     MobSpawnSettings $$2 = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 4, 4)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2)).addMobCharge(EntityType.ENDERMAN, 1.0D, 0.12D).build();
/*     */ 
/*     */ 
/*     */     
/* 267 */     BiomeGenerationSettings.Builder $$3 = (new BiomeGenerationSettings.Builder($$0, $$1)).addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA);
/*     */     
/* 269 */     BiomeDefaultFeatures.addDefaultMushrooms($$3);
/* 270 */     $$3
/* 271 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_OPEN)
/* 272 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE)
/* 273 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE)
/* 274 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE_EXTRA)
/* 275 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.GLOWSTONE)
/* 276 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA)
/* 277 */       .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED)
/* 278 */       .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, TreePlacements.WARPED_FUNGI)
/* 279 */       .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.WARPED_FOREST_VEGETATION)
/* 280 */       .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.NETHER_SPROUTS)
/* 281 */       .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, NetherPlacements.TWISTING_VINES);
/*     */     
/* 283 */     BiomeDefaultFeatures.addNetherDefaultOres($$3);
/*     */     
/* 285 */     return (new Biome.BiomeBuilder())
/* 286 */       .hasPrecipitation(false)
/* 287 */       .temperature(2.0F)
/* 288 */       .downfall(0.0F)
/* 289 */       .specialEffects((new BiomeSpecialEffects.Builder())
/* 290 */         .waterColor(4159204)
/* 291 */         .waterFogColor(329011)
/* 292 */         .fogColor(1705242)
/* 293 */         .skyColor(OverworldBiomes.calculateSkyColor(2.0F))
/* 294 */         .ambientParticle(new AmbientParticleSettings((ParticleOptions)ParticleTypes.WARPED_SPORE, 0.01428F))
/* 295 */         .ambientLoopSound((Holder)SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
/* 296 */         .ambientMoodSound(new AmbientMoodSettings((Holder)SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2.0D))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 302 */         .ambientAdditionsSound(new AmbientAdditionsSettings((Holder)SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS, 0.0111D))
/*     */ 
/*     */ 
/*     */         
/* 306 */         .backgroundMusic(Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_WARPED_FOREST))
/* 307 */         .build())
/*     */       
/* 309 */       .mobSpawnSettings($$2)
/* 310 */       .generationSettings($$3.build())
/* 311 */       .build();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\biome\NetherBiomes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */