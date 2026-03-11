/*     */ package net.minecraft.data.recipes;
/*     */ 
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementRequirements;
/*     */ import net.minecraft.advancements.AdvancementRewards;
/*     */ import net.minecraft.advancements.Criterion;
/*     */ import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.item.crafting.Recipe;
/*     */ import net.minecraft.world.item.crafting.ShapelessRecipe;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ 
/*     */ public class ShapelessRecipeBuilder implements RecipeBuilder {
/*     */   private final RecipeCategory category;
/*     */   private final Item result;
/*     */   private final int count;
/*  26 */   private final NonNullList<Ingredient> ingredients = NonNullList.create();
/*  27 */   private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
/*     */   @Nullable
/*     */   private String group;
/*     */   
/*     */   public ShapelessRecipeBuilder(RecipeCategory $$0, ItemLike $$1, int $$2) {
/*  32 */     this.category = $$0;
/*  33 */     this.result = $$1.asItem();
/*  34 */     this.count = $$2;
/*     */   }
/*     */   
/*     */   public static ShapelessRecipeBuilder shapeless(RecipeCategory $$0, ItemLike $$1) {
/*  38 */     return new ShapelessRecipeBuilder($$0, $$1, 1);
/*     */   }
/*     */   
/*     */   public static ShapelessRecipeBuilder shapeless(RecipeCategory $$0, ItemLike $$1, int $$2) {
/*  42 */     return new ShapelessRecipeBuilder($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public ShapelessRecipeBuilder requires(TagKey<Item> $$0) {
/*  46 */     return requires(Ingredient.of($$0));
/*     */   }
/*     */   
/*     */   public ShapelessRecipeBuilder requires(ItemLike $$0) {
/*  50 */     return requires($$0, 1);
/*     */   }
/*     */   
/*     */   public ShapelessRecipeBuilder requires(ItemLike $$0, int $$1) {
/*  54 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/*  55 */       requires(Ingredient.of(new ItemLike[] { $$0 }));
/*     */     } 
/*  57 */     return this;
/*     */   }
/*     */   
/*     */   public ShapelessRecipeBuilder requires(Ingredient $$0) {
/*  61 */     return requires($$0, 1);
/*     */   }
/*     */   
/*     */   public ShapelessRecipeBuilder requires(Ingredient $$0, int $$1) {
/*  65 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/*  66 */       this.ingredients.add($$0);
/*     */     }
/*  68 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ShapelessRecipeBuilder unlockedBy(String $$0, Criterion<?> $$1) {
/*  73 */     this.criteria.put($$0, $$1);
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ShapelessRecipeBuilder group(@Nullable String $$0) {
/*  79 */     this.group = $$0;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getResult() {
/*  85 */     return this.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void save(RecipeOutput $$0, ResourceLocation $$1) {
/*  90 */     ensureValid($$1);
/*     */ 
/*     */ 
/*     */     
/*  94 */     Advancement.Builder $$2 = $$0.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked($$1)).rewards(AdvancementRewards.Builder.recipe($$1)).requirements(AdvancementRequirements.Strategy.OR);
/*  95 */     Objects.requireNonNull($$2); this.criteria.forEach($$2::addCriterion);
/*     */ 
/*     */     
/*  98 */     ShapelessRecipe $$3 = new ShapelessRecipe(Objects.<String>requireNonNullElse(this.group, ""), RecipeBuilder.determineBookCategory(this.category), new ItemStack((ItemLike)this.result, this.count), this.ingredients);
/*     */ 
/*     */ 
/*     */     
/* 102 */     $$0.accept($$1, (Recipe<?>)$$3, $$2.build($$1.withPrefix("recipes/" + this.category.getFolderName() + "/")));
/*     */   }
/*     */   
/*     */   private void ensureValid(ResourceLocation $$0) {
/* 106 */     if (this.criteria.isEmpty())
/* 107 */       throw new IllegalStateException("No way of obtaining recipe " + $$0); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\ShapelessRecipeBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */