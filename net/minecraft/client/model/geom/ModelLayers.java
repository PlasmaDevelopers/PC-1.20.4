/*     */ package net.minecraft.client.model.geom;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.vehicle.Boat;
/*     */ import net.minecraft.world.level.block.state.properties.WoodType;
/*     */ 
/*     */ public class ModelLayers
/*     */ {
/*     */   private static final String DEFAULT_LAYER = "main";
/*  13 */   private static final Set<ModelLayerLocation> ALL_MODELS = Sets.newHashSet();
/*     */   
/*  15 */   public static final ModelLayerLocation ALLAY = register("allay");
/*  16 */   public static final ModelLayerLocation ARMOR_STAND = register("armor_stand");
/*  17 */   public static final ModelLayerLocation ARMOR_STAND_INNER_ARMOR = registerInnerArmor("armor_stand");
/*  18 */   public static final ModelLayerLocation ARMOR_STAND_OUTER_ARMOR = registerOuterArmor("armor_stand");
/*  19 */   public static final ModelLayerLocation AXOLOTL = register("axolotl");
/*  20 */   public static final ModelLayerLocation BANNER = register("banner");
/*  21 */   public static final ModelLayerLocation BAT = register("bat");
/*  22 */   public static final ModelLayerLocation BED_FOOT = register("bed_foot");
/*  23 */   public static final ModelLayerLocation BED_HEAD = register("bed_head");
/*  24 */   public static final ModelLayerLocation BEE = register("bee");
/*  25 */   public static final ModelLayerLocation BELL = register("bell");
/*  26 */   public static final ModelLayerLocation BLAZE = register("blaze");
/*  27 */   public static final ModelLayerLocation BOOK = register("book");
/*  28 */   public static final ModelLayerLocation BREEZE = register("breeze");
/*  29 */   public static final ModelLayerLocation BREEZE_WIND = register("breeze", "wind");
/*  30 */   public static final ModelLayerLocation BREEZE_EYES = register("breeze", "eyes");
/*  31 */   public static final ModelLayerLocation CAT = register("cat");
/*  32 */   public static final ModelLayerLocation CAT_COLLAR = register("cat", "collar");
/*  33 */   public static final ModelLayerLocation CAMEL = register("camel");
/*  34 */   public static final ModelLayerLocation CAVE_SPIDER = register("cave_spider");
/*  35 */   public static final ModelLayerLocation CHEST = register("chest");
/*  36 */   public static final ModelLayerLocation CHEST_MINECART = register("chest_minecart");
/*  37 */   public static final ModelLayerLocation CHICKEN = register("chicken");
/*  38 */   public static final ModelLayerLocation COD = register("cod");
/*  39 */   public static final ModelLayerLocation COMMAND_BLOCK_MINECART = register("command_block_minecart");
/*  40 */   public static final ModelLayerLocation CONDUIT_CAGE = register("conduit", "cage");
/*  41 */   public static final ModelLayerLocation CONDUIT_EYE = register("conduit", "eye");
/*  42 */   public static final ModelLayerLocation CONDUIT_SHELL = register("conduit", "shell");
/*  43 */   public static final ModelLayerLocation CONDUIT_WIND = register("conduit", "wind");
/*  44 */   public static final ModelLayerLocation COW = register("cow");
/*  45 */   public static final ModelLayerLocation CREEPER = register("creeper");
/*  46 */   public static final ModelLayerLocation CREEPER_ARMOR = register("creeper", "armor");
/*  47 */   public static final ModelLayerLocation CREEPER_HEAD = register("creeper_head");
/*  48 */   public static final ModelLayerLocation DECORATED_POT_BASE = register("decorated_pot_base");
/*  49 */   public static final ModelLayerLocation DECORATED_POT_SIDES = register("decorated_pot_sides");
/*  50 */   public static final ModelLayerLocation DOLPHIN = register("dolphin");
/*  51 */   public static final ModelLayerLocation DONKEY = register("donkey");
/*  52 */   public static final ModelLayerLocation DOUBLE_CHEST_LEFT = register("double_chest_left");
/*  53 */   public static final ModelLayerLocation DOUBLE_CHEST_RIGHT = register("double_chest_right");
/*  54 */   public static final ModelLayerLocation DRAGON_SKULL = register("dragon_skull");
/*  55 */   public static final ModelLayerLocation DROWNED = register("drowned");
/*  56 */   public static final ModelLayerLocation DROWNED_INNER_ARMOR = registerInnerArmor("drowned");
/*  57 */   public static final ModelLayerLocation DROWNED_OUTER_ARMOR = registerOuterArmor("drowned");
/*  58 */   public static final ModelLayerLocation DROWNED_OUTER_LAYER = register("drowned", "outer");
/*  59 */   public static final ModelLayerLocation ELDER_GUARDIAN = register("elder_guardian");
/*  60 */   public static final ModelLayerLocation ELYTRA = register("elytra");
/*  61 */   public static final ModelLayerLocation ENDERMAN = register("enderman");
/*  62 */   public static final ModelLayerLocation ENDERMITE = register("endermite");
/*  63 */   public static final ModelLayerLocation ENDER_DRAGON = register("ender_dragon");
/*  64 */   public static final ModelLayerLocation END_CRYSTAL = register("end_crystal");
/*  65 */   public static final ModelLayerLocation EVOKER = register("evoker");
/*  66 */   public static final ModelLayerLocation EVOKER_FANGS = register("evoker_fangs");
/*  67 */   public static final ModelLayerLocation FOX = register("fox");
/*  68 */   public static final ModelLayerLocation FROG = register("frog");
/*  69 */   public static final ModelLayerLocation FURNACE_MINECART = register("furnace_minecart");
/*  70 */   public static final ModelLayerLocation GHAST = register("ghast");
/*  71 */   public static final ModelLayerLocation GIANT = register("giant");
/*  72 */   public static final ModelLayerLocation GIANT_INNER_ARMOR = registerInnerArmor("giant");
/*  73 */   public static final ModelLayerLocation GIANT_OUTER_ARMOR = registerOuterArmor("giant");
/*  74 */   public static final ModelLayerLocation GLOW_SQUID = register("glow_squid");
/*  75 */   public static final ModelLayerLocation GOAT = register("goat");
/*  76 */   public static final ModelLayerLocation GUARDIAN = register("guardian");
/*  77 */   public static final ModelLayerLocation HOGLIN = register("hoglin");
/*  78 */   public static final ModelLayerLocation HOPPER_MINECART = register("hopper_minecart");
/*  79 */   public static final ModelLayerLocation HORSE = register("horse");
/*  80 */   public static final ModelLayerLocation HORSE_ARMOR = register("horse_armor");
/*  81 */   public static final ModelLayerLocation HUSK = register("husk");
/*  82 */   public static final ModelLayerLocation HUSK_INNER_ARMOR = registerInnerArmor("husk");
/*  83 */   public static final ModelLayerLocation HUSK_OUTER_ARMOR = registerOuterArmor("husk");
/*  84 */   public static final ModelLayerLocation ILLUSIONER = register("illusioner");
/*  85 */   public static final ModelLayerLocation IRON_GOLEM = register("iron_golem");
/*  86 */   public static final ModelLayerLocation LEASH_KNOT = register("leash_knot");
/*  87 */   public static final ModelLayerLocation LLAMA = register("llama");
/*  88 */   public static final ModelLayerLocation LLAMA_DECOR = register("llama", "decor");
/*  89 */   public static final ModelLayerLocation LLAMA_SPIT = register("llama_spit");
/*  90 */   public static final ModelLayerLocation MAGMA_CUBE = register("magma_cube");
/*  91 */   public static final ModelLayerLocation MINECART = register("minecart");
/*  92 */   public static final ModelLayerLocation MOOSHROOM = register("mooshroom");
/*  93 */   public static final ModelLayerLocation MULE = register("mule");
/*  94 */   public static final ModelLayerLocation OCELOT = register("ocelot");
/*  95 */   public static final ModelLayerLocation PANDA = register("panda");
/*  96 */   public static final ModelLayerLocation PARROT = register("parrot");
/*  97 */   public static final ModelLayerLocation PHANTOM = register("phantom");
/*  98 */   public static final ModelLayerLocation PIG = register("pig");
/*  99 */   public static final ModelLayerLocation PIGLIN = register("piglin");
/* 100 */   public static final ModelLayerLocation PIGLIN_BRUTE = register("piglin_brute");
/* 101 */   public static final ModelLayerLocation PIGLIN_BRUTE_INNER_ARMOR = registerInnerArmor("piglin_brute");
/* 102 */   public static final ModelLayerLocation PIGLIN_BRUTE_OUTER_ARMOR = registerOuterArmor("piglin_brute");
/* 103 */   public static final ModelLayerLocation PIGLIN_HEAD = register("piglin_head");
/* 104 */   public static final ModelLayerLocation PIGLIN_INNER_ARMOR = registerInnerArmor("piglin");
/* 105 */   public static final ModelLayerLocation PIGLIN_OUTER_ARMOR = registerOuterArmor("piglin");
/* 106 */   public static final ModelLayerLocation PIG_SADDLE = register("pig", "saddle");
/* 107 */   public static final ModelLayerLocation PILLAGER = register("pillager");
/* 108 */   public static final ModelLayerLocation PLAYER = register("player");
/* 109 */   public static final ModelLayerLocation PLAYER_HEAD = register("player_head");
/* 110 */   public static final ModelLayerLocation PLAYER_INNER_ARMOR = registerInnerArmor("player");
/* 111 */   public static final ModelLayerLocation PLAYER_OUTER_ARMOR = registerOuterArmor("player");
/* 112 */   public static final ModelLayerLocation PLAYER_SLIM = register("player_slim");
/* 113 */   public static final ModelLayerLocation PLAYER_SLIM_INNER_ARMOR = registerInnerArmor("player_slim");
/* 114 */   public static final ModelLayerLocation PLAYER_SLIM_OUTER_ARMOR = registerOuterArmor("player_slim");
/* 115 */   public static final ModelLayerLocation PLAYER_SPIN_ATTACK = register("spin_attack");
/* 116 */   public static final ModelLayerLocation POLAR_BEAR = register("polar_bear");
/* 117 */   public static final ModelLayerLocation PUFFERFISH_BIG = register("pufferfish_big");
/* 118 */   public static final ModelLayerLocation PUFFERFISH_MEDIUM = register("pufferfish_medium");
/* 119 */   public static final ModelLayerLocation PUFFERFISH_SMALL = register("pufferfish_small");
/* 120 */   public static final ModelLayerLocation RABBIT = register("rabbit");
/* 121 */   public static final ModelLayerLocation RAVAGER = register("ravager");
/* 122 */   public static final ModelLayerLocation SALMON = register("salmon");
/* 123 */   public static final ModelLayerLocation SHEEP = register("sheep");
/* 124 */   public static final ModelLayerLocation SHEEP_FUR = register("sheep", "fur");
/* 125 */   public static final ModelLayerLocation SHIELD = register("shield");
/* 126 */   public static final ModelLayerLocation SHULKER = register("shulker");
/* 127 */   public static final ModelLayerLocation SHULKER_BULLET = register("shulker_bullet");
/* 128 */   public static final ModelLayerLocation SILVERFISH = register("silverfish");
/* 129 */   public static final ModelLayerLocation SKELETON = register("skeleton");
/* 130 */   public static final ModelLayerLocation SKELETON_HORSE = register("skeleton_horse");
/* 131 */   public static final ModelLayerLocation SKELETON_INNER_ARMOR = registerInnerArmor("skeleton");
/* 132 */   public static final ModelLayerLocation SKELETON_OUTER_ARMOR = registerOuterArmor("skeleton");
/* 133 */   public static final ModelLayerLocation SKELETON_SKULL = register("skeleton_skull");
/* 134 */   public static final ModelLayerLocation SLIME = register("slime");
/* 135 */   public static final ModelLayerLocation SLIME_OUTER = register("slime", "outer");
/* 136 */   public static final ModelLayerLocation SNIFFER = register("sniffer");
/* 137 */   public static final ModelLayerLocation SNOW_GOLEM = register("snow_golem");
/* 138 */   public static final ModelLayerLocation SPAWNER_MINECART = register("spawner_minecart");
/* 139 */   public static final ModelLayerLocation SPIDER = register("spider");
/* 140 */   public static final ModelLayerLocation SQUID = register("squid");
/* 141 */   public static final ModelLayerLocation STRAY = register("stray");
/* 142 */   public static final ModelLayerLocation STRAY_INNER_ARMOR = registerInnerArmor("stray");
/* 143 */   public static final ModelLayerLocation STRAY_OUTER_ARMOR = registerOuterArmor("stray");
/* 144 */   public static final ModelLayerLocation STRAY_OUTER_LAYER = register("stray", "outer");
/* 145 */   public static final ModelLayerLocation STRIDER = register("strider");
/* 146 */   public static final ModelLayerLocation STRIDER_SADDLE = register("strider", "saddle");
/* 147 */   public static final ModelLayerLocation TADPOLE = register("tadpole");
/* 148 */   public static final ModelLayerLocation TNT_MINECART = register("tnt_minecart");
/* 149 */   public static final ModelLayerLocation TRADER_LLAMA = register("trader_llama");
/* 150 */   public static final ModelLayerLocation TRIDENT = register("trident");
/* 151 */   public static final ModelLayerLocation TROPICAL_FISH_LARGE = register("tropical_fish_large");
/* 152 */   public static final ModelLayerLocation TROPICAL_FISH_LARGE_PATTERN = register("tropical_fish_large", "pattern");
/* 153 */   public static final ModelLayerLocation TROPICAL_FISH_SMALL = register("tropical_fish_small");
/* 154 */   public static final ModelLayerLocation TROPICAL_FISH_SMALL_PATTERN = register("tropical_fish_small", "pattern");
/* 155 */   public static final ModelLayerLocation TURTLE = register("turtle");
/* 156 */   public static final ModelLayerLocation VEX = register("vex");
/* 157 */   public static final ModelLayerLocation VILLAGER = register("villager");
/* 158 */   public static final ModelLayerLocation VINDICATOR = register("vindicator");
/* 159 */   public static final ModelLayerLocation WARDEN = register("warden");
/* 160 */   public static final ModelLayerLocation WANDERING_TRADER = register("wandering_trader");
/* 161 */   public static final ModelLayerLocation WIND_CHARGE = register("wind_charge");
/* 162 */   public static final ModelLayerLocation WITCH = register("witch");
/* 163 */   public static final ModelLayerLocation WITHER = register("wither");
/* 164 */   public static final ModelLayerLocation WITHER_ARMOR = register("wither", "armor");
/* 165 */   public static final ModelLayerLocation WITHER_SKELETON = register("wither_skeleton");
/* 166 */   public static final ModelLayerLocation WITHER_SKELETON_INNER_ARMOR = registerInnerArmor("wither_skeleton");
/* 167 */   public static final ModelLayerLocation WITHER_SKELETON_OUTER_ARMOR = registerOuterArmor("wither_skeleton");
/* 168 */   public static final ModelLayerLocation WITHER_SKELETON_SKULL = register("wither_skeleton_skull");
/* 169 */   public static final ModelLayerLocation WITHER_SKULL = register("wither_skull");
/* 170 */   public static final ModelLayerLocation WOLF = register("wolf");
/* 171 */   public static final ModelLayerLocation ZOGLIN = register("zoglin");
/* 172 */   public static final ModelLayerLocation ZOMBIE = register("zombie");
/* 173 */   public static final ModelLayerLocation ZOMBIE_HEAD = register("zombie_head");
/* 174 */   public static final ModelLayerLocation ZOMBIE_HORSE = register("zombie_horse");
/* 175 */   public static final ModelLayerLocation ZOMBIE_INNER_ARMOR = registerInnerArmor("zombie");
/* 176 */   public static final ModelLayerLocation ZOMBIE_OUTER_ARMOR = registerOuterArmor("zombie");
/* 177 */   public static final ModelLayerLocation ZOMBIE_VILLAGER = register("zombie_villager");
/* 178 */   public static final ModelLayerLocation ZOMBIE_VILLAGER_INNER_ARMOR = registerInnerArmor("zombie_villager");
/* 179 */   public static final ModelLayerLocation ZOMBIE_VILLAGER_OUTER_ARMOR = registerOuterArmor("zombie_villager");
/* 180 */   public static final ModelLayerLocation ZOMBIFIED_PIGLIN = register("zombified_piglin");
/* 181 */   public static final ModelLayerLocation ZOMBIFIED_PIGLIN_INNER_ARMOR = registerInnerArmor("zombified_piglin");
/* 182 */   public static final ModelLayerLocation ZOMBIFIED_PIGLIN_OUTER_ARMOR = registerOuterArmor("zombified_piglin");
/*     */   
/*     */   private static ModelLayerLocation register(String $$0) {
/* 185 */     return register($$0, "main");
/*     */   }
/*     */   
/*     */   private static ModelLayerLocation register(String $$0, String $$1) {
/* 189 */     ModelLayerLocation $$2 = createLocation($$0, $$1);
/* 190 */     if (!ALL_MODELS.add($$2)) {
/* 191 */       throw new IllegalStateException("Duplicate registration for " + $$2);
/*     */     }
/* 193 */     return $$2;
/*     */   }
/*     */   
/*     */   private static ModelLayerLocation createLocation(String $$0, String $$1) {
/* 197 */     return new ModelLayerLocation(new ResourceLocation("minecraft", $$0), $$1);
/*     */   }
/*     */   
/*     */   private static ModelLayerLocation registerInnerArmor(String $$0) {
/* 201 */     return register($$0, "inner_armor");
/*     */   }
/*     */   
/*     */   private static ModelLayerLocation registerOuterArmor(String $$0) {
/* 205 */     return register($$0, "outer_armor");
/*     */   }
/*     */   
/*     */   public static ModelLayerLocation createRaftModelName(Boat.Type $$0) {
/* 209 */     return createLocation("raft/" + $$0.getName(), "main");
/*     */   }
/*     */   
/*     */   public static ModelLayerLocation createChestRaftModelName(Boat.Type $$0) {
/* 213 */     return createLocation("chest_raft/" + $$0.getName(), "main");
/*     */   }
/*     */   
/*     */   public static ModelLayerLocation createBoatModelName(Boat.Type $$0) {
/* 217 */     return createLocation("boat/" + $$0.getName(), "main");
/*     */   }
/*     */   
/*     */   public static ModelLayerLocation createChestBoatModelName(Boat.Type $$0) {
/* 221 */     return createLocation("chest_boat/" + $$0.getName(), "main");
/*     */   }
/*     */   
/*     */   public static ModelLayerLocation createSignModelName(WoodType $$0) {
/* 225 */     return createLocation("sign/" + $$0.name(), "main");
/*     */   }
/*     */   
/*     */   public static ModelLayerLocation createHangingSignModelName(WoodType $$0) {
/* 229 */     return createLocation("hanging_sign/" + $$0.name(), "main");
/*     */   }
/*     */   
/*     */   public static Stream<ModelLayerLocation> getKnownLocations() {
/* 233 */     return ALL_MODELS.stream();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\geom\ModelLayers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */