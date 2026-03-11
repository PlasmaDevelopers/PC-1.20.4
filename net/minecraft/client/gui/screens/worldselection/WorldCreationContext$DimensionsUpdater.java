package net.minecraft.client.gui.screens.worldselection;

import java.util.function.BiFunction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.levelgen.WorldDimensions;

@FunctionalInterface
public interface DimensionsUpdater extends BiFunction<RegistryAccess.Frozen, WorldDimensions, WorldDimensions> {}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\WorldCreationContext$DimensionsUpdater.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */