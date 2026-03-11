package net.minecraft.server.level.progress;

import javax.annotation.Nullable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;

public interface ChunkProgressListener {
  void updateSpawnPos(ChunkPos paramChunkPos);
  
  void onStatusChange(ChunkPos paramChunkPos, @Nullable ChunkStatus paramChunkStatus);
  
  void start();
  
  void stop();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\progress\ChunkProgressListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */