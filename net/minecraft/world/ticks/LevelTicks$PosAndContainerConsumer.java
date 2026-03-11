package net.minecraft.world.ticks;

@FunctionalInterface
interface PosAndContainerConsumer<T> {
  void accept(long paramLong, LevelChunkTicks<T> paramLevelChunkTicks);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\LevelTicks$PosAndContainerConsumer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */