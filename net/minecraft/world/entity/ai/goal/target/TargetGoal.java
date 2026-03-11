/*     */ package net.minecraft.world.entity.ai.goal.target;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.level.pathfinder.Node;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ 
/*     */ 
/*     */ public abstract class TargetGoal
/*     */   extends Goal
/*     */ {
/*     */   private static final int EMPTY_REACH_CACHE = 0;
/*     */   private static final int CAN_REACH_CACHE = 1;
/*     */   private static final int CANT_REACH_CACHE = 2;
/*     */   protected final Mob mob;
/*     */   protected final boolean mustSee;
/*     */   private final boolean mustReach;
/*     */   private int reachCache;
/*     */   private int reachCacheTime;
/*     */   private int unseenTicks;
/*     */   @Nullable
/*     */   protected LivingEntity targetMob;
/*  29 */   protected int unseenMemoryTicks = 60;
/*     */   
/*     */   public TargetGoal(Mob $$0, boolean $$1) {
/*  32 */     this($$0, $$1, false);
/*     */   }
/*     */   
/*     */   public TargetGoal(Mob $$0, boolean $$1, boolean $$2) {
/*  36 */     this.mob = $$0;
/*  37 */     this.mustSee = $$1;
/*  38 */     this.mustReach = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/*  43 */     LivingEntity $$0 = this.mob.getTarget();
/*  44 */     if ($$0 == null) {
/*  45 */       $$0 = this.targetMob;
/*     */     }
/*  47 */     if ($$0 == null) {
/*  48 */       return false;
/*     */     }
/*  50 */     if (!this.mob.canAttack($$0)) {
/*  51 */       return false;
/*     */     }
/*     */     
/*  54 */     PlayerTeam playerTeam1 = this.mob.getTeam();
/*  55 */     PlayerTeam playerTeam2 = $$0.getTeam();
/*  56 */     if (playerTeam1 != null && playerTeam2 == playerTeam1) {
/*  57 */       return false;
/*     */     }
/*     */     
/*  60 */     double $$3 = getFollowDistance();
/*  61 */     if (this.mob.distanceToSqr((Entity)$$0) > $$3 * $$3) {
/*  62 */       return false;
/*     */     }
/*  64 */     if (this.mustSee) {
/*  65 */       if (this.mob.getSensing().hasLineOfSight((Entity)$$0)) {
/*  66 */         this.unseenTicks = 0;
/*     */       }
/*  68 */       else if (++this.unseenTicks > reducedTickDelay(this.unseenMemoryTicks)) {
/*  69 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*  73 */     this.mob.setTarget($$0);
/*  74 */     return true;
/*     */   }
/*     */   
/*     */   protected double getFollowDistance() {
/*  78 */     return this.mob.getAttributeValue(Attributes.FOLLOW_RANGE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  83 */     this.reachCache = 0;
/*  84 */     this.reachCacheTime = 0;
/*  85 */     this.unseenTicks = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  90 */     this.mob.setTarget(null);
/*  91 */     this.targetMob = null;
/*     */   }
/*     */   
/*     */   protected boolean canAttack(@Nullable LivingEntity $$0, TargetingConditions $$1) {
/*  95 */     if ($$0 == null) {
/*  96 */       return false;
/*     */     }
/*  98 */     if (!$$1.test((LivingEntity)this.mob, $$0)) {
/*  99 */       return false;
/*     */     }
/* 101 */     if (!this.mob.isWithinRestriction($$0.blockPosition())) {
/* 102 */       return false;
/*     */     }
/*     */     
/* 105 */     if (this.mustReach) {
/* 106 */       if (--this.reachCacheTime <= 0) {
/* 107 */         this.reachCache = 0;
/*     */       }
/* 109 */       if (this.reachCache == 0) {
/* 110 */         this.reachCache = canReach($$0) ? 1 : 2;
/*     */       }
/* 112 */       if (this.reachCache == 2) {
/* 113 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 117 */     return true;
/*     */   }
/*     */   
/*     */   private boolean canReach(LivingEntity $$0) {
/* 121 */     this.reachCacheTime = reducedTickDelay(10 + this.mob.getRandom().nextInt(5));
/* 122 */     Path $$1 = this.mob.getNavigation().createPath((Entity)$$0, 0);
/* 123 */     if ($$1 == null) {
/* 124 */       return false;
/*     */     }
/* 126 */     Node $$2 = $$1.getEndNode();
/* 127 */     if ($$2 == null) {
/* 128 */       return false;
/*     */     }
/* 130 */     int $$3 = $$2.x - $$0.getBlockX();
/* 131 */     int $$4 = $$2.z - $$0.getBlockZ();
/* 132 */     return (($$3 * $$3 + $$4 * $$4) <= 2.25D);
/*     */   }
/*     */   
/*     */   public TargetGoal setUnseenMemoryTicks(int $$0) {
/* 136 */     this.unseenMemoryTicks = $$0;
/* 137 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\target\TargetGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */