/*    */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
/*    */ import net.minecraft.world.level.pathfinder.Path;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class DragonTakeoffPhase
/*    */   extends AbstractDragonPhaseInstance {
/*    */   private boolean firstTick;
/*    */   @Nullable
/*    */   private Path currentPath;
/*    */   @Nullable
/*    */   private Vec3 targetLocation;
/*    */   
/*    */   public DragonTakeoffPhase(EnderDragon $$0) {
/* 21 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void doServerTick() {
/* 26 */     if (this.firstTick || this.currentPath == null) {
/* 27 */       this.firstTick = false;
/* 28 */       findNewTarget();
/*    */     } else {
/* 30 */       BlockPos $$0 = this.dragon.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.getLocation(this.dragon.getFightOrigin()));
/* 31 */       if (!$$0.closerToCenterThan((Position)this.dragon.position(), 10.0D)) {
/* 32 */         this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void begin() {
/* 39 */     this.firstTick = true;
/* 40 */     this.currentPath = null;
/* 41 */     this.targetLocation = null;
/*    */   }
/*    */   
/*    */   private void findNewTarget() {
/* 45 */     int $$0 = this.dragon.findClosestNode();
/* 46 */     Vec3 $$1 = this.dragon.getHeadLookVector(1.0F);
/* 47 */     int $$2 = this.dragon.findClosestNode(-$$1.x * 40.0D, 105.0D, -$$1.z * 40.0D);
/*    */     
/* 49 */     if (this.dragon.getDragonFight() == null || this.dragon.getDragonFight().getCrystalsAlive() <= 0) {
/*    */       
/* 51 */       $$2 -= 12;
/* 52 */       $$2 &= 0x7;
/* 53 */       $$2 += 12;
/*    */     } else {
/*    */       
/* 56 */       $$2 %= 12;
/* 57 */       if ($$2 < 0) {
/* 58 */         $$2 += 12;
/*    */       }
/*    */     } 
/*    */     
/* 62 */     this.currentPath = this.dragon.findPath($$0, $$2, null);
/*    */     
/* 64 */     navigateToNextPathNode();
/*    */   }
/*    */   
/*    */   private void navigateToNextPathNode() {
/* 68 */     if (this.currentPath != null) {
/* 69 */       this.currentPath.advance();
/* 70 */       if (!this.currentPath.isDone()) {
/* 71 */         double $$1; BlockPos blockPos = this.currentPath.getNextNodePos();
/* 72 */         this.currentPath.advance();
/*    */ 
/*    */         
/*    */         do {
/* 76 */           $$1 = (blockPos.getY() + this.dragon.getRandom().nextFloat() * 20.0F);
/* 77 */         } while ($$1 < blockPos.getY());
/*    */         
/* 79 */         this.targetLocation = new Vec3(blockPos.getX(), $$1, blockPos.getZ());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Vec3 getFlyTargetLocation() {
/* 87 */     return this.targetLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnderDragonPhase<DragonTakeoffPhase> getPhase() {
/* 92 */     return EnderDragonPhase.TAKEOFF;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\DragonTakeoffPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */