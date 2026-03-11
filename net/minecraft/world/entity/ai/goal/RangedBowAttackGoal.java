/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.monster.Monster;
/*     */ import net.minecraft.world.entity.monster.RangedAttackMob;
/*     */ import net.minecraft.world.entity.projectile.ProjectileUtil;
/*     */ import net.minecraft.world.item.BowItem;
/*     */ import net.minecraft.world.item.Items;
/*     */ 
/*     */ public class RangedBowAttackGoal<T extends Monster & RangedAttackMob>
/*     */   extends Goal {
/*     */   private final T mob;
/*     */   private final double speedModifier;
/*     */   private int attackIntervalMin;
/*     */   private final float attackRadiusSqr;
/*  19 */   private int attackTime = -1;
/*     */   private int seeTime;
/*     */   private boolean strafingClockwise;
/*     */   private boolean strafingBackwards;
/*  23 */   private int strafingTime = -1;
/*     */   
/*     */   public RangedBowAttackGoal(T $$0, double $$1, int $$2, float $$3) {
/*  26 */     this.mob = $$0;
/*  27 */     this.speedModifier = $$1;
/*  28 */     this.attackIntervalMin = $$2;
/*  29 */     this.attackRadiusSqr = $$3 * $$3;
/*  30 */     setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */   }
/*     */   
/*     */   public void setMinAttackInterval(int $$0) {
/*  34 */     this.attackIntervalMin = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  39 */     if (this.mob.getTarget() == null) {
/*  40 */       return false;
/*     */     }
/*  42 */     return isHoldingBow();
/*     */   }
/*     */   
/*     */   protected boolean isHoldingBow() {
/*  46 */     return this.mob.isHolding(Items.BOW);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/*  51 */     return ((canUse() || !this.mob.getNavigation().isDone()) && isHoldingBow());
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  56 */     super.start();
/*     */     
/*  58 */     this.mob.setAggressive(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  63 */     super.stop();
/*     */     
/*  65 */     this.mob.setAggressive(false);
/*  66 */     this.seeTime = 0;
/*  67 */     this.attackTime = -1;
/*  68 */     this.mob.stopUsingItem();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresUpdateEveryTick() {
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  78 */     LivingEntity $$0 = this.mob.getTarget();
/*  79 */     if ($$0 == null) {
/*     */       return;
/*     */     }
/*  82 */     double $$1 = this.mob.distanceToSqr($$0.getX(), $$0.getY(), $$0.getZ());
/*  83 */     boolean $$2 = this.mob.getSensing().hasLineOfSight((Entity)$$0);
/*  84 */     boolean $$3 = (this.seeTime > 0);
/*     */     
/*  86 */     if ($$2 != $$3) {
/*  87 */       this.seeTime = 0;
/*     */     }
/*     */     
/*  90 */     if ($$2) {
/*  91 */       this.seeTime++;
/*     */     } else {
/*  93 */       this.seeTime--;
/*     */     } 
/*     */     
/*  96 */     if ($$1 > this.attackRadiusSqr || this.seeTime < 20) {
/*  97 */       this.mob.getNavigation().moveTo((Entity)$$0, this.speedModifier);
/*  98 */       this.strafingTime = -1;
/*     */     } else {
/* 100 */       this.mob.getNavigation().stop();
/* 101 */       this.strafingTime++;
/*     */     } 
/*     */     
/* 104 */     if (this.strafingTime >= 20) {
/* 105 */       if (this.mob.getRandom().nextFloat() < 0.3D) {
/* 106 */         this.strafingClockwise = !this.strafingClockwise;
/*     */       }
/* 108 */       if (this.mob.getRandom().nextFloat() < 0.3D) {
/* 109 */         this.strafingBackwards = !this.strafingBackwards;
/*     */       }
/* 111 */       this.strafingTime = 0;
/*     */     } 
/*     */     
/* 114 */     if (this.strafingTime > -1) {
/* 115 */       if ($$1 > (this.attackRadiusSqr * 0.75F)) {
/* 116 */         this.strafingBackwards = false;
/* 117 */       } else if ($$1 < (this.attackRadiusSqr * 0.25F)) {
/* 118 */         this.strafingBackwards = true;
/*     */       } 
/* 120 */       this.mob.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
/* 121 */       Entity entity = this.mob.getControlledVehicle(); if (entity instanceof Mob) { Mob $$4 = (Mob)entity;
/* 122 */         $$4.lookAt((Entity)$$0, 30.0F, 30.0F); }
/*     */       
/* 124 */       this.mob.lookAt((Entity)$$0, 30.0F, 30.0F);
/*     */     } else {
/* 126 */       this.mob.getLookControl().setLookAt((Entity)$$0, 30.0F, 30.0F);
/*     */     } 
/*     */     
/* 129 */     if (this.mob.isUsingItem()) {
/* 130 */       if (!$$2 && this.seeTime < -60) {
/* 131 */         this.mob.stopUsingItem();
/* 132 */       } else if ($$2) {
/* 133 */         int $$5 = this.mob.getTicksUsingItem();
/*     */         
/* 135 */         if ($$5 >= 20) {
/* 136 */           this.mob.stopUsingItem();
/* 137 */           ((RangedAttackMob)this.mob).performRangedAttack($$0, BowItem.getPowerForTime($$5));
/* 138 */           this.attackTime = this.attackIntervalMin;
/*     */         } 
/*     */       } 
/* 141 */     } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
/* 142 */       this.mob.startUsingItem(ProjectileUtil.getWeaponHoldingHand((LivingEntity)this.mob, Items.BOW));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\RangedBowAttackGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */