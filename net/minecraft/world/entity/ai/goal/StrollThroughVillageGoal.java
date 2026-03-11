/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*    */ import net.minecraft.world.entity.ai.util.LandRandomPos;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class StrollThroughVillageGoal extends Goal {
/*    */   private static final int DISTANCE_THRESHOLD = 10;
/*    */   private final PathfinderMob mob;
/*    */   private final int interval;
/*    */   @Nullable
/*    */   private BlockPos wantedPos;
/*    */   
/*    */   public StrollThroughVillageGoal(PathfinderMob $$0, int $$1) {
/* 25 */     this.mob = $$0;
/* 26 */     this.interval = reducedTickDelay($$1);
/* 27 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 32 */     if (this.mob.hasControllingPassenger()) {
/* 33 */       return false;
/*    */     }
/*    */     
/* 36 */     if (this.mob.level().isDay()) {
/* 37 */       return false;
/*    */     }
/*    */     
/* 40 */     if (this.mob.getRandom().nextInt(this.interval) != 0) {
/* 41 */       return false;
/*    */     }
/*    */     
/* 44 */     ServerLevel $$0 = (ServerLevel)this.mob.level();
/*    */     
/* 46 */     BlockPos $$1 = this.mob.blockPosition();
/* 47 */     if (!$$0.isCloseToVillage($$1, 6)) {
/* 48 */       return false;
/*    */     }
/*    */     
/* 51 */     Vec3 $$2 = LandRandomPos.getPos(this.mob, 15, 7, $$1 -> -$$0.sectionsToVillage(SectionPos.of($$1)));
/* 52 */     this.wantedPos = ($$2 == null) ? null : BlockPos.containing((Position)$$2);
/* 53 */     return (this.wantedPos != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 58 */     return (this.wantedPos != null && !this.mob.getNavigation().isDone() && this.mob.getNavigation().getTargetPos().equals(this.wantedPos));
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 63 */     if (this.wantedPos == null) {
/*    */       return;
/*    */     }
/* 66 */     PathNavigation $$0 = this.mob.getNavigation();
/* 67 */     if ($$0.isDone() && 
/* 68 */       !this.wantedPos.closerToCenterThan((Position)this.mob.position(), 10.0D)) {
/* 69 */       Vec3 $$1 = Vec3.atBottomCenterOf((Vec3i)this.wantedPos);
/*    */ 
/*    */       
/* 72 */       Vec3 $$2 = this.mob.position();
/* 73 */       Vec3 $$3 = $$2.subtract($$1);
/*    */       
/* 75 */       $$1 = $$3.scale(0.4D).add($$1);
/*    */       
/* 77 */       Vec3 $$4 = $$1.subtract($$2).normalize().scale(10.0D).add($$2);
/* 78 */       BlockPos $$5 = BlockPos.containing((Position)$$4);
/* 79 */       $$5 = this.mob.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, $$5);
/*    */       
/* 81 */       if (!$$0.moveTo($$5.getX(), $$5.getY(), $$5.getZ(), 1.0D))
/*    */       {
/* 83 */         moveRandomly();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void moveRandomly() {
/* 90 */     RandomSource $$0 = this.mob.getRandom();
/* 91 */     BlockPos $$1 = this.mob.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.mob.blockPosition().offset(-8 + $$0.nextInt(16), 0, -8 + $$0.nextInt(16)));
/* 92 */     this.mob.getNavigation().moveTo($$1.getX(), $$1.getY(), $$1.getZ(), 1.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\StrollThroughVillageGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */