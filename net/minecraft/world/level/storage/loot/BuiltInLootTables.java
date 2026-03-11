/*     */ package net.minecraft.world.level.storage.loot;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collections;
/*     */ import java.util.Set;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public class BuiltInLootTables
/*     */ {
/*  10 */   private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
/*  11 */   private static final Set<ResourceLocation> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);
/*     */   
/*  13 */   public static final ResourceLocation EMPTY = new ResourceLocation("empty");
/*     */ 
/*     */   
/*  16 */   public static final ResourceLocation SPAWN_BONUS_CHEST = register("chests/spawn_bonus_chest");
/*  17 */   public static final ResourceLocation END_CITY_TREASURE = register("chests/end_city_treasure");
/*  18 */   public static final ResourceLocation SIMPLE_DUNGEON = register("chests/simple_dungeon");
/*  19 */   public static final ResourceLocation VILLAGE_WEAPONSMITH = register("chests/village/village_weaponsmith");
/*  20 */   public static final ResourceLocation VILLAGE_TOOLSMITH = register("chests/village/village_toolsmith");
/*  21 */   public static final ResourceLocation VILLAGE_ARMORER = register("chests/village/village_armorer");
/*  22 */   public static final ResourceLocation VILLAGE_CARTOGRAPHER = register("chests/village/village_cartographer");
/*  23 */   public static final ResourceLocation VILLAGE_MASON = register("chests/village/village_mason");
/*  24 */   public static final ResourceLocation VILLAGE_SHEPHERD = register("chests/village/village_shepherd");
/*  25 */   public static final ResourceLocation VILLAGE_BUTCHER = register("chests/village/village_butcher");
/*  26 */   public static final ResourceLocation VILLAGE_FLETCHER = register("chests/village/village_fletcher");
/*  27 */   public static final ResourceLocation VILLAGE_FISHER = register("chests/village/village_fisher");
/*  28 */   public static final ResourceLocation VILLAGE_TANNERY = register("chests/village/village_tannery");
/*  29 */   public static final ResourceLocation VILLAGE_TEMPLE = register("chests/village/village_temple");
/*  30 */   public static final ResourceLocation VILLAGE_DESERT_HOUSE = register("chests/village/village_desert_house");
/*  31 */   public static final ResourceLocation VILLAGE_PLAINS_HOUSE = register("chests/village/village_plains_house");
/*  32 */   public static final ResourceLocation VILLAGE_TAIGA_HOUSE = register("chests/village/village_taiga_house");
/*  33 */   public static final ResourceLocation VILLAGE_SNOWY_HOUSE = register("chests/village/village_snowy_house");
/*  34 */   public static final ResourceLocation VILLAGE_SAVANNA_HOUSE = register("chests/village/village_savanna_house");
/*  35 */   public static final ResourceLocation ABANDONED_MINESHAFT = register("chests/abandoned_mineshaft");
/*  36 */   public static final ResourceLocation NETHER_BRIDGE = register("chests/nether_bridge");
/*  37 */   public static final ResourceLocation STRONGHOLD_LIBRARY = register("chests/stronghold_library");
/*  38 */   public static final ResourceLocation STRONGHOLD_CROSSING = register("chests/stronghold_crossing");
/*  39 */   public static final ResourceLocation STRONGHOLD_CORRIDOR = register("chests/stronghold_corridor");
/*  40 */   public static final ResourceLocation DESERT_PYRAMID = register("chests/desert_pyramid");
/*  41 */   public static final ResourceLocation JUNGLE_TEMPLE = register("chests/jungle_temple");
/*  42 */   public static final ResourceLocation JUNGLE_TEMPLE_DISPENSER = register("chests/jungle_temple_dispenser");
/*  43 */   public static final ResourceLocation IGLOO_CHEST = register("chests/igloo_chest");
/*  44 */   public static final ResourceLocation WOODLAND_MANSION = register("chests/woodland_mansion");
/*  45 */   public static final ResourceLocation UNDERWATER_RUIN_SMALL = register("chests/underwater_ruin_small");
/*  46 */   public static final ResourceLocation UNDERWATER_RUIN_BIG = register("chests/underwater_ruin_big");
/*  47 */   public static final ResourceLocation BURIED_TREASURE = register("chests/buried_treasure");
/*  48 */   public static final ResourceLocation SHIPWRECK_MAP = register("chests/shipwreck_map");
/*  49 */   public static final ResourceLocation SHIPWRECK_SUPPLY = register("chests/shipwreck_supply");
/*  50 */   public static final ResourceLocation SHIPWRECK_TREASURE = register("chests/shipwreck_treasure");
/*  51 */   public static final ResourceLocation PILLAGER_OUTPOST = register("chests/pillager_outpost");
/*  52 */   public static final ResourceLocation BASTION_TREASURE = register("chests/bastion_treasure");
/*  53 */   public static final ResourceLocation BASTION_OTHER = register("chests/bastion_other");
/*  54 */   public static final ResourceLocation BASTION_BRIDGE = register("chests/bastion_bridge");
/*  55 */   public static final ResourceLocation BASTION_HOGLIN_STABLE = register("chests/bastion_hoglin_stable");
/*  56 */   public static final ResourceLocation ANCIENT_CITY = register("chests/ancient_city");
/*  57 */   public static final ResourceLocation ANCIENT_CITY_ICE_BOX = register("chests/ancient_city_ice_box");
/*  58 */   public static final ResourceLocation RUINED_PORTAL = register("chests/ruined_portal");
/*  59 */   public static final ResourceLocation TRIAL_CHAMBERS_REWARD = register("chests/trial_chambers/reward");
/*  60 */   public static final ResourceLocation TRIAL_CHAMBERS_SUPPLY = register("chests/trial_chambers/supply");
/*  61 */   public static final ResourceLocation TRIAL_CHAMBERS_CORRIDOR = register("chests/trial_chambers/corridor");
/*  62 */   public static final ResourceLocation TRIAL_CHAMBERS_INTERSECTION = register("chests/trial_chambers/intersection");
/*  63 */   public static final ResourceLocation TRIAL_CHAMBERS_INTERSECTION_BARREL = register("chests/trial_chambers/intersection_barrel");
/*  64 */   public static final ResourceLocation TRIAL_CHAMBERS_ENTRANCE = register("chests/trial_chambers/entrance");
/*  65 */   public static final ResourceLocation TRIAL_CHAMBERS_CORRIDOR_DISPENSER = register("dispensers/trial_chambers/corridor");
/*  66 */   public static final ResourceLocation TRIAL_CHAMBERS_CHAMBER_DISPENSER = register("dispensers/trial_chambers/chamber");
/*  67 */   public static final ResourceLocation TRIAL_CHAMBERS_WATER_DISPENSER = register("dispensers/trial_chambers/water");
/*  68 */   public static final ResourceLocation TRIAL_CHAMBERS_CORRIDOR_POT = register("pots/trial_chambers/corridor");
/*     */ 
/*     */   
/*  71 */   public static final ResourceLocation SHEEP_WHITE = register("entities/sheep/white");
/*  72 */   public static final ResourceLocation SHEEP_ORANGE = register("entities/sheep/orange");
/*  73 */   public static final ResourceLocation SHEEP_MAGENTA = register("entities/sheep/magenta");
/*  74 */   public static final ResourceLocation SHEEP_LIGHT_BLUE = register("entities/sheep/light_blue");
/*  75 */   public static final ResourceLocation SHEEP_YELLOW = register("entities/sheep/yellow");
/*  76 */   public static final ResourceLocation SHEEP_LIME = register("entities/sheep/lime");
/*  77 */   public static final ResourceLocation SHEEP_PINK = register("entities/sheep/pink");
/*  78 */   public static final ResourceLocation SHEEP_GRAY = register("entities/sheep/gray");
/*  79 */   public static final ResourceLocation SHEEP_LIGHT_GRAY = register("entities/sheep/light_gray");
/*  80 */   public static final ResourceLocation SHEEP_CYAN = register("entities/sheep/cyan");
/*  81 */   public static final ResourceLocation SHEEP_PURPLE = register("entities/sheep/purple");
/*  82 */   public static final ResourceLocation SHEEP_BLUE = register("entities/sheep/blue");
/*  83 */   public static final ResourceLocation SHEEP_BROWN = register("entities/sheep/brown");
/*  84 */   public static final ResourceLocation SHEEP_GREEN = register("entities/sheep/green");
/*  85 */   public static final ResourceLocation SHEEP_RED = register("entities/sheep/red");
/*  86 */   public static final ResourceLocation SHEEP_BLACK = register("entities/sheep/black");
/*     */ 
/*     */   
/*  89 */   public static final ResourceLocation FISHING = register("gameplay/fishing");
/*  90 */   public static final ResourceLocation FISHING_JUNK = register("gameplay/fishing/junk");
/*  91 */   public static final ResourceLocation FISHING_TREASURE = register("gameplay/fishing/treasure");
/*  92 */   public static final ResourceLocation FISHING_FISH = register("gameplay/fishing/fish");
/*     */ 
/*     */   
/*  95 */   public static final ResourceLocation CAT_MORNING_GIFT = register("gameplay/cat_morning_gift");
/*  96 */   public static final ResourceLocation ARMORER_GIFT = register("gameplay/hero_of_the_village/armorer_gift");
/*  97 */   public static final ResourceLocation BUTCHER_GIFT = register("gameplay/hero_of_the_village/butcher_gift");
/*  98 */   public static final ResourceLocation CARTOGRAPHER_GIFT = register("gameplay/hero_of_the_village/cartographer_gift");
/*  99 */   public static final ResourceLocation CLERIC_GIFT = register("gameplay/hero_of_the_village/cleric_gift");
/* 100 */   public static final ResourceLocation FARMER_GIFT = register("gameplay/hero_of_the_village/farmer_gift");
/* 101 */   public static final ResourceLocation FISHERMAN_GIFT = register("gameplay/hero_of_the_village/fisherman_gift");
/* 102 */   public static final ResourceLocation FLETCHER_GIFT = register("gameplay/hero_of_the_village/fletcher_gift");
/* 103 */   public static final ResourceLocation LEATHERWORKER_GIFT = register("gameplay/hero_of_the_village/leatherworker_gift");
/* 104 */   public static final ResourceLocation LIBRARIAN_GIFT = register("gameplay/hero_of_the_village/librarian_gift");
/* 105 */   public static final ResourceLocation MASON_GIFT = register("gameplay/hero_of_the_village/mason_gift");
/* 106 */   public static final ResourceLocation SHEPHERD_GIFT = register("gameplay/hero_of_the_village/shepherd_gift");
/* 107 */   public static final ResourceLocation TOOLSMITH_GIFT = register("gameplay/hero_of_the_village/toolsmith_gift");
/* 108 */   public static final ResourceLocation WEAPONSMITH_GIFT = register("gameplay/hero_of_the_village/weaponsmith_gift");
/*     */ 
/*     */   
/* 111 */   public static final ResourceLocation SNIFFER_DIGGING = register("gameplay/sniffer_digging");
/*     */ 
/*     */   
/* 114 */   public static final ResourceLocation PIGLIN_BARTERING = register("gameplay/piglin_bartering");
/*     */ 
/*     */   
/* 117 */   public static final ResourceLocation SPAWNER_TRIAL_CHAMBER_KEY = register("spawners/trial_chamber/key");
/* 118 */   public static final ResourceLocation SPAWNER_TRIAL_CHAMBER_CONSUMABLES = register("spawners/trial_chamber/consumables");
/*     */ 
/*     */   
/* 121 */   public static final ResourceLocation DESERT_WELL_ARCHAEOLOGY = register("archaeology/desert_well");
/* 122 */   public static final ResourceLocation DESERT_PYRAMID_ARCHAEOLOGY = register("archaeology/desert_pyramid");
/* 123 */   public static final ResourceLocation TRAIL_RUINS_ARCHAEOLOGY_COMMON = register("archaeology/trail_ruins_common");
/* 124 */   public static final ResourceLocation TRAIL_RUINS_ARCHAEOLOGY_RARE = register("archaeology/trail_ruins_rare");
/* 125 */   public static final ResourceLocation OCEAN_RUIN_WARM_ARCHAEOLOGY = register("archaeology/ocean_ruin_warm");
/* 126 */   public static final ResourceLocation OCEAN_RUIN_COLD_ARCHAEOLOGY = register("archaeology/ocean_ruin_cold");
/*     */   
/*     */   private static ResourceLocation register(String $$0) {
/* 129 */     return register(new ResourceLocation($$0));
/*     */   }
/*     */   
/*     */   private static ResourceLocation register(ResourceLocation $$0) {
/* 133 */     if (LOCATIONS.add($$0)) {
/* 134 */       return $$0;
/*     */     }
/*     */     
/* 137 */     throw new IllegalArgumentException("" + $$0 + " is already a registered built-in loot table");
/*     */   }
/*     */   
/*     */   public static Set<ResourceLocation> all() {
/* 141 */     return IMMUTABLE_LOCATIONS;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\BuiltInLootTables.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */