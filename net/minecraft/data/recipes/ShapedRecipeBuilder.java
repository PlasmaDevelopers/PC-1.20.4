/*     */ package net.minecraft.data.recipes;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementRequirements;
/*     */ import net.minecraft.advancements.AdvancementRewards;
/*     */ import net.minecraft.advancements.Criterion;
/*     */ import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.item.crafting.Recipe;
/*     */ import net.minecraft.world.item.crafting.ShapedRecipe;
/*     */ import net.minecraft.world.item.crafting.ShapedRecipePattern;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ 
/*     */ public class ShapedRecipeBuilder implements RecipeBuilder {
/*     */   private final RecipeCategory category;
/*     */   private final Item result;
/*     */   private final int count;
/*  29 */   private final List<String> rows = Lists.newArrayList();
/*  30 */   private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
/*  31 */   private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
/*     */   @Nullable
/*     */   private String group;
/*     */   private boolean showNotification = true;
/*     */   
/*     */   public ShapedRecipeBuilder(RecipeCategory $$0, ItemLike $$1, int $$2) {
/*  37 */     this.category = $$0;
/*  38 */     this.result = $$1.asItem();
/*  39 */     this.count = $$2;
/*     */   }
/*     */   
/*     */   public static ShapedRecipeBuilder shaped(RecipeCategory $$0, ItemLike $$1) {
/*  43 */     return shaped($$0, $$1, 1);
/*     */   }
/*     */   
/*     */   public static ShapedRecipeBuilder shaped(RecipeCategory $$0, ItemLike $$1, int $$2) {
/*  47 */     return new ShapedRecipeBuilder($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public ShapedRecipeBuilder define(Character $$0, TagKey<Item> $$1) {
/*  51 */     return define($$0, Ingredient.of($$1));
/*     */   }
/*     */   
/*     */   public ShapedRecipeBuilder define(Character $$0, ItemLike $$1) {
/*  55 */     return define($$0, Ingredient.of(new ItemLike[] { $$1 }));
/*     */   }
/*     */   
/*     */   public ShapedRecipeBuilder define(Character $$0, Ingredient $$1) {
/*  59 */     if (this.key.containsKey($$0)) {
/*  60 */       throw new IllegalArgumentException("Symbol '" + $$0 + "' is already defined!");
/*     */     }
/*  62 */     if ($$0.charValue() == ' ') {
/*  63 */       throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
/*     */     }
/*  65 */     this.key.put($$0, $$1);
/*  66 */     return this;
/*     */   }
/*     */   
/*     */   public ShapedRecipeBuilder pattern(String $$0) {
/*  70 */     if (!this.rows.isEmpty() && $$0.length() != ((String)this.rows.get(0)).length()) {
/*  71 */       throw new IllegalArgumentException("Pattern must be the same width on every line!");
/*     */     }
/*  73 */     this.rows.add($$0);
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ShapedRecipeBuilder unlockedBy(String $$0, Criterion<?> $$1) {
/*  79 */     this.criteria.put($$0, $$1);
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ShapedRecipeBuilder group(@Nullable String $$0) {
/*  85 */     this.group = $$0;
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   public ShapedRecipeBuilder showNotification(boolean $$0) {
/*  90 */     this.showNotification = $$0;
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getResult() {
/*  96 */     return this.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void save(RecipeOutput $$0, ResourceLocation $$1) {
/* 101 */     ShapedRecipePattern $$2 = ensureValid($$1);
/*     */ 
/*     */ 
/*     */     
/* 105 */     Advancement.Builder $$3 = $$0.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked($$1)).rewards(AdvancementRewards.Builder.recipe($$1)).requirements(AdvancementRequirements.Strategy.OR);
/* 106 */     Objects.requireNonNull($$3); this.criteria.forEach($$3::addCriterion);
/*     */ 
/*     */     
/* 109 */     ShapedRecipe $$4 = new ShapedRecipe(Objects.<String>requireNonNullElse(this.group, ""), RecipeBuilder.determineBookCategory(this.category), $$2, new ItemStack((ItemLike)this.result, this.count), this.showNotification);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     $$0.accept($$1, (Recipe<?>)$$4, $$3.build($$1.withPrefix("recipes/" + this.category.getFolderName() + "/")));
/*     */   }
/*     */   
/*     */   private ShapedRecipePattern ensureValid(ResourceLocation $$0) {
/* 118 */     if (this.criteria.isEmpty()) {
/* 119 */       throw new IllegalStateException("No way of obtaining recipe " + $$0);
/*     */     }
/* 121 */     return ShapedRecipePattern.of(this.key, this.rows);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\ShapedRecipeBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */