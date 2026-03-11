package net.minecraft.world.level;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.chunk.ChunkAccess;

@FunctionalInterface
public interface AfterSpawnCallback {
  void run(Mob paramMob, ChunkAccess paramChunkAccess);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\NaturalSpawner$AfterSpawnCallback.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */