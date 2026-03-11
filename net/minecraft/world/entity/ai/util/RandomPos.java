/*     */ package net.minecraft.world.entity.ai.util;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class RandomPos
/*     */ {
/*     */   public static BlockPos generateRandomDirection(RandomSource $$0, int $$1, int $$2) {
/*  19 */     int $$3 = $$0.nextInt(2 * $$1 + 1) - $$1;
/*  20 */     int $$4 = $$0.nextInt(2 * $$2 + 1) - $$2;
/*  21 */     int $$5 = $$0.nextInt(2 * $$1 + 1) - $$1;
/*     */     
/*  23 */     return new BlockPos($$3, $$4, $$5);
/*     */   }
/*     */   private static final int RANDOM_POS_ATTEMPTS = 10;
/*     */   @Nullable
/*     */   public static BlockPos generateRandomDirectionWithinRadians(RandomSource $$0, int $$1, int $$2, int $$3, double $$4, double $$5, double $$6) {
/*  28 */     double $$7 = Mth.atan2($$5, $$4) - 1.5707963705062866D;
/*  29 */     double $$8 = $$7 + (2.0F * $$0.nextFloat() - 1.0F) * $$6;
/*  30 */     double $$9 = Math.sqrt($$0.nextDouble()) * Mth.SQRT_OF_TWO * $$1;
/*  31 */     double $$10 = -$$9 * Math.sin($$8);
/*  32 */     double $$11 = $$9 * Math.cos($$8);
/*     */     
/*  34 */     if (Math.abs($$10) > $$1 || Math.abs($$11) > $$1) {
/*  35 */       return null;
/*     */     }
/*     */     
/*  38 */     int $$12 = $$0.nextInt(2 * $$2 + 1) - $$2 + $$3;
/*  39 */     return BlockPos.containing($$10, $$12, $$11);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public static BlockPos moveUpOutOfSolid(BlockPos $$0, int $$1, Predicate<BlockPos> $$2) {
/*  44 */     if ($$2.test($$0)) {
/*     */       
/*  46 */       BlockPos $$3 = $$0.above();
/*  47 */       while ($$3.getY() < $$1 && $$2.test($$3)) {
/*  48 */         $$3 = $$3.above();
/*     */       }
/*     */       
/*  51 */       return $$3;
/*     */     } 
/*     */     
/*  54 */     return $$0;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public static BlockPos moveUpToAboveSolid(BlockPos $$0, int $$1, int $$2, Predicate<BlockPos> $$3) {
/*  59 */     if ($$1 < 0) {
/*  60 */       throw new IllegalArgumentException("aboveSolidAmount was " + $$1 + ", expected >= 0");
/*     */     }
/*     */     
/*  63 */     if ($$3.test($$0)) {
/*     */       
/*  65 */       BlockPos $$4 = $$0.above();
/*  66 */       while ($$4.getY() < $$2 && $$3.test($$4)) {
/*  67 */         $$4 = $$4.above();
/*     */       }
/*     */ 
/*     */       
/*  71 */       BlockPos $$5 = $$4;
/*  72 */       while ($$5.getY() < $$2 && $$5.getY() - $$4.getY() < $$1) {
/*  73 */         BlockPos $$6 = $$5.above();
/*  74 */         if ($$3.test($$6)) {
/*     */           break;
/*     */         }
/*  77 */         $$5 = $$6;
/*     */       } 
/*     */       
/*  80 */       return $$5;
/*     */     } 
/*     */     
/*  83 */     return $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Vec3 generateRandomPos(PathfinderMob $$0, Supplier<BlockPos> $$1) {
/*  88 */     Objects.requireNonNull($$0); return generateRandomPos($$1, $$0::getWalkTargetValue);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Vec3 generateRandomPos(Supplier<BlockPos> $$0, ToDoubleFunction<BlockPos> $$1) {
/*  93 */     double $$2 = Double.NEGATIVE_INFINITY;
/*  94 */     BlockPos $$3 = null;
/*     */     
/*  96 */     for (int $$4 = 0; $$4 < 10; $$4++) {
/*  97 */       BlockPos $$5 = $$0.get();
/*  98 */       if ($$5 != null) {
/*     */ 
/*     */ 
/*     */         
/* 102 */         double $$6 = $$1.applyAsDouble($$5);
/* 103 */         if ($$6 > $$2) {
/* 104 */           $$2 = $$6;
/* 105 */           $$3 = $$5;
/*     */         } 
/*     */       } 
/*     */     } 
/* 109 */     return ($$3 != null) ? Vec3.atBottomCenterOf((Vec3i)$$3) : null;
/*     */   }
/*     */   
/*     */   public static BlockPos generateRandomPosTowardDirection(PathfinderMob $$0, int $$1, RandomSource $$2, BlockPos $$3) {
/* 113 */     int $$4 = $$3.getX();
/* 114 */     int $$5 = $$3.getZ();
/*     */     
/* 116 */     if ($$0.hasRestriction() && $$1 > 1) {
/* 117 */       BlockPos $$6 = $$0.getRestrictCenter();
/*     */       
/* 119 */       if ($$0.getX() > $$6.getX()) {
/* 120 */         $$4 -= $$2.nextInt($$1 / 2);
/*     */       } else {
/* 122 */         $$4 += $$2.nextInt($$1 / 2);
/*     */       } 
/*     */       
/* 125 */       if ($$0.getZ() > $$6.getZ()) {
/* 126 */         $$5 -= $$2.nextInt($$1 / 2);
/*     */       } else {
/* 128 */         $$5 += $$2.nextInt($$1 / 2);
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     return BlockPos.containing($$4 + $$0.getX(), $$3.getY() + $$0.getY(), $$5 + $$0.getZ());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\a\\util\RandomPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */