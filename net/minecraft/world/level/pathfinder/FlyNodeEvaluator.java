/*     */ package net.minecraft.world.level.pathfinder;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.PathNavigationRegion;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ 
/*     */ public class FlyNodeEvaluator
/*     */   extends WalkNodeEvaluator
/*     */ {
/*  20 */   private final Long2ObjectMap<BlockPathTypes> pathTypeByPosCache = (Long2ObjectMap<BlockPathTypes>)new Long2ObjectOpenHashMap();
/*     */   
/*     */   private static final float SMALL_MOB_INFLATED_START_NODE_BOUNDING_BOX = 1.5F;
/*     */   private static final int MAX_START_NODE_CANDIDATES = 10;
/*     */   
/*     */   public void prepare(PathNavigationRegion $$0, Mob $$1) {
/*  26 */     super.prepare($$0, $$1);
/*  27 */     this.pathTypeByPosCache.clear();
/*     */     
/*  29 */     $$1.onPathfindingStart();
/*     */   }
/*     */ 
/*     */   
/*     */   public void done() {
/*  34 */     this.mob.onPathfindingDone();
/*     */     
/*  36 */     this.pathTypeByPosCache.clear();
/*  37 */     super.done();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Node getStart() {
/*     */     int $$3;
/*  44 */     if (canFloat() && this.mob.isInWater()) {
/*  45 */       int $$0 = this.mob.getBlockY();
/*  46 */       BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos(this.mob.getX(), $$0, this.mob.getZ());
/*  47 */       BlockState $$2 = this.level.getBlockState((BlockPos)$$1);
/*  48 */       while ($$2.is(Blocks.WATER)) {
/*  49 */         $$0++;
/*  50 */         $$1.set(this.mob.getX(), $$0, this.mob.getZ());
/*  51 */         $$2 = this.level.getBlockState((BlockPos)$$1);
/*     */       } 
/*     */     } else {
/*  54 */       $$3 = Mth.floor(this.mob.getY() + 0.5D);
/*     */     } 
/*     */     
/*  57 */     BlockPos $$4 = BlockPos.containing(this.mob.getX(), $$3, this.mob.getZ());
/*  58 */     if (!canStartAt($$4)) {
/*  59 */       for (BlockPos $$5 : iteratePathfindingStartNodeCandidatePositions(this.mob)) {
/*  60 */         if (canStartAt($$5)) {
/*  61 */           return getStartNode($$5);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  66 */     return getStartNode($$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStartAt(BlockPos $$0) {
/*  71 */     BlockPathTypes $$1 = getBlockPathType(this.mob, $$0);
/*  72 */     return (this.mob.getPathfindingMalus($$1) >= 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public Target getGoal(double $$0, double $$1, double $$2) {
/*  77 */     return getTargetFromNode(getNode(Mth.floor($$0), Mth.floor($$1), Mth.floor($$2)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNeighbors(Node[] $$0, Node $$1) {
/*  82 */     int $$2 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     Node $$3 = findAcceptedNode($$1.x, $$1.y, $$1.z + 1);
/*  88 */     if (isOpen($$3)) {
/*  89 */       $$0[$$2++] = $$3;
/*     */     }
/*     */     
/*  92 */     Node $$4 = findAcceptedNode($$1.x - 1, $$1.y, $$1.z);
/*  93 */     if (isOpen($$4)) {
/*  94 */       $$0[$$2++] = $$4;
/*     */     }
/*     */     
/*  97 */     Node $$5 = findAcceptedNode($$1.x + 1, $$1.y, $$1.z);
/*  98 */     if (isOpen($$5)) {
/*  99 */       $$0[$$2++] = $$5;
/*     */     }
/*     */     
/* 102 */     Node $$6 = findAcceptedNode($$1.x, $$1.y, $$1.z - 1);
/* 103 */     if (isOpen($$6)) {
/* 104 */       $$0[$$2++] = $$6;
/*     */     }
/*     */     
/* 107 */     Node $$7 = findAcceptedNode($$1.x, $$1.y + 1, $$1.z);
/* 108 */     if (isOpen($$7)) {
/* 109 */       $$0[$$2++] = $$7;
/*     */     }
/*     */     
/* 112 */     Node $$8 = findAcceptedNode($$1.x, $$1.y - 1, $$1.z);
/* 113 */     if (isOpen($$8)) {
/* 114 */       $$0[$$2++] = $$8;
/*     */     }
/*     */     
/* 117 */     Node $$9 = findAcceptedNode($$1.x, $$1.y + 1, $$1.z + 1);
/* 118 */     if (isOpen($$9) && hasMalus($$3) && hasMalus($$7)) {
/* 119 */       $$0[$$2++] = $$9;
/*     */     }
/*     */     
/* 122 */     Node $$10 = findAcceptedNode($$1.x - 1, $$1.y + 1, $$1.z);
/* 123 */     if (isOpen($$10) && hasMalus($$4) && hasMalus($$7)) {
/* 124 */       $$0[$$2++] = $$10;
/*     */     }
/*     */     
/* 127 */     Node $$11 = findAcceptedNode($$1.x + 1, $$1.y + 1, $$1.z);
/* 128 */     if (isOpen($$11) && hasMalus($$5) && hasMalus($$7)) {
/* 129 */       $$0[$$2++] = $$11;
/*     */     }
/*     */     
/* 132 */     Node $$12 = findAcceptedNode($$1.x, $$1.y + 1, $$1.z - 1);
/* 133 */     if (isOpen($$12) && hasMalus($$6) && hasMalus($$7)) {
/* 134 */       $$0[$$2++] = $$12;
/*     */     }
/*     */     
/* 137 */     Node $$13 = findAcceptedNode($$1.x, $$1.y - 1, $$1.z + 1);
/* 138 */     if (isOpen($$13) && hasMalus($$3) && hasMalus($$8)) {
/* 139 */       $$0[$$2++] = $$13;
/*     */     }
/*     */     
/* 142 */     Node $$14 = findAcceptedNode($$1.x - 1, $$1.y - 1, $$1.z);
/* 143 */     if (isOpen($$14) && hasMalus($$4) && hasMalus($$8)) {
/* 144 */       $$0[$$2++] = $$14;
/*     */     }
/*     */     
/* 147 */     Node $$15 = findAcceptedNode($$1.x + 1, $$1.y - 1, $$1.z);
/* 148 */     if (isOpen($$15) && hasMalus($$5) && hasMalus($$8)) {
/* 149 */       $$0[$$2++] = $$15;
/*     */     }
/*     */     
/* 152 */     Node $$16 = findAcceptedNode($$1.x, $$1.y - 1, $$1.z - 1);
/* 153 */     if (isOpen($$16) && hasMalus($$6) && hasMalus($$8)) {
/* 154 */       $$0[$$2++] = $$16;
/*     */     }
/*     */     
/* 157 */     Node $$17 = findAcceptedNode($$1.x + 1, $$1.y, $$1.z - 1);
/* 158 */     if (isOpen($$17) && hasMalus($$6) && hasMalus($$5)) {
/* 159 */       $$0[$$2++] = $$17;
/*     */     }
/*     */     
/* 162 */     Node $$18 = findAcceptedNode($$1.x + 1, $$1.y, $$1.z + 1);
/* 163 */     if (isOpen($$18) && hasMalus($$3) && hasMalus($$5)) {
/* 164 */       $$0[$$2++] = $$18;
/*     */     }
/*     */     
/* 167 */     Node $$19 = findAcceptedNode($$1.x - 1, $$1.y, $$1.z - 1);
/* 168 */     if (isOpen($$19) && hasMalus($$6) && hasMalus($$4)) {
/* 169 */       $$0[$$2++] = $$19;
/*     */     }
/*     */     
/* 172 */     Node $$20 = findAcceptedNode($$1.x - 1, $$1.y, $$1.z + 1);
/* 173 */     if (isOpen($$20) && hasMalus($$3) && hasMalus($$4)) {
/* 174 */       $$0[$$2++] = $$20;
/*     */     }
/*     */     
/* 177 */     Node $$21 = findAcceptedNode($$1.x + 1, $$1.y + 1, $$1.z - 1);
/* 178 */     if (isOpen($$21) && hasMalus($$17) && hasMalus($$6) && hasMalus($$5) && hasMalus($$7) && hasMalus($$12) && hasMalus($$11)) {
/* 179 */       $$0[$$2++] = $$21;
/*     */     }
/*     */     
/* 182 */     Node $$22 = findAcceptedNode($$1.x + 1, $$1.y + 1, $$1.z + 1);
/* 183 */     if (isOpen($$22) && hasMalus($$18) && hasMalus($$3) && hasMalus($$5) && hasMalus($$7) && hasMalus($$9) && hasMalus($$11)) {
/* 184 */       $$0[$$2++] = $$22;
/*     */     }
/*     */     
/* 187 */     Node $$23 = findAcceptedNode($$1.x - 1, $$1.y + 1, $$1.z - 1);
/* 188 */     if (isOpen($$23) && hasMalus($$19) && hasMalus($$6) && hasMalus($$4) && hasMalus($$7) && hasMalus($$12) && hasMalus($$10)) {
/* 189 */       $$0[$$2++] = $$23;
/*     */     }
/*     */     
/* 192 */     Node $$24 = findAcceptedNode($$1.x - 1, $$1.y + 1, $$1.z + 1);
/* 193 */     if (isOpen($$24) && hasMalus($$20) && hasMalus($$3) && hasMalus($$4) && hasMalus($$7) && hasMalus($$9) && hasMalus($$10)) {
/* 194 */       $$0[$$2++] = $$24;
/*     */     }
/*     */     
/* 197 */     Node $$25 = findAcceptedNode($$1.x + 1, $$1.y - 1, $$1.z - 1);
/* 198 */     if (isOpen($$25) && hasMalus($$17) && hasMalus($$6) && hasMalus($$5) && hasMalus($$8) && hasMalus($$16) && hasMalus($$15)) {
/* 199 */       $$0[$$2++] = $$25;
/*     */     }
/*     */     
/* 202 */     Node $$26 = findAcceptedNode($$1.x + 1, $$1.y - 1, $$1.z + 1);
/* 203 */     if (isOpen($$26) && hasMalus($$18) && hasMalus($$3) && hasMalus($$5) && hasMalus($$8) && hasMalus($$13) && hasMalus($$15)) {
/* 204 */       $$0[$$2++] = $$26;
/*     */     }
/*     */     
/* 207 */     Node $$27 = findAcceptedNode($$1.x - 1, $$1.y - 1, $$1.z - 1);
/* 208 */     if (isOpen($$27) && hasMalus($$19) && hasMalus($$6) && hasMalus($$4) && hasMalus($$8) && hasMalus($$16) && hasMalus($$14)) {
/* 209 */       $$0[$$2++] = $$27;
/*     */     }
/*     */     
/* 212 */     Node $$28 = findAcceptedNode($$1.x - 1, $$1.y - 1, $$1.z + 1);
/* 213 */     if (isOpen($$28) && hasMalus($$20) && hasMalus($$3) && hasMalus($$4) && hasMalus($$8) && hasMalus($$13) && hasMalus($$14)) {
/* 214 */       $$0[$$2++] = $$28;
/*     */     }
/*     */     
/* 217 */     return $$2;
/*     */   }
/*     */   
/*     */   private boolean hasMalus(@Nullable Node $$0) {
/* 221 */     return ($$0 != null && $$0.costMalus >= 0.0F);
/*     */   }
/*     */   
/*     */   private boolean isOpen(@Nullable Node $$0) {
/* 225 */     return ($$0 != null && !$$0.closed);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Node findAcceptedNode(int $$0, int $$1, int $$2) {
/* 230 */     Node $$3 = null;
/*     */     
/* 232 */     BlockPathTypes $$4 = getCachedBlockPathType($$0, $$1, $$2);
/*     */     
/* 234 */     float $$5 = this.mob.getPathfindingMalus($$4);
/*     */     
/* 236 */     if ($$5 >= 0.0F) {
/* 237 */       $$3 = getNode($$0, $$1, $$2);
/* 238 */       $$3.type = $$4;
/* 239 */       $$3.costMalus = Math.max($$3.costMalus, $$5);
/*     */       
/* 241 */       if ($$4 == BlockPathTypes.WALKABLE) {
/* 242 */         $$3.costMalus++;
/*     */       }
/*     */     } 
/*     */     
/* 246 */     return $$3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BlockPathTypes getCachedBlockPathType(int $$0, int $$1, int $$2) {
/* 253 */     return (BlockPathTypes)this.pathTypeByPosCache.computeIfAbsent(BlockPos.asLong($$0, $$1, $$2), $$3 -> getBlockPathType((BlockGetter)this.level, $$0, $$1, $$2, this.mob));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPathTypes getBlockPathType(BlockGetter $$0, int $$1, int $$2, int $$3, Mob $$4) {
/* 259 */     EnumSet<BlockPathTypes> $$5 = EnumSet.noneOf(BlockPathTypes.class);
/* 260 */     BlockPathTypes $$6 = BlockPathTypes.BLOCKED;
/*     */     
/* 262 */     BlockPos $$7 = $$4.blockPosition();
/*     */     
/* 264 */     $$6 = getBlockPathTypes($$0, $$1, $$2, $$3, $$5, $$6, $$7);
/*     */     
/* 266 */     if ($$5.contains(BlockPathTypes.FENCE)) {
/* 267 */       return BlockPathTypes.FENCE;
/*     */     }
/*     */     
/* 270 */     BlockPathTypes $$8 = BlockPathTypes.BLOCKED;
/* 271 */     for (BlockPathTypes $$9 : $$5) {
/*     */       
/* 273 */       if ($$4.getPathfindingMalus($$9) < 0.0F) {
/* 274 */         return $$9;
/*     */       }
/*     */ 
/*     */       
/* 278 */       if ($$4.getPathfindingMalus($$9) >= $$4.getPathfindingMalus($$8)) {
/* 279 */         $$8 = $$9;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 284 */     if ($$6 == BlockPathTypes.OPEN && $$4.getPathfindingMalus($$8) == 0.0F) {
/* 285 */       return BlockPathTypes.OPEN;
/*     */     }
/*     */     
/* 288 */     return $$8;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPathTypes getBlockPathType(BlockGetter $$0, int $$1, int $$2, int $$3) {
/* 293 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/* 294 */     BlockPathTypes $$5 = getBlockPathTypeRaw($$0, (BlockPos)$$4.set($$1, $$2, $$3));
/*     */     
/* 296 */     if ($$5 == BlockPathTypes.OPEN && $$2 >= $$0.getMinBuildHeight() + 1) {
/* 297 */       BlockPathTypes $$6 = getBlockPathTypeRaw($$0, (BlockPos)$$4.set($$1, $$2 - 1, $$3));
/*     */       
/* 299 */       if ($$6 == BlockPathTypes.DAMAGE_FIRE || $$6 == BlockPathTypes.LAVA) {
/* 300 */         $$5 = BlockPathTypes.DAMAGE_FIRE;
/* 301 */       } else if ($$6 == BlockPathTypes.DAMAGE_OTHER) {
/* 302 */         $$5 = BlockPathTypes.DAMAGE_OTHER;
/* 303 */       } else if ($$6 == BlockPathTypes.COCOA) {
/* 304 */         $$5 = BlockPathTypes.COCOA;
/* 305 */       } else if ($$6 == BlockPathTypes.FENCE) {
/* 306 */         if (!$$4.equals(this.mob.blockPosition())) {
/* 307 */           $$5 = BlockPathTypes.FENCE;
/*     */         }
/*     */       } else {
/*     */         
/* 311 */         $$5 = ($$6 == BlockPathTypes.WALKABLE || $$6 == BlockPathTypes.OPEN || $$6 == BlockPathTypes.WATER) ? BlockPathTypes.OPEN : BlockPathTypes.WALKABLE;
/*     */       } 
/*     */     } 
/*     */     
/* 315 */     if ($$5 == BlockPathTypes.WALKABLE || $$5 == BlockPathTypes.OPEN) {
/* 316 */       $$5 = checkNeighbourBlocks($$0, $$4.set($$1, $$2, $$3), $$5);
/*     */     }
/*     */     
/* 319 */     return $$5;
/*     */   }
/*     */   
/*     */   private Iterable<BlockPos> iteratePathfindingStartNodeCandidatePositions(Mob $$0) {
/* 323 */     float $$1 = 1.0F;
/* 324 */     AABB $$2 = $$0.getBoundingBox();
/* 325 */     boolean $$3 = ($$2.getSize() < 1.0D);
/* 326 */     if (!$$3) {
/* 327 */       return List.of(
/* 328 */           BlockPos.containing($$2.minX, $$0.getBlockY(), $$2.minZ), 
/* 329 */           BlockPos.containing($$2.minX, $$0.getBlockY(), $$2.maxZ), 
/* 330 */           BlockPos.containing($$2.maxX, $$0.getBlockY(), $$2.minZ), 
/* 331 */           BlockPos.containing($$2.maxX, $$0.getBlockY(), $$2.maxZ));
/*     */     }
/*     */     
/* 334 */     double $$4 = Math.max(0.0D, (1.5D - $$2.getZsize()) / 2.0D);
/* 335 */     double $$5 = Math.max(0.0D, (1.5D - $$2.getXsize()) / 2.0D);
/* 336 */     double $$6 = Math.max(0.0D, (1.5D - $$2.getYsize()) / 2.0D);
/* 337 */     AABB $$7 = $$2.inflate($$5, $$6, $$4);
/* 338 */     return BlockPos.randomBetweenClosed($$0.getRandom(), 10, 
/* 339 */         Mth.floor($$7.minX), Mth.floor($$7.minY), Mth.floor($$7.minZ), 
/* 340 */         Mth.floor($$7.maxX), Mth.floor($$7.maxY), Mth.floor($$7.maxZ));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\pathfinder\FlyNodeEvaluator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */