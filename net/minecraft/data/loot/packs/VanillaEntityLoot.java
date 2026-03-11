/*     */ package net.minecraft.data.loot.packs;
/*     */ import net.minecraft.advancements.critereon.DamageSourcePredicate;
/*     */ import net.minecraft.advancements.critereon.EntityPredicate;
/*     */ import net.minecraft.advancements.critereon.EntitySubPredicate;
/*     */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*     */ import net.minecraft.advancements.critereon.SlimePredicate;
/*     */ import net.minecraft.advancements.critereon.TagPredicate;
/*     */ import net.minecraft.data.loot.EntityLootSubProvider;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.tags.EntityTypeTags;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.animal.FrogVariant;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.LootPool;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootItem;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootTableReference;
/*     */ import net.minecraft.world.level.storage.loot.entries.TagEntry;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
/*     */ 
/*     */ public class VanillaEntityLoot extends EntityLootSubProvider {
/*     */   public VanillaEntityLoot() {
/*  44 */     super(FeatureFlags.REGISTRY.allFlags());
/*     */   }
/*     */ 
/*     */   
/*     */   public void generate() {
/*  49 */     add(EntityType.ALLAY, 
/*  50 */         LootTable.lootTable());
/*     */ 
/*     */     
/*  53 */     add(EntityType.ARMOR_STAND, 
/*  54 */         LootTable.lootTable());
/*     */ 
/*     */     
/*  57 */     add(EntityType.AXOLOTL, 
/*  58 */         LootTable.lootTable());
/*     */ 
/*     */     
/*  61 */     add(EntityType.BAT, 
/*  62 */         LootTable.lootTable());
/*     */ 
/*     */     
/*  65 */     add(EntityType.BEE, 
/*  66 */         LootTable.lootTable());
/*     */ 
/*     */     
/*  69 */     add(EntityType.BLAZE, 
/*  70 */         LootTable.lootTable()
/*  71 */         .withPool(LootPool.lootPool()
/*  72 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  73 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BLAZE_ROD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/*  74 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())));
/*     */ 
/*     */     
/*  77 */     add(EntityType.CAT, 
/*  78 */         LootTable.lootTable()
/*  79 */         .withPool(LootPool.lootPool()
/*  80 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  81 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))))));
/*     */ 
/*     */ 
/*     */     
/*  85 */     add(EntityType.CAMEL, 
/*  86 */         LootTable.lootTable());
/*     */ 
/*     */     
/*  89 */     add(EntityType.CAVE_SPIDER, 
/*  90 */         LootTable.lootTable()
/*  91 */         .withPool(LootPool.lootPool()
/*  92 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  93 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/*  95 */         .withPool(LootPool.lootPool()
/*  96 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/*  97 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPIDER_EYE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(-1.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/*  98 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())));
/*     */ 
/*     */     
/* 101 */     add(EntityType.CHICKEN, 
/* 102 */         LootTable.lootTable()
/* 103 */         .withPool(LootPool.lootPool()
/* 104 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 105 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FEATHER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 107 */         .withPool(LootPool.lootPool()
/* 108 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 109 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CHICKEN).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 113 */     add(EntityType.COD, 
/* 114 */         LootTable.lootTable()
/* 115 */         .withPool(LootPool.lootPool()
/* 116 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 117 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COD).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))))
/*     */         
/* 119 */         .withPool(LootPool.lootPool()
/* 120 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 121 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE_MEAL))
/* 122 */           .when(LootItemRandomChanceCondition.randomChance(0.05F))));
/*     */ 
/*     */     
/* 125 */     add(EntityType.COW, 
/* 126 */         LootTable.lootTable()
/* 127 */         .withPool(LootPool.lootPool()
/* 128 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 129 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 131 */         .withPool(LootPool.lootPool()
/* 132 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 133 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BEEF).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 137 */     add(EntityType.CREEPER, 
/* 138 */         LootTable.lootTable()
/* 139 */         .withPool(LootPool.lootPool()
/* 140 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 141 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GUNPOWDER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 143 */         .withPool(LootPool.lootPool()
/* 144 */           .add((LootPoolEntryContainer.Builder)TagEntry.expandTag(ItemTags.CREEPER_DROP_MUSIC_DISCS))
/* 145 */           .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS)))));
/*     */ 
/*     */     
/* 148 */     add(EntityType.DOLPHIN, 
/* 149 */         LootTable.lootTable()
/* 150 */         .withPool(LootPool.lootPool()
/* 151 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 152 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))))));
/*     */ 
/*     */ 
/*     */     
/* 156 */     add(EntityType.DONKEY, 
/* 157 */         LootTable.lootTable()
/* 158 */         .withPool(LootPool.lootPool()
/* 159 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 160 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 164 */     add(EntityType.DROWNED, 
/* 165 */         LootTable.lootTable()
/* 166 */         .withPool(LootPool.lootPool()
/* 167 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 168 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 170 */         .withPool(LootPool.lootPool()
/* 171 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 172 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COPPER_INGOT))
/* 173 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())
/* 174 */           .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.11F, 0.02F))));
/*     */ 
/*     */     
/* 177 */     add(EntityType.ELDER_GUARDIAN, elderGuardianLootTable());
/*     */     
/* 179 */     add(EntityType.ENDER_DRAGON, 
/* 180 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 183 */     add(EntityType.ENDERMAN, 
/* 184 */         LootTable.lootTable()
/* 185 */         .withPool(LootPool.lootPool()
/* 186 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 187 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ENDER_PEARL).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 191 */     add(EntityType.ENDERMITE, 
/* 192 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 195 */     add(EntityType.EVOKER, 
/* 196 */         LootTable.lootTable()
/* 197 */         .withPool(LootPool.lootPool()
/* 198 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 199 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TOTEM_OF_UNDYING)))
/*     */         
/* 201 */         .withPool(LootPool.lootPool()
/* 202 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 203 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/* 204 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())));
/*     */ 
/*     */     
/* 207 */     add(EntityType.BREEZE, 
/* 208 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 211 */     add(EntityType.FOX, 
/* 212 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 215 */     add(EntityType.FROG, 
/* 216 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 219 */     add(EntityType.GHAST, 
/* 220 */         LootTable.lootTable()
/* 221 */         .withPool(LootPool.lootPool()
/* 222 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 223 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GHAST_TEAR).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 225 */         .withPool(LootPool.lootPool()
/* 226 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 227 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GUNPOWDER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 231 */     add(EntityType.GIANT, 
/* 232 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 235 */     add(EntityType.GLOW_SQUID, 
/* 236 */         LootTable.lootTable()
/* 237 */         .withPool(LootPool.lootPool()
/* 238 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 239 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GLOW_INK_SAC).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 243 */     add(EntityType.GOAT, 
/* 244 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 247 */     add(EntityType.GUARDIAN, 
/* 248 */         LootTable.lootTable()
/* 249 */         .withPool(LootPool.lootPool()
/* 250 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 251 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PRISMARINE_SHARD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 253 */         .withPool(LootPool.lootPool()
/* 254 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 255 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COD).setWeight(2).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))))
/* 256 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PRISMARINE_CRYSTALS).setWeight(2).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/* 257 */           .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem()))
/*     */         
/* 259 */         .withPool(LootPool.lootPool()
/* 260 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 261 */           .add((LootPoolEntryContainer.Builder)LootTableReference.lootTableReference(BuiltInLootTables.FISHING_FISH).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))))
/* 262 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())
/* 263 */           .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
/*     */ 
/*     */     
/* 266 */     add(EntityType.HORSE, 
/* 267 */         LootTable.lootTable()
/* 268 */         .withPool(LootPool.lootPool()
/* 269 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 270 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 274 */     add(EntityType.HUSK, 
/* 275 */         LootTable.lootTable()
/* 276 */         .withPool(LootPool.lootPool()
/* 277 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 278 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 280 */         .withPool(LootPool.lootPool()
/* 281 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 282 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT))
/* 283 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CARROT))
/* 284 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTATO).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))))
/* 285 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())
/* 286 */           .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
/*     */ 
/*     */     
/* 289 */     add(EntityType.RAVAGER, 
/* 290 */         LootTable.lootTable()
/* 291 */         .withPool(LootPool.lootPool()
/* 292 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 293 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SADDLE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 297 */     add(EntityType.ILLUSIONER, 
/* 298 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 301 */     add(EntityType.IRON_GOLEM, 
/* 302 */         LootTable.lootTable()
/* 303 */         .withPool(LootPool.lootPool()
/* 304 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 305 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.POPPY).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F)))))
/*     */         
/* 307 */         .withPool(LootPool.lootPool()
/* 308 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 309 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(3.0F, 5.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 313 */     add(EntityType.LLAMA, 
/* 314 */         LootTable.lootTable()
/* 315 */         .withPool(LootPool.lootPool()
/* 316 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 317 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 321 */     add(EntityType.MAGMA_CUBE, 
/* 322 */         LootTable.lootTable()
/* 323 */         .withPool(LootPool.lootPool()
/* 324 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 325 */           .add(((LootPoolSingletonContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MAGMA_CREAM).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(-2.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))
/* 326 */             .when(killedByFrog().invert()))
/* 327 */             .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate((EntitySubPredicate)SlimePredicate.sized(MinMaxBounds.Ints.atLeast(2))))))
/*     */           
/* 329 */           .add(LootItem.lootTableItem((ItemLike)Items.PEARLESCENT_FROGLIGHT).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).when(killedByFrogVariant(FrogVariant.WARM)))
/* 330 */           .add(LootItem.lootTableItem((ItemLike)Items.VERDANT_FROGLIGHT).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).when(killedByFrogVariant(FrogVariant.COLD)))
/* 331 */           .add(LootItem.lootTableItem((ItemLike)Items.OCHRE_FROGLIGHT).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).when(killedByFrogVariant(FrogVariant.TEMPERATE)))));
/*     */ 
/*     */ 
/*     */     
/* 335 */     add(EntityType.MULE, 
/* 336 */         LootTable.lootTable()
/* 337 */         .withPool(LootPool.lootPool()
/* 338 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 339 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 343 */     add(EntityType.MOOSHROOM, 
/* 344 */         LootTable.lootTable()
/* 345 */         .withPool(LootPool.lootPool()
/* 346 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 347 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 349 */         .withPool(LootPool.lootPool()
/* 350 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 351 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BEEF).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 355 */     add(EntityType.OCELOT, 
/* 356 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 359 */     add(EntityType.PANDA, 
/* 360 */         LootTable.lootTable()
/* 361 */         .withPool(LootPool.lootPool()
/* 362 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 363 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.BAMBOO).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 367 */     add(EntityType.PARROT, 
/* 368 */         LootTable.lootTable()
/* 369 */         .withPool(LootPool.lootPool()
/* 370 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 371 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.FEATHER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 375 */     add(EntityType.PHANTOM, 
/* 376 */         LootTable.lootTable()
/* 377 */         .withPool(LootPool.lootPool()
/* 378 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 379 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PHANTOM_MEMBRANE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/* 380 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())));
/*     */ 
/*     */     
/* 383 */     add(EntityType.PIG, 
/* 384 */         LootTable.lootTable()
/* 385 */         .withPool(LootPool.lootPool()
/* 386 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 387 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PORKCHOP).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 391 */     add(EntityType.PILLAGER, 
/* 392 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 395 */     add(EntityType.PLAYER, 
/* 396 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 399 */     add(EntityType.POLAR_BEAR, 
/* 400 */         LootTable.lootTable()
/* 401 */         .withPool(LootPool.lootPool()
/* 402 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 403 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COD).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/* 404 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SALMON).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 408 */     add(EntityType.PUFFERFISH, 
/* 409 */         LootTable.lootTable()
/* 410 */         .withPool(LootPool.lootPool()
/* 411 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 412 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PUFFERFISH).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F)))))
/*     */         
/* 414 */         .withPool(LootPool.lootPool()
/* 415 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 416 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE_MEAL))
/* 417 */           .when(LootItemRandomChanceCondition.randomChance(0.05F))));
/*     */ 
/*     */     
/* 420 */     add(EntityType.RABBIT, 
/* 421 */         LootTable.lootTable()
/* 422 */         .withPool(LootPool.lootPool()
/* 423 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 424 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.RABBIT_HIDE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 426 */         .withPool(LootPool.lootPool()
/* 427 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 428 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.RABBIT).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 430 */         .withPool(LootPool.lootPool()
/* 431 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 432 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.RABBIT_FOOT))
/* 433 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())
/* 434 */           .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1F, 0.03F))));
/*     */ 
/*     */     
/* 437 */     add(EntityType.SALMON, 
/* 438 */         LootTable.lootTable()
/* 439 */         .withPool(LootPool.lootPool()
/* 440 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 441 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SALMON).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))))
/*     */         
/* 443 */         .withPool(LootPool.lootPool()
/* 444 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 445 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE_MEAL))
/* 446 */           .when(LootItemRandomChanceCondition.randomChance(0.05F))));
/*     */ 
/*     */     
/* 449 */     add(EntityType.SHEEP, 
/* 450 */         LootTable.lootTable()
/* 451 */         .withPool(LootPool.lootPool()
/* 452 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 453 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.MUTTON).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 2.0F))).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 457 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_BLACK, createSheepTable((ItemLike)Blocks.BLACK_WOOL));
/* 458 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_BLUE, createSheepTable((ItemLike)Blocks.BLUE_WOOL));
/* 459 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_BROWN, createSheepTable((ItemLike)Blocks.BROWN_WOOL));
/* 460 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_CYAN, createSheepTable((ItemLike)Blocks.CYAN_WOOL));
/* 461 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_GRAY, createSheepTable((ItemLike)Blocks.GRAY_WOOL));
/* 462 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_GREEN, createSheepTable((ItemLike)Blocks.GREEN_WOOL));
/* 463 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_LIGHT_BLUE, createSheepTable((ItemLike)Blocks.LIGHT_BLUE_WOOL));
/* 464 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_LIGHT_GRAY, createSheepTable((ItemLike)Blocks.LIGHT_GRAY_WOOL));
/* 465 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_LIME, createSheepTable((ItemLike)Blocks.LIME_WOOL));
/* 466 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_MAGENTA, createSheepTable((ItemLike)Blocks.MAGENTA_WOOL));
/* 467 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_ORANGE, createSheepTable((ItemLike)Blocks.ORANGE_WOOL));
/* 468 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_PINK, createSheepTable((ItemLike)Blocks.PINK_WOOL));
/* 469 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_PURPLE, createSheepTable((ItemLike)Blocks.PURPLE_WOOL));
/* 470 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_RED, createSheepTable((ItemLike)Blocks.RED_WOOL));
/* 471 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_WHITE, createSheepTable((ItemLike)Blocks.WHITE_WOOL));
/* 472 */     add(EntityType.SHEEP, BuiltInLootTables.SHEEP_YELLOW, createSheepTable((ItemLike)Blocks.YELLOW_WOOL));
/*     */     
/* 474 */     add(EntityType.SHULKER, 
/* 475 */         LootTable.lootTable()
/* 476 */         .withPool(LootPool.lootPool()
/* 477 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 478 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SHULKER_SHELL))
/* 479 */           .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.5F, 0.0625F))));
/*     */ 
/*     */     
/* 482 */     add(EntityType.SILVERFISH, 
/* 483 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 486 */     add(EntityType.SKELETON, 
/* 487 */         LootTable.lootTable()
/* 488 */         .withPool(LootPool.lootPool()
/* 489 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 490 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 492 */         .withPool(LootPool.lootPool()
/* 493 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 494 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 498 */     add(EntityType.SKELETON_HORSE, 
/* 499 */         LootTable.lootTable()
/* 500 */         .withPool(LootPool.lootPool()
/* 501 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 502 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 506 */     add(EntityType.SLIME, 
/* 507 */         LootTable.lootTable()
/* 508 */         .withPool(LootPool.lootPool()
/* 509 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 510 */           .add(LootItem.lootTableItem((ItemLike)Items.SLIME_BALL).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).when(killedByFrog().invert()))
/* 511 */           .add(LootItem.lootTableItem((ItemLike)Items.SLIME_BALL).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F))).when(killedByFrog()))
/* 512 */           .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().subPredicate((EntitySubPredicate)SlimePredicate.sized(MinMaxBounds.Ints.exactly(1)))))));
/*     */ 
/*     */ 
/*     */     
/* 516 */     add(EntityType.SNIFFER, 
/* 517 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 520 */     add(EntityType.SNOW_GOLEM, 
/* 521 */         LootTable.lootTable()
/* 522 */         .withPool(LootPool.lootPool()
/* 523 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 524 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SNOWBALL).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 15.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 528 */     add(EntityType.SPIDER, 
/* 529 */         LootTable.lootTable()
/* 530 */         .withPool(LootPool.lootPool()
/* 531 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 532 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 534 */         .withPool(LootPool.lootPool()
/* 535 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 536 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPIDER_EYE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(-1.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/* 537 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())));
/*     */ 
/*     */     
/* 540 */     add(EntityType.SQUID, 
/* 541 */         LootTable.lootTable()
/* 542 */         .withPool(LootPool.lootPool()
/* 543 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 544 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.INK_SAC).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 548 */     add(EntityType.STRAY, 
/* 549 */         LootTable.lootTable()
/* 550 */         .withPool(LootPool.lootPool()
/* 551 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 552 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 554 */         .withPool(LootPool.lootPool()
/* 555 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 556 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 558 */         .withPool(LootPool.lootPool()
/* 559 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 560 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIPPED_ARROW).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)).setLimit(1)).apply((LootItemFunction.Builder)SetPotionFunction.setPotion(Potions.SLOWNESS)))
/* 561 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())));
/*     */ 
/*     */     
/* 564 */     add(EntityType.STRIDER, 
/* 565 */         LootTable.lootTable()
/* 566 */         .withPool(LootPool.lootPool()
/* 567 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 568 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STRING).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 5.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 572 */     add(EntityType.TADPOLE, 
/* 573 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 576 */     add(EntityType.TRADER_LLAMA, 
/* 577 */         LootTable.lootTable()
/* 578 */         .withPool(LootPool.lootPool()
/* 579 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 580 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 584 */     add(EntityType.TROPICAL_FISH, 
/* 585 */         LootTable.lootTable()
/* 586 */         .withPool(LootPool.lootPool()
/* 587 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 588 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TROPICAL_FISH).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)ConstantValue.exactly(1.0F)))))
/*     */         
/* 590 */         .withPool(LootPool.lootPool()
/* 591 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 592 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE_MEAL))
/* 593 */           .when(LootItemRandomChanceCondition.randomChance(0.05F))));
/*     */ 
/*     */     
/* 596 */     add(EntityType.TURTLE, 
/* 597 */         LootTable.lootTable()
/* 598 */         .withPool(LootPool.lootPool()
/* 599 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 600 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.SEAGRASS).setWeight(3).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 602 */         .withPool(LootPool.lootPool()
/* 603 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 604 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BOWL))
/* 605 */           .when(DamageSourceCondition.hasDamageSource(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_LIGHTNING))))));
/*     */ 
/*     */ 
/*     */     
/* 609 */     add(EntityType.VEX, 
/* 610 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 613 */     add(EntityType.VILLAGER, 
/* 614 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 617 */     add(EntityType.WARDEN, 
/* 618 */         LootTable.lootTable()
/* 619 */         .withPool(LootPool.lootPool()
/* 620 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 621 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SCULK_CATALYST))));
/*     */ 
/*     */     
/* 624 */     add(EntityType.WANDERING_TRADER, 
/* 625 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 628 */     add(EntityType.VINDICATOR, 
/* 629 */         LootTable.lootTable()
/* 630 */         .withPool(LootPool.lootPool()
/* 631 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 632 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.EMERALD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/* 633 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())));
/*     */ 
/*     */     
/* 636 */     add(EntityType.WITCH, 
/* 637 */         LootTable.lootTable()
/* 638 */         .withPool(LootPool.lootPool()
/* 639 */           .setRolls((NumberProvider)UniformGenerator.between(1.0F, 3.0F))
/* 640 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GLOWSTONE_DUST).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/* 641 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SUGAR).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/* 642 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.REDSTONE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/* 643 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.SPIDER_EYE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/* 644 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GLASS_BOTTLE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/* 645 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GUNPOWDER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/* 646 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.STICK).setWeight(2).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 650 */     add(EntityType.WITHER, 
/* 651 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 654 */     add(EntityType.WITHER_SKELETON, 
/* 655 */         LootTable.lootTable()
/* 656 */         .withPool(LootPool.lootPool()
/* 657 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 658 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COAL).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(-1.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 660 */         .withPool(LootPool.lootPool()
/* 661 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 662 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.BONE).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 664 */         .withPool(LootPool.lootPool()
/* 665 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 666 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.WITHER_SKELETON_SKULL))
/* 667 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())
/* 668 */           .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
/*     */ 
/*     */     
/* 671 */     add(EntityType.WOLF, 
/* 672 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 675 */     add(EntityType.ZOGLIN, 
/* 676 */         LootTable.lootTable()
/* 677 */         .withPool(LootPool.lootPool()
/* 678 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 679 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(1.0F, 3.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 683 */     add(EntityType.ZOMBIE, 
/* 684 */         LootTable.lootTable()
/* 685 */         .withPool(LootPool.lootPool()
/* 686 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 687 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 689 */         .withPool(LootPool.lootPool()
/* 690 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 691 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT))
/* 692 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CARROT))
/* 693 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTATO).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))))
/* 694 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())
/* 695 */           .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
/*     */ 
/*     */     
/* 698 */     add(EntityType.ZOMBIE_HORSE, 
/* 699 */         LootTable.lootTable()
/* 700 */         .withPool(LootPool.lootPool()
/* 701 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 702 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 706 */     add(EntityType.ZOMBIFIED_PIGLIN, 
/* 707 */         LootTable.lootTable()
/* 708 */         .withPool(LootPool.lootPool()
/* 709 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 710 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 712 */         .withPool(LootPool.lootPool()
/* 713 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 714 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_NUGGET).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 716 */         .withPool(LootPool.lootPool()
/* 717 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 718 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.GOLD_INGOT))
/* 719 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())
/* 720 */           .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
/*     */ 
/*     */     
/* 723 */     add(EntityType.HOGLIN, 
/* 724 */         LootTable.lootTable()
/* 725 */         .withPool(LootPool.lootPool()
/* 726 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 727 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PORKCHOP).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(2.0F, 4.0F))).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 729 */         .withPool(LootPool.lootPool()
/* 730 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 731 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.LEATHER).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))));
/*     */ 
/*     */ 
/*     */     
/* 735 */     add(EntityType.PIGLIN, 
/* 736 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 739 */     add(EntityType.PIGLIN_BRUTE, 
/* 740 */         LootTable.lootTable());
/*     */ 
/*     */     
/* 743 */     add(EntityType.ZOMBIE_VILLAGER, 
/* 744 */         LootTable.lootTable()
/* 745 */         .withPool(LootPool.lootPool()
/* 746 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 747 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.ROTTEN_FLESH).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */         
/* 749 */         .withPool(LootPool.lootPool()
/* 750 */           .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 751 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.IRON_INGOT))
/* 752 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.CARROT))
/* 753 */           .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.POTATO).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))))
/* 754 */           .when(LootItemKilledByPlayerCondition.killedByPlayer())
/* 755 */           .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F))));
/*     */   }
/*     */ 
/*     */   
/*     */   public static LootTable.Builder elderGuardianLootTable() {
/* 760 */     return LootTable.lootTable()
/* 761 */       .withPool(LootPool.lootPool()
/* 762 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 763 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PRISMARINE_SHARD).apply((LootItemFunction.Builder)SetItemCountFunction.setCount((NumberProvider)UniformGenerator.between(0.0F, 2.0F))).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F)))))
/*     */       
/* 765 */       .withPool(LootPool.lootPool()
/* 766 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 767 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.COD).setWeight(3).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))))
/* 768 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.PRISMARINE_CRYSTALS).setWeight(2).apply((LootItemFunction.Builder)LootingEnchantFunction.lootingMultiplier((NumberProvider)UniformGenerator.between(0.0F, 1.0F))))
/* 769 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem()))
/*     */       
/* 771 */       .withPool(LootPool.lootPool()
/* 772 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 773 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Blocks.WET_SPONGE))
/* 774 */         .when(LootItemKilledByPlayerCondition.killedByPlayer()))
/* 775 */       .withPool(LootPool.lootPool()
/* 776 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 777 */         .add((LootPoolEntryContainer.Builder)LootTableReference.lootTableReference(BuiltInLootTables.FISHING_FISH).apply((LootItemFunction.Builder)SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE))))
/* 778 */         .when(LootItemKilledByPlayerCondition.killedByPlayer())
/* 779 */         .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.025F, 0.01F)))
/* 780 */       .withPool(LootPool.lootPool()
/* 781 */         .setRolls((NumberProvider)ConstantValue.exactly(1.0F))
/* 782 */         .add((LootPoolEntryContainer.Builder)EmptyLootItem.emptyItem().setWeight(4))
/* 783 */         .add((LootPoolEntryContainer.Builder)LootItem.lootTableItem((ItemLike)Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\packs\VanillaEntityLoot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */