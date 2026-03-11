package net.minecraft.world.item.crafting;

import java.util.Optional;
import net.minecraft.world.level.Level;

public interface CachedCheck<C extends net.minecraft.world.Container, T extends Recipe<C>> {
  Optional<RecipeHolder<T>> getRecipeFor(C paramC, Level paramLevel);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\RecipeManager$CachedCheck.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */