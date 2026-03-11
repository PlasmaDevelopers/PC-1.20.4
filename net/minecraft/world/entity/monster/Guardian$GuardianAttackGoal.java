/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class GuardianAttackGoal
/*     */   extends Goal
/*     */ {
/*     */   private final Guardian guardian;
/*     */   private int attackTime;
/*     */   private final boolean elder;
/*     */   
/*     */   public GuardianAttackGoal(Guardian $$0) {
/* 401 */     this.guardian = $$0;
/*     */ 
/*     */     
/* 404 */     this.elder = $$0 instanceof ElderGuardian;
/*     */     
/* 406 */     setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 411 */     LivingEntity $$0 = this.guardian.getTarget();
/* 412 */     return ($$0 != null && $$0.isAlive());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/* 417 */     return (super.canContinueToUse() && (this.elder || (this.guardian.getTarget() != null && this.guardian.distanceToSqr((Entity)this.guardian.getTarget()) > 9.0D)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 422 */     this.attackTime = -10;
/* 423 */     this.guardian.getNavigation().stop();
/* 424 */     LivingEntity $$0 = this.guardian.getTarget();
/* 425 */     if ($$0 != null) {
/* 426 */       this.guardian.getLookControl().setLookAt((Entity)$$0, 90.0F, 90.0F);
/*     */     }
/*     */ 
/*     */     
/* 430 */     this.guardian.hasImpulse = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 435 */     this.guardian.setActiveAttackTarget(0);
/* 436 */     this.guardian.setTarget(null);
/*     */     
/* 438 */     this.guardian.randomStrollGoal.trigger();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresUpdateEveryTick() {
/* 443 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 448 */     LivingEntity $$0 = this.guardian.getTarget();
/* 449 */     if ($$0 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 453 */     this.guardian.getNavigation().stop();
/* 454 */     this.guardian.getLookControl().setLookAt((Entity)$$0, 90.0F, 90.0F);
/*     */     
/* 456 */     if (!this.guardian.hasLineOfSight((Entity)$$0)) {
/* 457 */       this.guardian.setTarget(null);
/*     */       
/*     */       return;
/*     */     } 
/* 461 */     this.attackTime++;
/* 462 */     if (this.attackTime == 0) {
/*     */       
/* 464 */       this.guardian.setActiveAttackTarget($$0.getId());
/* 465 */       if (!this.guardian.isSilent()) {
/* 466 */         this.guardian.level().broadcastEntityEvent((Entity)this.guardian, (byte)21);
/*     */       }
/* 468 */     } else if (this.attackTime >= this.guardian.getAttackDuration()) {
/* 469 */       float $$1 = 1.0F;
/* 470 */       if (this.guardian.level().getDifficulty() == Difficulty.HARD) {
/* 471 */         $$1 += 2.0F;
/*     */       }
/* 473 */       if (this.elder) {
/* 474 */         $$1 += 2.0F;
/*     */       }
/* 476 */       $$0.hurt(this.guardian.damageSources().indirectMagic((Entity)this.guardian, (Entity)this.guardian), $$1);
/* 477 */       $$0.hurt(this.guardian.damageSources().mobAttack((LivingEntity)this.guardian), (float)this.guardian.getAttributeValue(Attributes.ATTACK_DAMAGE));
/* 478 */       this.guardian.setTarget(null);
/*     */     } 
/*     */     
/* 481 */     super.tick();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Guardian$GuardianAttackGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */