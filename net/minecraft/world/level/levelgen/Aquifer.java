/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.biome.OverworldBiomeBuilder;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import org.apache.commons.lang3.mutable.MutableDouble;
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
/*     */ public interface Aquifer
/*     */ {
/*     */   static Aquifer create(NoiseChunk $$0, ChunkPos $$1, NoiseRouter $$2, PositionalRandomFactory $$3, int $$4, int $$5, FluidPicker $$6) {
/*  57 */     return new NoiseBasedAquifer($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*     */   }
/*     */   
/*     */   static Aquifer createDisabled(final FluidPicker fluidRule) {
/*  61 */     return new Aquifer()
/*     */       {
/*     */         @Nullable
/*     */         public BlockState computeSubstance(DensityFunction.FunctionContext $$0, double $$1) {
/*  65 */           if ($$1 > 0.0D) {
/*  66 */             return null;
/*     */           }
/*  68 */           return fluidRule.computeFluid($$0.blockX(), $$0.blockY(), $$0.blockZ()).at($$0.blockY());
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean shouldScheduleFluidUpdate() {
/*  73 */           return false;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   BlockState computeSubstance(DensityFunction.FunctionContext paramFunctionContext, double paramDouble);
/*     */ 
/*     */   
/*     */   boolean shouldScheduleFluidUpdate();
/*     */ 
/*     */   
/*     */   public static class NoiseBasedAquifer
/*     */     implements Aquifer
/*     */   {
/*     */     private static final int X_RANGE = 10;
/*     */     
/*     */     private static final int Y_RANGE = 9;
/*     */     
/*     */     private static final int Z_RANGE = 10;
/*     */     
/*     */     private static final int X_SEPARATION = 6;
/*     */     
/*     */     private static final int Y_SEPARATION = 3;
/*     */     
/*     */     private static final int Z_SEPARATION = 6;
/*     */     private static final int X_SPACING = 16;
/*     */     private static final int Y_SPACING = 12;
/*     */     private static final int Z_SPACING = 16;
/*     */     private static final int MAX_REASONABLE_DISTANCE_TO_AQUIFER_CENTER = 11;
/* 104 */     private static final double FLOWING_UPDATE_SIMULARITY = similarity(
/* 105 */         Mth.square(10), 
/* 106 */         Mth.square(12));
/*     */ 
/*     */     
/*     */     private final NoiseChunk noiseChunk;
/*     */ 
/*     */     
/*     */     private final DensityFunction barrierNoise;
/*     */ 
/*     */     
/*     */     private final DensityFunction fluidLevelFloodednessNoise;
/*     */ 
/*     */     
/*     */     private final DensityFunction fluidLevelSpreadNoise;
/*     */ 
/*     */     
/*     */     private final DensityFunction lavaNoise;
/*     */ 
/*     */     
/*     */     private final PositionalRandomFactory positionalRandomFactory;
/*     */ 
/*     */     
/*     */     private final Aquifer.FluidStatus[] aquiferCache;
/*     */     
/*     */     private final long[] aquiferLocationCache;
/*     */     
/*     */     private final Aquifer.FluidPicker globalFluidPicker;
/*     */     
/*     */     private final DensityFunction erosion;
/*     */     
/*     */     private final DensityFunction depth;
/*     */     
/*     */     private boolean shouldScheduleFluidUpdate;
/*     */     
/*     */     private final int minGridX;
/*     */     
/*     */     private final int minGridY;
/*     */     
/*     */     private final int minGridZ;
/*     */     
/*     */     private final int gridSizeX;
/*     */     
/*     */     private final int gridSizeZ;
/*     */     
/* 149 */     private static final int[][] SURFACE_SAMPLING_OFFSETS_IN_CHUNKS = new int[][] { { 0, 0 }, { -2, -1 }, { -1, -1 }, { 0, -1 }, { 1, -1 }, { -3, 0 }, { -2, 0 }, { -1, 0 }, { 1, 0 }, { -2, 1 }, { -1, 1 }, { 0, 1 }, { 1, 1 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     NoiseBasedAquifer(NoiseChunk $$0, ChunkPos $$1, NoiseRouter $$2, PositionalRandomFactory $$3, int $$4, int $$5, Aquifer.FluidPicker $$6) {
/* 157 */       this.noiseChunk = $$0;
/* 158 */       this.barrierNoise = $$2.barrierNoise();
/* 159 */       this.fluidLevelFloodednessNoise = $$2.fluidLevelFloodednessNoise();
/* 160 */       this.fluidLevelSpreadNoise = $$2.fluidLevelSpreadNoise();
/* 161 */       this.lavaNoise = $$2.lavaNoise();
/* 162 */       this.erosion = $$2.erosion();
/* 163 */       this.depth = $$2.depth();
/*     */       
/* 165 */       this.positionalRandomFactory = $$3;
/*     */       
/* 167 */       this.minGridX = gridX($$1.getMinBlockX()) - 1;
/* 168 */       this.globalFluidPicker = $$6;
/* 169 */       int $$7 = gridX($$1.getMaxBlockX()) + 1;
/* 170 */       this.gridSizeX = $$7 - this.minGridX + 1;
/*     */       
/* 172 */       this.minGridY = gridY($$4) - 1;
/* 173 */       int $$8 = gridY($$4 + $$5) + 1;
/* 174 */       int $$9 = $$8 - this.minGridY + 1;
/*     */       
/* 176 */       this.minGridZ = gridZ($$1.getMinBlockZ()) - 1;
/* 177 */       int $$10 = gridZ($$1.getMaxBlockZ()) + 1;
/* 178 */       this.gridSizeZ = $$10 - this.minGridZ + 1;
/* 179 */       int $$11 = this.gridSizeX * $$9 * this.gridSizeZ;
/*     */       
/* 181 */       this.aquiferCache = new Aquifer.FluidStatus[$$11];
/*     */       
/* 183 */       this.aquiferLocationCache = new long[$$11];
/* 184 */       Arrays.fill(this.aquiferLocationCache, Long.MAX_VALUE);
/*     */     }
/*     */     
/*     */     private int getIndex(int $$0, int $$1, int $$2) {
/* 188 */       int $$3 = $$0 - this.minGridX;
/* 189 */       int $$4 = $$1 - this.minGridY;
/* 190 */       int $$5 = $$2 - this.minGridZ;
/*     */       
/* 192 */       return ($$4 * this.gridSizeZ + $$5) * this.gridSizeX + $$3;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public BlockState computeSubstance(DensityFunction.FunctionContext $$0, double $$1) {
/* 201 */       int $$2 = $$0.blockX();
/* 202 */       int $$3 = $$0.blockY();
/* 203 */       int $$4 = $$0.blockZ();
/*     */       
/* 205 */       if ($$1 > 0.0D) {
/* 206 */         this.shouldScheduleFluidUpdate = false;
/* 207 */         return null;
/*     */       } 
/*     */       
/* 210 */       Aquifer.FluidStatus $$5 = this.globalFluidPicker.computeFluid($$2, $$3, $$4);
/* 211 */       if ($$5.at($$3).is(Blocks.LAVA)) {
/* 212 */         this.shouldScheduleFluidUpdate = false;
/* 213 */         return Blocks.LAVA.defaultBlockState();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 220 */       int $$6 = Math.floorDiv($$2 - 5, 16);
/* 221 */       int $$7 = Math.floorDiv($$3 + 1, 12);
/* 222 */       int $$8 = Math.floorDiv($$4 - 5, 16);
/*     */ 
/*     */       
/* 225 */       int $$9 = Integer.MAX_VALUE;
/* 226 */       int $$10 = Integer.MAX_VALUE;
/* 227 */       int $$11 = Integer.MAX_VALUE;
/*     */       
/* 229 */       long $$12 = 0L;
/* 230 */       long $$13 = 0L;
/* 231 */       long $$14 = 0L;
/*     */ 
/*     */       
/* 234 */       for (int $$15 = 0; $$15 <= 1; $$15++) {
/* 235 */         for (int $$16 = -1; $$16 <= 1; $$16++) {
/* 236 */           for (int $$17 = 0; $$17 <= 1; $$17++) {
/* 237 */             long $$25; int $$18 = $$6 + $$15;
/* 238 */             int $$19 = $$7 + $$16;
/* 239 */             int $$20 = $$8 + $$17;
/*     */             
/* 241 */             int $$21 = getIndex($$18, $$19, $$20);
/*     */ 
/*     */             
/* 244 */             long $$22 = this.aquiferLocationCache[$$21];
/* 245 */             if ($$22 != Long.MAX_VALUE) {
/* 246 */               long $$23 = $$22;
/*     */             } else {
/* 248 */               RandomSource $$24 = this.positionalRandomFactory.at($$18, $$19, $$20);
/*     */               
/* 250 */               $$25 = BlockPos.asLong($$18 * 16 + $$24
/* 251 */                   .nextInt(10), $$19 * 12 + $$24
/* 252 */                   .nextInt(9), $$20 * 16 + $$24
/* 253 */                   .nextInt(10));
/*     */               
/* 255 */               this.aquiferLocationCache[$$21] = $$25;
/*     */             } 
/*     */             
/* 258 */             int $$26 = BlockPos.getX($$25) - $$2;
/* 259 */             int $$27 = BlockPos.getY($$25) - $$3;
/* 260 */             int $$28 = BlockPos.getZ($$25) - $$4;
/* 261 */             int $$29 = $$26 * $$26 + $$27 * $$27 + $$28 * $$28;
/*     */ 
/*     */             
/* 264 */             if ($$9 >= $$29) {
/* 265 */               $$14 = $$13;
/* 266 */               $$13 = $$12;
/* 267 */               $$12 = $$25;
/*     */               
/* 269 */               $$11 = $$10;
/* 270 */               $$10 = $$9;
/* 271 */               $$9 = $$29;
/* 272 */             } else if ($$10 >= $$29) {
/* 273 */               $$14 = $$13;
/* 274 */               $$13 = $$25;
/*     */               
/* 276 */               $$11 = $$10;
/* 277 */               $$10 = $$29;
/* 278 */             } else if ($$11 >= $$29) {
/* 279 */               $$14 = $$25;
/*     */               
/* 281 */               $$11 = $$29;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 292 */       Aquifer.FluidStatus $$30 = getAquiferStatus($$12);
/* 293 */       double $$31 = similarity($$9, $$10);
/*     */       
/* 295 */       BlockState $$32 = $$30.at($$3);
/* 296 */       BlockState $$33 = $$32;
/*     */ 
/*     */ 
/*     */       
/* 300 */       if ($$31 <= 0.0D) {
/* 301 */         this.shouldScheduleFluidUpdate = ($$31 >= FLOWING_UPDATE_SIMULARITY);
/* 302 */         return $$33;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 307 */       if ($$32.is(Blocks.WATER) && this.globalFluidPicker.computeFluid($$2, $$3 - 1, $$4).at($$3 - 1).is(Blocks.LAVA)) {
/* 308 */         this.shouldScheduleFluidUpdate = true;
/* 309 */         return $$33;
/*     */       } 
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
/* 326 */       MutableDouble $$34 = new MutableDouble(Double.NaN);
/* 327 */       Aquifer.FluidStatus $$35 = getAquiferStatus($$13);
/*     */ 
/*     */       
/* 330 */       double $$36 = $$31 * calculatePressure($$0, $$34, $$30, $$35);
/* 331 */       if ($$1 + $$36 > 0.0D) {
/* 332 */         this.shouldScheduleFluidUpdate = false;
/* 333 */         return null;
/*     */       } 
/*     */       
/* 336 */       Aquifer.FluidStatus $$37 = getAquiferStatus($$14);
/*     */       
/* 338 */       double $$38 = similarity($$9, $$11);
/* 339 */       if ($$38 > 0.0D) {
/*     */         
/* 341 */         double $$39 = $$31 * $$38 * calculatePressure($$0, $$34, $$30, $$37);
/* 342 */         if ($$1 + $$39 > 0.0D) {
/* 343 */           this.shouldScheduleFluidUpdate = false;
/* 344 */           return null;
/*     */         } 
/*     */       } 
/*     */       
/* 348 */       double $$40 = similarity($$10, $$11);
/* 349 */       if ($$40 > 0.0D) {
/*     */         
/* 351 */         double $$41 = $$31 * $$40 * calculatePressure($$0, $$34, $$35, $$37);
/* 352 */         if ($$1 + $$41 > 0.0D) {
/* 353 */           this.shouldScheduleFluidUpdate = false;
/* 354 */           return null;
/*     */         } 
/*     */       } 
/*     */       
/* 358 */       this.shouldScheduleFluidUpdate = true;
/* 359 */       return $$33;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldScheduleFluidUpdate() {
/* 364 */       return this.shouldScheduleFluidUpdate;
/*     */     }
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
/*     */     private static double similarity(int $$0, int $$1) {
/* 377 */       double $$2 = 25.0D;
/*     */ 
/*     */       
/* 380 */       return 1.0D - Math.abs($$1 - $$0) / 25.0D;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private double calculatePressure(DensityFunction.FunctionContext $$0, MutableDouble $$1, Aquifer.FluidStatus $$2, Aquifer.FluidStatus $$3) {
/*     */       double $$23, $$29;
/* 388 */       int $$4 = $$0.blockY();
/* 389 */       BlockState $$5 = $$2.at($$4);
/* 390 */       BlockState $$6 = $$3.at($$4);
/*     */       
/* 392 */       if (($$5.is(Blocks.LAVA) && $$6.is(Blocks.WATER)) || ($$5.is(Blocks.WATER) && $$6.is(Blocks.LAVA)))
/*     */       {
/* 394 */         return 2.0D;
/*     */       }
/*     */ 
/*     */       
/* 398 */       int $$7 = Math.abs($$2.fluidLevel - $$3.fluidLevel);
/*     */       
/* 400 */       if ($$7 == 0) {
/* 401 */         return 0.0D;
/*     */       }
/*     */       
/* 404 */       double $$8 = 0.5D * ($$2.fluidLevel + $$3.fluidLevel);
/*     */ 
/*     */       
/* 407 */       double $$9 = $$4 + 0.5D - $$8;
/*     */       
/* 409 */       double $$10 = $$7 / 2.0D;
/*     */ 
/*     */ 
/*     */       
/* 413 */       double $$11 = 0.0D;
/*     */       
/* 415 */       double $$12 = 2.5D;
/*     */       
/* 417 */       double $$13 = 1.5D;
/*     */ 
/*     */       
/* 420 */       double $$14 = 3.0D;
/* 421 */       double $$15 = 10.0D;
/* 422 */       double $$16 = 3.0D;
/*     */ 
/*     */ 
/*     */       
/* 426 */       double $$17 = $$10 - Math.abs($$9);
/*     */ 
/*     */       
/* 429 */       if ($$9 > 0.0D) {
/*     */         
/* 431 */         double $$18 = 0.0D + $$17;
/* 432 */         if ($$18 > 0.0D) {
/*     */           
/* 434 */           double $$19 = $$18 / 1.5D;
/*     */         } else {
/*     */           
/* 437 */           double $$20 = $$18 / 2.5D;
/*     */         } 
/*     */       } else {
/*     */         
/* 441 */         double $$21 = 3.0D + $$17;
/* 442 */         if ($$21 > 0.0D) {
/*     */           
/* 444 */           double $$22 = $$21 / 3.0D;
/*     */         } else {
/*     */           
/* 447 */           $$23 = $$21 / 10.0D;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 452 */       double $$24 = 2.0D;
/*     */ 
/*     */ 
/*     */       
/* 456 */       if ($$23 < -2.0D || $$23 > 2.0D) {
/* 457 */         double $$25 = 0.0D;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 462 */         double $$26 = $$1.getValue().doubleValue();
/* 463 */         if (Double.isNaN($$26)) {
/* 464 */           double $$27 = this.barrierNoise.compute($$0);
/* 465 */           $$1.setValue($$27);
/* 466 */           double $$28 = $$27;
/*     */         } else {
/* 468 */           $$29 = $$26;
/*     */         } 
/*     */       } 
/*     */       
/* 472 */       return 2.0D * ($$29 + $$23);
/*     */     }
/*     */     
/*     */     private int gridX(int $$0) {
/* 476 */       return Math.floorDiv($$0, 16);
/*     */     }
/*     */     
/*     */     private int gridY(int $$0) {
/* 480 */       return Math.floorDiv($$0, 12);
/*     */     }
/*     */     
/*     */     private int gridZ(int $$0) {
/* 484 */       return Math.floorDiv($$0, 16);
/*     */     }
/*     */     
/*     */     private Aquifer.FluidStatus getAquiferStatus(long $$0) {
/* 488 */       int $$1 = BlockPos.getX($$0);
/* 489 */       int $$2 = BlockPos.getY($$0);
/* 490 */       int $$3 = BlockPos.getZ($$0);
/*     */       
/* 492 */       int $$4 = gridX($$1);
/* 493 */       int $$5 = gridY($$2);
/* 494 */       int $$6 = gridZ($$3);
/*     */       
/* 496 */       int $$7 = getIndex($$4, $$5, $$6);
/* 497 */       Aquifer.FluidStatus $$8 = this.aquiferCache[$$7];
/* 498 */       if ($$8 != null) {
/* 499 */         return $$8;
/*     */       }
/* 501 */       Aquifer.FluidStatus $$9 = computeFluid($$1, $$2, $$3);
/* 502 */       this.aquiferCache[$$7] = $$9;
/* 503 */       return $$9;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Aquifer.FluidStatus computeFluid(int $$0, int $$1, int $$2) {
/* 511 */       Aquifer.FluidStatus $$3 = this.globalFluidPicker.computeFluid($$0, $$1, $$2);
/*     */       
/* 513 */       int $$4 = Integer.MAX_VALUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 519 */       int $$5 = $$1 + 12;
/* 520 */       int $$6 = $$1 - 12;
/* 521 */       boolean $$7 = false;
/* 522 */       for (int[] $$8 : SURFACE_SAMPLING_OFFSETS_IN_CHUNKS) {
/* 523 */         int $$9 = $$0 + SectionPos.sectionToBlockCoord($$8[0]);
/* 524 */         int $$10 = $$2 + SectionPos.sectionToBlockCoord($$8[1]);
/* 525 */         int $$11 = this.noiseChunk.preliminarySurfaceLevel($$9, $$10);
/*     */ 
/*     */         
/* 528 */         int $$12 = $$11 + 8;
/*     */         
/* 530 */         boolean $$13 = ($$8[0] == 0 && $$8[1] == 0);
/*     */         
/* 532 */         if ($$13 && $$6 > $$12)
/*     */         {
/*     */           
/* 535 */           return $$3;
/*     */         }
/*     */         
/* 538 */         boolean $$14 = ($$5 > $$12);
/* 539 */         if ($$14 || $$13) {
/* 540 */           Aquifer.FluidStatus $$15 = this.globalFluidPicker.computeFluid($$9, $$12, $$10);
/* 541 */           if (!$$15.at($$12).isAir()) {
/* 542 */             if ($$13) {
/* 543 */               $$7 = true;
/*     */             }
/* 545 */             if ($$14)
/*     */             {
/* 547 */               return $$15;
/*     */             }
/*     */           } 
/*     */         } 
/* 551 */         $$4 = Math.min($$4, $$11);
/*     */       } 
/*     */       
/* 554 */       int $$16 = computeSurfaceLevel($$0, $$1, $$2, $$3, $$4, $$7);
/*     */       
/* 556 */       return new Aquifer.FluidStatus($$16, computeFluidType($$0, $$1, $$2, $$3, $$16));
/*     */     } private int computeSurfaceLevel(int $$0, int $$1, int $$2, Aquifer.FluidStatus $$3, int $$4, boolean $$5) {
/*     */       double $$15, $$16;
/*     */       int $$19;
/* 560 */       DensityFunction.SinglePointContext $$6 = new DensityFunction.SinglePointContext($$0, $$1, $$2);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 565 */       if (OverworldBiomeBuilder.isDeepDarkRegion(this.erosion, this.depth, $$6)) {
/* 566 */         double $$7 = -1.0D;
/* 567 */         double $$8 = -1.0D;
/*     */       } else {
/* 569 */         int $$9 = $$4 + 8 - $$1;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 574 */         int $$10 = 64;
/* 575 */         double $$11 = $$5 ? Mth.clampedMap($$9, 0.0D, 64.0D, 1.0D, 0.0D) : 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 580 */         double $$12 = Mth.clamp(this.fluidLevelFloodednessNoise.compute($$6), -1.0D, 1.0D);
/*     */ 
/*     */ 
/*     */         
/* 584 */         double $$13 = Mth.map($$11, 1.0D, 0.0D, -0.3D, 0.8D);
/*     */ 
/*     */ 
/*     */         
/* 588 */         double $$14 = Mth.map($$11, 1.0D, 0.0D, -0.8D, 0.4D);
/*     */         
/* 590 */         $$15 = $$12 - $$14;
/* 591 */         $$16 = $$12 - $$13;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 597 */       if ($$16 > 0.0D) {
/*     */         
/* 599 */         int $$17 = $$3.fluidLevel;
/* 600 */       } else if ($$15 > 0.0D) {
/* 601 */         int $$18 = computeRandomizedFluidSurfaceLevel($$0, $$1, $$2, $$4);
/*     */       } else {
/*     */         
/* 604 */         $$19 = DimensionType.WAY_BELOW_MIN_Y;
/*     */       } 
/* 606 */       return $$19;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int computeRandomizedFluidSurfaceLevel(int $$0, int $$1, int $$2, int $$3) {
/* 615 */       int $$4 = 16;
/* 616 */       int $$5 = 40;
/* 617 */       int $$6 = Math.floorDiv($$0, 16);
/* 618 */       int $$7 = Math.floorDiv($$1, 40);
/* 619 */       int $$8 = Math.floorDiv($$2, 16);
/* 620 */       int $$9 = $$7 * 40 + 20;
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
/* 631 */       int $$10 = 10;
/* 632 */       double $$11 = this.fluidLevelSpreadNoise.compute(new DensityFunction.SinglePointContext($$6, $$7, $$8)) * 10.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 637 */       int $$12 = Mth.quantize($$11, 3);
/*     */       
/* 639 */       int $$13 = $$9 + $$12;
/*     */ 
/*     */       
/* 642 */       return Math.min($$3, $$13);
/*     */     }
/*     */     
/*     */     private BlockState computeFluidType(int $$0, int $$1, int $$2, Aquifer.FluidStatus $$3, int $$4) {
/* 646 */       BlockState $$5 = $$3.fluidType;
/*     */ 
/*     */       
/* 649 */       if ($$4 <= -10 && $$4 != DimensionType.WAY_BELOW_MIN_Y && $$3.fluidType != Blocks.LAVA.defaultBlockState()) {
/* 650 */         int $$6 = 64;
/* 651 */         int $$7 = 40;
/*     */         
/* 653 */         int $$8 = Math.floorDiv($$0, 64);
/* 654 */         int $$9 = Math.floorDiv($$1, 40);
/* 655 */         int $$10 = Math.floorDiv($$2, 64);
/*     */         
/* 657 */         double $$11 = this.lavaNoise.compute(new DensityFunction.SinglePointContext($$8, $$9, $$10));
/* 658 */         if (Math.abs($$11) > 0.3D) {
/* 659 */           $$5 = Blocks.LAVA.defaultBlockState();
/*     */         }
/*     */       } 
/* 662 */       return $$5;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class FluidStatus
/*     */   {
/*     */     final int fluidLevel;
/*     */ 
/*     */     
/*     */     final BlockState fluidType;
/*     */ 
/*     */ 
/*     */     
/*     */     public FluidStatus(int $$0, BlockState $$1) {
/* 678 */       this.fluidLevel = $$0;
/* 679 */       this.fluidType = $$1;
/*     */     }
/*     */     
/*     */     public BlockState at(int $$0) {
/* 683 */       return ($$0 < this.fluidLevel) ? this.fluidType : Blocks.AIR.defaultBlockState();
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface FluidPicker {
/*     */     Aquifer.FluidStatus computeFluid(int param1Int1, int param1Int2, int param1Int3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\Aquifer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */