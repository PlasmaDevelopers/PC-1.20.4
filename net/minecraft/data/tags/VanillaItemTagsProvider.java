/*     */ package net.minecraft.data.tags;
/*     */ 
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.data.PackOutput;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ 
/*     */ public class VanillaItemTagsProvider extends ItemTagsProvider {
/*     */   public VanillaItemTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1, CompletableFuture<TagsProvider.TagLookup<Block>> $$2) {
/*  14 */     super($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addTags(HolderLookup.Provider $$0) {
/*  19 */     copy(BlockTags.WOOL, ItemTags.WOOL);
/*  20 */     copy(BlockTags.PLANKS, ItemTags.PLANKS);
/*  21 */     copy(BlockTags.STONE_BRICKS, ItemTags.STONE_BRICKS);
/*  22 */     copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
/*  23 */     copy(BlockTags.STONE_BUTTONS, ItemTags.STONE_BUTTONS);
/*  24 */     copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
/*  25 */     copy(BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS);
/*  26 */     copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
/*  27 */     copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
/*  28 */     copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
/*  29 */     copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
/*  30 */     copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
/*  31 */     copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
/*  32 */     copy(BlockTags.DOORS, ItemTags.DOORS);
/*  33 */     copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
/*  34 */     copy(BlockTags.BAMBOO_BLOCKS, ItemTags.BAMBOO_BLOCKS);
/*  35 */     copy(BlockTags.OAK_LOGS, ItemTags.OAK_LOGS);
/*  36 */     copy(BlockTags.DARK_OAK_LOGS, ItemTags.DARK_OAK_LOGS);
/*  37 */     copy(BlockTags.BIRCH_LOGS, ItemTags.BIRCH_LOGS);
/*  38 */     copy(BlockTags.ACACIA_LOGS, ItemTags.ACACIA_LOGS);
/*  39 */     copy(BlockTags.SPRUCE_LOGS, ItemTags.SPRUCE_LOGS);
/*  40 */     copy(BlockTags.MANGROVE_LOGS, ItemTags.MANGROVE_LOGS);
/*  41 */     copy(BlockTags.JUNGLE_LOGS, ItemTags.JUNGLE_LOGS);
/*  42 */     copy(BlockTags.CHERRY_LOGS, ItemTags.CHERRY_LOGS);
/*  43 */     copy(BlockTags.CRIMSON_STEMS, ItemTags.CRIMSON_STEMS);
/*  44 */     copy(BlockTags.WARPED_STEMS, ItemTags.WARPED_STEMS);
/*  45 */     copy(BlockTags.WART_BLOCKS, ItemTags.WART_BLOCKS);
/*  46 */     copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
/*  47 */     copy(BlockTags.LOGS, ItemTags.LOGS);
/*  48 */     copy(BlockTags.SAND, ItemTags.SAND);
/*  49 */     copy(BlockTags.SMELTS_TO_GLASS, ItemTags.SMELTS_TO_GLASS);
/*  50 */     copy(BlockTags.SLABS, ItemTags.SLABS);
/*  51 */     copy(BlockTags.WALLS, ItemTags.WALLS);
/*  52 */     copy(BlockTags.STAIRS, ItemTags.STAIRS);
/*  53 */     copy(BlockTags.ANVIL, ItemTags.ANVIL);
/*  54 */     copy(BlockTags.RAILS, ItemTags.RAILS);
/*  55 */     copy(BlockTags.LEAVES, ItemTags.LEAVES);
/*  56 */     copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
/*  57 */     copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
/*  58 */     copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
/*  59 */     copy(BlockTags.BEDS, ItemTags.BEDS);
/*  60 */     copy(BlockTags.FENCES, ItemTags.FENCES);
/*  61 */     copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
/*  62 */     copy(BlockTags.FLOWERS, ItemTags.FLOWERS);
/*  63 */     copy(BlockTags.SOUL_FIRE_BASE_BLOCKS, ItemTags.SOUL_FIRE_BASE_BLOCKS);
/*  64 */     copy(BlockTags.CANDLES, ItemTags.CANDLES);
/*  65 */     copy(BlockTags.DAMPENS_VIBRATIONS, ItemTags.DAMPENS_VIBRATIONS);
/*  66 */     copy(BlockTags.GOLD_ORES, ItemTags.GOLD_ORES);
/*  67 */     copy(BlockTags.IRON_ORES, ItemTags.IRON_ORES);
/*  68 */     copy(BlockTags.DIAMOND_ORES, ItemTags.DIAMOND_ORES);
/*  69 */     copy(BlockTags.REDSTONE_ORES, ItemTags.REDSTONE_ORES);
/*  70 */     copy(BlockTags.LAPIS_ORES, ItemTags.LAPIS_ORES);
/*  71 */     copy(BlockTags.COAL_ORES, ItemTags.COAL_ORES);
/*  72 */     copy(BlockTags.EMERALD_ORES, ItemTags.EMERALD_ORES);
/*  73 */     copy(BlockTags.COPPER_ORES, ItemTags.COPPER_ORES);
/*  74 */     copy(BlockTags.DIRT, ItemTags.DIRT);
/*  75 */     copy(BlockTags.TERRACOTTA, ItemTags.TERRACOTTA);
/*  76 */     copy(BlockTags.COMPLETES_FIND_TREE_TUTORIAL, ItemTags.COMPLETES_FIND_TREE_TUTORIAL);
/*     */     
/*  78 */     tag(ItemTags.BANNERS).add(new Item[] { Items.WHITE_BANNER, Items.ORANGE_BANNER, Items.MAGENTA_BANNER, Items.LIGHT_BLUE_BANNER, Items.YELLOW_BANNER, Items.LIME_BANNER, Items.PINK_BANNER, Items.GRAY_BANNER, Items.LIGHT_GRAY_BANNER, Items.CYAN_BANNER, Items.PURPLE_BANNER, Items.BLUE_BANNER, Items.BROWN_BANNER, Items.GREEN_BANNER, Items.RED_BANNER, Items.BLACK_BANNER });
/*  79 */     tag(ItemTags.BOATS).add(new Item[] { Items.OAK_BOAT, Items.SPRUCE_BOAT, Items.BIRCH_BOAT, Items.JUNGLE_BOAT, Items.ACACIA_BOAT, Items.DARK_OAK_BOAT, Items.MANGROVE_BOAT, Items.BAMBOO_RAFT, Items.CHERRY_BOAT }).addTag(ItemTags.CHEST_BOATS);
/*  80 */     tag(ItemTags.CHEST_BOATS).add(new Item[] { Items.OAK_CHEST_BOAT, Items.SPRUCE_CHEST_BOAT, Items.BIRCH_CHEST_BOAT, Items.JUNGLE_CHEST_BOAT, Items.ACACIA_CHEST_BOAT, Items.DARK_OAK_CHEST_BOAT, Items.MANGROVE_CHEST_BOAT, Items.BAMBOO_CHEST_RAFT, Items.CHERRY_CHEST_BOAT });
/*  81 */     tag(ItemTags.FISHES).add(new Item[] { Items.COD, Items.COOKED_COD, Items.SALMON, Items.COOKED_SALMON, Items.PUFFERFISH, Items.TROPICAL_FISH });
/*  82 */     copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
/*  83 */     copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
/*  84 */     tag(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(new Item[] { Items.MUSIC_DISC_13, Items.MUSIC_DISC_CAT, Items.MUSIC_DISC_BLOCKS, Items.MUSIC_DISC_CHIRP, Items.MUSIC_DISC_FAR, Items.MUSIC_DISC_MALL, Items.MUSIC_DISC_MELLOHI, Items.MUSIC_DISC_STAL, Items.MUSIC_DISC_STRAD, Items.MUSIC_DISC_WARD, Items.MUSIC_DISC_11, Items.MUSIC_DISC_WAIT });
/*  85 */     tag(ItemTags.MUSIC_DISCS).addTag(ItemTags.CREEPER_DROP_MUSIC_DISCS).add(Items.MUSIC_DISC_PIGSTEP).add(Items.MUSIC_DISC_OTHERSIDE).add(Items.MUSIC_DISC_5).add(Items.MUSIC_DISC_RELIC);
/*  86 */     tag(ItemTags.COALS).add(new Item[] { Items.COAL, Items.CHARCOAL });
/*  87 */     tag(ItemTags.ARROWS).add(new Item[] { Items.ARROW, Items.TIPPED_ARROW, Items.SPECTRAL_ARROW });
/*  88 */     tag(ItemTags.LECTERN_BOOKS).add(new Item[] { Items.WRITTEN_BOOK, Items.WRITABLE_BOOK });
/*  89 */     tag(ItemTags.BEACON_PAYMENT_ITEMS).add(new Item[] { Items.NETHERITE_INGOT, Items.EMERALD, Items.DIAMOND, Items.GOLD_INGOT, Items.IRON_INGOT });
/*  90 */     tag(ItemTags.PIGLIN_REPELLENTS).add(Items.SOUL_TORCH).add(Items.SOUL_LANTERN).add(Items.SOUL_CAMPFIRE);
/*  91 */     tag(ItemTags.PIGLIN_LOVED).addTag(ItemTags.GOLD_ORES).add(new Item[] { Items.GOLD_BLOCK, Items.GILDED_BLACKSTONE, Items.LIGHT_WEIGHTED_PRESSURE_PLATE, Items.GOLD_INGOT, Items.BELL, Items.CLOCK, Items.GOLDEN_CARROT, Items.GLISTERING_MELON_SLICE, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS, Items.GOLDEN_HORSE_ARMOR, Items.GOLDEN_SWORD, Items.GOLDEN_PICKAXE, Items.GOLDEN_SHOVEL, Items.GOLDEN_AXE, Items.GOLDEN_HOE, Items.RAW_GOLD, Items.RAW_GOLD_BLOCK });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     tag(ItemTags.IGNORED_BY_PIGLIN_BABIES).add(Items.LEATHER);
/*  98 */     tag(ItemTags.PIGLIN_FOOD).add(new Item[] { Items.PORKCHOP, Items.COOKED_PORKCHOP });
/*  99 */     tag(ItemTags.FOX_FOOD).add(new Item[] { Items.SWEET_BERRIES, Items.GLOW_BERRIES });
/*     */     
/* 101 */     tag(ItemTags.NON_FLAMMABLE_WOOD).add(new Item[] { Items.WARPED_STEM, Items.STRIPPED_WARPED_STEM, Items.WARPED_HYPHAE, Items.STRIPPED_WARPED_HYPHAE, Items.CRIMSON_STEM, Items.STRIPPED_CRIMSON_STEM, Items.CRIMSON_HYPHAE, Items.STRIPPED_CRIMSON_HYPHAE, Items.CRIMSON_PLANKS, Items.WARPED_PLANKS, Items.CRIMSON_SLAB, Items.WARPED_SLAB, Items.CRIMSON_PRESSURE_PLATE, Items.WARPED_PRESSURE_PLATE, Items.CRIMSON_FENCE, Items.WARPED_FENCE, Items.CRIMSON_TRAPDOOR, Items.WARPED_TRAPDOOR, Items.CRIMSON_FENCE_GATE, Items.WARPED_FENCE_GATE, Items.CRIMSON_STAIRS, Items.WARPED_STAIRS, Items.CRIMSON_BUTTON, Items.WARPED_BUTTON, Items.CRIMSON_DOOR, Items.WARPED_DOOR, Items.CRIMSON_SIGN, Items.WARPED_SIGN, Items.WARPED_HANGING_SIGN, Items.CRIMSON_HANGING_SIGN });
/* 102 */     tag(ItemTags.STONE_TOOL_MATERIALS).add(new Item[] { Items.COBBLESTONE, Items.BLACKSTONE, Items.COBBLED_DEEPSLATE });
/* 103 */     tag(ItemTags.STONE_CRAFTING_MATERIALS).add(new Item[] { Items.COBBLESTONE, Items.BLACKSTONE, Items.COBBLED_DEEPSLATE });
/* 104 */     tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(new Item[] { Items.LEATHER_BOOTS, Items.LEATHER_LEGGINGS, Items.LEATHER_CHESTPLATE, Items.LEATHER_HELMET, Items.LEATHER_HORSE_ARMOR });
/* 105 */     tag(ItemTags.AXOLOTL_TEMPT_ITEMS).add(Items.TROPICAL_FISH_BUCKET);
/* 106 */     tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(new Item[] { Items.DIAMOND_PICKAXE, Items.GOLDEN_PICKAXE, Items.IRON_PICKAXE, Items.NETHERITE_PICKAXE, Items.STONE_PICKAXE, Items.WOODEN_PICKAXE });
/* 107 */     tag(ItemTags.COMPASSES).add(Items.COMPASS).add(Items.RECOVERY_COMPASS);
/* 108 */     tag(ItemTags.CREEPER_IGNITERS).add(Items.FLINT_AND_STEEL).add(Items.FIRE_CHARGE);
/*     */     
/* 110 */     tag(ItemTags.SWORDS).add(Items.DIAMOND_SWORD).add(Items.STONE_SWORD).add(Items.GOLDEN_SWORD).add(Items.NETHERITE_SWORD).add(Items.WOODEN_SWORD).add(Items.IRON_SWORD);
/* 111 */     tag(ItemTags.AXES).add(Items.DIAMOND_AXE).add(Items.STONE_AXE).add(Items.GOLDEN_AXE).add(Items.NETHERITE_AXE).add(Items.WOODEN_AXE).add(Items.IRON_AXE);
/* 112 */     tag(ItemTags.PICKAXES).add(Items.DIAMOND_PICKAXE).add(Items.STONE_PICKAXE).add(Items.GOLDEN_PICKAXE).add(Items.NETHERITE_PICKAXE).add(Items.WOODEN_PICKAXE).add(Items.IRON_PICKAXE);
/* 113 */     tag(ItemTags.SHOVELS).add(Items.DIAMOND_SHOVEL).add(Items.STONE_SHOVEL).add(Items.GOLDEN_SHOVEL).add(Items.NETHERITE_SHOVEL).add(Items.WOODEN_SHOVEL).add(Items.IRON_SHOVEL);
/* 114 */     tag(ItemTags.HOES).add(Items.DIAMOND_HOE).add(Items.STONE_HOE).add(Items.GOLDEN_HOE).add(Items.NETHERITE_HOE).add(Items.WOODEN_HOE).add(Items.IRON_HOE);
/*     */     
/* 116 */     tag(ItemTags.TOOLS).addTag(ItemTags.SWORDS).addTag(ItemTags.AXES).addTag(ItemTags.PICKAXES).addTag(ItemTags.SHOVELS).addTag(ItemTags.HOES).add(Items.TRIDENT);
/*     */     
/* 118 */     tag(ItemTags.BREAKS_DECORATED_POTS).addTag(ItemTags.TOOLS);
/*     */     
/* 120 */     tag(ItemTags.DECORATED_POT_SHERDS).add(new Item[] { Items.ANGLER_POTTERY_SHERD, Items.ARCHER_POTTERY_SHERD, Items.ARMS_UP_POTTERY_SHERD, Items.BLADE_POTTERY_SHERD, Items.BREWER_POTTERY_SHERD, Items.BURN_POTTERY_SHERD, Items.DANGER_POTTERY_SHERD, Items.EXPLORER_POTTERY_SHERD, Items.FRIEND_POTTERY_SHERD, Items.HEART_POTTERY_SHERD, Items.HEARTBREAK_POTTERY_SHERD, Items.HOWL_POTTERY_SHERD, Items.MINER_POTTERY_SHERD, Items.MOURNER_POTTERY_SHERD, Items.PLENTY_POTTERY_SHERD, Items.PRIZE_POTTERY_SHERD, Items.SHEAF_POTTERY_SHERD, Items.SHELTER_POTTERY_SHERD, Items.SKULL_POTTERY_SHERD, Items.SNORT_POTTERY_SHERD });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     tag(ItemTags.DECORATED_POT_INGREDIENTS)
/* 144 */       .add(Items.BRICK)
/* 145 */       .addTag(ItemTags.DECORATED_POT_SHERDS);
/*     */     
/* 147 */     tag(ItemTags.TRIMMABLE_ARMOR)
/* 148 */       .add(Items.NETHERITE_HELMET).add(Items.NETHERITE_CHESTPLATE).add(Items.NETHERITE_LEGGINGS).add(Items.NETHERITE_BOOTS)
/* 149 */       .add(Items.DIAMOND_HELMET).add(Items.DIAMOND_CHESTPLATE).add(Items.DIAMOND_LEGGINGS).add(Items.DIAMOND_BOOTS)
/* 150 */       .add(Items.GOLDEN_HELMET).add(Items.GOLDEN_CHESTPLATE).add(Items.GOLDEN_LEGGINGS).add(Items.GOLDEN_BOOTS)
/* 151 */       .add(Items.IRON_HELMET).add(Items.IRON_CHESTPLATE).add(Items.IRON_LEGGINGS).add(Items.IRON_BOOTS)
/* 152 */       .add(Items.CHAINMAIL_HELMET).add(Items.CHAINMAIL_CHESTPLATE).add(Items.CHAINMAIL_LEGGINGS).add(Items.CHAINMAIL_BOOTS)
/* 153 */       .add(Items.LEATHER_HELMET).add(Items.LEATHER_CHESTPLATE).add(Items.LEATHER_LEGGINGS).add(Items.LEATHER_BOOTS)
/* 154 */       .add(Items.TURTLE_HELMET);
/* 155 */     tag(ItemTags.TRIM_MATERIALS).add(Items.IRON_INGOT).add(Items.COPPER_INGOT).add(Items.GOLD_INGOT).add(Items.LAPIS_LAZULI)
/* 156 */       .add(Items.EMERALD).add(Items.DIAMOND).add(Items.NETHERITE_INGOT).add(Items.REDSTONE).add(Items.QUARTZ).add(Items.AMETHYST_SHARD);
/* 157 */     tag(ItemTags.TRIM_TEMPLATES).add(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE).add(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE).add(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE)
/* 158 */       .add(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE).add(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).add(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE)
/* 159 */       .add(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE).add(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE).add(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE)
/* 160 */       .add(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE).add(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).add(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE)
/* 161 */       .add(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE).add(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE).add(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE)
/* 162 */       .add(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE);
/*     */     
/* 164 */     tag(ItemTags.BOOKSHELF_BOOKS).add(new Item[] { Items.BOOK, Items.WRITTEN_BOOK, Items.ENCHANTED_BOOK, Items.WRITABLE_BOOK, Items.KNOWLEDGE_BOOK });
/* 165 */     tag(ItemTags.NOTE_BLOCK_TOP_INSTRUMENTS).add(new Item[] { Items.ZOMBIE_HEAD, Items.SKELETON_SKULL, Items.CREEPER_HEAD, Items.DRAGON_HEAD, Items.WITHER_SKELETON_SKULL, Items.PIGLIN_HEAD, Items.PLAYER_HEAD });
/* 166 */     tag(ItemTags.SNIFFER_FOOD).add(Items.TORCHFLOWER_SEEDS);
/* 167 */     tag(ItemTags.VILLAGER_PLANTABLE_SEEDS).add(new Item[] { Items.WHEAT_SEEDS, Items.POTATO, Items.CARROT, Items.BEETROOT_SEEDS, Items.TORCHFLOWER_SEEDS, Items.PITCHER_POD });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\VanillaItemTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */