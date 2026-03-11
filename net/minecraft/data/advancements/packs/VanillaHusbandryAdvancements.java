/*     */ package net.minecraft.data.advancements.packs;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementHolder;
/*     */ import net.minecraft.advancements.AdvancementRequirements;
/*     */ import net.minecraft.advancements.AdvancementRewards;
/*     */ import net.minecraft.advancements.AdvancementType;
/*     */ import net.minecraft.advancements.critereon.BeeNestDestroyedTrigger;
/*     */ import net.minecraft.advancements.critereon.BlockPredicate;
/*     */ import net.minecraft.advancements.critereon.BredAnimalsTrigger;
/*     */ import net.minecraft.advancements.critereon.ConsumeItemTrigger;
/*     */ import net.minecraft.advancements.critereon.EffectsChangedTrigger;
/*     */ import net.minecraft.advancements.critereon.EnchantmentPredicate;
/*     */ import net.minecraft.advancements.critereon.EntityFlagsPredicate;
/*     */ import net.minecraft.advancements.critereon.EntityPredicate;
/*     */ import net.minecraft.advancements.critereon.EntitySubPredicate;
/*     */ import net.minecraft.advancements.critereon.FilledBucketTrigger;
/*     */ import net.minecraft.advancements.critereon.FishingRodHookedTrigger;
/*     */ import net.minecraft.advancements.critereon.InventoryChangeTrigger;
/*     */ import net.minecraft.advancements.critereon.ItemPredicate;
/*     */ import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
/*     */ import net.minecraft.advancements.critereon.LocationPredicate;
/*     */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*     */ import net.minecraft.advancements.critereon.PickedUpItemTrigger;
/*     */ import net.minecraft.advancements.critereon.PlayerInteractTrigger;
/*     */ import net.minecraft.advancements.critereon.StartRidingTrigger;
/*     */ import net.minecraft.advancements.critereon.TameAnimalTrigger;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.data.advancements.AdvancementSubProvider;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.animal.CatVariant;
/*     */ import net.minecraft.world.entity.animal.FrogVariant;
/*     */ import net.minecraft.world.item.HoneycombItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VanillaHusbandryAdvancements
/*     */   implements AdvancementSubProvider
/*     */ {
/*  60 */   public static final List<EntityType<?>> BREEDABLE_ANIMALS = List.of((EntityType<?>[])new EntityType[] { EntityType.HORSE, EntityType.DONKEY, EntityType.MULE, EntityType.SHEEP, EntityType.COW, EntityType.MOOSHROOM, EntityType.PIG, EntityType.CHICKEN, EntityType.WOLF, EntityType.OCELOT, EntityType.RABBIT, EntityType.LLAMA, EntityType.CAT, EntityType.PANDA, EntityType.FOX, EntityType.BEE, EntityType.HOGLIN, EntityType.STRIDER, EntityType.GOAT, EntityType.AXOLOTL, EntityType.CAMEL });
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
/*     */ 
/*     */   
/*  85 */   public static final List<EntityType<?>> INDIRECTLY_BREEDABLE_ANIMALS = List.of(EntityType.TURTLE, EntityType.FROG, EntityType.SNIFFER);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   private static final Item[] FISH = new Item[] { Items.COD, Items.TROPICAL_FISH, Items.PUFFERFISH, Items.SALMON };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   private static final Item[] FISH_BUCKETS = new Item[] { Items.COD_BUCKET, Items.TROPICAL_FISH_BUCKET, Items.PUFFERFISH_BUCKET, Items.SALMON_BUCKET };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   private static final Item[] EDIBLE_ITEMS = new Item[] { Items.APPLE, Items.MUSHROOM_STEW, Items.BREAD, Items.PORKCHOP, Items.COOKED_PORKCHOP, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH, Items.COOKED_COD, Items.COOKED_SALMON, Items.COOKIE, Items.MELON_SLICE, Items.BEEF, Items.COOKED_BEEF, Items.CHICKEN, Items.COOKED_CHICKEN, Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.CARROT, Items.POTATO, Items.BAKED_POTATO, Items.POISONOUS_POTATO, Items.GOLDEN_CARROT, Items.PUMPKIN_PIE, Items.RABBIT, Items.COOKED_RABBIT, Items.RABBIT_STEW, Items.MUTTON, Items.COOKED_MUTTON, Items.CHORUS_FRUIT, Items.BEETROOT, Items.BEETROOT_SOUP, Items.DRIED_KELP, Items.SUSPICIOUS_STEW, Items.SWEET_BERRIES, Items.HONEY_BOTTLE, Items.GLOW_BERRIES };
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
/* 148 */   private static final Item[] WAX_SCRAPING_TOOLS = new Item[] { Items.WOODEN_AXE, Items.GOLDEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE };
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
/*     */   public void generate(HolderLookup.Provider $$0, Consumer<AdvancementHolder> $$1) {
/* 162 */     AdvancementHolder $$2 = Advancement.Builder.advancement().display((ItemLike)Blocks.HAY_BLOCK, (Component)Component.translatable("advancements.husbandry.root.title"), (Component)Component.translatable("advancements.husbandry.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/husbandry.png"), AdvancementType.TASK, false, false, false).addCriterion("consumed_item", ConsumeItemTrigger.TriggerInstance.usedItem()).save($$1, "husbandry/root");
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
/* 175 */     AdvancementHolder $$3 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.WHEAT, (Component)Component.translatable("advancements.husbandry.plant_seed.title"), (Component)Component.translatable("advancements.husbandry.plant_seed.description"), null, AdvancementType.TASK, true, true, false).requirements(AdvancementRequirements.Strategy.OR).addCriterion("wheat", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(Blocks.WHEAT)).addCriterion("pumpkin_stem", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(Blocks.PUMPKIN_STEM)).addCriterion("melon_stem", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(Blocks.MELON_STEM)).addCriterion("beetroots", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(Blocks.BEETROOTS)).addCriterion("nether_wart", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(Blocks.NETHER_WART)).addCriterion("torchflower", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(Blocks.TORCHFLOWER_CROP)).addCriterion("pitcher_pod", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(Blocks.PITCHER_CROP)).save($$1, "husbandry/plant_seed");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 182 */     AdvancementHolder $$4 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.WHEAT, (Component)Component.translatable("advancements.husbandry.breed_an_animal.title"), (Component)Component.translatable("advancements.husbandry.breed_an_animal.description"), null, AdvancementType.TASK, true, true, false).requirements(AdvancementRequirements.Strategy.OR).addCriterion("bred", BredAnimalsTrigger.TriggerInstance.bredAnimals()).save($$1, "husbandry/breed_an_animal");
/*     */     
/* 184 */     createBreedAllAnimalsAdvancement($$4, $$1, BREEDABLE_ANIMALS.stream(), INDIRECTLY_BREEDABLE_ANIMALS.stream());
/*     */     
/* 186 */     addFood(Advancement.Builder.advancement())
/* 187 */       .parent($$3)
/* 188 */       .display((ItemLike)Items.APPLE, (Component)Component.translatable("advancements.husbandry.balanced_diet.title"), (Component)Component.translatable("advancements.husbandry.balanced_diet.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 189 */       .rewards(AdvancementRewards.Builder.experience(100))
/* 190 */       .save($$1, "husbandry/balanced_diet");
/*     */     
/* 192 */     Advancement.Builder.advancement()
/* 193 */       .parent($$3)
/* 194 */       .display((ItemLike)Items.NETHERITE_HOE, (Component)Component.translatable("advancements.husbandry.netherite_hoe.title"), (Component)Component.translatable("advancements.husbandry.netherite_hoe.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 195 */       .rewards(AdvancementRewards.Builder.experience(100))
/* 196 */       .addCriterion("netherite_hoe", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.NETHERITE_HOE
/* 197 */           })).save($$1, "husbandry/obtain_netherite_hoe");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     AdvancementHolder $$5 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.LEAD, (Component)Component.translatable("advancements.husbandry.tame_an_animal.title"), (Component)Component.translatable("advancements.husbandry.tame_an_animal.description"), null, AdvancementType.TASK, true, true, false).addCriterion("tamed_animal", TameAnimalTrigger.TriggerInstance.tamedAnimal()).save($$1, "husbandry/tame_an_animal");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     AdvancementHolder $$6 = addFish(Advancement.Builder.advancement()).parent($$2).requirements(AdvancementRequirements.Strategy.OR).display((ItemLike)Items.FISHING_ROD, (Component)Component.translatable("advancements.husbandry.fishy_business.title"), (Component)Component.translatable("advancements.husbandry.fishy_business.description"), null, AdvancementType.TASK, true, true, false).save($$1, "husbandry/fishy_business");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     AdvancementHolder $$7 = addFishBuckets(Advancement.Builder.advancement()).parent($$6).requirements(AdvancementRequirements.Strategy.OR).display((ItemLike)Items.PUFFERFISH_BUCKET, (Component)Component.translatable("advancements.husbandry.tactical_fishing.title"), (Component)Component.translatable("advancements.husbandry.tactical_fishing.description"), null, AdvancementType.TASK, true, true, false).save($$1, "husbandry/tactical_fishing");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     AdvancementHolder $$8 = Advancement.Builder.advancement().parent($$7).requirements(AdvancementRequirements.Strategy.OR).addCriterion(BuiltInRegistries.ITEM.getKey(Items.AXOLOTL_BUCKET).getPath(), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.AXOLOTL_BUCKET }))).display((ItemLike)Items.AXOLOTL_BUCKET, (Component)Component.translatable("advancements.husbandry.axolotl_in_a_bucket.title"), (Component)Component.translatable("advancements.husbandry.axolotl_in_a_bucket.description"), null, AdvancementType.TASK, true, true, false).save($$1, "husbandry/axolotl_in_a_bucket");
/*     */     
/* 224 */     Advancement.Builder.advancement()
/* 225 */       .parent($$8)
/* 226 */       .addCriterion("kill_axolotl_target", EffectsChangedTrigger.TriggerInstance.gotEffectsFrom(EntityPredicate.Builder.entity().of(EntityType.AXOLOTL)))
/* 227 */       .display((ItemLike)Items.TROPICAL_FISH_BUCKET, (Component)Component.translatable("advancements.husbandry.kill_axolotl_target.title"), (Component)Component.translatable("advancements.husbandry.kill_axolotl_target.description"), null, AdvancementType.TASK, true, true, false)
/* 228 */       .save($$1, "husbandry/kill_axolotl_target");
/*     */     
/* 230 */     addCatVariants(Advancement.Builder.advancement())
/* 231 */       .parent($$5)
/* 232 */       .display((ItemLike)Items.COD, (Component)Component.translatable("advancements.husbandry.complete_catalogue.title"), (Component)Component.translatable("advancements.husbandry.complete_catalogue.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 233 */       .rewards(AdvancementRewards.Builder.experience(50))
/* 234 */       .save($$1, "husbandry/complete_catalogue");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     AdvancementHolder $$9 = Advancement.Builder.advancement().parent($$2).addCriterion("safely_harvest_honey", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(BlockTags.BEEHIVES)).setSmokey(true), ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.GLASS_BOTTLE }))).display((ItemLike)Items.HONEY_BOTTLE, (Component)Component.translatable("advancements.husbandry.safely_harvest_honey.title"), (Component)Component.translatable("advancements.husbandry.safely_harvest_honey.description"), null, AdvancementType.TASK, true, true, false).save($$1, "husbandry/safely_harvest_honey");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     AdvancementHolder $$10 = Advancement.Builder.advancement().parent($$9).display((ItemLike)Items.HONEYCOMB, (Component)Component.translatable("advancements.husbandry.wax_on.title"), (Component)Component.translatable("advancements.husbandry.wax_on.description"), null, AdvancementType.TASK, true, true, false).addCriterion("wax_on", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(((BiMap)HoneycombItem.WAXABLES.get()).keySet())), ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.HONEYCOMB }))).save($$1, "husbandry/wax_on");
/*     */     
/* 248 */     Advancement.Builder.advancement()
/* 249 */       .parent($$10)
/* 250 */       .display((ItemLike)Items.STONE_AXE, (Component)Component.translatable("advancements.husbandry.wax_off.title"), (Component)Component.translatable("advancements.husbandry.wax_off.description"), null, AdvancementType.TASK, true, true, false)
/* 251 */       .addCriterion("wax_off", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(((BiMap)HoneycombItem.WAX_OFF_BY_BLOCK.get()).keySet())), ItemPredicate.Builder.item().of((ItemLike[])WAX_SCRAPING_TOOLS)))
/* 252 */       .save($$1, "husbandry/wax_off");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     AdvancementHolder $$11 = Advancement.Builder.advancement().parent($$2).addCriterion(BuiltInRegistries.ITEM.getKey(Items.TADPOLE_BUCKET).getPath(), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.TADPOLE_BUCKET }))).display((ItemLike)Items.TADPOLE_BUCKET, (Component)Component.translatable("advancements.husbandry.tadpole_in_a_bucket.title"), (Component)Component.translatable("advancements.husbandry.tadpole_in_a_bucket.description"), null, AdvancementType.TASK, true, true, false).save($$1, "husbandry/tadpole_in_a_bucket");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 263 */     AdvancementHolder $$12 = addLeashedFrogVariants(Advancement.Builder.advancement()).parent($$11).display((ItemLike)Items.LEAD, (Component)Component.translatable("advancements.husbandry.leash_all_frog_variants.title"), (Component)Component.translatable("advancements.husbandry.leash_all_frog_variants.description"), null, AdvancementType.TASK, true, true, false).save($$1, "husbandry/leash_all_frog_variants");
/*     */     
/* 265 */     Advancement.Builder.advancement()
/* 266 */       .parent($$12)
/* 267 */       .display((ItemLike)Items.VERDANT_FROGLIGHT, (Component)Component.translatable("advancements.husbandry.froglights.title"), (Component)Component.translatable("advancements.husbandry.froglights.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 268 */       .addCriterion("froglights", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.OCHRE_FROGLIGHT, (ItemLike)Items.PEARLESCENT_FROGLIGHT, (ItemLike)Items.VERDANT_FROGLIGHT
/* 269 */           })).save($$1, "husbandry/froglights");
/*     */     
/* 271 */     Advancement.Builder.advancement()
/* 272 */       .parent($$2)
/* 273 */       .addCriterion("silk_touch_nest", BeeNestDestroyedTrigger.TriggerInstance.destroyedBeeNest(Blocks.BEE_NEST, ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))), MinMaxBounds.Ints.exactly(3)))
/* 274 */       .display((ItemLike)Blocks.BEE_NEST, (Component)Component.translatable("advancements.husbandry.silk_touch_nest.title"), (Component)Component.translatable("advancements.husbandry.silk_touch_nest.description"), null, AdvancementType.TASK, true, true, false)
/* 275 */       .save($$1, "husbandry/silk_touch_nest");
/*     */     
/* 277 */     Advancement.Builder.advancement()
/* 278 */       .parent($$2)
/* 279 */       .display((ItemLike)Items.OAK_BOAT, (Component)Component.translatable("advancements.husbandry.ride_a_boat_with_a_goat.title"), (Component)Component.translatable("advancements.husbandry.ride_a_boat_with_a_goat.description"), null, AdvancementType.TASK, true, true, false)
/* 280 */       .addCriterion("ride_a_boat_with_a_goat", 
/* 281 */         StartRidingTrigger.TriggerInstance.playerStartsRiding(
/* 282 */           EntityPredicate.Builder.entity().vehicle(
/* 283 */             EntityPredicate.Builder.entity()
/* 284 */             .of(EntityType.BOAT)
/* 285 */             .passenger(
/* 286 */               EntityPredicate.Builder.entity().of(EntityType.GOAT)))))
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 291 */       .save($$1, "husbandry/ride_a_boat_with_a_goat");
/*     */     
/* 293 */     Advancement.Builder.advancement()
/* 294 */       .parent($$2)
/* 295 */       .display((ItemLike)Items.GLOW_INK_SAC, (Component)Component.translatable("advancements.husbandry.make_a_sign_glow.title"), (Component)Component.translatable("advancements.husbandry.make_a_sign_glow.description"), null, AdvancementType.TASK, true, true, false)
/* 296 */       .addCriterion("make_a_sign_glow", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(BlockTags.ALL_SIGNS)), ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.GLOW_INK_SAC
/* 297 */             }))).save($$1, "husbandry/make_a_sign_glow");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     AdvancementHolder $$13 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.COOKIE, (Component)Component.translatable("advancements.husbandry.allay_deliver_item_to_player.title"), (Component)Component.translatable("advancements.husbandry.allay_deliver_item_to_player.description"), null, AdvancementType.TASK, true, true, true).addCriterion("allay_deliver_item_to_player", PickedUpItemTrigger.TriggerInstance.thrownItemPickedUpByPlayer(Optional.empty(), Optional.empty(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.ALLAY))))).save($$1, "husbandry/allay_deliver_item_to_player");
/*     */     
/* 305 */     Advancement.Builder.advancement()
/* 306 */       .parent($$13)
/* 307 */       .display((ItemLike)Items.NOTE_BLOCK, (Component)Component.translatable("advancements.husbandry.allay_deliver_cake_to_note_block.title"), (Component)Component.translatable("advancements.husbandry.allay_deliver_cake_to_note_block.description"), null, AdvancementType.TASK, true, true, true)
/* 308 */       .addCriterion("allay_deliver_cake_to_note_block", ItemUsedOnLocationTrigger.TriggerInstance.allayDropItemOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(new Block[] { Blocks.NOTE_BLOCK }, )), ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.CAKE
/* 309 */             }))).save($$1, "husbandry/allay_deliver_cake_to_note_block");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 315 */     AdvancementHolder $$14 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.SNIFFER_EGG, (Component)Component.translatable("advancements.husbandry.obtain_sniffer_egg.title"), (Component)Component.translatable("advancements.husbandry.obtain_sniffer_egg.description"), null, AdvancementType.TASK, true, true, true).addCriterion("obtain_sniffer_egg", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.SNIFFER_EGG })).save($$1, "husbandry/obtain_sniffer_egg");
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
/* 326 */     AdvancementHolder $$15 = Advancement.Builder.advancement().parent($$14).display((ItemLike)Items.TORCHFLOWER_SEEDS, (Component)Component.translatable("advancements.husbandry.feed_snifflet.title"), (Component)Component.translatable("advancements.husbandry.feed_snifflet.description"), null, AdvancementType.TASK, true, true, true).addCriterion("feed_snifflet", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item().of(ItemTags.SNIFFER_FOOD), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.SNIFFER).flags(EntityFlagsPredicate.Builder.flags().setIsBaby(Boolean.valueOf(true))))))).save($$1, "husbandry/feed_snifflet");
/*     */     
/* 328 */     Advancement.Builder.advancement()
/* 329 */       .parent($$15)
/* 330 */       .display((ItemLike)Items.PITCHER_POD, (Component)Component.translatable("advancements.husbandry.plant_any_sniffer_seed.title"), (Component)Component.translatable("advancements.husbandry.plant_any_sniffer_seed.description"), null, AdvancementType.TASK, true, true, true)
/* 331 */       .requirements(AdvancementRequirements.Strategy.OR)
/* 332 */       .addCriterion("torchflower", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(Blocks.TORCHFLOWER_CROP))
/* 333 */       .addCriterion("pitcher_pod", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(Blocks.PITCHER_CROP))
/* 334 */       .save($$1, "husbandry/plant_any_sniffer_seed");
/*     */   }
/*     */   
/*     */   public static AdvancementHolder createBreedAllAnimalsAdvancement(AdvancementHolder $$0, Consumer<AdvancementHolder> $$1, Stream<EntityType<?>> $$2, Stream<EntityType<?>> $$3) {
/* 338 */     return addBreedable(Advancement.Builder.advancement(), $$2, $$3)
/* 339 */       .parent($$0)
/* 340 */       .display((ItemLike)Items.GOLDEN_CARROT, (Component)Component.translatable("advancements.husbandry.breed_all_animals.title"), (Component)Component.translatable("advancements.husbandry.breed_all_animals.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 341 */       .rewards(AdvancementRewards.Builder.experience(100))
/* 342 */       .save($$1, "husbandry/bred_all_animals");
/*     */   }
/*     */   
/*     */   private static Advancement.Builder addLeashedFrogVariants(Advancement.Builder $$0) {
/* 346 */     BuiltInRegistries.FROG_VARIANT.holders().forEach($$1 -> $$0.addCriterion($$1.key().location().toString(), PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.LEAD }, ), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.FROG).subPredicate(EntitySubPredicate.variant((FrogVariant)$$1.value())))))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 352 */     return $$0;
/*     */   }
/*     */   
/*     */   private static Advancement.Builder addFood(Advancement.Builder $$0) {
/* 356 */     for (Item $$1 : EDIBLE_ITEMS) {
/* 357 */       $$0.addCriterion(BuiltInRegistries.ITEM.getKey($$1).getPath(), ConsumeItemTrigger.TriggerInstance.usedItem((ItemLike)$$1));
/*     */     }
/* 359 */     return $$0;
/*     */   }
/*     */   
/*     */   private static Advancement.Builder addBreedable(Advancement.Builder $$0, Stream<EntityType<?>> $$1, Stream<EntityType<?>> $$2) {
/* 363 */     $$1.forEach($$1 -> $$0.addCriterion(EntityType.getKey($$1).toString(), BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of($$1))));
/*     */ 
/*     */     
/* 366 */     $$2.forEach($$1 -> $$0.addCriterion(EntityType.getKey($$1).toString(), BredAnimalsTrigger.TriggerInstance.bredAnimals(Optional.of(EntityPredicate.Builder.entity().of($$1).build()), Optional.of(EntityPredicate.Builder.entity().of($$1).build()), Optional.empty())));
/*     */ 
/*     */     
/* 369 */     return $$0;
/*     */   }
/*     */   
/*     */   private static Advancement.Builder addFishBuckets(Advancement.Builder $$0) {
/* 373 */     for (Item $$1 : FISH_BUCKETS) {
/* 374 */       $$0.addCriterion(BuiltInRegistries.ITEM.getKey($$1).getPath(), FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)$$1 })));
/*     */     } 
/* 376 */     return $$0;
/*     */   }
/*     */   
/*     */   private static Advancement.Builder addFish(Advancement.Builder $$0) {
/* 380 */     for (Item $$1 : FISH) {
/* 381 */       $$0.addCriterion(BuiltInRegistries.ITEM.getKey($$1).getPath(), FishingRodHookedTrigger.TriggerInstance.fishedItem(Optional.empty(), Optional.empty(), Optional.of(ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)$$1 }).build())));
/*     */     } 
/* 383 */     return $$0;
/*     */   }
/*     */   
/*     */   private static Advancement.Builder addCatVariants(Advancement.Builder $$0) {
/* 387 */     BuiltInRegistries.CAT_VARIANT.entrySet().stream()
/* 388 */       .sorted(Map.Entry.comparingByKey(Comparator.comparing(ResourceKey::location)))
/* 389 */       .forEach($$1 -> $$0.addCriterion(((ResourceKey)$$1.getKey()).location().toString(), TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().subPredicate(EntitySubPredicate.variant((CatVariant)$$1.getValue())))));
/*     */     
/* 391 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\advancements\packs\VanillaHusbandryAdvancements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */