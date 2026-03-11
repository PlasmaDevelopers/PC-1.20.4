/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.monster.RangedAttackMob;
/*    */ 
/*    */ public class RangedAttackGoal extends Goal {
/*    */   private final Mob mob;
/*    */   private final RangedAttackMob rangedAttackMob;
/*    */   @Nullable
/*    */   private LivingEntity target;
/* 16 */   private int attackTime = -1;
/*    */   private final double speedModifier;
/*    */   private int seeTime;
/*    */   private final int attackIntervalMin;
/*    */   private final int attackIntervalMax;
/*    */   private final float attackRadius;
/*    */   private final float attackRadiusSqr;
/*    */   
/*    */   public RangedAttackGoal(RangedAttackMob $$0, double $$1, int $$2, float $$3) {
/* 25 */     this($$0, $$1, $$2, $$2, $$3);
/*    */   }
/*    */   
/*    */   public RangedAttackGoal(RangedAttackMob $$0, double $$1, int $$2, int $$3, float $$4) {
/* 29 */     if (!($$0 instanceof LivingEntity)) {
/* 30 */       throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
/*    */     }
/* 32 */     this.rangedAttackMob = $$0;
/* 33 */     this.mob = (Mob)$$0;
/* 34 */     this.speedModifier = $$1;
/* 35 */     this.attackIntervalMin = $$2;
/* 36 */     this.attackIntervalMax = $$3;
/* 37 */     this.attackRadius = $$4;
/* 38 */     this.attackRadiusSqr = $$4 * $$4;
/* 39 */     setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 44 */     LivingEntity $$0 = this.mob.getTarget();
/* 45 */     if ($$0 == null || !$$0.isAlive()) {
/* 46 */       return false;
/*    */     }
/* 48 */     this.target = $$0;
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 54 */     return (canUse() || (this.target.isAlive() && !this.mob.getNavigation().isDone()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 59 */     this.target = null;
/* 60 */     this.seeTime = 0;
/* 61 */     this.attackTime = -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean requiresUpdateEveryTick() {
/* 66 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 71 */     double $$0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
/* 72 */     boolean $$1 = this.mob.getSensing().hasLineOfSight((Entity)this.target);
/*    */     
/* 74 */     if ($$1) {
/* 75 */       this.seeTime++;
/*    */     } else {
/* 77 */       this.seeTime = 0;
/*    */     } 
/*    */     
/* 80 */     if ($$0 > this.attackRadiusSqr || this.seeTime < 5) {
/* 81 */       this.mob.getNavigation().moveTo((Entity)this.target, this.speedModifier);
/*    */     } else {
/* 83 */       this.mob.getNavigation().stop();
/*    */     } 
/*    */     
/* 86 */     this.mob.getLookControl().setLookAt((Entity)this.target, 30.0F, 30.0F);
/*    */     
/* 88 */     if (--this.attackTime == 0) {
/* 89 */       if (!$$1) {
/*    */         return;
/*    */       }
/*    */       
/* 93 */       float $$2 = (float)Math.sqrt($$0) / this.attackRadius;
/* 94 */       float $$3 = Mth.clamp($$2, 0.1F, 1.0F);
/*    */       
/* 96 */       this.rangedAttackMob.performRangedAttack(this.target, $$3);
/* 97 */       this.attackTime = Mth.floor($$2 * (this.attackIntervalMax - this.attackIntervalMin) + this.attackIntervalMin);
/* 98 */     } else if (this.attackTime < 0) {
/* 99 */       this.attackTime = Mth.floor(Mth.lerp(Math.sqrt($$0) / this.attackRadius, this.attackIntervalMin, this.attackIntervalMax));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\RangedAttackGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */