/*    */ package net.minecraft.world.effect;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ class HealOrHarmMobEffect
/*    */   extends InstantenousMobEffect {
/*    */   private final boolean isHarm;
/*    */   
/*    */   public HealOrHarmMobEffect(MobEffectCategory $$0, int $$1, boolean $$2) {
/* 12 */     super($$0, $$1);
/* 13 */     this.isHarm = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyEffectTick(LivingEntity $$0, int $$1) {
/* 18 */     super.applyEffectTick($$0, $$1);
/* 19 */     if (this.isHarm == $$0.isInvertedHealAndHarm()) {
/* 20 */       $$0.heal(Math.max(4 << $$1, 0));
/*    */     } else {
/* 22 */       $$0.hurt($$0.damageSources().magic(), (6 << $$1));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyInstantenousEffect(@Nullable Entity $$0, @Nullable Entity $$1, LivingEntity $$2, int $$3, double $$4) {
/* 28 */     if (this.isHarm == $$2.isInvertedHealAndHarm()) {
/* 29 */       int $$5 = (int)($$4 * (4 << $$3) + 0.5D);
/* 30 */       $$2.heal($$5);
/*    */     } else {
/* 32 */       int $$6 = (int)($$4 * (6 << $$3) + 0.5D);
/* 33 */       if ($$0 == null) {
/* 34 */         $$2.hurt($$2.damageSources().magic(), $$6);
/*    */       } else {
/* 36 */         $$2.hurt($$2.damageSources().indirectMagic($$0, $$1), $$6);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\HealOrHarmMobEffect.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */