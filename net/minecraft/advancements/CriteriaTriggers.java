/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import net.minecraft.advancements.critereon.BeeNestDestroyedTrigger;
/*     */ import net.minecraft.advancements.critereon.BredAnimalsTrigger;
/*     */ import net.minecraft.advancements.critereon.BrewedPotionTrigger;
/*     */ import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
/*     */ import net.minecraft.advancements.critereon.ChanneledLightningTrigger;
/*     */ import net.minecraft.advancements.critereon.ConstructBeaconTrigger;
/*     */ import net.minecraft.advancements.critereon.ConsumeItemTrigger;
/*     */ import net.minecraft.advancements.critereon.CuredZombieVillagerTrigger;
/*     */ import net.minecraft.advancements.critereon.DistanceTrigger;
/*     */ import net.minecraft.advancements.critereon.EffectsChangedTrigger;
/*     */ import net.minecraft.advancements.critereon.EnchantedItemTrigger;
/*     */ import net.minecraft.advancements.critereon.EnterBlockTrigger;
/*     */ import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
/*     */ import net.minecraft.advancements.critereon.FilledBucketTrigger;
/*     */ import net.minecraft.advancements.critereon.FishingRodHookedTrigger;
/*     */ import net.minecraft.advancements.critereon.ImpossibleTrigger;
/*     */ import net.minecraft.advancements.critereon.InventoryChangeTrigger;
/*     */ import net.minecraft.advancements.critereon.ItemDurabilityTrigger;
/*     */ import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
/*     */ import net.minecraft.advancements.critereon.KilledByCrossbowTrigger;
/*     */ import net.minecraft.advancements.critereon.KilledTrigger;
/*     */ import net.minecraft.advancements.critereon.LevitationTrigger;
/*     */ import net.minecraft.advancements.critereon.LightningStrikeTrigger;
/*     */ import net.minecraft.advancements.critereon.LootTableTrigger;
/*     */ import net.minecraft.advancements.critereon.PickedUpItemTrigger;
/*     */ import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
/*     */ import net.minecraft.advancements.critereon.PlayerInteractTrigger;
/*     */ import net.minecraft.advancements.critereon.PlayerTrigger;
/*     */ import net.minecraft.advancements.critereon.RecipeCraftedTrigger;
/*     */ import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
/*     */ import net.minecraft.advancements.critereon.ShotCrossbowTrigger;
/*     */ import net.minecraft.advancements.critereon.SlideDownBlockTrigger;
/*     */ import net.minecraft.advancements.critereon.StartRidingTrigger;
/*     */ import net.minecraft.advancements.critereon.SummonedEntityTrigger;
/*     */ import net.minecraft.advancements.critereon.TameAnimalTrigger;
/*     */ import net.minecraft.advancements.critereon.TargetBlockTrigger;
/*     */ import net.minecraft.advancements.critereon.TradeTrigger;
/*     */ import net.minecraft.advancements.critereon.UsedEnderEyeTrigger;
/*     */ import net.minecraft.advancements.critereon.UsedTotemTrigger;
/*     */ import net.minecraft.advancements.critereon.UsingItemTrigger;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ 
/*     */ public class CriteriaTriggers {
/*  48 */   public static final Codec<CriterionTrigger<?>> CODEC = BuiltInRegistries.TRIGGER_TYPES.byNameCodec();
/*     */   
/*  50 */   public static final ImpossibleTrigger IMPOSSIBLE = register("impossible", new ImpossibleTrigger());
/*  51 */   public static final KilledTrigger PLAYER_KILLED_ENTITY = register("player_killed_entity", new KilledTrigger());
/*  52 */   public static final KilledTrigger ENTITY_KILLED_PLAYER = register("entity_killed_player", new KilledTrigger());
/*  53 */   public static final EnterBlockTrigger ENTER_BLOCK = register("enter_block", new EnterBlockTrigger());
/*  54 */   public static final InventoryChangeTrigger INVENTORY_CHANGED = register("inventory_changed", new InventoryChangeTrigger());
/*  55 */   public static final RecipeUnlockedTrigger RECIPE_UNLOCKED = register("recipe_unlocked", new RecipeUnlockedTrigger());
/*  56 */   public static final PlayerHurtEntityTrigger PLAYER_HURT_ENTITY = register("player_hurt_entity", new PlayerHurtEntityTrigger());
/*  57 */   public static final EntityHurtPlayerTrigger ENTITY_HURT_PLAYER = register("entity_hurt_player", new EntityHurtPlayerTrigger());
/*  58 */   public static final EnchantedItemTrigger ENCHANTED_ITEM = register("enchanted_item", new EnchantedItemTrigger());
/*  59 */   public static final FilledBucketTrigger FILLED_BUCKET = register("filled_bucket", new FilledBucketTrigger());
/*  60 */   public static final BrewedPotionTrigger BREWED_POTION = register("brewed_potion", new BrewedPotionTrigger());
/*  61 */   public static final ConstructBeaconTrigger CONSTRUCT_BEACON = register("construct_beacon", new ConstructBeaconTrigger());
/*  62 */   public static final UsedEnderEyeTrigger USED_ENDER_EYE = register("used_ender_eye", new UsedEnderEyeTrigger());
/*  63 */   public static final SummonedEntityTrigger SUMMONED_ENTITY = register("summoned_entity", new SummonedEntityTrigger());
/*  64 */   public static final BredAnimalsTrigger BRED_ANIMALS = register("bred_animals", new BredAnimalsTrigger());
/*  65 */   public static final PlayerTrigger LOCATION = register("location", new PlayerTrigger());
/*  66 */   public static final PlayerTrigger SLEPT_IN_BED = register("slept_in_bed", new PlayerTrigger());
/*  67 */   public static final CuredZombieVillagerTrigger CURED_ZOMBIE_VILLAGER = register("cured_zombie_villager", new CuredZombieVillagerTrigger());
/*  68 */   public static final TradeTrigger TRADE = register("villager_trade", new TradeTrigger());
/*  69 */   public static final ItemDurabilityTrigger ITEM_DURABILITY_CHANGED = register("item_durability_changed", new ItemDurabilityTrigger());
/*  70 */   public static final LevitationTrigger LEVITATION = register("levitation", new LevitationTrigger());
/*  71 */   public static final ChangeDimensionTrigger CHANGED_DIMENSION = register("changed_dimension", new ChangeDimensionTrigger());
/*  72 */   public static final PlayerTrigger TICK = register("tick", new PlayerTrigger());
/*  73 */   public static final TameAnimalTrigger TAME_ANIMAL = register("tame_animal", new TameAnimalTrigger());
/*  74 */   public static final ItemUsedOnLocationTrigger PLACED_BLOCK = register("placed_block", new ItemUsedOnLocationTrigger());
/*  75 */   public static final ConsumeItemTrigger CONSUME_ITEM = register("consume_item", new ConsumeItemTrigger());
/*  76 */   public static final EffectsChangedTrigger EFFECTS_CHANGED = register("effects_changed", new EffectsChangedTrigger());
/*  77 */   public static final UsedTotemTrigger USED_TOTEM = register("used_totem", new UsedTotemTrigger());
/*  78 */   public static final DistanceTrigger NETHER_TRAVEL = register("nether_travel", new DistanceTrigger());
/*  79 */   public static final FishingRodHookedTrigger FISHING_ROD_HOOKED = register("fishing_rod_hooked", new FishingRodHookedTrigger());
/*  80 */   public static final ChanneledLightningTrigger CHANNELED_LIGHTNING = register("channeled_lightning", new ChanneledLightningTrigger());
/*  81 */   public static final ShotCrossbowTrigger SHOT_CROSSBOW = register("shot_crossbow", new ShotCrossbowTrigger());
/*  82 */   public static final KilledByCrossbowTrigger KILLED_BY_CROSSBOW = register("killed_by_crossbow", new KilledByCrossbowTrigger());
/*  83 */   public static final PlayerTrigger RAID_WIN = register("hero_of_the_village", new PlayerTrigger());
/*  84 */   public static final PlayerTrigger BAD_OMEN = register("voluntary_exile", new PlayerTrigger());
/*  85 */   public static final SlideDownBlockTrigger HONEY_BLOCK_SLIDE = register("slide_down_block", new SlideDownBlockTrigger());
/*  86 */   public static final BeeNestDestroyedTrigger BEE_NEST_DESTROYED = register("bee_nest_destroyed", new BeeNestDestroyedTrigger());
/*  87 */   public static final TargetBlockTrigger TARGET_BLOCK_HIT = register("target_hit", new TargetBlockTrigger());
/*  88 */   public static final ItemUsedOnLocationTrigger ITEM_USED_ON_BLOCK = register("item_used_on_block", new ItemUsedOnLocationTrigger());
/*  89 */   public static final LootTableTrigger GENERATE_LOOT = register("player_generates_container_loot", new LootTableTrigger());
/*  90 */   public static final PickedUpItemTrigger THROWN_ITEM_PICKED_UP_BY_ENTITY = register("thrown_item_picked_up_by_entity", new PickedUpItemTrigger());
/*  91 */   public static final PickedUpItemTrigger THROWN_ITEM_PICKED_UP_BY_PLAYER = register("thrown_item_picked_up_by_player", new PickedUpItemTrigger());
/*  92 */   public static final PlayerInteractTrigger PLAYER_INTERACTED_WITH_ENTITY = register("player_interacted_with_entity", new PlayerInteractTrigger());
/*  93 */   public static final StartRidingTrigger START_RIDING_TRIGGER = register("started_riding", new StartRidingTrigger());
/*  94 */   public static final LightningStrikeTrigger LIGHTNING_STRIKE = register("lightning_strike", new LightningStrikeTrigger());
/*  95 */   public static final UsingItemTrigger USING_ITEM = register("using_item", new UsingItemTrigger());
/*  96 */   public static final DistanceTrigger FALL_FROM_HEIGHT = register("fall_from_height", new DistanceTrigger());
/*  97 */   public static final DistanceTrigger RIDE_ENTITY_IN_LAVA_TRIGGER = register("ride_entity_in_lava", new DistanceTrigger());
/*  98 */   public static final KilledTrigger KILL_MOB_NEAR_SCULK_CATALYST = register("kill_mob_near_sculk_catalyst", new KilledTrigger());
/*  99 */   public static final ItemUsedOnLocationTrigger ALLAY_DROP_ITEM_ON_BLOCK = register("allay_drop_item_on_block", new ItemUsedOnLocationTrigger());
/* 100 */   public static final PlayerTrigger AVOID_VIBRATION = register("avoid_vibration", new PlayerTrigger());
/* 101 */   public static final RecipeCraftedTrigger RECIPE_CRAFTED = register("recipe_crafted", new RecipeCraftedTrigger());
/*     */   
/*     */   private static <T extends CriterionTrigger<?>> T register(String $$0, T $$1) {
/* 104 */     return (T)Registry.register(BuiltInRegistries.TRIGGER_TYPES, $$0, $$1);
/*     */   }
/*     */   
/*     */   public static CriterionTrigger<?> bootstrap(Registry<CriterionTrigger<?>> $$0) {
/* 108 */     return (CriterionTrigger<?>)IMPOSSIBLE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\CriteriaTriggers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */