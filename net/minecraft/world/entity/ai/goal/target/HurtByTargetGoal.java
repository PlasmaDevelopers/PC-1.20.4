/*     */ package net.minecraft.world.entity.ai.goal.target;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.TamableAnimal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ 
/*     */ public class HurtByTargetGoal
/*     */   extends TargetGoal {
/*  20 */   private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
/*     */   
/*     */   private static final int ALERT_RANGE_Y = 10;
/*     */   
/*     */   private boolean alertSameType;
/*     */   
/*     */   private int timestamp;
/*     */   private final Class<?>[] toIgnoreDamage;
/*     */   @Nullable
/*     */   private Class<?>[] toIgnoreAlert;
/*     */   
/*     */   public HurtByTargetGoal(PathfinderMob $$0, Class<?>... $$1) {
/*  32 */     super((Mob)$$0, true);
/*  33 */     this.toIgnoreDamage = $$1;
/*  34 */     setFlags(EnumSet.of(Goal.Flag.TARGET));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  39 */     int $$0 = this.mob.getLastHurtByMobTimestamp();
/*  40 */     LivingEntity $$1 = this.mob.getLastHurtByMob();
/*     */     
/*  42 */     if ($$0 == this.timestamp || $$1 == null) {
/*  43 */       return false;
/*     */     }
/*     */     
/*  46 */     if ($$1.getType() == EntityType.PLAYER && this.mob.level().getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER))
/*     */     {
/*  48 */       return false;
/*     */     }
/*     */     
/*  51 */     for (Class<?> $$2 : this.toIgnoreDamage) {
/*  52 */       if ($$2.isAssignableFrom($$1.getClass())) {
/*  53 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  57 */     return canAttack($$1, HURT_BY_TARGETING);
/*     */   }
/*     */   
/*     */   public HurtByTargetGoal setAlertOthers(Class<?>... $$0) {
/*  61 */     this.alertSameType = true;
/*  62 */     this.toIgnoreAlert = $$0;
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  68 */     this.mob.setTarget(this.mob.getLastHurtByMob());
/*  69 */     this.targetMob = this.mob.getTarget();
/*  70 */     this.timestamp = this.mob.getLastHurtByMobTimestamp();
/*  71 */     this.unseenMemoryTicks = 300;
/*     */     
/*  73 */     if (this.alertSameType) {
/*  74 */       alertOthers();
/*     */     }
/*     */     
/*  77 */     super.start();
/*     */   }
/*     */   
/*     */   protected void alertOthers() {
/*  81 */     double $$0 = getFollowDistance();
/*     */     
/*  83 */     AABB $$1 = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate($$0, 10.0D, $$0);
/*  84 */     List<? extends Mob> $$2 = this.mob.level().getEntitiesOfClass(this.mob.getClass(), $$1, EntitySelector.NO_SPECTATORS);
/*  85 */     for (Mob $$3 : $$2) {
/*  86 */       if (this.mob == $$3) {
/*     */         continue;
/*     */       }
/*  89 */       if ($$3.getTarget() != null) {
/*     */         continue;
/*     */       }
/*  92 */       if (this.mob instanceof TamableAnimal && ((TamableAnimal)this.mob).getOwner() != ((TamableAnimal)$$3).getOwner()) {
/*     */         continue;
/*     */       }
/*  95 */       if ($$3.isAlliedTo((Entity)this.mob.getLastHurtByMob())) {
/*     */         continue;
/*     */       }
/*  98 */       if (this.toIgnoreAlert != null) {
/*  99 */         boolean $$4 = false;
/* 100 */         for (Class<?> $$5 : this.toIgnoreAlert) {
/* 101 */           if ($$3.getClass() == $$5) {
/* 102 */             $$4 = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 106 */         if ($$4) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */       
/* 111 */       alertOther($$3, this.mob.getLastHurtByMob());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void alertOther(Mob $$0, LivingEntity $$1) {
/* 116 */     $$0.setTarget($$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\target\HurtByTargetGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */