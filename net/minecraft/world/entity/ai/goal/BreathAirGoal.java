/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.MoverType;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class BreathAirGoal
/*    */   extends Goal {
/*    */   private final PathfinderMob mob;
/*    */   
/*    */   public BreathAirGoal(PathfinderMob $$0) {
/* 20 */     this.mob = $$0;
/* 21 */     setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 26 */     return (this.mob.getAirSupply() < 140);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 31 */     return canUse();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInterruptable() {
/* 36 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 41 */     findAirPosition();
/*    */   }
/*    */   
/*    */   private void findAirPosition() {
/* 45 */     Iterable<BlockPos> $$0 = BlockPos.betweenClosed(
/* 46 */         Mth.floor(this.mob.getX() - 1.0D), this.mob
/* 47 */         .getBlockY(), 
/* 48 */         Mth.floor(this.mob.getZ() - 1.0D), 
/* 49 */         Mth.floor(this.mob.getX() + 1.0D), 
/* 50 */         Mth.floor(this.mob.getY() + 8.0D), 
/* 51 */         Mth.floor(this.mob.getZ() + 1.0D));
/*    */ 
/*    */     
/* 54 */     BlockPos $$1 = null;
/* 55 */     for (BlockPos $$2 : $$0) {
/* 56 */       if (givesAir((LevelReader)this.mob.level(), $$2)) {
/* 57 */         $$1 = $$2;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 62 */     if ($$1 == null) {
/* 63 */       $$1 = BlockPos.containing(this.mob.getX(), this.mob.getY() + 8.0D, this.mob.getZ());
/*    */     }
/*    */     
/* 66 */     this.mob.getNavigation().moveTo($$1.getX(), ($$1.getY() + 1), $$1.getZ(), 1.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 71 */     findAirPosition();
/*    */     
/* 73 */     this.mob.moveRelative(0.02F, new Vec3(this.mob.xxa, this.mob.yya, this.mob.zza));
/* 74 */     this.mob.move(MoverType.SELF, this.mob.getDeltaMovement());
/*    */   }
/*    */   
/*    */   private boolean givesAir(LevelReader $$0, BlockPos $$1) {
/* 78 */     BlockState $$2 = $$0.getBlockState($$1);
/* 79 */     return (($$0.getFluidState($$1).isEmpty() || $$2.is(Blocks.BUBBLE_COLUMN)) && $$2.isPathfindable((BlockGetter)$$0, $$1, PathComputationType.LAND));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\BreathAirGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */