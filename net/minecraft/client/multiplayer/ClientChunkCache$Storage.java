/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
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
/*     */ final class Storage
/*     */ {
/*     */   final AtomicReferenceArray<LevelChunk> chunks;
/*     */   final int chunkRadius;
/*     */   private final int viewRange;
/*     */   volatile int viewCenterX;
/*     */   volatile int viewCenterZ;
/*     */   int chunkCount;
/*     */   
/*     */   Storage(int $$0) {
/* 185 */     this.chunkRadius = $$0;
/* 186 */     this.viewRange = $$0 * 2 + 1;
/* 187 */     this.chunks = new AtomicReferenceArray<>(this.viewRange * this.viewRange);
/*     */   }
/*     */   
/*     */   int getIndex(int $$0, int $$1) {
/* 191 */     return Math.floorMod($$1, this.viewRange) * this.viewRange + Math.floorMod($$0, this.viewRange);
/*     */   }
/*     */   
/*     */   protected void replace(int $$0, @Nullable LevelChunk $$1) {
/* 195 */     LevelChunk $$2 = this.chunks.getAndSet($$0, $$1);
/* 196 */     if ($$2 != null) {
/* 197 */       this.chunkCount--;
/* 198 */       ClientChunkCache.this.level.unload($$2);
/*     */     } 
/*     */     
/* 201 */     if ($$1 != null) {
/* 202 */       this.chunkCount++;
/*     */     }
/*     */   }
/*     */   
/*     */   protected LevelChunk replace(int $$0, LevelChunk $$1, @Nullable LevelChunk $$2) {
/* 207 */     if (this.chunks.compareAndSet($$0, $$1, $$2) && 
/* 208 */       $$2 == null) {
/* 209 */       this.chunkCount--;
/*     */     }
/*     */     
/* 212 */     ClientChunkCache.this.level.unload($$1);
/* 213 */     return $$1;
/*     */   }
/*     */   
/*     */   boolean inRange(int $$0, int $$1) {
/* 217 */     return (Math.abs($$0 - this.viewCenterX) <= this.chunkRadius && Math.abs($$1 - this.viewCenterZ) <= this.chunkRadius);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected LevelChunk getChunk(int $$0) {
/* 222 */     return this.chunks.get($$0);
/*     */   }
/*     */   
/*     */   private void dumpChunks(String $$0) {
/*     */     
/* 227 */     try { FileOutputStream $$1 = new FileOutputStream($$0); 
/* 228 */       try { int $$2 = ClientChunkCache.this.storage.chunkRadius;
/* 229 */         for (int $$3 = this.viewCenterZ - $$2; $$3 <= this.viewCenterZ + $$2; $$3++) {
/* 230 */           for (int $$4 = this.viewCenterX - $$2; $$4 <= this.viewCenterX + $$2; $$4++) {
/* 231 */             LevelChunk $$5 = ClientChunkCache.this.storage.chunks.get(ClientChunkCache.this.storage.getIndex($$4, $$3));
/* 232 */             if ($$5 != null) {
/* 233 */               ChunkPos $$6 = $$5.getPos();
/* 234 */               $$1.write(("" + $$6.x + "\t" + $$6.x + "\t" + $$6.z + "\n").getBytes(StandardCharsets.UTF_8));
/*     */             } 
/*     */           } 
/*     */         } 
/* 238 */         $$1.close(); } catch (Throwable throwable) { try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException $$7)
/* 239 */     { ClientChunkCache.LOGGER.error("Failed to dump chunks to file {}", $$0, $$7); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ClientChunkCache$Storage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */