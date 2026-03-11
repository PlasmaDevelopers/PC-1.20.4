/*     */ package net.minecraft.world.level.pathfinder;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.PathNavigationRegion;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ 
/*     */ public class SwimNodeEvaluator
/*     */   extends NodeEvaluator {
/*     */   private final boolean allowBreaching;
/*  21 */   private final Long2ObjectMap<BlockPathTypes> pathTypesByPosCache = (Long2ObjectMap<BlockPathTypes>)new Long2ObjectOpenHashMap();
/*     */   
/*     */   public SwimNodeEvaluator(boolean $$0) {
/*  24 */     this.allowBreaching = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepare(PathNavigationRegion $$0, Mob $$1) {
/*  29 */     super.prepare($$0, $$1);
/*  30 */     this.pathTypesByPosCache.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/*  35 */     super.done();
/*  36 */     this.pathTypesByPosCache.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Node getStart() {
/*  41 */     return getNode(Mth.floor((this.mob.getBoundingBox()).minX), Mth.floor((this.mob.getBoundingBox()).minY + 0.5D), Mth.floor((this.mob.getBoundingBox()).minZ));
/*     */   }
/*     */ 
/*     */   
/*     */   public Target getGoal(double $$0, double $$1, double $$2) {
/*  46 */     return getTargetFromNode(getNode(Mth.floor($$0), Mth.floor($$1), Mth.floor($$2)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNeighbors(Node[] $$0, Node $$1) {
/*  51 */     int $$2 = 0;
/*     */     
/*  53 */     Map<Direction, Node> $$3 = Maps.newEnumMap(Direction.class);
/*     */     
/*  55 */     for (Direction $$4 : Direction.values()) {
/*  56 */       Node $$5 = findAcceptedNode($$1.x + $$4.getStepX(), $$1.y + $$4.getStepY(), $$1.z + $$4.getStepZ());
/*  57 */       $$3.put($$4, $$5);
/*  58 */       if (isNodeValid($$5)) {
/*  59 */         $$0[$$2++] = $$5;
/*     */       }
/*     */     } 
/*     */     
/*  63 */     for (Direction $$6 : Direction.Plane.HORIZONTAL) {
/*  64 */       Direction $$7 = $$6.getClockWise();
/*  65 */       Node $$8 = findAcceptedNode($$1.x + $$6.getStepX() + $$7.getStepX(), $$1.y, $$1.z + $$6.getStepZ() + $$7.getStepZ());
/*  66 */       if (isDiagonalNodeValid($$8, $$3.get($$6), $$3.get($$7))) {
/*  67 */         $$0[$$2++] = $$8;
/*     */       }
/*     */     } 
/*  70 */     return $$2;
/*     */   }
/*     */   
/*     */   protected boolean isNodeValid(@Nullable Node $$0) {
/*  74 */     return ($$0 != null && !$$0.closed);
/*     */   }
/*     */   
/*     */   protected boolean isDiagonalNodeValid(@Nullable Node $$0, @Nullable Node $$1, @Nullable Node $$2) {
/*  78 */     return (isNodeValid($$0) && $$1 != null && $$1.costMalus >= 0.0F && $$2 != null && $$2.costMalus >= 0.0F);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Node findAcceptedNode(int $$0, int $$1, int $$2) {
/*  83 */     Node $$3 = null;
/*  84 */     BlockPathTypes $$4 = getCachedBlockType($$0, $$1, $$2);
/*     */     
/*  86 */     if ((this.allowBreaching && $$4 == BlockPathTypes.BREACH) || $$4 == BlockPathTypes.WATER) {
/*  87 */       float $$5 = this.mob.getPathfindingMalus($$4);
/*     */ 
/*     */       
/*  90 */       $$3 = getNode($$0, $$1, $$2);
/*  91 */       $$3.type = $$4;
/*  92 */       $$3.costMalus = Math.max($$3.costMalus, $$5);
/*     */       
/*  94 */       if ($$5 >= 0.0F && this.level.getFluidState(new BlockPos($$0, $$1, $$2)).isEmpty()) {
/*  95 */         $$3.costMalus += 8.0F;
/*     */       }
/*     */     } 
/*     */     
/*  99 */     return $$3;
/*     */   }
/*     */   
/*     */   protected BlockPathTypes getCachedBlockType(int $$0, int $$1, int $$2) {
/* 103 */     return (BlockPathTypes)this.pathTypesByPosCache.computeIfAbsent(BlockPos.asLong($$0, $$1, $$2), $$3 -> getBlockPathType((BlockGetter)this.level, $$0, $$1, $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPathTypes getBlockPathType(BlockGetter $$0, int $$1, int $$2, int $$3) {
/* 108 */     return getBlockPathType($$0, $$1, $$2, $$3, this.mob);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPathTypes getBlockPathType(BlockGetter $$0, int $$1, int $$2, int $$3, Mob $$4) {
/* 113 */     BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
/* 114 */     for (int $$6 = $$1; $$6 < $$1 + this.entityWidth; $$6++) {
/* 115 */       for (int $$7 = $$2; $$7 < $$2 + this.entityHeight; $$7++) {
/* 116 */         for (int $$8 = $$3; $$8 < $$3 + this.entityDepth; $$8++) {
/* 117 */           FluidState $$9 = $$0.getFluidState((BlockPos)$$5.set($$6, $$7, $$8));
/* 118 */           BlockState $$10 = $$0.getBlockState((BlockPos)$$5.set($$6, $$7, $$8));
/*     */           
/* 120 */           if ($$9.isEmpty() && $$10.isPathfindable($$0, $$5.below(), PathComputationType.WATER) && $$10.isAir())
/* 121 */             return BlockPathTypes.BREACH; 
/* 122 */           if (!$$9.is(FluidTags.WATER)) {
/* 123 */             return BlockPathTypes.BLOCKED;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     BlockState $$11 = $$0.getBlockState((BlockPos)$$5);
/*     */ 
/*     */     
/* 132 */     if ($$11.isPathfindable($$0, (BlockPos)$$5, PathComputationType.WATER)) {
/* 133 */       return BlockPathTypes.WATER;
/*     */     }
/*     */     
/* 136 */     return BlockPathTypes.BLOCKED;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\pathfinder\SwimNodeEvaluator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */