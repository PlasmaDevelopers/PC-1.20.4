/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.tags.StructureTags;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DolphinSwimToTreasureGoal
/*     */   extends Goal
/*     */ {
/*     */   private final Dolphin dolphin;
/*     */   private boolean stuck;
/*     */   
/*     */   DolphinSwimToTreasureGoal(Dolphin $$0) {
/* 515 */     this.dolphin = $$0;
/* 516 */     setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInterruptable() {
/* 521 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 526 */     return (this.dolphin.gotFish() && this.dolphin.getAirSupply() >= 100);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canContinueToUse() {
/* 531 */     BlockPos $$0 = this.dolphin.getTreasurePos();
/* 532 */     return (!BlockPos.containing($$0.getX(), this.dolphin.getY(), $$0.getZ()).closerToCenterThan((Position)this.dolphin.position(), 4.0D) && !this.stuck && this.dolphin.getAirSupply() >= 100);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 537 */     if (!(this.dolphin.level() instanceof ServerLevel)) {
/*     */       return;
/*     */     }
/* 540 */     ServerLevel $$0 = (ServerLevel)this.dolphin.level();
/* 541 */     this.stuck = false;
/* 542 */     this.dolphin.getNavigation().stop();
/*     */     
/* 544 */     BlockPos $$1 = this.dolphin.blockPosition();
/*     */     
/* 546 */     BlockPos $$2 = $$0.findNearestMapStructure(StructureTags.DOLPHIN_LOCATED, $$1, 50, false);
/* 547 */     if ($$2 != null) {
/* 548 */       this.dolphin.setTreasurePos($$2);
/*     */     } else {
/*     */       
/* 551 */       this.stuck = true;
/*     */       
/*     */       return;
/*     */     } 
/* 555 */     $$0.broadcastEntityEvent((Entity)this.dolphin, (byte)38);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 560 */     BlockPos $$0 = this.dolphin.getTreasurePos();
/* 561 */     if (BlockPos.containing($$0.getX(), this.dolphin.getY(), $$0.getZ()).closerToCenterThan((Position)this.dolphin.position(), 4.0D) || this.stuck) {
/* 562 */       this.dolphin.setGotFish(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 568 */     Level $$0 = this.dolphin.level();
/*     */     
/* 570 */     if (this.dolphin.closeToNextPos() || this.dolphin.getNavigation().isDone()) {
/* 571 */       Vec3 $$1 = Vec3.atCenterOf((Vec3i)this.dolphin.getTreasurePos());
/* 572 */       Vec3 $$2 = DefaultRandomPos.getPosTowards(this.dolphin, 16, 1, $$1, 0.39269909262657166D);
/* 573 */       if ($$2 == null) {
/* 574 */         $$2 = DefaultRandomPos.getPosTowards(this.dolphin, 8, 4, $$1, 1.5707963705062866D);
/*     */       }
/*     */       
/* 577 */       if ($$2 != null) {
/* 578 */         BlockPos $$3 = BlockPos.containing((Position)$$2);
/* 579 */         if (!$$0.getFluidState($$3).is(FluidTags.WATER) || !$$0.getBlockState($$3).isPathfindable((BlockGetter)$$0, $$3, PathComputationType.WATER)) {
/* 580 */           $$2 = DefaultRandomPos.getPosTowards(this.dolphin, 8, 5, $$1, 1.5707963705062866D);
/*     */         }
/*     */       } 
/*     */       
/* 584 */       if ($$2 == null) {
/* 585 */         this.stuck = true;
/*     */         
/*     */         return;
/*     */       } 
/* 589 */       this.dolphin.getLookControl().setLookAt($$2.x, $$2.y, $$2.z, (this.dolphin.getMaxHeadYRot() + 20), this.dolphin.getMaxHeadXRot());
/* 590 */       this.dolphin.getNavigation().moveTo($$2.x, $$2.y, $$2.z, 1.3D);
/*     */       
/* 592 */       if ($$0.random.nextInt(adjustedTickDelay(80)) == 0)
/* 593 */         $$0.broadcastEntityEvent((Entity)this.dolphin, (byte)38); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Dolphin$DolphinSwimToTreasureGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */