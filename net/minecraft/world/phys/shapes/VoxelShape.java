/*     */ package net.minecraft.world.phys.shapes;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.math.DoubleMath;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.AxisCycle;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ public abstract class VoxelShape
/*     */ {
/*     */   protected final DiscreteVoxelShape shape;
/*     */   @Nullable
/*     */   private VoxelShape[] faces;
/*     */   
/*     */   VoxelShape(DiscreteVoxelShape $$0) {
/*  26 */     this.shape = $$0;
/*     */   }
/*     */   
/*     */   public double min(Direction.Axis $$0) {
/*  30 */     int $$1 = this.shape.firstFull($$0);
/*  31 */     if ($$1 >= this.shape.getSize($$0)) {
/*  32 */       return Double.POSITIVE_INFINITY;
/*     */     }
/*  34 */     return get($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public double max(Direction.Axis $$0) {
/*  39 */     int $$1 = this.shape.lastFull($$0);
/*  40 */     if ($$1 <= 0) {
/*  41 */       return Double.NEGATIVE_INFINITY;
/*     */     }
/*  43 */     return get($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public AABB bounds() {
/*  48 */     if (isEmpty()) {
/*  49 */       throw (UnsupportedOperationException)Util.pauseInIde(new UnsupportedOperationException("No bounds for empty shape."));
/*     */     }
/*  51 */     return new AABB(min(Direction.Axis.X), min(Direction.Axis.Y), min(Direction.Axis.Z), max(Direction.Axis.X), max(Direction.Axis.Y), max(Direction.Axis.Z));
/*     */   }
/*     */   
/*     */   public VoxelShape singleEncompassing() {
/*  55 */     if (isEmpty()) {
/*  56 */       return Shapes.empty();
/*     */     }
/*  58 */     return Shapes.box(min(Direction.Axis.X), min(Direction.Axis.Y), min(Direction.Axis.Z), max(Direction.Axis.X), max(Direction.Axis.Y), max(Direction.Axis.Z));
/*     */   }
/*     */   
/*     */   protected double get(Direction.Axis $$0, int $$1) {
/*  62 */     return getCoords($$0).getDouble($$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  68 */     return this.shape.isEmpty();
/*     */   }
/*     */   
/*     */   public VoxelShape move(double $$0, double $$1, double $$2) {
/*  72 */     if (isEmpty()) {
/*  73 */       return Shapes.empty();
/*     */     }
/*  75 */     return new ArrayVoxelShape(this.shape, (DoubleList)new OffsetDoubleList(
/*     */           
/*  77 */           getCoords(Direction.Axis.X), $$0), (DoubleList)new OffsetDoubleList(
/*  78 */           getCoords(Direction.Axis.Y), $$1), (DoubleList)new OffsetDoubleList(
/*  79 */           getCoords(Direction.Axis.Z), $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape optimize() {
/*  84 */     VoxelShape[] $$0 = { Shapes.empty() };
/*  85 */     forAllBoxes(($$1, $$2, $$3, $$4, $$5, $$6) -> $$0[0] = Shapes.joinUnoptimized($$0[0], Shapes.box($$1, $$2, $$3, $$4, $$5, $$6), BooleanOp.OR));
/*     */ 
/*     */     
/*  88 */     return $$0[0];
/*     */   }
/*     */   
/*     */   public void forAllEdges(Shapes.DoubleLineConsumer $$0) {
/*  92 */     this.shape.forAllEdges(($$1, $$2, $$3, $$4, $$5, $$6) -> $$0.consume(get(Direction.Axis.X, $$1), get(Direction.Axis.Y, $$2), get(Direction.Axis.Z, $$3), get(Direction.Axis.X, $$4), get(Direction.Axis.Y, $$5), get(Direction.Axis.Z, $$6)), true);
/*     */   }
/*     */   
/*     */   public void forAllBoxes(Shapes.DoubleLineConsumer $$0) {
/*  96 */     DoubleList $$1 = getCoords(Direction.Axis.X);
/*  97 */     DoubleList $$2 = getCoords(Direction.Axis.Y);
/*  98 */     DoubleList $$3 = getCoords(Direction.Axis.Z);
/*     */     
/* 100 */     this.shape.forAllBoxes(($$4, $$5, $$6, $$7, $$8, $$9) -> $$0.consume($$1.getDouble($$4), $$2.getDouble($$5), $$3.getDouble($$6), $$1.getDouble($$7), $$2.getDouble($$8), $$3.getDouble($$9)), true);
/*     */   }
/*     */   
/*     */   public List<AABB> toAabbs() {
/* 104 */     List<AABB> $$0 = Lists.newArrayList();
/* 105 */     forAllBoxes(($$1, $$2, $$3, $$4, $$5, $$6) -> $$0.add(new AABB($$1, $$2, $$3, $$4, $$5, $$6)));
/* 106 */     return $$0;
/*     */   }
/*     */   
/*     */   public double min(Direction.Axis $$0, double $$1, double $$2) {
/* 110 */     Direction.Axis $$3 = AxisCycle.FORWARD.cycle($$0);
/* 111 */     Direction.Axis $$4 = AxisCycle.BACKWARD.cycle($$0);
/* 112 */     int $$5 = findIndex($$3, $$1);
/* 113 */     int $$6 = findIndex($$4, $$2);
/* 114 */     int $$7 = this.shape.firstFull($$0, $$5, $$6);
/* 115 */     if ($$7 >= this.shape.getSize($$0)) {
/* 116 */       return Double.POSITIVE_INFINITY;
/*     */     }
/* 118 */     return get($$0, $$7);
/*     */   }
/*     */ 
/*     */   
/*     */   public double max(Direction.Axis $$0, double $$1, double $$2) {
/* 123 */     Direction.Axis $$3 = AxisCycle.FORWARD.cycle($$0);
/* 124 */     Direction.Axis $$4 = AxisCycle.BACKWARD.cycle($$0);
/* 125 */     int $$5 = findIndex($$3, $$1);
/* 126 */     int $$6 = findIndex($$4, $$2);
/* 127 */     int $$7 = this.shape.lastFull($$0, $$5, $$6);
/* 128 */     if ($$7 <= 0) {
/* 129 */       return Double.NEGATIVE_INFINITY;
/*     */     }
/* 131 */     return get($$0, $$7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int findIndex(Direction.Axis $$0, double $$1) {
/* 140 */     return Mth.binarySearch(0, this.shape.getSize($$0) + 1, $$2 -> ($$0 < get($$1, $$2))) - 1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockHitResult clip(Vec3 $$0, Vec3 $$1, BlockPos $$2) {
/* 145 */     if (isEmpty()) {
/* 146 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 150 */     Vec3 $$3 = $$1.subtract($$0);
/* 151 */     if ($$3.lengthSqr() < 1.0E-7D) {
/* 152 */       return null;
/*     */     }
/*     */     
/* 155 */     Vec3 $$4 = $$0.add($$3.scale(0.001D));
/*     */ 
/*     */     
/* 158 */     if (this.shape.isFullWide(findIndex(Direction.Axis.X, $$4.x - $$2.getX()), findIndex(Direction.Axis.Y, $$4.y - $$2.getY()), findIndex(Direction.Axis.Z, $$4.z - $$2.getZ()))) {
/* 159 */       return new BlockHitResult($$4, Direction.getNearest($$3.x, $$3.y, $$3.z).getOpposite(), $$2, true);
/*     */     }
/*     */ 
/*     */     
/* 163 */     return AABB.clip(toAabbs(), $$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public Optional<Vec3> closestPointTo(Vec3 $$0) {
/* 167 */     if (isEmpty()) {
/* 168 */       return Optional.empty();
/*     */     }
/* 170 */     Vec3[] $$1 = new Vec3[1];
/* 171 */     forAllBoxes(($$2, $$3, $$4, $$5, $$6, $$7) -> {
/*     */           double $$8 = Mth.clamp($$0.x(), $$2, $$5);
/*     */           double $$9 = Mth.clamp($$0.y(), $$3, $$6);
/*     */           double $$10 = Mth.clamp($$0.z(), $$4, $$7);
/*     */           if ($$1[0] == null || $$0.distanceToSqr($$8, $$9, $$10) < $$0.distanceToSqr($$1[0])) {
/*     */             $$1[0] = new Vec3($$8, $$9, $$10);
/*     */           }
/*     */         });
/* 179 */     return Optional.of($$1[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VoxelShape getFaceShape(Direction $$0) {
/* 186 */     if (isEmpty() || this == Shapes.block()) {
/* 187 */       return this;
/*     */     }
/*     */     
/* 190 */     if (this.faces != null) {
/* 191 */       VoxelShape $$1 = this.faces[$$0.ordinal()];
/* 192 */       if ($$1 != null) {
/* 193 */         return $$1;
/*     */       }
/*     */     } else {
/* 196 */       this.faces = new VoxelShape[6];
/*     */     } 
/*     */     
/* 199 */     VoxelShape $$2 = calculateFace($$0);
/* 200 */     this.faces[$$0.ordinal()] = $$2;
/* 201 */     return $$2;
/*     */   }
/*     */   
/*     */   private VoxelShape calculateFace(Direction $$0) {
/* 205 */     Direction.Axis $$1 = $$0.getAxis();
/* 206 */     DoubleList $$2 = getCoords($$1);
/* 207 */     if ($$2.size() == 2 && DoubleMath.fuzzyEquals($$2.getDouble(0), 0.0D, 1.0E-7D) && DoubleMath.fuzzyEquals($$2.getDouble(1), 1.0D, 1.0E-7D)) {
/* 208 */       return this;
/*     */     }
/*     */     
/* 211 */     Direction.AxisDirection $$3 = $$0.getAxisDirection();
/* 212 */     int $$4 = findIndex($$1, ($$3 == Direction.AxisDirection.POSITIVE) ? 0.9999999D : 1.0E-7D);
/* 213 */     return new SliceShape(this, $$1, $$4);
/*     */   }
/*     */   
/*     */   public double collide(Direction.Axis $$0, AABB $$1, double $$2) {
/* 217 */     return collideX(AxisCycle.between($$0, Direction.Axis.X), $$1, $$2);
/*     */   }
/*     */   
/*     */   protected double collideX(AxisCycle $$0, AABB $$1, double $$2) {
/* 221 */     if (isEmpty()) {
/* 222 */       return $$2;
/*     */     }
/* 224 */     if (Math.abs($$2) < 1.0E-7D) {
/* 225 */       return 0.0D;
/*     */     }
/*     */     
/* 228 */     AxisCycle $$3 = $$0.inverse();
/* 229 */     Direction.Axis $$4 = $$3.cycle(Direction.Axis.X);
/* 230 */     Direction.Axis $$5 = $$3.cycle(Direction.Axis.Y);
/* 231 */     Direction.Axis $$6 = $$3.cycle(Direction.Axis.Z);
/*     */     
/* 233 */     double $$7 = $$1.max($$4);
/* 234 */     double $$8 = $$1.min($$4);
/*     */     
/* 236 */     int $$9 = findIndex($$4, $$8 + 1.0E-7D);
/* 237 */     int $$10 = findIndex($$4, $$7 - 1.0E-7D);
/*     */     
/* 239 */     int $$11 = Math.max(0, findIndex($$5, $$1.min($$5) + 1.0E-7D));
/* 240 */     int $$12 = Math.min(this.shape.getSize($$5), findIndex($$5, $$1.max($$5) - 1.0E-7D) + 1);
/*     */     
/* 242 */     int $$13 = Math.max(0, findIndex($$6, $$1.min($$6) + 1.0E-7D));
/* 243 */     int $$14 = Math.min(this.shape.getSize($$6), findIndex($$6, $$1.max($$6) - 1.0E-7D) + 1);
/*     */     
/* 245 */     int $$15 = this.shape.getSize($$4);
/*     */     
/* 247 */     if ($$2 > 0.0D) {
/* 248 */       for (int $$16 = $$10 + 1; $$16 < $$15; $$16++) {
/* 249 */         for (int $$17 = $$11; $$17 < $$12; $$17++) {
/* 250 */           for (int $$18 = $$13; $$18 < $$14; $$18++) {
/* 251 */             if (this.shape.isFullWide($$3, $$16, $$17, $$18)) {
/* 252 */               double $$19 = get($$4, $$16) - $$7;
/* 253 */               if ($$19 >= -1.0E-7D) {
/* 254 */                 $$2 = Math.min($$2, $$19);
/*     */               }
/* 256 */               return $$2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 261 */     } else if ($$2 < 0.0D) {
/* 262 */       for (int $$20 = $$9 - 1; $$20 >= 0; $$20--) {
/* 263 */         for (int $$21 = $$11; $$21 < $$12; $$21++) {
/* 264 */           for (int $$22 = $$13; $$22 < $$14; $$22++) {
/* 265 */             if (this.shape.isFullWide($$3, $$20, $$21, $$22)) {
/* 266 */               double $$23 = get($$4, $$20 + 1) - $$8;
/* 267 */               if ($$23 <= 1.0E-7D) {
/* 268 */                 $$2 = Math.max($$2, $$23);
/*     */               }
/* 270 */               return $$2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 276 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 281 */     return isEmpty() ? "EMPTY" : ("VoxelShape[" + bounds() + "]");
/*     */   }
/*     */   
/*     */   protected abstract DoubleList getCoords(Direction.Axis paramAxis);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\VoxelShape.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */