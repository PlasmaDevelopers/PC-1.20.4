/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.UniformInt;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Ticker
/*    */ {
/*    */   private final UniformInt interval;
/*    */   private int ticksUntilNextStart;
/*    */   
/*    */   public Ticker(UniformInt $$0) {
/* 56 */     if ($$0.getMinValue() <= 1) {
/* 57 */       throw new IllegalArgumentException();
/*    */     }
/* 59 */     this.interval = $$0;
/*    */   }
/*    */   
/*    */   public boolean tickDownAndCheck(RandomSource $$0) {
/* 63 */     if (this.ticksUntilNextStart == 0) {
/* 64 */       this.ticksUntilNextStart = this.interval.sample($$0) - 1;
/* 65 */       return false;
/*    */     } 
/*    */     
/* 68 */     return (--this.ticksUntilNextStart == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\SetEntityLookTargetSometimes$Ticker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */