/*     */ package net.minecraft.world.level.levelgen.structure;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class BoundingBox {
/*  23 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static final Codec<BoundingBox> CODEC;
/*     */   
/*     */   static {
/*  28 */     CODEC = Codec.INT_STREAM.comapFlatMap($$0 -> Util.fixedSize($$0, 6).map(()), $$0 -> IntStream.of(new int[] { $$0.minX, $$0.minY, $$0.minZ, $$0.maxX, $$0.maxY, $$0.maxZ })).stable();
/*     */   }
/*     */   private int minX;
/*     */   private int minY;
/*     */   private int minZ;
/*     */   private int maxX;
/*     */   private int maxY;
/*     */   private int maxZ;
/*     */   
/*     */   public BoundingBox(BlockPos $$0) {
/*  38 */     this($$0.getX(), $$0.getY(), $$0.getZ(), $$0.getX(), $$0.getY(), $$0.getZ());
/*     */   }
/*     */   
/*     */   public BoundingBox(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/*  42 */     this.minX = $$0;
/*  43 */     this.minY = $$1;
/*  44 */     this.minZ = $$2;
/*  45 */     this.maxX = $$3;
/*  46 */     this.maxY = $$4;
/*  47 */     this.maxZ = $$5;
/*     */     
/*  49 */     if ($$3 < $$0 || $$4 < $$1 || $$5 < $$2) {
/*  50 */       String $$6 = "Invalid bounding box data, inverted bounds for: " + this;
/*  51 */       if (SharedConstants.IS_RUNNING_IN_IDE) {
/*  52 */         throw new IllegalStateException($$6);
/*     */       }
/*  54 */       LOGGER.error($$6);
/*     */ 
/*     */       
/*  57 */       this.minX = Math.min($$0, $$3);
/*  58 */       this.minY = Math.min($$1, $$4);
/*  59 */       this.minZ = Math.min($$2, $$5);
/*  60 */       this.maxX = Math.max($$0, $$3);
/*  61 */       this.maxY = Math.max($$1, $$4);
/*  62 */       this.maxZ = Math.max($$2, $$5);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static BoundingBox fromCorners(Vec3i $$0, Vec3i $$1) {
/*  68 */     return new BoundingBox(Math.min($$0.getX(), $$1.getX()), Math.min($$0.getY(), $$1.getY()), Math.min($$0.getZ(), $$1.getZ()), Math.max($$0.getX(), $$1.getX()), Math.max($$0.getY(), $$1.getY()), Math.max($$0.getZ(), $$1.getZ()));
/*     */   }
/*     */   
/*     */   public static BoundingBox infinite() {
/*  72 */     return new BoundingBox(-2147483648, -2147483648, -2147483648, 2147483647, 2147483647, 2147483647);
/*     */   }
/*     */   
/*     */   public static BoundingBox orientBox(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, Direction $$9) {
/*  76 */     switch ($$9) {
/*     */ 
/*     */       
/*     */       default:
/*  80 */         return new BoundingBox($$0 + $$3, $$1 + $$4, $$2 + $$5, $$0 + $$6 - 1 + $$3, $$1 + $$7 - 1 + $$4, $$2 + $$8 - 1 + $$5);
/*     */       
/*     */       case NORTH:
/*  83 */         return new BoundingBox($$0 + $$3, $$1 + $$4, $$2 - $$8 + 1 + $$5, $$0 + $$6 - 1 + $$3, $$1 + $$7 - 1 + $$4, $$2 + $$5);
/*     */       
/*     */       case WEST:
/*  86 */         return new BoundingBox($$0 - $$8 + 1 + $$5, $$1 + $$4, $$2 + $$3, $$0 + $$5, $$1 + $$7 - 1 + $$4, $$2 + $$6 - 1 + $$3);
/*     */       case EAST:
/*     */         break;
/*  89 */     }  return new BoundingBox($$0 + $$5, $$1 + $$4, $$2 + $$3, $$0 + $$8 - 1 + $$5, $$1 + $$7 - 1 + $$4, $$2 + $$6 - 1 + $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<ChunkPos> intersectingChunks() {
/*  94 */     int $$0 = SectionPos.blockToSectionCoord(minX());
/*  95 */     int $$1 = SectionPos.blockToSectionCoord(minZ());
/*  96 */     int $$2 = SectionPos.blockToSectionCoord(maxX());
/*  97 */     int $$3 = SectionPos.blockToSectionCoord(maxZ());
/*  98 */     return ChunkPos.rangeClosed(new ChunkPos($$0, $$1), new ChunkPos($$2, $$3));
/*     */   }
/*     */   
/*     */   public boolean intersects(BoundingBox $$0) {
/* 102 */     return (this.maxX >= $$0.minX && this.minX <= $$0.maxX && this.maxZ >= $$0.minZ && this.minZ <= $$0.maxZ && this.maxY >= $$0.minY && this.minY <= $$0.maxY);
/*     */   }
/*     */   
/*     */   public boolean intersects(int $$0, int $$1, int $$2, int $$3) {
/* 106 */     return (this.maxX >= $$0 && this.minX <= $$2 && this.maxZ >= $$1 && this.minZ <= $$3);
/*     */   }
/*     */   
/*     */   public static Optional<BoundingBox> encapsulatingPositions(Iterable<BlockPos> $$0) {
/* 110 */     Iterator<BlockPos> $$1 = $$0.iterator();
/* 111 */     if (!$$1.hasNext()) {
/* 112 */       return Optional.empty();
/*     */     }
/*     */     
/* 115 */     BoundingBox $$2 = new BoundingBox($$1.next());
/* 116 */     Objects.requireNonNull($$2); $$1.forEachRemaining($$2::encapsulate);
/* 117 */     return Optional.of($$2);
/*     */   }
/*     */   
/*     */   public static Optional<BoundingBox> encapsulatingBoxes(Iterable<BoundingBox> $$0) {
/* 121 */     Iterator<BoundingBox> $$1 = $$0.iterator();
/* 122 */     if (!$$1.hasNext()) {
/* 123 */       return Optional.empty();
/*     */     }
/*     */     
/* 126 */     BoundingBox $$2 = $$1.next();
/* 127 */     BoundingBox $$3 = new BoundingBox($$2.minX, $$2.minY, $$2.minZ, $$2.maxX, $$2.maxY, $$2.maxZ);
/* 128 */     Objects.requireNonNull($$3); $$1.forEachRemaining($$3::encapsulate);
/* 129 */     return Optional.of($$3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BoundingBox encapsulate(BoundingBox $$0) {
/* 137 */     this.minX = Math.min(this.minX, $$0.minX);
/* 138 */     this.minY = Math.min(this.minY, $$0.minY);
/* 139 */     this.minZ = Math.min(this.minZ, $$0.minZ);
/* 140 */     this.maxX = Math.max(this.maxX, $$0.maxX);
/* 141 */     this.maxY = Math.max(this.maxY, $$0.maxY);
/* 142 */     this.maxZ = Math.max(this.maxZ, $$0.maxZ);
/* 143 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BoundingBox encapsulate(BlockPos $$0) {
/* 151 */     this.minX = Math.min(this.minX, $$0.getX());
/* 152 */     this.minY = Math.min(this.minY, $$0.getY());
/* 153 */     this.minZ = Math.min(this.minZ, $$0.getZ());
/* 154 */     this.maxX = Math.max(this.maxX, $$0.getX());
/* 155 */     this.maxY = Math.max(this.maxY, $$0.getY());
/* 156 */     this.maxZ = Math.max(this.maxZ, $$0.getZ());
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BoundingBox move(int $$0, int $$1, int $$2) {
/* 165 */     this.minX += $$0;
/* 166 */     this.minY += $$1;
/* 167 */     this.minZ += $$2;
/* 168 */     this.maxX += $$0;
/* 169 */     this.maxY += $$1;
/* 170 */     this.maxZ += $$2;
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BoundingBox move(Vec3i $$0) {
/* 179 */     return move($$0.getX(), $$0.getY(), $$0.getZ());
/*     */   }
/*     */   
/*     */   public BoundingBox moved(int $$0, int $$1, int $$2) {
/* 183 */     return new BoundingBox(this.minX + $$0, this.minY + $$1, this.minZ + $$2, this.maxX + $$0, this.maxY + $$1, this.maxZ + $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundingBox inflatedBy(int $$0) {
/* 194 */     return new BoundingBox(
/* 195 */         minX() - $$0, 
/* 196 */         minY() - $$0, 
/* 197 */         minZ() - $$0, 
/* 198 */         maxX() + $$0, 
/* 199 */         maxY() + $$0, 
/* 200 */         maxZ() + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInside(Vec3i $$0) {
/* 205 */     return isInside($$0.getX(), $$0.getY(), $$0.getZ());
/*     */   }
/*     */   
/*     */   public boolean isInside(int $$0, int $$1, int $$2) {
/* 209 */     return ($$0 >= this.minX && $$0 <= this.maxX && $$2 >= this.minZ && $$2 <= this.maxZ && $$1 >= this.minY && $$1 <= this.maxY);
/*     */   }
/*     */   
/*     */   public Vec3i getLength() {
/* 213 */     return new Vec3i(this.maxX - this.minX, this.maxY - this.minY, this.maxZ - this.minZ);
/*     */   }
/*     */   
/*     */   public int getXSpan() {
/* 217 */     return this.maxX - this.minX + 1;
/*     */   }
/*     */   
/*     */   public int getYSpan() {
/* 221 */     return this.maxY - this.minY + 1;
/*     */   }
/*     */   
/*     */   public int getZSpan() {
/* 225 */     return this.maxZ - this.minZ + 1;
/*     */   }
/*     */   
/*     */   public BlockPos getCenter() {
/* 229 */     return new BlockPos(this.minX + (this.maxX - this.minX + 1) / 2, this.minY + (this.maxY - this.minY + 1) / 2, this.minZ + (this.maxZ - this.minZ + 1) / 2);
/*     */   }
/*     */   
/*     */   public void forAllCorners(Consumer<BlockPos> $$0) {
/* 233 */     BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
/* 234 */     $$0.accept($$1.set(this.maxX, this.maxY, this.maxZ));
/* 235 */     $$0.accept($$1.set(this.minX, this.maxY, this.maxZ));
/* 236 */     $$0.accept($$1.set(this.maxX, this.minY, this.maxZ));
/* 237 */     $$0.accept($$1.set(this.minX, this.minY, this.maxZ));
/* 238 */     $$0.accept($$1.set(this.maxX, this.maxY, this.minZ));
/* 239 */     $$0.accept($$1.set(this.minX, this.maxY, this.minZ));
/* 240 */     $$0.accept($$1.set(this.maxX, this.minY, this.minZ));
/* 241 */     $$0.accept($$1.set(this.minX, this.minY, this.minZ));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 246 */     return MoreObjects.toStringHelper(this)
/* 247 */       .add("minX", this.minX)
/* 248 */       .add("minY", this.minY)
/* 249 */       .add("minZ", this.minZ)
/* 250 */       .add("maxX", this.maxX)
/* 251 */       .add("maxY", this.maxY)
/* 252 */       .add("maxZ", this.maxZ)
/* 253 */       .toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 258 */     if (this == $$0) {
/* 259 */       return true;
/*     */     }
/* 261 */     if ($$0 instanceof BoundingBox) { BoundingBox $$1 = (BoundingBox)$$0;
/* 262 */       return (this.minX == $$1.minX && this.minY == $$1.minY && this.minZ == $$1.minZ && this.maxX == $$1.maxX && this.maxY == $$1.maxY && this.maxZ == $$1.maxZ); }
/*     */     
/* 264 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 269 */     return Objects.hash(new Object[] { Integer.valueOf(this.minX), Integer.valueOf(this.minY), Integer.valueOf(this.minZ), Integer.valueOf(this.maxX), Integer.valueOf(this.maxY), Integer.valueOf(this.maxZ) });
/*     */   }
/*     */   
/*     */   public int minX() {
/* 273 */     return this.minX;
/*     */   }
/*     */   
/*     */   public int minY() {
/* 277 */     return this.minY;
/*     */   }
/*     */   
/*     */   public int minZ() {
/* 281 */     return this.minZ;
/*     */   }
/*     */   
/*     */   public int maxX() {
/* 285 */     return this.maxX;
/*     */   }
/*     */   
/*     */   public int maxY() {
/* 289 */     return this.maxY;
/*     */   }
/*     */   
/*     */   public int maxZ() {
/* 293 */     return this.maxZ;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\BoundingBox.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */