/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ import java.util.EnumSet;
/*     */ import net.minecraft.util.TimeUtil;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.monster.CrossbowAttackMob;
/*     */ import net.minecraft.world.entity.monster.Monster;
/*     */ import net.minecraft.world.entity.monster.RangedAttackMob;
/*     */ import net.minecraft.world.entity.projectile.ProjectileUtil;
/*     */ import net.minecraft.world.item.CrossbowItem;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ 
/*     */ public class RangedCrossbowAttackGoal<T extends Monster & RangedAttackMob & CrossbowAttackMob> extends Goal {
/*     */   private final T mob;
/*  17 */   public static final UniformInt PATHFINDING_DELAY_RANGE = TimeUtil.rangeOfSeconds(1, 2);
/*     */   
/*     */   private enum CrossbowState {
/*  20 */     UNCHARGED,
/*  21 */     CHARGING,
/*  22 */     CHARGED,
/*  23 */     READY_TO_ATTACK;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  28 */   private CrossbowState crossbowState = CrossbowState.UNCHARGED;
/*     */   private final double speedModifier;
/*     */   private final float attackRadiusSqr;
/*     */   private int seeTime;
/*     */   private int attackDelay;
/*     */   private int updatePathDelay;
/*     */   
/*     */   public RangedCrossbowAttackGoal(T $$0, double $$1, float $$2) {
/*  36 */     this.mob = $$0;
/*  37 */     this.speedModifier = $$1;
/*  38 */     this.attackRadiusSqr = $$2 * $$2;
/*  39 */     setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  44 */     return (isValidTarget() && isHoldingCrossbow());
/*     */   }
/*     */   
/*     */   private boolean isHoldingCrossbow() {
/*  48 */     return this.mob.isHolding(Items.CROSSBOW);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/*  53 */     return (isValidTarget() && (canUse() || !this.mob.getNavigation().isDone()) && isHoldingCrossbow());
/*     */   }
/*     */   
/*     */   private boolean isValidTarget() {
/*  57 */     return (this.mob.getTarget() != null && this.mob.getTarget().isAlive());
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  62 */     super.stop();
/*  63 */     this.mob.setAggressive(false);
/*  64 */     this.mob.setTarget(null);
/*  65 */     this.seeTime = 0;
/*  66 */     if (this.mob.isUsingItem()) {
/*  67 */       this.mob.stopUsingItem();
/*  68 */       ((CrossbowAttackMob)this.mob).setChargingCrossbow(false);
/*  69 */       CrossbowItem.setCharged(this.mob.getUseItem(), false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresUpdateEveryTick() {
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  80 */     LivingEntity $$0 = this.mob.getTarget();
/*  81 */     if ($$0 == null) {
/*     */       return;
/*     */     }
/*     */     
/*  85 */     boolean $$1 = this.mob.getSensing().hasLineOfSight((Entity)$$0);
/*  86 */     boolean $$2 = (this.seeTime > 0);
/*     */     
/*  88 */     if ($$1 != $$2) {
/*  89 */       this.seeTime = 0;
/*     */     }
/*     */     
/*  92 */     if ($$1) {
/*  93 */       this.seeTime++;
/*     */     } else {
/*  95 */       this.seeTime--;
/*     */     } 
/*     */     
/*  98 */     double $$3 = this.mob.distanceToSqr((Entity)$$0);
/*  99 */     boolean $$4 = (($$3 > this.attackRadiusSqr || this.seeTime < 5) && this.attackDelay == 0);
/* 100 */     if ($$4) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 105 */       this.updatePathDelay--;
/* 106 */       if (this.updatePathDelay <= 0) {
/* 107 */         this.mob.getNavigation().moveTo((Entity)$$0, canRun() ? this.speedModifier : (this.speedModifier * 0.5D));
/* 108 */         this.updatePathDelay = PATHFINDING_DELAY_RANGE.sample(this.mob.getRandom());
/*     */       } 
/*     */     } else {
/* 111 */       this.updatePathDelay = 0;
/* 112 */       this.mob.getNavigation().stop();
/*     */     } 
/*     */     
/* 115 */     this.mob.getLookControl().setLookAt((Entity)$$0, 30.0F, 30.0F);
/*     */     
/* 117 */     if (this.crossbowState == CrossbowState.UNCHARGED) {
/* 118 */       if (!$$4) {
/* 119 */         this.mob.startUsingItem(ProjectileUtil.getWeaponHoldingHand((LivingEntity)this.mob, Items.CROSSBOW));
/* 120 */         this.crossbowState = CrossbowState.CHARGING;
/* 121 */         ((CrossbowAttackMob)this.mob).setChargingCrossbow(true);
/*     */       } 
/* 123 */     } else if (this.crossbowState == CrossbowState.CHARGING) {
/* 124 */       if (!this.mob.isUsingItem()) {
/* 125 */         this.crossbowState = CrossbowState.UNCHARGED;
/*     */       }
/* 127 */       int $$5 = this.mob.getTicksUsingItem();
/* 128 */       ItemStack $$6 = this.mob.getUseItem();
/* 129 */       if ($$5 >= CrossbowItem.getChargeDuration($$6)) {
/* 130 */         this.mob.releaseUsingItem();
/*     */         
/* 132 */         this.crossbowState = CrossbowState.CHARGED;
/* 133 */         this.attackDelay = 20 + this.mob.getRandom().nextInt(20);
/* 134 */         ((CrossbowAttackMob)this.mob).setChargingCrossbow(false);
/*     */       } 
/* 136 */     } else if (this.crossbowState == CrossbowState.CHARGED) {
/* 137 */       this.attackDelay--;
/* 138 */       if (this.attackDelay == 0) {
/* 139 */         this.crossbowState = CrossbowState.READY_TO_ATTACK;
/*     */       }
/* 141 */     } else if (this.crossbowState == CrossbowState.READY_TO_ATTACK && 
/* 142 */       $$1) {
/* 143 */       ((RangedAttackMob)this.mob).performRangedAttack($$0, 1.0F);
/*     */       
/* 145 */       ItemStack $$7 = this.mob.getItemInHand(ProjectileUtil.getWeaponHoldingHand((LivingEntity)this.mob, Items.CROSSBOW));
/* 146 */       CrossbowItem.setCharged($$7, false);
/* 147 */       this.crossbowState = CrossbowState.UNCHARGED;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canRun() {
/* 153 */     return (this.crossbowState == CrossbowState.UNCHARGED);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\RangedCrossbowAttackGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */