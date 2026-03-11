package net.minecraft.world.level.entity;

import net.minecraft.server.level.FullChunkStatus;
import net.minecraft.world.level.ChunkPos;

@FunctionalInterface
public interface ChunkStatusUpdateListener {
  void onChunkStatusChange(ChunkPos paramChunkPos, FullChunkStatus paramFullChunkStatus);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\ChunkStatusUpdateListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */