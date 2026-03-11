/*     */ package net.minecraft.data.loot;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.IntStream;
/*     */ import net.minecraft.advancements.critereon.BlockPredicate;
/*     */ import net.minecraft.advancements.critereon.EnchantmentPredicate;
/*     */ import net.minecraft.advancements.critereon.ItemPredicate;
/*     */ import net.minecraft.advancements.critereon.LocationPredicate;
/*     */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*     */ import net.minecraft.advancements.critereon.StatePropertiesPredicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.BeehiveBlock;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.CandleBlock;
/*     */ import net.minecraft.world.level.block.CaveVines;
/*     */ import net.minecraft.world.level.block.DoorBlock;
/*     */ import net.minecraft.world.level.block.DoublePlantBlock;
/*     */ import net.minecraft.world.level.block.FlowerPotBlock;
/*     */ import net.minecraft.world.level.block.MultifaceBlock;
/*     */ import net.minecraft.world.level.block.PinkPetalsBlock;
/*     */ import net.minecraft.world.level.block.ShulkerBoxBlock;
/*     */ import net.minecraft.world.level.block.SlabBlock;
/*     */ import net.minecraft.world.level.block.StemBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.SlabType;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.IntRange;
/*     */ import net.minecraft.world.level.storage.loot.LootPool;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
/*     */ import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootItem;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
/*     */ import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
/*     */ import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
/*     */ import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
/*     */ import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
/*     */ import net.minecraft.world.level.storage.loot.functions.LimitCount;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
/*     */ import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
/*     */ import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.MatchTool;
/*     */ import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.nbt.NbtProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BlockLootSubProvider
/*     */   implements LootTableSubProvider
/*     */ {
/*  86 */   protected static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
/*  87 */   protected static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
/*     */   
/*  89 */   protected static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.SHEARS }));
/*  90 */   private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = (LootItemCondition.Builder)HAS_SHEARS.or(HAS_SILK_TOUCH);
/*     */   
/*  92 */   private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
/*     */   
/*     */   protected final Set<Item> explosionResistant;
/*     */   
/*     */   protected final FeatureFlagSet enabledFeatures;
/*     */   
/*     */   protected final Map<ResourceLocation, LootTable.Builder> map;
/*  99 */   protected static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[] { 0.05F, 0.0625F, 0.083333336F, 0.1F };
/* 100 */   private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[] { 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F };
/*     */   
/*     */   protected BlockLootSubProvider(Set<Item> $$0, FeatureFlagSet $$1) {
/* 103 */     this($$0, $$1, new HashMap<>());
/*     */   }
/*     */   
/*     */   protected BlockLootSubProvider(Set<Item> $$0, FeatureFlagSet $$1, Map<ResourceLocation, LootTable.Builder> $$2) {
/* 107 */     this.explosionResistant = $$0;
/* 108 */     this.enabledFeatures = $$1;
/* 109 */     this.map = $$2;
/*     */   }
/*     */   
/*     */   protected <T extends FunctionUserBuilder<T>> T applyExplosionDecay(ItemLike $$0, FunctionUserBuilder<T> $$1) {
/* 113 */     if (!this.explosionResistant.contains($$0.asItem())) {
/* 114 */       return (T)$$1.apply((LootItemFunction.Builder)ApplyExplosionDecay.explosionDecay());
/*     */     }
/*     */     
/* 117 */     return (T)$$1.unwrap();
/*     */   }
/*     */   
/*     */   protected <T extends ConditionUserBuilder<T>> T applyExplosionCondition(ItemLike $$0, ConditionUserBuilder<T> $$1) {
/* 121 */     if (!this.explosionResistant.contains($$0.asItem())) {
/* 122 */       return (T)$$1.when(ExplosionCondition.survivesExplosion());
/*     */     }
/*     */     
/* 125 */     return (T)$$1.unwrap();
/*     */   }
/*     */   
/*     */   public LootTable.Builder createSingleItemTable(ItemLike $$0) {
/* 129 */     return LootTable.lootTable()
/* 130 */       .withPool(applyExplosionCondition($$0, (ConditionUserBuilder<LootPool.Builder>)LootPool.lootPool()
/* 131 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 132 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem($$0))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static LootTable.Builder createSelfDropDispatchTable(Block $$0, LootItemCondition.Builder $$1, LootPoolEntryContainer.Builder<?> $$2) {
/* 137 */     return LootTable.lootTable()
/* 138 */       .withPool(LootPool.lootPool()
/* 139 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 140 */         .add((LootPoolEntryContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)$$0)
/* 141 */           .when($$1))
/* 142 */           .otherwise($$2)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static LootTable.Builder createSilkTouchDispatchTable(Block $$0, LootPoolEntryContainer.Builder<?> $$1) {
/* 148 */     return createSelfDropDispatchTable($$0, HAS_SILK_TOUCH, $$1);
/*     */   }
/*     */   
/*     */   protected static LootTable.Builder createShearsDispatchTable(Block $$0, LootPoolEntryContainer.Builder<?> $$1) {
/* 152 */     return createSelfDropDispatchTable($$0, HAS_SHEARS, $$1);
/*     */   }
/*     */   
/*     */   protected static LootTable.Builder createSilkTouchOrShearsDispatchTable(Block $$0, LootPoolEntryContainer.Builder<?> $$1) {
/* 156 */     return createSelfDropDispatchTable($$0, HAS_SHEARS_OR_SILK_TOUCH, $$1);
/*     */   }
/*     */   
/*     */   protected LootTable.Builder createSingleItemTableWithSilkTouch(Block $$0, ItemLike $$1) {
/* 160 */     return createSilkTouchDispatchTable($$0, applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder<LootPoolEntryContainer.Builder>)LootItem.lootTableItem($$1)));
/*     */   }
/*     */   
/*     */   protected LootTable.Builder createSingleItemTable(ItemLike $$0, NumberProvider $$1) {
/* 164 */     return LootTable.lootTable()
/* 165 */       .withPool(LootPool.lootPool()
/* 166 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 167 */         .add(applyExplosionDecay($$0, (FunctionUserBuilder<LootPoolEntryContainer.Builder>)LootItem.lootTableItem($$0).apply((LootItemFunction.Builder)SetItemCountFunction.setCount($$1)))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createSingleItemTableWithSilkTouch(Block $$0, ItemLike $$1, NumberProvider $$2) {
/* 172 */     return createSilkTouchDispatchTable($$0, applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder<LootPoolEntryContainer.Builder>)LootItem.lootTableItem($$1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount($$2))));
/*     */   }
/*     */   
/*     */   private static LootTable.Builder createSilkTouchOnlyTable(ItemLike $$0) {
/* 176 */     return LootTable.lootTable()
/* 177 */       .withPool(LootPool.lootPool()
/* 178 */         .when(HAS_SILK_TOUCH)
/* 179 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 180 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   private LootTable.Builder createPotFlowerItemTable(ItemLike $$0) {
/* 185 */     return LootTable.lootTable()
/* 186 */       .withPool(applyExplosionCondition((ItemLike)Blocks.FLOWER_POT, (ConditionUserBuilder<LootPool.Builder>)LootPool.lootPool()
/* 187 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 188 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.FLOWER_POT))))
/*     */       
/* 190 */       .withPool(applyExplosionCondition($$0, (ConditionUserBuilder<LootPool.Builder>)LootPool.lootPool()
/* 191 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 192 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem($$0))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createSlabItemTable(Block $$0) {
/* 197 */     return LootTable.lootTable()
/* 198 */       .withPool(LootPool.lootPool()
/* 199 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 200 */         .add(applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder<LootPoolEntryContainer.Builder>)LootItem.lootTableItem((ItemLike)$$0)
/* 201 */             .apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F)).when(
/* 202 */                 (LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)SlabBlock.TYPE, (Comparable)SlabType.DOUBLE)))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected <T extends Comparable<T> & net.minecraft.util.StringRepresentable> LootTable.Builder createSinglePropConditionTable(Block $$0, Property<T> $$1, T $$2) {
/* 209 */     return LootTable.lootTable()
/* 210 */       .withPool(applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder<LootPool.Builder>)LootPool.lootPool()
/* 211 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 212 */           .add(LootItem.lootTableItem((ItemLike)$$0)
/* 213 */             .when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty($$1, (Comparable)$$2))))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createNameableBlockEntityTable(Block $$0) {
/* 219 */     return LootTable.lootTable()
/* 220 */       .withPool(applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder<LootPool.Builder>)LootPool.lootPool()
/* 221 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 222 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$0)
/* 223 */             .apply((LootItemFunction.Builder)CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createShulkerBoxDrop(Block $$0) {
/* 228 */     return LootTable.lootTable()
/* 229 */       .withPool(applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder<LootPool.Builder>)LootPool.lootPool()
/* 230 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 231 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$0)
/* 232 */             .apply((LootItemFunction.Builder)CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
/* 233 */             .apply((LootItemFunction.Builder)CopyNbtFunction.copyData((NbtProvider)ContextNbtProvider.BLOCK_ENTITY)
/* 234 */               .copy("Lock", "BlockEntityTag.Lock")
/* 235 */               .copy("LootTable", "BlockEntityTag.LootTable")
/* 236 */               .copy("LootTableSeed", "BlockEntityTag.LootTableSeed"))
/*     */             
/* 238 */             .apply((LootItemFunction.Builder)SetContainerContents.setContents(BlockEntityType.SHULKER_BOX).withEntry((LootPoolEntryContainer.Builder)DynamicLoot.dynamicEntry(ShulkerBoxBlock.CONTENTS))))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createCopperOreDrops(Block $$0) {
/* 244 */     return createSilkTouchDispatchTable($$0, 
/* 245 */         applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder<LootPoolEntryContainer.Builder>)LootItem.lootTableItem((ItemLike)Items.RAW_COPPER)
/* 246 */           .apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F)))
/* 247 */           .apply((LootItemFunction.Builder)ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createLapisOreDrops(Block $$0) {
/* 253 */     return createSilkTouchDispatchTable($$0, 
/* 254 */         applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder<LootPoolEntryContainer.Builder>)LootItem.lootTableItem((ItemLike)Items.LAPIS_LAZULI)
/* 255 */           .apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 9.0F)))
/* 256 */           .apply((LootItemFunction.Builder)ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createRedstoneOreDrops(Block $$0) {
/* 262 */     return createSilkTouchDispatchTable($$0, 
/* 263 */         applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder<LootPoolEntryContainer.Builder>)LootItem.lootTableItem((ItemLike)Items.REDSTONE)
/* 264 */           .apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(4.0F, 5.0F)))
/* 265 */           .apply((LootItemFunction.Builder)ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createBannerDrop(Block $$0) {
/* 271 */     return LootTable.lootTable()
/* 272 */       .withPool(applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder<LootPool.Builder>)LootPool.lootPool()
/* 273 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 274 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$0)
/* 275 */             .apply((LootItemFunction.Builder)CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
/* 276 */             .apply((LootItemFunction.Builder)CopyNbtFunction.copyData((NbtProvider)ContextNbtProvider.BLOCK_ENTITY)
/* 277 */               .copy("Patterns", "BlockEntityTag.Patterns")))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static LootTable.Builder createBeeNestDrop(Block $$0) {
/* 284 */     return LootTable.lootTable()
/* 285 */       .withPool(LootPool.lootPool()
/* 286 */         .when(HAS_SILK_TOUCH)
/* 287 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 288 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$0)
/* 289 */           .apply((LootItemFunction.Builder)CopyNbtFunction.copyData((NbtProvider)ContextNbtProvider.BLOCK_ENTITY)
/* 290 */             .copy("Bees", "BlockEntityTag.Bees"))
/*     */           
/* 292 */           .apply((LootItemFunction.Builder)CopyBlockState.copyState($$0).copy((Property)BeehiveBlock.HONEY_LEVEL))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static LootTable.Builder createBeeHiveDrop(Block $$0) {
/* 298 */     return LootTable.lootTable()
/* 299 */       .withPool(LootPool.lootPool()
/* 300 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 301 */         .add((LootPoolEntryContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)$$0)
/* 302 */           .when(HAS_SILK_TOUCH))
/* 303 */           .apply((LootItemFunction.Builder)CopyNbtFunction.copyData((NbtProvider)ContextNbtProvider.BLOCK_ENTITY)
/* 304 */             .copy("Bees", "BlockEntityTag.Bees"))
/*     */           
/* 306 */           .apply((LootItemFunction.Builder)CopyBlockState.copyState($$0).copy((Property)BeehiveBlock.HONEY_LEVEL))
/* 307 */           .otherwise((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$0))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static LootTable.Builder createCaveVinesDrop(Block $$0) {
/* 313 */     return LootTable.lootTable()
/* 314 */       .withPool(LootPool.lootPool()
/* 315 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GLOW_BERRIES))
/* 316 */         .when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)CaveVines.BERRIES, true))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createOreDrop(Block $$0, Item $$1) {
/* 321 */     return createSilkTouchDispatchTable($$0, 
/* 322 */         applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder<LootPoolEntryContainer.Builder>)LootItem.lootTableItem((ItemLike)$$1)
/* 323 */           .apply((LootItemFunction.Builder)ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createMushroomBlockDrop(Block $$0, ItemLike $$1) {
/* 329 */     return createSilkTouchDispatchTable($$0, applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder<LootPoolEntryContainer.Builder>)LootItem.lootTableItem($$1)
/* 330 */           .apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(-6.0F, 2.0F)))
/* 331 */           .apply((LootItemFunction.Builder)LimitCount.limitCount(IntRange.lowerBound(0)))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createGrassDrops(Block $$0) {
/* 337 */     return createShearsDispatchTable($$0, applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder<LootPoolEntryContainer.Builder>)((LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)Items.WHEAT_SEEDS)
/* 338 */           .when(LootItemRandomChanceCondition.randomChance(0.125F)))
/* 339 */           .apply((LootItemFunction.Builder)ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LootTable.Builder createStemDrops(Block $$0, Item $$1) {
/* 345 */     return LootTable.lootTable()
/* 346 */       .withPool(applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder<LootPool.Builder>)LootPool.lootPool()
/* 347 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 348 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$1)
/* 349 */             .apply(StemBlock.AGE.getPossibleValues(), $$1 -> SetItemCountFunction.setCount((NumberProvider)BinomialDistributionGenerator.binomial(3, ($$1.intValue() + 1) / 15.0F)).when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)StemBlock.AGE, $$1.intValue())))))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LootTable.Builder createAttachedStemDrops(Block $$0, Item $$1) {
/* 355 */     return LootTable.lootTable()
/* 356 */       .withPool(applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder<LootPool.Builder>)LootPool.lootPool()
/* 357 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 358 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$1)
/* 359 */             .apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)BinomialDistributionGenerator.binomial(3, 0.53333336F))))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static LootTable.Builder createShearsOnlyDrop(ItemLike $$0) {
/* 365 */     return LootTable.lootTable()
/* 366 */       .withPool(LootPool.lootPool()
/* 367 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 368 */         .when(HAS_SHEARS)
/* 369 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createMultifaceBlockDrops(Block $$0, LootItemCondition.Builder $$1) {
/* 374 */     return LootTable.lootTable()
/* 375 */       .withPool(LootPool.lootPool()
/* 376 */         .add(applyExplosionDecay((ItemLike)$$0, 
/* 377 */             (FunctionUserBuilder<LootPoolEntryContainer.Builder>)((LootPoolSingletonContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)$$0)
/* 378 */             .when($$1))
/* 379 */             .apply((Object[])Direction.values(), $$1 -> SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F), true).when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)MultifaceBlock.getFaceProperty($$1), true)))))
/* 380 */             .apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(-1.0F), true)))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createLeavesDrops(Block $$0, Block $$1, float... $$2) {
/* 386 */     return createSilkTouchOrShearsDispatchTable($$0, ((LootPoolSingletonContainer.Builder)
/* 387 */         applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder<LootPoolSingletonContainer.Builder>)LootItem.lootTableItem((ItemLike)$$1)))
/* 388 */         .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, $$2)))
/*     */       
/* 390 */       .withPool(LootPool.lootPool()
/* 391 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 392 */         .when(HAS_NO_SHEARS_OR_SILK_TOUCH)
/* 393 */         .add(((LootPoolSingletonContainer.Builder)applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder<LootPoolSingletonContainer.Builder>)LootItem.lootTableItem((ItemLike)Items.STICK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F)))))
/* 394 */           .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, NORMAL_LEAVES_STICK_CHANCES))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createOakLeavesDrops(Block $$0, Block $$1, float... $$2) {
/* 400 */     return 
/* 401 */       createLeavesDrops($$0, $$1, $$2)
/* 402 */       .withPool(LootPool.lootPool()
/* 403 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 404 */         .when(HAS_NO_SHEARS_OR_SILK_TOUCH)
/* 405 */         .add(((LootPoolSingletonContainer.Builder)applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder<LootPoolSingletonContainer.Builder>)LootItem.lootTableItem((ItemLike)Items.APPLE)))
/* 406 */           .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, new float[] { 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F }))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createMangroveLeavesDrops(Block $$0) {
/* 412 */     return createSilkTouchOrShearsDispatchTable($$0, ((LootPoolSingletonContainer.Builder)
/* 413 */         applyExplosionDecay((ItemLike)Blocks.MANGROVE_LEAVES, (FunctionUserBuilder<LootPoolSingletonContainer.Builder>)LootItem.lootTableItem((ItemLike)Items.STICK).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F)))))
/* 414 */         .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, NORMAL_LEAVES_STICK_CHANCES)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createCropDrops(Block $$0, Item $$1, Item $$2, LootItemCondition.Builder $$3) {
/* 419 */     return applyExplosionDecay((ItemLike)$$0, (FunctionUserBuilder<LootTable.Builder>)LootTable.lootTable()
/* 420 */         .withPool(LootPool.lootPool()
/* 421 */           .add((LootPoolEntryContainer.Builder)((LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)$$1)
/* 422 */             .when($$3))
/* 423 */             .otherwise((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$2))))
/*     */ 
/*     */         
/* 426 */         .withPool(LootPool.lootPool()
/* 427 */           .when($$3)
/* 428 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$2).apply((LootItemFunction.Builder)ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static LootTable.Builder createDoublePlantShearsDrop(Block $$0) {
/* 434 */     return LootTable.lootTable().withPool(LootPool.lootPool()
/* 435 */         .when(HAS_SHEARS)
/* 436 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$0).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F)))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createDoublePlantWithSeedDrops(Block $$0, Block $$1) {
/* 442 */     AlternativesEntry.Builder builder = ((LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)$$1).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(2.0F))).when(HAS_SHEARS)).otherwise(((LootPoolSingletonContainer.Builder)applyExplosionCondition((ItemLike)$$0, (ConditionUserBuilder<LootPoolSingletonContainer.Builder>)LootItem.lootTableItem((ItemLike)Items.WHEAT_SEEDS)))
/* 443 */         .when(LootItemRandomChanceCondition.randomChance(0.125F)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 448 */     return LootTable.lootTable()
/* 449 */       .withPool(
/* 450 */         LootPool.lootPool()
/* 451 */         .add((LootPoolEntryContainer.Builder)builder)
/* 452 */         .when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)DoublePlantBlock.HALF, (Comparable)DoubleBlockHalf.LOWER)))
/* 453 */         .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(new Block[] { $$0 }, ).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)DoublePlantBlock.HALF, (Comparable)DoubleBlockHalf.UPPER))), new BlockPos(0, 1, 0))))
/*     */       
/* 455 */       .withPool(
/* 456 */         LootPool.lootPool()
/* 457 */         .add((LootPoolEntryContainer.Builder)builder)
/* 458 */         .when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)DoublePlantBlock.HALF, (Comparable)DoubleBlockHalf.UPPER)))
/* 459 */         .when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(new Block[] { $$0 }, ).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)DoublePlantBlock.HALF, (Comparable)DoubleBlockHalf.LOWER))), new BlockPos(0, -1, 0))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createCandleDrops(Block $$0) {
/* 464 */     return LootTable.lootTable()
/* 465 */       .withPool(LootPool.lootPool()
/* 466 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 467 */         .add(applyExplosionDecay((ItemLike)$$0, LootItem.lootTableItem((ItemLike)$$0)
/* 468 */             .apply(List.of(Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4)), $$1 -> SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly($$1.intValue())).when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)CandleBlock.CANDLES, $$1.intValue())))))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LootTable.Builder createPetalsDrops(Block $$0) {
/* 474 */     return LootTable.lootTable()
/* 475 */       .withPool(LootPool.lootPool()
/* 476 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 477 */         .add(applyExplosionDecay((ItemLike)$$0, LootItem.lootTableItem((ItemLike)$$0)
/* 478 */             .apply(IntStream.rangeClosed(1, 4).boxed().toList(), $$1 -> SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly($$1.intValue())).when((LootItemCondition.Builder)LootItemBlockStatePropertyCondition.hasBlockStateProperties($$0).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty((Property)PinkPetalsBlock.AMOUNT, $$1.intValue())))))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static LootTable.Builder createCandleCakeDrops(Block $$0) {
/* 484 */     return LootTable.lootTable()
/* 485 */       .withPool(LootPool.lootPool()
/* 486 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 487 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)$$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static LootTable.Builder noDrop() {
/* 492 */     return LootTable.lootTable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generate(BiConsumer<ResourceLocation, LootTable.Builder> $$0) {
/* 499 */     generate();
/*     */     
/* 501 */     Set<ResourceLocation> $$1 = new HashSet<>();
/* 502 */     for (Block $$2 : BuiltInRegistries.BLOCK) {
/* 503 */       if (!$$2.isEnabled(this.enabledFeatures)) {
/*     */         continue;
/*     */       }
/* 506 */       ResourceLocation $$3 = $$2.getLootTable();
/* 507 */       if ($$3 != BuiltInLootTables.EMPTY && $$1.add($$3)) {
/* 508 */         LootTable.Builder $$4 = this.map.remove($$3);
/* 509 */         if ($$4 == null) {
/* 510 */           throw new IllegalStateException(String.format(Locale.ROOT, "Missing loottable '%s' for '%s'", new Object[] { $$3, BuiltInRegistries.BLOCK.getKey($$2) }));
/*     */         }
/* 512 */         $$0.accept($$3, $$4);
/*     */       } 
/*     */     } 
/*     */     
/* 516 */     if (!this.map.isEmpty()) {
/* 517 */       throw new IllegalStateException("Created block loot tables for non-blocks: " + this.map.keySet());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void addNetherVinesDropTable(Block $$0, Block $$1) {
/* 522 */     LootTable.Builder $$2 = createSilkTouchOrShearsDispatchTable($$0, 
/* 523 */         LootItem.lootTableItem((ItemLike)$$0).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, new float[] { 0.33F, 0.55F, 0.77F, 1.0F })));
/* 524 */     add($$0, $$2);
/* 525 */     add($$1, $$2);
/*     */   }
/*     */   
/*     */   protected LootTable.Builder createDoorTable(Block $$0) {
/* 529 */     return createSinglePropConditionTable($$0, (Property<DoubleBlockHalf>)DoorBlock.HALF, DoubleBlockHalf.LOWER);
/*     */   }
/*     */   
/*     */   protected void dropPottedContents(Block $$0) {
/* 533 */     add($$0, $$0 -> createPotFlowerItemTable((ItemLike)((FlowerPotBlock)$$0).getPotted()));
/*     */   }
/*     */   
/*     */   protected void otherWhenSilkTouch(Block $$0, Block $$1) {
/* 537 */     add($$0, createSilkTouchOnlyTable((ItemLike)$$1));
/*     */   }
/*     */   
/*     */   protected void dropOther(Block $$0, ItemLike $$1) {
/* 541 */     add($$0, createSingleItemTable($$1));
/*     */   }
/*     */   
/*     */   protected void dropWhenSilkTouch(Block $$0) {
/* 545 */     otherWhenSilkTouch($$0, $$0);
/*     */   }
/*     */   
/*     */   protected void dropSelf(Block $$0) {
/* 549 */     dropOther($$0, (ItemLike)$$0);
/*     */   }
/*     */   
/*     */   protected void add(Block $$0, Function<Block, LootTable.Builder> $$1) {
/* 553 */     add($$0, $$1.apply($$0));
/*     */   }
/*     */   
/*     */   protected void add(Block $$0, LootTable.Builder $$1) {
/* 557 */     this.map.put($$0.getLootTable(), $$1);
/*     */   }
/*     */   
/*     */   protected abstract void generate();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\BlockLootSubProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */