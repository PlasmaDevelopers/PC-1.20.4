/*     */ package net.minecraft.data.loot.packs;
/*     */ 
/*     */ import java.util.function.BiConsumer;
/*     */ import net.minecraft.data.loot.LootTableSubProvider;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.LootPool;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootItem;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*     */ import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
/*     */ 
/*     */ 
/*     */ public class UpdateOneTwentyOneChestLoot
/*     */   implements LootTableSubProvider
/*     */ {
/*     */   public void generate(BiConsumer<ResourceLocation, LootTable.Builder> $$0) {
/*  30 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR_DISPENSER, 
/*  31 */         LootTable.lootTable()
/*  32 */         .withPool(LootPool.lootPool()
/*  33 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  34 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 8.0F))))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_WATER_DISPENSER, 
/*  40 */         LootTable.lootTable()
/*  41 */         .withPool(LootPool.lootPool()
/*  42 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  43 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WATER_BUCKET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  48 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_CHAMBER_DISPENSER, 
/*  49 */         LootTable.lootTable()
/*  50 */         .withPool(LootPool.lootPool()
/*  51 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  52 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WATER_BUCKET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).setWeight(4))
/*  53 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 8.0F))).setWeight(4))
/*  54 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SNOWBALL).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 8.0F))).setWeight(6))
/*  55 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EGG).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 8.0F))).setWeight(2))
/*  56 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FIRE_CHARGE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 8.0F))).setWeight(6))
/*  57 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPLASH_POTION).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.SLOWNESS)).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))).setWeight(1))
/*  58 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPLASH_POTION).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.POISON)).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))).setWeight(1))
/*  59 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPLASH_POTION).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.WEAKNESS)).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))).setWeight(1))
/*  60 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LINGERING_POTION).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.SLOWNESS)).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))).setWeight(1))
/*  61 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LINGERING_POTION).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.POISON)).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))).setWeight(1))
/*  62 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LINGERING_POTION).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.WEAKNESS)).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))).setWeight(1))
/*  63 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LINGERING_POTION).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.HEALING)).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))).setWeight(1))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR_POT, 
/*  70 */         LootTable.lootTable()
/*  71 */         .withPool(LootPool.lootPool()
/*  72 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  73 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))).setWeight(100))
/*  74 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LAPIS_LAZULI).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))).setWeight(100))
/*  75 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.AMETHYST_SHARD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))).setWeight(100))
/*  76 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))).setWeight(100))
/*  77 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 6.0F))).setWeight(50))
/*  78 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COPPER_INGOT).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 6.0F))).setWeight(50))
/*  79 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TRIAL_KEY).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))).setWeight(20))
/*  80 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 6.0F))).setWeight(20))
/*  81 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))).setWeight(5))
/*  82 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD_BLOCK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))).setWeight(5))
/*  83 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_BLOCK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))).setWeight(1))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_SUPPLY, 
/*  89 */         LootTable.lootTable()
/*  90 */         .withPool(LootPool.lootPool()
/*  91 */           .setRolls((NumberProvider)UniformGenerator.between(3.0F, 5.0F))
/*  92 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 14.0F))).setWeight(2))
/*  93 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 8.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.POISON)).setWeight(1))
/*  94 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 8.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.SLOWNESS)).setWeight(1))
/*  95 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BAKED_POTATO).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))).setWeight(2))
/*  96 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GLOW_BERRIES).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 10.0F))).setWeight(2))
/*  97 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ACACIA_PLANKS).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 6.0F))).setWeight(1))
/*  98 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MOSS_BLOCK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))).setWeight(1))
/*  99 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE_MEAL).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))).setWeight(1))
/* 100 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TUFF).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(5.0F, 10.0F))).setWeight(1))
/* 101 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TORCH).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 6.0F))).setWeight(1))
/* 102 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTION).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.REGENERATION)))
/* 103 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTION).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.STRENGTH)))
/* 104 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STONE_PICKAXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.15F, 0.8F))).setWeight(2))
/* 105 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MILK_BUCKET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_ENTRANCE, 
/* 112 */         LootTable.lootTable()
/* 113 */         .withPool(LootPool.lootPool()
/* 114 */           .setRolls((NumberProvider)UniformGenerator.between(2.0F, 3.0F))
/* 115 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TRIAL_KEY).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).setWeight(1))
/* 116 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STICK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))).setWeight(5))
/* 117 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WOODEN_AXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).setWeight(10))
/* 118 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.HONEYCOMB).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 8.0F))).setWeight(10))
/* 119 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(5.0F, 10.0F))).setWeight(10))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION, 
/* 126 */         LootTable.lootTable()
/* 127 */         .withPool(LootPool.lootPool()
/* 128 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 3.0F))
/* 129 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_BLOCK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).setWeight(1))
/* 130 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD_BLOCK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))).setWeight(5))
/* 131 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_AXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.1F, 0.5F))).setWeight(5))
/* 132 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_PICKAXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.1F, 0.5F))).setWeight(5))
/* 133 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))).setWeight(10))
/* 134 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CAKE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))).setWeight(20))
/* 135 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.AMETHYST_SHARD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(8.0F, 20.0F))).setWeight(20))
/* 136 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_BLOCK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))).setWeight(20))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION_BARREL, 
/* 143 */         LootTable.lootTable()
/* 144 */         .withPool(LootPool.lootPool()
/* 145 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 3.0F))
/* 146 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_AXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.4F, 0.9F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()).setWeight(1))
/* 147 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_PICKAXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.15F, 0.8F))).setWeight(1))
/* 148 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))).setWeight(1))
/* 149 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COMPASS).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.15F, 0.8F))).setWeight(1))
/* 150 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BUCKET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))).setWeight(1))
/* 151 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_AXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.15F, 0.8F))).setWeight(4))
/* 152 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_PICKAXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.15F, 0.8F))).setWeight(4))
/* 153 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BAMBOO_PLANKS).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(5.0F, 15.0F))).setWeight(10))
/* 154 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BAKED_POTATO).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(6.0F, 10.0F))).setWeight(10))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR, 
/* 162 */         LootTable.lootTable()
/* 163 */         .withPool(LootPool.lootPool()
/* 164 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 3.0F))
/* 165 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_AXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.4F, 0.9F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()).setWeight(1))
/* 166 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.HONEYCOMB).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 8.0F))).setWeight(1))
/* 167 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STONE_AXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.15F, 0.8F))).setWeight(2))
/* 168 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STONE_PICKAXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.15F, 0.8F))).setWeight(2))
/* 169 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENDER_PEARL).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))).setWeight(2))
/* 170 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BAMBOO_HANGING_SIGN).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))).setWeight(2))
/* 171 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BAMBOO_PLANKS).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 6.0F))).setWeight(2))
/* 172 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SCAFFOLDING).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 10.0F))).setWeight(2))
/* 173 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TORCH).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 6.0F))).setWeight(2))
/* 174 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TUFF).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(8.0F, 20.0F))).setWeight(3))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_REWARD, 
/* 180 */         LootTable.lootTable()
/* 181 */         .withPool(LootPool.lootPool()
/* 182 */           .setRolls((NumberProvider)UniformGenerator.between(2.0F, 6.0F))
/* 183 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(8).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 6.0F))))
/* 184 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HORSE_ARMOR).setWeight(8))
/*     */           
/* 186 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SHIELD).setWeight(6).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.15F, 0.8F))))
/* 187 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_BOOTS).setWeight(6).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(10.0F, 20.0F)).allowTreasure()))
/* 188 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_CHESTPLATE).setWeight(6).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(10.0F, 20.0F)).allowTreasure()))
/* 189 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_LEGGINGS).setWeight(6).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(10.0F, 20.0F)).allowTreasure()))
/* 190 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HELMET).setWeight(6).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(10.0F, 20.0F)).allowTreasure()))
/* 191 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_AXE).setWeight(6).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(10.0F, 20.0F)).allowTreasure()))
/* 192 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_PICKAXE).setWeight(6).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(10.0F, 20.0F)).allowTreasure()))
/* 193 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_SHOVEL).setWeight(6).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(10.0F, 20.0F)).allowTreasure()))
/* 194 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(6))
/* 195 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HORSE_ARMOR).setWeight(6))
/*     */           
/* 197 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_AXE).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(10.0F, 20.0F)).allowTreasure()))
/* 198 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CROSSBOW).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(10.0F, 20.0F)).allowTreasure()))
/* 199 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_CHESTPLATE).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(10.0F, 20.0F)).allowTreasure()))
/* 200 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HORSE_ARMOR).setWeight(3))
/* 201 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENCHANTED_GOLDEN_APPLE).setWeight(3))
/*     */           
/* 203 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(12).apply((LootItemFunction.Builder)(new EnchantRandomlyFunction.Builder())
/* 204 */               .withEnchantment(Enchantments.SHARPNESS)
/* 205 */               .withEnchantment(Enchantments.BANE_OF_ARTHROPODS)
/* 206 */               .withEnchantment(Enchantments.BLOCK_EFFICIENCY)
/* 207 */               .withEnchantment(Enchantments.BLOCK_FORTUNE)
/* 208 */               .withEnchantment(Enchantments.SILK_TOUCH)
/* 209 */               .withEnchantment(Enchantments.FALL_PROTECTION)))
/*     */           
/* 211 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*     */           
/* 213 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(5).apply((LootItemFunction.Builder)(new EnchantRandomlyFunction.Builder())
/* 214 */               .withEnchantment(Enchantments.RIPTIDE)
/* 215 */               .withEnchantment(Enchantments.LOYALTY)
/* 216 */               .withEnchantment(Enchantments.CHANNELING)
/* 217 */               .withEnchantment(Enchantments.IMPALING)
/* 218 */               .withEnchantment(Enchantments.MENDING)))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     $$0.accept(BuiltInLootTables.SPAWNER_TRIAL_CHAMBER_KEY, 
/* 225 */         LootTable.lootTable()
/* 226 */         .withPool(LootPool.lootPool()
/* 227 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 228 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TRIAL_KEY))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     $$0.accept(BuiltInLootTables.SPAWNER_TRIAL_CHAMBER_CONSUMABLES, 
/* 236 */         LootTable.lootTable()
/* 237 */         .withPool(LootPool.lootPool()
/* 238 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 239 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 6.0F))))
/* 240 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_CARROT).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 241 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BAKED_POTATO).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 242 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GLOW_BERRIES).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 10.0F))))
/* 243 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENDER_PEARL).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/* 244 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTION).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.REGENERATION)))
/* 245 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTION).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.STRENGTH)))));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\packs\UpdateOneTwentyOneChestLoot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */