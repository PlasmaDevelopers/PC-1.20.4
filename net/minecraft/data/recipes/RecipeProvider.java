/*     */ package net.minecraft.data.recipes;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementHolder;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.advancements.Criterion;
/*     */ import net.minecraft.advancements.CriterionTriggerInstance;
/*     */ import net.minecraft.advancements.critereon.EnterBlockTrigger;
/*     */ import net.minecraft.advancements.critereon.InventoryChangeTrigger;
/*     */ import net.minecraft.advancements.critereon.ItemPredicate;
/*     */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.data.BlockFamilies;
/*     */ import net.minecraft.data.BlockFamily;
/*     */ import net.minecraft.data.CachedOutput;
/*     */ import net.minecraft.data.DataProvider;
/*     */ import net.minecraft.data.PackOutput;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.item.HoneycombItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.AbstractCookingRecipe;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.item.crafting.Recipe;
/*     */ import net.minecraft.world.item.crafting.RecipeSerializer;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RecipeProvider
/*     */   implements DataProvider
/*     */ {
/*     */   final PackOutput.PathProvider recipePathProvider;
/*     */   final PackOutput.PathProvider advancementPathProvider;
/*     */   private static final Map<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>> SHAPE_BUILDERS;
/*     */   
/*     */   public RecipeProvider(PackOutput $$0) {
/*  62 */     this.recipePathProvider = $$0.createPathProvider(PackOutput.Target.DATA_PACK, "recipes");
/*  63 */     this.advancementPathProvider = $$0.createPathProvider(PackOutput.Target.DATA_PACK, "advancements");
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<?> run(final CachedOutput cache) {
/*  68 */     final Set<ResourceLocation> allRecipes = Sets.newHashSet();
/*  69 */     final List<CompletableFuture<?>> tasks = new ArrayList<>();
/*  70 */     buildRecipes(new RecipeOutput()
/*     */         {
/*     */           public void accept(ResourceLocation $$0, Recipe<?> $$1, @Nullable AdvancementHolder $$2) {
/*  73 */             if (!allRecipes.add($$0)) {
/*  74 */               throw new IllegalStateException("Duplicate recipe " + $$0);
/*     */             }
/*  76 */             tasks.add(DataProvider.saveStable(cache, Recipe.CODEC, $$1, RecipeProvider.this.recipePathProvider.json($$0)));
/*  77 */             if ($$2 != null) {
/*  78 */               tasks.add(DataProvider.saveStable(cache, Advancement.CODEC, $$2.value(), RecipeProvider.this.advancementPathProvider.json($$2.id())));
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public Advancement.Builder advancement() {
/*  84 */             return Advancement.Builder.recipeAdvancement().parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT);
/*     */           }
/*     */         });
/*  87 */     return CompletableFuture.allOf((CompletableFuture<?>[])$$2.toArray($$0 -> new CompletableFuture[$$0]));
/*     */   }
/*     */   
/*     */   protected CompletableFuture<?> buildAdvancement(CachedOutput $$0, AdvancementHolder $$1) {
/*  91 */     return DataProvider.saveStable($$0, Advancement.CODEC, $$1.value(), this.advancementPathProvider.json($$1.id()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void generateForEnabledBlockFamilies(RecipeOutput $$0, FeatureFlagSet $$1) {
/*  97 */     BlockFamilies.getAllFamilies()
/*  98 */       .filter(BlockFamily::shouldGenerateRecipe)
/*  99 */       .forEach($$2 -> generateRecipes($$0, $$2, $$1));
/*     */   }
/*     */   
/*     */   protected static void oneToOneConversionRecipe(RecipeOutput $$0, ItemLike $$1, ItemLike $$2, @Nullable String $$3) {
/* 103 */     oneToOneConversionRecipe($$0, $$1, $$2, $$3, 1);
/*     */   }
/*     */   
/*     */   protected static void oneToOneConversionRecipe(RecipeOutput $$0, ItemLike $$1, ItemLike $$2, @Nullable String $$3, int $$4) {
/* 107 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, $$1, $$4)
/* 108 */       .requires($$2)
/* 109 */       .group($$3)
/* 110 */       .unlockedBy(getHasName($$2), has($$2))
/* 111 */       .save($$0, getConversionRecipeName($$1, $$2));
/*     */   }
/*     */   
/*     */   protected static void oreSmelting(RecipeOutput $$0, List<ItemLike> $$1, RecipeCategory $$2, ItemLike $$3, float $$4, int $$5, String $$6) {
/* 115 */     oreCooking($$0, RecipeSerializer.SMELTING_RECIPE, net.minecraft.world.item.crafting.SmeltingRecipe::new, $$1, $$2, $$3, $$4, $$5, $$6, "_from_smelting");
/*     */   }
/*     */   
/*     */   protected static void oreBlasting(RecipeOutput $$0, List<ItemLike> $$1, RecipeCategory $$2, ItemLike $$3, float $$4, int $$5, String $$6) {
/* 119 */     oreCooking($$0, RecipeSerializer.BLASTING_RECIPE, net.minecraft.world.item.crafting.BlastingRecipe::new, $$1, $$2, $$3, $$4, $$5, $$6, "_from_blasting");
/*     */   }
/*     */   
/*     */   private static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput $$0, RecipeSerializer<T> $$1, AbstractCookingRecipe.Factory<T> $$2, List<ItemLike> $$3, RecipeCategory $$4, ItemLike $$5, float $$6, int $$7, String $$8, String $$9) {
/* 123 */     for (ItemLike $$10 : $$3) {
/* 124 */       SimpleCookingRecipeBuilder.<T>generic(Ingredient.of(new ItemLike[] { $$10 }, ), $$4, $$5, $$6, $$7, $$1, $$2)
/* 125 */         .group($$8)
/* 126 */         .unlockedBy(getHasName($$10), has($$10))
/* 127 */         .save($$0, getItemName($$5) + getItemName($$5) + "_" + $$9);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void netheriteSmithing(RecipeOutput $$0, Item $$1, RecipeCategory $$2, Item $$3) {
/* 132 */     SmithingTransformRecipeBuilder.smithing(Ingredient.of(new ItemLike[] { (ItemLike)Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE }, ), Ingredient.of(new ItemLike[] { (ItemLike)$$1 }, ), Ingredient.of(new ItemLike[] { (ItemLike)Items.NETHERITE_INGOT }, ), $$2, $$3)
/* 133 */       .unlocks("has_netherite_ingot", has((ItemLike)Items.NETHERITE_INGOT))
/* 134 */       .save($$0, getItemName((ItemLike)$$3) + "_smithing");
/*     */   }
/*     */   
/*     */   protected static void trimSmithing(RecipeOutput $$0, Item $$1, ResourceLocation $$2) {
/* 138 */     SmithingTrimRecipeBuilder.smithingTrim(Ingredient.of(new ItemLike[] { (ItemLike)$$1 }, ), Ingredient.of(ItemTags.TRIMMABLE_ARMOR), Ingredient.of(ItemTags.TRIM_MATERIALS), RecipeCategory.MISC)
/* 139 */       .unlocks("has_smithing_trim_template", has((ItemLike)$$1))
/* 140 */       .save($$0, $$2);
/*     */   }
/*     */   
/*     */   protected static void twoByTwoPacker(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, ItemLike $$3) {
/* 144 */     ShapedRecipeBuilder.shaped($$1, $$2, 1)
/* 145 */       .define(Character.valueOf('#'), $$3)
/* 146 */       .pattern("##")
/* 147 */       .pattern("##")
/* 148 */       .unlockedBy(getHasName($$3), has($$3))
/* 149 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void threeByThreePacker(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, ItemLike $$3, String $$4) {
/* 153 */     ShapelessRecipeBuilder.shapeless($$1, $$2)
/* 154 */       .requires($$3, 9)
/* 155 */       .unlockedBy($$4, has($$3))
/* 156 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void threeByThreePacker(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, ItemLike $$3) {
/* 160 */     threeByThreePacker($$0, $$1, $$2, $$3, getHasName($$3));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void planksFromLog(RecipeOutput $$0, ItemLike $$1, TagKey<Item> $$2, int $$3) {
/* 165 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, $$1, $$3)
/* 166 */       .requires($$2)
/* 167 */       .group("planks")
/* 168 */       .unlockedBy("has_log", has($$2))
/* 169 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void planksFromLogs(RecipeOutput $$0, ItemLike $$1, TagKey<Item> $$2, int $$3) {
/* 173 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, $$1, $$3)
/* 174 */       .requires($$2)
/* 175 */       .group("planks")
/* 176 */       .unlockedBy("has_logs", has($$2))
/* 177 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void woodFromLogs(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 181 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, $$1, 3)
/* 182 */       .define(Character.valueOf('#'), $$2)
/* 183 */       .pattern("##")
/* 184 */       .pattern("##")
/* 185 */       .group("bark")
/* 186 */       .unlockedBy("has_log", has($$2))
/* 187 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void woodenBoat(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 191 */     ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, $$1)
/* 192 */       .define(Character.valueOf('#'), $$2)
/* 193 */       .pattern("# #")
/* 194 */       .pattern("###")
/* 195 */       .group("boat")
/* 196 */       .unlockedBy("in_water", insideOf(Blocks.WATER))
/* 197 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void chestBoat(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 201 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, $$1)
/* 202 */       .requires((ItemLike)Blocks.CHEST)
/* 203 */       .requires($$2)
/* 204 */       .group("chest_boat")
/* 205 */       .unlockedBy("has_boat", has(ItemTags.BOATS))
/* 206 */       .save($$0);
/*     */   }
/*     */   
/*     */   private static RecipeBuilder buttonBuilder(ItemLike $$0, Ingredient $$1) {
/* 210 */     return ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, $$0)
/* 211 */       .requires($$1);
/*     */   }
/*     */   
/*     */   protected static RecipeBuilder doorBuilder(ItemLike $$0, Ingredient $$1) {
/* 215 */     return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, $$0, 3)
/* 216 */       .define(Character.valueOf('#'), $$1)
/* 217 */       .pattern("##")
/* 218 */       .pattern("##")
/* 219 */       .pattern("##");
/*     */   }
/*     */   
/*     */   private static RecipeBuilder fenceBuilder(ItemLike $$0, Ingredient $$1) {
/* 223 */     int $$2 = ($$0 == Blocks.NETHER_BRICK_FENCE) ? 6 : 3;
/* 224 */     Item $$3 = ($$0 == Blocks.NETHER_BRICK_FENCE) ? Items.NETHER_BRICK : Items.STICK;
/* 225 */     return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, $$0, $$2)
/* 226 */       .define(Character.valueOf('W'), $$1)
/* 227 */       .define(Character.valueOf('#'), (ItemLike)$$3)
/* 228 */       .pattern("W#W")
/* 229 */       .pattern("W#W");
/*     */   }
/*     */   
/*     */   private static RecipeBuilder fenceGateBuilder(ItemLike $$0, Ingredient $$1) {
/* 233 */     return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, $$0)
/* 234 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 235 */       .define(Character.valueOf('W'), $$1)
/* 236 */       .pattern("#W#")
/* 237 */       .pattern("#W#");
/*     */   }
/*     */   
/*     */   protected static void pressurePlate(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 241 */     pressurePlateBuilder(RecipeCategory.REDSTONE, $$1, Ingredient.of(new ItemLike[] { $$2
/* 242 */           })).unlockedBy(getHasName($$2), has($$2))
/* 243 */       .save($$0);
/*     */   }
/*     */   
/*     */   private static RecipeBuilder pressurePlateBuilder(RecipeCategory $$0, ItemLike $$1, Ingredient $$2) {
/* 247 */     return ShapedRecipeBuilder.shaped($$0, $$1)
/* 248 */       .define(Character.valueOf('#'), $$2)
/* 249 */       .pattern("##");
/*     */   }
/*     */   
/*     */   protected static void slab(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, ItemLike $$3) {
/* 253 */     slabBuilder($$1, $$2, Ingredient.of(new ItemLike[] { $$3
/* 254 */           })).unlockedBy(getHasName($$3), has($$3))
/* 255 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static RecipeBuilder slabBuilder(RecipeCategory $$0, ItemLike $$1, Ingredient $$2) {
/* 259 */     return ShapedRecipeBuilder.shaped($$0, $$1, 6)
/* 260 */       .define(Character.valueOf('#'), $$2)
/* 261 */       .pattern("###");
/*     */   }
/*     */   
/*     */   protected static RecipeBuilder stairBuilder(ItemLike $$0, Ingredient $$1) {
/* 265 */     return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, $$0, 4)
/* 266 */       .define(Character.valueOf('#'), $$1)
/* 267 */       .pattern("#  ")
/* 268 */       .pattern("## ")
/* 269 */       .pattern("###");
/*     */   }
/*     */   
/*     */   private static RecipeBuilder trapdoorBuilder(ItemLike $$0, Ingredient $$1) {
/* 273 */     return ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, $$0, 2)
/* 274 */       .define(Character.valueOf('#'), $$1)
/* 275 */       .pattern("###")
/* 276 */       .pattern("###");
/*     */   }
/*     */   
/*     */   private static RecipeBuilder signBuilder(ItemLike $$0, Ingredient $$1) {
/* 280 */     return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, $$0, 3)
/* 281 */       .group("sign")
/* 282 */       .define(Character.valueOf('#'), $$1)
/* 283 */       .define(Character.valueOf('X'), (ItemLike)Items.STICK)
/* 284 */       .pattern("###")
/* 285 */       .pattern("###")
/* 286 */       .pattern(" X ");
/*     */   }
/*     */   
/*     */   protected static void hangingSign(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 290 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, $$1, 6)
/* 291 */       .group("hanging_sign")
/* 292 */       .define(Character.valueOf('#'), $$2)
/* 293 */       .define(Character.valueOf('X'), (ItemLike)Items.CHAIN)
/* 294 */       .pattern("X X")
/* 295 */       .pattern("###")
/* 296 */       .pattern("###")
/* 297 */       .unlockedBy("has_stripped_logs", has($$2))
/* 298 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void colorBlockWithDye(RecipeOutput $$0, List<Item> $$1, List<Item> $$2, String $$3) {
/* 302 */     for (int $$4 = 0; $$4 < $$1.size(); $$4++) {
/* 303 */       Item $$5 = $$1.get($$4);
/* 304 */       Item $$6 = $$2.get($$4);
/*     */       
/* 306 */       ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)$$6)
/* 307 */         .requires((ItemLike)$$5)
/* 308 */         .requires(Ingredient.of($$2.stream().filter($$1 -> !$$1.equals($$0)).map(net.minecraft.world.item.ItemStack::new)))
/* 309 */         .group($$3)
/* 310 */         .unlockedBy("has_needed_dye", has((ItemLike)$$5))
/* 311 */         .save($$0, "dye_" + getItemName((ItemLike)$$6));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static void carpet(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 316 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, $$1, 3)
/* 317 */       .define(Character.valueOf('#'), $$2)
/* 318 */       .pattern("##")
/* 319 */       .group("carpet")
/* 320 */       .unlockedBy(getHasName($$2), has($$2))
/* 321 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void bedFromPlanksAndWool(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 325 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, $$1)
/* 326 */       .define(Character.valueOf('#'), $$2)
/* 327 */       .define(Character.valueOf('X'), ItemTags.PLANKS)
/* 328 */       .pattern("###")
/* 329 */       .pattern("XXX")
/* 330 */       .group("bed")
/* 331 */       .unlockedBy(getHasName($$2), has($$2))
/* 332 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void banner(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 336 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, $$1)
/* 337 */       .define(Character.valueOf('#'), $$2)
/* 338 */       .define(Character.valueOf('|'), (ItemLike)Items.STICK)
/* 339 */       .pattern("###")
/* 340 */       .pattern("###")
/* 341 */       .pattern(" | ")
/* 342 */       .group("banner")
/* 343 */       .unlockedBy(getHasName($$2), has($$2))
/* 344 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void stainedGlassFromGlassAndDye(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 348 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, $$1, 8)
/* 349 */       .define(Character.valueOf('#'), (ItemLike)Blocks.GLASS)
/* 350 */       .define(Character.valueOf('X'), $$2)
/* 351 */       .pattern("###")
/* 352 */       .pattern("#X#")
/* 353 */       .pattern("###")
/* 354 */       .group("stained_glass")
/* 355 */       .unlockedBy("has_glass", has((ItemLike)Blocks.GLASS))
/* 356 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void stainedGlassPaneFromStainedGlass(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 360 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, $$1, 16)
/* 361 */       .define(Character.valueOf('#'), $$2)
/* 362 */       .pattern("###")
/* 363 */       .pattern("###")
/* 364 */       .group("stained_glass_pane")
/* 365 */       .unlockedBy("has_glass", has($$2))
/* 366 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void stainedGlassPaneFromGlassPaneAndDye(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 370 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, $$1, 8)
/* 371 */       .define(Character.valueOf('#'), (ItemLike)Blocks.GLASS_PANE)
/* 372 */       .define(Character.valueOf('$'), $$2)
/* 373 */       .pattern("###")
/* 374 */       .pattern("#$#")
/* 375 */       .pattern("###")
/* 376 */       .group("stained_glass_pane")
/* 377 */       .unlockedBy("has_glass_pane", has((ItemLike)Blocks.GLASS_PANE))
/* 378 */       .unlockedBy(getHasName($$2), has($$2))
/* 379 */       .save($$0, getConversionRecipeName($$1, (ItemLike)Blocks.GLASS_PANE));
/*     */   }
/*     */   
/*     */   protected static void coloredTerracottaFromTerracottaAndDye(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 383 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, $$1, 8)
/* 384 */       .define(Character.valueOf('#'), (ItemLike)Blocks.TERRACOTTA)
/* 385 */       .define(Character.valueOf('X'), $$2)
/* 386 */       .pattern("###")
/* 387 */       .pattern("#X#")
/* 388 */       .pattern("###")
/* 389 */       .group("stained_terracotta")
/* 390 */       .unlockedBy("has_terracotta", has((ItemLike)Blocks.TERRACOTTA))
/* 391 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void concretePowder(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 395 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, $$1, 8)
/* 396 */       .requires($$2)
/* 397 */       .requires((ItemLike)Blocks.SAND, 4)
/* 398 */       .requires((ItemLike)Blocks.GRAVEL, 4)
/* 399 */       .group("concrete_powder")
/* 400 */       .unlockedBy("has_sand", has((ItemLike)Blocks.SAND))
/* 401 */       .unlockedBy("has_gravel", has((ItemLike)Blocks.GRAVEL))
/* 402 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void candle(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 406 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, $$1)
/* 407 */       .requires((ItemLike)Blocks.CANDLE)
/* 408 */       .requires($$2)
/* 409 */       .group("dyed_candle")
/* 410 */       .unlockedBy(getHasName($$2), has($$2))
/* 411 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void wall(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, ItemLike $$3) {
/* 415 */     wallBuilder($$1, $$2, Ingredient.of(new ItemLike[] { $$3
/* 416 */           })).unlockedBy(getHasName($$3), has($$3))
/* 417 */       .save($$0);
/*     */   }
/*     */   
/*     */   private static RecipeBuilder wallBuilder(RecipeCategory $$0, ItemLike $$1, Ingredient $$2) {
/* 421 */     return ShapedRecipeBuilder.shaped($$0, $$1, 6)
/* 422 */       .define(Character.valueOf('#'), $$2)
/* 423 */       .pattern("###")
/* 424 */       .pattern("###");
/*     */   }
/*     */   
/*     */   protected static void polished(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, ItemLike $$3) {
/* 428 */     polishedBuilder($$1, $$2, Ingredient.of(new ItemLike[] { $$3
/* 429 */           })).unlockedBy(getHasName($$3), has($$3))
/* 430 */       .save($$0);
/*     */   }
/*     */   
/*     */   private static RecipeBuilder polishedBuilder(RecipeCategory $$0, ItemLike $$1, Ingredient $$2) {
/* 434 */     return ShapedRecipeBuilder.shaped($$0, $$1, 4)
/* 435 */       .define(Character.valueOf('S'), $$2)
/* 436 */       .pattern("SS")
/* 437 */       .pattern("SS");
/*     */   }
/*     */   
/*     */   protected static void cut(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, ItemLike $$3) {
/* 441 */     cutBuilder($$1, $$2, Ingredient.of(new ItemLike[] { $$3
/* 442 */           })).unlockedBy(getHasName($$3), has($$3))
/* 443 */       .save($$0);
/*     */   }
/*     */   
/*     */   private static ShapedRecipeBuilder cutBuilder(RecipeCategory $$0, ItemLike $$1, Ingredient $$2) {
/* 447 */     return ShapedRecipeBuilder.shaped($$0, $$1, 4)
/* 448 */       .define(Character.valueOf('#'), $$2)
/* 449 */       .pattern("##")
/* 450 */       .pattern("##");
/*     */   }
/*     */   
/*     */   protected static void chiseled(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, ItemLike $$3) {
/* 454 */     chiseledBuilder($$1, $$2, Ingredient.of(new ItemLike[] { $$3
/* 455 */           })).unlockedBy(getHasName($$3), has($$3))
/* 456 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void mosaicBuilder(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, ItemLike $$3) {
/* 460 */     ShapedRecipeBuilder.shaped($$1, $$2)
/* 461 */       .define(Character.valueOf('#'), $$3)
/* 462 */       .pattern("#")
/* 463 */       .pattern("#")
/* 464 */       .unlockedBy(getHasName($$3), has($$3))
/* 465 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static ShapedRecipeBuilder chiseledBuilder(RecipeCategory $$0, ItemLike $$1, Ingredient $$2) {
/* 469 */     return ShapedRecipeBuilder.shaped($$0, $$1)
/* 470 */       .define(Character.valueOf('#'), $$2)
/* 471 */       .pattern("#")
/* 472 */       .pattern("#");
/*     */   }
/*     */   
/*     */   protected static void stonecutterResultFromBase(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, ItemLike $$3) {
/* 476 */     stonecutterResultFromBase($$0, $$1, $$2, $$3, 1);
/*     */   }
/*     */   
/*     */   protected static void stonecutterResultFromBase(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, ItemLike $$3, int $$4) {
/* 480 */     SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[] { $$3 }, ), $$1, $$2, $$4)
/* 481 */       .unlockedBy(getHasName($$3), has($$3))
/* 482 */       .save($$0, getConversionRecipeName($$2, $$3) + "_stonecutting");
/*     */   }
/*     */   
/*     */   private static void smeltingResultFromBase(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 486 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { $$2 }, ), RecipeCategory.BUILDING_BLOCKS, $$1, 0.1F, 200)
/* 487 */       .unlockedBy(getHasName($$2), has($$2))
/* 488 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void nineBlockStorageRecipes(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, RecipeCategory $$3, ItemLike $$4) {
/* 492 */     nineBlockStorageRecipes($$0, $$1, $$2, $$3, $$4, getSimpleRecipeName($$4), null, getSimpleRecipeName($$2), null);
/*     */   }
/*     */   
/*     */   protected static void nineBlockStorageRecipesWithCustomPacking(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, RecipeCategory $$3, ItemLike $$4, String $$5, String $$6) {
/* 496 */     nineBlockStorageRecipes($$0, $$1, $$2, $$3, $$4, $$5, $$6, getSimpleRecipeName($$2), null);
/*     */   }
/*     */   
/*     */   protected static void nineBlockStorageRecipesRecipesWithCustomUnpacking(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, RecipeCategory $$3, ItemLike $$4, String $$5, String $$6) {
/* 500 */     nineBlockStorageRecipes($$0, $$1, $$2, $$3, $$4, getSimpleRecipeName($$4), null, $$5, $$6);
/*     */   }
/*     */   
/*     */   private static void nineBlockStorageRecipes(RecipeOutput $$0, RecipeCategory $$1, ItemLike $$2, RecipeCategory $$3, ItemLike $$4, String $$5, @Nullable String $$6, String $$7, @Nullable String $$8) {
/* 504 */     ShapelessRecipeBuilder.shapeless($$1, $$2, 9)
/* 505 */       .requires($$4)
/* 506 */       .group($$8)
/* 507 */       .unlockedBy(getHasName($$4), has($$4))
/* 508 */       .save($$0, new ResourceLocation($$7));
/*     */     
/* 510 */     ShapedRecipeBuilder.shaped($$3, $$4)
/* 511 */       .define(Character.valueOf('#'), $$2)
/* 512 */       .pattern("###")
/* 513 */       .pattern("###")
/* 514 */       .pattern("###")
/* 515 */       .group($$6)
/* 516 */       .unlockedBy(getHasName($$2), has($$2))
/* 517 */       .save($$0, new ResourceLocation($$5));
/*     */   }
/*     */   
/*     */   protected static void copySmithingTemplate(RecipeOutput $$0, ItemLike $$1, TagKey<Item> $$2) {
/* 521 */     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, $$1, 2)
/* 522 */       .define(Character.valueOf('#'), (ItemLike)Items.DIAMOND)
/* 523 */       .define(Character.valueOf('C'), $$2)
/* 524 */       .define(Character.valueOf('S'), $$1)
/* 525 */       .pattern("#S#")
/* 526 */       .pattern("#C#")
/* 527 */       .pattern("###")
/* 528 */       .unlockedBy(getHasName($$1), has($$1))
/* 529 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void copySmithingTemplate(RecipeOutput $$0, ItemLike $$1, ItemLike $$2) {
/* 533 */     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, $$1, 2)
/* 534 */       .define(Character.valueOf('#'), (ItemLike)Items.DIAMOND)
/* 535 */       .define(Character.valueOf('C'), $$2)
/* 536 */       .define(Character.valueOf('S'), $$1)
/* 537 */       .pattern("#S#")
/* 538 */       .pattern("#C#")
/* 539 */       .pattern("###")
/* 540 */       .unlockedBy(getHasName($$1), has($$1))
/* 541 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static <T extends AbstractCookingRecipe> void cookRecipes(RecipeOutput $$0, String $$1, RecipeSerializer<T> $$2, AbstractCookingRecipe.Factory<T> $$3, int $$4) {
/* 545 */     simpleCookingRecipe($$0, $$1, $$2, $$3, $$4, (ItemLike)Items.BEEF, (ItemLike)Items.COOKED_BEEF, 0.35F);
/* 546 */     simpleCookingRecipe($$0, $$1, $$2, $$3, $$4, (ItemLike)Items.CHICKEN, (ItemLike)Items.COOKED_CHICKEN, 0.35F);
/* 547 */     simpleCookingRecipe($$0, $$1, $$2, $$3, $$4, (ItemLike)Items.COD, (ItemLike)Items.COOKED_COD, 0.35F);
/* 548 */     simpleCookingRecipe($$0, $$1, $$2, $$3, $$4, (ItemLike)Items.KELP, (ItemLike)Items.DRIED_KELP, 0.1F);
/* 549 */     simpleCookingRecipe($$0, $$1, $$2, $$3, $$4, (ItemLike)Items.SALMON, (ItemLike)Items.COOKED_SALMON, 0.35F);
/* 550 */     simpleCookingRecipe($$0, $$1, $$2, $$3, $$4, (ItemLike)Items.MUTTON, (ItemLike)Items.COOKED_MUTTON, 0.35F);
/* 551 */     simpleCookingRecipe($$0, $$1, $$2, $$3, $$4, (ItemLike)Items.PORKCHOP, (ItemLike)Items.COOKED_PORKCHOP, 0.35F);
/* 552 */     simpleCookingRecipe($$0, $$1, $$2, $$3, $$4, (ItemLike)Items.POTATO, (ItemLike)Items.BAKED_POTATO, 0.35F);
/* 553 */     simpleCookingRecipe($$0, $$1, $$2, $$3, $$4, (ItemLike)Items.RABBIT, (ItemLike)Items.COOKED_RABBIT, 0.35F);
/*     */   }
/*     */   
/*     */   private static <T extends AbstractCookingRecipe> void simpleCookingRecipe(RecipeOutput $$0, String $$1, RecipeSerializer<T> $$2, AbstractCookingRecipe.Factory<T> $$3, int $$4, ItemLike $$5, ItemLike $$6, float $$7) {
/* 557 */     SimpleCookingRecipeBuilder.<T>generic(Ingredient.of(new ItemLike[] { $$5 }, ), RecipeCategory.FOOD, $$6, $$7, $$4, $$2, $$3)
/* 558 */       .unlockedBy(getHasName($$5), has($$5))
/* 559 */       .save($$0, getItemName($$6) + "_from_" + getItemName($$6));
/*     */   }
/*     */   
/*     */   protected static void waxRecipes(RecipeOutput $$0, FeatureFlagSet $$1) {
/* 563 */     ((BiMap)HoneycombItem.WAXABLES.get()).forEach(($$2, $$3) -> {
/*     */           if (!$$3.requiredFeatures().isSubsetOf($$0)) {
/*     */             return;
/*     */           }
/*     */           ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)$$3).requires((ItemLike)$$2).requires((ItemLike)Items.HONEYCOMB).group(getItemName((ItemLike)$$3)).unlockedBy(getHasName((ItemLike)$$2), has((ItemLike)$$2)).save($$1, getConversionRecipeName((ItemLike)$$3, (ItemLike)Items.HONEYCOMB));
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void grate(RecipeOutput $$0, Block $$1, Block $$2) {
/* 578 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)$$1, 4)
/* 579 */       .define(Character.valueOf('M'), (ItemLike)$$2)
/* 580 */       .pattern(" M ")
/* 581 */       .pattern("M M")
/* 582 */       .pattern(" M ")
/* 583 */       .unlockedBy(getHasName((ItemLike)$$2), has((ItemLike)$$2))
/* 584 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void copperBulb(RecipeOutput $$0, Block $$1, Block $$2) {
/* 588 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)$$1, 4)
/* 589 */       .define(Character.valueOf('C'), (ItemLike)$$2)
/* 590 */       .define(Character.valueOf('R'), (ItemLike)Items.REDSTONE)
/* 591 */       .define(Character.valueOf('B'), (ItemLike)Items.BLAZE_ROD)
/* 592 */       .pattern(" C ")
/* 593 */       .pattern("CBC")
/* 594 */       .pattern(" R ")
/* 595 */       .unlockedBy(getHasName((ItemLike)$$2), has((ItemLike)$$2))
/* 596 */       .save($$0);
/*     */   }
/*     */   
/*     */   protected static void generateRecipes(RecipeOutput $$0, BlockFamily $$1, FeatureFlagSet $$2) {
/* 600 */     $$1.getVariants().forEach(($$3, $$4) -> {
/*     */           if (!$$4.requiredFeatures().isSubsetOf($$0)) {
/*     */             return;
/*     */           }
/*     */           BiFunction<ItemLike, ItemLike, RecipeBuilder> $$5 = SHAPE_BUILDERS.get($$3);
/*     */           Block block = getBaseBlock($$1, $$3);
/*     */           if ($$5 != null) {
/*     */             RecipeBuilder $$7 = $$5.apply($$4, block);
/*     */             $$1.getRecipeGroupPrefix().ifPresent(());
/*     */             $$7.unlockedBy($$1.getRecipeUnlockedBy().orElseGet(()), has((ItemLike)block));
/*     */             $$7.save($$2);
/*     */           } 
/*     */           if ($$3 == BlockFamily.Variant.CRACKED) {
/*     */             smeltingResultFromBase($$2, (ItemLike)$$4, (ItemLike)block);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static Block getBaseBlock(BlockFamily $$0, BlockFamily.Variant $$1) {
/* 620 */     if ($$1 == BlockFamily.Variant.CHISELED) {
/* 621 */       if (!$$0.getVariants().containsKey(BlockFamily.Variant.SLAB)) {
/* 622 */         throw new IllegalStateException("Slab is not defined for the family.");
/*     */       }
/* 624 */       return $$0.get(BlockFamily.Variant.SLAB);
/*     */     } 
/* 626 */     return $$0.getBaseBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 646 */     SHAPE_BUILDERS = (Map<BlockFamily.Variant, BiFunction<ItemLike, ItemLike, RecipeBuilder>>)ImmutableMap.builder().put(BlockFamily.Variant.BUTTON, ($$0, $$1) -> buttonBuilder($$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.CHISELED, ($$0, $$1) -> chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, $$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.CUT, ($$0, $$1) -> cutBuilder(RecipeCategory.BUILDING_BLOCKS, $$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.DOOR, ($$0, $$1) -> doorBuilder($$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.CUSTOM_FENCE, ($$0, $$1) -> fenceBuilder($$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.FENCE, ($$0, $$1) -> fenceBuilder($$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.CUSTOM_FENCE_GATE, ($$0, $$1) -> fenceGateBuilder($$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.FENCE_GATE, ($$0, $$1) -> fenceGateBuilder($$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.SIGN, ($$0, $$1) -> signBuilder($$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.SLAB, ($$0, $$1) -> slabBuilder(RecipeCategory.BUILDING_BLOCKS, $$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.STAIRS, ($$0, $$1) -> stairBuilder($$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.PRESSURE_PLATE, ($$0, $$1) -> pressurePlateBuilder(RecipeCategory.REDSTONE, $$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.POLISHED, ($$0, $$1) -> polishedBuilder(RecipeCategory.BUILDING_BLOCKS, $$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.TRAPDOOR, ($$0, $$1) -> trapdoorBuilder($$0, Ingredient.of(new ItemLike[] { $$1 }))).put(BlockFamily.Variant.WALL, ($$0, $$1) -> wallBuilder(RecipeCategory.DECORATIONS, $$0, Ingredient.of(new ItemLike[] { $$1 }))).build();
/*     */   }
/*     */   
/*     */   private static Criterion<EnterBlockTrigger.TriggerInstance> insideOf(Block $$0) {
/* 650 */     return CriteriaTriggers.ENTER_BLOCK.createCriterion((CriterionTriggerInstance)new EnterBlockTrigger.TriggerInstance(Optional.empty(), Optional.of($$0.builtInRegistryHolder()), Optional.empty()));
/*     */   }
/*     */   
/*     */   private static Criterion<InventoryChangeTrigger.TriggerInstance> has(MinMaxBounds.Ints $$0, ItemLike $$1) {
/* 654 */     return inventoryTrigger(new ItemPredicate.Builder[] { ItemPredicate.Builder.item().of(new ItemLike[] { $$1 }).withCount($$0) });
/*     */   }
/*     */   
/*     */   protected static Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike $$0) {
/* 658 */     return inventoryTrigger(new ItemPredicate.Builder[] { ItemPredicate.Builder.item().of(new ItemLike[] { $$0 }) });
/*     */   }
/*     */   
/*     */   protected static Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> $$0) {
/* 662 */     return inventoryTrigger(new ItemPredicate.Builder[] { ItemPredicate.Builder.item().of($$0) });
/*     */   }
/*     */   
/*     */   private static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate.Builder... $$0) {
/* 666 */     return inventoryTrigger((ItemPredicate[])Arrays.<ItemPredicate.Builder>stream($$0).map(ItemPredicate.Builder::build).toArray($$0 -> new ItemPredicate[$$0]));
/*     */   }
/*     */   
/*     */   private static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate... $$0) {
/* 670 */     return CriteriaTriggers.INVENTORY_CHANGED.createCriterion((CriterionTriggerInstance)new InventoryChangeTrigger.TriggerInstance(Optional.empty(), InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of($$0)));
/*     */   }
/*     */   
/*     */   protected static String getHasName(ItemLike $$0) {
/* 674 */     return "has_" + getItemName($$0);
/*     */   }
/*     */   
/*     */   protected static String getItemName(ItemLike $$0) {
/* 678 */     return BuiltInRegistries.ITEM.getKey($$0.asItem()).getPath();
/*     */   }
/*     */   
/*     */   protected static String getSimpleRecipeName(ItemLike $$0) {
/* 682 */     return getItemName($$0);
/*     */   }
/*     */   
/*     */   protected static String getConversionRecipeName(ItemLike $$0, ItemLike $$1) {
/* 686 */     return getItemName($$0) + "_from_" + getItemName($$0);
/*     */   }
/*     */   
/*     */   protected static String getSmeltingRecipeName(ItemLike $$0) {
/* 690 */     return getItemName($$0) + "_from_smelting";
/*     */   }
/*     */   
/*     */   protected static String getBlastingRecipeName(ItemLike $$0) {
/* 694 */     return getItemName($$0) + "_from_blasting";
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getName() {
/* 699 */     return "Recipes";
/*     */   }
/*     */   
/*     */   protected abstract void buildRecipes(RecipeOutput paramRecipeOutput);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\RecipeProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */