package net.minecraft.world.level.biome;

import java.util.function.Function;
import net.minecraft.resources.ResourceKey;

@FunctionalInterface
interface SourceProvider {
  <T> Climate.ParameterList<T> apply(Function<ResourceKey<Biome>, T> paramFunction);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\MultiNoiseBiomeSourceParameterList$Preset$SourceProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */