package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
interface BlockEntitySupplier<T extends BlockEntity> {
  T create(BlockPos paramBlockPos, BlockState paramBlockState);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BlockEntityType$BlockEntitySupplier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */