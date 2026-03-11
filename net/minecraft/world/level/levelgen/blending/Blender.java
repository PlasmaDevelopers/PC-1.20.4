/*     */ package net.minecraft.world.level.levelgen.blending;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Direction8;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.data.worldgen.NoiseData;
/*     */ import net.minecraft.server.level.WorldGenRegion;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeResolver;
/*     */ import net.minecraft.world.level.biome.Climate;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.CarvingMask;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ProtoChunk;
/*     */ import net.minecraft.world.level.levelgen.DensityFunction;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
/*     */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import org.apache.commons.lang3.mutable.MutableDouble;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ 
/*     */ public class Blender {
/*  41 */   private static final Blender EMPTY = new Blender(new Long2ObjectOpenHashMap(), new Long2ObjectOpenHashMap())
/*     */     {
/*     */       public Blender.BlendingOutput blendOffsetAndFactor(int $$0, int $$1) {
/*  44 */         return new Blender.BlendingOutput(1.0D, 0.0D);
/*     */       }
/*     */ 
/*     */       
/*     */       public double blendDensity(DensityFunction.FunctionContext $$0, double $$1) {
/*  49 */         return $$1;
/*     */       }
/*     */ 
/*     */       
/*     */       public BiomeResolver getBiomeResolver(BiomeResolver $$0) {
/*  54 */         return $$0;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*  59 */   private static final NormalNoise SHIFT_NOISE = NormalNoise.create((RandomSource)new XoroshiroRandomSource(42L), NoiseData.DEFAULT_SHIFT);
/*     */   
/*  61 */   private static final int HEIGHT_BLENDING_RANGE_CELLS = QuartPos.fromSection(7) - 1;
/*  62 */   private static final int HEIGHT_BLENDING_RANGE_CHUNKS = QuartPos.toSection(HEIGHT_BLENDING_RANGE_CELLS + 3);
/*     */   private static final int DENSITY_BLENDING_RANGE_CELLS = 2;
/*  64 */   private static final int DENSITY_BLENDING_RANGE_CHUNKS = QuartPos.toSection(5);
/*     */   
/*     */   private static final double OLD_CHUNK_XZ_RADIUS = 8.0D;
/*     */   private final Long2ObjectOpenHashMap<BlendingData> heightAndBiomeBlendingData;
/*     */   private final Long2ObjectOpenHashMap<BlendingData> densityBlendingData;
/*     */   
/*     */   public static Blender empty() {
/*  71 */     return EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Blender of(@Nullable WorldGenRegion $$0) {
/*  76 */     if ($$0 == null) {
/*  77 */       return EMPTY;
/*     */     }
/*     */     
/*  80 */     ChunkPos $$1 = $$0.getCenter();
/*  81 */     if (!$$0.isOldChunkAround($$1, HEIGHT_BLENDING_RANGE_CHUNKS)) {
/*  82 */       return EMPTY;
/*     */     }
/*     */     
/*  85 */     Long2ObjectOpenHashMap<BlendingData> $$2 = new Long2ObjectOpenHashMap();
/*  86 */     Long2ObjectOpenHashMap<BlendingData> $$3 = new Long2ObjectOpenHashMap();
/*     */     
/*  88 */     int $$4 = Mth.square(HEIGHT_BLENDING_RANGE_CHUNKS + 1);
/*  89 */     for (int $$5 = -HEIGHT_BLENDING_RANGE_CHUNKS; $$5 <= HEIGHT_BLENDING_RANGE_CHUNKS; $$5++) {
/*  90 */       for (int $$6 = -HEIGHT_BLENDING_RANGE_CHUNKS; $$6 <= HEIGHT_BLENDING_RANGE_CHUNKS; $$6++) {
/*  91 */         if ($$5 * $$5 + $$6 * $$6 <= $$4) {
/*     */ 
/*     */           
/*  94 */           int $$7 = $$1.x + $$5;
/*  95 */           int $$8 = $$1.z + $$6;
/*  96 */           BlendingData $$9 = BlendingData.getOrUpdateBlendingData($$0, $$7, $$8);
/*  97 */           if ($$9 != null) {
/*     */ 
/*     */             
/* 100 */             $$2.put(ChunkPos.asLong($$7, $$8), $$9);
/* 101 */             if ($$5 >= -DENSITY_BLENDING_RANGE_CHUNKS && $$5 <= DENSITY_BLENDING_RANGE_CHUNKS && $$6 >= -DENSITY_BLENDING_RANGE_CHUNKS && $$6 <= DENSITY_BLENDING_RANGE_CHUNKS)
/* 102 */               $$3.put(ChunkPos.asLong($$7, $$8), $$9); 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 107 */     if ($$2.isEmpty() && $$3.isEmpty()) {
/* 108 */       return EMPTY;
/*     */     }
/* 110 */     return new Blender($$2, $$3);
/*     */   }
/*     */   
/*     */   Blender(Long2ObjectOpenHashMap<BlendingData> $$0, Long2ObjectOpenHashMap<BlendingData> $$1) {
/* 114 */     this.heightAndBiomeBlendingData = $$0;
/* 115 */     this.densityBlendingData = $$1;
/*     */   } public static interface DistanceGetter {
/*     */     double getDistance(double param1Double1, double param1Double2, double param1Double3); } public static final class BlendingOutput extends Record { private final double alpha; private final double blendingOffset;
/* 118 */     public BlendingOutput(double $$0, double $$1) { this.alpha = $$0; this.blendingOffset = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/blending/Blender$BlendingOutput;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #118	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 118 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/blending/Blender$BlendingOutput; } public double alpha() { return this.alpha; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/blending/Blender$BlendingOutput;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #118	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 118 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/blending/Blender$BlendingOutput; } public double blendingOffset() { return this.blendingOffset; } public final boolean equals(Object $$0) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/blending/Blender$BlendingOutput;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #118	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/blending/Blender$BlendingOutput;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */     } } public BlendingOutput blendOffsetAndFactor(int $$0, int $$1) {
/* 121 */     int $$2 = QuartPos.fromBlock($$0);
/* 122 */     int $$3 = QuartPos.fromBlock($$1);
/*     */     
/* 124 */     double $$4 = getBlendingDataValue($$2, 0, $$3, BlendingData::getHeight);
/* 125 */     if ($$4 != Double.MAX_VALUE) {
/* 126 */       return new BlendingOutput(0.0D, heightToOffset($$4));
/*     */     }
/*     */ 
/*     */     
/* 130 */     MutableDouble $$5 = new MutableDouble(0.0D);
/* 131 */     MutableDouble $$6 = new MutableDouble(0.0D);
/* 132 */     MutableDouble $$7 = new MutableDouble(Double.POSITIVE_INFINITY);
/*     */     
/* 134 */     this.heightAndBiomeBlendingData.forEach(($$5, $$6) -> $$6.iterateHeights(QuartPos.fromSection(ChunkPos.getX($$5.longValue())), QuartPos.fromSection(ChunkPos.getZ($$5.longValue())), ()));
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
/* 156 */     if ($$7.doubleValue() == Double.POSITIVE_INFINITY) {
/* 157 */       return new BlendingOutput(1.0D, 0.0D);
/*     */     }
/*     */     
/* 160 */     double $$8 = $$6.doubleValue() / $$5.doubleValue();
/* 161 */     double $$9 = Mth.clamp($$7.doubleValue() / (HEIGHT_BLENDING_RANGE_CELLS + 1), 0.0D, 1.0D);
/*     */     
/* 163 */     $$9 = 3.0D * $$9 * $$9 - 2.0D * $$9 * $$9 * $$9;
/*     */     
/* 165 */     return new BlendingOutput($$9, heightToOffset($$8));
/*     */   }
/*     */ 
/*     */   
/*     */   private static double heightToOffset(double $$0) {
/* 170 */     double $$1 = 1.0D;
/* 171 */     double $$2 = $$0 + 0.5D;
/* 172 */     double $$3 = Mth.positiveModulo($$2, 8.0D);
/*     */     
/* 174 */     return 1.0D * (32.0D * ($$2 - 128.0D) - 3.0D * ($$2 - 120.0D) * $$3 + 3.0D * $$3 * $$3) / 128.0D * (32.0D - 3.0D * $$3);
/*     */   }
/*     */   
/*     */   public double blendDensity(DensityFunction.FunctionContext $$0, double $$1) {
/* 178 */     int $$2 = QuartPos.fromBlock($$0.blockX());
/* 179 */     int $$3 = $$0.blockY() / 8;
/* 180 */     int $$4 = QuartPos.fromBlock($$0.blockZ());
/*     */     
/* 182 */     double $$5 = getBlendingDataValue($$2, $$3, $$4, BlendingData::getDensity);
/* 183 */     if ($$5 != Double.MAX_VALUE) {
/* 184 */       return $$5;
/*     */     }
/*     */     
/* 187 */     MutableDouble $$6 = new MutableDouble(0.0D);
/* 188 */     MutableDouble $$7 = new MutableDouble(0.0D);
/* 189 */     MutableDouble $$8 = new MutableDouble(Double.POSITIVE_INFINITY);
/*     */     
/* 191 */     this.densityBlendingData.forEach(($$6, $$7) -> $$7.iterateDensities(QuartPos.fromSection(ChunkPos.getX($$6.longValue())), QuartPos.fromSection(ChunkPos.getZ($$6.longValue())), $$0 - 1, $$0 + 1, ()));
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
/* 215 */     if ($$8.doubleValue() == Double.POSITIVE_INFINITY) {
/* 216 */       return $$1;
/*     */     }
/* 218 */     double $$9 = $$7.doubleValue() / $$6.doubleValue();
/* 219 */     double $$10 = Mth.clamp($$8.doubleValue() / 3.0D, 0.0D, 1.0D);
/*     */     
/* 221 */     return Mth.lerp($$10, $$9, $$1);
/*     */   }
/*     */   
/*     */   private double getBlendingDataValue(int $$0, int $$1, int $$2, CellValueGetter $$3) {
/* 225 */     int $$4 = QuartPos.toSection($$0);
/* 226 */     int $$5 = QuartPos.toSection($$2);
/*     */     
/* 228 */     boolean $$6 = (($$0 & 0x3) == 0);
/* 229 */     boolean $$7 = (($$2 & 0x3) == 0);
/*     */ 
/*     */     
/* 232 */     double $$8 = getBlendingDataValue($$3, $$4, $$5, $$0, $$1, $$2);
/* 233 */     if ($$8 == Double.MAX_VALUE) {
/*     */       
/* 235 */       if ($$6 && $$7) {
/* 236 */         $$8 = getBlendingDataValue($$3, $$4 - 1, $$5 - 1, $$0, $$1, $$2);
/*     */       }
/*     */       
/* 239 */       if ($$8 == Double.MAX_VALUE) {
/* 240 */         if ($$6) {
/* 241 */           $$8 = getBlendingDataValue($$3, $$4 - 1, $$5, $$0, $$1, $$2);
/*     */         }
/*     */         
/* 244 */         if ($$8 == Double.MAX_VALUE && $$7) {
/* 245 */           $$8 = getBlendingDataValue($$3, $$4, $$5 - 1, $$0, $$1, $$2);
/*     */         }
/*     */       } 
/*     */     } 
/* 249 */     return $$8;
/*     */   }
/*     */   
/*     */   private double getBlendingDataValue(CellValueGetter $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 253 */     BlendingData $$6 = (BlendingData)this.heightAndBiomeBlendingData.get(ChunkPos.asLong($$1, $$2));
/* 254 */     if ($$6 != null) {
/* 255 */       return $$0.get($$6, $$3 - QuartPos.fromSection($$1), $$4, $$5 - QuartPos.fromSection($$2));
/*     */     }
/* 257 */     return Double.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public BiomeResolver getBiomeResolver(BiomeResolver $$0) {
/* 261 */     return ($$1, $$2, $$3, $$4) -> {
/*     */         Holder<Biome> $$5 = blendBiome($$1, $$2, $$3);
/*     */         return ($$5 == null) ? $$0.getNoiseBiome($$1, $$2, $$3, $$4) : $$5;
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Holder<Biome> blendBiome(int $$0, int $$1, int $$2) {
/* 272 */     MutableDouble $$3 = new MutableDouble(Double.POSITIVE_INFINITY);
/* 273 */     MutableObject<Holder<Biome>> $$4 = new MutableObject();
/*     */     
/* 275 */     this.heightAndBiomeBlendingData.forEach(($$5, $$6) -> $$6.iterateBiomes(QuartPos.fromSection(ChunkPos.getX($$5.longValue())), $$0, QuartPos.fromSection(ChunkPos.getZ($$5.longValue())), ()));
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
/* 294 */     if ($$3.doubleValue() == Double.POSITIVE_INFINITY) {
/* 295 */       return null;
/*     */     }
/*     */     
/* 298 */     double $$5 = SHIFT_NOISE.getValue($$0, 0.0D, $$2) * 12.0D;
/* 299 */     double $$6 = Mth.clamp(($$3.doubleValue() + $$5) / (HEIGHT_BLENDING_RANGE_CELLS + 1), 0.0D, 1.0D);
/* 300 */     if ($$6 > 0.5D) {
/* 301 */       return null;
/*     */     }
/*     */     
/* 304 */     return (Holder<Biome>)$$4.getValue();
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
/*     */   public static void generateBorderTicks(WorldGenRegion $$0, ChunkAccess $$1) {
/* 316 */     ChunkPos $$2 = $$1.getPos();
/* 317 */     boolean $$3 = $$1.isOldNoiseGeneration();
/* 318 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/* 319 */     BlockPos $$5 = new BlockPos($$2.getMinBlockX(), 0, $$2.getMinBlockZ());
/*     */     
/* 321 */     BlendingData $$6 = $$1.getBlendingData();
/* 322 */     if ($$6 == null) {
/*     */       return;
/*     */     }
/* 325 */     int $$7 = $$6.getAreaWithOldGeneration().getMinBuildHeight();
/* 326 */     int $$8 = $$6.getAreaWithOldGeneration().getMaxBuildHeight() - 1;
/*     */ 
/*     */     
/* 329 */     if ($$3) {
/* 330 */       for (int $$9 = 0; $$9 < 16; $$9++) {
/* 331 */         for (int $$10 = 0; $$10 < 16; $$10++) {
/* 332 */           generateBorderTick($$1, (BlockPos)$$4.setWithOffset((Vec3i)$$5, $$9, $$7 - 1, $$10));
/* 333 */           generateBorderTick($$1, (BlockPos)$$4.setWithOffset((Vec3i)$$5, $$9, $$7, $$10));
/* 334 */           generateBorderTick($$1, (BlockPos)$$4.setWithOffset((Vec3i)$$5, $$9, $$8, $$10));
/* 335 */           generateBorderTick($$1, (BlockPos)$$4.setWithOffset((Vec3i)$$5, $$9, $$8 + 1, $$10));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 340 */     for (Direction $$11 : Direction.Plane.HORIZONTAL) {
/* 341 */       if ($$0.getChunk($$2.x + $$11.getStepX(), $$2.z + $$11.getStepZ()).isOldNoiseGeneration() == $$3) {
/*     */         continue;
/*     */       }
/*     */       
/* 345 */       int $$12 = ($$11 == Direction.EAST) ? 15 : 0;
/* 346 */       int $$13 = ($$11 == Direction.WEST) ? 0 : 15;
/* 347 */       int $$14 = ($$11 == Direction.SOUTH) ? 15 : 0;
/* 348 */       int $$15 = ($$11 == Direction.NORTH) ? 0 : 15;
/*     */       
/* 350 */       for (int $$16 = $$12; $$16 <= $$13; $$16++) {
/* 351 */         for (int $$17 = $$14; $$17 <= $$15; $$17++) {
/* 352 */           int $$18 = Math.min($$8, $$1.getHeight(Heightmap.Types.MOTION_BLOCKING, $$16, $$17)) + 1;
/*     */           
/* 354 */           for (int $$19 = $$7; $$19 < $$18; $$19++) {
/* 355 */             generateBorderTick($$1, (BlockPos)$$4.setWithOffset((Vec3i)$$5, $$16, $$19, $$17));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void generateBorderTick(ChunkAccess $$0, BlockPos $$1) {
/* 364 */     BlockState $$2 = $$0.getBlockState($$1);
/* 365 */     if ($$2.is(BlockTags.LEAVES)) {
/* 366 */       $$0.markPosForPostprocessing($$1);
/*     */     }
/*     */     
/* 369 */     FluidState $$3 = $$0.getFluidState($$1);
/* 370 */     if (!$$3.isEmpty()) {
/* 371 */       $$0.markPosForPostprocessing($$1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addAroundOldChunksCarvingMaskFilter(WorldGenLevel $$0, ProtoChunk $$1) {
/* 379 */     ChunkPos $$2 = $$1.getPos();
/* 380 */     ImmutableMap.Builder<Direction8, BlendingData> $$3 = ImmutableMap.builder();
/* 381 */     for (Direction8 $$4 : Direction8.values()) {
/* 382 */       int $$5 = $$2.x + $$4.getStepX();
/* 383 */       int $$6 = $$2.z + $$4.getStepZ();
/*     */       
/* 385 */       BlendingData $$7 = $$0.getChunk($$5, $$6).getBlendingData();
/* 386 */       if ($$7 != null) {
/* 387 */         $$3.put($$4, $$7);
/*     */       }
/*     */     } 
/* 390 */     ImmutableMap<Direction8, BlendingData> $$8 = $$3.build();
/*     */ 
/*     */     
/* 393 */     if (!$$1.isOldNoiseGeneration() && $$8.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 397 */     DistanceGetter $$9 = makeOldChunkDistanceGetter($$1.getBlendingData(), (Map<Direction8, BlendingData>)$$8);
/*     */     
/* 399 */     CarvingMask.Mask $$10 = ($$1, $$2, $$3) -> {
/*     */         double $$4 = $$1 + 0.5D + SHIFT_NOISE.getValue($$1, $$2, $$3) * 4.0D;
/*     */         
/*     */         double $$5 = $$2 + 0.5D + SHIFT_NOISE.getValue($$2, $$3, $$1) * 4.0D;
/*     */         
/*     */         double $$6 = $$3 + 0.5D + SHIFT_NOISE.getValue($$3, $$1, $$2) * 4.0D;
/*     */         return ($$0.getDistance($$4, $$5, $$6) < 4.0D);
/*     */       };
/* 407 */     Objects.requireNonNull($$1); Stream.<GenerationStep.Carving>of(GenerationStep.Carving.values()).map($$1::getOrCreateCarvingMask).forEach($$1 -> $$1.setAdditionalMask($$0));
/*     */   }
/*     */   
/*     */   public static DistanceGetter makeOldChunkDistanceGetter(@Nullable BlendingData $$0, Map<Direction8, BlendingData> $$1) {
/* 411 */     List<DistanceGetter> $$2 = Lists.newArrayList();
/* 412 */     if ($$0 != null) {
/* 413 */       $$2.add(makeOffsetOldChunkDistanceGetter(null, $$0));
/*     */     }
/*     */     
/* 416 */     $$1.forEach(($$1, $$2) -> $$0.add(makeOffsetOldChunkDistanceGetter($$1, $$2)));
/*     */     
/* 418 */     return ($$1, $$2, $$3) -> {
/*     */         double $$4 = Double.POSITIVE_INFINITY;
/*     */         for (DistanceGetter $$5 : $$0) {
/*     */           double $$6 = $$5.getDistance($$1, $$2, $$3);
/*     */           if ($$6 < $$4) {
/*     */             $$4 = $$6;
/*     */           }
/*     */         } 
/*     */         return $$4;
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   private static DistanceGetter makeOffsetOldChunkDistanceGetter(@Nullable Direction8 $$0, BlendingData $$1) {
/* 432 */     double $$2 = 0.0D;
/* 433 */     double $$3 = 0.0D;
/*     */     
/* 435 */     if ($$0 != null)
/*     */     {
/* 437 */       for (Direction $$4 : $$0.getDirections()) {
/* 438 */         $$2 += ($$4.getStepX() * 16);
/* 439 */         $$3 += ($$4.getStepZ() * 16);
/*     */       } 
/*     */     }
/*     */     
/* 443 */     double $$5 = $$2;
/* 444 */     double $$6 = $$3;
/*     */     
/* 446 */     double $$7 = $$1.getAreaWithOldGeneration().getHeight() / 2.0D;
/* 447 */     double $$8 = $$1.getAreaWithOldGeneration().getMinBuildHeight() + $$7;
/*     */     
/* 449 */     return ($$4, $$5, $$6) -> distanceToCube($$4 - 8.0D - $$0, $$5 - $$1, $$6 - 8.0D - $$2, 8.0D, $$3, 8.0D);
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
/*     */   private static double distanceToCube(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
/* 466 */     double $$6 = Math.abs($$0) - $$3;
/* 467 */     double $$7 = Math.abs($$1) - $$4;
/* 468 */     double $$8 = Math.abs($$2) - $$5;
/*     */     
/* 470 */     return Mth.length(Math.max(0.0D, $$6), Math.max(0.0D, $$7), Math.max(0.0D, $$8));
/*     */   }
/*     */   
/*     */   private static interface CellValueGetter {
/*     */     double get(BlendingData param1BlendingData, int param1Int1, int param1Int2, int param1Int3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blending\Blender.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */