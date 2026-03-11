/*     */ package net.minecraft.world.level.pathfinder;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.PathNavigationRegion;
/*     */ 
/*     */ public class AmphibiousNodeEvaluator
/*     */   extends WalkNodeEvaluator {
/*     */   private final boolean prefersShallowSwimming;
/*     */   private float oldWalkableCost;
/*     */   private float oldWaterBorderCost;
/*     */   
/*     */   public AmphibiousNodeEvaluator(boolean $$0) {
/*  18 */     this.prefersShallowSwimming = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepare(PathNavigationRegion $$0, Mob $$1) {
/*  23 */     super.prepare($$0, $$1);
/*  24 */     $$1.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
/*  25 */     this.oldWalkableCost = $$1.getPathfindingMalus(BlockPathTypes.WALKABLE);
/*  26 */     $$1.setPathfindingMalus(BlockPathTypes.WALKABLE, 6.0F);
/*  27 */     this.oldWaterBorderCost = $$1.getPathfindingMalus(BlockPathTypes.WATER_BORDER);
/*  28 */     $$1.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 4.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/*  33 */     this.mob.setPathfindingMalus(BlockPathTypes.WALKABLE, this.oldWalkableCost);
/*  34 */     this.mob.setPathfindingMalus(BlockPathTypes.WATER_BORDER, this.oldWaterBorderCost);
/*  35 */     super.done();
/*     */   }
/*     */ 
/*     */   
/*     */   public Node getStart() {
/*  40 */     if (!this.mob.isInWater()) {
/*  41 */       return super.getStart();
/*     */     }
/*  43 */     return getStartNode(new BlockPos(Mth.floor((this.mob.getBoundingBox()).minX), Mth.floor((this.mob.getBoundingBox()).minY + 0.5D), Mth.floor((this.mob.getBoundingBox()).minZ)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Target getGoal(double $$0, double $$1, double $$2) {
/*  49 */     return getTargetFromNode(getNode(Mth.floor($$0), Mth.floor($$1 + 0.5D), Mth.floor($$2)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNeighbors(Node[] $$0, Node $$1) {
/*  55 */     int $$6, $$2 = super.getNeighbors($$0, $$1);
/*     */ 
/*     */     
/*  58 */     BlockPathTypes $$3 = getCachedBlockType(this.mob, $$1.x, $$1.y + 1, $$1.z);
/*  59 */     BlockPathTypes $$4 = getCachedBlockType(this.mob, $$1.x, $$1.y, $$1.z);
/*     */     
/*  61 */     if (this.mob.getPathfindingMalus($$3) >= 0.0F && $$4 != BlockPathTypes.STICKY_HONEY) {
/*  62 */       int $$5 = Mth.floor(Math.max(1.0F, this.mob.maxUpStep()));
/*     */     } else {
/*  64 */       $$6 = 0;
/*     */     } 
/*     */     
/*  67 */     double $$7 = getFloorLevel(new BlockPos($$1.x, $$1.y, $$1.z));
/*     */     
/*  69 */     Node $$8 = findAcceptedNode($$1.x, $$1.y + 1, $$1.z, Math.max(0, $$6 - 1), $$7, Direction.UP, $$4);
/*  70 */     Node $$9 = findAcceptedNode($$1.x, $$1.y - 1, $$1.z, $$6, $$7, Direction.DOWN, $$4);
/*     */     
/*  72 */     if (isVerticalNeighborValid($$8, $$1)) {
/*  73 */       $$0[$$2++] = $$8;
/*     */     }
/*     */     
/*  76 */     if (isVerticalNeighborValid($$9, $$1) && $$4 != BlockPathTypes.TRAPDOOR) {
/*  77 */       $$0[$$2++] = $$9;
/*     */     }
/*     */ 
/*     */     
/*  81 */     for (int $$10 = 0; $$10 < $$2; $$10++) {
/*  82 */       Node $$11 = $$0[$$10];
/*  83 */       if ($$11.type == BlockPathTypes.WATER && this.prefersShallowSwimming && $$11.y < this.mob.level().getSeaLevel() - 10) {
/*  84 */         $$11.costMalus++;
/*     */       }
/*     */     } 
/*     */     
/*  88 */     return $$2;
/*     */   }
/*     */   
/*     */   private boolean isVerticalNeighborValid(@Nullable Node $$0, Node $$1) {
/*  92 */     return (isNeighborValid($$0, $$1) && $$0.type == BlockPathTypes.WATER);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isAmphibious() {
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPathTypes getBlockPathType(BlockGetter $$0, int $$1, int $$2, int $$3) {
/* 102 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/* 103 */     BlockPathTypes $$5 = getBlockPathTypeRaw($$0, (BlockPos)$$4.set($$1, $$2, $$3));
/*     */     
/* 105 */     if ($$5 == BlockPathTypes.WATER) {
/* 106 */       for (Direction $$6 : Direction.values()) {
/* 107 */         BlockPathTypes $$7 = getBlockPathTypeRaw($$0, (BlockPos)$$4.set($$1, $$2, $$3).move($$6));
/* 108 */         if ($$7 == BlockPathTypes.BLOCKED) {
/* 109 */           return BlockPathTypes.WATER_BORDER;
/*     */         }
/*     */       } 
/*     */       
/* 113 */       return BlockPathTypes.WATER;
/*     */     } 
/*     */     
/* 116 */     return getBlockPathTypeStatic($$0, $$4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\pathfinder\AmphibiousNodeEvaluator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */