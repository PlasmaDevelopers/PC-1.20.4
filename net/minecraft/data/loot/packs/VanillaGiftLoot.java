/*     */ package net.minecraft.data.loot.packs;
/*     */ 
/*     */ import java.util.function.BiConsumer;
/*     */ import net.minecraft.data.loot.LootTableSubProvider;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.LootPool;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootItem;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
/*     */ 
/*     */ public class VanillaGiftLoot implements LootTableSubProvider {
/*     */   public void generate(BiConsumer<ResourceLocation, LootTable.Builder> $$0) {
/*  23 */     $$0.accept(BuiltInLootTables.CAT_MORNING_GIFT, 
/*  24 */         LootTable.lootTable()
/*  25 */         .withPool(LootPool.lootPool()
/*  26 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  27 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.RABBIT_HIDE).setWeight(10))
/*  28 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.RABBIT_FOOT).setWeight(10))
/*  29 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CHICKEN).setWeight(10))
/*  30 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FEATHER).setWeight(10))
/*  31 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(10))
/*  32 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).setWeight(10))
/*  33 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PHANTOM_MEMBRANE).setWeight(2))));
/*     */ 
/*     */     
/*  36 */     $$0.accept(BuiltInLootTables.ARMORER_GIFT, 
/*  37 */         LootTable.lootTable()
/*  38 */         .withPool(LootPool.lootPool()
/*  39 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  40 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CHAINMAIL_HELMET))
/*  41 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CHAINMAIL_CHESTPLATE))
/*  42 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CHAINMAIL_LEGGINGS))
/*  43 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CHAINMAIL_BOOTS))));
/*     */ 
/*     */     
/*  46 */     $$0.accept(BuiltInLootTables.BUTCHER_GIFT, 
/*  47 */         LootTable.lootTable()
/*  48 */         .withPool(LootPool.lootPool()
/*  49 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  50 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COOKED_RABBIT))
/*  51 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COOKED_CHICKEN))
/*  52 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COOKED_PORKCHOP))
/*  53 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COOKED_BEEF))
/*  54 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COOKED_MUTTON))));
/*     */ 
/*     */     
/*  57 */     $$0.accept(BuiltInLootTables.CARTOGRAPHER_GIFT, 
/*  58 */         LootTable.lootTable()
/*  59 */         .withPool(LootPool.lootPool()
/*  60 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  61 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MAP))
/*  62 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PAPER))));
/*     */ 
/*     */     
/*  65 */     $$0.accept(BuiltInLootTables.CLERIC_GIFT, 
/*  66 */         LootTable.lootTable()
/*  67 */         .withPool(LootPool.lootPool()
/*  68 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  69 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.REDSTONE))
/*  70 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LAPIS_LAZULI))));
/*     */ 
/*     */     
/*  73 */     $$0.accept(BuiltInLootTables.FARMER_GIFT, 
/*  74 */         LootTable.lootTable()
/*  75 */         .withPool(LootPool.lootPool()
/*  76 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  77 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD))
/*  78 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PUMPKIN_PIE))
/*  79 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COOKIE))));
/*     */ 
/*     */ 
/*     */     
/*  83 */     $$0.accept(BuiltInLootTables.FISHERMAN_GIFT, 
/*  84 */         LootTable.lootTable()
/*  85 */         .withPool(LootPool.lootPool()
/*  86 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  87 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COD))
/*  88 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SALMON))));
/*     */ 
/*     */     
/*  91 */     $$0.accept(BuiltInLootTables.FLETCHER_GIFT, 
/*  92 */         LootTable.lootTable()
/*  93 */         .withPool(LootPool.lootPool()
/*  94 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  95 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).setWeight(26))
/*  96 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.SWIFTNESS)))
/*  97 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.SLOWNESS)))
/*  98 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.STRENGTH)))
/*  99 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.HEALING)))
/* 100 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.HARMING)))
/* 101 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.LEAPING)))
/* 102 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.REGENERATION)))
/* 103 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.FIRE_RESISTANCE)))
/* 104 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.WATER_BREATHING)))
/* 105 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.INVISIBILITY)))
/* 106 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.NIGHT_VISION)))
/* 107 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.WEAKNESS)))
/* 108 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.POISON)))));
/*     */ 
/*     */     
/* 111 */     $$0.accept(BuiltInLootTables.LEATHERWORKER_GIFT, 
/* 112 */         LootTable.lootTable()
/* 113 */         .withPool(LootPool.lootPool()
/* 114 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 115 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER))));
/*     */ 
/*     */     
/* 118 */     $$0.accept(BuiltInLootTables.LIBRARIAN_GIFT, 
/* 119 */         LootTable.lootTable()
/* 120 */         .withPool(LootPool.lootPool()
/* 121 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 122 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK))));
/*     */ 
/*     */     
/* 125 */     $$0.accept(BuiltInLootTables.MASON_GIFT, 
/* 126 */         LootTable.lootTable()
/* 127 */         .withPool(LootPool.lootPool()
/* 128 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 129 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CLAY))));
/*     */ 
/*     */     
/* 132 */     $$0.accept(BuiltInLootTables.SHEPHERD_GIFT, 
/* 133 */         LootTable.lootTable()
/* 134 */         .withPool(LootPool.lootPool()
/* 135 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 136 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHITE_WOOL))
/* 137 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ORANGE_WOOL))
/* 138 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MAGENTA_WOOL))
/* 139 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LIGHT_BLUE_WOOL))
/* 140 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.YELLOW_WOOL))
/* 141 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LIME_WOOL))
/* 142 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PINK_WOOL))
/* 143 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GRAY_WOOL))
/* 144 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LIGHT_GRAY_WOOL))
/* 145 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CYAN_WOOL))
/* 146 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PURPLE_WOOL))
/* 147 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BLUE_WOOL))
/* 148 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BROWN_WOOL))
/* 149 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GREEN_WOOL))
/* 150 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.RED_WOOL))
/* 151 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BLACK_WOOL))));
/*     */ 
/*     */     
/* 154 */     $$0.accept(BuiltInLootTables.TOOLSMITH_GIFT, 
/* 155 */         LootTable.lootTable()
/* 156 */         .withPool(LootPool.lootPool()
/* 157 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 158 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STONE_PICKAXE))
/* 159 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STONE_AXE))
/* 160 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STONE_HOE))
/* 161 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STONE_SHOVEL))));
/*     */ 
/*     */     
/* 164 */     $$0.accept(BuiltInLootTables.WEAPONSMITH_GIFT, 
/* 165 */         LootTable.lootTable()
/* 166 */         .withPool(LootPool.lootPool()
/* 167 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 168 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STONE_AXE))
/* 169 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_AXE))
/* 170 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_AXE))));
/*     */ 
/*     */     
/* 173 */     $$0.accept(BuiltInLootTables.SNIFFER_DIGGING, 
/* 174 */         LootTable.lootTable()
/* 175 */         .withPool(LootPool.lootPool()
/* 176 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 177 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TORCHFLOWER_SEEDS))
/* 178 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PITCHER_POD))));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\packs\VanillaGiftLoot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */