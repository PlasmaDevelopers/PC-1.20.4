/*    */ package net.minecraft.data.recipes;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.crafting.CraftingBookCategory;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ 
/*    */ public interface RecipeBuilder
/*    */ {
/* 13 */   public static final ResourceLocation ROOT_RECIPE_ADVANCEMENT = new ResourceLocation("recipes/root");
/*    */   
/*    */   RecipeBuilder unlockedBy(String paramString, Criterion<?> paramCriterion);
/*    */   
/*    */   RecipeBuilder group(@Nullable String paramString);
/*    */   
/*    */   Item getResult();
/*    */   
/*    */   void save(RecipeOutput paramRecipeOutput, ResourceLocation paramResourceLocation);
/*    */   
/*    */   default void save(RecipeOutput $$0) {
/* 24 */     save($$0, getDefaultRecipeId((ItemLike)getResult()));
/*    */   }
/*    */   
/*    */   default void save(RecipeOutput $$0, String $$1) {
/* 28 */     ResourceLocation $$2 = getDefaultRecipeId((ItemLike)getResult());
/* 29 */     ResourceLocation $$3 = new ResourceLocation($$1);
/* 30 */     if ($$3.equals($$2)) {
/* 31 */       throw new IllegalStateException("Recipe " + $$1 + " should remove its 'save' argument as it is equal to default one");
/*    */     }
/* 33 */     save($$0, $$3);
/*    */   }
/*    */   
/*    */   static ResourceLocation getDefaultRecipeId(ItemLike $$0) {
/* 37 */     return BuiltInRegistries.ITEM.getKey($$0.asItem());
/*    */   }
/*    */   
/*    */   static CraftingBookCategory determineBookCategory(RecipeCategory $$0) {
/* 41 */     switch ($$0) { case BUILDING_BLOCKS: case TOOLS: case COMBAT: case REDSTONE:  }  return 
/*    */ 
/*    */ 
/*    */       
/* 45 */       CraftingBookCategory.MISC;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\RecipeBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */