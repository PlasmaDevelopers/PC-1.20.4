package net.minecraft.world.level.block.state;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.properties.Property;

public interface Factory<O, S> {
  S create(O paramO, ImmutableMap<Property<?>, Comparable<?>> paramImmutableMap, MapCodec<S> paramMapCodec);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\StateDefinition$Factory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */