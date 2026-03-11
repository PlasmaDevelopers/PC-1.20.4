/*    */ package net.minecraft.world.entity.monster.hoglin;
/*    */ 
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public interface HoglinBase
/*    */ {
/*    */   public static final int ATTACK_ANIMATION_DURATION = 10;
/*    */   
/*    */   int getAttackAnimationRemainingTicks();
/*    */   
/*    */   static boolean hurtAndThrowTarget(LivingEntity $$0, LivingEntity $$1) {
/* 15 */     float $$4, $$2 = (float)$$0.getAttributeValue(Attributes.ATTACK_DAMAGE);
/* 16 */     if (!$$0.isBaby() && (int)$$2 > 0) {
/* 17 */       float $$3 = $$2 / 2.0F + ($$0.level()).random.nextInt((int)$$2);
/*    */     } else {
/* 19 */       $$4 = $$2;
/*    */     } 
/*    */     
/* 22 */     boolean $$5 = $$1.hurt($$0.damageSources().mobAttack($$0), $$4);
/* 23 */     if ($$5) {
/* 24 */       $$0.doEnchantDamageEffects($$0, (Entity)$$1);
/* 25 */       if (!$$0.isBaby()) {
/* 26 */         throwTarget($$0, $$1);
/*    */       }
/*    */     } 
/* 29 */     return $$5;
/*    */   }
/*    */   
/*    */   static void throwTarget(LivingEntity $$0, LivingEntity $$1) {
/* 33 */     double $$2 = $$0.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
/* 34 */     double $$3 = $$1.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
/* 35 */     double $$4 = $$2 - $$3;
/* 36 */     if ($$4 <= 0.0D) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     double $$5 = $$1.getX() - $$0.getX();
/* 41 */     double $$6 = $$1.getZ() - $$0.getZ();
/* 42 */     float $$7 = (($$0.level()).random.nextInt(21) - 10);
/* 43 */     double $$8 = $$4 * (($$0.level()).random.nextFloat() * 0.5F + 0.2F);
/* 44 */     Vec3 $$9 = (new Vec3($$5, 0.0D, $$6)).normalize().scale($$8).yRot($$7);
/*    */     
/* 46 */     double $$10 = $$4 * ($$0.level()).random.nextFloat() * 0.5D;
/* 47 */     $$1.push($$9.x, $$10, $$9.z);
/* 48 */     $$1.hurtMarked = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\hoglin\HoglinBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */