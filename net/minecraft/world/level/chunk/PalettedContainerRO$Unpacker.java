package net.minecraft.world.level.chunk;

import com.mojang.serialization.DataResult;
import net.minecraft.core.IdMap;

public interface Unpacker<T, C extends PalettedContainerRO<T>> {
  DataResult<C> read(IdMap<T> paramIdMap, PalettedContainer.Strategy paramStrategy, PalettedContainerRO.PackedData<T> paramPackedData);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\PalettedContainerRO$Unpacker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */