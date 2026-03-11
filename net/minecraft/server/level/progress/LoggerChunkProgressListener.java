/*    */ package net.minecraft.server.level.progress;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.chunk.ChunkStatus;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class LoggerChunkProgressListener
/*    */   implements ChunkProgressListener
/*    */ {
/* 15 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   private final int maxCount;
/*    */   private int count;
/*    */   private long startTime;
/* 19 */   private long nextTickTime = Long.MAX_VALUE;
/*    */   
/*    */   public LoggerChunkProgressListener(int $$0) {
/* 22 */     int $$1 = $$0 * 2 + 1;
/* 23 */     this.maxCount = $$1 * $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateSpawnPos(ChunkPos $$0) {
/* 28 */     this.nextTickTime = Util.getMillis();
/* 29 */     this.startTime = this.nextTickTime;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onStatusChange(ChunkPos $$0, @Nullable ChunkStatus $$1) {
/* 34 */     if ($$1 == ChunkStatus.FULL) {
/* 35 */       this.count++;
/*    */     }
/* 37 */     int $$2 = getProgress();
/*    */ 
/*    */ 
/*    */     
/* 41 */     if (Util.getMillis() > this.nextTickTime) {
/* 42 */       this.nextTickTime += 500L;
/*    */       
/* 44 */       LOGGER.info(Component.translatable("menu.preparingSpawn", new Object[] { Integer.valueOf(Mth.clamp($$2, 0, 100)) }).getString());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void start() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void stop() {
/* 55 */     LOGGER.info("Time elapsed: {} ms", Long.valueOf(Util.getMillis() - this.startTime));
/* 56 */     this.nextTickTime = Long.MAX_VALUE;
/*    */   }
/*    */   
/*    */   public int getProgress() {
/* 60 */     return Mth.floor(this.count * 100.0F / this.maxCount);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\progress\LoggerChunkProgressListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */