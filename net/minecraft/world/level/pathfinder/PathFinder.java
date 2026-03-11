/*     */ package net.minecraft.world.level.pathfinder;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.util.profiling.metrics.MetricCategory;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.level.PathNavigationRegion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathFinder
/*     */ {
/*     */   private static final float FUDGING = 1.5F;
/*  27 */   private final Node[] neighbors = new Node[32];
/*     */   
/*     */   private final int maxVisitedNodes;
/*     */   private final NodeEvaluator nodeEvaluator;
/*     */   private static final boolean DEBUG = false;
/*  32 */   private final BinaryHeap openSet = new BinaryHeap();
/*     */   
/*     */   public PathFinder(NodeEvaluator $$0, int $$1) {
/*  35 */     this.nodeEvaluator = $$0;
/*  36 */     this.maxVisitedNodes = $$1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Path findPath(PathNavigationRegion $$0, Mob $$1, Set<BlockPos> $$2, float $$3, int $$4, float $$5) {
/*  41 */     this.openSet.clear();
/*  42 */     this.nodeEvaluator.prepare($$0, $$1);
/*  43 */     Node $$6 = this.nodeEvaluator.getStart();
/*  44 */     if ($$6 == null) {
/*  45 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  49 */     Map<Target, BlockPos> $$7 = (Map<Target, BlockPos>)$$2.stream().collect(Collectors.toMap($$0 -> this.nodeEvaluator.getGoal($$0.getX(), $$0.getY(), $$0.getZ()), Function.identity()));
/*  50 */     Path $$8 = findPath($$0.getProfiler(), $$6, $$7, $$3, $$4, $$5);
/*     */     
/*  52 */     this.nodeEvaluator.done();
/*  53 */     return $$8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Path findPath(ProfilerFiller $$0, Node $$1, Map<Target, BlockPos> $$2, float $$3, int $$4, float $$5) {
/*  63 */     $$0.push("find_path");
/*  64 */     $$0.markForCharting(MetricCategory.PATH_FINDING);
/*  65 */     Set<Target> $$6 = $$2.keySet();
/*     */     
/*  67 */     $$1.g = 0.0F;
/*  68 */     $$1.h = getBestH($$1, $$6);
/*  69 */     $$1.f = $$1.h;
/*     */     
/*  71 */     this.openSet.clear();
/*  72 */     this.openSet.insert($$1);
/*  73 */     ImmutableSet immutableSet = ImmutableSet.of();
/*     */ 
/*     */     
/*  76 */     int $$8 = 0;
/*     */     
/*  78 */     Set<Target> $$9 = Sets.newHashSetWithExpectedSize($$6.size());
/*     */     
/*  80 */     int $$10 = (int)(this.maxVisitedNodes * $$5);
/*  81 */     while (!this.openSet.isEmpty() && ++$$8 < $$10) {
/*  82 */       Node $$11 = this.openSet.pop();
/*  83 */       $$11.closed = true;
/*     */ 
/*     */       
/*  86 */       for (Target $$12 : $$6) {
/*  87 */         if ($$11.distanceManhattan($$12) <= $$4) {
/*  88 */           $$12.setReached();
/*  89 */           $$9.add($$12);
/*     */         } 
/*     */       } 
/*     */       
/*  93 */       if (!$$9.isEmpty()) {
/*     */         break;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 101 */       if ($$11.distanceTo($$1) >= $$3) {
/*     */         continue;
/*     */       }
/*     */       
/* 105 */       int $$13 = this.nodeEvaluator.getNeighbors(this.neighbors, $$11);
/* 106 */       for (int $$14 = 0; $$14 < $$13; $$14++) {
/* 107 */         Node $$15 = this.neighbors[$$14];
/*     */         
/* 109 */         float $$16 = distance($$11, $$15);
/* 110 */         $$11.walkedDistance += $$16;
/*     */         
/* 112 */         float $$17 = $$11.g + $$16 + $$15.costMalus;
/* 113 */         if ($$15.walkedDistance < $$3 && (!$$15.inOpenSet() || $$17 < $$15.g)) {
/* 114 */           $$15.cameFrom = $$11;
/* 115 */           $$15.g = $$17;
/* 116 */           $$15.h = getBestH($$15, $$6) * 1.5F;
/*     */           
/* 118 */           if ($$15.inOpenSet()) {
/* 119 */             this.openSet.changeCost($$15, $$15.g + $$15.h);
/*     */           } else {
/* 121 */             $$15.f = $$15.g + $$15.h;
/* 122 */             this.openSet.insert($$15);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     Optional<Path> $$18 = !$$9.isEmpty() ? $$9.stream().map($$1 -> reconstructPath($$1.getBestNode(), (BlockPos)$$0.get($$1), true)).min(Comparator.comparingInt(Path::getNodeCount)) : $$6.stream().map($$1 -> reconstructPath($$1.getBestNode(), (BlockPos)$$0.get($$1), false)).min(Comparator.<Path>comparingDouble(Path::getDistToTarget).thenComparingInt(Path::getNodeCount));
/*     */     
/* 136 */     $$0.pop();
/* 137 */     if ($$18.isEmpty()) {
/* 138 */       return null;
/*     */     }
/* 140 */     Path $$19 = $$18.get();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     return $$19;
/*     */   }
/*     */   
/*     */   protected float distance(Node $$0, Node $$1) {
/* 149 */     return $$0.distanceTo($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   private float getBestH(Node $$0, Set<Target> $$1) {
/* 154 */     float $$2 = Float.MAX_VALUE;
/* 155 */     for (Target $$3 : $$1) {
/* 156 */       float $$4 = $$0.distanceTo($$3);
/* 157 */       $$3.updateBest($$4, $$0);
/* 158 */       $$2 = Math.min($$4, $$2);
/*     */     } 
/* 160 */     return $$2;
/*     */   }
/*     */   
/*     */   private Path reconstructPath(Node $$0, BlockPos $$1, boolean $$2) {
/* 164 */     List<Node> $$3 = Lists.newArrayList();
/* 165 */     Node $$4 = $$0;
/* 166 */     $$3.add(0, $$4);
/* 167 */     while ($$4.cameFrom != null) {
/* 168 */       $$4 = $$4.cameFrom;
/* 169 */       $$3.add(0, $$4);
/*     */     } 
/* 171 */     return new Path($$3, $$1, $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\pathfinder\PathFinder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */