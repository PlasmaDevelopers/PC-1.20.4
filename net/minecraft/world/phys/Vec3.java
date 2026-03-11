/*     */ package net.minecraft.world.phys;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Vec3 implements Position {
/*     */   static {
/*  16 */     CODEC = Codec.DOUBLE.listOf().comapFlatMap($$0 -> Util.fixedSize($$0, 3).map(()), $$0 -> List.of(Double.valueOf($$0.x()), Double.valueOf($$0.y()), Double.valueOf($$0.z())));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<Vec3> CODEC;
/*  21 */   public static final Vec3 ZERO = new Vec3(0.0D, 0.0D, 0.0D);
/*     */   public final double x;
/*     */   public final double y;
/*     */   public final double z;
/*     */   
/*     */   public static Vec3 fromRGB24(int $$0) {
/*  27 */     double $$1 = ($$0 >> 16 & 0xFF) / 255.0D;
/*  28 */     double $$2 = ($$0 >> 8 & 0xFF) / 255.0D;
/*  29 */     double $$3 = ($$0 & 0xFF) / 255.0D;
/*  30 */     return new Vec3($$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static Vec3 atLowerCornerOf(Vec3i $$0) {
/*  34 */     return new Vec3($$0.getX(), $$0.getY(), $$0.getZ());
/*     */   }
/*     */   
/*     */   public static Vec3 atLowerCornerWithOffset(Vec3i $$0, double $$1, double $$2, double $$3) {
/*  38 */     return new Vec3($$0.getX() + $$1, $$0.getY() + $$2, $$0.getZ() + $$3);
/*     */   }
/*     */   
/*     */   public static Vec3 atCenterOf(Vec3i $$0) {
/*  42 */     return atLowerCornerWithOffset($$0, 0.5D, 0.5D, 0.5D);
/*     */   }
/*     */   
/*     */   public static Vec3 atBottomCenterOf(Vec3i $$0) {
/*  46 */     return atLowerCornerWithOffset($$0, 0.5D, 0.0D, 0.5D);
/*     */   }
/*     */   
/*     */   public static Vec3 upFromBottomCenterOf(Vec3i $$0, double $$1) {
/*  50 */     return atLowerCornerWithOffset($$0, 0.5D, $$1, 0.5D);
/*     */   }
/*     */   
/*     */   public Vec3(double $$0, double $$1, double $$2) {
/*  54 */     this.x = $$0;
/*  55 */     this.y = $$1;
/*  56 */     this.z = $$2;
/*     */   }
/*     */   
/*     */   public Vec3(Vector3f $$0) {
/*  60 */     this($$0.x(), $$0.y(), $$0.z());
/*     */   }
/*     */   
/*     */   public Vec3 vectorTo(Vec3 $$0) {
/*  64 */     return new Vec3($$0.x - this.x, $$0.y - this.y, $$0.z - this.z);
/*     */   }
/*     */   
/*     */   public Vec3 normalize() {
/*  68 */     double $$0 = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*  69 */     if ($$0 < 1.0E-4D) {
/*  70 */       return ZERO;
/*     */     }
/*  72 */     return new Vec3(this.x / $$0, this.y / $$0, this.z / $$0);
/*     */   }
/*     */   
/*     */   public double dot(Vec3 $$0) {
/*  76 */     return this.x * $$0.x + this.y * $$0.y + this.z * $$0.z;
/*     */   }
/*     */   
/*     */   public Vec3 cross(Vec3 $$0) {
/*  80 */     return new Vec3(this.y * $$0.z - this.z * $$0.y, this.z * $$0.x - this.x * $$0.z, this.x * $$0.y - this.y * $$0.x);
/*     */   }
/*     */   
/*     */   public Vec3 subtract(Vec3 $$0) {
/*  84 */     return subtract($$0.x, $$0.y, $$0.z);
/*     */   }
/*     */   
/*     */   public Vec3 subtract(double $$0, double $$1, double $$2) {
/*  88 */     return add(-$$0, -$$1, -$$2);
/*     */   }
/*     */   
/*     */   public Vec3 add(Vec3 $$0) {
/*  92 */     return add($$0.x, $$0.y, $$0.z);
/*     */   }
/*     */   
/*     */   public Vec3 add(double $$0, double $$1, double $$2) {
/*  96 */     return new Vec3(this.x + $$0, this.y + $$1, this.z + $$2);
/*     */   }
/*     */   
/*     */   public boolean closerThan(Position $$0, double $$1) {
/* 100 */     return (distanceToSqr($$0.x(), $$0.y(), $$0.z()) < $$1 * $$1);
/*     */   }
/*     */   
/*     */   public double distanceTo(Vec3 $$0) {
/* 104 */     double $$1 = $$0.x - this.x;
/* 105 */     double $$2 = $$0.y - this.y;
/* 106 */     double $$3 = $$0.z - this.z;
/* 107 */     return Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
/*     */   }
/*     */   
/*     */   public double distanceToSqr(Vec3 $$0) {
/* 111 */     double $$1 = $$0.x - this.x;
/* 112 */     double $$2 = $$0.y - this.y;
/* 113 */     double $$3 = $$0.z - this.z;
/* 114 */     return $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
/*     */   }
/*     */   
/*     */   public double distanceToSqr(double $$0, double $$1, double $$2) {
/* 118 */     double $$3 = $$0 - this.x;
/* 119 */     double $$4 = $$1 - this.y;
/* 120 */     double $$5 = $$2 - this.z;
/* 121 */     return $$3 * $$3 + $$4 * $$4 + $$5 * $$5;
/*     */   }
/*     */   
/*     */   public boolean closerThan(Vec3 $$0, double $$1, double $$2) {
/* 125 */     double $$3 = $$0.x() - this.x;
/* 126 */     double $$4 = $$0.y() - this.y;
/* 127 */     double $$5 = $$0.z() - this.z;
/* 128 */     return (Mth.lengthSquared($$3, $$5) < Mth.square($$1) && Math.abs($$4) < $$2);
/*     */   }
/*     */   
/*     */   public Vec3 scale(double $$0) {
/* 132 */     return multiply($$0, $$0, $$0);
/*     */   }
/*     */   
/*     */   public Vec3 reverse() {
/* 136 */     return scale(-1.0D);
/*     */   }
/*     */   
/*     */   public Vec3 multiply(Vec3 $$0) {
/* 140 */     return multiply($$0.x, $$0.y, $$0.z);
/*     */   }
/*     */   
/*     */   public Vec3 multiply(double $$0, double $$1, double $$2) {
/* 144 */     return new Vec3(this.x * $$0, this.y * $$1, this.z * $$2);
/*     */   }
/*     */   
/*     */   public Vec3 offsetRandom(RandomSource $$0, float $$1) {
/* 148 */     return add((($$0.nextFloat() - 0.5F) * $$1), (($$0.nextFloat() - 0.5F) * $$1), (($$0.nextFloat() - 0.5F) * $$1));
/*     */   }
/*     */   
/*     */   public double length() {
/* 152 */     return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*     */   }
/*     */   
/*     */   public double lengthSqr() {
/* 156 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */   
/*     */   public double horizontalDistance() {
/* 160 */     return Math.sqrt(this.x * this.x + this.z * this.z);
/*     */   }
/*     */   
/*     */   public double horizontalDistanceSqr() {
/* 164 */     return this.x * this.x + this.z * this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 169 */     if (this == $$0) {
/* 170 */       return true;
/*     */     }
/* 172 */     if (!($$0 instanceof Vec3)) {
/* 173 */       return false;
/*     */     }
/*     */     
/* 176 */     Vec3 $$1 = (Vec3)$$0;
/*     */     
/* 178 */     if (Double.compare($$1.x, this.x) != 0) {
/* 179 */       return false;
/*     */     }
/* 181 */     if (Double.compare($$1.y, this.y) != 0) {
/* 182 */       return false;
/*     */     }
/* 184 */     return (Double.compare($$1.z, this.z) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 191 */     long $$0 = Double.doubleToLongBits(this.x);
/* 192 */     int $$1 = (int)($$0 ^ $$0 >>> 32L);
/* 193 */     $$0 = Double.doubleToLongBits(this.y);
/* 194 */     $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32L);
/* 195 */     $$0 = Double.doubleToLongBits(this.z);
/* 196 */     $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32L);
/* 197 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 202 */     return "(" + this.x + ", " + this.y + ", " + this.z + ")";
/*     */   }
/*     */   
/*     */   public Vec3 lerp(Vec3 $$0, double $$1) {
/* 206 */     return new Vec3(Mth.lerp($$1, this.x, $$0.x), Mth.lerp($$1, this.y, $$0.y), Mth.lerp($$1, this.z, $$0.z));
/*     */   }
/*     */   
/*     */   public Vec3 xRot(float $$0) {
/* 210 */     float $$1 = Mth.cos($$0);
/* 211 */     float $$2 = Mth.sin($$0);
/*     */     
/* 213 */     double $$3 = this.x;
/* 214 */     double $$4 = this.y * $$1 + this.z * $$2;
/* 215 */     double $$5 = this.z * $$1 - this.y * $$2;
/*     */     
/* 217 */     return new Vec3($$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public Vec3 yRot(float $$0) {
/* 221 */     float $$1 = Mth.cos($$0);
/* 222 */     float $$2 = Mth.sin($$0);
/*     */     
/* 224 */     double $$3 = this.x * $$1 + this.z * $$2;
/* 225 */     double $$4 = this.y;
/* 226 */     double $$5 = this.z * $$1 - this.x * $$2;
/*     */     
/* 228 */     return new Vec3($$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public Vec3 zRot(float $$0) {
/* 232 */     float $$1 = Mth.cos($$0);
/* 233 */     float $$2 = Mth.sin($$0);
/*     */     
/* 235 */     double $$3 = this.x * $$1 + this.y * $$2;
/* 236 */     double $$4 = this.y * $$1 - this.x * $$2;
/* 237 */     double $$5 = this.z;
/*     */     
/* 239 */     return new Vec3($$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public static Vec3 directionFromRotation(Vec2 $$0) {
/* 243 */     return directionFromRotation($$0.x, $$0.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3 directionFromRotation(float $$0, float $$1) {
/* 248 */     float $$2 = Mth.cos(-$$1 * 0.017453292F - 3.1415927F);
/* 249 */     float $$3 = Mth.sin(-$$1 * 0.017453292F - 3.1415927F);
/* 250 */     float $$4 = -Mth.cos(-$$0 * 0.017453292F);
/* 251 */     float $$5 = Mth.sin(-$$0 * 0.017453292F);
/*     */     
/* 253 */     return new Vec3(($$3 * $$4), $$5, ($$2 * $$4));
/*     */   }
/*     */   
/*     */   public Vec3 align(EnumSet<Direction.Axis> $$0) {
/* 257 */     double $$1 = $$0.contains(Direction.Axis.X) ? Mth.floor(this.x) : this.x;
/* 258 */     double $$2 = $$0.contains(Direction.Axis.Y) ? Mth.floor(this.y) : this.y;
/* 259 */     double $$3 = $$0.contains(Direction.Axis.Z) ? Mth.floor(this.z) : this.z;
/* 260 */     return new Vec3($$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public double get(Direction.Axis $$0) {
/* 264 */     return $$0.choose(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public Vec3 with(Direction.Axis $$0, double $$1) {
/* 268 */     double $$2 = ($$0 == Direction.Axis.X) ? $$1 : this.x;
/* 269 */     double $$3 = ($$0 == Direction.Axis.Y) ? $$1 : this.y;
/* 270 */     double $$4 = ($$0 == Direction.Axis.Z) ? $$1 : this.z;
/* 271 */     return new Vec3($$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public Vec3 relative(Direction $$0, double $$1) {
/* 275 */     Vec3i $$2 = $$0.getNormal();
/* 276 */     return new Vec3(this.x + $$1 * $$2
/* 277 */         .getX(), this.y + $$1 * $$2
/* 278 */         .getY(), this.z + $$1 * $$2
/* 279 */         .getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final double x() {
/* 285 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public final double y() {
/* 290 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public final double z() {
/* 295 */     return this.z;
/*     */   }
/*     */   
/*     */   public Vector3f toVector3f() {
/* 299 */     return new Vector3f((float)this.x, (float)this.y, (float)this.z);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\Vec3.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */