/*    */ package net.minecraft.world.level.chunk;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*    */ 
/*    */ public abstract class ChunkSource
/*    */   implements LightChunkGetter, AutoCloseable {
/*    */   @Nullable
/*    */   public LevelChunk getChunk(int $$0, int $$1, boolean $$2) {
/* 13 */     return (LevelChunk)getChunk($$0, $$1, ChunkStatus.FULL, $$2);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public LevelChunk getChunkNow(int $$0, int $$1) {
/* 18 */     return getChunk($$0, $$1, false);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public LightChunk getChunkForLighting(int $$0, int $$1) {
/* 24 */     return getChunk($$0, $$1, ChunkStatus.EMPTY, false);
/*    */   }
/*    */   
/*    */   public boolean hasChunk(int $$0, int $$1) {
/* 28 */     return (getChunk($$0, $$1, ChunkStatus.FULL, false) != null);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public abstract ChunkAccess getChunk(int paramInt1, int paramInt2, ChunkStatus paramChunkStatus, boolean paramBoolean);
/*    */   
/*    */   public abstract void tick(BooleanSupplier paramBooleanSupplier, boolean paramBoolean);
/*    */   
/*    */   public abstract String gatherStats();
/*    */   
/*    */   public abstract int getLoadedChunksCount();
/*    */   
/*    */   public void close() throws IOException {}
/*    */   
/*    */   public abstract LevelLightEngine getLightEngine();
/*    */   
/*    */   public void setSpawnSettings(boolean $$0, boolean $$1) {}
/*    */   
/*    */   public void updateChunkForced(ChunkPos $$0, boolean $$1) {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\ChunkSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */