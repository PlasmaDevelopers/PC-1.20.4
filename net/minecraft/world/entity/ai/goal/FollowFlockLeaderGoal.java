/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import com.mojang.datafixers.DataFixUtils;
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.entity.animal.AbstractSchoolingFish;
/*    */ 
/*    */ public class FollowFlockLeaderGoal
/*    */   extends Goal
/*    */ {
/*    */   private static final int INTERVAL_TICKS = 200;
/*    */   private final AbstractSchoolingFish mob;
/*    */   private int timeToRecalcPath;
/*    */   private int nextStartTick;
/*    */   
/*    */   public FollowFlockLeaderGoal(AbstractSchoolingFish $$0) {
/* 17 */     this.mob = $$0;
/* 18 */     this.nextStartTick = nextStartTick($$0);
/*    */   }
/*    */   
/*    */   protected int nextStartTick(AbstractSchoolingFish $$0) {
/* 22 */     return reducedTickDelay(200 + $$0.getRandom().nextInt(200) % 20);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 27 */     if (this.mob.hasFollowers()) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     if (this.mob.isFollower()) {
/* 32 */       return true;
/*    */     }
/*    */     
/* 35 */     if (this.nextStartTick > 0) {
/* 36 */       this.nextStartTick--;
/* 37 */       return false;
/*    */     } 
/*    */     
/* 40 */     this.nextStartTick = nextStartTick(this.mob);
/*    */     
/* 42 */     Predicate<AbstractSchoolingFish> $$0 = $$0 -> ($$0.canBeFollowed() || !$$0.isFollower());
/* 43 */     List<? extends AbstractSchoolingFish> $$1 = this.mob.level().getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), $$0);
/*    */     
/* 45 */     AbstractSchoolingFish $$2 = (AbstractSchoolingFish)DataFixUtils.orElse($$1.stream().filter(AbstractSchoolingFish::canBeFollowed).findAny(), this.mob);
/*    */     
/* 47 */     $$2.addFollowers($$1.stream().filter($$0 -> !$$0.isFollower()));
/*    */     
/* 49 */     return this.mob.isFollower();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 54 */     return (this.mob.isFollower() && this.mob.inRangeOfLeader());
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 59 */     this.timeToRecalcPath = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 64 */     this.mob.stopFollowing();
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 69 */     if (--this.timeToRecalcPath > 0) {
/*    */       return;
/*    */     }
/* 72 */     this.timeToRecalcPath = adjustedTickDelay(10);
/*    */     
/* 74 */     this.mob.pathToLeader();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\FollowFlockLeaderGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */