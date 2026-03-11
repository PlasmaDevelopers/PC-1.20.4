/*    */ package net.minecraft.world.effect;
/*    */ 
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ class WitherMobEffect extends MobEffect {
/*    */   protected WitherMobEffect(MobEffectCategory $$0, int $$1) {
/*  7 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyEffectTick(LivingEntity $$0, int $$1) {
/* 12 */     super.applyEffectTick($$0, $$1);
/* 13 */     $$0.hurt($$0.damageSources().wither(), 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldApplyEffectTickThisTick(int $$0, int $$1) {
/* 18 */     int $$2 = 40 >> $$1;
/* 19 */     if ($$2 > 0) {
/* 20 */       return ($$0 % $$2 == 0);
/*    */     }
/* 22 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\WitherMobEffect.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */