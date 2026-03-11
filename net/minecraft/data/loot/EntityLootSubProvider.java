/*     */ package net.minecraft.data.loot;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.advancements.critereon.DamageSourcePredicate;
/*     */ import net.minecraft.advancements.critereon.EntityFlagsPredicate;
/*     */ import net.minecraft.advancements.critereon.EntityPredicate;
/*     */ import net.minecraft.advancements.critereon.EntitySubPredicate;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobCategory;
/*     */ import net.minecraft.world.entity.animal.FrogVariant;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.LootPool;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootItem;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootTableReference;
/*     */ import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ 
/*     */ public abstract class EntityLootSubProvider
/*     */   implements LootTableSubProvider {
/*  37 */   protected static final EntityPredicate.Builder ENTITY_ON_FIRE = EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(Boolean.valueOf(true)));
/*  38 */   private static final Set<EntityType<?>> SPECIAL_LOOT_TABLE_TYPES = (Set<EntityType<?>>)ImmutableSet.of(EntityType.PLAYER, EntityType.ARMOR_STAND, EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM, EntityType.VILLAGER); private final FeatureFlagSet allowed;
/*     */   private final FeatureFlagSet required;
/*     */   private final Map<EntityType<?>, Map<ResourceLocation, LootTable.Builder>> map;
/*     */   
/*     */   protected EntityLootSubProvider(FeatureFlagSet $$0) {
/*  43 */     this($$0, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected EntityLootSubProvider(FeatureFlagSet $$0, FeatureFlagSet $$1) {
/*  66 */     this.map = Maps.newHashMap();
/*     */     this.allowed = $$0;
/*     */     this.required = $$1;
/*     */   }
/*     */   
/*     */   public void generate(BiConsumer<ResourceLocation, LootTable.Builder> $$0) {
/*  72 */     generate();
/*     */     
/*  74 */     Set<ResourceLocation> $$1 = Sets.newHashSet();
/*  75 */     BuiltInRegistries.ENTITY_TYPE.holders().forEach($$2 -> {
/*     */           EntityType<?> $$3 = (EntityType)$$2.value();
/*     */ 
/*     */           
/*     */           if (!$$3.isEnabled(this.allowed)) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*     */           if (canHaveLootTable($$3)) {
/*     */             Map<ResourceLocation, LootTable.Builder> $$4 = this.map.remove($$3);
/*     */ 
/*     */             
/*     */             ResourceLocation $$5 = $$3.getDefaultLootTable();
/*     */             
/*     */             if (!$$5.equals(BuiltInLootTables.EMPTY) && $$3.isEnabled(this.required) && ($$4 == null || !$$4.containsKey($$5))) {
/*     */               throw new IllegalStateException(String.format(Locale.ROOT, "Missing loottable '%s' for '%s'", new Object[] { $$5, $$2.key().location() }));
/*     */             }
/*     */             
/*     */             if ($$4 != null) {
/*     */               $$4.forEach(());
/*     */             }
/*     */           } else {
/*     */             Map<ResourceLocation, LootTable.Builder> $$6 = this.map.remove($$3);
/*     */             
/*     */             if ($$6 != null) {
/*     */               throw new IllegalStateException(String.format(Locale.ROOT, "Weird loottables '%s' for '%s', not a LivingEntity so should not have loot", new Object[] { $$6.keySet().stream().map(ResourceLocation::toString).collect(Collectors.joining(",")), $$2.key().location() }));
/*     */             }
/*     */           } 
/*     */         });
/*     */     
/* 106 */     if (!this.map.isEmpty()) {
/* 107 */       throw new IllegalStateException("Created loot tables for entities not supported by datapack: " + this.map.keySet());
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean canHaveLootTable(EntityType<?> $$0) {
/* 112 */     return (SPECIAL_LOOT_TABLE_TYPES.contains($$0) || $$0.getCategory() != MobCategory.MISC);
/*     */   } protected static LootTable.Builder createSheepTable(ItemLike $$0) {
/*     */     return LootTable.lootTable().withPool(LootPool.lootPool().setRolls((NumberProvider)ConstantValue.exactly(1.0F)).add((LootPoolEntryContainer.Builder)LootItem.lootTableItem($$0))).withPool(LootPool.lootPool().setRolls((NumberProvider)ConstantValue.exactly(1.0F)).add((LootPoolEntryContainer.Builder)LootTableReference.lootTableReference(EntityType.SHEEP.getDefaultLootTable())));
/*     */   } protected LootItemCondition.Builder killedByFrog() {
/* 116 */     return DamageSourceCondition.hasDamageSource(
/* 117 */         DamageSourcePredicate.Builder.damageType().source(
/* 118 */           EntityPredicate.Builder.entity().of(EntityType.FROG)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LootItemCondition.Builder killedByFrogVariant(FrogVariant $$0) {
/* 124 */     return DamageSourceCondition.hasDamageSource(
/* 125 */         DamageSourcePredicate.Builder.damageType().source(
/* 126 */           EntityPredicate.Builder.entity().of(EntityType.FROG).subPredicate(EntitySubPredicate.variant($$0))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void add(EntityType<?> $$0, LootTable.Builder $$1) {
/* 132 */     add($$0, $$0.getDefaultLootTable(), $$1);
/*     */   }
/*     */   
/*     */   protected void add(EntityType<?> $$0, ResourceLocation $$1, LootTable.Builder $$2) {
/* 136 */     ((Map<ResourceLocation, LootTable.Builder>)this.map.computeIfAbsent($$0, $$0 -> new HashMap<>())).put($$1, $$2);
/*     */   }
/*     */   
/*     */   public abstract void generate();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\EntityLootSubProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */