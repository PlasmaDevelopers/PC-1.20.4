/*    */ package net.minecraft.data.recipes;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
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
/*    */ import net.minecraft.world.item.crafting.SingleItemRecipe;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ 
/*    */ public class SingleItemRecipeBuilder
/*    */   implements RecipeBuilder {
/*    */   private final RecipeCategory category;
/*    */   private final Item result;
/*    */   private final Ingredient ingredient;
/*    */   private final int count;
/* 26 */   private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
/*    */   @Nullable
/*    */   private String group;
/*    */   private final SingleItemRecipe.Factory<?> factory;
/*    */   
/*    */   public SingleItemRecipeBuilder(RecipeCategory $$0, SingleItemRecipe.Factory<?> $$1, Ingredient $$2, ItemLike $$3, int $$4) {
/* 32 */     this.category = $$0;
/* 33 */     this.factory = $$1;
/* 34 */     this.result = $$3.asItem();
/* 35 */     this.ingredient = $$2;
/* 36 */     this.count = $$4;
/*    */   }
/*    */   
/*    */   public static SingleItemRecipeBuilder stonecutting(Ingredient $$0, RecipeCategory $$1, ItemLike $$2) {
/* 40 */     return new SingleItemRecipeBuilder($$1, net.minecraft.world.item.crafting.StonecutterRecipe::new, $$0, $$2, 1);
/*    */   }
/*    */   
/*    */   public static SingleItemRecipeBuilder stonecutting(Ingredient $$0, RecipeCategory $$1, ItemLike $$2, int $$3) {
/* 44 */     return new SingleItemRecipeBuilder($$1, net.minecraft.world.item.crafting.StonecutterRecipe::new, $$0, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public SingleItemRecipeBuilder unlockedBy(String $$0, Criterion<?> $$1) {
/* 49 */     this.criteria.put($$0, $$1);
/* 50 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public SingleItemRecipeBuilder group(@Nullable String $$0) {
/* 55 */     this.group = $$0;
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getResult() {
/* 61 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public void save(RecipeOutput $$0, ResourceLocation $$1) {
/* 66 */     ensureValid($$1);
/*    */ 
/*    */ 
/*    */     
/* 70 */     Advancement.Builder $$2 = $$0.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked($$1)).rewards(AdvancementRewards.Builder.recipe($$1)).requirements(AdvancementRequirements.Strategy.OR);
/* 71 */     Objects.requireNonNull($$2); this.criteria.forEach($$2::addCriterion);
/* 72 */     SingleItemRecipe $$3 = this.factory.create(
/* 73 */         Objects.<String>requireNonNullElse(this.group, ""), this.ingredient, new ItemStack((ItemLike)this.result, this.count));
/*    */ 
/*    */ 
/*    */     
/* 77 */     $$0.accept($$1, (Recipe<?>)$$3, $$2.build($$1.withPrefix("recipes/" + this.category.getFolderName() + "/")));
/*    */   }
/*    */   
/*    */   private void ensureValid(ResourceLocation $$0) {
/* 81 */     if (this.criteria.isEmpty())
/* 82 */       throw new IllegalStateException("No way of obtaining recipe " + $$0); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\SingleItemRecipeBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */