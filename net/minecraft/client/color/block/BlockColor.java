package net.minecraft.client.color.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockColor {
  int getColor(BlockState paramBlockState, @Nullable BlockAndTintGetter paramBlockAndTintGetter, @Nullable BlockPos paramBlockPos, int paramInt);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\color\block\BlockColor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */