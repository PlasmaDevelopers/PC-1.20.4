/*     */ package net.minecraft.world.entity.ai.navigation;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.PathNavigationRegion;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.level.pathfinder.Node;
/*     */ import net.minecraft.world.level.pathfinder.NodeEvaluator;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.level.pathfinder.PathFinder;
/*     */ import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PathNavigation
/*     */ {
/*     */   private static final int MAX_TIME_RECOMPUTE = 20;
/*     */   private static final int STUCK_CHECK_INTERVAL = 100;
/*     */   private static final float STUCK_THRESHOLD_DISTANCE_FACTOR = 0.25F;
/*     */   protected final Mob mob;
/*     */   protected final Level level;
/*     */   @Nullable
/*     */   protected Path path;
/*     */   protected double speedModifier;
/*     */   protected int tick;
/*     */   protected int lastStuckCheck;
/*  59 */   protected Vec3 lastStuckCheckPos = Vec3.ZERO;
/*  60 */   protected Vec3i timeoutCachedNode = Vec3i.ZERO;
/*     */   protected long timeoutTimer;
/*     */   protected long lastTimeoutCheck;
/*     */   protected double timeoutLimit;
/*  64 */   protected float maxDistanceToWaypoint = 0.5F;
/*     */   
/*     */   protected boolean hasDelayedRecomputation;
/*     */   
/*     */   protected long timeLastRecompute;
/*     */   
/*     */   protected NodeEvaluator nodeEvaluator;
/*     */   @Nullable
/*     */   private BlockPos targetPos;
/*     */   private int reachRange;
/*  74 */   private float maxVisitedNodesMultiplier = 1.0F;
/*     */   
/*     */   private final PathFinder pathFinder;
/*     */   private boolean isStuck;
/*     */   
/*     */   public PathNavigation(Mob $$0, Level $$1) {
/*  80 */     this.mob = $$0;
/*  81 */     this.level = $$1;
/*     */ 
/*     */     
/*  84 */     int $$2 = Mth.floor($$0.getAttributeValue(Attributes.FOLLOW_RANGE) * 16.0D);
/*  85 */     this.pathFinder = createPathFinder($$2);
/*     */   }
/*     */   
/*     */   public void resetMaxVisitedNodesMultiplier() {
/*  89 */     this.maxVisitedNodesMultiplier = 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxVisitedNodesMultiplier(float $$0) {
/*  96 */     this.maxVisitedNodesMultiplier = $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getTargetPos() {
/* 101 */     return this.targetPos;
/*     */   }
/*     */   
/*     */   protected abstract PathFinder createPathFinder(int paramInt);
/*     */   
/*     */   public void setSpeedModifier(double $$0) {
/* 107 */     this.speedModifier = $$0;
/*     */   }
/*     */   
/*     */   public void recomputePath() {
/* 111 */     if (this.level.getGameTime() - this.timeLastRecompute > 20L) {
/* 112 */       if (this.targetPos != null) {
/* 113 */         this.path = null;
/* 114 */         this.path = createPath(this.targetPos, this.reachRange);
/* 115 */         this.timeLastRecompute = this.level.getGameTime();
/* 116 */         this.hasDelayedRecomputation = false;
/*     */       } 
/*     */     } else {
/* 119 */       this.hasDelayedRecomputation = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public final Path createPath(double $$0, double $$1, double $$2, int $$3) {
/* 125 */     return createPath(BlockPos.containing($$0, $$1, $$2), $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Path createPath(Stream<BlockPos> $$0, int $$1) {
/* 138 */     return createPath($$0.collect((Collector)Collectors.toSet()), 8, false, $$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Path createPath(Set<BlockPos> $$0, int $$1) {
/* 143 */     return createPath($$0, 8, false, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Path createPath(BlockPos $$0, int $$1) {
/* 156 */     return createPath((Set<BlockPos>)ImmutableSet.of($$0), 8, false, $$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Path createPath(BlockPos $$0, int $$1, int $$2) {
/* 161 */     return createPath((Set<BlockPos>)ImmutableSet.of($$0), 8, false, $$1, $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Path createPath(Entity $$0, int $$1) {
/* 174 */     return createPath((Set<BlockPos>)ImmutableSet.of($$0.blockPosition()), 16, true, $$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Path createPath(Set<BlockPos> $$0, int $$1, boolean $$2, int $$3) {
/* 179 */     return createPath($$0, $$1, $$2, $$3, (float)this.mob.getAttributeValue(Attributes.FOLLOW_RANGE));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Path createPath(Set<BlockPos> $$0, int $$1, boolean $$2, int $$3, float $$4) {
/* 184 */     if ($$0.isEmpty()) {
/* 185 */       return null;
/*     */     }
/*     */     
/* 188 */     if (this.mob.getY() < this.level.getMinBuildHeight()) {
/* 189 */       return null;
/*     */     }
/*     */     
/* 192 */     if (!canUpdatePath()) {
/* 193 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 197 */     if (this.path != null && !this.path.isDone() && $$0.contains(this.targetPos)) {
/* 198 */       return this.path;
/*     */     }
/*     */     
/* 201 */     this.level.getProfiler().push("pathfind");
/* 202 */     BlockPos $$5 = $$2 ? this.mob.blockPosition().above() : this.mob.blockPosition();
/* 203 */     int $$6 = (int)($$4 + $$1);
/*     */ 
/*     */     
/* 206 */     PathNavigationRegion $$7 = new PathNavigationRegion(this.level, $$5.offset(-$$6, -$$6, -$$6), $$5.offset($$6, $$6, $$6));
/* 207 */     Path $$8 = this.pathFinder.findPath($$7, this.mob, $$0, $$4, $$3, this.maxVisitedNodesMultiplier);
/* 208 */     this.level.getProfiler().pop();
/*     */     
/* 210 */     if ($$8 != null && $$8.getTarget() != null) {
/*     */ 
/*     */ 
/*     */       
/* 214 */       this.targetPos = $$8.getTarget();
/* 215 */       this.reachRange = $$3;
/* 216 */       resetStuckTimeout();
/*     */     } 
/*     */     
/* 219 */     return $$8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean moveTo(double $$0, double $$1, double $$2, double $$3) {
/* 228 */     return moveTo(createPath($$0, $$1, $$2, 1), $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean moveTo(Entity $$0, double $$1) {
/* 237 */     Path $$2 = createPath($$0, 1);
/* 238 */     return ($$2 != null && moveTo($$2, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean moveTo(@Nullable Path $$0, double $$1) {
/* 246 */     if ($$0 == null) {
/* 247 */       this.path = null;
/* 248 */       return false;
/*     */     } 
/* 250 */     if (!$$0.sameAs(this.path)) {
/* 251 */       this.path = $$0;
/*     */     }
/* 253 */     if (isDone()) {
/* 254 */       return false;
/*     */     }
/* 256 */     trimPath();
/* 257 */     if (this.path.getNodeCount() <= 0) {
/* 258 */       return false;
/*     */     }
/*     */     
/* 261 */     this.speedModifier = $$1;
/* 262 */     Vec3 $$2 = getTempMobPos();
/* 263 */     this.lastStuckCheck = this.tick;
/* 264 */     this.lastStuckCheckPos = $$2;
/* 265 */     return true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Path getPath() {
/* 270 */     return this.path;
/*     */   }
/*     */   
/*     */   public void tick() {
/* 274 */     this.tick++;
/*     */     
/* 276 */     if (this.hasDelayedRecomputation) {
/* 277 */       recomputePath();
/*     */     }
/*     */     
/* 280 */     if (isDone()) {
/*     */       return;
/*     */     }
/*     */     
/* 284 */     if (canUpdatePath()) {
/* 285 */       followThePath();
/* 286 */     } else if (this.path != null && !this.path.isDone()) {
/* 287 */       Vec3 $$0 = getTempMobPos();
/* 288 */       Vec3 $$1 = this.path.getNextEntityPos((Entity)this.mob);
/* 289 */       if ($$0.y > $$1.y && !this.mob.onGround() && Mth.floor($$0.x) == Mth.floor($$1.x) && Mth.floor($$0.z) == Mth.floor($$1.z)) {
/* 290 */         this.path.advance();
/*     */       }
/*     */     } 
/*     */     
/* 294 */     DebugPackets.sendPathFindingPacket(this.level, this.mob, this.path, this.maxDistanceToWaypoint);
/*     */     
/* 296 */     if (isDone()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 301 */     Vec3 $$2 = this.path.getNextEntityPos((Entity)this.mob);
/* 302 */     this.mob.getMoveControl().setWantedPosition($$2.x, getGroundY($$2), $$2.z, this.speedModifier);
/*     */   }
/*     */ 
/*     */   
/*     */   protected double getGroundY(Vec3 $$0) {
/* 307 */     BlockPos $$1 = BlockPos.containing((Position)$$0);
/* 308 */     return this.level.getBlockState($$1.below()).isAir() ? $$0.y : WalkNodeEvaluator.getFloorLevel((BlockGetter)this.level, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void followThePath() {
/* 315 */     Vec3 $$0 = getTempMobPos();
/*     */     
/* 317 */     this.maxDistanceToWaypoint = (this.mob.getBbWidth() > 0.75F) ? (this.mob.getBbWidth() / 2.0F) : (0.75F - this.mob.getBbWidth() / 2.0F);
/* 318 */     BlockPos blockPos = this.path.getNextNodePos();
/* 319 */     double $$2 = Math.abs(this.mob.getX() - blockPos.getX() + 0.5D);
/* 320 */     double $$3 = Math.abs(this.mob.getY() - blockPos.getY());
/* 321 */     double $$4 = Math.abs(this.mob.getZ() - blockPos.getZ() + 0.5D);
/* 322 */     boolean $$5 = ($$2 < this.maxDistanceToWaypoint && $$4 < this.maxDistanceToWaypoint && $$3 < 1.0D);
/*     */ 
/*     */ 
/*     */     
/* 326 */     if ($$5 || (canCutCorner((this.path.getNextNode()).type) && shouldTargetNextNodeInDirection($$0))) {
/* 327 */       this.path.advance();
/*     */     }
/* 329 */     doStuckDetection($$0);
/*     */   }
/*     */   
/*     */   private boolean shouldTargetNextNodeInDirection(Vec3 $$0) {
/* 333 */     if (this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()) {
/* 334 */       return false;
/*     */     }
/*     */     
/* 337 */     Vec3 $$1 = Vec3.atBottomCenterOf((Vec3i)this.path.getNextNodePos());
/* 338 */     if (!$$0.closerThan((Position)$$1, 2.0D))
/*     */     {
/*     */       
/* 341 */       return false;
/*     */     }
/*     */     
/* 344 */     if (canMoveDirectly($$0, this.path.getNextEntityPos((Entity)this.mob))) {
/* 345 */       return true;
/*     */     }
/*     */     
/* 348 */     Vec3 $$2 = Vec3.atBottomCenterOf((Vec3i)this.path.getNodePos(this.path.getNextNodeIndex() + 1));
/*     */ 
/*     */     
/* 351 */     Vec3 $$3 = $$1.subtract($$0);
/* 352 */     Vec3 $$4 = $$2.subtract($$0);
/* 353 */     double $$5 = $$3.lengthSqr();
/* 354 */     double $$6 = $$4.lengthSqr();
/* 355 */     boolean $$7 = ($$6 < $$5);
/* 356 */     boolean $$8 = ($$5 < 0.5D);
/* 357 */     if ($$7 || $$8) {
/* 358 */       Vec3 $$9 = $$3.normalize();
/* 359 */       Vec3 $$10 = $$4.normalize();
/* 360 */       return ($$10.dot($$9) < 0.0D);
/*     */     } 
/*     */     
/* 363 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doStuckDetection(Vec3 $$0) {
/* 368 */     if (this.tick - this.lastStuckCheck > 100) {
/*     */       
/* 370 */       float $$1 = (this.mob.getSpeed() >= 1.0F) ? this.mob.getSpeed() : (this.mob.getSpeed() * this.mob.getSpeed());
/* 371 */       float $$2 = $$1 * 100.0F * 0.25F;
/* 372 */       if ($$0.distanceToSqr(this.lastStuckCheckPos) < ($$2 * $$2)) {
/* 373 */         this.isStuck = true;
/* 374 */         stop();
/*     */       } else {
/* 376 */         this.isStuck = false;
/*     */       } 
/* 378 */       this.lastStuckCheck = this.tick;
/* 379 */       this.lastStuckCheckPos = $$0;
/*     */     } 
/*     */     
/* 382 */     if (this.path != null && !this.path.isDone()) {
/* 383 */       BlockPos blockPos = this.path.getNextNodePos();
/*     */       
/* 385 */       long $$4 = this.level.getGameTime();
/* 386 */       if (blockPos.equals(this.timeoutCachedNode)) {
/* 387 */         this.timeoutTimer += $$4 - this.lastTimeoutCheck;
/*     */       } else {
/* 389 */         this.timeoutCachedNode = (Vec3i)blockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 395 */         double $$5 = $$0.distanceTo(Vec3.atBottomCenterOf(this.timeoutCachedNode));
/* 396 */         this.timeoutLimit = (this.mob.getSpeed() > 0.0F) ? ($$5 / this.mob.getSpeed() * 20.0D) : 0.0D;
/*     */       } 
/*     */       
/* 399 */       if (this.timeoutLimit > 0.0D && this.timeoutTimer > this.timeoutLimit * 3.0D) {
/* 400 */         timeoutPath();
/*     */       }
/* 402 */       this.lastTimeoutCheck = $$4;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void timeoutPath() {
/* 407 */     resetStuckTimeout();
/* 408 */     stop();
/*     */   }
/*     */   
/*     */   private void resetStuckTimeout() {
/* 412 */     this.timeoutCachedNode = Vec3i.ZERO;
/* 413 */     this.timeoutTimer = 0L;
/* 414 */     this.timeoutLimit = 0.0D;
/* 415 */     this.isStuck = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDone() {
/* 420 */     return (this.path == null || this.path.isDone());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInProgress() {
/* 427 */     return !isDone();
/*     */   }
/*     */   
/*     */   public void stop() {
/* 431 */     this.path = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Vec3 getTempMobPos();
/*     */ 
/*     */   
/*     */   protected abstract boolean canUpdatePath();
/*     */ 
/*     */   
/*     */   protected void trimPath() {
/* 443 */     if (this.path == null) {
/*     */       return;
/*     */     }
/*     */     
/* 447 */     for (int $$0 = 0; $$0 < this.path.getNodeCount(); $$0++) {
/* 448 */       Node $$1 = this.path.getNode($$0);
/* 449 */       Node $$2 = ($$0 + 1 < this.path.getNodeCount()) ? this.path.getNode($$0 + 1) : null;
/*     */       
/* 451 */       BlockState $$3 = this.level.getBlockState(new BlockPos($$1.x, $$1.y, $$1.z));
/*     */       
/* 453 */       if ($$3.is(BlockTags.CAULDRONS)) {
/* 454 */         this.path.replaceNode($$0, $$1.cloneAndMove($$1.x, $$1.y + 1, $$1.z));
/* 455 */         if ($$2 != null && $$1.y >= $$2.y) {
/* 456 */           this.path.replaceNode($$0 + 1, $$1.cloneAndMove($$2.x, $$1.y + 1, $$2.z));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean canMoveDirectly(Vec3 $$0, Vec3 $$1) {
/* 463 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCutCorner(BlockPathTypes $$0) {
/* 473 */     return ($$0 != BlockPathTypes.DANGER_FIRE && $$0 != BlockPathTypes.DANGER_OTHER && $$0 != BlockPathTypes.WALKABLE_DOOR);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isClearForMovementBetween(Mob $$0, Vec3 $$1, Vec3 $$2, boolean $$3) {
/* 479 */     Vec3 $$4 = new Vec3($$2.x, $$2.y + $$0.getBbHeight() * 0.5D, $$2.z);
/* 480 */     return ($$0.level().clip(new ClipContext($$1, $$4, ClipContext.Block.COLLIDER, $$3 ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, (Entity)$$0)).getType() == HitResult.Type.MISS);
/*     */   }
/*     */   
/*     */   public boolean isStableDestination(BlockPos $$0) {
/* 484 */     BlockPos $$1 = $$0.below();
/* 485 */     return this.level.getBlockState($$1).isSolidRender((BlockGetter)this.level, $$1);
/*     */   }
/*     */   
/*     */   public NodeEvaluator getNodeEvaluator() {
/* 489 */     return this.nodeEvaluator;
/*     */   }
/*     */   
/*     */   public void setCanFloat(boolean $$0) {
/* 493 */     this.nodeEvaluator.setCanFloat($$0);
/*     */   }
/*     */   
/*     */   public boolean canFloat() {
/* 497 */     return this.nodeEvaluator.canFloat();
/*     */   }
/*     */   
/*     */   public boolean shouldRecomputePath(BlockPos $$0) {
/* 501 */     if (this.hasDelayedRecomputation) {
/* 502 */       return false;
/*     */     }
/*     */     
/* 505 */     if (this.path == null || this.path.isDone() || this.path.getNodeCount() == 0) {
/* 506 */       return false;
/*     */     }
/*     */     
/* 509 */     Node $$1 = this.path.getEndNode();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 514 */     Vec3 $$2 = new Vec3(($$1.x + this.mob.getX()) / 2.0D, ($$1.y + this.mob.getY()) / 2.0D, ($$1.z + this.mob.getZ()) / 2.0D);
/*     */ 
/*     */     
/* 517 */     return $$0.closerToCenterThan((Position)$$2, (this.path.getNodeCount() - this.path.getNextNodeIndex()));
/*     */   }
/*     */   
/*     */   public float getMaxDistanceToWaypoint() {
/* 521 */     return this.maxDistanceToWaypoint;
/*     */   }
/*     */   
/*     */   public boolean isStuck() {
/* 525 */     return this.isStuck;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\navigation\PathNavigation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */