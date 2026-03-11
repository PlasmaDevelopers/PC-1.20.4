/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class MarsagliaPolarGaussian
/*    */ {
/*    */   public final RandomSource randomSource;
/*    */   private double nextNextGaussian;
/*    */   private boolean haveNextNextGaussian;
/*    */   
/*    */   public MarsagliaPolarGaussian(RandomSource $$0) {
/* 13 */     this.randomSource = $$0;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 17 */     this.haveNextNextGaussian = false;
/*    */   }
/*    */   
/*    */   public double nextGaussian() {
/*    */     double $$0, $$1, $$2;
/* 22 */     if (this.haveNextNextGaussian) {
/* 23 */       this.haveNextNextGaussian = false;
/* 24 */       return this.nextNextGaussian;
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     do {
/* 33 */       $$0 = 2.0D * this.randomSource.nextDouble() - 1.0D;
/* 34 */       $$1 = 2.0D * this.randomSource.nextDouble() - 1.0D;
/* 35 */       $$2 = Mth.square($$0) + Mth.square($$1);
/* 36 */     } while ($$2 >= 1.0D || $$2 == 0.0D);
/*    */     
/* 38 */     double $$3 = Math.sqrt(-2.0D * Math.log($$2) / $$2);
/*    */     
/* 40 */     this.nextNextGaussian = $$1 * $$3;
/* 41 */     this.haveNextNextGaussian = true;
/* 42 */     return $$0 * $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\MarsagliaPolarGaussian.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */