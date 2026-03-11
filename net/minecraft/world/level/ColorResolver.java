package net.minecraft.world.level;

import net.minecraft.world.level.biome.Biome;

@FunctionalInterface
public interface ColorResolver {
  int getColor(Biome paramBiome, double paramDouble1, double paramDouble2);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\ColorResolver.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */