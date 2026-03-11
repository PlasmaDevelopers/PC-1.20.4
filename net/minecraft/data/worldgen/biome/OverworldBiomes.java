/*     */ package net.minecraft.data.worldgen.biome;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.data.worldgen.BiomeDefaultFeatures;
/*     */ import net.minecraft.data.worldgen.Carvers;
/*     */ import net.minecraft.data.worldgen.placement.AquaticPlacements;
/*     */ import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
/*     */ import net.minecraft.data.worldgen.placement.VegetationPlacements;
/*     */ import net.minecraft.sounds.Music;
/*     */ import net.minecraft.sounds.Musics;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobCategory;
/*     */ import net.minecraft.world.level.biome.AmbientMoodSettings;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeGenerationSettings;
/*     */ import net.minecraft.world.level.biome.BiomeSpecialEffects;
/*     */ import net.minecraft.world.level.biome.MobSpawnSettings;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*     */ 
/*     */ public class OverworldBiomes {
/*     */   protected static final int NORMAL_WATER_COLOR = 4159204;
/*     */   protected static final int NORMAL_WATER_FOG_COLOR = 329011;
/*     */   private static final int OVERWORLD_FOG_COLOR = 12638463;
/*     */   @Nullable
/*  31 */   private static final Music NORMAL_MUSIC = null;
/*     */   
/*     */   protected static int calculateSkyColor(float $$0) {
/*  34 */     float $$1 = $$0;
/*  35 */     $$1 /= 3.0F;
/*  36 */     $$1 = Mth.clamp($$1, -1.0F, 1.0F);
/*  37 */     return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
/*     */   }
/*     */   
/*     */   private static Biome biome(boolean $$0, float $$1, float $$2, MobSpawnSettings.Builder $$3, BiomeGenerationSettings.Builder $$4, @Nullable Music $$5) {
/*  41 */     return biome($$0, $$1, $$2, 4159204, 329011, null, null, $$3, $$4, $$5);
/*     */   }
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
/*     */   private static Biome biome(boolean $$0, float $$1, float $$2, int $$3, int $$4, @Nullable Integer $$5, @Nullable Integer $$6, MobSpawnSettings.Builder $$7, BiomeGenerationSettings.Builder $$8, @Nullable Music $$9) {
/*  61 */     BiomeSpecialEffects.Builder $$10 = (new BiomeSpecialEffects.Builder()).waterColor($$3).waterFogColor($$4).fogColor(12638463).skyColor(calculateSkyColor($$1)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic($$9);
/*     */     
/*  63 */     if ($$5 != null) {
/*  64 */       $$10.grassColorOverride($$5.intValue());
/*     */     }
/*     */     
/*  67 */     if ($$6 != null) {
/*  68 */       $$10.foliageColorOverride($$6.intValue());
/*     */     }
/*     */     
/*  71 */     return (new Biome.BiomeBuilder())
/*  72 */       .hasPrecipitation($$0)
/*  73 */       .temperature($$1)
/*  74 */       .downfall($$2)
/*  75 */       .specialEffects($$10.build())
/*  76 */       .mobSpawnSettings($$7.build())
/*  77 */       .generationSettings($$8.build())
/*  78 */       .build();
/*     */   }
/*     */   
/*     */   private static void globalOverworldGeneration(BiomeGenerationSettings.Builder $$0) {
/*  82 */     BiomeDefaultFeatures.addDefaultCarversAndLakes($$0);
/*  83 */     BiomeDefaultFeatures.addDefaultCrystalFormations($$0);
/*  84 */     BiomeDefaultFeatures.addDefaultMonsterRoom($$0);
/*  85 */     BiomeDefaultFeatures.addDefaultUndergroundVariety($$0);
/*  86 */     BiomeDefaultFeatures.addDefaultSprings($$0);
/*  87 */     BiomeDefaultFeatures.addSurfaceFreezing($$0);
/*     */   }
/*     */   
/*     */   public static Biome oldGrowthTaiga(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2) {
/*  91 */     MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
/*  92 */     BiomeDefaultFeatures.farmAnimals($$3);
/*  93 */     $$3.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4));
/*  94 */     $$3.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
/*  95 */     $$3.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
/*  96 */     if ($$2) {
/*  97 */       BiomeDefaultFeatures.commonSpawns($$3);
/*     */     } else {
/*  99 */       BiomeDefaultFeatures.caveSpawns($$3);
/* 100 */       BiomeDefaultFeatures.monsters($$3, 100, 25, 100, false);
/*     */     } 
/*     */     
/* 103 */     BiomeGenerationSettings.Builder $$4 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 105 */     globalOverworldGeneration($$4);
/* 106 */     BiomeDefaultFeatures.addMossyStoneBlock($$4);
/* 107 */     BiomeDefaultFeatures.addFerns($$4);
/* 108 */     BiomeDefaultFeatures.addDefaultOres($$4);
/* 109 */     BiomeDefaultFeatures.addDefaultSoftDisks($$4);
/* 110 */     $$4.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, $$2 ? VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA : VegetationPlacements.TREES_OLD_GROWTH_PINE_TAIGA);
/* 111 */     BiomeDefaultFeatures.addDefaultFlowers($$4);
/* 112 */     BiomeDefaultFeatures.addGiantTaigaVegetation($$4);
/* 113 */     BiomeDefaultFeatures.addDefaultMushrooms($$4);
/* 114 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$4);
/* 115 */     BiomeDefaultFeatures.addCommonBerryBushes($$4);
/*     */     
/* 117 */     Music $$5 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_OLD_GROWTH_TAIGA);
/*     */     
/* 119 */     return biome(true, $$2 ? 0.25F : 0.3F, 0.8F, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public static Biome sparseJungle(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 123 */     MobSpawnSettings.Builder $$2 = new MobSpawnSettings.Builder();
/* 124 */     BiomeDefaultFeatures.baseJungleSpawns($$2);
/*     */     
/* 126 */     return baseJungle($$0, $$1, 0.8F, false, true, false, $$2, Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_SPARSE_JUNGLE));
/*     */   }
/*     */   
/*     */   public static Biome jungle(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 130 */     MobSpawnSettings.Builder $$2 = new MobSpawnSettings.Builder();
/* 131 */     BiomeDefaultFeatures.baseJungleSpawns($$2);
/* 132 */     $$2.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PARROT, 40, 1, 2))
/* 133 */       .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 2, 1, 3))
/* 134 */       .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PANDA, 1, 1, 2));
/*     */     
/* 136 */     return baseJungle($$0, $$1, 0.9F, false, false, true, $$2, Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_JUNGLE));
/*     */   }
/*     */   
/*     */   public static Biome bambooJungle(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 140 */     MobSpawnSettings.Builder $$2 = new MobSpawnSettings.Builder();
/* 141 */     BiomeDefaultFeatures.baseJungleSpawns($$2);
/* 142 */     $$2.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PARROT, 40, 1, 2))
/* 143 */       .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PANDA, 80, 1, 2))
/* 144 */       .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 2, 1, 1));
/*     */     
/* 146 */     return baseJungle($$0, $$1, 0.9F, true, false, true, $$2, Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_BAMBOO_JUNGLE));
/*     */   }
/*     */   
/*     */   private static Biome baseJungle(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, float $$2, boolean $$3, boolean $$4, boolean $$5, MobSpawnSettings.Builder $$6, Music $$7) {
/* 150 */     BiomeGenerationSettings.Builder $$8 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 152 */     globalOverworldGeneration($$8);
/* 153 */     BiomeDefaultFeatures.addDefaultOres($$8);
/* 154 */     BiomeDefaultFeatures.addDefaultSoftDisks($$8);
/* 155 */     if ($$3) {
/* 156 */       BiomeDefaultFeatures.addBambooVegetation($$8);
/*     */     } else {
/* 158 */       if ($$5) {
/* 159 */         BiomeDefaultFeatures.addLightBambooVegetation($$8);
/*     */       }
/* 161 */       if ($$4) {
/* 162 */         BiomeDefaultFeatures.addSparseJungleTrees($$8);
/*     */       } else {
/* 164 */         BiomeDefaultFeatures.addJungleTrees($$8);
/*     */       } 
/*     */     } 
/* 167 */     BiomeDefaultFeatures.addWarmFlowers($$8);
/* 168 */     BiomeDefaultFeatures.addJungleGrass($$8);
/* 169 */     BiomeDefaultFeatures.addDefaultMushrooms($$8);
/* 170 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$8);
/* 171 */     BiomeDefaultFeatures.addJungleVines($$8);
/* 172 */     if ($$4) {
/* 173 */       BiomeDefaultFeatures.addSparseJungleMelons($$8);
/*     */     } else {
/* 175 */       BiomeDefaultFeatures.addJungleMelons($$8);
/*     */     } 
/*     */     
/* 178 */     return biome(true, 0.95F, $$2, $$6, $$8, $$7);
/*     */   }
/*     */   
/*     */   public static Biome windsweptHills(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2) {
/* 182 */     MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
/* 183 */     BiomeDefaultFeatures.farmAnimals($$3);
/* 184 */     $$3.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.LLAMA, 5, 4, 6));
/* 185 */     BiomeDefaultFeatures.commonSpawns($$3);
/*     */     
/* 187 */     BiomeGenerationSettings.Builder $$4 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 189 */     globalOverworldGeneration($$4);
/* 190 */     BiomeDefaultFeatures.addDefaultOres($$4);
/* 191 */     BiomeDefaultFeatures.addDefaultSoftDisks($$4);
/* 192 */     if ($$2) {
/* 193 */       BiomeDefaultFeatures.addMountainForestTrees($$4);
/*     */     } else {
/* 195 */       BiomeDefaultFeatures.addMountainTrees($$4);
/*     */     } 
/* 197 */     BiomeDefaultFeatures.addDefaultFlowers($$4);
/* 198 */     BiomeDefaultFeatures.addDefaultGrass($$4);
/* 199 */     BiomeDefaultFeatures.addDefaultMushrooms($$4);
/* 200 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$4);
/* 201 */     BiomeDefaultFeatures.addExtraEmeralds($$4);
/* 202 */     BiomeDefaultFeatures.addInfestedStone($$4);
/*     */     
/* 204 */     return biome(true, 0.2F, 0.3F, $$3, $$4, NORMAL_MUSIC);
/*     */   }
/*     */   
/*     */   public static Biome desert(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 208 */     MobSpawnSettings.Builder $$2 = new MobSpawnSettings.Builder();
/* 209 */     BiomeDefaultFeatures.desertSpawns($$2);
/*     */     
/* 211 */     BiomeGenerationSettings.Builder $$3 = new BiomeGenerationSettings.Builder($$0, $$1);
/* 212 */     BiomeDefaultFeatures.addFossilDecoration($$3);
/*     */     
/* 214 */     globalOverworldGeneration($$3);
/* 215 */     BiomeDefaultFeatures.addDefaultOres($$3);
/* 216 */     BiomeDefaultFeatures.addDefaultSoftDisks($$3);
/* 217 */     BiomeDefaultFeatures.addDefaultFlowers($$3);
/* 218 */     BiomeDefaultFeatures.addDefaultGrass($$3);
/* 219 */     BiomeDefaultFeatures.addDesertVegetation($$3);
/* 220 */     BiomeDefaultFeatures.addDefaultMushrooms($$3);
/* 221 */     BiomeDefaultFeatures.addDesertExtraVegetation($$3);
/* 222 */     BiomeDefaultFeatures.addDesertExtraDecoration($$3);
/*     */     
/* 224 */     return biome(false, 2.0F, 0.0F, $$2, $$3, Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_DESERT));
/*     */   }
/*     */   
/*     */   public static Biome plains(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2, boolean $$3, boolean $$4) {
/* 228 */     MobSpawnSettings.Builder $$5 = new MobSpawnSettings.Builder();
/* 229 */     BiomeGenerationSettings.Builder $$6 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 231 */     globalOverworldGeneration($$6);
/*     */     
/* 233 */     if ($$3) {
/* 234 */       $$5.creatureGenerationProbability(0.07F);
/* 235 */       BiomeDefaultFeatures.snowySpawns($$5);
/*     */       
/* 237 */       if ($$4) {
/* 238 */         $$6.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_SPIKE);
/* 239 */         $$6.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_PATCH);
/*     */       } 
/*     */     } else {
/* 242 */       BiomeDefaultFeatures.plainsSpawns($$5);
/* 243 */       BiomeDefaultFeatures.addPlainGrass($$6);
/* 244 */       if ($$2) {
/* 245 */         $$6.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUNFLOWER);
/*     */       }
/*     */     } 
/*     */     
/* 249 */     BiomeDefaultFeatures.addDefaultOres($$6);
/* 250 */     BiomeDefaultFeatures.addDefaultSoftDisks($$6);
/*     */     
/* 252 */     if ($$3) {
/* 253 */       BiomeDefaultFeatures.addSnowyTrees($$6);
/* 254 */       BiomeDefaultFeatures.addDefaultFlowers($$6);
/* 255 */       BiomeDefaultFeatures.addDefaultGrass($$6);
/*     */     } else {
/* 257 */       BiomeDefaultFeatures.addPlainVegetation($$6);
/*     */     } 
/*     */     
/* 260 */     BiomeDefaultFeatures.addDefaultMushrooms($$6);
/*     */     
/* 262 */     if ($$2) {
/* 263 */       $$6.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE);
/* 264 */       $$6.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
/*     */     } else {
/* 266 */       BiomeDefaultFeatures.addDefaultExtraVegetation($$6);
/*     */     } 
/*     */     
/* 269 */     float $$7 = $$3 ? 0.0F : 0.8F;
/*     */     
/* 271 */     return biome(true, $$7, $$3 ? 0.5F : 0.4F, $$5, $$6, NORMAL_MUSIC);
/*     */   }
/*     */   
/*     */   public static Biome mushroomFields(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 275 */     MobSpawnSettings.Builder $$2 = new MobSpawnSettings.Builder();
/* 276 */     BiomeDefaultFeatures.mooshroomSpawns($$2);
/*     */     
/* 278 */     BiomeGenerationSettings.Builder $$3 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 280 */     globalOverworldGeneration($$3);
/* 281 */     BiomeDefaultFeatures.addDefaultOres($$3);
/* 282 */     BiomeDefaultFeatures.addDefaultSoftDisks($$3);
/* 283 */     BiomeDefaultFeatures.addMushroomFieldVegetation($$3);
/* 284 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$3);
/*     */     
/* 286 */     return biome(true, 0.9F, 1.0F, $$2, $$3, NORMAL_MUSIC);
/*     */   }
/*     */   
/*     */   public static Biome savanna(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2, boolean $$3) {
/* 290 */     BiomeGenerationSettings.Builder $$4 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 292 */     globalOverworldGeneration($$4);
/* 293 */     if (!$$2) {
/* 294 */       BiomeDefaultFeatures.addSavannaGrass($$4);
/*     */     }
/* 296 */     BiomeDefaultFeatures.addDefaultOres($$4);
/* 297 */     BiomeDefaultFeatures.addDefaultSoftDisks($$4);
/* 298 */     if ($$2) {
/* 299 */       BiomeDefaultFeatures.addShatteredSavannaTrees($$4);
/* 300 */       BiomeDefaultFeatures.addDefaultFlowers($$4);
/* 301 */       BiomeDefaultFeatures.addShatteredSavannaGrass($$4);
/*     */     } else {
/* 303 */       BiomeDefaultFeatures.addSavannaTrees($$4);
/* 304 */       BiomeDefaultFeatures.addWarmFlowers($$4);
/* 305 */       BiomeDefaultFeatures.addSavannaExtraGrass($$4);
/*     */     } 
/* 307 */     BiomeDefaultFeatures.addDefaultMushrooms($$4);
/* 308 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$4);
/*     */     
/* 310 */     MobSpawnSettings.Builder $$5 = new MobSpawnSettings.Builder();
/* 311 */     BiomeDefaultFeatures.farmAnimals($$5);
/* 312 */     $$5.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 1, 2, 6))
/* 313 */       .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 1, 1));
/*     */     
/* 315 */     BiomeDefaultFeatures.commonSpawns($$5);
/*     */     
/* 317 */     if ($$3) {
/* 318 */       $$5.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.LLAMA, 8, 4, 4));
/*     */     }
/*     */     
/* 321 */     return biome(false, 2.0F, 0.0F, $$5, $$4, NORMAL_MUSIC);
/*     */   }
/*     */   
/*     */   public static Biome badlands(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2) {
/* 325 */     MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
/* 326 */     BiomeDefaultFeatures.commonSpawns($$3);
/*     */     
/* 328 */     BiomeGenerationSettings.Builder $$4 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 330 */     globalOverworldGeneration($$4);
/* 331 */     BiomeDefaultFeatures.addDefaultOres($$4);
/* 332 */     BiomeDefaultFeatures.addExtraGold($$4);
/* 333 */     BiomeDefaultFeatures.addDefaultSoftDisks($$4);
/* 334 */     if ($$2) {
/* 335 */       BiomeDefaultFeatures.addBadlandsTrees($$4);
/*     */     }
/* 337 */     BiomeDefaultFeatures.addBadlandGrass($$4);
/* 338 */     BiomeDefaultFeatures.addDefaultMushrooms($$4);
/* 339 */     BiomeDefaultFeatures.addBadlandExtraVegetation($$4);
/* 340 */     return (new Biome.BiomeBuilder())
/* 341 */       .hasPrecipitation(false)
/* 342 */       .temperature(2.0F)
/* 343 */       .downfall(0.0F)
/* 344 */       .specialEffects((new BiomeSpecialEffects.Builder())
/* 345 */         .waterColor(4159204)
/* 346 */         .waterFogColor(329011)
/* 347 */         .fogColor(12638463)
/* 348 */         .skyColor(calculateSkyColor(2.0F))
/* 349 */         .foliageColorOverride(10387789)
/* 350 */         .grassColorOverride(9470285)
/* 351 */         .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
/* 352 */         .backgroundMusic(Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_BADLANDS))
/* 353 */         .build())
/*     */       
/* 355 */       .mobSpawnSettings($$3.build())
/* 356 */       .generationSettings($$4.build())
/* 357 */       .build();
/*     */   }
/*     */   
/*     */   private static Biome baseOcean(MobSpawnSettings.Builder $$0, int $$1, int $$2, BiomeGenerationSettings.Builder $$3) {
/* 361 */     return biome(true, 0.5F, 0.5F, $$1, $$2, null, null, $$0, $$3, NORMAL_MUSIC);
/*     */   }
/*     */   
/*     */   private static BiomeGenerationSettings.Builder baseOceanGeneration(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 365 */     BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 367 */     globalOverworldGeneration($$2);
/* 368 */     BiomeDefaultFeatures.addDefaultOres($$2);
/* 369 */     BiomeDefaultFeatures.addDefaultSoftDisks($$2);
/* 370 */     BiomeDefaultFeatures.addWaterTrees($$2);
/* 371 */     BiomeDefaultFeatures.addDefaultFlowers($$2);
/* 372 */     BiomeDefaultFeatures.addDefaultGrass($$2);
/* 373 */     BiomeDefaultFeatures.addDefaultMushrooms($$2);
/* 374 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$2);
/* 375 */     return $$2;
/*     */   }
/*     */   
/*     */   public static Biome coldOcean(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2) {
/* 379 */     MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
/* 380 */     BiomeDefaultFeatures.oceanSpawns($$3, 3, 4, 15);
/* 381 */     $$3.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 15, 1, 5));
/*     */     
/* 383 */     BiomeGenerationSettings.Builder $$4 = baseOceanGeneration($$0, $$1);
/* 384 */     $$4.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, $$2 ? AquaticPlacements.SEAGRASS_DEEP_COLD : AquaticPlacements.SEAGRASS_COLD);
/* 385 */     BiomeDefaultFeatures.addDefaultSeagrass($$4);
/* 386 */     BiomeDefaultFeatures.addColdOceanExtraVegetation($$4);
/*     */     
/* 388 */     return baseOcean($$3, 4020182, 329011, $$4);
/*     */   }
/*     */   
/*     */   public static Biome ocean(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2) {
/* 392 */     MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
/* 393 */     BiomeDefaultFeatures.oceanSpawns($$3, 1, 4, 10);
/* 394 */     $$3.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 1, 1, 2));
/*     */     
/* 396 */     BiomeGenerationSettings.Builder $$4 = baseOceanGeneration($$0, $$1);
/* 397 */     $$4.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, $$2 ? AquaticPlacements.SEAGRASS_DEEP : AquaticPlacements.SEAGRASS_NORMAL);
/* 398 */     BiomeDefaultFeatures.addDefaultSeagrass($$4);
/* 399 */     BiomeDefaultFeatures.addColdOceanExtraVegetation($$4);
/*     */     
/* 401 */     return baseOcean($$3, 4159204, 329011, $$4);
/*     */   }
/*     */   
/*     */   public static Biome lukeWarmOcean(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2) {
/* 405 */     MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
/* 406 */     if ($$2) {
/* 407 */       BiomeDefaultFeatures.oceanSpawns($$3, 8, 4, 8);
/*     */     } else {
/* 409 */       BiomeDefaultFeatures.oceanSpawns($$3, 10, 2, 15);
/*     */     } 
/* 411 */     $$3.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.PUFFERFISH, 5, 1, 3))
/* 412 */       .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 8, 8))
/* 413 */       .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 2, 1, 2));
/*     */     
/* 415 */     BiomeGenerationSettings.Builder $$4 = baseOceanGeneration($$0, $$1);
/* 416 */     $$4.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, $$2 ? AquaticPlacements.SEAGRASS_DEEP_WARM : AquaticPlacements.SEAGRASS_WARM);
/* 417 */     if ($$2) {
/* 418 */       BiomeDefaultFeatures.addDefaultSeagrass($$4);
/*     */     }
/* 420 */     BiomeDefaultFeatures.addLukeWarmKelp($$4);
/*     */     
/* 422 */     return baseOcean($$3, 4566514, 267827, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Biome warmOcean(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 427 */     MobSpawnSettings.Builder $$2 = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.PUFFERFISH, 15, 1, 3));
/* 428 */     BiomeDefaultFeatures.warmOceanSpawns($$2, 10, 4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 433 */     BiomeGenerationSettings.Builder $$3 = baseOceanGeneration($$0, $$1).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.WARM_OCEAN_VEGETATION).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_WARM).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEA_PICKLE);
/*     */     
/* 435 */     return baseOcean($$2, 4445678, 270131, $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Biome frozenOcean(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2) {
/* 442 */     MobSpawnSettings.Builder $$3 = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 1, 1, 4)).addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 15, 1, 5)).addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 1, 1, 2));
/* 443 */     BiomeDefaultFeatures.commonSpawns($$3);
/* 444 */     $$3.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 5, 1, 1));
/*     */     
/* 446 */     float $$4 = $$2 ? 0.5F : 0.0F;
/* 447 */     BiomeGenerationSettings.Builder $$5 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 449 */     BiomeDefaultFeatures.addIcebergs($$5);
/*     */     
/* 451 */     globalOverworldGeneration($$5);
/* 452 */     BiomeDefaultFeatures.addBlueIce($$5);
/* 453 */     BiomeDefaultFeatures.addDefaultOres($$5);
/* 454 */     BiomeDefaultFeatures.addDefaultSoftDisks($$5);
/* 455 */     BiomeDefaultFeatures.addWaterTrees($$5);
/* 456 */     BiomeDefaultFeatures.addDefaultFlowers($$5);
/* 457 */     BiomeDefaultFeatures.addDefaultGrass($$5);
/* 458 */     BiomeDefaultFeatures.addDefaultMushrooms($$5);
/* 459 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$5);
/*     */     
/* 461 */     return (new Biome.BiomeBuilder())
/* 462 */       .hasPrecipitation(true)
/* 463 */       .temperature($$4)
/* 464 */       .temperatureAdjustment(Biome.TemperatureModifier.FROZEN)
/* 465 */       .downfall(0.5F)
/* 466 */       .specialEffects((new BiomeSpecialEffects.Builder())
/* 467 */         .waterColor(3750089)
/* 468 */         .waterFogColor(329011)
/* 469 */         .fogColor(12638463)
/* 470 */         .skyColor(calculateSkyColor($$4))
/* 471 */         .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
/* 472 */         .build())
/*     */       
/* 474 */       .mobSpawnSettings($$3.build())
/* 475 */       .generationSettings($$5.build())
/* 476 */       .build();
/*     */   }
/*     */   public static Biome forest(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2, boolean $$3, boolean $$4) {
/*     */     Music $$7;
/* 480 */     BiomeGenerationSettings.Builder $$5 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 482 */     globalOverworldGeneration($$5);
/*     */ 
/*     */     
/* 485 */     if ($$4) {
/* 486 */       Music $$6 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_FLOWER_FOREST);
/* 487 */       $$5.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_FOREST_FLOWERS);
/*     */     } else {
/* 489 */       $$7 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_FOREST);
/* 490 */       BiomeDefaultFeatures.addForestFlowers($$5);
/*     */     } 
/*     */     
/* 493 */     BiomeDefaultFeatures.addDefaultOres($$5);
/* 494 */     BiomeDefaultFeatures.addDefaultSoftDisks($$5);
/*     */     
/* 496 */     if ($$4) {
/* 497 */       $$5.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_FLOWER_FOREST);
/* 498 */       $$5.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_FLOWER_FOREST);
/* 499 */       BiomeDefaultFeatures.addDefaultGrass($$5);
/*     */     } else {
/* 501 */       if ($$2) {
/* 502 */         if ($$3) {
/* 503 */           BiomeDefaultFeatures.addTallBirchTrees($$5);
/*     */         } else {
/* 505 */           BiomeDefaultFeatures.addBirchTrees($$5);
/*     */         } 
/*     */       } else {
/* 508 */         BiomeDefaultFeatures.addOtherBirchTrees($$5);
/*     */       } 
/* 510 */       BiomeDefaultFeatures.addDefaultFlowers($$5);
/* 511 */       BiomeDefaultFeatures.addForestGrass($$5);
/*     */     } 
/*     */     
/* 514 */     BiomeDefaultFeatures.addDefaultMushrooms($$5);
/* 515 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$5);
/*     */     
/* 517 */     MobSpawnSettings.Builder $$8 = new MobSpawnSettings.Builder();
/* 518 */     BiomeDefaultFeatures.farmAnimals($$8);
/* 519 */     BiomeDefaultFeatures.commonSpawns($$8);
/*     */     
/* 521 */     if ($$4) {
/* 522 */       $$8.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
/* 523 */     } else if (!$$2) {
/* 524 */       $$8.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));
/*     */     } 
/*     */     
/* 527 */     float $$9 = $$2 ? 0.6F : 0.7F;
/*     */     
/* 529 */     return biome(true, $$9, $$2 ? 0.6F : 0.8F, $$8, $$5, $$7);
/*     */   }
/*     */   
/*     */   public static Biome taiga(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2) {
/* 533 */     MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
/* 534 */     BiomeDefaultFeatures.farmAnimals($$3);
/* 535 */     $$3.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4))
/* 536 */       .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3))
/* 537 */       .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
/*     */     
/* 539 */     BiomeDefaultFeatures.commonSpawns($$3);
/*     */     
/* 541 */     float $$4 = $$2 ? -0.5F : 0.25F;
/*     */     
/* 543 */     BiomeGenerationSettings.Builder $$5 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 545 */     globalOverworldGeneration($$5);
/* 546 */     BiomeDefaultFeatures.addFerns($$5);
/* 547 */     BiomeDefaultFeatures.addDefaultOres($$5);
/* 548 */     BiomeDefaultFeatures.addDefaultSoftDisks($$5);
/* 549 */     BiomeDefaultFeatures.addTaigaTrees($$5);
/* 550 */     BiomeDefaultFeatures.addDefaultFlowers($$5);
/* 551 */     BiomeDefaultFeatures.addTaigaGrass($$5);
/* 552 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$5);
/* 553 */     if ($$2) {
/* 554 */       BiomeDefaultFeatures.addRareBerryBushes($$5);
/*     */     } else {
/* 556 */       BiomeDefaultFeatures.addCommonBerryBushes($$5);
/*     */     } 
/*     */     
/* 559 */     return biome(true, $$4, $$2 ? 0.4F : 0.8F, $$2 ? 4020182 : 4159204, 329011, null, null, $$3, $$5, NORMAL_MUSIC);
/*     */   }
/*     */   
/*     */   public static Biome darkForest(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 563 */     MobSpawnSettings.Builder $$2 = new MobSpawnSettings.Builder();
/* 564 */     BiomeDefaultFeatures.farmAnimals($$2);
/* 565 */     BiomeDefaultFeatures.commonSpawns($$2);
/*     */     
/* 567 */     BiomeGenerationSettings.Builder $$3 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 569 */     globalOverworldGeneration($$3);
/* 570 */     $$3.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.DARK_FOREST_VEGETATION);
/* 571 */     BiomeDefaultFeatures.addForestFlowers($$3);
/* 572 */     BiomeDefaultFeatures.addDefaultOres($$3);
/* 573 */     BiomeDefaultFeatures.addDefaultSoftDisks($$3);
/* 574 */     BiomeDefaultFeatures.addDefaultFlowers($$3);
/* 575 */     BiomeDefaultFeatures.addForestGrass($$3);
/* 576 */     BiomeDefaultFeatures.addDefaultMushrooms($$3);
/* 577 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$3);
/*     */     
/* 579 */     Music $$4 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_FOREST);
/*     */     
/* 581 */     return (new Biome.BiomeBuilder())
/* 582 */       .hasPrecipitation(true)
/* 583 */       .temperature(0.7F)
/* 584 */       .downfall(0.8F)
/* 585 */       .specialEffects((new BiomeSpecialEffects.Builder())
/* 586 */         .waterColor(4159204)
/* 587 */         .waterFogColor(329011)
/* 588 */         .fogColor(12638463)
/* 589 */         .skyColor(calculateSkyColor(0.7F))
/* 590 */         .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
/* 591 */         .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
/* 592 */         .backgroundMusic($$4)
/* 593 */         .build())
/*     */       
/* 595 */       .mobSpawnSettings($$2.build())
/* 596 */       .generationSettings($$3.build())
/* 597 */       .build();
/*     */   }
/*     */   
/*     */   public static Biome swamp(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 601 */     MobSpawnSettings.Builder $$2 = new MobSpawnSettings.Builder();
/*     */     
/* 603 */     BiomeDefaultFeatures.farmAnimals($$2);
/* 604 */     BiomeDefaultFeatures.commonSpawns($$2);
/* 605 */     $$2.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 1, 1));
/* 606 */     $$2.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 10, 2, 5));
/*     */     
/* 608 */     BiomeGenerationSettings.Builder $$3 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 610 */     BiomeDefaultFeatures.addFossilDecoration($$3);
/*     */     
/* 612 */     globalOverworldGeneration($$3);
/* 613 */     BiomeDefaultFeatures.addDefaultOres($$3);
/*     */     
/* 615 */     BiomeDefaultFeatures.addSwampClayDisk($$3);
/* 616 */     BiomeDefaultFeatures.addSwampVegetation($$3);
/* 617 */     BiomeDefaultFeatures.addDefaultMushrooms($$3);
/* 618 */     BiomeDefaultFeatures.addSwampExtraVegetation($$3);
/* 619 */     $$3.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP);
/*     */     
/* 621 */     Music $$4 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_SWAMP);
/*     */     
/* 623 */     return (new Biome.BiomeBuilder())
/* 624 */       .hasPrecipitation(true)
/* 625 */       .temperature(0.8F)
/* 626 */       .downfall(0.9F)
/* 627 */       .specialEffects((new BiomeSpecialEffects.Builder())
/* 628 */         .waterColor(6388580)
/* 629 */         .waterFogColor(2302743)
/* 630 */         .fogColor(12638463)
/* 631 */         .skyColor(calculateSkyColor(0.8F))
/* 632 */         .foliageColorOverride(6975545)
/* 633 */         .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.SWAMP)
/* 634 */         .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
/* 635 */         .backgroundMusic($$4)
/* 636 */         .build())
/*     */       
/* 638 */       .mobSpawnSettings($$2.build())
/* 639 */       .generationSettings($$3.build())
/* 640 */       .build();
/*     */   }
/*     */   
/*     */   public static Biome mangroveSwamp(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 644 */     MobSpawnSettings.Builder $$2 = new MobSpawnSettings.Builder();
/* 645 */     BiomeDefaultFeatures.commonSpawns($$2);
/* 646 */     $$2.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 1, 1));
/* 647 */     $$2.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 10, 2, 5));
/* 648 */     $$2.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 8, 8));
/*     */     
/* 650 */     BiomeGenerationSettings.Builder $$3 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 652 */     BiomeDefaultFeatures.addFossilDecoration($$3);
/*     */     
/* 654 */     globalOverworldGeneration($$3);
/* 655 */     BiomeDefaultFeatures.addDefaultOres($$3);
/* 656 */     BiomeDefaultFeatures.addMangroveSwampDisks($$3);
/* 657 */     BiomeDefaultFeatures.addMangroveSwampVegetation($$3);
/* 658 */     $$3.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP);
/*     */     
/* 660 */     Music $$4 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_SWAMP);
/*     */     
/* 662 */     return (new Biome.BiomeBuilder())
/* 663 */       .hasPrecipitation(true)
/* 664 */       .temperature(0.8F)
/* 665 */       .downfall(0.9F)
/* 666 */       .specialEffects((new BiomeSpecialEffects.Builder())
/* 667 */         .waterColor(3832426)
/* 668 */         .waterFogColor(5077600)
/* 669 */         .fogColor(12638463)
/* 670 */         .skyColor(calculateSkyColor(0.8F))
/* 671 */         .foliageColorOverride(9285927)
/* 672 */         .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.SWAMP)
/* 673 */         .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
/* 674 */         .backgroundMusic($$4)
/* 675 */         .build())
/*     */       
/* 677 */       .mobSpawnSettings($$2.build())
/* 678 */       .generationSettings($$3.build())
/* 679 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Biome river(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2) {
/* 685 */     MobSpawnSettings.Builder $$3 = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 2, 1, 4)).addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5));
/* 686 */     BiomeDefaultFeatures.commonSpawns($$3);
/* 687 */     $$3.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, $$2 ? 1 : 100, 1, 1));
/*     */     
/* 689 */     BiomeGenerationSettings.Builder $$4 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 691 */     globalOverworldGeneration($$4);
/* 692 */     BiomeDefaultFeatures.addDefaultOres($$4);
/* 693 */     BiomeDefaultFeatures.addDefaultSoftDisks($$4);
/* 694 */     BiomeDefaultFeatures.addWaterTrees($$4);
/* 695 */     BiomeDefaultFeatures.addDefaultFlowers($$4);
/* 696 */     BiomeDefaultFeatures.addDefaultGrass($$4);
/* 697 */     BiomeDefaultFeatures.addDefaultMushrooms($$4);
/* 698 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$4);
/* 699 */     if (!$$2) {
/* 700 */       $$4.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);
/*     */     }
/*     */     
/* 703 */     float $$5 = $$2 ? 0.0F : 0.5F;
/*     */     
/* 705 */     return biome(true, $$5, 0.5F, $$2 ? 3750089 : 4159204, 329011, null, null, $$3, $$4, NORMAL_MUSIC);
/*     */   }
/*     */   public static Biome beach(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2, boolean $$3) {
/*     */     float $$9;
/* 709 */     MobSpawnSettings.Builder $$4 = new MobSpawnSettings.Builder();
/* 710 */     boolean $$5 = (!$$3 && !$$2);
/* 711 */     if ($$5) {
/* 712 */       $$4.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.TURTLE, 5, 2, 5));
/*     */     }
/* 714 */     BiomeDefaultFeatures.commonSpawns($$4);
/*     */     
/* 716 */     BiomeGenerationSettings.Builder $$6 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 718 */     globalOverworldGeneration($$6);
/* 719 */     BiomeDefaultFeatures.addDefaultOres($$6);
/* 720 */     BiomeDefaultFeatures.addDefaultSoftDisks($$6);
/* 721 */     BiomeDefaultFeatures.addDefaultFlowers($$6);
/* 722 */     BiomeDefaultFeatures.addDefaultGrass($$6);
/* 723 */     BiomeDefaultFeatures.addDefaultMushrooms($$6);
/* 724 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$6);
/*     */ 
/*     */     
/* 727 */     if ($$2) {
/* 728 */       float $$7 = 0.05F;
/* 729 */     } else if ($$3) {
/* 730 */       float $$8 = 0.2F;
/*     */     } else {
/* 732 */       $$9 = 0.8F;
/*     */     } 
/*     */     
/* 735 */     return biome(true, $$9, $$5 ? 0.4F : 0.3F, $$2 ? 4020182 : 4159204, 329011, null, null, $$4, $$6, NORMAL_MUSIC);
/*     */   }
/*     */   
/*     */   public static Biome theVoid(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 739 */     BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder($$0, $$1);
/* 740 */     $$2.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.VOID_START_PLATFORM);
/*     */     
/* 742 */     return biome(false, 0.5F, 0.5F, new MobSpawnSettings.Builder(), $$2, NORMAL_MUSIC);
/*     */   }
/*     */   
/*     */   public static Biome meadowOrCherryGrove(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1, boolean $$2) {
/* 746 */     BiomeGenerationSettings.Builder $$3 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 748 */     MobSpawnSettings.Builder $$4 = new MobSpawnSettings.Builder();
/* 749 */     $$4.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData($$2 ? EntityType.PIG : EntityType.DONKEY, 1, 1, 2))
/* 750 */       .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 2, 6))
/* 751 */       .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 2, 2, 4));
/* 752 */     BiomeDefaultFeatures.commonSpawns($$4);
/*     */     
/* 754 */     globalOverworldGeneration($$3);
/* 755 */     BiomeDefaultFeatures.addPlainGrass($$3);
/*     */     
/* 757 */     BiomeDefaultFeatures.addDefaultOres($$3);
/* 758 */     BiomeDefaultFeatures.addDefaultSoftDisks($$3);
/*     */     
/* 760 */     if ($$2) {
/* 761 */       BiomeDefaultFeatures.addCherryGroveVegetation($$3);
/*     */     } else {
/* 763 */       BiomeDefaultFeatures.addMeadowVegetation($$3);
/*     */     } 
/*     */     
/* 766 */     BiomeDefaultFeatures.addExtraEmeralds($$3);
/* 767 */     BiomeDefaultFeatures.addInfestedStone($$3);
/*     */     
/* 769 */     Music $$5 = Musics.createGameMusic($$2 ? (Holder)SoundEvents.MUSIC_BIOME_CHERRY_GROVE : (Holder)SoundEvents.MUSIC_BIOME_MEADOW);
/*     */     
/* 771 */     if ($$2) {
/* 772 */       return biome(true, 0.5F, 0.8F, 6141935, 6141935, Integer.valueOf(11983713), Integer.valueOf(11983713), $$4, $$3, $$5);
/*     */     }
/* 774 */     return biome(true, 0.5F, 0.8F, 937679, 329011, null, null, $$4, $$3, $$5);
/*     */   }
/*     */   
/*     */   public static Biome frozenPeaks(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 778 */     BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 780 */     MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
/* 781 */     $$3.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GOAT, 5, 1, 3));
/* 782 */     BiomeDefaultFeatures.commonSpawns($$3);
/*     */     
/* 784 */     globalOverworldGeneration($$2);
/* 785 */     BiomeDefaultFeatures.addFrozenSprings($$2);
/* 786 */     BiomeDefaultFeatures.addDefaultOres($$2);
/* 787 */     BiomeDefaultFeatures.addDefaultSoftDisks($$2);
/*     */     
/* 789 */     BiomeDefaultFeatures.addExtraEmeralds($$2);
/* 790 */     BiomeDefaultFeatures.addInfestedStone($$2);
/*     */     
/* 792 */     Music $$4 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_FROZEN_PEAKS);
/*     */     
/* 794 */     return biome(true, -0.7F, 0.9F, $$3, $$2, $$4);
/*     */   }
/*     */   
/*     */   public static Biome jaggedPeaks(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 798 */     BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 800 */     MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
/* 801 */     $$3.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GOAT, 5, 1, 3));
/* 802 */     BiomeDefaultFeatures.commonSpawns($$3);
/*     */     
/* 804 */     globalOverworldGeneration($$2);
/* 805 */     BiomeDefaultFeatures.addFrozenSprings($$2);
/* 806 */     BiomeDefaultFeatures.addDefaultOres($$2);
/* 807 */     BiomeDefaultFeatures.addDefaultSoftDisks($$2);
/*     */     
/* 809 */     BiomeDefaultFeatures.addExtraEmeralds($$2);
/* 810 */     BiomeDefaultFeatures.addInfestedStone($$2);
/*     */     
/* 812 */     Music $$4 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_JAGGED_PEAKS);
/*     */     
/* 814 */     return biome(true, -0.7F, 0.9F, $$3, $$2, $$4);
/*     */   }
/*     */   
/*     */   public static Biome stonyPeaks(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 818 */     BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 820 */     MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
/* 821 */     BiomeDefaultFeatures.commonSpawns($$3);
/*     */     
/* 823 */     globalOverworldGeneration($$2);
/* 824 */     BiomeDefaultFeatures.addDefaultOres($$2);
/* 825 */     BiomeDefaultFeatures.addDefaultSoftDisks($$2);
/*     */     
/* 827 */     BiomeDefaultFeatures.addExtraEmeralds($$2);
/* 828 */     BiomeDefaultFeatures.addInfestedStone($$2);
/*     */     
/* 830 */     Music $$4 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_STONY_PEAKS);
/*     */     
/* 832 */     return biome(true, 1.0F, 0.3F, $$3, $$2, $$4);
/*     */   }
/*     */   
/*     */   public static Biome snowySlopes(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 836 */     BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 838 */     MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
/* 839 */     $$3.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3))
/* 840 */       .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GOAT, 5, 1, 3));
/* 841 */     BiomeDefaultFeatures.commonSpawns($$3);
/*     */     
/* 843 */     globalOverworldGeneration($$2);
/* 844 */     BiomeDefaultFeatures.addFrozenSprings($$2);
/* 845 */     BiomeDefaultFeatures.addDefaultOres($$2);
/* 846 */     BiomeDefaultFeatures.addDefaultSoftDisks($$2);
/*     */     
/* 848 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$2);
/* 849 */     BiomeDefaultFeatures.addExtraEmeralds($$2);
/* 850 */     BiomeDefaultFeatures.addInfestedStone($$2);
/*     */     
/* 852 */     Music $$4 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_SNOWY_SLOPES);
/*     */     
/* 854 */     return biome(true, -0.3F, 0.9F, $$3, $$2, $$4);
/*     */   }
/*     */   
/*     */   public static Biome grove(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 858 */     BiomeGenerationSettings.Builder $$2 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 860 */     MobSpawnSettings.Builder $$3 = new MobSpawnSettings.Builder();
/* 861 */     BiomeDefaultFeatures.farmAnimals($$3);
/* 862 */     $$3.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4))
/* 863 */       .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3))
/* 864 */       .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
/* 865 */     BiomeDefaultFeatures.commonSpawns($$3);
/*     */     
/* 867 */     globalOverworldGeneration($$2);
/* 868 */     BiomeDefaultFeatures.addFrozenSprings($$2);
/* 869 */     BiomeDefaultFeatures.addDefaultOres($$2);
/* 870 */     BiomeDefaultFeatures.addDefaultSoftDisks($$2);
/*     */     
/* 872 */     BiomeDefaultFeatures.addGroveTrees($$2);
/*     */     
/* 874 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$2);
/* 875 */     BiomeDefaultFeatures.addExtraEmeralds($$2);
/* 876 */     BiomeDefaultFeatures.addInfestedStone($$2);
/*     */     
/* 878 */     Music $$4 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_GROVE);
/*     */     
/* 880 */     return biome(true, -0.2F, 0.8F, $$3, $$2, $$4);
/*     */   }
/*     */   
/*     */   public static Biome lushCaves(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 884 */     MobSpawnSettings.Builder $$2 = new MobSpawnSettings.Builder();
/* 885 */     $$2.addSpawn(MobCategory.AXOLOTLS, new MobSpawnSettings.SpawnerData(EntityType.AXOLOTL, 10, 4, 6));
/* 886 */     $$2.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 8, 8));
/* 887 */     BiomeDefaultFeatures.commonSpawns($$2);
/*     */     
/* 889 */     BiomeGenerationSettings.Builder $$3 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 891 */     globalOverworldGeneration($$3);
/* 892 */     BiomeDefaultFeatures.addPlainGrass($$3);
/*     */     
/* 894 */     BiomeDefaultFeatures.addDefaultOres($$3);
/* 895 */     BiomeDefaultFeatures.addLushCavesSpecialOres($$3);
/* 896 */     BiomeDefaultFeatures.addDefaultSoftDisks($$3);
/*     */     
/* 898 */     BiomeDefaultFeatures.addLushCavesVegetationFeatures($$3);
/*     */     
/* 900 */     Music $$4 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_LUSH_CAVES);
/*     */     
/* 902 */     return biome(true, 0.5F, 0.5F, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static Biome dripstoneCaves(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 906 */     MobSpawnSettings.Builder $$2 = new MobSpawnSettings.Builder();
/* 907 */     BiomeDefaultFeatures.dripstoneCavesSpawns($$2);
/*     */     
/* 909 */     BiomeGenerationSettings.Builder $$3 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */     
/* 911 */     globalOverworldGeneration($$3);
/* 912 */     BiomeDefaultFeatures.addPlainGrass($$3);
/*     */ 
/*     */     
/* 915 */     BiomeDefaultFeatures.addDefaultOres($$3, true);
/* 916 */     BiomeDefaultFeatures.addDefaultSoftDisks($$3);
/* 917 */     BiomeDefaultFeatures.addPlainVegetation($$3);
/* 918 */     BiomeDefaultFeatures.addDefaultMushrooms($$3);
/* 919 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$3);
/*     */     
/* 921 */     BiomeDefaultFeatures.addDripstone($$3);
/*     */     
/* 923 */     Music $$4 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES);
/*     */     
/* 925 */     return biome(true, 0.8F, 0.4F, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static Biome deepDark(HolderGetter<PlacedFeature> $$0, HolderGetter<ConfiguredWorldCarver<?>> $$1) {
/* 929 */     MobSpawnSettings.Builder $$2 = new MobSpawnSettings.Builder();
/*     */     
/* 931 */     BiomeGenerationSettings.Builder $$3 = new BiomeGenerationSettings.Builder($$0, $$1);
/*     */ 
/*     */     
/* 934 */     $$3.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);
/* 935 */     $$3.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND);
/* 936 */     $$3.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
/*     */     
/* 938 */     BiomeDefaultFeatures.addDefaultCrystalFormations($$3);
/* 939 */     BiomeDefaultFeatures.addDefaultMonsterRoom($$3);
/* 940 */     BiomeDefaultFeatures.addDefaultUndergroundVariety($$3);
/* 941 */     BiomeDefaultFeatures.addSurfaceFreezing($$3);
/*     */     
/* 943 */     BiomeDefaultFeatures.addPlainGrass($$3);
/*     */     
/* 945 */     BiomeDefaultFeatures.addDefaultOres($$3);
/* 946 */     BiomeDefaultFeatures.addDefaultSoftDisks($$3);
/* 947 */     BiomeDefaultFeatures.addPlainVegetation($$3);
/* 948 */     BiomeDefaultFeatures.addDefaultMushrooms($$3);
/* 949 */     BiomeDefaultFeatures.addDefaultExtraVegetation($$3);
/*     */     
/* 951 */     BiomeDefaultFeatures.addSculk($$3);
/*     */     
/* 953 */     Music $$4 = Musics.createGameMusic((Holder)SoundEvents.MUSIC_BIOME_DEEP_DARK);
/*     */     
/* 955 */     return biome(true, 0.8F, 0.4F, $$2, $$3, $$4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\biome\OverworldBiomes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */