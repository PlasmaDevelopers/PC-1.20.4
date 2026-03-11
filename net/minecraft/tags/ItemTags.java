/*     */ package net.minecraft.tags;
/*     */ 
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.Item;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ItemTags
/*     */ {
/*  11 */   public static final TagKey<Item> WOOL = bind("wool");
/*  12 */   public static final TagKey<Item> PLANKS = bind("planks");
/*  13 */   public static final TagKey<Item> STONE_BRICKS = bind("stone_bricks");
/*  14 */   public static final TagKey<Item> WOODEN_BUTTONS = bind("wooden_buttons");
/*  15 */   public static final TagKey<Item> STONE_BUTTONS = bind("stone_buttons");
/*  16 */   public static final TagKey<Item> BUTTONS = bind("buttons");
/*  17 */   public static final TagKey<Item> WOOL_CARPETS = bind("wool_carpets");
/*  18 */   public static final TagKey<Item> WOODEN_DOORS = bind("wooden_doors");
/*  19 */   public static final TagKey<Item> WOODEN_STAIRS = bind("wooden_stairs");
/*  20 */   public static final TagKey<Item> WOODEN_SLABS = bind("wooden_slabs");
/*  21 */   public static final TagKey<Item> WOODEN_FENCES = bind("wooden_fences");
/*  22 */   public static final TagKey<Item> FENCE_GATES = bind("fence_gates");
/*  23 */   public static final TagKey<Item> WOODEN_PRESSURE_PLATES = bind("wooden_pressure_plates");
/*  24 */   public static final TagKey<Item> WOODEN_TRAPDOORS = bind("wooden_trapdoors");
/*  25 */   public static final TagKey<Item> DOORS = bind("doors");
/*  26 */   public static final TagKey<Item> SAPLINGS = bind("saplings");
/*  27 */   public static final TagKey<Item> LOGS_THAT_BURN = bind("logs_that_burn");
/*  28 */   public static final TagKey<Item> LOGS = bind("logs");
/*  29 */   public static final TagKey<Item> DARK_OAK_LOGS = bind("dark_oak_logs");
/*  30 */   public static final TagKey<Item> OAK_LOGS = bind("oak_logs");
/*  31 */   public static final TagKey<Item> BIRCH_LOGS = bind("birch_logs");
/*  32 */   public static final TagKey<Item> ACACIA_LOGS = bind("acacia_logs");
/*  33 */   public static final TagKey<Item> CHERRY_LOGS = bind("cherry_logs");
/*  34 */   public static final TagKey<Item> JUNGLE_LOGS = bind("jungle_logs");
/*  35 */   public static final TagKey<Item> SPRUCE_LOGS = bind("spruce_logs");
/*  36 */   public static final TagKey<Item> MANGROVE_LOGS = bind("mangrove_logs");
/*  37 */   public static final TagKey<Item> CRIMSON_STEMS = bind("crimson_stems");
/*  38 */   public static final TagKey<Item> WARPED_STEMS = bind("warped_stems");
/*  39 */   public static final TagKey<Item> BAMBOO_BLOCKS = bind("bamboo_blocks");
/*  40 */   public static final TagKey<Item> WART_BLOCKS = bind("wart_blocks");
/*  41 */   public static final TagKey<Item> BANNERS = bind("banners");
/*  42 */   public static final TagKey<Item> SAND = bind("sand");
/*  43 */   public static final TagKey<Item> SMELTS_TO_GLASS = bind("smelts_to_glass");
/*  44 */   public static final TagKey<Item> STAIRS = bind("stairs");
/*  45 */   public static final TagKey<Item> SLABS = bind("slabs");
/*  46 */   public static final TagKey<Item> WALLS = bind("walls");
/*  47 */   public static final TagKey<Item> ANVIL = bind("anvil");
/*  48 */   public static final TagKey<Item> RAILS = bind("rails");
/*  49 */   public static final TagKey<Item> LEAVES = bind("leaves");
/*  50 */   public static final TagKey<Item> TRAPDOORS = bind("trapdoors");
/*  51 */   public static final TagKey<Item> SMALL_FLOWERS = bind("small_flowers");
/*  52 */   public static final TagKey<Item> BEDS = bind("beds");
/*  53 */   public static final TagKey<Item> FENCES = bind("fences");
/*  54 */   public static final TagKey<Item> TALL_FLOWERS = bind("tall_flowers");
/*  55 */   public static final TagKey<Item> FLOWERS = bind("flowers");
/*  56 */   public static final TagKey<Item> PIGLIN_REPELLENTS = bind("piglin_repellents");
/*  57 */   public static final TagKey<Item> PIGLIN_LOVED = bind("piglin_loved");
/*  58 */   public static final TagKey<Item> IGNORED_BY_PIGLIN_BABIES = bind("ignored_by_piglin_babies");
/*  59 */   public static final TagKey<Item> PIGLIN_FOOD = bind("piglin_food");
/*  60 */   public static final TagKey<Item> FOX_FOOD = bind("fox_food");
/*     */   
/*  62 */   public static final TagKey<Item> GOLD_ORES = bind("gold_ores");
/*  63 */   public static final TagKey<Item> IRON_ORES = bind("iron_ores");
/*  64 */   public static final TagKey<Item> DIAMOND_ORES = bind("diamond_ores");
/*  65 */   public static final TagKey<Item> REDSTONE_ORES = bind("redstone_ores");
/*  66 */   public static final TagKey<Item> LAPIS_ORES = bind("lapis_ores");
/*  67 */   public static final TagKey<Item> COAL_ORES = bind("coal_ores");
/*  68 */   public static final TagKey<Item> EMERALD_ORES = bind("emerald_ores");
/*  69 */   public static final TagKey<Item> COPPER_ORES = bind("copper_ores");
/*     */   
/*  71 */   public static final TagKey<Item> NON_FLAMMABLE_WOOD = bind("non_flammable_wood");
/*  72 */   public static final TagKey<Item> SOUL_FIRE_BASE_BLOCKS = bind("soul_fire_base_blocks");
/*  73 */   public static final TagKey<Item> CANDLES = bind("candles");
/*     */   
/*  75 */   public static final TagKey<Item> DIRT = bind("dirt");
/*  76 */   public static final TagKey<Item> TERRACOTTA = bind("terracotta");
/*     */   
/*  78 */   public static final TagKey<Item> COMPLETES_FIND_TREE_TUTORIAL = bind("completes_find_tree_tutorial");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public static final TagKey<Item> BOATS = bind("boats");
/*  84 */   public static final TagKey<Item> CHEST_BOATS = bind("chest_boats");
/*  85 */   public static final TagKey<Item> FISHES = bind("fishes");
/*  86 */   public static final TagKey<Item> SIGNS = bind("signs");
/*  87 */   public static final TagKey<Item> MUSIC_DISCS = bind("music_discs");
/*  88 */   public static final TagKey<Item> CREEPER_DROP_MUSIC_DISCS = bind("creeper_drop_music_discs");
/*  89 */   public static final TagKey<Item> COALS = bind("coals");
/*  90 */   public static final TagKey<Item> ARROWS = bind("arrows");
/*  91 */   public static final TagKey<Item> LECTERN_BOOKS = bind("lectern_books");
/*  92 */   public static final TagKey<Item> BOOKSHELF_BOOKS = bind("bookshelf_books");
/*  93 */   public static final TagKey<Item> BEACON_PAYMENT_ITEMS = bind("beacon_payment_items");
/*  94 */   public static final TagKey<Item> STONE_TOOL_MATERIALS = bind("stone_tool_materials");
/*  95 */   public static final TagKey<Item> STONE_CRAFTING_MATERIALS = bind("stone_crafting_materials");
/*  96 */   public static final TagKey<Item> FREEZE_IMMUNE_WEARABLES = bind("freeze_immune_wearables");
/*  97 */   public static final TagKey<Item> AXOLOTL_TEMPT_ITEMS = bind("axolotl_tempt_items");
/*  98 */   public static final TagKey<Item> DAMPENS_VIBRATIONS = bind("dampens_vibrations");
/*  99 */   public static final TagKey<Item> CLUSTER_MAX_HARVESTABLES = bind("cluster_max_harvestables");
/* 100 */   public static final TagKey<Item> COMPASSES = bind("compasses");
/* 101 */   public static final TagKey<Item> HANGING_SIGNS = bind("hanging_signs");
/* 102 */   public static final TagKey<Item> CREEPER_IGNITERS = bind("creeper_igniters");
/* 103 */   public static final TagKey<Item> NOTE_BLOCK_TOP_INSTRUMENTS = bind("noteblock_top_instruments");
/* 104 */   public static final TagKey<Item> TRIMMABLE_ARMOR = bind("trimmable_armor");
/* 105 */   public static final TagKey<Item> TRIM_MATERIALS = bind("trim_materials");
/* 106 */   public static final TagKey<Item> TRIM_TEMPLATES = bind("trim_templates");
/* 107 */   public static final TagKey<Item> SNIFFER_FOOD = bind("sniffer_food");
/* 108 */   public static final TagKey<Item> DECORATED_POT_SHERDS = bind("decorated_pot_sherds");
/* 109 */   public static final TagKey<Item> DECORATED_POT_INGREDIENTS = bind("decorated_pot_ingredients");
/* 110 */   public static final TagKey<Item> SWORDS = bind("swords");
/* 111 */   public static final TagKey<Item> AXES = bind("axes");
/* 112 */   public static final TagKey<Item> HOES = bind("hoes");
/* 113 */   public static final TagKey<Item> PICKAXES = bind("pickaxes");
/* 114 */   public static final TagKey<Item> SHOVELS = bind("shovels");
/* 115 */   public static final TagKey<Item> TOOLS = bind("tools");
/* 116 */   public static final TagKey<Item> BREAKS_DECORATED_POTS = bind("breaks_decorated_pots");
/* 117 */   public static final TagKey<Item> VILLAGER_PLANTABLE_SEEDS = bind("villager_plantable_seeds");
/*     */   
/*     */   private static TagKey<Item> bind(String $$0) {
/* 120 */     return TagKey.create(Registries.ITEM, new ResourceLocation($$0));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\ItemTags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */