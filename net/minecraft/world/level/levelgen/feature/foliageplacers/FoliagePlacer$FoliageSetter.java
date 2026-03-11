package net.minecraft.world.level.levelgen.feature.foliageplacers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface FoliageSetter {
  void set(BlockPos paramBlockPos, BlockState paramBlockState);
  
  boolean isSet(BlockPos paramBlockPos);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\foliageplacers\FoliagePlacer$FoliageSetter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */