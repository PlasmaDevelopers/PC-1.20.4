/*     */ package net.minecraft.server.level;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import net.minecraft.world.level.ChunkPos;
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
/*     */ class FixedPlayerDistanceChunkTracker
/*     */   extends ChunkTracker
/*     */ {
/* 334 */   protected final Long2ByteMap chunks = (Long2ByteMap)new Long2ByteOpenHashMap();
/*     */   protected final int maxDistance;
/*     */   
/*     */   protected FixedPlayerDistanceChunkTracker(int $$0) {
/* 338 */     super($$0 + 2, 16, 256);
/* 339 */     this.maxDistance = $$0;
/* 340 */     this.chunks.defaultReturnValue((byte)($$0 + 2));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLevel(long $$0) {
/* 345 */     return this.chunks.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setLevel(long $$0, int $$1) {
/*     */     byte $$3;
/* 351 */     if ($$1 > this.maxDistance) {
/* 352 */       byte $$2 = this.chunks.remove($$0);
/*     */     } else {
/* 354 */       $$3 = this.chunks.put($$0, (byte)$$1);
/*     */     } 
/* 356 */     onLevelChange($$0, $$3, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onLevelChange(long $$0, int $$1, int $$2) {}
/*     */ 
/*     */   
/*     */   protected int getLevelFromSource(long $$0) {
/* 364 */     return havePlayer($$0) ? 0 : Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   private boolean havePlayer(long $$0) {
/* 368 */     ObjectSet<ServerPlayer> $$1 = (ObjectSet<ServerPlayer>)DistanceManager.this.playersPerChunk.get($$0);
/* 369 */     return ($$1 != null && !$$1.isEmpty());
/*     */   }
/*     */   
/*     */   public void runAllUpdates() {
/* 373 */     runUpdates(2147483647);
/*     */   }
/*     */   
/*     */   private void dumpChunks(String $$0) {
/*     */     
/* 378 */     try { FileOutputStream $$1 = new FileOutputStream(new File($$0)); 
/* 379 */       try { for (ObjectIterator<Long2ByteMap.Entry> objectIterator = this.chunks.long2ByteEntrySet().iterator(); objectIterator.hasNext(); ) { Long2ByteMap.Entry $$2 = objectIterator.next();
/* 380 */           ChunkPos $$3 = new ChunkPos($$2.getLongKey());
/* 381 */           String $$4 = Byte.toString($$2.getByteValue());
/* 382 */           $$1.write(("" + $$3.x + "\t" + $$3.x + "\t" + $$3.z + "\n")
/*     */ 
/*     */ 
/*     */               
/* 386 */               .getBytes(StandardCharsets.UTF_8)); }
/*     */         
/* 388 */         $$1.close(); } catch (Throwable throwable) { try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException $$5)
/* 389 */     { DistanceManager.LOGGER.error("Failed to dump chunks to {}", $$0, $$5); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\DistanceManager$FixedPlayerDistanceChunkTracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */