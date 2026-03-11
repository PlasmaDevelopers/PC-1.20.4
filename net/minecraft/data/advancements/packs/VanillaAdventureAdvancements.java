/*     */ package net.minecraft.data.advancements.packs;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementHolder;
/*     */ import net.minecraft.advancements.AdvancementRequirements;
/*     */ import net.minecraft.advancements.AdvancementRewards;
/*     */ import net.minecraft.advancements.AdvancementType;
/*     */ import net.minecraft.advancements.Criterion;
/*     */ import net.minecraft.advancements.critereon.BlockPredicate;
/*     */ import net.minecraft.advancements.critereon.ChanneledLightningTrigger;
/*     */ import net.minecraft.advancements.critereon.DamagePredicate;
/*     */ import net.minecraft.advancements.critereon.DamageSourcePredicate;
/*     */ import net.minecraft.advancements.critereon.DistancePredicate;
/*     */ import net.minecraft.advancements.critereon.DistanceTrigger;
/*     */ import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
/*     */ import net.minecraft.advancements.critereon.EntityPredicate;
/*     */ import net.minecraft.advancements.critereon.EntitySubPredicate;
/*     */ import net.minecraft.advancements.critereon.InventoryChangeTrigger;
/*     */ import net.minecraft.advancements.critereon.ItemPredicate;
/*     */ import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
/*     */ import net.minecraft.advancements.critereon.KilledByCrossbowTrigger;
/*     */ import net.minecraft.advancements.critereon.KilledTrigger;
/*     */ import net.minecraft.advancements.critereon.LightningBoltPredicate;
/*     */ import net.minecraft.advancements.critereon.LightningStrikeTrigger;
/*     */ import net.minecraft.advancements.critereon.LocationPredicate;
/*     */ import net.minecraft.advancements.critereon.LootTableTrigger;
/*     */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*     */ import net.minecraft.advancements.critereon.PlayerHurtEntityTrigger;
/*     */ import net.minecraft.advancements.critereon.PlayerPredicate;
/*     */ import net.minecraft.advancements.critereon.PlayerTrigger;
/*     */ import net.minecraft.advancements.critereon.RecipeCraftedTrigger;
/*     */ import net.minecraft.advancements.critereon.ShotCrossbowTrigger;
/*     */ import net.minecraft.advancements.critereon.SlideDownBlockTrigger;
/*     */ import net.minecraft.advancements.critereon.StatePropertiesPredicate;
/*     */ import net.minecraft.advancements.critereon.SummonedEntityTrigger;
/*     */ import net.minecraft.advancements.critereon.TagPredicate;
/*     */ import net.minecraft.advancements.critereon.TargetBlockTrigger;
/*     */ import net.minecraft.advancements.critereon.TradeTrigger;
/*     */ import net.minecraft.advancements.critereon.UsedTotemTrigger;
/*     */ import net.minecraft.advancements.critereon.UsingItemTrigger;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.data.advancements.AdvancementSubProvider;
/*     */ import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.tags.EntityTypeTags;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.raid.Raid;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.ComparatorBlock;
/*     */ import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
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
/*     */ public class VanillaAdventureAdvancements
/*     */   implements AdvancementSubProvider
/*     */ {
/*     */   private static final int DISTANCE_FROM_BOTTOM_TO_TOP = 384;
/*     */   private static final int Y_COORDINATE_AT_TOP = 320;
/*     */   private static final int Y_COORDINATE_AT_BOTTOM = -64;
/*     */   private static final int BEDROCK_THICKNESS = 5;
/* 110 */   protected static final List<EntityType<?>> MOBS_TO_KILL = Arrays.asList((EntityType<?>[])new EntityType[] { EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CREEPER, EntityType.DROWNED, EntityType.ELDER_GUARDIAN, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER, EntityType.GHAST, EntityType.GUARDIAN, EntityType.HOGLIN, EntityType.HUSK, EntityType.MAGMA_CUBE, EntityType.PHANTOM, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.PILLAGER, EntityType.RAVAGER, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SPIDER, EntityType.STRAY, EntityType.VEX, EntityType.VINDICATOR, EntityType.WITCH, EntityType.WITHER_SKELETON, EntityType.WITHER, EntityType.ZOGLIN, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIE, EntityType.ZOMBIFIED_PIGLIN });
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
/*     */   private static Criterion<LightningStrikeTrigger.TriggerInstance> fireCountAndBystander(MinMaxBounds.Ints $$0, Optional<EntityPredicate> $$1) {
/* 148 */     return LightningStrikeTrigger.TriggerInstance.lightningStrike(
/* 149 */         Optional.of(EntityPredicate.Builder.entity()
/* 150 */           .distance(DistancePredicate.absolute(MinMaxBounds.Doubles.atMost(30.0D)))
/* 151 */           .subPredicate((EntitySubPredicate)LightningBoltPredicate.blockSetOnFire($$0))
/* 152 */           .build()), $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Criterion<UsingItemTrigger.TriggerInstance> lookAtThroughItem(EntityType<?> $$0, Item $$1) {
/* 158 */     return UsingItemTrigger.TriggerInstance.lookingAt(
/* 159 */         EntityPredicate.Builder.entity().subPredicate(
/* 160 */           (EntitySubPredicate)PlayerPredicate.Builder.player().setLookingAt(
/* 161 */             EntityPredicate.Builder.entity().of($$0))
/* 162 */           .build()), 
/*     */         
/* 164 */         ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)$$1 }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generate(HolderLookup.Provider $$0, Consumer<AdvancementHolder> $$1) {
/* 175 */     AdvancementHolder $$2 = Advancement.Builder.advancement().display((ItemLike)Items.MAP, (Component)Component.translatable("advancements.adventure.root.title"), (Component)Component.translatable("advancements.adventure.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"), AdvancementType.TASK, false, false, false).requirements(AdvancementRequirements.Strategy.OR).addCriterion("killed_something", KilledTrigger.TriggerInstance.playerKilledEntity()).addCriterion("killed_by_something", KilledTrigger.TriggerInstance.entityKilledPlayer()).save($$1, "adventure/root");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     AdvancementHolder $$3 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Blocks.RED_BED, (Component)Component.translatable("advancements.adventure.sleep_in_bed.title"), (Component)Component.translatable("advancements.adventure.sleep_in_bed.description"), null, AdvancementType.TASK, true, true, false).addCriterion("slept_in_bed", PlayerTrigger.TriggerInstance.sleptInBed()).save($$1, "adventure/sleep_in_bed");
/*     */     
/* 183 */     createAdventuringTime($$1, $$3, MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     AdvancementHolder $$4 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.EMERALD, (Component)Component.translatable("advancements.adventure.trade.title"), (Component)Component.translatable("advancements.adventure.trade.description"), null, AdvancementType.TASK, true, true, false).addCriterion("traded", TradeTrigger.TriggerInstance.tradedWithVillager()).save($$1, "adventure/trade");
/*     */     
/* 191 */     Advancement.Builder.advancement()
/* 192 */       .parent($$4)
/* 193 */       .display((ItemLike)Items.EMERALD, (Component)Component.translatable("advancements.adventure.trade_at_world_height.title"), (Component)Component.translatable("advancements.adventure.trade_at_world_height.description"), null, AdvancementType.TASK, true, true, false)
/* 194 */       .addCriterion("trade_at_world_height", TradeTrigger.TriggerInstance.tradedWithVillager(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.atYLocation(MinMaxBounds.Doubles.atLeast(319.0D)))))
/* 195 */       .save($$1, "adventure/trade_at_world_height");
/*     */     
/* 197 */     AdvancementHolder $$5 = createMonsterHunterAdvancement($$2, $$1, MOBS_TO_KILL);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     AdvancementHolder $$6 = Advancement.Builder.advancement().parent($$5).display((ItemLike)Items.BOW, (Component)Component.translatable("advancements.adventure.shoot_arrow.title"), (Component)Component.translatable("advancements.adventure.shoot_arrow.description"), null, AdvancementType.TASK, true, true, false).addCriterion("shot_arrow", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntityWithDamage(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE)).direct(EntityPredicate.Builder.entity().of(EntityTypeTags.ARROWS))))).save($$1, "adventure/shoot_arrow");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     AdvancementHolder $$7 = Advancement.Builder.advancement().parent($$5).display((ItemLike)Items.TRIDENT, (Component)Component.translatable("advancements.adventure.throw_trident.title"), (Component)Component.translatable("advancements.adventure.throw_trident.description"), null, AdvancementType.TASK, true, true, false).addCriterion("shot_trident", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntityWithDamage(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE)).direct(EntityPredicate.Builder.entity().of(EntityType.TRIDENT))))).save($$1, "adventure/throw_trident");
/*     */     
/* 211 */     Advancement.Builder.advancement()
/* 212 */       .parent($$7)
/* 213 */       .display((ItemLike)Items.TRIDENT, (Component)Component.translatable("advancements.adventure.very_very_frightening.title"), (Component)Component.translatable("advancements.adventure.very_very_frightening.description"), null, AdvancementType.TASK, true, true, false)
/* 214 */       .addCriterion("struck_villager", ChanneledLightningTrigger.TriggerInstance.channeledLightning(new EntityPredicate.Builder[] { EntityPredicate.Builder.entity().of(EntityType.VILLAGER)
/* 215 */           })).save($$1, "adventure/very_very_frightening");
/*     */     
/* 217 */     Advancement.Builder.advancement()
/* 218 */       .parent($$4)
/* 219 */       .display((ItemLike)Blocks.CARVED_PUMPKIN, (Component)Component.translatable("advancements.adventure.summon_iron_golem.title"), (Component)Component.translatable("advancements.adventure.summon_iron_golem.description"), null, AdvancementType.GOAL, true, true, false)
/* 220 */       .addCriterion("summoned_golem", SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(EntityType.IRON_GOLEM)))
/* 221 */       .save($$1, "adventure/summon_iron_golem");
/*     */     
/* 223 */     Advancement.Builder.advancement()
/* 224 */       .parent($$6)
/* 225 */       .display((ItemLike)Items.ARROW, (Component)Component.translatable("advancements.adventure.sniper_duel.title"), (Component)Component.translatable("advancements.adventure.sniper_duel.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 226 */       .rewards(AdvancementRewards.Builder.experience(50))
/* 227 */       .addCriterion("killed_skeleton", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityType.SKELETON).distance(DistancePredicate.horizontal(MinMaxBounds.Doubles.atLeast(50.0D))), DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE))))
/* 228 */       .save($$1, "adventure/sniper_duel");
/*     */     
/* 230 */     Advancement.Builder.advancement()
/* 231 */       .parent($$5)
/* 232 */       .display((ItemLike)Items.TOTEM_OF_UNDYING, (Component)Component.translatable("advancements.adventure.totem_of_undying.title"), (Component)Component.translatable("advancements.adventure.totem_of_undying.description"), null, AdvancementType.GOAL, true, true, false)
/* 233 */       .addCriterion("used_totem", UsedTotemTrigger.TriggerInstance.usedTotem((ItemLike)Items.TOTEM_OF_UNDYING))
/* 234 */       .save($$1, "adventure/totem_of_undying");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 240 */     AdvancementHolder $$8 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.CROSSBOW, (Component)Component.translatable("advancements.adventure.ol_betsy.title"), (Component)Component.translatable("advancements.adventure.ol_betsy.description"), null, AdvancementType.TASK, true, true, false).addCriterion("shot_crossbow", ShotCrossbowTrigger.TriggerInstance.shotCrossbow((ItemLike)Items.CROSSBOW)).save($$1, "adventure/ol_betsy");
/*     */     
/* 242 */     Advancement.Builder.advancement()
/* 243 */       .parent($$8)
/* 244 */       .display((ItemLike)Items.CROSSBOW, (Component)Component.translatable("advancements.adventure.whos_the_pillager_now.title"), (Component)Component.translatable("advancements.adventure.whos_the_pillager_now.description"), null, AdvancementType.TASK, true, true, false)
/* 245 */       .addCriterion("kill_pillager", KilledByCrossbowTrigger.TriggerInstance.crossbowKilled(new EntityPredicate.Builder[] { EntityPredicate.Builder.entity().of(EntityType.PILLAGER)
/* 246 */           })).save($$1, "adventure/whos_the_pillager_now");
/*     */     
/* 248 */     Advancement.Builder.advancement()
/* 249 */       .parent($$8)
/* 250 */       .display((ItemLike)Items.CROSSBOW, (Component)Component.translatable("advancements.adventure.two_birds_one_arrow.title"), (Component)Component.translatable("advancements.adventure.two_birds_one_arrow.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 251 */       .rewards(AdvancementRewards.Builder.experience(65))
/* 252 */       .addCriterion("two_birds", KilledByCrossbowTrigger.TriggerInstance.crossbowKilled(new EntityPredicate.Builder[] { EntityPredicate.Builder.entity().of(EntityType.PHANTOM), EntityPredicate.Builder.entity().of(EntityType.PHANTOM)
/* 253 */           })).save($$1, "adventure/two_birds_one_arrow");
/*     */     
/* 255 */     Advancement.Builder.advancement()
/* 256 */       .parent($$8)
/* 257 */       .display((ItemLike)Items.CROSSBOW, (Component)Component.translatable("advancements.adventure.arbalistic.title"), (Component)Component.translatable("advancements.adventure.arbalistic.description"), null, AdvancementType.CHALLENGE, true, true, true)
/* 258 */       .rewards(AdvancementRewards.Builder.experience(85))
/* 259 */       .addCriterion("arbalistic", KilledByCrossbowTrigger.TriggerInstance.crossbowKilled(MinMaxBounds.Ints.exactly(5)))
/* 260 */       .save($$1, "adventure/arbalistic");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 266 */     AdvancementHolder $$9 = Advancement.Builder.advancement().parent($$2).display(Raid.getLeaderBannerInstance(), (Component)Component.translatable("advancements.adventure.voluntary_exile.title"), (Component)Component.translatable("advancements.adventure.voluntary_exile.description"), null, AdvancementType.TASK, true, true, true).addCriterion("voluntary_exile", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityTypeTags.RAIDERS).equipment(EntityEquipmentPredicate.CAPTAIN))).save($$1, "adventure/voluntary_exile");
/*     */     
/* 268 */     Advancement.Builder.advancement()
/* 269 */       .parent($$9)
/* 270 */       .display(Raid.getLeaderBannerInstance(), (Component)Component.translatable("advancements.adventure.hero_of_the_village.title"), (Component)Component.translatable("advancements.adventure.hero_of_the_village.description"), null, AdvancementType.CHALLENGE, true, true, true)
/* 271 */       .rewards(AdvancementRewards.Builder.experience(100))
/* 272 */       .addCriterion("hero_of_the_village", PlayerTrigger.TriggerInstance.raidWon())
/* 273 */       .save($$1, "adventure/hero_of_the_village");
/*     */     
/* 275 */     Advancement.Builder.advancement()
/* 276 */       .parent($$2)
/* 277 */       .display((ItemLike)Blocks.HONEY_BLOCK.asItem(), (Component)Component.translatable("advancements.adventure.honey_block_slide.title"), (Component)Component.translatable("advancements.adventure.honey_block_slide.description"), null, AdvancementType.TASK, true, true, false)
/* 278 */       .addCriterion("honey_block_slide", SlideDownBlockTrigger.TriggerInstance.slidesDownBlock(Blocks.HONEY_BLOCK))
/* 279 */       .save($$1, "adventure/honey_block_slide");
/*     */     
/* 281 */     Advancement.Builder.advancement()
/* 282 */       .parent($$6)
/* 283 */       .display((ItemLike)Blocks.TARGET.asItem(), (Component)Component.translatable("advancements.adventure.bullseye.title"), (Component)Component.translatable("advancements.adventure.bullseye.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 284 */       .rewards(AdvancementRewards.Builder.experience(50))
/* 285 */       .addCriterion("bullseye", TargetBlockTrigger.TriggerInstance.targetHit(MinMaxBounds.Ints.exactly(15), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().distance(DistancePredicate.horizontal(MinMaxBounds.Doubles.atLeast(30.0D)))))))
/* 286 */       .save($$1, "adventure/bullseye");
/*     */     
/* 288 */     Advancement.Builder.advancement()
/* 289 */       .parent($$3)
/* 290 */       .display((ItemLike)Items.LEATHER_BOOTS, (Component)Component.translatable("advancements.adventure.walk_on_powder_snow_with_leather_boots.title"), (Component)Component.translatable("advancements.adventure.walk_on_powder_snow_with_leather_boots.description"), null, AdvancementType.TASK, true, true, false)
/* 291 */       .addCriterion("walk_on_powder_snow_with_leather_boots", PlayerTrigger.TriggerInstance.walkOnBlockWithEquipment(Blocks.POWDER_SNOW, Items.LEATHER_BOOTS))
/* 292 */       .save($$1, "adventure/walk_on_powder_snow_with_leather_boots");
/*     */     
/* 294 */     Advancement.Builder.advancement()
/* 295 */       .parent($$2)
/* 296 */       .display((ItemLike)Items.LIGHTNING_ROD, (Component)Component.translatable("advancements.adventure.lightning_rod_with_villager_no_fire.title"), (Component)Component.translatable("advancements.adventure.lightning_rod_with_villager_no_fire.description"), null, AdvancementType.TASK, true, true, false)
/* 297 */       .addCriterion("lightning_rod_with_villager_no_fire", fireCountAndBystander(MinMaxBounds.Ints.exactly(0), Optional.of(EntityPredicate.Builder.entity().of(EntityType.VILLAGER).build())))
/* 298 */       .save($$1, "adventure/lightning_rod_with_villager_no_fire");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     AdvancementHolder $$10 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.SPYGLASS, (Component)Component.translatable("advancements.adventure.spyglass_at_parrot.title"), (Component)Component.translatable("advancements.adventure.spyglass_at_parrot.description"), null, AdvancementType.TASK, true, true, false).addCriterion("spyglass_at_parrot", lookAtThroughItem(EntityType.PARROT, Items.SPYGLASS)).save($$1, "adventure/spyglass_at_parrot");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 310 */     AdvancementHolder $$11 = Advancement.Builder.advancement().parent($$10).display((ItemLike)Items.SPYGLASS, (Component)Component.translatable("advancements.adventure.spyglass_at_ghast.title"), (Component)Component.translatable("advancements.adventure.spyglass_at_ghast.description"), null, AdvancementType.TASK, true, true, false).addCriterion("spyglass_at_ghast", lookAtThroughItem(EntityType.GHAST, Items.SPYGLASS)).save($$1, "adventure/spyglass_at_ghast");
/*     */     
/* 312 */     Advancement.Builder.advancement()
/* 313 */       .parent($$3)
/* 314 */       .display((ItemLike)Items.JUKEBOX, (Component)Component.translatable("advancements.adventure.play_jukebox_in_meadows.title"), (Component)Component.translatable("advancements.adventure.play_jukebox_in_meadows.description"), null, AdvancementType.TASK, true, true, false)
/* 315 */       .addCriterion("play_jukebox_in_meadows", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBiome(Biomes.MEADOW).setBlock(BlockPredicate.Builder.block().of(new Block[] { Blocks.JUKEBOX }, )), ItemPredicate.Builder.item().of(ItemTags.MUSIC_DISCS)))
/* 316 */       .save($$1, "adventure/play_jukebox_in_meadows");
/*     */     
/* 318 */     Advancement.Builder.advancement()
/* 319 */       .parent($$11)
/* 320 */       .display((ItemLike)Items.SPYGLASS, (Component)Component.translatable("advancements.adventure.spyglass_at_dragon.title"), (Component)Component.translatable("advancements.adventure.spyglass_at_dragon.description"), null, AdvancementType.TASK, true, true, false)
/* 321 */       .addCriterion("spyglass_at_dragon", lookAtThroughItem(EntityType.ENDER_DRAGON, Items.SPYGLASS))
/* 322 */       .save($$1, "adventure/spyglass_at_dragon");
/*     */     
/* 324 */     Advancement.Builder.advancement()
/* 325 */       .parent($$2)
/* 326 */       .display((ItemLike)Items.WATER_BUCKET, (Component)Component.translatable("advancements.adventure.fall_from_world_height.title"), (Component)Component.translatable("advancements.adventure.fall_from_world_height.description"), null, AdvancementType.TASK, true, true, false)
/* 327 */       .addCriterion("fall_from_world_height", 
/* 328 */         DistanceTrigger.TriggerInstance.fallFromHeight(
/* 329 */           EntityPredicate.Builder.entity().located(LocationPredicate.Builder.atYLocation(MinMaxBounds.Doubles.atMost(-59.0D))), 
/* 330 */           DistancePredicate.vertical(MinMaxBounds.Doubles.atLeast(379.0D)), 
/* 331 */           LocationPredicate.Builder.atYLocation(MinMaxBounds.Doubles.atLeast(319.0D))))
/*     */ 
/*     */       
/* 334 */       .save($$1, "adventure/fall_from_world_height");
/*     */     
/* 336 */     Advancement.Builder.advancement()
/* 337 */       .parent($$5)
/* 338 */       .display((ItemLike)Blocks.SCULK_CATALYST, (Component)Component.translatable("advancements.adventure.kill_mob_near_sculk_catalyst.title"), (Component)Component.translatable("advancements.adventure.kill_mob_near_sculk_catalyst.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 339 */       .addCriterion("kill_mob_near_sculk_catalyst", KilledTrigger.TriggerInstance.playerKilledEntityNearSculkCatalyst())
/* 340 */       .save($$1, "adventure/kill_mob_near_sculk_catalyst");
/*     */     
/* 342 */     Advancement.Builder.advancement()
/* 343 */       .parent($$2)
/* 344 */       .display((ItemLike)Blocks.SCULK_SENSOR, (Component)Component.translatable("advancements.adventure.avoid_vibration.title"), (Component)Component.translatable("advancements.adventure.avoid_vibration.description"), null, AdvancementType.TASK, true, true, false)
/* 345 */       .addCriterion("avoid_vibration", PlayerTrigger.TriggerInstance.avoidVibration())
/* 346 */       .save($$1, "adventure/avoid_vibration");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 351 */     AdvancementHolder $$12 = respectingTheRemnantsCriterions(Advancement.Builder.advancement()).parent($$2).display((ItemLike)Items.BRUSH, (Component)Component.translatable("advancements.adventure.salvage_sherd.title"), (Component)Component.translatable("advancements.adventure.salvage_sherd.description"), null, AdvancementType.TASK, true, true, false).save($$1, "adventure/salvage_sherd");
/*     */     
/* 353 */     Advancement.Builder.advancement()
/* 354 */       .parent($$12)
/* 355 */       .display(
/* 356 */         DecoratedPotBlockEntity.createDecoratedPotItem(new DecoratedPotBlockEntity.Decorations(Items.BRICK, Items.HEART_POTTERY_SHERD, Items.BRICK, Items.EXPLORER_POTTERY_SHERD)), 
/* 357 */         (Component)Component.translatable("advancements.adventure.craft_decorated_pot_using_only_sherds.title"), 
/* 358 */         (Component)Component.translatable("advancements.adventure.craft_decorated_pot_using_only_sherds.description"), null, AdvancementType.TASK, true, true, false)
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 365 */       .addCriterion("pot_crafted_using_only_sherds", 
/* 366 */         RecipeCraftedTrigger.TriggerInstance.craftedItem(new ResourceLocation("minecraft:decorated_pot"), 
/*     */           
/* 368 */           List.of(
/* 369 */             ItemPredicate.Builder.item().of(ItemTags.DECORATED_POT_SHERDS), 
/* 370 */             ItemPredicate.Builder.item().of(ItemTags.DECORATED_POT_SHERDS), 
/* 371 */             ItemPredicate.Builder.item().of(ItemTags.DECORATED_POT_SHERDS), 
/* 372 */             ItemPredicate.Builder.item().of(ItemTags.DECORATED_POT_SHERDS))))
/*     */ 
/*     */ 
/*     */       
/* 376 */       .save($$1, "adventure/craft_decorated_pot_using_only_sherds");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 381 */     AdvancementHolder $$13 = craftingANewLook(Advancement.Builder.advancement()).parent($$2).display(new ItemStack((ItemLike)Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE), (Component)Component.translatable("advancements.adventure.trim_with_any_armor_pattern.title"), (Component)Component.translatable("advancements.adventure.trim_with_any_armor_pattern.description"), null, AdvancementType.TASK, true, true, false).save($$1, "adventure/trim_with_any_armor_pattern");
/*     */     
/* 383 */     smithingWithStyle(Advancement.Builder.advancement())
/* 384 */       .parent($$13)
/* 385 */       .display(new ItemStack((ItemLike)Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE), (Component)Component.translatable("advancements.adventure.trim_with_all_exclusive_armor_patterns.title"), (Component)Component.translatable("advancements.adventure.trim_with_all_exclusive_armor_patterns.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 386 */       .rewards(AdvancementRewards.Builder.experience(150))
/* 387 */       .save($$1, "adventure/trim_with_all_exclusive_armor_patterns");
/*     */     
/* 389 */     Advancement.Builder.advancement()
/* 390 */       .parent($$2)
/* 391 */       .display((ItemLike)Items.CHISELED_BOOKSHELF, (Component)Component.translatable("advancements.adventure.read_power_from_chiseled_bookshelf.title"), (Component)Component.translatable("advancements.adventure.read_power_from_chiseled_bookshelf.description"), null, AdvancementType.TASK, true, true, false)
/* 392 */       .requirements(AdvancementRequirements.Strategy.OR)
/* 393 */       .addCriterion("chiseled_bookshelf", placedBlockReadByComparator(Blocks.CHISELED_BOOKSHELF))
/* 394 */       .addCriterion("comparator", placedComparatorReadingBlock(Blocks.CHISELED_BOOKSHELF))
/* 395 */       .save($$1, "adventure/read_power_of_chiseled_bookshelf");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AdvancementHolder createMonsterHunterAdvancement(AdvancementHolder $$0, Consumer<AdvancementHolder> $$1, List<EntityType<?>> $$2) {
/* 403 */     AdvancementHolder $$3 = addMobsToKill(Advancement.Builder.advancement(), $$2).parent($$0).display((ItemLike)Items.IRON_SWORD, (Component)Component.translatable("advancements.adventure.kill_a_mob.title"), (Component)Component.translatable("advancements.adventure.kill_a_mob.description"), null, AdvancementType.TASK, true, true, false).requirements(AdvancementRequirements.Strategy.OR).save($$1, "adventure/kill_a_mob");
/*     */     
/* 405 */     addMobsToKill(Advancement.Builder.advancement(), $$2)
/* 406 */       .parent($$3)
/* 407 */       .display((ItemLike)Items.DIAMOND_SWORD, (Component)Component.translatable("advancements.adventure.kill_all_mobs.title"), (Component)Component.translatable("advancements.adventure.kill_all_mobs.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 408 */       .rewards(AdvancementRewards.Builder.experience(100))
/* 409 */       .save($$1, "adventure/kill_all_mobs");
/*     */     
/* 411 */     return $$3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Criterion<ItemUsedOnLocationTrigger.TriggerInstance> placedBlockReadByComparator(Block $$0) {
/* 420 */     LootItemCondition.Builder[] $$1 = (LootItemCondition.Builder[])ComparatorBlock.FACING.getPossibleValues().stream().map($$0 -> { StatePropertiesPredicate.Builder $$1 = StatePropertiesPredicate.Builder.properties().hasProperty((Property)ComparatorBlock.FACING, (Comparable)$$0); BlockPredicate.Builder $$2 = BlockPredicate.Builder.block().of(new Block[] { Blocks.COMPARATOR }).setProperties($$1); return LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock($$2), new BlockPos($$0.getOpposite().getNormal())); }).toArray($$0 -> new LootItemCondition.Builder[$$0]);
/*     */     
/* 422 */     return ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(new LootItemCondition.Builder[] {
/* 423 */           (LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0), 
/* 424 */           (LootItemCondition.Builder)AnyOfCondition.anyOf($$1)
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Criterion<ItemUsedOnLocationTrigger.TriggerInstance> placedComparatorReadingBlock(Block $$0) {
/* 434 */     LootItemCondition.Builder[] $$1 = (LootItemCondition.Builder[])ComparatorBlock.FACING.getPossibleValues().stream().map($$1 -> { StatePropertiesPredicate.Builder $$2 = StatePropertiesPredicate.Builder.properties().hasProperty((Property)ComparatorBlock.FACING, (Comparable)$$1); LootItemBlockStatePropertyCondition.Builder $$3 = (new LootItemBlockStatePropertyCondition.Builder(Blocks.COMPARATOR)).setProperties($$2); LootItemCondition.Builder $$4 = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(new Block[] { $$0 }, )), new BlockPos($$1.getNormal())); return AllOfCondition.allOf(new LootItemCondition.Builder[] { (LootItemCondition.Builder)$$3, $$4 }); }).toArray($$0 -> new LootItemCondition.Builder[$$0]);
/*     */     
/* 436 */     return ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(new LootItemCondition.Builder[] {
/* 437 */           (LootItemCondition.Builder)AnyOfCondition.anyOf($$1)
/*     */         });
/*     */   }
/*     */   
/*     */   private static Advancement.Builder smithingWithStyle(Advancement.Builder $$0) {
/* 442 */     $$0.requirements(AdvancementRequirements.Strategy.AND);
/*     */     
/* 444 */     Set<Item> $$1 = Set.of(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE);
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
/* 455 */     VanillaRecipeProvider.smithingTrims().filter($$1 -> $$0.contains($$1.template())).forEach($$1 -> $$0.addCriterion("armor_trimmed_" + $$1.id(), RecipeCraftedTrigger.TriggerInstance.craftedItem($$1.id())));
/*     */ 
/*     */ 
/*     */     
/* 459 */     return $$0;
/*     */   }
/*     */   
/*     */   private static Advancement.Builder craftingANewLook(Advancement.Builder $$0) {
/* 463 */     $$0.requirements(AdvancementRequirements.Strategy.OR);
/*     */     
/* 465 */     VanillaRecipeProvider.smithingTrims().map(VanillaRecipeProvider.TrimTemplate::id).forEach($$1 -> $$0.addCriterion("armor_trimmed_" + $$1, RecipeCraftedTrigger.TriggerInstance.craftedItem($$1)));
/*     */ 
/*     */ 
/*     */     
/* 469 */     return $$0;
/*     */   }
/*     */   
/*     */   private static Advancement.Builder respectingTheRemnantsCriterions(Advancement.Builder $$0) {
/* 473 */     List<Pair<String, Criterion<LootTableTrigger.TriggerInstance>>> $$1 = List.of(
/* 474 */         Pair.of("desert_pyramid", LootTableTrigger.TriggerInstance.lootTableUsed(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY)), 
/* 475 */         Pair.of("desert_well", LootTableTrigger.TriggerInstance.lootTableUsed(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY)), 
/* 476 */         Pair.of("ocean_ruin_cold", LootTableTrigger.TriggerInstance.lootTableUsed(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY)), 
/* 477 */         Pair.of("ocean_ruin_warm", LootTableTrigger.TriggerInstance.lootTableUsed(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY)), 
/* 478 */         Pair.of("trail_ruins_rare", LootTableTrigger.TriggerInstance.lootTableUsed(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE)), 
/* 479 */         Pair.of("trail_ruins_common", LootTableTrigger.TriggerInstance.lootTableUsed(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON)));
/*     */     
/* 481 */     $$1.forEach($$1 -> $$0.addCriterion((String)$$1.getFirst(), (Criterion)$$1.getSecond()));
/*     */     
/* 483 */     String $$2 = "has_sherd";
/* 484 */     $$0.addCriterion("has_sherd", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemPredicate.Builder[] { ItemPredicate.Builder.item().of(ItemTags.DECORATED_POT_SHERDS) }));
/*     */     
/* 486 */     $$0.requirements(new AdvancementRequirements(List.of($$1
/* 487 */             .stream().map(Pair::getFirst).toList(), 
/* 488 */             List.of("has_sherd"))));
/*     */ 
/*     */     
/* 491 */     return $$0;
/*     */   }
/*     */   
/*     */   protected static void createAdventuringTime(Consumer<AdvancementHolder> $$0, AdvancementHolder $$1, MultiNoiseBiomeSourceParameterList.Preset $$2) {
/* 495 */     addBiomes(Advancement.Builder.advancement(), $$2.usedBiomes().toList())
/* 496 */       .parent($$1)
/* 497 */       .display((ItemLike)Items.DIAMOND_BOOTS, (Component)Component.translatable("advancements.adventure.adventuring_time.title"), (Component)Component.translatable("advancements.adventure.adventuring_time.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 498 */       .rewards(AdvancementRewards.Builder.experience(500))
/* 499 */       .save($$0, "adventure/adventuring_time");
/*     */   }
/*     */   
/*     */   private static Advancement.Builder addMobsToKill(Advancement.Builder $$0, List<EntityType<?>> $$1) {
/* 503 */     $$1.forEach($$1 -> $$0.addCriterion(BuiltInRegistries.ENTITY_TYPE.getKey($$1).toString(), KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of($$1))));
/* 504 */     return $$0;
/*     */   }
/*     */   
/*     */   protected static Advancement.Builder addBiomes(Advancement.Builder $$0, List<ResourceKey<Biome>> $$1) {
/* 508 */     for (ResourceKey<Biome> $$2 : $$1) {
/* 509 */       $$0.addCriterion($$2.location().toString(), PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome($$2)));
/*     */     }
/* 511 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\advancements\packs\VanillaAdventureAdvancements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */