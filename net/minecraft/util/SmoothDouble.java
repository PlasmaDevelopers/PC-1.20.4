/*    */ package net.minecraft.util;
/*    */ 
/*    */ public class SmoothDouble {
/*    */   private double targetValue;
/*    */   private double remainingValue;
/*    */   private double lastAmount;
/*    */   
/*    */   public double getNewDeltaValue(double $$0, double $$1) {
/*  9 */     this.targetValue += $$0;
/*    */     
/* 11 */     double $$2 = this.targetValue - this.remainingValue;
/*    */     
/* 13 */     double $$3 = Mth.lerp(0.5D, this.lastAmount, $$2);
/*    */ 
/*    */     
/* 16 */     double $$4 = Math.signum($$2);
/* 17 */     if ($$4 * $$2 > $$4 * this.lastAmount) {
/* 18 */       $$2 = $$3;
/*    */     }
/*    */     
/* 21 */     this.lastAmount = $$3;
/* 22 */     this.remainingValue += $$2 * $$1;
/*    */     
/* 24 */     return $$2 * $$1;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 28 */     this.targetValue = 0.0D;
/* 29 */     this.remainingValue = 0.0D;
/* 30 */     this.lastAmount = 0.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\SmoothDouble.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */