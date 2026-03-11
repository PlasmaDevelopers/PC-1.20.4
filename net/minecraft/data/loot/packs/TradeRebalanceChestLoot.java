/*     */ package net.minecraft.data.loot.packs;
/*     */ 
/*     */ import java.util.function.BiConsumer;
/*     */ import net.minecraft.data.loot.LootTableSubProvider;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.InstrumentTags;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.LootPool;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootItem;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*     */ import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetInstrumentFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
/*     */ 
/*     */ public class TradeRebalanceChestLoot
/*     */   implements LootTableSubProvider {
/*     */   public void generate(BiConsumer<ResourceLocation, LootTable.Builder> $$0) {
/*  32 */     $$0.accept(BuiltInLootTables.ABANDONED_MINESHAFT, 
/*  33 */         LootTable.lootTable()
/*  34 */         .withPool(LootPool.lootPool()
/*  35 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  36 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_APPLE).setWeight(20))
/*  37 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENCHANTED_GOLDEN_APPLE))
/*  38 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NAME_TAG).setWeight(30))
/*  39 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(10).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  40 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_PICKAXE).setWeight(5))
/*  41 */           .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(5)))
/*     */         
/*  43 */         .withPool(LootPool.lootPool()
/*  44 */           .setRolls((NumberProvider)UniformGenerator.between(2.0F, 4.0F))
/*  45 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  46 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  47 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.REDSTONE).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 9.0F))))
/*  48 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LAPIS_LAZULI).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 9.0F))))
/*  49 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))
/*  50 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 8.0F))))
/*  51 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  52 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GLOW_BERRIES).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 6.0F))))
/*  53 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MELON_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))))
/*  54 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PUMPKIN_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))))
/*  55 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BEETROOT_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F)))))
/*     */         
/*  57 */         .withPool(LootPool.lootPool()
/*  58 */           .setRolls((NumberProvider)ConstantValue.exactly(3.0F))
/*  59 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.RAIL).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 8.0F))))
/*  60 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.POWERED_RAIL).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  61 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.DETECTOR_RAIL).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  62 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.ACTIVATOR_RAIL).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  63 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.TORCH).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 16.0F)))))
/*     */         
/*  65 */         .withPool(LootPool.lootPool()
/*  66 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  67 */           .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(4))
/*  68 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(1).apply((LootItemFunction.Builder)(new EnchantRandomlyFunction.Builder()).withEnchantment(Enchantments.BLOCK_EFFICIENCY)))));
/*     */ 
/*     */ 
/*     */     
/*  72 */     $$0.accept(BuiltInLootTables.ANCIENT_CITY, ancientCityLootTable());
/*  73 */     $$0.accept(BuiltInLootTables.DESERT_PYRAMID, desertPyramidLootTable());
/*  74 */     $$0.accept(BuiltInLootTables.JUNGLE_TEMPLE, jungleTempleLootTable());
/*  75 */     $$0.accept(BuiltInLootTables.PILLAGER_OUTPOST, pillagerOutpostLootTable());
/*     */   }
/*     */   
/*     */   public static LootTable.Builder pillagerOutpostLootTable() {
/*  79 */     return LootTable.lootTable()
/*  80 */       .withPool(LootPool.lootPool()
/*  81 */         .setRolls((NumberProvider)UniformGenerator.between(0.0F, 1.0F))
/*  82 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CROSSBOW)))
/*     */       
/*  84 */       .withPool(LootPool.lootPool()
/*  85 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 3.0F))
/*  86 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(7).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 5.0F))))
/*  87 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTATO).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))))
/*  88 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CARROT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 5.0F)))))
/*     */       
/*  90 */       .withPool(LootPool.lootPool()
/*  91 */         .setRolls((NumberProvider)UniformGenerator.between(1.0F, 3.0F))
/*  92 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.DARK_OAK_LOG).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 3.0F)))))
/*     */       
/*  94 */       .withPool(LootPool.lootPool()
/*  95 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 3.0F))
/*  96 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EXPERIENCE_BOTTLE).setWeight(7))
/*  97 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).setWeight(4).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 6.0F))))
/*  98 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).setWeight(4).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F))))
/*  99 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TRIPWIRE_HOOK).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 100 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 101 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(1).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment())))
/*     */       
/* 103 */       .withPool(LootPool.lootPool()
/* 104 */         .setRolls((NumberProvider)UniformGenerator.between(0.0F, 1.0F))
/* 105 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOAT_HORN)).apply((LootItemFunction.Builder)SetInstrumentFunction.setInstrumentOptions(InstrumentTags.REGULAR_GOAT_HORNS)))
/*     */       
/* 107 */       .withPool(LootPool.lootPool()
/* 108 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 109 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(3))
/* 110 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F)))))
/*     */       
/* 112 */       .withPool(LootPool.lootPool()
/* 113 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 114 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(1))
/* 115 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(2).apply((LootItemFunction.Builder)(new EnchantRandomlyFunction.Builder()).withEnchantment(Enchantments.QUICK_CHARGE))));
/*     */   }
/*     */ 
/*     */   
/*     */   public static LootTable.Builder desertPyramidLootTable() {
/* 120 */     return LootTable.lootTable()
/* 121 */       .withPool(LootPool.lootPool()
/* 122 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 4.0F))
/* 123 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 124 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/* 125 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F))))
/* 126 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 127 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).setWeight(25).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 6.0F))))
/* 128 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPIDER_EYE).setWeight(25).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 129 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(25).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 7.0F))))
/* 130 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(20))
/* 131 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HORSE_ARMOR).setWeight(15))
/* 132 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HORSE_ARMOR).setWeight(10))
/* 133 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HORSE_ARMOR).setWeight(5))
/* 134 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(10).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/* 135 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_APPLE).setWeight(20))
/* 136 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
/* 137 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(15)))
/*     */       
/* 139 */       .withPool(LootPool.lootPool()
/* 140 */         .setRolls((NumberProvider)ConstantValue.exactly(4.0F))
/* 141 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/* 142 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GUNPOWDER).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/* 143 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/* 144 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/* 145 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.SAND).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F)))))
/*     */       
/* 147 */       .withPool(LootPool.lootPool()
/* 148 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 149 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(4))
/* 150 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F))))
/* 151 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(2).apply((LootItemFunction.Builder)(new EnchantRandomlyFunction.Builder()).withEnchantment(Enchantments.UNBREAKING))));
/*     */   }
/*     */ 
/*     */   
/*     */   public static LootTable.Builder ancientCityLootTable() {
/* 156 */     return LootTable.lootTable()
/* 157 */       .withPool(LootPool.lootPool()
/* 158 */         .setRolls((NumberProvider)UniformGenerator.between(5.0F, 10.0F))
/*     */ 
/*     */         
/* 161 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENCHANTED_GOLDEN_APPLE).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))
/* 162 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_OTHERSIDE).setWeight(1))
/*     */ 
/*     */         
/* 165 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COMPASS).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/* 166 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SCULK_CATALYST).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))
/* 167 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NAME_TAG).setWeight(2))
/* 168 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HOE).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.8F, 1.0F))).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(30.0F, 50.0F)).allowTreasure()))
/* 169 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEAD).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/* 170 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HORSE_ARMOR).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/* 171 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/* 172 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_13).setWeight(2))
/* 173 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_CAT).setWeight(2))
/* 174 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_LEGGINGS).setWeight(2).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(30.0F, 50.0F)).allowTreasure()))
/*     */ 
/*     */         
/* 177 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(3).apply((LootItemFunction.Builder)(new EnchantRandomlyFunction.Builder()).withEnchantment(Enchantments.SWIFT_SNEAK)))
/* 178 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SCULK).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 10.0F))))
/* 179 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SCULK_SENSOR).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 180 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CANDLE).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/* 181 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.AMETHYST_SHARD).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 15.0F))))
/* 182 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EXPERIENCE_BOTTLE).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 183 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GLOW_BERRIES).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 15.0F))))
/* 184 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_LEGGINGS).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*     */         
/* 186 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ECHO_SHARD).setWeight(4).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 187 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DISC_FRAGMENT_5).setWeight(4).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*     */ 
/*     */         
/* 190 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTION).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.STRONG_REGENERATION)))
/* 191 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(5).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/* 192 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 10.0F))))
/* 193 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 15.0F))))
/* 194 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SOUL_TORCH).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 15.0F))))
/*     */ 
/*     */         
/* 197 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(7).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(6.0F, 15.0F)))))
/*     */       
/* 199 */       .withPool(LootPool.lootPool()
/* 200 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 201 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(71))
/* 202 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(4).apply((LootItemFunction.Builder)(new EnchantRandomlyFunction.Builder()).withEnchantment(Enchantments.MENDING)))
/* 203 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(4))
/* 204 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static LootTable.Builder jungleTempleLootTable() {
/* 209 */     return LootTable.lootTable()
/* 210 */       .withPool(LootPool.lootPool()
/* 211 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 6.0F))
/* 212 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 213 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/* 214 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F))))
/* 215 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.BAMBOO).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 216 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 217 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 6.0F))))
/* 218 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(16).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 7.0F))))
/* 219 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(3))
/* 220 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HORSE_ARMOR))
/* 221 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HORSE_ARMOR))
/* 222 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HORSE_ARMOR))
/* 223 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)ConstantValue.exactly(30.0F)).allowTreasure())))
/*     */       
/* 225 */       .withPool(LootPool.lootPool()
/* 226 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 227 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(2))
/* 228 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F)))))
/*     */       
/* 230 */       .withPool(LootPool.lootPool()
/* 231 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 232 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(1))
/* 233 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).apply((LootItemFunction.Builder)(new EnchantRandomlyFunction.Builder()).withEnchantment(Enchantments.UNBREAKING))));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\packs\TradeRebalanceChestLoot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */