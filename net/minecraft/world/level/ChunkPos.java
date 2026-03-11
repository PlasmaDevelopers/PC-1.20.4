/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkPos
/*     */ {
/*     */   private static final int SAFETY_MARGIN = 1056;
/*  17 */   public static final long INVALID_CHUNK_POS = asLong(1875066, 1875066);
/*  18 */   public static final ChunkPos ZERO = new ChunkPos(0, 0);
/*     */   
/*     */   private static final long COORD_BITS = 32L;
/*     */   
/*     */   private static final long COORD_MASK = 4294967295L;
/*     */   
/*     */   private static final int REGION_BITS = 5;
/*     */   
/*     */   public static final int REGION_SIZE = 32;
/*     */   private static final int REGION_MASK = 31;
/*     */   
/*     */   public ChunkPos(int $$0, int $$1) {
/*  30 */     this.x = $$0;
/*  31 */     this.z = $$1;
/*     */   }
/*     */   public static final int REGION_MAX_INDEX = 31; public final int x; public final int z; private static final int HASH_A = 1664525; private static final int HASH_C = 1013904223; private static final int HASH_Z_XOR = -559038737;
/*     */   public ChunkPos(BlockPos $$0) {
/*  35 */     this.x = SectionPos.blockToSectionCoord($$0.getX());
/*  36 */     this.z = SectionPos.blockToSectionCoord($$0.getZ());
/*     */   }
/*     */   
/*     */   public ChunkPos(long $$0) {
/*  40 */     this.x = (int)$$0;
/*  41 */     this.z = (int)($$0 >> 32L);
/*     */   }
/*     */   
/*     */   public static ChunkPos minFromRegion(int $$0, int $$1) {
/*  45 */     return new ChunkPos($$0 << 5, $$1 << 5);
/*     */   }
/*     */   
/*     */   public static ChunkPos maxFromRegion(int $$0, int $$1) {
/*  49 */     return new ChunkPos(($$0 << 5) + 31, ($$1 << 5) + 31);
/*     */   }
/*     */   
/*     */   public long toLong() {
/*  53 */     return asLong(this.x, this.z);
/*     */   }
/*     */   
/*     */   public static long asLong(int $$0, int $$1) {
/*  57 */     return $$0 & 0xFFFFFFFFL | ($$1 & 0xFFFFFFFFL) << 32L;
/*     */   }
/*     */   
/*     */   public static long asLong(BlockPos $$0) {
/*  61 */     return asLong(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()));
/*     */   }
/*     */   
/*     */   public static int getX(long $$0) {
/*  65 */     return (int)($$0 & 0xFFFFFFFFL);
/*     */   }
/*     */   
/*     */   public static int getZ(long $$0) {
/*  69 */     return (int)($$0 >>> 32L & 0xFFFFFFFFL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  78 */     return hash(this.x, this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int hash(int $$0, int $$1) {
/*  85 */     int $$2 = 1664525 * $$0 + 1013904223;
/*  86 */     int $$3 = 1664525 * ($$1 ^ 0xDEADBEEF) + 1013904223;
/*  87 */     return $$2 ^ $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  92 */     if (this == $$0) {
/*  93 */       return true;
/*     */     }
/*     */     
/*  96 */     if ($$0 instanceof ChunkPos) { ChunkPos $$1 = (ChunkPos)$$0;
/*  97 */       return (this.x == $$1.x && this.z == $$1.z); }
/*     */ 
/*     */     
/* 100 */     return false;
/*     */   }
/*     */   
/*     */   public int getMiddleBlockX() {
/* 104 */     return getBlockX(8);
/*     */   }
/*     */   
/*     */   public int getMiddleBlockZ() {
/* 108 */     return getBlockZ(8);
/*     */   }
/*     */   
/*     */   public int getMinBlockX() {
/* 112 */     return SectionPos.sectionToBlockCoord(this.x);
/*     */   }
/*     */   
/*     */   public int getMinBlockZ() {
/* 116 */     return SectionPos.sectionToBlockCoord(this.z);
/*     */   }
/*     */   
/*     */   public int getMaxBlockX() {
/* 120 */     return getBlockX(15);
/*     */   }
/*     */   
/*     */   public int getMaxBlockZ() {
/* 124 */     return getBlockZ(15);
/*     */   }
/*     */   
/*     */   public int getRegionX() {
/* 128 */     return this.x >> 5;
/*     */   }
/*     */   
/*     */   public int getRegionZ() {
/* 132 */     return this.z >> 5;
/*     */   }
/*     */   
/*     */   public int getRegionLocalX() {
/* 136 */     return this.x & 0x1F;
/*     */   }
/*     */   
/*     */   public int getRegionLocalZ() {
/* 140 */     return this.z & 0x1F;
/*     */   }
/*     */   
/*     */   public BlockPos getBlockAt(int $$0, int $$1, int $$2) {
/* 144 */     return new BlockPos(getBlockX($$0), $$1, getBlockZ($$2));
/*     */   }
/*     */   
/*     */   public int getBlockX(int $$0) {
/* 148 */     return SectionPos.sectionToBlockCoord(this.x, $$0);
/*     */   }
/*     */   
/*     */   public int getBlockZ(int $$0) {
/* 152 */     return SectionPos.sectionToBlockCoord(this.z, $$0);
/*     */   }
/*     */   
/*     */   public BlockPos getMiddleBlockPosition(int $$0) {
/* 156 */     return new BlockPos(getMiddleBlockX(), $$0, getMiddleBlockZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 161 */     return "[" + this.x + ", " + this.z + "]";
/*     */   }
/*     */   
/*     */   public BlockPos getWorldPosition() {
/* 165 */     return new BlockPos(getMinBlockX(), 0, getMinBlockZ());
/*     */   }
/*     */   
/*     */   public int getChessboardDistance(ChunkPos $$0) {
/* 169 */     return Math.max(Math.abs(this.x - $$0.x), Math.abs(this.z - $$0.z));
/*     */   }
/*     */   
/*     */   public int distanceSquared(ChunkPos $$0) {
/* 173 */     return distanceSquared($$0.x, $$0.z);
/*     */   }
/*     */   
/*     */   public int distanceSquared(long $$0) {
/* 177 */     return distanceSquared(getX($$0), getZ($$0));
/*     */   }
/*     */   
/*     */   private int distanceSquared(int $$0, int $$1) {
/* 181 */     int $$2 = $$0 - this.x;
/* 182 */     int $$3 = $$1 - this.z;
/* 183 */     return $$2 * $$2 + $$3 * $$3;
/*     */   }
/*     */   
/*     */   public static Stream<ChunkPos> rangeClosed(ChunkPos $$0, int $$1) {
/* 187 */     return rangeClosed(new ChunkPos($$0.x - $$1, $$0.z - $$1), new ChunkPos($$0.x + $$1, $$0.z + $$1));
/*     */   }
/*     */   
/*     */   public static Stream<ChunkPos> rangeClosed(final ChunkPos from, final ChunkPos to) {
/* 191 */     int $$2 = Math.abs(from.x - to.x) + 1;
/* 192 */     int $$3 = Math.abs(from.z - to.z) + 1;
/* 193 */     final int xDiff = (from.x < to.x) ? 1 : -1;
/* 194 */     final int zDiff = (from.z < to.z) ? 1 : -1;
/* 195 */     return StreamSupport.stream(new Spliterators.AbstractSpliterator<ChunkPos>(($$2 * $$3), 64)
/*     */         {
/*     */           @Nullable
/*     */           private ChunkPos pos;
/*     */           
/*     */           public boolean tryAdvance(Consumer<? super ChunkPos> $$0) {
/* 201 */             if (this.pos == null) {
/* 202 */               this.pos = from;
/*     */             } else {
/* 204 */               int $$1 = this.pos.x;
/* 205 */               int $$2 = this.pos.z;
/* 206 */               if ($$1 == to.x) {
/* 207 */                 if ($$2 == to.z) {
/* 208 */                   return false;
/*     */                 }
/* 210 */                 this.pos = new ChunkPos(from.x, $$2 + zDiff);
/*     */               } else {
/* 212 */                 this.pos = new ChunkPos($$1 + xDiff, $$2);
/*     */               } 
/*     */             } 
/* 215 */             $$0.accept(this.pos);
/* 216 */             return true;
/*     */           }
/*     */         }false);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\ChunkPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */