/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TurtleTravelGoal
/*     */   extends Goal
/*     */ {
/*     */   private final Turtle turtle;
/*     */   private final double speedModifier;
/*     */   private boolean stuck;
/*     */   
/*     */   TurtleTravelGoal(Turtle $$0, double $$1) {
/* 402 */     this.turtle = $$0;
/* 403 */     this.speedModifier = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 408 */     return (!this.turtle.isGoingHome() && !this.turtle.hasEgg() && this.turtle.isInWater());
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 413 */     int $$0 = 512;
/* 414 */     int $$1 = 4;
/* 415 */     RandomSource $$2 = Turtle.access$000(this.turtle);
/* 416 */     int $$3 = $$2.nextInt(1025) - 512;
/* 417 */     int $$4 = $$2.nextInt(9) - 4;
/* 418 */     int $$5 = $$2.nextInt(1025) - 512;
/*     */     
/* 420 */     if ($$4 + this.turtle.getY() > (this.turtle.level().getSeaLevel() - 1)) {
/* 421 */       $$4 = 0;
/*     */     }
/* 423 */     BlockPos $$6 = BlockPos.containing($$3 + this.turtle.getX(), $$4 + this.turtle.getY(), $$5 + this.turtle.getZ());
/* 424 */     this.turtle.setTravelPos($$6);
/* 425 */     this.turtle.setTravelling(true);
/* 426 */     this.stuck = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 431 */     if (this.turtle.getNavigation().isDone()) {
/* 432 */       Vec3 $$0 = Vec3.atBottomCenterOf((Vec3i)this.turtle.getTravelPos());
/* 433 */       Vec3 $$1 = DefaultRandomPos.getPosTowards((PathfinderMob)this.turtle, 16, 3, $$0, 0.3141592741012573D);
/* 434 */       if ($$1 == null) {
/* 435 */         $$1 = DefaultRandomPos.getPosTowards((PathfinderMob)this.turtle, 8, 7, $$0, 1.5707963705062866D);
/*     */       }
/*     */ 
/*     */       
/* 439 */       if ($$1 != null) {
/* 440 */         int $$2 = Mth.floor($$1.x);
/* 441 */         int $$3 = Mth.floor($$1.z);
/* 442 */         int $$4 = 34;
/* 443 */         if (!this.turtle.level().hasChunksAt($$2 - 34, $$3 - 34, $$2 + 34, $$3 + 34)) {
/* 444 */           $$1 = null;
/*     */         }
/*     */       } 
/*     */       
/* 448 */       if ($$1 == null) {
/* 449 */         this.stuck = true;
/*     */         
/*     */         return;
/*     */       } 
/* 453 */       this.turtle.getNavigation().moveTo($$1.x, $$1.y, $$1.z, this.speedModifier);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/* 459 */     return (!this.turtle.getNavigation().isDone() && !this.stuck && !this.turtle.isGoingHome() && !this.turtle.isInLove() && !this.turtle.hasEgg());
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 464 */     this.turtle.setTravelling(false);
/* 465 */     super.stop();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Turtle$TurtleTravelGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */