/*     */ package net.minecraft.data.advancements.packs;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementHolder;
/*     */ import net.minecraft.advancements.AdvancementRequirements;
/*     */ import net.minecraft.advancements.AdvancementRewards;
/*     */ import net.minecraft.advancements.AdvancementType;
/*     */ import net.minecraft.advancements.critereon.BlockPredicate;
/*     */ import net.minecraft.advancements.critereon.BrewedPotionTrigger;
/*     */ import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
/*     */ import net.minecraft.advancements.critereon.ConstructBeaconTrigger;
/*     */ import net.minecraft.advancements.critereon.ContextAwarePredicate;
/*     */ import net.minecraft.advancements.critereon.DamageSourcePredicate;
/*     */ import net.minecraft.advancements.critereon.DistancePredicate;
/*     */ import net.minecraft.advancements.critereon.DistanceTrigger;
/*     */ import net.minecraft.advancements.critereon.EffectsChangedTrigger;
/*     */ import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
/*     */ import net.minecraft.advancements.critereon.EntityFlagsPredicate;
/*     */ import net.minecraft.advancements.critereon.EntityPredicate;
/*     */ import net.minecraft.advancements.critereon.InventoryChangeTrigger;
/*     */ import net.minecraft.advancements.critereon.ItemDurabilityTrigger;
/*     */ import net.minecraft.advancements.critereon.ItemPredicate;
/*     */ import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
/*     */ import net.minecraft.advancements.critereon.KilledTrigger;
/*     */ import net.minecraft.advancements.critereon.LocationPredicate;
/*     */ import net.minecraft.advancements.critereon.LootTableTrigger;
/*     */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*     */ import net.minecraft.advancements.critereon.MobEffectsPredicate;
/*     */ import net.minecraft.advancements.critereon.PickedUpItemTrigger;
/*     */ import net.minecraft.advancements.critereon.PlayerInteractTrigger;
/*     */ import net.minecraft.advancements.critereon.PlayerTrigger;
/*     */ import net.minecraft.advancements.critereon.StatePropertiesPredicate;
/*     */ import net.minecraft.advancements.critereon.SummonedEntityTrigger;
/*     */ import net.minecraft.advancements.critereon.TagPredicate;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.data.advancements.AdvancementSubProvider;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.monster.piglin.PiglinAi;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.RespawnAnchorBlock;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
/*     */ 
/*     */ public class VanillaNetherAdvancements
/*     */   implements AdvancementSubProvider
/*     */ {
/*  62 */   private static final ContextAwarePredicate DISTRACT_PIGLIN_PLAYER_ARMOR_PREDICATE = ContextAwarePredicate.create(new LootItemCondition[] {
/*  63 */         LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().head(ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.GOLDEN_HELMET })))).invert().build(), 
/*  64 */         LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().chest(ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.GOLDEN_CHESTPLATE })))).invert().build(), 
/*  65 */         LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().legs(ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.GOLDEN_LEGGINGS })))).invert().build(), 
/*  66 */         LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().feet(ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.GOLDEN_BOOTS })))).invert().build()
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generate(HolderLookup.Provider $$0, Consumer<AdvancementHolder> $$1) {
/*  74 */     AdvancementHolder $$2 = Advancement.Builder.advancement().display((ItemLike)Blocks.RED_NETHER_BRICKS, (Component)Component.translatable("advancements.nether.root.title"), (Component)Component.translatable("advancements.nether.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/nether.png"), AdvancementType.TASK, false, false, false).addCriterion("entered_nether", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(Level.NETHER)).save($$1, "nether/root");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     AdvancementHolder $$3 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.FIRE_CHARGE, (Component)Component.translatable("advancements.nether.return_to_sender.title"), (Component)Component.translatable("advancements.nether.return_to_sender.description"), null, AdvancementType.CHALLENGE, true, true, false).rewards(AdvancementRewards.Builder.experience(50)).addCriterion("killed_ghast", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityType.GHAST), DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE)).direct(EntityPredicate.Builder.entity().of(EntityType.FIREBALL)))).save($$1, "nether/return_to_sender");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     AdvancementHolder $$4 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Blocks.NETHER_BRICKS, (Component)Component.translatable("advancements.nether.find_fortress.title"), (Component)Component.translatable("advancements.nether.find_fortress.description"), null, AdvancementType.TASK, true, true, false).addCriterion("fortress", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(BuiltinStructures.FORTRESS))).save($$1, "nether/find_fortress");
/*     */     
/*  89 */     Advancement.Builder.advancement()
/*  90 */       .parent($$2)
/*  91 */       .display((ItemLike)Items.MAP, (Component)Component.translatable("advancements.nether.fast_travel.title"), (Component)Component.translatable("advancements.nether.fast_travel.description"), null, AdvancementType.CHALLENGE, true, true, false)
/*  92 */       .rewards(AdvancementRewards.Builder.experience(100))
/*  93 */       .addCriterion("travelled", DistanceTrigger.TriggerInstance.travelledThroughNether(DistancePredicate.horizontal(MinMaxBounds.Doubles.atLeast(7000.0D))))
/*  94 */       .save($$1, "nether/fast_travel");
/*     */     
/*  96 */     Advancement.Builder.advancement()
/*  97 */       .parent($$3)
/*  98 */       .display((ItemLike)Items.GHAST_TEAR, (Component)Component.translatable("advancements.nether.uneasy_alliance.title"), (Component)Component.translatable("advancements.nether.uneasy_alliance.description"), null, AdvancementType.CHALLENGE, true, true, false)
/*  99 */       .rewards(AdvancementRewards.Builder.experience(100))
/* 100 */       .addCriterion("killed_ghast", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityType.GHAST).located(LocationPredicate.Builder.inDimension(Level.OVERWORLD))))
/* 101 */       .save($$1, "nether/uneasy_alliance");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     AdvancementHolder $$5 = Advancement.Builder.advancement().parent($$4).display((ItemLike)Blocks.WITHER_SKELETON_SKULL, (Component)Component.translatable("advancements.nether.get_wither_skull.title"), (Component)Component.translatable("advancements.nether.get_wither_skull.description"), null, AdvancementType.TASK, true, true, false).addCriterion("wither_skull", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Blocks.WITHER_SKELETON_SKULL })).save($$1, "nether/get_wither_skull");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     AdvancementHolder $$6 = Advancement.Builder.advancement().parent($$5).display((ItemLike)Items.NETHER_STAR, (Component)Component.translatable("advancements.nether.summon_wither.title"), (Component)Component.translatable("advancements.nether.summon_wither.description"), null, AdvancementType.TASK, true, true, false).addCriterion("summoned", SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(EntityType.WITHER))).save($$1, "nether/summon_wither");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     AdvancementHolder $$7 = Advancement.Builder.advancement().parent($$4).display((ItemLike)Items.BLAZE_ROD, (Component)Component.translatable("advancements.nether.obtain_blaze_rod.title"), (Component)Component.translatable("advancements.nether.obtain_blaze_rod.description"), null, AdvancementType.TASK, true, true, false).addCriterion("blaze_rod", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.BLAZE_ROD })).save($$1, "nether/obtain_blaze_rod");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     AdvancementHolder $$8 = Advancement.Builder.advancement().parent($$6).display((ItemLike)Blocks.BEACON, (Component)Component.translatable("advancements.nether.create_beacon.title"), (Component)Component.translatable("advancements.nether.create_beacon.description"), null, AdvancementType.TASK, true, true, false).addCriterion("beacon", ConstructBeaconTrigger.TriggerInstance.constructedBeacon(MinMaxBounds.Ints.atLeast(1))).save($$1, "nether/create_beacon");
/*     */     
/* 127 */     Advancement.Builder.advancement()
/* 128 */       .parent($$8)
/* 129 */       .display((ItemLike)Blocks.BEACON, (Component)Component.translatable("advancements.nether.create_full_beacon.title"), (Component)Component.translatable("advancements.nether.create_full_beacon.description"), null, AdvancementType.GOAL, true, true, false)
/* 130 */       .addCriterion("beacon", ConstructBeaconTrigger.TriggerInstance.constructedBeacon(MinMaxBounds.Ints.exactly(4)))
/* 131 */       .save($$1, "nether/create_full_beacon");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     AdvancementHolder $$9 = Advancement.Builder.advancement().parent($$7).display((ItemLike)Items.POTION, (Component)Component.translatable("advancements.nether.brew_potion.title"), (Component)Component.translatable("advancements.nether.brew_potion.description"), null, AdvancementType.TASK, true, true, false).addCriterion("potion", BrewedPotionTrigger.TriggerInstance.brewedPotion()).save($$1, "nether/brew_potion");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     AdvancementHolder $$10 = Advancement.Builder.advancement().parent($$9).display((ItemLike)Items.MILK_BUCKET, (Component)Component.translatable("advancements.nether.all_potions.title"), (Component)Component.translatable("advancements.nether.all_potions.description"), null, AdvancementType.CHALLENGE, true, true, false).rewards(AdvancementRewards.Builder.experience(100)).addCriterion("all_effects", EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.Builder.effects().and(MobEffects.MOVEMENT_SPEED).and(MobEffects.MOVEMENT_SLOWDOWN).and(MobEffects.DAMAGE_BOOST).and(MobEffects.JUMP).and(MobEffects.REGENERATION).and(MobEffects.FIRE_RESISTANCE).and(MobEffects.WATER_BREATHING).and(MobEffects.INVISIBILITY).and(MobEffects.NIGHT_VISION).and(MobEffects.WEAKNESS).and(MobEffects.POISON).and(MobEffects.SLOW_FALLING).and(MobEffects.DAMAGE_RESISTANCE))).save($$1, "nether/all_potions");
/*     */     
/* 146 */     Advancement.Builder.advancement()
/* 147 */       .parent($$10)
/* 148 */       .display((ItemLike)Items.BUCKET, (Component)Component.translatable("advancements.nether.all_effects.title"), (Component)Component.translatable("advancements.nether.all_effects.description"), null, AdvancementType.CHALLENGE, true, true, true)
/* 149 */       .rewards(AdvancementRewards.Builder.experience(1000))
/* 150 */       .addCriterion("all_effects", EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.Builder.effects().and(MobEffects.MOVEMENT_SPEED).and(MobEffects.MOVEMENT_SLOWDOWN).and(MobEffects.DAMAGE_BOOST).and(MobEffects.JUMP).and(MobEffects.REGENERATION).and(MobEffects.FIRE_RESISTANCE).and(MobEffects.WATER_BREATHING).and(MobEffects.INVISIBILITY).and(MobEffects.NIGHT_VISION).and(MobEffects.WEAKNESS).and(MobEffects.POISON).and(MobEffects.WITHER).and(MobEffects.DIG_SPEED).and(MobEffects.DIG_SLOWDOWN).and(MobEffects.LEVITATION).and(MobEffects.GLOWING).and(MobEffects.ABSORPTION).and(MobEffects.HUNGER).and(MobEffects.CONFUSION).and(MobEffects.DAMAGE_RESISTANCE).and(MobEffects.SLOW_FALLING).and(MobEffects.CONDUIT_POWER).and(MobEffects.DOLPHINS_GRACE).and(MobEffects.BLINDNESS).and(MobEffects.BAD_OMEN).and(MobEffects.HERO_OF_THE_VILLAGE).and(MobEffects.DARKNESS)))
/* 151 */       .save($$1, "nether/all_effects");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     AdvancementHolder $$11 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.ANCIENT_DEBRIS, (Component)Component.translatable("advancements.nether.obtain_ancient_debris.title"), (Component)Component.translatable("advancements.nether.obtain_ancient_debris.description"), null, AdvancementType.TASK, true, true, false).addCriterion("ancient_debris", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.ANCIENT_DEBRIS })).save($$1, "nether/obtain_ancient_debris");
/*     */     
/* 159 */     Advancement.Builder.advancement()
/* 160 */       .parent($$11)
/* 161 */       .display((ItemLike)Items.NETHERITE_CHESTPLATE, (Component)Component.translatable("advancements.nether.netherite_armor.title"), (Component)Component.translatable("advancements.nether.netherite_armor.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 162 */       .rewards(AdvancementRewards.Builder.experience(100))
/* 163 */       .addCriterion("netherite_armor", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.NETHERITE_HELMET, (ItemLike)Items.NETHERITE_CHESTPLATE, (ItemLike)Items.NETHERITE_LEGGINGS, (ItemLike)Items.NETHERITE_BOOTS
/* 164 */           })).save($$1, "nether/netherite_armor");
/*     */     
/* 166 */     Advancement.Builder.advancement()
/* 167 */       .parent($$11)
/* 168 */       .display((ItemLike)Items.LODESTONE, (Component)Component.translatable("advancements.nether.use_lodestone.title"), (Component)Component.translatable("advancements.nether.use_lodestone.description"), null, AdvancementType.TASK, true, true, false)
/* 169 */       .addCriterion("use_lodestone", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(new Block[] { Blocks.LODESTONE }, )), ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.COMPASS
/* 170 */             }))).save($$1, "nether/use_lodestone");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     AdvancementHolder $$12 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.CRYING_OBSIDIAN, (Component)Component.translatable("advancements.nether.obtain_crying_obsidian.title"), (Component)Component.translatable("advancements.nether.obtain_crying_obsidian.description"), null, AdvancementType.TASK, true, true, false).addCriterion("crying_obsidian", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.CRYING_OBSIDIAN })).save($$1, "nether/obtain_crying_obsidian");
/*     */     
/* 178 */     Advancement.Builder.advancement()
/* 179 */       .parent($$12)
/* 180 */       .display((ItemLike)Items.RESPAWN_ANCHOR, (Component)Component.translatable("advancements.nether.charge_respawn_anchor.title"), (Component)Component.translatable("advancements.nether.charge_respawn_anchor.description"), null, AdvancementType.TASK, true, true, false)
/* 181 */       .addCriterion("charge_respawn_anchor", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(new Block[] { Blocks.RESPAWN_ANCHOR }, ).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)RespawnAnchorBlock.CHARGE, 4))), ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Blocks.GLOWSTONE
/* 182 */             }))).save($$1, "nether/charge_respawn_anchor");
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
/* 193 */     AdvancementHolder $$13 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.WARPED_FUNGUS_ON_A_STICK, (Component)Component.translatable("advancements.nether.ride_strider.title"), (Component)Component.translatable("advancements.nether.ride_strider.description"), null, AdvancementType.TASK, true, true, false).addCriterion("used_warped_fungus_on_a_stick", ItemDurabilityTrigger.TriggerInstance.changedDurability(Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().vehicle(EntityPredicate.Builder.entity().of(EntityType.STRIDER)))), Optional.of(ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.WARPED_FUNGUS_ON_A_STICK }, ).build()), MinMaxBounds.Ints.ANY)).save($$1, "nether/ride_strider");
/*     */     
/* 195 */     Advancement.Builder.advancement()
/* 196 */       .parent($$13)
/* 197 */       .display((ItemLike)Items.WARPED_FUNGUS_ON_A_STICK, (Component)Component.translatable("advancements.nether.ride_strider_in_overworld_lava.title"), (Component)Component.translatable("advancements.nether.ride_strider_in_overworld_lava.description"), null, AdvancementType.TASK, true, true, false)
/* 198 */       .addCriterion("ride_entity_distance", DistanceTrigger.TriggerInstance.rideEntityInLava(
/* 199 */           EntityPredicate.Builder.entity().located(LocationPredicate.Builder.inDimension(Level.OVERWORLD)).vehicle(EntityPredicate.Builder.entity().of(EntityType.STRIDER)), 
/* 200 */           DistancePredicate.horizontal(MinMaxBounds.Doubles.atLeast(50.0D))))
/*     */       
/* 202 */       .save($$1, "nether/ride_strider_in_overworld_lava");
/*     */     
/* 204 */     VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), MultiNoiseBiomeSourceParameterList.Preset.NETHER.usedBiomes().toList())
/* 205 */       .parent($$13)
/* 206 */       .display((ItemLike)Items.NETHERITE_BOOTS, (Component)Component.translatable("advancements.nether.explore_nether.title"), (Component)Component.translatable("advancements.nether.explore_nether.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 207 */       .rewards(AdvancementRewards.Builder.experience(500))
/* 208 */       .save($$1, "nether/explore_nether");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 214 */     AdvancementHolder $$14 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.POLISHED_BLACKSTONE_BRICKS, (Component)Component.translatable("advancements.nether.find_bastion.title"), (Component)Component.translatable("advancements.nether.find_bastion.description"), null, AdvancementType.TASK, true, true, false).addCriterion("bastion", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(BuiltinStructures.BASTION_REMNANT))).save($$1, "nether/find_bastion");
/*     */     
/* 216 */     Advancement.Builder.advancement()
/* 217 */       .parent($$14)
/* 218 */       .display((ItemLike)Blocks.CHEST, (Component)Component.translatable("advancements.nether.loot_bastion.title"), (Component)Component.translatable("advancements.nether.loot_bastion.description"), null, AdvancementType.TASK, true, true, false)
/* 219 */       .requirements(AdvancementRequirements.Strategy.OR)
/* 220 */       .addCriterion("loot_bastion_other", LootTableTrigger.TriggerInstance.lootTableUsed(new ResourceLocation("minecraft:chests/bastion_other")))
/* 221 */       .addCriterion("loot_bastion_treasure", LootTableTrigger.TriggerInstance.lootTableUsed(new ResourceLocation("minecraft:chests/bastion_treasure")))
/* 222 */       .addCriterion("loot_bastion_hoglin_stable", LootTableTrigger.TriggerInstance.lootTableUsed(new ResourceLocation("minecraft:chests/bastion_hoglin_stable")))
/* 223 */       .addCriterion("loot_bastion_bridge", LootTableTrigger.TriggerInstance.lootTableUsed(new ResourceLocation("minecraft:chests/bastion_bridge")))
/* 224 */       .save($$1, "nether/loot_bastion");
/*     */     
/* 226 */     Advancement.Builder.advancement()
/* 227 */       .parent($$2)
/* 228 */       .requirements(AdvancementRequirements.Strategy.OR)
/* 229 */       .display((ItemLike)Items.GOLD_INGOT, (Component)Component.translatable("advancements.nether.distract_piglin.title"), (Component)Component.translatable("advancements.nether.distract_piglin.description"), null, AdvancementType.TASK, true, true, false)
/* 230 */       .addCriterion("distract_piglin", PickedUpItemTrigger.TriggerInstance.thrownItemPickedUpByEntity(DISTRACT_PIGLIN_PLAYER_ARMOR_PREDICATE, 
/*     */           
/* 232 */           Optional.of(ItemPredicate.Builder.item().of(ItemTags.PIGLIN_LOVED).build()), 
/* 233 */           Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.PIGLIN).flags(EntityFlagsPredicate.Builder.flags().setIsBaby(Boolean.valueOf(false)))))))
/*     */       
/* 235 */       .addCriterion("distract_piglin_directly", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
/* 236 */           Optional.of(DISTRACT_PIGLIN_PLAYER_ARMOR_PREDICATE), 
/* 237 */           ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)PiglinAi.BARTERING_ITEM
/* 238 */             }, ), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EntityType.PIGLIN).flags(EntityFlagsPredicate.Builder.flags().setIsBaby(Boolean.valueOf(false)))))))
/*     */       
/* 240 */       .save($$1, "nether/distract_piglin");
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\advancements\packs\VanillaNetherAdvancements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */