/*    */ package net.minecraft.world.effect;
/*    */ 
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ class AbsorptionMobEffect extends MobEffect {
/*    */   protected AbsorptionMobEffect(MobEffectCategory $$0, int $$1) {
/*  7 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyEffectTick(LivingEntity $$0, int $$1) {
/* 12 */     super.applyEffectTick($$0, $$1);
/* 13 */     if ($$0.getAbsorptionAmount() <= 0.0F && !($$0.level()).isClientSide) {
/* 14 */       $$0.removeEffect(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldApplyEffectTickThisTick(int $$0, int $$1) {
/* 20 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEffectStarted(LivingEntity $$0, int $$1) {
/* 25 */     super.onEffectStarted($$0, $$1);
/* 26 */     $$0.setAbsorptionAmount(Math.max($$0.getAbsorptionAmount(), (4 * (1 + $$1))));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\AbsorptionMobEffect.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */