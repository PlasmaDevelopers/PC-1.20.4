/*    */ package net.minecraft.data.recipes;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.advancements.Advancement;
/*    */ import net.minecraft.advancements.AdvancementRequirements;
/*    */ import net.minecraft.advancements.AdvancementRewards;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.crafting.Ingredient;
/*    */ import net.minecraft.world.item.crafting.Recipe;
/*    */ import net.minecraft.world.item.crafting.SmithingTrimRecipe;
/*    */ 
/*    */ public class SmithingTrimRecipeBuilder
/*    */ {
/*    */   private final RecipeCategory category;
/*    */   private final Ingredient template;
/* 20 */   private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>(); private final Ingredient base; private final Ingredient addition;
/*    */   
/*    */   public SmithingTrimRecipeBuilder(RecipeCategory $$0, Ingredient $$1, Ingredient $$2, Ingredient $$3) {
/* 23 */     this.category = $$0;
/* 24 */     this.template = $$1;
/* 25 */     this.base = $$2;
/* 26 */     this.addition = $$3;
/*    */   }
/*    */   
/*    */   public static SmithingTrimRecipeBuilder smithingTrim(Ingredient $$0, Ingredient $$1, Ingredient $$2, RecipeCategory $$3) {
/* 30 */     return new SmithingTrimRecipeBuilder($$3, $$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public SmithingTrimRecipeBuilder unlocks(String $$0, Criterion<?> $$1) {
/* 34 */     this.criteria.put($$0, $$1);
/* 35 */     return this;
/*    */   }
/*    */   
/*    */   public void save(RecipeOutput $$0, ResourceLocation $$1) {
/* 39 */     ensureValid($$1);
/*    */ 
/*    */ 
/*    */     
/* 43 */     Advancement.Builder $$2 = $$0.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked($$1)).rewards(AdvancementRewards.Builder.recipe($$1)).requirements(AdvancementRequirements.Strategy.OR);
/* 44 */     Objects.requireNonNull($$2); this.criteria.forEach($$2::addCriterion);
/* 45 */     SmithingTrimRecipe $$3 = new SmithingTrimRecipe(this.template, this.base, this.addition);
/* 46 */     $$0.accept($$1, (Recipe<?>)$$3, $$2.build($$1.withPrefix("recipes/" + this.category.getFolderName() + "/")));
/*    */   }
/*    */   
/*    */   private void ensureValid(ResourceLocation $$0) {
/* 50 */     if (this.criteria.isEmpty())
/* 51 */       throw new IllegalStateException("No way of obtaining recipe " + $$0); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\SmithingTrimRecipeBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */