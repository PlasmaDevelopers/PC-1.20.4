/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class MoveTowardsRestrictionGoal
/*    */   extends Goal {
/*    */   private final PathfinderMob mob;
/*    */   private double wantedX;
/*    */   private double wantedY;
/*    */   private double wantedZ;
/*    */   private final double speedModifier;
/*    */   
/*    */   public MoveTowardsRestrictionGoal(PathfinderMob $$0, double $$1) {
/* 18 */     this.mob = $$0;
/* 19 */     this.speedModifier = $$1;
/* 20 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 25 */     if (this.mob.isWithinRestriction()) {
/* 26 */       return false;
/*    */     }
/* 28 */     Vec3 $$0 = DefaultRandomPos.getPosTowards(this.mob, 16, 7, Vec3.atBottomCenterOf((Vec3i)this.mob.getRestrictCenter()), 1.5707963705062866D);
/* 29 */     if ($$0 == null) {
/* 30 */       return false;
/*    */     }
/* 32 */     this.wantedX = $$0.x;
/* 33 */     this.wantedY = $$0.y;
/* 34 */     this.wantedZ = $$0.z;
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 40 */     return !this.mob.getNavigation().isDone();
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 45 */     this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\MoveTowardsRestrictionGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */