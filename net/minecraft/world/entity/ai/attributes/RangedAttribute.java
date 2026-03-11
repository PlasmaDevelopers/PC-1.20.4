/*    */ package net.minecraft.world.entity.ai.attributes;
/*    */ 
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class RangedAttribute extends Attribute {
/*    */   private final double minValue;
/*    */   private final double maxValue;
/*    */   
/*    */   public RangedAttribute(String $$0, double $$1, double $$2, double $$3) {
/* 10 */     super($$0, $$1);
/* 11 */     this.minValue = $$2;
/* 12 */     this.maxValue = $$3;
/*    */     
/* 14 */     if ($$2 > $$3) {
/* 15 */       throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
/*    */     }
/* 17 */     if ($$1 < $$2) {
/* 18 */       throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
/*    */     }
/* 20 */     if ($$1 > $$3) {
/* 21 */       throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
/*    */     }
/*    */   }
/*    */   
/*    */   public double getMinValue() {
/* 26 */     return this.minValue;
/*    */   }
/*    */   
/*    */   public double getMaxValue() {
/* 30 */     return this.maxValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public double sanitizeValue(double $$0) {
/* 35 */     if (Double.isNaN($$0)) {
/* 36 */       return this.minValue;
/*    */     }
/* 38 */     return Mth.clamp($$0, this.minValue, this.maxValue);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\attributes\RangedAttribute.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */