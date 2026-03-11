/*     */ package net.minecraft.world.entity.ai.goal;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.control.LookControl;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ 
/*     */ public class FollowMobGoal
/*     */   extends Goal
/*     */ {
/*     */   private final Mob mob;
/*     */   private final Predicate<Mob> followPredicate;
/*     */   @Nullable
/*     */   private Mob followingMob;
/*     */   private final double speedModifier;
/*     */   private final PathNavigation navigation;
/*     */   private int timeToRecalcPath;
/*     */   private final float stopDistance;
/*     */   private float oldWaterCost;
/*     */   private final float areaSize;
/*     */   
/*     */   public FollowMobGoal(Mob $$0, double $$1, float $$2, float $$3) {
/*  28 */     this.mob = $$0;
/*  29 */     this.followPredicate = ($$1 -> ($$1 != null && $$0.getClass() != $$1.getClass()));
/*  30 */     this.speedModifier = $$1;
/*  31 */     this.navigation = $$0.getNavigation();
/*  32 */     this.stopDistance = $$2;
/*  33 */     this.areaSize = $$3;
/*     */     
/*  35 */     setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */     
/*  37 */     if (!($$0.getNavigation() instanceof net.minecraft.world.entity.ai.navigation.GroundPathNavigation) && !($$0.getNavigation() instanceof net.minecraft.world.entity.ai.navigation.FlyingPathNavigation)) {
/*  38 */       throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/*  44 */     List<Mob> $$0 = this.mob.level().getEntitiesOfClass(Mob.class, this.mob.getBoundingBox().inflate(this.areaSize), this.followPredicate);
/*  45 */     if (!$$0.isEmpty()) {
/*  46 */       for (Mob $$1 : $$0) {
/*  47 */         if ($$1.isInvisible()) {
/*     */           continue;
/*     */         }
/*     */         
/*  51 */         this.followingMob = $$1;
/*  52 */         return true;
/*     */       } 
/*     */     }
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/*  60 */     return (this.followingMob != null && !this.navigation.isDone() && this.mob.distanceToSqr((Entity)this.followingMob) > (this.stopDistance * this.stopDistance));
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/*  65 */     this.timeToRecalcPath = 0;
/*  66 */     this.oldWaterCost = this.mob.getPathfindingMalus(BlockPathTypes.WATER);
/*  67 */     this.mob.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  72 */     this.followingMob = null;
/*  73 */     this.navigation.stop();
/*  74 */     this.mob.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  79 */     if (this.followingMob == null || this.mob.isLeashed()) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     this.mob.getLookControl().setLookAt((Entity)this.followingMob, 10.0F, this.mob.getMaxHeadXRot());
/*     */     
/*  85 */     if (--this.timeToRecalcPath > 0) {
/*     */       return;
/*     */     }
/*  88 */     this.timeToRecalcPath = adjustedTickDelay(10);
/*     */     
/*  90 */     double $$0 = this.mob.getX() - this.followingMob.getX();
/*  91 */     double $$1 = this.mob.getY() - this.followingMob.getY();
/*  92 */     double $$2 = this.mob.getZ() - this.followingMob.getZ();
/*     */     
/*  94 */     double $$3 = $$0 * $$0 + $$1 * $$1 + $$2 * $$2;
/*  95 */     if ($$3 <= (this.stopDistance * this.stopDistance)) {
/*  96 */       this.navigation.stop();
/*     */       
/*  98 */       LookControl $$4 = this.followingMob.getLookControl();
/*  99 */       if ($$3 <= this.stopDistance || ($$4.getWantedX() == this.mob.getX() && $$4.getWantedY() == this.mob.getY() && $$4.getWantedZ() == this.mob.getZ())) {
/* 100 */         double $$5 = this.followingMob.getX() - this.mob.getX();
/* 101 */         double $$6 = this.followingMob.getZ() - this.mob.getZ();
/* 102 */         this.navigation.moveTo(this.mob.getX() - $$5, this.mob.getY(), this.mob.getZ() - $$6, this.speedModifier);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 107 */     this.navigation.moveTo((Entity)this.followingMob, this.speedModifier);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\FollowMobGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */