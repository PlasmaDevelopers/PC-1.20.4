/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.google.common.collect.AbstractIterator;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Iterator;
/*     */ import java.util.Optional;
/*     */ import java.util.Queue;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import javax.annotation.concurrent.Immutable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ @Immutable
/*     */ public class BlockPos
/*     */   extends Vec3i {
/*     */   public static final Codec<BlockPos> CODEC;
/*     */   
/*     */   static {
/*  36 */     CODEC = Codec.INT_STREAM.comapFlatMap($$0 -> Util.fixedSize($$0, 3).map(()), $$0 -> IntStream.of(new int[] { $$0.getX(), $$0.getY(), $$0.getZ() })).stable();
/*     */   }
/*  38 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  40 */   public static final BlockPos ZERO = new BlockPos(0, 0, 0);
/*     */ 
/*     */   
/*  43 */   private static final int PACKED_X_LENGTH = 1 + Mth.log2(Mth.smallestEncompassingPowerOfTwo(30000000));
/*     */   
/*  45 */   private static final int PACKED_Z_LENGTH = PACKED_X_LENGTH;
/*     */   
/*  47 */   public static final int PACKED_Y_LENGTH = 64 - PACKED_X_LENGTH - PACKED_Z_LENGTH;
/*     */   
/*  49 */   private static final long PACKED_X_MASK = (1L << PACKED_X_LENGTH) - 1L;
/*  50 */   private static final long PACKED_Y_MASK = (1L << PACKED_Y_LENGTH) - 1L;
/*  51 */   private static final long PACKED_Z_MASK = (1L << PACKED_Z_LENGTH) - 1L;
/*     */   
/*     */   private static final int Y_OFFSET = 0;
/*  54 */   private static final int Z_OFFSET = PACKED_Y_LENGTH;
/*  55 */   private static final int X_OFFSET = PACKED_Y_LENGTH + PACKED_Z_LENGTH;
/*     */   
/*     */   public BlockPos(int $$0, int $$1, int $$2) {
/*  58 */     super($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public BlockPos(Vec3i $$0) {
/*  62 */     this($$0.getX(), $$0.getY(), $$0.getZ());
/*     */   }
/*     */   
/*     */   public static long offset(long $$0, Direction $$1) {
/*  66 */     return offset($$0, $$1.getStepX(), $$1.getStepY(), $$1.getStepZ());
/*     */   }
/*     */   
/*     */   public static long offset(long $$0, int $$1, int $$2, int $$3) {
/*  70 */     return asLong(getX($$0) + $$1, getY($$0) + $$2, getZ($$0) + $$3);
/*     */   }
/*     */   
/*     */   public static int getX(long $$0) {
/*  74 */     return (int)($$0 << 64 - X_OFFSET - PACKED_X_LENGTH >> 64 - PACKED_X_LENGTH);
/*     */   }
/*     */   
/*     */   public static int getY(long $$0) {
/*  78 */     return (int)($$0 << 64 - PACKED_Y_LENGTH >> 64 - PACKED_Y_LENGTH);
/*     */   }
/*     */   
/*     */   public static int getZ(long $$0) {
/*  82 */     return (int)($$0 << 64 - Z_OFFSET - PACKED_Z_LENGTH >> 64 - PACKED_Z_LENGTH);
/*     */   }
/*     */   
/*     */   public static BlockPos of(long $$0) {
/*  86 */     return new BlockPos(getX($$0), getY($$0), getZ($$0));
/*     */   }
/*     */   
/*     */   public static BlockPos containing(double $$0, double $$1, double $$2) {
/*  90 */     return new BlockPos(Mth.floor($$0), Mth.floor($$1), Mth.floor($$2));
/*     */   }
/*     */   
/*     */   public static BlockPos containing(Position $$0) {
/*  94 */     return containing($$0.x(), $$0.y(), $$0.z());
/*     */   }
/*     */   
/*     */   public long asLong() {
/*  98 */     return asLong(getX(), getY(), getZ());
/*     */   }
/*     */   
/*     */   public static long asLong(int $$0, int $$1, int $$2) {
/* 102 */     long $$3 = 0L;
/* 103 */     $$3 |= ($$0 & PACKED_X_MASK) << X_OFFSET;
/* 104 */     $$3 |= ($$1 & PACKED_Y_MASK) << 0L;
/* 105 */     $$3 |= ($$2 & PACKED_Z_MASK) << Z_OFFSET;
/* 106 */     return $$3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getFlatIndex(long $$0) {
/* 114 */     return $$0 & 0xFFFFFFFFFFFFFFF0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos offset(int $$0, int $$1, int $$2) {
/* 119 */     if ($$0 == 0 && $$1 == 0 && $$2 == 0) {
/* 120 */       return this;
/*     */     }
/* 122 */     return new BlockPos(getX() + $$0, getY() + $$1, getZ() + $$2);
/*     */   }
/*     */   
/*     */   public Vec3 getCenter() {
/* 126 */     return Vec3.atCenterOf(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos offset(Vec3i $$0) {
/* 131 */     return offset($$0.getX(), $$0.getY(), $$0.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos subtract(Vec3i $$0) {
/* 136 */     return offset(-$$0.getX(), -$$0.getY(), -$$0.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos multiply(int $$0) {
/* 141 */     if ($$0 == 1)
/* 142 */       return this; 
/* 143 */     if ($$0 == 0) {
/* 144 */       return ZERO;
/*     */     }
/* 146 */     return new BlockPos(getX() * $$0, getY() * $$0, getZ() * $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos above() {
/* 151 */     return relative(Direction.UP);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos above(int $$0) {
/* 156 */     return relative(Direction.UP, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos below() {
/* 161 */     return relative(Direction.DOWN);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos below(int $$0) {
/* 166 */     return relative(Direction.DOWN, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos north() {
/* 171 */     return relative(Direction.NORTH);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos north(int $$0) {
/* 176 */     return relative(Direction.NORTH, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos south() {
/* 181 */     return relative(Direction.SOUTH);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos south(int $$0) {
/* 186 */     return relative(Direction.SOUTH, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos west() {
/* 191 */     return relative(Direction.WEST);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos west(int $$0) {
/* 196 */     return relative(Direction.WEST, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos east() {
/* 201 */     return relative(Direction.EAST);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos east(int $$0) {
/* 206 */     return relative(Direction.EAST, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos relative(Direction $$0) {
/* 211 */     return new BlockPos(getX() + $$0.getStepX(), getY() + $$0.getStepY(), getZ() + $$0.getStepZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos relative(Direction $$0, int $$1) {
/* 216 */     if ($$1 == 0) {
/* 217 */       return this;
/*     */     }
/* 219 */     return new BlockPos(getX() + $$0.getStepX() * $$1, getY() + $$0.getStepY() * $$1, getZ() + $$0.getStepZ() * $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos relative(Direction.Axis $$0, int $$1) {
/* 224 */     if ($$1 == 0) {
/* 225 */       return this;
/*     */     }
/* 227 */     int $$2 = ($$0 == Direction.Axis.X) ? $$1 : 0;
/* 228 */     int $$3 = ($$0 == Direction.Axis.Y) ? $$1 : 0;
/* 229 */     int $$4 = ($$0 == Direction.Axis.Z) ? $$1 : 0;
/* 230 */     return new BlockPos(getX() + $$2, getY() + $$3, getZ() + $$4);
/*     */   }
/*     */   
/*     */   public BlockPos rotate(Rotation $$0) {
/* 234 */     switch ($$0)
/*     */     
/*     */     { default:
/* 237 */         return this;
/*     */       case Y:
/* 239 */         return new BlockPos(-getZ(), getY(), getX());
/*     */       case Z:
/* 241 */         return new BlockPos(-getX(), getY(), -getZ());
/*     */       case null:
/* 243 */         break; }  return new BlockPos(getZ(), getY(), -getX());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockPos cross(Vec3i $$0) {
/* 249 */     return new BlockPos(getY() * $$0.getZ() - getZ() * $$0.getY(), getZ() * $$0.getX() - getX() * $$0.getZ(), getX() * $$0.getY() - getY() * $$0.getX());
/*     */   }
/*     */   
/*     */   public BlockPos atY(int $$0) {
/* 253 */     return new BlockPos(getX(), $$0, getZ());
/*     */   }
/*     */   
/*     */   public BlockPos immutable() {
/* 257 */     return this;
/*     */   }
/*     */   
/*     */   public MutableBlockPos mutable() {
/* 261 */     return new MutableBlockPos(getX(), getY(), getZ());
/*     */   }
/*     */   
/*     */   public static class MutableBlockPos extends BlockPos {
/*     */     public MutableBlockPos() {
/* 266 */       this(0, 0, 0);
/*     */     }
/*     */     
/*     */     public MutableBlockPos(int $$0, int $$1, int $$2) {
/* 270 */       super($$0, $$1, $$2);
/*     */     }
/*     */     
/*     */     public MutableBlockPos(double $$0, double $$1, double $$2) {
/* 274 */       this(Mth.floor($$0), Mth.floor($$1), Mth.floor($$2));
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos offset(int $$0, int $$1, int $$2) {
/* 279 */       return super.offset($$0, $$1, $$2).immutable();
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos multiply(int $$0) {
/* 284 */       return super.multiply($$0).immutable();
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos relative(Direction $$0, int $$1) {
/* 289 */       return super.relative($$0, $$1).immutable();
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos relative(Direction.Axis $$0, int $$1) {
/* 294 */       return super.relative($$0, $$1).immutable();
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos rotate(Rotation $$0) {
/* 299 */       return super.rotate($$0).immutable();
/*     */     }
/*     */     
/*     */     public MutableBlockPos set(int $$0, int $$1, int $$2) {
/* 303 */       setX($$0);
/* 304 */       setY($$1);
/* 305 */       setZ($$2);
/* 306 */       return this;
/*     */     }
/*     */     
/*     */     public MutableBlockPos set(double $$0, double $$1, double $$2) {
/* 310 */       return set(Mth.floor($$0), Mth.floor($$1), Mth.floor($$2));
/*     */     }
/*     */     
/*     */     public MutableBlockPos set(Vec3i $$0) {
/* 314 */       return set($$0.getX(), $$0.getY(), $$0.getZ());
/*     */     }
/*     */     
/*     */     public MutableBlockPos set(long $$0) {
/* 318 */       return set(getX($$0), getY($$0), getZ($$0));
/*     */     }
/*     */     
/*     */     public MutableBlockPos set(AxisCycle $$0, int $$1, int $$2, int $$3) {
/* 322 */       return set($$0
/* 323 */           .cycle($$1, $$2, $$3, Direction.Axis.X), $$0
/* 324 */           .cycle($$1, $$2, $$3, Direction.Axis.Y), $$0
/* 325 */           .cycle($$1, $$2, $$3, Direction.Axis.Z));
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos setWithOffset(Vec3i $$0, Direction $$1) {
/* 330 */       return set($$0.getX() + $$1.getStepX(), $$0.getY() + $$1.getStepY(), $$0.getZ() + $$1.getStepZ());
/*     */     }
/*     */     
/*     */     public MutableBlockPos setWithOffset(Vec3i $$0, int $$1, int $$2, int $$3) {
/* 334 */       return set($$0.getX() + $$1, $$0.getY() + $$2, $$0.getZ() + $$3);
/*     */     }
/*     */     
/*     */     public MutableBlockPos setWithOffset(Vec3i $$0, Vec3i $$1) {
/* 338 */       return set($$0.getX() + $$1.getX(), $$0.getY() + $$1.getY(), $$0.getZ() + $$1.getZ());
/*     */     }
/*     */     
/*     */     public MutableBlockPos move(Direction $$0) {
/* 342 */       return move($$0, 1);
/*     */     }
/*     */     
/*     */     public MutableBlockPos move(Direction $$0, int $$1) {
/* 346 */       return set(getX() + $$0.getStepX() * $$1, getY() + $$0.getStepY() * $$1, getZ() + $$0.getStepZ() * $$1);
/*     */     }
/*     */     
/*     */     public MutableBlockPos move(int $$0, int $$1, int $$2) {
/* 350 */       return set(getX() + $$0, getY() + $$1, getZ() + $$2);
/*     */     }
/*     */     
/*     */     public MutableBlockPos move(Vec3i $$0) {
/* 354 */       return set(getX() + $$0.getX(), getY() + $$0.getY(), getZ() + $$0.getZ());
/*     */     }
/*     */     
/*     */     public MutableBlockPos clamp(Direction.Axis $$0, int $$1, int $$2) {
/* 358 */       switch ($$0) {
/*     */         case X:
/* 360 */           return set(Mth.clamp(getX(), $$1, $$2), getY(), getZ());
/*     */         case Y:
/* 362 */           return set(getX(), Mth.clamp(getY(), $$1, $$2), getZ());
/*     */         case Z:
/* 364 */           return set(getX(), getY(), Mth.clamp(getZ(), $$1, $$2));
/*     */       } 
/* 366 */       throw new IllegalStateException("Unable to clamp axis " + $$0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public MutableBlockPos setX(int $$0) {
/* 372 */       super.setX($$0);
/* 373 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos setY(int $$0) {
/* 378 */       super.setY($$0);
/* 379 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public MutableBlockPos setZ(int $$0) {
/* 384 */       super.setZ($$0);
/* 385 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockPos immutable() {
/* 390 */       return new BlockPos(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Iterable<BlockPos> randomInCube(RandomSource $$0, int $$1, BlockPos $$2, int $$3) {
/* 395 */     return randomBetweenClosed($$0, $$1, $$2.getX() - $$3, $$2.getY() - $$3, $$2.getZ() - $$3, $$2.getX() + $$3, $$2.getY() + $$3, $$2.getZ() + $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Stream<BlockPos> squareOutSouthEast(BlockPos $$0) {
/* 407 */     return Stream.of(new BlockPos[] { $$0, $$0
/*     */           
/* 409 */           .south(), $$0
/* 410 */           .east(), $$0
/* 411 */           .south().east() });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Iterable<BlockPos> randomBetweenClosed(final RandomSource limit, final int minX, final int random, final int width, final int minY, final int height, final int minZ, final int depth) {
/* 417 */     int $$8 = height - random + 1;
/* 418 */     int $$9 = minZ - width + 1;
/* 419 */     int $$10 = depth - minY + 1;
/*     */     
/* 421 */     return () -> new AbstractIterator<BlockPos>() {
/* 422 */         final BlockPos.MutableBlockPos nextPos = new BlockPos.MutableBlockPos();
/* 423 */         int counter = limit;
/*     */ 
/*     */         
/*     */         protected BlockPos computeNext() {
/* 427 */           if (this.counter <= 0) {
/* 428 */             return (BlockPos)endOfData();
/*     */           }
/*     */           
/* 431 */           BlockPos $$0 = this.nextPos.set(minX + random
/* 432 */               .nextInt(width), minY + random
/* 433 */               .nextInt(height), minZ + random
/* 434 */               .nextInt(depth));
/*     */           
/* 436 */           this.counter--;
/* 437 */           return $$0;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static Iterable<BlockPos> withinManhattan(final BlockPos originZ, final int maxDepth, final int reachX, final int reachY) {
/* 443 */     final int reachZ = maxDepth + reachX + reachY;
/* 444 */     final int originX = originZ.getX();
/* 445 */     final int originY = originZ.getY();
/* 446 */     int $$7 = originZ.getZ();
/*     */     
/* 448 */     return () -> new AbstractIterator<BlockPos>() {
/* 449 */         private final BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
/*     */         
/*     */         private int currentDepth;
/*     */         
/*     */         private int maxX;
/*     */         
/*     */         private int maxY;
/*     */         
/*     */         private int x;
/*     */         private int y;
/*     */         private boolean zMirror;
/*     */         
/*     */         protected BlockPos computeNext() {
/* 462 */           if (this.zMirror) {
/* 463 */             this.zMirror = false;
/* 464 */             this.cursor.setZ(originZ - this.cursor.getZ() - originZ);
/* 465 */             return this.cursor;
/*     */           } 
/*     */           
/* 468 */           BlockPos $$0 = null;
/* 469 */           while ($$0 == null) {
/* 470 */             if (this.y > this.maxY) {
/* 471 */               this.x++;
/* 472 */               if (this.x > this.maxX) {
/* 473 */                 this.currentDepth++;
/* 474 */                 if (this.currentDepth > maxDepth) {
/* 475 */                   return (BlockPos)endOfData();
/*     */                 }
/* 477 */                 this.maxX = Math.min(reachX, this.currentDepth);
/* 478 */                 this.x = -this.maxX;
/*     */               } 
/* 480 */               this.maxY = Math.min(reachY, this.currentDepth - Math.abs(this.x));
/* 481 */               this.y = -this.maxY;
/*     */             } 
/*     */             
/* 484 */             int $$1 = this.x;
/* 485 */             int $$2 = this.y;
/* 486 */             int $$3 = this.currentDepth - Math.abs($$1) - Math.abs($$2);
/* 487 */             if ($$3 <= reachZ) {
/* 488 */               this.zMirror = ($$3 != 0);
/* 489 */               $$0 = this.cursor.set(originX + $$1, originY + $$2, originZ + $$3);
/*     */             } 
/* 491 */             this.y++;
/*     */           } 
/* 493 */           return $$0;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static Optional<BlockPos> findClosestMatch(BlockPos $$0, int $$1, int $$2, Predicate<BlockPos> $$3) {
/* 499 */     for (BlockPos $$4 : withinManhattan($$0, $$1, $$2, $$1)) {
/* 500 */       if ($$3.test($$4)) {
/* 501 */         return Optional.of($$4);
/*     */       }
/*     */     } 
/* 504 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   public static Stream<BlockPos> withinManhattanStream(BlockPos $$0, int $$1, int $$2, int $$3) {
/* 508 */     return StreamSupport.stream(withinManhattan($$0, $$1, $$2, $$3).spliterator(), false);
/*     */   }
/*     */   
/*     */   public static Iterable<BlockPos> betweenClosed(BlockPos $$0, BlockPos $$1) {
/* 512 */     return betweenClosed(
/* 513 */         Math.min($$0.getX(), $$1.getX()), 
/* 514 */         Math.min($$0.getY(), $$1.getY()), 
/* 515 */         Math.min($$0.getZ(), $$1.getZ()), 
/* 516 */         Math.max($$0.getX(), $$1.getX()), 
/* 517 */         Math.max($$0.getY(), $$1.getY()), 
/* 518 */         Math.max($$0.getZ(), $$1.getZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Stream<BlockPos> betweenClosedStream(BlockPos $$0, BlockPos $$1) {
/* 523 */     return StreamSupport.stream(betweenClosed($$0, $$1).spliterator(), false);
/*     */   }
/*     */   
/*     */   public static Stream<BlockPos> betweenClosedStream(BoundingBox $$0) {
/* 527 */     return betweenClosedStream(
/* 528 */         Math.min($$0.minX(), $$0.maxX()), 
/* 529 */         Math.min($$0.minY(), $$0.maxY()), 
/* 530 */         Math.min($$0.minZ(), $$0.maxZ()), 
/* 531 */         Math.max($$0.minX(), $$0.maxX()), 
/* 532 */         Math.max($$0.minY(), $$0.maxY()), 
/* 533 */         Math.max($$0.minZ(), $$0.maxZ()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Stream<BlockPos> betweenClosedStream(AABB $$0) {
/* 538 */     return betweenClosedStream(Mth.floor($$0.minX), Mth.floor($$0.minY), Mth.floor($$0.minZ), Mth.floor($$0.maxX), Mth.floor($$0.maxY), Mth.floor($$0.maxZ));
/*     */   }
/*     */   
/*     */   public static Stream<BlockPos> betweenClosedStream(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 542 */     return StreamSupport.stream(betweenClosed($$0, $$1, $$2, $$3, $$4, $$5).spliterator(), false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Iterable<BlockPos> betweenClosed(final int end, final int width, final int height, final int minX, final int minY, final int minZ) {
/* 547 */     int $$6 = minX - end + 1;
/* 548 */     int $$7 = minY - width + 1;
/* 549 */     int $$8 = minZ - height + 1;
/* 550 */     int $$9 = $$6 * $$7 * $$8;
/*     */     
/* 552 */     return () -> new AbstractIterator<BlockPos>() {
/* 553 */         private final BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
/*     */         
/*     */         private int index;
/*     */         
/*     */         protected BlockPos computeNext() {
/* 558 */           if (this.index == end) {
/* 559 */             return (BlockPos)endOfData();
/*     */           }
/*     */           
/* 562 */           int $$0 = this.index % width;
/* 563 */           int $$1 = this.index / width;
/* 564 */           int $$2 = $$1 % height;
/* 565 */           int $$3 = $$1 / height;
/*     */           
/* 567 */           this.index++;
/* 568 */           return this.cursor.set(minX + $$0, minY + $$2, minZ + $$3);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static Iterable<MutableBlockPos> spiralAround(final BlockPos firstDirection, final int secondDirection, final Direction center, final Direction radius) {
/* 574 */     Validate.validState((center.getAxis() != radius.getAxis()), "The two directions cannot be on the same axis", new Object[0]);
/*     */     
/* 576 */     return () -> new AbstractIterator<MutableBlockPos>() {
/* 577 */         private final Direction[] directions = new Direction[] { this.val$firstDirection, this.val$secondDirection, this.val$firstDirection
/*     */ 
/*     */             
/* 580 */             .getOpposite(), this.val$secondDirection
/* 581 */             .getOpposite() };
/*     */         
/* 583 */         private final BlockPos.MutableBlockPos cursor = center.mutable().move(secondDirection);
/* 584 */         private final int legs = 4 * radius;
/* 585 */         private int leg = -1;
/*     */         
/*     */         private int legSize;
/*     */         private int legIndex;
/* 589 */         private int lastX = this.cursor.getX();
/* 590 */         private int lastY = this.cursor.getY();
/* 591 */         private int lastZ = this.cursor.getZ();
/*     */ 
/*     */         
/*     */         protected BlockPos.MutableBlockPos computeNext() {
/* 595 */           this.cursor.set(this.lastX, this.lastY, this.lastZ).move(this.directions[(this.leg + 4) % 4]);
/*     */           
/* 597 */           this.lastX = this.cursor.getX();
/* 598 */           this.lastY = this.cursor.getY();
/* 599 */           this.lastZ = this.cursor.getZ();
/*     */           
/* 601 */           if (this.legIndex >= this.legSize) {
/* 602 */             if (this.leg >= this.legs) {
/* 603 */               return (BlockPos.MutableBlockPos)endOfData();
/*     */             }
/* 605 */             this.leg++;
/* 606 */             this.legIndex = 0;
/* 607 */             this.legSize = this.leg / 2 + 1;
/*     */           } 
/* 609 */           this.legIndex++;
/*     */           
/* 611 */           return this.cursor;
/*     */         }
/*     */       };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int breadthFirstTraversal(BlockPos $$0, int $$1, int $$2, BiConsumer<BlockPos, Consumer<BlockPos>> $$3, Predicate<BlockPos> $$4) {
/* 631 */     Queue<Pair<BlockPos, Integer>> $$5 = new ArrayDeque<>();
/* 632 */     LongOpenHashSet longOpenHashSet = new LongOpenHashSet();
/* 633 */     $$5.add(Pair.of($$0, Integer.valueOf(0)));
/* 634 */     int $$7 = 0;
/* 635 */     while (!$$5.isEmpty()) {
/* 636 */       Pair<BlockPos, Integer> $$8 = $$5.poll();
/* 637 */       BlockPos $$9 = (BlockPos)$$8.getLeft();
/* 638 */       int $$10 = ((Integer)$$8.getRight()).intValue();
/* 639 */       long $$11 = $$9.asLong();
/* 640 */       if (!longOpenHashSet.add($$11) || !$$4.test($$9)) {
/*     */         continue;
/*     */       }
/* 643 */       $$7++;
/* 644 */       if ($$7 >= $$2) {
/* 645 */         return $$7;
/*     */       }
/* 647 */       if ($$10 >= $$1) {
/*     */         continue;
/*     */       }
/* 650 */       $$3.accept($$9, $$2 -> $$0.add(Pair.of($$2, Integer.valueOf($$1 + 1))));
/*     */     } 
/* 652 */     return $$7;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\BlockPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */