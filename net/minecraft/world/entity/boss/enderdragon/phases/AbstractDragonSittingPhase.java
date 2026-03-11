/*    */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*    */ 
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*    */ 
/*    */ public abstract class AbstractDragonSittingPhase
/*    */   extends AbstractDragonPhaseInstance {
/*    */   public AbstractDragonSittingPhase(EnderDragon $$0) {
/*  9 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSitting() {
/* 14 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public float onHurt(DamageSource $$0, float $$1) {
/* 19 */     if ($$0.getDirectEntity() instanceof net.minecraft.world.entity.projectile.AbstractArrow) {
/* 20 */       $$0.getDirectEntity().setSecondsOnFire(1);
/* 21 */       return 0.0F;
/*    */     } 
/* 23 */     return super.onHurt($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\AbstractDragonSittingPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */