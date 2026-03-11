/*    */ package net.minecraft.world.item.crafting;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ 
/*    */ public class SmeltingRecipe extends AbstractCookingRecipe {
/*    */   public SmeltingRecipe(String $$0, CookingBookCategory $$1, Ingredient $$2, ItemStack $$3, float $$4, int $$5) {
/*  8 */     super(RecipeType.SMELTING, $$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getToastSymbol() {
/* 13 */     return new ItemStack((ItemLike)Blocks.FURNACE);
/*    */   }
/*    */ 
/*    */   
/*    */   public RecipeSerializer<?> getSerializer() {
/* 18 */     return RecipeSerializer.SMELTING_RECIPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\SmeltingRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */