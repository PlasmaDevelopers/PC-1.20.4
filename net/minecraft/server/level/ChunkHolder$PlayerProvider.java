package net.minecraft.server.level;

import java.util.List;
import net.minecraft.world.level.ChunkPos;

public interface PlayerProvider {
  List<ServerPlayer> getPlayers(ChunkPos paramChunkPos, boolean paramBoolean);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ChunkHolder$PlayerProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */