package net.minecraft.world.item.crafting;

@FunctionalInterface
public interface Factory<T extends CraftingRecipe> {
  T create(CraftingBookCategory paramCraftingBookCategory);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\SimpleCraftingRecipeSerializer$Factory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */