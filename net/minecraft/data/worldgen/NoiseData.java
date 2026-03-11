/*     */ package net.minecraft.data.worldgen;
/*     */ 
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.level.levelgen.Noises;
/*     */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NoiseData
/*     */ {
/*     */   @Deprecated
/*  12 */   public static final NormalNoise.NoiseParameters DEFAULT_SHIFT = new NormalNoise.NoiseParameters(-3, 1.0D, new double[] { 1.0D, 1.0D, 0.0D });
/*     */   
/*     */   public static void bootstrap(BootstapContext<NormalNoise.NoiseParameters> $$0) {
/*  15 */     registerBiomeNoises($$0, 0, Noises.TEMPERATURE, Noises.VEGETATION, Noises.CONTINENTALNESS, Noises.EROSION);
/*  16 */     registerBiomeNoises($$0, -2, Noises.TEMPERATURE_LARGE, Noises.VEGETATION_LARGE, Noises.CONTINENTALNESS_LARGE, Noises.EROSION_LARGE);
/*     */     
/*  18 */     register($$0, Noises.RIDGE, -7, 1.0D, new double[] { 2.0D, 1.0D, 0.0D, 0.0D, 0.0D });
/*  19 */     $$0.register(Noises.SHIFT, DEFAULT_SHIFT);
/*     */     
/*  21 */     register($$0, Noises.AQUIFER_BARRIER, -3, 1.0D, new double[0]);
/*  22 */     register($$0, Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS, -7, 1.0D, new double[0]);
/*  23 */     register($$0, Noises.AQUIFER_LAVA, -1, 1.0D, new double[0]);
/*  24 */     register($$0, Noises.AQUIFER_FLUID_LEVEL_SPREAD, -5, 1.0D, new double[0]);
/*     */     
/*  26 */     register($$0, Noises.PILLAR, -7, 1.0D, new double[] { 1.0D });
/*  27 */     register($$0, Noises.PILLAR_RARENESS, -8, 1.0D, new double[0]);
/*  28 */     register($$0, Noises.PILLAR_THICKNESS, -8, 1.0D, new double[0]);
/*     */     
/*  30 */     register($$0, Noises.SPAGHETTI_2D, -7, 1.0D, new double[0]);
/*  31 */     register($$0, Noises.SPAGHETTI_2D_ELEVATION, -8, 1.0D, new double[0]);
/*  32 */     register($$0, Noises.SPAGHETTI_2D_MODULATOR, -11, 1.0D, new double[0]);
/*  33 */     register($$0, Noises.SPAGHETTI_2D_THICKNESS, -11, 1.0D, new double[0]);
/*     */     
/*  35 */     register($$0, Noises.SPAGHETTI_3D_1, -7, 1.0D, new double[0]);
/*  36 */     register($$0, Noises.SPAGHETTI_3D_2, -7, 1.0D, new double[0]);
/*  37 */     register($$0, Noises.SPAGHETTI_3D_RARITY, -11, 1.0D, new double[0]);
/*  38 */     register($$0, Noises.SPAGHETTI_3D_THICKNESS, -8, 1.0D, new double[0]);
/*     */     
/*  40 */     register($$0, Noises.SPAGHETTI_ROUGHNESS, -5, 1.0D, new double[0]);
/*  41 */     register($$0, Noises.SPAGHETTI_ROUGHNESS_MODULATOR, -8, 1.0D, new double[0]);
/*     */     
/*  43 */     register($$0, Noises.CAVE_ENTRANCE, -7, 0.4D, new double[] { 0.5D, 1.0D });
/*  44 */     register($$0, Noises.CAVE_LAYER, -8, 1.0D, new double[0]);
/*     */     
/*  46 */     register($$0, Noises.CAVE_CHEESE, -8, 0.5D, new double[] { 1.0D, 2.0D, 1.0D, 2.0D, 1.0D, 0.0D, 2.0D, 0.0D });
/*     */     
/*  48 */     register($$0, Noises.ORE_VEININESS, -8, 1.0D, new double[0]);
/*  49 */     register($$0, Noises.ORE_VEIN_A, -7, 1.0D, new double[0]);
/*  50 */     register($$0, Noises.ORE_VEIN_B, -7, 1.0D, new double[0]);
/*  51 */     register($$0, Noises.ORE_GAP, -5, 1.0D, new double[0]);
/*     */     
/*  53 */     register($$0, Noises.NOODLE, -8, 1.0D, new double[0]);
/*  54 */     register($$0, Noises.NOODLE_THICKNESS, -8, 1.0D, new double[0]);
/*  55 */     register($$0, Noises.NOODLE_RIDGE_A, -7, 1.0D, new double[0]);
/*  56 */     register($$0, Noises.NOODLE_RIDGE_B, -7, 1.0D, new double[0]);
/*     */     
/*  58 */     register($$0, Noises.JAGGED, -16, 1.0D, new double[] { 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     register($$0, Noises.SURFACE, -6, 1.0D, new double[] { 1.0D, 1.0D });
/*  66 */     register($$0, Noises.SURFACE_SECONDARY, -6, 1.0D, new double[] { 1.0D, 0.0D, 1.0D });
/*     */     
/*  68 */     register($$0, Noises.CLAY_BANDS_OFFSET, -8, 1.0D, new double[0]);
/*  69 */     register($$0, Noises.BADLANDS_PILLAR, -2, 1.0D, new double[] { 1.0D, 1.0D, 1.0D });
/*  70 */     register($$0, Noises.BADLANDS_PILLAR_ROOF, -8, 1.0D, new double[0]);
/*  71 */     register($$0, Noises.BADLANDS_SURFACE, -6, 1.0D, new double[] { 1.0D, 1.0D });
/*     */     
/*  73 */     register($$0, Noises.ICEBERG_PILLAR, -6, 1.0D, new double[] { 1.0D, 1.0D, 1.0D });
/*  74 */     register($$0, Noises.ICEBERG_PILLAR_ROOF, -3, 1.0D, new double[0]);
/*  75 */     register($$0, Noises.ICEBERG_SURFACE, -6, 1.0D, new double[] { 1.0D, 1.0D });
/*     */     
/*  77 */     register($$0, Noises.SWAMP, -2, 1.0D, new double[0]);
/*     */     
/*  79 */     register($$0, Noises.CALCITE, -9, 1.0D, new double[] { 1.0D, 1.0D, 1.0D });
/*  80 */     register($$0, Noises.GRAVEL, -8, 1.0D, new double[] { 1.0D, 1.0D, 1.0D });
/*  81 */     register($$0, Noises.POWDER_SNOW, -6, 1.0D, new double[] { 1.0D, 1.0D, 1.0D });
/*  82 */     register($$0, Noises.PACKED_ICE, -7, 1.0D, new double[] { 1.0D, 1.0D, 1.0D });
/*  83 */     register($$0, Noises.ICE, -4, 1.0D, new double[] { 1.0D, 1.0D, 1.0D });
/*     */     
/*  85 */     register($$0, Noises.SOUL_SAND_LAYER, -8, 1.0D, new double[] { 1.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.013333333333333334D });
/*  86 */     register($$0, Noises.GRAVEL_LAYER, -8, 1.0D, new double[] { 1.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.013333333333333334D });
/*  87 */     register($$0, Noises.PATCH, -5, 1.0D, new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 0.013333333333333334D });
/*  88 */     register($$0, Noises.NETHERRACK, -3, 1.0D, new double[] { 0.0D, 0.0D, 0.35D });
/*  89 */     register($$0, Noises.NETHER_WART, -3, 1.0D, new double[] { 0.0D, 0.0D, 0.9D });
/*  90 */     register($$0, Noises.NETHER_STATE_SELECTOR, -4, 1.0D, new double[0]);
/*     */   }
/*     */   
/*     */   private static void registerBiomeNoises(BootstapContext<NormalNoise.NoiseParameters> $$0, int $$1, ResourceKey<NormalNoise.NoiseParameters> $$2, ResourceKey<NormalNoise.NoiseParameters> $$3, ResourceKey<NormalNoise.NoiseParameters> $$4, ResourceKey<NormalNoise.NoiseParameters> $$5) {
/*  94 */     register($$0, $$2, -10 + $$1, 1.5D, new double[] { 0.0D, 1.0D, 0.0D, 0.0D, 0.0D });
/*  95 */     register($$0, $$3, -8 + $$1, 1.0D, new double[] { 1.0D, 0.0D, 0.0D, 0.0D, 0.0D });
/*  96 */     register($$0, $$4, -9 + $$1, 1.0D, new double[] { 1.0D, 2.0D, 2.0D, 2.0D, 1.0D, 1.0D, 1.0D, 1.0D });
/*  97 */     register($$0, $$5, -9 + $$1, 1.0D, new double[] { 1.0D, 0.0D, 1.0D, 1.0D });
/*     */   }
/*     */   
/*     */   private static void register(BootstapContext<NormalNoise.NoiseParameters> $$0, ResourceKey<NormalNoise.NoiseParameters> $$1, int $$2, double $$3, double... $$4) {
/* 101 */     $$0.register($$1, new NormalNoise.NoiseParameters($$2, $$3, $$4));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\NoiseData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */