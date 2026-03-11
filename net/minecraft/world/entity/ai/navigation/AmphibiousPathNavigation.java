/*    */ package net.minecraft.world.entity.ai.navigation;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
/*    */ import net.minecraft.world.level.pathfinder.NodeEvaluator;
/*    */ import net.minecraft.world.level.pathfinder.PathFinder;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class AmphibiousPathNavigation extends PathNavigation {
/*    */   public AmphibiousPathNavigation(Mob $$0, Level $$1) {
/* 12 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected PathFinder createPathFinder(int $$0) {
/* 17 */     this.nodeEvaluator = (NodeEvaluator)new AmphibiousNodeEvaluator(false);
/* 18 */     this.nodeEvaluator.setCanPassDoors(true);
/* 19 */     return new PathFinder(this.nodeEvaluator, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canUpdatePath() {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Vec3 getTempMobPos() {
/* 29 */     return new Vec3(this.mob.getX(), this.mob.getY(0.5D), this.mob.getZ());
/*    */   }
/*    */ 
/*    */   
/*    */   protected double getGroundY(Vec3 $$0) {
/* 34 */     return $$0.y;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canMoveDirectly(Vec3 $$0, Vec3 $$1) {
/* 39 */     if (this.mob.isInLiquid()) {
/* 40 */       return isClearForMovementBetween(this.mob, $$0, $$1, false);
/*    */     }
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStableDestination(BlockPos $$0) {
/* 47 */     return !this.level.getBlockState($$0.below()).isAir();
/*    */   }
/*    */   
/*    */   public void setCanFloat(boolean $$0) {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\navigation\AmphibiousPathNavigation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */