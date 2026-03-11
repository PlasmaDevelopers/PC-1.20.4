/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.util.valueproviders.UniformInt;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class ParticleUtils {
/*    */   public static void spawnParticlesOnBlockFaces(Level $$0, BlockPos $$1, ParticleOptions $$2, IntProvider $$3) {
/* 15 */     for (Direction $$4 : Direction.values()) {
/* 16 */       spawnParticlesOnBlockFace($$0, $$1, $$2, $$3, $$4, () -> getRandomSpeedRanges($$0.random), 0.55D);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void spawnParticlesOnBlockFace(Level $$0, BlockPos $$1, ParticleOptions $$2, IntProvider $$3, Direction $$4, Supplier<Vec3> $$5, double $$6) {
/* 21 */     int $$7 = $$3.sample($$0.random);
/* 22 */     for (int $$8 = 0; $$8 < $$7; $$8++) {
/* 23 */       spawnParticleOnFace($$0, $$1, $$4, $$2, $$5.get(), $$6);
/*    */     }
/*    */   }
/*    */   
/*    */   private static Vec3 getRandomSpeedRanges(RandomSource $$0) {
/* 28 */     return new Vec3(Mth.nextDouble($$0, -0.5D, 0.5D), Mth.nextDouble($$0, -0.5D, 0.5D), Mth.nextDouble($$0, -0.5D, 0.5D));
/*    */   }
/*    */   
/*    */   public static void spawnParticlesAlongAxis(Direction.Axis $$0, Level $$1, BlockPos $$2, double $$3, ParticleOptions $$4, UniformInt $$5) {
/* 32 */     Vec3 $$6 = Vec3.atCenterOf((Vec3i)$$2);
/*    */     
/* 34 */     boolean $$7 = ($$0 == Direction.Axis.X);
/* 35 */     boolean $$8 = ($$0 == Direction.Axis.Y);
/* 36 */     boolean $$9 = ($$0 == Direction.Axis.Z);
/*    */     
/* 38 */     int $$10 = $$5.sample($$1.random);
/* 39 */     for (int $$11 = 0; $$11 < $$10; $$11++) {
/* 40 */       double $$12 = $$6.x + Mth.nextDouble($$1.random, -1.0D, 1.0D) * ($$7 ? 0.5D : $$3);
/* 41 */       double $$13 = $$6.y + Mth.nextDouble($$1.random, -1.0D, 1.0D) * ($$8 ? 0.5D : $$3);
/* 42 */       double $$14 = $$6.z + Mth.nextDouble($$1.random, -1.0D, 1.0D) * ($$9 ? 0.5D : $$3);
/* 43 */       double $$15 = $$7 ? Mth.nextDouble($$1.random, -1.0D, 1.0D) : 0.0D;
/* 44 */       double $$16 = $$8 ? Mth.nextDouble($$1.random, -1.0D, 1.0D) : 0.0D;
/* 45 */       double $$17 = $$9 ? Mth.nextDouble($$1.random, -1.0D, 1.0D) : 0.0D;
/*    */       
/* 47 */       $$1.addParticle($$4, $$12, $$13, $$14, $$15, $$16, $$17);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void spawnParticleOnFace(Level $$0, BlockPos $$1, Direction $$2, ParticleOptions $$3, Vec3 $$4, double $$5) {
/* 52 */     Vec3 $$6 = Vec3.atCenterOf((Vec3i)$$1);
/* 53 */     int $$7 = $$2.getStepX();
/* 54 */     int $$8 = $$2.getStepY();
/* 55 */     int $$9 = $$2.getStepZ();
/* 56 */     double $$10 = $$6.x + (($$7 == 0) ? Mth.nextDouble($$0.random, -0.5D, 0.5D) : ($$7 * $$5));
/* 57 */     double $$11 = $$6.y + (($$8 == 0) ? Mth.nextDouble($$0.random, -0.5D, 0.5D) : ($$8 * $$5));
/* 58 */     double $$12 = $$6.z + (($$9 == 0) ? Mth.nextDouble($$0.random, -0.5D, 0.5D) : ($$9 * $$5));
/* 59 */     double $$13 = ($$7 == 0) ? $$4.x() : 0.0D;
/* 60 */     double $$14 = ($$8 == 0) ? $$4.y() : 0.0D;
/* 61 */     double $$15 = ($$9 == 0) ? $$4.z() : 0.0D;
/*    */     
/* 63 */     $$0.addParticle($$3, $$10, $$11, $$12, $$13, $$14, $$15);
/*    */   }
/*    */   
/*    */   public static void spawnParticleBelow(Level $$0, BlockPos $$1, RandomSource $$2, ParticleOptions $$3) {
/* 67 */     double $$4 = $$1.getX() + $$2.nextDouble();
/* 68 */     double $$5 = $$1.getY() - 0.05D;
/* 69 */     double $$6 = $$1.getZ() + $$2.nextDouble();
/*    */     
/* 71 */     $$0.addParticle($$3, $$4, $$5, $$6, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ParticleUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */