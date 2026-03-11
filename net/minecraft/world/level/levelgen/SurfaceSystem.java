/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeManager;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.BlockColumn;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.levelgen.carver.CarvingContext;
/*     */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SurfaceSystem
/*     */ {
/*  31 */   private static final BlockState WHITE_TERRACOTTA = Blocks.WHITE_TERRACOTTA.defaultBlockState();
/*  32 */   private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.defaultBlockState();
/*  33 */   private static final BlockState TERRACOTTA = Blocks.TERRACOTTA.defaultBlockState();
/*  34 */   private static final BlockState YELLOW_TERRACOTTA = Blocks.YELLOW_TERRACOTTA.defaultBlockState();
/*  35 */   private static final BlockState BROWN_TERRACOTTA = Blocks.BROWN_TERRACOTTA.defaultBlockState();
/*  36 */   private static final BlockState RED_TERRACOTTA = Blocks.RED_TERRACOTTA.defaultBlockState();
/*  37 */   private static final BlockState LIGHT_GRAY_TERRACOTTA = Blocks.LIGHT_GRAY_TERRACOTTA.defaultBlockState();
/*     */   
/*  39 */   private static final BlockState PACKED_ICE = Blocks.PACKED_ICE.defaultBlockState();
/*  40 */   private static final BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.defaultBlockState();
/*     */   
/*     */   private final BlockState defaultBlock;
/*     */   
/*     */   private final int seaLevel;
/*     */   
/*     */   private final BlockState[] clayBands;
/*     */   
/*     */   private final NormalNoise clayBandsOffsetNoise;
/*     */   
/*     */   private final NormalNoise badlandsPillarNoise;
/*     */   private final NormalNoise badlandsPillarRoofNoise;
/*     */   private final NormalNoise badlandsSurfaceNoise;
/*     */   private final NormalNoise icebergPillarNoise;
/*     */   private final NormalNoise icebergPillarRoofNoise;
/*     */   private final NormalNoise icebergSurfaceNoise;
/*     */   private final PositionalRandomFactory noiseRandom;
/*     */   private final NormalNoise surfaceNoise;
/*     */   private final NormalNoise surfaceSecondaryNoise;
/*     */   
/*     */   public SurfaceSystem(RandomState $$0, BlockState $$1, int $$2, PositionalRandomFactory $$3) {
/*  61 */     this.defaultBlock = $$1;
/*  62 */     this.seaLevel = $$2;
/*     */     
/*  64 */     this.noiseRandom = $$3;
/*     */     
/*  66 */     this.clayBandsOffsetNoise = $$0.getOrCreateNoise(Noises.CLAY_BANDS_OFFSET);
/*  67 */     this.clayBands = generateBands($$3.fromHashOf(new ResourceLocation("clay_bands")));
/*     */     
/*  69 */     this.surfaceNoise = $$0.getOrCreateNoise(Noises.SURFACE);
/*  70 */     this.surfaceSecondaryNoise = $$0.getOrCreateNoise(Noises.SURFACE_SECONDARY);
/*     */     
/*  72 */     this.badlandsPillarNoise = $$0.getOrCreateNoise(Noises.BADLANDS_PILLAR);
/*  73 */     this.badlandsPillarRoofNoise = $$0.getOrCreateNoise(Noises.BADLANDS_PILLAR_ROOF);
/*  74 */     this.badlandsSurfaceNoise = $$0.getOrCreateNoise(Noises.BADLANDS_SURFACE);
/*     */     
/*  76 */     this.icebergPillarNoise = $$0.getOrCreateNoise(Noises.ICEBERG_PILLAR);
/*  77 */     this.icebergPillarRoofNoise = $$0.getOrCreateNoise(Noises.ICEBERG_PILLAR_ROOF);
/*  78 */     this.icebergSurfaceNoise = $$0.getOrCreateNoise(Noises.ICEBERG_SURFACE);
/*     */   }
/*     */   
/*     */   public void buildSurface(RandomState $$0, BiomeManager $$1, Registry<Biome> $$2, boolean $$3, WorldGenerationContext $$4, final ChunkAccess protoChunk, NoiseChunk $$6, SurfaceRules.RuleSource $$7) {
/*  82 */     final BlockPos.MutableBlockPos columnPos = new BlockPos.MutableBlockPos();
/*     */     
/*  84 */     final ChunkPos chunkPos = protoChunk.getPos();
/*  85 */     int $$10 = $$9.getMinBlockX();
/*  86 */     int $$11 = $$9.getMinBlockZ();
/*     */     
/*  88 */     BlockColumn $$12 = new BlockColumn()
/*     */       {
/*     */         public BlockState getBlock(int $$0) {
/*  91 */           return protoChunk.getBlockState((BlockPos)columnPos.setY($$0));
/*     */         }
/*     */ 
/*     */         
/*     */         public void setBlock(int $$0, BlockState $$1) {
/*  96 */           LevelHeightAccessor $$2 = protoChunk.getHeightAccessorForGeneration();
/*  97 */           if ($$0 >= $$2.getMinBuildHeight() && $$0 < $$2.getMaxBuildHeight()) {
/*  98 */             protoChunk.setBlockState((BlockPos)columnPos.setY($$0), $$1, false);
/*  99 */             if (!$$1.getFluidState().isEmpty()) {
/* 100 */               protoChunk.markPosForPostprocessing((BlockPos)columnPos);
/*     */             }
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public String toString() {
/* 107 */           return "ChunkBlockColumn " + chunkPos;
/*     */         }
/*     */       };
/*     */     
/* 111 */     Objects.requireNonNull($$1); SurfaceRules.Context $$13 = new SurfaceRules.Context(this, $$0, protoChunk, $$6, $$1::getBiome, $$2, $$4);
/* 112 */     SurfaceRules.SurfaceRule $$14 = $$7.apply($$13);
/*     */     
/* 114 */     BlockPos.MutableBlockPos $$15 = new BlockPos.MutableBlockPos();
/*     */     
/* 116 */     for (int $$16 = 0; $$16 < 16; $$16++) {
/* 117 */       for (int $$17 = 0; $$17 < 16; $$17++) {
/* 118 */         int $$18 = $$10 + $$16;
/* 119 */         int $$19 = $$11 + $$17;
/* 120 */         int $$20 = protoChunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$16, $$17) + 1;
/*     */         
/* 122 */         $$8.setX($$18).setZ($$19);
/*     */         
/* 124 */         Holder<Biome> $$21 = $$1.getBiome((BlockPos)$$15.set($$18, $$3 ? 0 : $$20, $$19));
/*     */ 
/*     */         
/* 127 */         if ($$21.is(Biomes.ERODED_BADLANDS)) {
/* 128 */           erodedBadlandsExtension($$12, $$18, $$19, $$20, (LevelHeightAccessor)protoChunk);
/*     */         }
/* 130 */         int $$22 = protoChunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$16, $$17) + 1;
/*     */         
/* 132 */         $$13.updateXZ($$18, $$19);
/*     */         
/* 134 */         int $$23 = 0;
/* 135 */         int $$24 = Integer.MIN_VALUE;
/* 136 */         int $$25 = Integer.MAX_VALUE;
/*     */         
/* 138 */         int $$26 = protoChunk.getMinBuildHeight();
/* 139 */         for (int $$27 = $$22; $$27 >= $$26; $$27--) {
/* 140 */           BlockState $$28 = $$12.getBlock($$27);
/*     */           
/* 142 */           if ($$28.isAir()) {
/* 143 */             $$23 = 0;
/*     */             
/* 145 */             $$24 = Integer.MIN_VALUE;
/*     */ 
/*     */           
/*     */           }
/* 149 */           else if (!$$28.getFluidState().isEmpty()) {
/*     */ 
/*     */ 
/*     */             
/* 153 */             if ($$24 == Integer.MIN_VALUE) {
/* 154 */               $$24 = $$27 + 1;
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 159 */             if ($$25 >= $$27) {
/*     */               
/* 161 */               $$25 = DimensionType.WAY_BELOW_MIN_Y;
/* 162 */               for (int $$29 = $$27 - 1; $$29 >= $$26 - 1; $$29--) {
/* 163 */                 BlockState $$30 = $$12.getBlock($$29);
/* 164 */                 if (!isStone($$30)) {
/* 165 */                   $$25 = $$29 + 1;
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/* 171 */             $$23++;
/* 172 */             int $$31 = $$27 - $$25 + 1;
/*     */             
/* 174 */             $$13.updateY($$23, $$31, $$24, $$18, $$27, $$19);
/*     */             
/* 176 */             if ($$28 == this.defaultBlock) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 182 */               BlockState $$32 = $$14.tryApply($$18, $$27, $$19);
/*     */               
/* 184 */               if ($$32 != null) {
/* 185 */                 $$12.setBlock($$27, $$32);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 190 */         if ($$21.is(Biomes.FROZEN_OCEAN) || $$21.is(Biomes.DEEP_FROZEN_OCEAN)) {
/* 191 */           frozenOceanExtension($$13.getMinSurfaceLevel(), (Biome)$$21.value(), $$12, $$15, $$18, $$19, $$20);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getSurfaceDepth(int $$0, int $$1) {
/* 201 */     double $$2 = this.surfaceNoise.getValue($$0, 0.0D, $$1);
/*     */     
/* 203 */     return (int)($$2 * 2.75D + 3.0D + this.noiseRandom.at($$0, 0, $$1).nextDouble() * 0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getSurfaceSecondary(int $$0, int $$1) {
/* 210 */     return this.surfaceSecondaryNoise.getValue($$0, 0.0D, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isStone(BlockState $$0) {
/* 215 */     return (!$$0.isAir() && $$0.getFluidState().isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Optional<BlockState> topMaterial(SurfaceRules.RuleSource $$0, CarvingContext $$1, Function<BlockPos, Holder<Biome>> $$2, ChunkAccess $$3, NoiseChunk $$4, BlockPos $$5, boolean $$6) {
/* 223 */     SurfaceRules.Context $$7 = new SurfaceRules.Context(this, $$1.randomState(), $$3, $$4, $$2, $$1.registryAccess().registryOrThrow(Registries.BIOME), (WorldGenerationContext)$$1);
/* 224 */     SurfaceRules.SurfaceRule $$8 = $$0.apply($$7);
/*     */     
/* 226 */     int $$9 = $$5.getX();
/* 227 */     int $$10 = $$5.getY();
/* 228 */     int $$11 = $$5.getZ();
/*     */     
/* 230 */     $$7.updateXZ($$9, $$11);
/* 231 */     $$7.updateY(1, 1, $$6 ? ($$10 + 1) : Integer.MIN_VALUE, $$9, $$10, $$11);
/* 232 */     BlockState $$12 = $$8.tryApply($$9, $$10, $$11);
/*     */     
/* 234 */     return Optional.ofNullable($$12);
/*     */   }
/*     */   
/*     */   private void erodedBadlandsExtension(BlockColumn $$0, int $$1, int $$2, int $$3, LevelHeightAccessor $$4) {
/* 238 */     double $$5 = 0.2D;
/* 239 */     double $$6 = Math.min(Math.abs(this.badlandsSurfaceNoise.getValue($$1, 0.0D, $$2) * 8.25D), this.badlandsPillarNoise.getValue($$1 * 0.2D, 0.0D, $$2 * 0.2D) * 15.0D);
/* 240 */     if ($$6 <= 0.0D) {
/*     */       return;
/*     */     }
/*     */     
/* 244 */     double $$7 = 0.75D;
/* 245 */     double $$8 = 1.5D;
/* 246 */     double $$9 = Math.abs(this.badlandsPillarRoofNoise.getValue($$1 * 0.75D, 0.0D, $$2 * 0.75D) * 1.5D);
/* 247 */     double $$10 = 64.0D + Math.min($$6 * $$6 * 2.5D, Math.ceil($$9 * 50.0D) + 24.0D);
/*     */     
/* 249 */     int $$11 = Mth.floor($$10);
/*     */ 
/*     */     
/* 252 */     if ($$3 > $$11) {
/*     */       return;
/*     */     }
/*     */     
/* 256 */     for (int $$12 = $$11; $$12 >= $$4.getMinBuildHeight(); $$12--) {
/* 257 */       BlockState $$13 = $$0.getBlock($$12);
/* 258 */       if ($$13.is(this.defaultBlock.getBlock())) {
/*     */         break;
/*     */       }
/* 261 */       if ($$13.is(Blocks.WATER)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 266 */     for (int $$14 = $$11; $$14 >= $$4.getMinBuildHeight() && 
/* 267 */       $$0.getBlock($$14).isAir(); $$14--)
/*     */     {
/*     */       
/* 270 */       $$0.setBlock($$14, this.defaultBlock);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void frozenOceanExtension(int $$0, Biome $$1, BlockColumn $$2, BlockPos.MutableBlockPos $$3, int $$4, int $$5, int $$6) {
/* 276 */     double $$14, $$7 = 1.28D;
/* 277 */     double $$8 = Math.min(Math.abs(this.icebergSurfaceNoise.getValue($$4, 0.0D, $$5) * 8.25D), this.icebergPillarNoise.getValue($$4 * 1.28D, 0.0D, $$5 * 1.28D) * 15.0D);
/*     */ 
/*     */     
/* 280 */     if ($$8 <= 1.8D) {
/*     */       return;
/*     */     }
/*     */     
/* 284 */     double $$9 = 1.17D;
/* 285 */     double $$10 = 1.5D;
/* 286 */     double $$11 = Math.abs(this.icebergPillarRoofNoise.getValue($$4 * 1.17D, 0.0D, $$5 * 1.17D) * 1.5D);
/* 287 */     double $$12 = Math.min($$8 * $$8 * 1.2D, Math.ceil($$11 * 40.0D) + 14.0D);
/*     */ 
/*     */     
/* 290 */     if ($$1.shouldMeltFrozenOceanIcebergSlightly((BlockPos)$$3.set($$4, 63, $$5))) {
/* 291 */       $$12 -= 2.0D;
/*     */     }
/*     */     
/* 294 */     if ($$12 > 2.0D) {
/* 295 */       double $$13 = this.seaLevel - $$12 - 7.0D;
/* 296 */       $$12 += this.seaLevel;
/*     */     } else {
/* 298 */       $$12 = 0.0D;
/* 299 */       $$14 = 0.0D;
/*     */     } 
/* 301 */     double $$15 = $$12;
/*     */     
/* 303 */     RandomSource $$16 = this.noiseRandom.at($$4, 0, $$5);
/*     */     
/* 305 */     int $$17 = 2 + $$16.nextInt(4);
/* 306 */     int $$18 = this.seaLevel + 18 + $$16.nextInt(10);
/*     */     
/* 308 */     int $$19 = 0;
/*     */     
/* 310 */     for (int $$20 = Math.max($$6, (int)$$15 + 1); $$20 >= $$0; $$20--) {
/*     */       
/* 312 */       if (($$2.getBlock($$20).isAir() && $$20 < (int)$$15 && $$16.nextDouble() > 0.01D) || ($$2.getBlock($$20).is(Blocks.WATER) && $$20 > (int)$$14 && $$20 < this.seaLevel && $$14 != 0.0D && $$16.nextDouble() > 0.15D)) {
/* 313 */         if ($$19 <= $$17 && $$20 > $$18) {
/* 314 */           $$2.setBlock($$20, SNOW_BLOCK);
/* 315 */           $$19++;
/*     */         } else {
/* 317 */           $$2.setBlock($$20, PACKED_ICE);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static BlockState[] generateBands(RandomSource $$0) {
/* 324 */     BlockState[] $$1 = new BlockState[192];
/* 325 */     Arrays.fill((Object[])$$1, TERRACOTTA);
/*     */ 
/*     */     
/* 328 */     for (int $$2 = 0; $$2 < $$1.length; $$2++) {
/* 329 */       $$2 += $$0.nextInt(5) + 1;
/* 330 */       if ($$2 < $$1.length) {
/* 331 */         $$1[$$2] = ORANGE_TERRACOTTA;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 336 */     makeBands($$0, $$1, 1, YELLOW_TERRACOTTA);
/*     */     
/* 338 */     makeBands($$0, $$1, 2, BROWN_TERRACOTTA);
/*     */     
/* 340 */     makeBands($$0, $$1, 1, RED_TERRACOTTA);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 345 */     int $$3 = $$0.nextIntBetweenInclusive(9, 15); int $$5;
/* 346 */     for (int $$4 = 0; $$4 < $$3 && $$5 < $$1.length; $$4++, $$5 += $$0.nextInt(16) + 4) {
/* 347 */       $$1[$$5] = WHITE_TERRACOTTA;
/* 348 */       if ($$5 - 1 > 0 && $$0.nextBoolean()) {
/* 349 */         $$1[$$5 - 1] = LIGHT_GRAY_TERRACOTTA;
/*     */       }
/* 351 */       if ($$5 + 1 < $$1.length && $$0.nextBoolean()) {
/* 352 */         $$1[$$5 + 1] = LIGHT_GRAY_TERRACOTTA;
/*     */       }
/*     */     } 
/*     */     
/* 356 */     return $$1;
/*     */   }
/*     */   
/*     */   private static void makeBands(RandomSource $$0, BlockState[] $$1, int $$2, BlockState $$3) {
/* 360 */     int $$4 = $$0.nextIntBetweenInclusive(6, 15);
/* 361 */     for (int $$5 = 0; $$5 < $$4; $$5++) {
/* 362 */       int $$6 = $$2 + $$0.nextInt(3);
/* 363 */       int $$7 = $$0.nextInt($$1.length);
/*     */       
/* 365 */       for (int $$8 = 0; $$7 + $$8 < $$1.length && $$8 < $$6; $$8++) {
/* 366 */         $$1[$$7 + $$8] = $$3;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected BlockState getBand(int $$0, int $$1, int $$2) {
/* 372 */     int $$3 = (int)Math.round(this.clayBandsOffsetNoise.getValue($$0, 0.0D, $$2) * 4.0D);
/* 373 */     return this.clayBands[($$1 + $$3 + this.clayBands.length) % this.clayBands.length];
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\SurfaceSystem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */