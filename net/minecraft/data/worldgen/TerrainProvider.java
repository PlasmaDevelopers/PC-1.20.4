/*     */ package net.minecraft.data.worldgen;
/*     */ 
/*     */ import net.minecraft.util.CubicSpline;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.ToFloatFunction;
/*     */ import net.minecraft.world.level.levelgen.NoiseRouterData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TerrainProvider
/*     */ {
/*     */   private static final float DEEP_OCEAN_CONTINENTALNESS = -0.51F;
/*     */   private static final float OCEAN_CONTINENTALNESS = -0.4F;
/*     */   private static final float PLAINS_CONTINENTALNESS = 0.1F;
/*     */   private static final float BEACH_CONTINENTALNESS = -0.15F;
/*     */   private static final ToFloatFunction<Float> AMPLIFIED_OFFSET;
/*     */   private static final ToFloatFunction<Float> AMPLIFIED_FACTOR;
/*     */   private static final ToFloatFunction<Float> AMPLIFIED_JAGGEDNESS;
/*  21 */   private static final ToFloatFunction<Float> NO_TRANSFORM = ToFloatFunction.IDENTITY;
/*     */   static {
/*  23 */     AMPLIFIED_OFFSET = ToFloatFunction.createUnlimited($$0 -> ($$0 < 0.0F) ? $$0 : ($$0 * 2.0F));
/*  24 */     AMPLIFIED_FACTOR = ToFloatFunction.createUnlimited($$0 -> 1.25F - 6.25F / ($$0 + 5.0F));
/*  25 */     AMPLIFIED_JAGGEDNESS = ToFloatFunction.createUnlimited($$0 -> $$0 * 2.0F);
/*     */   }
/*     */   public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> overworldOffset(I $$0, I $$1, I $$2, boolean $$3) {
/*  28 */     ToFloatFunction<Float> $$4 = $$3 ? AMPLIFIED_OFFSET : NO_TRANSFORM;
/*     */ 
/*     */     
/*  31 */     CubicSpline<C, I> $$5 = buildErosionOffsetSpline($$1, $$2, -0.15F, 0.0F, 0.0F, 0.1F, 0.0F, -0.03F, false, false, $$4);
/*  32 */     CubicSpline<C, I> $$6 = buildErosionOffsetSpline($$1, $$2, -0.1F, 0.03F, 0.1F, 0.1F, 0.01F, -0.03F, false, false, $$4);
/*  33 */     CubicSpline<C, I> $$7 = buildErosionOffsetSpline($$1, $$2, -0.1F, 0.03F, 0.1F, 0.7F, 0.01F, -0.03F, true, true, $$4);
/*  34 */     CubicSpline<C, I> $$8 = buildErosionOffsetSpline($$1, $$2, -0.05F, 0.03F, 0.1F, 1.0F, 0.01F, 0.01F, true, true, $$4);
/*     */     
/*  36 */     return CubicSpline.builder((ToFloatFunction)$$0, $$4)
/*  37 */       .addPoint(-1.1F, 0.044F)
/*  38 */       .addPoint(-1.02F, -0.2222F)
/*  39 */       .addPoint(-0.51F, -0.2222F)
/*  40 */       .addPoint(-0.44F, -0.12F)
/*  41 */       .addPoint(-0.18F, -0.12F)
/*  42 */       .addPoint(-0.16F, $$5)
/*  43 */       .addPoint(-0.15F, $$5)
/*  44 */       .addPoint(-0.1F, $$6)
/*  45 */       .addPoint(0.25F, $$7)
/*  46 */       .addPoint(1.0F, $$8)
/*  47 */       .build();
/*     */   }
/*     */   
/*     */   public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> overworldFactor(I $$0, I $$1, I $$2, I $$3, boolean $$4) {
/*  51 */     ToFloatFunction<Float> $$5 = $$4 ? AMPLIFIED_FACTOR : NO_TRANSFORM;
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
/*  73 */     return CubicSpline.builder((ToFloatFunction)$$0, NO_TRANSFORM)
/*     */ 
/*     */       
/*  76 */       .addPoint(-0.19F, 3.95F)
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  82 */       .addPoint(-0.15F, getErosionFactor($$1, $$2, $$3, 6.25F, true, NO_TRANSFORM))
/*     */ 
/*     */       
/*  85 */       .addPoint(-0.1F, getErosionFactor($$1, $$2, $$3, 5.47F, true, $$5))
/*  86 */       .addPoint(0.03F, getErosionFactor($$1, $$2, $$3, 5.08F, true, $$5))
/*  87 */       .addPoint(0.06F, getErosionFactor($$1, $$2, $$3, 4.69F, false, $$5))
/*  88 */       .build();
/*     */   }
/*     */   
/*     */   public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> overworldJaggedness(I $$0, I $$1, I $$2, I $$3, boolean $$4) {
/*  92 */     ToFloatFunction<Float> $$5 = $$4 ? AMPLIFIED_JAGGEDNESS : NO_TRANSFORM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     float $$6 = 0.65F;
/* 101 */     return CubicSpline.builder((ToFloatFunction)$$0, $$5)
/* 102 */       .addPoint(-0.11F, 0.0F)
/* 103 */       .addPoint(0.03F, buildErosionJaggednessSpline($$1, $$2, $$3, 1.0F, 0.5F, 0.0F, 0.0F, $$5))
/* 104 */       .addPoint(0.65F, buildErosionJaggednessSpline($$1, $$2, $$3, 1.0F, 1.0F, 1.0F, 0.0F, $$5))
/* 105 */       .build();
/*     */   }
/*     */   
/*     */   private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> buildErosionJaggednessSpline(I $$0, I $$1, I $$2, float $$3, float $$4, float $$5, float $$6, ToFloatFunction<Float> $$7) {
/* 109 */     float $$8 = -0.5775F;
/*     */     
/* 111 */     CubicSpline<C, I> $$9 = buildRidgeJaggednessSpline($$1, $$2, $$3, $$5, $$7);
/* 112 */     CubicSpline<C, I> $$10 = buildRidgeJaggednessSpline($$1, $$2, $$4, $$6, $$7);
/*     */     
/* 114 */     return CubicSpline.builder((ToFloatFunction)$$0, $$7)
/* 115 */       .addPoint(-1.0F, $$9)
/* 116 */       .addPoint(-0.78F, $$10)
/* 117 */       .addPoint(-0.5775F, $$10)
/* 118 */       .addPoint(-0.375F, 0.0F)
/* 119 */       .build();
/*     */   }
/*     */   
/*     */   private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> buildRidgeJaggednessSpline(I $$0, I $$1, float $$2, float $$3, ToFloatFunction<Float> $$4) {
/* 123 */     float $$5 = NoiseRouterData.peaksAndValleys(0.4F);
/* 124 */     float $$6 = NoiseRouterData.peaksAndValleys(0.56666666F);
/*     */     
/* 126 */     float $$7 = ($$5 + $$6) / 2.0F;
/*     */     
/* 128 */     CubicSpline.Builder<C, I> $$8 = CubicSpline.builder((ToFloatFunction)$$1, $$4);
/*     */     
/* 130 */     $$8.addPoint($$5, 0.0F);
/*     */     
/* 132 */     if ($$3 > 0.0F) {
/* 133 */       $$8.addPoint($$7, buildWeirdnessJaggednessSpline($$0, $$3, $$4));
/*     */     } else {
/* 135 */       $$8.addPoint($$7, 0.0F);
/*     */     } 
/*     */     
/* 138 */     if ($$2 > 0.0F) {
/* 139 */       $$8.addPoint(1.0F, buildWeirdnessJaggednessSpline($$0, $$2, $$4));
/*     */     } else {
/* 141 */       $$8.addPoint(1.0F, 0.0F);
/*     */     } 
/* 143 */     return $$8.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> buildWeirdnessJaggednessSpline(I $$0, float $$1, ToFloatFunction<Float> $$2) {
/* 151 */     float $$3 = 0.63F * $$1;
/* 152 */     float $$4 = 0.3F * $$1;
/*     */     
/* 154 */     return CubicSpline.builder((ToFloatFunction)$$0, $$2)
/* 155 */       .addPoint(-0.01F, $$3)
/* 156 */       .addPoint(0.01F, $$4)
/* 157 */       .build();
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
/*     */   private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> getErosionFactor(I $$0, I $$1, I $$2, float $$3, boolean $$4, ToFloatFunction<Float> $$5) {
/* 169 */     CubicSpline<C, I> $$6 = CubicSpline.builder((ToFloatFunction)$$1, $$5).addPoint(-0.2F, 6.3F).addPoint(0.2F, $$3).build();
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
/* 186 */     CubicSpline.Builder<C, I> $$7 = CubicSpline.builder((ToFloatFunction)$$0, $$5).addPoint(-0.6F, $$6).addPoint(-0.5F, CubicSpline.builder((ToFloatFunction)$$1, $$5).addPoint(-0.05F, 6.3F).addPoint(0.05F, 2.67F).build()).addPoint(-0.35F, $$6).addPoint(-0.25F, $$6).addPoint(-0.1F, CubicSpline.builder((ToFloatFunction)$$1, $$5).addPoint(-0.05F, 2.67F).addPoint(0.05F, 6.3F).build()).addPoint(0.03F, $$6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     if ($$4) {
/*     */ 
/*     */ 
/*     */       
/* 198 */       CubicSpline<C, I> $$8 = CubicSpline.builder((ToFloatFunction)$$1, $$5).addPoint(0.0F, $$3).addPoint(0.1F, 0.625F).build();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 203 */       CubicSpline<C, I> $$9 = CubicSpline.builder((ToFloatFunction)$$2, $$5).addPoint(-0.9F, $$3).addPoint(-0.69F, $$8).build();
/*     */       
/* 205 */       $$7
/* 206 */         .addPoint(0.35F, $$3)
/* 207 */         .addPoint(0.45F, $$9)
/* 208 */         .addPoint(0.55F, $$9)
/* 209 */         .addPoint(0.62F, $$3);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 214 */       CubicSpline<C, I> $$10 = CubicSpline.builder((ToFloatFunction)$$2, $$5).addPoint(-0.7F, $$6).addPoint(-0.15F, 1.37F).build();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 219 */       CubicSpline<C, I> $$11 = CubicSpline.builder((ToFloatFunction)$$2, $$5).addPoint(0.45F, $$6).addPoint(0.7F, 1.56F).build();
/*     */       
/* 221 */       $$7
/* 222 */         .addPoint(0.05F, $$11)
/* 223 */         .addPoint(0.4F, $$11)
/* 224 */         .addPoint(0.45F, $$10)
/* 225 */         .addPoint(0.55F, $$10)
/* 226 */         .addPoint(0.58F, $$3);
/*     */     } 
/* 228 */     return $$7.build();
/*     */   }
/*     */   
/*     */   private static float calculateSlope(float $$0, float $$1, float $$2, float $$3) {
/* 232 */     return ($$1 - $$0) / ($$3 - $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> buildMountainRidgeSplineWithPoints(I $$0, float $$1, boolean $$2, ToFloatFunction<Float> $$3) {
/* 237 */     CubicSpline.Builder<C, I> $$4 = CubicSpline.builder((ToFloatFunction)$$0, $$3);
/*     */     
/* 239 */     float $$5 = -0.7F;
/* 240 */     float $$6 = -1.0F;
/* 241 */     float $$7 = mountainContinentalness(-1.0F, $$1, -0.7F);
/* 242 */     float $$8 = 1.0F;
/* 243 */     float $$9 = mountainContinentalness(1.0F, $$1, -0.7F);
/*     */     
/* 245 */     float $$10 = calculateMountainRidgeZeroContinentalnessPoint($$1);
/*     */     
/* 247 */     float $$11 = -0.65F;
/*     */     
/* 249 */     if (-0.65F < $$10 && $$10 < 1.0F) {
/*     */ 
/*     */ 
/*     */       
/* 253 */       float $$12 = mountainContinentalness(-0.65F, $$1, -0.7F);
/* 254 */       float $$13 = -0.75F;
/* 255 */       float $$14 = mountainContinentalness(-0.75F, $$1, -0.7F);
/*     */ 
/*     */       
/* 258 */       float $$15 = calculateSlope($$7, $$14, -1.0F, -0.75F);
/* 259 */       $$4.addPoint(-1.0F, $$7, $$15);
/*     */ 
/*     */       
/* 262 */       $$4.addPoint(-0.75F, $$14);
/* 263 */       $$4.addPoint(-0.65F, $$12);
/*     */ 
/*     */       
/* 266 */       float $$16 = mountainContinentalness($$10, $$1, -0.7F);
/* 267 */       float $$17 = calculateSlope($$16, $$9, $$10, 1.0F);
/* 268 */       float $$18 = 0.01F;
/* 269 */       $$4.addPoint($$10 - 0.01F, $$16);
/* 270 */       $$4.addPoint($$10, $$16, $$17);
/* 271 */       $$4.addPoint(1.0F, $$9, $$17);
/*     */     } else {
/* 273 */       float $$19 = calculateSlope($$7, $$9, -1.0F, 1.0F);
/*     */       
/* 275 */       if ($$2) {
/*     */         
/* 277 */         $$4.addPoint(-1.0F, Math.max(0.2F, $$7));
/* 278 */         $$4.addPoint(0.0F, Mth.lerp(0.5F, $$7, $$9), $$19);
/*     */       }
/*     */       else {
/*     */         
/* 282 */         $$4.addPoint(-1.0F, $$7, $$19);
/*     */       } 
/* 284 */       $$4.addPoint(1.0F, $$9, $$19);
/*     */     } 
/*     */     
/* 287 */     return $$4.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float mountainContinentalness(float $$0, float $$1, float $$2) {
/* 295 */     float $$3 = 1.17F;
/* 296 */     float $$4 = 0.46082947F;
/* 297 */     float $$5 = 1.0F - (1.0F - $$1) * 0.5F;
/* 298 */     float $$6 = 0.5F * (1.0F - $$1);
/*     */     
/* 300 */     float $$7 = ($$0 + 1.17F) * 0.46082947F;
/* 301 */     float $$8 = $$7 * $$5 - $$6;
/*     */     
/* 303 */     if ($$0 < $$2)
/*     */     {
/*     */       
/* 306 */       return Math.max($$8, -0.2222F);
/*     */     }
/*     */     
/* 309 */     return Math.max($$8, 0.0F);
/*     */   }
/*     */   
/*     */   private static float calculateMountainRidgeZeroContinentalnessPoint(float $$0) {
/* 313 */     float $$1 = 1.17F;
/* 314 */     float $$2 = 0.46082947F;
/* 315 */     float $$3 = 1.0F - (1.0F - $$0) * 0.5F;
/* 316 */     float $$4 = 0.5F * (1.0F - $$0);
/*     */     
/* 318 */     return $$4 / 0.46082947F * $$3 - 1.17F;
/*     */   }
/*     */   
/*     */   public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> buildErosionOffsetSpline(I $$0, I $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, boolean $$8, boolean $$9, ToFloatFunction<Float> $$10) {
/* 322 */     float $$11 = 0.6F;
/*     */     
/* 324 */     float $$12 = 0.5F;
/* 325 */     float $$13 = 0.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 335 */     CubicSpline<C, I> $$14 = buildMountainRidgeSplineWithPoints($$1, Mth.lerp($$5, 0.6F, 1.5F), $$9, $$10);
/*     */     
/* 337 */     CubicSpline<C, I> $$15 = buildMountainRidgeSplineWithPoints($$1, Mth.lerp($$5, 0.6F, 1.0F), $$9, $$10);
/* 338 */     CubicSpline<C, I> $$16 = buildMountainRidgeSplineWithPoints($$1, $$5, $$9, $$10);
/*     */     
/* 340 */     CubicSpline<C, I> $$17 = ridgeSpline($$1, $$2 - 0.15F, 0.5F * $$5, 
/*     */ 
/*     */         
/* 343 */         Mth.lerp(0.5F, 0.5F, 0.5F) * $$5, 0.5F * $$5, 0.6F * $$5, 0.5F, $$10);
/*     */ 
/*     */ 
/*     */     
/* 347 */     CubicSpline<C, I> $$18 = ridgeSpline($$1, $$2, $$6 * $$5, $$3 * $$5, 0.5F * $$5, 0.6F * $$5, 0.5F, $$10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 354 */     CubicSpline<C, I> $$19 = ridgeSpline($$1, $$2, $$6, $$6, $$3, $$4, 0.5F, $$10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 361 */     CubicSpline<C, I> $$20 = ridgeSpline($$1, $$2, $$6, $$6, $$3, $$4, 0.5F, $$10);
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
/* 373 */     CubicSpline<C, I> $$21 = CubicSpline.builder((ToFloatFunction)$$1, $$10).addPoint(-1.0F, $$2).addPoint(-0.4F, $$19).addPoint(0.0F, $$4 + 0.07F).build();
/*     */     
/* 375 */     CubicSpline<C, I> $$22 = ridgeSpline($$1, -0.02F, $$7, $$7, $$3, $$4, 0.0F, $$10);
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
/* 390 */     CubicSpline.Builder<C, I> $$23 = CubicSpline.builder((ToFloatFunction)$$0, $$10).addPoint(-0.85F, $$14).addPoint(-0.7F, $$15).addPoint(-0.4F, $$16).addPoint(-0.35F, $$17).addPoint(-0.1F, $$18).addPoint(0.2F, $$19);
/*     */     
/* 392 */     if ($$8)
/*     */     {
/*     */       
/* 395 */       $$23
/* 396 */         .addPoint(0.4F, $$20)
/* 397 */         .addPoint(0.45F, $$21)
/* 398 */         .addPoint(0.55F, $$21)
/* 399 */         .addPoint(0.58F, $$20);
/*     */     }
/* 401 */     $$23
/* 402 */       .addPoint(0.7F, $$22);
/*     */     
/* 404 */     return $$23.build();
/*     */   }
/*     */ 
/*     */   
/*     */   private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> ridgeSpline(I $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, ToFloatFunction<Float> $$7) {
/* 409 */     float $$8 = Math.max(0.5F * ($$2 - $$1), $$6);
/* 410 */     float $$9 = 5.0F * ($$3 - $$2);
/* 411 */     return CubicSpline.builder((ToFloatFunction)$$0, $$7)
/* 412 */       .addPoint(-1.0F, $$1, $$8)
/* 413 */       .addPoint(-0.4F, $$2, Math.min($$8, $$9))
/* 414 */       .addPoint(0.0F, $$3, $$9)
/* 415 */       .addPoint(0.4F, $$4, 2.0F * ($$4 - $$3))
/* 416 */       .addPoint(1.0F, $$5, 0.7F * ($$5 - $$4))
/* 417 */       .build();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\TerrainProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */