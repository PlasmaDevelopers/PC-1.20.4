/*    */ package net.minecraft.data.loot.packs;
/*    */ 
/*    */ import java.util.function.BiConsumer;
/*    */ import net.minecraft.data.loot.LootTableSubProvider;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.item.alchemy.Potions;
/*    */ import net.minecraft.world.item.enchantment.Enchantments;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*    */ import net.minecraft.world.level.storage.loot.LootPool;
/*    */ import net.minecraft.world.level.storage.loot.LootTable;
/*    */ import net.minecraft.world.level.storage.loot.entries.LootItem;
/*    */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*    */ import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
/*    */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*    */ import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
/*    */ import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
/*    */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*    */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*    */ import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
/*    */ 
/*    */ public class VanillaPiglinBarterLoot implements LootTableSubProvider {
/*    */   public void generate(BiConsumer<ResourceLocation, LootTable.Builder> $$0) {
/* 25 */     $$0.accept(BuiltInLootTables.PIGLIN_BARTERING, 
/* 26 */         LootTable.lootTable()
/* 27 */         .withPool(LootPool.lootPool()
/* 28 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*    */ 
/*    */           
/* 31 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(5).apply((LootItemFunction.Builder)(new EnchantRandomlyFunction.Builder()).withEnchantment(Enchantments.SOUL_SPEED)))
/*    */ 
/*    */           
/* 34 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_BOOTS).setWeight(8).apply((LootItemFunction.Builder)(new EnchantRandomlyFunction.Builder()).withEnchantment(Enchantments.SOUL_SPEED)))
/* 35 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTION).setWeight(8).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.FIRE_RESISTANCE)))
/* 36 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPLASH_POTION).setWeight(8).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.FIRE_RESISTANCE)))
/*    */ 
/*    */           
/* 39 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTION).setWeight(10).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.WATER)))
/* 40 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_NUGGET).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(10.0F, 36.0F))))
/* 41 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENDER_PEARL).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))))
/*    */ 
/*    */           
/* 44 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 9.0F))))
/* 45 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.QUARTZ).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(5.0F, 12.0F))))
/*    */ 
/*    */           
/* 48 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.OBSIDIAN).setWeight(40))
/* 49 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CRYING_OBSIDIAN).setWeight(40).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 50 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FIRE_CHARGE).setWeight(40))
/* 51 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER).setWeight(40).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))))
/* 52 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SOUL_SAND).setWeight(40).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 8.0F))))
/* 53 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NETHER_BRICK).setWeight(40).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 8.0F))))
/* 54 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPECTRAL_ARROW).setWeight(40).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(6.0F, 12.0F))))
/* 55 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GRAVEL).setWeight(40).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(8.0F, 16.0F))))
/* 56 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BLACKSTONE).setWeight(40).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(8.0F, 16.0F))))));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\packs\VanillaPiglinBarterLoot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */