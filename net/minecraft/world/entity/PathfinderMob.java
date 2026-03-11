/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.ai.goal.Goal;
/*    */ import net.minecraft.world.entity.ai.goal.WrappedGoal;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public abstract class PathfinderMob extends Mob {
/*    */   protected static final float DEFAULT_WALK_TARGET_VALUE = 0.0F;
/*    */   
/*    */   protected PathfinderMob(EntityType<? extends PathfinderMob> $$0, Level $$1) {
/* 16 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public float getWalkTargetValue(BlockPos $$0) {
/* 20 */     return getWalkTargetValue($$0, (LevelReader)level());
/*    */   }
/*    */   
/*    */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/* 24 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkSpawnRules(LevelAccessor $$0, MobSpawnType $$1) {
/* 29 */     return (getWalkTargetValue(blockPosition(), (LevelReader)$$0) >= 0.0F);
/*    */   }
/*    */   
/*    */   public boolean isPathFinding() {
/* 33 */     return !getNavigation().isDone();
/*    */   }
/*    */   
/*    */   public boolean isPanicking() {
/* 37 */     if (this.brain.hasMemoryValue(MemoryModuleType.IS_PANICKING)) {
/* 38 */       return this.brain.getMemory(MemoryModuleType.IS_PANICKING).isPresent();
/*    */     }
/* 40 */     return this.goalSelector.getRunningGoals().anyMatch($$0 -> $$0.getGoal() instanceof net.minecraft.world.entity.ai.goal.PanicGoal);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void tickLeash() {
/* 46 */     super.tickLeash();
/*    */     
/* 48 */     Entity $$0 = getLeashHolder();
/* 49 */     if ($$0 != null && $$0.level() == level()) {
/*    */       
/* 51 */       restrictTo($$0.blockPosition(), 5);
/*    */       
/* 53 */       float $$1 = distanceTo($$0);
/*    */       
/* 55 */       if (this instanceof TamableAnimal && ((TamableAnimal)this).isInSittingPose()) {
/* 56 */         if ($$1 > 10.0F) {
/* 57 */           dropLeash(true, true);
/*    */         }
/*    */         
/*    */         return;
/*    */       } 
/* 62 */       onLeashDistance($$1);
/*    */       
/* 64 */       if ($$1 > 10.0F) {
/* 65 */         dropLeash(true, true);
/* 66 */         this.goalSelector.disableControlFlag(Goal.Flag.MOVE);
/* 67 */       } else if ($$1 > 6.0F) {
/*    */         
/* 69 */         double $$2 = ($$0.getX() - getX()) / $$1;
/* 70 */         double $$3 = ($$0.getY() - getY()) / $$1;
/* 71 */         double $$4 = ($$0.getZ() - getZ()) / $$1;
/*    */         
/* 73 */         setDeltaMovement(getDeltaMovement().add(
/* 74 */               Math.copySign($$2 * $$2 * 0.4D, $$2), 
/* 75 */               Math.copySign($$3 * $$3 * 0.4D, $$3), 
/* 76 */               Math.copySign($$4 * $$4 * 0.4D, $$4)));
/*    */         
/* 78 */         checkSlowFallDistance();
/* 79 */       } else if (shouldStayCloseToLeashHolder() && !isPanicking()) {
/* 80 */         this.goalSelector.enableControlFlag(Goal.Flag.MOVE);
/* 81 */         float $$5 = 2.0F;
/*    */         
/* 83 */         Vec3 $$6 = (new Vec3($$0.getX() - getX(), $$0.getY() - getY(), $$0.getZ() - getZ())).normalize().scale(Math.max($$1 - 2.0F, 0.0F));
/* 84 */         getNavigation().moveTo(getX() + $$6.x, getY() + $$6.y, getZ() + $$6.z, followLeashSpeed());
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected boolean shouldStayCloseToLeashHolder() {
/* 90 */     return true;
/*    */   }
/*    */   
/*    */   protected double followLeashSpeed() {
/* 94 */     return 1.0D;
/*    */   }
/*    */   
/*    */   protected void onLeashDistance(float $$0) {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\PathfinderMob.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */