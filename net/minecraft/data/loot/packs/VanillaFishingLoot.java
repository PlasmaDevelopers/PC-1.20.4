/*     */ package net.minecraft.data.loot.packs;
/*     */ import java.util.function.BiConsumer;
/*     */ import net.minecraft.advancements.critereon.EntityPredicate;
/*     */ import net.minecraft.advancements.critereon.LocationPredicate;
/*     */ import net.minecraft.data.loot.LootTableSubProvider;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.LootPool;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootItem;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootTableReference;
/*     */ import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
/*     */ 
/*     */ public class VanillaFishingLoot implements LootTableSubProvider {
/*  33 */   public static final LootItemCondition.Builder IN_JUNGLE = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.JUNGLE));
/*  34 */   public static final LootItemCondition.Builder IN_SPARSE_JUNGLE = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.SPARSE_JUNGLE));
/*  35 */   public static final LootItemCondition.Builder IN_BAMBOO_JUNGLE = LocationCheck.checkLocation(LocationPredicate.Builder.location().setBiome(Biomes.BAMBOO_JUNGLE));
/*     */ 
/*     */   
/*     */   public void generate(BiConsumer<ResourceLocation, LootTable.Builder> $$0) {
/*  39 */     $$0.accept(BuiltInLootTables.FISHING, 
/*  40 */         LootTable.lootTable()
/*  41 */         .withPool(LootPool.lootPool()
/*  42 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  43 */           .add((LootPoolEntryContainer.Builder)LootTableReference.lootTableReference(BuiltInLootTables.FISHING_JUNK).setWeight(10).setQuality(-2))
/*  44 */           .add(LootTableReference.lootTableReference(BuiltInLootTables.FISHING_TREASURE).setWeight(5).setQuality(2)
/*  45 */             .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate((EntitySubPredicate)FishingHookPredicate.inOpenWater(true)))))
/*  46 */           .add((LootPoolEntryContainer.Builder)LootTableReference.lootTableReference(BuiltInLootTables.FISHING_FISH).setWeight(85).setQuality(-1))));
/*     */ 
/*     */ 
/*     */     
/*  50 */     $$0.accept(BuiltInLootTables.FISHING_FISH, fishingFishLootTable());
/*     */     
/*  52 */     $$0.accept(BuiltInLootTables.FISHING_JUNK, 
/*  53 */         LootTable.lootTable()
/*  54 */         .withPool(LootPool.lootPool()
/*  55 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.LILY_PAD).setWeight(17))
/*  56 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER_BOOTS).setWeight(10).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.0F, 0.9F))))
/*  57 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER).setWeight(10))
/*  58 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).setWeight(10))
/*  59 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTION).setWeight(10).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.WATER)))
/*  60 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).setWeight(5))
/*  61 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FISHING_ROD).setWeight(2).apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.0F, 0.9F))))
/*  62 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOWL).setWeight(10))
/*  63 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STICK).setWeight(5))
/*  64 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.INK_SAC).setWeight(1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(10.0F))))
/*  65 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.TRIPWIRE_HOOK).setWeight(10))
/*  66 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).setWeight(10))
/*  67 */           .add((LootPoolEntryContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.BAMBOO)
/*  68 */             .when((LootItemCondition.Builder)IN_JUNGLE.or(IN_SPARSE_JUNGLE).or(IN_BAMBOO_JUNGLE)))
/*  69 */             .setWeight(10))));
/*     */ 
/*     */ 
/*     */     
/*  73 */     $$0.accept(BuiltInLootTables.FISHING_TREASURE, 
/*  74 */         LootTable.lootTable()
/*  75 */         .withPool(LootPool.lootPool()
/*  76 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NAME_TAG))
/*  77 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE))
/*  78 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOW)
/*  79 */             .apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.0F, 0.25F)))
/*  80 */             .apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)ConstantValue.exactly(30.0F)).allowTreasure()))
/*     */           
/*  82 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FISHING_ROD)
/*  83 */             .apply((LootItemFunction.Builder)SetItemDamageFunction.setDamage((NumberProvider)UniformGenerator.between(0.0F, 0.25F)))
/*  84 */             .apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)ConstantValue.exactly(30.0F)).allowTreasure()))
/*     */           
/*  86 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOOK)
/*  87 */             .apply((LootItemFunction.Builder)EnchantWithLevelsFunction.enchantWithLevels((NumberProvider)ConstantValue.exactly(30.0F)).allowTreasure()))
/*     */           
/*  89 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.NAUTILUS_SHELL))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static LootTable.Builder fishingFishLootTable() {
/*  95 */     return LootTable.lootTable()
/*  96 */       .withPool(LootPool.lootPool()
/*  97 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COD).setWeight(60))
/*  98 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SALMON).setWeight(25))
/*  99 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TROPICAL_FISH).setWeight(2))
/* 100 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PUFFERFISH).setWeight(13)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\packs\VanillaFishingLoot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */