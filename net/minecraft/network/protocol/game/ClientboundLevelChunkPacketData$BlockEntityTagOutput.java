package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;

@FunctionalInterface
public interface BlockEntityTagOutput {
  void accept(BlockPos paramBlockPos, BlockEntityType<?> paramBlockEntityType, @Nullable CompoundTag paramCompoundTag);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundLevelChunkPacketData$BlockEntityTagOutput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */