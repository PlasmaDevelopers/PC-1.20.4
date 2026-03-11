/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import java.util.stream.IntStream;
/*     */ import javax.annotation.concurrent.Immutable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ @Immutable
/*     */ public class Vec3i implements Comparable<Vec3i> {
/*     */   static {
/*  15 */     CODEC = Codec.INT_STREAM.comapFlatMap($$0 -> Util.fixedSize($$0, 3).map(()), $$0 -> IntStream.of(new int[] { $$0.getX(), $$0.getY(), $$0.getZ() }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final Codec<Vec3i> CODEC;
/*     */ 
/*     */   
/*     */   public static Codec<Vec3i> offsetCodec(int $$0) {
/*  24 */     return ExtraCodecs.validate(CODEC, $$1 -> 
/*  25 */         (Math.abs($$1.getX()) < $$0 && Math.abs($$1.getY()) < $$0 && Math.abs($$1.getZ()) < $$0) ? DataResult.success($$1) : DataResult.error(()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   public static final Vec3i ZERO = new Vec3i(0, 0, 0);
/*     */   
/*     */   private int x;
/*     */   private int y;
/*     */   private int z;
/*     */   
/*     */   public Vec3i(int $$0, int $$1, int $$2) {
/*  40 */     this.x = $$0;
/*  41 */     this.y = $$1;
/*  42 */     this.z = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  47 */     if (this == $$0) {
/*  48 */       return true;
/*     */     }
/*  50 */     if (!($$0 instanceof Vec3i)) {
/*  51 */       return false;
/*     */     }
/*     */     
/*  54 */     Vec3i $$1 = (Vec3i)$$0;
/*     */     
/*  56 */     if (getX() != $$1.getX()) {
/*  57 */       return false;
/*     */     }
/*  59 */     if (getY() != $$1.getY()) {
/*  60 */       return false;
/*     */     }
/*  62 */     if (getZ() != $$1.getZ()) {
/*  63 */       return false;
/*     */     }
/*     */     
/*  66 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  71 */     return (getY() + getZ() * 31) * 31 + getX();
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Vec3i $$0) {
/*  76 */     if (getY() == $$0.getY()) {
/*  77 */       if (getZ() == $$0.getZ()) {
/*  78 */         return getX() - $$0.getX();
/*     */       }
/*  80 */       return getZ() - $$0.getZ();
/*     */     } 
/*  82 */     return getY() - $$0.getY();
/*     */   }
/*     */   
/*     */   public int getX() {
/*  86 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/*  90 */     return this.y;
/*     */   }
/*     */   
/*     */   public int getZ() {
/*  94 */     return this.z;
/*     */   }
/*     */   
/*     */   protected Vec3i setX(int $$0) {
/*  98 */     this.x = $$0;
/*  99 */     return this;
/*     */   }
/*     */   
/*     */   protected Vec3i setY(int $$0) {
/* 103 */     this.y = $$0;
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   protected Vec3i setZ(int $$0) {
/* 108 */     this.z = $$0;
/* 109 */     return this;
/*     */   }
/*     */   
/*     */   public Vec3i offset(int $$0, int $$1, int $$2) {
/* 113 */     if ($$0 == 0 && $$1 == 0 && $$2 == 0) {
/* 114 */       return this;
/*     */     }
/* 116 */     return new Vec3i(getX() + $$0, getY() + $$1, getZ() + $$2);
/*     */   }
/*     */   
/*     */   public Vec3i offset(Vec3i $$0) {
/* 120 */     return offset($$0.getX(), $$0.getY(), $$0.getZ());
/*     */   }
/*     */   
/*     */   public Vec3i subtract(Vec3i $$0) {
/* 124 */     return offset(-$$0.getX(), -$$0.getY(), -$$0.getZ());
/*     */   }
/*     */   
/*     */   public Vec3i multiply(int $$0) {
/* 128 */     if ($$0 == 1)
/* 129 */       return this; 
/* 130 */     if ($$0 == 0) {
/* 131 */       return ZERO;
/*     */     }
/* 133 */     return new Vec3i(getX() * $$0, getY() * $$0, getZ() * $$0);
/*     */   }
/*     */   
/*     */   public Vec3i above() {
/* 137 */     return above(1);
/*     */   }
/*     */   
/*     */   public Vec3i above(int $$0) {
/* 141 */     return relative(Direction.UP, $$0);
/*     */   }
/*     */   
/*     */   public Vec3i below() {
/* 145 */     return below(1);
/*     */   }
/*     */   
/*     */   public Vec3i below(int $$0) {
/* 149 */     return relative(Direction.DOWN, $$0);
/*     */   }
/*     */   
/*     */   public Vec3i north() {
/* 153 */     return north(1);
/*     */   }
/*     */   
/*     */   public Vec3i north(int $$0) {
/* 157 */     return relative(Direction.NORTH, $$0);
/*     */   }
/*     */   
/*     */   public Vec3i south() {
/* 161 */     return south(1);
/*     */   }
/*     */   
/*     */   public Vec3i south(int $$0) {
/* 165 */     return relative(Direction.SOUTH, $$0);
/*     */   }
/*     */   
/*     */   public Vec3i west() {
/* 169 */     return west(1);
/*     */   }
/*     */   
/*     */   public Vec3i west(int $$0) {
/* 173 */     return relative(Direction.WEST, $$0);
/*     */   }
/*     */   
/*     */   public Vec3i east() {
/* 177 */     return east(1);
/*     */   }
/*     */   
/*     */   public Vec3i east(int $$0) {
/* 181 */     return relative(Direction.EAST, $$0);
/*     */   }
/*     */   
/*     */   public Vec3i relative(Direction $$0) {
/* 185 */     return relative($$0, 1);
/*     */   }
/*     */   
/*     */   public Vec3i relative(Direction $$0, int $$1) {
/* 189 */     if ($$1 == 0) {
/* 190 */       return this;
/*     */     }
/* 192 */     return new Vec3i(getX() + $$0.getStepX() * $$1, getY() + $$0.getStepY() * $$1, getZ() + $$0.getStepZ() * $$1);
/*     */   }
/*     */   
/*     */   public Vec3i relative(Direction.Axis $$0, int $$1) {
/* 196 */     if ($$1 == 0) {
/* 197 */       return this;
/*     */     }
/* 199 */     int $$2 = ($$0 == Direction.Axis.X) ? $$1 : 0;
/* 200 */     int $$3 = ($$0 == Direction.Axis.Y) ? $$1 : 0;
/* 201 */     int $$4 = ($$0 == Direction.Axis.Z) ? $$1 : 0;
/* 202 */     return new Vec3i(getX() + $$2, getY() + $$3, getZ() + $$4);
/*     */   }
/*     */   
/*     */   public Vec3i cross(Vec3i $$0) {
/* 206 */     return new Vec3i(getY() * $$0.getZ() - getZ() * $$0.getY(), getZ() * $$0.getX() - getX() * $$0.getZ(), getX() * $$0.getY() - getY() * $$0.getX());
/*     */   }
/*     */   
/*     */   public boolean closerThan(Vec3i $$0, double $$1) {
/* 210 */     return (distSqr($$0) < Mth.square($$1));
/*     */   }
/*     */   
/*     */   public boolean closerToCenterThan(Position $$0, double $$1) {
/* 214 */     return (distToCenterSqr($$0) < Mth.square($$1));
/*     */   }
/*     */   
/*     */   public double distSqr(Vec3i $$0) {
/* 218 */     return distToLowCornerSqr($$0.getX(), $$0.getY(), $$0.getZ());
/*     */   }
/*     */   
/*     */   public double distToCenterSqr(Position $$0) {
/* 222 */     return distToCenterSqr($$0.x(), $$0.y(), $$0.z());
/*     */   }
/*     */   
/*     */   public double distToCenterSqr(double $$0, double $$1, double $$2) {
/* 226 */     double $$3 = getX() + 0.5D - $$0;
/* 227 */     double $$4 = getY() + 0.5D - $$1;
/* 228 */     double $$5 = getZ() + 0.5D - $$2;
/* 229 */     return $$3 * $$3 + $$4 * $$4 + $$5 * $$5;
/*     */   }
/*     */   
/*     */   public double distToLowCornerSqr(double $$0, double $$1, double $$2) {
/* 233 */     double $$3 = getX() - $$0;
/* 234 */     double $$4 = getY() - $$1;
/* 235 */     double $$5 = getZ() - $$2;
/* 236 */     return $$3 * $$3 + $$4 * $$4 + $$5 * $$5;
/*     */   }
/*     */   
/*     */   public int distManhattan(Vec3i $$0) {
/* 240 */     float $$1 = Math.abs($$0.getX() - getX());
/* 241 */     float $$2 = Math.abs($$0.getY() - getY());
/* 242 */     float $$3 = Math.abs($$0.getZ() - getZ());
/* 243 */     return (int)($$1 + $$2 + $$3);
/*     */   }
/*     */   
/*     */   public int get(Direction.Axis $$0) {
/* 247 */     return $$0.choose(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 252 */     return MoreObjects.toStringHelper(this)
/* 253 */       .add("x", getX())
/* 254 */       .add("y", getY())
/* 255 */       .add("z", getZ())
/* 256 */       .toString();
/*     */   }
/*     */   
/*     */   public String toShortString() {
/* 260 */     return "" + getX() + ", " + getX() + ", " + getY();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\Vec3i.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */