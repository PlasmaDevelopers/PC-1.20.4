package net.minecraft.data.structures;

import net.minecraft.nbt.CompoundTag;

@FunctionalInterface
public interface Filter {
  CompoundTag apply(String paramString, CompoundTag paramCompoundTag);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\structures\SnbtToNbt$Filter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */