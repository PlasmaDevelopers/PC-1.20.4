package net.minecraft.world.level.block.state;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

public interface StateArgumentPredicate<A> {
  boolean test(BlockState paramBlockState, BlockGetter paramBlockGetter, BlockPos paramBlockPos, A paramA);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\BlockBehaviour$StateArgumentPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */