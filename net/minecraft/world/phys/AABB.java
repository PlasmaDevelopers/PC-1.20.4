/*     */ package net.minecraft.world.phys;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AABB
/*     */ {
/*     */   private static final double EPSILON = 1.0E-7D;
/*     */   public final double minX;
/*     */   public final double minY;
/*     */   public final double minZ;
/*     */   public final double maxX;
/*     */   public final double maxY;
/*     */   public final double maxZ;
/*     */   
/*     */   public AABB(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
/*  26 */     this.minX = Math.min($$0, $$3);
/*  27 */     this.minY = Math.min($$1, $$4);
/*  28 */     this.minZ = Math.min($$2, $$5);
/*  29 */     this.maxX = Math.max($$0, $$3);
/*  30 */     this.maxY = Math.max($$1, $$4);
/*  31 */     this.maxZ = Math.max($$2, $$5);
/*     */   }
/*     */   
/*     */   public AABB(BlockPos $$0) {
/*  35 */     this($$0.getX(), $$0.getY(), $$0.getZ(), ($$0.getX() + 1), ($$0.getY() + 1), ($$0.getZ() + 1));
/*     */   }
/*     */   
/*     */   public AABB(Vec3 $$0, Vec3 $$1) {
/*  39 */     this($$0.x, $$0.y, $$0.z, $$1.x, $$1.y, $$1.z);
/*     */   }
/*     */   
/*     */   public static AABB of(BoundingBox $$0) {
/*  43 */     return new AABB($$0.minX(), $$0.minY(), $$0.minZ(), ($$0.maxX() + 1), ($$0.maxY() + 1), ($$0.maxZ() + 1));
/*     */   }
/*     */   
/*     */   public static AABB unitCubeFromLowerCorner(Vec3 $$0) {
/*  47 */     return new AABB($$0.x, $$0.y, $$0.z, $$0.x + 1.0D, $$0.y + 1.0D, $$0.z + 1.0D);
/*     */   }
/*     */   
/*     */   public static AABB encapsulatingFullBlocks(BlockPos $$0, BlockPos $$1) {
/*  51 */     return new AABB(Math.min($$0.getX(), $$1.getX()), Math.min($$0.getY(), $$1.getY()), Math.min($$0.getZ(), $$1.getZ()), (Math.max($$0.getX(), $$1.getX()) + 1), (Math.max($$0.getY(), $$1.getY()) + 1), (Math.max($$0.getZ(), $$1.getZ()) + 1));
/*     */   }
/*     */   
/*     */   public AABB setMinX(double $$0) {
/*  55 */     return new AABB($$0, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
/*     */   }
/*     */   
/*     */   public AABB setMinY(double $$0) {
/*  59 */     return new AABB(this.minX, $$0, this.minZ, this.maxX, this.maxY, this.maxZ);
/*     */   }
/*     */   
/*     */   public AABB setMinZ(double $$0) {
/*  63 */     return new AABB(this.minX, this.minY, $$0, this.maxX, this.maxY, this.maxZ);
/*     */   }
/*     */   
/*     */   public AABB setMaxX(double $$0) {
/*  67 */     return new AABB(this.minX, this.minY, this.minZ, $$0, this.maxY, this.maxZ);
/*     */   }
/*     */   
/*     */   public AABB setMaxY(double $$0) {
/*  71 */     return new AABB(this.minX, this.minY, this.minZ, this.maxX, $$0, this.maxZ);
/*     */   }
/*     */   
/*     */   public AABB setMaxZ(double $$0) {
/*  75 */     return new AABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, $$0);
/*     */   }
/*     */   
/*     */   public double min(Direction.Axis $$0) {
/*  79 */     return $$0.choose(this.minX, this.minY, this.minZ);
/*     */   }
/*     */   
/*     */   public double max(Direction.Axis $$0) {
/*  83 */     return $$0.choose(this.maxX, this.maxY, this.maxZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  88 */     if (this == $$0) {
/*  89 */       return true;
/*     */     }
/*  91 */     if (!($$0 instanceof AABB)) {
/*  92 */       return false;
/*     */     }
/*     */     
/*  95 */     AABB $$1 = (AABB)$$0;
/*     */     
/*  97 */     if (Double.compare($$1.minX, this.minX) != 0) {
/*  98 */       return false;
/*     */     }
/* 100 */     if (Double.compare($$1.minY, this.minY) != 0) {
/* 101 */       return false;
/*     */     }
/* 103 */     if (Double.compare($$1.minZ, this.minZ) != 0) {
/* 104 */       return false;
/*     */     }
/* 106 */     if (Double.compare($$1.maxX, this.maxX) != 0) {
/* 107 */       return false;
/*     */     }
/* 109 */     if (Double.compare($$1.maxY, this.maxY) != 0) {
/* 110 */       return false;
/*     */     }
/* 112 */     return (Double.compare($$1.maxZ, this.maxZ) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 117 */     long $$0 = Double.doubleToLongBits(this.minX);
/* 118 */     int $$1 = (int)($$0 ^ $$0 >>> 32L);
/* 119 */     $$0 = Double.doubleToLongBits(this.minY);
/* 120 */     $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32L);
/* 121 */     $$0 = Double.doubleToLongBits(this.minZ);
/* 122 */     $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32L);
/* 123 */     $$0 = Double.doubleToLongBits(this.maxX);
/* 124 */     $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32L);
/* 125 */     $$0 = Double.doubleToLongBits(this.maxY);
/* 126 */     $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32L);
/* 127 */     $$0 = Double.doubleToLongBits(this.maxZ);
/* 128 */     $$1 = 31 * $$1 + (int)($$0 ^ $$0 >>> 32L);
/* 129 */     return $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AABB contract(double $$0, double $$1, double $$2) {
/* 139 */     double $$3 = this.minX;
/* 140 */     double $$4 = this.minY;
/* 141 */     double $$5 = this.minZ;
/* 142 */     double $$6 = this.maxX;
/* 143 */     double $$7 = this.maxY;
/* 144 */     double $$8 = this.maxZ;
/*     */     
/* 146 */     if ($$0 < 0.0D) {
/* 147 */       $$3 -= $$0;
/* 148 */     } else if ($$0 > 0.0D) {
/* 149 */       $$6 -= $$0;
/*     */     } 
/*     */     
/* 152 */     if ($$1 < 0.0D) {
/* 153 */       $$4 -= $$1;
/* 154 */     } else if ($$1 > 0.0D) {
/* 155 */       $$7 -= $$1;
/*     */     } 
/*     */     
/* 158 */     if ($$2 < 0.0D) {
/* 159 */       $$5 -= $$2;
/* 160 */     } else if ($$2 > 0.0D) {
/* 161 */       $$8 -= $$2;
/*     */     } 
/*     */     
/* 164 */     return new AABB($$3, $$4, $$5, $$6, $$7, $$8);
/*     */   }
/*     */   
/*     */   public AABB expandTowards(Vec3 $$0) {
/* 168 */     return expandTowards($$0.x, $$0.y, $$0.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AABB expandTowards(double $$0, double $$1, double $$2) {
/* 178 */     double $$3 = this.minX;
/* 179 */     double $$4 = this.minY;
/* 180 */     double $$5 = this.minZ;
/* 181 */     double $$6 = this.maxX;
/* 182 */     double $$7 = this.maxY;
/* 183 */     double $$8 = this.maxZ;
/*     */     
/* 185 */     if ($$0 < 0.0D) {
/* 186 */       $$3 += $$0;
/* 187 */     } else if ($$0 > 0.0D) {
/* 188 */       $$6 += $$0;
/*     */     } 
/*     */     
/* 191 */     if ($$1 < 0.0D) {
/* 192 */       $$4 += $$1;
/* 193 */     } else if ($$1 > 0.0D) {
/* 194 */       $$7 += $$1;
/*     */     } 
/*     */     
/* 197 */     if ($$2 < 0.0D) {
/* 198 */       $$5 += $$2;
/* 199 */     } else if ($$2 > 0.0D) {
/* 200 */       $$8 += $$2;
/*     */     } 
/*     */     
/* 203 */     return new AABB($$3, $$4, $$5, $$6, $$7, $$8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AABB inflate(double $$0, double $$1, double $$2) {
/* 213 */     double $$3 = this.minX - $$0;
/* 214 */     double $$4 = this.minY - $$1;
/* 215 */     double $$5 = this.minZ - $$2;
/* 216 */     double $$6 = this.maxX + $$0;
/* 217 */     double $$7 = this.maxY + $$1;
/* 218 */     double $$8 = this.maxZ + $$2;
/*     */     
/* 220 */     return new AABB($$3, $$4, $$5, $$6, $$7, $$8);
/*     */   }
/*     */   
/*     */   public AABB inflate(double $$0) {
/* 224 */     return inflate($$0, $$0, $$0);
/*     */   }
/*     */   
/*     */   public AABB intersect(AABB $$0) {
/* 228 */     double $$1 = Math.max(this.minX, $$0.minX);
/* 229 */     double $$2 = Math.max(this.minY, $$0.minY);
/* 230 */     double $$3 = Math.max(this.minZ, $$0.minZ);
/* 231 */     double $$4 = Math.min(this.maxX, $$0.maxX);
/* 232 */     double $$5 = Math.min(this.maxY, $$0.maxY);
/* 233 */     double $$6 = Math.min(this.maxZ, $$0.maxZ);
/*     */     
/* 235 */     return new AABB($$1, $$2, $$3, $$4, $$5, $$6);
/*     */   }
/*     */   
/*     */   public AABB minmax(AABB $$0) {
/* 239 */     double $$1 = Math.min(this.minX, $$0.minX);
/* 240 */     double $$2 = Math.min(this.minY, $$0.minY);
/* 241 */     double $$3 = Math.min(this.minZ, $$0.minZ);
/* 242 */     double $$4 = Math.max(this.maxX, $$0.maxX);
/* 243 */     double $$5 = Math.max(this.maxY, $$0.maxY);
/* 244 */     double $$6 = Math.max(this.maxZ, $$0.maxZ);
/*     */     
/* 246 */     return new AABB($$1, $$2, $$3, $$4, $$5, $$6);
/*     */   }
/*     */   
/*     */   public AABB move(double $$0, double $$1, double $$2) {
/* 250 */     return new AABB(this.minX + $$0, this.minY + $$1, this.minZ + $$2, this.maxX + $$0, this.maxY + $$1, this.maxZ + $$2);
/*     */   }
/*     */   
/*     */   public AABB move(BlockPos $$0) {
/* 254 */     return new AABB(this.minX + $$0.getX(), this.minY + $$0.getY(), this.minZ + $$0.getZ(), this.maxX + $$0.getX(), this.maxY + $$0.getY(), this.maxZ + $$0.getZ());
/*     */   }
/*     */   
/*     */   public AABB move(Vec3 $$0) {
/* 258 */     return move($$0.x, $$0.y, $$0.z);
/*     */   }
/*     */   
/*     */   public boolean intersects(AABB $$0) {
/* 262 */     return intersects($$0.minX, $$0.minY, $$0.minZ, $$0.maxX, $$0.maxY, $$0.maxZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean intersects(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
/* 267 */     return (this.minX < $$3 && this.maxX > $$0 && this.minY < $$4 && this.maxY > $$1 && this.minZ < $$5 && this.maxZ > $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersects(Vec3 $$0, Vec3 $$1) {
/* 276 */     return intersects(Math.min($$0.x, $$1.x), Math.min($$0.y, $$1.y), Math.min($$0.z, $$1.z), Math.max($$0.x, $$1.x), Math.max($$0.y, $$1.y), Math.max($$0.z, $$1.z));
/*     */   }
/*     */   
/*     */   public boolean contains(Vec3 $$0) {
/* 280 */     return contains($$0.x, $$0.y, $$0.z);
/*     */   }
/*     */   
/*     */   public boolean contains(double $$0, double $$1, double $$2) {
/* 284 */     return ($$0 >= this.minX && $$0 < this.maxX && $$1 >= this.minY && $$1 < this.maxY && $$2 >= this.minZ && $$2 < this.maxZ);
/*     */   }
/*     */   
/*     */   public double getSize() {
/* 288 */     double $$0 = getXsize();
/* 289 */     double $$1 = getYsize();
/* 290 */     double $$2 = getZsize();
/* 291 */     return ($$0 + $$1 + $$2) / 3.0D;
/*     */   }
/*     */   
/*     */   public double getXsize() {
/* 295 */     return this.maxX - this.minX;
/*     */   }
/*     */   
/*     */   public double getYsize() {
/* 299 */     return this.maxY - this.minY;
/*     */   }
/*     */   
/*     */   public double getZsize() {
/* 303 */     return this.maxZ - this.minZ;
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
/*     */   public AABB deflate(double $$0, double $$1, double $$2) {
/* 315 */     return inflate(-$$0, -$$1, -$$2);
/*     */   }
/*     */   
/*     */   public AABB deflate(double $$0) {
/* 319 */     return inflate(-$$0);
/*     */   }
/*     */   
/*     */   public Optional<Vec3> clip(Vec3 $$0, Vec3 $$1) {
/* 323 */     double[] $$2 = { 1.0D };
/* 324 */     double $$3 = $$1.x - $$0.x;
/* 325 */     double $$4 = $$1.y - $$0.y;
/* 326 */     double $$5 = $$1.z - $$0.z;
/*     */     
/* 328 */     Direction $$6 = getDirection(this, $$0, $$2, null, $$3, $$4, $$5);
/* 329 */     if ($$6 == null) {
/* 330 */       return Optional.empty();
/*     */     }
/*     */     
/* 333 */     double $$7 = $$2[0];
/* 334 */     return Optional.of($$0.add($$7 * $$3, $$7 * $$4, $$7 * $$5));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BlockHitResult clip(Iterable<AABB> $$0, Vec3 $$1, Vec3 $$2, BlockPos $$3) {
/* 339 */     double[] $$4 = { 1.0D };
/* 340 */     Direction $$5 = null;
/*     */     
/* 342 */     double $$6 = $$2.x - $$1.x;
/* 343 */     double $$7 = $$2.y - $$1.y;
/* 344 */     double $$8 = $$2.z - $$1.z;
/*     */     
/* 346 */     for (AABB $$9 : $$0) {
/* 347 */       $$5 = getDirection($$9.move($$3), $$1, $$4, $$5, $$6, $$7, $$8);
/*     */     }
/*     */     
/* 350 */     if ($$5 == null) {
/* 351 */       return null;
/*     */     }
/*     */     
/* 354 */     double $$10 = $$4[0];
/* 355 */     return new BlockHitResult($$1.add($$10 * $$6, $$10 * $$7, $$10 * $$8), $$5, $$3, false);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Direction getDirection(AABB $$0, Vec3 $$1, double[] $$2, @Nullable Direction $$3, double $$4, double $$5, double $$6) {
/* 360 */     if ($$4 > 1.0E-7D) {
/* 361 */       $$3 = clipPoint($$2, $$3, $$4, $$5, $$6, $$0.minX, $$0.minY, $$0.maxY, $$0.minZ, $$0.maxZ, Direction.WEST, $$1.x, $$1.y, $$1.z);
/* 362 */     } else if ($$4 < -1.0E-7D) {
/* 363 */       $$3 = clipPoint($$2, $$3, $$4, $$5, $$6, $$0.maxX, $$0.minY, $$0.maxY, $$0.minZ, $$0.maxZ, Direction.EAST, $$1.x, $$1.y, $$1.z);
/*     */     } 
/*     */     
/* 366 */     if ($$5 > 1.0E-7D) {
/* 367 */       $$3 = clipPoint($$2, $$3, $$5, $$6, $$4, $$0.minY, $$0.minZ, $$0.maxZ, $$0.minX, $$0.maxX, Direction.DOWN, $$1.y, $$1.z, $$1.x);
/* 368 */     } else if ($$5 < -1.0E-7D) {
/* 369 */       $$3 = clipPoint($$2, $$3, $$5, $$6, $$4, $$0.maxY, $$0.minZ, $$0.maxZ, $$0.minX, $$0.maxX, Direction.UP, $$1.y, $$1.z, $$1.x);
/*     */     } 
/*     */     
/* 372 */     if ($$6 > 1.0E-7D) {
/* 373 */       $$3 = clipPoint($$2, $$3, $$6, $$4, $$5, $$0.minZ, $$0.minX, $$0.maxX, $$0.minY, $$0.maxY, Direction.NORTH, $$1.z, $$1.x, $$1.y);
/* 374 */     } else if ($$6 < -1.0E-7D) {
/* 375 */       $$3 = clipPoint($$2, $$3, $$6, $$4, $$5, $$0.maxZ, $$0.minX, $$0.maxX, $$0.minY, $$0.maxY, Direction.SOUTH, $$1.z, $$1.x, $$1.y);
/*     */     } 
/* 377 */     return $$3;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Direction clipPoint(double[] $$0, @Nullable Direction $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7, double $$8, double $$9, Direction $$10, double $$11, double $$12, double $$13) {
/* 382 */     double $$14 = ($$5 - $$11) / $$2;
/* 383 */     double $$15 = $$12 + $$14 * $$3;
/* 384 */     double $$16 = $$13 + $$14 * $$4;
/* 385 */     if (0.0D < $$14 && $$14 < $$0[0] && $$6 - 1.0E-7D < $$15 && $$15 < $$7 + 1.0E-7D && $$8 - 1.0E-7D < $$16 && $$16 < $$9 + 1.0E-7D) {
/*     */ 
/*     */ 
/*     */       
/* 389 */       $$0[0] = $$14;
/* 390 */       return $$10;
/*     */     } 
/* 392 */     return $$1;
/*     */   }
/*     */   
/*     */   public double distanceToSqr(Vec3 $$0) {
/* 396 */     double $$1 = Math.max(Math.max(this.minX - $$0.x, $$0.x - this.maxX), 0.0D);
/* 397 */     double $$2 = Math.max(Math.max(this.minY - $$0.y, $$0.y - this.maxY), 0.0D);
/* 398 */     double $$3 = Math.max(Math.max(this.minZ - $$0.z, $$0.z - this.maxZ), 0.0D);
/* 399 */     return Mth.lengthSquared($$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 404 */     return "AABB[" + this.minX + ", " + this.minY + ", " + this.minZ + "] -> [" + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
/*     */   }
/*     */   
/*     */   public boolean hasNaN() {
/* 408 */     return (Double.isNaN(this.minX) || Double.isNaN(this.minY) || Double.isNaN(this.minZ) || Double.isNaN(this.maxX) || Double.isNaN(this.maxY) || Double.isNaN(this.maxZ));
/*     */   }
/*     */   
/*     */   public Vec3 getCenter() {
/* 412 */     return new Vec3(Mth.lerp(0.5D, this.minX, this.maxX), Mth.lerp(0.5D, this.minY, this.maxY), Mth.lerp(0.5D, this.minZ, this.maxZ));
/*     */   }
/*     */   
/*     */   public static AABB ofSize(Vec3 $$0, double $$1, double $$2, double $$3) {
/* 416 */     return new AABB($$0.x - $$1 / 2.0D, $$0.y - $$2 / 2.0D, $$0.z - $$3 / 2.0D, $$0.x + $$1 / 2.0D, $$0.y + $$2 / 2.0D, $$0.z + $$3 / 2.0D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\AABB.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */