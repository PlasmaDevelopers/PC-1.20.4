package net.minecraft.server.commands;

import javax.annotation.Nullable;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public interface Filter {
  @Nullable
  BlockInput filter(BoundingBox paramBoundingBox, BlockPos paramBlockPos, BlockInput paramBlockInput, ServerLevel paramServerLevel);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SetBlockCommand$Filter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */