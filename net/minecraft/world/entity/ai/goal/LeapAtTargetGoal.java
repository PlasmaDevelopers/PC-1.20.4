/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class LeapAtTargetGoal
/*    */   extends Goal {
/*    */   private final Mob mob;
/*    */   private LivingEntity target;
/*    */   private final float yd;
/*    */   
/*    */   public LeapAtTargetGoal(Mob $$0, float $$1) {
/* 16 */     this.mob = $$0;
/* 17 */     this.yd = $$1;
/* 18 */     setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 23 */     if (this.mob.hasControllingPassenger()) {
/* 24 */       return false;
/*    */     }
/* 26 */     this.target = this.mob.getTarget();
/* 27 */     if (this.target == null) {
/* 28 */       return false;
/*    */     }
/* 30 */     double $$0 = this.mob.distanceToSqr((Entity)this.target);
/* 31 */     if ($$0 < 4.0D || $$0 > 16.0D) {
/* 32 */       return false;
/*    */     }
/* 34 */     if (!this.mob.onGround()) {
/* 35 */       return false;
/*    */     }
/* 37 */     if (this.mob.getRandom().nextInt(reducedTickDelay(5)) != 0) {
/* 38 */       return false;
/*    */     }
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 45 */     return !this.mob.onGround();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void start() {
/* 51 */     Vec3 $$0 = this.mob.getDeltaMovement();
/* 52 */     Vec3 $$1 = new Vec3(this.target.getX() - this.mob.getX(), 0.0D, this.target.getZ() - this.mob.getZ());
/* 53 */     if ($$1.lengthSqr() > 1.0E-7D) {
/* 54 */       $$1 = $$1.normalize().scale(0.4D).add($$0.scale(0.2D));
/*    */     }
/*    */     
/* 57 */     this.mob.setDeltaMovement($$1.x, this.yd, $$1.z);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\LeapAtTargetGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */