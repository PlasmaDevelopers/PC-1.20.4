/*     */ package net.minecraft.data.advancements.packs;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementHolder;
/*     */ import net.minecraft.advancements.AdvancementRequirements;
/*     */ import net.minecraft.advancements.AdvancementType;
/*     */ import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
/*     */ import net.minecraft.advancements.critereon.CuredZombieVillagerTrigger;
/*     */ import net.minecraft.advancements.critereon.DamagePredicate;
/*     */ import net.minecraft.advancements.critereon.DamageSourcePredicate;
/*     */ import net.minecraft.advancements.critereon.EnchantedItemTrigger;
/*     */ import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
/*     */ import net.minecraft.advancements.critereon.InventoryChangeTrigger;
/*     */ import net.minecraft.advancements.critereon.ItemPredicate;
/*     */ import net.minecraft.advancements.critereon.LocationPredicate;
/*     */ import net.minecraft.advancements.critereon.PlayerTrigger;
/*     */ import net.minecraft.advancements.critereon.TagPredicate;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.data.advancements.AdvancementSubProvider;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VanillaStoryAdvancements
/*     */   implements AdvancementSubProvider
/*     */ {
/*     */   public void generate(HolderLookup.Provider $$0, Consumer<AdvancementHolder> $$1) {
/*  38 */     AdvancementHolder $$2 = Advancement.Builder.advancement().display((ItemLike)Blocks.GRASS_BLOCK, (Component)Component.translatable("advancements.story.root.title"), (Component)Component.translatable("advancements.story.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), AdvancementType.TASK, false, false, false).addCriterion("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Blocks.CRAFTING_TABLE })).save($$1, "story/root");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  44 */     AdvancementHolder $$3 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Items.WOODEN_PICKAXE, (Component)Component.translatable("advancements.story.mine_stone.title"), (Component)Component.translatable("advancements.story.mine_stone.description"), null, AdvancementType.TASK, true, true, false).addCriterion("get_stone", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemPredicate.Builder[] { ItemPredicate.Builder.item().of(ItemTags.STONE_TOOL_MATERIALS) })).save($$1, "story/mine_stone");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  50 */     AdvancementHolder $$4 = Advancement.Builder.advancement().parent($$3).display((ItemLike)Items.STONE_PICKAXE, (Component)Component.translatable("advancements.story.upgrade_tools.title"), (Component)Component.translatable("advancements.story.upgrade_tools.description"), null, AdvancementType.TASK, true, true, false).addCriterion("stone_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.STONE_PICKAXE })).save($$1, "story/upgrade_tools");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     AdvancementHolder $$5 = Advancement.Builder.advancement().parent($$4).display((ItemLike)Items.IRON_INGOT, (Component)Component.translatable("advancements.story.smelt_iron.title"), (Component)Component.translatable("advancements.story.smelt_iron.description"), null, AdvancementType.TASK, true, true, false).addCriterion("iron", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.IRON_INGOT })).save($$1, "story/smelt_iron");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  62 */     AdvancementHolder $$6 = Advancement.Builder.advancement().parent($$5).display((ItemLike)Items.IRON_PICKAXE, (Component)Component.translatable("advancements.story.iron_tools.title"), (Component)Component.translatable("advancements.story.iron_tools.description"), null, AdvancementType.TASK, true, true, false).addCriterion("iron_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.IRON_PICKAXE })).save($$1, "story/iron_tools");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     AdvancementHolder $$7 = Advancement.Builder.advancement().parent($$6).display((ItemLike)Items.DIAMOND, (Component)Component.translatable("advancements.story.mine_diamond.title"), (Component)Component.translatable("advancements.story.mine_diamond.description"), null, AdvancementType.TASK, true, true, false).addCriterion("diamond", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.DIAMOND })).save($$1, "story/mine_diamond");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     AdvancementHolder $$8 = Advancement.Builder.advancement().parent($$5).display((ItemLike)Items.LAVA_BUCKET, (Component)Component.translatable("advancements.story.lava_bucket.title"), (Component)Component.translatable("advancements.story.lava_bucket.description"), null, AdvancementType.TASK, true, true, false).addCriterion("lava_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.LAVA_BUCKET })).save($$1, "story/lava_bucket");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     AdvancementHolder $$9 = Advancement.Builder.advancement().parent($$5).display((ItemLike)Items.IRON_CHESTPLATE, (Component)Component.translatable("advancements.story.obtain_armor.title"), (Component)Component.translatable("advancements.story.obtain_armor.description"), null, AdvancementType.TASK, true, true, false).requirements(AdvancementRequirements.Strategy.OR).addCriterion("iron_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.IRON_HELMET })).addCriterion("iron_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.IRON_CHESTPLATE })).addCriterion("iron_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.IRON_LEGGINGS })).addCriterion("iron_boots", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.IRON_BOOTS })).save($$1, "story/obtain_armor");
/*     */     
/*  86 */     Advancement.Builder.advancement()
/*  87 */       .parent($$7)
/*  88 */       .display((ItemLike)Items.ENCHANTED_BOOK, (Component)Component.translatable("advancements.story.enchant_item.title"), (Component)Component.translatable("advancements.story.enchant_item.description"), null, AdvancementType.TASK, true, true, false)
/*  89 */       .addCriterion("enchanted_item", EnchantedItemTrigger.TriggerInstance.enchantedItem())
/*  90 */       .save($$1, "story/enchant_item");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     AdvancementHolder $$10 = Advancement.Builder.advancement().parent($$8).display((ItemLike)Blocks.OBSIDIAN, (Component)Component.translatable("advancements.story.form_obsidian.title"), (Component)Component.translatable("advancements.story.form_obsidian.description"), null, AdvancementType.TASK, true, true, false).addCriterion("obsidian", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Blocks.OBSIDIAN })).save($$1, "story/form_obsidian");
/*     */     
/*  98 */     Advancement.Builder.advancement()
/*  99 */       .parent($$9)
/* 100 */       .display((ItemLike)Items.SHIELD, (Component)Component.translatable("advancements.story.deflect_arrow.title"), (Component)Component.translatable("advancements.story.deflect_arrow.description"), null, AdvancementType.TASK, true, true, false)
/* 101 */       .addCriterion("deflected_projectile", EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(DamagePredicate.Builder.damageInstance().type(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE))).blocked(Boolean.valueOf(true))))
/* 102 */       .save($$1, "story/deflect_arrow");
/*     */     
/* 104 */     Advancement.Builder.advancement()
/* 105 */       .parent($$7)
/* 106 */       .display((ItemLike)Items.DIAMOND_CHESTPLATE, (Component)Component.translatable("advancements.story.shiny_gear.title"), (Component)Component.translatable("advancements.story.shiny_gear.description"), null, AdvancementType.TASK, true, true, false)
/* 107 */       .requirements(AdvancementRequirements.Strategy.OR)
/* 108 */       .addCriterion("diamond_helmet", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.DIAMOND_HELMET
/* 109 */           })).addCriterion("diamond_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.DIAMOND_CHESTPLATE
/* 110 */           })).addCriterion("diamond_leggings", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.DIAMOND_LEGGINGS
/* 111 */           })).addCriterion("diamond_boots", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.DIAMOND_BOOTS
/* 112 */           })).save($$1, "story/shiny_gear");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     AdvancementHolder $$11 = Advancement.Builder.advancement().parent($$10).display((ItemLike)Items.FLINT_AND_STEEL, (Component)Component.translatable("advancements.story.enter_the_nether.title"), (Component)Component.translatable("advancements.story.enter_the_nether.description"), null, AdvancementType.TASK, true, true, false).addCriterion("entered_nether", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(Level.NETHER)).save($$1, "story/enter_the_nether");
/*     */     
/* 120 */     Advancement.Builder.advancement()
/* 121 */       .parent($$11)
/* 122 */       .display((ItemLike)Items.GOLDEN_APPLE, (Component)Component.translatable("advancements.story.cure_zombie_villager.title"), (Component)Component.translatable("advancements.story.cure_zombie_villager.description"), null, AdvancementType.GOAL, true, true, false)
/* 123 */       .addCriterion("cured_zombie", CuredZombieVillagerTrigger.TriggerInstance.curedZombieVillager())
/* 124 */       .save($$1, "story/cure_zombie_villager");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     AdvancementHolder $$12 = Advancement.Builder.advancement().parent($$11).display((ItemLike)Items.ENDER_EYE, (Component)Component.translatable("advancements.story.follow_ender_eye.title"), (Component)Component.translatable("advancements.story.follow_ender_eye.description"), null, AdvancementType.TASK, true, true, false).addCriterion("in_stronghold", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(BuiltinStructures.STRONGHOLD))).save($$1, "story/follow_ender_eye");
/*     */     
/* 132 */     Advancement.Builder.advancement()
/* 133 */       .parent($$12)
/* 134 */       .display((ItemLike)Blocks.END_STONE, (Component)Component.translatable("advancements.story.enter_the_end.title"), (Component)Component.translatable("advancements.story.enter_the_end.description"), null, AdvancementType.TASK, true, true, false)
/* 135 */       .addCriterion("entered_end", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(Level.END))
/* 136 */       .save($$1, "story/enter_the_end");
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\advancements\packs\VanillaStoryAdvancements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */