/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DecoratedPotPatterns
/*     */ {
/*     */   private static final String BASE_NAME = "decorated_pot_base";
/*  17 */   public static final ResourceKey<String> BASE = create("decorated_pot_base");
/*     */   
/*     */   private static final String BRICK_NAME = "decorated_pot_side";
/*     */   
/*     */   private static final String ANGLER_NAME = "angler_pottery_pattern";
/*     */   
/*     */   private static final String ARCHER_NAME = "archer_pottery_pattern";
/*     */   
/*     */   private static final String ARMS_UP_NAME = "arms_up_pottery_pattern";
/*     */   private static final String BLADE_NAME = "blade_pottery_pattern";
/*     */   private static final String BREWER_NAME = "brewer_pottery_pattern";
/*     */   private static final String BURN_NAME = "burn_pottery_pattern";
/*     */   private static final String DANGER_NAME = "danger_pottery_pattern";
/*     */   private static final String EXPLORER_NAME = "explorer_pottery_pattern";
/*     */   private static final String FRIEND_NAME = "friend_pottery_pattern";
/*     */   private static final String HEART_NAME = "heart_pottery_pattern";
/*     */   private static final String HEARTBREAK_NAME = "heartbreak_pottery_pattern";
/*     */   private static final String HOWL_NAME = "howl_pottery_pattern";
/*     */   private static final String MINER_NAME = "miner_pottery_pattern";
/*     */   private static final String MOURNER_NAME = "mourner_pottery_pattern";
/*     */   private static final String PLENTY_NAME = "plenty_pottery_pattern";
/*     */   private static final String PRIZE_NAME = "prize_pottery_pattern";
/*     */   private static final String SHEAF_NAME = "sheaf_pottery_pattern";
/*     */   private static final String SHELTER_NAME = "shelter_pottery_pattern";
/*     */   private static final String SKULL_NAME = "skull_pottery_pattern";
/*     */   private static final String SNORT_NAME = "snort_pottery_pattern";
/*  43 */   private static final ResourceKey<String> BRICK = create("decorated_pot_side");
/*     */   
/*  45 */   private static final ResourceKey<String> ANGLER = create("angler_pottery_pattern");
/*  46 */   private static final ResourceKey<String> ARCHER = create("archer_pottery_pattern");
/*  47 */   private static final ResourceKey<String> ARMS_UP = create("arms_up_pottery_pattern");
/*  48 */   private static final ResourceKey<String> BLADE = create("blade_pottery_pattern");
/*  49 */   private static final ResourceKey<String> BREWER = create("brewer_pottery_pattern");
/*  50 */   private static final ResourceKey<String> BURN = create("burn_pottery_pattern");
/*  51 */   private static final ResourceKey<String> DANGER = create("danger_pottery_pattern");
/*  52 */   private static final ResourceKey<String> EXPLORER = create("explorer_pottery_pattern");
/*  53 */   private static final ResourceKey<String> FRIEND = create("friend_pottery_pattern");
/*  54 */   private static final ResourceKey<String> HEART = create("heart_pottery_pattern");
/*  55 */   private static final ResourceKey<String> HEARTBREAK = create("heartbreak_pottery_pattern");
/*  56 */   private static final ResourceKey<String> HOWL = create("howl_pottery_pattern");
/*  57 */   private static final ResourceKey<String> MINER = create("miner_pottery_pattern");
/*  58 */   private static final ResourceKey<String> MOURNER = create("mourner_pottery_pattern");
/*  59 */   private static final ResourceKey<String> PLENTY = create("plenty_pottery_pattern");
/*  60 */   private static final ResourceKey<String> PRIZE = create("prize_pottery_pattern");
/*  61 */   private static final ResourceKey<String> SHEAF = create("sheaf_pottery_pattern");
/*  62 */   private static final ResourceKey<String> SHELTER = create("shelter_pottery_pattern");
/*  63 */   private static final ResourceKey<String> SKULL = create("skull_pottery_pattern");
/*  64 */   private static final ResourceKey<String> SNORT = create("snort_pottery_pattern");
/*     */   
/*  66 */   private static final Map<Item, ResourceKey<String>> ITEM_TO_POT_TEXTURE = Map.ofEntries((Map.Entry<? extends Item, ? extends ResourceKey<String>>[])new Map.Entry[] { 
/*  67 */         Map.entry(Items.BRICK, BRICK), 
/*     */         
/*  69 */         Map.entry(Items.ANGLER_POTTERY_SHERD, ANGLER), 
/*  70 */         Map.entry(Items.ARCHER_POTTERY_SHERD, ARCHER), 
/*  71 */         Map.entry(Items.ARMS_UP_POTTERY_SHERD, ARMS_UP), 
/*  72 */         Map.entry(Items.BLADE_POTTERY_SHERD, BLADE), 
/*  73 */         Map.entry(Items.BREWER_POTTERY_SHERD, BREWER), 
/*  74 */         Map.entry(Items.BURN_POTTERY_SHERD, BURN), 
/*  75 */         Map.entry(Items.DANGER_POTTERY_SHERD, DANGER), 
/*  76 */         Map.entry(Items.EXPLORER_POTTERY_SHERD, EXPLORER), 
/*  77 */         Map.entry(Items.FRIEND_POTTERY_SHERD, FRIEND), 
/*  78 */         Map.entry(Items.HEART_POTTERY_SHERD, HEART), 
/*  79 */         Map.entry(Items.HEARTBREAK_POTTERY_SHERD, HEARTBREAK), 
/*  80 */         Map.entry(Items.HOWL_POTTERY_SHERD, HOWL), 
/*  81 */         Map.entry(Items.MINER_POTTERY_SHERD, MINER), 
/*  82 */         Map.entry(Items.MOURNER_POTTERY_SHERD, MOURNER), 
/*  83 */         Map.entry(Items.PLENTY_POTTERY_SHERD, PLENTY), 
/*  84 */         Map.entry(Items.PRIZE_POTTERY_SHERD, PRIZE), 
/*  85 */         Map.entry(Items.SHEAF_POTTERY_SHERD, SHEAF), 
/*  86 */         Map.entry(Items.SHELTER_POTTERY_SHERD, SHELTER), 
/*  87 */         Map.entry(Items.SKULL_POTTERY_SHERD, SKULL), 
/*  88 */         Map.entry(Items.SNORT_POTTERY_SHERD, SNORT) });
/*     */ 
/*     */   
/*     */   private static ResourceKey<String> create(String $$0) {
/*  92 */     return ResourceKey.create(Registries.DECORATED_POT_PATTERNS, new ResourceLocation($$0));
/*     */   }
/*     */   
/*     */   public static ResourceLocation location(ResourceKey<String> $$0) {
/*  96 */     return $$0.location().withPrefix("entity/decorated_pot/");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static ResourceKey<String> getResourceKey(Item $$0) {
/* 101 */     return ITEM_TO_POT_TEXTURE.get($$0);
/*     */   }
/*     */   
/*     */   public static String bootstrap(Registry<String> $$0) {
/* 105 */     Registry.register($$0, BRICK, "decorated_pot_side");
/*     */     
/* 107 */     Registry.register($$0, ANGLER, "angler_pottery_pattern");
/* 108 */     Registry.register($$0, ARCHER, "archer_pottery_pattern");
/* 109 */     Registry.register($$0, ARMS_UP, "arms_up_pottery_pattern");
/* 110 */     Registry.register($$0, BLADE, "blade_pottery_pattern");
/* 111 */     Registry.register($$0, BREWER, "brewer_pottery_pattern");
/* 112 */     Registry.register($$0, BURN, "burn_pottery_pattern");
/* 113 */     Registry.register($$0, DANGER, "danger_pottery_pattern");
/* 114 */     Registry.register($$0, EXPLORER, "explorer_pottery_pattern");
/* 115 */     Registry.register($$0, FRIEND, "friend_pottery_pattern");
/* 116 */     Registry.register($$0, HEART, "heart_pottery_pattern");
/* 117 */     Registry.register($$0, HEARTBREAK, "heartbreak_pottery_pattern");
/* 118 */     Registry.register($$0, HOWL, "howl_pottery_pattern");
/* 119 */     Registry.register($$0, MINER, "miner_pottery_pattern");
/* 120 */     Registry.register($$0, MOURNER, "mourner_pottery_pattern");
/* 121 */     Registry.register($$0, PLENTY, "plenty_pottery_pattern");
/* 122 */     Registry.register($$0, PRIZE, "prize_pottery_pattern");
/* 123 */     Registry.register($$0, SHEAF, "sheaf_pottery_pattern");
/* 124 */     Registry.register($$0, SHELTER, "shelter_pottery_pattern");
/* 125 */     Registry.register($$0, SKULL, "skull_pottery_pattern");
/* 126 */     Registry.register($$0, SNORT, "snort_pottery_pattern");
/*     */     
/* 128 */     return (String)Registry.register($$0, BASE, "decorated_pot_base");
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\DecoratedPotPatterns.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */