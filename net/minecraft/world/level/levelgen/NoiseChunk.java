/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.util.KeyDispatchDataCodec;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.biome.Climate;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.levelgen.blending.Blender;
/*     */ import net.minecraft.world.level.levelgen.material.MaterialRuleList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NoiseChunk
/*     */   implements DensityFunction.ContextProvider, DensityFunction.FunctionContext
/*     */ {
/*     */   private final NoiseSettings noiseSettings;
/*     */   final int cellCountXZ;
/*     */   final int cellCountY;
/*     */   final int cellNoiseMinY;
/*     */   private final int firstCellX;
/*     */   private final int firstCellZ;
/*     */   final int firstNoiseX;
/*     */   final int firstNoiseZ;
/*     */   final List<NoiseInterpolator> interpolators;
/*     */   final List<CacheAllInCell> cellCaches;
/*  42 */   private final Map<DensityFunction, DensityFunction> wrapped = new HashMap<>();
/*     */   
/*  44 */   private final Long2IntMap preliminarySurfaceLevel = (Long2IntMap)new Long2IntOpenHashMap();
/*     */   
/*     */   private final Aquifer aquifer;
/*     */   
/*     */   private final DensityFunction initialDensityNoJaggedness;
/*     */   
/*     */   private final BlockStateFiller blockStateRule;
/*     */   
/*     */   private final Blender blender;
/*     */   
/*     */   private final FlatCache blendAlpha;
/*     */   private final FlatCache blendOffset;
/*     */   private final DensityFunctions.BeardifierOrMarker beardifier;
/*  57 */   private long lastBlendingDataPos = ChunkPos.INVALID_CHUNK_POS;
/*  58 */   private Blender.BlendingOutput lastBlendingOutput = new Blender.BlendingOutput(1.0D, 0.0D);
/*     */   
/*     */   final int noiseSizeXZ;
/*     */   
/*     */   final int cellWidth;
/*     */   
/*     */   final int cellHeight;
/*     */   
/*     */   boolean interpolating;
/*     */   
/*     */   boolean fillingCell;
/*     */   
/*     */   private int cellStartBlockX;
/*     */   
/*     */   int cellStartBlockY;
/*     */   private int cellStartBlockZ;
/*     */   int inCellX;
/*     */   int inCellY;
/*     */   int inCellZ;
/*     */   long interpolationCounter;
/*     */   long arrayInterpolationCounter;
/*     */   int arrayIndex;
/*     */   
/*  81 */   private final DensityFunction.ContextProvider sliceFillingContextProvider = new DensityFunction.ContextProvider()
/*     */     {
/*     */       public DensityFunction.FunctionContext forIndex(int $$0) {
/*  84 */         NoiseChunk.this.cellStartBlockY = ($$0 + NoiseChunk.this.cellNoiseMinY) * NoiseChunk.this.cellHeight;
/*  85 */         NoiseChunk.this.interpolationCounter++;
/*     */         
/*  87 */         NoiseChunk.this.inCellY = 0;
/*  88 */         NoiseChunk.this.arrayIndex = $$0;
/*  89 */         return NoiseChunk.this;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void fillAllDirectly(double[] $$0, DensityFunction $$1) {
/*  95 */         for (int $$2 = 0; $$2 < NoiseChunk.this.cellCountY + 1; $$2++) {
/*  96 */           NoiseChunk.this.cellStartBlockY = ($$2 + NoiseChunk.this.cellNoiseMinY) * NoiseChunk.this.cellHeight;
/*  97 */           NoiseChunk.this.interpolationCounter++;
/*     */           
/*  99 */           NoiseChunk.this.inCellY = 0;
/* 100 */           NoiseChunk.this.arrayIndex = $$2;
/*     */           
/* 102 */           $$0[$$2] = $$1.compute(NoiseChunk.this);
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/*     */   public static NoiseChunk forChunk(ChunkAccess $$0, RandomState $$1, DensityFunctions.BeardifierOrMarker $$2, NoiseGeneratorSettings $$3, Aquifer.FluidPicker $$4, Blender $$5) {
/* 108 */     NoiseSettings $$6 = $$3.noiseSettings().clampToHeightAccessor((LevelHeightAccessor)$$0);
/* 109 */     ChunkPos $$7 = $$0.getPos();
/* 110 */     int $$8 = 16 / $$6.getCellWidth();
/* 111 */     return new NoiseChunk($$8, $$1, $$7.getMinBlockX(), $$7.getMinBlockZ(), $$6, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NoiseChunk(int $$0, RandomState $$1, int $$2, int $$3, NoiseSettings $$4, DensityFunctions.BeardifierOrMarker $$5, NoiseGeneratorSettings $$6, Aquifer.FluidPicker $$7, Blender $$8) {
/* 120 */     this.noiseSettings = $$4;
/*     */     
/* 122 */     this.cellWidth = $$4.getCellWidth();
/* 123 */     this.cellHeight = $$4.getCellHeight();
/*     */     
/* 125 */     this.cellCountXZ = $$0;
/* 126 */     this.cellCountY = Mth.floorDiv($$4.height(), this.cellHeight);
/* 127 */     this.cellNoiseMinY = Mth.floorDiv($$4.minY(), this.cellHeight);
/*     */     
/* 129 */     this.firstCellX = Math.floorDiv($$2, this.cellWidth);
/* 130 */     this.firstCellZ = Math.floorDiv($$3, this.cellWidth);
/*     */     
/* 132 */     this.interpolators = Lists.newArrayList();
/* 133 */     this.cellCaches = Lists.newArrayList();
/*     */     
/* 135 */     this.firstNoiseX = QuartPos.fromBlock($$2);
/* 136 */     this.firstNoiseZ = QuartPos.fromBlock($$3);
/*     */     
/* 138 */     this.noiseSizeXZ = QuartPos.fromBlock($$0 * this.cellWidth);
/*     */     
/* 140 */     this.blender = $$8;
/* 141 */     this.beardifier = $$5;
/*     */     
/* 143 */     this.blendAlpha = new FlatCache(new BlendAlpha(), false);
/* 144 */     this.blendOffset = new FlatCache(new BlendOffset(), false);
/*     */ 
/*     */     
/* 147 */     for (int $$9 = 0; $$9 <= this.noiseSizeXZ; $$9++) {
/* 148 */       int $$10 = this.firstNoiseX + $$9;
/* 149 */       int $$11 = QuartPos.toBlock($$10);
/*     */       
/* 151 */       for (int $$12 = 0; $$12 <= this.noiseSizeXZ; $$12++) {
/* 152 */         int $$13 = this.firstNoiseZ + $$12;
/* 153 */         int $$14 = QuartPos.toBlock($$13);
/*     */         
/* 155 */         Blender.BlendingOutput $$15 = $$8.blendOffsetAndFactor($$11, $$14);
/*     */         
/* 157 */         this.blendAlpha.values[$$9][$$12] = $$15.alpha();
/* 158 */         this.blendOffset.values[$$9][$$12] = $$15.blendingOffset();
/*     */       } 
/*     */     } 
/*     */     
/* 162 */     NoiseRouter $$16 = $$1.router();
/*     */ 
/*     */     
/* 165 */     NoiseRouter $$17 = $$16.mapAll(this::wrap);
/*     */ 
/*     */ 
/*     */     
/* 169 */     if (!$$6.isAquifersEnabled()) {
/* 170 */       this.aquifer = Aquifer.createDisabled($$7);
/*     */     } else {
/* 172 */       int $$18 = SectionPos.blockToSectionCoord($$2);
/* 173 */       int $$19 = SectionPos.blockToSectionCoord($$3);
/* 174 */       this.aquifer = Aquifer.create(this, new ChunkPos($$18, $$19), $$17, $$1
/*     */ 
/*     */ 
/*     */           
/* 178 */           .aquiferRandom(), $$4
/* 179 */           .minY(), $$4
/* 180 */           .height(), $$7);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 185 */     ImmutableList.Builder<BlockStateFiller> $$20 = ImmutableList.builder();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     DensityFunction $$21 = DensityFunctions.cacheAllInCell(DensityFunctions.add($$17.finalDensity(), DensityFunctions.BeardifierMarker.INSTANCE)).mapAll(this::wrap);
/*     */ 
/*     */     
/* 193 */     $$20.add($$1 -> this.aquifer.computeSubstance($$1, $$0.compute($$1)));
/*     */ 
/*     */     
/* 196 */     if ($$6.oreVeinsEnabled()) {
/* 197 */       $$20.add(OreVeinifier.create($$17
/* 198 */             .veinToggle(), $$17
/* 199 */             .veinRidged(), $$17
/* 200 */             .veinGap(), $$1
/* 201 */             .oreRandom()));
/*     */     }
/*     */ 
/*     */     
/* 205 */     this.blockStateRule = (BlockStateFiller)new MaterialRuleList((List)$$20.build());
/*     */ 
/*     */     
/* 208 */     this.initialDensityNoJaggedness = $$17.initialDensityWithoutJaggedness();
/*     */   }
/*     */   
/*     */   protected Climate.Sampler cachedClimateSampler(NoiseRouter $$0, List<Climate.ParameterPoint> $$1) {
/* 212 */     return new Climate.Sampler($$0
/* 213 */         .temperature().mapAll(this::wrap), $$0
/* 214 */         .vegetation().mapAll(this::wrap), $$0
/* 215 */         .continents().mapAll(this::wrap), $$0
/* 216 */         .erosion().mapAll(this::wrap), $$0
/* 217 */         .depth().mapAll(this::wrap), $$0
/* 218 */         .ridges().mapAll(this::wrap), $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected BlockState getInterpolatedState() {
/* 225 */     return this.blockStateRule.calculate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int blockX() {
/* 230 */     return this.cellStartBlockX + this.inCellX;
/*     */   }
/*     */ 
/*     */   
/*     */   public int blockY() {
/* 235 */     return this.cellStartBlockY + this.inCellY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int blockZ() {
/* 240 */     return this.cellStartBlockZ + this.inCellZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int preliminarySurfaceLevel(int $$0, int $$1) {
/* 248 */     int $$2 = QuartPos.toBlock(QuartPos.fromBlock($$0));
/* 249 */     int $$3 = QuartPos.toBlock(QuartPos.fromBlock($$1));
/* 250 */     return this.preliminarySurfaceLevel.computeIfAbsent(ColumnPos.asLong($$2, $$3), this::computePreliminarySurfaceLevel);
/*     */   }
/*     */   
/*     */   private int computePreliminarySurfaceLevel(long $$0) {
/* 254 */     int $$1 = ColumnPos.getX($$0);
/* 255 */     int $$2 = ColumnPos.getZ($$0);
/*     */     
/* 257 */     int $$3 = this.noiseSettings.minY();
/*     */     int $$4;
/* 259 */     for ($$4 = $$3 + this.noiseSettings.height(); $$4 >= $$3; $$4 -= this.cellHeight) {
/* 260 */       if (this.initialDensityNoJaggedness.compute(new DensityFunction.SinglePointContext($$1, $$4, $$2)) > 0.390625D) {
/* 261 */         return $$4;
/*     */       }
/*     */     } 
/*     */     
/* 265 */     return Integer.MAX_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public Blender getBlender() {
/* 270 */     return this.blender;
/*     */   }
/*     */   
/*     */   private void fillSlice(boolean $$0, int $$1) {
/* 274 */     this.cellStartBlockX = $$1 * this.cellWidth;
/*     */     
/* 276 */     this.inCellX = 0;
/*     */     
/* 278 */     for (int $$2 = 0; $$2 < this.cellCountXZ + 1; $$2++) {
/* 279 */       int $$3 = this.firstCellZ + $$2;
/* 280 */       this.cellStartBlockZ = $$3 * this.cellWidth;
/* 281 */       this.inCellZ = 0;
/*     */       
/* 283 */       this.arrayInterpolationCounter++;
/*     */       
/* 285 */       for (NoiseInterpolator $$4 : this.interpolators) {
/* 286 */         double[] $$5 = ($$0 ? $$4.slice0 : $$4.slice1)[$$2];
/*     */         
/* 288 */         $$4.fillArray($$5, this.sliceFillingContextProvider);
/*     */       } 
/*     */     } 
/* 291 */     this.arrayInterpolationCounter++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initializeForFirstCellX() {
/* 296 */     if (this.interpolating) {
/* 297 */       throw new IllegalStateException("Staring interpolation twice");
/*     */     }
/* 299 */     this.interpolating = true;
/* 300 */     this.interpolationCounter = 0L;
/* 301 */     fillSlice(true, this.firstCellX);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void advanceCellX(int $$0) {
/* 312 */     fillSlice(false, this.firstCellX + $$0 + 1);
/* 313 */     this.cellStartBlockX = (this.firstCellX + $$0) * this.cellWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NoiseChunk forIndex(int $$0) {
/* 319 */     int $$1 = Math.floorMod($$0, this.cellWidth);
/* 320 */     int $$2 = Math.floorDiv($$0, this.cellWidth);
/*     */     
/* 322 */     int $$3 = Math.floorMod($$2, this.cellWidth);
/* 323 */     int $$4 = this.cellHeight - 1 - Math.floorDiv($$2, this.cellWidth);
/*     */     
/* 325 */     this.inCellX = $$3;
/* 326 */     this.inCellY = $$4;
/* 327 */     this.inCellZ = $$1;
/*     */     
/* 329 */     this.arrayIndex = $$0;
/* 330 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillAllDirectly(double[] $$0, DensityFunction $$1) {
/* 336 */     this.arrayIndex = 0;
/* 337 */     for (int $$2 = this.cellHeight - 1; $$2 >= 0; $$2--) {
/* 338 */       this.inCellY = $$2;
/* 339 */       for (int $$3 = 0; $$3 < this.cellWidth; $$3++) {
/* 340 */         this.inCellX = $$3;
/* 341 */         for (int $$4 = 0; $$4 < this.cellWidth; $$4++) {
/* 342 */           this.inCellZ = $$4;
/* 343 */           $$0[this.arrayIndex++] = $$1.compute(this);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void selectCellYZ(int $$0, int $$1) {
/* 350 */     this.interpolators.forEach($$2 -> $$2.selectCellYZ($$0, $$1));
/*     */     
/* 352 */     this.fillingCell = true;
/* 353 */     this.cellStartBlockY = ($$0 + this.cellNoiseMinY) * this.cellHeight;
/* 354 */     this.cellStartBlockZ = (this.firstCellZ + $$1) * this.cellWidth;
/*     */     
/* 356 */     this.arrayInterpolationCounter++;
/*     */     
/* 358 */     for (CacheAllInCell $$2 : this.cellCaches) {
/* 359 */       $$2.noiseFiller.fillArray($$2.values, this);
/*     */     }
/*     */     
/* 362 */     this.arrayInterpolationCounter++;
/* 363 */     this.fillingCell = false;
/*     */   }
/*     */   
/*     */   public void updateForY(int $$0, double $$1) {
/* 367 */     this.inCellY = $$0 - this.cellStartBlockY;
/* 368 */     this.interpolators.forEach($$1 -> $$1.updateForY($$0));
/*     */   }
/*     */   
/*     */   public void updateForX(int $$0, double $$1) {
/* 372 */     this.inCellX = $$0 - this.cellStartBlockX;
/* 373 */     this.interpolators.forEach($$1 -> $$1.updateForX($$0));
/*     */   }
/*     */   
/*     */   public void updateForZ(int $$0, double $$1) {
/* 377 */     this.inCellZ = $$0 - this.cellStartBlockZ;
/* 378 */     this.interpolationCounter++;
/* 379 */     this.interpolators.forEach($$1 -> $$1.updateForZ($$0));
/*     */   }
/*     */   
/*     */   public void stopInterpolation() {
/* 383 */     if (!this.interpolating) {
/* 384 */       throw new IllegalStateException("Staring interpolation twice");
/*     */     }
/* 386 */     this.interpolating = false;
/*     */   }
/*     */   
/*     */   public void swapSlices() {
/* 390 */     this.interpolators.forEach(NoiseInterpolator::swapSlices);
/*     */   }
/*     */   
/*     */   public Aquifer aquifer() {
/* 394 */     return this.aquifer;
/*     */   }
/*     */   
/*     */   protected int cellWidth() {
/* 398 */     return this.cellWidth;
/*     */   }
/*     */   
/*     */   protected int cellHeight() {
/* 402 */     return this.cellHeight;
/*     */   }
/*     */   
/*     */   private static interface NoiseChunkDensityFunction
/*     */     extends DensityFunction {
/*     */     DensityFunction wrapped();
/*     */     
/*     */     default double minValue() {
/* 410 */       return wrapped().minValue();
/*     */     }
/*     */ 
/*     */     
/*     */     default double maxValue() {
/* 415 */       return wrapped().maxValue();
/*     */     }
/*     */   }
/*     */   
/*     */   private class FlatCache implements DensityFunctions.MarkerOrMarked, NoiseChunkDensityFunction {
/*     */     private final DensityFunction noiseFiller;
/*     */     final double[][] values;
/*     */     
/*     */     FlatCache(DensityFunction $$0, boolean $$1) {
/* 424 */       this.noiseFiller = $$0;
/* 425 */       this.values = new double[NoiseChunk.this.noiseSizeXZ + 1][NoiseChunk.this.noiseSizeXZ + 1];
/* 426 */       if ($$1) {
/* 427 */         for (int $$2 = 0; $$2 <= NoiseChunk.this.noiseSizeXZ; $$2++) {
/* 428 */           int $$3 = NoiseChunk.this.firstNoiseX + $$2;
/* 429 */           int $$4 = QuartPos.toBlock($$3);
/*     */           
/* 431 */           for (int $$5 = 0; $$5 <= NoiseChunk.this.noiseSizeXZ; $$5++) {
/* 432 */             int $$6 = NoiseChunk.this.firstNoiseZ + $$5;
/* 433 */             int $$7 = QuartPos.toBlock($$6);
/*     */             
/* 435 */             this.values[$$2][$$5] = $$0.compute(new DensityFunction.SinglePointContext($$4, 0, $$7));
/*     */           } 
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public double compute(DensityFunction.FunctionContext $$0) {
/* 443 */       int $$1 = QuartPos.fromBlock($$0.blockX());
/* 444 */       int $$2 = QuartPos.fromBlock($$0.blockZ());
/*     */       
/* 446 */       int $$3 = $$1 - NoiseChunk.this.firstNoiseX;
/* 447 */       int $$4 = $$2 - NoiseChunk.this.firstNoiseZ;
/* 448 */       int $$5 = this.values.length;
/*     */       
/* 450 */       if ($$3 >= 0 && $$4 >= 0 && $$3 < $$5 && $$4 < $$5) {
/* 451 */         return this.values[$$3][$$4];
/*     */       }
/* 453 */       return this.noiseFiller.compute($$0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/* 459 */       $$1.fillAllDirectly($$0, this);
/*     */     }
/*     */ 
/*     */     
/*     */     public DensityFunction wrapped() {
/* 464 */       return this.noiseFiller;
/*     */     }
/*     */ 
/*     */     
/*     */     public DensityFunctions.Marker.Type type() {
/* 469 */       return DensityFunctions.Marker.Type.FlatCache;
/*     */     }
/*     */   }
/*     */   
/*     */   private class CacheAllInCell implements DensityFunctions.MarkerOrMarked, NoiseChunkDensityFunction {
/*     */     final DensityFunction noiseFiller;
/*     */     final double[] values;
/*     */     
/*     */     CacheAllInCell(DensityFunction $$0) {
/* 478 */       this.noiseFiller = $$0;
/* 479 */       this.values = new double[NoiseChunk.this.cellWidth * NoiseChunk.this.cellWidth * NoiseChunk.this.cellHeight];
/* 480 */       NoiseChunk.this.cellCaches.add(this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double compute(DensityFunction.FunctionContext $$0) {
/* 486 */       if ($$0 != NoiseChunk.this) {
/* 487 */         return this.noiseFiller.compute($$0);
/*     */       }
/* 489 */       if (!NoiseChunk.this.interpolating) {
/* 490 */         throw new IllegalStateException("Trying to sample interpolator outside the interpolation loop");
/*     */       }
/* 492 */       int $$1 = NoiseChunk.this.inCellX;
/* 493 */       int $$2 = NoiseChunk.this.inCellY;
/* 494 */       int $$3 = NoiseChunk.this.inCellZ;
/*     */       
/* 496 */       if ($$1 >= 0 && $$2 >= 0 && $$3 >= 0 && $$1 < NoiseChunk.this.cellWidth && $$2 < NoiseChunk.this.cellHeight && $$3 < NoiseChunk.this.cellWidth) {
/* 497 */         return this.values[((NoiseChunk.this.cellHeight - 1 - $$2) * NoiseChunk.this.cellWidth + $$1) * NoiseChunk.this.cellWidth + $$3];
/*     */       }
/* 499 */       return this.noiseFiller.compute($$0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/* 505 */       $$1.fillAllDirectly($$0, this);
/*     */     }
/*     */ 
/*     */     
/*     */     public DensityFunction wrapped() {
/* 510 */       return this.noiseFiller;
/*     */     }
/*     */ 
/*     */     
/*     */     public DensityFunctions.Marker.Type type() {
/* 515 */       return DensityFunctions.Marker.Type.CacheAllInCell;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class NoiseInterpolator
/*     */     implements DensityFunctions.MarkerOrMarked, NoiseChunkDensityFunction
/*     */   {
/*     */     double[][] slice0;
/*     */     
/*     */     double[][] slice1;
/*     */     
/*     */     private final DensityFunction noiseFiller;
/*     */     
/*     */     private double noise000;
/*     */     
/*     */     private double noise001;
/*     */     
/*     */     private double noise100;
/*     */     
/*     */     private double noise101;
/*     */     
/*     */     private double noise010;
/*     */     
/*     */     private double noise011;
/*     */     
/*     */     private double noise110;
/*     */     
/*     */     private double noise111;
/*     */     
/*     */     private double valueXZ00;
/*     */     
/*     */     private double valueXZ10;
/*     */     
/*     */     private double valueXZ01;
/*     */     
/*     */     private double valueXZ11;
/*     */     
/*     */     private double valueZ0;
/*     */     private double valueZ1;
/*     */     private double value;
/*     */     
/*     */     NoiseInterpolator(DensityFunction $$1) {
/* 558 */       this.noiseFiller = $$1;
/* 559 */       this.slice0 = allocateSlice($$0.cellCountY, $$0.cellCountXZ);
/* 560 */       this.slice1 = allocateSlice($$0.cellCountY, $$0.cellCountXZ);
/*     */       
/* 562 */       $$0.interpolators.add(this);
/*     */     }
/*     */     
/*     */     private double[][] allocateSlice(int $$0, int $$1) {
/* 566 */       int $$2 = $$1 + 1;
/* 567 */       int $$3 = $$0 + 1;
/* 568 */       double[][] $$4 = new double[$$2][$$3];
/* 569 */       for (int $$5 = 0; $$5 < $$2; $$5++) {
/* 570 */         $$4[$$5] = new double[$$3];
/*     */       }
/* 572 */       return $$4;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void selectCellYZ(int $$0, int $$1) {
/* 582 */       this.noise000 = this.slice0[$$1][$$0];
/* 583 */       this.noise001 = this.slice0[$$1 + 1][$$0];
/* 584 */       this.noise100 = this.slice1[$$1][$$0];
/* 585 */       this.noise101 = this.slice1[$$1 + 1][$$0];
/*     */       
/* 587 */       this.noise010 = this.slice0[$$1][$$0 + 1];
/* 588 */       this.noise011 = this.slice0[$$1 + 1][$$0 + 1];
/* 589 */       this.noise110 = this.slice1[$$1][$$0 + 1];
/* 590 */       this.noise111 = this.slice1[$$1 + 1][$$0 + 1];
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void updateForY(double $$0) {
/* 599 */       this.valueXZ00 = Mth.lerp($$0, this.noise000, this.noise010);
/* 600 */       this.valueXZ10 = Mth.lerp($$0, this.noise100, this.noise110);
/* 601 */       this.valueXZ01 = Mth.lerp($$0, this.noise001, this.noise011);
/* 602 */       this.valueXZ11 = Mth.lerp($$0, this.noise101, this.noise111);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void updateForX(double $$0) {
/* 611 */       this.valueZ0 = Mth.lerp($$0, this.valueXZ00, this.valueXZ10);
/* 612 */       this.valueZ1 = Mth.lerp($$0, this.valueXZ01, this.valueXZ11);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void updateForZ(double $$0) {
/* 621 */       this.value = Mth.lerp($$0, this.valueZ0, this.valueZ1);
/*     */     }
/*     */ 
/*     */     
/*     */     public double compute(DensityFunction.FunctionContext $$0) {
/* 626 */       if ($$0 != NoiseChunk.this) {
/* 627 */         return this.noiseFiller.compute($$0);
/*     */       }
/* 629 */       if (!NoiseChunk.this.interpolating) {
/* 630 */         throw new IllegalStateException("Trying to sample interpolator outside the interpolation loop");
/*     */       }
/* 632 */       if (NoiseChunk.this.fillingCell) {
/* 633 */         return Mth.lerp3(NoiseChunk.this.inCellX / NoiseChunk.this.cellWidth, NoiseChunk.this.inCellY / NoiseChunk.this.cellHeight, NoiseChunk.this.inCellZ / NoiseChunk.this.cellWidth, this.noise000, this.noise100, this.noise010, this.noise110, this.noise001, this.noise101, this.noise011, this.noise111);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 641 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/* 646 */       if (NoiseChunk.this.fillingCell) {
/*     */         
/* 648 */         $$1.fillAllDirectly($$0, this);
/*     */         return;
/*     */       } 
/* 651 */       wrapped().fillArray($$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public DensityFunction wrapped() {
/* 656 */       return this.noiseFiller;
/*     */     }
/*     */ 
/*     */     
/*     */     private void swapSlices() {
/* 661 */       double[][] $$0 = this.slice0;
/* 662 */       this.slice0 = this.slice1;
/* 663 */       this.slice1 = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public DensityFunctions.Marker.Type type() {
/* 668 */       return DensityFunctions.Marker.Type.Interpolated;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private class CacheOnce
/*     */     implements DensityFunctions.MarkerOrMarked, NoiseChunkDensityFunction
/*     */   {
/*     */     private final DensityFunction function;
/*     */     private long lastCounter;
/*     */     private long lastArrayCounter;
/*     */     private double lastValue;
/*     */     @Nullable
/*     */     private double[] lastArray;
/*     */     
/*     */     CacheOnce(DensityFunction $$0) {
/* 684 */       this.function = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public double compute(DensityFunction.FunctionContext $$0) {
/* 689 */       if ($$0 != NoiseChunk.this) {
/* 690 */         return this.function.compute($$0);
/*     */       }
/* 692 */       if (this.lastArray != null && this.lastArrayCounter == NoiseChunk.this.arrayInterpolationCounter) {
/* 693 */         return this.lastArray[NoiseChunk.this.arrayIndex];
/*     */       }
/* 695 */       if (this.lastCounter == NoiseChunk.this.interpolationCounter) {
/* 696 */         return this.lastValue;
/*     */       }
/* 698 */       this.lastCounter = NoiseChunk.this.interpolationCounter;
/* 699 */       double $$1 = this.function.compute($$0);
/* 700 */       this.lastValue = $$1;
/* 701 */       return $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/* 706 */       if (this.lastArray != null && this.lastArrayCounter == NoiseChunk.this.arrayInterpolationCounter) {
/* 707 */         System.arraycopy(this.lastArray, 0, $$0, 0, $$0.length);
/*     */         return;
/*     */       } 
/* 710 */       wrapped().fillArray($$0, $$1);
/* 711 */       if (this.lastArray != null && this.lastArray.length == $$0.length) {
/* 712 */         System.arraycopy($$0, 0, this.lastArray, 0, $$0.length);
/*     */       } else {
/* 714 */         this.lastArray = (double[])$$0.clone();
/*     */       } 
/* 716 */       this.lastArrayCounter = NoiseChunk.this.arrayInterpolationCounter;
/*     */     }
/*     */ 
/*     */     
/*     */     public DensityFunction wrapped() {
/* 721 */       return this.function;
/*     */     }
/*     */ 
/*     */     
/*     */     public DensityFunctions.Marker.Type type() {
/* 726 */       return DensityFunctions.Marker.Type.CacheOnce;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Cache2D
/*     */     implements DensityFunctions.MarkerOrMarked, NoiseChunkDensityFunction
/*     */   {
/*     */     private final DensityFunction function;
/* 735 */     private long lastPos2D = ChunkPos.INVALID_CHUNK_POS;
/*     */     private double lastValue;
/*     */     
/*     */     Cache2D(DensityFunction $$0) {
/* 739 */       this.function = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public double compute(DensityFunction.FunctionContext $$0) {
/* 744 */       int $$1 = $$0.blockX();
/* 745 */       int $$2 = $$0.blockZ();
/* 746 */       long $$3 = ChunkPos.asLong($$1, $$2);
/* 747 */       if (this.lastPos2D == $$3) {
/* 748 */         return this.lastValue;
/*     */       }
/* 750 */       this.lastPos2D = $$3;
/* 751 */       double $$4 = this.function.compute($$0);
/* 752 */       this.lastValue = $$4;
/* 753 */       return $$4;
/*     */     }
/*     */ 
/*     */     
/*     */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/* 758 */       this.function.fillArray($$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public DensityFunction wrapped() {
/* 763 */       return this.function;
/*     */     }
/*     */ 
/*     */     
/*     */     public DensityFunctions.Marker.Type type() {
/* 768 */       return DensityFunctions.Marker.Type.Cache2D;
/*     */     }
/*     */   }
/*     */   
/*     */   Blender.BlendingOutput getOrComputeBlendingOutput(int $$0, int $$1) {
/* 773 */     long $$2 = ChunkPos.asLong($$0, $$1);
/* 774 */     if (this.lastBlendingDataPos == $$2) {
/* 775 */       return this.lastBlendingOutput;
/*     */     }
/* 777 */     this.lastBlendingDataPos = $$2;
/* 778 */     Blender.BlendingOutput $$3 = this.blender.blendOffsetAndFactor($$0, $$1);
/* 779 */     this.lastBlendingOutput = $$3;
/* 780 */     return $$3;
/*     */   }
/*     */   
/*     */   private class BlendAlpha
/*     */     implements NoiseChunkDensityFunction {
/*     */     public DensityFunction wrapped() {
/* 786 */       return DensityFunctions.BlendAlpha.INSTANCE;
/*     */     }
/*     */ 
/*     */     
/*     */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/* 791 */       return wrapped().mapAll($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public double compute(DensityFunction.FunctionContext $$0) {
/* 796 */       return NoiseChunk.this.getOrComputeBlendingOutput($$0.blockX(), $$0.blockZ()).alpha();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/* 802 */       $$1.fillAllDirectly($$0, this);
/*     */     }
/*     */ 
/*     */     
/*     */     public double minValue() {
/* 807 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     public double maxValue() {
/* 812 */       return 1.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/* 817 */       return DensityFunctions.BlendAlpha.CODEC;
/*     */     }
/*     */   }
/*     */   
/*     */   private class BlendOffset
/*     */     implements NoiseChunkDensityFunction {
/*     */     public DensityFunction wrapped() {
/* 824 */       return DensityFunctions.BlendOffset.INSTANCE;
/*     */     }
/*     */ 
/*     */     
/*     */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/* 829 */       return wrapped().mapAll($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public double compute(DensityFunction.FunctionContext $$0) {
/* 834 */       return NoiseChunk.this.getOrComputeBlendingOutput($$0.blockX(), $$0.blockZ()).blendingOffset();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/* 840 */       $$1.fillAllDirectly($$0, this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double minValue() {
/* 846 */       return Double.NEGATIVE_INFINITY;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double maxValue() {
/* 852 */       return Double.POSITIVE_INFINITY;
/*     */     }
/*     */ 
/*     */     
/*     */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/* 857 */       return DensityFunctions.BlendOffset.CODEC;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DensityFunction wrap(DensityFunction $$0) {
/* 868 */     return this.wrapped.computeIfAbsent($$0, this::wrapNew);
/*     */   }
/*     */   
/*     */   private DensityFunction wrapNew(DensityFunction $$0) {
/* 872 */     if ($$0 instanceof DensityFunctions.Marker) { DensityFunctions.Marker $$1 = (DensityFunctions.Marker)$$0;
/* 873 */       switch ($$1.type()) { default: throw new IncompatibleClassChangeError();case Interpolated: case FlatCache: case Cache2D: case CacheOnce: case CacheAllInCell: break; }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 878 */         new CacheAllInCell($$1.wrapped()); }
/*     */ 
/*     */     
/* 881 */     if (this.blender != Blender.empty()) {
/* 882 */       if ($$0 == DensityFunctions.BlendAlpha.INSTANCE) {
/* 883 */         return this.blendAlpha;
/*     */       }
/* 885 */       if ($$0 == DensityFunctions.BlendOffset.INSTANCE) {
/* 886 */         return this.blendOffset;
/*     */       }
/*     */     } 
/* 889 */     if ($$0 == DensityFunctions.BeardifierMarker.INSTANCE) {
/* 890 */       return this.beardifier;
/*     */     }
/* 892 */     if ($$0 instanceof DensityFunctions.HolderHolder) { DensityFunctions.HolderHolder $$2 = (DensityFunctions.HolderHolder)$$0;
/* 893 */       return (DensityFunction)$$2.function().value(); }
/*     */     
/* 895 */     return $$0;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface BlockStateFiller {
/*     */     @Nullable
/*     */     BlockState calculate(DensityFunction.FunctionContext param1FunctionContext);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\NoiseChunk.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */