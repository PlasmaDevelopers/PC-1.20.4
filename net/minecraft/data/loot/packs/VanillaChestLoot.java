/*      */ package net.minecraft.data.loot.packs;
/*      */ 
/*      */ import java.util.function.BiConsumer;
/*      */ import net.minecraft.data.loot.LootTableSubProvider;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.tags.InstrumentTags;
/*      */ import net.minecraft.tags.StructureTags;
/*      */ import net.minecraft.world.effect.MobEffects;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.alchemy.Potions;
/*      */ import net.minecraft.world.item.enchantment.Enchantments;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.saveddata.maps.MapDecoration;
/*      */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*      */ import net.minecraft.world.level.storage.loot.LootPool;
/*      */ import net.minecraft.world.level.storage.loot.LootTable;
/*      */ import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
/*      */ import net.minecraft.world.level.storage.loot.entries.LootItem;
/*      */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*      */ import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
/*      */ import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
/*      */ import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
/*      */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*      */ import net.minecraft.world.level.storage.loot.functions.SetInstrumentFunction;
/*      */ import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
/*      */ import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
/*      */ import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
/*      */ import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
/*      */ import net.minecraft.world.level.storage.loot.functions.SetStewEffectFunction;
/*      */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*      */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*      */ import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
/*      */ 
/*      */ public class VanillaChestLoot
/*      */   implements LootTableSubProvider {
/*      */   public void generate(BiConsumer<ResourceLocation, LootTable.Builder> $$0) {
/*   39 */     $$0.accept(BuiltInLootTables.ABANDONED_MINESHAFT, 
/*   40 */         LootTable.lootTable()
/*   41 */         .withPool(LootPool.lootPool()
/*   42 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*   43 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_APPLE).setWeight(20))
/*   44 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENCHANTED_GOLDEN_APPLE))
/*   45 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NAME_TAG).setWeight(30))
/*   46 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(10).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*   47 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_PICKAXE).setWeight(5))
/*   48 */           .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(5)))
/*      */         
/*   50 */         .withPool(LootPool.lootPool()
/*   51 */           .setRolls((NumberProvider)UniformGenerator.between(2.0F, 4.0F))
/*   52 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*   53 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*   54 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.REDSTONE).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 9.0F))))
/*   55 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LAPIS_LAZULI).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 9.0F))))
/*   56 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))
/*   57 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 8.0F))))
/*   58 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*   59 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GLOW_BERRIES).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 6.0F))))
/*   60 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MELON_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))))
/*   61 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PUMPKIN_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))))
/*   62 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BEETROOT_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F)))))
/*      */         
/*   64 */         .withPool(LootPool.lootPool()
/*   65 */           .setRolls((NumberProvider)ConstantValue.exactly(3.0F))
/*   66 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.RAIL).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 8.0F))))
/*   67 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.POWERED_RAIL).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*   68 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.DETECTOR_RAIL).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*   69 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.ACTIVATOR_RAIL).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*   70 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.TORCH).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 16.0F))))));
/*      */ 
/*      */ 
/*      */     
/*   74 */     $$0.accept(BuiltInLootTables.BASTION_BRIDGE, bastionBridgeLootTable());
/*   75 */     $$0.accept(BuiltInLootTables.BASTION_HOGLIN_STABLE, bastionHoglinStableLootTable());
/*   76 */     $$0.accept(BuiltInLootTables.BASTION_OTHER, bastionOtherLootTable());
/*   77 */     $$0.accept(BuiltInLootTables.BASTION_TREASURE, bastionTreasureLootTable());
/*      */     
/*   79 */     $$0.accept(BuiltInLootTables.BURIED_TREASURE, 
/*   80 */         LootTable.lootTable()
/*   81 */         .withPool(LootPool.lootPool()
/*   82 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*   83 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.HEART_OF_THE_SEA)))
/*      */         
/*   85 */         .withPool(LootPool.lootPool()
/*   86 */           .setRolls((NumberProvider)UniformGenerator.between(5.0F, 8.0F))
/*   87 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*   88 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*   89 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.TNT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F)))))
/*      */         
/*   91 */         .withPool(LootPool.lootPool()
/*   92 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 3.0F))
/*   93 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 8.0F))))
/*   94 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))
/*   95 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PRISMARINE_CRYSTALS).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F)))))
/*      */         
/*   97 */         .withPool(LootPool.lootPool()
/*   98 */           .setRolls((NumberProvider)UniformGenerator.between(0.0F, 1.0F))
/*   99 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER_CHESTPLATE))
/*  100 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_SWORD)))
/*      */         
/*  102 */         .withPool(LootPool.lootPool()
/*  103 */           .setRolls((NumberProvider)ConstantValue.exactly(2.0F))
/*  104 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COOKED_COD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))))
/*  105 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COOKED_SALMON).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F)))))
/*      */         
/*  107 */         .withPool(LootPool.lootPool()
/*  108 */           .setRolls((NumberProvider)UniformGenerator.between(0.0F, 2.0F))
/*  109 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTION)).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.WATER_BREATHING))));
/*      */ 
/*      */ 
/*      */     
/*  113 */     $$0.accept(BuiltInLootTables.ANCIENT_CITY, ancientCityLootTable());
/*      */     
/*  115 */     $$0.accept(BuiltInLootTables.ANCIENT_CITY_ICE_BOX, 
/*  116 */         LootTable.lootTable()
/*  117 */         .withPool(LootPool.lootPool()
/*  118 */           .setRolls((NumberProvider)UniformGenerator.between(4.0F, 10.0F))
/*      */ 
/*      */           
/*  121 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SUSPICIOUS_STEW).setWeight(1).apply((LootItemFunction.Builder)SetStewEffectFunction.stewEffect()
/*  122 */               .withEffect(MobEffects.NIGHT_VISION, (NumberProvider)UniformGenerator.between(7.0F, 10.0F))
/*  123 */               .withEffect(MobEffects.BLINDNESS, (NumberProvider)UniformGenerator.between(5.0F, 7.0F)))
/*  124 */             .apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 6.0F))))
/*  125 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_CARROT).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 10.0F))))
/*  126 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BAKED_POTATO).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 10.0F))))
/*      */ 
/*      */           
/*  129 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PACKED_ICE).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 6.0F))))
/*      */ 
/*      */           
/*  132 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SNOWBALL).setWeight(4).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 6.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  136 */     $$0.accept(BuiltInLootTables.DESERT_PYRAMID, desertPyramidLootTable());
/*  137 */     $$0.accept(BuiltInLootTables.END_CITY_TREASURE, endCityTreasureLootTable());
/*      */     
/*  139 */     $$0.accept(BuiltInLootTables.IGLOO_CHEST, 
/*  140 */         LootTable.lootTable()
/*  141 */         .withPool(LootPool.lootPool()
/*  142 */           .setRolls((NumberProvider)UniformGenerator.between(2.0F, 8.0F))
/*  143 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.APPLE).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  144 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  145 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  146 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STONE_AXE).setWeight(2))
/*  147 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(10))
/*  148 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD))
/*  149 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 3.0F)))))
/*      */         
/*  151 */         .withPool(LootPool.lootPool()
/*  152 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  153 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_APPLE))));
/*      */ 
/*      */ 
/*      */     
/*  157 */     $$0.accept(BuiltInLootTables.JUNGLE_TEMPLE, jungleTempleLootTable());
/*      */     
/*  159 */     $$0.accept(BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER, 
/*  160 */         LootTable.lootTable()
/*  161 */         .withPool(LootPool.lootPool()
/*  162 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 2.0F))
/*  163 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).setWeight(30).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  167 */     $$0.accept(BuiltInLootTables.NETHER_BRIDGE, netherBridgeLootTable());
/*      */     
/*  169 */     $$0.accept(BuiltInLootTables.PILLAGER_OUTPOST, pillagerOutpostLootTable());
/*      */     
/*  171 */     $$0.accept(BuiltInLootTables.SHIPWRECK_MAP, shipwreckMapLootTable());
/*      */     
/*  173 */     $$0.accept(BuiltInLootTables.SHIPWRECK_SUPPLY, shipwreckSupplyLootTable());
/*      */     
/*  175 */     $$0.accept(BuiltInLootTables.SHIPWRECK_TREASURE, shipwreckTreasureLootTable());
/*      */     
/*  177 */     $$0.accept(BuiltInLootTables.SIMPLE_DUNGEON, 
/*  178 */         LootTable.lootTable()
/*  179 */         .withPool(LootPool.lootPool()
/*  180 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 3.0F))
/*  181 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(20))
/*  182 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_APPLE).setWeight(15))
/*  183 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
/*  184 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_OTHERSIDE).setWeight(2))
/*  185 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_13).setWeight(15))
/*  186 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_CAT).setWeight(15))
/*  187 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NAME_TAG).setWeight(20))
/*  188 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HORSE_ARMOR).setWeight(10))
/*  189 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HORSE_ARMOR).setWeight(15))
/*  190 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HORSE_ARMOR).setWeight(5))
/*  191 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(10).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment())))
/*      */         
/*  193 */         .withPool(LootPool.lootPool()
/*  194 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 4.0F))
/*  195 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  196 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  197 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(20))
/*  198 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  199 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BUCKET).setWeight(10))
/*  200 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.REDSTONE).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  201 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  202 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MELON_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))))
/*  203 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PUMPKIN_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))))
/*  204 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BEETROOT_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F)))))
/*      */         
/*  206 */         .withPool(LootPool.lootPool()
/*  207 */           .setRolls((NumberProvider)ConstantValue.exactly(3.0F))
/*  208 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/*  209 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GUNPOWDER).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/*  210 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/*  211 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  215 */     $$0.accept(BuiltInLootTables.SPAWN_BONUS_CHEST, 
/*  216 */         LootTable.lootTable()
/*  217 */         .withPool(LootPool.lootPool()
/*  218 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  219 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STONE_AXE))
/*  220 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WOODEN_AXE).setWeight(3)))
/*      */         
/*  222 */         .withPool(LootPool.lootPool()
/*  223 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  224 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STONE_PICKAXE))
/*  225 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WOODEN_PICKAXE).setWeight(3)))
/*      */         
/*  227 */         .withPool(LootPool.lootPool()
/*  228 */           .setRolls((NumberProvider)ConstantValue.exactly(3.0F))
/*  229 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.APPLE).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))
/*  230 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))
/*  231 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SALMON).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F)))))
/*      */         
/*  233 */         .withPool(LootPool.lootPool()
/*  234 */           .setRolls((NumberProvider)ConstantValue.exactly(4.0F))
/*  235 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STICK).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 12.0F))))
/*  236 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.OAK_PLANKS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 12.0F))))
/*  237 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.OAK_LOG).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  238 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.SPRUCE_LOG).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  239 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.BIRCH_LOG).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  240 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.JUNGLE_LOG).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  241 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.ACACIA_LOG).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  242 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.DARK_OAK_LOG).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  243 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.MANGROVE_LOG).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  247 */     $$0.accept(BuiltInLootTables.STRONGHOLD_CORRIDOR, strongholdCorridorLootTable());
/*      */     
/*  249 */     $$0.accept(BuiltInLootTables.STRONGHOLD_CROSSING, 
/*  250 */         LootTable.lootTable()
/*  251 */         .withPool(LootPool.lootPool()
/*  252 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 4.0F))
/*  253 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  254 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  255 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.REDSTONE).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 9.0F))))
/*  256 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 8.0F))))
/*  257 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  258 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.APPLE).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  259 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_PICKAXE))
/*  260 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)ConstantValue.exactly(30.0F)).allowTreasure()))));
/*      */ 
/*      */ 
/*      */     
/*  264 */     $$0.accept(BuiltInLootTables.STRONGHOLD_LIBRARY, strongholdLibraryLootTable());
/*      */     
/*  266 */     $$0.accept(BuiltInLootTables.UNDERWATER_RUIN_BIG, 
/*  267 */         LootTable.lootTable()
/*  268 */         .withPool(LootPool.lootPool()
/*  269 */           .setRolls((NumberProvider)UniformGenerator.between(2.0F, 8.0F))
/*  270 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  271 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  272 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD))
/*  273 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 3.0F)))))
/*      */         
/*  275 */         .withPool(LootPool.lootPool()
/*  276 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  277 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_APPLE))
/*  278 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(5).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  279 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER_CHESTPLATE))
/*  280 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HELMET))
/*  281 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FISHING_ROD).setWeight(5).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  282 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MAP).setWeight(10).apply((LootItemFunction.Builder)ExplorationMapFunction.makeExplorationMap().setDestination(StructureTags.ON_TREASURE_MAPS).setMapDecoration(MapDecoration.Type.RED_X).setZoom((byte)1).setSkipKnownStructures(false)).apply((LootItemFunction.Builder)SetNameFunction.setName((Component)Component.translatable("filled_map.buried_treasure"))))));
/*      */ 
/*      */ 
/*      */     
/*  286 */     $$0.accept(BuiltInLootTables.UNDERWATER_RUIN_SMALL, 
/*  287 */         LootTable.lootTable()
/*  288 */         .withPool(LootPool.lootPool()
/*  289 */           .setRolls((NumberProvider)UniformGenerator.between(2.0F, 8.0F))
/*  290 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  291 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STONE_AXE).setWeight(2))
/*  292 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(5))
/*  293 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD))
/*  294 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 3.0F)))))
/*      */         
/*  296 */         .withPool(LootPool.lootPool()
/*  297 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  298 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER_CHESTPLATE))
/*  299 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HELMET))
/*  300 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FISHING_ROD).setWeight(5).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  301 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MAP).setWeight(5).apply((LootItemFunction.Builder)ExplorationMapFunction.makeExplorationMap().setDestination(StructureTags.ON_TREASURE_MAPS).setMapDecoration(MapDecoration.Type.RED_X).setZoom((byte)1).setSkipKnownStructures(false)).apply((LootItemFunction.Builder)SetNameFunction.setName((Component)Component.translatable("filled_map.buried_treasure"))))));
/*      */ 
/*      */ 
/*      */     
/*  305 */     $$0.accept(BuiltInLootTables.VILLAGE_WEAPONSMITH, 
/*  306 */         LootTable.lootTable()
/*  307 */         .withPool(LootPool.lootPool()
/*  308 */           .setRolls((NumberProvider)UniformGenerator.between(3.0F, 8.0F))
/*  309 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  310 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  311 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  312 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  313 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.APPLE).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  314 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_PICKAXE).setWeight(5))
/*  315 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_SWORD).setWeight(5))
/*  316 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_CHESTPLATE).setWeight(5))
/*  317 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HELMET).setWeight(5))
/*  318 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_LEGGINGS).setWeight(5))
/*  319 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_BOOTS).setWeight(5))
/*  320 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.OBSIDIAN).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 7.0F))))
/*  321 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.OAK_SAPLING).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 7.0F))))
/*  322 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(3))
/*  323 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HORSE_ARMOR))
/*  324 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HORSE_ARMOR))
/*  325 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HORSE_ARMOR))));
/*      */ 
/*      */ 
/*      */     
/*  329 */     $$0.accept(BuiltInLootTables.VILLAGE_TOOLSMITH, 
/*  330 */         LootTable.lootTable()
/*  331 */         .withPool(LootPool.lootPool()
/*  332 */           .setRolls((NumberProvider)UniformGenerator.between(3.0F, 8.0F))
/*  333 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  334 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  335 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  336 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  337 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_PICKAXE).setWeight(5))
/*  338 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  339 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STICK).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  340 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_SHOVEL).setWeight(5))));
/*      */ 
/*      */ 
/*      */     
/*  344 */     $$0.accept(BuiltInLootTables.VILLAGE_CARTOGRAPHER, 
/*  345 */         LootTable.lootTable()
/*  346 */         .withPool(LootPool.lootPool()
/*  347 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 5.0F))
/*  348 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MAP).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  349 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PAPER).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  350 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COMPASS).setWeight(5))
/*  351 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  352 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STICK).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  356 */     $$0.accept(BuiltInLootTables.VILLAGE_MASON, 
/*  357 */         LootTable.lootTable()
/*  358 */         .withPool(LootPool.lootPool()
/*  359 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 5.0F))
/*  360 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CLAY_BALL).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  361 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FLOWER_POT).setWeight(1))
/*  362 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.STONE).setWeight(2))
/*  363 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.STONE_BRICKS).setWeight(2))
/*  364 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(4).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  365 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.YELLOW_DYE).setWeight(1))
/*  366 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.SMOOTH_STONE).setWeight(1))
/*  367 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(1))));
/*      */ 
/*      */ 
/*      */     
/*  371 */     $$0.accept(BuiltInLootTables.VILLAGE_ARMORER, 
/*  372 */         LootTable.lootTable()
/*  373 */         .withPool(LootPool.lootPool()
/*  374 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 5.0F))
/*  375 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  376 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(4).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  377 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HELMET).setWeight(1))
/*  378 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(1))));
/*      */ 
/*      */ 
/*      */     
/*  382 */     $$0.accept(BuiltInLootTables.VILLAGE_SHEPHERD, 
/*  383 */         LootTable.lootTable()
/*  384 */         .withPool(LootPool.lootPool()
/*  385 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 5.0F))
/*  386 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.WHITE_WOOL).setWeight(6).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/*  387 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.BLACK_WOOL).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  388 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.GRAY_WOOL).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  389 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.BROWN_WOOL).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  390 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.LIGHT_GRAY_WOOL).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  391 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(1))
/*  392 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SHEARS).setWeight(1))
/*  393 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(6).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 6.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  397 */     $$0.accept(BuiltInLootTables.VILLAGE_BUTCHER, 
/*  398 */         LootTable.lootTable()
/*  399 */         .withPool(LootPool.lootPool()
/*  400 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 5.0F))
/*  401 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(1))
/*  402 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PORKCHOP).setWeight(6).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  403 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(6).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  404 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BEEF).setWeight(6).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  405 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUTTON).setWeight(6).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  406 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  410 */     $$0.accept(BuiltInLootTables.VILLAGE_FLETCHER, 
/*  411 */         LootTable.lootTable()
/*  412 */         .withPool(LootPool.lootPool()
/*  413 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 5.0F))
/*  414 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(1))
/*  415 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  416 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FEATHER).setWeight(6).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  417 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EGG).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  418 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FLINT).setWeight(6).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  419 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STICK).setWeight(6).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  423 */     $$0.accept(BuiltInLootTables.VILLAGE_FISHER, 
/*  424 */         LootTable.lootTable()
/*  425 */         .withPool(LootPool.lootPool()
/*  426 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 5.0F))
/*  427 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(1))
/*  428 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COD).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  429 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SALMON).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  430 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WATER_BUCKET).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  431 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BARREL).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  432 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT_SEEDS).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  433 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  437 */     $$0.accept(BuiltInLootTables.VILLAGE_TANNERY, 
/*  438 */         LootTable.lootTable()
/*  439 */         .withPool(LootPool.lootPool()
/*  440 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 5.0F))
/*  441 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  442 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER_CHESTPLATE).setWeight(2))
/*  443 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER_BOOTS).setWeight(2))
/*  444 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER_HELMET).setWeight(2))
/*  445 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  446 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER_LEGGINGS).setWeight(2))
/*  447 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(1))
/*  448 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  452 */     $$0.accept(BuiltInLootTables.VILLAGE_TEMPLE, 
/*  453 */         LootTable.lootTable()
/*  454 */         .withPool(LootPool.lootPool()
/*  455 */           .setRolls((NumberProvider)UniformGenerator.between(3.0F, 8.0F))
/*  456 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.REDSTONE).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  457 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(7).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  458 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(7).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  459 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LAPIS_LAZULI).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  460 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  461 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  465 */     $$0.accept(BuiltInLootTables.VILLAGE_PLAINS_HOUSE, 
/*  466 */         LootTable.lootTable()
/*  467 */         .withPool(LootPool.lootPool()
/*  468 */           .setRolls((NumberProvider)UniformGenerator.between(3.0F, 8.0F))
/*  469 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  470 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DANDELION).setWeight(2))
/*  471 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POPPY).setWeight(1))
/*  472 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTATO).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 7.0F))))
/*  473 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  474 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.APPLE).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  475 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(1))
/*  476 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FEATHER).setWeight(1))
/*  477 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  478 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.OAK_SAPLING).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  482 */     $$0.accept(BuiltInLootTables.VILLAGE_TAIGA_HOUSE, 
/*  483 */         LootTable.lootTable()
/*  484 */         .withPool(LootPool.lootPool()
/*  485 */           .setRolls((NumberProvider)UniformGenerator.between(3.0F, 8.0F))
/*  486 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_NUGGET).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  487 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FERN).setWeight(2))
/*  488 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LARGE_FERN).setWeight(2))
/*  489 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTATO).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 7.0F))))
/*  490 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SWEET_BERRIES).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 7.0F))))
/*  491 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  492 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PUMPKIN_SEEDS).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  493 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PUMPKIN_PIE).setWeight(1))
/*  494 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  495 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.SPRUCE_SAPLING).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  496 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPRUCE_SIGN).setWeight(1))
/*  497 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPRUCE_LOG).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  501 */     $$0.accept(BuiltInLootTables.VILLAGE_SAVANNA_HOUSE, 
/*  502 */         LootTable.lootTable()
/*  503 */         .withPool(LootPool.lootPool()
/*  504 */           .setRolls((NumberProvider)UniformGenerator.between(3.0F, 8.0F))
/*  505 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  506 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SHORT_GRASS).setWeight(5))
/*  507 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TALL_GRASS).setWeight(5))
/*  508 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  509 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  510 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  511 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.ACACIA_SAPLING).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))
/*  512 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(1))
/*  513 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.TORCH).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))
/*  514 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BUCKET).setWeight(1))));
/*      */ 
/*      */ 
/*      */     
/*  518 */     $$0.accept(BuiltInLootTables.VILLAGE_SNOWY_HOUSE, 
/*  519 */         LootTable.lootTable()
/*  520 */         .withPool(LootPool.lootPool()
/*  521 */           .setRolls((NumberProvider)UniformGenerator.between(3.0F, 8.0F))
/*  522 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.BLUE_ICE).setWeight(1))
/*  523 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.SNOW_BLOCK).setWeight(4))
/*  524 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTATO).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 7.0F))))
/*  525 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  526 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BEETROOT_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  527 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BEETROOT_SOUP).setWeight(1))
/*  528 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FURNACE).setWeight(1))
/*  529 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  530 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SNOWBALL).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 7.0F))))
/*  531 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  535 */     $$0.accept(BuiltInLootTables.VILLAGE_DESERT_HOUSE, 
/*  536 */         LootTable.lootTable()
/*  537 */         .withPool(LootPool.lootPool()
/*  538 */           .setRolls((NumberProvider)UniformGenerator.between(3.0F, 8.0F))
/*  539 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CLAY_BALL).setWeight(1))
/*  540 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GREEN_DYE).setWeight(1))
/*  541 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.CACTUS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  542 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 7.0F))))
/*  543 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  544 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(1))
/*  545 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.DEAD_BUSH).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  546 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  550 */     $$0.accept(BuiltInLootTables.WOODLAND_MANSION, woodlandMansionLootTable());
/*      */     
/*  552 */     $$0.accept(BuiltInLootTables.RUINED_PORTAL, 
/*  553 */         LootTable.lootTable()
/*  554 */         .withPool(LootPool.lootPool()
/*  555 */           .setRolls((NumberProvider)UniformGenerator.between(4.0F, 8.0F))
/*  556 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.OBSIDIAN).setWeight(40).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))
/*  557 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FLINT).setWeight(40).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  558 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_NUGGET).setWeight(40).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(9.0F, 18.0F))))
/*  559 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FLINT_AND_STEEL).setWeight(40))
/*  560 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FIRE_CHARGE).setWeight(40))
/*      */           
/*  562 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_APPLE).setWeight(15))
/*  563 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 24.0F))))
/*  564 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_SWORD).setWeight(15).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  565 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_AXE).setWeight(15).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  566 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HOE).setWeight(15).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  567 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_SHOVEL).setWeight(15).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  568 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_PICKAXE).setWeight(15).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  569 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_BOOTS).setWeight(15).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  570 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_CHESTPLATE).setWeight(15).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  571 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HELMET).setWeight(15).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  572 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_LEGGINGS).setWeight(15).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*      */           
/*  574 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GLISTERING_MELON_SLICE).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 12.0F))))
/*  575 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HORSE_ARMOR).setWeight(5))
/*  576 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LIGHT_WEIGHTED_PRESSURE_PLATE).setWeight(5))
/*  577 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_CARROT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 12.0F))))
/*  578 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CLOCK).setWeight(5))
/*  579 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 8.0F))))
/*      */           
/*  581 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BELL).setWeight(1))
/*  582 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENCHANTED_GOLDEN_APPLE).setWeight(1))
/*  583 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_BLOCK).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))));
/*      */ 
/*      */ 
/*      */     
/*  587 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_REWARD, LootTable.lootTable());
/*  588 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_SUPPLY, LootTable.lootTable());
/*  589 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR, LootTable.lootTable());
/*  590 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_ENTRANCE, LootTable.lootTable());
/*  591 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION, LootTable.lootTable());
/*  592 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION_BARREL, LootTable.lootTable());
/*  593 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_CHAMBER_DISPENSER, LootTable.lootTable());
/*  594 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR_DISPENSER, LootTable.lootTable());
/*  595 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_WATER_DISPENSER, LootTable.lootTable());
/*  596 */     $$0.accept(BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR_POT, LootTable.lootTable());
/*      */     
/*  598 */     spawnerLootTables($$0);
/*      */   }
/*      */   
/*      */   public static void spawnerLootTables(BiConsumer<ResourceLocation, LootTable.Builder> $$0) {
/*  602 */     $$0.accept(BuiltInLootTables.SPAWNER_TRIAL_CHAMBER_KEY, LootTable.lootTable());
/*  603 */     $$0.accept(BuiltInLootTables.SPAWNER_TRIAL_CHAMBER_CONSUMABLES, LootTable.lootTable());
/*      */   }
/*      */   
/*      */   public static LootTable.Builder shipwreckSupplyLootTable() {
/*  607 */     return LootTable.lootTable()
/*  608 */       .withPool(LootPool.lootPool()
/*  609 */         .setRolls((NumberProvider)UniformGenerator.between(3.0F, 10.0F))
/*  610 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PAPER).setWeight(8).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 12.0F))))
/*  611 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTATO).setWeight(7).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 6.0F))))
/*  612 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MOSS_BLOCK).setWeight(7).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  613 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POISONOUS_POTATO).setWeight(7).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 6.0F))))
/*  614 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CARROT).setWeight(7).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 8.0F))))
/*  615 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(7).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(8.0F, 21.0F))))
/*  616 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SUSPICIOUS_STEW).setWeight(10).apply((LootItemFunction.Builder)SetStewEffectFunction.stewEffect()
/*  617 */             .withEffect(MobEffects.NIGHT_VISION, (NumberProvider)UniformGenerator.between(7.0F, 10.0F))
/*  618 */             .withEffect(MobEffects.JUMP, (NumberProvider)UniformGenerator.between(7.0F, 10.0F))
/*  619 */             .withEffect(MobEffects.WEAKNESS, (NumberProvider)UniformGenerator.between(6.0F, 8.0F))
/*  620 */             .withEffect(MobEffects.BLINDNESS, (NumberProvider)UniformGenerator.between(5.0F, 7.0F))
/*  621 */             .withEffect(MobEffects.POISON, (NumberProvider)UniformGenerator.between(10.0F, 20.0F))
/*  622 */             .withEffect(MobEffects.SATURATION, (NumberProvider)UniformGenerator.between(7.0F, 10.0F))))
/*      */         
/*  624 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(6).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 8.0F))))
/*  625 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(5.0F, 24.0F))))
/*  626 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.PUMPKIN).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  627 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.BAMBOO).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  628 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GUNPOWDER).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  629 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.TNT).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))
/*  630 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER_HELMET).setWeight(3).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  631 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER_CHESTPLATE).setWeight(3).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  632 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER_LEGGINGS).setWeight(3).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  633 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER_BOOTS).setWeight(3).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment())))
/*      */       
/*  635 */       .withPool(LootPool.lootPool()
/*  636 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  637 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(5))
/*  638 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F)))));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder shipwreckMapLootTable() {
/*  643 */     return LootTable.lootTable()
/*  644 */       .withPool(LootPool.lootPool()
/*  645 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  646 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MAP).apply((LootItemFunction.Builder)ExplorationMapFunction.makeExplorationMap().setDestination(StructureTags.ON_TREASURE_MAPS).setMapDecoration(MapDecoration.Type.RED_X).setZoom((byte)1).setSkipKnownStructures(false)).apply((LootItemFunction.Builder)SetNameFunction.setName((Component)Component.translatable("filled_map.buried_treasure")))))
/*      */       
/*  648 */       .withPool(LootPool.lootPool()
/*  649 */         .setRolls((NumberProvider)ConstantValue.exactly(3.0F))
/*  650 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COMPASS))
/*  651 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MAP))
/*  652 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CLOCK))
/*  653 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PAPER).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 10.0F))))
/*  654 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FEATHER).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  655 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F)))))
/*      */       
/*  657 */       .withPool(LootPool.lootPool()
/*  658 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  659 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(5))
/*  660 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F)))));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder bastionHoglinStableLootTable() {
/*  665 */     return LootTable.lootTable()
/*  666 */       .withPool(LootPool.lootPool()
/*  667 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  668 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_SHOVEL).setWeight(15).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.15F, 0.8F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  669 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_PICKAXE).setWeight(12).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.15F, 0.95F))).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  670 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NETHERITE_SCRAP).setWeight(8).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  671 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ANCIENT_DEBRIS).setWeight(12).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  672 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ANCIENT_DEBRIS).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F))))
/*  673 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(12).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  674 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.GOLD_BLOCK).setWeight(16).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))))
/*  675 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_CARROT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(8.0F, 17.0F))))
/*  676 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_APPLE).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F)))))
/*      */       
/*  678 */       .withPool(LootPool.lootPool()
/*  679 */         .setRolls((NumberProvider)UniformGenerator.between(3.0F, 4.0F))
/*  680 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_AXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  681 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.CRYING_OBSIDIAN).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  682 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.GLOWSTONE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 6.0F))))
/*  683 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.GILDED_BLACKSTONE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))))
/*  684 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.SOUL_SAND).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F))))
/*  685 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.CRIMSON_NYLIUM).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F))))
/*  686 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 8.0F))))
/*  687 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  688 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(5.0F, 17.0F))))
/*  689 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 8.0F))))
/*  690 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PORKCHOP).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))))
/*  691 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COOKED_PORKCHOP).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))))
/*  692 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.CRIMSON_FUNGUS).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F))))
/*  693 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.CRIMSON_ROOTS).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F)))))
/*      */       
/*  695 */       .withPool(LootPool.lootPool()
/*  696 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  697 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(11))
/*  698 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1)))
/*      */       
/*  700 */       .withPool(LootPool.lootPool()
/*  701 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  702 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(9))
/*  703 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(1)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder bastionBridgeLootTable() {
/*  708 */     return LootTable.lootTable()
/*  709 */       .withPool(LootPool.lootPool()
/*  710 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  711 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.LODESTONE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F)))))
/*      */       
/*  713 */       .withPool(LootPool.lootPool()
/*  714 */         .setRolls((NumberProvider)UniformGenerator.between(1.0F, 2.0F))
/*  715 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CROSSBOW).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.1F, 0.5F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  716 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPECTRAL_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(10.0F, 28.0F))))
/*  717 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.GILDED_BLACKSTONE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(8.0F, 12.0F))))
/*  718 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.CRYING_OBSIDIAN).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 8.0F))))
/*  719 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.GOLD_BLOCK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  720 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 9.0F))))
/*  721 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 9.0F))))
/*  722 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_SWORD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  723 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_CHESTPLATE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  724 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HELMET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  725 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_LEGGINGS).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  726 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_BOOTS).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  727 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_AXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment())))
/*      */       
/*  729 */       .withPool(LootPool.lootPool()
/*  730 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 4.0F))
/*  731 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 6.0F))))
/*  732 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  733 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(5.0F, 17.0F))))
/*  734 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_NUGGET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 6.0F))))
/*  735 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 6.0F)))))
/*      */       
/*  737 */       .withPool(LootPool.lootPool()
/*  738 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  739 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(11))
/*  740 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1)))
/*      */       
/*  742 */       .withPool(LootPool.lootPool()
/*  743 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  744 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(9))
/*  745 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(1)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder endCityTreasureLootTable() {
/*  750 */     return LootTable.lootTable()
/*  751 */       .withPool(LootPool.lootPool()
/*  752 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 6.0F))
/*  753 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F))))
/*  754 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 8.0F))))
/*  755 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F))))
/*  756 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 6.0F))))
/*  757 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BEETROOT_SEEDS).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 10.0F))))
/*  758 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(3))
/*  759 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HORSE_ARMOR))
/*  760 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HORSE_ARMOR))
/*  761 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HORSE_ARMOR))
/*  762 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_SWORD).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*  763 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_BOOTS).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*  764 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_CHESTPLATE).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*  765 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_LEGGINGS).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*  766 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HELMET).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*  767 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_PICKAXE).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*  768 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_SHOVEL).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*  769 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_SWORD).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*  770 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_BOOTS).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*  771 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_CHESTPLATE).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*  772 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_LEGGINGS).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*  773 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HELMET).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*  774 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_PICKAXE).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*  775 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_SHOVEL).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure())))
/*      */       
/*  777 */       .withPool(LootPool.lootPool()
/*  778 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  779 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(14))
/*  780 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder netherBridgeLootTable() {
/*  785 */     return LootTable.lootTable()
/*  786 */       .withPool(LootPool.lootPool()
/*  787 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 4.0F))
/*  788 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  789 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  790 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  791 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_SWORD).setWeight(5))
/*  792 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_CHESTPLATE).setWeight(5))
/*  793 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FLINT_AND_STEEL).setWeight(5))
/*  794 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NETHER_WART).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 7.0F))))
/*  795 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(10))
/*  796 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HORSE_ARMOR).setWeight(8))
/*  797 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HORSE_ARMOR).setWeight(5))
/*  798 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HORSE_ARMOR).setWeight(3))
/*  799 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.OBSIDIAN).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F)))))
/*      */       
/*  801 */       .withPool(LootPool.lootPool()
/*  802 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  803 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(14))
/*  804 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder bastionTreasureLootTable() {
/*  809 */     return LootTable.lootTable()
/*  810 */       .withPool(LootPool.lootPool()
/*  811 */         .setRolls((NumberProvider)ConstantValue.exactly(3.0F))
/*  812 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NETHERITE_INGOT).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  813 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.ANCIENT_DEBRIS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  814 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NETHERITE_SCRAP).setWeight(8).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  815 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.ANCIENT_DEBRIS).setWeight(4).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F))))
/*  816 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_SWORD).setWeight(6).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.8F, 1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  817 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_CHESTPLATE).setWeight(6).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.8F, 1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  818 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HELMET).setWeight(6).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.8F, 1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  819 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_LEGGINGS).setWeight(6).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.8F, 1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  820 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_BOOTS).setWeight(6).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.8F, 1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  821 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_SWORD).setWeight(6))
/*  822 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_CHESTPLATE).setWeight(5))
/*  823 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HELMET).setWeight(5))
/*  824 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_BOOTS).setWeight(5))
/*  825 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_LEGGINGS).setWeight(5))
/*  826 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 6.0F))))
/*  827 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENCHANTED_GOLDEN_APPLE).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F)))))
/*      */       
/*  829 */       .withPool(LootPool.lootPool()
/*  830 */         .setRolls((NumberProvider)UniformGenerator.between(3.0F, 4.0F))
/*  831 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPECTRAL_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(12.0F, 25.0F))))
/*  832 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.GOLD_BLOCK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))))
/*  833 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.IRON_BLOCK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))))
/*  834 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 9.0F))))
/*  835 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 9.0F))))
/*  836 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.CRYING_OBSIDIAN).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 5.0F))))
/*  837 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.QUARTZ).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(8.0F, 23.0F))))
/*  838 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.GILDED_BLACKSTONE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(5.0F, 15.0F))))
/*  839 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MAGMA_CREAM).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 8.0F)))))
/*      */       
/*  841 */       .withPool(LootPool.lootPool()
/*  842 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  843 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(11))
/*  844 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1)))
/*      */       
/*  846 */       .withPool(LootPool.lootPool()
/*  847 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  848 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(1)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder bastionOtherLootTable() {
/*  853 */     return LootTable.lootTable()
/*  854 */       .withPool(LootPool.lootPool()
/*  855 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  856 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_PICKAXE).setWeight(6).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  857 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_SHOVEL).setWeight(6).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  858 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CROSSBOW).setWeight(6).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.1F, 0.9F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  859 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ANCIENT_DEBRIS).setWeight(12).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  860 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NETHERITE_SCRAP).setWeight(4).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  861 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPECTRAL_ARROW).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(10.0F, 22.0F))))
/*  862 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PIGLIN_BANNER_PATTERN).setWeight(9).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  863 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_PIGSTEP).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  864 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_CARROT).setWeight(12).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(6.0F, 17.0F))))
/*  865 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_APPLE).setWeight(9).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  866 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(10).apply((LootItemFunction.Builder)(new EnchantRandomlyFunction.Builder()).withEnchantment(Enchantments.SOUL_SPEED))))
/*      */       
/*  868 */       .withPool(LootPool.lootPool()
/*  869 */         .setRolls((NumberProvider)ConstantValue.exactly(2.0F))
/*  870 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_SWORD).setWeight(2).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.1F, 0.9F))).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  871 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.IRON_BLOCK).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  872 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_BOOTS).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)(new EnchantRandomlyFunction.Builder()).withEnchantment(Enchantments.SOUL_SPEED)))
/*  873 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_AXE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/*  874 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.GOLD_BLOCK).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  875 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CROSSBOW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  876 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 6.0F))))
/*  877 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 6.0F))))
/*  878 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_SWORD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  879 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_CHESTPLATE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  880 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HELMET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  881 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_LEGGINGS).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  882 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_BOOTS).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/*  883 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.CRYING_OBSIDIAN).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F)))))
/*      */       
/*  885 */       .withPool(LootPool.lootPool()
/*  886 */         .setRolls((NumberProvider)UniformGenerator.between(3.0F, 4.0F))
/*  887 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.GILDED_BLACKSTONE).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  888 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.CHAIN).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 10.0F))))
/*  889 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MAGMA_CREAM).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 6.0F))))
/*  890 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.BONE_BLOCK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 6.0F))))
/*  891 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_NUGGET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 8.0F))))
/*  892 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.OBSIDIAN).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 6.0F))))
/*  893 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 8.0F))))
/*  894 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 6.0F))))
/*  895 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(5.0F, 17.0F))))
/*  896 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COOKED_PORKCHOP).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F)))))
/*      */       
/*  898 */       .withPool(LootPool.lootPool()
/*  899 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  900 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(11))
/*  901 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1)))
/*      */       
/*  903 */       .withPool(LootPool.lootPool()
/*  904 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  905 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(9))
/*  906 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setWeight(1)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder woodlandMansionLootTable() {
/*  911 */     return LootTable.lootTable()
/*  912 */       .withPool(LootPool.lootPool()
/*  913 */         .setRolls((NumberProvider)UniformGenerator.between(1.0F, 3.0F))
/*  914 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEAD).setWeight(20))
/*  915 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_APPLE).setWeight(15))
/*  916 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
/*  917 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_13).setWeight(15))
/*  918 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_CAT).setWeight(15))
/*  919 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NAME_TAG).setWeight(20))
/*  920 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CHAINMAIL_CHESTPLATE).setWeight(10))
/*  921 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HOE).setWeight(15))
/*  922 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_CHESTPLATE).setWeight(5))
/*  923 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(10).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment())))
/*      */       
/*  925 */       .withPool(LootPool.lootPool()
/*  926 */         .setRolls((NumberProvider)UniformGenerator.between(1.0F, 4.0F))
/*  927 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  928 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  929 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(20))
/*  930 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  931 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BUCKET).setWeight(10))
/*  932 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.REDSTONE).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  933 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/*  934 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MELON_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))))
/*  935 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PUMPKIN_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))))
/*  936 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BEETROOT_SEEDS).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F)))))
/*      */       
/*  938 */       .withPool(LootPool.lootPool()
/*  939 */         .setRolls((NumberProvider)ConstantValue.exactly(3.0F))
/*  940 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/*  941 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GUNPOWDER).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/*  942 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/*  943 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F)))))
/*      */       
/*  945 */       .withPool(LootPool.lootPool()
/*  946 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  947 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(1))
/*  948 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder strongholdLibraryLootTable() {
/*  953 */     return LootTable.lootTable()
/*  954 */       .withPool(LootPool.lootPool()
/*  955 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 10.0F))
/*  956 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  957 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PAPER).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F))))
/*  958 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MAP))
/*  959 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COMPASS))
/*  960 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(10).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)ConstantValue.exactly(30.0F)).allowTreasure())))
/*      */       
/*  962 */       .withPool(LootPool.lootPool()
/*  963 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  964 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder strongholdCorridorLootTable() {
/*  969 */     return LootTable.lootTable()
/*  970 */       .withPool(LootPool.lootPool()
/*  971 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 3.0F))
/*  972 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENDER_PEARL).setWeight(10))
/*  973 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  974 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/*  975 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  976 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.REDSTONE).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 9.0F))))
/*  977 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREAD).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  978 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.APPLE).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*  979 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_PICKAXE).setWeight(5))
/*  980 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_SWORD).setWeight(5))
/*  981 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_CHESTPLATE).setWeight(5))
/*  982 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HELMET).setWeight(5))
/*  983 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_LEGGINGS).setWeight(5))
/*  984 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_BOOTS).setWeight(5))
/*  985 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_APPLE))
/*  986 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE))
/*  987 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HORSE_ARMOR))
/*  988 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HORSE_ARMOR))
/*  989 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HORSE_ARMOR))
/*  990 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_OTHERSIDE))
/*  991 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)ConstantValue.exactly(30.0F)).allowTreasure())))
/*      */       
/*  993 */       .withPool(LootPool.lootPool()
/*  994 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  995 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(9))
/*  996 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder ancientCityLootTable() {
/* 1001 */     return LootTable.lootTable()
/* 1002 */       .withPool(LootPool.lootPool()
/* 1003 */         .setRolls((NumberProvider)UniformGenerator.between(5.0F, 10.0F))
/*      */ 
/*      */         
/* 1006 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENCHANTED_GOLDEN_APPLE).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))
/* 1007 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_OTHERSIDE).setWeight(1))
/*      */ 
/*      */         
/* 1010 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COMPASS).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/* 1011 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SCULK_CATALYST).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))))
/* 1012 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NAME_TAG).setWeight(2))
/* 1013 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HOE).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.8F, 1.0F))).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(30.0F, 50.0F)).allowTreasure()))
/* 1014 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEAD).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/* 1015 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HORSE_ARMOR).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/* 1016 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))
/* 1017 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_13).setWeight(2))
/* 1018 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_CAT).setWeight(2))
/* 1019 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_LEGGINGS).setWeight(2).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(30.0F, 50.0F)).allowTreasure()))
/*      */ 
/*      */         
/* 1022 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(3).apply((LootItemFunction.Builder)(new EnchantRandomlyFunction.Builder()).withEnchantment(Enchantments.SWIFT_SNEAK)))
/* 1023 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SCULK).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 10.0F))))
/* 1024 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SCULK_SENSOR).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 1025 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CANDLE).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 4.0F))))
/* 1026 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.AMETHYST_SHARD).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 15.0F))))
/* 1027 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EXPERIENCE_BOTTLE).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 1028 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GLOW_BERRIES).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 15.0F))))
/* 1029 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_LEGGINGS).setWeight(3).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)UniformGenerator.between(20.0F, 39.0F)).allowTreasure()))
/*      */         
/* 1031 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ECHO_SHARD).setWeight(4).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 1032 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DISC_FRAGMENT_5).setWeight(4).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/*      */ 
/*      */         
/* 1035 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTION).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.STRONG_REGENERATION)))
/* 1036 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(5).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/* 1037 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 10.0F))))
/* 1038 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 15.0F))))
/* 1039 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SOUL_TORCH).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 15.0F))))
/*      */ 
/*      */         
/* 1042 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(7).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(6.0F, 15.0F)))))
/*      */       
/* 1044 */       .withPool(LootPool.lootPool()
/* 1045 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 1046 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(75))
/* 1047 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(4))
/* 1048 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder jungleTempleLootTable() {
/* 1053 */     return LootTable.lootTable()
/* 1054 */       .withPool(LootPool.lootPool()
/* 1055 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 6.0F))
/* 1056 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 1057 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/* 1058 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F))))
/* 1059 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.BAMBOO).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 1060 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 1061 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 6.0F))))
/* 1062 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(16).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 7.0F))))
/* 1063 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(3))
/* 1064 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HORSE_ARMOR))
/* 1065 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HORSE_ARMOR))
/* 1066 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HORSE_ARMOR))
/* 1067 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)ConstantValue.exactly(30.0F)).allowTreasure())))
/*      */       
/* 1069 */       .withPool(LootPool.lootPool()
/* 1070 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 1071 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(2))
/* 1072 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F)))));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder shipwreckTreasureLootTable() {
/* 1077 */     return LootTable.lootTable()
/* 1078 */       .withPool(LootPool.lootPool()
/* 1079 */         .setRolls((NumberProvider)UniformGenerator.between(3.0F, 6.0F))
/* 1080 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(90).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/* 1081 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/* 1082 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(40).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/* 1083 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(5))
/* 1084 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EXPERIENCE_BOTTLE).setWeight(5)))
/*      */       
/* 1086 */       .withPool(LootPool.lootPool()
/* 1087 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 5.0F))
/* 1088 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_NUGGET).setWeight(50).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 10.0F))))
/* 1089 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 10.0F))))
/* 1090 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LAPIS_LAZULI).setWeight(20).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 10.0F)))))
/*      */       
/* 1092 */       .withPool(LootPool.lootPool()
/* 1093 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 1094 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(5))
/* 1095 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F)))));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder pillagerOutpostLootTable() {
/* 1100 */     return LootTable.lootTable()
/* 1101 */       .withPool(LootPool.lootPool()
/* 1102 */         .setRolls((NumberProvider)UniformGenerator.between(0.0F, 1.0F))
/* 1103 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CROSSBOW)))
/*      */       
/* 1105 */       .withPool(LootPool.lootPool()
/* 1106 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 3.0F))
/* 1107 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(7).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 5.0F))))
/* 1108 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTATO).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))))
/* 1109 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CARROT).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 5.0F)))))
/*      */       
/* 1111 */       .withPool(LootPool.lootPool()
/* 1112 */         .setRolls((NumberProvider)UniformGenerator.between(1.0F, 3.0F))
/* 1113 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.DARK_OAK_LOG).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 3.0F)))))
/*      */       
/* 1115 */       .withPool(LootPool.lootPool()
/* 1116 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 3.0F))
/* 1117 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EXPERIENCE_BOTTLE).setWeight(7))
/* 1118 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).setWeight(4).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 6.0F))))
/* 1119 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).setWeight(4).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F))))
/* 1120 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TRIPWIRE_HOOK).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 1121 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 1122 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(1).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment())))
/*      */       
/* 1124 */       .withPool(LootPool.lootPool()
/* 1125 */         .setRolls((NumberProvider)UniformGenerator.between(0.0F, 1.0F))
/* 1126 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOAT_HORN)).apply((LootItemFunction.Builder)SetInstrumentFunction.setInstrumentOptions(InstrumentTags.REGULAR_GOAT_HORNS)))
/*      */       
/* 1128 */       .withPool(LootPool.lootPool()
/* 1129 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 1130 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(3))
/* 1131 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F)))));
/*      */   }
/*      */ 
/*      */   
/*      */   public static LootTable.Builder desertPyramidLootTable() {
/* 1136 */     return LootTable.lootTable()
/* 1137 */       .withPool(LootPool.lootPool()
/* 1138 */         .setRolls((NumberProvider)UniformGenerator.between(2.0F, 4.0F))
/* 1139 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND).setWeight(5).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 1140 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 5.0F))))
/* 1141 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 7.0F))))
/* 1142 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(15).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 1143 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).setWeight(25).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 6.0F))))
/* 1144 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPIDER_EYE).setWeight(25).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))))
/* 1145 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(25).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 7.0F))))
/* 1146 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).setWeight(20))
/* 1147 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_HORSE_ARMOR).setWeight(15))
/* 1148 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_HORSE_ARMOR).setWeight(10))
/* 1149 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND_HORSE_ARMOR).setWeight(5))
/* 1150 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK).setWeight(20).apply((LootItemFunction.Builder)EnchantRandomlyFunction.randomApplicableEnchantment()))
/* 1151 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLDEN_APPLE).setWeight(20))
/* 1152 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENCHANTED_GOLDEN_APPLE).setWeight(2))
/* 1153 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(15)))
/*      */       
/* 1155 */       .withPool(LootPool.lootPool()
/* 1156 */         .setRolls((NumberProvider)ConstantValue.exactly(4.0F))
/* 1157 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/* 1158 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GUNPOWDER).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/* 1159 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/* 1160 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F))))
/* 1161 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.SAND).setWeight(10).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 8.0F)))))
/*      */       
/* 1163 */       .withPool(LootPool.lootPool()
/* 1164 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 1165 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(6))
/* 1166 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F)))));
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\packs\VanillaChestLoot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */