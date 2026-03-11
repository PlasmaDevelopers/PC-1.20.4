/*    */ package net.minecraft.data.worldgen.biome;
/*    */ 
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.worldgen.BootstapContext;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.biome.Biomes;
/*    */ import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ public abstract class BiomeData {
/*    */   public static void bootstrap(BootstapContext<Biome> $$0) {
/* 13 */     HolderGetter<PlacedFeature> $$1 = $$0.lookup(Registries.PLACED_FEATURE);
/* 14 */     HolderGetter<ConfiguredWorldCarver<?>> $$2 = $$0.lookup(Registries.CONFIGURED_CARVER);
/*    */     
/* 16 */     $$0.register(Biomes.THE_VOID, OverworldBiomes.theVoid($$1, $$2));
/*    */     
/* 18 */     $$0.register(Biomes.PLAINS, OverworldBiomes.plains($$1, $$2, false, false, false));
/* 19 */     $$0.register(Biomes.SUNFLOWER_PLAINS, OverworldBiomes.plains($$1, $$2, true, false, false));
/*    */     
/* 21 */     $$0.register(Biomes.SNOWY_PLAINS, OverworldBiomes.plains($$1, $$2, false, true, false));
/* 22 */     $$0.register(Biomes.ICE_SPIKES, OverworldBiomes.plains($$1, $$2, false, true, true));
/*    */     
/* 24 */     $$0.register(Biomes.DESERT, OverworldBiomes.desert($$1, $$2));
/*    */     
/* 26 */     $$0.register(Biomes.SWAMP, OverworldBiomes.swamp($$1, $$2));
/* 27 */     $$0.register(Biomes.MANGROVE_SWAMP, OverworldBiomes.mangroveSwamp($$1, $$2));
/*    */     
/* 29 */     $$0.register(Biomes.FOREST, OverworldBiomes.forest($$1, $$2, false, false, false));
/* 30 */     $$0.register(Biomes.FLOWER_FOREST, OverworldBiomes.forest($$1, $$2, false, false, true));
/* 31 */     $$0.register(Biomes.BIRCH_FOREST, OverworldBiomes.forest($$1, $$2, true, false, false));
/*    */     
/* 33 */     $$0.register(Biomes.DARK_FOREST, OverworldBiomes.darkForest($$1, $$2));
/* 34 */     $$0.register(Biomes.OLD_GROWTH_BIRCH_FOREST, OverworldBiomes.forest($$1, $$2, true, true, false));
/* 35 */     $$0.register(Biomes.OLD_GROWTH_PINE_TAIGA, OverworldBiomes.oldGrowthTaiga($$1, $$2, false));
/* 36 */     $$0.register(Biomes.OLD_GROWTH_SPRUCE_TAIGA, OverworldBiomes.oldGrowthTaiga($$1, $$2, true));
/*    */     
/* 38 */     $$0.register(Biomes.TAIGA, OverworldBiomes.taiga($$1, $$2, false));
/* 39 */     $$0.register(Biomes.SNOWY_TAIGA, OverworldBiomes.taiga($$1, $$2, true));
/*    */     
/* 41 */     $$0.register(Biomes.SAVANNA, OverworldBiomes.savanna($$1, $$2, false, false));
/* 42 */     $$0.register(Biomes.SAVANNA_PLATEAU, OverworldBiomes.savanna($$1, $$2, false, true));
/*    */     
/* 44 */     $$0.register(Biomes.WINDSWEPT_HILLS, OverworldBiomes.windsweptHills($$1, $$2, false));
/* 45 */     $$0.register(Biomes.WINDSWEPT_GRAVELLY_HILLS, OverworldBiomes.windsweptHills($$1, $$2, false));
/* 46 */     $$0.register(Biomes.WINDSWEPT_FOREST, OverworldBiomes.windsweptHills($$1, $$2, true));
/* 47 */     $$0.register(Biomes.WINDSWEPT_SAVANNA, OverworldBiomes.savanna($$1, $$2, true, false));
/*    */     
/* 49 */     $$0.register(Biomes.JUNGLE, OverworldBiomes.jungle($$1, $$2));
/* 50 */     $$0.register(Biomes.SPARSE_JUNGLE, OverworldBiomes.sparseJungle($$1, $$2));
/* 51 */     $$0.register(Biomes.BAMBOO_JUNGLE, OverworldBiomes.bambooJungle($$1, $$2));
/*    */     
/* 53 */     $$0.register(Biomes.BADLANDS, OverworldBiomes.badlands($$1, $$2, false));
/* 54 */     $$0.register(Biomes.ERODED_BADLANDS, OverworldBiomes.badlands($$1, $$2, false));
/* 55 */     $$0.register(Biomes.WOODED_BADLANDS, OverworldBiomes.badlands($$1, $$2, true));
/*    */     
/* 57 */     $$0.register(Biomes.MEADOW, OverworldBiomes.meadowOrCherryGrove($$1, $$2, false));
/* 58 */     $$0.register(Biomes.CHERRY_GROVE, OverworldBiomes.meadowOrCherryGrove($$1, $$2, true));
/* 59 */     $$0.register(Biomes.GROVE, OverworldBiomes.grove($$1, $$2));
/*    */     
/* 61 */     $$0.register(Biomes.SNOWY_SLOPES, OverworldBiomes.snowySlopes($$1, $$2));
/* 62 */     $$0.register(Biomes.FROZEN_PEAKS, OverworldBiomes.frozenPeaks($$1, $$2));
/* 63 */     $$0.register(Biomes.JAGGED_PEAKS, OverworldBiomes.jaggedPeaks($$1, $$2));
/* 64 */     $$0.register(Biomes.STONY_PEAKS, OverworldBiomes.stonyPeaks($$1, $$2));
/*    */     
/* 66 */     $$0.register(Biomes.RIVER, OverworldBiomes.river($$1, $$2, false));
/* 67 */     $$0.register(Biomes.FROZEN_RIVER, OverworldBiomes.river($$1, $$2, true));
/*    */     
/* 69 */     $$0.register(Biomes.BEACH, OverworldBiomes.beach($$1, $$2, false, false));
/* 70 */     $$0.register(Biomes.SNOWY_BEACH, OverworldBiomes.beach($$1, $$2, true, false));
/* 71 */     $$0.register(Biomes.STONY_SHORE, OverworldBiomes.beach($$1, $$2, false, true));
/*    */     
/* 73 */     $$0.register(Biomes.WARM_OCEAN, OverworldBiomes.warmOcean($$1, $$2));
/* 74 */     $$0.register(Biomes.LUKEWARM_OCEAN, OverworldBiomes.lukeWarmOcean($$1, $$2, false));
/* 75 */     $$0.register(Biomes.DEEP_LUKEWARM_OCEAN, OverworldBiomes.lukeWarmOcean($$1, $$2, true));
/* 76 */     $$0.register(Biomes.OCEAN, OverworldBiomes.ocean($$1, $$2, false));
/* 77 */     $$0.register(Biomes.DEEP_OCEAN, OverworldBiomes.ocean($$1, $$2, true));
/* 78 */     $$0.register(Biomes.COLD_OCEAN, OverworldBiomes.coldOcean($$1, $$2, false));
/* 79 */     $$0.register(Biomes.DEEP_COLD_OCEAN, OverworldBiomes.coldOcean($$1, $$2, true));
/* 80 */     $$0.register(Biomes.FROZEN_OCEAN, OverworldBiomes.frozenOcean($$1, $$2, false));
/* 81 */     $$0.register(Biomes.DEEP_FROZEN_OCEAN, OverworldBiomes.frozenOcean($$1, $$2, true));
/*    */     
/* 83 */     $$0.register(Biomes.MUSHROOM_FIELDS, OverworldBiomes.mushroomFields($$1, $$2));
/*    */     
/* 85 */     $$0.register(Biomes.DRIPSTONE_CAVES, OverworldBiomes.dripstoneCaves($$1, $$2));
/* 86 */     $$0.register(Biomes.LUSH_CAVES, OverworldBiomes.lushCaves($$1, $$2));
/* 87 */     $$0.register(Biomes.DEEP_DARK, OverworldBiomes.deepDark($$1, $$2));
/*    */     
/* 89 */     $$0.register(Biomes.NETHER_WASTES, NetherBiomes.netherWastes($$1, $$2));
/* 90 */     $$0.register(Biomes.WARPED_FOREST, NetherBiomes.warpedForest($$1, $$2));
/* 91 */     $$0.register(Biomes.CRIMSON_FOREST, NetherBiomes.crimsonForest($$1, $$2));
/* 92 */     $$0.register(Biomes.SOUL_SAND_VALLEY, NetherBiomes.soulSandValley($$1, $$2));
/* 93 */     $$0.register(Biomes.BASALT_DELTAS, NetherBiomes.basaltDeltas($$1, $$2));
/*    */     
/* 95 */     $$0.register(Biomes.THE_END, EndBiomes.theEnd($$1, $$2));
/* 96 */     $$0.register(Biomes.END_HIGHLANDS, EndBiomes.endHighlands($$1, $$2));
/* 97 */     $$0.register(Biomes.END_MIDLANDS, EndBiomes.endMidlands($$1, $$2));
/* 98 */     $$0.register(Biomes.SMALL_END_ISLANDS, EndBiomes.smallEndIslands($$1, $$2));
/* 99 */     $$0.register(Biomes.END_BARRENS, EndBiomes.endBarrens($$1, $$2));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\biome\BiomeData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */