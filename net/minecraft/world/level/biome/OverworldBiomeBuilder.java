/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.registries.VanillaRegistries;
/*     */ import net.minecraft.data.worldgen.TerrainProvider;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.util.CubicSpline;
/*     */ import net.minecraft.util.ToFloatFunction;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.world.level.levelgen.DensityFunction;
/*     */ import net.minecraft.world.level.levelgen.DensityFunctions;
/*     */ import net.minecraft.world.level.levelgen.NoiseRouterData;
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
/*     */ public final class OverworldBiomeBuilder
/*     */ {
/*     */   private static final float VALLEY_SIZE = 0.05F;
/*     */   private static final float LOW_START = 0.26666668F;
/*     */   public static final float HIGH_START = 0.4F;
/*     */   private static final float HIGH_END = 0.93333334F;
/*     */   private static final float PEAK_SIZE = 0.1F;
/*     */   public static final float PEAK_START = 0.56666666F;
/*     */   private static final float PEAK_END = 0.7666667F;
/*     */   public static final float NEAR_INLAND_START = -0.11F;
/*     */   public static final float MID_INLAND_START = 0.03F;
/*     */   public static final float FAR_INLAND_START = 0.3F;
/*     */   public static final float EROSION_INDEX_1_START = -0.78F;
/*     */   public static final float EROSION_INDEX_2_START = -0.375F;
/*     */   private static final float EROSION_DEEP_DARK_DRYNESS_THRESHOLD = -0.225F;
/*     */   private static final float DEPTH_DEEP_DARK_DRYNESS_THRESHOLD = 0.9F;
/*  60 */   private final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
/*     */   
/*  62 */   private final Climate.Parameter[] temperatures = new Climate.Parameter[] {
/*  63 */       Climate.Parameter.span(-1.0F, -0.45F), 
/*  64 */       Climate.Parameter.span(-0.45F, -0.15F), 
/*  65 */       Climate.Parameter.span(-0.15F, 0.2F), 
/*  66 */       Climate.Parameter.span(0.2F, 0.55F), 
/*  67 */       Climate.Parameter.span(0.55F, 1.0F)
/*     */     };
/*  69 */   private final Climate.Parameter[] humidities = new Climate.Parameter[] {
/*  70 */       Climate.Parameter.span(-1.0F, -0.35F), 
/*  71 */       Climate.Parameter.span(-0.35F, -0.1F), 
/*  72 */       Climate.Parameter.span(-0.1F, 0.1F), 
/*  73 */       Climate.Parameter.span(0.1F, 0.3F), 
/*  74 */       Climate.Parameter.span(0.3F, 1.0F)
/*     */     };
/*     */   
/*  77 */   private final Climate.Parameter[] erosions = new Climate.Parameter[] {
/*  78 */       Climate.Parameter.span(-1.0F, -0.78F), 
/*  79 */       Climate.Parameter.span(-0.78F, -0.375F), 
/*  80 */       Climate.Parameter.span(-0.375F, -0.2225F), 
/*  81 */       Climate.Parameter.span(-0.2225F, 0.05F), 
/*  82 */       Climate.Parameter.span(0.05F, 0.45F), 
/*  83 */       Climate.Parameter.span(0.45F, 0.55F), 
/*  84 */       Climate.Parameter.span(0.55F, 1.0F)
/*     */     };
/*     */   
/*  87 */   private final Climate.Parameter FROZEN_RANGE = this.temperatures[0];
/*  88 */   private final Climate.Parameter UNFROZEN_RANGE = Climate.Parameter.span(this.temperatures[1], this.temperatures[4]);
/*     */   
/*  90 */   private final Climate.Parameter mushroomFieldsContinentalness = Climate.Parameter.span(-1.2F, -1.05F);
/*  91 */   private final Climate.Parameter deepOceanContinentalness = Climate.Parameter.span(-1.05F, -0.455F);
/*  92 */   private final Climate.Parameter oceanContinentalness = Climate.Parameter.span(-0.455F, -0.19F);
/*  93 */   private final Climate.Parameter coastContinentalness = Climate.Parameter.span(-0.19F, -0.11F);
/*  94 */   private final Climate.Parameter inlandContinentalness = Climate.Parameter.span(-0.11F, 0.55F);
/*     */   
/*  96 */   private final Climate.Parameter nearInlandContinentalness = Climate.Parameter.span(-0.11F, 0.03F);
/*  97 */   private final Climate.Parameter midInlandContinentalness = Climate.Parameter.span(0.03F, 0.3F);
/*  98 */   private final Climate.Parameter farInlandContinentalness = Climate.Parameter.span(0.3F, 1.0F);
/*     */ 
/*     */   
/* 101 */   private final ResourceKey<Biome>[][] OCEANS = new ResourceKey[][] { { Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.WARM_OCEAN }, { Biomes.FROZEN_OCEAN, Biomes.COLD_OCEAN, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private final ResourceKey<Biome>[][] MIDDLE_BIOMES = new ResourceKey[][] { { Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.TAIGA }, { Biomes.PLAINS, Biomes.PLAINS, Biomes.FOREST, Biomes.TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA }, { Biomes.FLOWER_FOREST, Biomes.PLAINS, Biomes.FOREST, Biomes.BIRCH_FOREST, Biomes.DARK_FOREST }, { Biomes.SAVANNA, Biomes.SAVANNA, Biomes.FOREST, Biomes.JUNGLE, Biomes.JUNGLE }, { Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, Biomes.DESERT } };
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
/* 119 */   private final ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT = new ResourceKey[][] { { Biomes.ICE_SPIKES, null, Biomes.SNOWY_TAIGA, null, null }, { null, null, null, null, Biomes.OLD_GROWTH_PINE_TAIGA }, { Biomes.SUNFLOWER_PLAINS, null, null, Biomes.OLD_GROWTH_BIRCH_FOREST, null }, { null, null, Biomes.PLAINS, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE }, { null, null, null, null, null } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   private final ResourceKey<Biome>[][] PLATEAU_BIOMES = new ResourceKey[][] { { Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_PLAINS, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA }, { Biomes.MEADOW, Biomes.MEADOW, Biomes.FOREST, Biomes.TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA }, { Biomes.MEADOW, Biomes.MEADOW, Biomes.MEADOW, Biomes.MEADOW, Biomes.DARK_FOREST }, { Biomes.SAVANNA_PLATEAU, Biomes.SAVANNA_PLATEAU, Biomes.FOREST, Biomes.FOREST, Biomes.JUNGLE }, { Biomes.BADLANDS, Biomes.BADLANDS, Biomes.BADLANDS, Biomes.WOODED_BADLANDS, Biomes.WOODED_BADLANDS } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   private final ResourceKey<Biome>[][] PLATEAU_BIOMES_VARIANT = new ResourceKey[][] { { Biomes.ICE_SPIKES, null, null, null, null }, { Biomes.CHERRY_GROVE, null, Biomes.MEADOW, Biomes.MEADOW, Biomes.OLD_GROWTH_PINE_TAIGA }, { Biomes.CHERRY_GROVE, Biomes.CHERRY_GROVE, Biomes.FOREST, Biomes.BIRCH_FOREST, null }, { null, null, null, null, null }, { Biomes.ERODED_BADLANDS, Biomes.ERODED_BADLANDS, null, null, null } };
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
/* 150 */   private final ResourceKey<Biome>[][] SHATTERED_BIOMES = new ResourceKey[][] { { Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_FOREST }, { Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_FOREST }, { Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_FOREST }, { null, null, null, null, null }, { null, null, null, null, null } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Climate.ParameterPoint> spawnTarget() {
/* 159 */     Climate.Parameter $$0 = Climate.Parameter.point(0.0F);
/* 160 */     float $$1 = 0.16F;
/* 161 */     return List.of(new Climate.ParameterPoint(this.FULL_RANGE, this.FULL_RANGE, 
/*     */ 
/*     */ 
/*     */           
/* 165 */           Climate.Parameter.span(this.inlandContinentalness, this.FULL_RANGE), this.FULL_RANGE, $$0, 
/*     */ 
/*     */           
/* 168 */           Climate.Parameter.span(-1.0F, -0.16F), 0L), new Climate.ParameterPoint(this.FULL_RANGE, this.FULL_RANGE, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 174 */           Climate.Parameter.span(this.inlandContinentalness, this.FULL_RANGE), this.FULL_RANGE, $$0, 
/*     */ 
/*     */           
/* 177 */           Climate.Parameter.span(0.16F, 1.0F), 0L));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> $$0) {
/* 184 */     if (SharedConstants.debugGenerateSquareTerrainWithoutNoise) {
/* 185 */       addDebugBiomes($$0);
/*     */       
/*     */       return;
/*     */     } 
/* 189 */     addOffCoastBiomes($$0);
/* 190 */     addInlandBiomes($$0);
/* 191 */     addUndergroundBiomes($$0);
/*     */   }
/*     */   
/*     */   private void addDebugBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> $$0) {
/* 195 */     HolderLookup.Provider $$1 = VanillaRegistries.createLookup();
/* 196 */     HolderLookup.RegistryLookup registryLookup = $$1.lookupOrThrow(Registries.DENSITY_FUNCTION);
/* 197 */     DensityFunctions.Spline.Coordinate $$3 = new DensityFunctions.Spline.Coordinate((Holder)registryLookup.getOrThrow(NoiseRouterData.CONTINENTS));
/* 198 */     DensityFunctions.Spline.Coordinate $$4 = new DensityFunctions.Spline.Coordinate((Holder)registryLookup.getOrThrow(NoiseRouterData.EROSION));
/* 199 */     DensityFunctions.Spline.Coordinate $$5 = new DensityFunctions.Spline.Coordinate((Holder)registryLookup.getOrThrow(NoiseRouterData.RIDGES_FOLDED));
/*     */     
/* 201 */     $$0.accept(Pair.of(Climate.parameters(this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.point(0.0F), this.FULL_RANGE, 0.01F), Biomes.PLAINS));
/*     */     
/* 203 */     CubicSpline<?, ?> $$6 = TerrainProvider.buildErosionOffsetSpline((ToFloatFunction)$$4, (ToFloatFunction)$$5, -0.15F, 0.0F, 0.0F, 0.1F, 0.0F, -0.03F, false, false, ToFloatFunction.IDENTITY);
/* 204 */     if ($$6 instanceof CubicSpline.Multipoint) { CubicSpline.Multipoint<?, ?> $$7 = (CubicSpline.Multipoint<?, ?>)$$6;
/* 205 */       ResourceKey<Biome> $$8 = Biomes.DESERT;
/* 206 */       for (float $$9 : $$7.locations()) {
/* 207 */         $$0.accept(Pair.of(Climate.parameters(this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.point($$9), Climate.Parameter.point(0.0F), this.FULL_RANGE, 0.0F), $$8));
/* 208 */         $$8 = ($$8 == Biomes.DESERT) ? Biomes.BADLANDS : Biomes.DESERT;
/*     */       }  }
/*     */ 
/*     */     
/* 212 */     CubicSpline<?, ?> $$10 = TerrainProvider.overworldOffset((ToFloatFunction)$$3, (ToFloatFunction)$$4, (ToFloatFunction)$$5, false);
/* 213 */     if ($$10 instanceof CubicSpline.Multipoint) { CubicSpline.Multipoint<?, ?> $$11 = (CubicSpline.Multipoint<?, ?>)$$10;
/* 214 */       for (float $$12 : $$11.locations()) {
/* 215 */         $$0.accept(Pair.of(Climate.parameters(this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.point($$12), this.FULL_RANGE, Climate.Parameter.point(0.0F), this.FULL_RANGE, 0.0F), Biomes.SNOWY_TAIGA));
/*     */       } }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addOffCoastBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> $$0) {
/* 224 */     addSurfaceBiome($$0, this.FULL_RANGE, this.FULL_RANGE, this.mushroomFieldsContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, Biomes.MUSHROOM_FIELDS);
/* 225 */     for (int $$1 = 0; $$1 < this.temperatures.length; $$1++) {
/* 226 */       Climate.Parameter $$2 = this.temperatures[$$1];
/*     */       
/* 228 */       addSurfaceBiome($$0, $$2, this.FULL_RANGE, this.deepOceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, this.OCEANS[0][$$1]);
/* 229 */       addSurfaceBiome($$0, $$2, this.FULL_RANGE, this.oceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, this.OCEANS[1][$$1]);
/*     */     } 
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
/*     */   private void addInlandBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> $$0) {
/* 249 */     addMidSlice($$0, Climate.Parameter.span(-1.0F, -0.93333334F));
/*     */ 
/*     */     
/* 252 */     addHighSlice($$0, Climate.Parameter.span(-0.93333334F, -0.7666667F));
/* 253 */     addPeaks($$0, Climate.Parameter.span(-0.7666667F, -0.56666666F));
/* 254 */     addHighSlice($$0, Climate.Parameter.span(-0.56666666F, -0.4F));
/*     */ 
/*     */     
/* 257 */     addMidSlice($$0, Climate.Parameter.span(-0.4F, -0.26666668F));
/*     */ 
/*     */     
/* 260 */     addLowSlice($$0, Climate.Parameter.span(-0.26666668F, -0.05F));
/* 261 */     addValleys($$0, Climate.Parameter.span(-0.05F, 0.05F));
/* 262 */     addLowSlice($$0, Climate.Parameter.span(0.05F, 0.26666668F));
/*     */ 
/*     */     
/* 265 */     addMidSlice($$0, Climate.Parameter.span(0.26666668F, 0.4F));
/*     */ 
/*     */     
/* 268 */     addHighSlice($$0, Climate.Parameter.span(0.4F, 0.56666666F));
/* 269 */     addPeaks($$0, Climate.Parameter.span(0.56666666F, 0.7666667F));
/* 270 */     addHighSlice($$0, Climate.Parameter.span(0.7666667F, 0.93333334F));
/*     */ 
/*     */     
/* 273 */     addMidSlice($$0, Climate.Parameter.span(0.93333334F, 1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addPeaks(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> $$0, Climate.Parameter $$1) {
/* 281 */     for (int $$2 = 0; $$2 < this.temperatures.length; $$2++) {
/* 282 */       Climate.Parameter $$3 = this.temperatures[$$2];
/* 283 */       for (int $$4 = 0; $$4 < this.humidities.length; $$4++) {
/* 284 */         Climate.Parameter $$5 = this.humidities[$$4];
/*     */         
/* 286 */         ResourceKey<Biome> $$6 = pickMiddleBiome($$2, $$4, $$1);
/* 287 */         ResourceKey<Biome> $$7 = pickMiddleBiomeOrBadlandsIfHot($$2, $$4, $$1);
/* 288 */         ResourceKey<Biome> $$8 = pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold($$2, $$4, $$1);
/* 289 */         ResourceKey<Biome> $$9 = pickPlateauBiome($$2, $$4, $$1);
/* 290 */         ResourceKey<Biome> $$10 = pickShatteredBiome($$2, $$4, $$1);
/* 291 */         ResourceKey<Biome> $$11 = maybePickWindsweptSavannaBiome($$2, $$4, $$1, $$10);
/* 292 */         ResourceKey<Biome> $$12 = pickPeakBiome($$2, $$4, $$1);
/*     */         
/* 294 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[0], $$1, 0.0F, $$12);
/*     */         
/* 296 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[1], $$1, 0.0F, $$8);
/*     */         
/* 298 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[1], $$1, 0.0F, $$12);
/*     */         
/* 300 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), $$1, 0.0F, $$6);
/* 301 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], $$1, 0.0F, $$9);
/*     */         
/* 303 */         addSurfaceBiome($$0, $$3, $$5, this.midInlandContinentalness, this.erosions[3], $$1, 0.0F, $$7);
/* 304 */         addSurfaceBiome($$0, $$3, $$5, this.farInlandContinentalness, this.erosions[3], $$1, 0.0F, $$9);
/*     */         
/* 306 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], $$1, 0.0F, $$6);
/*     */         
/* 308 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[5], $$1, 0.0F, $$11);
/* 309 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], $$1, 0.0F, $$10);
/*     */         
/* 311 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], $$1, 0.0F, $$6);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addHighSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> $$0, Climate.Parameter $$1) {
/* 322 */     for (int $$2 = 0; $$2 < this.temperatures.length; $$2++) {
/* 323 */       Climate.Parameter $$3 = this.temperatures[$$2];
/* 324 */       for (int $$4 = 0; $$4 < this.humidities.length; $$4++) {
/* 325 */         Climate.Parameter $$5 = this.humidities[$$4];
/*     */         
/* 327 */         ResourceKey<Biome> $$6 = pickMiddleBiome($$2, $$4, $$1);
/* 328 */         ResourceKey<Biome> $$7 = pickMiddleBiomeOrBadlandsIfHot($$2, $$4, $$1);
/* 329 */         ResourceKey<Biome> $$8 = pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold($$2, $$4, $$1);
/* 330 */         ResourceKey<Biome> $$9 = pickPlateauBiome($$2, $$4, $$1);
/* 331 */         ResourceKey<Biome> $$10 = pickShatteredBiome($$2, $$4, $$1);
/* 332 */         ResourceKey<Biome> $$11 = maybePickWindsweptSavannaBiome($$2, $$4, $$1, $$6);
/* 333 */         ResourceKey<Biome> $$12 = pickSlopeBiome($$2, $$4, $$1);
/* 334 */         ResourceKey<Biome> $$13 = pickPeakBiome($$2, $$4, $$1);
/*     */         
/* 336 */         addSurfaceBiome($$0, $$3, $$5, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), $$1, 0.0F, $$6);
/* 337 */         addSurfaceBiome($$0, $$3, $$5, this.nearInlandContinentalness, this.erosions[0], $$1, 0.0F, $$12);
/* 338 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[0], $$1, 0.0F, $$13);
/*     */         
/* 340 */         addSurfaceBiome($$0, $$3, $$5, this.nearInlandContinentalness, this.erosions[1], $$1, 0.0F, $$8);
/* 341 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[1], $$1, 0.0F, $$12);
/*     */         
/* 343 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), $$1, 0.0F, $$6);
/* 344 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], $$1, 0.0F, $$9);
/*     */         
/* 346 */         addSurfaceBiome($$0, $$3, $$5, this.midInlandContinentalness, this.erosions[3], $$1, 0.0F, $$7);
/* 347 */         addSurfaceBiome($$0, $$3, $$5, this.farInlandContinentalness, this.erosions[3], $$1, 0.0F, $$9);
/*     */         
/* 349 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], $$1, 0.0F, $$6);
/*     */         
/* 351 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[5], $$1, 0.0F, $$11);
/* 352 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], $$1, 0.0F, $$10);
/*     */         
/* 354 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], $$1, 0.0F, $$6);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addMidSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> $$0, Climate.Parameter $$1) {
/* 364 */     addSurfaceBiome($$0, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), $$1, 0.0F, Biomes.STONY_SHORE);
/* 365 */     addSurfaceBiome($$0, Climate.Parameter.span(this.temperatures[1], this.temperatures[2]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], $$1, 0.0F, Biomes.SWAMP);
/* 366 */     addSurfaceBiome($$0, Climate.Parameter.span(this.temperatures[3], this.temperatures[4]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], $$1, 0.0F, Biomes.MANGROVE_SWAMP);
/*     */     
/* 368 */     for (int $$2 = 0; $$2 < this.temperatures.length; $$2++) {
/* 369 */       Climate.Parameter $$3 = this.temperatures[$$2];
/* 370 */       for (int $$4 = 0; $$4 < this.humidities.length; $$4++) {
/* 371 */         Climate.Parameter $$5 = this.humidities[$$4];
/*     */         
/* 373 */         ResourceKey<Biome> $$6 = pickMiddleBiome($$2, $$4, $$1);
/* 374 */         ResourceKey<Biome> $$7 = pickMiddleBiomeOrBadlandsIfHot($$2, $$4, $$1);
/* 375 */         ResourceKey<Biome> $$8 = pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold($$2, $$4, $$1);
/* 376 */         ResourceKey<Biome> $$9 = pickShatteredBiome($$2, $$4, $$1);
/* 377 */         ResourceKey<Biome> $$10 = pickPlateauBiome($$2, $$4, $$1);
/* 378 */         ResourceKey<Biome> $$11 = pickBeachBiome($$2, $$4);
/* 379 */         ResourceKey<Biome> $$12 = maybePickWindsweptSavannaBiome($$2, $$4, $$1, $$6);
/* 380 */         ResourceKey<Biome> $$13 = pickShatteredCoastBiome($$2, $$4, $$1);
/* 381 */         ResourceKey<Biome> $$14 = pickSlopeBiome($$2, $$4, $$1);
/*     */         
/* 383 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[0], $$1, 0.0F, $$14);
/*     */         
/* 385 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[1], $$1, 0.0F, $$8);
/* 386 */         addSurfaceBiome($$0, $$3, $$5, this.farInlandContinentalness, this.erosions[1], $$1, 0.0F, ($$2 == 0) ? $$14 : $$10);
/*     */         
/* 388 */         addSurfaceBiome($$0, $$3, $$5, this.nearInlandContinentalness, this.erosions[2], $$1, 0.0F, $$6);
/* 389 */         addSurfaceBiome($$0, $$3, $$5, this.midInlandContinentalness, this.erosions[2], $$1, 0.0F, $$7);
/* 390 */         addSurfaceBiome($$0, $$3, $$5, this.farInlandContinentalness, this.erosions[2], $$1, 0.0F, $$10);
/*     */         
/* 392 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[3], $$1, 0.0F, $$6);
/* 393 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[3], $$1, 0.0F, $$7);
/*     */         
/* 395 */         if ($$1.max() < 0L) {
/* 396 */           addSurfaceBiome($$0, $$3, $$5, this.coastContinentalness, this.erosions[4], $$1, 0.0F, $$11);
/* 397 */           addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], $$1, 0.0F, $$6);
/*     */         } else {
/* 399 */           addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], $$1, 0.0F, $$6);
/*     */         } 
/*     */         
/* 402 */         addSurfaceBiome($$0, $$3, $$5, this.coastContinentalness, this.erosions[5], $$1, 0.0F, $$13);
/* 403 */         addSurfaceBiome($$0, $$3, $$5, this.nearInlandContinentalness, this.erosions[5], $$1, 0.0F, $$12);
/* 404 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], $$1, 0.0F, $$9);
/*     */         
/* 406 */         if ($$1.max() < 0L) {
/* 407 */           addSurfaceBiome($$0, $$3, $$5, this.coastContinentalness, this.erosions[6], $$1, 0.0F, $$11);
/*     */         } else {
/* 409 */           addSurfaceBiome($$0, $$3, $$5, this.coastContinentalness, this.erosions[6], $$1, 0.0F, $$6);
/*     */         } 
/*     */ 
/*     */         
/* 413 */         if ($$2 == 0) {
/* 414 */           addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], $$1, 0.0F, $$6);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> $$0, Climate.Parameter $$1) {
/* 425 */     addSurfaceBiome($$0, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), $$1, 0.0F, Biomes.STONY_SHORE);
/* 426 */     addSurfaceBiome($$0, Climate.Parameter.span(this.temperatures[1], this.temperatures[2]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], $$1, 0.0F, Biomes.SWAMP);
/* 427 */     addSurfaceBiome($$0, Climate.Parameter.span(this.temperatures[3], this.temperatures[4]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], $$1, 0.0F, Biomes.MANGROVE_SWAMP);
/*     */     
/* 429 */     for (int $$2 = 0; $$2 < this.temperatures.length; $$2++) {
/* 430 */       Climate.Parameter $$3 = this.temperatures[$$2];
/* 431 */       for (int $$4 = 0; $$4 < this.humidities.length; $$4++) {
/* 432 */         Climate.Parameter $$5 = this.humidities[$$4];
/*     */         
/* 434 */         ResourceKey<Biome> $$6 = pickMiddleBiome($$2, $$4, $$1);
/* 435 */         ResourceKey<Biome> $$7 = pickMiddleBiomeOrBadlandsIfHot($$2, $$4, $$1);
/* 436 */         ResourceKey<Biome> $$8 = pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold($$2, $$4, $$1);
/* 437 */         ResourceKey<Biome> $$9 = pickBeachBiome($$2, $$4);
/* 438 */         ResourceKey<Biome> $$10 = maybePickWindsweptSavannaBiome($$2, $$4, $$1, $$6);
/* 439 */         ResourceKey<Biome> $$11 = pickShatteredCoastBiome($$2, $$4, $$1);
/*     */         
/* 441 */         addSurfaceBiome($$0, $$3, $$5, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), $$1, 0.0F, $$7);
/* 442 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[0], this.erosions[1]), $$1, 0.0F, $$8);
/*     */         
/* 444 */         addSurfaceBiome($$0, $$3, $$5, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[2], this.erosions[3]), $$1, 0.0F, $$6);
/* 445 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), $$1, 0.0F, $$7);
/*     */         
/* 447 */         addSurfaceBiome($$0, $$3, $$5, this.coastContinentalness, Climate.Parameter.span(this.erosions[3], this.erosions[4]), $$1, 0.0F, $$9);
/*     */         
/* 449 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], $$1, 0.0F, $$6);
/*     */         
/* 451 */         addSurfaceBiome($$0, $$3, $$5, this.coastContinentalness, this.erosions[5], $$1, 0.0F, $$11);
/* 452 */         addSurfaceBiome($$0, $$3, $$5, this.nearInlandContinentalness, this.erosions[5], $$1, 0.0F, $$10);
/* 453 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], $$1, 0.0F, $$6);
/*     */         
/* 455 */         addSurfaceBiome($$0, $$3, $$5, this.coastContinentalness, this.erosions[6], $$1, 0.0F, $$9);
/*     */ 
/*     */         
/* 458 */         if ($$2 == 0) {
/* 459 */           addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], $$1, 0.0F, $$6);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addValleys(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> $$0, Climate.Parameter $$1) {
/* 470 */     addSurfaceBiome($$0, this.FROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), $$1, 0.0F, ($$1.max() < 0L) ? Biomes.STONY_SHORE : Biomes.FROZEN_RIVER);
/* 471 */     addSurfaceBiome($$0, this.UNFROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), $$1, 0.0F, ($$1.max() < 0L) ? Biomes.STONY_SHORE : Biomes.RIVER);
/*     */     
/* 473 */     addSurfaceBiome($$0, this.FROZEN_RANGE, this.FULL_RANGE, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), $$1, 0.0F, Biomes.FROZEN_RIVER);
/* 474 */     addSurfaceBiome($$0, this.UNFROZEN_RANGE, this.FULL_RANGE, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), $$1, 0.0F, Biomes.RIVER);
/*     */     
/* 476 */     addSurfaceBiome($$0, this.FROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[5]), $$1, 0.0F, Biomes.FROZEN_RIVER);
/* 477 */     addSurfaceBiome($$0, this.UNFROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[5]), $$1, 0.0F, Biomes.RIVER);
/*     */     
/* 479 */     addSurfaceBiome($$0, this.FROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, this.erosions[6], $$1, 0.0F, Biomes.FROZEN_RIVER);
/* 480 */     addSurfaceBiome($$0, this.UNFROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, this.erosions[6], $$1, 0.0F, Biomes.RIVER);
/*     */     
/* 482 */     addSurfaceBiome($$0, Climate.Parameter.span(this.temperatures[1], this.temperatures[2]), this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness), this.erosions[6], $$1, 0.0F, Biomes.SWAMP);
/* 483 */     addSurfaceBiome($$0, Climate.Parameter.span(this.temperatures[3], this.temperatures[4]), this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness), this.erosions[6], $$1, 0.0F, Biomes.MANGROVE_SWAMP);
/* 484 */     addSurfaceBiome($$0, this.FROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness), this.erosions[6], $$1, 0.0F, Biomes.FROZEN_RIVER);
/*     */     
/* 486 */     for (int $$2 = 0; $$2 < this.temperatures.length; $$2++) {
/* 487 */       Climate.Parameter $$3 = this.temperatures[$$2];
/* 488 */       for (int $$4 = 0; $$4 < this.humidities.length; $$4++) {
/* 489 */         Climate.Parameter $$5 = this.humidities[$$4];
/* 490 */         ResourceKey<Biome> $$6 = pickMiddleBiomeOrBadlandsIfHot($$2, $$4, $$1);
/*     */         
/* 492 */         addSurfaceBiome($$0, $$3, $$5, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[0], this.erosions[1]), $$1, 0.0F, $$6);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> $$0) {
/* 499 */     addUndergroundBiome($$0, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(0.8F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, 0.0F, Biomes.DRIPSTONE_CAVES);
/*     */     
/* 501 */     addUndergroundBiome($$0, this.FULL_RANGE, Climate.Parameter.span(0.7F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, 0.0F, Biomes.LUSH_CAVES);
/*     */     
/* 503 */     addBottomBiome($$0, this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.erosions[0], this.erosions[1]), this.FULL_RANGE, 0.0F, Biomes.DEEP_DARK);
/*     */   }
/*     */   
/*     */   private ResourceKey<Biome> pickMiddleBiome(int $$0, int $$1, Climate.Parameter $$2) {
/* 507 */     if ($$2.max() < 0L) {
/* 508 */       return this.MIDDLE_BIOMES[$$0][$$1];
/*     */     }
/* 510 */     ResourceKey<Biome> $$3 = this.MIDDLE_BIOMES_VARIANT[$$0][$$1];
/* 511 */     return ($$3 == null) ? this.MIDDLE_BIOMES[$$0][$$1] : $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHot(int $$0, int $$1, Climate.Parameter $$2) {
/* 516 */     return ($$0 == 4) ? pickBadlandsBiome($$1, $$2) : pickMiddleBiome($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(int $$0, int $$1, Climate.Parameter $$2) {
/* 520 */     return ($$0 == 0) ? pickSlopeBiome($$0, $$1, $$2) : pickMiddleBiomeOrBadlandsIfHot($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private ResourceKey<Biome> maybePickWindsweptSavannaBiome(int $$0, int $$1, Climate.Parameter $$2, ResourceKey<Biome> $$3) {
/* 524 */     if ($$0 > 1 && $$1 < 4 && $$2.max() >= 0L) {
/* 525 */       return Biomes.WINDSWEPT_SAVANNA;
/*     */     }
/* 527 */     return $$3;
/*     */   }
/*     */   
/*     */   private ResourceKey<Biome> pickShatteredCoastBiome(int $$0, int $$1, Climate.Parameter $$2) {
/* 531 */     ResourceKey<Biome> $$3 = ($$2.max() >= 0L) ? pickMiddleBiome($$0, $$1, $$2) : pickBeachBiome($$0, $$1);
/* 532 */     return maybePickWindsweptSavannaBiome($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   private ResourceKey<Biome> pickBeachBiome(int $$0, int $$1) {
/* 536 */     if ($$0 == 0) {
/* 537 */       return Biomes.SNOWY_BEACH;
/*     */     }
/* 539 */     if ($$0 == 4)
/*     */     {
/* 541 */       return Biomes.DESERT;
/*     */     }
/* 543 */     return Biomes.BEACH;
/*     */   }
/*     */   
/*     */   private ResourceKey<Biome> pickBadlandsBiome(int $$0, Climate.Parameter $$1) {
/* 547 */     if ($$0 < 2)
/* 548 */       return ($$1.max() < 0L) ? Biomes.BADLANDS : Biomes.ERODED_BADLANDS; 
/* 549 */     if ($$0 < 3) {
/* 550 */       return Biomes.BADLANDS;
/*     */     }
/* 552 */     return Biomes.WOODED_BADLANDS;
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceKey<Biome> pickPlateauBiome(int $$0, int $$1, Climate.Parameter $$2) {
/* 557 */     if ($$2.max() >= 0L) {
/* 558 */       ResourceKey<Biome> $$3 = this.PLATEAU_BIOMES_VARIANT[$$0][$$1];
/* 559 */       if ($$3 != null) {
/* 560 */         return $$3;
/*     */       }
/*     */     } 
/* 563 */     return this.PLATEAU_BIOMES[$$0][$$1];
/*     */   }
/*     */   
/*     */   private ResourceKey<Biome> pickPeakBiome(int $$0, int $$1, Climate.Parameter $$2) {
/* 567 */     if ($$0 <= 2)
/*     */     {
/* 569 */       return ($$2.max() < 0L) ? Biomes.JAGGED_PEAKS : Biomes.FROZEN_PEAKS;
/*     */     }
/* 571 */     if ($$0 == 3) {
/* 572 */       return Biomes.STONY_PEAKS;
/*     */     }
/* 574 */     return pickBadlandsBiome($$1, $$2);
/*     */   }
/*     */   
/*     */   private ResourceKey<Biome> pickSlopeBiome(int $$0, int $$1, Climate.Parameter $$2) {
/* 578 */     if ($$0 >= 3) {
/* 579 */       return pickPlateauBiome($$0, $$1, $$2);
/*     */     }
/* 581 */     if ($$1 <= 1) {
/* 582 */       return Biomes.SNOWY_SLOPES;
/*     */     }
/* 584 */     return Biomes.GROVE;
/*     */   }
/*     */   
/*     */   private ResourceKey<Biome> pickShatteredBiome(int $$0, int $$1, Climate.Parameter $$2) {
/* 588 */     ResourceKey<Biome> $$3 = this.SHATTERED_BIOMES[$$0][$$1];
/* 589 */     return ($$3 == null) ? pickMiddleBiome($$0, $$1, $$2) : $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> $$0, Climate.Parameter $$1, Climate.Parameter $$2, Climate.Parameter $$3, Climate.Parameter $$4, Climate.Parameter $$5, float $$6, ResourceKey<Biome> $$7) {
/* 594 */     $$0.accept(Pair.of(Climate.parameters($$1, $$2, $$3, $$4, Climate.Parameter.point(0.0F), $$5, $$6), $$7));
/*     */     
/* 596 */     $$0.accept(Pair.of(Climate.parameters($$1, $$2, $$3, $$4, Climate.Parameter.point(1.0F), $$5, $$6), $$7));
/*     */   }
/*     */   
/*     */   private void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> $$0, Climate.Parameter $$1, Climate.Parameter $$2, Climate.Parameter $$3, Climate.Parameter $$4, Climate.Parameter $$5, float $$6, ResourceKey<Biome> $$7) {
/* 600 */     $$0.accept(Pair.of(Climate.parameters($$1, $$2, $$3, $$4, Climate.Parameter.span(0.2F, 0.9F), $$5, $$6), $$7));
/*     */   }
/*     */   
/*     */   private void addBottomBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> $$0, Climate.Parameter $$1, Climate.Parameter $$2, Climate.Parameter $$3, Climate.Parameter $$4, Climate.Parameter $$5, float $$6, ResourceKey<Biome> $$7) {
/* 604 */     $$0.accept(Pair.of(Climate.parameters($$1, $$2, $$3, $$4, Climate.Parameter.point(1.1F), $$5, $$6), $$7));
/*     */   }
/*     */   
/*     */   public static boolean isDeepDarkRegion(DensityFunction $$0, DensityFunction $$1, DensityFunction.FunctionContext $$2) {
/* 608 */     return ($$0.compute($$2) < -0.22499999403953552D && $$1.compute($$2) > 0.8999999761581421D);
/*     */   }
/*     */   
/*     */   public static String getDebugStringForPeaksAndValleys(double $$0) {
/* 612 */     if ($$0 < NoiseRouterData.peaksAndValleys(0.05F))
/* 613 */       return "Valley"; 
/* 614 */     if ($$0 < NoiseRouterData.peaksAndValleys(0.26666668F))
/* 615 */       return "Low"; 
/* 616 */     if ($$0 < NoiseRouterData.peaksAndValleys(0.4F))
/* 617 */       return "Mid"; 
/* 618 */     if ($$0 < NoiseRouterData.peaksAndValleys(0.56666666F)) {
/* 619 */       return "High";
/*     */     }
/* 621 */     return "Peak";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDebugStringForContinentalness(double $$0) {
/* 626 */     double $$1 = Climate.quantizeCoord((float)$$0);
/* 627 */     if ($$1 < this.mushroomFieldsContinentalness.max())
/* 628 */       return "Mushroom fields"; 
/* 629 */     if ($$1 < this.deepOceanContinentalness.max())
/* 630 */       return "Deep ocean"; 
/* 631 */     if ($$1 < this.oceanContinentalness.max())
/* 632 */       return "Ocean"; 
/* 633 */     if ($$1 < this.coastContinentalness.max())
/* 634 */       return "Coast"; 
/* 635 */     if ($$1 < this.nearInlandContinentalness.max())
/* 636 */       return "Near inland"; 
/* 637 */     if ($$1 < this.midInlandContinentalness.max()) {
/* 638 */       return "Mid inland";
/*     */     }
/* 640 */     return "Far inland";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDebugStringForErosion(double $$0) {
/* 645 */     return getDebugStringForNoiseValue($$0, this.erosions);
/*     */   }
/*     */   
/*     */   public String getDebugStringForTemperature(double $$0) {
/* 649 */     return getDebugStringForNoiseValue($$0, this.temperatures);
/*     */   }
/*     */   
/*     */   public String getDebugStringForHumidity(double $$0) {
/* 653 */     return getDebugStringForNoiseValue($$0, this.humidities);
/*     */   }
/*     */   
/*     */   private static String getDebugStringForNoiseValue(double $$0, Climate.Parameter[] $$1) {
/* 657 */     double $$2 = Climate.quantizeCoord((float)$$0);
/* 658 */     for (int $$3 = 0; $$3 < $$1.length; $$3++) {
/* 659 */       if ($$2 < $$1[$$3].max()) {
/* 660 */         return "" + $$3;
/*     */       }
/*     */     } 
/* 663 */     return "?";
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public Climate.Parameter[] getTemperatureThresholds() {
/* 668 */     return this.temperatures;
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public Climate.Parameter[] getHumidityThresholds() {
/* 673 */     return this.humidities;
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public Climate.Parameter[] getErosionThresholds() {
/* 678 */     return this.erosions;
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public Climate.Parameter[] getContinentalnessThresholds() {
/* 683 */     return new Climate.Parameter[] { this.mushroomFieldsContinentalness, this.deepOceanContinentalness, this.oceanContinentalness, this.coastContinentalness, this.nearInlandContinentalness, this.midInlandContinentalness, this.farInlandContinentalness };
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
/*     */   @VisibleForDebug
/*     */   public Climate.Parameter[] getPeaksAndValleysThresholds() {
/* 696 */     return new Climate.Parameter[] {
/* 697 */         Climate.Parameter.span(-2.0F, NoiseRouterData.peaksAndValleys(0.05F)), 
/* 698 */         Climate.Parameter.span(NoiseRouterData.peaksAndValleys(0.05F), NoiseRouterData.peaksAndValleys(0.26666668F)), 
/* 699 */         Climate.Parameter.span(NoiseRouterData.peaksAndValleys(0.26666668F), NoiseRouterData.peaksAndValleys(0.4F)), 
/* 700 */         Climate.Parameter.span(NoiseRouterData.peaksAndValleys(0.4F), NoiseRouterData.peaksAndValleys(0.56666666F)), 
/* 701 */         Climate.Parameter.span(NoiseRouterData.peaksAndValleys(0.56666666F), 2.0F)
/*     */       };
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public Climate.Parameter[] getWeirdnessThresholds() {
/* 707 */     return new Climate.Parameter[] {
/* 708 */         Climate.Parameter.span(-2.0F, 0.0F), 
/* 709 */         Climate.Parameter.span(0.0F, 2.0F)
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\OverworldBiomeBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */