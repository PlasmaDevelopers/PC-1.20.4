package net.minecraft.world.level.chunk.storage;

import java.io.IOException;

interface CommitOp {
  void run() throws IOException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\storage\RegionFile$CommitOp.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */