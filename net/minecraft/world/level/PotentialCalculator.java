/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ 
/*    */ public class PotentialCalculator
/*    */ {
/*    */   private static class PointCharge {
/*    */     private final BlockPos pos;
/*    */     private final double charge;
/*    */     
/*    */     public PointCharge(BlockPos $$0, double $$1) {
/* 15 */       this.pos = $$0;
/* 16 */       this.charge = $$1;
/*    */     }
/*    */     
/*    */     public double getPotentialChange(BlockPos $$0) {
/* 20 */       double $$1 = this.pos.distSqr((Vec3i)$$0);
/* 21 */       if ($$1 == 0.0D)
/*    */       {
/* 23 */         return Double.POSITIVE_INFINITY;
/*    */       }
/* 25 */       return this.charge / Math.sqrt($$1);
/*    */     }
/*    */   }
/*    */   
/* 29 */   private final List<PointCharge> charges = Lists.newArrayList();
/*    */   
/*    */   public void addCharge(BlockPos $$0, double $$1) {
/* 32 */     if ($$1 != 0.0D) {
/* 33 */       this.charges.add(new PointCharge($$0, $$1));
/*    */     }
/*    */   }
/*    */   
/*    */   public double getPotentialEnergyChange(BlockPos $$0, double $$1) {
/* 38 */     if ($$1 == 0.0D) {
/* 39 */       return 0.0D;
/*    */     }
/* 41 */     double $$2 = 0.0D;
/* 42 */     for (PointCharge $$3 : this.charges) {
/* 43 */       $$2 += $$3.getPotentialChange($$0);
/*    */     }
/* 45 */     return $$2 * $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\PotentialCalculator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */