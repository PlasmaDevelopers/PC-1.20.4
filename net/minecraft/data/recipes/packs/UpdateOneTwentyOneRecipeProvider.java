/*     */ package net.minecraft.data.recipes.packs;
/*     */ 
/*     */ import net.minecraft.data.PackOutput;
/*     */ import net.minecraft.data.recipes.RecipeCategory;
/*     */ import net.minecraft.data.recipes.RecipeOutput;
/*     */ import net.minecraft.data.recipes.RecipeProvider;
/*     */ import net.minecraft.data.recipes.ShapedRecipeBuilder;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ 
/*     */ public class UpdateOneTwentyOneRecipeProvider extends RecipeProvider {
/*     */   public UpdateOneTwentyOneRecipeProvider(PackOutput $$0) {
/*  16 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void buildRecipes(RecipeOutput $$0) {
/*  21 */     generateForEnabledBlockFamilies($$0, FeatureFlagSet.of(FeatureFlags.UPDATE_1_21));
/*     */     
/*  23 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.CRAFTER)
/*  24 */       .define(Character.valueOf('#'), (ItemLike)Items.IRON_INGOT)
/*  25 */       .define(Character.valueOf('C'), (ItemLike)Items.CRAFTING_TABLE)
/*  26 */       .define(Character.valueOf('R'), (ItemLike)Items.REDSTONE)
/*  27 */       .define(Character.valueOf('D'), (ItemLike)Items.DROPPER)
/*  28 */       .pattern("###")
/*  29 */       .pattern("#C#")
/*  30 */       .pattern("RDR")
/*  31 */       .unlockedBy("has_dropper", has((ItemLike)Items.DROPPER))
/*  32 */       .save($$0);
/*     */     
/*  34 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.TUFF_SLAB, (ItemLike)Blocks.TUFF, 2);
/*  35 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.TUFF_STAIRS, (ItemLike)Blocks.TUFF);
/*  36 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.TUFF_WALL, (ItemLike)Blocks.TUFF);
/*  37 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_TUFF, (ItemLike)Blocks.TUFF);
/*  38 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_TUFF, (ItemLike)Blocks.TUFF);
/*  39 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_TUFF_SLAB, (ItemLike)Blocks.TUFF, 2);
/*  40 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_TUFF_STAIRS, (ItemLike)Blocks.TUFF);
/*  41 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.POLISHED_TUFF_WALL, (ItemLike)Blocks.TUFF);
/*  42 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.TUFF_BRICKS, (ItemLike)Blocks.TUFF);
/*  43 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.TUFF_BRICK_SLAB, (ItemLike)Blocks.TUFF, 2);
/*  44 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.TUFF_BRICK_STAIRS, (ItemLike)Blocks.TUFF);
/*  45 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.TUFF_BRICK_WALL, (ItemLike)Blocks.TUFF);
/*  46 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_TUFF_BRICKS, (ItemLike)Blocks.TUFF);
/*     */     
/*  48 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_TUFF_SLAB, (ItemLike)Blocks.POLISHED_TUFF, 2);
/*  49 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_TUFF_STAIRS, (ItemLike)Blocks.POLISHED_TUFF);
/*  50 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.POLISHED_TUFF_WALL, (ItemLike)Blocks.POLISHED_TUFF);
/*  51 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.TUFF_BRICKS, (ItemLike)Blocks.POLISHED_TUFF);
/*  52 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.TUFF_BRICK_SLAB, (ItemLike)Blocks.POLISHED_TUFF, 2);
/*  53 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.TUFF_BRICK_STAIRS, (ItemLike)Blocks.POLISHED_TUFF);
/*  54 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.TUFF_BRICK_WALL, (ItemLike)Blocks.POLISHED_TUFF);
/*  55 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_TUFF_BRICKS, (ItemLike)Blocks.POLISHED_TUFF);
/*     */     
/*  57 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.TUFF_BRICK_SLAB, (ItemLike)Blocks.TUFF_BRICKS, 2);
/*  58 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.TUFF_BRICK_STAIRS, (ItemLike)Blocks.TUFF_BRICKS);
/*  59 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.TUFF_BRICK_WALL, (ItemLike)Blocks.TUFF_BRICKS);
/*  60 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_TUFF_BRICKS, (ItemLike)Blocks.TUFF_BRICKS);
/*     */     
/*  62 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_COPPER, (ItemLike)Blocks.COPPER_BLOCK, 4);
/*  63 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.EXPOSED_CHISELED_COPPER, (ItemLike)Blocks.EXPOSED_COPPER, 4);
/*  64 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WEATHERED_CHISELED_COPPER, (ItemLike)Blocks.WEATHERED_COPPER, 4);
/*  65 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.OXIDIZED_CHISELED_COPPER, (ItemLike)Blocks.OXIDIZED_COPPER, 4);
/*  66 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_CHISELED_COPPER, (ItemLike)Blocks.WAXED_COPPER_BLOCK, 4);
/*  67 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_EXPOSED_CHISELED_COPPER, (ItemLike)Blocks.WAXED_EXPOSED_COPPER, 4);
/*  68 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_WEATHERED_CHISELED_COPPER, (ItemLike)Blocks.WAXED_WEATHERED_COPPER, 4);
/*  69 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_OXIDIZED_CHISELED_COPPER, (ItemLike)Blocks.WAXED_OXIDIZED_COPPER, 4);
/*     */     
/*  71 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_COPPER, (ItemLike)Blocks.CUT_COPPER, 1);
/*  72 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.EXPOSED_CHISELED_COPPER, (ItemLike)Blocks.EXPOSED_CUT_COPPER, 1);
/*  73 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WEATHERED_CHISELED_COPPER, (ItemLike)Blocks.WEATHERED_CUT_COPPER, 1);
/*  74 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.OXIDIZED_CHISELED_COPPER, (ItemLike)Blocks.OXIDIZED_CUT_COPPER, 1);
/*  75 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_CHISELED_COPPER, (ItemLike)Blocks.WAXED_CUT_COPPER, 1);
/*  76 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_EXPOSED_CHISELED_COPPER, (ItemLike)Blocks.WAXED_EXPOSED_CUT_COPPER, 1);
/*  77 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_WEATHERED_CHISELED_COPPER, (ItemLike)Blocks.WAXED_WEATHERED_CUT_COPPER, 1);
/*  78 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_OXIDIZED_CHISELED_COPPER, (ItemLike)Blocks.WAXED_OXIDIZED_CUT_COPPER, 1);
/*     */     
/*  80 */     grate($$0, Blocks.COPPER_GRATE, Blocks.COPPER_BLOCK);
/*  81 */     grate($$0, Blocks.EXPOSED_COPPER_GRATE, Blocks.EXPOSED_COPPER);
/*  82 */     grate($$0, Blocks.WEATHERED_COPPER_GRATE, Blocks.WEATHERED_COPPER);
/*  83 */     grate($$0, Blocks.OXIDIZED_COPPER_GRATE, Blocks.OXIDIZED_COPPER);
/*  84 */     grate($$0, Blocks.WAXED_COPPER_GRATE, Blocks.WAXED_COPPER_BLOCK);
/*  85 */     grate($$0, Blocks.WAXED_EXPOSED_COPPER_GRATE, Blocks.WAXED_EXPOSED_COPPER);
/*  86 */     grate($$0, Blocks.WAXED_WEATHERED_COPPER_GRATE, Blocks.WAXED_WEATHERED_COPPER);
/*  87 */     grate($$0, Blocks.WAXED_OXIDIZED_COPPER_GRATE, Blocks.WAXED_OXIDIZED_COPPER);
/*     */     
/*  89 */     copperBulb($$0, Blocks.COPPER_BULB, Blocks.COPPER_BLOCK);
/*  90 */     copperBulb($$0, Blocks.EXPOSED_COPPER_BULB, Blocks.EXPOSED_COPPER);
/*  91 */     copperBulb($$0, Blocks.WEATHERED_COPPER_BULB, Blocks.WEATHERED_COPPER);
/*  92 */     copperBulb($$0, Blocks.OXIDIZED_COPPER_BULB, Blocks.OXIDIZED_COPPER);
/*  93 */     copperBulb($$0, Blocks.WAXED_COPPER_BULB, Blocks.WAXED_COPPER_BLOCK);
/*  94 */     copperBulb($$0, Blocks.WAXED_EXPOSED_COPPER_BULB, Blocks.WAXED_EXPOSED_COPPER);
/*  95 */     copperBulb($$0, Blocks.WAXED_WEATHERED_COPPER_BULB, Blocks.WAXED_WEATHERED_COPPER);
/*  96 */     copperBulb($$0, Blocks.WAXED_OXIDIZED_COPPER_BULB, Blocks.WAXED_OXIDIZED_COPPER);
/*     */     
/*  98 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.COPPER_GRATE, (ItemLike)Blocks.COPPER_BLOCK, 4);
/*  99 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.EXPOSED_COPPER_GRATE, (ItemLike)Blocks.EXPOSED_COPPER, 4);
/* 100 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WEATHERED_COPPER_GRATE, (ItemLike)Blocks.WEATHERED_COPPER, 4);
/* 101 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.OXIDIZED_COPPER_GRATE, (ItemLike)Blocks.OXIDIZED_COPPER, 4);
/* 102 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_COPPER_GRATE, (ItemLike)Blocks.WAXED_COPPER_BLOCK, 4);
/* 103 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_EXPOSED_COPPER_GRATE, (ItemLike)Blocks.WAXED_EXPOSED_COPPER, 4);
/* 104 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_WEATHERED_COPPER_GRATE, (ItemLike)Blocks.WAXED_WEATHERED_COPPER, 4);
/* 105 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_OXIDIZED_COPPER_GRATE, (ItemLike)Blocks.WAXED_OXIDIZED_COPPER, 4);
/*     */     
/* 107 */     waxRecipes($$0, FeatureFlagSet.of(FeatureFlags.UPDATE_1_21));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\packs\UpdateOneTwentyOneRecipeProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */