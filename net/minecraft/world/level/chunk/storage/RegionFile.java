/*     */ package net.minecraft.world.level.chunk.storage;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import org.slf4j.Logger;
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
/*     */ public class RegionFile
/*     */   implements AutoCloseable
/*     */ {
/*  83 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int SECTOR_BYTES = 4096;
/*     */   
/*     */   @VisibleForTesting
/*     */   protected static final int SECTOR_INTS = 1024;
/*     */   
/*     */   private static final int CHUNK_HEADER_SIZE = 5;
/*     */   private static final int HEADER_OFFSET = 0;
/*  92 */   private static final ByteBuffer PADDING_BUFFER = ByteBuffer.allocateDirect(1);
/*     */   
/*     */   private static final String EXTERNAL_FILE_EXTENSION = ".mcc";
/*     */   
/*     */   private static final int EXTERNAL_STREAM_FLAG = 128;
/*     */   
/*     */   private static final int EXTERNAL_CHUNK_THRESHOLD = 256;
/*     */   private static final int CHUNK_NOT_PRESENT = 0;
/*     */   private final FileChannel file;
/*     */   private final Path externalFileDir;
/*     */   final RegionFileVersion version;
/* 103 */   private final ByteBuffer header = ByteBuffer.allocateDirect(8192);
/*     */   private final IntBuffer offsets;
/*     */   private final IntBuffer timestamps;
/*     */   @VisibleForTesting
/* 107 */   protected final RegionBitmap usedSectors = new RegionBitmap();
/*     */ 
/*     */   
/*     */   public RegionFile(Path $$0, Path $$1, boolean $$2) throws IOException {
/* 111 */     this($$0, $$1, RegionFileVersion.VERSION_DEFLATE, $$2);
/*     */   }
/*     */   
/*     */   public RegionFile(Path $$0, Path $$1, RegionFileVersion $$2, boolean $$3) throws IOException {
/* 115 */     this.version = $$2;
/* 116 */     if (!Files.isDirectory($$1, new java.nio.file.LinkOption[0])) {
/* 117 */       throw new IllegalArgumentException("Expected directory, got " + $$1.toAbsolutePath());
/*     */     }
/* 119 */     this.externalFileDir = $$1;
/* 120 */     this.offsets = this.header.asIntBuffer();
/* 121 */     this.offsets.limit(1024);
/* 122 */     this.header.position(4096);
/* 123 */     this.timestamps = this.header.asIntBuffer();
/*     */     
/* 125 */     if ($$3) {
/* 126 */       this.file = FileChannel.open($$0, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC });
/*     */     } else {
/* 128 */       this.file = FileChannel.open($$0, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE });
/*     */     } 
/*     */ 
/*     */     
/* 132 */     this.usedSectors.force(0, 2);
/*     */     
/* 134 */     this.header.position(0);
/* 135 */     int $$4 = this.file.read(this.header, 0L);
/* 136 */     if ($$4 != -1) {
/* 137 */       if ($$4 != 8192) {
/* 138 */         LOGGER.warn("Region file {} has truncated header: {}", $$0, Integer.valueOf($$4));
/*     */       }
/*     */       
/* 141 */       long $$5 = Files.size($$0);
/* 142 */       for (int $$6 = 0; $$6 < 1024; $$6++) {
/* 143 */         int $$7 = this.offsets.get($$6);
/* 144 */         if ($$7 != 0) {
/* 145 */           int $$8 = getSectorNumber($$7);
/* 146 */           int $$9 = getNumSectors($$7);
/* 147 */           if ($$8 < 2) {
/* 148 */             LOGGER.warn("Region file {} has invalid sector at index: {}; sector {} overlaps with header", new Object[] { $$0, Integer.valueOf($$6), Integer.valueOf($$8) });
/* 149 */             this.offsets.put($$6, 0);
/* 150 */           } else if ($$9 == 0) {
/* 151 */             LOGGER.warn("Region file {} has an invalid sector at index: {}; size has to be > 0", $$0, Integer.valueOf($$6));
/* 152 */             this.offsets.put($$6, 0);
/* 153 */           } else if ($$8 * 4096L > $$5) {
/* 154 */             LOGGER.warn("Region file {} has an invalid sector at index: {}; sector {} is out of bounds", new Object[] { $$0, Integer.valueOf($$6), Integer.valueOf($$8) });
/* 155 */             this.offsets.put($$6, 0);
/*     */           } else {
/* 157 */             this.usedSectors.force($$8, $$9);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Path getExternalChunkPath(ChunkPos $$0) {
/* 165 */     String $$1 = "c." + $$0.x + "." + $$0.z + ".mcc";
/* 166 */     return this.externalFileDir.resolve($$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public synchronized DataInputStream getChunkDataInputStream(ChunkPos $$0) throws IOException {
/* 171 */     int $$1 = getOffset($$0);
/* 172 */     if ($$1 == 0) {
/* 173 */       return null;
/*     */     }
/*     */     
/* 176 */     int $$2 = getSectorNumber($$1);
/* 177 */     int $$3 = getNumSectors($$1);
/*     */     
/* 179 */     int $$4 = $$3 * 4096;
/* 180 */     ByteBuffer $$5 = ByteBuffer.allocate($$4);
/* 181 */     this.file.read($$5, ($$2 * 4096));
/* 182 */     $$5.flip();
/*     */     
/* 184 */     if ($$5.remaining() < 5) {
/* 185 */       LOGGER.error("Chunk {} header is truncated: expected {} but read {}", new Object[] { $$0, Integer.valueOf($$4), Integer.valueOf($$5.remaining()) });
/* 186 */       return null;
/*     */     } 
/*     */     
/* 189 */     int $$6 = $$5.getInt();
/* 190 */     byte $$7 = $$5.get();
/*     */     
/* 192 */     if ($$6 == 0) {
/* 193 */       LOGGER.warn("Chunk {} is allocated, but stream is missing", $$0);
/* 194 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 198 */     int $$8 = $$6 - 1;
/*     */     
/* 200 */     if (isExternalStreamChunk($$7)) {
/* 201 */       if ($$8 != 0) {
/* 202 */         LOGGER.warn("Chunk has both internal and external streams");
/*     */       }
/* 204 */       return createExternalChunkInputStream($$0, getExternalChunkVersion($$7));
/*     */     } 
/*     */     
/* 207 */     if ($$8 > $$5.remaining()) {
/* 208 */       LOGGER.error("Chunk {} stream is truncated: expected {} but read {}", new Object[] { $$0, Integer.valueOf($$8), Integer.valueOf($$5.remaining()) });
/* 209 */       return null;
/*     */     } 
/*     */     
/* 212 */     if ($$8 < 0) {
/* 213 */       LOGGER.error("Declared size {} of chunk {} is negative", Integer.valueOf($$6), $$0);
/* 214 */       return null;
/*     */     } 
/*     */     
/* 217 */     return createChunkInputStream($$0, $$7, createStream($$5, $$8));
/*     */   }
/*     */   
/*     */   private static int getTimestamp() {
/* 221 */     return (int)(Util.getEpochMillis() / 1000L);
/*     */   }
/*     */   
/*     */   private static boolean isExternalStreamChunk(byte $$0) {
/* 225 */     return (($$0 & 0x80) != 0);
/*     */   }
/*     */   
/*     */   private static byte getExternalChunkVersion(byte $$0) {
/* 229 */     return (byte)($$0 & 0xFFFFFF7F);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private DataInputStream createChunkInputStream(ChunkPos $$0, byte $$1, InputStream $$2) throws IOException {
/* 234 */     RegionFileVersion $$3 = RegionFileVersion.fromId($$1);
/* 235 */     if ($$3 == null) {
/* 236 */       LOGGER.error("Chunk {} has invalid chunk stream version {}", $$0, Byte.valueOf($$1));
/* 237 */       return null;
/*     */     } 
/* 239 */     return new DataInputStream($$3.wrap($$2));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private DataInputStream createExternalChunkInputStream(ChunkPos $$0, byte $$1) throws IOException {
/* 244 */     Path $$2 = getExternalChunkPath($$0);
/* 245 */     if (!Files.isRegularFile($$2, new java.nio.file.LinkOption[0])) {
/* 246 */       LOGGER.error("External chunk path {} is not file", $$2);
/* 247 */       return null;
/*     */     } 
/*     */     
/* 250 */     return createChunkInputStream($$0, $$1, Files.newInputStream($$2, new OpenOption[0]));
/*     */   }
/*     */   
/*     */   private static ByteArrayInputStream createStream(ByteBuffer $$0, int $$1) {
/* 254 */     return new ByteArrayInputStream($$0.array(), $$0.position(), $$1);
/*     */   }
/*     */   
/*     */   private int packSectorOffset(int $$0, int $$1) {
/* 258 */     return $$0 << 8 | $$1;
/*     */   }
/*     */   
/*     */   private static int getNumSectors(int $$0) {
/* 262 */     return $$0 & 0xFF;
/*     */   }
/*     */   
/*     */   private static int getSectorNumber(int $$0) {
/* 266 */     return $$0 >> 8 & 0xFFFFFF;
/*     */   }
/*     */   
/*     */   private static int sizeToSectors(int $$0) {
/* 270 */     return ($$0 + 4096 - 1) / 4096;
/*     */   }
/*     */   
/*     */   public boolean doesChunkExist(ChunkPos $$0) {
/* 274 */     int $$1 = getOffset($$0);
/* 275 */     if ($$1 == 0) {
/* 276 */       return false;
/*     */     }
/*     */     
/* 279 */     int $$2 = getSectorNumber($$1);
/* 280 */     int $$3 = getNumSectors($$1);
/*     */     
/* 282 */     ByteBuffer $$4 = ByteBuffer.allocate(5);
/*     */     try {
/* 284 */       this.file.read($$4, ($$2 * 4096));
/* 285 */       $$4.flip();
/* 286 */       if ($$4.remaining() != 5) {
/* 287 */         return false;
/*     */       }
/*     */       
/* 290 */       int $$5 = $$4.getInt();
/* 291 */       byte $$6 = $$4.get();
/* 292 */       if (isExternalStreamChunk($$6)) {
/* 293 */         if (!RegionFileVersion.isValidVersion(getExternalChunkVersion($$6))) {
/* 294 */           return false;
/*     */         }
/*     */         
/* 297 */         if (!Files.isRegularFile(getExternalChunkPath($$0), new java.nio.file.LinkOption[0])) {
/* 298 */           return false;
/*     */         }
/*     */       } else {
/* 301 */         if (!RegionFileVersion.isValidVersion($$6)) {
/* 302 */           return false;
/*     */         }
/*     */         
/* 305 */         if ($$5 == 0) {
/* 306 */           return false;
/*     */         }
/*     */         
/* 309 */         int $$7 = $$5 - 1;
/* 310 */         if ($$7 < 0 || $$7 > 4096 * $$3) {
/* 311 */           return false;
/*     */         }
/*     */       } 
/* 314 */     } catch (IOException $$8) {
/* 315 */       return false;
/*     */     } 
/*     */     
/* 318 */     return true;
/*     */   }
/*     */   
/*     */   public DataOutputStream getChunkDataOutputStream(ChunkPos $$0) throws IOException {
/* 322 */     return new DataOutputStream(this.version.wrap(new ChunkBuffer($$0)));
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/* 326 */     this.file.force(true);
/*     */   }
/*     */   
/*     */   public void clear(ChunkPos $$0) throws IOException {
/* 330 */     int $$1 = getOffsetIndex($$0);
/* 331 */     int $$2 = this.offsets.get($$1);
/* 332 */     if ($$2 == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 336 */     this.offsets.put($$1, 0);
/* 337 */     this.timestamps.put($$1, getTimestamp());
/* 338 */     writeHeader();
/*     */     
/* 340 */     Files.deleteIfExists(getExternalChunkPath($$0));
/* 341 */     this.usedSectors.free(getSectorNumber($$2), getNumSectors($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   private class ChunkBuffer
/*     */     extends ByteArrayOutputStream
/*     */   {
/*     */     private final ChunkPos pos;
/*     */ 
/*     */     
/*     */     public ChunkBuffer(ChunkPos $$0) {
/* 352 */       super(8096);
/*     */ 
/*     */       
/* 355 */       write(0);
/* 356 */       write(0);
/* 357 */       write(0);
/* 358 */       write(0);
/*     */       
/* 360 */       write(RegionFile.this.version.getId());
/* 361 */       this.pos = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 366 */       ByteBuffer $$0 = ByteBuffer.wrap(this.buf, 0, this.count);
/*     */       
/* 368 */       $$0.putInt(0, this.count - 5 + 1);
/* 369 */       RegionFile.this.write(this.pos, $$0);
/*     */     } }
/*     */   protected synchronized void write(ChunkPos $$0, ByteBuffer $$1) throws IOException {
/*     */     int $$12;
/*     */     CommitOp $$13;
/* 374 */     int $$2 = getOffsetIndex($$0);
/* 375 */     int $$3 = this.offsets.get($$2);
/* 376 */     int $$4 = getSectorNumber($$3);
/* 377 */     int $$5 = getNumSectors($$3);
/*     */     
/* 379 */     int $$6 = $$1.remaining();
/* 380 */     int $$7 = sizeToSectors($$6);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     if ($$7 >= 256) {
/* 386 */       Path $$8 = getExternalChunkPath($$0);
/* 387 */       LOGGER.warn("Saving oversized chunk {} ({} bytes} to external file {}", new Object[] { $$0, Integer.valueOf($$6), $$8 });
/* 388 */       $$7 = 1;
/* 389 */       int $$9 = this.usedSectors.allocate($$7);
/* 390 */       CommitOp $$10 = writeToExternalFile($$8, $$1);
/* 391 */       ByteBuffer $$11 = createExternalStub();
/* 392 */       this.file.write($$11, ($$9 * 4096));
/*     */     } else {
/* 394 */       $$12 = this.usedSectors.allocate($$7);
/* 395 */       $$13 = (() -> Files.deleteIfExists(getExternalChunkPath($$0)));
/* 396 */       this.file.write($$1, ($$12 * 4096));
/*     */     } 
/*     */     
/* 399 */     this.offsets.put($$2, packSectorOffset($$12, $$7));
/* 400 */     this.timestamps.put($$2, getTimestamp());
/* 401 */     writeHeader();
/*     */     
/* 403 */     $$13.run();
/*     */     
/* 405 */     if ($$4 != 0) {
/* 406 */       this.usedSectors.free($$4, $$5);
/*     */     }
/*     */   }
/*     */   
/*     */   private ByteBuffer createExternalStub() {
/* 411 */     ByteBuffer $$0 = ByteBuffer.allocate(5);
/* 412 */     $$0.putInt(1);
/* 413 */     $$0.put((byte)(this.version.getId() | 0x80));
/* 414 */     $$0.flip();
/* 415 */     return $$0;
/*     */   }
/*     */   
/*     */   private CommitOp writeToExternalFile(Path $$0, ByteBuffer $$1) throws IOException {
/* 419 */     Path $$2 = Files.createTempFile(this.externalFileDir, "tmp", null, (FileAttribute<?>[])new FileAttribute[0]);
/* 420 */     FileChannel $$3 = FileChannel.open($$2, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.WRITE }); 
/* 421 */     try { $$1.position(5);
/* 422 */       $$3.write($$1);
/* 423 */       if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null)
/* 424 */         try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return () -> Files.move($$0, $$1, new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*     */   }
/*     */   
/*     */   private void writeHeader() throws IOException {
/* 428 */     this.header.position(0);
/* 429 */     this.file.write(this.header, 0L);
/*     */   }
/*     */   
/*     */   private int getOffset(ChunkPos $$0) {
/* 433 */     return this.offsets.get(getOffsetIndex($$0));
/*     */   }
/*     */   
/*     */   public boolean hasChunk(ChunkPos $$0) {
/* 437 */     return (getOffset($$0) != 0);
/*     */   }
/*     */   
/*     */   private static int getOffsetIndex(ChunkPos $$0) {
/* 441 */     return $$0.getRegionLocalX() + $$0.getRegionLocalZ() * 32;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/*     */     try {
/* 447 */       padToFullSector();
/*     */     } finally {
/*     */       try {
/* 450 */         this.file.force(true);
/*     */       } finally {
/* 452 */         this.file.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void padToFullSector() throws IOException {
/* 460 */     int $$0 = (int)this.file.size();
/* 461 */     int $$1 = sizeToSectors($$0) * 4096;
/* 462 */     if ($$0 != $$1) {
/* 463 */       ByteBuffer $$2 = PADDING_BUFFER.duplicate();
/* 464 */       $$2.position(0);
/* 465 */       this.file.write($$2, ($$1 - 1));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static interface CommitOp {
/*     */     void run() throws IOException;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\storage\RegionFile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */