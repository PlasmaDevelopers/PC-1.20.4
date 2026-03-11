/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PointCharge
/*    */ {
/*    */   private final BlockPos pos;
/*    */   private final double charge;
/*    */   
/*    */   public PointCharge(BlockPos $$0, double $$1) {
/* 15 */     this.pos = $$0;
/* 16 */     this.charge = $$1;
/*    */   }
/*    */   
/*    */   public double getPotentialChange(BlockPos $$0) {
/* 20 */     double $$1 = this.pos.distSqr((Vec3i)$$0);
/* 21 */     if ($$1 == 0.0D)
/*    */     {
/* 23 */       return Double.POSITIVE_INFINITY;
/*    */     }
/* 25 */     return this.charge / Math.sqrt($$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\PotentialCalculator$PointCharge.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */