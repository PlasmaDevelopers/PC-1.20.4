/*    */ package net.minecraft.world.entity.ai.navigation;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.protocol.game.DebugPackets;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
/*    */ import net.minecraft.world.level.pathfinder.NodeEvaluator;
/*    */ import net.minecraft.world.level.pathfinder.Path;
/*    */ import net.minecraft.world.level.pathfinder.PathFinder;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class FlyingPathNavigation extends PathNavigation {
/*    */   public FlyingPathNavigation(Mob $$0, Level $$1) {
/* 16 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected PathFinder createPathFinder(int $$0) {
/* 21 */     this.nodeEvaluator = (NodeEvaluator)new FlyNodeEvaluator();
/* 22 */     this.nodeEvaluator.setCanPassDoors(true);
/* 23 */     return new PathFinder(this.nodeEvaluator, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canMoveDirectly(Vec3 $$0, Vec3 $$1) {
/* 28 */     return isClearForMovementBetween(this.mob, $$0, $$1, true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canUpdatePath() {
/* 33 */     return ((canFloat() && this.mob.isInLiquid()) || !this.mob.isPassenger());
/*    */   }
/*    */ 
/*    */   
/*    */   protected Vec3 getTempMobPos() {
/* 38 */     return this.mob.position();
/*    */   }
/*    */ 
/*    */   
/*    */   public Path createPath(Entity $$0, int $$1) {
/* 43 */     return createPath($$0.blockPosition(), $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 48 */     this.tick++;
/*    */     
/* 50 */     if (this.hasDelayedRecomputation) {
/* 51 */       recomputePath();
/*    */     }
/*    */     
/* 54 */     if (isDone()) {
/*    */       return;
/*    */     }
/*    */     
/* 58 */     if (canUpdatePath()) {
/* 59 */       followThePath();
/* 60 */     } else if (this.path != null && !this.path.isDone()) {
/* 61 */       Vec3 $$0 = this.path.getNextEntityPos((Entity)this.mob);
/* 62 */       if (this.mob.getBlockX() == Mth.floor($$0.x) && this.mob.getBlockY() == Mth.floor($$0.y) && this.mob.getBlockZ() == Mth.floor($$0.z)) {
/* 63 */         this.path.advance();
/*    */       }
/*    */     } 
/*    */     
/* 67 */     DebugPackets.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
/*    */     
/* 69 */     if (isDone()) {
/*    */       return;
/*    */     }
/* 72 */     Vec3 $$1 = this.path.getNextEntityPos((Entity)this.mob);
/*    */     
/* 74 */     this.mob.getMoveControl().setWantedPosition($$1.x, $$1.y, $$1.z, this.speedModifier);
/*    */   }
/*    */   
/*    */   public void setCanOpenDoors(boolean $$0) {
/* 78 */     this.nodeEvaluator.setCanOpenDoors($$0);
/*    */   }
/*    */   
/*    */   public boolean canPassDoors() {
/* 82 */     return this.nodeEvaluator.canPassDoors();
/*    */   }
/*    */   
/*    */   public void setCanPassDoors(boolean $$0) {
/* 86 */     this.nodeEvaluator.setCanPassDoors($$0);
/*    */   }
/*    */   
/*    */   public boolean canOpenDoors() {
/* 90 */     return this.nodeEvaluator.canPassDoors();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStableDestination(BlockPos $$0) {
/* 95 */     return this.level.getBlockState($$0).entityCanStandOn((BlockGetter)this.level, $$0, (Entity)this.mob);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\navigation\FlyingPathNavigation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */