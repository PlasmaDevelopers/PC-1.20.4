/*     */ package net.minecraft.data.loot.packs;
/*     */ 
/*     */ import java.util.function.BiConsumer;
/*     */ import net.minecraft.data.loot.LootTableSubProvider;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.LootPool;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootItem;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetStewEffectFunction;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
/*     */ 
/*     */ public class VanillaArchaeologyLoot
/*     */   implements LootTableSubProvider {
/*     */   public void generate(BiConsumer<ResourceLocation, LootTable.Builder> $$0) {
/*  23 */     $$0.accept(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY, 
/*  24 */         LootTable.lootTable()
/*  25 */         .withPool(LootPool.lootPool()
/*  26 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  27 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARMS_UP_POTTERY_SHERD).setWeight(2))
/*  28 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BREWER_POTTERY_SHERD).setWeight(2))
/*  29 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BRICK))
/*  30 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD))
/*  31 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STICK))
/*  32 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SUSPICIOUS_STEW).apply((LootItemFunction.Builder)SetStewEffectFunction.stewEffect()
/*  33 */               .withEffect(MobEffects.NIGHT_VISION, (NumberProvider)UniformGenerator.between(7.0F, 10.0F))
/*  34 */               .withEffect(MobEffects.JUMP, (NumberProvider)UniformGenerator.between(7.0F, 10.0F))
/*  35 */               .withEffect(MobEffects.WEAKNESS, (NumberProvider)UniformGenerator.between(6.0F, 8.0F))
/*  36 */               .withEffect(MobEffects.BLINDNESS, (NumberProvider)UniformGenerator.between(5.0F, 7.0F))
/*  37 */               .withEffect(MobEffects.POISON, (NumberProvider)UniformGenerator.between(10.0F, 20.0F))
/*  38 */               .withEffect(MobEffects.SATURATION, (NumberProvider)UniformGenerator.between(7.0F, 10.0F))))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     $$0.accept(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY, 
/*  44 */         LootTable.lootTable()
/*  45 */         .withPool(LootPool.lootPool()
/*  46 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  47 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARCHER_POTTERY_SHERD))
/*  48 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MINER_POTTERY_SHERD))
/*  49 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PRIZE_POTTERY_SHERD))
/*  50 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SKULL_POTTERY_SHERD))
/*  51 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DIAMOND))
/*  52 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TNT))
/*  53 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GUNPOWDER))
/*  54 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD))));
/*     */ 
/*     */ 
/*     */     
/*  58 */     $$0.accept(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON, 
/*  59 */         LootTable.lootTable()
/*  60 */         .withPool(LootPool.lootPool()
/*  61 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  62 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(2))
/*  63 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(2))
/*  64 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WOODEN_HOE).setWeight(2))
/*  65 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CLAY).setWeight(2))
/*  66 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BRICK).setWeight(2))
/*  67 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.YELLOW_DYE).setWeight(2))
/*  68 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BLUE_DYE).setWeight(2))
/*  69 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LIGHT_BLUE_DYE).setWeight(2))
/*  70 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHITE_DYE).setWeight(2))
/*  71 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ORANGE_DYE).setWeight(2))
/*  72 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.RED_CANDLE).setWeight(2))
/*  73 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GREEN_CANDLE).setWeight(2))
/*  74 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PURPLE_CANDLE).setWeight(2))
/*  75 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BROWN_CANDLE).setWeight(2))
/*  76 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MAGENTA_STAINED_GLASS_PANE))
/*  77 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PINK_STAINED_GLASS_PANE))
/*  78 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BLUE_STAINED_GLASS_PANE))
/*  79 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LIGHT_BLUE_STAINED_GLASS_PANE))
/*  80 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.RED_STAINED_GLASS_PANE))
/*  81 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.YELLOW_STAINED_GLASS_PANE))
/*  82 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PURPLE_STAINED_GLASS_PANE))
/*  83 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPRUCE_HANGING_SIGN))
/*  84 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.OAK_HANGING_SIGN))
/*  85 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET))
/*  86 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL))
/*  87 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT_SEEDS))
/*  88 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BEETROOT_SEEDS))
/*  89 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DEAD_BUSH))
/*  90 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FLOWER_POT))
/*  91 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING))
/*  92 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEAD))));
/*     */ 
/*     */ 
/*     */     
/*  96 */     $$0.accept(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE, 
/*  97 */         LootTable.lootTable()
/*  98 */         .withPool(LootPool.lootPool()
/*  99 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 100 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BURN_POTTERY_SHERD))
/* 101 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.DANGER_POTTERY_SHERD))
/* 102 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FRIEND_POTTERY_SHERD))
/* 103 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.HEART_POTTERY_SHERD))
/* 104 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.HEARTBREAK_POTTERY_SHERD))
/* 105 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.HOWL_POTTERY_SHERD))
/* 106 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SHEAF_POTTERY_SHERD))
/* 107 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE))
/* 108 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE))
/* 109 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE))
/* 110 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE))
/* 111 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUSIC_DISC_RELIC))));
/*     */ 
/*     */ 
/*     */     
/* 115 */     $$0.accept(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY, 
/* 116 */         LootTable.lootTable()
/* 117 */         .withPool(LootPool.lootPool()
/* 118 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 119 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ANGLER_POTTERY_SHERD))
/* 120 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SHELTER_POTTERY_SHERD))
/* 121 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SNORT_POTTERY_SHERD))
/* 122 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SNIFFER_EGG))
/* 123 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_AXE))
/* 124 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(2))
/* 125 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(2))
/* 126 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WOODEN_HOE).setWeight(2))
/* 127 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(2))
/* 128 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).setWeight(2))));
/*     */ 
/*     */ 
/*     */     
/* 132 */     $$0.accept(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY, 
/* 133 */         LootTable.lootTable()
/* 134 */         .withPool(LootPool.lootPool()
/* 135 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 136 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BLADE_POTTERY_SHERD))
/* 137 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EXPLORER_POTTERY_SHERD))
/* 138 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MOURNER_POTTERY_SHERD))
/* 139 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PLENTY_POTTERY_SHERD))
/* 140 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_AXE))
/* 141 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).setWeight(2))
/* 142 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT).setWeight(2))
/* 143 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WOODEN_HOE).setWeight(2))
/* 144 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).setWeight(2))
/* 145 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).setWeight(2))));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\packs\VanillaArchaeologyLoot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */