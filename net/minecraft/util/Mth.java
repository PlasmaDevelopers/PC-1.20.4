/*     */ package net.minecraft.util;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.UUID;
/*     */ import java.util.function.IntPredicate;
/*     */ import java.util.stream.IntStream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.apache.commons.lang3.math.NumberUtils;
/*     */ import org.joml.Math;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Mth
/*     */ {
/*     */   private static final long UUID_VERSION = 61440L;
/*     */   private static final long UUID_VERSION_TYPE_4 = 16384L;
/*     */   private static final long UUID_VARIANT = -4611686018427387904L;
/*     */   private static final long UUID_VARIANT_2 = -9223372036854775808L;
/*     */   public static final float PI = 3.1415927F;
/*     */   public static final float HALF_PI = 1.5707964F;
/*     */   public static final float TWO_PI = 6.2831855F;
/*     */   public static final float DEG_TO_RAD = 0.017453292F;
/*     */   public static final float RAD_TO_DEG = 57.295776F;
/*     */   public static final float EPSILON = 1.0E-5F;
/*  34 */   public static final float SQRT_OF_TWO = sqrt(2.0F);
/*     */   
/*     */   private static final float SIN_SCALE = 10430.378F;
/*     */   
/*  38 */   public static final Vector3f Y_AXIS = new Vector3f(0.0F, 1.0F, 0.0F);
/*  39 */   public static final Vector3f X_AXIS = new Vector3f(1.0F, 0.0F, 0.0F);
/*  40 */   public static final Vector3f Z_AXIS = new Vector3f(0.0F, 0.0F, 1.0F);
/*     */   static {
/*  42 */     SIN = (float[])Util.make(new float[65536], $$0 -> {
/*     */           for (int $$1 = 0; $$1 < $$0.length; $$1++)
/*     */             $$0[$$1] = (float)Math.sin($$1 * Math.PI * 2.0D / 65536.0D); 
/*     */         });
/*     */   }
/*     */   private static final float[] SIN;
/*  48 */   private static final RandomSource RANDOM = RandomSource.createThreadSafe();
/*     */   
/*     */   public static float sin(float $$0) {
/*  51 */     return SIN[(int)($$0 * 10430.378F) & 0xFFFF];
/*     */   }
/*     */   
/*     */   public static float cos(float $$0) {
/*  55 */     return SIN[(int)($$0 * 10430.378F + 16384.0F) & 0xFFFF];
/*     */   }
/*     */   
/*     */   public static float sqrt(float $$0) {
/*  59 */     return (float)Math.sqrt($$0);
/*     */   }
/*     */   
/*     */   public static int floor(float $$0) {
/*  63 */     int $$1 = (int)$$0;
/*  64 */     return ($$0 < $$1) ? ($$1 - 1) : $$1;
/*     */   }
/*     */   
/*     */   public static int floor(double $$0) {
/*  68 */     int $$1 = (int)$$0;
/*  69 */     return ($$0 < $$1) ? ($$1 - 1) : $$1;
/*     */   }
/*     */   
/*     */   public static long lfloor(double $$0) {
/*  73 */     long $$1 = (long)$$0;
/*  74 */     return ($$0 < $$1) ? ($$1 - 1L) : $$1;
/*     */   }
/*     */   
/*     */   public static float abs(float $$0) {
/*  78 */     return Math.abs($$0);
/*     */   }
/*     */   
/*     */   public static int abs(int $$0) {
/*  82 */     return Math.abs($$0);
/*     */   }
/*     */   
/*     */   public static int ceil(float $$0) {
/*  86 */     int $$1 = (int)$$0;
/*  87 */     return ($$0 > $$1) ? ($$1 + 1) : $$1;
/*     */   }
/*     */   
/*     */   public static int ceil(double $$0) {
/*  91 */     int $$1 = (int)$$0;
/*  92 */     return ($$0 > $$1) ? ($$1 + 1) : $$1;
/*     */   }
/*     */   
/*     */   public static int clamp(int $$0, int $$1, int $$2) {
/*  96 */     return Math.min(Math.max($$0, $$1), $$2);
/*     */   }
/*     */   
/*     */   public static long clamp(long $$0, long $$1, long $$2) {
/* 100 */     return Math.min(Math.max($$0, $$1), $$2);
/*     */   }
/*     */   
/*     */   public static float clamp(float $$0, float $$1, float $$2) {
/* 104 */     if ($$0 < $$1) {
/* 105 */       return $$1;
/*     */     }
/* 107 */     return Math.min($$0, $$2);
/*     */   }
/*     */   
/*     */   public static double clamp(double $$0, double $$1, double $$2) {
/* 111 */     if ($$0 < $$1) {
/* 112 */       return $$1;
/*     */     }
/* 114 */     return Math.min($$0, $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double clampedLerp(double $$0, double $$1, double $$2) {
/* 122 */     if ($$2 < 0.0D) {
/* 123 */       return $$0;
/*     */     }
/* 125 */     if ($$2 > 1.0D) {
/* 126 */       return $$1;
/*     */     }
/* 128 */     return lerp($$2, $$0, $$1);
/*     */   }
/*     */   
/*     */   public static float clampedLerp(float $$0, float $$1, float $$2) {
/* 132 */     if ($$2 < 0.0F) {
/* 133 */       return $$0;
/*     */     }
/* 135 */     if ($$2 > 1.0F) {
/* 136 */       return $$1;
/*     */     }
/* 138 */     return lerp($$2, $$0, $$1);
/*     */   }
/*     */   
/*     */   public static double absMax(double $$0, double $$1) {
/* 142 */     if ($$0 < 0.0D) {
/* 143 */       $$0 = -$$0;
/*     */     }
/* 145 */     if ($$1 < 0.0D) {
/* 146 */       $$1 = -$$1;
/*     */     }
/* 148 */     return Math.max($$0, $$1);
/*     */   }
/*     */   
/*     */   public static int floorDiv(int $$0, int $$1) {
/* 152 */     return Math.floorDiv($$0, $$1);
/*     */   }
/*     */   
/*     */   public static int nextInt(RandomSource $$0, int $$1, int $$2) {
/* 156 */     if ($$1 >= $$2) {
/* 157 */       return $$1;
/*     */     }
/* 159 */     return $$0.nextInt($$2 - $$1 + 1) + $$1;
/*     */   }
/*     */   
/*     */   public static float nextFloat(RandomSource $$0, float $$1, float $$2) {
/* 163 */     if ($$1 >= $$2) {
/* 164 */       return $$1;
/*     */     }
/* 166 */     return $$0.nextFloat() * ($$2 - $$1) + $$1;
/*     */   }
/*     */   
/*     */   public static double nextDouble(RandomSource $$0, double $$1, double $$2) {
/* 170 */     if ($$1 >= $$2) {
/* 171 */       return $$1;
/*     */     }
/* 173 */     return $$0.nextDouble() * ($$2 - $$1) + $$1;
/*     */   }
/*     */   
/*     */   public static boolean equal(float $$0, float $$1) {
/* 177 */     return (Math.abs($$1 - $$0) < 1.0E-5F);
/*     */   }
/*     */   
/*     */   public static boolean equal(double $$0, double $$1) {
/* 181 */     return (Math.abs($$1 - $$0) < 9.999999747378752E-6D);
/*     */   }
/*     */   
/*     */   public static int positiveModulo(int $$0, int $$1) {
/* 185 */     return Math.floorMod($$0, $$1);
/*     */   }
/*     */   
/*     */   public static float positiveModulo(float $$0, float $$1) {
/* 189 */     return ($$0 % $$1 + $$1) % $$1;
/*     */   }
/*     */   
/*     */   public static double positiveModulo(double $$0, double $$1) {
/* 193 */     return ($$0 % $$1 + $$1) % $$1;
/*     */   }
/*     */   
/*     */   public static boolean isMultipleOf(int $$0, int $$1) {
/* 197 */     return ($$0 % $$1 == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int wrapDegrees(int $$0) {
/* 204 */     int $$1 = $$0 % 360;
/* 205 */     if ($$1 >= 180) {
/* 206 */       $$1 -= 360;
/*     */     }
/* 208 */     if ($$1 < -180) {
/* 209 */       $$1 += 360;
/*     */     }
/* 211 */     return $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float wrapDegrees(float $$0) {
/* 218 */     float $$1 = $$0 % 360.0F;
/* 219 */     if ($$1 >= 180.0F) {
/* 220 */       $$1 -= 360.0F;
/*     */     }
/* 222 */     if ($$1 < -180.0F) {
/* 223 */       $$1 += 360.0F;
/*     */     }
/* 225 */     return $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double wrapDegrees(double $$0) {
/* 232 */     double $$1 = $$0 % 360.0D;
/* 233 */     if ($$1 >= 180.0D) {
/* 234 */       $$1 -= 360.0D;
/*     */     }
/* 236 */     if ($$1 < -180.0D) {
/* 237 */       $$1 += 360.0D;
/*     */     }
/* 239 */     return $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float degreesDifference(float $$0, float $$1) {
/* 247 */     return wrapDegrees($$1 - $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float degreesDifferenceAbs(float $$0, float $$1) {
/* 255 */     return abs(degreesDifference($$0, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float rotateIfNecessary(float $$0, float $$1, float $$2) {
/* 264 */     float $$3 = degreesDifference($$0, $$1);
/* 265 */     float $$4 = clamp($$3, -$$2, $$2);
/* 266 */     return $$1 - $$4;
/*     */   }
/*     */   
/*     */   public static float approach(float $$0, float $$1, float $$2) {
/* 270 */     $$2 = abs($$2);
/*     */     
/* 272 */     if ($$0 < $$1) {
/* 273 */       return clamp($$0 + $$2, $$0, $$1);
/*     */     }
/* 275 */     return clamp($$0 - $$2, $$1, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float approachDegrees(float $$0, float $$1, float $$2) {
/* 280 */     float $$3 = degreesDifference($$0, $$1);
/* 281 */     return approach($$0, $$0 + $$3, $$2);
/*     */   }
/*     */   
/*     */   public static int getInt(String $$0, int $$1) {
/* 285 */     return NumberUtils.toInt($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int smallestEncompassingPowerOfTwo(int $$0) {
/* 290 */     int $$1 = $$0 - 1;
/* 291 */     $$1 |= $$1 >> 1;
/* 292 */     $$1 |= $$1 >> 2;
/* 293 */     $$1 |= $$1 >> 4;
/* 294 */     $$1 |= $$1 >> 8;
/* 295 */     $$1 |= $$1 >> 16;
/* 296 */     return $$1 + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isPowerOfTwo(int $$0) {
/* 301 */     return ($$0 != 0 && ($$0 & $$0 - 1) == 0);
/*     */   }
/*     */ 
/*     */   
/* 305 */   private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[] { 0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9 }; private static final double ONE_SIXTH = 0.16666666666666666D;
/*     */   private static final int FRAC_EXP = 8;
/*     */   private static final int LUT_SIZE = 257;
/*     */   
/*     */   public static int ceillog2(int $$0) {
/* 310 */     $$0 = isPowerOfTwo($$0) ? $$0 : smallestEncompassingPowerOfTwo($$0);
/* 311 */     return MULTIPLY_DE_BRUIJN_BIT_POSITION[(int)($$0 * 125613361L >> 27L) & 0x1F];
/*     */   }
/*     */   
/*     */   public static int log2(int $$0) {
/* 315 */     return ceillog2($$0) - (isPowerOfTwo($$0) ? 0 : 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int color(float $$0, float $$1, float $$2) {
/* 320 */     return FastColor.ARGB32.color(0, floor($$0 * 255.0F), floor($$1 * 255.0F), floor($$2 * 255.0F));
/*     */   }
/*     */   
/*     */   public static float frac(float $$0) {
/* 324 */     return $$0 - floor($$0);
/*     */   }
/*     */   
/*     */   public static double frac(double $$0) {
/* 328 */     return $$0 - lfloor($$0);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public static long getSeed(Vec3i $$0) {
/* 333 */     return getSeed($$0.getX(), $$0.getY(), $$0.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static long getSeed(int $$0, int $$1, int $$2) {
/* 343 */     long $$3 = ($$0 * 3129871) ^ $$2 * 116129781L ^ $$1;
/* 344 */     $$3 = $$3 * $$3 * 42317861L + $$3 * 11L;
/* 345 */     return $$3 >> 16L;
/*     */   }
/*     */   
/*     */   public static UUID createInsecureUUID(RandomSource $$0) {
/* 349 */     long $$1 = $$0.nextLong() & 0xFFFFFFFFFFFF0FFFL | 0x4000L;
/* 350 */     long $$2 = $$0.nextLong() & 0x3FFFFFFFFFFFFFFFL | Long.MIN_VALUE;
/* 351 */     return new UUID($$1, $$2);
/*     */   }
/*     */   
/*     */   public static UUID createInsecureUUID() {
/* 355 */     return createInsecureUUID(RANDOM);
/*     */   }
/*     */   
/*     */   public static double inverseLerp(double $$0, double $$1, double $$2) {
/* 359 */     return ($$0 - $$1) / ($$2 - $$1);
/*     */   }
/*     */   
/*     */   public static float inverseLerp(float $$0, float $$1, float $$2) {
/* 363 */     return ($$0 - $$1) / ($$2 - $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean rayIntersectsAABB(Vec3 $$0, Vec3 $$1, AABB $$2) {
/* 368 */     double $$3 = ($$2.minX + $$2.maxX) * 0.5D;
/* 369 */     double $$4 = ($$2.maxX - $$2.minX) * 0.5D;
/* 370 */     double $$5 = $$0.x - $$3;
/* 371 */     if (Math.abs($$5) > $$4 && $$5 * $$1.x >= 0.0D) {
/* 372 */       return false;
/*     */     }
/*     */     
/* 375 */     double $$6 = ($$2.minY + $$2.maxY) * 0.5D;
/* 376 */     double $$7 = ($$2.maxY - $$2.minY) * 0.5D;
/* 377 */     double $$8 = $$0.y - $$6;
/* 378 */     if (Math.abs($$8) > $$7 && $$8 * $$1.y >= 0.0D) {
/* 379 */       return false;
/*     */     }
/*     */     
/* 382 */     double $$9 = ($$2.minZ + $$2.maxZ) * 0.5D;
/* 383 */     double $$10 = ($$2.maxZ - $$2.minZ) * 0.5D;
/* 384 */     double $$11 = $$0.z - $$9;
/* 385 */     if (Math.abs($$11) > $$10 && $$11 * $$1.z >= 0.0D) {
/* 386 */       return false;
/*     */     }
/*     */     
/* 389 */     double $$12 = Math.abs($$1.x);
/* 390 */     double $$13 = Math.abs($$1.y);
/* 391 */     double $$14 = Math.abs($$1.z);
/*     */     
/* 393 */     double $$15 = $$1.y * $$11 - $$1.z * $$8;
/* 394 */     if (Math.abs($$15) > $$7 * $$14 + $$10 * $$13) {
/* 395 */       return false;
/*     */     }
/*     */     
/* 398 */     $$15 = $$1.z * $$5 - $$1.x * $$11;
/* 399 */     if (Math.abs($$15) > $$4 * $$14 + $$10 * $$12) {
/* 400 */       return false;
/*     */     }
/*     */     
/* 403 */     $$15 = $$1.x * $$8 - $$1.y * $$5;
/*     */     
/* 405 */     return (Math.abs($$15) < $$4 * $$13 + $$7 * $$12);
/*     */   }
/*     */   
/*     */   public static double atan2(double $$0, double $$1) {
/* 409 */     double $$2 = $$1 * $$1 + $$0 * $$0;
/*     */ 
/*     */     
/* 412 */     if (Double.isNaN($$2)) {
/* 413 */       return Double.NaN;
/*     */     }
/*     */ 
/*     */     
/* 417 */     boolean $$3 = ($$0 < 0.0D);
/* 418 */     if ($$3) {
/* 419 */       $$0 = -$$0;
/*     */     }
/* 421 */     boolean $$4 = ($$1 < 0.0D);
/* 422 */     if ($$4) {
/* 423 */       $$1 = -$$1;
/*     */     }
/* 425 */     boolean $$5 = ($$0 > $$1);
/* 426 */     if ($$5) {
/* 427 */       double $$6 = $$1;
/*     */       
/* 429 */       $$1 = $$0;
/* 430 */       $$0 = $$6;
/*     */     } 
/*     */ 
/*     */     
/* 434 */     double $$7 = fastInvSqrt($$2);
/* 435 */     $$1 *= $$7;
/* 436 */     $$0 *= $$7;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 445 */     double $$8 = FRAC_BIAS + $$0;
/* 446 */     int $$9 = (int)Double.doubleToRawLongBits($$8);
/*     */ 
/*     */     
/* 449 */     double $$10 = ASIN_TAB[$$9];
/* 450 */     double $$11 = COS_TAB[$$9];
/*     */ 
/*     */ 
/*     */     
/* 454 */     double $$12 = $$8 - FRAC_BIAS;
/* 455 */     double $$13 = $$0 * $$11 - $$1 * $$12;
/*     */ 
/*     */     
/* 458 */     double $$14 = (6.0D + $$13 * $$13) * $$13 * 0.16666666666666666D;
/* 459 */     double $$15 = $$10 + $$14;
/*     */ 
/*     */     
/* 462 */     if ($$5) {
/* 463 */       $$15 = 1.5707963267948966D - $$15;
/*     */     }
/* 465 */     if ($$4) {
/* 466 */       $$15 = Math.PI - $$15;
/*     */     }
/* 468 */     if ($$3) {
/* 469 */       $$15 = -$$15;
/*     */     }
/*     */     
/* 472 */     return $$15;
/*     */   }
/*     */   
/*     */   public static float invSqrt(float $$0) {
/* 476 */     return Math.invsqrt($$0);
/*     */   }
/*     */   
/*     */   public static double invSqrt(double $$0) {
/* 480 */     return Math.invsqrt($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static double fastInvSqrt(double $$0) {
/* 490 */     double $$1 = 0.5D * $$0;
/* 491 */     long $$2 = Double.doubleToRawLongBits($$0);
/* 492 */     $$2 = 6910469410427058090L - ($$2 >> 1L);
/* 493 */     $$0 = Double.longBitsToDouble($$2);
/* 494 */     $$0 *= 1.5D - $$1 * $$0 * $$0;
/* 495 */     return $$0;
/*     */   }
/*     */   
/*     */   public static float fastInvCubeRoot(float $$0) {
/* 499 */     int $$1 = Float.floatToIntBits($$0);
/* 500 */     $$1 = 1419967116 - $$1 / 3;
/* 501 */     float $$2 = Float.intBitsToFloat($$1);
/* 502 */     $$2 = 0.6666667F * $$2 + 1.0F / 3.0F * $$2 * $$2 * $$0;
/* 503 */     $$2 = 0.6666667F * $$2 + 1.0F / 3.0F * $$2 * $$2 * $$0;
/* 504 */     return $$2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 510 */   private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
/* 511 */   private static final double[] ASIN_TAB = new double[257];
/* 512 */   private static final double[] COS_TAB = new double[257];
/*     */ 
/*     */   
/*     */   static {
/* 516 */     for (int $$0 = 0; $$0 < 257; $$0++) {
/* 517 */       double $$1 = $$0 / 256.0D;
/* 518 */       double $$2 = Math.asin($$1);
/* 519 */       COS_TAB[$$0] = Math.cos($$2);
/* 520 */       ASIN_TAB[$$0] = $$2;
/*     */     } 
/*     */   }
/*     */   public static int hsvToRgb(float $$0, float $$1, float $$2) {
/*     */     float $$8, $$11, $$14, $$17, $$20, $$23, $$9, $$12, $$15, $$18, $$21, $$24, $$10, $$13, $$16, $$19, $$22, $$25;
/* 525 */     int $$3 = (int)($$0 * 6.0F) % 6;
/* 526 */     float $$4 = $$0 * 6.0F - $$3;
/* 527 */     float $$5 = $$2 * (1.0F - $$1);
/* 528 */     float $$6 = $$2 * (1.0F - $$4 * $$1);
/* 529 */     float $$7 = $$2 * (1.0F - (1.0F - $$4) * $$1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 535 */     switch ($$3) {
/*     */       case 0:
/* 537 */         $$8 = $$2;
/* 538 */         $$9 = $$7;
/* 539 */         $$10 = $$5;
/*     */         break;
/*     */       case 1:
/* 542 */         $$11 = $$6;
/* 543 */         $$12 = $$2;
/* 544 */         $$13 = $$5;
/*     */         break;
/*     */       case 2:
/* 547 */         $$14 = $$5;
/* 548 */         $$15 = $$2;
/* 549 */         $$16 = $$7;
/*     */         break;
/*     */       case 3:
/* 552 */         $$17 = $$5;
/* 553 */         $$18 = $$6;
/* 554 */         $$19 = $$2;
/*     */         break;
/*     */       case 4:
/* 557 */         $$20 = $$7;
/* 558 */         $$21 = $$5;
/* 559 */         $$22 = $$2;
/*     */         break;
/*     */       case 5:
/* 562 */         $$23 = $$2;
/* 563 */         $$24 = $$5;
/* 564 */         $$25 = $$6;
/*     */         break;
/*     */       default:
/* 567 */         throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + $$0 + ", " + $$1 + ", " + $$2);
/*     */     } 
/*     */     
/* 570 */     return FastColor.ARGB32.color(0, 
/* 571 */         clamp((int)($$23 * 255.0F), 0, 255), 
/* 572 */         clamp((int)($$24 * 255.0F), 0, 255), 
/* 573 */         clamp((int)($$25 * 255.0F), 0, 255));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int murmurHash3Mixer(int $$0) {
/* 579 */     $$0 ^= $$0 >>> 16;
/* 580 */     $$0 *= -2048144789;
/* 581 */     $$0 ^= $$0 >>> 13;
/* 582 */     $$0 *= -1028477387;
/* 583 */     $$0 ^= $$0 >>> 16;
/* 584 */     return $$0;
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
/*     */   public static int binarySearch(int $$0, int $$1, IntPredicate $$2) {
/* 600 */     int $$3 = $$1 - $$0;
/* 601 */     while ($$3 > 0) {
/* 602 */       int $$4 = $$3 / 2;
/* 603 */       int $$5 = $$0 + $$4;
/* 604 */       if ($$2.test($$5)) {
/*     */         
/* 606 */         $$3 = $$4; continue;
/*     */       } 
/* 608 */       $$0 = $$5 + 1;
/* 609 */       $$3 -= $$4 + 1;
/*     */     } 
/*     */     
/* 612 */     return $$0;
/*     */   }
/*     */   
/*     */   public static int lerpInt(float $$0, int $$1, int $$2) {
/* 616 */     return $$1 + floor($$0 * ($$2 - $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public static int lerpDiscrete(float $$0, int $$1, int $$2) {
/* 621 */     int $$3 = $$2 - $$1;
/* 622 */     return $$1 + floor($$0 * ($$3 - 1)) + (($$0 > 0.0F) ? 1 : 0);
/*     */   }
/*     */   
/*     */   public static float lerp(float $$0, float $$1, float $$2) {
/* 626 */     return $$1 + $$0 * ($$2 - $$1);
/*     */   }
/*     */   
/*     */   public static double lerp(double $$0, double $$1, double $$2) {
/* 630 */     return $$1 + $$0 * ($$2 - $$1);
/*     */   }
/*     */   
/*     */   public static double lerp2(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
/* 634 */     return lerp($$1, 
/*     */         
/* 636 */         lerp($$0, $$2, $$3), 
/* 637 */         lerp($$0, $$4, $$5));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double lerp3(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7, double $$8, double $$9, double $$10) {
/* 646 */     return lerp($$2, 
/*     */         
/* 648 */         lerp2($$0, $$1, $$3, $$4, $$5, $$6), 
/* 649 */         lerp2($$0, $$1, $$7, $$8, $$9, $$10));
/*     */   }
/*     */ 
/*     */   
/*     */   public static float catmullrom(float $$0, float $$1, float $$2, float $$3, float $$4) {
/* 654 */     return 0.5F * (2.0F * $$2 + ($$3 - $$1) * $$0 + (2.0F * $$1 - 5.0F * $$2 + 4.0F * $$3 - $$4) * $$0 * $$0 + (3.0F * $$2 - $$1 - 3.0F * $$3 + $$4) * $$0 * $$0 * $$0);
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
/*     */   public static double smoothstep(double $$0) {
/* 667 */     return $$0 * $$0 * $$0 * ($$0 * ($$0 * 6.0D - 15.0D) + 10.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double smoothstepDerivative(double $$0) {
/* 672 */     return 30.0D * $$0 * $$0 * ($$0 - 1.0D) * ($$0 - 1.0D);
/*     */   }
/*     */   
/*     */   public static int sign(double $$0) {
/* 676 */     if ($$0 == 0.0D) {
/* 677 */       return 0;
/*     */     }
/* 679 */     return ($$0 > 0.0D) ? 1 : -1;
/*     */   }
/*     */   
/*     */   public static float rotLerp(float $$0, float $$1, float $$2) {
/* 683 */     return $$1 + $$0 * wrapDegrees($$2 - $$1);
/*     */   }
/*     */   
/*     */   public static double rotLerp(double $$0, double $$1, double $$2) {
/* 687 */     return $$1 + $$0 * wrapDegrees($$2 - $$1);
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
/*     */   public static float triangleWave(float $$0, float $$1) {
/* 699 */     return (Math.abs($$0 % $$1 - $$1 * 0.5F) - $$1 * 0.25F) / $$1 * 0.25F;
/*     */   }
/*     */   
/*     */   public static float square(float $$0) {
/* 703 */     return $$0 * $$0;
/*     */   }
/*     */   
/*     */   public static double square(double $$0) {
/* 707 */     return $$0 * $$0;
/*     */   }
/*     */   
/*     */   public static int square(int $$0) {
/* 711 */     return $$0 * $$0;
/*     */   }
/*     */   
/*     */   public static long square(long $$0) {
/* 715 */     return $$0 * $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double clampedMap(double $$0, double $$1, double $$2, double $$3, double $$4) {
/* 723 */     return clampedLerp($$3, $$4, inverseLerp($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   public static float clampedMap(float $$0, float $$1, float $$2, float $$3, float $$4) {
/* 727 */     return clampedLerp($$3, $$4, inverseLerp($$0, $$1, $$2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double map(double $$0, double $$1, double $$2, double $$3, double $$4) {
/* 734 */     return lerp(inverseLerp($$0, $$1, $$2), $$3, $$4);
/*     */   }
/*     */   
/*     */   public static float map(float $$0, float $$1, float $$2, float $$3, float $$4) {
/* 738 */     return lerp(inverseLerp($$0, $$1, $$2), $$3, $$4);
/*     */   }
/*     */   
/*     */   public static double wobble(double $$0) {
/* 742 */     return $$0 + (2.0D * RandomSource.create(floor($$0 * 3000.0D)).nextDouble() - 1.0D) * 1.0E-7D / 2.0D;
/*     */   }
/*     */   
/*     */   public static int roundToward(int $$0, int $$1) {
/* 746 */     return positiveCeilDiv($$0, $$1) * $$1;
/*     */   }
/*     */   
/*     */   public static int positiveCeilDiv(int $$0, int $$1) {
/* 750 */     return -Math.floorDiv(-$$0, $$1);
/*     */   }
/*     */   
/*     */   public static int randomBetweenInclusive(RandomSource $$0, int $$1, int $$2) {
/* 754 */     return $$0.nextInt($$2 - $$1 + 1) + $$1;
/*     */   }
/*     */   
/*     */   public static float randomBetween(RandomSource $$0, float $$1, float $$2) {
/* 758 */     return $$0.nextFloat() * ($$2 - $$1) + $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float normal(RandomSource $$0, float $$1, float $$2) {
/* 765 */     return $$1 + (float)$$0.nextGaussian() * $$2;
/*     */   }
/*     */   
/*     */   public static double lengthSquared(double $$0, double $$1) {
/* 769 */     return $$0 * $$0 + $$1 * $$1;
/*     */   }
/*     */   
/*     */   public static double length(double $$0, double $$1) {
/* 773 */     return Math.sqrt(lengthSquared($$0, $$1));
/*     */   }
/*     */   
/*     */   public static double lengthSquared(double $$0, double $$1, double $$2) {
/* 777 */     return $$0 * $$0 + $$1 * $$1 + $$2 * $$2;
/*     */   }
/*     */   
/*     */   public static double length(double $$0, double $$1, double $$2) {
/* 781 */     return Math.sqrt(lengthSquared($$0, $$1, $$2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int quantize(double $$0, int $$1) {
/* 788 */     return floor($$0 / $$1) * $$1;
/*     */   }
/*     */   
/*     */   public static IntStream outFromOrigin(int $$0, int $$1, int $$2) {
/* 792 */     return outFromOrigin($$0, $$1, $$2, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IntStream outFromOrigin(int $$0, int $$1, int $$2, int $$3) {
/* 800 */     if ($$1 > $$2) {
/* 801 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "upperbound %d expected to be > lowerBound %d", new Object[] { Integer.valueOf($$2), Integer.valueOf($$1) }));
/*     */     }
/*     */     
/* 804 */     if ($$3 < 1) {
/* 805 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "steps expected to be >= 1, was %d", new Object[] { Integer.valueOf($$3) }));
/*     */     }
/*     */     
/* 808 */     if ($$0 < $$1 || $$0 > $$2) {
/* 809 */       return IntStream.empty();
/*     */     }
/*     */     
/* 812 */     return IntStream.iterate($$0, $$3 -> {
/*     */           int $$4 = Math.abs($$0 - $$3);
/* 814 */           return ($$0 - $$4 >= $$1 || $$0 + $$4 <= $$2);
/*     */         }$$4 -> {
/*     */           boolean $$5 = ($$4 <= $$0);
/*     */           int $$6 = Math.abs($$0 - $$4);
/*     */           boolean $$7 = ($$0 + $$6 + $$1 <= $$2);
/*     */           if (!$$5 || !$$7) {
/*     */             int $$8 = $$0 - $$6 - ($$5 ? $$1 : 0);
/*     */             if ($$8 >= $$3) {
/*     */               return $$8;
/*     */             }
/*     */           } 
/*     */           return $$0 + $$6 + $$1;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Quaternionf rotationAroundAxis(Vector3f $$0, Quaternionf $$1, Quaternionf $$2) {
/* 832 */     float $$3 = $$0.dot($$1.x, $$1.y, $$1.z);
/* 833 */     return $$2.set($$0.x * $$3, $$0.y * $$3, $$0.z * $$3, $$1.w).normalize();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\Mth.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */