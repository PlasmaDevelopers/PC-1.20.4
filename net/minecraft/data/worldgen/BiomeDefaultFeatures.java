/*     */ package net.minecraft.data.worldgen;
/*     */ 
/*     */ import net.minecraft.data.worldgen.placement.AquaticPlacements;
/*     */ import net.minecraft.data.worldgen.placement.CavePlacements;
/*     */ import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
/*     */ import net.minecraft.data.worldgen.placement.OrePlacements;
/*     */ import net.minecraft.data.worldgen.placement.VegetationPlacements;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobCategory;
/*     */ import net.minecraft.world.level.biome.BiomeGenerationSettings;
/*     */ import net.minecraft.world.level.biome.MobSpawnSettings;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ 
/*     */ public class BiomeDefaultFeatures {
/*     */   public static void addDefaultCarversAndLakes(BiomeGenerationSettings.Builder $$0) {
/*  16 */     $$0.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);
/*  17 */     $$0.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND);
/*  18 */     $$0.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
/*  19 */     $$0.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_UNDERGROUND);
/*  20 */     $$0.addFeature(GenerationStep.Decoration.LAKES, MiscOverworldPlacements.LAKE_LAVA_SURFACE);
/*     */   }
/*     */   
/*     */   public static void addDefaultMonsterRoom(BiomeGenerationSettings.Builder $$0) {
/*  24 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM);
/*  25 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
/*     */   }
/*     */   
/*     */   public static void addDefaultUndergroundVariety(BiomeGenerationSettings.Builder $$0) {
/*  29 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIRT);
/*  30 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRAVEL);
/*  31 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_UPPER);
/*  32 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRANITE_LOWER);
/*  33 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_UPPER);
/*  34 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_LOWER);
/*  35 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_UPPER);
/*  36 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_LOWER);
/*  37 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_TUFF);
/*  38 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
/*     */   }
/*     */   
/*     */   public static void addDripstone(BiomeGenerationSettings.Builder $$0) {
/*  42 */     $$0.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, CavePlacements.LARGE_DRIPSTONE);
/*  43 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.DRIPSTONE_CLUSTER);
/*  44 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.POINTED_DRIPSTONE);
/*     */   }
/*     */   
/*     */   public static void addSculk(BiomeGenerationSettings.Builder $$0) {
/*  48 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.SCULK_VEIN);
/*  49 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.SCULK_PATCH_DEEP_DARK);
/*     */   }
/*     */   
/*     */   public static void addDefaultOres(BiomeGenerationSettings.Builder $$0) {
/*  53 */     addDefaultOres($$0, false);
/*     */   }
/*     */   
/*     */   public static void addDefaultOres(BiomeGenerationSettings.Builder $$0, boolean $$1) {
/*  57 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COAL_UPPER);
/*  58 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_COAL_LOWER);
/*     */     
/*  60 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_IRON_UPPER);
/*  61 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_IRON_MIDDLE);
/*  62 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_IRON_SMALL);
/*     */     
/*  64 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD);
/*  65 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_LOWER);
/*     */     
/*  67 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_REDSTONE);
/*  68 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_REDSTONE_LOWER);
/*     */     
/*  70 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND);
/*  71 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_MEDIUM);
/*  72 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_LARGE);
/*  73 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIAMOND_BURIED);
/*     */     
/*  75 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LAPIS);
/*  76 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_LAPIS_BURIED);
/*     */     
/*  78 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, $$1 ? OrePlacements.ORE_COPPER_LARGE : OrePlacements.ORE_COPPER);
/*     */     
/*  80 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, CavePlacements.UNDERWATER_MAGMA);
/*     */   }
/*     */   
/*     */   public static void addExtraGold(BiomeGenerationSettings.Builder $$0) {
/*  84 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GOLD_EXTRA);
/*     */   }
/*     */   
/*     */   public static void addExtraEmeralds(BiomeGenerationSettings.Builder $$0) {
/*  88 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_EMERALD);
/*     */   }
/*     */   
/*     */   public static void addInfestedStone(BiomeGenerationSettings.Builder $$0) {
/*  92 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_INFESTED);
/*     */   }
/*     */   
/*     */   public static void addDefaultSoftDisks(BiomeGenerationSettings.Builder $$0) {
/*  96 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_SAND);
/*  97 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
/*  98 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_GRAVEL);
/*     */   }
/*     */   
/*     */   public static void addSwampClayDisk(BiomeGenerationSettings.Builder $$0) {
/* 102 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
/*     */   }
/*     */   
/*     */   public static void addMangroveSwampDisks(BiomeGenerationSettings.Builder $$0) {
/* 106 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_GRASS);
/* 107 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
/*     */   }
/*     */   
/*     */   public static void addMossyStoneBlock(BiomeGenerationSettings.Builder $$0) {
/* 111 */     $$0.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MiscOverworldPlacements.FOREST_ROCK);
/*     */   }
/*     */   
/*     */   public static void addFerns(BiomeGenerationSettings.Builder $$0) {
/* 115 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_LARGE_FERN);
/*     */   }
/*     */   
/*     */   public static void addRareBerryBushes(BiomeGenerationSettings.Builder $$0) {
/* 119 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_BERRY_RARE);
/*     */   }
/*     */   
/*     */   public static void addCommonBerryBushes(BiomeGenerationSettings.Builder $$0) {
/* 123 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_BERRY_COMMON);
/*     */   }
/*     */   
/*     */   public static void addLightBambooVegetation(BiomeGenerationSettings.Builder $$0) {
/* 127 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BAMBOO_LIGHT);
/*     */   }
/*     */   
/*     */   public static void addBambooVegetation(BiomeGenerationSettings.Builder $$0) {
/* 131 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BAMBOO);
/* 132 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BAMBOO_VEGETATION);
/*     */   }
/*     */   
/*     */   public static void addTaigaTrees(BiomeGenerationSettings.Builder $$0) {
/* 136 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_TAIGA);
/*     */   }
/*     */   
/*     */   public static void addGroveTrees(BiomeGenerationSettings.Builder $$0) {
/* 140 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_GROVE);
/*     */   }
/*     */   
/*     */   public static void addWaterTrees(BiomeGenerationSettings.Builder $$0) {
/* 144 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_WATER);
/*     */   }
/*     */   
/*     */   public static void addBirchTrees(BiomeGenerationSettings.Builder $$0) {
/* 148 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_BIRCH);
/*     */   }
/*     */   
/*     */   public static void addOtherBirchTrees(BiomeGenerationSettings.Builder $$0) {
/* 152 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_BIRCH_AND_OAK);
/*     */   }
/*     */   
/*     */   public static void addTallBirchTrees(BiomeGenerationSettings.Builder $$0) {
/* 156 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BIRCH_TALL);
/*     */   }
/*     */   
/*     */   public static void addSavannaTrees(BiomeGenerationSettings.Builder $$0) {
/* 160 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_SAVANNA);
/*     */   }
/*     */   
/*     */   public static void addShatteredSavannaTrees(BiomeGenerationSettings.Builder $$0) {
/* 164 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_WINDSWEPT_SAVANNA);
/*     */   }
/*     */   
/*     */   public static void addLushCavesVegetationFeatures(BiomeGenerationSettings.Builder $$0) {
/* 168 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.LUSH_CAVES_CEILING_VEGETATION);
/* 169 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.CAVE_VINES);
/* 170 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.LUSH_CAVES_CLAY);
/* 171 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.LUSH_CAVES_VEGETATION);
/* 172 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.ROOTED_AZALEA_TREE);
/* 173 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.SPORE_BLOSSOM);
/* 174 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.CLASSIC_VINES);
/*     */   }
/*     */   
/*     */   public static void addLushCavesSpecialOres(BiomeGenerationSettings.Builder $$0) {
/* 178 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_CLAY);
/*     */   }
/*     */   
/*     */   public static void addMountainTrees(BiomeGenerationSettings.Builder $$0) {
/* 182 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_WINDSWEPT_HILLS);
/*     */   }
/*     */   
/*     */   public static void addMountainForestTrees(BiomeGenerationSettings.Builder $$0) {
/* 186 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_WINDSWEPT_FOREST);
/*     */   }
/*     */   
/*     */   public static void addJungleTrees(BiomeGenerationSettings.Builder $$0) {
/* 190 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_JUNGLE);
/*     */   }
/*     */   
/*     */   public static void addSparseJungleTrees(BiomeGenerationSettings.Builder $$0) {
/* 194 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_SPARSE_JUNGLE);
/*     */   }
/*     */   
/*     */   public static void addBadlandsTrees(BiomeGenerationSettings.Builder $$0) {
/* 198 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_BADLANDS);
/*     */   }
/*     */   
/*     */   public static void addSnowyTrees(BiomeGenerationSettings.Builder $$0) {
/* 202 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_SNOWY);
/*     */   }
/*     */   
/*     */   public static void addJungleGrass(BiomeGenerationSettings.Builder $$0) {
/* 206 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_JUNGLE);
/*     */   }
/*     */   
/*     */   public static void addSavannaGrass(BiomeGenerationSettings.Builder $$0) {
/* 210 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS);
/*     */   }
/*     */   
/*     */   public static void addShatteredSavannaGrass(BiomeGenerationSettings.Builder $$0) {
/* 214 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
/*     */   }
/*     */   
/*     */   public static void addSavannaExtraGrass(BiomeGenerationSettings.Builder $$0) {
/* 218 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_SAVANNA);
/*     */   }
/*     */   
/*     */   public static void addBadlandGrass(BiomeGenerationSettings.Builder $$0) {
/* 222 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_BADLANDS);
/* 223 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH_BADLANDS);
/*     */   }
/*     */   
/*     */   public static void addForestFlowers(BiomeGenerationSettings.Builder $$0) {
/* 227 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FOREST_FLOWERS);
/*     */   }
/*     */   
/*     */   public static void addForestGrass(BiomeGenerationSettings.Builder $$0) {
/* 231 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_FOREST);
/*     */   }
/*     */   
/*     */   public static void addSwampVegetation(BiomeGenerationSettings.Builder $$0) {
/* 235 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_SWAMP);
/* 236 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_SWAMP);
/* 237 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
/* 238 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
/* 239 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
/* 240 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_SWAMP);
/* 241 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_SWAMP);
/*     */   }
/*     */   
/*     */   public static void addMangroveSwampVegetation(BiomeGenerationSettings.Builder $$0) {
/* 245 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_MANGROVE);
/* 246 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
/* 247 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
/* 248 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
/*     */   }
/*     */   
/*     */   public static void addMushroomFieldVegetation(BiomeGenerationSettings.Builder $$0) {
/* 252 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.MUSHROOM_ISLAND_VEGETATION);
/* 253 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_TAIGA);
/* 254 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_TAIGA);
/*     */   }
/*     */   
/*     */   public static void addPlainVegetation(BiomeGenerationSettings.Builder $$0) {
/* 258 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);
/* 259 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_PLAINS);
/* 260 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
/*     */   }
/*     */   
/*     */   public static void addDesertVegetation(BiomeGenerationSettings.Builder $$0) {
/* 264 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH_2);
/*     */   }
/*     */   
/*     */   public static void addGiantTaigaVegetation(BiomeGenerationSettings.Builder $$0) {
/* 268 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA);
/* 269 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
/* 270 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_OLD_GROWTH);
/* 271 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_OLD_GROWTH);
/*     */   }
/*     */   
/*     */   public static void addDefaultFlowers(BiomeGenerationSettings.Builder $$0) {
/* 275 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_DEFAULT);
/*     */   }
/*     */   
/*     */   public static void addCherryGroveVegetation(BiomeGenerationSettings.Builder $$0) {
/* 279 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
/* 280 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_CHERRY);
/* 281 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_CHERRY);
/*     */   }
/*     */   
/*     */   public static void addMeadowVegetation(BiomeGenerationSettings.Builder $$0) {
/* 285 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
/* 286 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_MEADOW);
/* 287 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_MEADOW);
/*     */   }
/*     */   
/*     */   public static void addWarmFlowers(BiomeGenerationSettings.Builder $$0) {
/* 291 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_WARM);
/*     */   }
/*     */   
/*     */   public static void addDefaultGrass(BiomeGenerationSettings.Builder $$0) {
/* 295 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_BADLANDS);
/*     */   }
/*     */   
/*     */   public static void addTaigaGrass(BiomeGenerationSettings.Builder $$0) {
/* 299 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA_2);
/* 300 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_TAIGA);
/* 301 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_TAIGA);
/*     */   }
/*     */   
/*     */   public static void addPlainGrass(BiomeGenerationSettings.Builder $$0) {
/* 305 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS_2);
/*     */   }
/*     */   
/*     */   public static void addDefaultMushrooms(BiomeGenerationSettings.Builder $$0) {
/* 309 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_NORMAL);
/* 310 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_NORMAL);
/*     */   }
/*     */   
/*     */   public static void addDefaultExtraVegetation(BiomeGenerationSettings.Builder $$0) {
/* 314 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE);
/* 315 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
/*     */   }
/*     */   
/*     */   public static void addBadlandExtraVegetation(BiomeGenerationSettings.Builder $$0) {
/* 319 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_BADLANDS);
/* 320 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
/* 321 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_CACTUS_DECORATED);
/*     */   }
/*     */   
/*     */   public static void addJungleMelons(BiomeGenerationSettings.Builder $$0) {
/* 325 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_MELON);
/*     */   }
/*     */   
/*     */   public static void addSparseJungleMelons(BiomeGenerationSettings.Builder $$0) {
/* 329 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_MELON_SPARSE);
/*     */   }
/*     */   
/*     */   public static void addJungleVines(BiomeGenerationSettings.Builder $$0) {
/* 333 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.VINES);
/*     */   }
/*     */   
/*     */   public static void addDesertExtraVegetation(BiomeGenerationSettings.Builder $$0) {
/* 337 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_DESERT);
/* 338 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
/* 339 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_CACTUS_DESERT);
/*     */   }
/*     */   
/*     */   public static void addSwampExtraVegetation(BiomeGenerationSettings.Builder $$0) {
/* 343 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
/* 344 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
/*     */   }
/*     */   
/*     */   public static void addDesertExtraDecoration(BiomeGenerationSettings.Builder $$0) {
/* 348 */     $$0.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.DESERT_WELL);
/*     */   }
/*     */   
/*     */   public static void addFossilDecoration(BiomeGenerationSettings.Builder $$0) {
/* 352 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.FOSSIL_UPPER);
/* 353 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.FOSSIL_LOWER);
/*     */   }
/*     */   
/*     */   public static void addColdOceanExtraVegetation(BiomeGenerationSettings.Builder $$0) {
/* 357 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.KELP_COLD);
/*     */   }
/*     */   
/*     */   public static void addDefaultSeagrass(BiomeGenerationSettings.Builder $$0) {
/* 361 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SIMPLE);
/*     */   }
/*     */   
/*     */   public static void addLukeWarmKelp(BiomeGenerationSettings.Builder $$0) {
/* 365 */     $$0.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.KELP_WARM);
/*     */   }
/*     */   
/*     */   public static void addDefaultSprings(BiomeGenerationSettings.Builder $$0) {
/* 369 */     $$0.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_WATER);
/* 370 */     $$0.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_LAVA);
/*     */   }
/*     */   
/*     */   public static void addFrozenSprings(BiomeGenerationSettings.Builder $$0) {
/* 374 */     $$0.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, MiscOverworldPlacements.SPRING_LAVA_FROZEN);
/*     */   }
/*     */   
/*     */   public static void addIcebergs(BiomeGenerationSettings.Builder $$0) {
/* 378 */     $$0.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MiscOverworldPlacements.ICEBERG_PACKED);
/* 379 */     $$0.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MiscOverworldPlacements.ICEBERG_BLUE);
/*     */   }
/*     */   
/*     */   public static void addBlueIce(BiomeGenerationSettings.Builder $$0) {
/* 383 */     $$0.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.BLUE_ICE);
/*     */   }
/*     */   
/*     */   public static void addSurfaceFreezing(BiomeGenerationSettings.Builder $$0) {
/* 387 */     $$0.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);
/*     */   }
/*     */   
/*     */   public static void addNetherDefaultOres(BiomeGenerationSettings.Builder $$0) {
/* 391 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_GRAVEL_NETHER);
/* 392 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_BLACKSTONE);
/*     */     
/* 394 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_GOLD_NETHER);
/* 395 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_QUARTZ_NETHER);
/* 396 */     addAncientDebris($$0);
/*     */   }
/*     */   
/*     */   public static void addAncientDebris(BiomeGenerationSettings.Builder $$0) {
/* 400 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_ANCIENT_DEBRIS_LARGE);
/* 401 */     $$0.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_ANCIENT_DEBRIS_SMALL);
/*     */   }
/*     */   
/*     */   public static void addDefaultCrystalFormations(BiomeGenerationSettings.Builder $$0) {
/* 405 */     $$0.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, CavePlacements.AMETHYST_GEODE);
/*     */   }
/*     */   
/*     */   public static void farmAnimals(MobSpawnSettings.Builder $$0) {
/* 409 */     $$0.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4));
/* 410 */     $$0.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 10, 4, 4));
/* 411 */     $$0.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
/* 412 */     $$0.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 8, 4, 4));
/*     */   }
/*     */   
/*     */   public static void caveSpawns(MobSpawnSettings.Builder $$0) {
/* 416 */     $$0.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
/* 417 */     $$0.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 4, 6));
/*     */   }
/*     */   
/*     */   public static void commonSpawns(MobSpawnSettings.Builder $$0) {
/* 421 */     caveSpawns($$0);
/* 422 */     monsters($$0, 95, 5, 100, false);
/*     */   }
/*     */   
/*     */   public static void oceanSpawns(MobSpawnSettings.Builder $$0, int $$1, int $$2, int $$3) {
/* 426 */     $$0.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, $$1, 1, $$2));
/* 427 */     $$0.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, $$3, 3, 6));
/* 428 */     commonSpawns($$0);
/* 429 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 5, 1, 1));
/*     */   }
/*     */   
/*     */   public static void warmOceanSpawns(MobSpawnSettings.Builder $$0, int $$1, int $$2) {
/* 433 */     $$0.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, $$1, $$2, 4));
/* 434 */     $$0.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 8, 8));
/* 435 */     $$0.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 2, 1, 2));
/* 436 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 5, 1, 1));
/* 437 */     commonSpawns($$0);
/*     */   }
/*     */   
/*     */   public static void plainsSpawns(MobSpawnSettings.Builder $$0) {
/* 441 */     farmAnimals($$0);
/* 442 */     $$0.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 5, 2, 6));
/* 443 */     $$0.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 1, 3));
/* 444 */     commonSpawns($$0);
/*     */   }
/*     */   
/*     */   public static void snowySpawns(MobSpawnSettings.Builder $$0) {
/* 448 */     $$0.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 2, 3));
/* 449 */     $$0.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 1, 1, 2));
/* 450 */     caveSpawns($$0);
/* 451 */     monsters($$0, 95, 5, 20, false);
/* 452 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 80, 4, 4));
/*     */   }
/*     */   
/*     */   public static void desertSpawns(MobSpawnSettings.Builder $$0) {
/* 456 */     $$0.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
/* 457 */     caveSpawns($$0);
/* 458 */     monsters($$0, 19, 1, 100, false);
/* 459 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HUSK, 80, 4, 4));
/*     */   }
/*     */   
/*     */   public static void dripstoneCavesSpawns(MobSpawnSettings.Builder $$0) {
/* 463 */     caveSpawns($$0);
/* 464 */     int $$1 = 95;
/* 465 */     monsters($$0, 95, 5, 100, false);
/* 466 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 95, 4, 4));
/*     */   }
/*     */   
/*     */   public static void monsters(MobSpawnSettings.Builder $$0, int $$1, int $$2, int $$3, boolean $$4) {
/* 470 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 4, 4));
/* 471 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData($$4 ? EntityType.DROWNED : EntityType.ZOMBIE, $$1, 4, 4));
/* 472 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, $$2, 1, 1));
/* 473 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, $$3, 4, 4));
/* 474 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 100, 4, 4));
/* 475 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 100, 4, 4));
/* 476 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4));
/* 477 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 5, 1, 1));
/*     */   }
/*     */   
/*     */   public static void mooshroomSpawns(MobSpawnSettings.Builder $$0) {
/* 481 */     $$0.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.MOOSHROOM, 8, 4, 8));
/* 482 */     caveSpawns($$0);
/*     */   }
/*     */   
/*     */   public static void baseJungleSpawns(MobSpawnSettings.Builder $$0) {
/* 486 */     farmAnimals($$0);
/* 487 */     $$0.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
/* 488 */     commonSpawns($$0);
/*     */   }
/*     */   
/*     */   public static void endSpawns(MobSpawnSettings.Builder $$0) {
/* 492 */     $$0.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 4, 4));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\BiomeDefaultFeatures.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */