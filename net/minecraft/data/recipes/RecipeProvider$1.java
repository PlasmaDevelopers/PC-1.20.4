/*    */ package net.minecraft.data.recipes;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.advancements.Advancement;
/*    */ import net.minecraft.advancements.AdvancementHolder;
/*    */ import net.minecraft.data.CachedOutput;
/*    */ import net.minecraft.data.DataProvider;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.crafting.Recipe;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements RecipeOutput
/*    */ {
/*    */   public void accept(ResourceLocation $$0, Recipe<?> $$1, @Nullable AdvancementHolder $$2) {
/* 73 */     if (!allRecipes.add($$0)) {
/* 74 */       throw new IllegalStateException("Duplicate recipe " + $$0);
/*    */     }
/* 76 */     tasks.add(DataProvider.saveStable(cache, Recipe.CODEC, $$1, RecipeProvider.this.recipePathProvider.json($$0)));
/* 77 */     if ($$2 != null) {
/* 78 */       tasks.add(DataProvider.saveStable(cache, Advancement.CODEC, $$2.value(), RecipeProvider.this.advancementPathProvider.json($$2.id())));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Advancement.Builder advancement() {
/* 84 */     return Advancement.Builder.recipeAdvancement().parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\RecipeProvider$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */