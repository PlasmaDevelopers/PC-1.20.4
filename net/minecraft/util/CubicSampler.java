/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class CubicSampler {
/*    */   private static final int GAUSSIAN_SAMPLE_RADIUS = 2;
/*    */   private static final int GAUSSIAN_SAMPLE_BREADTH = 6;
/*  8 */   private static final double[] GAUSSIAN_SAMPLE_KERNEL = new double[] { 0.0D, 1.0D, 4.0D, 6.0D, 4.0D, 1.0D, 0.0D };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Vec3 gaussianSampleVec3(Vec3 $$0, Vec3Fetcher $$1) {
/* 14 */     int $$2 = Mth.floor($$0.x());
/* 15 */     int $$3 = Mth.floor($$0.y());
/* 16 */     int $$4 = Mth.floor($$0.z());
/*    */     
/* 18 */     double $$5 = $$0.x() - $$2;
/* 19 */     double $$6 = $$0.y() - $$3;
/* 20 */     double $$7 = $$0.z() - $$4;
/*    */     
/* 22 */     double $$8 = 0.0D;
/* 23 */     Vec3 $$9 = Vec3.ZERO;
/* 24 */     for (int $$10 = 0; $$10 < 6; $$10++) {
/* 25 */       double $$11 = Mth.lerp($$5, GAUSSIAN_SAMPLE_KERNEL[$$10 + 1], GAUSSIAN_SAMPLE_KERNEL[$$10]);
/* 26 */       int $$12 = $$2 - 2 + $$10;
/*    */       
/* 28 */       for (int $$13 = 0; $$13 < 6; $$13++) {
/* 29 */         double $$14 = Mth.lerp($$6, GAUSSIAN_SAMPLE_KERNEL[$$13 + 1], GAUSSIAN_SAMPLE_KERNEL[$$13]);
/* 30 */         int $$15 = $$3 - 2 + $$13;
/*    */         
/* 32 */         for (int $$16 = 0; $$16 < 6; $$16++) {
/* 33 */           double $$17 = Mth.lerp($$7, GAUSSIAN_SAMPLE_KERNEL[$$16 + 1], GAUSSIAN_SAMPLE_KERNEL[$$16]);
/* 34 */           int $$18 = $$4 - 2 + $$16;
/*    */           
/* 36 */           double $$19 = $$11 * $$14 * $$17;
/* 37 */           $$8 += $$19;
/*    */           
/* 39 */           $$9 = $$9.add($$1.fetch($$12, $$15, $$18).scale($$19));
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     $$9 = $$9.scale(1.0D / $$8);
/* 45 */     return $$9;
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface Vec3Fetcher {
/*    */     Vec3 fetch(int param1Int1, int param1Int2, int param1Int3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\CubicSampler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */