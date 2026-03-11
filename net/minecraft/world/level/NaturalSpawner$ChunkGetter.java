package net.minecraft.world.level;

import java.util.function.Consumer;
import net.minecraft.world.level.chunk.LevelChunk;

@FunctionalInterface
public interface ChunkGetter {
  void query(long paramLong, Consumer<LevelChunk> paramConsumer);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\NaturalSpawner$ChunkGetter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */