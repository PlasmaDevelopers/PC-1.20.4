/*    */ package net.minecraft.world.item.crafting;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ 
/*    */ public class StonecutterRecipe extends SingleItemRecipe {
/*    */   public StonecutterRecipe(String $$0, Ingredient $$1, ItemStack $$2) {
/* 10 */     super(RecipeType.STONECUTTING, RecipeSerializer.STONECUTTER, $$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(Container $$0, Level $$1) {
/* 15 */     return this.ingredient.test($$0.getItem(0));
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getToastSymbol() {
/* 20 */     return new ItemStack((ItemLike)Blocks.STONECUTTER);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\StonecutterRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */