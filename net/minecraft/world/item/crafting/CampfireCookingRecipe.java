/*    */ package net.minecraft.world.item.crafting;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ 
/*    */ public class CampfireCookingRecipe extends AbstractCookingRecipe {
/*    */   public CampfireCookingRecipe(String $$0, CookingBookCategory $$1, Ingredient $$2, ItemStack $$3, float $$4, int $$5) {
/*  8 */     super(RecipeType.CAMPFIRE_COOKING, $$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getToastSymbol() {
/* 13 */     return new ItemStack((ItemLike)Blocks.CAMPFIRE);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeSerializer<?> getSerializer() {
/* 18 */     return RecipeSerializer.CAMPFIRE_COOKING_RECIPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\CampfireCookingRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */