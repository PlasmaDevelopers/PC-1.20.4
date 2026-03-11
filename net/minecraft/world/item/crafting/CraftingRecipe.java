/*   */ package net.minecraft.world.item.crafting;
/*   */ 
/*   */ import net.minecraft.world.inventory.CraftingContainer;
/*   */ 
/*   */ public interface CraftingRecipe
/*   */   extends Recipe<CraftingContainer> {
/*   */   default RecipeType<?> getType() {
/* 8 */     return RecipeType.CRAFTING;
/*   */   }
/*   */   
/*   */   CraftingBookCategory category();
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\CraftingRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */