/*     */ package net.minecraft.world.entity.ai.navigation;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.level.pathfinder.Node;
/*     */ import net.minecraft.world.level.pathfinder.NodeEvaluator;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.level.pathfinder.PathFinder;
/*     */ import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class GroundPathNavigation
/*     */   extends PathNavigation {
/*     */   public GroundPathNavigation(Mob $$0, Level $$1) {
/*  23 */     super($$0, $$1);
/*     */   }
/*     */   private boolean avoidSun;
/*     */   
/*     */   protected PathFinder createPathFinder(int $$0) {
/*  28 */     this.nodeEvaluator = (NodeEvaluator)new WalkNodeEvaluator();
/*  29 */     this.nodeEvaluator.setCanPassDoors(true);
/*  30 */     return new PathFinder(this.nodeEvaluator, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canUpdatePath() {
/*  35 */     return (this.mob.onGround() || this.mob.isInLiquid() || this.mob.isPassenger());
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vec3 getTempMobPos() {
/*  40 */     return new Vec3(this.mob.getX(), getSurfaceY(), this.mob.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public Path createPath(BlockPos $$0, int $$1) {
/*  45 */     LevelChunk $$2 = this.level.getChunkSource().getChunkNow(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()));
/*  46 */     if ($$2 == null) {
/*  47 */       return null;
/*     */     }
/*  49 */     if ($$2.getBlockState($$0).isAir()) {
/*  50 */       BlockPos $$3 = $$0.below();
/*  51 */       while ($$3.getY() > this.level.getMinBuildHeight() && $$2.getBlockState($$3).isAir()) {
/*  52 */         $$3 = $$3.below();
/*     */       }
/*     */       
/*  55 */       if ($$3.getY() > this.level.getMinBuildHeight()) {
/*  56 */         return super.createPath($$3.above(), $$1);
/*     */       }
/*     */       
/*  59 */       while ($$3.getY() < this.level.getMaxBuildHeight() && $$2.getBlockState($$3).isAir()) {
/*  60 */         $$3 = $$3.above();
/*     */       }
/*  62 */       $$0 = $$3;
/*     */     } 
/*     */     
/*  65 */     if ($$2.getBlockState($$0).isSolid()) {
/*  66 */       BlockPos $$4 = $$0.above();
/*  67 */       while ($$4.getY() < this.level.getMaxBuildHeight() && $$2.getBlockState($$4).isSolid()) {
/*  68 */         $$4 = $$4.above();
/*     */       }
/*  70 */       return super.createPath($$4, $$1);
/*     */     } 
/*     */     
/*  73 */     return super.createPath($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Path createPath(Entity $$0, int $$1) {
/*  78 */     return createPath($$0.blockPosition(), $$1);
/*     */   }
/*     */   
/*     */   private int getSurfaceY() {
/*  82 */     if (!this.mob.isInWater() || !canFloat()) {
/*  83 */       return Mth.floor(this.mob.getY() + 0.5D);
/*     */     }
/*     */ 
/*     */     
/*  87 */     int $$0 = this.mob.getBlockY();
/*  88 */     BlockState $$1 = this.level.getBlockState(BlockPos.containing(this.mob.getX(), $$0, this.mob.getZ()));
/*  89 */     int $$2 = 0;
/*  90 */     while ($$1.is(Blocks.WATER)) {
/*  91 */       $$0++;
/*  92 */       $$1 = this.level.getBlockState(BlockPos.containing(this.mob.getX(), $$0, this.mob.getZ()));
/*  93 */       if (++$$2 > 16) {
/*  94 */         return this.mob.getBlockY();
/*     */       }
/*     */     } 
/*  97 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void trimPath() {
/* 102 */     super.trimPath();
/*     */     
/* 104 */     if (this.avoidSun) {
/* 105 */       if (this.level.canSeeSky(BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5D, this.mob.getZ()))) {
/*     */         return;
/*     */       }
/*     */       
/* 109 */       for (int $$0 = 0; $$0 < this.path.getNodeCount(); $$0++) {
/* 110 */         Node $$1 = this.path.getNode($$0);
/* 111 */         if (this.level.canSeeSky(new BlockPos($$1.x, $$1.y, $$1.z))) {
/* 112 */           this.path.truncateNodes($$0);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean hasValidPathType(BlockPathTypes $$0) {
/* 120 */     if ($$0 == BlockPathTypes.WATER) {
/* 121 */       return false;
/*     */     }
/*     */     
/* 124 */     if ($$0 == BlockPathTypes.LAVA) {
/* 125 */       return false;
/*     */     }
/*     */     
/* 128 */     if ($$0 == BlockPathTypes.OPEN) {
/* 129 */       return false;
/*     */     }
/*     */     
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   public void setCanOpenDoors(boolean $$0) {
/* 136 */     this.nodeEvaluator.setCanOpenDoors($$0);
/*     */   }
/*     */   
/*     */   public boolean canPassDoors() {
/* 140 */     return this.nodeEvaluator.canPassDoors();
/*     */   }
/*     */   
/*     */   public void setCanPassDoors(boolean $$0) {
/* 144 */     this.nodeEvaluator.setCanPassDoors($$0);
/*     */   }
/*     */   
/*     */   public boolean canOpenDoors() {
/* 148 */     return this.nodeEvaluator.canPassDoors();
/*     */   }
/*     */   
/*     */   public void setAvoidSun(boolean $$0) {
/* 152 */     this.avoidSun = $$0;
/*     */   }
/*     */   
/*     */   public void setCanWalkOverFences(boolean $$0) {
/* 156 */     this.nodeEvaluator.setCanWalkOverFences($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\navigation\GroundPathNavigation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */