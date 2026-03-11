/*     */ package net.minecraft.world.level.levelgen.blending;
/*     */ 
/*     */ import com.google.common.primitives.Doubles;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.DoubleStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Direction8;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.server.level.WorldGenRegion;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ 
/*     */ public class BlendingData {
/*     */   private static final double BLENDING_DENSITY_FACTOR = 0.1D;
/*     */   protected static final int CELL_WIDTH = 4;
/*     */   protected static final int CELL_HEIGHT = 8;
/*     */   protected static final int CELL_RATIO = 2;
/*     */   private static final double SOLID_DENSITY = 1.0D;
/*     */   private static final double AIR_DENSITY = -1.0D;
/*     */   private static final int CELLS_PER_SECTION_Y = 2;
/*  48 */   private static final int QUARTS_PER_SECTION = QuartPos.fromBlock(16);
/*  49 */   private static final int CELL_HORIZONTAL_MAX_INDEX_INSIDE = QUARTS_PER_SECTION - 1;
/*  50 */   private static final int CELL_HORIZONTAL_MAX_INDEX_OUTSIDE = QUARTS_PER_SECTION;
/*  51 */   private static final int CELL_COLUMN_INSIDE_COUNT = 2 * CELL_HORIZONTAL_MAX_INDEX_INSIDE + 1;
/*  52 */   private static final int CELL_COLUMN_OUTSIDE_COUNT = 2 * CELL_HORIZONTAL_MAX_INDEX_OUTSIDE + 1;
/*  53 */   private static final int CELL_COLUMN_COUNT = CELL_COLUMN_INSIDE_COUNT + CELL_COLUMN_OUTSIDE_COUNT;
/*     */ 
/*     */   
/*     */   private final LevelHeightAccessor areaWithOldGeneration;
/*     */   
/*  58 */   private static final List<Block> SURFACE_BLOCKS = List.of(new Block[] { Blocks.PODZOL, Blocks.GRAVEL, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.RED_SAND, Blocks.MYCELIUM, Blocks.SNOW_BLOCK, Blocks.TERRACOTTA, Blocks.DIRT });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final double NO_VALUE = 1.7976931348623157E308D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasCalculatedData;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double[] heights;
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<List<Holder<Biome>>> biomes;
/*     */ 
/*     */ 
/*     */   
/*     */   private final transient double[][] densities;
/*     */ 
/*     */ 
/*     */   
/*  85 */   private static final Codec<double[]> DOUBLE_ARRAY_CODEC = Codec.DOUBLE.listOf().xmap(Doubles::toArray, Doubles::asList);
/*     */   
/*     */   public static final Codec<BlendingData> CODEC;
/*     */   
/*     */   static {
/*  90 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("min_section").forGetter(()), (App)Codec.INT.fieldOf("max_section").forGetter(()), (App)DOUBLE_ARRAY_CODEC.optionalFieldOf("heights").forGetter(())).apply((Applicative)$$0, BlendingData::new)).comapFlatMap(BlendingData::validateArraySize, Function.identity());
/*     */   }
/*     */   private static DataResult<BlendingData> validateArraySize(BlendingData $$0) {
/*  93 */     if ($$0.heights.length != CELL_COLUMN_COUNT) {
/*  94 */       return DataResult.error(() -> "heights has to be of length " + CELL_COLUMN_COUNT);
/*     */     }
/*     */     
/*  97 */     return DataResult.success($$0);
/*     */   }
/*     */   
/*     */   private BlendingData(int $$0, int $$1, Optional<double[]> $$2) {
/* 101 */     this.heights = $$2.orElse((double[])Util.make(new double[CELL_COLUMN_COUNT], $$0 -> Arrays.fill($$0, Double.MAX_VALUE)));
/*     */     
/* 103 */     this.densities = new double[CELL_COLUMN_COUNT][];
/*     */     
/* 105 */     ObjectArrayList<List<Holder<Biome>>> $$3 = new ObjectArrayList(CELL_COLUMN_COUNT);
/* 106 */     $$3.size(CELL_COLUMN_COUNT);
/* 107 */     this.biomes = (List<List<Holder<Biome>>>)$$3;
/*     */     
/* 109 */     int $$4 = SectionPos.sectionToBlockCoord($$0);
/* 110 */     int $$5 = SectionPos.sectionToBlockCoord($$1) - $$4;
/*     */     
/* 112 */     this.areaWithOldGeneration = LevelHeightAccessor.create($$4, $$5);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BlendingData getOrUpdateBlendingData(WorldGenRegion $$0, int $$1, int $$2) {
/* 117 */     ChunkAccess $$3 = $$0.getChunk($$1, $$2);
/* 118 */     BlendingData $$4 = $$3.getBlendingData();
/* 119 */     if ($$4 == null || !$$3.getHighestGeneratedStatus().isOrAfter(ChunkStatus.BIOMES)) {
/* 120 */       return null;
/*     */     }
/*     */     
/* 123 */     $$4.calculateData($$3, sideByGenerationAge((WorldGenLevel)$$0, $$1, $$2, false));
/*     */     
/* 125 */     return $$4;
/*     */   }
/*     */   
/*     */   public static Set<Direction8> sideByGenerationAge(WorldGenLevel $$0, int $$1, int $$2, boolean $$3) {
/* 129 */     Set<Direction8> $$4 = EnumSet.noneOf(Direction8.class);
/* 130 */     for (Direction8 $$5 : Direction8.values()) {
/* 131 */       int $$6 = $$1 + $$5.getStepX();
/* 132 */       int $$7 = $$2 + $$5.getStepZ();
/*     */       
/* 134 */       if ($$0.getChunk($$6, $$7).isOldNoiseGeneration() == $$3) {
/* 135 */         $$4.add($$5);
/*     */       }
/*     */     } 
/* 138 */     return $$4;
/*     */   }
/*     */   
/*     */   private void calculateData(ChunkAccess $$0, Set<Direction8> $$1) {
/* 142 */     if (this.hasCalculatedData) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 147 */     if ($$1.contains(Direction8.NORTH) || $$1.contains(Direction8.WEST) || $$1.contains(Direction8.NORTH_WEST)) {
/* 148 */       addValuesForColumn(getInsideIndex(0, 0), $$0, 0, 0);
/*     */     }
/* 150 */     if ($$1.contains(Direction8.NORTH)) {
/* 151 */       for (int $$2 = 1; $$2 < QUARTS_PER_SECTION; $$2++) {
/* 152 */         addValuesForColumn(getInsideIndex($$2, 0), $$0, 4 * $$2, 0);
/*     */       }
/*     */     }
/* 155 */     if ($$1.contains(Direction8.WEST)) {
/* 156 */       for (int $$3 = 1; $$3 < QUARTS_PER_SECTION; $$3++) {
/* 157 */         addValuesForColumn(getInsideIndex(0, $$3), $$0, 0, 4 * $$3);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/* 162 */     if ($$1.contains(Direction8.EAST)) {
/* 163 */       for (int $$4 = 1; $$4 < QUARTS_PER_SECTION; $$4++) {
/* 164 */         addValuesForColumn(getOutsideIndex(CELL_HORIZONTAL_MAX_INDEX_OUTSIDE, $$4), $$0, 15, 4 * $$4);
/*     */       }
/*     */     }
/* 167 */     if ($$1.contains(Direction8.SOUTH)) {
/* 168 */       for (int $$5 = 0; $$5 < QUARTS_PER_SECTION; $$5++) {
/* 169 */         addValuesForColumn(getOutsideIndex($$5, CELL_HORIZONTAL_MAX_INDEX_OUTSIDE), $$0, 4 * $$5, 15);
/*     */       }
/*     */     }
/* 172 */     if ($$1.contains(Direction8.EAST) && $$1.contains(Direction8.NORTH_EAST)) {
/* 173 */       addValuesForColumn(getOutsideIndex(CELL_HORIZONTAL_MAX_INDEX_OUTSIDE, 0), $$0, 15, 0);
/*     */     }
/*     */     
/* 176 */     if ($$1.contains(Direction8.EAST) && $$1.contains(Direction8.SOUTH) && $$1.contains(Direction8.SOUTH_EAST)) {
/* 177 */       addValuesForColumn(getOutsideIndex(CELL_HORIZONTAL_MAX_INDEX_OUTSIDE, CELL_HORIZONTAL_MAX_INDEX_OUTSIDE), $$0, 15, 15);
/*     */     }
/* 179 */     this.hasCalculatedData = true;
/*     */   }
/*     */   
/*     */   private void addValuesForColumn(int $$0, ChunkAccess $$1, int $$2, int $$3) {
/* 183 */     if (this.heights[$$0] == Double.MAX_VALUE) {
/* 184 */       this.heights[$$0] = getHeightAtXZ($$1, $$2, $$3);
/*     */     }
/* 186 */     this.densities[$$0] = getDensityColumn($$1, $$2, $$3, Mth.floor(this.heights[$$0]));
/*     */     
/* 188 */     this.biomes.set($$0, getBiomeColumn($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   private int getHeightAtXZ(ChunkAccess $$0, int $$1, int $$2) {
/*     */     int $$4;
/* 193 */     if ($$0.hasPrimedHeightmap(Heightmap.Types.WORLD_SURFACE_WG)) {
/* 194 */       int $$3 = Math.min($$0.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$1, $$2) + 1, this.areaWithOldGeneration.getMaxBuildHeight());
/*     */     } else {
/* 196 */       $$4 = this.areaWithOldGeneration.getMaxBuildHeight();
/*     */     } 
/*     */     
/* 199 */     int $$5 = this.areaWithOldGeneration.getMinBuildHeight();
/* 200 */     BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos($$1, $$4, $$2);
/* 201 */     while ($$6.getY() > $$5) {
/* 202 */       $$6.move(Direction.DOWN);
/* 203 */       if (SURFACE_BLOCKS.contains($$0.getBlockState((BlockPos)$$6).getBlock())) {
/* 204 */         return $$6.getY();
/*     */       }
/*     */     } 
/* 207 */     return $$5;
/*     */   }
/*     */   
/*     */   private static double read1(ChunkAccess $$0, BlockPos.MutableBlockPos $$1) {
/* 211 */     return isGround($$0, (BlockPos)$$1.move(Direction.DOWN)) ? 1.0D : -1.0D;
/*     */   }
/*     */   
/*     */   private static double read7(ChunkAccess $$0, BlockPos.MutableBlockPos $$1) {
/* 215 */     double $$2 = 0.0D;
/* 216 */     for (int $$3 = 0; $$3 < 7; $$3++) {
/* 217 */       $$2 += read1($$0, $$1);
/*     */     }
/* 219 */     return $$2;
/*     */   }
/*     */   
/*     */   private double[] getDensityColumn(ChunkAccess $$0, int $$1, int $$2, int $$3) {
/* 223 */     double[] $$4 = new double[cellCountPerColumn()];
/* 224 */     Arrays.fill($$4, -1.0D);
/*     */     
/* 226 */     BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos($$1, this.areaWithOldGeneration.getMaxBuildHeight(), $$2);
/*     */     
/* 228 */     double $$6 = read7($$0, $$5);
/*     */     
/* 230 */     for (int $$7 = $$4.length - 2; $$7 >= 0; $$7--) {
/* 231 */       double $$8 = read1($$0, $$5);
/* 232 */       double $$9 = read7($$0, $$5);
/*     */       
/* 234 */       $$4[$$7] = ($$6 + $$8 + $$9) / 15.0D;
/*     */       
/* 236 */       $$6 = $$9;
/*     */     } 
/*     */     
/* 239 */     int $$10 = getCellYIndex(Mth.floorDiv($$3, 8));
/* 240 */     if ($$10 >= 0 && $$10 < $$4.length - 1) {
/* 241 */       double $$11 = ($$3 + 0.5D) % 8.0D / 8.0D;
/* 242 */       double $$12 = (1.0D - $$11) / $$11;
/* 243 */       double $$13 = Math.max($$12, 1.0D) * 0.25D;
/*     */       
/* 245 */       $$4[$$10 + 1] = -$$12 / $$13;
/* 246 */       $$4[$$10] = 1.0D / $$13;
/*     */     } 
/* 248 */     return $$4;
/*     */   }
/*     */   
/*     */   private List<Holder<Biome>> getBiomeColumn(ChunkAccess $$0, int $$1, int $$2) {
/* 252 */     ObjectArrayList<Holder<Biome>> $$3 = new ObjectArrayList(quartCountPerColumn());
/* 253 */     $$3.size(quartCountPerColumn());
/* 254 */     for (int $$4 = 0; $$4 < $$3.size(); $$4++) {
/* 255 */       int $$5 = $$4 + QuartPos.fromBlock(this.areaWithOldGeneration.getMinBuildHeight());
/* 256 */       $$3.set($$4, $$0.getNoiseBiome(QuartPos.fromBlock($$1), $$5, QuartPos.fromBlock($$2)));
/*     */     } 
/* 258 */     return (List<Holder<Biome>>)$$3;
/*     */   }
/*     */   
/*     */   private static boolean isGround(ChunkAccess $$0, BlockPos $$1) {
/* 262 */     BlockState $$2 = $$0.getBlockState($$1);
/* 263 */     if ($$2.isAir()) {
/* 264 */       return false;
/*     */     }
/* 266 */     if ($$2.is(BlockTags.LEAVES)) {
/* 267 */       return false;
/*     */     }
/* 269 */     if ($$2.is(BlockTags.LOGS)) {
/* 270 */       return false;
/*     */     }
/* 272 */     if ($$2.is(Blocks.BROWN_MUSHROOM_BLOCK) || $$2.is(Blocks.RED_MUSHROOM_BLOCK)) {
/* 273 */       return false;
/*     */     }
/* 275 */     if ($$2.getCollisionShape((BlockGetter)$$0, $$1).isEmpty()) {
/* 276 */       return false;
/*     */     }
/*     */     
/* 279 */     return true;
/*     */   }
/*     */   
/*     */   protected double getHeight(int $$0, int $$1, int $$2) {
/* 283 */     if ($$0 == CELL_HORIZONTAL_MAX_INDEX_OUTSIDE || $$2 == CELL_HORIZONTAL_MAX_INDEX_OUTSIDE) {
/* 284 */       return this.heights[getOutsideIndex($$0, $$2)];
/*     */     }
/* 286 */     if ($$0 == 0 || $$2 == 0) {
/* 287 */       return this.heights[getInsideIndex($$0, $$2)];
/*     */     }
/* 289 */     return Double.MAX_VALUE;
/*     */   }
/*     */   
/*     */   private double getDensity(@Nullable double[] $$0, int $$1) {
/* 293 */     if ($$0 == null) {
/* 294 */       return Double.MAX_VALUE;
/*     */     }
/* 296 */     int $$2 = getCellYIndex($$1);
/* 297 */     if ($$2 < 0 || $$2 >= $$0.length) {
/* 298 */       return Double.MAX_VALUE;
/*     */     }
/* 300 */     return $$0[$$2] * 0.1D;
/*     */   }
/*     */   
/*     */   protected double getDensity(int $$0, int $$1, int $$2) {
/* 304 */     if ($$1 == getMinY()) {
/* 305 */       return 0.1D;
/*     */     }
/* 307 */     if ($$0 == CELL_HORIZONTAL_MAX_INDEX_OUTSIDE || $$2 == CELL_HORIZONTAL_MAX_INDEX_OUTSIDE) {
/* 308 */       return getDensity(this.densities[getOutsideIndex($$0, $$2)], $$1);
/*     */     }
/* 310 */     if ($$0 == 0 || $$2 == 0) {
/* 311 */       return getDensity(this.densities[getInsideIndex($$0, $$2)], $$1);
/*     */     }
/* 313 */     return Double.MAX_VALUE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void iterateBiomes(int $$0, int $$1, int $$2, BiomeConsumer $$3) {
/* 321 */     if ($$1 < QuartPos.fromBlock(this.areaWithOldGeneration.getMinBuildHeight()) || $$1 >= QuartPos.fromBlock(this.areaWithOldGeneration.getMaxBuildHeight())) {
/*     */       return;
/*     */     }
/* 324 */     int $$4 = $$1 - QuartPos.fromBlock(this.areaWithOldGeneration.getMinBuildHeight());
/* 325 */     for (int $$5 = 0; $$5 < this.biomes.size(); $$5++) {
/* 326 */       if (this.biomes.get($$5) != null) {
/*     */ 
/*     */         
/* 329 */         Holder<Biome> $$6 = ((List<Holder<Biome>>)this.biomes.get($$5)).get($$4);
/* 330 */         if ($$6 != null) {
/* 331 */           $$3.consume($$0 + getX($$5), $$2 + getZ($$5), $$6);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void iterateHeights(int $$0, int $$1, HeightConsumer $$2) {
/* 341 */     for (int $$3 = 0; $$3 < this.heights.length; $$3++) {
/* 342 */       double $$4 = this.heights[$$3];
/* 343 */       if ($$4 != Double.MAX_VALUE) {
/* 344 */         $$2.consume($$0 + getX($$3), $$1 + getZ($$3), $$4);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void iterateDensities(int $$0, int $$1, int $$2, int $$3, DensityConsumer $$4) {
/* 354 */     int $$5 = getColumnMinY();
/* 355 */     int $$6 = Math.max(0, $$2 - $$5);
/* 356 */     int $$7 = Math.min(cellCountPerColumn(), $$3 - $$5);
/*     */     
/* 358 */     for (int $$8 = 0; $$8 < this.densities.length; $$8++) {
/* 359 */       double[] $$9 = this.densities[$$8];
/* 360 */       if ($$9 != null) {
/* 361 */         int $$10 = $$0 + getX($$8);
/* 362 */         int $$11 = $$1 + getZ($$8);
/* 363 */         for (int $$12 = $$6; $$12 < $$7; $$12++) {
/* 364 */           $$4.consume($$10, $$12 + $$5, $$11, $$9[$$12] * 0.1D);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int cellCountPerColumn() {
/* 371 */     return this.areaWithOldGeneration.getSectionsCount() * 2;
/*     */   }
/*     */   
/*     */   private int quartCountPerColumn() {
/* 375 */     return QuartPos.fromSection(this.areaWithOldGeneration.getSectionsCount());
/*     */   }
/*     */   
/*     */   private int getColumnMinY() {
/* 379 */     return getMinY() + 1;
/*     */   }
/*     */   
/*     */   private int getMinY() {
/* 383 */     return this.areaWithOldGeneration.getMinSection() * 2;
/*     */   }
/*     */   
/*     */   private int getCellYIndex(int $$0) {
/* 387 */     return $$0 - getColumnMinY();
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
/*     */   private static int getInsideIndex(int $$0, int $$1) {
/* 408 */     return CELL_HORIZONTAL_MAX_INDEX_INSIDE - $$0 + $$1;
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
/*     */   private static int getOutsideIndex(int $$0, int $$1) {
/* 431 */     return CELL_COLUMN_INSIDE_COUNT + $$0 + CELL_HORIZONTAL_MAX_INDEX_OUTSIDE - $$1;
/*     */   }
/*     */   
/*     */   private static int getX(int $$0) {
/* 435 */     if ($$0 < CELL_COLUMN_INSIDE_COUNT) {
/* 436 */       return zeroIfNegative(CELL_HORIZONTAL_MAX_INDEX_INSIDE - $$0);
/*     */     }
/* 438 */     int $$1 = $$0 - CELL_COLUMN_INSIDE_COUNT;
/* 439 */     return CELL_HORIZONTAL_MAX_INDEX_OUTSIDE - zeroIfNegative(CELL_HORIZONTAL_MAX_INDEX_OUTSIDE - $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getZ(int $$0) {
/* 444 */     if ($$0 < CELL_COLUMN_INSIDE_COUNT) {
/* 445 */       return zeroIfNegative($$0 - CELL_HORIZONTAL_MAX_INDEX_INSIDE);
/*     */     }
/* 447 */     int $$1 = $$0 - CELL_COLUMN_INSIDE_COUNT;
/* 448 */     return CELL_HORIZONTAL_MAX_INDEX_OUTSIDE - zeroIfNegative($$1 - CELL_HORIZONTAL_MAX_INDEX_OUTSIDE);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int zeroIfNegative(int $$0) {
/* 453 */     return $$0 & ($$0 >> 31 ^ 0xFFFFFFFF);
/*     */   }
/*     */   
/*     */   public LevelHeightAccessor getAreaWithOldGeneration() {
/* 457 */     return this.areaWithOldGeneration;
/*     */   }
/*     */   
/*     */   protected static interface BiomeConsumer {
/*     */     void consume(int param1Int1, int param1Int2, Holder<Biome> param1Holder);
/*     */   }
/*     */   
/*     */   protected static interface HeightConsumer {
/*     */     void consume(int param1Int1, int param1Int2, double param1Double);
/*     */   }
/*     */   
/*     */   protected static interface DensityConsumer {
/*     */     void consume(int param1Int1, int param1Int2, int param1Int3, double param1Double);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blending\BlendingData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */