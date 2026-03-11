/*    */ package net.minecraft.world.item.crafting;
/*    */ 
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ 
/*    */ public interface SmithingRecipe
/*    */   extends Recipe<Container> {
/*    */   default RecipeType<?> getType() {
/* 11 */     return RecipeType.SMITHING;
/*    */   }
/*    */ 
/*    */   
/*    */   default boolean canCraftInDimensions(int $$0, int $$1) {
/* 16 */     return ($$0 >= 3 && $$1 >= 1);
/*    */   }
/*    */ 
/*    */   
/*    */   default ItemStack getToastSymbol() {
/* 21 */     return new ItemStack((ItemLike)Blocks.SMITHING_TABLE);
/*    */   }
/*    */   
/*    */   boolean isTemplateIngredient(ItemStack paramItemStack);
/*    */   
/*    */   boolean isBaseIngredient(ItemStack paramItemStack);
/*    */   
/*    */   boolean isAdditionIngredient(ItemStack paramItemStack);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\SmithingRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */