/*     */ package net.minecraft.data.worldgen;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.util.random.SimpleWeightedRandomList;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBindings;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
/*     */ 
/*     */ public class TrialChambersStructurePools
/*     */ {
/*  19 */   public static final ResourceKey<StructureTemplatePool> START = Pools.createKey("trial_chambers/chamber/end");
/*  20 */   public static final ResourceKey<StructureTemplatePool> HALLWAY_FALLBACK = Pools.createKey("trial_chambers/hallway/fallback");
/*  21 */   public static final ResourceKey<StructureTemplatePool> CHAMBER_CAP_FALLBACK = Pools.createKey("trial_chambers/chamber/entrance_cap");
/*  22 */   public static final List<PoolAliasBinding> ALIAS_BINDINGS = (List<PoolAliasBinding>)ImmutableList.builder()
/*  23 */     .add(PoolAliasBinding.randomGroup(SimpleWeightedRandomList.builder()
/*  24 */         .add(List.of(
/*  25 */             PoolAliasBinding.direct(spawner("contents/ranged"), spawner("ranged/skeleton")), 
/*  26 */             PoolAliasBinding.direct(spawner("contents/slow_ranged"), spawner("slow_ranged/skeleton"))))
/*     */         
/*  28 */         .add(List.of(
/*  29 */             PoolAliasBinding.direct(spawner("contents/ranged"), spawner("ranged/stray")), 
/*  30 */             PoolAliasBinding.direct(spawner("contents/slow_ranged"), spawner("slow_ranged/stray"))))
/*     */         
/*  32 */         .add(List.of(
/*  33 */             PoolAliasBinding.direct(spawner("contents/ranged"), spawner("ranged/poison_skeleton")), 
/*  34 */             PoolAliasBinding.direct(spawner("contents/slow_ranged"), spawner("slow_ranged/poison_skeleton"))))
/*     */         
/*  36 */         .build()))
/*     */     
/*  38 */     .add(PoolAliasBinding.random(spawner("contents/melee"), SimpleWeightedRandomList.builder()
/*  39 */         .add(spawner("melee/zombie"))
/*  40 */         .add(spawner("melee/husk"))
/*  41 */         .add(spawner("melee/slime"))
/*  42 */         .build()))
/*     */     
/*  44 */     .add(PoolAliasBinding.random(spawner("contents/small_melee"), SimpleWeightedRandomList.builder()
/*  45 */         .add(spawner("small_melee/spider"))
/*  46 */         .add(spawner("small_melee/cave_spider"))
/*  47 */         .add(spawner("small_melee/silverfish"))
/*  48 */         .add(spawner("small_melee/baby_zombie"))
/*  49 */         .build()))
/*     */     
/*  51 */     .build();
/*     */   
/*     */   public static String spawner(String $$0) {
/*  54 */     return "trial_chambers/spawner/" + $$0;
/*     */   }
/*     */   
/*     */   public static void bootstrap(BootstapContext<StructureTemplatePool> $$0) {
/*  58 */     HolderGetter<StructureTemplatePool> $$1 = $$0.lookup(Registries.TEMPLATE_POOL);
/*  59 */     Holder.Reference reference1 = $$1.getOrThrow(Pools.EMPTY);
/*  60 */     Holder.Reference reference2 = $$1.getOrThrow(HALLWAY_FALLBACK);
/*  61 */     Holder.Reference reference3 = $$1.getOrThrow(CHAMBER_CAP_FALLBACK);
/*     */     
/*  63 */     HolderGetter<StructureProcessorList> $$5 = $$0.lookup(Registries.PROCESSOR_LIST);
/*  64 */     Holder.Reference reference4 = $$5.getOrThrow(UpdateOneTwentyOneProcessorLists.TRIAL_CHAMBERS_COPPER_BULB_DEGRADATION);
/*     */     
/*  66 */     $$0.register(START, new StructureTemplatePool((Holder)reference1, 
/*     */           
/*  68 */           List.of(
/*  69 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/end_1", (Holder)reference4), Integer.valueOf(1)), 
/*  70 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/end_2", (Holder)reference4), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     Pools.register($$0, "trial_chambers/chamber/entrance_cap", new StructureTemplatePool((Holder)reference3, 
/*     */           
/*  78 */           List.of(
/*  79 */             Pair.of(StructurePoolElement.single("trial_chambers/chamber/entrance_cap", (Holder)reference4), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     Pools.register($$0, "trial_chambers/chambers/end", new StructureTemplatePool((Holder)reference2, 
/*     */           
/*  86 */           List.of(
/*  87 */             Pair.of(StructurePoolElement.single("trial_chambers/chamber/chamber_1", (Holder)reference4), Integer.valueOf(1)), 
/*  88 */             Pair.of(StructurePoolElement.single("trial_chambers/chamber/chamber_5", (Holder)reference4), Integer.valueOf(1)), 
/*  89 */             Pair.of(StructurePoolElement.single("trial_chambers/chamber/chamber_6", (Holder)reference4), Integer.valueOf(1)), 
/*  90 */             Pair.of(StructurePoolElement.single("trial_chambers/chamber/chamber_9", (Holder)reference4), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     Pools.register($$0, "trial_chambers/chamber/addon", new StructureTemplatePool((Holder)reference1, 
/*     */           
/*  97 */           List.of(new Pair[] { 
/*  98 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/full_stacked_walkway"), Integer.valueOf(1)), 
/*  99 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/full_stacked_walkway_2"), Integer.valueOf(1)), 
/* 100 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/full_corner_column"), Integer.valueOf(1)), 
/* 101 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/full_column_ranged_spawner", (Holder)reference4), Integer.valueOf(1)), 
/* 102 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/middle_column_ranged_spawner", (Holder)reference4), Integer.valueOf(1)), 
/* 103 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/grate_bridge"), Integer.valueOf(1)), 
/* 104 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/hanging_platform"), Integer.valueOf(1)), 
/* 105 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/short_grate_platform"), Integer.valueOf(1)), 
/* 106 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/short_platform"), Integer.valueOf(1)), 
/* 107 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/lower_staircase_down"), Integer.valueOf(1)), 
/* 108 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/lower_walkway_platform"), Integer.valueOf(1)), 
/* 109 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/walkway_extension"), Integer.valueOf(1)), 
/* 110 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/side_walkway"), Integer.valueOf(1)), 
/* 111 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/closed_side_walkway"), Integer.valueOf(1)), 
/* 112 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/middle_walkway"), Integer.valueOf(1)), 
/* 113 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/10x15_rise"), Integer.valueOf(1)), 
/* 114 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/10x15_stacked_pathway"), Integer.valueOf(1)), 
/* 115 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/10x15_pathway_3"), Integer.valueOf(1)), 
/* 116 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/platform_with_space"), Integer.valueOf(1)), 
/* 117 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/stairs_with_space"), Integer.valueOf(1)), 
/* 118 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/stairs_with_space_2"), Integer.valueOf(1)), 
/* 119 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c3_side_walkway_1"), Integer.valueOf(1)), 
/* 120 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c3_side_walkway_2"), Integer.valueOf(1)), 
/* 121 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/walkway_with_bridge_1"), Integer.valueOf(1)), 
/* 122 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/corner_room_1"), Integer.valueOf(1)), 
/* 123 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_wide_platform"), Integer.valueOf(1)), 
/* 124 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c1_breeze"), Integer.valueOf(1)) }), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     Pools.register($$0, "trial_chambers/chamber/addon/c6", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 131 */           List.of(new Pair[] { 
/* 132 */               Pair.of(StructurePoolElement.empty(), Integer.valueOf(3)), 
/* 133 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_cover_small_1"), Integer.valueOf(2)), 
/* 134 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_cover_small_2"), Integer.valueOf(2)), 
/* 135 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_cover_small_3"), Integer.valueOf(2)), 
/* 136 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_column_full"), Integer.valueOf(1)), 
/* 137 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_column_tall"), Integer.valueOf(1)), 
/* 138 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_column_tall_wide"), Integer.valueOf(1)), 
/* 139 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_ranged_column_short"), Integer.valueOf(1)), 
/* 140 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_ranged_column_short_wide"), Integer.valueOf(1)), 
/* 141 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_chest"), Integer.valueOf(1)), 
/* 142 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_melee_spawner"), Integer.valueOf(1)), 
/* 143 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_cover"), Integer.valueOf(1)), 
/* 144 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_cover_long"), Integer.valueOf(1)), 
/* 145 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_cover_short"), Integer.valueOf(1)), 
/* 146 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_cover_small"), Integer.valueOf(1)), 
/* 147 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/addon/c6_stairs"), Integer.valueOf(1)) }), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     Pools.register($$0, "trial_chambers/corridor/second", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 154 */           List.of(
/* 155 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/second_plate"), Integer.valueOf(1)), 
/* 156 */             Pair.of(StructurePoolElement.single("trial_chambers/intersection/intersection_1", (Holder)reference4), Integer.valueOf(1)), 
/* 157 */             Pair.of(StructurePoolElement.single("trial_chambers/intersection/intersection_2", (Holder)reference4), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     Pools.register($$0, "trial_chambers/corridor/first", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 165 */           List.of(
/* 166 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/first_plate"), Integer.valueOf(1)), 
/* 167 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/entrance_1", (Holder)reference4), Integer.valueOf(2))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     Pools.register($$0, "trial_chambers/corridor/first/straight", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 174 */           List.of(
/* 175 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/first_plate"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     Pools.register($$0, "trial_chambers/corridor", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 182 */           List.of(
/* 183 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/straight_1", (Holder)reference4), Integer.valueOf(1)), 
/* 184 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/straight_2", (Holder)reference4), Integer.valueOf(2)), 
/* 185 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/straight_3", (Holder)reference4), Integer.valueOf(2)), 
/* 186 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/straight_4", (Holder)reference4), Integer.valueOf(2)), 
/* 187 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/straight_5", (Holder)reference4), Integer.valueOf(2)), 
/* 188 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/straight_6", (Holder)reference4), Integer.valueOf(2)), 
/* 189 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/straight_7", (Holder)reference4), Integer.valueOf(2)), 
/* 190 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/straight_8", (Holder)reference4), Integer.valueOf(2))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     $$0.register(HALLWAY_FALLBACK, new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 197 */           List.of(
/* 198 */             Pair.of(StructurePoolElement.single("trial_chambers/hallway/rubble"), Integer.valueOf(1)), 
/* 199 */             Pair.of(StructurePoolElement.single("trial_chambers/hallway/rubble_chamber"), Integer.valueOf(1)), 
/* 200 */             Pair.of(StructurePoolElement.single("trial_chambers/hallway/rubble_thin"), Integer.valueOf(1)), 
/* 201 */             Pair.of(StructurePoolElement.single("trial_chambers/hallway/rubble_chamber_thin"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     Pools.register($$0, "trial_chambers/hallway", new StructureTemplatePool((Holder)reference2, 
/*     */           
/* 208 */           List.of(new Pair[] {
/*     */               
/* 210 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/corridor_connector_1"), Integer.valueOf(1)), 
/* 211 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/upper_hallway_connector", (Holder)reference4), Integer.valueOf(1)), 
/* 212 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/lower_hallway_connector", (Holder)reference4), Integer.valueOf(1)), 
/*     */               
/* 214 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/rubble"), Integer.valueOf(1)), 
/*     */ 
/*     */               
/* 217 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/chamber_1", (Holder)reference4), Integer.valueOf(150)), 
/* 218 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/chamber_2", (Holder)reference4), Integer.valueOf(150)), 
/* 219 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/chamber_3", (Holder)reference4), Integer.valueOf(150)), 
/* 220 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/chamber_4", (Holder)reference4), Integer.valueOf(150)), 
/* 221 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/chamber_5", (Holder)reference4), Integer.valueOf(150)), 
/* 222 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/chamber_6", (Holder)reference4), Integer.valueOf(150)), 
/* 223 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/chamber_8", (Holder)reference4), Integer.valueOf(150)), 
/* 224 */               Pair.of(StructurePoolElement.single("trial_chambers/chamber/chamber_9", (Holder)reference4), Integer.valueOf(150)), 
/*     */ 
/*     */               
/* 227 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/rubble_chamber", (Holder)reference4), Integer.valueOf(10)), 
/* 228 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/rubble_chamber_thin", (Holder)reference4), Integer.valueOf(1)), 
/*     */ 
/*     */               
/* 231 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/cache_1", (Holder)reference4), Integer.valueOf(1)), 
/* 232 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/left_corner", (Holder)reference4), Integer.valueOf(1)), 
/* 233 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/right_corner", (Holder)reference4), Integer.valueOf(1)), 
/* 234 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/corner_staircase", (Holder)reference4), Integer.valueOf(1)), 
/* 235 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/corner_staircase_down", (Holder)reference4), Integer.valueOf(1)), 
/* 236 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/long_straight_staircase", (Holder)reference4), Integer.valueOf(1)), 
/* 237 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/long_straight_staircase_down", (Holder)reference4), Integer.valueOf(1)), 
/* 238 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/straight", (Holder)reference4), Integer.valueOf(1)), 
/* 239 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/straight_staircase", (Holder)reference4), Integer.valueOf(1)), 
/* 240 */               Pair.of(StructurePoolElement.single("trial_chambers/hallway/straight_staircase_down", (Holder)reference4), Integer.valueOf(1))
/*     */             }), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */     
/* 245 */     Pools.register($$0, "trial_chambers/corridors/addon/lower", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 247 */           List.of(
/* 248 */             Pair.of(StructurePoolElement.empty(), Integer.valueOf(8)), 
/* 249 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/addon/staircase"), Integer.valueOf(1)), 
/* 250 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/addon/wall"), Integer.valueOf(1)), 
/* 251 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/addon/ladder_to_middle"), Integer.valueOf(1)), 
/* 252 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/addon/arrow_dispenser"), Integer.valueOf(1)), 
/* 253 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/addon/bridge_lower"), Integer.valueOf(2))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     Pools.register($$0, "trial_chambers/corridors/addon/middle", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 260 */           List.of(
/* 261 */             Pair.of(StructurePoolElement.empty(), Integer.valueOf(8)), 
/* 262 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/addon/open_walkway"), Integer.valueOf(2)), 
/* 263 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/addon/walled_walkway"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 268 */     Pools.register($$0, "trial_chambers/corridors/addon/middle_upper", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 270 */           List.of(
/* 271 */             Pair.of(StructurePoolElement.empty(), Integer.valueOf(6)), 
/* 272 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/addon/open_walkway_upper"), Integer.valueOf(2)), 
/* 273 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/addon/chandelier_upper"), Integer.valueOf(1)), 
/* 274 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/addon/decoration_upper"), Integer.valueOf(1)), 
/* 275 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/addon/head_upper"), Integer.valueOf(1)), 
/* 276 */             Pair.of(StructurePoolElement.single("trial_chambers/corridor/addon/reward_upper"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     Pools.register($$0, "trial_chambers/decor", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 283 */           List.of(
/* 284 */             Pair.of(StructurePoolElement.empty(), Integer.valueOf(22)), 
/* 285 */             Pair.of(StructurePoolElement.single("trial_chambers/decor/empty_pot"), Integer.valueOf(2)), 
/* 286 */             Pair.of(StructurePoolElement.single("trial_chambers/decor/dead_bush_pot"), Integer.valueOf(2)), 
/* 287 */             Pair.of(StructurePoolElement.single("trial_chambers/decor/undecorated_pot"), Integer.valueOf(3)), 
/* 288 */             Pair.of(StructurePoolElement.single("trial_chambers/decor/candle_1"), Integer.valueOf(1)), 
/* 289 */             Pair.of(StructurePoolElement.single("trial_chambers/decor/candle_2"), Integer.valueOf(1)), 
/* 290 */             Pair.of(StructurePoolElement.single("trial_chambers/decor/candle_3"), Integer.valueOf(1)), 
/* 291 */             Pair.of(StructurePoolElement.single("trial_chambers/decor/candle_4"), Integer.valueOf(1)), 
/* 292 */             Pair.of(StructurePoolElement.single("trial_chambers/decor/barrel"), Integer.valueOf(2))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 297 */     Pools.register($$0, "trial_chambers/decor_chamber", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 299 */           List.of(
/* 300 */             Pair.of(StructurePoolElement.empty(), Integer.valueOf(4)), 
/* 301 */             Pair.of(StructurePoolElement.single("trial_chambers/decor/undecorated_pot"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 306 */     Pools.register($$0, "trial_chambers/reward/all", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 308 */           List.of(
/* 309 */             Pair.of(StructurePoolElement.single("trial_chambers/reward/connectors/default"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     Pools.register($$0, "trial_chambers/reward/contents/default", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 316 */           List.of(
/* 317 */             Pair.of(StructurePoolElement.single("trial_chambers/reward/default"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     Pools.register($$0, "trial_chambers/chests/supply", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 324 */           List.of(
/* 325 */             Pair.of(StructurePoolElement.single("trial_chambers/chests/connectors/supply"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     Pools.register($$0, "trial_chambers/chests/contents/supply", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 332 */           List.of(
/* 333 */             Pair.of(StructurePoolElement.single("trial_chambers/chests/supply"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 338 */     Pools.register($$0, "trial_chambers/spawner/ranged", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 340 */           List.of(
/* 341 */             Pair.of(StructurePoolElement.single("trial_chambers/spawner/connectors/ranged"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 346 */     Pools.register($$0, "trial_chambers/spawner/slow_ranged", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 348 */           List.of(
/* 349 */             Pair.of(StructurePoolElement.single("trial_chambers/spawner/connectors/slow_ranged"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 354 */     Pools.register($$0, "trial_chambers/spawner/melee", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 356 */           List.of(
/* 357 */             Pair.of(StructurePoolElement.single("trial_chambers/spawner/connectors/melee"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 362 */     Pools.register($$0, "trial_chambers/spawner/small_melee", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 364 */           List.of(
/* 365 */             Pair.of(StructurePoolElement.single("trial_chambers/spawner/connectors/small_melee"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 370 */     Pools.register($$0, "trial_chambers/spawner/breeze", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 372 */           List.of(
/* 373 */             Pair.of(StructurePoolElement.single("trial_chambers/spawner/connectors/breeze"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     Pools.register($$0, "trial_chambers/spawner/all", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 380 */           List.of(
/* 381 */             Pair.of(StructurePoolElement.empty(), Integer.valueOf(10)), 
/* 382 */             Pair.of(StructurePoolElement.single("trial_chambers/spawner/connectors/ranged"), Integer.valueOf(1)), 
/* 383 */             Pair.of(StructurePoolElement.single("trial_chambers/spawner/connectors/melee"), Integer.valueOf(1)), 
/* 384 */             Pair.of(StructurePoolElement.single("trial_chambers/spawner/connectors/small_melee"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 389 */     Pools.register($$0, "trial_chambers/spawner/contents/breeze", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 391 */           List.of(
/* 392 */             Pair.of(StructurePoolElement.single("trial_chambers/spawner/breeze/breeze"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 397 */     Pools.register($$0, "trial_chambers/dispensers/chamber", new StructureTemplatePool((Holder)reference1, 
/*     */           
/* 399 */           List.of(
/* 400 */             Pair.of(StructurePoolElement.empty(), Integer.valueOf(1)), 
/* 401 */             Pair.of(StructurePoolElement.single("trial_chambers/dispensers/chamber"), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 406 */     PoolAliasBindings.registerTargetsAsPools($$0, (Holder)reference1, ALIAS_BINDINGS);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\TrialChambersStructurePools.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */