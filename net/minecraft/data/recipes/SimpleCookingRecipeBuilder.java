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
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.AbstractCookingRecipe;
/*     */ import net.minecraft.world.item.crafting.CookingBookCategory;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.item.crafting.Recipe;
/*     */ import net.minecraft.world.item.crafting.RecipeSerializer;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleCookingRecipeBuilder
/*     */   implements RecipeBuilder
/*     */ {
/*     */   private final RecipeCategory category;
/*     */   private final CookingBookCategory bookCategory;
/*     */   private final Item result;
/*     */   private final Ingredient ingredient;
/*     */   private final float experience;
/*     */   private final int cookingTime;
/*  34 */   private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
/*     */   @Nullable
/*     */   private String group;
/*     */   private final AbstractCookingRecipe.Factory<?> factory;
/*     */   
/*     */   private SimpleCookingRecipeBuilder(RecipeCategory $$0, CookingBookCategory $$1, ItemLike $$2, Ingredient $$3, float $$4, int $$5, AbstractCookingRecipe.Factory<?> $$6) {
/*  40 */     this.category = $$0;
/*  41 */     this.bookCategory = $$1;
/*  42 */     this.result = $$2.asItem();
/*  43 */     this.ingredient = $$3;
/*  44 */     this.experience = $$4;
/*  45 */     this.cookingTime = $$5;
/*  46 */     this.factory = $$6;
/*     */   }
/*     */   
/*     */   public static <T extends AbstractCookingRecipe> SimpleCookingRecipeBuilder generic(Ingredient $$0, RecipeCategory $$1, ItemLike $$2, float $$3, int $$4, RecipeSerializer<T> $$5, AbstractCookingRecipe.Factory<T> $$6) {
/*  50 */     return new SimpleCookingRecipeBuilder($$1, determineRecipeCategory($$5, $$2), $$2, $$0, $$3, $$4, $$6);
/*     */   }
/*     */   
/*     */   public static SimpleCookingRecipeBuilder campfireCooking(Ingredient $$0, RecipeCategory $$1, ItemLike $$2, float $$3, int $$4) {
/*  54 */     return new SimpleCookingRecipeBuilder($$1, CookingBookCategory.FOOD, $$2, $$0, $$3, $$4, net.minecraft.world.item.crafting.CampfireCookingRecipe::new);
/*     */   }
/*     */   
/*     */   public static SimpleCookingRecipeBuilder blasting(Ingredient $$0, RecipeCategory $$1, ItemLike $$2, float $$3, int $$4) {
/*  58 */     return new SimpleCookingRecipeBuilder($$1, determineBlastingRecipeCategory($$2), $$2, $$0, $$3, $$4, net.minecraft.world.item.crafting.BlastingRecipe::new);
/*     */   }
/*     */   
/*     */   public static SimpleCookingRecipeBuilder smelting(Ingredient $$0, RecipeCategory $$1, ItemLike $$2, float $$3, int $$4) {
/*  62 */     return new SimpleCookingRecipeBuilder($$1, determineSmeltingRecipeCategory($$2), $$2, $$0, $$3, $$4, net.minecraft.world.item.crafting.SmeltingRecipe::new);
/*     */   }
/*     */   
/*     */   public static SimpleCookingRecipeBuilder smoking(Ingredient $$0, RecipeCategory $$1, ItemLike $$2, float $$3, int $$4) {
/*  66 */     return new SimpleCookingRecipeBuilder($$1, CookingBookCategory.FOOD, $$2, $$0, $$3, $$4, net.minecraft.world.item.crafting.SmokingRecipe::new);
/*     */   }
/*     */ 
/*     */   
/*     */   public SimpleCookingRecipeBuilder unlockedBy(String $$0, Criterion<?> $$1) {
/*  71 */     this.criteria.put($$0, $$1);
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SimpleCookingRecipeBuilder group(@Nullable String $$0) {
/*  77 */     this.group = $$0;
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getResult() {
/*  83 */     return this.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void save(RecipeOutput $$0, ResourceLocation $$1) {
/*  88 */     ensureValid($$1);
/*     */ 
/*     */ 
/*     */     
/*  92 */     Advancement.Builder $$2 = $$0.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked($$1)).rewards(AdvancementRewards.Builder.recipe($$1)).requirements(AdvancementRequirements.Strategy.OR);
/*  93 */     Objects.requireNonNull($$2); this.criteria.forEach($$2::addCriterion);
/*  94 */     AbstractCookingRecipe $$3 = this.factory.create(
/*  95 */         Objects.<String>requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, new ItemStack((ItemLike)this.result), this.experience, this.cookingTime);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     $$0.accept($$1, (Recipe<?>)$$3, $$2.build($$1.withPrefix("recipes/" + this.category.getFolderName() + "/")));
/*     */   }
/*     */   
/*     */   private static CookingBookCategory determineSmeltingRecipeCategory(ItemLike $$0) {
/* 106 */     if ($$0.asItem().isEdible()) {
/* 107 */       return CookingBookCategory.FOOD;
/*     */     }
/* 109 */     if ($$0.asItem() instanceof net.minecraft.world.item.BlockItem) {
/* 110 */       return CookingBookCategory.BLOCKS;
/*     */     }
/* 112 */     return CookingBookCategory.MISC;
/*     */   }
/*     */   
/*     */   private static CookingBookCategory determineBlastingRecipeCategory(ItemLike $$0) {
/* 116 */     if ($$0.asItem() instanceof net.minecraft.world.item.BlockItem) {
/* 117 */       return CookingBookCategory.BLOCKS;
/*     */     }
/* 119 */     return CookingBookCategory.MISC;
/*     */   }
/*     */   
/*     */   private static CookingBookCategory determineRecipeCategory(RecipeSerializer<? extends AbstractCookingRecipe> $$0, ItemLike $$1) {
/* 123 */     if ($$0 == RecipeSerializer.SMELTING_RECIPE) {
/* 124 */       return determineSmeltingRecipeCategory($$1);
/*     */     }
/* 126 */     if ($$0 == RecipeSerializer.BLASTING_RECIPE) {
/* 127 */       return determineBlastingRecipeCategory($$1);
/*     */     }
/* 129 */     if ($$0 == RecipeSerializer.SMOKING_RECIPE || $$0 == RecipeSerializer.CAMPFIRE_COOKING_RECIPE) {
/* 130 */       return CookingBookCategory.FOOD;
/*     */     }
/* 132 */     throw new IllegalStateException("Unknown cooking recipe type");
/*     */   }
/*     */   
/*     */   private void ensureValid(ResourceLocation $$0) {
/* 136 */     if (this.criteria.isEmpty())
/* 137 */       throw new IllegalStateException("No way of obtaining recipe " + $$0); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\SimpleCookingRecipeBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */