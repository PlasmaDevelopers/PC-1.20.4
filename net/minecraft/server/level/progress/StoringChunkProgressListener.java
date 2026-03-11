/*    */ package net.minecraft.server.level.progress;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.chunk.ChunkStatus;
/*    */ 
/*    */ public class StoringChunkProgressListener
/*    */   implements ChunkProgressListener
/*    */ {
/*    */   private final LoggerChunkProgressListener delegate;
/*    */   private final Long2ObjectOpenHashMap<ChunkStatus> statuses;
/* 13 */   private ChunkPos spawnPos = new ChunkPos(0, 0);
/*    */   private final int fullDiameter;
/*    */   private final int radius;
/*    */   private final int diameter;
/*    */   private boolean started;
/*    */   
/*    */   public StoringChunkProgressListener(int $$0) {
/* 20 */     this.delegate = new LoggerChunkProgressListener($$0);
/* 21 */     this.fullDiameter = $$0 * 2 + 1;
/* 22 */     this.radius = $$0 + ChunkStatus.maxDistance();
/* 23 */     this.diameter = this.radius * 2 + 1;
/* 24 */     this.statuses = new Long2ObjectOpenHashMap();
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateSpawnPos(ChunkPos $$0) {
/* 29 */     if (!this.started) {
/*    */       return;
/*    */     }
/* 32 */     this.delegate.updateSpawnPos($$0);
/* 33 */     this.spawnPos = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onStatusChange(ChunkPos $$0, @Nullable ChunkStatus $$1) {
/* 38 */     if (!this.started) {
/*    */       return;
/*    */     }
/* 41 */     this.delegate.onStatusChange($$0, $$1);
/* 42 */     if ($$1 == null) {
/* 43 */       this.statuses.remove($$0.toLong());
/*    */     } else {
/* 45 */       this.statuses.put($$0.toLong(), $$1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 51 */     this.started = true;
/* 52 */     this.statuses.clear();
/* 53 */     this.delegate.start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 58 */     this.started = false;
/* 59 */     this.delegate.stop();
/*    */   }
/*    */   
/*    */   public int getFullDiameter() {
/* 63 */     return this.fullDiameter;
/*    */   }
/*    */   
/*    */   public int getDiameter() {
/* 67 */     return this.diameter;
/*    */   }
/*    */   
/*    */   public int getProgress() {
/* 71 */     return this.delegate.getProgress();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public ChunkStatus getStatus(int $$0, int $$1) {
/* 76 */     return (ChunkStatus)this.statuses.get(ChunkPos.asLong($$0 + this.spawnPos.x - this.radius, $$1 + this.spawnPos.z - this.radius));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\progress\StoringChunkProgressListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */