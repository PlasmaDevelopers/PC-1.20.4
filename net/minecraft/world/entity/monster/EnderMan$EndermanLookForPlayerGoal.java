/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EndermanLookForPlayerGoal
/*     */   extends NearestAttackableTargetGoal<Player>
/*     */ {
/*     */   private final EnderMan enderman;
/*     */   @Nullable
/*     */   private Player pendingTarget;
/*     */   private int aggroTime;
/*     */   private int teleportTime;
/*     */   private final TargetingConditions startAggroTargetConditions;
/* 444 */   private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight();
/*     */   private final Predicate<LivingEntity> isAngerInducing;
/*     */   
/*     */   public EndermanLookForPlayerGoal(EnderMan $$0, @Nullable Predicate<LivingEntity> $$1) {
/* 448 */     super((Mob)$$0, Player.class, 10, false, false, $$1);
/* 449 */     this.enderman = $$0;
/* 450 */     this.isAngerInducing = ($$1 -> (($$0.isLookingAtMe((Player)$$1) || $$0.isAngryAt($$1)) && !$$0.hasIndirectPassenger((Entity)$$1)));
/*     */     
/* 452 */     this.startAggroTargetConditions = TargetingConditions.forCombat().range(getFollowDistance()).selector(this.isAngerInducing);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 457 */     this.pendingTarget = this.enderman.level().getNearestPlayer(this.startAggroTargetConditions, (LivingEntity)this.enderman);
/* 458 */     return (this.pendingTarget != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 463 */     this.aggroTime = adjustedTickDelay(5);
/* 464 */     this.teleportTime = 0;
/* 465 */     this.enderman.setBeingStaredAt();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 471 */     this.pendingTarget = null;
/*     */     
/* 473 */     super.stop();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/* 478 */     if (this.pendingTarget != null) {
/* 479 */       if (!this.isAngerInducing.test(this.pendingTarget)) {
/* 480 */         return false;
/*     */       }
/* 482 */       this.enderman.lookAt((Entity)this.pendingTarget, 10.0F, 10.0F);
/* 483 */       return true;
/* 484 */     }  if (this.target != null) {
/* 485 */       if (this.enderman.hasIndirectPassenger((Entity)this.target))
/* 486 */         return false; 
/* 487 */       if (this.continueAggroTargetConditions.test((LivingEntity)this.enderman, this.target)) {
/* 488 */         return true;
/*     */       }
/*     */     } 
/* 491 */     return super.canContinueToUse();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 496 */     if (this.enderman.getTarget() == null) {
/* 497 */       setTarget(null);
/*     */     }
/*     */     
/* 500 */     if (this.pendingTarget != null) {
/* 501 */       if (--this.aggroTime <= 0) {
/* 502 */         this.target = (LivingEntity)this.pendingTarget;
/* 503 */         this.pendingTarget = null;
/* 504 */         super.start();
/*     */       } 
/*     */     } else {
/* 507 */       if (this.target != null && !this.enderman.isPassenger()) {
/* 508 */         if (this.enderman.isLookingAtMe((Player)this.target)) {
/* 509 */           if (this.target.distanceToSqr((Entity)this.enderman) < 16.0D) {
/* 510 */             this.enderman.teleport();
/*     */           }
/* 512 */           this.teleportTime = 0;
/* 513 */         } else if (this.target.distanceToSqr((Entity)this.enderman) > 256.0D && 
/* 514 */           this.teleportTime++ >= adjustedTickDelay(30) && 
/* 515 */           this.enderman.teleportTowards((Entity)this.target)) {
/* 516 */           this.teleportTime = 0;
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 522 */       super.tick();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\EnderMan$EndermanLookForPlayerGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */