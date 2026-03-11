/*     */ package net.minecraft.core.particles;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ 
/*     */ public class ParticleTypes
/*     */ {
/*  10 */   public static final SimpleParticleType AMBIENT_ENTITY_EFFECT = register("ambient_entity_effect", false);
/*  11 */   public static final SimpleParticleType ANGRY_VILLAGER = register("angry_villager", false);
/*  12 */   public static final ParticleType<BlockParticleOption> BLOCK = register("block", false, BlockParticleOption.DESERIALIZER, BlockParticleOption::codec);
/*  13 */   public static final ParticleType<BlockParticleOption> BLOCK_MARKER = register("block_marker", true, BlockParticleOption.DESERIALIZER, BlockParticleOption::codec);
/*  14 */   public static final SimpleParticleType BUBBLE = register("bubble", false);
/*  15 */   public static final SimpleParticleType CLOUD = register("cloud", false);
/*  16 */   public static final SimpleParticleType CRIT = register("crit", false);
/*  17 */   public static final SimpleParticleType DAMAGE_INDICATOR = register("damage_indicator", true);
/*  18 */   public static final SimpleParticleType DRAGON_BREATH = register("dragon_breath", false);
/*  19 */   public static final SimpleParticleType DRIPPING_LAVA = register("dripping_lava", false);
/*  20 */   public static final SimpleParticleType FALLING_LAVA = register("falling_lava", false);
/*  21 */   public static final SimpleParticleType LANDING_LAVA = register("landing_lava", false);
/*  22 */   public static final SimpleParticleType DRIPPING_WATER = register("dripping_water", false);
/*  23 */   public static final SimpleParticleType FALLING_WATER = register("falling_water", false);
/*  24 */   public static final ParticleType<DustParticleOptions> DUST = register("dust", false, DustParticleOptions.DESERIALIZER, $$0 -> DustParticleOptions.CODEC);
/*  25 */   public static final ParticleType<DustColorTransitionOptions> DUST_COLOR_TRANSITION = register("dust_color_transition", false, DustColorTransitionOptions.DESERIALIZER, $$0 -> DustColorTransitionOptions.CODEC);
/*  26 */   public static final SimpleParticleType EFFECT = register("effect", false);
/*  27 */   public static final SimpleParticleType ELDER_GUARDIAN = register("elder_guardian", true);
/*  28 */   public static final SimpleParticleType ENCHANTED_HIT = register("enchanted_hit", false);
/*  29 */   public static final SimpleParticleType ENCHANT = register("enchant", false);
/*  30 */   public static final SimpleParticleType END_ROD = register("end_rod", false);
/*  31 */   public static final SimpleParticleType ENTITY_EFFECT = register("entity_effect", false);
/*  32 */   public static final SimpleParticleType EXPLOSION_EMITTER = register("explosion_emitter", true);
/*  33 */   public static final SimpleParticleType EXPLOSION = register("explosion", true);
/*  34 */   public static final SimpleParticleType GUST = register("gust", true);
/*  35 */   public static final SimpleParticleType GUST_EMITTER = register("gust_emitter", true);
/*  36 */   public static final SimpleParticleType SONIC_BOOM = register("sonic_boom", true);
/*  37 */   public static final ParticleType<BlockParticleOption> FALLING_DUST = register("falling_dust", false, BlockParticleOption.DESERIALIZER, BlockParticleOption::codec);
/*  38 */   public static final SimpleParticleType FIREWORK = register("firework", false);
/*  39 */   public static final SimpleParticleType FISHING = register("fishing", false);
/*  40 */   public static final SimpleParticleType FLAME = register("flame", false);
/*  41 */   public static final SimpleParticleType CHERRY_LEAVES = register("cherry_leaves", false);
/*  42 */   public static final SimpleParticleType SCULK_SOUL = register("sculk_soul", false);
/*  43 */   public static final ParticleType<SculkChargeParticleOptions> SCULK_CHARGE = register("sculk_charge", true, SculkChargeParticleOptions.DESERIALIZER, $$0 -> SculkChargeParticleOptions.CODEC);
/*  44 */   public static final SimpleParticleType SCULK_CHARGE_POP = register("sculk_charge_pop", true);
/*  45 */   public static final SimpleParticleType SOUL_FIRE_FLAME = register("soul_fire_flame", false);
/*  46 */   public static final SimpleParticleType SOUL = register("soul", false);
/*  47 */   public static final SimpleParticleType FLASH = register("flash", false);
/*  48 */   public static final SimpleParticleType HAPPY_VILLAGER = register("happy_villager", false);
/*  49 */   public static final SimpleParticleType COMPOSTER = register("composter", false);
/*  50 */   public static final SimpleParticleType HEART = register("heart", false);
/*  51 */   public static final SimpleParticleType INSTANT_EFFECT = register("instant_effect", false);
/*  52 */   public static final ParticleType<ItemParticleOption> ITEM = register("item", false, ItemParticleOption.DESERIALIZER, ItemParticleOption::codec);
/*  53 */   public static final ParticleType<VibrationParticleOption> VIBRATION = register("vibration", true, VibrationParticleOption.DESERIALIZER, $$0 -> VibrationParticleOption.CODEC);
/*     */   
/*  55 */   public static final SimpleParticleType ITEM_SLIME = register("item_slime", false);
/*  56 */   public static final SimpleParticleType ITEM_SNOWBALL = register("item_snowball", false);
/*  57 */   public static final SimpleParticleType LARGE_SMOKE = register("large_smoke", false);
/*  58 */   public static final SimpleParticleType LAVA = register("lava", false);
/*  59 */   public static final SimpleParticleType MYCELIUM = register("mycelium", false);
/*  60 */   public static final SimpleParticleType NOTE = register("note", false);
/*  61 */   public static final SimpleParticleType POOF = register("poof", true);
/*  62 */   public static final SimpleParticleType PORTAL = register("portal", false);
/*  63 */   public static final SimpleParticleType RAIN = register("rain", false);
/*  64 */   public static final SimpleParticleType SMOKE = register("smoke", false);
/*  65 */   public static final SimpleParticleType WHITE_SMOKE = register("white_smoke", false);
/*  66 */   public static final SimpleParticleType SNEEZE = register("sneeze", false);
/*  67 */   public static final SimpleParticleType SPIT = register("spit", true);
/*  68 */   public static final SimpleParticleType SQUID_INK = register("squid_ink", true);
/*  69 */   public static final SimpleParticleType SWEEP_ATTACK = register("sweep_attack", true);
/*  70 */   public static final SimpleParticleType TOTEM_OF_UNDYING = register("totem_of_undying", false);
/*     */   
/*  72 */   public static final SimpleParticleType UNDERWATER = register("underwater", false);
/*  73 */   public static final SimpleParticleType SPLASH = register("splash", false);
/*  74 */   public static final SimpleParticleType WITCH = register("witch", false);
/*  75 */   public static final SimpleParticleType BUBBLE_POP = register("bubble_pop", false);
/*  76 */   public static final SimpleParticleType CURRENT_DOWN = register("current_down", false);
/*  77 */   public static final SimpleParticleType BUBBLE_COLUMN_UP = register("bubble_column_up", false);
/*  78 */   public static final SimpleParticleType NAUTILUS = register("nautilus", false);
/*  79 */   public static final SimpleParticleType DOLPHIN = register("dolphin", false);
/*     */   
/*  81 */   public static final SimpleParticleType CAMPFIRE_COSY_SMOKE = register("campfire_cosy_smoke", true);
/*  82 */   public static final SimpleParticleType CAMPFIRE_SIGNAL_SMOKE = register("campfire_signal_smoke", true);
/*     */   
/*  84 */   public static final SimpleParticleType DRIPPING_HONEY = register("dripping_honey", false);
/*  85 */   public static final SimpleParticleType FALLING_HONEY = register("falling_honey", false);
/*  86 */   public static final SimpleParticleType LANDING_HONEY = register("landing_honey", false);
/*  87 */   public static final SimpleParticleType FALLING_NECTAR = register("falling_nectar", false);
/*  88 */   public static final SimpleParticleType FALLING_SPORE_BLOSSOM = register("falling_spore_blossom", false);
/*     */   
/*  90 */   public static final SimpleParticleType ASH = register("ash", false);
/*  91 */   public static final SimpleParticleType CRIMSON_SPORE = register("crimson_spore", false);
/*  92 */   public static final SimpleParticleType WARPED_SPORE = register("warped_spore", false);
/*  93 */   public static final SimpleParticleType SPORE_BLOSSOM_AIR = register("spore_blossom_air", false);
/*  94 */   public static final SimpleParticleType DRIPPING_OBSIDIAN_TEAR = register("dripping_obsidian_tear", false);
/*  95 */   public static final SimpleParticleType FALLING_OBSIDIAN_TEAR = register("falling_obsidian_tear", false);
/*  96 */   public static final SimpleParticleType LANDING_OBSIDIAN_TEAR = register("landing_obsidian_tear", false);
/*     */   
/*  98 */   public static final SimpleParticleType REVERSE_PORTAL = register("reverse_portal", false);
/*     */   
/* 100 */   public static final SimpleParticleType WHITE_ASH = register("white_ash", false);
/* 101 */   public static final SimpleParticleType SMALL_FLAME = register("small_flame", false);
/* 102 */   public static final SimpleParticleType SNOWFLAKE = register("snowflake", false);
/*     */   
/* 104 */   public static final SimpleParticleType DRIPPING_DRIPSTONE_LAVA = register("dripping_dripstone_lava", false);
/* 105 */   public static final SimpleParticleType FALLING_DRIPSTONE_LAVA = register("falling_dripstone_lava", false);
/*     */   
/* 107 */   public static final SimpleParticleType DRIPPING_DRIPSTONE_WATER = register("dripping_dripstone_water", false);
/* 108 */   public static final SimpleParticleType FALLING_DRIPSTONE_WATER = register("falling_dripstone_water", false);
/* 109 */   public static final SimpleParticleType GLOW_SQUID_INK = register("glow_squid_ink", true);
/* 110 */   public static final SimpleParticleType GLOW = register("glow", true);
/* 111 */   public static final SimpleParticleType WAX_ON = register("wax_on", true);
/* 112 */   public static final SimpleParticleType WAX_OFF = register("wax_off", true);
/* 113 */   public static final SimpleParticleType ELECTRIC_SPARK = register("electric_spark", true);
/* 114 */   public static final SimpleParticleType SCRAPE = register("scrape", true);
/* 115 */   public static final ParticleType<ShriekParticleOption> SHRIEK = register("shriek", false, ShriekParticleOption.DESERIALIZER, $$0 -> ShriekParticleOption.CODEC);
/* 116 */   public static final SimpleParticleType EGG_CRACK = register("egg_crack", false);
/* 117 */   public static final SimpleParticleType DUST_PLUME = register("dust_plume", false);
/* 118 */   public static final SimpleParticleType GUST_DUST = register("gust_dust", false);
/* 119 */   public static final SimpleParticleType TRIAL_SPAWNER_DETECTION = register("trial_spawner_detection", true);
/*     */   
/*     */   private static SimpleParticleType register(String $$0, boolean $$1) {
/* 122 */     return (SimpleParticleType)Registry.register(BuiltInRegistries.PARTICLE_TYPE, $$0, new SimpleParticleType($$1));
/*     */   }
/*     */   
/*     */   private static <T extends ParticleOptions> ParticleType<T> register(String $$0, boolean $$1, ParticleOptions.Deserializer<T> $$2, final Function<ParticleType<T>, Codec<T>> codec) {
/* 126 */     return (ParticleType<T>)Registry.register(BuiltInRegistries.PARTICLE_TYPE, $$0, new ParticleType<T>($$1, $$2)
/*     */         {
/*     */           public Codec<T> codec() {
/* 129 */             return codec.apply(this);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/* 134 */   public static final Codec<ParticleOptions> CODEC = BuiltInRegistries.PARTICLE_TYPE.byNameCodec().dispatch("type", ParticleOptions::getType, ParticleType::codec);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\ParticleTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */