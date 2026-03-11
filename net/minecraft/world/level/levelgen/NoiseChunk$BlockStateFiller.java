package net.minecraft.world.level.levelgen;

import javax.annotation.Nullable;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface BlockStateFiller {
  @Nullable
  BlockState calculate(DensityFunction.FunctionContext paramFunctionContext);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\NoiseChunk$BlockStateFiller.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */