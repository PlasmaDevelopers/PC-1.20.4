/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntitySelector;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ public class LookAtPlayerGoal
/*    */   extends Goal
/*    */ {
/*    */   public static final float DEFAULT_PROBABILITY = 0.02F;
/*    */   protected final Mob mob;
/*    */   @Nullable
/*    */   protected Entity lookAt;
/*    */   protected final float lookDistance;
/*    */   private int lookTime;
/*    */   protected final float probability;
/*    */   private final boolean onlyHorizontal;
/*    */   protected final Class<? extends LivingEntity> lookAtType;
/*    */   protected final TargetingConditions lookAtContext;
/*    */   
/*    */   public LookAtPlayerGoal(Mob $$0, Class<? extends LivingEntity> $$1, float $$2) {
/* 27 */     this($$0, $$1, $$2, 0.02F);
/*    */   }
/*    */   
/*    */   public LookAtPlayerGoal(Mob $$0, Class<? extends LivingEntity> $$1, float $$2, float $$3) {
/* 31 */     this($$0, $$1, $$2, $$3, false);
/*    */   }
/*    */   
/*    */   public LookAtPlayerGoal(Mob $$0, Class<? extends LivingEntity> $$1, float $$2, float $$3, boolean $$4) {
/* 35 */     this.mob = $$0;
/* 36 */     this.lookAtType = $$1;
/* 37 */     this.lookDistance = $$2;
/* 38 */     this.probability = $$3;
/* 39 */     this.onlyHorizontal = $$4;
/* 40 */     setFlags(EnumSet.of(Goal.Flag.LOOK));
/*    */     
/* 42 */     if ($$1 == Player.class) {
/* 43 */       this.lookAtContext = TargetingConditions.forNonCombat().range($$2).selector($$1 -> EntitySelector.notRiding((Entity)$$0).test($$1));
/*    */     } else {
/* 45 */       this.lookAtContext = TargetingConditions.forNonCombat().range($$2);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 51 */     if (this.mob.getRandom().nextFloat() >= this.probability) {
/* 52 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 56 */     if (this.mob.getTarget() != null) {
/* 57 */       this.lookAt = (Entity)this.mob.getTarget();
/*    */     }
/* 59 */     if (this.lookAtType == Player.class) {
/* 60 */       this.lookAt = (Entity)this.mob.level().getNearestPlayer(this.lookAtContext, (LivingEntity)this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
/*    */     } else {
/* 62 */       this.lookAt = (Entity)this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(this.lookAtType, this.mob.getBoundingBox().inflate(this.lookDistance, 3.0D, this.lookDistance), $$0 -> true), this.lookAtContext, (LivingEntity)this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
/*    */     } 
/*    */     
/* 65 */     return (this.lookAt != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 70 */     if (!this.lookAt.isAlive()) {
/* 71 */       return false;
/*    */     }
/* 73 */     if (this.mob.distanceToSqr(this.lookAt) > (this.lookDistance * this.lookDistance)) {
/* 74 */       return false;
/*    */     }
/* 76 */     return (this.lookTime > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 81 */     this.lookTime = adjustedTickDelay(40 + this.mob.getRandom().nextInt(40));
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 86 */     this.lookAt = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 91 */     if (!this.lookAt.isAlive()) {
/*    */       return;
/*    */     }
/* 94 */     double $$0 = this.onlyHorizontal ? this.mob.getEyeY() : this.lookAt.getEyeY();
/* 95 */     this.mob.getLookControl().setLookAt(this.lookAt.getX(), $$0, this.lookAt.getZ());
/* 96 */     this.lookTime--;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\LookAtPlayerGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */