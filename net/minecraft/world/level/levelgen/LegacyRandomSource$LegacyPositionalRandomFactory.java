/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LegacyPositionalRandomFactory
/*    */   implements PositionalRandomFactory
/*    */ {
/*    */   private final long seed;
/*    */   
/*    */   public LegacyPositionalRandomFactory(long $$0) {
/* 64 */     this.seed = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public RandomSource at(int $$0, int $$1, int $$2) {
/* 69 */     long $$3 = Mth.getSeed($$0, $$1, $$2);
/* 70 */     long $$4 = $$3 ^ this.seed;
/* 71 */     return new LegacyRandomSource($$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public RandomSource fromHashOf(String $$0) {
/* 76 */     int $$1 = $$0.hashCode();
/* 77 */     return new LegacyRandomSource($$1 ^ this.seed);
/*    */   }
/*    */ 
/*    */   
/*    */   @VisibleForTesting
/*    */   public void parityConfigString(StringBuilder $$0) {
/* 83 */     $$0.append("LegacyPositionalRandomFactory{").append(this.seed).append("}");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\LegacyRandomSource$LegacyPositionalRandomFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */