/*    */ package net.minecraft.world.level.biome;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Biomes
/*    */ {
/* 13 */   public static final ResourceKey<Biome> THE_VOID = register("the_void");
/*    */   
/* 15 */   public static final ResourceKey<Biome> PLAINS = register("plains");
/* 16 */   public static final ResourceKey<Biome> SUNFLOWER_PLAINS = register("sunflower_plains");
/*    */   
/* 18 */   public static final ResourceKey<Biome> SNOWY_PLAINS = register("snowy_plains");
/* 19 */   public static final ResourceKey<Biome> ICE_SPIKES = register("ice_spikes");
/*    */   
/* 21 */   public static final ResourceKey<Biome> DESERT = register("desert");
/*    */   
/* 23 */   public static final ResourceKey<Biome> SWAMP = register("swamp");
/* 24 */   public static final ResourceKey<Biome> MANGROVE_SWAMP = register("mangrove_swamp");
/*    */   
/* 26 */   public static final ResourceKey<Biome> FOREST = register("forest");
/* 27 */   public static final ResourceKey<Biome> FLOWER_FOREST = register("flower_forest");
/* 28 */   public static final ResourceKey<Biome> BIRCH_FOREST = register("birch_forest");
/*    */   
/* 30 */   public static final ResourceKey<Biome> DARK_FOREST = register("dark_forest");
/* 31 */   public static final ResourceKey<Biome> OLD_GROWTH_BIRCH_FOREST = register("old_growth_birch_forest");
/* 32 */   public static final ResourceKey<Biome> OLD_GROWTH_PINE_TAIGA = register("old_growth_pine_taiga");
/* 33 */   public static final ResourceKey<Biome> OLD_GROWTH_SPRUCE_TAIGA = register("old_growth_spruce_taiga");
/*    */   
/* 35 */   public static final ResourceKey<Biome> TAIGA = register("taiga");
/* 36 */   public static final ResourceKey<Biome> SNOWY_TAIGA = register("snowy_taiga");
/*    */   
/* 38 */   public static final ResourceKey<Biome> SAVANNA = register("savanna");
/* 39 */   public static final ResourceKey<Biome> SAVANNA_PLATEAU = register("savanna_plateau");
/*    */   
/* 41 */   public static final ResourceKey<Biome> WINDSWEPT_HILLS = register("windswept_hills");
/* 42 */   public static final ResourceKey<Biome> WINDSWEPT_GRAVELLY_HILLS = register("windswept_gravelly_hills");
/* 43 */   public static final ResourceKey<Biome> WINDSWEPT_FOREST = register("windswept_forest");
/* 44 */   public static final ResourceKey<Biome> WINDSWEPT_SAVANNA = register("windswept_savanna");
/*    */   
/* 46 */   public static final ResourceKey<Biome> JUNGLE = register("jungle");
/* 47 */   public static final ResourceKey<Biome> SPARSE_JUNGLE = register("sparse_jungle");
/* 48 */   public static final ResourceKey<Biome> BAMBOO_JUNGLE = register("bamboo_jungle");
/*    */   
/* 50 */   public static final ResourceKey<Biome> BADLANDS = register("badlands");
/* 51 */   public static final ResourceKey<Biome> ERODED_BADLANDS = register("eroded_badlands");
/* 52 */   public static final ResourceKey<Biome> WOODED_BADLANDS = register("wooded_badlands");
/*    */   
/* 54 */   public static final ResourceKey<Biome> MEADOW = register("meadow");
/* 55 */   public static final ResourceKey<Biome> CHERRY_GROVE = register("cherry_grove");
/* 56 */   public static final ResourceKey<Biome> GROVE = register("grove");
/*    */   
/* 58 */   public static final ResourceKey<Biome> SNOWY_SLOPES = register("snowy_slopes");
/* 59 */   public static final ResourceKey<Biome> FROZEN_PEAKS = register("frozen_peaks");
/* 60 */   public static final ResourceKey<Biome> JAGGED_PEAKS = register("jagged_peaks");
/* 61 */   public static final ResourceKey<Biome> STONY_PEAKS = register("stony_peaks");
/*    */   
/* 63 */   public static final ResourceKey<Biome> RIVER = register("river");
/* 64 */   public static final ResourceKey<Biome> FROZEN_RIVER = register("frozen_river");
/*    */   
/* 66 */   public static final ResourceKey<Biome> BEACH = register("beach");
/* 67 */   public static final ResourceKey<Biome> SNOWY_BEACH = register("snowy_beach");
/* 68 */   public static final ResourceKey<Biome> STONY_SHORE = register("stony_shore");
/*    */   
/* 70 */   public static final ResourceKey<Biome> WARM_OCEAN = register("warm_ocean");
/* 71 */   public static final ResourceKey<Biome> LUKEWARM_OCEAN = register("lukewarm_ocean");
/* 72 */   public static final ResourceKey<Biome> DEEP_LUKEWARM_OCEAN = register("deep_lukewarm_ocean");
/* 73 */   public static final ResourceKey<Biome> OCEAN = register("ocean");
/* 74 */   public static final ResourceKey<Biome> DEEP_OCEAN = register("deep_ocean");
/* 75 */   public static final ResourceKey<Biome> COLD_OCEAN = register("cold_ocean");
/* 76 */   public static final ResourceKey<Biome> DEEP_COLD_OCEAN = register("deep_cold_ocean");
/* 77 */   public static final ResourceKey<Biome> FROZEN_OCEAN = register("frozen_ocean");
/* 78 */   public static final ResourceKey<Biome> DEEP_FROZEN_OCEAN = register("deep_frozen_ocean");
/*    */   
/* 80 */   public static final ResourceKey<Biome> MUSHROOM_FIELDS = register("mushroom_fields");
/*    */   
/* 82 */   public static final ResourceKey<Biome> DRIPSTONE_CAVES = register("dripstone_caves");
/* 83 */   public static final ResourceKey<Biome> LUSH_CAVES = register("lush_caves");
/* 84 */   public static final ResourceKey<Biome> DEEP_DARK = register("deep_dark");
/*    */   
/* 86 */   public static final ResourceKey<Biome> NETHER_WASTES = register("nether_wastes");
/* 87 */   public static final ResourceKey<Biome> WARPED_FOREST = register("warped_forest");
/* 88 */   public static final ResourceKey<Biome> CRIMSON_FOREST = register("crimson_forest");
/* 89 */   public static final ResourceKey<Biome> SOUL_SAND_VALLEY = register("soul_sand_valley");
/* 90 */   public static final ResourceKey<Biome> BASALT_DELTAS = register("basalt_deltas");
/*    */   
/* 92 */   public static final ResourceKey<Biome> THE_END = register("the_end");
/* 93 */   public static final ResourceKey<Biome> END_HIGHLANDS = register("end_highlands");
/* 94 */   public static final ResourceKey<Biome> END_MIDLANDS = register("end_midlands");
/* 95 */   public static final ResourceKey<Biome> SMALL_END_ISLANDS = register("small_end_islands");
/* 96 */   public static final ResourceKey<Biome> END_BARRENS = register("end_barrens");
/*    */   
/*    */   private static ResourceKey<Biome> register(String $$0) {
/* 99 */     return ResourceKey.create(Registries.BIOME, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\Biomes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */