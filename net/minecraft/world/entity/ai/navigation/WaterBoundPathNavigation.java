/*    */ package net.minecraft.world.entity.ai.navigation;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.pathfinder.NodeEvaluator;
/*    */ import net.minecraft.world.level.pathfinder.PathFinder;
/*    */ import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class WaterBoundPathNavigation extends PathNavigation {
/*    */   public WaterBoundPathNavigation(Mob $$0, Level $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected PathFinder createPathFinder(int $$0) {
/* 20 */     this.allowBreaching = (this.mob.getType() == EntityType.DOLPHIN);
/* 21 */     this.nodeEvaluator = (NodeEvaluator)new SwimNodeEvaluator(this.allowBreaching);
/* 22 */     return new PathFinder(this.nodeEvaluator, $$0);
/*    */   }
/*    */   private boolean allowBreaching;
/*    */   
/*    */   protected boolean canUpdatePath() {
/* 27 */     return (this.allowBreaching || this.mob.isInLiquid());
/*    */   }
/*    */ 
/*    */   
/*    */   protected Vec3 getTempMobPos() {
/* 32 */     return new Vec3(this.mob.getX(), this.mob.getY(0.5D), this.mob.getZ());
/*    */   }
/*    */ 
/*    */   
/*    */   protected double getGroundY(Vec3 $$0) {
/* 37 */     return $$0.y;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canMoveDirectly(Vec3 $$0, Vec3 $$1) {
/* 42 */     return isClearForMovementBetween(this.mob, $$0, $$1, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStableDestination(BlockPos $$0) {
/* 47 */     return !this.level.getBlockState($$0).isSolidRender((BlockGetter)this.level, $$0);
/*    */   }
/*    */   
/*    */   public void setCanFloat(boolean $$0) {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\navigation\WaterBoundPathNavigation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */