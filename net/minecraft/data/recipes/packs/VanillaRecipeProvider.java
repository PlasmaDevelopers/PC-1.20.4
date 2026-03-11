/*      */ package net.minecraft.data.recipes.packs;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import java.util.List;
/*      */ import java.util.Optional;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.function.Function;
/*      */ import java.util.stream.Stream;
/*      */ import net.minecraft.advancements.Advancement;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.advancements.CriterionTriggerInstance;
/*      */ import net.minecraft.advancements.critereon.ImpossibleTrigger;
/*      */ import net.minecraft.advancements.critereon.InventoryChangeTrigger;
/*      */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*      */ import net.minecraft.advancements.critereon.PlayerTrigger;
/*      */ import net.minecraft.data.CachedOutput;
/*      */ import net.minecraft.data.PackOutput;
/*      */ import net.minecraft.data.recipes.RecipeBuilder;
/*      */ import net.minecraft.data.recipes.RecipeCategory;
/*      */ import net.minecraft.data.recipes.RecipeOutput;
/*      */ import net.minecraft.data.recipes.RecipeProvider;
/*      */ import net.minecraft.data.recipes.ShapedRecipeBuilder;
/*      */ import net.minecraft.data.recipes.ShapelessRecipeBuilder;
/*      */ import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
/*      */ import net.minecraft.data.recipes.SingleItemRecipeBuilder;
/*      */ import net.minecraft.data.recipes.SpecialRecipeBuilder;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.tags.ItemTags;
/*      */ import net.minecraft.world.flag.FeatureFlagSet;
/*      */ import net.minecraft.world.flag.FeatureFlags;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.crafting.AbstractCookingRecipe;
/*      */ import net.minecraft.world.item.crafting.Ingredient;
/*      */ import net.minecraft.world.item.crafting.RecipeSerializer;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class VanillaRecipeProvider
/*      */   extends RecipeProvider
/*      */ {
/*   58 */   private static final ImmutableList<ItemLike> COAL_SMELTABLES = ImmutableList.of(Items.COAL_ORE, Items.DEEPSLATE_COAL_ORE);
/*   59 */   private static final ImmutableList<ItemLike> IRON_SMELTABLES = ImmutableList.of(Items.IRON_ORE, Items.DEEPSLATE_IRON_ORE, Items.RAW_IRON);
/*   60 */   private static final ImmutableList<ItemLike> COPPER_SMELTABLES = ImmutableList.of(Items.COPPER_ORE, Items.DEEPSLATE_COPPER_ORE, Items.RAW_COPPER);
/*   61 */   private static final ImmutableList<ItemLike> GOLD_SMELTABLES = ImmutableList.of(Items.GOLD_ORE, Items.DEEPSLATE_GOLD_ORE, Items.NETHER_GOLD_ORE, Items.RAW_GOLD);
/*   62 */   private static final ImmutableList<ItemLike> DIAMOND_SMELTABLES = ImmutableList.of(Items.DIAMOND_ORE, Items.DEEPSLATE_DIAMOND_ORE);
/*   63 */   private static final ImmutableList<ItemLike> LAPIS_SMELTABLES = ImmutableList.of(Items.LAPIS_ORE, Items.DEEPSLATE_LAPIS_ORE);
/*   64 */   private static final ImmutableList<ItemLike> REDSTONE_SMELTABLES = ImmutableList.of(Items.REDSTONE_ORE, Items.DEEPSLATE_REDSTONE_ORE);
/*   65 */   private static final ImmutableList<ItemLike> EMERALD_SMELTABLES = ImmutableList.of(Items.EMERALD_ORE, Items.DEEPSLATE_EMERALD_ORE);
/*      */   
/*      */   public VanillaRecipeProvider(PackOutput $$0) {
/*   68 */     super($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public CompletableFuture<?> run(CachedOutput $$0) {
/*   73 */     return CompletableFuture.allOf((CompletableFuture<?>[])new CompletableFuture[] { super
/*   74 */           .run($$0), 
/*   75 */           buildAdvancement($$0, Advancement.Builder.recipeAdvancement().addCriterion("impossible", CriteriaTriggers.IMPOSSIBLE.createCriterion((CriterionTriggerInstance)new ImpossibleTrigger.TriggerInstance())).build(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)) });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void buildRecipes(RecipeOutput $$0) {
/*   81 */     generateForEnabledBlockFamilies($$0, FeatureFlagSet.of(FeatureFlags.VANILLA));
/*      */     
/*   83 */     planksFromLog($$0, (ItemLike)Blocks.ACACIA_PLANKS, ItemTags.ACACIA_LOGS, 4);
/*   84 */     planksFromLogs($$0, (ItemLike)Blocks.BIRCH_PLANKS, ItemTags.BIRCH_LOGS, 4);
/*   85 */     planksFromLogs($$0, (ItemLike)Blocks.CRIMSON_PLANKS, ItemTags.CRIMSON_STEMS, 4);
/*   86 */     planksFromLog($$0, (ItemLike)Blocks.DARK_OAK_PLANKS, ItemTags.DARK_OAK_LOGS, 4);
/*   87 */     planksFromLogs($$0, (ItemLike)Blocks.JUNGLE_PLANKS, ItemTags.JUNGLE_LOGS, 4);
/*   88 */     planksFromLogs($$0, (ItemLike)Blocks.OAK_PLANKS, ItemTags.OAK_LOGS, 4);
/*   89 */     planksFromLogs($$0, (ItemLike)Blocks.SPRUCE_PLANKS, ItemTags.SPRUCE_LOGS, 4);
/*   90 */     planksFromLogs($$0, (ItemLike)Blocks.WARPED_PLANKS, ItemTags.WARPED_STEMS, 4);
/*   91 */     planksFromLogs($$0, (ItemLike)Blocks.MANGROVE_PLANKS, ItemTags.MANGROVE_LOGS, 4);
/*      */     
/*   93 */     woodFromLogs($$0, (ItemLike)Blocks.ACACIA_WOOD, (ItemLike)Blocks.ACACIA_LOG);
/*   94 */     woodFromLogs($$0, (ItemLike)Blocks.BIRCH_WOOD, (ItemLike)Blocks.BIRCH_LOG);
/*   95 */     woodFromLogs($$0, (ItemLike)Blocks.DARK_OAK_WOOD, (ItemLike)Blocks.DARK_OAK_LOG);
/*   96 */     woodFromLogs($$0, (ItemLike)Blocks.JUNGLE_WOOD, (ItemLike)Blocks.JUNGLE_LOG);
/*   97 */     woodFromLogs($$0, (ItemLike)Blocks.OAK_WOOD, (ItemLike)Blocks.OAK_LOG);
/*   98 */     woodFromLogs($$0, (ItemLike)Blocks.SPRUCE_WOOD, (ItemLike)Blocks.SPRUCE_LOG);
/*   99 */     woodFromLogs($$0, (ItemLike)Blocks.CRIMSON_HYPHAE, (ItemLike)Blocks.CRIMSON_STEM);
/*  100 */     woodFromLogs($$0, (ItemLike)Blocks.WARPED_HYPHAE, (ItemLike)Blocks.WARPED_STEM);
/*  101 */     woodFromLogs($$0, (ItemLike)Blocks.MANGROVE_WOOD, (ItemLike)Blocks.MANGROVE_LOG);
/*      */     
/*  103 */     woodFromLogs($$0, (ItemLike)Blocks.STRIPPED_ACACIA_WOOD, (ItemLike)Blocks.STRIPPED_ACACIA_LOG);
/*  104 */     woodFromLogs($$0, (ItemLike)Blocks.STRIPPED_BIRCH_WOOD, (ItemLike)Blocks.STRIPPED_BIRCH_LOG);
/*  105 */     woodFromLogs($$0, (ItemLike)Blocks.STRIPPED_DARK_OAK_WOOD, (ItemLike)Blocks.STRIPPED_DARK_OAK_LOG);
/*  106 */     woodFromLogs($$0, (ItemLike)Blocks.STRIPPED_JUNGLE_WOOD, (ItemLike)Blocks.STRIPPED_JUNGLE_LOG);
/*  107 */     woodFromLogs($$0, (ItemLike)Blocks.STRIPPED_OAK_WOOD, (ItemLike)Blocks.STRIPPED_OAK_LOG);
/*  108 */     woodFromLogs($$0, (ItemLike)Blocks.STRIPPED_SPRUCE_WOOD, (ItemLike)Blocks.STRIPPED_SPRUCE_LOG);
/*  109 */     woodFromLogs($$0, (ItemLike)Blocks.STRIPPED_CRIMSON_HYPHAE, (ItemLike)Blocks.STRIPPED_CRIMSON_STEM);
/*  110 */     woodFromLogs($$0, (ItemLike)Blocks.STRIPPED_WARPED_HYPHAE, (ItemLike)Blocks.STRIPPED_WARPED_STEM);
/*  111 */     woodFromLogs($$0, (ItemLike)Blocks.STRIPPED_MANGROVE_WOOD, (ItemLike)Blocks.STRIPPED_MANGROVE_LOG);
/*      */     
/*  113 */     woodenBoat($$0, (ItemLike)Items.ACACIA_BOAT, (ItemLike)Blocks.ACACIA_PLANKS);
/*  114 */     woodenBoat($$0, (ItemLike)Items.BIRCH_BOAT, (ItemLike)Blocks.BIRCH_PLANKS);
/*  115 */     woodenBoat($$0, (ItemLike)Items.DARK_OAK_BOAT, (ItemLike)Blocks.DARK_OAK_PLANKS);
/*  116 */     woodenBoat($$0, (ItemLike)Items.JUNGLE_BOAT, (ItemLike)Blocks.JUNGLE_PLANKS);
/*  117 */     woodenBoat($$0, (ItemLike)Items.OAK_BOAT, (ItemLike)Blocks.OAK_PLANKS);
/*  118 */     woodenBoat($$0, (ItemLike)Items.SPRUCE_BOAT, (ItemLike)Blocks.SPRUCE_PLANKS);
/*  119 */     woodenBoat($$0, (ItemLike)Items.MANGROVE_BOAT, (ItemLike)Blocks.MANGROVE_PLANKS);
/*      */     
/*  121 */     List<Item> $$1 = List.of(new Item[] { Items.BLACK_DYE, Items.BLUE_DYE, Items.BROWN_DYE, Items.CYAN_DYE, Items.GRAY_DYE, Items.GREEN_DYE, Items.LIGHT_BLUE_DYE, Items.LIGHT_GRAY_DYE, Items.LIME_DYE, Items.MAGENTA_DYE, Items.ORANGE_DYE, Items.PINK_DYE, Items.PURPLE_DYE, Items.RED_DYE, Items.YELLOW_DYE, Items.WHITE_DYE });
/*      */ 
/*      */     
/*  124 */     List<Item> $$2 = List.of(new Item[] { Items.BLACK_WOOL, Items.BLUE_WOOL, Items.BROWN_WOOL, Items.CYAN_WOOL, Items.GRAY_WOOL, Items.GREEN_WOOL, Items.LIGHT_BLUE_WOOL, Items.LIGHT_GRAY_WOOL, Items.LIME_WOOL, Items.MAGENTA_WOOL, Items.ORANGE_WOOL, Items.PINK_WOOL, Items.PURPLE_WOOL, Items.RED_WOOL, Items.YELLOW_WOOL, Items.WHITE_WOOL });
/*      */ 
/*      */     
/*  127 */     List<Item> $$3 = List.of(new Item[] { Items.BLACK_BED, Items.BLUE_BED, Items.BROWN_BED, Items.CYAN_BED, Items.GRAY_BED, Items.GREEN_BED, Items.LIGHT_BLUE_BED, Items.LIGHT_GRAY_BED, Items.LIME_BED, Items.MAGENTA_BED, Items.ORANGE_BED, Items.PINK_BED, Items.PURPLE_BED, Items.RED_BED, Items.YELLOW_BED, Items.WHITE_BED });
/*      */ 
/*      */     
/*  130 */     List<Item> $$4 = List.of(new Item[] { Items.BLACK_CARPET, Items.BLUE_CARPET, Items.BROWN_CARPET, Items.CYAN_CARPET, Items.GRAY_CARPET, Items.GREEN_CARPET, Items.LIGHT_BLUE_CARPET, Items.LIGHT_GRAY_CARPET, Items.LIME_CARPET, Items.MAGENTA_CARPET, Items.ORANGE_CARPET, Items.PINK_CARPET, Items.PURPLE_CARPET, Items.RED_CARPET, Items.YELLOW_CARPET, Items.WHITE_CARPET });
/*      */ 
/*      */ 
/*      */     
/*  134 */     colorBlockWithDye($$0, $$1, $$2, "wool");
/*  135 */     colorBlockWithDye($$0, $$1, $$3, "bed");
/*  136 */     colorBlockWithDye($$0, $$1, $$4, "carpet");
/*      */     
/*  138 */     carpet($$0, (ItemLike)Blocks.BLACK_CARPET, (ItemLike)Blocks.BLACK_WOOL);
/*  139 */     bedFromPlanksAndWool($$0, (ItemLike)Items.BLACK_BED, (ItemLike)Blocks.BLACK_WOOL);
/*  140 */     banner($$0, (ItemLike)Items.BLACK_BANNER, (ItemLike)Blocks.BLACK_WOOL);
/*      */     
/*  142 */     carpet($$0, (ItemLike)Blocks.BLUE_CARPET, (ItemLike)Blocks.BLUE_WOOL);
/*  143 */     bedFromPlanksAndWool($$0, (ItemLike)Items.BLUE_BED, (ItemLike)Blocks.BLUE_WOOL);
/*  144 */     banner($$0, (ItemLike)Items.BLUE_BANNER, (ItemLike)Blocks.BLUE_WOOL);
/*      */     
/*  146 */     carpet($$0, (ItemLike)Blocks.BROWN_CARPET, (ItemLike)Blocks.BROWN_WOOL);
/*  147 */     bedFromPlanksAndWool($$0, (ItemLike)Items.BROWN_BED, (ItemLike)Blocks.BROWN_WOOL);
/*  148 */     banner($$0, (ItemLike)Items.BROWN_BANNER, (ItemLike)Blocks.BROWN_WOOL);
/*      */     
/*  150 */     carpet($$0, (ItemLike)Blocks.CYAN_CARPET, (ItemLike)Blocks.CYAN_WOOL);
/*  151 */     bedFromPlanksAndWool($$0, (ItemLike)Items.CYAN_BED, (ItemLike)Blocks.CYAN_WOOL);
/*  152 */     banner($$0, (ItemLike)Items.CYAN_BANNER, (ItemLike)Blocks.CYAN_WOOL);
/*      */     
/*  154 */     carpet($$0, (ItemLike)Blocks.GRAY_CARPET, (ItemLike)Blocks.GRAY_WOOL);
/*  155 */     bedFromPlanksAndWool($$0, (ItemLike)Items.GRAY_BED, (ItemLike)Blocks.GRAY_WOOL);
/*  156 */     banner($$0, (ItemLike)Items.GRAY_BANNER, (ItemLike)Blocks.GRAY_WOOL);
/*      */     
/*  158 */     carpet($$0, (ItemLike)Blocks.GREEN_CARPET, (ItemLike)Blocks.GREEN_WOOL);
/*  159 */     bedFromPlanksAndWool($$0, (ItemLike)Items.GREEN_BED, (ItemLike)Blocks.GREEN_WOOL);
/*  160 */     banner($$0, (ItemLike)Items.GREEN_BANNER, (ItemLike)Blocks.GREEN_WOOL);
/*      */     
/*  162 */     carpet($$0, (ItemLike)Blocks.LIGHT_BLUE_CARPET, (ItemLike)Blocks.LIGHT_BLUE_WOOL);
/*  163 */     bedFromPlanksAndWool($$0, (ItemLike)Items.LIGHT_BLUE_BED, (ItemLike)Blocks.LIGHT_BLUE_WOOL);
/*  164 */     banner($$0, (ItemLike)Items.LIGHT_BLUE_BANNER, (ItemLike)Blocks.LIGHT_BLUE_WOOL);
/*      */     
/*  166 */     carpet($$0, (ItemLike)Blocks.LIGHT_GRAY_CARPET, (ItemLike)Blocks.LIGHT_GRAY_WOOL);
/*  167 */     bedFromPlanksAndWool($$0, (ItemLike)Items.LIGHT_GRAY_BED, (ItemLike)Blocks.LIGHT_GRAY_WOOL);
/*  168 */     banner($$0, (ItemLike)Items.LIGHT_GRAY_BANNER, (ItemLike)Blocks.LIGHT_GRAY_WOOL);
/*      */     
/*  170 */     carpet($$0, (ItemLike)Blocks.LIME_CARPET, (ItemLike)Blocks.LIME_WOOL);
/*  171 */     bedFromPlanksAndWool($$0, (ItemLike)Items.LIME_BED, (ItemLike)Blocks.LIME_WOOL);
/*  172 */     banner($$0, (ItemLike)Items.LIME_BANNER, (ItemLike)Blocks.LIME_WOOL);
/*      */     
/*  174 */     carpet($$0, (ItemLike)Blocks.MAGENTA_CARPET, (ItemLike)Blocks.MAGENTA_WOOL);
/*  175 */     bedFromPlanksAndWool($$0, (ItemLike)Items.MAGENTA_BED, (ItemLike)Blocks.MAGENTA_WOOL);
/*  176 */     banner($$0, (ItemLike)Items.MAGENTA_BANNER, (ItemLike)Blocks.MAGENTA_WOOL);
/*      */     
/*  178 */     carpet($$0, (ItemLike)Blocks.ORANGE_CARPET, (ItemLike)Blocks.ORANGE_WOOL);
/*  179 */     bedFromPlanksAndWool($$0, (ItemLike)Items.ORANGE_BED, (ItemLike)Blocks.ORANGE_WOOL);
/*  180 */     banner($$0, (ItemLike)Items.ORANGE_BANNER, (ItemLike)Blocks.ORANGE_WOOL);
/*      */     
/*  182 */     carpet($$0, (ItemLike)Blocks.PINK_CARPET, (ItemLike)Blocks.PINK_WOOL);
/*  183 */     bedFromPlanksAndWool($$0, (ItemLike)Items.PINK_BED, (ItemLike)Blocks.PINK_WOOL);
/*  184 */     banner($$0, (ItemLike)Items.PINK_BANNER, (ItemLike)Blocks.PINK_WOOL);
/*      */     
/*  186 */     carpet($$0, (ItemLike)Blocks.PURPLE_CARPET, (ItemLike)Blocks.PURPLE_WOOL);
/*  187 */     bedFromPlanksAndWool($$0, (ItemLike)Items.PURPLE_BED, (ItemLike)Blocks.PURPLE_WOOL);
/*  188 */     banner($$0, (ItemLike)Items.PURPLE_BANNER, (ItemLike)Blocks.PURPLE_WOOL);
/*      */     
/*  190 */     carpet($$0, (ItemLike)Blocks.RED_CARPET, (ItemLike)Blocks.RED_WOOL);
/*  191 */     bedFromPlanksAndWool($$0, (ItemLike)Items.RED_BED, (ItemLike)Blocks.RED_WOOL);
/*  192 */     banner($$0, (ItemLike)Items.RED_BANNER, (ItemLike)Blocks.RED_WOOL);
/*      */     
/*  194 */     carpet($$0, (ItemLike)Blocks.WHITE_CARPET, (ItemLike)Blocks.WHITE_WOOL);
/*  195 */     bedFromPlanksAndWool($$0, (ItemLike)Items.WHITE_BED, (ItemLike)Blocks.WHITE_WOOL);
/*  196 */     banner($$0, (ItemLike)Items.WHITE_BANNER, (ItemLike)Blocks.WHITE_WOOL);
/*      */     
/*  198 */     carpet($$0, (ItemLike)Blocks.YELLOW_CARPET, (ItemLike)Blocks.YELLOW_WOOL);
/*  199 */     bedFromPlanksAndWool($$0, (ItemLike)Items.YELLOW_BED, (ItemLike)Blocks.YELLOW_WOOL);
/*  200 */     banner($$0, (ItemLike)Items.YELLOW_BANNER, (ItemLike)Blocks.YELLOW_WOOL);
/*      */     
/*  202 */     carpet($$0, (ItemLike)Blocks.MOSS_CARPET, (ItemLike)Blocks.MOSS_BLOCK);
/*      */     
/*  204 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.BLACK_STAINED_GLASS, (ItemLike)Items.BLACK_DYE);
/*  205 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.BLACK_STAINED_GLASS_PANE, (ItemLike)Blocks.BLACK_STAINED_GLASS);
/*  206 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.BLACK_STAINED_GLASS_PANE, (ItemLike)Items.BLACK_DYE);
/*      */     
/*  208 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.BLUE_STAINED_GLASS, (ItemLike)Items.BLUE_DYE);
/*  209 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.BLUE_STAINED_GLASS_PANE, (ItemLike)Blocks.BLUE_STAINED_GLASS);
/*  210 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.BLUE_STAINED_GLASS_PANE, (ItemLike)Items.BLUE_DYE);
/*      */     
/*  212 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.BROWN_STAINED_GLASS, (ItemLike)Items.BROWN_DYE);
/*  213 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.BROWN_STAINED_GLASS_PANE, (ItemLike)Blocks.BROWN_STAINED_GLASS);
/*  214 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.BROWN_STAINED_GLASS_PANE, (ItemLike)Items.BROWN_DYE);
/*      */     
/*  216 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.CYAN_STAINED_GLASS, (ItemLike)Items.CYAN_DYE);
/*  217 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.CYAN_STAINED_GLASS_PANE, (ItemLike)Blocks.CYAN_STAINED_GLASS);
/*  218 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.CYAN_STAINED_GLASS_PANE, (ItemLike)Items.CYAN_DYE);
/*      */     
/*  220 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.GRAY_STAINED_GLASS, (ItemLike)Items.GRAY_DYE);
/*  221 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.GRAY_STAINED_GLASS_PANE, (ItemLike)Blocks.GRAY_STAINED_GLASS);
/*  222 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.GRAY_STAINED_GLASS_PANE, (ItemLike)Items.GRAY_DYE);
/*      */     
/*  224 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.GREEN_STAINED_GLASS, (ItemLike)Items.GREEN_DYE);
/*  225 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.GREEN_STAINED_GLASS_PANE, (ItemLike)Blocks.GREEN_STAINED_GLASS);
/*  226 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.GREEN_STAINED_GLASS_PANE, (ItemLike)Items.GREEN_DYE);
/*      */     
/*  228 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.LIGHT_BLUE_STAINED_GLASS, (ItemLike)Items.LIGHT_BLUE_DYE);
/*  229 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, (ItemLike)Blocks.LIGHT_BLUE_STAINED_GLASS);
/*  230 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, (ItemLike)Items.LIGHT_BLUE_DYE);
/*      */     
/*  232 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.LIGHT_GRAY_STAINED_GLASS, (ItemLike)Items.LIGHT_GRAY_DYE);
/*  233 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, (ItemLike)Blocks.LIGHT_GRAY_STAINED_GLASS);
/*  234 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, (ItemLike)Items.LIGHT_GRAY_DYE);
/*      */     
/*  236 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.LIME_STAINED_GLASS, (ItemLike)Items.LIME_DYE);
/*  237 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.LIME_STAINED_GLASS_PANE, (ItemLike)Blocks.LIME_STAINED_GLASS);
/*  238 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.LIME_STAINED_GLASS_PANE, (ItemLike)Items.LIME_DYE);
/*      */     
/*  240 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.MAGENTA_STAINED_GLASS, (ItemLike)Items.MAGENTA_DYE);
/*  241 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.MAGENTA_STAINED_GLASS_PANE, (ItemLike)Blocks.MAGENTA_STAINED_GLASS);
/*  242 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.MAGENTA_STAINED_GLASS_PANE, (ItemLike)Items.MAGENTA_DYE);
/*      */     
/*  244 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.ORANGE_STAINED_GLASS, (ItemLike)Items.ORANGE_DYE);
/*  245 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.ORANGE_STAINED_GLASS_PANE, (ItemLike)Blocks.ORANGE_STAINED_GLASS);
/*  246 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.ORANGE_STAINED_GLASS_PANE, (ItemLike)Items.ORANGE_DYE);
/*      */     
/*  248 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.PINK_STAINED_GLASS, (ItemLike)Items.PINK_DYE);
/*  249 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.PINK_STAINED_GLASS_PANE, (ItemLike)Blocks.PINK_STAINED_GLASS);
/*  250 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.PINK_STAINED_GLASS_PANE, (ItemLike)Items.PINK_DYE);
/*      */     
/*  252 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.PURPLE_STAINED_GLASS, (ItemLike)Items.PURPLE_DYE);
/*  253 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.PURPLE_STAINED_GLASS_PANE, (ItemLike)Blocks.PURPLE_STAINED_GLASS);
/*  254 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.PURPLE_STAINED_GLASS_PANE, (ItemLike)Items.PURPLE_DYE);
/*      */     
/*  256 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.RED_STAINED_GLASS, (ItemLike)Items.RED_DYE);
/*  257 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.RED_STAINED_GLASS_PANE, (ItemLike)Blocks.RED_STAINED_GLASS);
/*  258 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.RED_STAINED_GLASS_PANE, (ItemLike)Items.RED_DYE);
/*      */     
/*  260 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.WHITE_STAINED_GLASS, (ItemLike)Items.WHITE_DYE);
/*  261 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.WHITE_STAINED_GLASS_PANE, (ItemLike)Blocks.WHITE_STAINED_GLASS);
/*  262 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.WHITE_STAINED_GLASS_PANE, (ItemLike)Items.WHITE_DYE);
/*      */     
/*  264 */     stainedGlassFromGlassAndDye($$0, (ItemLike)Blocks.YELLOW_STAINED_GLASS, (ItemLike)Items.YELLOW_DYE);
/*  265 */     stainedGlassPaneFromStainedGlass($$0, (ItemLike)Blocks.YELLOW_STAINED_GLASS_PANE, (ItemLike)Blocks.YELLOW_STAINED_GLASS);
/*  266 */     stainedGlassPaneFromGlassPaneAndDye($$0, (ItemLike)Blocks.YELLOW_STAINED_GLASS_PANE, (ItemLike)Items.YELLOW_DYE);
/*      */     
/*  268 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.BLACK_TERRACOTTA, (ItemLike)Items.BLACK_DYE);
/*  269 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.BLUE_TERRACOTTA, (ItemLike)Items.BLUE_DYE);
/*  270 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.BROWN_TERRACOTTA, (ItemLike)Items.BROWN_DYE);
/*  271 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.CYAN_TERRACOTTA, (ItemLike)Items.CYAN_DYE);
/*  272 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.GRAY_TERRACOTTA, (ItemLike)Items.GRAY_DYE);
/*  273 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.GREEN_TERRACOTTA, (ItemLike)Items.GREEN_DYE);
/*  274 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.LIGHT_BLUE_TERRACOTTA, (ItemLike)Items.LIGHT_BLUE_DYE);
/*  275 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.LIGHT_GRAY_TERRACOTTA, (ItemLike)Items.LIGHT_GRAY_DYE);
/*  276 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.LIME_TERRACOTTA, (ItemLike)Items.LIME_DYE);
/*  277 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.MAGENTA_TERRACOTTA, (ItemLike)Items.MAGENTA_DYE);
/*  278 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.ORANGE_TERRACOTTA, (ItemLike)Items.ORANGE_DYE);
/*  279 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.PINK_TERRACOTTA, (ItemLike)Items.PINK_DYE);
/*  280 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.PURPLE_TERRACOTTA, (ItemLike)Items.PURPLE_DYE);
/*  281 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.RED_TERRACOTTA, (ItemLike)Items.RED_DYE);
/*  282 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.WHITE_TERRACOTTA, (ItemLike)Items.WHITE_DYE);
/*  283 */     coloredTerracottaFromTerracottaAndDye($$0, (ItemLike)Blocks.YELLOW_TERRACOTTA, (ItemLike)Items.YELLOW_DYE);
/*      */     
/*  285 */     concretePowder($$0, (ItemLike)Blocks.BLACK_CONCRETE_POWDER, (ItemLike)Items.BLACK_DYE);
/*  286 */     concretePowder($$0, (ItemLike)Blocks.BLUE_CONCRETE_POWDER, (ItemLike)Items.BLUE_DYE);
/*  287 */     concretePowder($$0, (ItemLike)Blocks.BROWN_CONCRETE_POWDER, (ItemLike)Items.BROWN_DYE);
/*  288 */     concretePowder($$0, (ItemLike)Blocks.CYAN_CONCRETE_POWDER, (ItemLike)Items.CYAN_DYE);
/*  289 */     concretePowder($$0, (ItemLike)Blocks.GRAY_CONCRETE_POWDER, (ItemLike)Items.GRAY_DYE);
/*  290 */     concretePowder($$0, (ItemLike)Blocks.GREEN_CONCRETE_POWDER, (ItemLike)Items.GREEN_DYE);
/*  291 */     concretePowder($$0, (ItemLike)Blocks.LIGHT_BLUE_CONCRETE_POWDER, (ItemLike)Items.LIGHT_BLUE_DYE);
/*  292 */     concretePowder($$0, (ItemLike)Blocks.LIGHT_GRAY_CONCRETE_POWDER, (ItemLike)Items.LIGHT_GRAY_DYE);
/*  293 */     concretePowder($$0, (ItemLike)Blocks.LIME_CONCRETE_POWDER, (ItemLike)Items.LIME_DYE);
/*  294 */     concretePowder($$0, (ItemLike)Blocks.MAGENTA_CONCRETE_POWDER, (ItemLike)Items.MAGENTA_DYE);
/*  295 */     concretePowder($$0, (ItemLike)Blocks.ORANGE_CONCRETE_POWDER, (ItemLike)Items.ORANGE_DYE);
/*  296 */     concretePowder($$0, (ItemLike)Blocks.PINK_CONCRETE_POWDER, (ItemLike)Items.PINK_DYE);
/*  297 */     concretePowder($$0, (ItemLike)Blocks.PURPLE_CONCRETE_POWDER, (ItemLike)Items.PURPLE_DYE);
/*  298 */     concretePowder($$0, (ItemLike)Blocks.RED_CONCRETE_POWDER, (ItemLike)Items.RED_DYE);
/*  299 */     concretePowder($$0, (ItemLike)Blocks.WHITE_CONCRETE_POWDER, (ItemLike)Items.WHITE_DYE);
/*  300 */     concretePowder($$0, (ItemLike)Blocks.YELLOW_CONCRETE_POWDER, (ItemLike)Items.YELLOW_DYE);
/*      */     
/*  302 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Items.CANDLE)
/*  303 */       .define(Character.valueOf('S'), (ItemLike)Items.STRING)
/*  304 */       .define(Character.valueOf('H'), (ItemLike)Items.HONEYCOMB)
/*  305 */       .pattern("S")
/*  306 */       .pattern("H")
/*  307 */       .unlockedBy("has_string", has((ItemLike)Items.STRING))
/*  308 */       .unlockedBy("has_honeycomb", has((ItemLike)Items.HONEYCOMB))
/*  309 */       .save($$0);
/*      */     
/*  311 */     candle($$0, (ItemLike)Blocks.BLACK_CANDLE, (ItemLike)Items.BLACK_DYE);
/*  312 */     candle($$0, (ItemLike)Blocks.BLUE_CANDLE, (ItemLike)Items.BLUE_DYE);
/*  313 */     candle($$0, (ItemLike)Blocks.BROWN_CANDLE, (ItemLike)Items.BROWN_DYE);
/*  314 */     candle($$0, (ItemLike)Blocks.CYAN_CANDLE, (ItemLike)Items.CYAN_DYE);
/*  315 */     candle($$0, (ItemLike)Blocks.GRAY_CANDLE, (ItemLike)Items.GRAY_DYE);
/*  316 */     candle($$0, (ItemLike)Blocks.GREEN_CANDLE, (ItemLike)Items.GREEN_DYE);
/*  317 */     candle($$0, (ItemLike)Blocks.LIGHT_BLUE_CANDLE, (ItemLike)Items.LIGHT_BLUE_DYE);
/*  318 */     candle($$0, (ItemLike)Blocks.LIGHT_GRAY_CANDLE, (ItemLike)Items.LIGHT_GRAY_DYE);
/*  319 */     candle($$0, (ItemLike)Blocks.LIME_CANDLE, (ItemLike)Items.LIME_DYE);
/*  320 */     candle($$0, (ItemLike)Blocks.MAGENTA_CANDLE, (ItemLike)Items.MAGENTA_DYE);
/*  321 */     candle($$0, (ItemLike)Blocks.ORANGE_CANDLE, (ItemLike)Items.ORANGE_DYE);
/*  322 */     candle($$0, (ItemLike)Blocks.PINK_CANDLE, (ItemLike)Items.PINK_DYE);
/*  323 */     candle($$0, (ItemLike)Blocks.PURPLE_CANDLE, (ItemLike)Items.PURPLE_DYE);
/*  324 */     candle($$0, (ItemLike)Blocks.RED_CANDLE, (ItemLike)Items.RED_DYE);
/*  325 */     candle($$0, (ItemLike)Blocks.WHITE_CANDLE, (ItemLike)Items.WHITE_DYE);
/*  326 */     candle($$0, (ItemLike)Blocks.YELLOW_CANDLE, (ItemLike)Items.YELLOW_DYE);
/*      */     
/*  328 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PACKED_MUD, 1)
/*  329 */       .requires((ItemLike)Blocks.MUD)
/*  330 */       .requires((ItemLike)Items.WHEAT)
/*  331 */       .unlockedBy("has_mud", has((ItemLike)Blocks.MUD))
/*  332 */       .save($$0);
/*      */     
/*  334 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MUD_BRICKS, 4)
/*  335 */       .define(Character.valueOf('#'), (ItemLike)Blocks.PACKED_MUD)
/*  336 */       .pattern("##")
/*  337 */       .pattern("##")
/*  338 */       .unlockedBy("has_packed_mud", has((ItemLike)Blocks.PACKED_MUD))
/*  339 */       .save($$0);
/*      */     
/*  341 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MUDDY_MANGROVE_ROOTS, 1)
/*  342 */       .requires((ItemLike)Blocks.MUD)
/*  343 */       .requires((ItemLike)Items.MANGROVE_ROOTS)
/*  344 */       .unlockedBy("has_mangrove_roots", has((ItemLike)Blocks.MANGROVE_ROOTS))
/*  345 */       .save($$0);
/*      */     
/*  347 */     ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, (ItemLike)Blocks.ACTIVATOR_RAIL, 6)
/*  348 */       .define(Character.valueOf('#'), (ItemLike)Blocks.REDSTONE_TORCH)
/*  349 */       .define(Character.valueOf('S'), (ItemLike)Items.STICK)
/*  350 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_INGOT)
/*  351 */       .pattern("XSX")
/*  352 */       .pattern("X#X")
/*  353 */       .pattern("XSX")
/*  354 */       .unlockedBy("has_rail", has((ItemLike)Blocks.RAIL))
/*  355 */       .save($$0);
/*      */     
/*  357 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.ANDESITE, 2)
/*  358 */       .requires((ItemLike)Blocks.DIORITE)
/*  359 */       .requires((ItemLike)Blocks.COBBLESTONE)
/*  360 */       .unlockedBy("has_stone", has((ItemLike)Blocks.DIORITE))
/*  361 */       .save($$0);
/*      */     
/*  363 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.ANVIL)
/*  364 */       .define(Character.valueOf('I'), (ItemLike)Blocks.IRON_BLOCK)
/*  365 */       .define(Character.valueOf('i'), (ItemLike)Items.IRON_INGOT)
/*  366 */       .pattern("III")
/*  367 */       .pattern(" i ")
/*  368 */       .pattern("iii")
/*  369 */       .unlockedBy("has_iron_block", has((ItemLike)Blocks.IRON_BLOCK))
/*  370 */       .save($$0);
/*      */     
/*  372 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Items.ARMOR_STAND)
/*  373 */       .define(Character.valueOf('/'), (ItemLike)Items.STICK)
/*  374 */       .define(Character.valueOf('_'), (ItemLike)Blocks.SMOOTH_STONE_SLAB)
/*  375 */       .pattern("///")
/*  376 */       .pattern(" / ")
/*  377 */       .pattern("/_/")
/*  378 */       .unlockedBy("has_stone_slab", has((ItemLike)Blocks.SMOOTH_STONE_SLAB))
/*  379 */       .save($$0);
/*      */     
/*  381 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.ARROW, 4)
/*  382 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/*  383 */       .define(Character.valueOf('X'), (ItemLike)Items.FLINT)
/*  384 */       .define(Character.valueOf('Y'), (ItemLike)Items.FEATHER)
/*  385 */       .pattern("X")
/*  386 */       .pattern("#")
/*  387 */       .pattern("Y")
/*  388 */       .unlockedBy("has_feather", has((ItemLike)Items.FEATHER))
/*  389 */       .unlockedBy("has_flint", has((ItemLike)Items.FLINT))
/*  390 */       .save($$0);
/*      */     
/*  392 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.BARREL, 1)
/*  393 */       .define(Character.valueOf('P'), ItemTags.PLANKS)
/*  394 */       .define(Character.valueOf('S'), ItemTags.WOODEN_SLABS)
/*  395 */       .pattern("PSP")
/*  396 */       .pattern("P P")
/*  397 */       .pattern("PSP")
/*  398 */       .unlockedBy("has_planks", has(ItemTags.PLANKS))
/*  399 */       .unlockedBy("has_wood_slab", has(ItemTags.WOODEN_SLABS))
/*  400 */       .save($$0);
/*      */     
/*  402 */     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)Blocks.BEACON)
/*  403 */       .define(Character.valueOf('S'), (ItemLike)Items.NETHER_STAR)
/*  404 */       .define(Character.valueOf('G'), (ItemLike)Blocks.GLASS)
/*  405 */       .define(Character.valueOf('O'), (ItemLike)Blocks.OBSIDIAN)
/*  406 */       .pattern("GGG")
/*  407 */       .pattern("GSG")
/*  408 */       .pattern("OOO")
/*  409 */       .unlockedBy("has_nether_star", has((ItemLike)Items.NETHER_STAR))
/*  410 */       .save($$0);
/*      */     
/*  412 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.BEEHIVE)
/*  413 */       .define(Character.valueOf('P'), ItemTags.PLANKS)
/*  414 */       .define(Character.valueOf('H'), (ItemLike)Items.HONEYCOMB)
/*  415 */       .pattern("PPP")
/*  416 */       .pattern("HHH")
/*  417 */       .pattern("PPP")
/*  418 */       .unlockedBy("has_honeycomb", has((ItemLike)Items.HONEYCOMB))
/*  419 */       .save($$0);
/*      */     
/*  421 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, (ItemLike)Items.BEETROOT_SOUP)
/*  422 */       .requires((ItemLike)Items.BOWL)
/*  423 */       .requires((ItemLike)Items.BEETROOT, 6)
/*  424 */       .unlockedBy("has_beetroot", has((ItemLike)Items.BEETROOT))
/*  425 */       .save($$0);
/*      */     
/*  427 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.BLACK_DYE)
/*  428 */       .requires((ItemLike)Items.INK_SAC)
/*  429 */       .group("black_dye")
/*  430 */       .unlockedBy("has_ink_sac", has((ItemLike)Items.INK_SAC))
/*  431 */       .save($$0);
/*      */     
/*  433 */     oneToOneConversionRecipe($$0, (ItemLike)Items.BLACK_DYE, (ItemLike)Blocks.WITHER_ROSE, "black_dye");
/*      */     
/*  435 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, (ItemLike)Items.BLAZE_POWDER, 2)
/*  436 */       .requires((ItemLike)Items.BLAZE_ROD)
/*  437 */       .unlockedBy("has_blaze_rod", has((ItemLike)Items.BLAZE_ROD))
/*  438 */       .save($$0);
/*      */     
/*  440 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.BLUE_DYE)
/*  441 */       .requires((ItemLike)Items.LAPIS_LAZULI)
/*  442 */       .group("blue_dye")
/*  443 */       .unlockedBy("has_lapis_lazuli", has((ItemLike)Items.LAPIS_LAZULI))
/*  444 */       .save($$0);
/*      */     
/*  446 */     oneToOneConversionRecipe($$0, (ItemLike)Items.BLUE_DYE, (ItemLike)Blocks.CORNFLOWER, "blue_dye");
/*      */     
/*  448 */     threeByThreePacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.BLUE_ICE, (ItemLike)Blocks.PACKED_ICE);
/*      */     
/*  450 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.BONE_MEAL, 3)
/*  451 */       .requires((ItemLike)Items.BONE)
/*  452 */       .group("bonemeal")
/*  453 */       .unlockedBy("has_bone", has((ItemLike)Items.BONE))
/*  454 */       .save($$0);
/*      */     
/*  456 */     nineBlockStorageRecipesRecipesWithCustomUnpacking($$0, RecipeCategory.MISC, (ItemLike)Items.BONE_MEAL, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Items.BONE_BLOCK, "bone_meal_from_bone_block", "bonemeal");
/*      */     
/*  458 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.BOOK)
/*  459 */       .requires((ItemLike)Items.PAPER, 3)
/*  460 */       .requires((ItemLike)Items.LEATHER)
/*  461 */       .unlockedBy("has_paper", has((ItemLike)Items.PAPER))
/*  462 */       .save($$0);
/*      */     
/*  464 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.BOOKSHELF)
/*  465 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/*  466 */       .define(Character.valueOf('X'), (ItemLike)Items.BOOK)
/*  467 */       .pattern("###")
/*  468 */       .pattern("XXX")
/*  469 */       .pattern("###")
/*  470 */       .unlockedBy("has_book", has((ItemLike)Items.BOOK))
/*  471 */       .save($$0);
/*      */     
/*  473 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.BOW)
/*  474 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/*  475 */       .define(Character.valueOf('X'), (ItemLike)Items.STRING)
/*  476 */       .pattern(" #X")
/*  477 */       .pattern("# X")
/*  478 */       .pattern(" #X")
/*  479 */       .unlockedBy("has_string", has((ItemLike)Items.STRING))
/*  480 */       .save($$0);
/*      */     
/*  482 */     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)Items.BOWL, 4)
/*  483 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/*  484 */       .pattern("# #")
/*  485 */       .pattern(" # ")
/*  486 */       .unlockedBy("has_brown_mushroom", has((ItemLike)Blocks.BROWN_MUSHROOM))
/*  487 */       .unlockedBy("has_red_mushroom", has((ItemLike)Blocks.RED_MUSHROOM))
/*  488 */       .unlockedBy("has_mushroom_stew", has((ItemLike)Items.MUSHROOM_STEW))
/*  489 */       .save($$0);
/*      */     
/*  491 */     ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, (ItemLike)Items.BREAD)
/*  492 */       .define(Character.valueOf('#'), (ItemLike)Items.WHEAT)
/*  493 */       .pattern("###")
/*  494 */       .unlockedBy("has_wheat", has((ItemLike)Items.WHEAT))
/*  495 */       .save($$0);
/*      */     
/*  497 */     ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, (ItemLike)Blocks.BREWING_STAND)
/*  498 */       .define(Character.valueOf('B'), (ItemLike)Items.BLAZE_ROD)
/*  499 */       .define(Character.valueOf('#'), ItemTags.STONE_CRAFTING_MATERIALS)
/*  500 */       .pattern(" B ")
/*  501 */       .pattern("###")
/*  502 */       .unlockedBy("has_blaze_rod", has((ItemLike)Items.BLAZE_ROD))
/*  503 */       .save($$0);
/*      */     
/*  505 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.BRICKS)
/*  506 */       .define(Character.valueOf('#'), (ItemLike)Items.BRICK)
/*  507 */       .pattern("##")
/*  508 */       .pattern("##")
/*  509 */       .unlockedBy("has_brick", has((ItemLike)Items.BRICK))
/*  510 */       .save($$0);
/*      */     
/*  512 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.BROWN_DYE)
/*  513 */       .requires((ItemLike)Items.COCOA_BEANS)
/*  514 */       .group("brown_dye")
/*  515 */       .unlockedBy("has_cocoa_beans", has((ItemLike)Items.COCOA_BEANS))
/*  516 */       .save($$0);
/*      */     
/*  518 */     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)Items.BUCKET)
/*  519 */       .define(Character.valueOf('#'), (ItemLike)Items.IRON_INGOT)
/*  520 */       .pattern("# #")
/*  521 */       .pattern(" # ")
/*  522 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/*  523 */       .save($$0);
/*      */     
/*  525 */     ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, (ItemLike)Blocks.CAKE)
/*  526 */       .define(Character.valueOf('A'), (ItemLike)Items.MILK_BUCKET)
/*  527 */       .define(Character.valueOf('B'), (ItemLike)Items.SUGAR)
/*  528 */       .define(Character.valueOf('C'), (ItemLike)Items.WHEAT)
/*  529 */       .define(Character.valueOf('E'), (ItemLike)Items.EGG)
/*  530 */       .pattern("AAA")
/*  531 */       .pattern("BEB")
/*  532 */       .pattern("CCC")
/*  533 */       .unlockedBy("has_egg", has((ItemLike)Items.EGG))
/*  534 */       .save($$0);
/*      */     
/*  536 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.CAMPFIRE)
/*  537 */       .define(Character.valueOf('L'), ItemTags.LOGS)
/*  538 */       .define(Character.valueOf('S'), (ItemLike)Items.STICK)
/*  539 */       .define(Character.valueOf('C'), ItemTags.COALS)
/*  540 */       .pattern(" S ")
/*  541 */       .pattern("SCS")
/*  542 */       .pattern("LLL")
/*  543 */       .unlockedBy("has_stick", has((ItemLike)Items.STICK))
/*  544 */       .unlockedBy("has_coal", has(ItemTags.COALS))
/*  545 */       .save($$0);
/*      */     
/*  547 */     ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, (ItemLike)Items.CARROT_ON_A_STICK)
/*  548 */       .define(Character.valueOf('#'), (ItemLike)Items.FISHING_ROD)
/*  549 */       .define(Character.valueOf('X'), (ItemLike)Items.CARROT)
/*  550 */       .pattern("# ")
/*  551 */       .pattern(" X")
/*  552 */       .unlockedBy("has_carrot", has((ItemLike)Items.CARROT))
/*  553 */       .save($$0);
/*      */     
/*  555 */     ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, (ItemLike)Items.WARPED_FUNGUS_ON_A_STICK)
/*  556 */       .define(Character.valueOf('#'), (ItemLike)Items.FISHING_ROD)
/*  557 */       .define(Character.valueOf('X'), (ItemLike)Items.WARPED_FUNGUS)
/*  558 */       .pattern("# ")
/*  559 */       .pattern(" X")
/*  560 */       .unlockedBy("has_warped_fungus", has((ItemLike)Items.WARPED_FUNGUS))
/*  561 */       .save($$0);
/*      */     
/*  563 */     ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, (ItemLike)Blocks.CAULDRON)
/*  564 */       .define(Character.valueOf('#'), (ItemLike)Items.IRON_INGOT)
/*  565 */       .pattern("# #")
/*  566 */       .pattern("# #")
/*  567 */       .pattern("###")
/*  568 */       .unlockedBy("has_water_bucket", has((ItemLike)Items.WATER_BUCKET))
/*  569 */       .save($$0);
/*      */     
/*  571 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.COMPOSTER)
/*  572 */       .define(Character.valueOf('#'), ItemTags.WOODEN_SLABS)
/*  573 */       .pattern("# #")
/*  574 */       .pattern("# #")
/*  575 */       .pattern("###")
/*  576 */       .unlockedBy("has_wood_slab", has(ItemTags.WOODEN_SLABS))
/*  577 */       .save($$0);
/*      */     
/*  579 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.CHEST)
/*  580 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/*  581 */       .pattern("###")
/*  582 */       .pattern("# #")
/*  583 */       .pattern("###")
/*  584 */       .unlockedBy("has_lots_of_items", CriteriaTriggers.INVENTORY_CHANGED.createCriterion((CriterionTriggerInstance)new InventoryChangeTrigger.TriggerInstance(Optional.empty(), new InventoryChangeTrigger.TriggerInstance.Slots(MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY), List.of())))
/*  585 */       .save($$0);
/*      */     
/*  587 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, (ItemLike)Items.CHEST_MINECART)
/*  588 */       .requires((ItemLike)Blocks.CHEST)
/*  589 */       .requires((ItemLike)Items.MINECART)
/*  590 */       .unlockedBy("has_minecart", has((ItemLike)Items.MINECART))
/*  591 */       .save($$0);
/*      */     
/*  593 */     chestBoat($$0, (ItemLike)Items.ACACIA_CHEST_BOAT, (ItemLike)Items.ACACIA_BOAT);
/*  594 */     chestBoat($$0, (ItemLike)Items.BIRCH_CHEST_BOAT, (ItemLike)Items.BIRCH_BOAT);
/*  595 */     chestBoat($$0, (ItemLike)Items.DARK_OAK_CHEST_BOAT, (ItemLike)Items.DARK_OAK_BOAT);
/*  596 */     chestBoat($$0, (ItemLike)Items.JUNGLE_CHEST_BOAT, (ItemLike)Items.JUNGLE_BOAT);
/*  597 */     chestBoat($$0, (ItemLike)Items.OAK_CHEST_BOAT, (ItemLike)Items.OAK_BOAT);
/*  598 */     chestBoat($$0, (ItemLike)Items.SPRUCE_CHEST_BOAT, (ItemLike)Items.SPRUCE_BOAT);
/*  599 */     chestBoat($$0, (ItemLike)Items.MANGROVE_CHEST_BOAT, (ItemLike)Items.MANGROVE_BOAT);
/*      */     
/*  601 */     chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_QUARTZ_BLOCK, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.QUARTZ_SLAB
/*  602 */           })).unlockedBy("has_chiseled_quartz_block", has((ItemLike)Blocks.CHISELED_QUARTZ_BLOCK))
/*  603 */       .unlockedBy("has_quartz_block", has((ItemLike)Blocks.QUARTZ_BLOCK))
/*  604 */       .unlockedBy("has_quartz_pillar", has((ItemLike)Blocks.QUARTZ_PILLAR))
/*  605 */       .save($$0);
/*      */     
/*  607 */     chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_STONE_BRICKS, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.STONE_BRICK_SLAB
/*  608 */           })).unlockedBy("has_tag", has(ItemTags.STONE_BRICKS))
/*  609 */       .save($$0);
/*      */     
/*  611 */     twoByTwoPacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CLAY, (ItemLike)Items.CLAY_BALL);
/*      */     
/*  613 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.CLOCK)
/*  614 */       .define(Character.valueOf('#'), (ItemLike)Items.GOLD_INGOT)
/*  615 */       .define(Character.valueOf('X'), (ItemLike)Items.REDSTONE)
/*  616 */       .pattern(" # ")
/*  617 */       .pattern("#X#")
/*  618 */       .pattern(" # ")
/*  619 */       .unlockedBy("has_redstone", has((ItemLike)Items.REDSTONE))
/*  620 */       .save($$0);
/*      */     
/*  622 */     nineBlockStorageRecipes($$0, RecipeCategory.MISC, (ItemLike)Items.COAL, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Items.COAL_BLOCK);
/*      */     
/*  624 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.COARSE_DIRT, 4)
/*  625 */       .define(Character.valueOf('D'), (ItemLike)Blocks.DIRT)
/*  626 */       .define(Character.valueOf('G'), (ItemLike)Blocks.GRAVEL)
/*  627 */       .pattern("DG")
/*  628 */       .pattern("GD")
/*  629 */       .unlockedBy("has_gravel", has((ItemLike)Blocks.GRAVEL))
/*  630 */       .save($$0);
/*      */     
/*  632 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.COMPARATOR)
/*  633 */       .define(Character.valueOf('#'), (ItemLike)Blocks.REDSTONE_TORCH)
/*  634 */       .define(Character.valueOf('X'), (ItemLike)Items.QUARTZ)
/*  635 */       .define(Character.valueOf('I'), (ItemLike)Blocks.STONE)
/*  636 */       .pattern(" # ")
/*  637 */       .pattern("#X#")
/*  638 */       .pattern("III")
/*  639 */       .unlockedBy("has_quartz", has((ItemLike)Items.QUARTZ))
/*  640 */       .save($$0);
/*      */     
/*  642 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.COMPASS)
/*  643 */       .define(Character.valueOf('#'), (ItemLike)Items.IRON_INGOT)
/*  644 */       .define(Character.valueOf('X'), (ItemLike)Items.REDSTONE)
/*  645 */       .pattern(" # ")
/*  646 */       .pattern("#X#")
/*  647 */       .pattern(" # ")
/*  648 */       .unlockedBy("has_redstone", has((ItemLike)Items.REDSTONE))
/*  649 */       .save($$0);
/*      */     
/*  651 */     ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, (ItemLike)Items.COOKIE, 8)
/*  652 */       .define(Character.valueOf('#'), (ItemLike)Items.WHEAT)
/*  653 */       .define(Character.valueOf('X'), (ItemLike)Items.COCOA_BEANS)
/*  654 */       .pattern("#X#")
/*  655 */       .unlockedBy("has_cocoa", has((ItemLike)Items.COCOA_BEANS))
/*  656 */       .save($$0);
/*      */     
/*  658 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.CRAFTING_TABLE)
/*  659 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/*  660 */       .pattern("##")
/*  661 */       .pattern("##")
/*  662 */       .unlockedBy("unlock_right_away", PlayerTrigger.TriggerInstance.tick())
/*  663 */       .showNotification(false)
/*  664 */       .save($$0);
/*      */     
/*  666 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.CROSSBOW)
/*  667 */       .define(Character.valueOf('~'), (ItemLike)Items.STRING)
/*  668 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/*  669 */       .define(Character.valueOf('&'), (ItemLike)Items.IRON_INGOT)
/*  670 */       .define(Character.valueOf('$'), (ItemLike)Blocks.TRIPWIRE_HOOK)
/*  671 */       .pattern("#&#")
/*  672 */       .pattern("~$~")
/*  673 */       .pattern(" # ")
/*  674 */       .unlockedBy("has_string", has((ItemLike)Items.STRING))
/*  675 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/*  676 */       .unlockedBy("has_tripwire_hook", has((ItemLike)Blocks.TRIPWIRE_HOOK))
/*  677 */       .save($$0);
/*      */     
/*  679 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.LOOM)
/*  680 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/*  681 */       .define(Character.valueOf('@'), (ItemLike)Items.STRING)
/*  682 */       .pattern("@@")
/*  683 */       .pattern("##")
/*  684 */       .unlockedBy("has_string", has((ItemLike)Items.STRING))
/*  685 */       .save($$0);
/*      */     
/*  687 */     chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_RED_SANDSTONE, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.RED_SANDSTONE_SLAB
/*  688 */           })).unlockedBy("has_red_sandstone", has((ItemLike)Blocks.RED_SANDSTONE))
/*  689 */       .unlockedBy("has_chiseled_red_sandstone", has((ItemLike)Blocks.CHISELED_RED_SANDSTONE))
/*  690 */       .unlockedBy("has_cut_red_sandstone", has((ItemLike)Blocks.CUT_RED_SANDSTONE))
/*  691 */       .save($$0);
/*      */     
/*  693 */     chiseled($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_SANDSTONE, (ItemLike)Blocks.SANDSTONE_SLAB);
/*      */     
/*  695 */     nineBlockStorageRecipesRecipesWithCustomUnpacking($$0, RecipeCategory.MISC, (ItemLike)Items.COPPER_INGOT, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Items.COPPER_BLOCK, getSimpleRecipeName((ItemLike)Items.COPPER_INGOT), getItemName((ItemLike)Items.COPPER_INGOT));
/*      */     
/*  697 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.COPPER_INGOT, 9)
/*  698 */       .requires((ItemLike)Blocks.WAXED_COPPER_BLOCK)
/*  699 */       .group(getItemName((ItemLike)Items.COPPER_INGOT))
/*  700 */       .unlockedBy(getHasName((ItemLike)Blocks.WAXED_COPPER_BLOCK), has((ItemLike)Blocks.WAXED_COPPER_BLOCK))
/*  701 */       .save($$0, getConversionRecipeName((ItemLike)Items.COPPER_INGOT, (ItemLike)Blocks.WAXED_COPPER_BLOCK));
/*      */     
/*  703 */     waxRecipes($$0, FeatureFlagSet.of(FeatureFlags.VANILLA));
/*      */     
/*  705 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.CYAN_DYE, 2)
/*  706 */       .requires((ItemLike)Items.BLUE_DYE)
/*  707 */       .requires((ItemLike)Items.GREEN_DYE)
/*  708 */       .group("cyan_dye")
/*  709 */       .unlockedBy("has_green_dye", has((ItemLike)Items.GREEN_DYE))
/*  710 */       .unlockedBy("has_blue_dye", has((ItemLike)Items.BLUE_DYE))
/*  711 */       .save($$0);
/*      */     
/*  713 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DARK_PRISMARINE)
/*  714 */       .define(Character.valueOf('S'), (ItemLike)Items.PRISMARINE_SHARD)
/*  715 */       .define(Character.valueOf('I'), (ItemLike)Items.BLACK_DYE)
/*  716 */       .pattern("SSS")
/*  717 */       .pattern("SIS")
/*  718 */       .pattern("SSS")
/*  719 */       .unlockedBy("has_prismarine_shard", has((ItemLike)Items.PRISMARINE_SHARD))
/*  720 */       .save($$0);
/*      */     
/*  722 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.DAYLIGHT_DETECTOR)
/*  723 */       .define(Character.valueOf('Q'), (ItemLike)Items.QUARTZ)
/*  724 */       .define(Character.valueOf('G'), (ItemLike)Blocks.GLASS)
/*  725 */       .define(Character.valueOf('W'), Ingredient.of(ItemTags.WOODEN_SLABS))
/*  726 */       .pattern("GGG")
/*  727 */       .pattern("QQQ")
/*  728 */       .pattern("WWW")
/*  729 */       .unlockedBy("has_quartz", has((ItemLike)Items.QUARTZ))
/*  730 */       .save($$0);
/*      */     
/*  732 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_BRICKS, 4)
/*  733 */       .define(Character.valueOf('S'), (ItemLike)Blocks.POLISHED_DEEPSLATE)
/*  734 */       .pattern("SS")
/*  735 */       .pattern("SS")
/*  736 */       .unlockedBy("has_polished_deepslate", has((ItemLike)Blocks.POLISHED_DEEPSLATE))
/*  737 */       .save($$0);
/*      */     
/*  739 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_TILES, 4)
/*  740 */       .define(Character.valueOf('S'), (ItemLike)Blocks.DEEPSLATE_BRICKS)
/*  741 */       .pattern("SS")
/*  742 */       .pattern("SS")
/*  743 */       .unlockedBy("has_deepslate_bricks", has((ItemLike)Blocks.DEEPSLATE_BRICKS))
/*  744 */       .save($$0);
/*      */     
/*  746 */     ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, (ItemLike)Blocks.DETECTOR_RAIL, 6)
/*  747 */       .define(Character.valueOf('R'), (ItemLike)Items.REDSTONE)
/*  748 */       .define(Character.valueOf('#'), (ItemLike)Blocks.STONE_PRESSURE_PLATE)
/*  749 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_INGOT)
/*  750 */       .pattern("X X")
/*  751 */       .pattern("X#X")
/*  752 */       .pattern("XRX")
/*  753 */       .unlockedBy("has_rail", has((ItemLike)Blocks.RAIL))
/*  754 */       .save($$0);
/*      */     
/*  756 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.DIAMOND_AXE)
/*  757 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/*  758 */       .define(Character.valueOf('X'), (ItemLike)Items.DIAMOND)
/*  759 */       .pattern("XX")
/*  760 */       .pattern("X#")
/*  761 */       .pattern(" #")
/*  762 */       .unlockedBy("has_diamond", has((ItemLike)Items.DIAMOND))
/*  763 */       .save($$0);
/*      */     
/*  765 */     nineBlockStorageRecipes($$0, RecipeCategory.MISC, (ItemLike)Items.DIAMOND, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Items.DIAMOND_BLOCK);
/*      */     
/*  767 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.DIAMOND_BOOTS)
/*  768 */       .define(Character.valueOf('X'), (ItemLike)Items.DIAMOND)
/*  769 */       .pattern("X X")
/*  770 */       .pattern("X X")
/*  771 */       .unlockedBy("has_diamond", has((ItemLike)Items.DIAMOND))
/*  772 */       .save($$0);
/*      */     
/*  774 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.DIAMOND_CHESTPLATE)
/*  775 */       .define(Character.valueOf('X'), (ItemLike)Items.DIAMOND)
/*  776 */       .pattern("X X")
/*  777 */       .pattern("XXX")
/*  778 */       .pattern("XXX")
/*  779 */       .unlockedBy("has_diamond", has((ItemLike)Items.DIAMOND))
/*  780 */       .save($$0);
/*      */     
/*  782 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.DIAMOND_HELMET)
/*  783 */       .define(Character.valueOf('X'), (ItemLike)Items.DIAMOND)
/*  784 */       .pattern("XXX")
/*  785 */       .pattern("X X")
/*  786 */       .unlockedBy("has_diamond", has((ItemLike)Items.DIAMOND))
/*  787 */       .save($$0);
/*      */     
/*  789 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.DIAMOND_HOE)
/*  790 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/*  791 */       .define(Character.valueOf('X'), (ItemLike)Items.DIAMOND)
/*  792 */       .pattern("XX")
/*  793 */       .pattern(" #")
/*  794 */       .pattern(" #")
/*  795 */       .unlockedBy("has_diamond", has((ItemLike)Items.DIAMOND))
/*  796 */       .save($$0);
/*      */     
/*  798 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.DIAMOND_LEGGINGS)
/*  799 */       .define(Character.valueOf('X'), (ItemLike)Items.DIAMOND)
/*  800 */       .pattern("XXX")
/*  801 */       .pattern("X X")
/*  802 */       .pattern("X X")
/*  803 */       .unlockedBy("has_diamond", has((ItemLike)Items.DIAMOND))
/*  804 */       .save($$0);
/*      */     
/*  806 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.DIAMOND_PICKAXE)
/*  807 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/*  808 */       .define(Character.valueOf('X'), (ItemLike)Items.DIAMOND)
/*  809 */       .pattern("XXX")
/*  810 */       .pattern(" # ")
/*  811 */       .pattern(" # ")
/*  812 */       .unlockedBy("has_diamond", has((ItemLike)Items.DIAMOND))
/*  813 */       .save($$0);
/*      */     
/*  815 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.DIAMOND_SHOVEL)
/*  816 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/*  817 */       .define(Character.valueOf('X'), (ItemLike)Items.DIAMOND)
/*  818 */       .pattern("X")
/*  819 */       .pattern("#")
/*  820 */       .pattern("#")
/*  821 */       .unlockedBy("has_diamond", has((ItemLike)Items.DIAMOND))
/*  822 */       .save($$0);
/*      */     
/*  824 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.DIAMOND_SWORD)
/*  825 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/*  826 */       .define(Character.valueOf('X'), (ItemLike)Items.DIAMOND)
/*  827 */       .pattern("X")
/*  828 */       .pattern("X")
/*  829 */       .pattern("#")
/*  830 */       .unlockedBy("has_diamond", has((ItemLike)Items.DIAMOND))
/*  831 */       .save($$0);
/*      */     
/*  833 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DIORITE, 2)
/*  834 */       .define(Character.valueOf('Q'), (ItemLike)Items.QUARTZ)
/*  835 */       .define(Character.valueOf('C'), (ItemLike)Blocks.COBBLESTONE)
/*  836 */       .pattern("CQ")
/*  837 */       .pattern("QC")
/*  838 */       .unlockedBy("has_quartz", has((ItemLike)Items.QUARTZ))
/*  839 */       .save($$0);
/*      */     
/*  841 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.DISPENSER)
/*  842 */       .define(Character.valueOf('R'), (ItemLike)Items.REDSTONE)
/*  843 */       .define(Character.valueOf('#'), (ItemLike)Blocks.COBBLESTONE)
/*  844 */       .define(Character.valueOf('X'), (ItemLike)Items.BOW)
/*  845 */       .pattern("###")
/*  846 */       .pattern("#X#")
/*  847 */       .pattern("#R#")
/*  848 */       .unlockedBy("has_bow", has((ItemLike)Items.BOW))
/*  849 */       .save($$0);
/*      */     
/*  851 */     twoByTwoPacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DRIPSTONE_BLOCK, (ItemLike)Items.POINTED_DRIPSTONE);
/*      */     
/*  853 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.DROPPER)
/*  854 */       .define(Character.valueOf('R'), (ItemLike)Items.REDSTONE)
/*  855 */       .define(Character.valueOf('#'), (ItemLike)Blocks.COBBLESTONE)
/*  856 */       .pattern("###")
/*  857 */       .pattern("# #")
/*  858 */       .pattern("#R#")
/*  859 */       .unlockedBy("has_redstone", has((ItemLike)Items.REDSTONE))
/*  860 */       .save($$0);
/*      */     
/*  862 */     nineBlockStorageRecipes($$0, RecipeCategory.MISC, (ItemLike)Items.EMERALD, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Items.EMERALD_BLOCK);
/*      */     
/*  864 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.ENCHANTING_TABLE)
/*  865 */       .define(Character.valueOf('B'), (ItemLike)Items.BOOK)
/*  866 */       .define(Character.valueOf('#'), (ItemLike)Blocks.OBSIDIAN)
/*  867 */       .define(Character.valueOf('D'), (ItemLike)Items.DIAMOND)
/*  868 */       .pattern(" B ")
/*  869 */       .pattern("D#D")
/*  870 */       .pattern("###")
/*  871 */       .unlockedBy("has_obsidian", has((ItemLike)Blocks.OBSIDIAN))
/*  872 */       .save($$0);
/*      */     
/*  874 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.ENDER_CHEST)
/*  875 */       .define(Character.valueOf('#'), (ItemLike)Blocks.OBSIDIAN)
/*  876 */       .define(Character.valueOf('E'), (ItemLike)Items.ENDER_EYE)
/*  877 */       .pattern("###")
/*  878 */       .pattern("#E#")
/*  879 */       .pattern("###")
/*  880 */       .unlockedBy("has_ender_eye", has((ItemLike)Items.ENDER_EYE))
/*  881 */       .save($$0);
/*      */     
/*  883 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.ENDER_EYE)
/*  884 */       .requires((ItemLike)Items.ENDER_PEARL)
/*  885 */       .requires((ItemLike)Items.BLAZE_POWDER)
/*  886 */       .unlockedBy("has_blaze_powder", has((ItemLike)Items.BLAZE_POWDER))
/*  887 */       .save($$0);
/*      */     
/*  889 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.END_STONE_BRICKS, 4)
/*  890 */       .define(Character.valueOf('#'), (ItemLike)Blocks.END_STONE)
/*  891 */       .pattern("##")
/*  892 */       .pattern("##")
/*  893 */       .unlockedBy("has_end_stone", has((ItemLike)Blocks.END_STONE))
/*  894 */       .save($$0);
/*      */     
/*  896 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Items.END_CRYSTAL)
/*  897 */       .define(Character.valueOf('T'), (ItemLike)Items.GHAST_TEAR)
/*  898 */       .define(Character.valueOf('E'), (ItemLike)Items.ENDER_EYE)
/*  899 */       .define(Character.valueOf('G'), (ItemLike)Blocks.GLASS)
/*  900 */       .pattern("GGG")
/*  901 */       .pattern("GEG")
/*  902 */       .pattern("GTG")
/*  903 */       .unlockedBy("has_ender_eye", has((ItemLike)Items.ENDER_EYE))
/*  904 */       .save($$0);
/*      */     
/*  906 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.END_ROD, 4)
/*  907 */       .define(Character.valueOf('#'), (ItemLike)Items.POPPED_CHORUS_FRUIT)
/*  908 */       .define(Character.valueOf('/'), (ItemLike)Items.BLAZE_ROD)
/*  909 */       .pattern("/")
/*  910 */       .pattern("#")
/*  911 */       .unlockedBy("has_chorus_fruit_popped", has((ItemLike)Items.POPPED_CHORUS_FRUIT))
/*  912 */       .save($$0);
/*      */     
/*  914 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, (ItemLike)Items.FERMENTED_SPIDER_EYE)
/*  915 */       .requires((ItemLike)Items.SPIDER_EYE)
/*  916 */       .requires((ItemLike)Blocks.BROWN_MUSHROOM)
/*  917 */       .requires((ItemLike)Items.SUGAR)
/*  918 */       .unlockedBy("has_spider_eye", has((ItemLike)Items.SPIDER_EYE))
/*  919 */       .save($$0);
/*      */     
/*  921 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.FIRE_CHARGE, 3)
/*  922 */       .requires((ItemLike)Items.GUNPOWDER)
/*  923 */       .requires((ItemLike)Items.BLAZE_POWDER)
/*  924 */       .requires(Ingredient.of(new ItemLike[] { (ItemLike)Items.COAL, (ItemLike)Items.CHARCOAL
/*  925 */           })).unlockedBy("has_blaze_powder", has((ItemLike)Items.BLAZE_POWDER))
/*  926 */       .save($$0);
/*      */     
/*  928 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.FIREWORK_ROCKET, 3)
/*  929 */       .requires((ItemLike)Items.GUNPOWDER)
/*  930 */       .requires((ItemLike)Items.PAPER)
/*  931 */       .unlockedBy("has_gunpowder", has((ItemLike)Items.GUNPOWDER))
/*  932 */       .save($$0, "firework_rocket_simple");
/*      */     
/*  934 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.FISHING_ROD)
/*  935 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/*  936 */       .define(Character.valueOf('X'), (ItemLike)Items.STRING)
/*  937 */       .pattern("  #")
/*  938 */       .pattern(" #X")
/*  939 */       .pattern("# X")
/*  940 */       .unlockedBy("has_string", has((ItemLike)Items.STRING))
/*  941 */       .save($$0);
/*      */     
/*  943 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, (ItemLike)Items.FLINT_AND_STEEL)
/*  944 */       .requires((ItemLike)Items.IRON_INGOT)
/*  945 */       .requires((ItemLike)Items.FLINT)
/*  946 */       .unlockedBy("has_flint", has((ItemLike)Items.FLINT))
/*  947 */       .unlockedBy("has_obsidian", has((ItemLike)Blocks.OBSIDIAN))
/*  948 */       .save($$0);
/*      */     
/*  950 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.FLOWER_POT)
/*  951 */       .define(Character.valueOf('#'), (ItemLike)Items.BRICK)
/*  952 */       .pattern("# #")
/*  953 */       .pattern(" # ")
/*  954 */       .unlockedBy("has_brick", has((ItemLike)Items.BRICK))
/*  955 */       .save($$0);
/*      */     
/*  957 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.FURNACE)
/*  958 */       .define(Character.valueOf('#'), ItemTags.STONE_CRAFTING_MATERIALS)
/*  959 */       .pattern("###")
/*  960 */       .pattern("# #")
/*  961 */       .pattern("###")
/*  962 */       .unlockedBy("has_cobblestone", has(ItemTags.STONE_CRAFTING_MATERIALS))
/*  963 */       .save($$0);
/*      */     
/*  965 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, (ItemLike)Items.FURNACE_MINECART)
/*  966 */       .requires((ItemLike)Blocks.FURNACE)
/*  967 */       .requires((ItemLike)Items.MINECART)
/*  968 */       .unlockedBy("has_minecart", has((ItemLike)Items.MINECART))
/*  969 */       .save($$0);
/*      */     
/*  971 */     ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, (ItemLike)Items.GLASS_BOTTLE, 3)
/*  972 */       .define(Character.valueOf('#'), (ItemLike)Blocks.GLASS)
/*  973 */       .pattern("# #")
/*  974 */       .pattern(" # ")
/*  975 */       .unlockedBy("has_glass", has((ItemLike)Blocks.GLASS))
/*  976 */       .save($$0);
/*      */     
/*  978 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.GLASS_PANE, 16)
/*  979 */       .define(Character.valueOf('#'), (ItemLike)Blocks.GLASS)
/*  980 */       .pattern("###")
/*  981 */       .pattern("###")
/*  982 */       .unlockedBy("has_glass", has((ItemLike)Blocks.GLASS))
/*  983 */       .save($$0);
/*      */     
/*  985 */     twoByTwoPacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.GLOWSTONE, (ItemLike)Items.GLOWSTONE_DUST);
/*      */     
/*  987 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, (ItemLike)Items.GLOW_ITEM_FRAME)
/*  988 */       .requires((ItemLike)Items.ITEM_FRAME)
/*  989 */       .requires((ItemLike)Items.GLOW_INK_SAC)
/*  990 */       .unlockedBy("has_item_frame", has((ItemLike)Items.ITEM_FRAME))
/*  991 */       .unlockedBy("has_glow_ink_sac", has((ItemLike)Items.GLOW_INK_SAC))
/*  992 */       .save($$0);
/*      */     
/*  994 */     ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, (ItemLike)Items.GOLDEN_APPLE)
/*  995 */       .define(Character.valueOf('#'), (ItemLike)Items.GOLD_INGOT)
/*  996 */       .define(Character.valueOf('X'), (ItemLike)Items.APPLE)
/*  997 */       .pattern("###")
/*  998 */       .pattern("#X#")
/*  999 */       .pattern("###")
/* 1000 */       .unlockedBy("has_gold_ingot", has((ItemLike)Items.GOLD_INGOT))
/* 1001 */       .save($$0);
/*      */     
/* 1003 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.GOLDEN_AXE)
/* 1004 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1005 */       .define(Character.valueOf('X'), (ItemLike)Items.GOLD_INGOT)
/* 1006 */       .pattern("XX")
/* 1007 */       .pattern("X#")
/* 1008 */       .pattern(" #")
/* 1009 */       .unlockedBy("has_gold_ingot", has((ItemLike)Items.GOLD_INGOT))
/* 1010 */       .save($$0);
/*      */     
/* 1012 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.GOLDEN_BOOTS)
/* 1013 */       .define(Character.valueOf('X'), (ItemLike)Items.GOLD_INGOT)
/* 1014 */       .pattern("X X")
/* 1015 */       .pattern("X X")
/* 1016 */       .unlockedBy("has_gold_ingot", has((ItemLike)Items.GOLD_INGOT))
/* 1017 */       .save($$0);
/*      */     
/* 1019 */     ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, (ItemLike)Items.GOLDEN_CARROT)
/* 1020 */       .define(Character.valueOf('#'), (ItemLike)Items.GOLD_NUGGET)
/* 1021 */       .define(Character.valueOf('X'), (ItemLike)Items.CARROT)
/* 1022 */       .pattern("###")
/* 1023 */       .pattern("#X#")
/* 1024 */       .pattern("###")
/* 1025 */       .unlockedBy("has_gold_nugget", has((ItemLike)Items.GOLD_NUGGET))
/* 1026 */       .save($$0);
/*      */     
/* 1028 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.GOLDEN_CHESTPLATE)
/* 1029 */       .define(Character.valueOf('X'), (ItemLike)Items.GOLD_INGOT)
/* 1030 */       .pattern("X X")
/* 1031 */       .pattern("XXX")
/* 1032 */       .pattern("XXX")
/* 1033 */       .unlockedBy("has_gold_ingot", has((ItemLike)Items.GOLD_INGOT))
/* 1034 */       .save($$0);
/*      */     
/* 1036 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.GOLDEN_HELMET)
/* 1037 */       .define(Character.valueOf('X'), (ItemLike)Items.GOLD_INGOT)
/* 1038 */       .pattern("XXX")
/* 1039 */       .pattern("X X")
/* 1040 */       .unlockedBy("has_gold_ingot", has((ItemLike)Items.GOLD_INGOT))
/* 1041 */       .save($$0);
/*      */     
/* 1043 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.GOLDEN_HOE)
/* 1044 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1045 */       .define(Character.valueOf('X'), (ItemLike)Items.GOLD_INGOT)
/* 1046 */       .pattern("XX")
/* 1047 */       .pattern(" #")
/* 1048 */       .pattern(" #")
/* 1049 */       .unlockedBy("has_gold_ingot", has((ItemLike)Items.GOLD_INGOT))
/* 1050 */       .save($$0);
/*      */     
/* 1052 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.GOLDEN_LEGGINGS)
/* 1053 */       .define(Character.valueOf('X'), (ItemLike)Items.GOLD_INGOT)
/* 1054 */       .pattern("XXX")
/* 1055 */       .pattern("X X")
/* 1056 */       .pattern("X X")
/* 1057 */       .unlockedBy("has_gold_ingot", has((ItemLike)Items.GOLD_INGOT))
/* 1058 */       .save($$0);
/*      */     
/* 1060 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.GOLDEN_PICKAXE)
/* 1061 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1062 */       .define(Character.valueOf('X'), (ItemLike)Items.GOLD_INGOT)
/* 1063 */       .pattern("XXX")
/* 1064 */       .pattern(" # ")
/* 1065 */       .pattern(" # ")
/* 1066 */       .unlockedBy("has_gold_ingot", has((ItemLike)Items.GOLD_INGOT))
/* 1067 */       .save($$0);
/*      */     
/* 1069 */     ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, (ItemLike)Blocks.POWERED_RAIL, 6)
/* 1070 */       .define(Character.valueOf('R'), (ItemLike)Items.REDSTONE)
/* 1071 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1072 */       .define(Character.valueOf('X'), (ItemLike)Items.GOLD_INGOT)
/* 1073 */       .pattern("X X")
/* 1074 */       .pattern("X#X")
/* 1075 */       .pattern("XRX")
/* 1076 */       .unlockedBy("has_rail", has((ItemLike)Blocks.RAIL))
/* 1077 */       .save($$0);
/*      */     
/* 1079 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.GOLDEN_SHOVEL)
/* 1080 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1081 */       .define(Character.valueOf('X'), (ItemLike)Items.GOLD_INGOT)
/* 1082 */       .pattern("X")
/* 1083 */       .pattern("#")
/* 1084 */       .pattern("#")
/* 1085 */       .unlockedBy("has_gold_ingot", has((ItemLike)Items.GOLD_INGOT))
/* 1086 */       .save($$0);
/*      */     
/* 1088 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.GOLDEN_SWORD)
/* 1089 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1090 */       .define(Character.valueOf('X'), (ItemLike)Items.GOLD_INGOT)
/* 1091 */       .pattern("X")
/* 1092 */       .pattern("X")
/* 1093 */       .pattern("#")
/* 1094 */       .unlockedBy("has_gold_ingot", has((ItemLike)Items.GOLD_INGOT))
/* 1095 */       .save($$0);
/*      */     
/* 1097 */     nineBlockStorageRecipesRecipesWithCustomUnpacking($$0, RecipeCategory.MISC, (ItemLike)Items.GOLD_INGOT, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Items.GOLD_BLOCK, "gold_ingot_from_gold_block", "gold_ingot");
/* 1098 */     nineBlockStorageRecipesWithCustomPacking($$0, RecipeCategory.MISC, (ItemLike)Items.GOLD_NUGGET, RecipeCategory.MISC, (ItemLike)Items.GOLD_INGOT, "gold_ingot_from_nuggets", "gold_ingot");
/*      */     
/* 1100 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.GRANITE)
/* 1101 */       .requires((ItemLike)Blocks.DIORITE)
/* 1102 */       .requires((ItemLike)Items.QUARTZ)
/* 1103 */       .unlockedBy("has_quartz", has((ItemLike)Items.QUARTZ))
/* 1104 */       .save($$0);
/*      */     
/* 1106 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.GRAY_DYE, 2)
/* 1107 */       .requires((ItemLike)Items.BLACK_DYE)
/* 1108 */       .requires((ItemLike)Items.WHITE_DYE)
/* 1109 */       .unlockedBy("has_white_dye", has((ItemLike)Items.WHITE_DYE))
/* 1110 */       .unlockedBy("has_black_dye", has((ItemLike)Items.BLACK_DYE))
/* 1111 */       .save($$0);
/*      */     
/* 1113 */     threeByThreePacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.HAY_BLOCK, (ItemLike)Items.WHEAT);
/*      */     
/* 1115 */     pressurePlate($$0, (ItemLike)Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, (ItemLike)Items.IRON_INGOT);
/*      */     
/* 1117 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, (ItemLike)Items.HONEY_BOTTLE, 4)
/* 1118 */       .requires((ItemLike)Items.HONEY_BLOCK)
/* 1119 */       .requires((ItemLike)Items.GLASS_BOTTLE, 4)
/* 1120 */       .unlockedBy("has_honey_block", has((ItemLike)Blocks.HONEY_BLOCK))
/* 1121 */       .save($$0);
/*      */     
/* 1123 */     twoByTwoPacker($$0, RecipeCategory.REDSTONE, (ItemLike)Blocks.HONEY_BLOCK, (ItemLike)Items.HONEY_BOTTLE);
/*      */     
/* 1125 */     twoByTwoPacker($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.HONEYCOMB_BLOCK, (ItemLike)Items.HONEYCOMB);
/*      */     
/* 1127 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.HOPPER)
/* 1128 */       .define(Character.valueOf('C'), (ItemLike)Blocks.CHEST)
/* 1129 */       .define(Character.valueOf('I'), (ItemLike)Items.IRON_INGOT)
/* 1130 */       .pattern("I I")
/* 1131 */       .pattern("ICI")
/* 1132 */       .pattern(" I ")
/* 1133 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1134 */       .save($$0);
/*      */     
/* 1136 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, (ItemLike)Items.HOPPER_MINECART)
/* 1137 */       .requires((ItemLike)Blocks.HOPPER)
/* 1138 */       .requires((ItemLike)Items.MINECART)
/* 1139 */       .unlockedBy("has_minecart", has((ItemLike)Items.MINECART))
/* 1140 */       .save($$0);
/*      */     
/* 1142 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.IRON_AXE)
/* 1143 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1144 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_INGOT)
/* 1145 */       .pattern("XX")
/* 1146 */       .pattern("X#")
/* 1147 */       .pattern(" #")
/* 1148 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1149 */       .save($$0);
/*      */     
/* 1151 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.IRON_BARS, 16)
/* 1152 */       .define(Character.valueOf('#'), (ItemLike)Items.IRON_INGOT)
/* 1153 */       .pattern("###")
/* 1154 */       .pattern("###")
/* 1155 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1156 */       .save($$0);
/*      */     
/* 1158 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.IRON_BOOTS)
/* 1159 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_INGOT)
/* 1160 */       .pattern("X X")
/* 1161 */       .pattern("X X")
/* 1162 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1163 */       .save($$0);
/*      */     
/* 1165 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.IRON_CHESTPLATE)
/* 1166 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_INGOT)
/* 1167 */       .pattern("X X")
/* 1168 */       .pattern("XXX")
/* 1169 */       .pattern("XXX")
/* 1170 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1171 */       .save($$0);
/*      */     
/* 1173 */     doorBuilder((ItemLike)Blocks.IRON_DOOR, Ingredient.of(new ItemLike[] { (ItemLike)Items.IRON_INGOT
/* 1174 */           })).unlockedBy(getHasName((ItemLike)Items.IRON_INGOT), has((ItemLike)Items.IRON_INGOT))
/* 1175 */       .save($$0);
/*      */     
/* 1177 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.IRON_HELMET)
/* 1178 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_INGOT)
/* 1179 */       .pattern("XXX")
/* 1180 */       .pattern("X X")
/* 1181 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1182 */       .save($$0);
/*      */     
/* 1184 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.IRON_HOE)
/* 1185 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1186 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_INGOT)
/* 1187 */       .pattern("XX")
/* 1188 */       .pattern(" #")
/* 1189 */       .pattern(" #")
/* 1190 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1191 */       .save($$0);
/*      */     
/* 1193 */     nineBlockStorageRecipesRecipesWithCustomUnpacking($$0, RecipeCategory.MISC, (ItemLike)Items.IRON_INGOT, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Items.IRON_BLOCK, "iron_ingot_from_iron_block", "iron_ingot");
/* 1194 */     nineBlockStorageRecipesWithCustomPacking($$0, RecipeCategory.MISC, (ItemLike)Items.IRON_NUGGET, RecipeCategory.MISC, (ItemLike)Items.IRON_INGOT, "iron_ingot_from_nuggets", "iron_ingot");
/*      */     
/* 1196 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.IRON_LEGGINGS)
/* 1197 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_INGOT)
/* 1198 */       .pattern("XXX")
/* 1199 */       .pattern("X X")
/* 1200 */       .pattern("X X")
/* 1201 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1202 */       .save($$0);
/*      */     
/* 1204 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.IRON_PICKAXE)
/* 1205 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1206 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_INGOT)
/* 1207 */       .pattern("XXX")
/* 1208 */       .pattern(" # ")
/* 1209 */       .pattern(" # ")
/* 1210 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1211 */       .save($$0);
/*      */     
/* 1213 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.IRON_SHOVEL)
/* 1214 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1215 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_INGOT)
/* 1216 */       .pattern("X")
/* 1217 */       .pattern("#")
/* 1218 */       .pattern("#")
/* 1219 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1220 */       .save($$0);
/*      */     
/* 1222 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.IRON_SWORD)
/* 1223 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1224 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_INGOT)
/* 1225 */       .pattern("X")
/* 1226 */       .pattern("X")
/* 1227 */       .pattern("#")
/* 1228 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1229 */       .save($$0);
/*      */     
/* 1231 */     twoByTwoPacker($$0, RecipeCategory.REDSTONE, (ItemLike)Blocks.IRON_TRAPDOOR, (ItemLike)Items.IRON_INGOT);
/*      */     
/* 1233 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Items.ITEM_FRAME)
/* 1234 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1235 */       .define(Character.valueOf('X'), (ItemLike)Items.LEATHER)
/* 1236 */       .pattern("###")
/* 1237 */       .pattern("#X#")
/* 1238 */       .pattern("###")
/* 1239 */       .unlockedBy("has_leather", has((ItemLike)Items.LEATHER))
/* 1240 */       .save($$0);
/*      */     
/* 1242 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.JUKEBOX)
/* 1243 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/* 1244 */       .define(Character.valueOf('X'), (ItemLike)Items.DIAMOND)
/* 1245 */       .pattern("###")
/* 1246 */       .pattern("#X#")
/* 1247 */       .pattern("###")
/* 1248 */       .unlockedBy("has_diamond", has((ItemLike)Items.DIAMOND))
/* 1249 */       .save($$0);
/*      */     
/* 1251 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.LADDER, 3)
/* 1252 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1253 */       .pattern("# #")
/* 1254 */       .pattern("###")
/* 1255 */       .pattern("# #")
/* 1256 */       .unlockedBy("has_stick", has((ItemLike)Items.STICK))
/* 1257 */       .save($$0);
/*      */     
/* 1259 */     nineBlockStorageRecipes($$0, RecipeCategory.MISC, (ItemLike)Items.LAPIS_LAZULI, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Items.LAPIS_BLOCK);
/*      */     
/* 1261 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.LEAD, 2)
/* 1262 */       .define(Character.valueOf('~'), (ItemLike)Items.STRING)
/* 1263 */       .define(Character.valueOf('O'), (ItemLike)Items.SLIME_BALL)
/* 1264 */       .pattern("~~ ")
/* 1265 */       .pattern("~O ")
/* 1266 */       .pattern("  ~")
/* 1267 */       .unlockedBy("has_slime_ball", has((ItemLike)Items.SLIME_BALL))
/* 1268 */       .save($$0);
/*      */     
/* 1270 */     twoByTwoPacker($$0, RecipeCategory.MISC, (ItemLike)Items.LEATHER, (ItemLike)Items.RABBIT_HIDE);
/*      */     
/* 1272 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.LEATHER_BOOTS)
/* 1273 */       .define(Character.valueOf('X'), (ItemLike)Items.LEATHER)
/* 1274 */       .pattern("X X")
/* 1275 */       .pattern("X X")
/* 1276 */       .unlockedBy("has_leather", has((ItemLike)Items.LEATHER))
/* 1277 */       .save($$0);
/*      */     
/* 1279 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.LEATHER_CHESTPLATE)
/* 1280 */       .define(Character.valueOf('X'), (ItemLike)Items.LEATHER)
/* 1281 */       .pattern("X X")
/* 1282 */       .pattern("XXX")
/* 1283 */       .pattern("XXX")
/* 1284 */       .unlockedBy("has_leather", has((ItemLike)Items.LEATHER))
/* 1285 */       .save($$0);
/*      */     
/* 1287 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.LEATHER_HELMET)
/* 1288 */       .define(Character.valueOf('X'), (ItemLike)Items.LEATHER)
/* 1289 */       .pattern("XXX")
/* 1290 */       .pattern("X X")
/* 1291 */       .unlockedBy("has_leather", has((ItemLike)Items.LEATHER))
/* 1292 */       .save($$0);
/*      */     
/* 1294 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.LEATHER_LEGGINGS)
/* 1295 */       .define(Character.valueOf('X'), (ItemLike)Items.LEATHER)
/* 1296 */       .pattern("XXX")
/* 1297 */       .pattern("X X")
/* 1298 */       .pattern("X X")
/* 1299 */       .unlockedBy("has_leather", has((ItemLike)Items.LEATHER))
/* 1300 */       .save($$0);
/*      */     
/* 1302 */     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)Items.LEATHER_HORSE_ARMOR)
/* 1303 */       .define(Character.valueOf('X'), (ItemLike)Items.LEATHER)
/* 1304 */       .pattern("X X")
/* 1305 */       .pattern("XXX")
/* 1306 */       .pattern("X X")
/* 1307 */       .unlockedBy("has_leather", has((ItemLike)Items.LEATHER))
/* 1308 */       .save($$0);
/*      */     
/* 1310 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.LECTERN)
/* 1311 */       .define(Character.valueOf('S'), ItemTags.WOODEN_SLABS)
/* 1312 */       .define(Character.valueOf('B'), (ItemLike)Blocks.BOOKSHELF)
/* 1313 */       .pattern("SSS")
/* 1314 */       .pattern(" B ")
/* 1315 */       .pattern(" S ")
/* 1316 */       .unlockedBy("has_book", has((ItemLike)Items.BOOK))
/* 1317 */       .save($$0);
/*      */     
/* 1319 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.LEVER)
/* 1320 */       .define(Character.valueOf('#'), (ItemLike)Blocks.COBBLESTONE)
/* 1321 */       .define(Character.valueOf('X'), (ItemLike)Items.STICK)
/* 1322 */       .pattern("X")
/* 1323 */       .pattern("#")
/* 1324 */       .unlockedBy("has_cobblestone", has((ItemLike)Blocks.COBBLESTONE))
/* 1325 */       .save($$0);
/*      */     
/* 1327 */     oneToOneConversionRecipe($$0, (ItemLike)Items.LIGHT_BLUE_DYE, (ItemLike)Blocks.BLUE_ORCHID, "light_blue_dye");
/*      */     
/* 1329 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.LIGHT_BLUE_DYE, 2)
/* 1330 */       .requires((ItemLike)Items.BLUE_DYE)
/* 1331 */       .requires((ItemLike)Items.WHITE_DYE)
/* 1332 */       .group("light_blue_dye")
/* 1333 */       .unlockedBy("has_blue_dye", has((ItemLike)Items.BLUE_DYE))
/* 1334 */       .unlockedBy("has_white_dye", has((ItemLike)Items.WHITE_DYE))
/* 1335 */       .save($$0, "light_blue_dye_from_blue_white_dye");
/*      */     
/* 1337 */     oneToOneConversionRecipe($$0, (ItemLike)Items.LIGHT_GRAY_DYE, (ItemLike)Blocks.AZURE_BLUET, "light_gray_dye");
/*      */     
/* 1339 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.LIGHT_GRAY_DYE, 2)
/* 1340 */       .requires((ItemLike)Items.GRAY_DYE)
/* 1341 */       .requires((ItemLike)Items.WHITE_DYE)
/* 1342 */       .group("light_gray_dye")
/* 1343 */       .unlockedBy("has_gray_dye", has((ItemLike)Items.GRAY_DYE))
/* 1344 */       .unlockedBy("has_white_dye", has((ItemLike)Items.WHITE_DYE))
/* 1345 */       .save($$0, "light_gray_dye_from_gray_white_dye");
/*      */     
/* 1347 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.LIGHT_GRAY_DYE, 3)
/* 1348 */       .requires((ItemLike)Items.BLACK_DYE)
/* 1349 */       .requires((ItemLike)Items.WHITE_DYE, 2)
/* 1350 */       .group("light_gray_dye")
/* 1351 */       .unlockedBy("has_white_dye", has((ItemLike)Items.WHITE_DYE))
/* 1352 */       .unlockedBy("has_black_dye", has((ItemLike)Items.BLACK_DYE))
/* 1353 */       .save($$0, "light_gray_dye_from_black_white_dye");
/*      */     
/* 1355 */     oneToOneConversionRecipe($$0, (ItemLike)Items.LIGHT_GRAY_DYE, (ItemLike)Blocks.OXEYE_DAISY, "light_gray_dye");
/*      */     
/* 1357 */     oneToOneConversionRecipe($$0, (ItemLike)Items.LIGHT_GRAY_DYE, (ItemLike)Blocks.WHITE_TULIP, "light_gray_dye");
/*      */     
/* 1359 */     pressurePlate($$0, (ItemLike)Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, (ItemLike)Items.GOLD_INGOT);
/*      */     
/* 1361 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.LIGHTNING_ROD)
/* 1362 */       .define(Character.valueOf('#'), (ItemLike)Items.COPPER_INGOT)
/* 1363 */       .pattern("#")
/* 1364 */       .pattern("#")
/* 1365 */       .pattern("#")
/* 1366 */       .unlockedBy("has_copper_ingot", has((ItemLike)Items.COPPER_INGOT))
/* 1367 */       .save($$0);
/*      */     
/* 1369 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.LIME_DYE, 2)
/* 1370 */       .requires((ItemLike)Items.GREEN_DYE)
/* 1371 */       .requires((ItemLike)Items.WHITE_DYE)
/* 1372 */       .unlockedBy("has_green_dye", has((ItemLike)Items.GREEN_DYE))
/* 1373 */       .unlockedBy("has_white_dye", has((ItemLike)Items.WHITE_DYE))
/* 1374 */       .save($$0);
/*      */     
/* 1376 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.JACK_O_LANTERN)
/* 1377 */       .define(Character.valueOf('A'), (ItemLike)Blocks.CARVED_PUMPKIN)
/* 1378 */       .define(Character.valueOf('B'), (ItemLike)Blocks.TORCH)
/* 1379 */       .pattern("A")
/* 1380 */       .pattern("B")
/* 1381 */       .unlockedBy("has_carved_pumpkin", has((ItemLike)Blocks.CARVED_PUMPKIN))
/* 1382 */       .save($$0);
/*      */     
/* 1384 */     oneToOneConversionRecipe($$0, (ItemLike)Items.MAGENTA_DYE, (ItemLike)Blocks.ALLIUM, "magenta_dye");
/*      */     
/* 1386 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.MAGENTA_DYE, 4)
/* 1387 */       .requires((ItemLike)Items.BLUE_DYE)
/* 1388 */       .requires((ItemLike)Items.RED_DYE, 2)
/* 1389 */       .requires((ItemLike)Items.WHITE_DYE)
/* 1390 */       .group("magenta_dye")
/* 1391 */       .unlockedBy("has_blue_dye", has((ItemLike)Items.BLUE_DYE))
/* 1392 */       .unlockedBy("has_rose_red", has((ItemLike)Items.RED_DYE))
/* 1393 */       .unlockedBy("has_white_dye", has((ItemLike)Items.WHITE_DYE))
/* 1394 */       .save($$0, "magenta_dye_from_blue_red_white_dye");
/*      */     
/* 1396 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.MAGENTA_DYE, 3)
/* 1397 */       .requires((ItemLike)Items.BLUE_DYE)
/* 1398 */       .requires((ItemLike)Items.RED_DYE)
/* 1399 */       .requires((ItemLike)Items.PINK_DYE)
/* 1400 */       .group("magenta_dye")
/* 1401 */       .unlockedBy("has_pink_dye", has((ItemLike)Items.PINK_DYE))
/* 1402 */       .unlockedBy("has_blue_dye", has((ItemLike)Items.BLUE_DYE))
/* 1403 */       .unlockedBy("has_red_dye", has((ItemLike)Items.RED_DYE))
/* 1404 */       .save($$0, "magenta_dye_from_blue_red_pink");
/*      */     
/* 1406 */     oneToOneConversionRecipe($$0, (ItemLike)Items.MAGENTA_DYE, (ItemLike)Blocks.LILAC, "magenta_dye", 2);
/*      */     
/* 1408 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.MAGENTA_DYE, 2)
/* 1409 */       .requires((ItemLike)Items.PURPLE_DYE)
/* 1410 */       .requires((ItemLike)Items.PINK_DYE)
/* 1411 */       .group("magenta_dye")
/* 1412 */       .unlockedBy("has_pink_dye", has((ItemLike)Items.PINK_DYE))
/* 1413 */       .unlockedBy("has_purple_dye", has((ItemLike)Items.PURPLE_DYE))
/* 1414 */       .save($$0, "magenta_dye_from_purple_and_pink");
/*      */     
/* 1416 */     twoByTwoPacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MAGMA_BLOCK, (ItemLike)Items.MAGMA_CREAM);
/*      */     
/* 1418 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, (ItemLike)Items.MAGMA_CREAM)
/* 1419 */       .requires((ItemLike)Items.BLAZE_POWDER)
/* 1420 */       .requires((ItemLike)Items.SLIME_BALL)
/* 1421 */       .unlockedBy("has_blaze_powder", has((ItemLike)Items.BLAZE_POWDER))
/* 1422 */       .save($$0);
/*      */     
/* 1424 */     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)Items.MAP)
/* 1425 */       .define(Character.valueOf('#'), (ItemLike)Items.PAPER)
/* 1426 */       .define(Character.valueOf('X'), (ItemLike)Items.COMPASS)
/* 1427 */       .pattern("###")
/* 1428 */       .pattern("#X#")
/* 1429 */       .pattern("###")
/* 1430 */       .unlockedBy("has_compass", has((ItemLike)Items.COMPASS))
/* 1431 */       .save($$0);
/*      */     
/* 1433 */     threeByThreePacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MELON, (ItemLike)Items.MELON_SLICE, "has_melon");
/*      */     
/* 1435 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.MELON_SEEDS)
/* 1436 */       .requires((ItemLike)Items.MELON_SLICE)
/* 1437 */       .unlockedBy("has_melon", has((ItemLike)Items.MELON_SLICE))
/* 1438 */       .save($$0);
/*      */     
/* 1440 */     ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, (ItemLike)Items.MINECART)
/* 1441 */       .define(Character.valueOf('#'), (ItemLike)Items.IRON_INGOT)
/* 1442 */       .pattern("# #")
/* 1443 */       .pattern("###")
/* 1444 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1445 */       .save($$0);
/*      */     
/* 1447 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MOSSY_COBBLESTONE)
/* 1448 */       .requires((ItemLike)Blocks.COBBLESTONE)
/* 1449 */       .requires((ItemLike)Blocks.VINE)
/* 1450 */       .group("mossy_cobblestone")
/* 1451 */       .unlockedBy("has_vine", has((ItemLike)Blocks.VINE))
/* 1452 */       .save($$0, getConversionRecipeName((ItemLike)Blocks.MOSSY_COBBLESTONE, (ItemLike)Blocks.VINE));
/*      */     
/* 1454 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MOSSY_STONE_BRICKS)
/* 1455 */       .requires((ItemLike)Blocks.STONE_BRICKS)
/* 1456 */       .requires((ItemLike)Blocks.VINE)
/* 1457 */       .group("mossy_stone_bricks")
/* 1458 */       .unlockedBy("has_vine", has((ItemLike)Blocks.VINE))
/* 1459 */       .save($$0, getConversionRecipeName((ItemLike)Blocks.MOSSY_STONE_BRICKS, (ItemLike)Blocks.VINE));
/*      */     
/* 1461 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MOSSY_COBBLESTONE)
/* 1462 */       .requires((ItemLike)Blocks.COBBLESTONE)
/* 1463 */       .requires((ItemLike)Blocks.MOSS_BLOCK)
/* 1464 */       .group("mossy_cobblestone")
/* 1465 */       .unlockedBy("has_moss_block", has((ItemLike)Blocks.MOSS_BLOCK))
/* 1466 */       .save($$0, getConversionRecipeName((ItemLike)Blocks.MOSSY_COBBLESTONE, (ItemLike)Blocks.MOSS_BLOCK));
/*      */     
/* 1468 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MOSSY_STONE_BRICKS)
/* 1469 */       .requires((ItemLike)Blocks.STONE_BRICKS)
/* 1470 */       .requires((ItemLike)Blocks.MOSS_BLOCK)
/* 1471 */       .group("mossy_stone_bricks")
/* 1472 */       .unlockedBy("has_moss_block", has((ItemLike)Blocks.MOSS_BLOCK))
/* 1473 */       .save($$0, getConversionRecipeName((ItemLike)Blocks.MOSSY_STONE_BRICKS, (ItemLike)Blocks.MOSS_BLOCK));
/*      */     
/* 1475 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, (ItemLike)Items.MUSHROOM_STEW)
/* 1476 */       .requires((ItemLike)Blocks.BROWN_MUSHROOM)
/* 1477 */       .requires((ItemLike)Blocks.RED_MUSHROOM)
/* 1478 */       .requires((ItemLike)Items.BOWL)
/* 1479 */       .unlockedBy("has_mushroom_stew", has((ItemLike)Items.MUSHROOM_STEW))
/* 1480 */       .unlockedBy("has_bowl", has((ItemLike)Items.BOWL))
/* 1481 */       .unlockedBy("has_brown_mushroom", has((ItemLike)Blocks.BROWN_MUSHROOM))
/* 1482 */       .unlockedBy("has_red_mushroom", has((ItemLike)Blocks.RED_MUSHROOM))
/* 1483 */       .save($$0);
/*      */     
/* 1485 */     twoByTwoPacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.NETHER_BRICKS, (ItemLike)Items.NETHER_BRICK);
/*      */     
/* 1487 */     threeByThreePacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.NETHER_WART_BLOCK, (ItemLike)Items.NETHER_WART);
/*      */     
/* 1489 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.NOTE_BLOCK)
/* 1490 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/* 1491 */       .define(Character.valueOf('X'), (ItemLike)Items.REDSTONE)
/* 1492 */       .pattern("###")
/* 1493 */       .pattern("#X#")
/* 1494 */       .pattern("###")
/* 1495 */       .unlockedBy("has_redstone", has((ItemLike)Items.REDSTONE))
/* 1496 */       .save($$0);
/*      */     
/* 1498 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.OBSERVER)
/* 1499 */       .define(Character.valueOf('Q'), (ItemLike)Items.QUARTZ)
/* 1500 */       .define(Character.valueOf('R'), (ItemLike)Items.REDSTONE)
/* 1501 */       .define(Character.valueOf('#'), (ItemLike)Blocks.COBBLESTONE)
/* 1502 */       .pattern("###")
/* 1503 */       .pattern("RRQ")
/* 1504 */       .pattern("###")
/* 1505 */       .unlockedBy("has_quartz", has((ItemLike)Items.QUARTZ))
/* 1506 */       .save($$0);
/*      */     
/* 1508 */     oneToOneConversionRecipe($$0, (ItemLike)Items.ORANGE_DYE, (ItemLike)Blocks.ORANGE_TULIP, "orange_dye");
/*      */     
/* 1510 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.ORANGE_DYE, 2)
/* 1511 */       .requires((ItemLike)Items.RED_DYE)
/* 1512 */       .requires((ItemLike)Items.YELLOW_DYE)
/* 1513 */       .group("orange_dye")
/* 1514 */       .unlockedBy("has_red_dye", has((ItemLike)Items.RED_DYE))
/* 1515 */       .unlockedBy("has_yellow_dye", has((ItemLike)Items.YELLOW_DYE))
/* 1516 */       .save($$0, "orange_dye_from_red_yellow");
/*      */     
/* 1518 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Items.PAINTING)
/* 1519 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1520 */       .define(Character.valueOf('X'), Ingredient.of(ItemTags.WOOL))
/* 1521 */       .pattern("###")
/* 1522 */       .pattern("#X#")
/* 1523 */       .pattern("###")
/* 1524 */       .unlockedBy("has_wool", has(ItemTags.WOOL))
/* 1525 */       .save($$0);
/*      */     
/* 1527 */     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)Items.PAPER, 3)
/* 1528 */       .define(Character.valueOf('#'), (ItemLike)Blocks.SUGAR_CANE)
/* 1529 */       .pattern("###")
/* 1530 */       .unlockedBy("has_reeds", has((ItemLike)Blocks.SUGAR_CANE))
/* 1531 */       .save($$0);
/*      */     
/* 1533 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.QUARTZ_PILLAR, 2)
/* 1534 */       .define(Character.valueOf('#'), (ItemLike)Blocks.QUARTZ_BLOCK)
/* 1535 */       .pattern("#")
/* 1536 */       .pattern("#")
/* 1537 */       .unlockedBy("has_chiseled_quartz_block", has((ItemLike)Blocks.CHISELED_QUARTZ_BLOCK))
/* 1538 */       .unlockedBy("has_quartz_block", has((ItemLike)Blocks.QUARTZ_BLOCK))
/* 1539 */       .unlockedBy("has_quartz_pillar", has((ItemLike)Blocks.QUARTZ_PILLAR))
/* 1540 */       .save($$0);
/*      */     
/* 1542 */     threeByThreePacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PACKED_ICE, (ItemLike)Blocks.ICE);
/*      */     
/* 1544 */     oneToOneConversionRecipe($$0, (ItemLike)Items.PINK_DYE, (ItemLike)Blocks.PEONY, "pink_dye", 2);
/*      */     
/* 1546 */     oneToOneConversionRecipe($$0, (ItemLike)Items.PINK_DYE, (ItemLike)Blocks.PINK_TULIP, "pink_dye");
/*      */     
/* 1548 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.PINK_DYE, 2)
/* 1549 */       .requires((ItemLike)Items.RED_DYE)
/* 1550 */       .requires((ItemLike)Items.WHITE_DYE)
/* 1551 */       .group("pink_dye")
/* 1552 */       .unlockedBy("has_white_dye", has((ItemLike)Items.WHITE_DYE))
/* 1553 */       .unlockedBy("has_red_dye", has((ItemLike)Items.RED_DYE))
/* 1554 */       .save($$0, "pink_dye_from_red_white_dye");
/*      */     
/* 1556 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.PISTON)
/* 1557 */       .define(Character.valueOf('R'), (ItemLike)Items.REDSTONE)
/* 1558 */       .define(Character.valueOf('#'), (ItemLike)Blocks.COBBLESTONE)
/* 1559 */       .define(Character.valueOf('T'), ItemTags.PLANKS)
/* 1560 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_INGOT)
/* 1561 */       .pattern("TTT")
/* 1562 */       .pattern("#X#")
/* 1563 */       .pattern("#R#")
/* 1564 */       .unlockedBy("has_redstone", has((ItemLike)Items.REDSTONE))
/* 1565 */       .save($$0);
/*      */     
/* 1567 */     polished($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BASALT, (ItemLike)Blocks.BASALT);
/*      */     
/* 1569 */     twoByTwoPacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PRISMARINE, (ItemLike)Items.PRISMARINE_SHARD);
/*      */     
/* 1571 */     threeByThreePacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PRISMARINE_BRICKS, (ItemLike)Items.PRISMARINE_SHARD);
/*      */     
/* 1573 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, (ItemLike)Items.PUMPKIN_PIE)
/* 1574 */       .requires((ItemLike)Blocks.PUMPKIN)
/* 1575 */       .requires((ItemLike)Items.SUGAR)
/* 1576 */       .requires((ItemLike)Items.EGG)
/* 1577 */       .unlockedBy("has_carved_pumpkin", has((ItemLike)Blocks.CARVED_PUMPKIN))
/* 1578 */       .unlockedBy("has_pumpkin", has((ItemLike)Blocks.PUMPKIN))
/* 1579 */       .save($$0);
/*      */     
/* 1581 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.PUMPKIN_SEEDS, 4)
/* 1582 */       .requires((ItemLike)Blocks.PUMPKIN)
/* 1583 */       .unlockedBy("has_pumpkin", has((ItemLike)Blocks.PUMPKIN))
/* 1584 */       .save($$0);
/*      */     
/* 1586 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.PURPLE_DYE, 2)
/* 1587 */       .requires((ItemLike)Items.BLUE_DYE)
/* 1588 */       .requires((ItemLike)Items.RED_DYE)
/* 1589 */       .unlockedBy("has_blue_dye", has((ItemLike)Items.BLUE_DYE))
/* 1590 */       .unlockedBy("has_red_dye", has((ItemLike)Items.RED_DYE))
/* 1591 */       .save($$0);
/*      */     
/* 1593 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.SHULKER_BOX)
/* 1594 */       .define(Character.valueOf('#'), (ItemLike)Blocks.CHEST)
/* 1595 */       .define(Character.valueOf('-'), (ItemLike)Items.SHULKER_SHELL)
/* 1596 */       .pattern("-")
/* 1597 */       .pattern("#")
/* 1598 */       .pattern("-")
/* 1599 */       .unlockedBy("has_shulker_shell", has((ItemLike)Items.SHULKER_SHELL))
/* 1600 */       .save($$0);
/*      */     
/* 1602 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PURPUR_BLOCK, 4)
/* 1603 */       .define(Character.valueOf('F'), (ItemLike)Items.POPPED_CHORUS_FRUIT)
/* 1604 */       .pattern("FF")
/* 1605 */       .pattern("FF")
/* 1606 */       .unlockedBy("has_chorus_fruit_popped", has((ItemLike)Items.POPPED_CHORUS_FRUIT))
/* 1607 */       .save($$0);
/*      */     
/* 1609 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PURPUR_PILLAR)
/* 1610 */       .define(Character.valueOf('#'), (ItemLike)Blocks.PURPUR_SLAB)
/* 1611 */       .pattern("#")
/* 1612 */       .pattern("#")
/* 1613 */       .unlockedBy("has_purpur_block", has((ItemLike)Blocks.PURPUR_BLOCK))
/* 1614 */       .save($$0);
/*      */     
/* 1616 */     slabBuilder(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PURPUR_SLAB, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.PURPUR_BLOCK, (ItemLike)Blocks.PURPUR_PILLAR
/* 1617 */           })).unlockedBy("has_purpur_block", has((ItemLike)Blocks.PURPUR_BLOCK))
/* 1618 */       .save($$0);
/*      */     
/* 1620 */     stairBuilder((ItemLike)Blocks.PURPUR_STAIRS, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.PURPUR_BLOCK, (ItemLike)Blocks.PURPUR_PILLAR
/* 1621 */           })).unlockedBy("has_purpur_block", has((ItemLike)Blocks.PURPUR_BLOCK))
/* 1622 */       .save($$0);
/*      */     
/* 1624 */     twoByTwoPacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.QUARTZ_BLOCK, (ItemLike)Items.QUARTZ);
/*      */     
/* 1626 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.QUARTZ_BRICKS, 4)
/* 1627 */       .define(Character.valueOf('#'), (ItemLike)Blocks.QUARTZ_BLOCK)
/* 1628 */       .pattern("##")
/* 1629 */       .pattern("##")
/* 1630 */       .unlockedBy("has_quartz_block", has((ItemLike)Blocks.QUARTZ_BLOCK))
/* 1631 */       .save($$0);
/*      */     
/* 1633 */     slabBuilder(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.QUARTZ_SLAB, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.CHISELED_QUARTZ_BLOCK, (ItemLike)Blocks.QUARTZ_BLOCK, (ItemLike)Blocks.QUARTZ_PILLAR
/* 1634 */           })).unlockedBy("has_chiseled_quartz_block", has((ItemLike)Blocks.CHISELED_QUARTZ_BLOCK))
/* 1635 */       .unlockedBy("has_quartz_block", has((ItemLike)Blocks.QUARTZ_BLOCK))
/* 1636 */       .unlockedBy("has_quartz_pillar", has((ItemLike)Blocks.QUARTZ_PILLAR))
/* 1637 */       .save($$0);
/*      */     
/* 1639 */     stairBuilder((ItemLike)Blocks.QUARTZ_STAIRS, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.CHISELED_QUARTZ_BLOCK, (ItemLike)Blocks.QUARTZ_BLOCK, (ItemLike)Blocks.QUARTZ_PILLAR
/* 1640 */           })).unlockedBy("has_chiseled_quartz_block", has((ItemLike)Blocks.CHISELED_QUARTZ_BLOCK))
/* 1641 */       .unlockedBy("has_quartz_block", has((ItemLike)Blocks.QUARTZ_BLOCK))
/* 1642 */       .unlockedBy("has_quartz_pillar", has((ItemLike)Blocks.QUARTZ_PILLAR))
/* 1643 */       .save($$0);
/*      */     
/* 1645 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, (ItemLike)Items.RABBIT_STEW)
/* 1646 */       .requires((ItemLike)Items.BAKED_POTATO)
/* 1647 */       .requires((ItemLike)Items.COOKED_RABBIT)
/* 1648 */       .requires((ItemLike)Items.BOWL)
/* 1649 */       .requires((ItemLike)Items.CARROT)
/* 1650 */       .requires((ItemLike)Blocks.BROWN_MUSHROOM)
/* 1651 */       .group("rabbit_stew")
/* 1652 */       .unlockedBy("has_cooked_rabbit", has((ItemLike)Items.COOKED_RABBIT))
/* 1653 */       .save($$0, getConversionRecipeName((ItemLike)Items.RABBIT_STEW, (ItemLike)Items.BROWN_MUSHROOM));
/*      */     
/* 1655 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, (ItemLike)Items.RABBIT_STEW)
/* 1656 */       .requires((ItemLike)Items.BAKED_POTATO)
/* 1657 */       .requires((ItemLike)Items.COOKED_RABBIT)
/* 1658 */       .requires((ItemLike)Items.BOWL)
/* 1659 */       .requires((ItemLike)Items.CARROT)
/* 1660 */       .requires((ItemLike)Blocks.RED_MUSHROOM)
/* 1661 */       .group("rabbit_stew")
/* 1662 */       .unlockedBy("has_cooked_rabbit", has((ItemLike)Items.COOKED_RABBIT))
/* 1663 */       .save($$0, getConversionRecipeName((ItemLike)Items.RABBIT_STEW, (ItemLike)Items.RED_MUSHROOM));
/*      */     
/* 1665 */     ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, (ItemLike)Blocks.RAIL, 16)
/* 1666 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1667 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_INGOT)
/* 1668 */       .pattern("X X")
/* 1669 */       .pattern("X#X")
/* 1670 */       .pattern("X X")
/* 1671 */       .unlockedBy("has_minecart", has((ItemLike)Items.MINECART))
/* 1672 */       .save($$0);
/*      */     
/* 1674 */     nineBlockStorageRecipes($$0, RecipeCategory.REDSTONE, (ItemLike)Items.REDSTONE, RecipeCategory.REDSTONE, (ItemLike)Items.REDSTONE_BLOCK);
/*      */     
/* 1676 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.REDSTONE_LAMP)
/* 1677 */       .define(Character.valueOf('R'), (ItemLike)Items.REDSTONE)
/* 1678 */       .define(Character.valueOf('G'), (ItemLike)Blocks.GLOWSTONE)
/* 1679 */       .pattern(" R ")
/* 1680 */       .pattern("RGR")
/* 1681 */       .pattern(" R ")
/* 1682 */       .unlockedBy("has_glowstone", has((ItemLike)Blocks.GLOWSTONE))
/* 1683 */       .save($$0);
/*      */     
/* 1685 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.REDSTONE_TORCH)
/* 1686 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1687 */       .define(Character.valueOf('X'), (ItemLike)Items.REDSTONE)
/* 1688 */       .pattern("X")
/* 1689 */       .pattern("#")
/* 1690 */       .unlockedBy("has_redstone", has((ItemLike)Items.REDSTONE))
/* 1691 */       .save($$0);
/*      */     
/* 1693 */     oneToOneConversionRecipe($$0, (ItemLike)Items.RED_DYE, (ItemLike)Items.BEETROOT, "red_dye");
/* 1694 */     oneToOneConversionRecipe($$0, (ItemLike)Items.RED_DYE, (ItemLike)Blocks.POPPY, "red_dye");
/* 1695 */     oneToOneConversionRecipe($$0, (ItemLike)Items.RED_DYE, (ItemLike)Blocks.ROSE_BUSH, "red_dye", 2);
/*      */     
/* 1697 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.RED_DYE)
/* 1698 */       .requires((ItemLike)Blocks.RED_TULIP)
/* 1699 */       .group("red_dye")
/* 1700 */       .unlockedBy("has_red_flower", has((ItemLike)Blocks.RED_TULIP))
/* 1701 */       .save($$0, "red_dye_from_tulip");
/*      */     
/* 1703 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.RED_NETHER_BRICKS)
/* 1704 */       .define(Character.valueOf('W'), (ItemLike)Items.NETHER_WART)
/* 1705 */       .define(Character.valueOf('N'), (ItemLike)Items.NETHER_BRICK)
/* 1706 */       .pattern("NW")
/* 1707 */       .pattern("WN")
/* 1708 */       .unlockedBy("has_nether_wart", has((ItemLike)Items.NETHER_WART))
/* 1709 */       .save($$0);
/*      */     
/* 1711 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.RED_SANDSTONE)
/* 1712 */       .define(Character.valueOf('#'), (ItemLike)Blocks.RED_SAND)
/* 1713 */       .pattern("##")
/* 1714 */       .pattern("##")
/* 1715 */       .unlockedBy("has_sand", has((ItemLike)Blocks.RED_SAND))
/* 1716 */       .save($$0);
/*      */     
/* 1718 */     slabBuilder(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.RED_SANDSTONE_SLAB, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.RED_SANDSTONE, (ItemLike)Blocks.CHISELED_RED_SANDSTONE
/* 1719 */           })).unlockedBy("has_red_sandstone", has((ItemLike)Blocks.RED_SANDSTONE))
/* 1720 */       .unlockedBy("has_chiseled_red_sandstone", has((ItemLike)Blocks.CHISELED_RED_SANDSTONE))
/* 1721 */       .save($$0);
/*      */     
/* 1723 */     stairBuilder((ItemLike)Blocks.RED_SANDSTONE_STAIRS, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.RED_SANDSTONE, (ItemLike)Blocks.CHISELED_RED_SANDSTONE, (ItemLike)Blocks.CUT_RED_SANDSTONE
/* 1724 */           })).unlockedBy("has_red_sandstone", has((ItemLike)Blocks.RED_SANDSTONE))
/* 1725 */       .unlockedBy("has_chiseled_red_sandstone", has((ItemLike)Blocks.CHISELED_RED_SANDSTONE))
/* 1726 */       .unlockedBy("has_cut_red_sandstone", has((ItemLike)Blocks.CUT_RED_SANDSTONE))
/* 1727 */       .save($$0);
/*      */     
/* 1729 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.REPEATER)
/* 1730 */       .define(Character.valueOf('#'), (ItemLike)Blocks.REDSTONE_TORCH)
/* 1731 */       .define(Character.valueOf('X'), (ItemLike)Items.REDSTONE)
/* 1732 */       .define(Character.valueOf('I'), (ItemLike)Blocks.STONE)
/* 1733 */       .pattern("#X#")
/* 1734 */       .pattern("III")
/* 1735 */       .unlockedBy("has_redstone_torch", has((ItemLike)Blocks.REDSTONE_TORCH))
/* 1736 */       .save($$0);
/*      */     
/* 1738 */     twoByTwoPacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SANDSTONE, (ItemLike)Blocks.SAND);
/*      */     
/* 1740 */     slabBuilder(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SANDSTONE_SLAB, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.SANDSTONE, (ItemLike)Blocks.CHISELED_SANDSTONE
/* 1741 */           })).unlockedBy("has_sandstone", has((ItemLike)Blocks.SANDSTONE))
/* 1742 */       .unlockedBy("has_chiseled_sandstone", has((ItemLike)Blocks.CHISELED_SANDSTONE))
/* 1743 */       .save($$0);
/*      */     
/* 1745 */     stairBuilder((ItemLike)Blocks.SANDSTONE_STAIRS, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.SANDSTONE, (ItemLike)Blocks.CHISELED_SANDSTONE, (ItemLike)Blocks.CUT_SANDSTONE
/* 1746 */           })).unlockedBy("has_sandstone", has((ItemLike)Blocks.SANDSTONE))
/* 1747 */       .unlockedBy("has_chiseled_sandstone", has((ItemLike)Blocks.CHISELED_SANDSTONE))
/* 1748 */       .unlockedBy("has_cut_sandstone", has((ItemLike)Blocks.CUT_SANDSTONE))
/* 1749 */       .save($$0);
/*      */     
/* 1751 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SEA_LANTERN)
/* 1752 */       .define(Character.valueOf('S'), (ItemLike)Items.PRISMARINE_SHARD)
/* 1753 */       .define(Character.valueOf('C'), (ItemLike)Items.PRISMARINE_CRYSTALS)
/* 1754 */       .pattern("SCS")
/* 1755 */       .pattern("CCC")
/* 1756 */       .pattern("SCS")
/* 1757 */       .unlockedBy("has_prismarine_crystals", has((ItemLike)Items.PRISMARINE_CRYSTALS))
/* 1758 */       .save($$0);
/*      */     
/* 1760 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.SHEARS)
/* 1761 */       .define(Character.valueOf('#'), (ItemLike)Items.IRON_INGOT)
/* 1762 */       .pattern(" #")
/* 1763 */       .pattern("# ")
/* 1764 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1765 */       .save($$0);
/*      */     
/* 1767 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.SHIELD)
/* 1768 */       .define(Character.valueOf('W'), ItemTags.PLANKS)
/* 1769 */       .define(Character.valueOf('o'), (ItemLike)Items.IRON_INGOT)
/* 1770 */       .pattern("WoW")
/* 1771 */       .pattern("WWW")
/* 1772 */       .pattern(" W ")
/* 1773 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1774 */       .save($$0);
/*      */     
/* 1776 */     nineBlockStorageRecipes($$0, RecipeCategory.MISC, (ItemLike)Items.SLIME_BALL, RecipeCategory.REDSTONE, (ItemLike)Items.SLIME_BLOCK);
/*      */     
/* 1778 */     cut($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CUT_RED_SANDSTONE, (ItemLike)Blocks.RED_SANDSTONE);
/* 1779 */     cut($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CUT_SANDSTONE, (ItemLike)Blocks.SANDSTONE);
/*      */     
/* 1781 */     twoByTwoPacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SNOW_BLOCK, (ItemLike)Items.SNOWBALL);
/*      */     
/* 1783 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.SNOW, 6)
/* 1784 */       .define(Character.valueOf('#'), (ItemLike)Blocks.SNOW_BLOCK)
/* 1785 */       .pattern("###")
/* 1786 */       .unlockedBy("has_snowball", has((ItemLike)Items.SNOWBALL))
/* 1787 */       .save($$0);
/*      */     
/* 1789 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.SOUL_CAMPFIRE)
/* 1790 */       .define(Character.valueOf('L'), ItemTags.LOGS)
/* 1791 */       .define(Character.valueOf('S'), (ItemLike)Items.STICK)
/* 1792 */       .define(Character.valueOf('#'), ItemTags.SOUL_FIRE_BASE_BLOCKS)
/* 1793 */       .pattern(" S ")
/* 1794 */       .pattern("S#S")
/* 1795 */       .pattern("LLL")
/* 1796 */       .unlockedBy("has_soul_sand", has(ItemTags.SOUL_FIRE_BASE_BLOCKS))
/* 1797 */       .save($$0);
/*      */     
/* 1799 */     ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, (ItemLike)Items.GLISTERING_MELON_SLICE)
/* 1800 */       .define(Character.valueOf('#'), (ItemLike)Items.GOLD_NUGGET)
/* 1801 */       .define(Character.valueOf('X'), (ItemLike)Items.MELON_SLICE)
/* 1802 */       .pattern("###")
/* 1803 */       .pattern("#X#")
/* 1804 */       .pattern("###")
/* 1805 */       .unlockedBy("has_melon", has((ItemLike)Items.MELON_SLICE))
/* 1806 */       .save($$0);
/*      */     
/* 1808 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.SPECTRAL_ARROW, 2)
/* 1809 */       .define(Character.valueOf('#'), (ItemLike)Items.GLOWSTONE_DUST)
/* 1810 */       .define(Character.valueOf('X'), (ItemLike)Items.ARROW)
/* 1811 */       .pattern(" # ")
/* 1812 */       .pattern("#X#")
/* 1813 */       .pattern(" # ")
/* 1814 */       .unlockedBy("has_glowstone_dust", has((ItemLike)Items.GLOWSTONE_DUST))
/* 1815 */       .save($$0);
/*      */     
/* 1817 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.SPYGLASS)
/* 1818 */       .define(Character.valueOf('#'), (ItemLike)Items.AMETHYST_SHARD)
/* 1819 */       .define(Character.valueOf('X'), (ItemLike)Items.COPPER_INGOT)
/* 1820 */       .pattern(" # ")
/* 1821 */       .pattern(" X ")
/* 1822 */       .pattern(" X ")
/* 1823 */       .unlockedBy("has_amethyst_shard", has((ItemLike)Items.AMETHYST_SHARD))
/* 1824 */       .save($$0);
/*      */     
/* 1826 */     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)Items.STICK, 4)
/* 1827 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/* 1828 */       .pattern("#")
/* 1829 */       .pattern("#")
/* 1830 */       .group("sticks")
/* 1831 */       .unlockedBy("has_planks", has(ItemTags.PLANKS))
/* 1832 */       .save($$0);
/*      */     
/* 1834 */     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)Items.STICK, 1)
/* 1835 */       .define(Character.valueOf('#'), (ItemLike)Blocks.BAMBOO)
/* 1836 */       .pattern("#")
/* 1837 */       .pattern("#")
/* 1838 */       .group("sticks")
/* 1839 */       .unlockedBy("has_bamboo", has((ItemLike)Blocks.BAMBOO))
/* 1840 */       .save($$0, "stick_from_bamboo_item");
/*      */     
/* 1842 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.STICKY_PISTON)
/* 1843 */       .define(Character.valueOf('P'), (ItemLike)Blocks.PISTON)
/* 1844 */       .define(Character.valueOf('S'), (ItemLike)Items.SLIME_BALL)
/* 1845 */       .pattern("S")
/* 1846 */       .pattern("P")
/* 1847 */       .unlockedBy("has_slime_ball", has((ItemLike)Items.SLIME_BALL))
/* 1848 */       .save($$0);
/*      */     
/* 1850 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.STONE_BRICKS, 4)
/* 1851 */       .define(Character.valueOf('#'), (ItemLike)Blocks.STONE)
/* 1852 */       .pattern("##")
/* 1853 */       .pattern("##")
/* 1854 */       .unlockedBy("has_stone", has((ItemLike)Blocks.STONE))
/* 1855 */       .save($$0);
/*      */     
/* 1857 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.STONE_AXE)
/* 1858 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1859 */       .define(Character.valueOf('X'), ItemTags.STONE_TOOL_MATERIALS)
/* 1860 */       .pattern("XX")
/* 1861 */       .pattern("X#")
/* 1862 */       .pattern(" #")
/* 1863 */       .unlockedBy("has_cobblestone", has(ItemTags.STONE_TOOL_MATERIALS))
/* 1864 */       .save($$0);
/*      */     
/* 1866 */     slabBuilder(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.STONE_BRICK_SLAB, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.STONE_BRICKS
/* 1867 */           })).unlockedBy("has_stone_bricks", has(ItemTags.STONE_BRICKS))
/* 1868 */       .save($$0);
/*      */     
/* 1870 */     stairBuilder((ItemLike)Blocks.STONE_BRICK_STAIRS, Ingredient.of(new ItemLike[] { (ItemLike)Blocks.STONE_BRICKS
/* 1871 */           })).unlockedBy("has_stone_bricks", has(ItemTags.STONE_BRICKS))
/* 1872 */       .save($$0);
/*      */     
/* 1874 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.STONE_HOE)
/* 1875 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1876 */       .define(Character.valueOf('X'), ItemTags.STONE_TOOL_MATERIALS)
/* 1877 */       .pattern("XX")
/* 1878 */       .pattern(" #")
/* 1879 */       .pattern(" #")
/* 1880 */       .unlockedBy("has_cobblestone", has(ItemTags.STONE_TOOL_MATERIALS))
/* 1881 */       .save($$0);
/*      */     
/* 1883 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.STONE_PICKAXE)
/* 1884 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1885 */       .define(Character.valueOf('X'), ItemTags.STONE_TOOL_MATERIALS)
/* 1886 */       .pattern("XXX")
/* 1887 */       .pattern(" # ")
/* 1888 */       .pattern(" # ")
/* 1889 */       .unlockedBy("has_cobblestone", has(ItemTags.STONE_TOOL_MATERIALS))
/* 1890 */       .save($$0);
/*      */     
/* 1892 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.STONE_SHOVEL)
/* 1893 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1894 */       .define(Character.valueOf('X'), ItemTags.STONE_TOOL_MATERIALS)
/* 1895 */       .pattern("X")
/* 1896 */       .pattern("#")
/* 1897 */       .pattern("#")
/* 1898 */       .unlockedBy("has_cobblestone", has(ItemTags.STONE_TOOL_MATERIALS))
/* 1899 */       .save($$0);
/*      */     
/* 1901 */     slab($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SMOOTH_STONE_SLAB, (ItemLike)Blocks.SMOOTH_STONE);
/*      */     
/* 1903 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.STONE_SWORD)
/* 1904 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1905 */       .define(Character.valueOf('X'), ItemTags.STONE_TOOL_MATERIALS)
/* 1906 */       .pattern("X")
/* 1907 */       .pattern("X")
/* 1908 */       .pattern("#")
/* 1909 */       .unlockedBy("has_cobblestone", has(ItemTags.STONE_TOOL_MATERIALS))
/* 1910 */       .save($$0);
/*      */     
/* 1912 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WHITE_WOOL)
/* 1913 */       .define(Character.valueOf('#'), (ItemLike)Items.STRING)
/* 1914 */       .pattern("##")
/* 1915 */       .pattern("##")
/* 1916 */       .unlockedBy("has_string", has((ItemLike)Items.STRING))
/* 1917 */       .save($$0, getConversionRecipeName((ItemLike)Blocks.WHITE_WOOL, (ItemLike)Items.STRING));
/*      */     
/* 1919 */     oneToOneConversionRecipe($$0, (ItemLike)Items.SUGAR, (ItemLike)Blocks.SUGAR_CANE, "sugar");
/*      */     
/* 1921 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.SUGAR, 3)
/* 1922 */       .requires((ItemLike)Items.HONEY_BOTTLE)
/* 1923 */       .group("sugar")
/* 1924 */       .unlockedBy("has_honey_bottle", has((ItemLike)Items.HONEY_BOTTLE))
/* 1925 */       .save($$0, getConversionRecipeName((ItemLike)Items.SUGAR, (ItemLike)Items.HONEY_BOTTLE));
/*      */     
/* 1927 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.TARGET)
/* 1928 */       .define(Character.valueOf('H'), (ItemLike)Items.HAY_BLOCK)
/* 1929 */       .define(Character.valueOf('R'), (ItemLike)Items.REDSTONE)
/* 1930 */       .pattern(" R ")
/* 1931 */       .pattern("RHR")
/* 1932 */       .pattern(" R ")
/* 1933 */       .unlockedBy("has_redstone", has((ItemLike)Items.REDSTONE))
/* 1934 */       .unlockedBy("has_hay_block", has((ItemLike)Blocks.HAY_BLOCK))
/* 1935 */       .save($$0);
/*      */     
/* 1937 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.TNT)
/* 1938 */       .define(Character.valueOf('#'), Ingredient.of(new ItemLike[] { (ItemLike)Blocks.SAND, (ItemLike)Blocks.RED_SAND
/* 1939 */           })).define(Character.valueOf('X'), (ItemLike)Items.GUNPOWDER)
/* 1940 */       .pattern("X#X")
/* 1941 */       .pattern("#X#")
/* 1942 */       .pattern("X#X")
/* 1943 */       .unlockedBy("has_gunpowder", has((ItemLike)Items.GUNPOWDER))
/* 1944 */       .save($$0);
/*      */     
/* 1946 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, (ItemLike)Items.TNT_MINECART)
/* 1947 */       .requires((ItemLike)Blocks.TNT)
/* 1948 */       .requires((ItemLike)Items.MINECART)
/* 1949 */       .unlockedBy("has_minecart", has((ItemLike)Items.MINECART))
/* 1950 */       .save($$0);
/*      */     
/* 1952 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.TORCH, 4)
/* 1953 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1954 */       .define(Character.valueOf('X'), Ingredient.of(new ItemLike[] { (ItemLike)Items.COAL, (ItemLike)Items.CHARCOAL
/* 1955 */           })).pattern("X")
/* 1956 */       .pattern("#")
/* 1957 */       .unlockedBy("has_stone_pickaxe", has((ItemLike)Items.STONE_PICKAXE))
/* 1958 */       .save($$0);
/*      */     
/* 1960 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.SOUL_TORCH, 4)
/* 1961 */       .define(Character.valueOf('X'), Ingredient.of(new ItemLike[] { (ItemLike)Items.COAL, (ItemLike)Items.CHARCOAL
/* 1962 */           })).define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 1963 */       .define(Character.valueOf('S'), ItemTags.SOUL_FIRE_BASE_BLOCKS)
/* 1964 */       .pattern("X")
/* 1965 */       .pattern("#")
/* 1966 */       .pattern("S")
/* 1967 */       .unlockedBy("has_soul_sand", has(ItemTags.SOUL_FIRE_BASE_BLOCKS))
/* 1968 */       .save($$0);
/*      */     
/* 1970 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.LANTERN)
/* 1971 */       .define(Character.valueOf('#'), (ItemLike)Items.TORCH)
/* 1972 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_NUGGET)
/* 1973 */       .pattern("XXX")
/* 1974 */       .pattern("X#X")
/* 1975 */       .pattern("XXX")
/* 1976 */       .unlockedBy("has_iron_nugget", has((ItemLike)Items.IRON_NUGGET))
/* 1977 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 1978 */       .save($$0);
/*      */     
/* 1980 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.SOUL_LANTERN)
/* 1981 */       .define(Character.valueOf('#'), (ItemLike)Items.SOUL_TORCH)
/* 1982 */       .define(Character.valueOf('X'), (ItemLike)Items.IRON_NUGGET)
/* 1983 */       .pattern("XXX")
/* 1984 */       .pattern("X#X")
/* 1985 */       .pattern("XXX")
/* 1986 */       .unlockedBy("has_soul_torch", has((ItemLike)Items.SOUL_TORCH))
/* 1987 */       .save($$0);
/*      */     
/* 1989 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, (ItemLike)Blocks.TRAPPED_CHEST)
/* 1990 */       .requires((ItemLike)Blocks.CHEST)
/* 1991 */       .requires((ItemLike)Blocks.TRIPWIRE_HOOK)
/* 1992 */       .unlockedBy("has_tripwire_hook", has((ItemLike)Blocks.TRIPWIRE_HOOK))
/* 1993 */       .save($$0);
/*      */     
/* 1995 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Blocks.TRIPWIRE_HOOK, 2)
/* 1996 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/* 1997 */       .define(Character.valueOf('S'), (ItemLike)Items.STICK)
/* 1998 */       .define(Character.valueOf('I'), (ItemLike)Items.IRON_INGOT)
/* 1999 */       .pattern("I")
/* 2000 */       .pattern("S")
/* 2001 */       .pattern("#")
/* 2002 */       .unlockedBy("has_string", has((ItemLike)Items.STRING))
/* 2003 */       .save($$0);
/*      */     
/* 2005 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.TURTLE_HELMET)
/* 2006 */       .define(Character.valueOf('X'), (ItemLike)Items.SCUTE)
/* 2007 */       .pattern("XXX")
/* 2008 */       .pattern("X X")
/* 2009 */       .unlockedBy("has_scute", has((ItemLike)Items.SCUTE))
/* 2010 */       .save($$0);
/*      */     
/* 2012 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.WHEAT, 9)
/* 2013 */       .requires((ItemLike)Blocks.HAY_BLOCK)
/* 2014 */       .unlockedBy("has_hay_block", has((ItemLike)Blocks.HAY_BLOCK))
/* 2015 */       .save($$0);
/*      */     
/* 2017 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.WHITE_DYE)
/* 2018 */       .requires((ItemLike)Items.BONE_MEAL)
/* 2019 */       .group("white_dye")
/* 2020 */       .unlockedBy("has_bone_meal", has((ItemLike)Items.BONE_MEAL))
/* 2021 */       .save($$0);
/*      */     
/* 2023 */     oneToOneConversionRecipe($$0, (ItemLike)Items.WHITE_DYE, (ItemLike)Blocks.LILY_OF_THE_VALLEY, "white_dye");
/*      */     
/* 2025 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.WOODEN_AXE)
/* 2026 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 2027 */       .define(Character.valueOf('X'), ItemTags.PLANKS)
/* 2028 */       .pattern("XX")
/* 2029 */       .pattern("X#")
/* 2030 */       .pattern(" #")
/* 2031 */       .unlockedBy("has_stick", has((ItemLike)Items.STICK))
/* 2032 */       .save($$0);
/*      */     
/* 2034 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.WOODEN_HOE)
/* 2035 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 2036 */       .define(Character.valueOf('X'), ItemTags.PLANKS)
/* 2037 */       .pattern("XX")
/* 2038 */       .pattern(" #")
/* 2039 */       .pattern(" #")
/* 2040 */       .unlockedBy("has_stick", has((ItemLike)Items.STICK))
/* 2041 */       .save($$0);
/*      */     
/* 2043 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.WOODEN_PICKAXE)
/* 2044 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 2045 */       .define(Character.valueOf('X'), ItemTags.PLANKS)
/* 2046 */       .pattern("XXX")
/* 2047 */       .pattern(" # ")
/* 2048 */       .pattern(" # ")
/* 2049 */       .unlockedBy("has_stick", has((ItemLike)Items.STICK))
/* 2050 */       .save($$0);
/*      */     
/* 2052 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.WOODEN_SHOVEL)
/* 2053 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 2054 */       .define(Character.valueOf('X'), ItemTags.PLANKS)
/* 2055 */       .pattern("X")
/* 2056 */       .pattern("#")
/* 2057 */       .pattern("#")
/* 2058 */       .unlockedBy("has_stick", has((ItemLike)Items.STICK))
/* 2059 */       .save($$0);
/*      */     
/* 2061 */     ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, (ItemLike)Items.WOODEN_SWORD)
/* 2062 */       .define(Character.valueOf('#'), (ItemLike)Items.STICK)
/* 2063 */       .define(Character.valueOf('X'), ItemTags.PLANKS)
/* 2064 */       .pattern("X")
/* 2065 */       .pattern("X")
/* 2066 */       .pattern("#")
/* 2067 */       .unlockedBy("has_stick", has((ItemLike)Items.STICK))
/* 2068 */       .save($$0);
/*      */     
/* 2070 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.WRITABLE_BOOK)
/* 2071 */       .requires((ItemLike)Items.BOOK)
/* 2072 */       .requires((ItemLike)Items.INK_SAC)
/* 2073 */       .requires((ItemLike)Items.FEATHER)
/* 2074 */       .unlockedBy("has_book", has((ItemLike)Items.BOOK))
/* 2075 */       .save($$0);
/*      */     
/* 2077 */     oneToOneConversionRecipe($$0, (ItemLike)Items.YELLOW_DYE, (ItemLike)Blocks.DANDELION, "yellow_dye");
/* 2078 */     oneToOneConversionRecipe($$0, (ItemLike)Items.YELLOW_DYE, (ItemLike)Blocks.SUNFLOWER, "yellow_dye", 2);
/*      */     
/* 2080 */     nineBlockStorageRecipes($$0, RecipeCategory.FOOD, (ItemLike)Items.DRIED_KELP, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Items.DRIED_KELP_BLOCK);
/*      */     
/* 2082 */     ShapedRecipeBuilder.shaped(RecipeCategory.MISC, (ItemLike)Blocks.CONDUIT)
/* 2083 */       .define(Character.valueOf('#'), (ItemLike)Items.NAUTILUS_SHELL)
/* 2084 */       .define(Character.valueOf('X'), (ItemLike)Items.HEART_OF_THE_SEA)
/* 2085 */       .pattern("###")
/* 2086 */       .pattern("#X#")
/* 2087 */       .pattern("###")
/* 2088 */       .unlockedBy("has_nautilus_core", has((ItemLike)Items.HEART_OF_THE_SEA))
/* 2089 */       .unlockedBy("has_nautilus_shell", has((ItemLike)Items.NAUTILUS_SHELL))
/* 2090 */       .save($$0);
/*      */     
/* 2092 */     wall($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.RED_SANDSTONE_WALL, (ItemLike)Blocks.RED_SANDSTONE);
/* 2093 */     wall($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.STONE_BRICK_WALL, (ItemLike)Blocks.STONE_BRICKS);
/* 2094 */     wall($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.SANDSTONE_WALL, (ItemLike)Blocks.SANDSTONE);
/*      */     
/* 2096 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.CREEPER_BANNER_PATTERN)
/* 2097 */       .requires((ItemLike)Items.PAPER)
/* 2098 */       .requires((ItemLike)Items.CREEPER_HEAD)
/* 2099 */       .unlockedBy("has_creeper_head", has((ItemLike)Items.CREEPER_HEAD))
/* 2100 */       .save($$0);
/*      */     
/* 2102 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.SKULL_BANNER_PATTERN)
/* 2103 */       .requires((ItemLike)Items.PAPER)
/* 2104 */       .requires((ItemLike)Items.WITHER_SKELETON_SKULL)
/* 2105 */       .unlockedBy("has_wither_skeleton_skull", has((ItemLike)Items.WITHER_SKELETON_SKULL))
/* 2106 */       .save($$0);
/*      */     
/* 2108 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.FLOWER_BANNER_PATTERN)
/* 2109 */       .requires((ItemLike)Items.PAPER)
/* 2110 */       .requires((ItemLike)Blocks.OXEYE_DAISY)
/* 2111 */       .unlockedBy("has_oxeye_daisy", has((ItemLike)Blocks.OXEYE_DAISY))
/* 2112 */       .save($$0);
/*      */     
/* 2114 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.MOJANG_BANNER_PATTERN)
/* 2115 */       .requires((ItemLike)Items.PAPER)
/* 2116 */       .requires((ItemLike)Items.ENCHANTED_GOLDEN_APPLE)
/* 2117 */       .unlockedBy("has_enchanted_golden_apple", has((ItemLike)Items.ENCHANTED_GOLDEN_APPLE))
/* 2118 */       .save($$0);
/*      */     
/* 2120 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.SCAFFOLDING, 6)
/* 2121 */       .define(Character.valueOf('~'), (ItemLike)Items.STRING)
/* 2122 */       .define(Character.valueOf('I'), (ItemLike)Blocks.BAMBOO)
/* 2123 */       .pattern("I~I")
/* 2124 */       .pattern("I I")
/* 2125 */       .pattern("I I")
/* 2126 */       .unlockedBy("has_bamboo", has((ItemLike)Blocks.BAMBOO))
/* 2127 */       .save($$0);
/*      */     
/* 2129 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.GRINDSTONE)
/* 2130 */       .define(Character.valueOf('I'), (ItemLike)Items.STICK)
/* 2131 */       .define(Character.valueOf('-'), (ItemLike)Blocks.STONE_SLAB)
/* 2132 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/* 2133 */       .pattern("I-I")
/* 2134 */       .pattern("# #")
/* 2135 */       .unlockedBy("has_stone_slab", has((ItemLike)Blocks.STONE_SLAB))
/* 2136 */       .save($$0);
/*      */     
/* 2138 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.BLAST_FURNACE)
/* 2139 */       .define(Character.valueOf('#'), (ItemLike)Blocks.SMOOTH_STONE)
/* 2140 */       .define(Character.valueOf('X'), (ItemLike)Blocks.FURNACE)
/* 2141 */       .define(Character.valueOf('I'), (ItemLike)Items.IRON_INGOT)
/* 2142 */       .pattern("III")
/* 2143 */       .pattern("IXI")
/* 2144 */       .pattern("###")
/* 2145 */       .unlockedBy("has_smooth_stone", has((ItemLike)Blocks.SMOOTH_STONE))
/* 2146 */       .save($$0);
/*      */     
/* 2148 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.SMOKER)
/* 2149 */       .define(Character.valueOf('#'), ItemTags.LOGS)
/* 2150 */       .define(Character.valueOf('X'), (ItemLike)Blocks.FURNACE)
/* 2151 */       .pattern(" # ")
/* 2152 */       .pattern("#X#")
/* 2153 */       .pattern(" # ")
/* 2154 */       .unlockedBy("has_furnace", has((ItemLike)Blocks.FURNACE))
/* 2155 */       .save($$0);
/*      */     
/* 2157 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.CARTOGRAPHY_TABLE)
/* 2158 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/* 2159 */       .define(Character.valueOf('@'), (ItemLike)Items.PAPER)
/* 2160 */       .pattern("@@")
/* 2161 */       .pattern("##")
/* 2162 */       .pattern("##")
/* 2163 */       .unlockedBy("has_paper", has((ItemLike)Items.PAPER))
/* 2164 */       .save($$0);
/*      */     
/* 2166 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.SMITHING_TABLE)
/* 2167 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/* 2168 */       .define(Character.valueOf('@'), (ItemLike)Items.IRON_INGOT)
/* 2169 */       .pattern("@@")
/* 2170 */       .pattern("##")
/* 2171 */       .pattern("##")
/* 2172 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 2173 */       .save($$0);
/*      */     
/* 2175 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.FLETCHING_TABLE)
/* 2176 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/* 2177 */       .define(Character.valueOf('@'), (ItemLike)Items.FLINT)
/* 2178 */       .pattern("@@")
/* 2179 */       .pattern("##")
/* 2180 */       .pattern("##")
/* 2181 */       .unlockedBy("has_flint", has((ItemLike)Items.FLINT))
/* 2182 */       .save($$0);
/*      */     
/* 2184 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.STONECUTTER)
/* 2185 */       .define(Character.valueOf('I'), (ItemLike)Items.IRON_INGOT)
/* 2186 */       .define(Character.valueOf('#'), (ItemLike)Blocks.STONE)
/* 2187 */       .pattern(" I ")
/* 2188 */       .pattern("###")
/* 2189 */       .unlockedBy("has_stone", has((ItemLike)Blocks.STONE))
/* 2190 */       .save($$0);
/*      */     
/* 2192 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.LODESTONE)
/* 2193 */       .define(Character.valueOf('S'), (ItemLike)Items.CHISELED_STONE_BRICKS)
/* 2194 */       .define(Character.valueOf('#'), (ItemLike)Items.NETHERITE_INGOT)
/* 2195 */       .pattern("SSS")
/* 2196 */       .pattern("S#S")
/* 2197 */       .pattern("SSS")
/* 2198 */       .unlockedBy("has_netherite_ingot", has((ItemLike)Items.NETHERITE_INGOT))
/* 2199 */       .save($$0);
/*      */     
/* 2201 */     nineBlockStorageRecipesRecipesWithCustomUnpacking($$0, RecipeCategory.MISC, (ItemLike)Items.NETHERITE_INGOT, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Items.NETHERITE_BLOCK, "netherite_ingot_from_netherite_block", "netherite_ingot");
/*      */     
/* 2203 */     ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, (ItemLike)Items.NETHERITE_INGOT)
/* 2204 */       .requires((ItemLike)Items.NETHERITE_SCRAP, 4)
/* 2205 */       .requires((ItemLike)Items.GOLD_INGOT, 4)
/* 2206 */       .group("netherite_ingot")
/* 2207 */       .unlockedBy("has_netherite_scrap", has((ItemLike)Items.NETHERITE_SCRAP))
/* 2208 */       .save($$0);
/*      */     
/* 2210 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.RESPAWN_ANCHOR)
/* 2211 */       .define(Character.valueOf('O'), (ItemLike)Blocks.CRYING_OBSIDIAN)
/* 2212 */       .define(Character.valueOf('G'), (ItemLike)Blocks.GLOWSTONE)
/* 2213 */       .pattern("OOO")
/* 2214 */       .pattern("GGG")
/* 2215 */       .pattern("OOO")
/* 2216 */       .unlockedBy("has_obsidian", has((ItemLike)Blocks.CRYING_OBSIDIAN))
/* 2217 */       .save($$0);
/*      */     
/* 2219 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Blocks.CHAIN)
/* 2220 */       .define(Character.valueOf('I'), (ItemLike)Items.IRON_INGOT)
/* 2221 */       .define(Character.valueOf('N'), (ItemLike)Items.IRON_NUGGET)
/* 2222 */       .pattern("N")
/* 2223 */       .pattern("I")
/* 2224 */       .pattern("N")
/* 2225 */       .unlockedBy("has_iron_nugget", has((ItemLike)Items.IRON_NUGGET))
/* 2226 */       .unlockedBy("has_iron_ingot", has((ItemLike)Items.IRON_INGOT))
/* 2227 */       .save($$0);
/*      */     
/* 2229 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.TINTED_GLASS, 2)
/* 2230 */       .define(Character.valueOf('G'), (ItemLike)Blocks.GLASS)
/* 2231 */       .define(Character.valueOf('S'), (ItemLike)Items.AMETHYST_SHARD)
/* 2232 */       .pattern(" S ")
/* 2233 */       .pattern("SGS")
/* 2234 */       .pattern(" S ")
/* 2235 */       .unlockedBy("has_amethyst_shard", has((ItemLike)Items.AMETHYST_SHARD))
/* 2236 */       .save($$0);
/*      */     
/* 2238 */     twoByTwoPacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.AMETHYST_BLOCK, (ItemLike)Items.AMETHYST_SHARD);
/*      */     
/* 2240 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.RECOVERY_COMPASS)
/* 2241 */       .define(Character.valueOf('C'), (ItemLike)Items.COMPASS)
/* 2242 */       .define(Character.valueOf('S'), (ItemLike)Items.ECHO_SHARD)
/* 2243 */       .pattern("SSS")
/* 2244 */       .pattern("SCS")
/* 2245 */       .pattern("SSS")
/* 2246 */       .unlockedBy("has_echo_shard", has((ItemLike)Items.ECHO_SHARD))
/* 2247 */       .save($$0);
/*      */     
/* 2249 */     ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, (ItemLike)Items.CALIBRATED_SCULK_SENSOR)
/* 2250 */       .define(Character.valueOf('#'), (ItemLike)Items.AMETHYST_SHARD)
/* 2251 */       .define(Character.valueOf('X'), (ItemLike)Items.SCULK_SENSOR)
/* 2252 */       .pattern(" # ")
/* 2253 */       .pattern("#X#")
/* 2254 */       .unlockedBy("has_amethyst_shard", has((ItemLike)Items.AMETHYST_SHARD))
/* 2255 */       .save($$0);
/*      */     
/* 2257 */     threeByThreePacker($$0, RecipeCategory.MISC, (ItemLike)Items.MUSIC_DISC_5, (ItemLike)Items.DISC_FRAGMENT_5);
/*      */     
/* 2259 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.ArmorDyeRecipe::new)
/* 2260 */       .save($$0, "armor_dye");
/*      */     
/* 2262 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.BannerDuplicateRecipe::new)
/* 2263 */       .save($$0, "banner_duplicate");
/*      */     
/* 2265 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.BookCloningRecipe::new)
/* 2266 */       .save($$0, "book_cloning");
/*      */     
/* 2268 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.FireworkRocketRecipe::new)
/* 2269 */       .save($$0, "firework_rocket");
/*      */     
/* 2271 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.FireworkStarRecipe::new)
/* 2272 */       .save($$0, "firework_star");
/*      */     
/* 2274 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.FireworkStarFadeRecipe::new)
/* 2275 */       .save($$0, "firework_star_fade");
/*      */     
/* 2277 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.MapCloningRecipe::new)
/* 2278 */       .save($$0, "map_cloning");
/*      */     
/* 2280 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.MapExtendingRecipe::new)
/* 2281 */       .save($$0, "map_extending");
/*      */     
/* 2283 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.RepairItemRecipe::new)
/* 2284 */       .save($$0, "repair_item");
/*      */     
/* 2286 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.ShieldDecorationRecipe::new)
/* 2287 */       .save($$0, "shield_decoration");
/*      */     
/* 2289 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.ShulkerBoxColoring::new)
/* 2290 */       .save($$0, "shulker_box_coloring");
/*      */     
/* 2292 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.TippedArrowRecipe::new)
/* 2293 */       .save($$0, "tipped_arrow");
/*      */     
/* 2295 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.SuspiciousStewRecipe::new)
/* 2296 */       .save($$0, "suspicious_stew");
/*      */ 
/*      */     
/* 2299 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Items.POTATO }, ), RecipeCategory.FOOD, (ItemLike)Items.BAKED_POTATO, 0.35F, 200)
/* 2300 */       .unlockedBy("has_potato", has((ItemLike)Items.POTATO))
/* 2301 */       .save($$0);
/*      */     
/* 2303 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Items.CLAY_BALL }, ), RecipeCategory.MISC, (ItemLike)Items.BRICK, 0.3F, 200)
/* 2304 */       .unlockedBy("has_clay_ball", has((ItemLike)Items.CLAY_BALL))
/* 2305 */       .save($$0);
/*      */     
/* 2307 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.LOGS_THAT_BURN), RecipeCategory.MISC, (ItemLike)Items.CHARCOAL, 0.15F, 200)
/* 2308 */       .unlockedBy("has_log", has(ItemTags.LOGS_THAT_BURN))
/* 2309 */       .save($$0);
/*      */     
/* 2311 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Items.CHORUS_FRUIT }, ), RecipeCategory.MISC, (ItemLike)Items.POPPED_CHORUS_FRUIT, 0.1F, 200)
/* 2312 */       .unlockedBy("has_chorus_fruit", has((ItemLike)Items.CHORUS_FRUIT))
/* 2313 */       .save($$0);
/*      */     
/* 2315 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Items.BEEF }, ), RecipeCategory.FOOD, (ItemLike)Items.COOKED_BEEF, 0.35F, 200)
/* 2316 */       .unlockedBy("has_beef", has((ItemLike)Items.BEEF))
/* 2317 */       .save($$0);
/*      */     
/* 2319 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Items.CHICKEN }, ), RecipeCategory.FOOD, (ItemLike)Items.COOKED_CHICKEN, 0.35F, 200)
/* 2320 */       .unlockedBy("has_chicken", has((ItemLike)Items.CHICKEN))
/* 2321 */       .save($$0);
/*      */     
/* 2323 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Items.COD }, ), RecipeCategory.FOOD, (ItemLike)Items.COOKED_COD, 0.35F, 200)
/* 2324 */       .unlockedBy("has_cod", has((ItemLike)Items.COD))
/* 2325 */       .save($$0);
/*      */     
/* 2327 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.KELP }, ), RecipeCategory.FOOD, (ItemLike)Items.DRIED_KELP, 0.1F, 200)
/* 2328 */       .unlockedBy("has_kelp", has((ItemLike)Blocks.KELP))
/* 2329 */       .save($$0, getSmeltingRecipeName((ItemLike)Items.DRIED_KELP));
/*      */     
/* 2331 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Items.SALMON }, ), RecipeCategory.FOOD, (ItemLike)Items.COOKED_SALMON, 0.35F, 200)
/* 2332 */       .unlockedBy("has_salmon", has((ItemLike)Items.SALMON))
/* 2333 */       .save($$0);
/*      */     
/* 2335 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Items.MUTTON }, ), RecipeCategory.FOOD, (ItemLike)Items.COOKED_MUTTON, 0.35F, 200)
/* 2336 */       .unlockedBy("has_mutton", has((ItemLike)Items.MUTTON))
/* 2337 */       .save($$0);
/*      */     
/* 2339 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Items.PORKCHOP }, ), RecipeCategory.FOOD, (ItemLike)Items.COOKED_PORKCHOP, 0.35F, 200)
/* 2340 */       .unlockedBy("has_porkchop", has((ItemLike)Items.PORKCHOP))
/* 2341 */       .save($$0);
/*      */     
/* 2343 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Items.RABBIT }, ), RecipeCategory.FOOD, (ItemLike)Items.COOKED_RABBIT, 0.35F, 200)
/* 2344 */       .unlockedBy("has_rabbit", has((ItemLike)Items.RABBIT))
/* 2345 */       .save($$0);
/*      */     
/* 2347 */     oreSmelting($$0, (List)COAL_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.COAL, 0.1F, 200, "coal");
/* 2348 */     oreSmelting($$0, (List)IRON_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.IRON_INGOT, 0.7F, 200, "iron_ingot");
/* 2349 */     oreSmelting($$0, (List)COPPER_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.COPPER_INGOT, 0.7F, 200, "copper_ingot");
/* 2350 */     oreSmelting($$0, (List)GOLD_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.GOLD_INGOT, 1.0F, 200, "gold_ingot");
/* 2351 */     oreSmelting($$0, (List)DIAMOND_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.DIAMOND, 1.0F, 200, "diamond");
/* 2352 */     oreSmelting($$0, (List)LAPIS_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.LAPIS_LAZULI, 0.2F, 200, "lapis_lazuli");
/* 2353 */     oreSmelting($$0, (List)REDSTONE_SMELTABLES, RecipeCategory.REDSTONE, (ItemLike)Items.REDSTONE, 0.7F, 200, "redstone");
/* 2354 */     oreSmelting($$0, (List)EMERALD_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.EMERALD, 1.0F, 200, "emerald");
/*      */     
/* 2356 */     nineBlockStorageRecipes($$0, RecipeCategory.MISC, (ItemLike)Items.RAW_IRON, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Items.RAW_IRON_BLOCK);
/* 2357 */     nineBlockStorageRecipes($$0, RecipeCategory.MISC, (ItemLike)Items.RAW_COPPER, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Items.RAW_COPPER_BLOCK);
/* 2358 */     nineBlockStorageRecipes($$0, RecipeCategory.MISC, (ItemLike)Items.RAW_GOLD, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Items.RAW_GOLD_BLOCK);
/*      */     
/* 2360 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemTags.SMELTS_TO_GLASS), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.GLASS.asItem(), 0.1F, 200)
/* 2361 */       .unlockedBy("has_smelts_to_glass", has(ItemTags.SMELTS_TO_GLASS))
/* 2362 */       .save($$0);
/*      */     
/* 2364 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.SEA_PICKLE }, ), RecipeCategory.MISC, (ItemLike)Items.LIME_DYE, 0.1F, 200)
/* 2365 */       .unlockedBy("has_sea_pickle", has((ItemLike)Blocks.SEA_PICKLE))
/* 2366 */       .save($$0, getSmeltingRecipeName((ItemLike)Items.LIME_DYE));
/*      */     
/* 2368 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.CACTUS.asItem() }, ), RecipeCategory.MISC, (ItemLike)Items.GREEN_DYE, 1.0F, 200)
/* 2369 */       .unlockedBy("has_cactus", has((ItemLike)Blocks.CACTUS))
/* 2370 */       .save($$0);
/*      */     
/* 2372 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Items.GOLDEN_PICKAXE, (ItemLike)Items.GOLDEN_SHOVEL, (ItemLike)Items.GOLDEN_AXE, (ItemLike)Items.GOLDEN_HOE, (ItemLike)Items.GOLDEN_SWORD, (ItemLike)Items.GOLDEN_HELMET, (ItemLike)Items.GOLDEN_CHESTPLATE, (ItemLike)Items.GOLDEN_LEGGINGS, (ItemLike)Items.GOLDEN_BOOTS, (ItemLike)Items.GOLDEN_HORSE_ARMOR }, ), RecipeCategory.MISC, (ItemLike)Items.GOLD_NUGGET, 0.1F, 200)
/* 2373 */       .unlockedBy("has_golden_pickaxe", has((ItemLike)Items.GOLDEN_PICKAXE))
/* 2374 */       .unlockedBy("has_golden_shovel", has((ItemLike)Items.GOLDEN_SHOVEL))
/* 2375 */       .unlockedBy("has_golden_axe", has((ItemLike)Items.GOLDEN_AXE))
/* 2376 */       .unlockedBy("has_golden_hoe", has((ItemLike)Items.GOLDEN_HOE))
/* 2377 */       .unlockedBy("has_golden_sword", has((ItemLike)Items.GOLDEN_SWORD))
/* 2378 */       .unlockedBy("has_golden_helmet", has((ItemLike)Items.GOLDEN_HELMET))
/* 2379 */       .unlockedBy("has_golden_chestplate", has((ItemLike)Items.GOLDEN_CHESTPLATE))
/* 2380 */       .unlockedBy("has_golden_leggings", has((ItemLike)Items.GOLDEN_LEGGINGS))
/* 2381 */       .unlockedBy("has_golden_boots", has((ItemLike)Items.GOLDEN_BOOTS))
/* 2382 */       .unlockedBy("has_golden_horse_armor", has((ItemLike)Items.GOLDEN_HORSE_ARMOR))
/* 2383 */       .save($$0, getSmeltingRecipeName((ItemLike)Items.GOLD_NUGGET));
/*      */     
/* 2385 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Items.IRON_PICKAXE, (ItemLike)Items.IRON_SHOVEL, (ItemLike)Items.IRON_AXE, (ItemLike)Items.IRON_HOE, (ItemLike)Items.IRON_SWORD, (ItemLike)Items.IRON_HELMET, (ItemLike)Items.IRON_CHESTPLATE, (ItemLike)Items.IRON_LEGGINGS, (ItemLike)Items.IRON_BOOTS, (ItemLike)Items.IRON_HORSE_ARMOR, (ItemLike)Items.CHAINMAIL_HELMET, (ItemLike)Items.CHAINMAIL_CHESTPLATE, (ItemLike)Items.CHAINMAIL_LEGGINGS, (ItemLike)Items.CHAINMAIL_BOOTS }, ), RecipeCategory.MISC, (ItemLike)Items.IRON_NUGGET, 0.1F, 200)
/* 2386 */       .unlockedBy("has_iron_pickaxe", has((ItemLike)Items.IRON_PICKAXE))
/* 2387 */       .unlockedBy("has_iron_shovel", has((ItemLike)Items.IRON_SHOVEL))
/* 2388 */       .unlockedBy("has_iron_axe", has((ItemLike)Items.IRON_AXE))
/* 2389 */       .unlockedBy("has_iron_hoe", has((ItemLike)Items.IRON_HOE))
/* 2390 */       .unlockedBy("has_iron_sword", has((ItemLike)Items.IRON_SWORD))
/* 2391 */       .unlockedBy("has_iron_helmet", has((ItemLike)Items.IRON_HELMET))
/* 2392 */       .unlockedBy("has_iron_chestplate", has((ItemLike)Items.IRON_CHESTPLATE))
/* 2393 */       .unlockedBy("has_iron_leggings", has((ItemLike)Items.IRON_LEGGINGS))
/* 2394 */       .unlockedBy("has_iron_boots", has((ItemLike)Items.IRON_BOOTS))
/* 2395 */       .unlockedBy("has_iron_horse_armor", has((ItemLike)Items.IRON_HORSE_ARMOR))
/* 2396 */       .unlockedBy("has_chainmail_helmet", has((ItemLike)Items.CHAINMAIL_HELMET))
/* 2397 */       .unlockedBy("has_chainmail_chestplate", has((ItemLike)Items.CHAINMAIL_CHESTPLATE))
/* 2398 */       .unlockedBy("has_chainmail_leggings", has((ItemLike)Items.CHAINMAIL_LEGGINGS))
/* 2399 */       .unlockedBy("has_chainmail_boots", has((ItemLike)Items.CHAINMAIL_BOOTS))
/* 2400 */       .save($$0, getSmeltingRecipeName((ItemLike)Items.IRON_NUGGET));
/*      */     
/* 2402 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.CLAY }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.TERRACOTTA.asItem(), 0.35F, 200)
/* 2403 */       .unlockedBy("has_clay_block", has((ItemLike)Blocks.CLAY))
/* 2404 */       .save($$0);
/*      */     
/* 2406 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.NETHERRACK }, ), RecipeCategory.MISC, (ItemLike)Items.NETHER_BRICK, 0.1F, 200)
/* 2407 */       .unlockedBy("has_netherrack", has((ItemLike)Blocks.NETHERRACK))
/* 2408 */       .save($$0);
/*      */     
/* 2410 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.NETHER_QUARTZ_ORE }, ), RecipeCategory.MISC, (ItemLike)Items.QUARTZ, 0.2F, 200)
/* 2411 */       .unlockedBy("has_nether_quartz_ore", has((ItemLike)Blocks.NETHER_QUARTZ_ORE))
/* 2412 */       .save($$0);
/*      */     
/* 2414 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.WET_SPONGE }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SPONGE.asItem(), 0.15F, 200)
/* 2415 */       .unlockedBy("has_wet_sponge", has((ItemLike)Blocks.WET_SPONGE))
/* 2416 */       .save($$0);
/*      */     
/* 2418 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.COBBLESTONE }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.STONE.asItem(), 0.1F, 200)
/* 2419 */       .unlockedBy("has_cobblestone", has((ItemLike)Blocks.COBBLESTONE))
/* 2420 */       .save($$0);
/*      */     
/* 2422 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.STONE }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SMOOTH_STONE.asItem(), 0.1F, 200)
/* 2423 */       .unlockedBy("has_stone", has((ItemLike)Blocks.STONE))
/* 2424 */       .save($$0);
/*      */     
/* 2426 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.SANDSTONE }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SMOOTH_SANDSTONE.asItem(), 0.1F, 200)
/* 2427 */       .unlockedBy("has_sandstone", has((ItemLike)Blocks.SANDSTONE))
/* 2428 */       .save($$0);
/*      */     
/* 2430 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.RED_SANDSTONE }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SMOOTH_RED_SANDSTONE.asItem(), 0.1F, 200)
/* 2431 */       .unlockedBy("has_red_sandstone", has((ItemLike)Blocks.RED_SANDSTONE))
/* 2432 */       .save($$0);
/*      */     
/* 2434 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.QUARTZ_BLOCK }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SMOOTH_QUARTZ.asItem(), 0.1F, 200)
/* 2435 */       .unlockedBy("has_quartz_block", has((ItemLike)Blocks.QUARTZ_BLOCK))
/* 2436 */       .save($$0);
/*      */     
/* 2438 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.STONE_BRICKS }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CRACKED_STONE_BRICKS.asItem(), 0.1F, 200)
/* 2439 */       .unlockedBy("has_stone_bricks", has((ItemLike)Blocks.STONE_BRICKS))
/* 2440 */       .save($$0);
/*      */     
/* 2442 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.BLACK_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.BLACK_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2443 */       .unlockedBy("has_black_terracotta", has((ItemLike)Blocks.BLACK_TERRACOTTA))
/* 2444 */       .save($$0);
/*      */     
/* 2446 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.BLUE_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.BLUE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2447 */       .unlockedBy("has_blue_terracotta", has((ItemLike)Blocks.BLUE_TERRACOTTA))
/* 2448 */       .save($$0);
/*      */     
/* 2450 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.BROWN_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.BROWN_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2451 */       .unlockedBy("has_brown_terracotta", has((ItemLike)Blocks.BROWN_TERRACOTTA))
/* 2452 */       .save($$0);
/*      */     
/* 2454 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.CYAN_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.CYAN_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2455 */       .unlockedBy("has_cyan_terracotta", has((ItemLike)Blocks.CYAN_TERRACOTTA))
/* 2456 */       .save($$0);
/*      */     
/* 2458 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.GRAY_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.GRAY_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2459 */       .unlockedBy("has_gray_terracotta", has((ItemLike)Blocks.GRAY_TERRACOTTA))
/* 2460 */       .save($$0);
/*      */     
/* 2462 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.GREEN_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.GREEN_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2463 */       .unlockedBy("has_green_terracotta", has((ItemLike)Blocks.GREEN_TERRACOTTA))
/* 2464 */       .save($$0);
/*      */     
/* 2466 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.LIGHT_BLUE_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2467 */       .unlockedBy("has_light_blue_terracotta", has((ItemLike)Blocks.LIGHT_BLUE_TERRACOTTA))
/* 2468 */       .save($$0);
/*      */     
/* 2470 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.LIGHT_GRAY_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2471 */       .unlockedBy("has_light_gray_terracotta", has((ItemLike)Blocks.LIGHT_GRAY_TERRACOTTA))
/* 2472 */       .save($$0);
/*      */     
/* 2474 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.LIME_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.LIME_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2475 */       .unlockedBy("has_lime_terracotta", has((ItemLike)Blocks.LIME_TERRACOTTA))
/* 2476 */       .save($$0);
/*      */     
/* 2478 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.MAGENTA_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.MAGENTA_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2479 */       .unlockedBy("has_magenta_terracotta", has((ItemLike)Blocks.MAGENTA_TERRACOTTA))
/* 2480 */       .save($$0);
/*      */     
/* 2482 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.ORANGE_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.ORANGE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2483 */       .unlockedBy("has_orange_terracotta", has((ItemLike)Blocks.ORANGE_TERRACOTTA))
/* 2484 */       .save($$0);
/*      */     
/* 2486 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.PINK_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.PINK_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2487 */       .unlockedBy("has_pink_terracotta", has((ItemLike)Blocks.PINK_TERRACOTTA))
/* 2488 */       .save($$0);
/*      */     
/* 2490 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.PURPLE_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.PURPLE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2491 */       .unlockedBy("has_purple_terracotta", has((ItemLike)Blocks.PURPLE_TERRACOTTA))
/* 2492 */       .save($$0);
/*      */     
/* 2494 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.RED_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.RED_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2495 */       .unlockedBy("has_red_terracotta", has((ItemLike)Blocks.RED_TERRACOTTA))
/* 2496 */       .save($$0);
/*      */     
/* 2498 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.WHITE_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.WHITE_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2499 */       .unlockedBy("has_white_terracotta", has((ItemLike)Blocks.WHITE_TERRACOTTA))
/* 2500 */       .save($$0);
/*      */     
/* 2502 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.YELLOW_TERRACOTTA }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.YELLOW_GLAZED_TERRACOTTA.asItem(), 0.1F, 200)
/* 2503 */       .unlockedBy("has_yellow_terracotta", has((ItemLike)Blocks.YELLOW_TERRACOTTA))
/* 2504 */       .save($$0);
/*      */     
/* 2506 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.ANCIENT_DEBRIS }, ), RecipeCategory.MISC, (ItemLike)Items.NETHERITE_SCRAP, 2.0F, 200)
/* 2507 */       .unlockedBy("has_ancient_debris", has((ItemLike)Blocks.ANCIENT_DEBRIS))
/* 2508 */       .save($$0);
/*      */     
/* 2510 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.BASALT }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SMOOTH_BASALT, 0.1F, 200)
/* 2511 */       .unlockedBy("has_basalt", has((ItemLike)Blocks.BASALT))
/* 2512 */       .save($$0);
/*      */     
/* 2514 */     SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.COBBLED_DEEPSLATE }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE, 0.1F, 200)
/* 2515 */       .unlockedBy("has_cobbled_deepslate", has((ItemLike)Blocks.COBBLED_DEEPSLATE))
/* 2516 */       .save($$0);
/*      */ 
/*      */     
/* 2519 */     oreBlasting($$0, (List)COAL_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.COAL, 0.1F, 100, "coal");
/* 2520 */     oreBlasting($$0, (List)IRON_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.IRON_INGOT, 0.7F, 100, "iron_ingot");
/* 2521 */     oreBlasting($$0, (List)COPPER_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.COPPER_INGOT, 0.7F, 100, "copper_ingot");
/* 2522 */     oreBlasting($$0, (List)GOLD_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.GOLD_INGOT, 1.0F, 100, "gold_ingot");
/* 2523 */     oreBlasting($$0, (List)DIAMOND_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.DIAMOND, 1.0F, 100, "diamond");
/* 2524 */     oreBlasting($$0, (List)LAPIS_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.LAPIS_LAZULI, 0.2F, 100, "lapis_lazuli");
/* 2525 */     oreBlasting($$0, (List)REDSTONE_SMELTABLES, RecipeCategory.REDSTONE, (ItemLike)Items.REDSTONE, 0.7F, 100, "redstone");
/* 2526 */     oreBlasting($$0, (List)EMERALD_SMELTABLES, RecipeCategory.MISC, (ItemLike)Items.EMERALD, 1.0F, 100, "emerald");
/*      */     
/* 2528 */     SimpleCookingRecipeBuilder.blasting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.NETHER_QUARTZ_ORE }, ), RecipeCategory.MISC, (ItemLike)Items.QUARTZ, 0.2F, 100)
/* 2529 */       .unlockedBy("has_nether_quartz_ore", has((ItemLike)Blocks.NETHER_QUARTZ_ORE))
/* 2530 */       .save($$0, getBlastingRecipeName((ItemLike)Items.QUARTZ));
/*      */     
/* 2532 */     SimpleCookingRecipeBuilder.blasting(Ingredient.of(new ItemLike[] { (ItemLike)Items.GOLDEN_PICKAXE, (ItemLike)Items.GOLDEN_SHOVEL, (ItemLike)Items.GOLDEN_AXE, (ItemLike)Items.GOLDEN_HOE, (ItemLike)Items.GOLDEN_SWORD, (ItemLike)Items.GOLDEN_HELMET, (ItemLike)Items.GOLDEN_CHESTPLATE, (ItemLike)Items.GOLDEN_LEGGINGS, (ItemLike)Items.GOLDEN_BOOTS, (ItemLike)Items.GOLDEN_HORSE_ARMOR }, ), RecipeCategory.MISC, (ItemLike)Items.GOLD_NUGGET, 0.1F, 100)
/* 2533 */       .unlockedBy("has_golden_pickaxe", has((ItemLike)Items.GOLDEN_PICKAXE))
/* 2534 */       .unlockedBy("has_golden_shovel", has((ItemLike)Items.GOLDEN_SHOVEL))
/* 2535 */       .unlockedBy("has_golden_axe", has((ItemLike)Items.GOLDEN_AXE))
/* 2536 */       .unlockedBy("has_golden_hoe", has((ItemLike)Items.GOLDEN_HOE))
/* 2537 */       .unlockedBy("has_golden_sword", has((ItemLike)Items.GOLDEN_SWORD))
/* 2538 */       .unlockedBy("has_golden_helmet", has((ItemLike)Items.GOLDEN_HELMET))
/* 2539 */       .unlockedBy("has_golden_chestplate", has((ItemLike)Items.GOLDEN_CHESTPLATE))
/* 2540 */       .unlockedBy("has_golden_leggings", has((ItemLike)Items.GOLDEN_LEGGINGS))
/* 2541 */       .unlockedBy("has_golden_boots", has((ItemLike)Items.GOLDEN_BOOTS))
/* 2542 */       .unlockedBy("has_golden_horse_armor", has((ItemLike)Items.GOLDEN_HORSE_ARMOR))
/* 2543 */       .save($$0, getBlastingRecipeName((ItemLike)Items.GOLD_NUGGET));
/*      */     
/* 2545 */     SimpleCookingRecipeBuilder.blasting(Ingredient.of(new ItemLike[] { (ItemLike)Items.IRON_PICKAXE, (ItemLike)Items.IRON_SHOVEL, (ItemLike)Items.IRON_AXE, (ItemLike)Items.IRON_HOE, (ItemLike)Items.IRON_SWORD, (ItemLike)Items.IRON_HELMET, (ItemLike)Items.IRON_CHESTPLATE, (ItemLike)Items.IRON_LEGGINGS, (ItemLike)Items.IRON_BOOTS, (ItemLike)Items.IRON_HORSE_ARMOR, (ItemLike)Items.CHAINMAIL_HELMET, (ItemLike)Items.CHAINMAIL_CHESTPLATE, (ItemLike)Items.CHAINMAIL_LEGGINGS, (ItemLike)Items.CHAINMAIL_BOOTS }, ), RecipeCategory.MISC, (ItemLike)Items.IRON_NUGGET, 0.1F, 100)
/* 2546 */       .unlockedBy("has_iron_pickaxe", has((ItemLike)Items.IRON_PICKAXE))
/* 2547 */       .unlockedBy("has_iron_shovel", has((ItemLike)Items.IRON_SHOVEL))
/* 2548 */       .unlockedBy("has_iron_axe", has((ItemLike)Items.IRON_AXE))
/* 2549 */       .unlockedBy("has_iron_hoe", has((ItemLike)Items.IRON_HOE))
/* 2550 */       .unlockedBy("has_iron_sword", has((ItemLike)Items.IRON_SWORD))
/* 2551 */       .unlockedBy("has_iron_helmet", has((ItemLike)Items.IRON_HELMET))
/* 2552 */       .unlockedBy("has_iron_chestplate", has((ItemLike)Items.IRON_CHESTPLATE))
/* 2553 */       .unlockedBy("has_iron_leggings", has((ItemLike)Items.IRON_LEGGINGS))
/* 2554 */       .unlockedBy("has_iron_boots", has((ItemLike)Items.IRON_BOOTS))
/* 2555 */       .unlockedBy("has_iron_horse_armor", has((ItemLike)Items.IRON_HORSE_ARMOR))
/* 2556 */       .unlockedBy("has_chainmail_helmet", has((ItemLike)Items.CHAINMAIL_HELMET))
/* 2557 */       .unlockedBy("has_chainmail_chestplate", has((ItemLike)Items.CHAINMAIL_CHESTPLATE))
/* 2558 */       .unlockedBy("has_chainmail_leggings", has((ItemLike)Items.CHAINMAIL_LEGGINGS))
/* 2559 */       .unlockedBy("has_chainmail_boots", has((ItemLike)Items.CHAINMAIL_BOOTS))
/* 2560 */       .save($$0, getBlastingRecipeName((ItemLike)Items.IRON_NUGGET));
/*      */     
/* 2562 */     SimpleCookingRecipeBuilder.blasting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.ANCIENT_DEBRIS }, ), RecipeCategory.MISC, (ItemLike)Items.NETHERITE_SCRAP, 2.0F, 100)
/* 2563 */       .unlockedBy("has_ancient_debris", has((ItemLike)Blocks.ANCIENT_DEBRIS))
/* 2564 */       .save($$0, getBlastingRecipeName((ItemLike)Items.NETHERITE_SCRAP));
/*      */     
/* 2566 */     cookRecipes($$0, "smoking", RecipeSerializer.SMOKING_RECIPE, net.minecraft.world.item.crafting.SmokingRecipe::new, 100);
/* 2567 */     cookRecipes($$0, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, net.minecraft.world.item.crafting.CampfireCookingRecipe::new, 600);
/*      */ 
/*      */     
/* 2570 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.STONE_SLAB, (ItemLike)Blocks.STONE, 2);
/* 2571 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.STONE_STAIRS, (ItemLike)Blocks.STONE);
/* 2572 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.STONE_BRICKS, (ItemLike)Blocks.STONE);
/* 2573 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.STONE_BRICK_SLAB, (ItemLike)Blocks.STONE, 2);
/* 2574 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.STONE_BRICK_STAIRS, (ItemLike)Blocks.STONE);
/*      */     
/* 2576 */     SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.STONE }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_STONE_BRICKS)
/* 2577 */       .unlockedBy("has_stone", has((ItemLike)Blocks.STONE))
/* 2578 */       .save($$0, "chiseled_stone_bricks_stone_from_stonecutting");
/*      */     
/* 2580 */     SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.STONE }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.STONE_BRICK_WALL)
/* 2581 */       .unlockedBy("has_stone", has((ItemLike)Blocks.STONE))
/* 2582 */       .save($$0, "stone_brick_walls_from_stone_stonecutting");
/*      */     
/* 2584 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CUT_SANDSTONE, (ItemLike)Blocks.SANDSTONE);
/* 2585 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SANDSTONE_SLAB, (ItemLike)Blocks.SANDSTONE, 2);
/* 2586 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CUT_SANDSTONE_SLAB, (ItemLike)Blocks.SANDSTONE, 2);
/*      */     
/* 2588 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CUT_SANDSTONE_SLAB, (ItemLike)Blocks.CUT_SANDSTONE, 2);
/*      */     
/* 2590 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SANDSTONE_STAIRS, (ItemLike)Blocks.SANDSTONE);
/* 2591 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.SANDSTONE_WALL, (ItemLike)Blocks.SANDSTONE);
/* 2592 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_SANDSTONE, (ItemLike)Blocks.SANDSTONE);
/*      */     
/* 2594 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CUT_RED_SANDSTONE, (ItemLike)Blocks.RED_SANDSTONE);
/* 2595 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.RED_SANDSTONE_SLAB, (ItemLike)Blocks.RED_SANDSTONE, 2);
/* 2596 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CUT_RED_SANDSTONE_SLAB, (ItemLike)Blocks.RED_SANDSTONE, 2);
/*      */     
/* 2598 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CUT_RED_SANDSTONE_SLAB, (ItemLike)Blocks.CUT_RED_SANDSTONE, 2);
/*      */     
/* 2600 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.RED_SANDSTONE_STAIRS, (ItemLike)Blocks.RED_SANDSTONE);
/* 2601 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.RED_SANDSTONE_WALL, (ItemLike)Blocks.RED_SANDSTONE);
/* 2602 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_RED_SANDSTONE, (ItemLike)Blocks.RED_SANDSTONE);
/*      */     
/* 2604 */     SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.QUARTZ_BLOCK }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.QUARTZ_SLAB, 2)
/* 2605 */       .unlockedBy("has_quartz_block", has((ItemLike)Blocks.QUARTZ_BLOCK))
/* 2606 */       .save($$0, "quartz_slab_from_stonecutting");
/*      */     
/* 2608 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.QUARTZ_STAIRS, (ItemLike)Blocks.QUARTZ_BLOCK);
/* 2609 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.QUARTZ_PILLAR, (ItemLike)Blocks.QUARTZ_BLOCK);
/* 2610 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_QUARTZ_BLOCK, (ItemLike)Blocks.QUARTZ_BLOCK);
/* 2611 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.QUARTZ_BRICKS, (ItemLike)Blocks.QUARTZ_BLOCK);
/*      */     
/* 2613 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.COBBLESTONE_STAIRS, (ItemLike)Blocks.COBBLESTONE);
/* 2614 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.COBBLESTONE_SLAB, (ItemLike)Blocks.COBBLESTONE, 2);
/* 2615 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.COBBLESTONE_WALL, (ItemLike)Blocks.COBBLESTONE);
/*      */     
/* 2617 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.STONE_BRICK_SLAB, (ItemLike)Blocks.STONE_BRICKS, 2);
/* 2618 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.STONE_BRICK_STAIRS, (ItemLike)Blocks.STONE_BRICKS);
/*      */     
/* 2620 */     SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.STONE_BRICKS }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.STONE_BRICK_WALL)
/* 2621 */       .unlockedBy("has_stone_bricks", has((ItemLike)Blocks.STONE_BRICKS))
/* 2622 */       .save($$0, "stone_brick_wall_from_stone_bricks_stonecutting");
/*      */     
/* 2624 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_STONE_BRICKS, (ItemLike)Blocks.STONE_BRICKS);
/* 2625 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.BRICK_SLAB, (ItemLike)Blocks.BRICKS, 2);
/* 2626 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.BRICK_STAIRS, (ItemLike)Blocks.BRICKS);
/* 2627 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.BRICK_WALL, (ItemLike)Blocks.BRICKS);
/*      */     
/* 2629 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MUD_BRICK_SLAB, (ItemLike)Blocks.MUD_BRICKS, 2);
/* 2630 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MUD_BRICK_STAIRS, (ItemLike)Blocks.MUD_BRICKS);
/* 2631 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.MUD_BRICK_WALL, (ItemLike)Blocks.MUD_BRICKS);
/*      */     
/* 2633 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.NETHER_BRICK_SLAB, (ItemLike)Blocks.NETHER_BRICKS, 2);
/* 2634 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.NETHER_BRICK_STAIRS, (ItemLike)Blocks.NETHER_BRICKS);
/* 2635 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.NETHER_BRICK_WALL, (ItemLike)Blocks.NETHER_BRICKS);
/* 2636 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_NETHER_BRICKS, (ItemLike)Blocks.NETHER_BRICKS);
/*      */     
/* 2638 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.RED_NETHER_BRICK_SLAB, (ItemLike)Blocks.RED_NETHER_BRICKS, 2);
/* 2639 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.RED_NETHER_BRICK_STAIRS, (ItemLike)Blocks.RED_NETHER_BRICKS);
/* 2640 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.RED_NETHER_BRICK_WALL, (ItemLike)Blocks.RED_NETHER_BRICKS);
/*      */     
/* 2642 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PURPUR_SLAB, (ItemLike)Blocks.PURPUR_BLOCK, 2);
/* 2643 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PURPUR_STAIRS, (ItemLike)Blocks.PURPUR_BLOCK);
/* 2644 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PURPUR_PILLAR, (ItemLike)Blocks.PURPUR_BLOCK);
/*      */     
/* 2646 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PRISMARINE_SLAB, (ItemLike)Blocks.PRISMARINE, 2);
/* 2647 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PRISMARINE_STAIRS, (ItemLike)Blocks.PRISMARINE);
/* 2648 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.PRISMARINE_WALL, (ItemLike)Blocks.PRISMARINE);
/*      */     
/* 2650 */     SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.PRISMARINE_BRICKS }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PRISMARINE_BRICK_SLAB, 2)
/* 2651 */       .unlockedBy("has_prismarine_brick", has((ItemLike)Blocks.PRISMARINE_BRICKS))
/* 2652 */       .save($$0, "prismarine_brick_slab_from_prismarine_stonecutting");
/*      */     
/* 2654 */     SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.PRISMARINE_BRICKS }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.PRISMARINE_BRICK_STAIRS)
/* 2655 */       .unlockedBy("has_prismarine_brick", has((ItemLike)Blocks.PRISMARINE_BRICKS))
/* 2656 */       .save($$0, "prismarine_brick_stairs_from_prismarine_stonecutting");
/*      */     
/* 2658 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DARK_PRISMARINE_SLAB, (ItemLike)Blocks.DARK_PRISMARINE, 2);
/* 2659 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DARK_PRISMARINE_STAIRS, (ItemLike)Blocks.DARK_PRISMARINE);
/*      */     
/* 2661 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.ANDESITE_SLAB, (ItemLike)Blocks.ANDESITE, 2);
/* 2662 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.ANDESITE_STAIRS, (ItemLike)Blocks.ANDESITE);
/*      */     
/* 2664 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.ANDESITE_WALL, (ItemLike)Blocks.ANDESITE);
/* 2665 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_ANDESITE, (ItemLike)Blocks.ANDESITE);
/*      */     
/* 2667 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_ANDESITE_SLAB, (ItemLike)Blocks.ANDESITE, 2);
/* 2668 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_ANDESITE_STAIRS, (ItemLike)Blocks.ANDESITE);
/*      */     
/* 2670 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_ANDESITE_SLAB, (ItemLike)Blocks.POLISHED_ANDESITE, 2);
/* 2671 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_ANDESITE_STAIRS, (ItemLike)Blocks.POLISHED_ANDESITE);
/*      */     
/* 2673 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BASALT, (ItemLike)Blocks.BASALT);
/*      */     
/* 2675 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.GRANITE_SLAB, (ItemLike)Blocks.GRANITE, 2);
/* 2676 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.GRANITE_STAIRS, (ItemLike)Blocks.GRANITE);
/* 2677 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.GRANITE_WALL, (ItemLike)Blocks.GRANITE);
/* 2678 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_GRANITE, (ItemLike)Blocks.GRANITE);
/*      */     
/* 2680 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_GRANITE_SLAB, (ItemLike)Blocks.GRANITE, 2);
/* 2681 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_GRANITE_STAIRS, (ItemLike)Blocks.GRANITE);
/*      */     
/* 2683 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_GRANITE_SLAB, (ItemLike)Blocks.POLISHED_GRANITE, 2);
/* 2684 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_GRANITE_STAIRS, (ItemLike)Blocks.POLISHED_GRANITE);
/*      */     
/* 2686 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DIORITE_SLAB, (ItemLike)Blocks.DIORITE, 2);
/* 2687 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DIORITE_STAIRS, (ItemLike)Blocks.DIORITE);
/* 2688 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.DIORITE_WALL, (ItemLike)Blocks.DIORITE);
/*      */     
/* 2690 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_DIORITE, (ItemLike)Blocks.DIORITE);
/* 2691 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_DIORITE_SLAB, (ItemLike)Blocks.DIORITE, 2);
/* 2692 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_DIORITE_STAIRS, (ItemLike)Blocks.DIORITE);
/*      */     
/* 2694 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_DIORITE_SLAB, (ItemLike)Blocks.POLISHED_DIORITE, 2);
/* 2695 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_DIORITE_STAIRS, (ItemLike)Blocks.POLISHED_DIORITE);
/*      */     
/* 2697 */     SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.MOSSY_STONE_BRICKS }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MOSSY_STONE_BRICK_SLAB, 2)
/* 2698 */       .unlockedBy("has_mossy_stone_bricks", has((ItemLike)Blocks.MOSSY_STONE_BRICKS))
/* 2699 */       .save($$0, "mossy_stone_brick_slab_from_mossy_stone_brick_stonecutting");
/*      */     
/* 2701 */     SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.MOSSY_STONE_BRICKS }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MOSSY_STONE_BRICK_STAIRS)
/* 2702 */       .unlockedBy("has_mossy_stone_bricks", has((ItemLike)Blocks.MOSSY_STONE_BRICKS))
/* 2703 */       .save($$0, "mossy_stone_brick_stairs_from_mossy_stone_brick_stonecutting");
/*      */     
/* 2705 */     SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.MOSSY_STONE_BRICKS }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.MOSSY_STONE_BRICK_WALL)
/* 2706 */       .unlockedBy("has_mossy_stone_bricks", has((ItemLike)Blocks.MOSSY_STONE_BRICKS))
/* 2707 */       .save($$0, "mossy_stone_brick_wall_from_mossy_stone_brick_stonecutting");
/*      */     
/* 2709 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MOSSY_COBBLESTONE_SLAB, (ItemLike)Blocks.MOSSY_COBBLESTONE, 2);
/* 2710 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.MOSSY_COBBLESTONE_STAIRS, (ItemLike)Blocks.MOSSY_COBBLESTONE);
/* 2711 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.MOSSY_COBBLESTONE_WALL, (ItemLike)Blocks.MOSSY_COBBLESTONE);
/*      */     
/* 2713 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SMOOTH_SANDSTONE_SLAB, (ItemLike)Blocks.SMOOTH_SANDSTONE, 2);
/* 2714 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SMOOTH_SANDSTONE_STAIRS, (ItemLike)Blocks.SMOOTH_SANDSTONE);
/*      */     
/* 2716 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SMOOTH_RED_SANDSTONE_SLAB, (ItemLike)Blocks.SMOOTH_RED_SANDSTONE, 2);
/* 2717 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SMOOTH_RED_SANDSTONE_STAIRS, (ItemLike)Blocks.SMOOTH_RED_SANDSTONE);
/*      */     
/* 2719 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SMOOTH_QUARTZ_SLAB, (ItemLike)Blocks.SMOOTH_QUARTZ, 2);
/* 2720 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SMOOTH_QUARTZ_STAIRS, (ItemLike)Blocks.SMOOTH_QUARTZ);
/*      */     
/* 2722 */     SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.END_STONE_BRICKS }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.END_STONE_BRICK_SLAB, 2)
/* 2723 */       .unlockedBy("has_end_stone_brick", has((ItemLike)Blocks.END_STONE_BRICKS))
/* 2724 */       .save($$0, "end_stone_brick_slab_from_end_stone_brick_stonecutting");
/*      */     
/* 2726 */     SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.END_STONE_BRICKS }, ), RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.END_STONE_BRICK_STAIRS)
/* 2727 */       .unlockedBy("has_end_stone_brick", has((ItemLike)Blocks.END_STONE_BRICKS))
/* 2728 */       .save($$0, "end_stone_brick_stairs_from_end_stone_brick_stonecutting");
/*      */     
/* 2730 */     SingleItemRecipeBuilder.stonecutting(Ingredient.of(new ItemLike[] { (ItemLike)Blocks.END_STONE_BRICKS }, ), RecipeCategory.DECORATIONS, (ItemLike)Blocks.END_STONE_BRICK_WALL)
/* 2731 */       .unlockedBy("has_end_stone_brick", has((ItemLike)Blocks.END_STONE_BRICKS))
/* 2732 */       .save($$0, "end_stone_brick_wall_from_end_stone_brick_stonecutting");
/*      */     
/* 2734 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.END_STONE_BRICKS, (ItemLike)Blocks.END_STONE);
/* 2735 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.END_STONE_BRICK_SLAB, (ItemLike)Blocks.END_STONE, 2);
/* 2736 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.END_STONE_BRICK_STAIRS, (ItemLike)Blocks.END_STONE);
/* 2737 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.END_STONE_BRICK_WALL, (ItemLike)Blocks.END_STONE);
/*      */     
/* 2739 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.SMOOTH_STONE_SLAB, (ItemLike)Blocks.SMOOTH_STONE, 2);
/*      */     
/* 2741 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.BLACKSTONE_SLAB, (ItemLike)Blocks.BLACKSTONE, 2);
/* 2742 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.BLACKSTONE_STAIRS, (ItemLike)Blocks.BLACKSTONE);
/* 2743 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.BLACKSTONE_WALL, (ItemLike)Blocks.BLACKSTONE);
/* 2744 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BLACKSTONE, (ItemLike)Blocks.BLACKSTONE);
/* 2745 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.POLISHED_BLACKSTONE_WALL, (ItemLike)Blocks.BLACKSTONE);
/* 2746 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BLACKSTONE_SLAB, (ItemLike)Blocks.BLACKSTONE, 2);
/* 2747 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BLACKSTONE_STAIRS, (ItemLike)Blocks.BLACKSTONE);
/* 2748 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_POLISHED_BLACKSTONE, (ItemLike)Blocks.BLACKSTONE);
/* 2749 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICKS, (ItemLike)Blocks.BLACKSTONE);
/* 2750 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, (ItemLike)Blocks.BLACKSTONE, 2);
/* 2751 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, (ItemLike)Blocks.BLACKSTONE);
/* 2752 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICK_WALL, (ItemLike)Blocks.BLACKSTONE);
/*      */     
/* 2754 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BLACKSTONE_SLAB, (ItemLike)Blocks.POLISHED_BLACKSTONE, 2);
/* 2755 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BLACKSTONE_STAIRS, (ItemLike)Blocks.POLISHED_BLACKSTONE);
/* 2756 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICKS, (ItemLike)Blocks.POLISHED_BLACKSTONE);
/* 2757 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.POLISHED_BLACKSTONE_WALL, (ItemLike)Blocks.POLISHED_BLACKSTONE);
/* 2758 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, (ItemLike)Blocks.POLISHED_BLACKSTONE, 2);
/* 2759 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, (ItemLike)Blocks.POLISHED_BLACKSTONE);
/* 2760 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICK_WALL, (ItemLike)Blocks.POLISHED_BLACKSTONE);
/* 2761 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_POLISHED_BLACKSTONE, (ItemLike)Blocks.POLISHED_BLACKSTONE);
/*      */     
/* 2763 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICKS, 2);
/* 2764 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICKS);
/* 2765 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICK_WALL, (ItemLike)Blocks.POLISHED_BLACKSTONE_BRICKS);
/*      */     
/* 2767 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CUT_COPPER_SLAB, (ItemLike)Blocks.CUT_COPPER, 2);
/* 2768 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CUT_COPPER_STAIRS, (ItemLike)Blocks.CUT_COPPER);
/* 2769 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.EXPOSED_CUT_COPPER_SLAB, (ItemLike)Blocks.EXPOSED_CUT_COPPER, 2);
/* 2770 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.EXPOSED_CUT_COPPER_STAIRS, (ItemLike)Blocks.EXPOSED_CUT_COPPER);
/* 2771 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WEATHERED_CUT_COPPER_SLAB, (ItemLike)Blocks.WEATHERED_CUT_COPPER, 2);
/* 2772 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WEATHERED_CUT_COPPER_STAIRS, (ItemLike)Blocks.WEATHERED_CUT_COPPER);
/* 2773 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.OXIDIZED_CUT_COPPER_SLAB, (ItemLike)Blocks.OXIDIZED_CUT_COPPER, 2);
/* 2774 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.OXIDIZED_CUT_COPPER_STAIRS, (ItemLike)Blocks.OXIDIZED_CUT_COPPER);
/* 2775 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_CUT_COPPER_SLAB, (ItemLike)Blocks.WAXED_CUT_COPPER, 2);
/* 2776 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_CUT_COPPER_STAIRS, (ItemLike)Blocks.WAXED_CUT_COPPER);
/* 2777 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, (ItemLike)Blocks.WAXED_EXPOSED_CUT_COPPER, 2);
/* 2778 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS, (ItemLike)Blocks.WAXED_EXPOSED_CUT_COPPER);
/* 2779 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, (ItemLike)Blocks.WAXED_WEATHERED_CUT_COPPER, 2);
/* 2780 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS, (ItemLike)Blocks.WAXED_WEATHERED_CUT_COPPER);
/* 2781 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, (ItemLike)Blocks.WAXED_OXIDIZED_CUT_COPPER, 2);
/* 2782 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS, (ItemLike)Blocks.WAXED_OXIDIZED_CUT_COPPER);
/* 2783 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CUT_COPPER, (ItemLike)Blocks.COPPER_BLOCK, 4);
/* 2784 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CUT_COPPER_STAIRS, (ItemLike)Blocks.COPPER_BLOCK, 4);
/* 2785 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CUT_COPPER_SLAB, (ItemLike)Blocks.COPPER_BLOCK, 8);
/* 2786 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.EXPOSED_CUT_COPPER, (ItemLike)Blocks.EXPOSED_COPPER, 4);
/* 2787 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.EXPOSED_CUT_COPPER_STAIRS, (ItemLike)Blocks.EXPOSED_COPPER, 4);
/* 2788 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.EXPOSED_CUT_COPPER_SLAB, (ItemLike)Blocks.EXPOSED_COPPER, 8);
/* 2789 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WEATHERED_CUT_COPPER, (ItemLike)Blocks.WEATHERED_COPPER, 4);
/* 2790 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WEATHERED_CUT_COPPER_STAIRS, (ItemLike)Blocks.WEATHERED_COPPER, 4);
/* 2791 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WEATHERED_CUT_COPPER_SLAB, (ItemLike)Blocks.WEATHERED_COPPER, 8);
/* 2792 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.OXIDIZED_CUT_COPPER, (ItemLike)Blocks.OXIDIZED_COPPER, 4);
/* 2793 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.OXIDIZED_CUT_COPPER_STAIRS, (ItemLike)Blocks.OXIDIZED_COPPER, 4);
/* 2794 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.OXIDIZED_CUT_COPPER_SLAB, (ItemLike)Blocks.OXIDIZED_COPPER, 8);
/* 2795 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_CUT_COPPER, (ItemLike)Blocks.WAXED_COPPER_BLOCK, 4);
/* 2796 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_CUT_COPPER_STAIRS, (ItemLike)Blocks.WAXED_COPPER_BLOCK, 4);
/* 2797 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_CUT_COPPER_SLAB, (ItemLike)Blocks.WAXED_COPPER_BLOCK, 8);
/* 2798 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_EXPOSED_CUT_COPPER, (ItemLike)Blocks.WAXED_EXPOSED_COPPER, 4);
/* 2799 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS, (ItemLike)Blocks.WAXED_EXPOSED_COPPER, 4);
/* 2800 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, (ItemLike)Blocks.WAXED_EXPOSED_COPPER, 8);
/* 2801 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_WEATHERED_CUT_COPPER, (ItemLike)Blocks.WAXED_WEATHERED_COPPER, 4);
/* 2802 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS, (ItemLike)Blocks.WAXED_WEATHERED_COPPER, 4);
/* 2803 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, (ItemLike)Blocks.WAXED_WEATHERED_COPPER, 8);
/* 2804 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_OXIDIZED_CUT_COPPER, (ItemLike)Blocks.WAXED_OXIDIZED_COPPER, 4);
/* 2805 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS, (ItemLike)Blocks.WAXED_OXIDIZED_COPPER, 4);
/* 2806 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, (ItemLike)Blocks.WAXED_OXIDIZED_COPPER, 8);
/*      */     
/* 2808 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.COBBLED_DEEPSLATE_SLAB, (ItemLike)Blocks.COBBLED_DEEPSLATE, 2);
/* 2809 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.COBBLED_DEEPSLATE_STAIRS, (ItemLike)Blocks.COBBLED_DEEPSLATE);
/* 2810 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.COBBLED_DEEPSLATE_WALL, (ItemLike)Blocks.COBBLED_DEEPSLATE);
/* 2811 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_DEEPSLATE, (ItemLike)Blocks.COBBLED_DEEPSLATE);
/* 2812 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_DEEPSLATE, (ItemLike)Blocks.COBBLED_DEEPSLATE);
/* 2813 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_DEEPSLATE_SLAB, (ItemLike)Blocks.COBBLED_DEEPSLATE, 2);
/* 2814 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_DEEPSLATE_STAIRS, (ItemLike)Blocks.COBBLED_DEEPSLATE);
/* 2815 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.POLISHED_DEEPSLATE_WALL, (ItemLike)Blocks.COBBLED_DEEPSLATE);
/* 2816 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_BRICKS, (ItemLike)Blocks.COBBLED_DEEPSLATE);
/* 2817 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_BRICK_SLAB, (ItemLike)Blocks.COBBLED_DEEPSLATE, 2);
/* 2818 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_BRICK_STAIRS, (ItemLike)Blocks.COBBLED_DEEPSLATE);
/* 2819 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.DEEPSLATE_BRICK_WALL, (ItemLike)Blocks.COBBLED_DEEPSLATE);
/* 2820 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_TILES, (ItemLike)Blocks.COBBLED_DEEPSLATE);
/* 2821 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_TILE_SLAB, (ItemLike)Blocks.COBBLED_DEEPSLATE, 2);
/* 2822 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_TILE_STAIRS, (ItemLike)Blocks.COBBLED_DEEPSLATE);
/* 2823 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.DEEPSLATE_TILE_WALL, (ItemLike)Blocks.COBBLED_DEEPSLATE);
/*      */     
/* 2825 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_DEEPSLATE_SLAB, (ItemLike)Blocks.POLISHED_DEEPSLATE, 2);
/* 2826 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.POLISHED_DEEPSLATE_STAIRS, (ItemLike)Blocks.POLISHED_DEEPSLATE);
/* 2827 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.POLISHED_DEEPSLATE_WALL, (ItemLike)Blocks.POLISHED_DEEPSLATE);
/* 2828 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_BRICKS, (ItemLike)Blocks.POLISHED_DEEPSLATE);
/* 2829 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_BRICK_SLAB, (ItemLike)Blocks.POLISHED_DEEPSLATE, 2);
/* 2830 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_BRICK_STAIRS, (ItemLike)Blocks.POLISHED_DEEPSLATE);
/* 2831 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.DEEPSLATE_BRICK_WALL, (ItemLike)Blocks.POLISHED_DEEPSLATE);
/* 2832 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_TILES, (ItemLike)Blocks.POLISHED_DEEPSLATE);
/* 2833 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_TILE_SLAB, (ItemLike)Blocks.POLISHED_DEEPSLATE, 2);
/* 2834 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_TILE_STAIRS, (ItemLike)Blocks.POLISHED_DEEPSLATE);
/* 2835 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.DEEPSLATE_TILE_WALL, (ItemLike)Blocks.POLISHED_DEEPSLATE);
/*      */     
/* 2837 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_BRICK_SLAB, (ItemLike)Blocks.DEEPSLATE_BRICKS, 2);
/* 2838 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_BRICK_STAIRS, (ItemLike)Blocks.DEEPSLATE_BRICKS);
/* 2839 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.DEEPSLATE_BRICK_WALL, (ItemLike)Blocks.DEEPSLATE_BRICKS);
/* 2840 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_TILES, (ItemLike)Blocks.DEEPSLATE_BRICKS);
/* 2841 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_TILE_SLAB, (ItemLike)Blocks.DEEPSLATE_BRICKS, 2);
/* 2842 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_TILE_STAIRS, (ItemLike)Blocks.DEEPSLATE_BRICKS);
/* 2843 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.DEEPSLATE_TILE_WALL, (ItemLike)Blocks.DEEPSLATE_BRICKS);
/*      */     
/* 2845 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_TILE_SLAB, (ItemLike)Blocks.DEEPSLATE_TILES, 2);
/* 2846 */     stonecutterResultFromBase($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.DEEPSLATE_TILE_STAIRS, (ItemLike)Blocks.DEEPSLATE_TILES);
/* 2847 */     stonecutterResultFromBase($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.DEEPSLATE_TILE_WALL, (ItemLike)Blocks.DEEPSLATE_TILES);
/*      */     
/* 2849 */     smithingTrims().forEach($$1 -> trimSmithing($$0, $$1.template(), $$1.id()));
/*      */     
/* 2851 */     netheriteSmithing($$0, Items.DIAMOND_CHESTPLATE, RecipeCategory.COMBAT, Items.NETHERITE_CHESTPLATE);
/* 2852 */     netheriteSmithing($$0, Items.DIAMOND_LEGGINGS, RecipeCategory.COMBAT, Items.NETHERITE_LEGGINGS);
/* 2853 */     netheriteSmithing($$0, Items.DIAMOND_HELMET, RecipeCategory.COMBAT, Items.NETHERITE_HELMET);
/* 2854 */     netheriteSmithing($$0, Items.DIAMOND_BOOTS, RecipeCategory.COMBAT, Items.NETHERITE_BOOTS);
/* 2855 */     netheriteSmithing($$0, Items.DIAMOND_SWORD, RecipeCategory.COMBAT, Items.NETHERITE_SWORD);
/* 2856 */     netheriteSmithing($$0, Items.DIAMOND_AXE, RecipeCategory.TOOLS, Items.NETHERITE_AXE);
/* 2857 */     netheriteSmithing($$0, Items.DIAMOND_PICKAXE, RecipeCategory.TOOLS, Items.NETHERITE_PICKAXE);
/* 2858 */     netheriteSmithing($$0, Items.DIAMOND_HOE, RecipeCategory.TOOLS, Items.NETHERITE_HOE);
/* 2859 */     netheriteSmithing($$0, Items.DIAMOND_SHOVEL, RecipeCategory.TOOLS, Items.NETHERITE_SHOVEL);
/*      */     
/* 2861 */     copySmithingTemplate($$0, (ItemLike)Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, (ItemLike)Items.NETHERRACK);
/* 2862 */     copySmithingTemplate($$0, (ItemLike)Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.COBBLESTONE);
/* 2863 */     copySmithingTemplate($$0, (ItemLike)Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.SANDSTONE);
/* 2864 */     copySmithingTemplate($$0, (ItemLike)Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.COBBLESTONE);
/* 2865 */     copySmithingTemplate($$0, (ItemLike)Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.MOSSY_COBBLESTONE);
/* 2866 */     copySmithingTemplate($$0, (ItemLike)Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.COBBLED_DEEPSLATE);
/* 2867 */     copySmithingTemplate($$0, (ItemLike)Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.END_STONE);
/* 2868 */     copySmithingTemplate($$0, (ItemLike)Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.COBBLESTONE);
/* 2869 */     copySmithingTemplate($$0, (ItemLike)Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.PRISMARINE);
/* 2870 */     copySmithingTemplate($$0, (ItemLike)Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.BLACKSTONE);
/* 2871 */     copySmithingTemplate($$0, (ItemLike)Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.NETHERRACK);
/* 2872 */     copySmithingTemplate($$0, (ItemLike)Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.PURPUR_BLOCK);
/* 2873 */     copySmithingTemplate($$0, (ItemLike)Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.COBBLED_DEEPSLATE);
/* 2874 */     copySmithingTemplate($$0, (ItemLike)Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.TERRACOTTA);
/* 2875 */     copySmithingTemplate($$0, (ItemLike)Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.TERRACOTTA);
/* 2876 */     copySmithingTemplate($$0, (ItemLike)Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.TERRACOTTA);
/* 2877 */     copySmithingTemplate($$0, (ItemLike)Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, (ItemLike)Items.TERRACOTTA);
/*      */     
/* 2879 */     threeByThreePacker($$0, RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.BAMBOO_BLOCK, (ItemLike)Items.BAMBOO);
/*      */     
/* 2881 */     planksFromLogs($$0, (ItemLike)Blocks.BAMBOO_PLANKS, ItemTags.BAMBOO_BLOCKS, 2);
/*      */     
/* 2883 */     mosaicBuilder($$0, RecipeCategory.DECORATIONS, (ItemLike)Blocks.BAMBOO_MOSAIC, (ItemLike)Blocks.BAMBOO_SLAB);
/* 2884 */     woodenBoat($$0, (ItemLike)Items.BAMBOO_RAFT, (ItemLike)Blocks.BAMBOO_PLANKS);
/* 2885 */     chestBoat($$0, (ItemLike)Items.BAMBOO_CHEST_RAFT, (ItemLike)Items.BAMBOO_RAFT);
/*      */     
/* 2887 */     hangingSign($$0, (ItemLike)Items.OAK_HANGING_SIGN, (ItemLike)Blocks.STRIPPED_OAK_LOG);
/* 2888 */     hangingSign($$0, (ItemLike)Items.SPRUCE_HANGING_SIGN, (ItemLike)Blocks.STRIPPED_SPRUCE_LOG);
/* 2889 */     hangingSign($$0, (ItemLike)Items.BIRCH_HANGING_SIGN, (ItemLike)Blocks.STRIPPED_BIRCH_LOG);
/* 2890 */     hangingSign($$0, (ItemLike)Items.JUNGLE_HANGING_SIGN, (ItemLike)Blocks.STRIPPED_JUNGLE_LOG);
/* 2891 */     hangingSign($$0, (ItemLike)Items.ACACIA_HANGING_SIGN, (ItemLike)Blocks.STRIPPED_ACACIA_LOG);
/* 2892 */     hangingSign($$0, (ItemLike)Items.CHERRY_HANGING_SIGN, (ItemLike)Blocks.STRIPPED_CHERRY_LOG);
/* 2893 */     hangingSign($$0, (ItemLike)Items.DARK_OAK_HANGING_SIGN, (ItemLike)Blocks.STRIPPED_DARK_OAK_LOG);
/* 2894 */     hangingSign($$0, (ItemLike)Items.MANGROVE_HANGING_SIGN, (ItemLike)Blocks.STRIPPED_MANGROVE_LOG);
/* 2895 */     hangingSign($$0, (ItemLike)Items.BAMBOO_HANGING_SIGN, (ItemLike)Items.STRIPPED_BAMBOO_BLOCK);
/* 2896 */     hangingSign($$0, (ItemLike)Items.CRIMSON_HANGING_SIGN, (ItemLike)Blocks.STRIPPED_CRIMSON_STEM);
/* 2897 */     hangingSign($$0, (ItemLike)Items.WARPED_HANGING_SIGN, (ItemLike)Blocks.STRIPPED_WARPED_STEM);
/*      */     
/* 2899 */     ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)Blocks.CHISELED_BOOKSHELF)
/* 2900 */       .define(Character.valueOf('#'), ItemTags.PLANKS)
/* 2901 */       .define(Character.valueOf('X'), ItemTags.WOODEN_SLABS)
/* 2902 */       .pattern("###")
/* 2903 */       .pattern("XXX")
/* 2904 */       .pattern("###")
/* 2905 */       .unlockedBy("has_book", has((ItemLike)Items.BOOK))
/* 2906 */       .save($$0);
/*      */     
/* 2908 */     oneToOneConversionRecipe($$0, (ItemLike)Items.ORANGE_DYE, (ItemLike)Blocks.TORCHFLOWER, "orange_dye");
/* 2909 */     oneToOneConversionRecipe($$0, (ItemLike)Items.CYAN_DYE, (ItemLike)Blocks.PITCHER_PLANT, "cyan_dye", 2);
/*      */     
/* 2911 */     planksFromLog($$0, (ItemLike)Blocks.CHERRY_PLANKS, ItemTags.CHERRY_LOGS, 4);
/* 2912 */     woodFromLogs($$0, (ItemLike)Blocks.CHERRY_WOOD, (ItemLike)Blocks.CHERRY_LOG);
/* 2913 */     woodFromLogs($$0, (ItemLike)Blocks.STRIPPED_CHERRY_WOOD, (ItemLike)Blocks.STRIPPED_CHERRY_LOG);
/* 2914 */     woodenBoat($$0, (ItemLike)Items.CHERRY_BOAT, (ItemLike)Blocks.CHERRY_PLANKS);
/* 2915 */     chestBoat($$0, (ItemLike)Items.CHERRY_CHEST_BOAT, (ItemLike)Items.CHERRY_BOAT);
/* 2916 */     oneToOneConversionRecipe($$0, (ItemLike)Items.PINK_DYE, (ItemLike)Items.PINK_PETALS, "pink_dye", 1);
/*      */     
/* 2918 */     ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, (ItemLike)Items.BRUSH)
/* 2919 */       .define(Character.valueOf('X'), (ItemLike)Items.FEATHER)
/* 2920 */       .define(Character.valueOf('#'), (ItemLike)Items.COPPER_INGOT)
/* 2921 */       .define(Character.valueOf('I'), (ItemLike)Items.STICK)
/* 2922 */       .pattern("X")
/* 2923 */       .pattern("#")
/* 2924 */       .pattern("I")
/* 2925 */       .unlockedBy("has_copper_ingot", has((ItemLike)Items.COPPER_INGOT))
/* 2926 */       .save($$0);
/*      */     
/* 2928 */     ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)Items.DECORATED_POT)
/* 2929 */       .define(Character.valueOf('#'), (ItemLike)Items.BRICK)
/* 2930 */       .pattern(" # ")
/* 2931 */       .pattern("# #")
/* 2932 */       .pattern(" # ")
/* 2933 */       .unlockedBy("has_brick", has(ItemTags.DECORATED_POT_INGREDIENTS))
/* 2934 */       .save($$0, "decorated_pot_simple");
/*      */     
/* 2936 */     SpecialRecipeBuilder.special(net.minecraft.world.item.crafting.DecoratedPotRecipe::new).save($$0, "decorated_pot");
/*      */   }
/*      */   public static final class TrimTemplate extends Record { private final Item template; private final ResourceLocation id;
/* 2939 */     public TrimTemplate(Item $$0, ResourceLocation $$1) { this.template = $$0; this.id = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/data/recipes/packs/VanillaRecipeProvider$TrimTemplate;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #2939	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/* 2939 */       //   0	7	0	this	Lnet/minecraft/data/recipes/packs/VanillaRecipeProvider$TrimTemplate; } public Item template() { return this.template; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/data/recipes/packs/VanillaRecipeProvider$TrimTemplate;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #2939	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/* 2939 */       //   0	7	0	this	Lnet/minecraft/data/recipes/packs/VanillaRecipeProvider$TrimTemplate; } public ResourceLocation id() { return this.id; } public final boolean equals(Object $$0) {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/data/recipes/packs/VanillaRecipeProvider$TrimTemplate;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #2939	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/data/recipes/packs/VanillaRecipeProvider$TrimTemplate;
/*      */       //   0	8	1	$$0	Ljava/lang/Object;
/*      */     } } public static Stream<TrimTemplate> smithingTrims() {
/* 2942 */     return Stream.<Item>of(new Item[] { Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2959 */         }).map($$0 -> new TrimTemplate($$0, new ResourceLocation(getItemName((ItemLike)$$0) + "_smithing_trim")));
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\packs\VanillaRecipeProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */