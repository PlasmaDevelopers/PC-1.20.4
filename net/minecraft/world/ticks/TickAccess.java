package net.minecraft.world.ticks;

import net.minecraft.core.BlockPos;

public interface TickAccess<T> {
  void schedule(ScheduledTick<T> paramScheduledTick);
  
  boolean hasScheduledTick(BlockPos paramBlockPos, T paramT);
  
  int count();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\TickAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */