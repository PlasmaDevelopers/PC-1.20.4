/*    */ package net.minecraft.world.item.crafting;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public interface RecipeSerializer<T extends Recipe<?>> {
/*  9 */   public static final RecipeSerializer<ShapedRecipe> SHAPED_RECIPE = register("crafting_shaped", new ShapedRecipe.Serializer());
/* 10 */   public static final RecipeSerializer<ShapelessRecipe> SHAPELESS_RECIPE = register("crafting_shapeless", new ShapelessRecipe.Serializer());
/* 11 */   public static final RecipeSerializer<ArmorDyeRecipe> ARMOR_DYE = register("crafting_special_armordye", new SimpleCraftingRecipeSerializer<>(ArmorDyeRecipe::new));
/* 12 */   public static final RecipeSerializer<BookCloningRecipe> BOOK_CLONING = register("crafting_special_bookcloning", new SimpleCraftingRecipeSerializer<>(BookCloningRecipe::new));
/* 13 */   public static final RecipeSerializer<MapCloningRecipe> MAP_CLONING = register("crafting_special_mapcloning", new SimpleCraftingRecipeSerializer<>(MapCloningRecipe::new));
/* 14 */   public static final RecipeSerializer<MapExtendingRecipe> MAP_EXTENDING = register("crafting_special_mapextending", new SimpleCraftingRecipeSerializer<>(MapExtendingRecipe::new));
/* 15 */   public static final RecipeSerializer<FireworkRocketRecipe> FIREWORK_ROCKET = register("crafting_special_firework_rocket", new SimpleCraftingRecipeSerializer<>(FireworkRocketRecipe::new));
/* 16 */   public static final RecipeSerializer<FireworkStarRecipe> FIREWORK_STAR = register("crafting_special_firework_star", new SimpleCraftingRecipeSerializer<>(FireworkStarRecipe::new));
/* 17 */   public static final RecipeSerializer<FireworkStarFadeRecipe> FIREWORK_STAR_FADE = register("crafting_special_firework_star_fade", new SimpleCraftingRecipeSerializer<>(FireworkStarFadeRecipe::new));
/* 18 */   public static final RecipeSerializer<TippedArrowRecipe> TIPPED_ARROW = register("crafting_special_tippedarrow", new SimpleCraftingRecipeSerializer<>(TippedArrowRecipe::new));
/* 19 */   public static final RecipeSerializer<BannerDuplicateRecipe> BANNER_DUPLICATE = register("crafting_special_bannerduplicate", new SimpleCraftingRecipeSerializer<>(BannerDuplicateRecipe::new));
/* 20 */   public static final RecipeSerializer<ShieldDecorationRecipe> SHIELD_DECORATION = register("crafting_special_shielddecoration", new SimpleCraftingRecipeSerializer<>(ShieldDecorationRecipe::new));
/* 21 */   public static final RecipeSerializer<ShulkerBoxColoring> SHULKER_BOX_COLORING = register("crafting_special_shulkerboxcoloring", new SimpleCraftingRecipeSerializer<>(ShulkerBoxColoring::new));
/* 22 */   public static final RecipeSerializer<SuspiciousStewRecipe> SUSPICIOUS_STEW = register("crafting_special_suspiciousstew", new SimpleCraftingRecipeSerializer<>(SuspiciousStewRecipe::new));
/* 23 */   public static final RecipeSerializer<RepairItemRecipe> REPAIR_ITEM = register("crafting_special_repairitem", new SimpleCraftingRecipeSerializer<>(RepairItemRecipe::new));
/* 24 */   public static final RecipeSerializer<SmeltingRecipe> SMELTING_RECIPE = register("smelting", new SimpleCookingSerializer<>(SmeltingRecipe::new, 200));
/* 25 */   public static final RecipeSerializer<BlastingRecipe> BLASTING_RECIPE = register("blasting", new SimpleCookingSerializer<>(BlastingRecipe::new, 100));
/* 26 */   public static final RecipeSerializer<SmokingRecipe> SMOKING_RECIPE = register("smoking", new SimpleCookingSerializer<>(SmokingRecipe::new, 100));
/* 27 */   public static final RecipeSerializer<CampfireCookingRecipe> CAMPFIRE_COOKING_RECIPE = register("campfire_cooking", new SimpleCookingSerializer<>(CampfireCookingRecipe::new, 100));
/* 28 */   public static final RecipeSerializer<StonecutterRecipe> STONECUTTER = register("stonecutting", new SingleItemRecipe.Serializer<>(StonecutterRecipe::new));
/* 29 */   public static final RecipeSerializer<SmithingTransformRecipe> SMITHING_TRANSFORM = register("smithing_transform", new SmithingTransformRecipe.Serializer());
/* 30 */   public static final RecipeSerializer<SmithingTrimRecipe> SMITHING_TRIM = register("smithing_trim", new SmithingTrimRecipe.Serializer());
/* 31 */   public static final RecipeSerializer<DecoratedPotRecipe> DECORATED_POT_RECIPE = register("crafting_decorated_pot", new SimpleCraftingRecipeSerializer<>(DecoratedPotRecipe::new));
/*    */   
/*    */   Codec<T> codec();
/*    */   
/*    */   T fromNetwork(FriendlyByteBuf paramFriendlyByteBuf);
/*    */   
/*    */   void toNetwork(FriendlyByteBuf paramFriendlyByteBuf, T paramT);
/*    */   
/*    */   static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String $$0, S $$1) {
/* 40 */     return (S)Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, $$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\RecipeSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */