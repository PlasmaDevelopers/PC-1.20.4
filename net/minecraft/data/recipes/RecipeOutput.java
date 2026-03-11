package net.minecraft.data.recipes;

import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public interface RecipeOutput {
  void accept(ResourceLocation paramResourceLocation, Recipe<?> paramRecipe, @Nullable AdvancementHolder paramAdvancementHolder);
  
  Advancement.Builder advancement();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\RecipeOutput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */