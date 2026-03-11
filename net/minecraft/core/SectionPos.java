/*     */ package net.minecraft.core;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.LongConsumer;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.entity.EntityAccess;
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
/*     */ public class SectionPos
/*     */   extends Vec3i
/*     */ {
/*     */   public static final int SECTION_BITS = 4;
/*     */   public static final int SECTION_SIZE = 16;
/*     */   public static final int SECTION_MASK = 15;
/*     */   public static final int SECTION_HALF_SIZE = 8;
/*     */   public static final int SECTION_MAX_INDEX = 15;
/*     */   private static final int PACKED_X_LENGTH = 22;
/*     */   private static final int PACKED_Y_LENGTH = 20;
/*     */   private static final int PACKED_Z_LENGTH = 22;
/*     */   private static final long PACKED_X_MASK = 4194303L;
/*     */   private static final long PACKED_Y_MASK = 1048575L;
/*     */   private static final long PACKED_Z_MASK = 4194303L;
/*     */   private static final int Y_OFFSET = 0;
/*     */   private static final int Z_OFFSET = 20;
/*     */   private static final int X_OFFSET = 42;
/*     */   private static final int RELATIVE_X_SHIFT = 8;
/*     */   private static final int RELATIVE_Y_SHIFT = 0;
/*     */   private static final int RELATIVE_Z_SHIFT = 4;
/*     */   
/*     */   SectionPos(int $$0, int $$1, int $$2) {
/*  48 */     super($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static SectionPos of(int $$0, int $$1, int $$2) {
/*  52 */     return new SectionPos($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static SectionPos of(BlockPos $$0) {
/*  56 */     return new SectionPos(blockToSectionCoord($$0.getX()), blockToSectionCoord($$0.getY()), blockToSectionCoord($$0.getZ()));
/*     */   }
/*     */   
/*     */   public static SectionPos of(ChunkPos $$0, int $$1) {
/*  60 */     return new SectionPos($$0.x, $$1, $$0.z);
/*     */   }
/*     */   
/*     */   public static SectionPos of(EntityAccess $$0) {
/*  64 */     return of($$0.blockPosition());
/*     */   }
/*     */   
/*     */   public static SectionPos of(Position $$0) {
/*  68 */     return new SectionPos(
/*  69 */         blockToSectionCoord($$0.x()), 
/*  70 */         blockToSectionCoord($$0.y()), 
/*  71 */         blockToSectionCoord($$0.z()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static SectionPos of(long $$0) {
/*  76 */     return new SectionPos(x($$0), y($$0), z($$0));
/*     */   }
/*     */   
/*     */   public static SectionPos bottomOf(ChunkAccess $$0) {
/*  80 */     return of($$0.getPos(), $$0.getMinSection());
/*     */   }
/*     */   
/*     */   public static long offset(long $$0, Direction $$1) {
/*  84 */     return offset($$0, $$1.getStepX(), $$1.getStepY(), $$1.getStepZ());
/*     */   }
/*     */   
/*     */   public static long offset(long $$0, int $$1, int $$2, int $$3) {
/*  88 */     return asLong(x($$0) + $$1, y($$0) + $$2, z($$0) + $$3);
/*     */   }
/*     */   
/*     */   public static int posToSectionCoord(double $$0) {
/*  92 */     return blockToSectionCoord(Mth.floor($$0));
/*     */   }
/*     */   
/*     */   public static int blockToSectionCoord(int $$0) {
/*  96 */     return $$0 >> 4;
/*     */   }
/*     */   
/*     */   public static int blockToSectionCoord(double $$0) {
/* 100 */     return Mth.floor($$0) >> 4;
/*     */   }
/*     */   
/*     */   public static int sectionRelative(int $$0) {
/* 104 */     return $$0 & 0xF;
/*     */   }
/*     */   
/*     */   public static short sectionRelativePos(BlockPos $$0) {
/* 108 */     int $$1 = sectionRelative($$0.getX());
/* 109 */     int $$2 = sectionRelative($$0.getY());
/* 110 */     int $$3 = sectionRelative($$0.getZ());
/* 111 */     return (short)($$1 << 8 | $$3 << 4 | $$2 << 0);
/*     */   }
/*     */   
/*     */   public static int sectionRelativeX(short $$0) {
/* 115 */     return $$0 >>> 8 & 0xF;
/*     */   }
/*     */   
/*     */   public static int sectionRelativeY(short $$0) {
/* 119 */     return $$0 >>> 0 & 0xF;
/*     */   }
/*     */   
/*     */   public static int sectionRelativeZ(short $$0) {
/* 123 */     return $$0 >>> 4 & 0xF;
/*     */   }
/*     */   
/*     */   public int relativeToBlockX(short $$0) {
/* 127 */     return minBlockX() + sectionRelativeX($$0);
/*     */   }
/*     */   
/*     */   public int relativeToBlockY(short $$0) {
/* 131 */     return minBlockY() + sectionRelativeY($$0);
/*     */   }
/*     */   
/*     */   public int relativeToBlockZ(short $$0) {
/* 135 */     return minBlockZ() + sectionRelativeZ($$0);
/*     */   }
/*     */   
/*     */   public BlockPos relativeToBlockPos(short $$0) {
/* 139 */     return new BlockPos(relativeToBlockX($$0), relativeToBlockY($$0), relativeToBlockZ($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int sectionToBlockCoord(int $$0) {
/* 147 */     return $$0 << 4;
/*     */   }
/*     */   
/*     */   public static int sectionToBlockCoord(int $$0, int $$1) {
/* 151 */     return sectionToBlockCoord($$0) + $$1;
/*     */   }
/*     */   
/*     */   public static int x(long $$0) {
/* 155 */     return (int)($$0 << 0L >> 42L);
/*     */   }
/*     */   
/*     */   public static int y(long $$0) {
/* 159 */     return (int)($$0 << 44L >> 44L);
/*     */   }
/*     */   
/*     */   public static int z(long $$0) {
/* 163 */     return (int)($$0 << 22L >> 42L);
/*     */   }
/*     */   
/*     */   public int x() {
/* 167 */     return getX();
/*     */   }
/*     */   
/*     */   public int y() {
/* 171 */     return getY();
/*     */   }
/*     */   
/*     */   public int z() {
/* 175 */     return getZ();
/*     */   }
/*     */   
/*     */   public int minBlockX() {
/* 179 */     return sectionToBlockCoord(x());
/*     */   }
/*     */   
/*     */   public int minBlockY() {
/* 183 */     return sectionToBlockCoord(y());
/*     */   }
/*     */   
/*     */   public int minBlockZ() {
/* 187 */     return sectionToBlockCoord(z());
/*     */   }
/*     */   
/*     */   public int maxBlockX() {
/* 191 */     return sectionToBlockCoord(x(), 15);
/*     */   }
/*     */   
/*     */   public int maxBlockY() {
/* 195 */     return sectionToBlockCoord(y(), 15);
/*     */   }
/*     */   
/*     */   public int maxBlockZ() {
/* 199 */     return sectionToBlockCoord(z(), 15);
/*     */   }
/*     */   
/*     */   public static long blockToSection(long $$0) {
/* 203 */     return asLong(
/* 204 */         blockToSectionCoord(BlockPos.getX($$0)), 
/* 205 */         blockToSectionCoord(BlockPos.getY($$0)), 
/* 206 */         blockToSectionCoord(BlockPos.getZ($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getZeroNode(int $$0, int $$1) {
/* 211 */     return getZeroNode(asLong($$0, 0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getZeroNode(long $$0) {
/* 216 */     return $$0 & 0xFFFFFFFFFFF00000L;
/*     */   }
/*     */   
/*     */   public BlockPos origin() {
/* 220 */     return new BlockPos(sectionToBlockCoord(x()), sectionToBlockCoord(y()), sectionToBlockCoord(z()));
/*     */   }
/*     */   
/*     */   public BlockPos center() {
/* 224 */     int $$0 = 8;
/* 225 */     return origin().offset(8, 8, 8);
/*     */   }
/*     */   
/*     */   public ChunkPos chunk() {
/* 229 */     return new ChunkPos(x(), z());
/*     */   }
/*     */   
/*     */   public static long asLong(BlockPos $$0) {
/* 233 */     return asLong(blockToSectionCoord($$0.getX()), blockToSectionCoord($$0.getY()), blockToSectionCoord($$0.getZ()));
/*     */   }
/*     */   
/*     */   public static long asLong(int $$0, int $$1, int $$2) {
/* 237 */     long $$3 = 0L;
/* 238 */     $$3 |= ($$0 & 0x3FFFFFL) << 42L;
/* 239 */     $$3 |= ($$1 & 0xFFFFFL) << 0L;
/* 240 */     $$3 |= ($$2 & 0x3FFFFFL) << 20L;
/* 241 */     return $$3;
/*     */   }
/*     */   
/*     */   public long asLong() {
/* 245 */     return asLong(x(), y(), z());
/*     */   }
/*     */ 
/*     */   
/*     */   public SectionPos offset(int $$0, int $$1, int $$2) {
/* 250 */     if ($$0 == 0 && $$1 == 0 && $$2 == 0) {
/* 251 */       return this;
/*     */     }
/* 253 */     return new SectionPos(x() + $$0, y() + $$1, z() + $$2);
/*     */   }
/*     */   
/*     */   public Stream<BlockPos> blocksInside() {
/* 257 */     return BlockPos.betweenClosedStream(minBlockX(), minBlockY(), minBlockZ(), maxBlockX(), maxBlockY(), maxBlockZ());
/*     */   }
/*     */   
/*     */   public static Stream<SectionPos> cube(SectionPos $$0, int $$1) {
/* 261 */     int $$2 = $$0.x();
/* 262 */     int $$3 = $$0.y();
/* 263 */     int $$4 = $$0.z();
/* 264 */     return betweenClosedStream($$2 - $$1, $$3 - $$1, $$4 - $$1, $$2 + $$1, $$3 + $$1, $$4 + $$1);
/*     */   }
/*     */   
/*     */   public static Stream<SectionPos> aroundChunk(ChunkPos $$0, int $$1, int $$2, int $$3) {
/* 268 */     int $$4 = $$0.x;
/* 269 */     int $$5 = $$0.z;
/* 270 */     return betweenClosedStream($$4 - $$1, $$2, $$5 - $$1, $$4 + $$1, $$3 - 1, $$5 + $$1);
/*     */   }
/*     */   
/*     */   public static Stream<SectionPos> betweenClosedStream(final int minX, final int minY, final int minZ, final int maxX, final int maxY, final int maxZ) {
/* 274 */     return StreamSupport.stream(new Spliterators.AbstractSpliterator<SectionPos>(((maxX - minX + 1) * (maxY - minY + 1) * (maxZ - minZ + 1)), 64) {
/* 275 */           final Cursor3D cursor = new Cursor3D(minX, minY, minZ, maxX, maxY, maxZ);
/*     */ 
/*     */           
/*     */           public boolean tryAdvance(Consumer<? super SectionPos> $$0) {
/* 279 */             if (this.cursor.advance()) {
/* 280 */               $$0.accept(new SectionPos(this.cursor.nextX(), this.cursor.nextY(), this.cursor.nextZ()));
/* 281 */               return true;
/*     */             } 
/* 283 */             return false;
/*     */           }
/*     */         }false);
/*     */   }
/*     */   
/*     */   public static void aroundAndAtBlockPos(BlockPos $$0, LongConsumer $$1) {
/* 289 */     aroundAndAtBlockPos($$0.getX(), $$0.getY(), $$0.getZ(), $$1);
/*     */   }
/*     */   
/*     */   public static void aroundAndAtBlockPos(long $$0, LongConsumer $$1) {
/* 293 */     aroundAndAtBlockPos(BlockPos.getX($$0), BlockPos.getY($$0), BlockPos.getZ($$0), $$1);
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
/*     */   public static void aroundAndAtBlockPos(int $$0, int $$1, int $$2, LongConsumer $$3) {
/* 305 */     int $$4 = blockToSectionCoord($$0 - 1);
/* 306 */     int $$5 = blockToSectionCoord($$0 + 1);
/*     */     
/* 308 */     int $$6 = blockToSectionCoord($$1 - 1);
/* 309 */     int $$7 = blockToSectionCoord($$1 + 1);
/*     */     
/* 311 */     int $$8 = blockToSectionCoord($$2 - 1);
/* 312 */     int $$9 = blockToSectionCoord($$2 + 1);
/*     */     
/* 314 */     if ($$4 == $$5 && $$6 == $$7 && $$8 == $$9) {
/* 315 */       $$3.accept(asLong($$4, $$6, $$8));
/*     */     } else {
/* 317 */       for (int $$10 = $$4; $$10 <= $$5; $$10++) {
/* 318 */         for (int $$11 = $$6; $$11 <= $$7; $$11++) {
/* 319 */           for (int $$12 = $$8; $$12 <= $$9; $$12++)
/* 320 */             $$3.accept(asLong($$10, $$11, $$12)); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\SectionPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */