/*    */ package net.minecraft.world.effect;
/*    */ 
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.Difficulty;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ class BadOmenMobEffect extends MobEffect {
/*    */   protected BadOmenMobEffect(MobEffectCategory $$0, int $$1) {
/* 10 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldApplyEffectTickThisTick(int $$0, int $$1) {
/* 15 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyEffectTick(LivingEntity $$0, int $$1) {
/* 20 */     super.applyEffectTick($$0, $$1);
/* 21 */     if ($$0 instanceof ServerPlayer) { ServerPlayer $$2 = (ServerPlayer)$$0; if (!$$0.isSpectator()) {
/* 22 */         ServerLevel $$3 = $$2.serverLevel();
/* 23 */         if ($$3.getDifficulty() == Difficulty.PEACEFUL) {
/*    */           return;
/*    */         }
/* 26 */         if ($$3.isVillage($$0.blockPosition()))
/* 27 */           $$3.getRaids().createOrExtendRaid($$2); 
/*    */       }  }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\BadOmenMobEffect.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */