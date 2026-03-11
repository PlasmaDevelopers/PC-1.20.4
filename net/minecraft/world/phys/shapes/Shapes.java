/*     */ package net.minecraft.world.phys.shapes;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.math.DoubleMath;
/*     */ import com.google.common.math.IntMath;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.AxisCycle;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ 
/*     */ public final class Shapes {
/*     */   public static final double EPSILON = 1.0E-7D;
/*     */   public static final double BIG_EPSILON = 1.0E-6D;
/*     */   private static final VoxelShape BLOCK;
/*     */   
/*     */   static {
/*  20 */     BLOCK = (VoxelShape)Util.make(() -> {
/*     */           DiscreteVoxelShape $$0 = new BitSetDiscreteVoxelShape(1, 1, 1);
/*     */           $$0.fill(0, 0, 0);
/*     */           return new CubeVoxelShape($$0);
/*     */         });
/*     */   }
/*  26 */   public static final VoxelShape INFINITY = box(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private static final VoxelShape EMPTY = new ArrayVoxelShape(new BitSetDiscreteVoxelShape(0, 0, 0), (DoubleList)new DoubleArrayList(new double[] { 0.0D }, ), (DoubleList)new DoubleArrayList(new double[] { 0.0D }, ), (DoubleList)new DoubleArrayList(new double[] { 0.0D }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static VoxelShape empty() {
/*  39 */     return EMPTY;
/*     */   }
/*     */   
/*     */   public static VoxelShape block() {
/*  43 */     return BLOCK;
/*     */   }
/*     */   
/*     */   public static VoxelShape box(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
/*  47 */     if ($$0 > $$3 || $$1 > $$4 || $$2 > $$5) {
/*  48 */       throw new IllegalArgumentException("The min values need to be smaller or equals to the max values");
/*     */     }
/*  50 */     return create($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public static VoxelShape create(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
/*  54 */     if ($$3 - $$0 < 1.0E-7D || $$4 - $$1 < 1.0E-7D || $$5 - $$2 < 1.0E-7D) {
/*  55 */       return empty();
/*     */     }
/*     */     
/*  58 */     int $$6 = findBits($$0, $$3);
/*  59 */     int $$7 = findBits($$1, $$4);
/*  60 */     int $$8 = findBits($$2, $$5);
/*     */     
/*  62 */     if ($$6 < 0 || $$7 < 0 || $$8 < 0) {
/*  63 */       return new ArrayVoxelShape(BLOCK.shape, 
/*     */           
/*  65 */           (DoubleList)DoubleArrayList.wrap(new double[] { $$0, $$3
/*  66 */             }, ), (DoubleList)DoubleArrayList.wrap(new double[] { $$1, $$4
/*  67 */             }, ), (DoubleList)DoubleArrayList.wrap(new double[] { $$2, $$5 }));
/*     */     }
/*     */ 
/*     */     
/*  71 */     if ($$6 == 0 && $$7 == 0 && $$8 == 0) {
/*  72 */       return block();
/*     */     }
/*     */     
/*  75 */     int $$9 = 1 << $$6;
/*  76 */     int $$10 = 1 << $$7;
/*  77 */     int $$11 = 1 << $$8;
/*     */     
/*  79 */     BitSetDiscreteVoxelShape $$12 = BitSetDiscreteVoxelShape.withFilledBounds($$9, $$10, $$11, 
/*     */ 
/*     */ 
/*     */         
/*  83 */         (int)Math.round($$0 * $$9), 
/*  84 */         (int)Math.round($$1 * $$10), 
/*  85 */         (int)Math.round($$2 * $$11), 
/*  86 */         (int)Math.round($$3 * $$9), 
/*  87 */         (int)Math.round($$4 * $$10), 
/*  88 */         (int)Math.round($$5 * $$11));
/*     */     
/*  90 */     return new CubeVoxelShape($$12);
/*     */   }
/*     */   
/*     */   public static VoxelShape create(AABB $$0) {
/*  94 */     return create($$0.minX, $$0.minY, $$0.minZ, $$0.maxX, $$0.maxY, $$0.maxZ);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   protected static int findBits(double $$0, double $$1) {
/*  99 */     if ($$0 < -1.0E-7D || $$1 > 1.0000001D) {
/* 100 */       return -1;
/*     */     }
/* 102 */     for (int $$2 = 0; $$2 <= 3; $$2++) {
/* 103 */       int $$3 = 1 << $$2;
/* 104 */       double $$4 = $$0 * $$3;
/* 105 */       double $$5 = $$1 * $$3;
/* 106 */       boolean $$6 = (Math.abs($$4 - Math.round($$4)) < 1.0E-7D * $$3);
/* 107 */       boolean $$7 = (Math.abs($$5 - Math.round($$5)) < 1.0E-7D * $$3);
/* 108 */       if ($$6 && $$7) {
/* 109 */         return $$2;
/*     */       }
/*     */     } 
/* 112 */     return -1;
/*     */   }
/*     */   
/*     */   protected static long lcm(int $$0, int $$1) {
/* 116 */     return $$0 * ($$1 / IntMath.gcd($$0, $$1));
/*     */   }
/*     */   
/*     */   public static VoxelShape or(VoxelShape $$0, VoxelShape $$1) {
/* 120 */     return join($$0, $$1, BooleanOp.OR);
/*     */   }
/*     */   
/*     */   public static VoxelShape or(VoxelShape $$0, VoxelShape... $$1) {
/* 124 */     return Arrays.<VoxelShape>stream($$1).reduce($$0, Shapes::or);
/*     */   }
/*     */   
/*     */   public static VoxelShape join(VoxelShape $$0, VoxelShape $$1, BooleanOp $$2) {
/* 128 */     return joinUnoptimized($$0, $$1, $$2).optimize();
/*     */   }
/*     */   
/*     */   public static VoxelShape joinUnoptimized(VoxelShape $$0, VoxelShape $$1, BooleanOp $$2) {
/* 132 */     if ($$2.apply(false, false)) {
/* 133 */       throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException());
/*     */     }
/* 135 */     if ($$0 == $$1) {
/* 136 */       return $$2.apply(true, true) ? $$0 : empty();
/*     */     }
/* 138 */     boolean $$3 = $$2.apply(true, false);
/* 139 */     boolean $$4 = $$2.apply(false, true);
/*     */     
/* 141 */     if ($$0.isEmpty()) {
/* 142 */       return $$4 ? $$1 : empty();
/*     */     }
/* 144 */     if ($$1.isEmpty()) {
/* 145 */       return $$3 ? $$0 : empty();
/*     */     }
/*     */     
/* 148 */     IndexMerger $$5 = createIndexMerger(1, $$0.getCoords(Direction.Axis.X), $$1.getCoords(Direction.Axis.X), $$3, $$4);
/* 149 */     IndexMerger $$6 = createIndexMerger($$5.size() - 1, $$0.getCoords(Direction.Axis.Y), $$1.getCoords(Direction.Axis.Y), $$3, $$4);
/* 150 */     IndexMerger $$7 = createIndexMerger(($$5.size() - 1) * ($$6.size() - 1), $$0.getCoords(Direction.Axis.Z), $$1.getCoords(Direction.Axis.Z), $$3, $$4);
/*     */     
/* 152 */     BitSetDiscreteVoxelShape $$8 = BitSetDiscreteVoxelShape.join($$0.shape, $$1.shape, $$5, $$6, $$7, $$2);
/* 153 */     if ($$5 instanceof DiscreteCubeMerger && $$6 instanceof DiscreteCubeMerger && $$7 instanceof DiscreteCubeMerger) {
/* 154 */       return new CubeVoxelShape($$8);
/*     */     }
/* 156 */     return new ArrayVoxelShape($$8, $$5.getList(), $$6.getList(), $$7.getList());
/*     */   }
/*     */   
/*     */   public static boolean joinIsNotEmpty(VoxelShape $$0, VoxelShape $$1, BooleanOp $$2) {
/* 160 */     if ($$2.apply(false, false)) {
/* 161 */       throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException());
/*     */     }
/*     */     
/* 164 */     boolean $$3 = $$0.isEmpty();
/* 165 */     boolean $$4 = $$1.isEmpty();
/* 166 */     if ($$3 || $$4) {
/* 167 */       return $$2.apply(!$$3, !$$4);
/*     */     }
/* 169 */     if ($$0 == $$1) {
/* 170 */       return $$2.apply(true, true);
/*     */     }
/*     */     
/* 173 */     boolean $$5 = $$2.apply(true, false);
/* 174 */     boolean $$6 = $$2.apply(false, true);
/* 175 */     for (Direction.Axis $$7 : AxisCycle.AXIS_VALUES) {
/* 176 */       if ($$0.max($$7) < $$1.min($$7) - 1.0E-7D) {
/* 177 */         return ($$5 || $$6);
/*     */       }
/* 179 */       if ($$1.max($$7) < $$0.min($$7) - 1.0E-7D) {
/* 180 */         return ($$5 || $$6);
/*     */       }
/*     */     } 
/*     */     
/* 184 */     IndexMerger $$8 = createIndexMerger(1, $$0.getCoords(Direction.Axis.X), $$1.getCoords(Direction.Axis.X), $$5, $$6);
/* 185 */     IndexMerger $$9 = createIndexMerger($$8.size() - 1, $$0.getCoords(Direction.Axis.Y), $$1.getCoords(Direction.Axis.Y), $$5, $$6);
/* 186 */     IndexMerger $$10 = createIndexMerger(($$8.size() - 1) * ($$9.size() - 1), $$0.getCoords(Direction.Axis.Z), $$1.getCoords(Direction.Axis.Z), $$5, $$6);
/* 187 */     return joinIsNotEmpty($$8, $$9, $$10, $$0.shape, $$1.shape, $$2);
/*     */   }
/*     */   
/*     */   private static boolean joinIsNotEmpty(IndexMerger $$0, IndexMerger $$1, IndexMerger $$2, DiscreteVoxelShape $$3, DiscreteVoxelShape $$4, BooleanOp $$5) {
/* 191 */     return !$$0.forMergedIndexes(($$5, $$6, $$7) -> $$0.forMergedIndexes(()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double collide(Direction.Axis $$0, AABB $$1, Iterable<VoxelShape> $$2, double $$3) {
/* 201 */     for (VoxelShape $$4 : $$2) {
/* 202 */       if (Math.abs($$3) < 1.0E-7D) {
/* 203 */         return 0.0D;
/*     */       }
/* 205 */       $$3 = $$4.collide($$0, $$1, $$3);
/*     */     } 
/* 207 */     return $$3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean blockOccudes(VoxelShape $$0, VoxelShape $$1, Direction $$2) {
/* 214 */     if ($$0 == block() && $$1 == block()) {
/* 215 */       return true;
/*     */     }
/* 217 */     if ($$1.isEmpty()) {
/* 218 */       return false;
/*     */     }
/* 220 */     Direction.Axis $$3 = $$2.getAxis();
/* 221 */     Direction.AxisDirection $$4 = $$2.getAxisDirection();
/*     */     
/* 223 */     VoxelShape $$5 = ($$4 == Direction.AxisDirection.POSITIVE) ? $$0 : $$1;
/* 224 */     VoxelShape $$6 = ($$4 == Direction.AxisDirection.POSITIVE) ? $$1 : $$0;
/* 225 */     BooleanOp $$7 = ($$4 == Direction.AxisDirection.POSITIVE) ? BooleanOp.ONLY_FIRST : BooleanOp.ONLY_SECOND;
/*     */     
/* 227 */     return (DoubleMath.fuzzyEquals($$5.max($$3), 1.0D, 1.0E-7D) && 
/* 228 */       DoubleMath.fuzzyEquals($$6.min($$3), 0.0D, 1.0E-7D) && 
/* 229 */       !joinIsNotEmpty(new SliceShape($$5, $$3, $$5.shape.getSize($$3) - 1), new SliceShape($$6, $$3, 0), $$7));
/*     */   } public static VoxelShape getFaceShape(VoxelShape $$0, Direction $$1) {
/*     */     boolean $$5;
/*     */     int $$6;
/* 233 */     if ($$0 == block()) {
/* 234 */       return block();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 239 */     Direction.Axis $$2 = $$1.getAxis();
/* 240 */     if ($$1.getAxisDirection() == Direction.AxisDirection.POSITIVE) {
/* 241 */       boolean $$3 = DoubleMath.fuzzyEquals($$0.max($$2), 1.0D, 1.0E-7D);
/* 242 */       int $$4 = $$0.shape.getSize($$2) - 1;
/*     */     } else {
/* 244 */       $$5 = DoubleMath.fuzzyEquals($$0.min($$2), 0.0D, 1.0E-7D);
/* 245 */       $$6 = 0;
/*     */     } 
/*     */     
/* 248 */     if (!$$5) {
/* 249 */       return empty();
/*     */     }
/*     */     
/* 252 */     return new SliceShape($$0, $$2, $$6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean mergedFaceOccludes(VoxelShape $$0, VoxelShape $$1, Direction $$2) {
/* 259 */     if ($$0 == block() || $$1 == block()) {
/* 260 */       return true;
/*     */     }
/*     */     
/* 263 */     Direction.Axis $$3 = $$2.getAxis();
/* 264 */     Direction.AxisDirection $$4 = $$2.getAxisDirection();
/*     */     
/* 266 */     VoxelShape $$5 = ($$4 == Direction.AxisDirection.POSITIVE) ? $$0 : $$1;
/* 267 */     VoxelShape $$6 = ($$4 == Direction.AxisDirection.POSITIVE) ? $$1 : $$0;
/*     */     
/* 269 */     if (!DoubleMath.fuzzyEquals($$5.max($$3), 1.0D, 1.0E-7D)) {
/* 270 */       $$5 = empty();
/*     */     }
/* 272 */     if (!DoubleMath.fuzzyEquals($$6.min($$3), 0.0D, 1.0E-7D)) {
/* 273 */       $$6 = empty();
/*     */     }
/*     */     
/* 276 */     return !joinIsNotEmpty(block(), joinUnoptimized(new SliceShape($$5, $$3, $$5.shape.getSize($$3) - 1), new SliceShape($$6, $$3, 0), BooleanOp.OR), BooleanOp.ONLY_FIRST);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean faceShapeOccludes(VoxelShape $$0, VoxelShape $$1) {
/* 283 */     if ($$0 == block() || $$1 == block()) {
/* 284 */       return true;
/*     */     }
/*     */     
/* 287 */     if ($$0.isEmpty() && $$1.isEmpty()) {
/* 288 */       return false;
/*     */     }
/*     */     
/* 291 */     return !joinIsNotEmpty(
/* 292 */         block(), 
/* 293 */         joinUnoptimized($$0, $$1, BooleanOp.OR), BooleanOp.ONLY_FIRST);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   protected static IndexMerger createIndexMerger(int $$0, DoubleList $$1, DoubleList $$2, boolean $$3, boolean $$4) {
/* 304 */     int $$5 = $$1.size() - 1;
/* 305 */     int $$6 = $$2.size() - 1;
/* 306 */     if ($$1 instanceof CubePointRange && $$2 instanceof CubePointRange) {
/* 307 */       long $$7 = lcm($$5, $$6);
/* 308 */       if ($$0 * $$7 <= 256L) {
/* 309 */         return new DiscreteCubeMerger($$5, $$6);
/*     */       }
/*     */     } 
/*     */     
/* 313 */     if ($$1.getDouble($$5) < $$2.getDouble(0) - 1.0E-7D)
/* 314 */       return new NonOverlappingMerger($$1, $$2, false); 
/* 315 */     if ($$2.getDouble($$6) < $$1.getDouble(0) - 1.0E-7D) {
/* 316 */       return new NonOverlappingMerger($$2, $$1, true);
/*     */     }
/*     */     
/* 319 */     if ($$5 == $$6 && Objects.equals($$1, $$2)) {
/* 320 */       return new IdenticalMerger($$1);
/*     */     }
/*     */     
/* 323 */     return new IndirectMerger($$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static interface DoubleLineConsumer {
/*     */     void consume(double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\Shapes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */