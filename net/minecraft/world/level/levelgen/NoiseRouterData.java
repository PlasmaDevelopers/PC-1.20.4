/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ import net.minecraft.data.worldgen.TerrainProvider;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.levelgen.synth.BlendedNoise;
/*     */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
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
/*     */ public class NoiseRouterData
/*     */ {
/*     */   public static final float GLOBAL_OFFSET = -0.50375F;
/*     */   private static final float ORE_THICKNESS = 0.08F;
/*     */   private static final double VEININESS_FREQUENCY = 1.5D;
/*     */   private static final double NOODLE_SPACING_AND_STRAIGHTNESS = 1.5D;
/*     */   private static final double SURFACE_DENSITY_THRESHOLD = 1.5625D;
/*     */   private static final double CHEESE_NOISE_TARGET = -0.703125D;
/*     */   public static final int ISLAND_CHUNK_DISTANCE = 64;
/*     */   public static final long ISLAND_CHUNK_DISTANCE_SQR = 4096L;
/*  78 */   private static final DensityFunction BLENDING_FACTOR = DensityFunctions.constant(10.0D);
/*  79 */   private static final DensityFunction BLENDING_JAGGEDNESS = DensityFunctions.zero();
/*     */   
/*  81 */   private static final ResourceKey<DensityFunction> ZERO = createKey("zero");
/*  82 */   private static final ResourceKey<DensityFunction> Y = createKey("y");
/*  83 */   private static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
/*  84 */   private static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");
/*     */   
/*  86 */   private static final ResourceKey<DensityFunction> BASE_3D_NOISE_OVERWORLD = createKey("overworld/base_3d_noise");
/*  87 */   private static final ResourceKey<DensityFunction> BASE_3D_NOISE_NETHER = createKey("nether/base_3d_noise");
/*  88 */   private static final ResourceKey<DensityFunction> BASE_3D_NOISE_END = createKey("end/base_3d_noise");
/*     */   
/*  90 */   public static final ResourceKey<DensityFunction> CONTINENTS = createKey("overworld/continents");
/*  91 */   public static final ResourceKey<DensityFunction> EROSION = createKey("overworld/erosion");
/*  92 */   public static final ResourceKey<DensityFunction> RIDGES = createKey("overworld/ridges");
/*  93 */   public static final ResourceKey<DensityFunction> RIDGES_FOLDED = createKey("overworld/ridges_folded");
/*     */   
/*  95 */   public static final ResourceKey<DensityFunction> OFFSET = createKey("overworld/offset");
/*  96 */   public static final ResourceKey<DensityFunction> FACTOR = createKey("overworld/factor");
/*  97 */   public static final ResourceKey<DensityFunction> JAGGEDNESS = createKey("overworld/jaggedness");
/*  98 */   public static final ResourceKey<DensityFunction> DEPTH = createKey("overworld/depth");
/*  99 */   private static final ResourceKey<DensityFunction> SLOPED_CHEESE = createKey("overworld/sloped_cheese");
/*     */   
/* 101 */   public static final ResourceKey<DensityFunction> CONTINENTS_LARGE = createKey("overworld_large_biomes/continents");
/* 102 */   public static final ResourceKey<DensityFunction> EROSION_LARGE = createKey("overworld_large_biomes/erosion");
/*     */   
/* 104 */   private static final ResourceKey<DensityFunction> OFFSET_LARGE = createKey("overworld_large_biomes/offset");
/* 105 */   private static final ResourceKey<DensityFunction> FACTOR_LARGE = createKey("overworld_large_biomes/factor");
/* 106 */   private static final ResourceKey<DensityFunction> JAGGEDNESS_LARGE = createKey("overworld_large_biomes/jaggedness");
/* 107 */   private static final ResourceKey<DensityFunction> DEPTH_LARGE = createKey("overworld_large_biomes/depth");
/* 108 */   private static final ResourceKey<DensityFunction> SLOPED_CHEESE_LARGE = createKey("overworld_large_biomes/sloped_cheese");
/*     */   
/* 110 */   private static final ResourceKey<DensityFunction> OFFSET_AMPLIFIED = createKey("overworld_amplified/offset");
/* 111 */   private static final ResourceKey<DensityFunction> FACTOR_AMPLIFIED = createKey("overworld_amplified/factor");
/* 112 */   private static final ResourceKey<DensityFunction> JAGGEDNESS_AMPLIFIED = createKey("overworld_amplified/jaggedness");
/* 113 */   private static final ResourceKey<DensityFunction> DEPTH_AMPLIFIED = createKey("overworld_amplified/depth");
/* 114 */   private static final ResourceKey<DensityFunction> SLOPED_CHEESE_AMPLIFIED = createKey("overworld_amplified/sloped_cheese");
/*     */   
/* 116 */   private static final ResourceKey<DensityFunction> SLOPED_CHEESE_END = createKey("end/sloped_cheese");
/*     */   
/* 118 */   private static final ResourceKey<DensityFunction> SPAGHETTI_ROUGHNESS_FUNCTION = createKey("overworld/caves/spaghetti_roughness_function");
/* 119 */   private static final ResourceKey<DensityFunction> ENTRANCES = createKey("overworld/caves/entrances");
/* 120 */   private static final ResourceKey<DensityFunction> NOODLE = createKey("overworld/caves/noodle");
/* 121 */   private static final ResourceKey<DensityFunction> PILLARS = createKey("overworld/caves/pillars");
/* 122 */   private static final ResourceKey<DensityFunction> SPAGHETTI_2D_THICKNESS_MODULATOR = createKey("overworld/caves/spaghetti_2d_thickness_modulator");
/* 123 */   private static final ResourceKey<DensityFunction> SPAGHETTI_2D = createKey("overworld/caves/spaghetti_2d");
/*     */   
/*     */   private static ResourceKey<DensityFunction> createKey(String $$0) {
/* 126 */     return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation($$0));
/*     */   }
/*     */   
/*     */   public static Holder<? extends DensityFunction> bootstrap(BootstapContext<DensityFunction> $$0) {
/* 130 */     HolderGetter<NormalNoise.NoiseParameters> $$1 = $$0.lookup(Registries.NOISE);
/* 131 */     HolderGetter<DensityFunction> $$2 = $$0.lookup(Registries.DENSITY_FUNCTION);
/*     */     
/* 133 */     $$0.register(ZERO, DensityFunctions.zero());
/*     */     
/* 135 */     int $$3 = DimensionType.MIN_Y * 2;
/* 136 */     int $$4 = DimensionType.MAX_Y * 2;
/* 137 */     $$0.register(Y, DensityFunctions.yClampedGradient($$3, $$4, $$3, $$4));
/*     */     
/* 139 */     DensityFunction $$5 = registerAndWrap($$0, SHIFT_X, DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftA((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.SHIFT)))));
/* 140 */     DensityFunction $$6 = registerAndWrap($$0, SHIFT_Z, DensityFunctions.flatCache(DensityFunctions.cache2d(DensityFunctions.shiftB((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.SHIFT)))));
/*     */     
/* 142 */     $$0.register(BASE_3D_NOISE_OVERWORLD, BlendedNoise.createUnseeded(0.25D, 0.125D, 80.0D, 160.0D, 8.0D));
/* 143 */     $$0.register(BASE_3D_NOISE_NETHER, BlendedNoise.createUnseeded(0.25D, 0.375D, 80.0D, 60.0D, 8.0D));
/* 144 */     $$0.register(BASE_3D_NOISE_END, BlendedNoise.createUnseeded(0.25D, 0.25D, 80.0D, 160.0D, 4.0D));
/*     */     
/* 146 */     Holder.Reference reference1 = $$0.register(CONTINENTS, DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d($$5, $$6, 0.25D, (Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.CONTINENTALNESS))));
/* 147 */     Holder.Reference reference2 = $$0.register(EROSION, DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d($$5, $$6, 0.25D, (Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.EROSION))));
/* 148 */     DensityFunction $$9 = registerAndWrap($$0, RIDGES, DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d($$5, $$6, 0.25D, (Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.RIDGE))));
/* 149 */     $$0.register(RIDGES_FOLDED, peaksAndValleys($$9));
/*     */     
/* 151 */     DensityFunction $$10 = DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.JAGGED), 1500.0D, 0.0D);
/*     */     
/* 153 */     registerTerrainNoises($$0, $$2, $$10, (Holder<DensityFunction>)reference1, (Holder<DensityFunction>)reference2, OFFSET, FACTOR, JAGGEDNESS, DEPTH, SLOPED_CHEESE, false);
/*     */     
/* 155 */     Holder.Reference reference3 = $$0.register(CONTINENTS_LARGE, DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d($$5, $$6, 0.25D, (Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.CONTINENTALNESS_LARGE))));
/* 156 */     Holder.Reference reference4 = $$0.register(EROSION_LARGE, DensityFunctions.flatCache(DensityFunctions.shiftedNoise2d($$5, $$6, 0.25D, (Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.EROSION_LARGE))));
/*     */     
/* 158 */     registerTerrainNoises($$0, $$2, $$10, (Holder<DensityFunction>)reference3, (Holder<DensityFunction>)reference4, OFFSET_LARGE, FACTOR_LARGE, JAGGEDNESS_LARGE, DEPTH_LARGE, SLOPED_CHEESE_LARGE, false);
/* 159 */     registerTerrainNoises($$0, $$2, $$10, (Holder<DensityFunction>)reference1, (Holder<DensityFunction>)reference2, OFFSET_AMPLIFIED, FACTOR_AMPLIFIED, JAGGEDNESS_AMPLIFIED, DEPTH_AMPLIFIED, SLOPED_CHEESE_AMPLIFIED, true);
/*     */     
/* 161 */     $$0.register(SLOPED_CHEESE_END, DensityFunctions.add(DensityFunctions.endIslands(0L), getFunction($$2, BASE_3D_NOISE_END)));
/*     */     
/* 163 */     $$0.register(SPAGHETTI_ROUGHNESS_FUNCTION, spaghettiRoughnessFunction($$1));
/* 164 */     $$0.register(SPAGHETTI_2D_THICKNESS_MODULATOR, DensityFunctions.cacheOnce(DensityFunctions.mappedNoise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.SPAGHETTI_2D_THICKNESS), 2.0D, 1.0D, -0.6D, -1.3D)));
/* 165 */     $$0.register(SPAGHETTI_2D, spaghetti2D($$2, $$1));
/* 166 */     $$0.register(ENTRANCES, entrances($$2, $$1));
/* 167 */     $$0.register(NOODLE, noodle($$2, $$1));
/* 168 */     return (Holder<? extends DensityFunction>)$$0.register(PILLARS, pillars($$1));
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
/*     */   private static void registerTerrainNoises(BootstapContext<DensityFunction> $$0, HolderGetter<DensityFunction> $$1, DensityFunction $$2, Holder<DensityFunction> $$3, Holder<DensityFunction> $$4, ResourceKey<DensityFunction> $$5, ResourceKey<DensityFunction> $$6, ResourceKey<DensityFunction> $$7, ResourceKey<DensityFunction> $$8, ResourceKey<DensityFunction> $$9, boolean $$10) {
/* 184 */     DensityFunctions.Spline.Coordinate $$11 = new DensityFunctions.Spline.Coordinate($$3);
/* 185 */     DensityFunctions.Spline.Coordinate $$12 = new DensityFunctions.Spline.Coordinate($$4);
/* 186 */     DensityFunctions.Spline.Coordinate $$13 = new DensityFunctions.Spline.Coordinate((Holder<DensityFunction>)$$1.getOrThrow(RIDGES));
/* 187 */     DensityFunctions.Spline.Coordinate $$14 = new DensityFunctions.Spline.Coordinate((Holder<DensityFunction>)$$1.getOrThrow(RIDGES_FOLDED));
/*     */ 
/*     */     
/* 190 */     DensityFunction $$15 = registerAndWrap($$0, $$5, splineWithBlending(
/* 191 */           DensityFunctions.add(DensityFunctions.constant(-0.5037500262260437D), DensityFunctions.spline(TerrainProvider.overworldOffset($$11, $$12, $$14, $$10))), 
/* 192 */           DensityFunctions.blendOffset()));
/*     */     
/* 194 */     DensityFunction $$16 = registerAndWrap($$0, $$6, splineWithBlending(
/* 195 */           DensityFunctions.spline(TerrainProvider.overworldFactor($$11, $$12, $$13, $$14, $$10)), BLENDING_FACTOR));
/*     */ 
/*     */     
/* 198 */     DensityFunction $$17 = registerAndWrap($$0, $$8, DensityFunctions.add(DensityFunctions.yClampedGradient(-64, 320, 1.5D, -1.5D), $$15));
/* 199 */     DensityFunction $$18 = registerAndWrap($$0, $$7, splineWithBlending(
/* 200 */           DensityFunctions.spline(TerrainProvider.overworldJaggedness($$11, $$12, $$13, $$14, $$10)), BLENDING_JAGGEDNESS));
/*     */ 
/*     */ 
/*     */     
/* 204 */     DensityFunction $$19 = DensityFunctions.mul($$18, $$2.halfNegative());
/* 205 */     DensityFunction $$20 = noiseGradientDensity($$16, DensityFunctions.add($$17, $$19));
/* 206 */     $$0.register($$9, DensityFunctions.add($$20, getFunction($$1, BASE_3D_NOISE_OVERWORLD)));
/*     */   }
/*     */   
/*     */   private static DensityFunction registerAndWrap(BootstapContext<DensityFunction> $$0, ResourceKey<DensityFunction> $$1, DensityFunction $$2) {
/* 210 */     return new DensityFunctions.HolderHolder((Holder<DensityFunction>)$$0.register($$1, $$2));
/*     */   }
/*     */   
/*     */   private static DensityFunction getFunction(HolderGetter<DensityFunction> $$0, ResourceKey<DensityFunction> $$1) {
/* 214 */     return new DensityFunctions.HolderHolder((Holder<DensityFunction>)$$0.getOrThrow($$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DensityFunction peaksAndValleys(DensityFunction $$0) {
/* 224 */     return DensityFunctions.mul(DensityFunctions.add(DensityFunctions.add($$0.abs(), DensityFunctions.constant(-0.6666666666666666D)).abs(), DensityFunctions.constant(-0.3333333333333333D)), DensityFunctions.constant(-3.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float peaksAndValleys(float $$0) {
/* 231 */     return -(Math.abs(Math.abs($$0) - 0.6666667F) - 0.33333334F) * 3.0F;
/*     */   }
/*     */   
/*     */   private static DensityFunction spaghettiRoughnessFunction(HolderGetter<NormalNoise.NoiseParameters> $$0) {
/* 235 */     DensityFunction $$1 = DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$0.getOrThrow(Noises.SPAGHETTI_ROUGHNESS));
/*     */     
/* 237 */     DensityFunction $$2 = DensityFunctions.mappedNoise((Holder<NormalNoise.NoiseParameters>)$$0.getOrThrow(Noises.SPAGHETTI_ROUGHNESS_MODULATOR), 0.0D, -0.1D);
/*     */     
/* 239 */     return DensityFunctions.cacheOnce(DensityFunctions.mul($$2, 
/*     */           
/* 241 */           DensityFunctions.add($$1.abs(), DensityFunctions.constant(-0.4D))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static DensityFunction entrances(HolderGetter<DensityFunction> $$0, HolderGetter<NormalNoise.NoiseParameters> $$1) {
/* 246 */     DensityFunction $$2 = DensityFunctions.cacheOnce(DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.SPAGHETTI_3D_RARITY), 2.0D, 1.0D));
/*     */ 
/*     */     
/* 249 */     DensityFunction $$3 = DensityFunctions.mappedNoise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.SPAGHETTI_3D_THICKNESS), -0.065D, -0.088D);
/*     */     
/* 251 */     DensityFunction $$4 = DensityFunctions.weirdScaledSampler($$2, (Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.SPAGHETTI_3D_1), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE1);
/* 252 */     DensityFunction $$5 = DensityFunctions.weirdScaledSampler($$2, (Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.SPAGHETTI_3D_2), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     DensityFunction $$6 = DensityFunctions.add(DensityFunctions.max($$4, $$5), $$3).clamp(-1.0D, 1.0D);
/*     */     
/* 260 */     DensityFunction $$7 = getFunction($$0, SPAGHETTI_ROUGHNESS_FUNCTION);
/*     */     
/* 262 */     DensityFunction $$8 = DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.CAVE_ENTRANCE), 0.75D, 0.5D);
/*     */     
/* 264 */     DensityFunction $$9 = DensityFunctions.add(
/* 265 */         DensityFunctions.add($$8, DensityFunctions.constant(0.37D)), 
/*     */ 
/*     */         
/* 268 */         DensityFunctions.yClampedGradient(-10, 30, 0.3D, 0.0D));
/*     */ 
/*     */ 
/*     */     
/* 272 */     return DensityFunctions.cacheOnce(DensityFunctions.min($$9, DensityFunctions.add($$7, $$6)));
/*     */   }
/*     */   
/*     */   private static DensityFunction noodle(HolderGetter<DensityFunction> $$0, HolderGetter<NormalNoise.NoiseParameters> $$1) {
/* 276 */     DensityFunction $$2 = getFunction($$0, Y);
/*     */     
/* 278 */     int $$3 = -64;
/*     */ 
/*     */     
/* 281 */     int $$4 = -60;
/* 282 */     int $$5 = 320;
/*     */     
/* 284 */     DensityFunction $$6 = yLimitedInterpolatable($$2, DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.NOODLE), 1.0D, 1.0D), -60, 320, -1);
/*     */ 
/*     */ 
/*     */     
/* 288 */     DensityFunction $$7 = yLimitedInterpolatable($$2, DensityFunctions.mappedNoise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.NOODLE_THICKNESS), 1.0D, 1.0D, -0.05D, -0.1D), -60, 320, 0);
/*     */     
/* 290 */     double $$8 = 2.6666666666666665D;
/* 291 */     DensityFunction $$9 = yLimitedInterpolatable($$2, DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.NOODLE_RIDGE_A), 2.6666666666666665D, 2.6666666666666665D), -60, 320, 0);
/* 292 */     DensityFunction $$10 = yLimitedInterpolatable($$2, DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.NOODLE_RIDGE_B), 2.6666666666666665D, 2.6666666666666665D), -60, 320, 0);
/*     */     
/* 294 */     DensityFunction $$11 = DensityFunctions.mul(
/* 295 */         DensityFunctions.constant(1.5D), 
/* 296 */         DensityFunctions.max($$9.abs(), $$10.abs()));
/*     */ 
/*     */     
/* 299 */     return DensityFunctions.rangeChoice($$6, -1000000.0D, 0.0D, 
/*     */ 
/*     */ 
/*     */         
/* 303 */         DensityFunctions.constant(64.0D), 
/* 304 */         DensityFunctions.add($$7, $$11));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DensityFunction pillars(HolderGetter<NormalNoise.NoiseParameters> $$0) {
/* 312 */     double $$1 = 25.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 317 */     double $$2 = 0.3D;
/*     */     
/* 319 */     DensityFunction $$3 = DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$0.getOrThrow(Noises.PILLAR), 25.0D, 0.3D);
/*     */ 
/*     */ 
/*     */     
/* 323 */     DensityFunction $$4 = DensityFunctions.mappedNoise((Holder<NormalNoise.NoiseParameters>)$$0.getOrThrow(Noises.PILLAR_RARENESS), 0.0D, -2.0D);
/*     */ 
/*     */ 
/*     */     
/* 327 */     DensityFunction $$5 = DensityFunctions.mappedNoise((Holder<NormalNoise.NoiseParameters>)$$0.getOrThrow(Noises.PILLAR_THICKNESS), 0.0D, 1.1D);
/*     */     
/* 329 */     DensityFunction $$6 = DensityFunctions.add(
/* 330 */         DensityFunctions.mul($$3, DensityFunctions.constant(2.0D)), $$4);
/*     */ 
/*     */ 
/*     */     
/* 334 */     return DensityFunctions.cacheOnce(DensityFunctions.mul($$6, $$5
/*     */ 
/*     */           
/* 337 */           .cube()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static DensityFunction spaghetti2D(HolderGetter<DensityFunction> $$0, HolderGetter<NormalNoise.NoiseParameters> $$1) {
/* 342 */     DensityFunction $$2 = DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.SPAGHETTI_2D_MODULATOR), 2.0D, 1.0D);
/* 343 */     DensityFunction $$3 = DensityFunctions.weirdScaledSampler($$2, (Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.SPAGHETTI_2D), DensityFunctions.WeirdScaledSampler.RarityValueMapper.TYPE2);
/*     */     
/* 345 */     DensityFunction $$4 = DensityFunctions.mappedNoise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.SPAGHETTI_2D_ELEVATION), 0.0D, Math.floorDiv(-64, 8), 8.0D);
/*     */     
/* 347 */     DensityFunction $$5 = getFunction($$0, SPAGHETTI_2D_THICKNESS_MODULATOR);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 352 */     DensityFunction $$6 = DensityFunctions.add($$4, DensityFunctions.yClampedGradient(-64, 320, 8.0D, -40.0D)).abs();
/*     */     
/* 354 */     DensityFunction $$7 = DensityFunctions.add($$6, $$5).cube();
/*     */     
/* 356 */     double $$8 = 0.083D;
/* 357 */     DensityFunction $$9 = DensityFunctions.add($$3, DensityFunctions.mul(DensityFunctions.constant(0.083D), $$5));
/*     */ 
/*     */     
/* 360 */     return DensityFunctions.max($$9, $$7).clamp(-1.0D, 1.0D);
/*     */   }
/*     */   
/*     */   private static DensityFunction underground(HolderGetter<DensityFunction> $$0, HolderGetter<NormalNoise.NoiseParameters> $$1, DensityFunction $$2) {
/* 364 */     DensityFunction $$3 = getFunction($$0, SPAGHETTI_2D);
/*     */     
/* 366 */     DensityFunction $$4 = getFunction($$0, SPAGHETTI_ROUGHNESS_FUNCTION);
/*     */     
/* 368 */     DensityFunction $$5 = DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.CAVE_LAYER), 8.0D);
/*     */     
/* 370 */     DensityFunction $$6 = DensityFunctions.mul(DensityFunctions.constant(4.0D), $$5.square());
/*     */     
/* 372 */     DensityFunction $$7 = DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.CAVE_CHEESE), 0.6666666666666666D);
/*     */     
/* 374 */     DensityFunction $$8 = DensityFunctions.add(
/*     */         
/* 376 */         DensityFunctions.add(DensityFunctions.constant(0.27D), $$7).clamp(-1.0D, 1.0D), 
/*     */         
/* 378 */         DensityFunctions.add(DensityFunctions.constant(1.5D), DensityFunctions.mul(DensityFunctions.constant(-0.64D), $$2)).clamp(0.0D, 0.5D));
/*     */ 
/*     */     
/* 381 */     DensityFunction $$9 = DensityFunctions.add($$6, $$8);
/*     */     
/* 383 */     DensityFunction $$10 = DensityFunctions.min(DensityFunctions.min($$9, getFunction($$0, ENTRANCES)), DensityFunctions.add($$3, $$4));
/*     */     
/* 385 */     DensityFunction $$11 = getFunction($$0, PILLARS);
/*     */     
/* 387 */     DensityFunction $$12 = DensityFunctions.rangeChoice($$11, -1000000.0D, 0.03D, DensityFunctions.constant(-1000000.0D), $$11);
/*     */     
/* 389 */     return DensityFunctions.max($$10, $$12);
/*     */   }
/*     */ 
/*     */   
/*     */   private static DensityFunction postProcess(DensityFunction $$0) {
/* 394 */     DensityFunction $$1 = DensityFunctions.blendDensity($$0);
/* 395 */     return DensityFunctions.mul(DensityFunctions.interpolated($$1), DensityFunctions.constant(0.64D)).squeeze();
/*     */   }
/*     */ 
/*     */   
/*     */   protected static NoiseRouter overworld(HolderGetter<DensityFunction> $$0, HolderGetter<NormalNoise.NoiseParameters> $$1, boolean $$2, boolean $$3) {
/* 400 */     DensityFunction $$4 = DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.AQUIFER_BARRIER), 0.5D);
/* 401 */     DensityFunction $$5 = DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67D);
/* 402 */     DensityFunction $$6 = DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857142857143D);
/* 403 */     DensityFunction $$7 = DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.AQUIFER_LAVA));
/*     */     
/* 405 */     DensityFunction $$8 = getFunction($$0, SHIFT_X);
/* 406 */     DensityFunction $$9 = getFunction($$0, SHIFT_Z);
/*     */ 
/*     */     
/* 409 */     DensityFunction $$10 = DensityFunctions.shiftedNoise2d($$8, $$9, 0.25D, (Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow($$2 ? Noises.TEMPERATURE_LARGE : Noises.TEMPERATURE));
/* 410 */     DensityFunction $$11 = DensityFunctions.shiftedNoise2d($$8, $$9, 0.25D, (Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow($$2 ? Noises.VEGETATION_LARGE : Noises.VEGETATION));
/*     */     
/* 412 */     DensityFunction $$12 = getFunction($$0, $$2 ? FACTOR_LARGE : ($$3 ? FACTOR_AMPLIFIED : FACTOR));
/* 413 */     DensityFunction $$13 = getFunction($$0, $$2 ? DEPTH_LARGE : ($$3 ? DEPTH_AMPLIFIED : DEPTH));
/* 414 */     DensityFunction $$14 = noiseGradientDensity(DensityFunctions.cache2d($$12), $$13);
/*     */     
/* 416 */     DensityFunction $$15 = getFunction($$0, $$2 ? SLOPED_CHEESE_LARGE : ($$3 ? SLOPED_CHEESE_AMPLIFIED : SLOPED_CHEESE));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 421 */     DensityFunction $$16 = DensityFunctions.min($$15, DensityFunctions.mul(DensityFunctions.constant(5.0D), getFunction($$0, ENTRANCES)));
/* 422 */     DensityFunction $$17 = DensityFunctions.rangeChoice($$15, -1000000.0D, 1.5625D, $$16, underground($$0, $$1, $$15));
/*     */     
/* 424 */     DensityFunction $$18 = DensityFunctions.min(postProcess(slideOverworld($$3, $$17)), getFunction($$0, NOODLE));
/*     */     
/* 426 */     DensityFunction $$19 = getFunction($$0, Y);
/*     */     
/* 428 */     int $$20 = Stream.<OreVeinifier.VeinType>of(OreVeinifier.VeinType.values()).mapToInt($$0 -> $$0.minY).min().orElse(-DimensionType.MIN_Y * 2);
/* 429 */     int $$21 = Stream.<OreVeinifier.VeinType>of(OreVeinifier.VeinType.values()).mapToInt($$0 -> $$0.maxY).max().orElse(-DimensionType.MIN_Y * 2);
/*     */ 
/*     */     
/* 432 */     DensityFunction $$22 = yLimitedInterpolatable($$19, DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.ORE_VEININESS), 1.5D, 1.5D), $$20, $$21, 0);
/*     */     
/* 434 */     float $$23 = 4.0F;
/*     */ 
/*     */     
/* 437 */     DensityFunction $$24 = yLimitedInterpolatable($$19, DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.ORE_VEIN_A), 4.0D, 4.0D), $$20, $$21, 0).abs();
/* 438 */     DensityFunction $$25 = yLimitedInterpolatable($$19, DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.ORE_VEIN_B), 4.0D, 4.0D), $$20, $$21, 0).abs();
/*     */     
/* 440 */     DensityFunction $$26 = DensityFunctions.add(DensityFunctions.constant(-0.07999999821186066D), DensityFunctions.max($$24, $$25));
/*     */     
/* 442 */     DensityFunction $$27 = DensityFunctions.noise((Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.ORE_GAP));
/*     */ 
/*     */     
/* 445 */     return new NoiseRouter($$4, $$5, $$6, $$7, $$10, $$11, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 452 */         getFunction($$0, $$2 ? CONTINENTS_LARGE : CONTINENTS), 
/* 453 */         getFunction($$0, $$2 ? EROSION_LARGE : EROSION), $$13, 
/*     */         
/* 455 */         getFunction($$0, RIDGES), 
/* 456 */         slideOverworld($$3, DensityFunctions.add($$14, DensityFunctions.constant(-0.703125D)).clamp(-64.0D, 64.0D)), $$18, $$22, $$26, $$27);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static NoiseRouter noNewCaves(HolderGetter<DensityFunction> $$0, HolderGetter<NormalNoise.NoiseParameters> $$1, DensityFunction $$2) {
/* 465 */     DensityFunction $$3 = getFunction($$0, SHIFT_X);
/* 466 */     DensityFunction $$4 = getFunction($$0, SHIFT_Z);
/*     */     
/* 468 */     DensityFunction $$5 = DensityFunctions.shiftedNoise2d($$3, $$4, 0.25D, (Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.TEMPERATURE));
/* 469 */     DensityFunction $$6 = DensityFunctions.shiftedNoise2d($$3, $$4, 0.25D, (Holder<NormalNoise.NoiseParameters>)$$1.getOrThrow(Noises.VEGETATION));
/*     */     
/* 471 */     DensityFunction $$7 = postProcess($$2);
/*     */ 
/*     */ 
/*     */     
/* 475 */     return new NoiseRouter(
/* 476 */         DensityFunctions.zero(), 
/* 477 */         DensityFunctions.zero(), 
/* 478 */         DensityFunctions.zero(), 
/* 479 */         DensityFunctions.zero(), $$5, $$6, 
/*     */ 
/*     */         
/* 482 */         DensityFunctions.zero(), 
/* 483 */         DensityFunctions.zero(), 
/* 484 */         DensityFunctions.zero(), 
/* 485 */         DensityFunctions.zero(), 
/* 486 */         DensityFunctions.zero(), $$7, 
/*     */         
/* 488 */         DensityFunctions.zero(), 
/* 489 */         DensityFunctions.zero(), 
/* 490 */         DensityFunctions.zero());
/*     */   }
/*     */ 
/*     */   
/*     */   private static DensityFunction slideOverworld(boolean $$0, DensityFunction $$1) {
/* 495 */     return slide($$1, -64, 384, 
/*     */ 
/*     */ 
/*     */         
/* 499 */         $$0 ? 16 : 80, 
/* 500 */         $$0 ? 0 : 64, -0.078125D, 0, 24, 
/*     */ 
/*     */ 
/*     */         
/* 504 */         $$0 ? 0.4D : 0.1171875D);
/*     */   }
/*     */ 
/*     */   
/*     */   private static DensityFunction slideNetherLike(HolderGetter<DensityFunction> $$0, int $$1, int $$2) {
/* 509 */     return slide(
/* 510 */         getFunction($$0, BASE_3D_NOISE_NETHER), $$1, $$2, 24, 0, 0.9375D, -8, 24, 2.5D);
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
/*     */   private static DensityFunction slideEndLike(DensityFunction $$0, int $$1, int $$2) {
/* 523 */     return slide($$0, $$1, $$2, 72, -184, -23.4375D, 4, 32, -0.234375D);
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
/*     */   protected static NoiseRouter nether(HolderGetter<DensityFunction> $$0, HolderGetter<NormalNoise.NoiseParameters> $$1) {
/* 537 */     return noNewCaves($$0, $$1, slideNetherLike($$0, 0, 128));
/*     */   }
/*     */   
/*     */   protected static NoiseRouter caves(HolderGetter<DensityFunction> $$0, HolderGetter<NormalNoise.NoiseParameters> $$1) {
/* 541 */     return noNewCaves($$0, $$1, slideNetherLike($$0, -64, 192));
/*     */   }
/*     */   
/*     */   protected static NoiseRouter floatingIslands(HolderGetter<DensityFunction> $$0, HolderGetter<NormalNoise.NoiseParameters> $$1) {
/* 545 */     return noNewCaves($$0, $$1, slideEndLike(getFunction($$0, BASE_3D_NOISE_END), 0, 256));
/*     */   }
/*     */   
/*     */   private static DensityFunction slideEnd(DensityFunction $$0) {
/* 549 */     return slideEndLike($$0, 0, 128);
/*     */   }
/*     */   
/*     */   protected static NoiseRouter end(HolderGetter<DensityFunction> $$0) {
/* 553 */     DensityFunction $$1 = DensityFunctions.cache2d(DensityFunctions.endIslands(0L));
/* 554 */     DensityFunction $$2 = postProcess(slideEnd(getFunction($$0, SLOPED_CHEESE_END)));
/*     */ 
/*     */     
/* 557 */     return new NoiseRouter(
/* 558 */         DensityFunctions.zero(), 
/* 559 */         DensityFunctions.zero(), 
/* 560 */         DensityFunctions.zero(), 
/* 561 */         DensityFunctions.zero(), 
/* 562 */         DensityFunctions.zero(), 
/* 563 */         DensityFunctions.zero(), 
/* 564 */         DensityFunctions.zero(), $$1, 
/*     */         
/* 566 */         DensityFunctions.zero(), 
/* 567 */         DensityFunctions.zero(), 
/* 568 */         slideEnd(DensityFunctions.add($$1, DensityFunctions.constant(-0.703125D))), $$2, 
/*     */         
/* 570 */         DensityFunctions.zero(), 
/* 571 */         DensityFunctions.zero(), 
/* 572 */         DensityFunctions.zero());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static NoiseRouter none() {
/* 578 */     return new NoiseRouter(
/* 579 */         DensityFunctions.zero(), 
/* 580 */         DensityFunctions.zero(), 
/* 581 */         DensityFunctions.zero(), 
/* 582 */         DensityFunctions.zero(), 
/* 583 */         DensityFunctions.zero(), 
/* 584 */         DensityFunctions.zero(), 
/* 585 */         DensityFunctions.zero(), 
/* 586 */         DensityFunctions.zero(), 
/* 587 */         DensityFunctions.zero(), 
/* 588 */         DensityFunctions.zero(), 
/* 589 */         DensityFunctions.zero(), 
/* 590 */         DensityFunctions.zero(), 
/* 591 */         DensityFunctions.zero(), 
/* 592 */         DensityFunctions.zero(), 
/* 593 */         DensityFunctions.zero());
/*     */   }
/*     */ 
/*     */   
/*     */   private static DensityFunction splineWithBlending(DensityFunction $$0, DensityFunction $$1) {
/* 598 */     DensityFunction $$2 = DensityFunctions.lerp(DensityFunctions.blendAlpha(), $$1, $$0);
/* 599 */     return DensityFunctions.flatCache(DensityFunctions.cache2d($$2));
/*     */   }
/*     */   
/*     */   private static DensityFunction noiseGradientDensity(DensityFunction $$0, DensityFunction $$1) {
/* 603 */     DensityFunction $$2 = DensityFunctions.mul($$1, $$0);
/*     */ 
/*     */     
/* 606 */     return DensityFunctions.mul(DensityFunctions.constant(4.0D), $$2.quarterNegative());
/*     */   }
/*     */   
/*     */   private static DensityFunction yLimitedInterpolatable(DensityFunction $$0, DensityFunction $$1, int $$2, int $$3, int $$4) {
/* 610 */     return DensityFunctions.interpolated(DensityFunctions.rangeChoice($$0, $$2, ($$3 + 1), $$1, DensityFunctions.constant($$4)));
/*     */   }
/*     */   
/*     */   private static DensityFunction slide(DensityFunction $$0, int $$1, int $$2, int $$3, int $$4, double $$5, int $$6, int $$7, double $$8) {
/* 614 */     DensityFunction $$9 = $$0;
/*     */     
/* 616 */     DensityFunction $$10 = DensityFunctions.yClampedGradient($$1 + $$2 - $$3, $$1 + $$2 - $$4, 1.0D, 0.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 622 */     $$9 = DensityFunctions.lerp($$10, $$5, $$9);
/*     */     
/* 624 */     DensityFunction $$11 = DensityFunctions.yClampedGradient($$1 + $$6, $$1 + $$7, 0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 630 */     $$9 = DensityFunctions.lerp($$11, $$8, $$9);
/*     */     
/* 632 */     return $$9;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final class QuantizedSpaghettiRarity
/*     */   {
/*     */     protected static double getSphaghettiRarity2D(double $$0) {
/* 644 */       if ($$0 < -0.75D)
/* 645 */         return 0.5D; 
/* 646 */       if ($$0 < -0.5D)
/* 647 */         return 0.75D; 
/* 648 */       if ($$0 < 0.5D)
/* 649 */         return 1.0D; 
/* 650 */       if ($$0 < 0.75D) {
/* 651 */         return 2.0D;
/*     */       }
/* 653 */       return 3.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     protected static double getSpaghettiRarity3D(double $$0) {
/* 658 */       if ($$0 < -0.5D)
/* 659 */         return 0.75D; 
/* 660 */       if ($$0 < 0.0D)
/* 661 */         return 1.0D; 
/* 662 */       if ($$0 < 0.5D) {
/* 663 */         return 1.5D;
/*     */       }
/* 665 */       return 2.0D;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\NoiseRouterData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */