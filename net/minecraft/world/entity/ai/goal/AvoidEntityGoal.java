/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntitySelector;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*    */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*    */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*    */ import net.minecraft.world.level.pathfinder.Path;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class AvoidEntityGoal<T extends LivingEntity> extends Goal {
/*    */   protected final PathfinderMob mob;
/*    */   private final double walkSpeedModifier;
/*    */   private final double sprintSpeedModifier;
/*    */   @Nullable
/*    */   protected T toAvoid;
/*    */   protected final float maxDist;
/*    */   @Nullable
/*    */   protected Path path;
/*    */   protected final PathNavigation pathNav;
/*    */   protected final Class<T> avoidClass;
/*    */   protected final Predicate<LivingEntity> avoidPredicate;
/*    */   protected final Predicate<LivingEntity> predicateOnAvoidEntity;
/*    */   private final TargetingConditions avoidEntityTargeting;
/*    */   
/*    */   public AvoidEntityGoal(PathfinderMob $$0, Class<T> $$1, float $$2, double $$3, double $$4) {
/* 32 */     this($$0, $$1, $$0 -> true, $$2, $$3, $$4, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
/*    */   }
/*    */   
/*    */   public AvoidEntityGoal(PathfinderMob $$0, Class<T> $$1, Predicate<LivingEntity> $$2, float $$3, double $$4, double $$5, Predicate<LivingEntity> $$6) {
/* 36 */     this.mob = $$0;
/* 37 */     this.avoidClass = $$1;
/* 38 */     this.avoidPredicate = $$2;
/* 39 */     this.maxDist = $$3;
/* 40 */     this.walkSpeedModifier = $$4;
/* 41 */     this.sprintSpeedModifier = $$5;
/* 42 */     this.predicateOnAvoidEntity = $$6;
/* 43 */     this.pathNav = $$0.getNavigation();
/* 44 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*    */     
/* 46 */     this.avoidEntityTargeting = TargetingConditions.forCombat().range($$3).selector($$6.and($$2));
/*    */   }
/*    */   
/*    */   public AvoidEntityGoal(PathfinderMob $$0, Class<T> $$1, float $$2, double $$3, double $$4, Predicate<LivingEntity> $$5) {
/* 50 */     this($$0, $$1, $$0 -> true, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 55 */     this.toAvoid = (T)this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.avoidClass, this.mob.getBoundingBox().inflate(this.maxDist, 3.0D, this.maxDist), $$0 -> true), this.avoidEntityTargeting, (LivingEntity)this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
/* 56 */     if (this.toAvoid == null) {
/* 57 */       return false;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 63 */     Vec3 $$0 = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.toAvoid.position());
/* 64 */     if ($$0 == null) {
/* 65 */       return false;
/*    */     }
/* 67 */     if (this.toAvoid.distanceToSqr($$0.x, $$0.y, $$0.z) < this.toAvoid.distanceToSqr((Entity)this.mob)) {
/* 68 */       return false;
/*    */     }
/* 70 */     this.path = this.pathNav.createPath($$0.x, $$0.y, $$0.z, 0);
/* 71 */     return (this.path != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 76 */     return !this.pathNav.isDone();
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 81 */     this.pathNav.moveTo(this.path, this.walkSpeedModifier);
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 86 */     this.toAvoid = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 91 */     if (this.mob.distanceToSqr((Entity)this.toAvoid) < 49.0D) {
/* 92 */       this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
/*    */     } else {
/* 94 */       this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\AvoidEntityGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */