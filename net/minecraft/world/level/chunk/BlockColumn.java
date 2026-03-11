package net.minecraft.world.level.chunk;

import net.minecraft.world.level.block.state.BlockState;

public interface BlockColumn {
  BlockState getBlock(int paramInt);
  
  void setBlock(int paramInt, BlockState paramBlockState);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\BlockColumn.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */