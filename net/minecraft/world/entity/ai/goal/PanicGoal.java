/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class PanicGoal
/*    */   extends Goal
/*    */ {
/*    */   public static final int WATER_CHECK_DISTANCE_VERTICAL = 1;
/*    */   protected final PathfinderMob mob;
/*    */   protected final double speedModifier;
/*    */   protected double posX;
/*    */   protected double posY;
/*    */   protected double posZ;
/*    */   protected boolean isRunning;
/*    */   
/*    */   public PanicGoal(PathfinderMob $$0, double $$1) {
/* 25 */     this.mob = $$0;
/* 26 */     this.speedModifier = $$1;
/* 27 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 32 */     if (!shouldPanic()) {
/* 33 */       return false;
/*    */     }
/*    */     
/* 36 */     if (this.mob.isOnFire()) {
/* 37 */       BlockPos $$0 = lookForWater((BlockGetter)this.mob.level(), (Entity)this.mob, 5);
/* 38 */       if ($$0 != null) {
/* 39 */         this.posX = $$0.getX();
/* 40 */         this.posY = $$0.getY();
/* 41 */         this.posZ = $$0.getZ();
/*    */         
/* 43 */         return true;
/*    */       } 
/*    */     } 
/* 46 */     return findRandomPosition();
/*    */   }
/*    */   
/*    */   protected boolean shouldPanic() {
/* 50 */     return (this.mob.getLastHurtByMob() != null || this.mob.isFreezing() || this.mob.isOnFire());
/*    */   }
/*    */   
/*    */   protected boolean findRandomPosition() {
/* 54 */     Vec3 $$0 = DefaultRandomPos.getPos(this.mob, 5, 4);
/* 55 */     if ($$0 == null) {
/* 56 */       return false;
/*    */     }
/* 58 */     this.posX = $$0.x;
/* 59 */     this.posY = $$0.y;
/* 60 */     this.posZ = $$0.z;
/*    */     
/* 62 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isRunning() {
/* 66 */     return this.isRunning;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 71 */     this.mob.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
/* 72 */     this.isRunning = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 77 */     this.isRunning = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 82 */     return !this.mob.getNavigation().isDone();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected BlockPos lookForWater(BlockGetter $$0, Entity $$1, int $$2) {
/* 87 */     BlockPos $$3 = $$1.blockPosition();
/* 88 */     if (!$$0.getBlockState($$3).getCollisionShape($$0, $$3).isEmpty()) {
/* 89 */       return null;
/*    */     }
/* 91 */     return BlockPos.findClosestMatch($$1.blockPosition(), $$2, 1, $$1 -> $$0.getFluidState($$1).is(FluidTags.WATER)).orElse(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\PanicGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */