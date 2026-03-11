/*     */ package net.minecraft.world.level.pathfinder;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.PathNavigationRegion;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.CampfireBlock;
/*     */ import net.minecraft.world.level.block.DoorBlock;
/*     */ import net.minecraft.world.level.block.FenceGateBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WalkNodeEvaluator
/*     */   extends NodeEvaluator
/*     */ {
/*     */   public static final double SPACE_BETWEEN_WALL_POSTS = 0.5D;
/*     */   private static final double DEFAULT_MOB_JUMP_HEIGHT = 1.125D;
/*  38 */   private final Long2ObjectMap<BlockPathTypes> pathTypesByPosCache = (Long2ObjectMap<BlockPathTypes>)new Long2ObjectOpenHashMap();
/*  39 */   private final Object2BooleanMap<AABB> collisionCache = (Object2BooleanMap<AABB>)new Object2BooleanOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepare(PathNavigationRegion $$0, Mob $$1) {
/*  46 */     super.prepare($$0, $$1);
/*  47 */     $$1.onPathfindingStart();
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/*  52 */     this.mob.onPathfindingDone();
/*     */     
/*  54 */     this.pathTypesByPosCache.clear();
/*  55 */     this.collisionCache.clear();
/*     */     
/*  57 */     super.done();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getStart() {
/*  63 */     BlockPos.MutableBlockPos $$0 = new BlockPos.MutableBlockPos();
/*  64 */     int $$1 = this.mob.getBlockY();
/*  65 */     BlockState $$2 = this.level.getBlockState((BlockPos)$$0.set(this.mob.getX(), $$1, this.mob.getZ()));
/*     */     
/*  67 */     if (this.mob.canStandOnFluid($$2.getFluidState())) {
/*  68 */       while (this.mob.canStandOnFluid($$2.getFluidState())) {
/*  69 */         $$1++;
/*  70 */         $$2 = this.level.getBlockState((BlockPos)$$0.set(this.mob.getX(), $$1, this.mob.getZ()));
/*     */       } 
/*  72 */       $$1--;
/*  73 */     } else if (canFloat() && this.mob.isInWater()) {
/*  74 */       while ($$2.is(Blocks.WATER) || $$2.getFluidState() == Fluids.WATER.getSource(false)) {
/*  75 */         $$1++;
/*  76 */         $$2 = this.level.getBlockState((BlockPos)$$0.set(this.mob.getX(), $$1, this.mob.getZ()));
/*     */       } 
/*  78 */       $$1--;
/*     */     }
/*  80 */     else if (this.mob.onGround()) {
/*  81 */       $$1 = Mth.floor(this.mob.getY() + 0.5D);
/*     */     } else {
/*  83 */       BlockPos $$3 = this.mob.blockPosition();
/*  84 */       while ((this.level.getBlockState($$3).isAir() || this.level.getBlockState($$3).isPathfindable((BlockGetter)this.level, $$3, PathComputationType.LAND)) && $$3.getY() > this.mob.level().getMinBuildHeight()) {
/*  85 */         $$3 = $$3.below();
/*     */       }
/*  87 */       $$1 = $$3.above().getY();
/*     */     } 
/*     */ 
/*     */     
/*  91 */     BlockPos $$4 = this.mob.blockPosition();
/*  92 */     if (!canStartAt((BlockPos)$$0.set($$4.getX(), $$1, $$4.getZ()))) {
/*  93 */       AABB $$5 = this.mob.getBoundingBox();
/*     */       
/*  95 */       if (canStartAt((BlockPos)$$0.set($$5.minX, $$1, $$5.minZ)) || 
/*  96 */         canStartAt((BlockPos)$$0.set($$5.minX, $$1, $$5.maxZ)) || 
/*  97 */         canStartAt((BlockPos)$$0.set($$5.maxX, $$1, $$5.minZ)) || 
/*  98 */         canStartAt((BlockPos)$$0.set($$5.maxX, $$1, $$5.maxZ)))
/*     */       {
/* 100 */         return getStartNode((BlockPos)$$0);
/*     */       }
/*     */     } 
/* 103 */     return getStartNode(new BlockPos($$4.getX(), $$1, $$4.getZ()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Node getStartNode(BlockPos $$0) {
/* 109 */     Node $$1 = getNode($$0);
/* 110 */     $$1.type = getBlockPathType(this.mob, $$1.asBlockPos());
/* 111 */     $$1.costMalus = this.mob.getPathfindingMalus($$1.type);
/* 112 */     return $$1;
/*     */   }
/*     */   
/*     */   protected boolean canStartAt(BlockPos $$0) {
/* 116 */     BlockPathTypes $$1 = getBlockPathType(this.mob, $$0);
/* 117 */     return ($$1 != BlockPathTypes.OPEN && this.mob.getPathfindingMalus($$1) >= 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public Target getGoal(double $$0, double $$1, double $$2) {
/* 122 */     return getTargetFromNode(getNode(Mth.floor($$0), Mth.floor($$1), Mth.floor($$2)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNeighbors(Node[] $$0, Node $$1) {
/* 127 */     int $$2 = 0;
/* 128 */     int $$3 = 0;
/* 129 */     BlockPathTypes $$4 = getCachedBlockType(this.mob, $$1.x, $$1.y + 1, $$1.z);
/* 130 */     BlockPathTypes $$5 = getCachedBlockType(this.mob, $$1.x, $$1.y, $$1.z);
/*     */     
/* 132 */     if (this.mob.getPathfindingMalus($$4) >= 0.0F && $$5 != BlockPathTypes.STICKY_HONEY) {
/* 133 */       $$3 = Mth.floor(Math.max(1.0F, this.mob.maxUpStep()));
/*     */     }
/*     */     
/* 136 */     double $$6 = getFloorLevel(new BlockPos($$1.x, $$1.y, $$1.z));
/*     */     
/* 138 */     Node $$7 = findAcceptedNode($$1.x, $$1.y, $$1.z + 1, $$3, $$6, Direction.SOUTH, $$5);
/* 139 */     if (isNeighborValid($$7, $$1)) {
/* 140 */       $$0[$$2++] = $$7;
/*     */     }
/*     */     
/* 143 */     Node $$8 = findAcceptedNode($$1.x - 1, $$1.y, $$1.z, $$3, $$6, Direction.WEST, $$5);
/* 144 */     if (isNeighborValid($$8, $$1)) {
/* 145 */       $$0[$$2++] = $$8;
/*     */     }
/*     */     
/* 148 */     Node $$9 = findAcceptedNode($$1.x + 1, $$1.y, $$1.z, $$3, $$6, Direction.EAST, $$5);
/* 149 */     if (isNeighborValid($$9, $$1)) {
/* 150 */       $$0[$$2++] = $$9;
/*     */     }
/*     */     
/* 153 */     Node $$10 = findAcceptedNode($$1.x, $$1.y, $$1.z - 1, $$3, $$6, Direction.NORTH, $$5);
/* 154 */     if (isNeighborValid($$10, $$1)) {
/* 155 */       $$0[$$2++] = $$10;
/*     */     }
/*     */     
/* 158 */     Node $$11 = findAcceptedNode($$1.x - 1, $$1.y, $$1.z - 1, $$3, $$6, Direction.NORTH, $$5);
/* 159 */     if (isDiagonalValid($$1, $$8, $$10, $$11)) {
/* 160 */       $$0[$$2++] = $$11;
/*     */     }
/*     */     
/* 163 */     Node $$12 = findAcceptedNode($$1.x + 1, $$1.y, $$1.z - 1, $$3, $$6, Direction.NORTH, $$5);
/* 164 */     if (isDiagonalValid($$1, $$9, $$10, $$12)) {
/* 165 */       $$0[$$2++] = $$12;
/*     */     }
/*     */     
/* 168 */     Node $$13 = findAcceptedNode($$1.x - 1, $$1.y, $$1.z + 1, $$3, $$6, Direction.SOUTH, $$5);
/* 169 */     if (isDiagonalValid($$1, $$8, $$7, $$13)) {
/* 170 */       $$0[$$2++] = $$13;
/*     */     }
/*     */     
/* 173 */     Node $$14 = findAcceptedNode($$1.x + 1, $$1.y, $$1.z + 1, $$3, $$6, Direction.SOUTH, $$5);
/* 174 */     if (isDiagonalValid($$1, $$9, $$7, $$14)) {
/* 175 */       $$0[$$2++] = $$14;
/*     */     }
/*     */     
/* 178 */     return $$2;
/*     */   }
/*     */   
/*     */   protected boolean isNeighborValid(@Nullable Node $$0, Node $$1) {
/* 182 */     return ($$0 != null && !$$0.closed && ($$0.costMalus >= 0.0F || $$1.costMalus < 0.0F));
/*     */   }
/*     */   
/*     */   protected boolean isDiagonalValid(Node $$0, @Nullable Node $$1, @Nullable Node $$2, @Nullable Node $$3) {
/* 186 */     if ($$3 == null || $$2 == null || $$1 == null) {
/* 187 */       return false;
/*     */     }
/*     */     
/* 190 */     if ($$3.closed) {
/* 191 */       return false;
/*     */     }
/*     */     
/* 194 */     if ($$2.y > $$0.y || $$1.y > $$0.y) {
/* 195 */       return false;
/*     */     }
/*     */     
/* 198 */     if ($$1.type == BlockPathTypes.WALKABLE_DOOR || $$2.type == BlockPathTypes.WALKABLE_DOOR || $$3.type == BlockPathTypes.WALKABLE_DOOR)
/*     */     {
/* 200 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 204 */     boolean $$4 = ($$2.type == BlockPathTypes.FENCE && $$1.type == BlockPathTypes.FENCE && this.mob.getBbWidth() < 0.5D);
/*     */     
/* 206 */     return ($$3.costMalus >= 0.0F && ($$2.y < $$0.y || $$2.costMalus >= 0.0F || $$4) && ($$1.y < $$0.y || $$1.costMalus >= 0.0F || $$4));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean doesBlockHavePartialCollision(BlockPathTypes $$0) {
/* 212 */     return ($$0 == BlockPathTypes.FENCE || $$0 == BlockPathTypes.DOOR_WOOD_CLOSED || $$0 == BlockPathTypes.DOOR_IRON_CLOSED);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canReachWithoutCollision(Node $$0) {
/* 218 */     AABB $$1 = this.mob.getBoundingBox();
/*     */ 
/*     */ 
/*     */     
/* 222 */     Vec3 $$2 = new Vec3($$0.x - this.mob.getX() + $$1.getXsize() / 2.0D, $$0.y - this.mob.getY() + $$1.getYsize() / 2.0D, $$0.z - this.mob.getZ() + $$1.getZsize() / 2.0D);
/*     */     
/* 224 */     int $$3 = Mth.ceil($$2.length() / $$1.getSize());
/* 225 */     $$2 = $$2.scale((1.0F / $$3));
/* 226 */     for (int $$4 = 1; $$4 <= $$3; $$4++) {
/* 227 */       $$1 = $$1.move($$2);
/* 228 */       if (hasCollisions($$1)) {
/* 229 */         return false;
/*     */       }
/*     */     } 
/* 232 */     return true;
/*     */   }
/*     */   
/*     */   protected double getFloorLevel(BlockPos $$0) {
/* 236 */     if ((canFloat() || isAmphibious()) && this.level.getFluidState($$0).is(FluidTags.WATER)) {
/* 237 */       return $$0.getY() + 0.5D;
/*     */     }
/* 239 */     return getFloorLevel((BlockGetter)this.level, $$0);
/*     */   }
/*     */   
/*     */   public static double getFloorLevel(BlockGetter $$0, BlockPos $$1) {
/* 243 */     BlockPos $$2 = $$1.below();
/* 244 */     VoxelShape $$3 = $$0.getBlockState($$2).getCollisionShape($$0, $$2);
/* 245 */     return $$2.getY() + ($$3.isEmpty() ? 0.0D : $$3.max(Direction.Axis.Y));
/*     */   }
/*     */   
/*     */   protected boolean isAmphibious() {
/* 249 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Node findAcceptedNode(int $$0, int $$1, int $$2, int $$3, double $$4, Direction $$5, BlockPathTypes $$6) {
/* 254 */     Node $$7 = null;
/* 255 */     BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos();
/*     */     
/* 257 */     double $$9 = getFloorLevel((BlockPos)$$8.set($$0, $$1, $$2));
/*     */     
/* 259 */     if ($$9 - $$4 > getMobJumpHeight()) {
/* 260 */       return null;
/*     */     }
/*     */     
/* 263 */     BlockPathTypes $$10 = getCachedBlockType(this.mob, $$0, $$1, $$2);
/*     */     
/* 265 */     float $$11 = this.mob.getPathfindingMalus($$10);
/* 266 */     double $$12 = this.mob.getBbWidth() / 2.0D;
/*     */     
/* 268 */     if ($$11 >= 0.0F) {
/* 269 */       $$7 = getNodeAndUpdateCostToMax($$0, $$1, $$2, $$10, $$11);
/*     */     }
/*     */ 
/*     */     
/* 273 */     if (doesBlockHavePartialCollision($$6) && $$7 != null && $$7.costMalus >= 0.0F && !canReachWithoutCollision($$7)) {
/* 274 */       $$7 = null;
/*     */     }
/*     */     
/* 277 */     if ($$10 == BlockPathTypes.WALKABLE || (isAmphibious() && $$10 == BlockPathTypes.WATER)) {
/* 278 */       return $$7;
/*     */     }
/*     */     
/* 281 */     if (($$7 == null || $$7.costMalus < 0.0F) && $$3 > 0 && ($$10 != BlockPathTypes.FENCE || canWalkOverFences()) && $$10 != BlockPathTypes.UNPASSABLE_RAIL && $$10 != BlockPathTypes.TRAPDOOR && $$10 != BlockPathTypes.POWDER_SNOW) {
/* 282 */       $$7 = findAcceptedNode($$0, $$1 + 1, $$2, $$3 - 1, $$4, $$5, $$6);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 287 */       if ($$7 != null && ($$7.type == BlockPathTypes.OPEN || $$7.type == BlockPathTypes.WALKABLE) && this.mob.getBbWidth() < 1.0F) {
/* 288 */         double $$13 = ($$0 - $$5.getStepX()) + 0.5D;
/* 289 */         double $$14 = ($$2 - $$5.getStepZ()) + 0.5D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 296 */         AABB $$15 = new AABB($$13 - $$12, getFloorLevel((BlockPos)$$8.set($$13, ($$1 + 1), $$14)) + 0.001D, $$14 - $$12, $$13 + $$12, this.mob.getBbHeight() + getFloorLevel((BlockPos)$$8.set($$7.x, $$7.y, $$7.z)) - 0.002D, $$14 + $$12);
/*     */ 
/*     */         
/* 299 */         if (hasCollisions($$15)) {
/* 300 */           $$7 = null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 305 */     if (!isAmphibious() && $$10 == BlockPathTypes.WATER && !canFloat()) {
/* 306 */       if (getCachedBlockType(this.mob, $$0, $$1 - 1, $$2) != BlockPathTypes.WATER) {
/* 307 */         return $$7;
/*     */       }
/*     */ 
/*     */       
/* 311 */       while ($$1 > this.mob.level().getMinBuildHeight()) {
/* 312 */         $$1--;
/*     */         
/* 314 */         $$10 = getCachedBlockType(this.mob, $$0, $$1, $$2);
/*     */         
/* 316 */         if ($$10 == BlockPathTypes.WATER) {
/* 317 */           $$7 = getNodeAndUpdateCostToMax($$0, $$1, $$2, $$10, this.mob.getPathfindingMalus($$10)); continue;
/*     */         } 
/* 319 */         return $$7;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 324 */     if ($$10 == BlockPathTypes.OPEN) {
/*     */ 
/*     */       
/* 327 */       int $$16 = 0;
/* 328 */       int $$17 = $$1;
/* 329 */       while ($$10 == BlockPathTypes.OPEN) {
/* 330 */         $$1--;
/*     */         
/* 332 */         if ($$1 < this.mob.level().getMinBuildHeight()) {
/* 333 */           return getBlockedNode($$0, $$17, $$2);
/*     */         }
/*     */         
/* 336 */         if ($$16++ >= this.mob.getMaxFallDistance()) {
/* 337 */           return getBlockedNode($$0, $$1, $$2);
/*     */         }
/*     */         
/* 340 */         $$10 = getCachedBlockType(this.mob, $$0, $$1, $$2);
/* 341 */         $$11 = this.mob.getPathfindingMalus($$10);
/*     */         
/* 343 */         if ($$10 != BlockPathTypes.OPEN && $$11 >= 0.0F) {
/* 344 */           $$7 = getNodeAndUpdateCostToMax($$0, $$1, $$2, $$10, $$11); break;
/*     */         } 
/* 346 */         if ($$11 < 0.0F) {
/* 347 */           return getBlockedNode($$0, $$1, $$2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 352 */     if (doesBlockHavePartialCollision($$10) && $$7 == null) {
/* 353 */       $$7 = getNode($$0, $$1, $$2);
/* 354 */       $$7.closed = true;
/* 355 */       $$7.type = $$10;
/* 356 */       $$7.costMalus = $$10.getMalus();
/*     */     } 
/*     */     
/* 359 */     return $$7;
/*     */   }
/*     */   
/*     */   private double getMobJumpHeight() {
/* 363 */     return Math.max(1.125D, this.mob.maxUpStep());
/*     */   }
/*     */   
/*     */   private Node getNodeAndUpdateCostToMax(int $$0, int $$1, int $$2, BlockPathTypes $$3, float $$4) {
/* 367 */     Node $$5 = getNode($$0, $$1, $$2);
/* 368 */     $$5.type = $$3;
/* 369 */     $$5.costMalus = Math.max($$5.costMalus, $$4);
/* 370 */     return $$5;
/*     */   }
/*     */   
/*     */   private Node getBlockedNode(int $$0, int $$1, int $$2) {
/* 374 */     Node $$3 = getNode($$0, $$1, $$2);
/* 375 */     $$3.type = BlockPathTypes.BLOCKED;
/* 376 */     $$3.costMalus = -1.0F;
/* 377 */     return $$3;
/*     */   }
/*     */   
/*     */   private boolean hasCollisions(AABB $$0) {
/* 381 */     return this.collisionCache.computeIfAbsent($$0, $$1 -> !this.level.noCollision((Entity)this.mob, $$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPathTypes getBlockPathType(BlockGetter $$0, int $$1, int $$2, int $$3, Mob $$4) {
/* 387 */     EnumSet<BlockPathTypes> $$5 = EnumSet.noneOf(BlockPathTypes.class);
/* 388 */     BlockPathTypes $$6 = BlockPathTypes.BLOCKED;
/*     */     
/* 390 */     $$6 = getBlockPathTypes($$0, $$1, $$2, $$3, $$5, $$6, $$4.blockPosition());
/*     */     
/* 392 */     if ($$5.contains(BlockPathTypes.FENCE)) {
/* 393 */       return BlockPathTypes.FENCE;
/*     */     }
/*     */     
/* 396 */     if ($$5.contains(BlockPathTypes.UNPASSABLE_RAIL)) {
/* 397 */       return BlockPathTypes.UNPASSABLE_RAIL;
/*     */     }
/*     */     
/* 400 */     BlockPathTypes $$7 = BlockPathTypes.BLOCKED;
/* 401 */     for (BlockPathTypes $$8 : $$5) {
/*     */       
/* 403 */       if ($$4.getPathfindingMalus($$8) < 0.0F) {
/* 404 */         return $$8;
/*     */       }
/*     */ 
/*     */       
/* 408 */       if ($$4.getPathfindingMalus($$8) >= $$4.getPathfindingMalus($$7)) {
/* 409 */         $$7 = $$8;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 414 */     if ($$6 == BlockPathTypes.OPEN && $$4.getPathfindingMalus($$7) == 0.0F && this.entityWidth <= 1) {
/* 415 */       return BlockPathTypes.OPEN;
/*     */     }
/*     */     
/* 418 */     return $$7;
/*     */   }
/*     */   
/*     */   public BlockPathTypes getBlockPathTypes(BlockGetter $$0, int $$1, int $$2, int $$3, EnumSet<BlockPathTypes> $$4, BlockPathTypes $$5, BlockPos $$6) {
/* 422 */     for (int $$7 = 0; $$7 < this.entityWidth; $$7++) {
/* 423 */       for (int $$8 = 0; $$8 < this.entityHeight; $$8++) {
/* 424 */         for (int $$9 = 0; $$9 < this.entityDepth; $$9++) {
/* 425 */           int $$10 = $$7 + $$1;
/* 426 */           int $$11 = $$8 + $$2;
/* 427 */           int $$12 = $$9 + $$3;
/*     */           
/* 429 */           BlockPathTypes $$13 = getBlockPathType($$0, $$10, $$11, $$12);
/*     */           
/* 431 */           $$13 = evaluateBlockPathType($$0, $$6, $$13);
/*     */           
/* 433 */           if ($$7 == 0 && $$8 == 0 && $$9 == 0) {
/* 434 */             $$5 = $$13;
/*     */           }
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
/*     */ 
/*     */           
/* 454 */           $$4.add($$13);
/*     */         } 
/*     */       } 
/*     */     } 
/* 458 */     return $$5;
/*     */   }
/*     */   
/*     */   protected BlockPathTypes evaluateBlockPathType(BlockGetter $$0, BlockPos $$1, BlockPathTypes $$2) {
/* 462 */     boolean $$3 = canPassDoors();
/* 463 */     if ($$2 == BlockPathTypes.DOOR_WOOD_CLOSED && canOpenDoors() && $$3) {
/* 464 */       $$2 = BlockPathTypes.WALKABLE_DOOR;
/*     */     }
/* 466 */     if ($$2 == BlockPathTypes.DOOR_OPEN && !$$3) {
/* 467 */       $$2 = BlockPathTypes.BLOCKED;
/*     */     }
/* 469 */     if ($$2 == BlockPathTypes.RAIL && !($$0.getBlockState($$1).getBlock() instanceof net.minecraft.world.level.block.BaseRailBlock) && !($$0.getBlockState($$1.below()).getBlock() instanceof net.minecraft.world.level.block.BaseRailBlock)) {
/* 470 */       $$2 = BlockPathTypes.UNPASSABLE_RAIL;
/*     */     }
/* 472 */     return $$2;
/*     */   }
/*     */   
/*     */   protected BlockPathTypes getBlockPathType(Mob $$0, BlockPos $$1) {
/* 476 */     return getCachedBlockType($$0, $$1.getX(), $$1.getY(), $$1.getZ());
/*     */   }
/*     */   
/*     */   protected BlockPathTypes getCachedBlockType(Mob $$0, int $$1, int $$2, int $$3) {
/* 480 */     return (BlockPathTypes)this.pathTypesByPosCache.computeIfAbsent(BlockPos.asLong($$1, $$2, $$3), $$4 -> getBlockPathType((BlockGetter)this.level, $$0, $$1, $$2, $$3));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPathTypes getBlockPathType(BlockGetter $$0, int $$1, int $$2, int $$3) {
/* 485 */     return getBlockPathTypeStatic($$0, new BlockPos.MutableBlockPos($$1, $$2, $$3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockPathTypes getBlockPathTypeStatic(BlockGetter $$0, BlockPos.MutableBlockPos $$1) {
/* 496 */     int $$2 = $$1.getX();
/* 497 */     int $$3 = $$1.getY();
/* 498 */     int $$4 = $$1.getZ();
/*     */     
/* 500 */     BlockPathTypes $$5 = getBlockPathTypeRaw($$0, (BlockPos)$$1);
/* 501 */     if ($$5 != BlockPathTypes.OPEN || $$3 < $$0.getMinBuildHeight() + 1) {
/* 502 */       return $$5;
/*     */     }
/*     */     
/* 505 */     switch (getBlockPathTypeRaw($$0, (BlockPos)$$1.set($$2, $$3 - 1, $$4))) { case OPEN: case WATER: case LAVA: case WALKABLE: case DAMAGE_FIRE: case DAMAGE_OTHER: case STICKY_HONEY: case POWDER_SNOW: case DAMAGE_CAUTIOUS: case TRAPDOOR:  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 515 */       checkNeighbourBlocks($$0, $$1.set($$2, $$3, $$4), BlockPathTypes.WALKABLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockPathTypes checkNeighbourBlocks(BlockGetter $$0, BlockPos.MutableBlockPos $$1, BlockPathTypes $$2) {
/* 520 */     int $$3 = $$1.getX();
/* 521 */     int $$4 = $$1.getY();
/* 522 */     int $$5 = $$1.getZ();
/*     */     
/* 524 */     for (int $$6 = -1; $$6 <= 1; $$6++) {
/* 525 */       for (int $$7 = -1; $$7 <= 1; $$7++) {
/* 526 */         for (int $$8 = -1; $$8 <= 1; $$8++) {
/* 527 */           if ($$6 != 0 || $$8 != 0) {
/* 528 */             $$1.set($$3 + $$6, $$4 + $$7, $$5 + $$8);
/* 529 */             BlockState $$9 = $$0.getBlockState((BlockPos)$$1);
/* 530 */             if ($$9.is(Blocks.CACTUS) || $$9.is(Blocks.SWEET_BERRY_BUSH))
/* 531 */               return BlockPathTypes.DANGER_OTHER; 
/* 532 */             if (isBurningBlock($$9))
/* 533 */               return BlockPathTypes.DANGER_FIRE; 
/* 534 */             if ($$0.getFluidState((BlockPos)$$1).is(FluidTags.WATER))
/* 535 */               return BlockPathTypes.WATER_BORDER; 
/* 536 */             if ($$9.is(Blocks.WITHER_ROSE) || $$9.is(Blocks.POINTED_DRIPSTONE)) {
/* 537 */               return BlockPathTypes.DAMAGE_CAUTIOUS;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 543 */     return $$2;
/*     */   }
/*     */   
/*     */   protected static BlockPathTypes getBlockPathTypeRaw(BlockGetter $$0, BlockPos $$1) {
/* 547 */     BlockState $$2 = $$0.getBlockState($$1);
/* 548 */     Block $$3 = $$2.getBlock();
/*     */     
/* 550 */     if ($$2.isAir()) {
/* 551 */       return BlockPathTypes.OPEN;
/*     */     }
/*     */     
/* 554 */     if ($$2.is(BlockTags.TRAPDOORS) || $$2.is(Blocks.LILY_PAD) || $$2.is(Blocks.BIG_DRIPLEAF)) {
/* 555 */       return BlockPathTypes.TRAPDOOR;
/*     */     }
/*     */     
/* 558 */     if ($$2.is(Blocks.POWDER_SNOW)) {
/* 559 */       return BlockPathTypes.POWDER_SNOW;
/*     */     }
/*     */     
/* 562 */     if ($$2.is(Blocks.CACTUS) || $$2.is(Blocks.SWEET_BERRY_BUSH)) {
/* 563 */       return BlockPathTypes.DAMAGE_OTHER;
/*     */     }
/*     */     
/* 566 */     if ($$2.is(Blocks.HONEY_BLOCK)) {
/* 567 */       return BlockPathTypes.STICKY_HONEY;
/*     */     }
/*     */     
/* 570 */     if ($$2.is(Blocks.COCOA)) {
/* 571 */       return BlockPathTypes.COCOA;
/*     */     }
/*     */     
/* 574 */     if ($$2.is(Blocks.WITHER_ROSE) || $$2.is(Blocks.POINTED_DRIPSTONE)) {
/* 575 */       return BlockPathTypes.DAMAGE_CAUTIOUS;
/*     */     }
/*     */     
/* 578 */     FluidState $$4 = $$0.getFluidState($$1);
/* 579 */     if ($$4.is(FluidTags.LAVA)) {
/* 580 */       return BlockPathTypes.LAVA;
/*     */     }
/*     */     
/* 583 */     if (isBurningBlock($$2)) {
/* 584 */       return BlockPathTypes.DAMAGE_FIRE;
/*     */     }
/*     */     
/* 587 */     if ($$3 instanceof DoorBlock) { DoorBlock $$5 = (DoorBlock)$$3;
/* 588 */       if (((Boolean)$$2.getValue((Property)DoorBlock.OPEN)).booleanValue()) {
/* 589 */         return BlockPathTypes.DOOR_OPEN;
/*     */       }
/* 591 */       return $$5.type().canOpenByHand() ? BlockPathTypes.DOOR_WOOD_CLOSED : BlockPathTypes.DOOR_IRON_CLOSED; }
/*     */ 
/*     */     
/* 594 */     if ($$3 instanceof net.minecraft.world.level.block.BaseRailBlock) {
/* 595 */       return BlockPathTypes.RAIL;
/*     */     }
/*     */     
/* 598 */     if ($$3 instanceof net.minecraft.world.level.block.LeavesBlock) {
/* 599 */       return BlockPathTypes.LEAVES;
/*     */     }
/*     */     
/* 602 */     if ($$2.is(BlockTags.FENCES) || $$2.is(BlockTags.WALLS) || ($$3 instanceof FenceGateBlock && !((Boolean)$$2.getValue((Property)FenceGateBlock.OPEN)).booleanValue())) {
/* 603 */       return BlockPathTypes.FENCE;
/*     */     }
/*     */ 
/*     */     
/* 607 */     if (!$$2.isPathfindable($$0, $$1, PathComputationType.LAND)) {
/* 608 */       return BlockPathTypes.BLOCKED;
/*     */     }
/*     */     
/* 611 */     if ($$4.is(FluidTags.WATER)) {
/* 612 */       return BlockPathTypes.WATER;
/*     */     }
/*     */     
/* 615 */     return BlockPathTypes.OPEN;
/*     */   }
/*     */   
/*     */   public static boolean isBurningBlock(BlockState $$0) {
/* 619 */     return ($$0.is(BlockTags.FIRE) || $$0
/* 620 */       .is(Blocks.LAVA) || $$0
/* 621 */       .is(Blocks.MAGMA_BLOCK) || 
/* 622 */       CampfireBlock.isLitCampfire($$0) || $$0
/* 623 */       .is(Blocks.LAVA_CAULDRON));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\pathfinder\WalkNodeEvaluator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */