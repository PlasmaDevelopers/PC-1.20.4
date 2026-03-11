package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface BlockEntityTicker<T extends BlockEntity> {
  void tick(Level paramLevel, BlockPos paramBlockPos, BlockState paramBlockState, T paramT);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BlockEntityTicker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */