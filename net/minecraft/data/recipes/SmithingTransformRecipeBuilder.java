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
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.crafting.Ingredient;
/*    */ import net.minecraft.world.item.crafting.Recipe;
/*    */ import net.minecraft.world.item.crafting.SmithingTransformRecipe;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ 
/*    */ public class SmithingTransformRecipeBuilder
/*    */ {
/*    */   private final Ingredient template;
/*    */   private final Ingredient base;
/* 23 */   private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>(); private final Ingredient addition; private final RecipeCategory category; private final Item result;
/*    */   
/*    */   public SmithingTransformRecipeBuilder(Ingredient $$0, Ingredient $$1, Ingredient $$2, RecipeCategory $$3, Item $$4) {
/* 26 */     this.category = $$3;
/* 27 */     this.template = $$0;
/* 28 */     this.base = $$1;
/* 29 */     this.addition = $$2;
/* 30 */     this.result = $$4;
/*    */   }
/*    */   
/*    */   public static SmithingTransformRecipeBuilder smithing(Ingredient $$0, Ingredient $$1, Ingredient $$2, RecipeCategory $$3, Item $$4) {
/* 34 */     return new SmithingTransformRecipeBuilder($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */   
/*    */   public SmithingTransformRecipeBuilder unlocks(String $$0, Criterion<?> $$1) {
/* 38 */     this.criteria.put($$0, $$1);
/* 39 */     return this;
/*    */   }
/*    */   
/*    */   public void save(RecipeOutput $$0, String $$1) {
/* 43 */     save($$0, new ResourceLocation($$1));
/*    */   }
/*    */   
/*    */   public void save(RecipeOutput $$0, ResourceLocation $$1) {
/* 47 */     ensureValid($$1);
/*    */ 
/*    */ 
/*    */     
/* 51 */     Advancement.Builder $$2 = $$0.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked($$1)).rewards(AdvancementRewards.Builder.recipe($$1)).requirements(AdvancementRequirements.Strategy.OR);
/* 52 */     Objects.requireNonNull($$2); this.criteria.forEach($$2::addCriterion);
/* 53 */     SmithingTransformRecipe $$3 = new SmithingTransformRecipe(this.template, this.base, this.addition, new ItemStack((ItemLike)this.result));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 59 */     $$0.accept($$1, (Recipe<?>)$$3, $$2.build($$1.withPrefix("recipes/" + this.category.getFolderName() + "/")));
/*    */   }
/*    */   
/*    */   private void ensureValid(ResourceLocation $$0) {
/* 63 */     if (this.criteria.isEmpty())
/* 64 */       throw new IllegalStateException("No way of obtaining recipe " + $$0); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\SmithingTransformRecipeBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */