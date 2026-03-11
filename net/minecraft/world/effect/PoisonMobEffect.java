/*    */ package net.minecraft.world.effect;
/*    */ 
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ class PoisonMobEffect extends MobEffect {
/*    */   protected PoisonMobEffect(MobEffectCategory $$0, int $$1) {
/*  7 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyEffectTick(LivingEntity $$0, int $$1) {
/* 12 */     super.applyEffectTick($$0, $$1);
/* 13 */     if ($$0.getHealth() > 1.0F) {
/* 14 */       $$0.hurt($$0.damageSources().magic(), 1.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldApplyEffectTickThisTick(int $$0, int $$1) {
/* 20 */     int $$2 = 25 >> $$1;
/* 21 */     if ($$2 > 0) {
/* 22 */       return ($$0 % $$2 == 0);
/*    */     }
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\PoisonMobEffect.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */