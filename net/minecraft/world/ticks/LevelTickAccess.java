package net.minecraft.world.ticks;

import net.minecraft.core.BlockPos;

public interface LevelTickAccess<T> extends TickAccess<T> {
  boolean willTickThisTick(BlockPos paramBlockPos, T paramT);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\LevelTickAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */