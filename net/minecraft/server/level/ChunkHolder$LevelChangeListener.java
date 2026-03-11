package net.minecraft.server.level;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import net.minecraft.world.level.ChunkPos;

@FunctionalInterface
public interface LevelChangeListener {
  void onLevelChange(ChunkPos paramChunkPos, IntSupplier paramIntSupplier, int paramInt, IntConsumer paramIntConsumer);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ChunkHolder$LevelChangeListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */