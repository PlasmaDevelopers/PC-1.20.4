/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class RandomStrollGoal
/*    */   extends Goal
/*    */ {
/*    */   public static final int DEFAULT_INTERVAL = 120;
/*    */   protected final PathfinderMob mob;
/*    */   protected double wantedX;
/*    */   protected double wantedY;
/*    */   protected double wantedZ;
/*    */   protected final double speedModifier;
/*    */   protected int interval;
/*    */   protected boolean forceTrigger;
/*    */   private final boolean checkNoActionTime;
/*    */   
/*    */   public RandomStrollGoal(PathfinderMob $$0, double $$1) {
/* 23 */     this($$0, $$1, 120);
/*    */   }
/*    */   
/*    */   public RandomStrollGoal(PathfinderMob $$0, double $$1, int $$2) {
/* 27 */     this($$0, $$1, $$2, true);
/*    */   }
/*    */   
/*    */   public RandomStrollGoal(PathfinderMob $$0, double $$1, int $$2, boolean $$3) {
/* 31 */     this.mob = $$0;
/* 32 */     this.speedModifier = $$1;
/* 33 */     this.interval = $$2;
/* 34 */     this.checkNoActionTime = $$3;
/* 35 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 40 */     if (this.mob.hasControllingPassenger()) {
/* 41 */       return false;
/*    */     }
/* 43 */     if (!this.forceTrigger) {
/* 44 */       if (this.checkNoActionTime && this.mob.getNoActionTime() >= 100) {
/* 45 */         return false;
/*    */       }
/* 47 */       if (this.mob.getRandom().nextInt(reducedTickDelay(this.interval)) != 0) {
/* 48 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 52 */     Vec3 $$0 = getPosition();
/*    */     
/* 54 */     if ($$0 == null) {
/* 55 */       return false;
/*    */     }
/*    */     
/* 58 */     this.wantedX = $$0.x;
/* 59 */     this.wantedY = $$0.y;
/* 60 */     this.wantedZ = $$0.z;
/* 61 */     this.forceTrigger = false;
/* 62 */     return true;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected Vec3 getPosition() {
/* 67 */     return DefaultRandomPos.getPos(this.mob, 10, 7);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 72 */     return (!this.mob.getNavigation().isDone() && !this.mob.hasControllingPassenger());
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 77 */     this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 82 */     this.mob.getNavigation().stop();
/* 83 */     super.stop();
/*    */   }
/*    */   
/*    */   public void trigger() {
/* 87 */     this.forceTrigger = true;
/*    */   }
/*    */   
/*    */   public void setInterval(int $$0) {
/* 91 */     this.interval = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\RandomStrollGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */