/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.core.Direction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SegmentedAnglePrecision
/*    */ {
/*    */   private final int mask;
/*    */   private final int precision;
/*    */   private final float degreeToAngle;
/*    */   private final float angleToDegree;
/*    */   
/*    */   public SegmentedAnglePrecision(int $$0) {
/* 24 */     if ($$0 < 2) {
/* 25 */       throw new IllegalArgumentException("Precision cannot be less than 2 bits");
/*    */     }
/* 27 */     if ($$0 > 30) {
/* 28 */       throw new IllegalArgumentException("Precision cannot be greater than 30 bits");
/*    */     }
/*    */     
/* 31 */     int $$1 = 1 << $$0;
/* 32 */     this.mask = $$1 - 1;
/* 33 */     this.precision = $$0;
/* 34 */     this.degreeToAngle = $$1 / 360.0F;
/* 35 */     this.angleToDegree = 360.0F / $$1;
/*    */   }
/*    */   
/*    */   public boolean isSameAxis(int $$0, int $$1) {
/* 39 */     int $$2 = getMask() >> 1;
/*    */     
/* 41 */     return (($$0 & $$2) == ($$1 & $$2));
/*    */   }
/*    */   
/*    */   public int fromDirection(Direction $$0) {
/* 45 */     if ($$0.getAxis().isVertical()) {
/* 46 */       return 0;
/*    */     }
/* 48 */     int $$1 = $$0.get2DDataValue();
/* 49 */     return $$1 << this.precision - 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int fromDegreesWithTurns(float $$0) {
/* 58 */     return Math.round($$0 * this.degreeToAngle);
/*    */   }
/*    */   
/*    */   public int fromDegrees(float $$0) {
/* 62 */     return normalize(fromDegreesWithTurns($$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float toDegreesWithTurns(int $$0) {
/* 71 */     return $$0 * this.angleToDegree;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float toDegrees(int $$0) {
/* 80 */     float $$1 = toDegreesWithTurns(normalize($$0));
/* 81 */     return ($$1 >= 180.0F) ? ($$1 - 360.0F) : $$1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int normalize(int $$0) {
/* 90 */     return $$0 & this.mask;
/*    */   }
/*    */   
/*    */   public int getMask() {
/* 94 */     return this.mask;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\SegmentedAnglePrecision.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */