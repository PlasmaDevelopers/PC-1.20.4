/*     */ package net.minecraft.world.level.levelgen;
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.List;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.WorldGenRegion;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.NaturalSpawner;
/*     */ import net.minecraft.world.level.NoiseColumn;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeGenerationSettings;
/*     */ import net.minecraft.world.level.biome.BiomeManager;
/*     */ import net.minecraft.world.level.biome.BiomeResolver;
/*     */ import net.minecraft.world.level.biome.BiomeSource;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.CarvingMask;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.chunk.LevelChunkSection;
/*     */ import net.minecraft.world.level.chunk.ProtoChunk;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.levelgen.blending.Blender;
/*     */ import net.minecraft.world.level.levelgen.carver.CarvingContext;
/*     */ import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ 
/*     */ public final class NoiseBasedChunkGenerator extends ChunkGenerator {
/*     */   static {
/*  52 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BiomeSource.CODEC.fieldOf("biome_source").forGetter(()), (App)NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(())).apply((Applicative)$$0, $$0.stable(NoiseBasedChunkGenerator::new)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<NoiseBasedChunkGenerator> CODEC;
/*  57 */   private static final BlockState AIR = Blocks.AIR.defaultBlockState();
/*     */   
/*     */   private final Holder<NoiseGeneratorSettings> settings;
/*     */   
/*     */   private final Supplier<Aquifer.FluidPicker> globalFluidPicker;
/*     */   
/*     */   public NoiseBasedChunkGenerator(BiomeSource $$0, Holder<NoiseGeneratorSettings> $$1) {
/*  64 */     super($$0);
/*     */     
/*  66 */     this.settings = $$1;
/*  67 */     this.globalFluidPicker = (Supplier<Aquifer.FluidPicker>)Suppliers.memoize(() -> createFluidPicker((NoiseGeneratorSettings)$$0.value()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static Aquifer.FluidPicker createFluidPicker(NoiseGeneratorSettings $$0) {
/*  72 */     Aquifer.FluidStatus $$1 = new Aquifer.FluidStatus(-54, Blocks.LAVA.defaultBlockState());
/*  73 */     int $$2 = $$0.seaLevel();
/*  74 */     Aquifer.FluidStatus $$3 = new Aquifer.FluidStatus($$2, $$0.defaultFluid());
/*     */     
/*  76 */     Aquifer.FluidStatus $$4 = new Aquifer.FluidStatus(DimensionType.MIN_Y * 2, Blocks.AIR.defaultBlockState());
/*     */ 
/*     */     
/*  79 */     return ($$4, $$5, $$6) -> ($$5 < Math.min(-54, $$1)) ? $$2 : $$3;
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
/*     */   public CompletableFuture<ChunkAccess> createBiomes(Executor $$0, RandomState $$1, Blender $$2, StructureManager $$3, ChunkAccess $$4) {
/*  92 */     return CompletableFuture.supplyAsync(Util.wrapThreadWithTaskName("init_biomes", () -> {
/*     */             doCreateBiomes($$0, $$1, $$2, $$3);
/*     */             return $$3;
/*  95 */           }), Util.backgroundExecutor());
/*     */   }
/*     */   
/*     */   private void doCreateBiomes(Blender $$0, RandomState $$1, StructureManager $$2, ChunkAccess $$3) {
/*  99 */     NoiseChunk $$4 = $$3.getOrCreateNoiseChunk($$3 -> createNoiseChunk($$3, $$0, $$1, $$2));
/*     */     
/* 101 */     BiomeResolver $$5 = BelowZeroRetrogen.getBiomeResolver($$0.getBiomeResolver((BiomeResolver)this.biomeSource), $$3);
/*     */     
/* 103 */     $$3.fillBiomesFromNoise($$5, $$4.cachedClimateSampler($$1.router(), ((NoiseGeneratorSettings)this.settings.value()).spawnTarget()));
/*     */   }
/*     */   
/*     */   private NoiseChunk createNoiseChunk(ChunkAccess $$0, StructureManager $$1, Blender $$2, RandomState $$3) {
/* 107 */     return NoiseChunk.forChunk($$0, $$3, Beardifier.forStructuresInChunk($$1, $$0.getPos()), (NoiseGeneratorSettings)this.settings.value(), this.globalFluidPicker.get(), $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Codec<? extends ChunkGenerator> codec() {
/* 112 */     return (Codec)CODEC;
/*     */   }
/*     */   
/*     */   public Holder<NoiseGeneratorSettings> generatorSettings() {
/* 116 */     return this.settings;
/*     */   }
/*     */   
/*     */   public boolean stable(ResourceKey<NoiseGeneratorSettings> $$0) {
/* 120 */     return this.settings.is($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBaseHeight(int $$0, int $$1, Heightmap.Types $$2, LevelHeightAccessor $$3, RandomState $$4) {
/* 125 */     return iterateNoiseColumn($$3, $$4, $$0, $$1, (MutableObject<NoiseColumn>)null, $$2.isOpaque()).orElse($$3.getMinBuildHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   public NoiseColumn getBaseColumn(int $$0, int $$1, LevelHeightAccessor $$2, RandomState $$3) {
/* 130 */     MutableObject<NoiseColumn> $$4 = new MutableObject();
/* 131 */     iterateNoiseColumn($$2, $$3, $$0, $$1, $$4, (Predicate<BlockState>)null);
/* 132 */     return (NoiseColumn)$$4.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDebugScreenInfo(List<String> $$0, RandomState $$1, BlockPos $$2) {
/* 137 */     DecimalFormat $$3 = new DecimalFormat("0.000");
/*     */     
/* 139 */     NoiseRouter $$4 = $$1.router();
/* 140 */     DensityFunction.SinglePointContext $$5 = new DensityFunction.SinglePointContext($$2.getX(), $$2.getY(), $$2.getZ());
/*     */     
/* 142 */     double $$6 = $$4.ridges().compute($$5);
/* 143 */     $$0.add("NoiseRouter T: " + $$3
/* 144 */         .format($$4.temperature().compute($$5)) + " V: " + $$3
/* 145 */         .format($$4.vegetation().compute($$5)) + " C: " + $$3
/* 146 */         .format($$4.continents().compute($$5)) + " E: " + $$3
/* 147 */         .format($$4.erosion().compute($$5)) + " D: " + $$3
/* 148 */         .format($$4.depth().compute($$5)) + " W: " + $$3
/* 149 */         .format($$6) + " PV: " + $$3
/* 150 */         .format(NoiseRouterData.peaksAndValleys((float)$$6)) + " AS: " + $$3
/* 151 */         .format($$4.initialDensityWithoutJaggedness().compute($$5)) + " N: " + $$3
/* 152 */         .format($$4.finalDensity().compute($$5)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OptionalInt iterateNoiseColumn(LevelHeightAccessor $$0, RandomState $$1, int $$2, int $$3, @Nullable MutableObject<NoiseColumn> $$4, @Nullable Predicate<BlockState> $$5) {
/*     */     BlockState[] $$12;
/* 163 */     NoiseSettings $$6 = ((NoiseGeneratorSettings)this.settings.value()).noiseSettings().clampToHeightAccessor($$0);
/* 164 */     int $$7 = $$6.getCellHeight();
/*     */     
/* 166 */     int $$8 = $$6.minY();
/* 167 */     int $$9 = Mth.floorDiv($$8, $$7);
/* 168 */     int $$10 = Mth.floorDiv($$6.height(), $$7);
/*     */     
/* 170 */     if ($$10 <= 0) {
/* 171 */       return OptionalInt.empty();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 176 */     if ($$4 == null) {
/* 177 */       BlockState[] $$11 = null;
/*     */     } else {
/* 179 */       $$12 = new BlockState[$$6.height()];
/* 180 */       $$4.setValue(new NoiseColumn($$8, $$12));
/*     */     } 
/*     */     
/* 183 */     int $$13 = $$6.getCellWidth();
/*     */     
/* 185 */     int $$14 = Math.floorDiv($$2, $$13);
/* 186 */     int $$15 = Math.floorDiv($$3, $$13);
/* 187 */     int $$16 = Math.floorMod($$2, $$13);
/* 188 */     int $$17 = Math.floorMod($$3, $$13);
/* 189 */     int $$18 = $$14 * $$13;
/* 190 */     int $$19 = $$15 * $$13;
/*     */     
/* 192 */     double $$20 = $$16 / $$13;
/* 193 */     double $$21 = $$17 / $$13;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 199 */     NoiseChunk $$22 = new NoiseChunk(1, $$1, $$18, $$19, $$6, DensityFunctions.BeardifierMarker.INSTANCE, (NoiseGeneratorSettings)this.settings.value(), this.globalFluidPicker.get(), Blender.empty());
/*     */ 
/*     */     
/* 202 */     $$22.initializeForFirstCellX();
/* 203 */     $$22.advanceCellX(0);
/*     */     
/* 205 */     for (int $$23 = $$10 - 1; $$23 >= 0; $$23--) {
/* 206 */       $$22.selectCellYZ($$23, 0);
/*     */       
/* 208 */       for (int $$24 = $$7 - 1; $$24 >= 0; $$24--) {
/* 209 */         int $$25 = ($$9 + $$23) * $$7 + $$24;
/*     */         
/* 211 */         double $$26 = $$24 / $$7;
/* 212 */         $$22.updateForY($$25, $$26);
/* 213 */         $$22.updateForX($$2, $$20);
/* 214 */         $$22.updateForZ($$3, $$21);
/*     */         
/* 216 */         BlockState $$27 = $$22.getInterpolatedState();
/* 217 */         BlockState $$28 = ($$27 == null) ? ((NoiseGeneratorSettings)this.settings.value()).defaultBlock() : $$27;
/*     */         
/* 219 */         if ($$12 != null) {
/* 220 */           int $$29 = $$23 * $$7 + $$24;
/* 221 */           $$12[$$29] = $$28;
/*     */         } 
/* 223 */         if ($$5 != null && $$5.test($$28)) {
/* 224 */           $$22.stopInterpolation();
/* 225 */           return OptionalInt.of($$25 + 1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 230 */     $$22.stopInterpolation();
/*     */     
/* 232 */     return OptionalInt.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void buildSurface(WorldGenRegion $$0, StructureManager $$1, RandomState $$2, ChunkAccess $$3) {
/* 237 */     if (!SharedConstants.debugVoidTerrain($$3.getPos())) {
/*     */ 
/*     */ 
/*     */       
/* 241 */       WorldGenerationContext $$4 = new WorldGenerationContext(this, (LevelHeightAccessor)$$0);
/*     */       
/* 243 */       buildSurface($$3, $$4, $$2, $$1, $$0.getBiomeManager(), $$0.registryAccess().registryOrThrow(Registries.BIOME), Blender.of($$0));
/*     */       return;
/*     */     } 
/*     */   } @VisibleForTesting
/*     */   public void buildSurface(ChunkAccess $$0, WorldGenerationContext $$1, RandomState $$2, StructureManager $$3, BiomeManager $$4, Registry<Biome> $$5, Blender $$6) {
/* 248 */     NoiseChunk $$7 = $$0.getOrCreateNoiseChunk($$3 -> createNoiseChunk($$3, $$0, $$1, $$2));
/* 249 */     NoiseGeneratorSettings $$8 = (NoiseGeneratorSettings)this.settings.value();
/* 250 */     $$2.surfaceSystem().buildSurface($$2, $$4, $$5, $$8.useLegacyRandomSource(), $$1, $$0, $$7, $$8.surfaceRule());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyCarvers(WorldGenRegion $$0, long $$1, RandomState $$2, BiomeManager $$3, StructureManager $$4, ChunkAccess $$5, GenerationStep.Carving $$6) {
/* 258 */     BiomeManager $$7 = $$3.withDifferentSource(($$1, $$2, $$3) -> this.biomeSource.getNoiseBiome($$1, $$2, $$3, $$0.sampler()));
/*     */     
/* 260 */     WorldgenRandom $$8 = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
/* 261 */     int $$9 = 8;
/*     */     
/* 263 */     ChunkPos $$10 = $$5.getPos();
/*     */     
/* 265 */     NoiseChunk $$11 = $$5.getOrCreateNoiseChunk($$3 -> createNoiseChunk($$3, $$0, Blender.of($$1), $$2));
/* 266 */     Aquifer $$12 = $$11.aquifer();
/* 267 */     CarvingContext $$13 = new CarvingContext(this, $$0.registryAccess(), $$5.getHeightAccessorForGeneration(), $$11, $$2, ((NoiseGeneratorSettings)this.settings.value()).surfaceRule());
/*     */     
/* 269 */     CarvingMask $$14 = ((ProtoChunk)$$5).getOrCreateCarvingMask($$6);
/* 270 */     for (int $$15 = -8; $$15 <= 8; $$15++) {
/* 271 */       for (int $$16 = -8; $$16 <= 8; $$16++) {
/* 272 */         ChunkPos $$17 = new ChunkPos($$10.x + $$15, $$10.z + $$16);
/* 273 */         ChunkAccess $$18 = $$0.getChunk($$17.x, $$17.z);
/* 274 */         BiomeGenerationSettings $$19 = $$18.carverBiome(() -> getBiomeGenerationSettings(this.biomeSource.getNoiseBiome(QuartPos.fromBlock($$0.getMinBlockX()), 0, QuartPos.fromBlock($$0.getMinBlockZ()), $$1.sampler())));
/* 275 */         Iterable<Holder<ConfiguredWorldCarver<?>>> $$20 = $$19.getCarvers($$6);
/*     */         
/* 277 */         int $$21 = 0;
/* 278 */         for (Holder<ConfiguredWorldCarver<?>> $$22 : $$20) {
/* 279 */           ConfiguredWorldCarver<?> $$23 = (ConfiguredWorldCarver)$$22.value();
/* 280 */           $$8.setLargeFeatureSeed($$1 + $$21, $$17.x, $$17.z);
/* 281 */           if ($$23.isStartChunk($$8)) {
/* 282 */             Objects.requireNonNull($$7); $$23.carve($$13, $$5, $$7::getBiome, $$8, $$12, $$17, $$14);
/*     */           } 
/* 284 */           $$21++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<ChunkAccess> fillFromNoise(Executor $$0, Blender $$1, RandomState $$2, StructureManager $$3, ChunkAccess $$4) {
/* 292 */     NoiseSettings $$5 = ((NoiseGeneratorSettings)this.settings.value()).noiseSettings().clampToHeightAccessor($$4.getHeightAccessorForGeneration());
/*     */     
/* 294 */     int $$6 = $$5.minY();
/* 295 */     int $$7 = Mth.floorDiv($$6, $$5.getCellHeight());
/* 296 */     int $$8 = Mth.floorDiv($$5.height(), $$5.getCellHeight());
/*     */     
/* 298 */     if ($$8 <= 0) {
/* 299 */       return CompletableFuture.completedFuture($$4);
/*     */     }
/*     */     
/* 302 */     int $$9 = $$4.getSectionIndex($$8 * $$5.getCellHeight() - 1 + $$6);
/* 303 */     int $$10 = $$4.getSectionIndex($$6);
/*     */     
/* 305 */     Set<LevelChunkSection> $$11 = Sets.newHashSet();
/* 306 */     for (int $$12 = $$9; $$12 >= $$10; $$12--) {
/* 307 */       LevelChunkSection $$13 = $$4.getSection($$12);
/* 308 */       $$13.acquire();
/* 309 */       $$11.add($$13);
/*     */     } 
/*     */     
/* 312 */     return CompletableFuture.<ChunkAccess>supplyAsync(
/* 313 */         Util.wrapThreadWithTaskName("wgen_fill_noise", () -> doFill($$0, $$1, $$2, $$3, $$4, $$5)), 
/* 314 */         Util.backgroundExecutor())
/* 315 */       .whenCompleteAsync(($$1, $$2) -> {
/*     */           for (LevelChunkSection $$3 : $$0) {
/*     */             $$3.release();
/*     */           }
/*     */         }$$0);
/*     */   }
/*     */   
/*     */   private ChunkAccess doFill(Blender $$0, StructureManager $$1, RandomState $$2, ChunkAccess $$3, int $$4, int $$5) {
/* 323 */     NoiseChunk $$6 = $$3.getOrCreateNoiseChunk($$3 -> createNoiseChunk($$3, $$0, $$1, $$2));
/*     */     
/* 325 */     Heightmap $$7 = $$3.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
/* 326 */     Heightmap $$8 = $$3.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);
/*     */     
/* 328 */     ChunkPos $$9 = $$3.getPos();
/*     */     
/* 330 */     int $$10 = $$9.getMinBlockX();
/* 331 */     int $$11 = $$9.getMinBlockZ();
/*     */     
/* 333 */     Aquifer $$12 = $$6.aquifer();
/*     */     
/* 335 */     $$6.initializeForFirstCellX();
/*     */     
/* 337 */     BlockPos.MutableBlockPos $$13 = new BlockPos.MutableBlockPos();
/*     */     
/* 339 */     int $$14 = $$6.cellWidth();
/* 340 */     int $$15 = $$6.cellHeight();
/*     */     
/* 342 */     int $$16 = 16 / $$14;
/* 343 */     int $$17 = 16 / $$14;
/*     */ 
/*     */     
/* 346 */     for (int $$18 = 0; $$18 < $$16; $$18++) {
/* 347 */       $$6.advanceCellX($$18);
/*     */       
/* 349 */       for (int $$19 = 0; $$19 < $$17; $$19++) {
/* 350 */         int $$20 = $$3.getSectionsCount() - 1;
/* 351 */         LevelChunkSection $$21 = $$3.getSection($$20);
/*     */         
/* 353 */         for (int $$22 = $$5 - 1; $$22 >= 0; $$22--) {
/* 354 */           $$6.selectCellYZ($$22, $$19);
/*     */           
/* 356 */           for (int $$23 = $$15 - 1; $$23 >= 0; $$23--) {
/* 357 */             int $$24 = ($$4 + $$22) * $$15 + $$23;
/* 358 */             int $$25 = $$24 & 0xF;
/*     */             
/* 360 */             int $$26 = $$3.getSectionIndex($$24);
/* 361 */             if ($$20 != $$26) {
/* 362 */               $$20 = $$26;
/* 363 */               $$21 = $$3.getSection($$26);
/*     */             } 
/*     */             
/* 366 */             double $$27 = $$23 / $$15;
/* 367 */             $$6.updateForY($$24, $$27);
/*     */             
/* 369 */             for (int $$28 = 0; $$28 < $$14; $$28++) {
/* 370 */               int $$29 = $$10 + $$18 * $$14 + $$28;
/* 371 */               int $$30 = $$29 & 0xF;
/*     */               
/* 373 */               double $$31 = $$28 / $$14;
/* 374 */               $$6.updateForX($$29, $$31);
/*     */               
/* 376 */               for (int $$32 = 0; $$32 < $$14; $$32++) {
/* 377 */                 int $$33 = $$11 + $$19 * $$14 + $$32;
/* 378 */                 int $$34 = $$33 & 0xF;
/*     */                 
/* 380 */                 double $$35 = $$32 / $$14;
/*     */                 
/* 382 */                 $$6.updateForZ($$33, $$35);
/*     */                 
/* 384 */                 BlockState $$36 = $$6.getInterpolatedState();
/*     */                 
/* 386 */                 if ($$36 == null) {
/* 387 */                   $$36 = ((NoiseGeneratorSettings)this.settings.value()).defaultBlock();
/*     */                 }
/*     */                 
/* 390 */                 $$36 = debugPreliminarySurfaceLevel($$6, $$29, $$24, $$33, $$36);
/*     */                 
/* 392 */                 if ($$36 != AIR && !SharedConstants.debugVoidTerrain($$3.getPos())) {
/*     */ 
/*     */                   
/* 395 */                   $$21.setBlockState($$30, $$25, $$34, $$36, false);
/* 396 */                   $$7.update($$30, $$24, $$34, $$36);
/* 397 */                   $$8.update($$30, $$24, $$34, $$36);
/*     */                   
/* 399 */                   if ($$12.shouldScheduleFluidUpdate() && !$$36.getFluidState().isEmpty()) {
/* 400 */                     $$13.set($$29, $$24, $$33);
/*     */                     
/* 402 */                     $$3.markPosForPostprocessing((BlockPos)$$13);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 409 */       }  $$6.swapSlices();
/*     */     } 
/* 411 */     $$6.stopInterpolation();
/* 412 */     return $$3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BlockState debugPreliminarySurfaceLevel(NoiseChunk $$0, int $$1, int $$2, int $$3, BlockState $$4) {
/* 423 */     return $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGenDepth() {
/* 428 */     return ((NoiseGeneratorSettings)this.settings.value()).noiseSettings().height();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSeaLevel() {
/* 433 */     return ((NoiseGeneratorSettings)this.settings.value()).seaLevel();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinY() {
/* 438 */     return ((NoiseGeneratorSettings)this.settings.value()).noiseSettings().minY();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawnOriginalMobs(WorldGenRegion $$0) {
/* 444 */     if (((NoiseGeneratorSettings)this.settings.value()).disableMobGeneration()) {
/*     */       return;
/*     */     }
/* 447 */     ChunkPos $$1 = $$0.getCenter();
/*     */     
/* 449 */     Holder<Biome> $$2 = $$0.getBiome($$1.getWorldPosition().atY($$0.getMaxBuildHeight() - 1));
/*     */     
/* 451 */     WorldgenRandom $$3 = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
/* 452 */     $$3.setDecorationSeed($$0.getSeed(), $$1.getMinBlockX(), $$1.getMinBlockZ());
/* 453 */     NaturalSpawner.spawnMobsForChunkGeneration((ServerLevelAccessor)$$0, $$2, $$1, $$3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\NoiseBasedChunkGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */