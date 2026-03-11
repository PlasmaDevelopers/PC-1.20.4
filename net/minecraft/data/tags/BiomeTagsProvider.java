/*     */ package net.minecraft.data.tags;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.PackOutput;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
/*     */ 
/*     */ public class BiomeTagsProvider extends TagsProvider<Biome> {
/*     */   public BiomeTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/*  15 */     super($$0, Registries.BIOME, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addTags(HolderLookup.Provider $$0) {
/*  20 */     tag(BiomeTags.IS_DEEP_OCEAN)
/*  21 */       .add(Biomes.DEEP_FROZEN_OCEAN)
/*  22 */       .add(Biomes.DEEP_COLD_OCEAN)
/*  23 */       .add(Biomes.DEEP_OCEAN)
/*  24 */       .add(Biomes.DEEP_LUKEWARM_OCEAN);
/*     */ 
/*     */     
/*  27 */     tag(BiomeTags.IS_OCEAN)
/*  28 */       .addTag(BiomeTags.IS_DEEP_OCEAN)
/*  29 */       .add(Biomes.FROZEN_OCEAN)
/*  30 */       .add(Biomes.OCEAN)
/*  31 */       .add(Biomes.COLD_OCEAN)
/*  32 */       .add(Biomes.LUKEWARM_OCEAN)
/*  33 */       .add(Biomes.WARM_OCEAN);
/*     */ 
/*     */     
/*  36 */     tag(BiomeTags.IS_BEACH)
/*  37 */       .add(Biomes.BEACH)
/*  38 */       .add(Biomes.SNOWY_BEACH);
/*     */ 
/*     */     
/*  41 */     tag(BiomeTags.IS_RIVER)
/*  42 */       .add(Biomes.RIVER)
/*  43 */       .add(Biomes.FROZEN_RIVER);
/*     */ 
/*     */     
/*  46 */     tag(BiomeTags.IS_MOUNTAIN)
/*  47 */       .add(Biomes.MEADOW)
/*  48 */       .add(Biomes.FROZEN_PEAKS)
/*  49 */       .add(Biomes.JAGGED_PEAKS)
/*  50 */       .add(Biomes.STONY_PEAKS)
/*  51 */       .add(Biomes.SNOWY_SLOPES)
/*  52 */       .add(Biomes.CHERRY_GROVE);
/*     */ 
/*     */     
/*  55 */     tag(BiomeTags.IS_BADLANDS)
/*  56 */       .add(Biomes.BADLANDS)
/*  57 */       .add(Biomes.ERODED_BADLANDS)
/*  58 */       .add(Biomes.WOODED_BADLANDS);
/*     */ 
/*     */     
/*  61 */     tag(BiomeTags.IS_HILL)
/*  62 */       .add(Biomes.WINDSWEPT_HILLS)
/*  63 */       .add(Biomes.WINDSWEPT_FOREST)
/*  64 */       .add(Biomes.WINDSWEPT_GRAVELLY_HILLS);
/*     */ 
/*     */     
/*  67 */     tag(BiomeTags.IS_TAIGA)
/*  68 */       .add(Biomes.TAIGA)
/*  69 */       .add(Biomes.SNOWY_TAIGA)
/*  70 */       .add(Biomes.OLD_GROWTH_PINE_TAIGA)
/*  71 */       .add(Biomes.OLD_GROWTH_SPRUCE_TAIGA);
/*     */ 
/*     */     
/*  74 */     tag(BiomeTags.IS_JUNGLE)
/*  75 */       .add(Biomes.BAMBOO_JUNGLE)
/*  76 */       .add(Biomes.JUNGLE)
/*  77 */       .add(Biomes.SPARSE_JUNGLE);
/*     */ 
/*     */     
/*  80 */     tag(BiomeTags.IS_FOREST)
/*  81 */       .add(Biomes.FOREST)
/*  82 */       .add(Biomes.FLOWER_FOREST)
/*  83 */       .add(Biomes.BIRCH_FOREST)
/*  84 */       .add(Biomes.OLD_GROWTH_BIRCH_FOREST)
/*  85 */       .add(Biomes.DARK_FOREST)
/*  86 */       .add(Biomes.GROVE);
/*     */ 
/*     */     
/*  89 */     tag(BiomeTags.IS_SAVANNA)
/*  90 */       .add(Biomes.SAVANNA)
/*  91 */       .add(Biomes.SAVANNA_PLATEAU)
/*  92 */       .add(Biomes.WINDSWEPT_SAVANNA);
/*     */ 
/*     */     
/*  95 */     TagsProvider.TagAppender<Biome> $$1 = tag(BiomeTags.IS_NETHER);
/*  96 */     Objects.requireNonNull($$1); MultiNoiseBiomeSourceParameterList.Preset.NETHER.usedBiomes().forEach($$1::add);
/*     */     
/*  98 */     TagsProvider.TagAppender<Biome> $$2 = tag(BiomeTags.IS_OVERWORLD);
/*  99 */     Objects.requireNonNull($$2); MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD.usedBiomes().forEach($$2::add);
/*     */     
/* 101 */     tag(BiomeTags.IS_END)
/* 102 */       .add(Biomes.THE_END)
/* 103 */       .add(Biomes.END_HIGHLANDS)
/* 104 */       .add(Biomes.END_MIDLANDS)
/* 105 */       .add(Biomes.SMALL_END_ISLANDS)
/* 106 */       .add(Biomes.END_BARRENS);
/*     */ 
/*     */ 
/*     */     
/* 110 */     tag(BiomeTags.HAS_BURIED_TREASURE)
/* 111 */       .addTag(BiomeTags.IS_BEACH);
/*     */ 
/*     */     
/* 114 */     tag(BiomeTags.HAS_DESERT_PYRAMID)
/* 115 */       .add(Biomes.DESERT);
/*     */ 
/*     */     
/* 118 */     tag(BiomeTags.HAS_IGLOO)
/* 119 */       .add(Biomes.SNOWY_TAIGA)
/* 120 */       .add(Biomes.SNOWY_PLAINS)
/* 121 */       .add(Biomes.SNOWY_SLOPES);
/*     */ 
/*     */     
/* 124 */     tag(BiomeTags.HAS_JUNGLE_TEMPLE)
/* 125 */       .add(Biomes.BAMBOO_JUNGLE)
/* 126 */       .add(Biomes.JUNGLE);
/*     */ 
/*     */     
/* 129 */     tag(BiomeTags.HAS_MINESHAFT)
/* 130 */       .addTag(BiomeTags.IS_OCEAN)
/* 131 */       .addTag(BiomeTags.IS_RIVER)
/* 132 */       .addTag(BiomeTags.IS_BEACH)
/* 133 */       .addTag(BiomeTags.IS_MOUNTAIN)
/* 134 */       .addTag(BiomeTags.IS_HILL)
/* 135 */       .addTag(BiomeTags.IS_TAIGA)
/* 136 */       .addTag(BiomeTags.IS_JUNGLE)
/* 137 */       .addTag(BiomeTags.IS_FOREST)
/* 138 */       .add(Biomes.STONY_SHORE)
/* 139 */       .add(Biomes.MUSHROOM_FIELDS)
/* 140 */       .add(Biomes.ICE_SPIKES)
/* 141 */       .add(Biomes.WINDSWEPT_SAVANNA)
/* 142 */       .add(Biomes.DESERT)
/* 143 */       .add(Biomes.SAVANNA)
/* 144 */       .add(Biomes.SNOWY_PLAINS)
/* 145 */       .add(Biomes.PLAINS)
/* 146 */       .add(Biomes.SUNFLOWER_PLAINS)
/* 147 */       .add(Biomes.SWAMP)
/* 148 */       .add(Biomes.MANGROVE_SWAMP)
/* 149 */       .add(Biomes.SAVANNA_PLATEAU)
/* 150 */       .add(Biomes.DRIPSTONE_CAVES)
/* 151 */       .add(Biomes.LUSH_CAVES);
/*     */ 
/*     */     
/* 154 */     tag(BiomeTags.HAS_MINESHAFT_MESA)
/* 155 */       .addTag(BiomeTags.IS_BADLANDS);
/*     */ 
/*     */     
/* 158 */     tag(BiomeTags.MINESHAFT_BLOCKING)
/* 159 */       .add(Biomes.DEEP_DARK);
/*     */ 
/*     */     
/* 162 */     tag(BiomeTags.HAS_OCEAN_MONUMENT)
/* 163 */       .addTag(BiomeTags.IS_DEEP_OCEAN);
/*     */ 
/*     */     
/* 166 */     tag(BiomeTags.REQUIRED_OCEAN_MONUMENT_SURROUNDING)
/* 167 */       .addTag(BiomeTags.IS_OCEAN)
/* 168 */       .addTag(BiomeTags.IS_RIVER);
/*     */ 
/*     */     
/* 171 */     tag(BiomeTags.HAS_OCEAN_RUIN_COLD)
/* 172 */       .add(Biomes.FROZEN_OCEAN)
/* 173 */       .add(Biomes.COLD_OCEAN)
/* 174 */       .add(Biomes.OCEAN)
/* 175 */       .add(Biomes.DEEP_FROZEN_OCEAN)
/* 176 */       .add(Biomes.DEEP_COLD_OCEAN)
/* 177 */       .add(Biomes.DEEP_OCEAN);
/*     */ 
/*     */     
/* 180 */     tag(BiomeTags.HAS_OCEAN_RUIN_WARM)
/* 181 */       .add(Biomes.LUKEWARM_OCEAN)
/* 182 */       .add(Biomes.WARM_OCEAN)
/* 183 */       .add(Biomes.DEEP_LUKEWARM_OCEAN);
/*     */ 
/*     */     
/* 186 */     tag(BiomeTags.HAS_PILLAGER_OUTPOST)
/* 187 */       .add(Biomes.DESERT)
/* 188 */       .add(Biomes.PLAINS)
/* 189 */       .add(Biomes.SAVANNA)
/* 190 */       .add(Biomes.SNOWY_PLAINS)
/* 191 */       .add(Biomes.TAIGA)
/* 192 */       .addTag(BiomeTags.IS_MOUNTAIN)
/* 193 */       .add(Biomes.GROVE);
/*     */ 
/*     */     
/* 196 */     tag(BiomeTags.HAS_RUINED_PORTAL_DESERT)
/* 197 */       .add(Biomes.DESERT);
/*     */ 
/*     */     
/* 200 */     tag(BiomeTags.HAS_RUINED_PORTAL_JUNGLE)
/* 201 */       .addTag(BiomeTags.IS_JUNGLE);
/*     */ 
/*     */     
/* 204 */     tag(BiomeTags.HAS_RUINED_PORTAL_OCEAN)
/* 205 */       .addTag(BiomeTags.IS_OCEAN);
/*     */ 
/*     */     
/* 208 */     tag(BiomeTags.HAS_RUINED_PORTAL_SWAMP)
/* 209 */       .add(Biomes.SWAMP)
/* 210 */       .add(Biomes.MANGROVE_SWAMP);
/*     */ 
/*     */     
/* 213 */     tag(BiomeTags.HAS_RUINED_PORTAL_MOUNTAIN)
/* 214 */       .addTag(BiomeTags.IS_BADLANDS)
/* 215 */       .addTag(BiomeTags.IS_HILL)
/* 216 */       .add(Biomes.SAVANNA_PLATEAU)
/* 217 */       .add(Biomes.WINDSWEPT_SAVANNA)
/* 218 */       .add(Biomes.STONY_SHORE)
/* 219 */       .addTag(BiomeTags.IS_MOUNTAIN);
/*     */ 
/*     */     
/* 222 */     tag(BiomeTags.HAS_RUINED_PORTAL_STANDARD)
/* 223 */       .addTag(BiomeTags.IS_BEACH)
/* 224 */       .addTag(BiomeTags.IS_RIVER)
/* 225 */       .addTag(BiomeTags.IS_TAIGA)
/* 226 */       .addTag(BiomeTags.IS_FOREST)
/* 227 */       .add(Biomes.MUSHROOM_FIELDS)
/* 228 */       .add(Biomes.ICE_SPIKES)
/* 229 */       .add(Biomes.DRIPSTONE_CAVES)
/* 230 */       .add(Biomes.LUSH_CAVES)
/* 231 */       .add(Biomes.SAVANNA)
/* 232 */       .add(Biomes.SNOWY_PLAINS)
/* 233 */       .add(Biomes.PLAINS)
/* 234 */       .add(Biomes.SUNFLOWER_PLAINS);
/*     */ 
/*     */     
/* 237 */     tag(BiomeTags.HAS_SHIPWRECK_BEACHED)
/* 238 */       .addTag(BiomeTags.IS_BEACH);
/*     */ 
/*     */     
/* 241 */     tag(BiomeTags.HAS_SHIPWRECK)
/* 242 */       .addTag(BiomeTags.IS_OCEAN);
/*     */ 
/*     */     
/* 245 */     tag(BiomeTags.HAS_SWAMP_HUT)
/* 246 */       .add(Biomes.SWAMP);
/*     */ 
/*     */     
/* 249 */     tag(BiomeTags.HAS_VILLAGE_DESERT)
/* 250 */       .add(Biomes.DESERT);
/*     */ 
/*     */     
/* 253 */     tag(BiomeTags.HAS_VILLAGE_PLAINS)
/* 254 */       .add(Biomes.PLAINS)
/* 255 */       .add(Biomes.MEADOW);
/*     */ 
/*     */     
/* 258 */     tag(BiomeTags.HAS_VILLAGE_SAVANNA)
/* 259 */       .add(Biomes.SAVANNA);
/*     */ 
/*     */     
/* 262 */     tag(BiomeTags.HAS_VILLAGE_SNOWY)
/* 263 */       .add(Biomes.SNOWY_PLAINS);
/*     */ 
/*     */     
/* 266 */     tag(BiomeTags.HAS_VILLAGE_TAIGA)
/* 267 */       .add(Biomes.TAIGA);
/*     */ 
/*     */     
/* 270 */     tag(BiomeTags.HAS_TRAIL_RUINS)
/* 271 */       .add(Biomes.TAIGA)
/* 272 */       .add(Biomes.SNOWY_TAIGA)
/* 273 */       .add(Biomes.OLD_GROWTH_PINE_TAIGA)
/* 274 */       .add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
/* 275 */       .add(Biomes.OLD_GROWTH_BIRCH_FOREST)
/* 276 */       .add(Biomes.JUNGLE);
/*     */ 
/*     */     
/* 279 */     tag(BiomeTags.HAS_WOODLAND_MANSION)
/* 280 */       .add(Biomes.DARK_FOREST);
/*     */ 
/*     */     
/* 283 */     tag(BiomeTags.STRONGHOLD_BIASED_TO)
/* 284 */       .add(Biomes.PLAINS)
/* 285 */       .add(Biomes.SUNFLOWER_PLAINS)
/* 286 */       .add(Biomes.SNOWY_PLAINS)
/* 287 */       .add(Biomes.ICE_SPIKES)
/* 288 */       .add(Biomes.DESERT)
/* 289 */       .add(Biomes.FOREST)
/* 290 */       .add(Biomes.FLOWER_FOREST)
/* 291 */       .add(Biomes.BIRCH_FOREST)
/* 292 */       .add(Biomes.DARK_FOREST)
/* 293 */       .add(Biomes.OLD_GROWTH_BIRCH_FOREST)
/* 294 */       .add(Biomes.OLD_GROWTH_PINE_TAIGA)
/* 295 */       .add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
/* 296 */       .add(Biomes.TAIGA)
/* 297 */       .add(Biomes.SNOWY_TAIGA)
/* 298 */       .add(Biomes.SAVANNA)
/* 299 */       .add(Biomes.SAVANNA_PLATEAU)
/* 300 */       .add(Biomes.WINDSWEPT_HILLS)
/* 301 */       .add(Biomes.WINDSWEPT_GRAVELLY_HILLS)
/* 302 */       .add(Biomes.WINDSWEPT_FOREST)
/* 303 */       .add(Biomes.WINDSWEPT_SAVANNA)
/* 304 */       .add(Biomes.JUNGLE)
/* 305 */       .add(Biomes.SPARSE_JUNGLE)
/* 306 */       .add(Biomes.BAMBOO_JUNGLE)
/* 307 */       .add(Biomes.BADLANDS)
/* 308 */       .add(Biomes.ERODED_BADLANDS)
/* 309 */       .add(Biomes.WOODED_BADLANDS)
/* 310 */       .add(Biomes.MEADOW)
/* 311 */       .add(Biomes.GROVE)
/* 312 */       .add(Biomes.SNOWY_SLOPES)
/* 313 */       .add(Biomes.FROZEN_PEAKS)
/* 314 */       .add(Biomes.JAGGED_PEAKS)
/* 315 */       .add(Biomes.STONY_PEAKS)
/* 316 */       .add(Biomes.MUSHROOM_FIELDS)
/* 317 */       .add(Biomes.DRIPSTONE_CAVES)
/* 318 */       .add(Biomes.LUSH_CAVES);
/*     */ 
/*     */     
/* 321 */     tag(BiomeTags.HAS_STRONGHOLD)
/* 322 */       .addTag(BiomeTags.IS_OVERWORLD);
/*     */ 
/*     */     
/* 325 */     tag(BiomeTags.HAS_NETHER_FORTRESS)
/* 326 */       .addTag(BiomeTags.IS_NETHER);
/*     */ 
/*     */     
/* 329 */     tag(BiomeTags.HAS_NETHER_FOSSIL)
/* 330 */       .add(Biomes.SOUL_SAND_VALLEY);
/*     */ 
/*     */     
/* 333 */     tag(BiomeTags.HAS_BASTION_REMNANT)
/* 334 */       .add(Biomes.CRIMSON_FOREST)
/* 335 */       .add(Biomes.NETHER_WASTES)
/* 336 */       .add(Biomes.SOUL_SAND_VALLEY)
/* 337 */       .add(Biomes.WARPED_FOREST);
/*     */ 
/*     */     
/* 340 */     tag(BiomeTags.HAS_ANCIENT_CITY)
/* 341 */       .add(Biomes.DEEP_DARK);
/*     */ 
/*     */     
/* 344 */     tag(BiomeTags.HAS_RUINED_PORTAL_NETHER)
/* 345 */       .addTag(BiomeTags.IS_NETHER);
/*     */ 
/*     */ 
/*     */     
/* 349 */     tag(BiomeTags.HAS_END_CITY)
/* 350 */       .add(Biomes.END_HIGHLANDS)
/* 351 */       .add(Biomes.END_MIDLANDS);
/*     */ 
/*     */ 
/*     */     
/* 355 */     tag(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)
/* 356 */       .add(Biomes.WARM_OCEAN);
/*     */ 
/*     */ 
/*     */     
/* 360 */     tag(BiomeTags.PLAYS_UNDERWATER_MUSIC)
/* 361 */       .addTag(BiomeTags.IS_OCEAN)
/* 362 */       .addTag(BiomeTags.IS_RIVER);
/*     */ 
/*     */ 
/*     */     
/* 366 */     tag(BiomeTags.HAS_CLOSER_WATER_FOG)
/* 367 */       .add(Biomes.SWAMP)
/* 368 */       .add(Biomes.MANGROVE_SWAMP);
/*     */ 
/*     */     
/* 371 */     tag(BiomeTags.WATER_ON_MAP_OUTLINES)
/* 372 */       .addTag(BiomeTags.IS_OCEAN)
/* 373 */       .addTag(BiomeTags.IS_RIVER)
/* 374 */       .add(Biomes.SWAMP)
/* 375 */       .add(Biomes.MANGROVE_SWAMP);
/*     */ 
/*     */ 
/*     */     
/* 379 */     tag(BiomeTags.WITHOUT_ZOMBIE_SIEGES)
/* 380 */       .add(Biomes.MUSHROOM_FIELDS);
/*     */ 
/*     */     
/* 383 */     tag(BiomeTags.WITHOUT_PATROL_SPAWNS)
/* 384 */       .add(Biomes.MUSHROOM_FIELDS);
/*     */ 
/*     */     
/* 387 */     tag(BiomeTags.WITHOUT_WANDERING_TRADER_SPAWNS)
/* 388 */       .add(Biomes.THE_VOID);
/*     */ 
/*     */     
/* 391 */     tag(BiomeTags.SPAWNS_COLD_VARIANT_FROGS)
/* 392 */       .add(Biomes.SNOWY_PLAINS)
/* 393 */       .add(Biomes.ICE_SPIKES)
/* 394 */       .add(Biomes.FROZEN_PEAKS)
/* 395 */       .add(Biomes.JAGGED_PEAKS)
/* 396 */       .add(Biomes.SNOWY_SLOPES)
/* 397 */       .add(Biomes.FROZEN_OCEAN)
/* 398 */       .add(Biomes.DEEP_FROZEN_OCEAN)
/* 399 */       .add(Biomes.GROVE)
/* 400 */       .add(Biomes.DEEP_DARK)
/* 401 */       .add(Biomes.FROZEN_RIVER)
/* 402 */       .add(Biomes.SNOWY_TAIGA)
/* 403 */       .add(Biomes.SNOWY_BEACH)
/* 404 */       .addTag(BiomeTags.IS_END);
/*     */ 
/*     */     
/* 407 */     tag(BiomeTags.SPAWNS_WARM_VARIANT_FROGS)
/* 408 */       .add(Biomes.DESERT)
/* 409 */       .add(Biomes.WARM_OCEAN)
/* 410 */       .addTag(BiomeTags.IS_JUNGLE)
/* 411 */       .addTag(BiomeTags.IS_SAVANNA)
/* 412 */       .addTag(BiomeTags.IS_NETHER)
/* 413 */       .addTag(BiomeTags.IS_BADLANDS)
/* 414 */       .add(Biomes.MANGROVE_SWAMP);
/*     */ 
/*     */     
/* 417 */     tag(BiomeTags.SPAWNS_GOLD_RABBITS)
/* 418 */       .add(Biomes.DESERT);
/*     */ 
/*     */     
/* 421 */     tag(BiomeTags.SPAWNS_WHITE_RABBITS)
/* 422 */       .add(Biomes.SNOWY_PLAINS)
/* 423 */       .add(Biomes.ICE_SPIKES)
/* 424 */       .add(Biomes.FROZEN_OCEAN)
/* 425 */       .add(Biomes.SNOWY_TAIGA)
/* 426 */       .add(Biomes.FROZEN_RIVER)
/* 427 */       .add(Biomes.SNOWY_BEACH)
/* 428 */       .add(Biomes.FROZEN_PEAKS)
/* 429 */       .add(Biomes.JAGGED_PEAKS)
/* 430 */       .add(Biomes.SNOWY_SLOPES)
/* 431 */       .add(Biomes.GROVE);
/*     */ 
/*     */     
/* 434 */     tag(BiomeTags.REDUCED_WATER_AMBIENT_SPAWNS)
/* 435 */       .addTag(BiomeTags.IS_RIVER);
/*     */ 
/*     */     
/* 438 */     tag(BiomeTags.ALLOWS_TROPICAL_FISH_SPAWNS_AT_ANY_HEIGHT)
/* 439 */       .add(Biomes.LUSH_CAVES);
/*     */ 
/*     */     
/* 442 */     tag(BiomeTags.POLAR_BEARS_SPAWN_ON_ALTERNATE_BLOCKS)
/* 443 */       .add(Biomes.FROZEN_OCEAN)
/* 444 */       .add(Biomes.DEEP_FROZEN_OCEAN);
/*     */ 
/*     */     
/* 447 */     tag(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)
/* 448 */       .addTag(BiomeTags.IS_RIVER);
/*     */ 
/*     */     
/* 451 */     tag(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS)
/* 452 */       .add(Biomes.SWAMP)
/* 453 */       .add(Biomes.MANGROVE_SWAMP);
/*     */ 
/*     */     
/* 456 */     tag(BiomeTags.SPAWNS_SNOW_FOXES)
/* 457 */       .add(Biomes.SNOWY_PLAINS)
/* 458 */       .add(Biomes.ICE_SPIKES)
/* 459 */       .add(Biomes.FROZEN_OCEAN)
/* 460 */       .add(Biomes.SNOWY_TAIGA)
/* 461 */       .add(Biomes.FROZEN_RIVER)
/* 462 */       .add(Biomes.SNOWY_BEACH)
/* 463 */       .add(Biomes.FROZEN_PEAKS)
/* 464 */       .add(Biomes.JAGGED_PEAKS)
/* 465 */       .add(Biomes.SNOWY_SLOPES)
/* 466 */       .add(Biomes.GROVE);
/*     */ 
/*     */ 
/*     */     
/* 470 */     tag(BiomeTags.INCREASED_FIRE_BURNOUT)
/* 471 */       .add(Biomes.BAMBOO_JUNGLE)
/* 472 */       .add(Biomes.MUSHROOM_FIELDS)
/* 473 */       .add(Biomes.MANGROVE_SWAMP)
/* 474 */       .add(Biomes.SNOWY_SLOPES)
/* 475 */       .add(Biomes.FROZEN_PEAKS)
/* 476 */       .add(Biomes.JAGGED_PEAKS)
/* 477 */       .add(Biomes.SWAMP)
/* 478 */       .add(Biomes.JUNGLE);
/*     */     
/* 480 */     tag(BiomeTags.SNOW_GOLEM_MELTS)
/* 481 */       .add(Biomes.BADLANDS)
/* 482 */       .add(Biomes.BASALT_DELTAS)
/* 483 */       .add(Biomes.CRIMSON_FOREST)
/* 484 */       .add(Biomes.DESERT)
/* 485 */       .add(Biomes.ERODED_BADLANDS)
/* 486 */       .add(Biomes.NETHER_WASTES)
/* 487 */       .add(Biomes.SAVANNA)
/* 488 */       .add(Biomes.SAVANNA_PLATEAU)
/* 489 */       .add(Biomes.SOUL_SAND_VALLEY)
/* 490 */       .add(Biomes.WARPED_FOREST)
/* 491 */       .add(Biomes.WINDSWEPT_SAVANNA)
/* 492 */       .add(Biomes.WOODED_BADLANDS);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\BiomeTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */