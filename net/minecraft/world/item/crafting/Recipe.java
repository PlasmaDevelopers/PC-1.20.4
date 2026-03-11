/*    */ package net.minecraft.world.item.crafting;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ 
/*    */ public interface Recipe<C extends net.minecraft.world.Container> {
/* 14 */   public static final Codec<Recipe<?>> CODEC = BuiltInRegistries.RECIPE_SERIALIZER.byNameCodec().dispatch(Recipe::getSerializer, RecipeSerializer::codec);
/*    */ 
/*    */   
/*    */   boolean matches(C paramC, Level paramLevel);
/*    */   
/*    */   ItemStack assemble(C paramC, RegistryAccess paramRegistryAccess);
/*    */   
/*    */   boolean canCraftInDimensions(int paramInt1, int paramInt2);
/*    */   
/*    */   ItemStack getResultItem(RegistryAccess paramRegistryAccess);
/*    */   
/*    */   default NonNullList<ItemStack> getRemainingItems(C $$0) {
/* 26 */     NonNullList<ItemStack> $$1 = NonNullList.withSize($$0.getContainerSize(), ItemStack.EMPTY);
/*    */     
/* 28 */     for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/* 29 */       Item $$3 = $$0.getItem($$2).getItem();
/* 30 */       if ($$3.hasCraftingRemainingItem()) {
/* 31 */         $$1.set($$2, new ItemStack((ItemLike)$$3.getCraftingRemainingItem()));
/*    */       }
/*    */     } 
/*    */     
/* 35 */     return $$1;
/*    */   }
/*    */   
/*    */   default NonNullList<Ingredient> getIngredients() {
/* 39 */     return NonNullList.create();
/*    */   }
/*    */   
/*    */   default boolean isSpecial() {
/* 43 */     return false;
/*    */   }
/*    */   
/*    */   default boolean showNotification() {
/* 47 */     return true;
/*    */   }
/*    */   
/*    */   default String getGroup() {
/* 51 */     return "";
/*    */   }
/*    */   
/*    */   default ItemStack getToastSymbol() {
/* 55 */     return new ItemStack((ItemLike)Blocks.CRAFTING_TABLE);
/*    */   }
/*    */   
/*    */   RecipeSerializer<?> getSerializer();
/*    */   
/*    */   RecipeType<?> getType();
/*    */   
/*    */   default boolean isIncomplete() {
/* 63 */     NonNullList<Ingredient> $$0 = getIngredients();
/* 64 */     return ($$0.isEmpty() || $$0.stream().anyMatch($$0 -> (($$0.getItems()).length == 0)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\Recipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */