/*    */ package net.minecraft.world;
/*    */ 
/*    */ import javax.annotation.concurrent.Immutable;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Immutable
/*    */ public class DifficultyInstance
/*    */ {
/*    */   private static final float DIFFICULTY_TIME_GLOBAL_OFFSET = -72000.0F;
/*    */   private static final float MAX_DIFFICULTY_TIME_GLOBAL = 1440000.0F;
/*    */   private static final float MAX_DIFFICULTY_TIME_LOCAL = 3600000.0F;
/*    */   private final Difficulty base;
/*    */   private final float effectiveDifficulty;
/*    */   
/*    */   public DifficultyInstance(Difficulty $$0, long $$1, long $$2, float $$3) {
/* 22 */     this.base = $$0;
/* 23 */     this.effectiveDifficulty = calculateDifficulty($$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public Difficulty getDifficulty() {
/* 27 */     return this.base;
/*    */   }
/*    */   
/*    */   public float getEffectiveDifficulty() {
/* 31 */     return this.effectiveDifficulty;
/*    */   }
/*    */   
/*    */   public boolean isHard() {
/* 35 */     return (this.effectiveDifficulty >= Difficulty.HARD.ordinal());
/*    */   }
/*    */   
/*    */   public boolean isHarderThan(float $$0) {
/* 39 */     return (this.effectiveDifficulty > $$0);
/*    */   }
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
/*    */   public float getSpecialMultiplier() {
/* 52 */     if (this.effectiveDifficulty < 2.0F) {
/* 53 */       return 0.0F;
/*    */     }
/* 55 */     if (this.effectiveDifficulty > 4.0F) {
/* 56 */       return 1.0F;
/*    */     }
/* 58 */     return (this.effectiveDifficulty - 2.0F) / 2.0F;
/*    */   }
/*    */   
/*    */   private float calculateDifficulty(Difficulty $$0, long $$1, long $$2, float $$3) {
/* 62 */     if ($$0 == Difficulty.PEACEFUL) {
/* 63 */       return 0.0F;
/*    */     }
/*    */     
/* 66 */     boolean $$4 = ($$0 == Difficulty.HARD);
/* 67 */     float $$5 = 0.75F;
/*    */ 
/*    */     
/* 70 */     float $$6 = Mth.clamp(((float)$$1 + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
/* 71 */     $$5 += $$6;
/*    */     
/* 73 */     float $$7 = 0.0F;
/*    */ 
/*    */     
/* 76 */     $$7 += Mth.clamp((float)$$2 / 3600000.0F, 0.0F, 1.0F) * ($$4 ? 1.0F : 0.75F);
/* 77 */     $$7 += Mth.clamp($$3 * 0.25F, 0.0F, $$6);
/*    */     
/* 79 */     if ($$0 == Difficulty.EASY) {
/* 80 */       $$7 *= 0.5F;
/*    */     }
/* 82 */     $$5 += $$7;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 87 */     return $$0.getId() * $$5;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\DifficultyInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */