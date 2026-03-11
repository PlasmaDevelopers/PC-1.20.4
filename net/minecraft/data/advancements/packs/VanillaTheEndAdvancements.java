/*    */ package net.minecraft.data.advancements.packs;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.advancements.Advancement;
/*    */ import net.minecraft.advancements.AdvancementHolder;
/*    */ import net.minecraft.advancements.AdvancementRewards;
/*    */ import net.minecraft.advancements.AdvancementType;
/*    */ import net.minecraft.advancements.critereon.ChangeDimensionTrigger;
/*    */ import net.minecraft.advancements.critereon.DistancePredicate;
/*    */ import net.minecraft.advancements.critereon.EnterBlockTrigger;
/*    */ import net.minecraft.advancements.critereon.EntityPredicate;
/*    */ import net.minecraft.advancements.critereon.InventoryChangeTrigger;
/*    */ import net.minecraft.advancements.critereon.KilledTrigger;
/*    */ import net.minecraft.advancements.critereon.LevitationTrigger;
/*    */ import net.minecraft.advancements.critereon.LocationPredicate;
/*    */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*    */ import net.minecraft.advancements.critereon.PlayerTrigger;
/*    */ import net.minecraft.advancements.critereon.SummonedEntityTrigger;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.data.advancements.AdvancementSubProvider;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VanillaTheEndAdvancements
/*    */   implements AdvancementSubProvider
/*    */ {
/*    */   public void generate(HolderLookup.Provider $$0, Consumer<AdvancementHolder> $$1) {
/* 37 */     AdvancementHolder $$2 = Advancement.Builder.advancement().display((ItemLike)Blocks.END_STONE, (Component)Component.translatable("advancements.end.root.title"), (Component)Component.translatable("advancements.end.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/end.png"), AdvancementType.TASK, false, false, false).addCriterion("entered_end", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(Level.END)).save($$1, "end/root");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 43 */     AdvancementHolder $$3 = Advancement.Builder.advancement().parent($$2).display((ItemLike)Blocks.DRAGON_HEAD, (Component)Component.translatable("advancements.end.kill_dragon.title"), (Component)Component.translatable("advancements.end.kill_dragon.description"), null, AdvancementType.TASK, true, true, false).addCriterion("killed_dragon", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EntityType.ENDER_DRAGON))).save($$1, "end/kill_dragon");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 49 */     AdvancementHolder $$4 = Advancement.Builder.advancement().parent($$3).display((ItemLike)Items.ENDER_PEARL, (Component)Component.translatable("advancements.end.enter_end_gateway.title"), (Component)Component.translatable("advancements.end.enter_end_gateway.description"), null, AdvancementType.TASK, true, true, false).addCriterion("entered_end_gateway", EnterBlockTrigger.TriggerInstance.entersBlock(Blocks.END_GATEWAY)).save($$1, "end/enter_end_gateway");
/*    */     
/* 51 */     Advancement.Builder.advancement()
/* 52 */       .parent($$3)
/* 53 */       .display((ItemLike)Items.END_CRYSTAL, (Component)Component.translatable("advancements.end.respawn_dragon.title"), (Component)Component.translatable("advancements.end.respawn_dragon.description"), null, AdvancementType.GOAL, true, true, false)
/* 54 */       .addCriterion("summoned_dragon", SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().of(EntityType.ENDER_DRAGON)))
/* 55 */       .save($$1, "end/respawn_dragon");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 61 */     AdvancementHolder $$5 = Advancement.Builder.advancement().parent($$4).display((ItemLike)Blocks.PURPUR_BLOCK, (Component)Component.translatable("advancements.end.find_end_city.title"), (Component)Component.translatable("advancements.end.find_end_city.description"), null, AdvancementType.TASK, true, true, false).addCriterion("in_city", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(BuiltinStructures.END_CITY))).save($$1, "end/find_end_city");
/*    */     
/* 63 */     Advancement.Builder.advancement()
/* 64 */       .parent($$3)
/* 65 */       .display((ItemLike)Items.DRAGON_BREATH, (Component)Component.translatable("advancements.end.dragon_breath.title"), (Component)Component.translatable("advancements.end.dragon_breath.description"), null, AdvancementType.GOAL, true, true, false)
/* 66 */       .addCriterion("dragon_breath", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.DRAGON_BREATH
/* 67 */           })).save($$1, "end/dragon_breath");
/*    */     
/* 69 */     Advancement.Builder.advancement()
/* 70 */       .parent($$5)
/* 71 */       .display((ItemLike)Items.SHULKER_SHELL, (Component)Component.translatable("advancements.end.levitate.title"), (Component)Component.translatable("advancements.end.levitate.description"), null, AdvancementType.CHALLENGE, true, true, false)
/* 72 */       .rewards(AdvancementRewards.Builder.experience(50))
/* 73 */       .addCriterion("levitated", LevitationTrigger.TriggerInstance.levitated(DistancePredicate.vertical(MinMaxBounds.Doubles.atLeast(50.0D))))
/* 74 */       .save($$1, "end/levitate");
/*    */     
/* 76 */     Advancement.Builder.advancement()
/* 77 */       .parent($$5)
/* 78 */       .display((ItemLike)Items.ELYTRA, (Component)Component.translatable("advancements.end.elytra.title"), (Component)Component.translatable("advancements.end.elytra.description"), null, AdvancementType.GOAL, true, true, false)
/* 79 */       .addCriterion("elytra", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Items.ELYTRA
/* 80 */           })).save($$1, "end/elytra");
/*    */     
/* 82 */     Advancement.Builder.advancement()
/* 83 */       .parent($$3)
/* 84 */       .display((ItemLike)Blocks.DRAGON_EGG, (Component)Component.translatable("advancements.end.dragon_egg.title"), (Component)Component.translatable("advancements.end.dragon_egg.description"), null, AdvancementType.GOAL, true, true, false)
/* 85 */       .addCriterion("dragon_egg", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[] { (ItemLike)Blocks.DRAGON_EGG
/* 86 */           })).save($$1, "end/dragon_egg");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\advancements\packs\VanillaTheEndAdvancements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */