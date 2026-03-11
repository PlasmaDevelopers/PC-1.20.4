/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class FleeSunGoal extends Goal {
/*    */   protected final PathfinderMob mob;
/*    */   private double wantedX;
/*    */   private double wantedY;
/*    */   private double wantedZ;
/*    */   private final double speedModifier;
/*    */   private final Level level;
/*    */   
/*    */   public FleeSunGoal(PathfinderMob $$0, double $$1) {
/* 22 */     this.mob = $$0;
/* 23 */     this.speedModifier = $$1;
/* 24 */     this.level = $$0.level();
/* 25 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 30 */     if (this.mob.getTarget() != null) {
/* 31 */       return false;
/*    */     }
/* 33 */     if (!this.level.isDay()) {
/* 34 */       return false;
/*    */     }
/* 36 */     if (!this.mob.isOnFire()) {
/* 37 */       return false;
/*    */     }
/* 39 */     if (!this.level.canSeeSky(this.mob.blockPosition())) {
/* 40 */       return false;
/*    */     }
/* 42 */     if (!this.mob.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
/* 43 */       return false;
/*    */     }
/*    */     
/* 46 */     return setWantedPos();
/*    */   }
/*    */   
/*    */   protected boolean setWantedPos() {
/* 50 */     Vec3 $$0 = getHidePos();
/* 51 */     if ($$0 == null) {
/* 52 */       return false;
/*    */     }
/* 54 */     this.wantedX = $$0.x;
/* 55 */     this.wantedY = $$0.y;
/* 56 */     this.wantedZ = $$0.z;
/* 57 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 62 */     return !this.mob.getNavigation().isDone();
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 67 */     this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected Vec3 getHidePos() {
/* 72 */     RandomSource $$0 = this.mob.getRandom();
/* 73 */     BlockPos $$1 = this.mob.blockPosition();
/*    */     
/* 75 */     for (int $$2 = 0; $$2 < 10; $$2++) {
/* 76 */       BlockPos $$3 = $$1.offset($$0.nextInt(20) - 10, $$0.nextInt(6) - 3, $$0.nextInt(20) - 10);
/*    */       
/* 78 */       if (!this.level.canSeeSky($$3) && this.mob.getWalkTargetValue($$3) < 0.0F) {
/* 79 */         return Vec3.atBottomCenterOf((Vec3i)$$3);
/*    */       }
/*    */     } 
/* 82 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\FleeSunGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */