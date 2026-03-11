/*    */ package net.minecraft.world.entity.ai.goal.target;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.goal.Goal;
/*    */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ public class NearestAttackableTargetGoal<T extends LivingEntity>
/*    */   extends TargetGoal
/*    */ {
/*    */   private static final int DEFAULT_RANDOM_INTERVAL = 10;
/*    */   protected final Class<T> targetType;
/*    */   protected final int randomInterval;
/*    */   @Nullable
/*    */   protected LivingEntity target;
/*    */   protected TargetingConditions targetConditions;
/*    */   
/*    */   public NearestAttackableTargetGoal(Mob $$0, Class<T> $$1, boolean $$2) {
/* 25 */     this($$0, $$1, 10, $$2, false, (Predicate<LivingEntity>)null);
/*    */   }
/*    */   
/*    */   public NearestAttackableTargetGoal(Mob $$0, Class<T> $$1, boolean $$2, Predicate<LivingEntity> $$3) {
/* 29 */     this($$0, $$1, 10, $$2, false, $$3);
/*    */   }
/*    */   
/*    */   public NearestAttackableTargetGoal(Mob $$0, Class<T> $$1, boolean $$2, boolean $$3) {
/* 33 */     this($$0, $$1, 10, $$2, $$3, (Predicate<LivingEntity>)null);
/*    */   }
/*    */   
/*    */   public NearestAttackableTargetGoal(Mob $$0, Class<T> $$1, int $$2, boolean $$3, boolean $$4, @Nullable Predicate<LivingEntity> $$5) {
/* 37 */     super($$0, $$3, $$4);
/* 38 */     this.targetType = $$1;
/* 39 */     this.randomInterval = reducedTickDelay($$2);
/* 40 */     setFlags(EnumSet.of(Goal.Flag.TARGET));
/*    */     
/* 42 */     this.targetConditions = TargetingConditions.forCombat().range(getFollowDistance()).selector($$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 47 */     if (this.randomInterval > 0 && this.mob.getRandom().nextInt(this.randomInterval) != 0) {
/* 48 */       return false;
/*    */     }
/*    */     
/* 51 */     findTarget();
/* 52 */     return (this.target != null);
/*    */   }
/*    */   
/*    */   protected AABB getTargetSearchArea(double $$0) {
/* 56 */     return this.mob.getBoundingBox().inflate($$0, 4.0D, $$0);
/*    */   }
/*    */   
/*    */   protected void findTarget() {
/* 60 */     if (this.targetType == Player.class || this.targetType == ServerPlayer.class) {
/* 61 */       this.target = (LivingEntity)this.mob.level().getNearestPlayer(this.targetConditions, (LivingEntity)this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
/*    */     } else {
/* 63 */       this.target = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, getTargetSearchArea(getFollowDistance()), $$0 -> true), this.targetConditions, (LivingEntity)this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 69 */     this.mob.setTarget(this.target);
/* 70 */     super.start();
/*    */   }
/*    */   
/*    */   public void setTarget(@Nullable LivingEntity $$0) {
/* 74 */     this.target = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\target\NearestAttackableTargetGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */