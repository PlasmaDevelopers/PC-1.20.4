/*    */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
/*    */ import net.minecraft.world.level.pathfinder.Node;
/*    */ import net.minecraft.world.level.pathfinder.Path;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class DragonLandingApproachPhase
/*    */   extends AbstractDragonPhaseInstance {
/* 17 */   private static final TargetingConditions NEAR_EGG_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight();
/*    */   
/*    */   @Nullable
/*    */   private Path currentPath;
/*    */   @Nullable
/*    */   private Vec3 targetLocation;
/*    */   
/*    */   public DragonLandingApproachPhase(EnderDragon $$0) {
/* 25 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnderDragonPhase<DragonLandingApproachPhase> getPhase() {
/* 30 */     return EnderDragonPhase.LANDING_APPROACH;
/*    */   }
/*    */ 
/*    */   
/*    */   public void begin() {
/* 35 */     this.currentPath = null;
/* 36 */     this.targetLocation = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doServerTick() {
/* 41 */     double $$0 = (this.targetLocation == null) ? 0.0D : this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
/* 42 */     if ($$0 < 100.0D || $$0 > 22500.0D || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
/* 43 */       findNewTarget();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Vec3 getFlyTargetLocation() {
/* 50 */     return this.targetLocation;
/*    */   }
/*    */   
/*    */   private void findNewTarget() {
/* 54 */     if (this.currentPath == null || this.currentPath.isDone()) {
/* 55 */       int $$5, $$0 = this.dragon.findClosestNode();
/* 56 */       BlockPos $$1 = this.dragon.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.getLocation(this.dragon.getFightOrigin()));
/* 57 */       Player $$2 = this.dragon.level().getNearestPlayer(NEAR_EGG_TARGETING, (LivingEntity)this.dragon, $$1.getX(), $$1.getY(), $$1.getZ());
/*    */ 
/*    */       
/* 60 */       if ($$2 != null) {
/* 61 */         Vec3 $$3 = (new Vec3($$2.getX(), 0.0D, $$2.getZ())).normalize();
/* 62 */         int $$4 = this.dragon.findClosestNode(-$$3.x * 40.0D, 105.0D, -$$3.z * 40.0D);
/*    */       } else {
/* 64 */         $$5 = this.dragon.findClosestNode(40.0D, $$1.getY(), 0.0D);
/*    */       } 
/*    */       
/* 67 */       Node $$6 = new Node($$1.getX(), $$1.getY(), $$1.getZ());
/*    */       
/* 69 */       this.currentPath = this.dragon.findPath($$0, $$5, $$6);
/*    */       
/* 71 */       if (this.currentPath != null) {
/* 72 */         this.currentPath.advance();
/*    */       }
/*    */     } 
/*    */     
/* 76 */     navigateToNextPathNode();
/*    */     
/* 78 */     if (this.currentPath != null && this.currentPath.isDone()) {
/* 79 */       this.dragon.getPhaseManager().setPhase(EnderDragonPhase.LANDING);
/*    */     }
/*    */   }
/*    */   
/*    */   private void navigateToNextPathNode() {
/* 84 */     if (this.currentPath != null && !this.currentPath.isDone()) {
/* 85 */       double $$3; BlockPos blockPos = this.currentPath.getNextNodePos();
/*    */       
/* 87 */       this.currentPath.advance();
/* 88 */       double $$1 = blockPos.getX();
/* 89 */       double $$2 = blockPos.getZ();
/*    */ 
/*    */       
/*    */       do {
/* 93 */         $$3 = (blockPos.getY() + this.dragon.getRandom().nextFloat() * 20.0F);
/* 94 */       } while ($$3 < blockPos.getY());
/*    */       
/* 96 */       this.targetLocation = new Vec3($$1, $$3, $$2);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\DragonLandingApproachPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */