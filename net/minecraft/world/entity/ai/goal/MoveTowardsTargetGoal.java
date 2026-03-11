/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class MoveTowardsTargetGoal
/*    */   extends Goal {
/*    */   private final PathfinderMob mob;
/*    */   @Nullable
/*    */   private LivingEntity target;
/*    */   private double wantedX;
/*    */   private double wantedY;
/*    */   private double wantedZ;
/*    */   private final double speedModifier;
/*    */   private final float within;
/*    */   
/*    */   public MoveTowardsTargetGoal(PathfinderMob $$0, double $$1, float $$2) {
/* 23 */     this.mob = $$0;
/* 24 */     this.speedModifier = $$1;
/* 25 */     this.within = $$2;
/* 26 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 31 */     this.target = this.mob.getTarget();
/* 32 */     if (this.target == null) {
/* 33 */       return false;
/*    */     }
/* 35 */     if (this.target.distanceToSqr((Entity)this.mob) > (this.within * this.within)) {
/* 36 */       return false;
/*    */     }
/* 38 */     Vec3 $$0 = DefaultRandomPos.getPosTowards(this.mob, 16, 7, this.target.position(), 1.5707963705062866D);
/* 39 */     if ($$0 == null) {
/* 40 */       return false;
/*    */     }
/* 42 */     this.wantedX = $$0.x;
/* 43 */     this.wantedY = $$0.y;
/* 44 */     this.wantedZ = $$0.z;
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 50 */     return (!this.mob.getNavigation().isDone() && this.target.isAlive() && this.target.distanceToSqr((Entity)this.mob) < (this.within * this.within));
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 55 */     this.target = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 60 */     this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\MoveTowardsTargetGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */