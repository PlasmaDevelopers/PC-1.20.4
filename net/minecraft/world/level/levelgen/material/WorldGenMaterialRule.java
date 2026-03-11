package net.minecraft.world.level.levelgen.material;

import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseChunk;

public interface WorldGenMaterialRule {
  @Nullable
  BlockState apply(NoiseChunk paramNoiseChunk, int paramInt1, int paramInt2, int paramInt3);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\material\WorldGenMaterialRule.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */