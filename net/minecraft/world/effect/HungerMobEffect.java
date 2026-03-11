/*    */ package net.minecraft.world.effect;
/*    */ 
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ class HungerMobEffect
/*    */   extends MobEffect {
/*    */   protected HungerMobEffect(MobEffectCategory $$0, int $$1) {
/*  9 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyEffectTick(LivingEntity $$0, int $$1) {
/* 14 */     super.applyEffectTick($$0, $$1);
/* 15 */     if ($$0 instanceof Player) { Player $$2 = (Player)$$0;
/*    */ 
/*    */       
/* 18 */       $$2.causeFoodExhaustion(0.005F * ($$1 + 1)); }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldApplyEffectTickThisTick(int $$0, int $$1) {
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\HungerMobEffect.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */