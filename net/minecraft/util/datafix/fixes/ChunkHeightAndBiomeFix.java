/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFix;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.OpticFinder;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.OptionalDynamic;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntFunction;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.BitSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.LongStream;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ 
/*     */ public class ChunkHeightAndBiomeFix
/*     */   extends DataFix
/*     */ {
/*     */   public static final String DATAFIXER_CONTEXT_TAG = "__context";
/*     */   private static final String NAME = "ChunkHeightAndBiomeFix";
/*     */   private static final int OLD_SECTION_COUNT = 16;
/*     */   private static final int NEW_SECTION_COUNT = 24;
/*     */   private static final int NEW_MIN_SECTION_Y = -4;
/*     */   public static final int BLOCKS_PER_SECTION = 4096;
/*     */   private static final int LONGS_PER_SECTION = 64;
/*     */   private static final int HEIGHTMAP_BITS = 9;
/*     */   private static final long HEIGHTMAP_MASK = 511L;
/*     */   private static final int HEIGHTMAP_OFFSET = 64;
/*  53 */   private static final String[] HEIGHTMAP_TYPES = new String[] { "WORLD_SURFACE_WG", "WORLD_SURFACE", "WORLD_SURFACE_IGNORE_SNOW", "OCEAN_FLOOR_WG", "OCEAN_FLOOR", "MOTION_BLOCKING", "MOTION_BLOCKING_NO_LEAVES" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private static final Set<String> STATUS_IS_OR_AFTER_SURFACE = Set.of("surface", "carvers", "liquid_carvers", "features", "light", "spawn", "heightmaps", "full");
/*  64 */   private static final Set<String> STATUS_IS_OR_AFTER_NOISE = Set.of("noise", "surface", "carvers", "liquid_carvers", "features", "light", "spawn", "heightmaps", "full");
/*     */   
/*  66 */   private static final Set<String> BLOCKS_BEFORE_FEATURE_STATUS = Set.of(new String[] { "minecraft:air", "minecraft:basalt", "minecraft:bedrock", "minecraft:blackstone", "minecraft:calcite", "minecraft:cave_air", "minecraft:coarse_dirt", "minecraft:crimson_nylium", "minecraft:dirt", "minecraft:end_stone", "minecraft:grass_block", "minecraft:gravel", "minecraft:ice", "minecraft:lava", "minecraft:mycelium", "minecraft:nether_wart_block", "minecraft:netherrack", "minecraft:orange_terracotta", "minecraft:packed_ice", "minecraft:podzol", "minecraft:powder_snow", "minecraft:red_sand", "minecraft:red_sandstone", "minecraft:sand", "minecraft:sandstone", "minecraft:snow_block", "minecraft:soul_sand", "minecraft:soul_soil", "minecraft:stone", "minecraft:terracotta", "minecraft:warped_nylium", "minecraft:warped_wart_block", "minecraft:water", "minecraft:white_terracotta" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int BIOME_CONTAINER_LAYER_SIZE = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int BIOME_CONTAINER_SIZE = 64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int BIOME_CONTAINER_TOP_LAYER_OFFSET = 1008;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String DEFAULT_BIOME = "minecraft:plains";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private static final Int2ObjectMap<String> BIOMES_BY_ID = (Int2ObjectMap<String>)new Int2ObjectOpenHashMap();
/*     */   
/*     */   static {
/* 111 */     BIOMES_BY_ID.put(0, "minecraft:ocean");
/* 112 */     BIOMES_BY_ID.put(1, "minecraft:plains");
/* 113 */     BIOMES_BY_ID.put(2, "minecraft:desert");
/* 114 */     BIOMES_BY_ID.put(3, "minecraft:mountains");
/* 115 */     BIOMES_BY_ID.put(4, "minecraft:forest");
/* 116 */     BIOMES_BY_ID.put(5, "minecraft:taiga");
/* 117 */     BIOMES_BY_ID.put(6, "minecraft:swamp");
/* 118 */     BIOMES_BY_ID.put(7, "minecraft:river");
/* 119 */     BIOMES_BY_ID.put(8, "minecraft:nether_wastes");
/* 120 */     BIOMES_BY_ID.put(9, "minecraft:the_end");
/* 121 */     BIOMES_BY_ID.put(10, "minecraft:frozen_ocean");
/* 122 */     BIOMES_BY_ID.put(11, "minecraft:frozen_river");
/* 123 */     BIOMES_BY_ID.put(12, "minecraft:snowy_tundra");
/* 124 */     BIOMES_BY_ID.put(13, "minecraft:snowy_mountains");
/* 125 */     BIOMES_BY_ID.put(14, "minecraft:mushroom_fields");
/* 126 */     BIOMES_BY_ID.put(15, "minecraft:mushroom_field_shore");
/* 127 */     BIOMES_BY_ID.put(16, "minecraft:beach");
/* 128 */     BIOMES_BY_ID.put(17, "minecraft:desert_hills");
/* 129 */     BIOMES_BY_ID.put(18, "minecraft:wooded_hills");
/* 130 */     BIOMES_BY_ID.put(19, "minecraft:taiga_hills");
/* 131 */     BIOMES_BY_ID.put(20, "minecraft:mountain_edge");
/* 132 */     BIOMES_BY_ID.put(21, "minecraft:jungle");
/* 133 */     BIOMES_BY_ID.put(22, "minecraft:jungle_hills");
/* 134 */     BIOMES_BY_ID.put(23, "minecraft:jungle_edge");
/* 135 */     BIOMES_BY_ID.put(24, "minecraft:deep_ocean");
/* 136 */     BIOMES_BY_ID.put(25, "minecraft:stone_shore");
/* 137 */     BIOMES_BY_ID.put(26, "minecraft:snowy_beach");
/* 138 */     BIOMES_BY_ID.put(27, "minecraft:birch_forest");
/* 139 */     BIOMES_BY_ID.put(28, "minecraft:birch_forest_hills");
/* 140 */     BIOMES_BY_ID.put(29, "minecraft:dark_forest");
/* 141 */     BIOMES_BY_ID.put(30, "minecraft:snowy_taiga");
/* 142 */     BIOMES_BY_ID.put(31, "minecraft:snowy_taiga_hills");
/* 143 */     BIOMES_BY_ID.put(32, "minecraft:giant_tree_taiga");
/* 144 */     BIOMES_BY_ID.put(33, "minecraft:giant_tree_taiga_hills");
/* 145 */     BIOMES_BY_ID.put(34, "minecraft:wooded_mountains");
/* 146 */     BIOMES_BY_ID.put(35, "minecraft:savanna");
/* 147 */     BIOMES_BY_ID.put(36, "minecraft:savanna_plateau");
/* 148 */     BIOMES_BY_ID.put(37, "minecraft:badlands");
/* 149 */     BIOMES_BY_ID.put(38, "minecraft:wooded_badlands_plateau");
/* 150 */     BIOMES_BY_ID.put(39, "minecraft:badlands_plateau");
/* 151 */     BIOMES_BY_ID.put(40, "minecraft:small_end_islands");
/* 152 */     BIOMES_BY_ID.put(41, "minecraft:end_midlands");
/* 153 */     BIOMES_BY_ID.put(42, "minecraft:end_highlands");
/* 154 */     BIOMES_BY_ID.put(43, "minecraft:end_barrens");
/* 155 */     BIOMES_BY_ID.put(44, "minecraft:warm_ocean");
/* 156 */     BIOMES_BY_ID.put(45, "minecraft:lukewarm_ocean");
/* 157 */     BIOMES_BY_ID.put(46, "minecraft:cold_ocean");
/* 158 */     BIOMES_BY_ID.put(47, "minecraft:deep_warm_ocean");
/* 159 */     BIOMES_BY_ID.put(48, "minecraft:deep_lukewarm_ocean");
/* 160 */     BIOMES_BY_ID.put(49, "minecraft:deep_cold_ocean");
/* 161 */     BIOMES_BY_ID.put(50, "minecraft:deep_frozen_ocean");
/* 162 */     BIOMES_BY_ID.put(127, "minecraft:the_void");
/* 163 */     BIOMES_BY_ID.put(129, "minecraft:sunflower_plains");
/* 164 */     BIOMES_BY_ID.put(130, "minecraft:desert_lakes");
/* 165 */     BIOMES_BY_ID.put(131, "minecraft:gravelly_mountains");
/* 166 */     BIOMES_BY_ID.put(132, "minecraft:flower_forest");
/* 167 */     BIOMES_BY_ID.put(133, "minecraft:taiga_mountains");
/* 168 */     BIOMES_BY_ID.put(134, "minecraft:swamp_hills");
/* 169 */     BIOMES_BY_ID.put(140, "minecraft:ice_spikes");
/* 170 */     BIOMES_BY_ID.put(149, "minecraft:modified_jungle");
/* 171 */     BIOMES_BY_ID.put(151, "minecraft:modified_jungle_edge");
/* 172 */     BIOMES_BY_ID.put(155, "minecraft:tall_birch_forest");
/* 173 */     BIOMES_BY_ID.put(156, "minecraft:tall_birch_hills");
/* 174 */     BIOMES_BY_ID.put(157, "minecraft:dark_forest_hills");
/* 175 */     BIOMES_BY_ID.put(158, "minecraft:snowy_taiga_mountains");
/* 176 */     BIOMES_BY_ID.put(160, "minecraft:giant_spruce_taiga");
/* 177 */     BIOMES_BY_ID.put(161, "minecraft:giant_spruce_taiga_hills");
/* 178 */     BIOMES_BY_ID.put(162, "minecraft:modified_gravelly_mountains");
/* 179 */     BIOMES_BY_ID.put(163, "minecraft:shattered_savanna");
/* 180 */     BIOMES_BY_ID.put(164, "minecraft:shattered_savanna_plateau");
/* 181 */     BIOMES_BY_ID.put(165, "minecraft:eroded_badlands");
/* 182 */     BIOMES_BY_ID.put(166, "minecraft:modified_wooded_badlands_plateau");
/* 183 */     BIOMES_BY_ID.put(167, "minecraft:modified_badlands_plateau");
/* 184 */     BIOMES_BY_ID.put(168, "minecraft:bamboo_jungle");
/* 185 */     BIOMES_BY_ID.put(169, "minecraft:bamboo_jungle_hills");
/* 186 */     BIOMES_BY_ID.put(170, "minecraft:soul_sand_valley");
/* 187 */     BIOMES_BY_ID.put(171, "minecraft:crimson_forest");
/* 188 */     BIOMES_BY_ID.put(172, "minecraft:warped_forest");
/* 189 */     BIOMES_BY_ID.put(173, "minecraft:basalt_deltas");
/* 190 */     BIOMES_BY_ID.put(174, "minecraft:dripstone_caves");
/* 191 */     BIOMES_BY_ID.put(175, "minecraft:lush_caves");
/* 192 */     BIOMES_BY_ID.put(177, "minecraft:meadow");
/* 193 */     BIOMES_BY_ID.put(178, "minecraft:grove");
/* 194 */     BIOMES_BY_ID.put(179, "minecraft:snowy_slopes");
/* 195 */     BIOMES_BY_ID.put(180, "minecraft:snowcapped_peaks");
/* 196 */     BIOMES_BY_ID.put(181, "minecraft:lofty_peaks");
/* 197 */     BIOMES_BY_ID.put(182, "minecraft:stony_peaks");
/*     */   }
/*     */   
/*     */   public ChunkHeightAndBiomeFix(Schema $$0) {
/* 201 */     super($$0, true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TypeRewriteRule makeRule() {
/* 206 */     Type<?> $$0 = getInputSchema().getType(References.CHUNK);
/* 207 */     OpticFinder<?> $$1 = $$0.findField("Level");
/* 208 */     OpticFinder<?> $$2 = $$1.type().findField("Sections");
/*     */     
/* 210 */     Schema $$3 = getOutputSchema();
/* 211 */     Type<?> $$4 = $$3.getType(References.CHUNK);
/* 212 */     Type<?> $$5 = $$4.findField("Level").type();
/* 213 */     Type<?> $$6 = $$5.findField("Sections").type();
/*     */     
/* 215 */     return fixTypeEverywhereTyped("ChunkHeightAndBiomeFix", $$0, $$4, $$4 -> $$4.updateTyped($$0, $$1, ()));
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
/*     */   
/*     */   private Dynamic<?> predictChunkStatusBeforeSurface(Dynamic<?> $$0, Set<String> $$1) {
/* 290 */     return $$0.update("Status", $$1 -> {
/*     */           String $$2 = $$1.asString("empty");
/*     */ 
/*     */           
/*     */           if (STATUS_IS_OR_AFTER_SURFACE.contains($$2)) {
/*     */             return $$1;
/*     */           }
/*     */ 
/*     */           
/*     */           $$0.remove("minecraft:air");
/*     */ 
/*     */           
/*     */           boolean $$3 = !$$0.isEmpty();
/*     */ 
/*     */           
/*     */           $$0.removeAll(BLOCKS_BEFORE_FEATURE_STATUS);
/*     */           
/*     */           boolean $$4 = !$$0.isEmpty();
/*     */           
/* 309 */           return $$4 ? $$1.createString("liquid_carvers") : (("noise".equals($$2) || $$3) ? $$1.createString("noise") : ("biomes".equals($$2) ? $$1.createString("structure_references") : $$1));
/*     */         });
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
/*     */   private static Dynamic<?>[] getBiomeContainers(Dynamic<?> $$0, boolean $$1, int $$2, MutableBoolean $$3) {
/* 323 */     Dynamic[] arrayOfDynamic = new Dynamic[$$1 ? 24 : 16];
/*     */     
/* 325 */     int[] $$5 = $$0.get("Biomes").asIntStreamOpt().result().map(IntStream::toArray).orElse(null);
/* 326 */     if ($$5 != null && $$5.length == 1536) {
/* 327 */       $$3.setValue(true);
/*     */       
/* 329 */       for (int $$6 = 0; $$6 < 24; $$6++) {
/* 330 */         int $$7 = $$6;
/* 331 */         arrayOfDynamic[$$6] = makeBiomeContainer($$0, $$2 -> getOldBiome($$0, $$1 * 64 + $$2));
/*     */       } 
/* 333 */     } else if ($$5 != null && $$5.length == 1024) {
/* 334 */       for (int $$8 = 0; $$8 < 16; $$8++) {
/* 335 */         int $$9 = $$8 - $$2;
/* 336 */         int $$10 = $$8;
/* 337 */         arrayOfDynamic[$$9] = makeBiomeContainer($$0, $$2 -> getOldBiome($$0, $$1 * 64 + $$2));
/*     */       } 
/* 339 */       if ($$1) {
/* 340 */         Dynamic<?> $$11 = makeBiomeContainer($$0, $$1 -> getOldBiome($$0, $$1 % 16));
/* 341 */         Dynamic<?> $$12 = makeBiomeContainer($$0, $$1 -> getOldBiome($$0, $$1 % 16 + 1008));
/* 342 */         for (int $$13 = 0; $$13 < 4; $$13++) {
/* 343 */           arrayOfDynamic[$$13] = $$11;
/*     */         }
/* 345 */         for (int $$14 = 20; $$14 < 24; $$14++) {
/* 346 */           arrayOfDynamic[$$14] = $$12;
/*     */         }
/*     */       } 
/*     */     } else {
/* 350 */       Arrays.fill((Object[])arrayOfDynamic, makePalettedContainer($$0.createList(Stream.of($$0.createString("minecraft:plains")))));
/*     */     } 
/* 352 */     return (Dynamic<?>[])arrayOfDynamic;
/*     */   }
/*     */   
/*     */   private static int getOldBiome(int[] $$0, int $$1) {
/* 356 */     return $$0[$$1] & 0xFF;
/*     */   }
/*     */   
/*     */   private static Dynamic<?> updateChunkTag(Dynamic<?> $$0, boolean $$1, boolean $$2, boolean $$3, Supplier<ChunkProtoTickListFix.PoorMansPalettedContainer> $$4) {
/* 360 */     $$0 = $$0.remove("Biomes");
/*     */     
/* 362 */     if (!$$1) {
/* 363 */       return updateCarvingMasks($$0, 16, 0);
/*     */     }
/*     */     
/* 366 */     if ($$2) {
/* 367 */       return updateCarvingMasks($$0, 24, 0);
/*     */     }
/*     */     
/* 370 */     $$0 = updateHeightmaps($$0);
/* 371 */     $$0 = addPaddingEntries($$0, "LiquidsToBeTicked");
/* 372 */     $$0 = addPaddingEntries($$0, "PostProcessing");
/* 373 */     $$0 = addPaddingEntries($$0, "ToBeTicked");
/* 374 */     $$0 = updateCarvingMasks($$0, 24, 4);
/* 375 */     $$0 = $$0.update("UpgradeData", ChunkHeightAndBiomeFix::shiftUpgradeData);
/*     */     
/* 377 */     if (!$$3) {
/* 378 */       return $$0;
/*     */     }
/*     */     
/* 381 */     Optional<? extends Dynamic<?>> $$5 = $$0.get("Status").result();
/* 382 */     if ($$5.isPresent()) {
/* 383 */       Dynamic<?> $$6 = $$5.get();
/* 384 */       String $$7 = $$6.asString("");
/* 385 */       if (!"empty".equals($$7)) {
/* 386 */         $$0 = $$0.set("blending_data", $$0.createMap((Map)ImmutableMap.of($$0
/* 387 */                 .createString("old_noise"), $$0.createBoolean(STATUS_IS_OR_AFTER_NOISE.contains($$7)))));
/*     */ 
/*     */ 
/*     */         
/* 391 */         ChunkProtoTickListFix.PoorMansPalettedContainer $$8 = $$4.get();
/* 392 */         if ($$8 != null) {
/* 393 */           BitSet $$9 = new BitSet(256);
/* 394 */           boolean $$10 = $$7.equals("noise");
/* 395 */           for (int $$11 = 0; $$11 < 16; $$11++) {
/* 396 */             for (int $$12 = 0; $$12 < 16; $$12++) {
/* 397 */               Dynamic<?> $$13 = $$8.get($$12, 0, $$11);
/* 398 */               boolean $$14 = ($$13 != null && "minecraft:bedrock".equals($$13.get("Name").asString("")));
/* 399 */               boolean $$15 = ($$13 != null && "minecraft:air".equals($$13.get("Name").asString("")));
/* 400 */               if ($$15) {
/* 401 */                 $$9.set($$11 * 16 + $$12);
/*     */               }
/* 403 */               $$10 |= $$14;
/*     */             } 
/*     */           } 
/*     */           
/* 407 */           if ($$10 && $$9.cardinality() != $$9.size()) {
/*     */             
/* 409 */             Dynamic<?> $$16 = "full".equals($$7) ? $$0.createString("heightmaps") : $$6;
/*     */             
/* 411 */             $$0 = $$0.set("below_zero_retrogen", $$0.createMap((Map)ImmutableMap.of($$0
/* 412 */                     .createString("target_status"), $$16, $$0
/* 413 */                     .createString("missing_bedrock"), $$0.createLongList(LongStream.of($$9.toLongArray())))));
/*     */ 
/*     */             
/* 416 */             $$0 = $$0.set("Status", $$0.createString("empty"));
/*     */           } 
/* 418 */           $$0 = $$0.set("isLightOn", $$0.createBoolean(false));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 423 */     return $$0;
/*     */   }
/*     */   
/*     */   private static <T> Dynamic<T> shiftUpgradeData(Dynamic<T> $$0) {
/* 427 */     return $$0.update("Indices", $$0 -> {
/*     */           Map<Dynamic<?>, Dynamic<?>> $$1 = new HashMap<>();
/*     */           $$0.getMapValues().result().ifPresent(());
/*     */           return $$0.createMap($$1);
/*     */         });
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
/*     */   private static Dynamic<?> updateCarvingMasks(Dynamic<?> $$0, int $$1, int $$2) {
/* 448 */     Dynamic<?> $$3 = $$0.get("CarvingMasks").orElseEmptyMap();
/* 449 */     $$3 = $$3.updateMapValues($$3 -> {
/*     */           long[] $$4 = BitSet.valueOf(((Dynamic)$$3.getSecond()).asByteBuffer().array()).toLongArray();
/*     */           
/*     */           long[] $$5 = new long[64 * $$0];
/*     */           
/*     */           System.arraycopy($$4, 0, $$5, 64 * $$1, $$4.length);
/*     */           
/*     */           return Pair.of($$3.getFirst(), $$2.createLongList(LongStream.of($$5)));
/*     */         });
/* 458 */     return $$0.set("CarvingMasks", $$3);
/*     */   }
/*     */   
/*     */   private static Dynamic<?> addPaddingEntries(Dynamic<?> $$0, String $$1) {
/* 462 */     List<Dynamic<?>> $$2 = (List<Dynamic<?>>)$$0.get($$1).orElseEmptyList().asStream().collect(Collectors.toCollection(java.util.ArrayList::new));
/* 463 */     if ($$2.size() == 24)
/*     */     {
/* 465 */       return $$0;
/*     */     }
/* 467 */     Dynamic<?> $$3 = $$0.emptyList();
/* 468 */     for (int $$4 = 0; $$4 < 4; $$4++) {
/* 469 */       $$2.add(0, $$3);
/* 470 */       $$2.add($$3);
/*     */     } 
/* 472 */     return $$0.set($$1, $$0.createList($$2.stream()));
/*     */   }
/*     */   
/*     */   private static Dynamic<?> updateHeightmaps(Dynamic<?> $$0) {
/* 476 */     return $$0.update("Heightmaps", $$0 -> {
/*     */           for (String $$1 : HEIGHTMAP_TYPES) {
/*     */             $$0 = $$0.update($$1, ChunkHeightAndBiomeFix::getFixedHeightmap);
/*     */           }
/*     */           return $$0;
/*     */         });
/*     */   }
/*     */   
/*     */   private static Dynamic<?> getFixedHeightmap(Dynamic<?> $$0) {
/* 485 */     return $$0.createLongList($$0.asLongStream().map($$0 -> {
/*     */             long $$1 = 0L;
/*     */             for (int $$2 = 0; $$2 + 9 <= 64; $$2 += 9) {
/*     */               long $$5;
/*     */               long $$3 = $$0 >> $$2 & 0x1FFL;
/*     */               if ($$3 == 0L) {
/*     */                 long $$4 = 0L;
/*     */               } else {
/*     */                 $$5 = Math.min($$3 + 64L, 511L);
/*     */               } 
/*     */               $$1 |= $$5 << $$2;
/*     */             } 
/*     */             return $$1;
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Dynamic<?> makeBiomeContainer(Dynamic<?> $$0, Int2IntFunction $$1) {
/* 505 */     Int2IntLinkedOpenHashMap int2IntLinkedOpenHashMap = new Int2IntLinkedOpenHashMap();
/* 506 */     for (int $$3 = 0; $$3 < 64; $$3++) {
/* 507 */       int $$4 = $$1.applyAsInt($$3);
/* 508 */       if (!int2IntLinkedOpenHashMap.containsKey($$4)) {
/* 509 */         int2IntLinkedOpenHashMap.put($$4, int2IntLinkedOpenHashMap.size());
/*     */       }
/*     */     } 
/* 512 */     Dynamic<?> $$5 = $$0.createList(int2IntLinkedOpenHashMap.keySet().stream().map($$1 -> $$0.createString((String)BIOMES_BY_ID.getOrDefault($$1.intValue(), "minecraft:plains"))));
/*     */     
/* 514 */     int $$6 = ceillog2(int2IntLinkedOpenHashMap.size());
/* 515 */     if ($$6 == 0) {
/* 516 */       return makePalettedContainer($$5);
/*     */     }
/*     */     
/* 519 */     int $$7 = 64 / $$6;
/* 520 */     int $$8 = (64 + $$7 - 1) / $$7;
/* 521 */     long[] $$9 = new long[$$8];
/* 522 */     int $$10 = 0;
/* 523 */     int $$11 = 0;
/* 524 */     for (int $$12 = 0; $$12 < 64; $$12++) {
/* 525 */       int $$13 = $$1.applyAsInt($$12);
/* 526 */       $$9[$$10] = $$9[$$10] | int2IntLinkedOpenHashMap.get($$13) << $$11;
/* 527 */       $$11 += $$6;
/* 528 */       if ($$11 + $$6 > 64) {
/* 529 */         $$10++;
/* 530 */         $$11 = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 534 */     Dynamic<?> $$14 = $$0.createLongList(Arrays.stream($$9));
/* 535 */     return makePalettedContainer($$5, $$14);
/*     */   }
/*     */   
/*     */   private static Dynamic<?> makePalettedContainer(Dynamic<?> $$0) {
/* 539 */     return $$0.createMap((Map)ImmutableMap.of($$0.createString("palette"), $$0));
/*     */   }
/*     */   
/*     */   private static Dynamic<?> makePalettedContainer(Dynamic<?> $$0, Dynamic<?> $$1) {
/* 543 */     return $$0.createMap((Map)ImmutableMap.of($$0.createString("palette"), $$0, $$0.createString("data"), $$1));
/*     */   }
/*     */   
/*     */   private static Dynamic<?> makeOptimizedPalettedContainer(Dynamic<?> $$0, Dynamic<?> $$1) {
/* 547 */     List<Dynamic<?>> $$2 = (List<Dynamic<?>>)$$0.asStream().collect(Collectors.toCollection(java.util.ArrayList::new));
/* 548 */     if ($$2.size() == 1) {
/* 549 */       return makePalettedContainer($$0);
/*     */     }
/* 551 */     $$0 = padPaletteEntries($$0, $$1, $$2);
/* 552 */     return makePalettedContainer($$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Dynamic<?> padPaletteEntries(Dynamic<?> $$0, Dynamic<?> $$1, List<Dynamic<?>> $$2) {
/* 558 */     long $$3 = $$1.asLongStream().count() * 64L;
/* 559 */     long $$4 = $$3 / 4096L;
/*     */     
/* 561 */     int $$5 = $$2.size();
/* 562 */     int $$6 = ceillog2($$5);
/*     */     
/* 564 */     if ($$4 > $$6) {
/* 565 */       Dynamic<?> $$7 = $$0.createMap((Map)ImmutableMap.of($$0.createString("Name"), $$0.createString("minecraft:air")));
/*     */       
/* 567 */       int $$8 = (1 << (int)($$4 - 1L)) + 1;
/* 568 */       int $$9 = $$8 - $$5;
/* 569 */       for (int $$10 = 0; $$10 < $$9; $$10++) {
/* 570 */         $$2.add($$7);
/*     */       }
/* 572 */       return $$0.createList($$2.stream());
/*     */     } 
/* 574 */     return $$0;
/*     */   }
/*     */   
/*     */   public static int ceillog2(int $$0) {
/* 578 */     if ($$0 == 0) {
/* 579 */       return 0;
/*     */     }
/*     */     
/* 582 */     return (int)Math.ceil(Math.log($$0) / Math.log(2.0D));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ChunkHeightAndBiomeFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */