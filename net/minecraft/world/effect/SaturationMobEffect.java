/*    */ package net.minecraft.world.effect;
/*    */ 
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ class SaturationMobEffect
/*    */   extends InstantenousMobEffect {
/*    */   protected SaturationMobEffect(MobEffectCategory $$0, int $$1) {
/*  9 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyEffectTick(LivingEntity $$0, int $$1) {
/* 14 */     super.applyEffectTick($$0, $$1);
/* 15 */     if (!($$0.level()).isClientSide && $$0 instanceof Player) { Player $$2 = (Player)$$0;
/* 16 */       $$2.getFoodData().eat($$1 + 1, 1.0F); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\SaturationMobEffect.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */