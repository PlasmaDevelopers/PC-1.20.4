/*      */ package net.minecraft.data.loot.packs;
/*      */ 
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Set;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.Stream;
/*      */ import net.minecraft.advancements.critereon.ItemPredicate;
/*      */ import net.minecraft.advancements.critereon.StatePropertiesPredicate;
/*      */ import net.minecraft.data.loot.BlockLootSubProvider;
/*      */ import net.minecraft.tags.ItemTags;
/*      */ import net.minecraft.world.flag.FeatureFlags;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.enchantment.Enchantments;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.block.BedBlock;
/*      */ import net.minecraft.world.level.block.BeetrootBlock;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.CarrotBlock;
/*      */ import net.minecraft.world.level.block.CocoaBlock;
/*      */ import net.minecraft.world.level.block.ComposterBlock;
/*      */ import net.minecraft.world.level.block.CropBlock;
/*      */ import net.minecraft.world.level.block.DecoratedPotBlock;
/*      */ import net.minecraft.world.level.block.DoublePlantBlock;
/*      */ import net.minecraft.world.level.block.MangrovePropaguleBlock;
/*      */ import net.minecraft.world.level.block.NetherWartBlock;
/*      */ import net.minecraft.world.level.block.PitcherCropBlock;
/*      */ import net.minecraft.world.level.block.PotatoBlock;
/*      */ import net.minecraft.world.level.block.SeaPickleBlock;
/*      */ import net.minecraft.world.level.block.SnowLayerBlock;
/*      */ import net.minecraft.world.level.block.SweetBerryBushBlock;
/*      */ import net.minecraft.world.level.block.TntBlock;
/*      */ import net.minecraft.world.level.block.state.properties.BedPart;
/*      */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.level.storage.loot.IntRange;
/*      */ import net.minecraft.world.level.storage.loot.LootContext;
/*      */ import net.minecraft.world.level.storage.loot.LootPool;
/*      */ import net.minecraft.world.level.storage.loot.LootTable;
/*      */ import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
/*      */ import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
/*      */ import net.minecraft.world.level.storage.loot.entries.LootItem;
/*      */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*      */ import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
/*      */ import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
/*      */ import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
/*      */ import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
/*      */ import net.minecraft.world.level.storage.loot.functions.LimitCount;
/*      */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*      */ import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
/*      */ import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
/*      */ import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
/*      */ import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
/*      */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*      */ import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
/*      */ import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
/*      */ import net.minecraft.world.level.storage.loot.predicates.MatchTool;
/*      */ import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
/*      */ import net.minecraft.world.level.storage.loot.providers.nbt.NbtProvider;
/*      */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*      */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*      */ import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
/*      */ 
/*      */ 
/*      */ public class VanillaBlockLoot
/*      */   extends BlockLootSubProvider
/*      */ {
/*   70 */   private static final float[] JUNGLE_LEAVES_SAPLING_CHANGES = new float[] { 0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F };
/*      */   
/*   72 */   private static final Set<Item> EXPLOSION_RESISTANT = (Set<Item>)Stream.<Block>of(new Block[] { Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.PIGLIN_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX
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
/*  103 */       }).map(ItemLike::asItem).collect(Collectors.toSet());
/*      */ 
/*      */   
/*      */   public VanillaBlockLoot() {
/*  107 */     super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generate() {
/*  113 */     dropSelf(Blocks.GRANITE);
/*  114 */     dropSelf(Blocks.POLISHED_GRANITE);
/*  115 */     dropSelf(Blocks.DIORITE);
/*  116 */     dropSelf(Blocks.POLISHED_DIORITE);
/*  117 */     dropSelf(Blocks.ANDESITE);
/*  118 */     dropSelf(Blocks.POLISHED_ANDESITE);
/*  119 */     dropSelf(Blocks.DIRT);
/*  120 */     dropSelf(Blocks.COARSE_DIRT);
/*  121 */     dropSelf(Blocks.COBBLESTONE);
/*  122 */     dropSelf(Blocks.OAK_PLANKS);
/*  123 */     dropSelf(Blocks.SPRUCE_PLANKS);
/*  124 */     dropSelf(Blocks.BIRCH_PLANKS);
/*  125 */     dropSelf(Blocks.JUNGLE_PLANKS);
/*  126 */     dropSelf(Blocks.ACACIA_PLANKS);
/*  127 */     dropSelf(Blocks.DARK_OAK_PLANKS);
/*  128 */     dropSelf(Blocks.MANGROVE_PLANKS);
/*  129 */     dropSelf(Blocks.CHERRY_PLANKS);
/*  130 */     dropSelf(Blocks.BAMBOO_PLANKS);
/*  131 */     dropSelf(Blocks.BAMBOO_MOSAIC);
/*  132 */     add(Blocks.DECORATED_POT, this::createDecoratedPotTable);
/*  133 */     dropSelf(Blocks.OAK_SAPLING);
/*  134 */     dropSelf(Blocks.SPRUCE_SAPLING);
/*  135 */     dropSelf(Blocks.BIRCH_SAPLING);
/*  136 */     dropSelf(Blocks.JUNGLE_SAPLING);
/*  137 */     dropSelf(Blocks.ACACIA_SAPLING);
/*  138 */     dropSelf(Blocks.DARK_OAK_SAPLING);
/*  139 */     dropSelf(Blocks.CHERRY_SAPLING);
/*  140 */     dropSelf(Blocks.SAND);
/*  141 */     add(Blocks.SUSPICIOUS_SAND, noDrop());
/*  142 */     add(Blocks.SUSPICIOUS_GRAVEL, noDrop());
/*  143 */     dropSelf(Blocks.RED_SAND);
/*  144 */     dropSelf(Blocks.OAK_LOG);
/*  145 */     dropSelf(Blocks.SPRUCE_LOG);
/*  146 */     dropSelf(Blocks.BIRCH_LOG);
/*  147 */     dropSelf(Blocks.JUNGLE_LOG);
/*  148 */     dropSelf(Blocks.ACACIA_LOG);
/*  149 */     dropSelf(Blocks.DARK_OAK_LOG);
/*  150 */     dropSelf(Blocks.CHERRY_LOG);
/*  151 */     dropSelf(Blocks.BAMBOO_BLOCK);
/*  152 */     dropSelf(Blocks.STRIPPED_OAK_LOG);
/*  153 */     dropSelf(Blocks.STRIPPED_SPRUCE_LOG);
/*  154 */     dropSelf(Blocks.STRIPPED_BIRCH_LOG);
/*  155 */     dropSelf(Blocks.STRIPPED_JUNGLE_LOG);
/*  156 */     dropSelf(Blocks.STRIPPED_ACACIA_LOG);
/*  157 */     dropSelf(Blocks.STRIPPED_DARK_OAK_LOG);
/*  158 */     dropSelf(Blocks.STRIPPED_MANGROVE_LOG);
/*  159 */     dropSelf(Blocks.STRIPPED_CHERRY_LOG);
/*  160 */     dropSelf(Blocks.STRIPPED_BAMBOO_BLOCK);
/*  161 */     dropSelf(Blocks.STRIPPED_WARPED_STEM);
/*  162 */     dropSelf(Blocks.STRIPPED_CRIMSON_STEM);
/*  163 */     dropSelf(Blocks.OAK_WOOD);
/*  164 */     dropSelf(Blocks.SPRUCE_WOOD);
/*  165 */     dropSelf(Blocks.BIRCH_WOOD);
/*  166 */     dropSelf(Blocks.JUNGLE_WOOD);
/*  167 */     dropSelf(Blocks.ACACIA_WOOD);
/*  168 */     dropSelf(Blocks.DARK_OAK_WOOD);
/*  169 */     dropSelf(Blocks.MANGROVE_WOOD);
/*  170 */     dropSelf(Blocks.CHERRY_WOOD);
/*  171 */     dropSelf(Blocks.STRIPPED_OAK_WOOD);
/*  172 */     dropSelf(Blocks.STRIPPED_SPRUCE_WOOD);
/*  173 */     dropSelf(Blocks.STRIPPED_BIRCH_WOOD);
/*  174 */     dropSelf(Blocks.STRIPPED_JUNGLE_WOOD);
/*  175 */     dropSelf(Blocks.STRIPPED_ACACIA_WOOD);
/*  176 */     dropSelf(Blocks.STRIPPED_DARK_OAK_WOOD);
/*  177 */     dropSelf(Blocks.STRIPPED_MANGROVE_WOOD);
/*  178 */     dropSelf(Blocks.STRIPPED_CHERRY_WOOD);
/*  179 */     dropSelf(Blocks.STRIPPED_CRIMSON_HYPHAE);
/*  180 */     dropSelf(Blocks.STRIPPED_WARPED_HYPHAE);
/*  181 */     dropSelf(Blocks.SPONGE);
/*  182 */     dropSelf(Blocks.WET_SPONGE);
/*  183 */     dropSelf(Blocks.LAPIS_BLOCK);
/*  184 */     dropSelf(Blocks.SANDSTONE);
/*  185 */     dropSelf(Blocks.CHISELED_SANDSTONE);
/*  186 */     dropSelf(Blocks.CUT_SANDSTONE);
/*  187 */     dropSelf(Blocks.NOTE_BLOCK);
/*  188 */     dropSelf(Blocks.POWERED_RAIL);
/*  189 */     dropSelf(Blocks.DETECTOR_RAIL);
/*  190 */     dropSelf(Blocks.STICKY_PISTON);
/*  191 */     dropSelf(Blocks.PISTON);
/*  192 */     dropSelf(Blocks.WHITE_WOOL);
/*  193 */     dropSelf(Blocks.ORANGE_WOOL);
/*  194 */     dropSelf(Blocks.MAGENTA_WOOL);
/*  195 */     dropSelf(Blocks.LIGHT_BLUE_WOOL);
/*  196 */     dropSelf(Blocks.YELLOW_WOOL);
/*  197 */     dropSelf(Blocks.LIME_WOOL);
/*  198 */     dropSelf(Blocks.PINK_WOOL);
/*  199 */     dropSelf(Blocks.GRAY_WOOL);
/*  200 */     dropSelf(Blocks.LIGHT_GRAY_WOOL);
/*  201 */     dropSelf(Blocks.CYAN_WOOL);
/*  202 */     dropSelf(Blocks.PURPLE_WOOL);
/*  203 */     dropSelf(Blocks.BLUE_WOOL);
/*  204 */     dropSelf(Blocks.BROWN_WOOL);
/*  205 */     dropSelf(Blocks.GREEN_WOOL);
/*  206 */     dropSelf(Blocks.RED_WOOL);
/*  207 */     dropSelf(Blocks.BLACK_WOOL);
/*  208 */     dropSelf(Blocks.DANDELION);
/*  209 */     dropSelf(Blocks.POPPY);
/*  210 */     dropSelf(Blocks.TORCHFLOWER);
/*  211 */     dropSelf(Blocks.BLUE_ORCHID);
/*  212 */     dropSelf(Blocks.ALLIUM);
/*  213 */     dropSelf(Blocks.AZURE_BLUET);
/*  214 */     dropSelf(Blocks.RED_TULIP);
/*  215 */     dropSelf(Blocks.ORANGE_TULIP);
/*  216 */     dropSelf(Blocks.WHITE_TULIP);
/*  217 */     dropSelf(Blocks.PINK_TULIP);
/*  218 */     dropSelf(Blocks.OXEYE_DAISY);
/*  219 */     dropSelf(Blocks.CORNFLOWER);
/*  220 */     dropSelf(Blocks.WITHER_ROSE);
/*  221 */     dropSelf(Blocks.LILY_OF_THE_VALLEY);
/*  222 */     dropSelf(Blocks.BROWN_MUSHROOM);
/*  223 */     dropSelf(Blocks.RED_MUSHROOM);
/*  224 */     dropSelf(Blocks.GOLD_BLOCK);
/*  225 */     dropSelf(Blocks.IRON_BLOCK);
/*  226 */     dropSelf(Blocks.BRICKS);
/*  227 */     dropSelf(Blocks.MOSSY_COBBLESTONE);
/*  228 */     dropSelf(Blocks.OBSIDIAN);
/*  229 */     dropSelf(Blocks.CRYING_OBSIDIAN);
/*  230 */     dropSelf(Blocks.TORCH);
/*  231 */     dropSelf(Blocks.OAK_STAIRS);
/*  232 */     dropSelf(Blocks.MANGROVE_STAIRS);
/*  233 */     dropSelf(Blocks.BAMBOO_STAIRS);
/*  234 */     dropSelf(Blocks.BAMBOO_MOSAIC_STAIRS);
/*  235 */     dropSelf(Blocks.REDSTONE_WIRE);
/*  236 */     dropSelf(Blocks.DIAMOND_BLOCK);
/*  237 */     dropSelf(Blocks.CRAFTING_TABLE);
/*  238 */     dropSelf(Blocks.OAK_SIGN);
/*  239 */     dropSelf(Blocks.SPRUCE_SIGN);
/*  240 */     dropSelf(Blocks.BIRCH_SIGN);
/*  241 */     dropSelf(Blocks.ACACIA_SIGN);
/*  242 */     dropSelf(Blocks.JUNGLE_SIGN);
/*  243 */     dropSelf(Blocks.DARK_OAK_SIGN);
/*  244 */     dropSelf(Blocks.MANGROVE_SIGN);
/*  245 */     dropSelf(Blocks.CHERRY_SIGN);
/*  246 */     dropSelf(Blocks.BAMBOO_SIGN);
/*  247 */     dropSelf(Blocks.OAK_HANGING_SIGN);
/*  248 */     dropSelf(Blocks.SPRUCE_HANGING_SIGN);
/*  249 */     dropSelf(Blocks.BIRCH_HANGING_SIGN);
/*  250 */     dropSelf(Blocks.ACACIA_HANGING_SIGN);
/*  251 */     dropSelf(Blocks.CHERRY_HANGING_SIGN);
/*  252 */     dropSelf(Blocks.JUNGLE_HANGING_SIGN);
/*  253 */     dropSelf(Blocks.DARK_OAK_HANGING_SIGN);
/*  254 */     dropSelf(Blocks.MANGROVE_HANGING_SIGN);
/*  255 */     dropSelf(Blocks.CRIMSON_HANGING_SIGN);
/*  256 */     dropSelf(Blocks.WARPED_HANGING_SIGN);
/*  257 */     dropSelf(Blocks.BAMBOO_HANGING_SIGN);
/*  258 */     dropSelf(Blocks.LADDER);
/*  259 */     dropSelf(Blocks.RAIL);
/*  260 */     dropSelf(Blocks.COBBLESTONE_STAIRS);
/*  261 */     dropSelf(Blocks.LEVER);
/*  262 */     dropSelf(Blocks.STONE_PRESSURE_PLATE);
/*  263 */     dropSelf(Blocks.OAK_PRESSURE_PLATE);
/*  264 */     dropSelf(Blocks.SPRUCE_PRESSURE_PLATE);
/*  265 */     dropSelf(Blocks.BIRCH_PRESSURE_PLATE);
/*  266 */     dropSelf(Blocks.JUNGLE_PRESSURE_PLATE);
/*  267 */     dropSelf(Blocks.ACACIA_PRESSURE_PLATE);
/*  268 */     dropSelf(Blocks.DARK_OAK_PRESSURE_PLATE);
/*  269 */     dropSelf(Blocks.MANGROVE_PRESSURE_PLATE);
/*  270 */     dropSelf(Blocks.CHERRY_PRESSURE_PLATE);
/*  271 */     dropSelf(Blocks.BAMBOO_PRESSURE_PLATE);
/*  272 */     dropSelf(Blocks.REDSTONE_TORCH);
/*  273 */     dropSelf(Blocks.STONE_BUTTON);
/*  274 */     dropSelf(Blocks.CACTUS);
/*  275 */     dropSelf(Blocks.SUGAR_CANE);
/*  276 */     dropSelf(Blocks.JUKEBOX);
/*  277 */     dropSelf(Blocks.OAK_FENCE);
/*  278 */     dropSelf(Blocks.MANGROVE_FENCE);
/*  279 */     dropSelf(Blocks.BAMBOO_FENCE);
/*  280 */     dropSelf(Blocks.PUMPKIN);
/*  281 */     dropSelf(Blocks.NETHERRACK);
/*  282 */     dropSelf(Blocks.SOUL_SAND);
/*  283 */     dropSelf(Blocks.SOUL_SOIL);
/*  284 */     dropSelf(Blocks.BASALT);
/*  285 */     dropSelf(Blocks.POLISHED_BASALT);
/*  286 */     dropSelf(Blocks.SMOOTH_BASALT);
/*  287 */     dropSelf(Blocks.SOUL_TORCH);
/*  288 */     dropSelf(Blocks.CARVED_PUMPKIN);
/*  289 */     dropSelf(Blocks.JACK_O_LANTERN);
/*  290 */     dropSelf(Blocks.REPEATER);
/*  291 */     dropSelf(Blocks.OAK_TRAPDOOR);
/*  292 */     dropSelf(Blocks.SPRUCE_TRAPDOOR);
/*  293 */     dropSelf(Blocks.BIRCH_TRAPDOOR);
/*  294 */     dropSelf(Blocks.JUNGLE_TRAPDOOR);
/*  295 */     dropSelf(Blocks.ACACIA_TRAPDOOR);
/*  296 */     dropSelf(Blocks.DARK_OAK_TRAPDOOR);
/*  297 */     dropSelf(Blocks.MANGROVE_TRAPDOOR);
/*  298 */     dropSelf(Blocks.CHERRY_TRAPDOOR);
/*  299 */     dropSelf(Blocks.BAMBOO_TRAPDOOR);
/*  300 */     add(Blocks.COPPER_TRAPDOOR, noDrop());
/*  301 */     add(Blocks.EXPOSED_COPPER_TRAPDOOR, noDrop());
/*  302 */     add(Blocks.WEATHERED_COPPER_TRAPDOOR, noDrop());
/*  303 */     add(Blocks.OXIDIZED_COPPER_TRAPDOOR, noDrop());
/*  304 */     add(Blocks.WAXED_COPPER_TRAPDOOR, noDrop());
/*  305 */     add(Blocks.WAXED_EXPOSED_COPPER_TRAPDOOR, noDrop());
/*  306 */     add(Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR, noDrop());
/*  307 */     add(Blocks.WAXED_OXIDIZED_COPPER_TRAPDOOR, noDrop());
/*  308 */     dropSelf(Blocks.STONE_BRICKS);
/*  309 */     dropSelf(Blocks.MOSSY_STONE_BRICKS);
/*  310 */     dropSelf(Blocks.CRACKED_STONE_BRICKS);
/*  311 */     dropSelf(Blocks.CHISELED_STONE_BRICKS);
/*  312 */     dropSelf(Blocks.IRON_BARS);
/*  313 */     dropSelf(Blocks.OAK_FENCE_GATE);
/*  314 */     dropSelf(Blocks.MANGROVE_FENCE_GATE);
/*  315 */     dropSelf(Blocks.BAMBOO_FENCE_GATE);
/*  316 */     dropSelf(Blocks.BRICK_STAIRS);
/*  317 */     dropSelf(Blocks.STONE_BRICK_STAIRS);
/*  318 */     dropSelf(Blocks.LILY_PAD);
/*  319 */     dropSelf(Blocks.NETHER_BRICKS);
/*  320 */     dropSelf(Blocks.NETHER_BRICK_FENCE);
/*  321 */     dropSelf(Blocks.NETHER_BRICK_STAIRS);
/*  322 */     dropSelf(Blocks.CAULDRON);
/*  323 */     dropSelf(Blocks.END_STONE);
/*  324 */     dropSelf(Blocks.REDSTONE_LAMP);
/*  325 */     dropSelf(Blocks.SANDSTONE_STAIRS);
/*  326 */     dropSelf(Blocks.TRIPWIRE_HOOK);
/*  327 */     dropSelf(Blocks.EMERALD_BLOCK);
/*  328 */     dropSelf(Blocks.SPRUCE_STAIRS);
/*  329 */     dropSelf(Blocks.BIRCH_STAIRS);
/*  330 */     dropSelf(Blocks.JUNGLE_STAIRS);
/*  331 */     dropSelf(Blocks.COBBLESTONE_WALL);
/*  332 */     dropSelf(Blocks.MOSSY_COBBLESTONE_WALL);
/*  333 */     dropSelf(Blocks.FLOWER_POT);
/*  334 */     dropSelf(Blocks.OAK_BUTTON);
/*  335 */     dropSelf(Blocks.SPRUCE_BUTTON);
/*  336 */     dropSelf(Blocks.BIRCH_BUTTON);
/*  337 */     dropSelf(Blocks.JUNGLE_BUTTON);
/*  338 */     dropSelf(Blocks.ACACIA_BUTTON);
/*  339 */     dropSelf(Blocks.DARK_OAK_BUTTON);
/*  340 */     dropSelf(Blocks.MANGROVE_BUTTON);
/*  341 */     dropSelf(Blocks.CHERRY_BUTTON);
/*  342 */     dropSelf(Blocks.BAMBOO_BUTTON);
/*  343 */     dropSelf(Blocks.SKELETON_SKULL);
/*  344 */     dropSelf(Blocks.WITHER_SKELETON_SKULL);
/*  345 */     dropSelf(Blocks.ZOMBIE_HEAD);
/*  346 */     dropSelf(Blocks.CREEPER_HEAD);
/*  347 */     dropSelf(Blocks.DRAGON_HEAD);
/*  348 */     dropSelf(Blocks.PIGLIN_HEAD);
/*  349 */     dropSelf(Blocks.ANVIL);
/*  350 */     dropSelf(Blocks.CHIPPED_ANVIL);
/*  351 */     dropSelf(Blocks.DAMAGED_ANVIL);
/*  352 */     dropSelf(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
/*  353 */     dropSelf(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
/*  354 */     dropSelf(Blocks.COMPARATOR);
/*  355 */     dropSelf(Blocks.DAYLIGHT_DETECTOR);
/*  356 */     dropSelf(Blocks.REDSTONE_BLOCK);
/*  357 */     dropSelf(Blocks.QUARTZ_BLOCK);
/*  358 */     dropSelf(Blocks.CHISELED_QUARTZ_BLOCK);
/*  359 */     dropSelf(Blocks.QUARTZ_PILLAR);
/*  360 */     dropSelf(Blocks.QUARTZ_STAIRS);
/*  361 */     dropSelf(Blocks.ACTIVATOR_RAIL);
/*  362 */     dropSelf(Blocks.WHITE_TERRACOTTA);
/*  363 */     dropSelf(Blocks.ORANGE_TERRACOTTA);
/*  364 */     dropSelf(Blocks.MAGENTA_TERRACOTTA);
/*  365 */     dropSelf(Blocks.LIGHT_BLUE_TERRACOTTA);
/*  366 */     dropSelf(Blocks.YELLOW_TERRACOTTA);
/*  367 */     dropSelf(Blocks.LIME_TERRACOTTA);
/*  368 */     dropSelf(Blocks.PINK_TERRACOTTA);
/*  369 */     dropSelf(Blocks.GRAY_TERRACOTTA);
/*  370 */     dropSelf(Blocks.LIGHT_GRAY_TERRACOTTA);
/*  371 */     dropSelf(Blocks.CYAN_TERRACOTTA);
/*  372 */     dropSelf(Blocks.PURPLE_TERRACOTTA);
/*  373 */     dropSelf(Blocks.BLUE_TERRACOTTA);
/*  374 */     dropSelf(Blocks.BROWN_TERRACOTTA);
/*  375 */     dropSelf(Blocks.GREEN_TERRACOTTA);
/*  376 */     dropSelf(Blocks.RED_TERRACOTTA);
/*  377 */     dropSelf(Blocks.BLACK_TERRACOTTA);
/*  378 */     dropSelf(Blocks.ACACIA_STAIRS);
/*  379 */     dropSelf(Blocks.DARK_OAK_STAIRS);
/*  380 */     dropSelf(Blocks.CHERRY_STAIRS);
/*  381 */     dropSelf(Blocks.SLIME_BLOCK);
/*  382 */     dropSelf(Blocks.IRON_TRAPDOOR);
/*  383 */     dropSelf(Blocks.PRISMARINE);
/*  384 */     dropSelf(Blocks.PRISMARINE_BRICKS);
/*  385 */     dropSelf(Blocks.DARK_PRISMARINE);
/*  386 */     dropSelf(Blocks.PRISMARINE_STAIRS);
/*  387 */     dropSelf(Blocks.PRISMARINE_BRICK_STAIRS);
/*  388 */     dropSelf(Blocks.DARK_PRISMARINE_STAIRS);
/*  389 */     dropSelf(Blocks.HAY_BLOCK);
/*  390 */     dropSelf(Blocks.WHITE_CARPET);
/*  391 */     dropSelf(Blocks.ORANGE_CARPET);
/*  392 */     dropSelf(Blocks.MAGENTA_CARPET);
/*  393 */     dropSelf(Blocks.LIGHT_BLUE_CARPET);
/*  394 */     dropSelf(Blocks.YELLOW_CARPET);
/*  395 */     dropSelf(Blocks.LIME_CARPET);
/*  396 */     dropSelf(Blocks.PINK_CARPET);
/*  397 */     dropSelf(Blocks.GRAY_CARPET);
/*  398 */     dropSelf(Blocks.LIGHT_GRAY_CARPET);
/*  399 */     dropSelf(Blocks.CYAN_CARPET);
/*  400 */     dropSelf(Blocks.PURPLE_CARPET);
/*  401 */     dropSelf(Blocks.BLUE_CARPET);
/*  402 */     dropSelf(Blocks.BROWN_CARPET);
/*  403 */     dropSelf(Blocks.GREEN_CARPET);
/*  404 */     dropSelf(Blocks.RED_CARPET);
/*  405 */     dropSelf(Blocks.BLACK_CARPET);
/*  406 */     dropSelf(Blocks.TERRACOTTA);
/*  407 */     dropSelf(Blocks.COAL_BLOCK);
/*  408 */     dropSelf(Blocks.RED_SANDSTONE);
/*  409 */     dropSelf(Blocks.CHISELED_RED_SANDSTONE);
/*  410 */     dropSelf(Blocks.CUT_RED_SANDSTONE);
/*  411 */     dropSelf(Blocks.RED_SANDSTONE_STAIRS);
/*  412 */     dropSelf(Blocks.SMOOTH_STONE);
/*  413 */     dropSelf(Blocks.SMOOTH_SANDSTONE);
/*  414 */     dropSelf(Blocks.SMOOTH_QUARTZ);
/*  415 */     dropSelf(Blocks.SMOOTH_RED_SANDSTONE);
/*  416 */     dropSelf(Blocks.SPRUCE_FENCE_GATE);
/*  417 */     dropSelf(Blocks.BIRCH_FENCE_GATE);
/*  418 */     dropSelf(Blocks.JUNGLE_FENCE_GATE);
/*  419 */     dropSelf(Blocks.ACACIA_FENCE_GATE);
/*  420 */     dropSelf(Blocks.DARK_OAK_FENCE_GATE);
/*  421 */     dropSelf(Blocks.CHERRY_FENCE_GATE);
/*  422 */     dropSelf(Blocks.SPRUCE_FENCE);
/*  423 */     dropSelf(Blocks.BIRCH_FENCE);
/*  424 */     dropSelf(Blocks.JUNGLE_FENCE);
/*  425 */     dropSelf(Blocks.ACACIA_FENCE);
/*  426 */     dropSelf(Blocks.DARK_OAK_FENCE);
/*  427 */     dropSelf(Blocks.CHERRY_FENCE);
/*  428 */     dropSelf(Blocks.END_ROD);
/*  429 */     dropSelf(Blocks.PURPUR_BLOCK);
/*  430 */     dropSelf(Blocks.PURPUR_PILLAR);
/*  431 */     dropSelf(Blocks.PURPUR_STAIRS);
/*  432 */     dropSelf(Blocks.END_STONE_BRICKS);
/*  433 */     dropSelf(Blocks.MAGMA_BLOCK);
/*  434 */     dropSelf(Blocks.NETHER_WART_BLOCK);
/*  435 */     dropSelf(Blocks.RED_NETHER_BRICKS);
/*  436 */     dropSelf(Blocks.BONE_BLOCK);
/*  437 */     dropSelf(Blocks.OBSERVER);
/*  438 */     dropSelf(Blocks.TARGET);
/*  439 */     dropSelf(Blocks.WHITE_GLAZED_TERRACOTTA);
/*  440 */     dropSelf(Blocks.ORANGE_GLAZED_TERRACOTTA);
/*  441 */     dropSelf(Blocks.MAGENTA_GLAZED_TERRACOTTA);
/*  442 */     dropSelf(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA);
/*  443 */     dropSelf(Blocks.YELLOW_GLAZED_TERRACOTTA);
/*  444 */     dropSelf(Blocks.LIME_GLAZED_TERRACOTTA);
/*  445 */     dropSelf(Blocks.PINK_GLAZED_TERRACOTTA);
/*  446 */     dropSelf(Blocks.GRAY_GLAZED_TERRACOTTA);
/*  447 */     dropSelf(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA);
/*  448 */     dropSelf(Blocks.CYAN_GLAZED_TERRACOTTA);
/*  449 */     dropSelf(Blocks.PURPLE_GLAZED_TERRACOTTA);
/*  450 */     dropSelf(Blocks.BLUE_GLAZED_TERRACOTTA);
/*  451 */     dropSelf(Blocks.BROWN_GLAZED_TERRACOTTA);
/*  452 */     dropSelf(Blocks.GREEN_GLAZED_TERRACOTTA);
/*  453 */     dropSelf(Blocks.RED_GLAZED_TERRACOTTA);
/*  454 */     dropSelf(Blocks.BLACK_GLAZED_TERRACOTTA);
/*  455 */     dropSelf(Blocks.WHITE_CONCRETE);
/*  456 */     dropSelf(Blocks.ORANGE_CONCRETE);
/*  457 */     dropSelf(Blocks.MAGENTA_CONCRETE);
/*  458 */     dropSelf(Blocks.LIGHT_BLUE_CONCRETE);
/*  459 */     dropSelf(Blocks.YELLOW_CONCRETE);
/*  460 */     dropSelf(Blocks.LIME_CONCRETE);
/*  461 */     dropSelf(Blocks.PINK_CONCRETE);
/*  462 */     dropSelf(Blocks.GRAY_CONCRETE);
/*  463 */     dropSelf(Blocks.LIGHT_GRAY_CONCRETE);
/*  464 */     dropSelf(Blocks.CYAN_CONCRETE);
/*  465 */     dropSelf(Blocks.PURPLE_CONCRETE);
/*  466 */     dropSelf(Blocks.BLUE_CONCRETE);
/*  467 */     dropSelf(Blocks.BROWN_CONCRETE);
/*  468 */     dropSelf(Blocks.GREEN_CONCRETE);
/*  469 */     dropSelf(Blocks.RED_CONCRETE);
/*  470 */     dropSelf(Blocks.BLACK_CONCRETE);
/*  471 */     dropSelf(Blocks.WHITE_CONCRETE_POWDER);
/*  472 */     dropSelf(Blocks.ORANGE_CONCRETE_POWDER);
/*  473 */     dropSelf(Blocks.MAGENTA_CONCRETE_POWDER);
/*  474 */     dropSelf(Blocks.LIGHT_BLUE_CONCRETE_POWDER);
/*  475 */     dropSelf(Blocks.YELLOW_CONCRETE_POWDER);
/*  476 */     dropSelf(Blocks.LIME_CONCRETE_POWDER);
/*  477 */     dropSelf(Blocks.PINK_CONCRETE_POWDER);
/*  478 */     dropSelf(Blocks.GRAY_CONCRETE_POWDER);
/*  479 */     dropSelf(Blocks.LIGHT_GRAY_CONCRETE_POWDER);
/*  480 */     dropSelf(Blocks.CYAN_CONCRETE_POWDER);
/*  481 */     dropSelf(Blocks.PURPLE_CONCRETE_POWDER);
/*  482 */     dropSelf(Blocks.BLUE_CONCRETE_POWDER);
/*  483 */     dropSelf(Blocks.BROWN_CONCRETE_POWDER);
/*  484 */     dropSelf(Blocks.GREEN_CONCRETE_POWDER);
/*  485 */     dropSelf(Blocks.RED_CONCRETE_POWDER);
/*  486 */     dropSelf(Blocks.BLACK_CONCRETE_POWDER);
/*  487 */     dropSelf(Blocks.KELP);
/*  488 */     dropSelf(Blocks.DRIED_KELP_BLOCK);
/*  489 */     dropSelf(Blocks.DEAD_TUBE_CORAL_BLOCK);
/*  490 */     dropSelf(Blocks.DEAD_BRAIN_CORAL_BLOCK);
/*  491 */     dropSelf(Blocks.DEAD_BUBBLE_CORAL_BLOCK);
/*  492 */     dropSelf(Blocks.DEAD_FIRE_CORAL_BLOCK);
/*  493 */     dropSelf(Blocks.DEAD_HORN_CORAL_BLOCK);
/*  494 */     dropSelf(Blocks.CONDUIT);
/*  495 */     dropSelf(Blocks.DRAGON_EGG);
/*  496 */     dropSelf(Blocks.BAMBOO);
/*  497 */     dropSelf(Blocks.POLISHED_GRANITE_STAIRS);
/*  498 */     dropSelf(Blocks.SMOOTH_RED_SANDSTONE_STAIRS);
/*  499 */     dropSelf(Blocks.MOSSY_STONE_BRICK_STAIRS);
/*  500 */     dropSelf(Blocks.POLISHED_DIORITE_STAIRS);
/*  501 */     dropSelf(Blocks.MOSSY_COBBLESTONE_STAIRS);
/*  502 */     dropSelf(Blocks.END_STONE_BRICK_STAIRS);
/*  503 */     dropSelf(Blocks.STONE_STAIRS);
/*  504 */     dropSelf(Blocks.SMOOTH_SANDSTONE_STAIRS);
/*  505 */     dropSelf(Blocks.SMOOTH_QUARTZ_STAIRS);
/*  506 */     dropSelf(Blocks.GRANITE_STAIRS);
/*  507 */     dropSelf(Blocks.ANDESITE_STAIRS);
/*  508 */     dropSelf(Blocks.RED_NETHER_BRICK_STAIRS);
/*  509 */     dropSelf(Blocks.POLISHED_ANDESITE_STAIRS);
/*  510 */     dropSelf(Blocks.DIORITE_STAIRS);
/*  511 */     dropSelf(Blocks.BRICK_WALL);
/*  512 */     dropSelf(Blocks.PRISMARINE_WALL);
/*  513 */     dropSelf(Blocks.RED_SANDSTONE_WALL);
/*  514 */     dropSelf(Blocks.MOSSY_STONE_BRICK_WALL);
/*  515 */     dropSelf(Blocks.GRANITE_WALL);
/*  516 */     dropSelf(Blocks.STONE_BRICK_WALL);
/*  517 */     dropSelf(Blocks.NETHER_BRICK_WALL);
/*  518 */     dropSelf(Blocks.ANDESITE_WALL);
/*  519 */     dropSelf(Blocks.RED_NETHER_BRICK_WALL);
/*  520 */     dropSelf(Blocks.SANDSTONE_WALL);
/*  521 */     dropSelf(Blocks.END_STONE_BRICK_WALL);
/*  522 */     dropSelf(Blocks.DIORITE_WALL);
/*  523 */     dropSelf(Blocks.MUD_BRICK_WALL);
/*  524 */     dropSelf(Blocks.LOOM);
/*  525 */     dropSelf(Blocks.SCAFFOLDING);
/*  526 */     dropSelf(Blocks.HONEY_BLOCK);
/*  527 */     dropSelf(Blocks.HONEYCOMB_BLOCK);
/*  528 */     dropSelf(Blocks.RESPAWN_ANCHOR);
/*  529 */     dropSelf(Blocks.LODESTONE);
/*  530 */     dropSelf(Blocks.WARPED_STEM);
/*  531 */     dropSelf(Blocks.WARPED_HYPHAE);
/*  532 */     dropSelf(Blocks.WARPED_FUNGUS);
/*  533 */     dropSelf(Blocks.WARPED_WART_BLOCK);
/*  534 */     dropSelf(Blocks.CRIMSON_STEM);
/*  535 */     dropSelf(Blocks.CRIMSON_HYPHAE);
/*  536 */     dropSelf(Blocks.CRIMSON_FUNGUS);
/*  537 */     dropSelf(Blocks.SHROOMLIGHT);
/*  538 */     dropSelf(Blocks.CRIMSON_PLANKS);
/*  539 */     dropSelf(Blocks.WARPED_PLANKS);
/*  540 */     dropSelf(Blocks.WARPED_PRESSURE_PLATE);
/*  541 */     dropSelf(Blocks.WARPED_FENCE);
/*  542 */     dropSelf(Blocks.WARPED_TRAPDOOR);
/*  543 */     dropSelf(Blocks.WARPED_FENCE_GATE);
/*  544 */     dropSelf(Blocks.WARPED_STAIRS);
/*  545 */     dropSelf(Blocks.WARPED_BUTTON);
/*  546 */     dropSelf(Blocks.WARPED_SIGN);
/*  547 */     dropSelf(Blocks.CRIMSON_PRESSURE_PLATE);
/*  548 */     dropSelf(Blocks.CRIMSON_FENCE);
/*  549 */     dropSelf(Blocks.CRIMSON_TRAPDOOR);
/*  550 */     dropSelf(Blocks.CRIMSON_FENCE_GATE);
/*  551 */     dropSelf(Blocks.CRIMSON_STAIRS);
/*  552 */     dropSelf(Blocks.CRIMSON_BUTTON);
/*  553 */     dropSelf(Blocks.CRIMSON_SIGN);
/*  554 */     dropSelf(Blocks.NETHERITE_BLOCK);
/*  555 */     dropSelf(Blocks.ANCIENT_DEBRIS);
/*  556 */     dropSelf(Blocks.BLACKSTONE);
/*  557 */     dropSelf(Blocks.POLISHED_BLACKSTONE_BRICKS);
/*  558 */     dropSelf(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS);
/*  559 */     dropSelf(Blocks.BLACKSTONE_STAIRS);
/*  560 */     dropSelf(Blocks.BLACKSTONE_WALL);
/*  561 */     dropSelf(Blocks.POLISHED_BLACKSTONE_BRICK_WALL);
/*  562 */     dropSelf(Blocks.CHISELED_POLISHED_BLACKSTONE);
/*  563 */     dropSelf(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
/*  564 */     dropSelf(Blocks.POLISHED_BLACKSTONE);
/*  565 */     dropSelf(Blocks.POLISHED_BLACKSTONE_STAIRS);
/*  566 */     dropSelf(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE);
/*  567 */     dropSelf(Blocks.POLISHED_BLACKSTONE_BUTTON);
/*  568 */     dropSelf(Blocks.POLISHED_BLACKSTONE_WALL);
/*  569 */     dropSelf(Blocks.CHISELED_NETHER_BRICKS);
/*  570 */     dropSelf(Blocks.CRACKED_NETHER_BRICKS);
/*  571 */     dropSelf(Blocks.QUARTZ_BRICKS);
/*  572 */     dropSelf(Blocks.CHAIN);
/*  573 */     dropSelf(Blocks.WARPED_ROOTS);
/*  574 */     dropSelf(Blocks.CRIMSON_ROOTS);
/*  575 */     dropSelf(Blocks.MUD_BRICKS);
/*  576 */     dropSelf(Blocks.MUDDY_MANGROVE_ROOTS);
/*  577 */     dropSelf(Blocks.MUD_BRICK_STAIRS);
/*  578 */     dropSelf(Blocks.AMETHYST_BLOCK);
/*  579 */     dropSelf(Blocks.CALCITE);
/*  580 */     dropSelf(Blocks.TUFF);
/*  581 */     dropSelf(Blocks.TINTED_GLASS);
/*  582 */     dropWhenSilkTouch(Blocks.SCULK_SENSOR);
/*  583 */     dropWhenSilkTouch(Blocks.CALIBRATED_SCULK_SENSOR);
/*  584 */     dropWhenSilkTouch(Blocks.SCULK);
/*  585 */     dropWhenSilkTouch(Blocks.SCULK_CATALYST);
/*  586 */     add(Blocks.SCULK_VEIN, $$0 -> createMultifaceBlockDrops($$0, HAS_SILK_TOUCH));
/*  587 */     dropWhenSilkTouch(Blocks.SCULK_SHRIEKER);
/*  588 */     dropWhenSilkTouch(Blocks.CHISELED_BOOKSHELF);
/*      */     
/*  590 */     dropSelf(Blocks.COPPER_BLOCK);
/*  591 */     dropSelf(Blocks.EXPOSED_COPPER);
/*  592 */     dropSelf(Blocks.WEATHERED_COPPER);
/*  593 */     dropSelf(Blocks.OXIDIZED_COPPER);
/*  594 */     dropSelf(Blocks.CUT_COPPER);
/*  595 */     dropSelf(Blocks.EXPOSED_CUT_COPPER);
/*  596 */     dropSelf(Blocks.WEATHERED_CUT_COPPER);
/*  597 */     dropSelf(Blocks.OXIDIZED_CUT_COPPER);
/*  598 */     dropSelf(Blocks.WAXED_COPPER_BLOCK);
/*  599 */     dropSelf(Blocks.WAXED_WEATHERED_COPPER);
/*  600 */     dropSelf(Blocks.WAXED_EXPOSED_COPPER);
/*  601 */     dropSelf(Blocks.WAXED_OXIDIZED_COPPER);
/*  602 */     dropSelf(Blocks.WAXED_CUT_COPPER);
/*  603 */     dropSelf(Blocks.WAXED_WEATHERED_CUT_COPPER);
/*  604 */     dropSelf(Blocks.WAXED_EXPOSED_CUT_COPPER);
/*  605 */     dropSelf(Blocks.WAXED_OXIDIZED_CUT_COPPER);
/*  606 */     dropSelf(Blocks.WAXED_CUT_COPPER_STAIRS);
/*  607 */     dropSelf(Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS);
/*  608 */     dropSelf(Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS);
/*  609 */     dropSelf(Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS);
/*  610 */     dropSelf(Blocks.CUT_COPPER_STAIRS);
/*  611 */     dropSelf(Blocks.EXPOSED_CUT_COPPER_STAIRS);
/*  612 */     dropSelf(Blocks.WEATHERED_CUT_COPPER_STAIRS);
/*  613 */     dropSelf(Blocks.OXIDIZED_CUT_COPPER_STAIRS);
/*  614 */     dropSelf(Blocks.LIGHTNING_ROD);
/*  615 */     dropSelf(Blocks.POINTED_DRIPSTONE);
/*  616 */     dropSelf(Blocks.DRIPSTONE_BLOCK);
/*  617 */     dropSelf(Blocks.SPORE_BLOSSOM);
/*  618 */     dropSelf(Blocks.FLOWERING_AZALEA);
/*  619 */     dropSelf(Blocks.AZALEA);
/*  620 */     dropSelf(Blocks.MOSS_CARPET);
/*  621 */     add(Blocks.PINK_PETALS, createPetalsDrops(Blocks.PINK_PETALS));
/*  622 */     dropSelf(Blocks.BIG_DRIPLEAF);
/*  623 */     dropSelf(Blocks.MOSS_BLOCK);
/*  624 */     dropSelf(Blocks.ROOTED_DIRT);
/*  625 */     dropSelf(Blocks.COBBLED_DEEPSLATE);
/*  626 */     dropSelf(Blocks.COBBLED_DEEPSLATE_STAIRS);
/*  627 */     dropSelf(Blocks.COBBLED_DEEPSLATE_WALL);
/*  628 */     dropSelf(Blocks.POLISHED_DEEPSLATE);
/*  629 */     dropSelf(Blocks.POLISHED_DEEPSLATE_STAIRS);
/*  630 */     dropSelf(Blocks.POLISHED_DEEPSLATE_WALL);
/*  631 */     dropSelf(Blocks.DEEPSLATE_TILES);
/*  632 */     dropSelf(Blocks.DEEPSLATE_TILE_STAIRS);
/*  633 */     dropSelf(Blocks.DEEPSLATE_TILE_WALL);
/*  634 */     dropSelf(Blocks.DEEPSLATE_BRICKS);
/*  635 */     dropSelf(Blocks.DEEPSLATE_BRICK_STAIRS);
/*  636 */     dropSelf(Blocks.DEEPSLATE_BRICK_WALL);
/*  637 */     dropSelf(Blocks.CHISELED_DEEPSLATE);
/*  638 */     dropSelf(Blocks.CRACKED_DEEPSLATE_BRICKS);
/*  639 */     dropSelf(Blocks.CRACKED_DEEPSLATE_TILES);
/*  640 */     dropSelf(Blocks.RAW_IRON_BLOCK);
/*  641 */     dropSelf(Blocks.RAW_COPPER_BLOCK);
/*  642 */     dropSelf(Blocks.RAW_GOLD_BLOCK);
/*  643 */     dropSelf(Blocks.OCHRE_FROGLIGHT);
/*  644 */     dropSelf(Blocks.VERDANT_FROGLIGHT);
/*  645 */     dropSelf(Blocks.PEARLESCENT_FROGLIGHT);
/*  646 */     dropSelf(Blocks.MANGROVE_ROOTS);
/*  647 */     dropSelf(Blocks.MANGROVE_LOG);
/*  648 */     dropSelf(Blocks.MUD);
/*  649 */     dropSelf(Blocks.PACKED_MUD);
/*  650 */     add(Blocks.CRAFTER, noDrop());
/*  651 */     add(Blocks.CHISELED_TUFF, noDrop());
/*  652 */     add(Blocks.TUFF_STAIRS, noDrop());
/*  653 */     add(Blocks.TUFF_WALL, noDrop());
/*  654 */     add(Blocks.POLISHED_TUFF, noDrop());
/*  655 */     add(Blocks.POLISHED_TUFF_STAIRS, noDrop());
/*  656 */     add(Blocks.POLISHED_TUFF_WALL, noDrop());
/*  657 */     add(Blocks.TUFF_BRICKS, noDrop());
/*  658 */     add(Blocks.TUFF_BRICK_STAIRS, noDrop());
/*  659 */     add(Blocks.TUFF_BRICK_WALL, noDrop());
/*  660 */     add(Blocks.CHISELED_TUFF_BRICKS, noDrop());
/*  661 */     add(Blocks.TUFF_SLAB, noDrop());
/*  662 */     add(Blocks.TUFF_BRICK_SLAB, noDrop());
/*  663 */     add(Blocks.POLISHED_TUFF_SLAB, noDrop());
/*  664 */     add(Blocks.CHISELED_COPPER, noDrop());
/*  665 */     add(Blocks.EXPOSED_CHISELED_COPPER, noDrop());
/*  666 */     add(Blocks.WEATHERED_CHISELED_COPPER, noDrop());
/*  667 */     add(Blocks.OXIDIZED_CHISELED_COPPER, noDrop());
/*  668 */     add(Blocks.WAXED_CHISELED_COPPER, noDrop());
/*  669 */     add(Blocks.WAXED_EXPOSED_CHISELED_COPPER, noDrop());
/*  670 */     add(Blocks.WAXED_WEATHERED_CHISELED_COPPER, noDrop());
/*  671 */     add(Blocks.WAXED_OXIDIZED_CHISELED_COPPER, noDrop());
/*  672 */     add(Blocks.COPPER_GRATE, noDrop());
/*  673 */     add(Blocks.EXPOSED_COPPER_GRATE, noDrop());
/*  674 */     add(Blocks.WEATHERED_COPPER_GRATE, noDrop());
/*  675 */     add(Blocks.OXIDIZED_COPPER_GRATE, noDrop());
/*  676 */     add(Blocks.WAXED_COPPER_GRATE, noDrop());
/*  677 */     add(Blocks.WAXED_EXPOSED_COPPER_GRATE, noDrop());
/*  678 */     add(Blocks.WAXED_WEATHERED_COPPER_GRATE, noDrop());
/*  679 */     add(Blocks.WAXED_OXIDIZED_COPPER_GRATE, noDrop());
/*  680 */     add(Blocks.COPPER_BULB, noDrop());
/*  681 */     add(Blocks.EXPOSED_COPPER_BULB, noDrop());
/*  682 */     add(Blocks.WEATHERED_COPPER_BULB, noDrop());
/*  683 */     add(Blocks.OXIDIZED_COPPER_BULB, noDrop());
/*  684 */     add(Blocks.WAXED_COPPER_BULB, noDrop());
/*  685 */     add(Blocks.WAXED_EXPOSED_COPPER_BULB, noDrop());
/*  686 */     add(Blocks.WAXED_WEATHERED_COPPER_BULB, noDrop());
/*  687 */     add(Blocks.WAXED_OXIDIZED_COPPER_BULB, noDrop());
/*      */ 
/*      */     
/*  690 */     dropOther(Blocks.FARMLAND, (ItemLike)Blocks.DIRT);
/*  691 */     dropOther(Blocks.TRIPWIRE, (ItemLike)Items.STRING);
/*  692 */     dropOther(Blocks.DIRT_PATH, (ItemLike)Blocks.DIRT);
/*  693 */     dropOther(Blocks.KELP_PLANT, (ItemLike)Blocks.KELP);
/*  694 */     dropOther(Blocks.BAMBOO_SAPLING, (ItemLike)Blocks.BAMBOO);
/*  695 */     dropOther(Blocks.WATER_CAULDRON, (ItemLike)Blocks.CAULDRON);
/*  696 */     dropOther(Blocks.LAVA_CAULDRON, (ItemLike)Blocks.CAULDRON);
/*  697 */     dropOther(Blocks.POWDER_SNOW_CAULDRON, (ItemLike)Blocks.CAULDRON);
/*  698 */     dropOther(Blocks.BIG_DRIPLEAF_STEM, (ItemLike)Blocks.BIG_DRIPLEAF);
/*      */ 
/*      */     
/*  701 */     add(Blocks.STONE, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Blocks.COBBLESTONE));
/*  702 */     add(Blocks.DEEPSLATE, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Blocks.COBBLED_DEEPSLATE));
/*  703 */     add(Blocks.GRASS_BLOCK, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Blocks.DIRT));
/*  704 */     add(Blocks.PODZOL, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Blocks.DIRT));
/*  705 */     add(Blocks.MYCELIUM, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Blocks.DIRT));
/*  706 */     add(Blocks.TUBE_CORAL_BLOCK, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Blocks.DEAD_TUBE_CORAL_BLOCK));
/*  707 */     add(Blocks.BRAIN_CORAL_BLOCK, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Blocks.DEAD_BRAIN_CORAL_BLOCK));
/*  708 */     add(Blocks.BUBBLE_CORAL_BLOCK, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Blocks.DEAD_BUBBLE_CORAL_BLOCK));
/*  709 */     add(Blocks.FIRE_CORAL_BLOCK, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Blocks.DEAD_FIRE_CORAL_BLOCK));
/*  710 */     add(Blocks.HORN_CORAL_BLOCK, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Blocks.DEAD_HORN_CORAL_BLOCK));
/*  711 */     add(Blocks.CRIMSON_NYLIUM, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Blocks.NETHERRACK));
/*  712 */     add(Blocks.WARPED_NYLIUM, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Blocks.NETHERRACK));
/*      */ 
/*      */     
/*  715 */     add(Blocks.BOOKSHELF, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Items.BOOK, (NumberProvider)ConstantValue.exactly(3.0F)));
/*  716 */     add(Blocks.CLAY, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Items.CLAY_BALL, (NumberProvider)ConstantValue.exactly(4.0F)));
/*  717 */     add(Blocks.ENDER_CHEST, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Blocks.OBSIDIAN, (NumberProvider)ConstantValue.exactly(8.0F)));
/*  718 */     add(Blocks.SNOW_BLOCK, $$0 -> createSingleItemTableWithSilkTouch($$0, (ItemLike)Items.SNOWBALL, (NumberProvider)ConstantValue.exactly(4.0F)));
/*      */     
/*  720 */     add(Blocks.CHORUS_PLANT, createSingleItemTable((ItemLike)Items.CHORUS_FRUIT, (NumberProvider)UniformGenerator.between(0.0F, 1.0F)));
/*      */ 
/*      */     
/*  723 */     dropPottedContents(Blocks.POTTED_OAK_SAPLING);
/*  724 */     dropPottedContents(Blocks.POTTED_SPRUCE_SAPLING);
/*  725 */     dropPottedContents(Blocks.POTTED_BIRCH_SAPLING);
/*  726 */     dropPottedContents(Blocks.POTTED_JUNGLE_SAPLING);
/*  727 */     dropPottedContents(Blocks.POTTED_ACACIA_SAPLING);
/*  728 */     dropPottedContents(Blocks.POTTED_DARK_OAK_SAPLING);
/*  729 */     dropPottedContents(Blocks.POTTED_MANGROVE_PROPAGULE);
/*  730 */     dropPottedContents(Blocks.POTTED_CHERRY_SAPLING);
/*  731 */     dropPottedContents(Blocks.POTTED_FERN);
/*  732 */     dropPottedContents(Blocks.POTTED_DANDELION);
/*  733 */     dropPottedContents(Blocks.POTTED_POPPY);
/*  734 */     dropPottedContents(Blocks.POTTED_BLUE_ORCHID);
/*  735 */     dropPottedContents(Blocks.POTTED_ALLIUM);
/*  736 */     dropPottedContents(Blocks.POTTED_AZURE_BLUET);
/*  737 */     dropPottedContents(Blocks.POTTED_RED_TULIP);
/*  738 */     dropPottedContents(Blocks.POTTED_ORANGE_TULIP);
/*  739 */     dropPottedContents(Blocks.POTTED_WHITE_TULIP);
/*  740 */     dropPottedContents(Blocks.POTTED_PINK_TULIP);
/*  741 */     dropPottedContents(Blocks.POTTED_OXEYE_DAISY);
/*  742 */     dropPottedContents(Blocks.POTTED_CORNFLOWER);
/*  743 */     dropPottedContents(Blocks.POTTED_LILY_OF_THE_VALLEY);
/*  744 */     dropPottedContents(Blocks.POTTED_WITHER_ROSE);
/*  745 */     dropPottedContents(Blocks.POTTED_RED_MUSHROOM);
/*  746 */     dropPottedContents(Blocks.POTTED_BROWN_MUSHROOM);
/*  747 */     dropPottedContents(Blocks.POTTED_DEAD_BUSH);
/*  748 */     dropPottedContents(Blocks.POTTED_CACTUS);
/*  749 */     dropPottedContents(Blocks.POTTED_BAMBOO);
/*  750 */     dropPottedContents(Blocks.POTTED_CRIMSON_FUNGUS);
/*  751 */     dropPottedContents(Blocks.POTTED_WARPED_FUNGUS);
/*  752 */     dropPottedContents(Blocks.POTTED_CRIMSON_ROOTS);
/*  753 */     dropPottedContents(Blocks.POTTED_WARPED_ROOTS);
/*  754 */     dropPottedContents(Blocks.POTTED_AZALEA);
/*  755 */     dropPottedContents(Blocks.POTTED_FLOWERING_AZALEA);
/*  756 */     dropPottedContents(Blocks.POTTED_TORCHFLOWER);
/*      */ 
/*      */     
/*  759 */     add(Blocks.OAK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  760 */     add(Blocks.PETRIFIED_OAK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  761 */     add(Blocks.SPRUCE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  762 */     add(Blocks.BIRCH_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  763 */     add(Blocks.JUNGLE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  764 */     add(Blocks.ACACIA_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  765 */     add(Blocks.DARK_OAK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  766 */     add(Blocks.MANGROVE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  767 */     add(Blocks.CHERRY_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  768 */     add(Blocks.BAMBOO_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  769 */     add(Blocks.BAMBOO_MOSAIC_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  770 */     add(Blocks.BRICK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  771 */     add(Blocks.COBBLESTONE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  772 */     add(Blocks.DARK_PRISMARINE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  773 */     add(Blocks.NETHER_BRICK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  774 */     add(Blocks.PRISMARINE_BRICK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  775 */     add(Blocks.PRISMARINE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  776 */     add(Blocks.PURPUR_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  777 */     add(Blocks.QUARTZ_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  778 */     add(Blocks.RED_SANDSTONE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  779 */     add(Blocks.SANDSTONE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  780 */     add(Blocks.CUT_RED_SANDSTONE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  781 */     add(Blocks.CUT_SANDSTONE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  782 */     add(Blocks.STONE_BRICK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  783 */     add(Blocks.STONE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  784 */     add(Blocks.SMOOTH_STONE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  785 */     add(Blocks.POLISHED_GRANITE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  786 */     add(Blocks.SMOOTH_RED_SANDSTONE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  787 */     add(Blocks.MOSSY_STONE_BRICK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  788 */     add(Blocks.POLISHED_DIORITE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  789 */     add(Blocks.MOSSY_COBBLESTONE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  790 */     add(Blocks.END_STONE_BRICK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  791 */     add(Blocks.SMOOTH_SANDSTONE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  792 */     add(Blocks.SMOOTH_QUARTZ_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  793 */     add(Blocks.GRANITE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  794 */     add(Blocks.ANDESITE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  795 */     add(Blocks.RED_NETHER_BRICK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  796 */     add(Blocks.POLISHED_ANDESITE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  797 */     add(Blocks.DIORITE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  798 */     add(Blocks.CRIMSON_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  799 */     add(Blocks.WARPED_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  800 */     add(Blocks.BLACKSTONE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  801 */     add(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  802 */     add(Blocks.POLISHED_BLACKSTONE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  803 */     add(Blocks.OXIDIZED_CUT_COPPER_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  804 */     add(Blocks.WEATHERED_CUT_COPPER_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  805 */     add(Blocks.EXPOSED_CUT_COPPER_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  806 */     add(Blocks.CUT_COPPER_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  807 */     add(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  808 */     add(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  809 */     add(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  810 */     add(Blocks.WAXED_CUT_COPPER_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  811 */     add(Blocks.COBBLED_DEEPSLATE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  812 */     add(Blocks.POLISHED_DEEPSLATE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  813 */     add(Blocks.DEEPSLATE_TILE_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  814 */     add(Blocks.DEEPSLATE_BRICK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*  815 */     add(Blocks.MUD_BRICK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/*      */ 
/*      */     
/*  818 */     add(Blocks.OAK_DOOR, $$1 -> $$0.createDoorTable($$1));
/*  819 */     add(Blocks.SPRUCE_DOOR, $$1 -> $$0.createDoorTable($$1));
/*  820 */     add(Blocks.BIRCH_DOOR, $$1 -> $$0.createDoorTable($$1));
/*  821 */     add(Blocks.JUNGLE_DOOR, $$1 -> $$0.createDoorTable($$1));
/*  822 */     add(Blocks.ACACIA_DOOR, $$1 -> $$0.createDoorTable($$1));
/*  823 */     add(Blocks.DARK_OAK_DOOR, $$1 -> $$0.createDoorTable($$1));
/*  824 */     add(Blocks.MANGROVE_DOOR, $$1 -> $$0.createDoorTable($$1));
/*  825 */     add(Blocks.CHERRY_DOOR, $$1 -> $$0.createDoorTable($$1));
/*  826 */     add(Blocks.BAMBOO_DOOR, $$1 -> $$0.createDoorTable($$1));
/*  827 */     add(Blocks.WARPED_DOOR, $$1 -> $$0.createDoorTable($$1));
/*  828 */     add(Blocks.CRIMSON_DOOR, $$1 -> $$0.createDoorTable($$1));
/*  829 */     add(Blocks.IRON_DOOR, $$1 -> $$0.createDoorTable($$1));
/*  830 */     add(Blocks.COPPER_DOOR, noDrop());
/*  831 */     add(Blocks.EXPOSED_COPPER_DOOR, noDrop());
/*  832 */     add(Blocks.WEATHERED_COPPER_DOOR, noDrop());
/*  833 */     add(Blocks.OXIDIZED_COPPER_DOOR, noDrop());
/*  834 */     add(Blocks.WAXED_COPPER_DOOR, noDrop());
/*  835 */     add(Blocks.WAXED_EXPOSED_COPPER_DOOR, noDrop());
/*  836 */     add(Blocks.WAXED_WEATHERED_COPPER_DOOR, noDrop());
/*  837 */     add(Blocks.WAXED_OXIDIZED_COPPER_DOOR, noDrop());
/*      */ 
/*      */     
/*  840 */     add(Blocks.BLACK_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  841 */     add(Blocks.BLUE_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  842 */     add(Blocks.BROWN_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  843 */     add(Blocks.CYAN_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  844 */     add(Blocks.GRAY_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  845 */     add(Blocks.GREEN_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  846 */     add(Blocks.LIGHT_BLUE_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  847 */     add(Blocks.LIGHT_GRAY_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  848 */     add(Blocks.LIME_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  849 */     add(Blocks.MAGENTA_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  850 */     add(Blocks.PURPLE_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  851 */     add(Blocks.ORANGE_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  852 */     add(Blocks.PINK_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  853 */     add(Blocks.RED_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  854 */     add(Blocks.WHITE_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*  855 */     add(Blocks.YELLOW_BED, $$0 -> createSinglePropConditionTable($$0, (Property)BedBlock.PART, (Comparable)BedPart.HEAD));
/*      */ 
/*      */     
/*  858 */     add(Blocks.LILAC, $$0 -> createSinglePropConditionTable($$0, (Property)DoublePlantBlock.HALF, (Comparable)DoubleBlockHalf.LOWER));
/*  859 */     add(Blocks.SUNFLOWER, $$0 -> createSinglePropConditionTable($$0, (Property)DoublePlantBlock.HALF, (Comparable)DoubleBlockHalf.LOWER));
/*  860 */     add(Blocks.PEONY, $$0 -> createSinglePropConditionTable($$0, (Property)DoublePlantBlock.HALF, (Comparable)DoubleBlockHalf.LOWER));
/*  861 */     add(Blocks.ROSE_BUSH, $$0 -> createSinglePropConditionTable($$0, (Property)DoublePlantBlock.HALF, (Comparable)DoubleBlockHalf.LOWER));
/*      */ 
/*      */     
/*  864 */     add(Blocks.TNT, LootTable.lootTable()
/*  865 */         .withPool((LootPool.Builder)applyExplosionCondition((ItemLike)Blocks.TNT, (ConditionUserBuilder)LootPool.lootPool()
/*  866 */             .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  867 */             .add(LootItem.lootTableItem((ItemLike)Blocks.TNT)
/*  868 */               .when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TNT).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)TntBlock.UNSTABLE, false)))))));
/*      */ 
/*      */ 
/*      */     
/*  872 */     add(Blocks.COCOA, $$0 -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls((NumberProvider)ConstantValue.exactly(1.0F)).add((LootPoolEntryContainer.Builder)applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder)LootItem.lootTableItem((ItemLike)Items.COCOA_BEANS).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(3.0F)).when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)CocoaBlock.AGE, 2))))))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  882 */     add(Blocks.SEA_PICKLE, $$0 -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls((NumberProvider)ConstantValue.exactly(1.0F)).add((LootPoolEntryContainer.Builder)applyExplosionDecay((ItemLike)Blocks.SEA_PICKLE, LootItem.lootTableItem((ItemLike)$$0).apply(List.of(Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)), ())))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  890 */     add(Blocks.COMPOSTER, $$0 -> LootTable.lootTable().withPool(LootPool.lootPool().add((LootPoolEntryContainer.Builder)applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder)LootItem.lootTableItem((ItemLike)Items.COMPOSTER)))).withPool(LootPool.lootPool().add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE_MEAL)).when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)ComposterBlock.LEVEL, 8)))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  899 */     add(Blocks.CAVE_VINES, $$0 -> BlockLootSubProvider.createCaveVinesDrop($$0));
/*  900 */     add(Blocks.CAVE_VINES_PLANT, $$0 -> BlockLootSubProvider.createCaveVinesDrop($$0));
/*      */     
/*  902 */     add(Blocks.CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  903 */     add(Blocks.WHITE_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  904 */     add(Blocks.ORANGE_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  905 */     add(Blocks.MAGENTA_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  906 */     add(Blocks.LIGHT_BLUE_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  907 */     add(Blocks.YELLOW_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  908 */     add(Blocks.LIME_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  909 */     add(Blocks.PINK_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  910 */     add(Blocks.GRAY_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  911 */     add(Blocks.LIGHT_GRAY_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  912 */     add(Blocks.CYAN_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  913 */     add(Blocks.PURPLE_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  914 */     add(Blocks.BLUE_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  915 */     add(Blocks.BROWN_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  916 */     add(Blocks.GREEN_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  917 */     add(Blocks.RED_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*  918 */     add(Blocks.BLACK_CANDLE, $$1 -> $$0.createCandleDrops($$1));
/*      */ 
/*      */     
/*  921 */     add(Blocks.BEACON, $$1 -> $$0.createNameableBlockEntityTable($$1));
/*  922 */     add(Blocks.BREWING_STAND, $$1 -> $$0.createNameableBlockEntityTable($$1));
/*  923 */     add(Blocks.CHEST, $$1 -> $$0.createNameableBlockEntityTable($$1));
/*  924 */     add(Blocks.DISPENSER, $$1 -> $$0.createNameableBlockEntityTable($$1));
/*  925 */     add(Blocks.DROPPER, $$1 -> $$0.createNameableBlockEntityTable($$1));
/*  926 */     add(Blocks.ENCHANTING_TABLE, $$1 -> $$0.createNameableBlockEntityTable($$1));
/*  927 */     add(Blocks.FURNACE, $$1 -> $$0.createNameableBlockEntityTable($$1));
/*  928 */     add(Blocks.HOPPER, $$1 -> $$0.createNameableBlockEntityTable($$1));
/*  929 */     add(Blocks.TRAPPED_CHEST, $$1 -> $$0.createNameableBlockEntityTable($$1));
/*  930 */     add(Blocks.SMOKER, $$1 -> $$0.createNameableBlockEntityTable($$1));
/*  931 */     add(Blocks.BLAST_FURNACE, $$1 -> $$0.createNameableBlockEntityTable($$1));
/*      */     
/*  933 */     add(Blocks.BARREL, $$1 -> $$0.createNameableBlockEntityTable($$1));
/*  934 */     dropSelf(Blocks.CARTOGRAPHY_TABLE);
/*  935 */     dropSelf(Blocks.FLETCHING_TABLE);
/*  936 */     dropSelf(Blocks.GRINDSTONE);
/*  937 */     dropSelf(Blocks.LECTERN);
/*  938 */     dropSelf(Blocks.SMITHING_TABLE);
/*  939 */     dropSelf(Blocks.STONECUTTER);
/*      */     
/*  941 */     add(Blocks.BELL, this::createSingleItemTable);
/*  942 */     add(Blocks.LANTERN, this::createSingleItemTable);
/*  943 */     add(Blocks.SOUL_LANTERN, this::createSingleItemTable);
/*      */ 
/*      */     
/*  946 */     add(Blocks.SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  947 */     add(Blocks.BLACK_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  948 */     add(Blocks.BLUE_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  949 */     add(Blocks.BROWN_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  950 */     add(Blocks.CYAN_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  951 */     add(Blocks.GRAY_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  952 */     add(Blocks.GREEN_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  953 */     add(Blocks.LIGHT_BLUE_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  954 */     add(Blocks.LIGHT_GRAY_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  955 */     add(Blocks.LIME_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  956 */     add(Blocks.MAGENTA_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  957 */     add(Blocks.ORANGE_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  958 */     add(Blocks.PINK_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  959 */     add(Blocks.PURPLE_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  960 */     add(Blocks.RED_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  961 */     add(Blocks.WHITE_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*  962 */     add(Blocks.YELLOW_SHULKER_BOX, $$1 -> $$0.createShulkerBoxDrop($$1));
/*      */ 
/*      */     
/*  965 */     add(Blocks.BLACK_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  966 */     add(Blocks.BLUE_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  967 */     add(Blocks.BROWN_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  968 */     add(Blocks.CYAN_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  969 */     add(Blocks.GRAY_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  970 */     add(Blocks.GREEN_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  971 */     add(Blocks.LIGHT_BLUE_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  972 */     add(Blocks.LIGHT_GRAY_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  973 */     add(Blocks.LIME_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  974 */     add(Blocks.MAGENTA_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  975 */     add(Blocks.ORANGE_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  976 */     add(Blocks.PINK_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  977 */     add(Blocks.PURPLE_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  978 */     add(Blocks.RED_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  979 */     add(Blocks.WHITE_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*  980 */     add(Blocks.YELLOW_BANNER, $$1 -> $$0.createBannerDrop($$1));
/*      */     
/*  982 */     add(Blocks.PLAYER_HEAD, $$0 -> LootTable.lootTable().withPool((LootPool.Builder)applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder)LootPool.lootPool().setRolls((NumberProvider)ConstantValue.exactly(1.0F)).add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$0).apply((LootItemFunction.Builder)CopyNbtFunction.copyData((NbtProvider)ContextNbtProvider.BLOCK_ENTITY).copy("SkullOwner", "SkullOwner").copy("note_block_sound", String.format(Locale.ROOT, "%s.%s", new Object[] { "BlockEntityTag", "note_block_sound" })))))));
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
/*  994 */     add(Blocks.BEE_NEST, $$0 -> createBeeNestDrop($$0));
/*  995 */     add(Blocks.BEEHIVE, $$0 -> createBeeHiveDrop($$0));
/*      */ 
/*      */     
/*  998 */     add(Blocks.OAK_LEAVES, $$0 -> createOakLeavesDrops($$0, Blocks.OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
/*  999 */     add(Blocks.SPRUCE_LEAVES, $$0 -> createLeavesDrops($$0, Blocks.SPRUCE_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
/* 1000 */     add(Blocks.BIRCH_LEAVES, $$0 -> createLeavesDrops($$0, Blocks.BIRCH_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
/* 1001 */     add(Blocks.JUNGLE_LEAVES, $$0 -> createLeavesDrops($$0, Blocks.JUNGLE_SAPLING, JUNGLE_LEAVES_SAPLING_CHANGES));
/* 1002 */     add(Blocks.ACACIA_LEAVES, $$0 -> createLeavesDrops($$0, Blocks.ACACIA_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
/* 1003 */     add(Blocks.DARK_OAK_LEAVES, $$0 -> createOakLeavesDrops($$0, Blocks.DARK_OAK_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
/* 1004 */     add(Blocks.CHERRY_LEAVES, $$0 -> createLeavesDrops($$0, Blocks.CHERRY_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));
/*      */     
/* 1006 */     add(Blocks.AZALEA_LEAVES, $$0 -> createLeavesDrops($$0, Blocks.AZALEA, NORMAL_LEAVES_SAPLING_CHANCES));
/* 1007 */     add(Blocks.FLOWERING_AZALEA_LEAVES, $$0 -> createLeavesDrops($$0, Blocks.FLOWERING_AZALEA, NORMAL_LEAVES_SAPLING_CHANCES));
/*      */ 
/*      */     
/* 1010 */     LootItemBlockStatePropertyCondition.Builder builder1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.BEETROOTS).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)BeetrootBlock.AGE, 3));
/* 1011 */     add(Blocks.BEETROOTS, createCropDrops(Blocks.BEETROOTS, Items.BEETROOT, Items.BEETROOT_SEEDS, (LootItemCondition.Builder)builder1));
/*      */     
/* 1013 */     LootItemBlockStatePropertyCondition.Builder builder2 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.WHEAT).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)CropBlock.AGE, 7));
/* 1014 */     add(Blocks.WHEAT, createCropDrops(Blocks.WHEAT, Items.WHEAT, Items.WHEAT_SEEDS, (LootItemCondition.Builder)builder2));
/*      */     
/* 1016 */     LootItemBlockStatePropertyCondition.Builder builder3 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.CARROTS).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)CarrotBlock.AGE, 7));
/*      */     
/* 1018 */     LootItemBlockStatePropertyCondition.Builder builder4 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.MANGROVE_PROPAGULE).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)MangrovePropaguleBlock.AGE, 4));
/* 1019 */     add(Blocks.MANGROVE_PROPAGULE, (LootTable.Builder)applyExplosionDecay((ItemLike)Blocks.MANGROVE_PROPAGULE, (FunctionUserBuilder)LootTable.lootTable()
/* 1020 */           .withPool(LootPool.lootPool()
/* 1021 */             .when((LootItemCondition.Builder)builder4)
/* 1022 */             .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MANGROVE_PROPAGULE)))));
/*      */ 
/*      */ 
/*      */     
/* 1026 */     add(Blocks.TORCHFLOWER_CROP, (LootTable.Builder)applyExplosionDecay((ItemLike)Blocks.TORCHFLOWER_CROP, (FunctionUserBuilder)LootTable.lootTable()
/* 1027 */           .withPool(LootPool.lootPool()
/* 1028 */             .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TORCHFLOWER_SEEDS)))));
/*      */ 
/*      */     
/* 1031 */     dropSelf(Blocks.SNIFFER_EGG);
/* 1032 */     add(Blocks.PITCHER_CROP, $$0 -> createPitcherCropLoot());
/*      */     
/* 1034 */     dropSelf(Blocks.PITCHER_PLANT);
/* 1035 */     add(Blocks.PITCHER_PLANT, (LootTable.Builder)applyExplosionDecay((ItemLike)Blocks.PITCHER_PLANT, (FunctionUserBuilder)LootTable.lootTable()
/* 1036 */           .withPool(LootPool.lootPool()
/* 1037 */             .add(LootItem.lootTableItem((ItemLike)Items.PITCHER_PLANT)
/*      */               
/* 1039 */               .when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.PITCHER_PLANT).setProperties(StatePropertiesPredicate.Builder.properties()
/* 1040 */                   .hasProperty((Property)DoublePlantBlock.HALF, (Comparable)DoubleBlockHalf.LOWER)))))));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1045 */     add(Blocks.CARROTS, (LootTable.Builder)applyExplosionDecay((ItemLike)Blocks.CARROTS, (FunctionUserBuilder)LootTable.lootTable()
/* 1046 */           .withPool(LootPool.lootPool()
/* 1047 */             .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CARROT)))
/*      */           
/* 1049 */           .withPool(LootPool.lootPool()
/* 1050 */             .when((LootItemCondition.Builder)builder3)
/* 1051 */             .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CARROT).apply((LootItemFunction.Builder)ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))))));
/*      */ 
/*      */ 
/*      */     
/* 1055 */     LootItemBlockStatePropertyCondition.Builder builder5 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.POTATOES).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)PotatoBlock.AGE, 7));
/* 1056 */     add(Blocks.POTATOES, (LootTable.Builder)applyExplosionDecay((ItemLike)Blocks.POTATOES, (FunctionUserBuilder)LootTable.lootTable()
/* 1057 */           .withPool(LootPool.lootPool()
/* 1058 */             .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTATO)))
/*      */           
/* 1060 */           .withPool(LootPool.lootPool()
/* 1061 */             .when((LootItemCondition.Builder)builder5)
/* 1062 */             .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTATO).apply((LootItemFunction.Builder)ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))))
/*      */           
/* 1064 */           .withPool(LootPool.lootPool()
/* 1065 */             .when((LootItemCondition.Builder)builder5)
/* 1066 */             .add(LootItem.lootTableItem((ItemLike)Items.POISONOUS_POTATO).when(LootItemRandomChanceCondition.randomChance(0.02F))))));
/*      */ 
/*      */ 
/*      */     
/* 1070 */     add(Blocks.SWEET_BERRY_BUSH, $$0 -> (LootTable.Builder)applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder)LootTable.lootTable().withPool(LootPool.lootPool().when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SWEET_BERRY_BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)SweetBerryBushBlock.AGE, 3))).add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SWEET_BERRIES)).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 3.0F))).apply((LootItemFunction.Builder)ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))).withPool(LootPool.lootPool().when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SWEET_BERRY_BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)SweetBerryBushBlock.AGE, 2))).add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SWEET_BERRIES)).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))).apply((LootItemFunction.Builder)ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))));
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
/* 1088 */     add(Blocks.BROWN_MUSHROOM_BLOCK, $$0 -> createMushroomBlockDrop($$0, (ItemLike)Blocks.BROWN_MUSHROOM));
/* 1089 */     add(Blocks.RED_MUSHROOM_BLOCK, $$0 -> createMushroomBlockDrop($$0, (ItemLike)Blocks.RED_MUSHROOM));
/*      */ 
/*      */     
/* 1092 */     add(Blocks.COAL_ORE, $$0 -> createOreDrop($$0, Items.COAL));
/* 1093 */     add(Blocks.DEEPSLATE_COAL_ORE, $$0 -> createOreDrop($$0, Items.COAL));
/* 1094 */     add(Blocks.EMERALD_ORE, $$0 -> createOreDrop($$0, Items.EMERALD));
/* 1095 */     add(Blocks.DEEPSLATE_EMERALD_ORE, $$0 -> createOreDrop($$0, Items.EMERALD));
/* 1096 */     add(Blocks.NETHER_QUARTZ_ORE, $$0 -> createOreDrop($$0, Items.QUARTZ));
/* 1097 */     add(Blocks.DIAMOND_ORE, $$0 -> createOreDrop($$0, Items.DIAMOND));
/* 1098 */     add(Blocks.DEEPSLATE_DIAMOND_ORE, $$0 -> createOreDrop($$0, Items.DIAMOND));
/* 1099 */     add(Blocks.COPPER_ORE, $$1 -> $$0.createCopperOreDrops($$1));
/* 1100 */     add(Blocks.DEEPSLATE_COPPER_ORE, $$1 -> $$0.createCopperOreDrops($$1));
/* 1101 */     add(Blocks.IRON_ORE, $$0 -> createOreDrop($$0, Items.RAW_IRON));
/* 1102 */     add(Blocks.DEEPSLATE_IRON_ORE, $$0 -> createOreDrop($$0, Items.RAW_IRON));
/* 1103 */     add(Blocks.GOLD_ORE, $$0 -> createOreDrop($$0, Items.RAW_GOLD));
/* 1104 */     add(Blocks.DEEPSLATE_GOLD_ORE, $$0 -> createOreDrop($$0, Items.RAW_GOLD));
/*      */     
/* 1106 */     add(Blocks.NETHER_GOLD_ORE, $$0 -> createSilkTouchDispatchTable($$0, (LootPoolEntryContainer.Builder)applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 6.0F))).apply((LootItemFunction.Builder)ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1113 */     add(Blocks.LAPIS_ORE, $$1 -> $$0.createLapisOreDrops($$1));
/* 1114 */     add(Blocks.DEEPSLATE_LAPIS_ORE, $$1 -> $$0.createLapisOreDrops($$1));
/*      */ 
/*      */     
/* 1117 */     add(Blocks.COBWEB, $$0 -> createSilkTouchOrShearsDispatchTable($$0, (LootPoolEntryContainer.Builder)applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder)LootItem.lootTableItem((ItemLike)Items.STRING))));
/*      */ 
/*      */ 
/*      */     
/* 1121 */     add(Blocks.DEAD_BUSH, $$0 -> createShearsDispatchTable($$0, (LootPoolEntryContainer.Builder)applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder)LootItem.lootTableItem((ItemLike)Items.STICK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))))));
/*      */ 
/*      */ 
/*      */     
/* 1125 */     add(Blocks.NETHER_SPROUTS, $$0 -> BlockLootSubProvider.createShearsOnlyDrop($$0));
/* 1126 */     add(Blocks.SEAGRASS, $$0 -> BlockLootSubProvider.createShearsOnlyDrop($$0));
/* 1127 */     add(Blocks.VINE, $$0 -> BlockLootSubProvider.createShearsOnlyDrop($$0));
/* 1128 */     add(Blocks.GLOW_LICHEN, $$0 -> createMultifaceBlockDrops($$0, HAS_SHEARS));
/* 1129 */     add(Blocks.HANGING_ROOTS, $$0 -> BlockLootSubProvider.createShearsOnlyDrop($$0));
/* 1130 */     add(Blocks.SMALL_DRIPLEAF, $$0 -> BlockLootSubProvider.createShearsOnlyDrop($$0));
/*      */ 
/*      */     
/* 1133 */     add(Blocks.MANGROVE_LEAVES, $$1 -> $$0.createMangroveLeavesDrops($$1));
/*      */     
/* 1135 */     add(Blocks.TALL_SEAGRASS, createDoublePlantShearsDrop(Blocks.SEAGRASS));
/* 1136 */     add(Blocks.LARGE_FERN, $$0 -> createDoublePlantWithSeedDrops($$0, Blocks.FERN));
/* 1137 */     add(Blocks.TALL_GRASS, $$0 -> createDoublePlantWithSeedDrops($$0, Blocks.SHORT_GRASS));
/*      */ 
/*      */     
/* 1140 */     add(Blocks.MELON_STEM, $$0 -> createStemDrops($$0, Items.MELON_SEEDS));
/* 1141 */     add(Blocks.ATTACHED_MELON_STEM, $$0 -> createAttachedStemDrops($$0, Items.MELON_SEEDS));
/* 1142 */     add(Blocks.PUMPKIN_STEM, $$0 -> createStemDrops($$0, Items.PUMPKIN_SEEDS));
/* 1143 */     add(Blocks.ATTACHED_PUMPKIN_STEM, $$0 -> createAttachedStemDrops($$0, Items.PUMPKIN_SEEDS));
/*      */ 
/*      */     
/* 1146 */     add(Blocks.CHORUS_FLOWER, $$0 -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls((NumberProvider)ConstantValue.exactly(1.0F)).add(((LootPoolSingletonContainer.Builder)applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder)LootItem.lootTableItem((ItemLike)$$0))).when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS)))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1153 */     add(Blocks.FERN, $$1 -> $$0.createGrassDrops($$1));
/* 1154 */     add(Blocks.SHORT_GRASS, $$1 -> $$0.createGrassDrops($$1));
/*      */     
/* 1156 */     add(Blocks.GLOWSTONE, $$0 -> createSilkTouchDispatchTable($$0, (LootPoolEntryContainer.Builder)applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder)LootItem.lootTableItem((ItemLike)Items.GLOWSTONE_DUST).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))).apply((LootItemFunction.Builder)ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)).apply((LootItemFunction.Builder)LimitCount.limitCount(IntRange.range(1, 4))))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1164 */     add(Blocks.MELON, $$0 -> createSilkTouchDispatchTable($$0, (LootPoolEntryContainer.Builder)applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder)LootItem.lootTableItem((ItemLike)Items.MELON_SLICE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 7.0F))).apply((LootItemFunction.Builder)ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)).apply((LootItemFunction.Builder)LimitCount.limitCount(IntRange.upperBound(9))))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1172 */     add(Blocks.REDSTONE_ORE, $$1 -> $$0.createRedstoneOreDrops($$1));
/* 1173 */     add(Blocks.DEEPSLATE_REDSTONE_ORE, $$1 -> $$0.createRedstoneOreDrops($$1));
/*      */     
/* 1175 */     add(Blocks.SEA_LANTERN, $$0 -> createSilkTouchDispatchTable($$0, (LootPoolEntryContainer.Builder)applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder)LootItem.lootTableItem((ItemLike)Items.PRISMARINE_CRYSTALS).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 3.0F))).apply((LootItemFunction.Builder)ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)).apply((LootItemFunction.Builder)LimitCount.limitCount(IntRange.range(1, 5))))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1183 */     add(Blocks.NETHER_WART, $$0 -> LootTable.lootTable().withPool((LootPool.Builder)applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder)LootPool.lootPool().setRolls((NumberProvider)ConstantValue.exactly(1.0F)).add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NETHER_WART).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F)).when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)NetherWartBlock.AGE, 3)))).apply((LootItemFunction.Builder)ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE).when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)NetherWartBlock.AGE, 3))))))));
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
/* 1196 */     add(Blocks.SNOW, $$0 -> LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS)).add((LootPoolEntryContainer.Builder)AlternativesEntry.alternatives(new LootPoolEntryContainer.Builder[] { AlternativesEntry.alternatives(SnowLayerBlock.LAYERS.getPossibleValues(), ()).when(HAS_NO_SILK_TOUCH), (LootPoolEntryContainer.Builder)AlternativesEntry.alternatives(SnowLayerBlock.LAYERS.getPossibleValues(), ()) }))));
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
/* 1213 */     add(Blocks.GRAVEL, $$0 -> createSilkTouchDispatchTable($$0, (LootPoolEntryContainer.Builder)applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder)((LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FLINT).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, new float[] { 0.1F, 0.14285715F, 0.25F, 1.0F }))).otherwise((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$0)))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1220 */     add(Blocks.CAMPFIRE, $$0 -> createSilkTouchDispatchTable($$0, (LootPoolEntryContainer.Builder)applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder)LootItem.lootTableItem((ItemLike)Items.CHARCOAL).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F))))));
/*      */ 
/*      */ 
/*      */     
/* 1224 */     add(Blocks.GILDED_BLACKSTONE, $$0 -> createSilkTouchDispatchTable($$0, (LootPoolEntryContainer.Builder)applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder)((LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, new float[] { 0.1F, 0.14285715F, 0.25F, 1.0F }))).otherwise((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$0)))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1231 */     add(Blocks.SOUL_CAMPFIRE, $$0 -> createSilkTouchDispatchTable($$0, (LootPoolEntryContainer.Builder)applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder)LootItem.lootTableItem((ItemLike)Items.SOUL_SOIL).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))));
/*      */ 
/*      */ 
/*      */     
/* 1235 */     add(Blocks.AMETHYST_CLUSTER, $$0 -> createSilkTouchDispatchTable($$0, (LootPoolEntryContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)Items.AMETHYST_SHARD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(4.0F))).apply((LootItemFunction.Builder)ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)).when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(ItemTags.CLUSTER_MAX_HARVESTABLES)))).otherwise((LootPoolEntryContainer.Builder)applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder)LootItem.lootTableItem((ItemLike)Items.AMETHYST_SHARD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F)))))));
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
/* 1246 */     dropWhenSilkTouch(Blocks.SMALL_AMETHYST_BUD);
/* 1247 */     dropWhenSilkTouch(Blocks.MEDIUM_AMETHYST_BUD);
/* 1248 */     dropWhenSilkTouch(Blocks.LARGE_AMETHYST_BUD);
/*      */     
/* 1250 */     dropWhenSilkTouch(Blocks.GLASS);
/* 1251 */     dropWhenSilkTouch(Blocks.WHITE_STAINED_GLASS);
/* 1252 */     dropWhenSilkTouch(Blocks.ORANGE_STAINED_GLASS);
/* 1253 */     dropWhenSilkTouch(Blocks.MAGENTA_STAINED_GLASS);
/* 1254 */     dropWhenSilkTouch(Blocks.LIGHT_BLUE_STAINED_GLASS);
/* 1255 */     dropWhenSilkTouch(Blocks.YELLOW_STAINED_GLASS);
/* 1256 */     dropWhenSilkTouch(Blocks.LIME_STAINED_GLASS);
/* 1257 */     dropWhenSilkTouch(Blocks.PINK_STAINED_GLASS);
/* 1258 */     dropWhenSilkTouch(Blocks.GRAY_STAINED_GLASS);
/* 1259 */     dropWhenSilkTouch(Blocks.LIGHT_GRAY_STAINED_GLASS);
/* 1260 */     dropWhenSilkTouch(Blocks.CYAN_STAINED_GLASS);
/* 1261 */     dropWhenSilkTouch(Blocks.PURPLE_STAINED_GLASS);
/* 1262 */     dropWhenSilkTouch(Blocks.BLUE_STAINED_GLASS);
/* 1263 */     dropWhenSilkTouch(Blocks.BROWN_STAINED_GLASS);
/* 1264 */     dropWhenSilkTouch(Blocks.GREEN_STAINED_GLASS);
/* 1265 */     dropWhenSilkTouch(Blocks.RED_STAINED_GLASS);
/* 1266 */     dropWhenSilkTouch(Blocks.BLACK_STAINED_GLASS);
/*      */     
/* 1268 */     dropWhenSilkTouch(Blocks.GLASS_PANE);
/* 1269 */     dropWhenSilkTouch(Blocks.WHITE_STAINED_GLASS_PANE);
/* 1270 */     dropWhenSilkTouch(Blocks.ORANGE_STAINED_GLASS_PANE);
/* 1271 */     dropWhenSilkTouch(Blocks.MAGENTA_STAINED_GLASS_PANE);
/* 1272 */     dropWhenSilkTouch(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE);
/* 1273 */     dropWhenSilkTouch(Blocks.YELLOW_STAINED_GLASS_PANE);
/* 1274 */     dropWhenSilkTouch(Blocks.LIME_STAINED_GLASS_PANE);
/* 1275 */     dropWhenSilkTouch(Blocks.PINK_STAINED_GLASS_PANE);
/* 1276 */     dropWhenSilkTouch(Blocks.GRAY_STAINED_GLASS_PANE);
/* 1277 */     dropWhenSilkTouch(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE);
/* 1278 */     dropWhenSilkTouch(Blocks.CYAN_STAINED_GLASS_PANE);
/* 1279 */     dropWhenSilkTouch(Blocks.PURPLE_STAINED_GLASS_PANE);
/* 1280 */     dropWhenSilkTouch(Blocks.BLUE_STAINED_GLASS_PANE);
/* 1281 */     dropWhenSilkTouch(Blocks.BROWN_STAINED_GLASS_PANE);
/* 1282 */     dropWhenSilkTouch(Blocks.GREEN_STAINED_GLASS_PANE);
/* 1283 */     dropWhenSilkTouch(Blocks.RED_STAINED_GLASS_PANE);
/* 1284 */     dropWhenSilkTouch(Blocks.BLACK_STAINED_GLASS_PANE);
/*      */     
/* 1286 */     dropWhenSilkTouch(Blocks.ICE);
/* 1287 */     dropWhenSilkTouch(Blocks.PACKED_ICE);
/* 1288 */     dropWhenSilkTouch(Blocks.BLUE_ICE);
/*      */     
/* 1290 */     dropWhenSilkTouch(Blocks.TURTLE_EGG);
/*      */     
/* 1292 */     dropWhenSilkTouch(Blocks.MUSHROOM_STEM);
/*      */     
/* 1294 */     dropWhenSilkTouch(Blocks.DEAD_TUBE_CORAL);
/* 1295 */     dropWhenSilkTouch(Blocks.DEAD_BRAIN_CORAL);
/* 1296 */     dropWhenSilkTouch(Blocks.DEAD_BUBBLE_CORAL);
/* 1297 */     dropWhenSilkTouch(Blocks.DEAD_FIRE_CORAL);
/* 1298 */     dropWhenSilkTouch(Blocks.DEAD_HORN_CORAL);
/*      */     
/* 1300 */     dropWhenSilkTouch(Blocks.TUBE_CORAL);
/* 1301 */     dropWhenSilkTouch(Blocks.BRAIN_CORAL);
/* 1302 */     dropWhenSilkTouch(Blocks.BUBBLE_CORAL);
/* 1303 */     dropWhenSilkTouch(Blocks.FIRE_CORAL);
/* 1304 */     dropWhenSilkTouch(Blocks.HORN_CORAL);
/*      */     
/* 1306 */     dropWhenSilkTouch(Blocks.DEAD_TUBE_CORAL_FAN);
/* 1307 */     dropWhenSilkTouch(Blocks.DEAD_BRAIN_CORAL_FAN);
/* 1308 */     dropWhenSilkTouch(Blocks.DEAD_BUBBLE_CORAL_FAN);
/* 1309 */     dropWhenSilkTouch(Blocks.DEAD_FIRE_CORAL_FAN);
/* 1310 */     dropWhenSilkTouch(Blocks.DEAD_HORN_CORAL_FAN);
/*      */     
/* 1312 */     dropWhenSilkTouch(Blocks.TUBE_CORAL_FAN);
/* 1313 */     dropWhenSilkTouch(Blocks.BRAIN_CORAL_FAN);
/* 1314 */     dropWhenSilkTouch(Blocks.BUBBLE_CORAL_FAN);
/* 1315 */     dropWhenSilkTouch(Blocks.FIRE_CORAL_FAN);
/* 1316 */     dropWhenSilkTouch(Blocks.HORN_CORAL_FAN);
/*      */     
/* 1318 */     otherWhenSilkTouch(Blocks.INFESTED_STONE, Blocks.STONE);
/* 1319 */     otherWhenSilkTouch(Blocks.INFESTED_COBBLESTONE, Blocks.COBBLESTONE);
/* 1320 */     otherWhenSilkTouch(Blocks.INFESTED_STONE_BRICKS, Blocks.STONE_BRICKS);
/* 1321 */     otherWhenSilkTouch(Blocks.INFESTED_MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS);
/* 1322 */     otherWhenSilkTouch(Blocks.INFESTED_CRACKED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
/* 1323 */     otherWhenSilkTouch(Blocks.INFESTED_CHISELED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS);
/* 1324 */     otherWhenSilkTouch(Blocks.INFESTED_DEEPSLATE, Blocks.DEEPSLATE);
/*      */     
/* 1326 */     addNetherVinesDropTable(Blocks.WEEPING_VINES, Blocks.WEEPING_VINES_PLANT);
/* 1327 */     addNetherVinesDropTable(Blocks.TWISTING_VINES, Blocks.TWISTING_VINES_PLANT);
/*      */ 
/*      */     
/* 1330 */     add(Blocks.CAKE, noDrop());
/* 1331 */     add(Blocks.CANDLE_CAKE, createCandleCakeDrops(Blocks.CANDLE));
/* 1332 */     add(Blocks.WHITE_CANDLE_CAKE, createCandleCakeDrops(Blocks.WHITE_CANDLE));
/* 1333 */     add(Blocks.ORANGE_CANDLE_CAKE, createCandleCakeDrops(Blocks.ORANGE_CANDLE));
/* 1334 */     add(Blocks.MAGENTA_CANDLE_CAKE, createCandleCakeDrops(Blocks.MAGENTA_CANDLE));
/* 1335 */     add(Blocks.LIGHT_BLUE_CANDLE_CAKE, createCandleCakeDrops(Blocks.LIGHT_BLUE_CANDLE));
/* 1336 */     add(Blocks.YELLOW_CANDLE_CAKE, createCandleCakeDrops(Blocks.YELLOW_CANDLE));
/* 1337 */     add(Blocks.LIME_CANDLE_CAKE, createCandleCakeDrops(Blocks.LIME_CANDLE));
/* 1338 */     add(Blocks.PINK_CANDLE_CAKE, createCandleCakeDrops(Blocks.PINK_CANDLE));
/* 1339 */     add(Blocks.GRAY_CANDLE_CAKE, createCandleCakeDrops(Blocks.GRAY_CANDLE));
/* 1340 */     add(Blocks.LIGHT_GRAY_CANDLE_CAKE, createCandleCakeDrops(Blocks.LIGHT_GRAY_CANDLE));
/* 1341 */     add(Blocks.CYAN_CANDLE_CAKE, createCandleCakeDrops(Blocks.CYAN_CANDLE));
/* 1342 */     add(Blocks.PURPLE_CANDLE_CAKE, createCandleCakeDrops(Blocks.PURPLE_CANDLE));
/* 1343 */     add(Blocks.BLUE_CANDLE_CAKE, createCandleCakeDrops(Blocks.BLUE_CANDLE));
/* 1344 */     add(Blocks.BROWN_CANDLE_CAKE, createCandleCakeDrops(Blocks.BROWN_CANDLE));
/* 1345 */     add(Blocks.GREEN_CANDLE_CAKE, createCandleCakeDrops(Blocks.GREEN_CANDLE));
/* 1346 */     add(Blocks.RED_CANDLE_CAKE, createCandleCakeDrops(Blocks.RED_CANDLE));
/* 1347 */     add(Blocks.BLACK_CANDLE_CAKE, createCandleCakeDrops(Blocks.BLACK_CANDLE));
/* 1348 */     add(Blocks.FROSTED_ICE, noDrop());
/* 1349 */     add(Blocks.SPAWNER, noDrop());
/* 1350 */     add(Blocks.TRIAL_SPAWNER, noDrop());
/* 1351 */     add(Blocks.FIRE, noDrop());
/* 1352 */     add(Blocks.SOUL_FIRE, noDrop());
/* 1353 */     add(Blocks.NETHER_PORTAL, noDrop());
/* 1354 */     add(Blocks.BUDDING_AMETHYST, noDrop());
/* 1355 */     add(Blocks.POWDER_SNOW, noDrop());
/* 1356 */     add(Blocks.FROGSPAWN, noDrop());
/* 1357 */     add(Blocks.REINFORCED_DEEPSLATE, noDrop());
/*      */     
/* 1359 */     add(Blocks.SUSPICIOUS_SAND, noDrop());
/* 1360 */     add(Blocks.SUSPICIOUS_GRAVEL, noDrop());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LootTable.Builder createDecoratedPotTable(Block $$0) {
/* 1368 */     return LootTable.lootTable()
/* 1369 */       .withPool(LootPool.lootPool()
/* 1370 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 1371 */         .add((LootPoolEntryContainer.Builder)((LootPoolSingletonContainer.Builder)DynamicLoot.dynamicEntry(DecoratedPotBlock.SHERDS_DYNAMIC_DROP_ID)
/* 1372 */           .when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)DecoratedPotBlock.CRACKED, true))))
/* 1373 */           .otherwise((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$0)
/* 1374 */             .apply((LootItemFunction.Builder)CopyNbtFunction.copyData((NbtProvider)ContextNbtProvider.BLOCK_ENTITY).copy("sherds", "BlockEntityTag.sherds")))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LootTable.Builder createPitcherCropLoot() {
/* 1381 */     return (LootTable.Builder)applyExplosionDecay((ItemLike)Blocks.PITCHER_CROP, 
/* 1382 */         (FunctionUserBuilder)LootTable.lootTable()
/* 1383 */         .withPool(LootPool.lootPool()
/* 1384 */           .add((LootPoolEntryContainer.Builder)AlternativesEntry.alternatives(PitcherCropBlock.AGE.getPossibleValues(), $$0 -> {
/*      */                 LootItemBlockStatePropertyCondition.Builder $$1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.PITCHER_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)DoublePlantBlock.HALF, (Comparable)DoubleBlockHalf.LOWER));
/*      */                 LootItemBlockStatePropertyCondition.Builder $$2 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.PITCHER_CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)PitcherCropBlock.AGE, $$0.intValue()));
/*      */                 return (LootPoolEntryContainer.Builder)(($$0.intValue() == 4) ? ((LootPoolSingletonContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PITCHER_PLANT).when((LootItemCondition.Builder)$$2)).when((LootItemCondition.Builder)$$1)).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))) : ((LootPoolSingletonContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PITCHER_POD).when((LootItemCondition.Builder)$$2)).when((LootItemCondition.Builder)$$1)).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))));
/*      */               }))));
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\packs\VanillaBlockLoot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */