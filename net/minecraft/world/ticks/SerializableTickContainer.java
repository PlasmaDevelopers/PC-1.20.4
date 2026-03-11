package net.minecraft.world.ticks;

import java.util.function.Function;
import net.minecraft.nbt.Tag;

public interface SerializableTickContainer<T> {
  Tag save(long paramLong, Function<T, String> paramFunction);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\SerializableTickContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */