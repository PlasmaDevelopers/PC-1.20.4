package net.minecraft.world.item.crafting;

import net.minecraft.world.item.ItemStack;

public interface Factory<T extends AbstractCookingRecipe> {
  T create(String paramString, CookingBookCategory paramCookingBookCategory, Ingredient paramIngredient, ItemStack paramItemStack, float paramFloat, int paramInt);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\AbstractCookingRecipe$Factory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */