/*     */ package net.minecraft.core.registries;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.advancements.CriterionTrigger;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfos;
/*     */ import net.minecraft.core.DefaultedMappedRegistry;
/*     */ import net.minecraft.core.DefaultedRegistry;
/*     */ import net.minecraft.core.MappedRegistry;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.WritableRegistry;
/*     */ import net.minecraft.core.particles.ParticleType;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.network.chat.numbers.NumberFormatType;
/*     */ import net.minecraft.network.chat.numbers.NumberFormatTypes;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.Bootstrap;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.stats.StatType;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.valueproviders.FloatProviderType;
/*     */ import net.minecraft.util.valueproviders.IntProviderType;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiType;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiTypes;
/*     */ import net.minecraft.world.entity.animal.CatVariant;
/*     */ import net.minecraft.world.entity.animal.FrogVariant;
/*     */ import net.minecraft.world.entity.decoration.PaintingVariant;
/*     */ import net.minecraft.world.entity.decoration.PaintingVariants;
/*     */ import net.minecraft.world.entity.npc.VillagerProfession;
/*     */ import net.minecraft.world.entity.npc.VillagerType;
/*     */ import net.minecraft.world.entity.schedule.Activity;
/*     */ import net.minecraft.world.entity.schedule.Schedule;
/*     */ import net.minecraft.world.inventory.MenuType;
/*     */ import net.minecraft.world.item.CreativeModeTab;
/*     */ import net.minecraft.world.item.CreativeModeTabs;
/*     */ import net.minecraft.world.item.Instrument;
/*     */ import net.minecraft.world.item.Instruments;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.Potion;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.item.crafting.RecipeSerializer;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.item.enchantment.Enchantment;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.biome.BiomeSource;
/*     */ import net.minecraft.world.level.biome.BiomeSources;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.BlockTypes;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BannerPattern;
/*     */ import net.minecraft.world.level.block.entity.BannerPatterns;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerators;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.gameevent.PositionSourceType;
/*     */ import net.minecraft.world.level.levelgen.DensityFunction;
/*     */ import net.minecraft.world.level.levelgen.DensityFunctions;
/*     */ import net.minecraft.world.level.levelgen.SurfaceRules;
/*     */ import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
/*     */ import net.minecraft.world.level.levelgen.carver.WorldCarver;
/*     */ import net.minecraft.world.level.levelgen.feature.Feature;
/*     */ import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeType;
/*     */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
/*     */ import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
/*     */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
/*     */ import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
/*     */ import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
/*     */ import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;
/*     */ import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBindings;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifierType;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
/*     */ import net.minecraft.world.level.storage.loot.providers.nbt.LootNbtProviderType;
/*     */ import net.minecraft.world.level.storage.loot.providers.nbt.NbtProviders;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
/*     */ import net.minecraft.world.level.storage.loot.providers.score.LootScoreProviderType;
/*     */ import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProviders;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class BuiltInRegistries
/*     */ {
/* 120 */   private static final Logger LOGGER = LogUtils.getLogger();
/* 121 */   private static final Map<ResourceLocation, Supplier<?>> LOADERS = Maps.newLinkedHashMap();
/*     */   
/* 123 */   private static final WritableRegistry<WritableRegistry<?>> WRITABLE_REGISTRY = (WritableRegistry<WritableRegistry<?>>)new MappedRegistry(ResourceKey.createRegistryKey(Registries.ROOT_REGISTRY_NAME), Lifecycle.stable());
/*     */ 
/*     */ 
/*     */   
/* 127 */   public static final DefaultedRegistry<GameEvent> GAME_EVENT = registerDefaultedWithIntrusiveHolders(Registries.GAME_EVENT, "step", $$0 -> GameEvent.STEP);
/* 128 */   public static final Registry<SoundEvent> SOUND_EVENT = registerSimple(Registries.SOUND_EVENT, $$0 -> SoundEvents.ITEM_PICKUP);
/* 129 */   public static final DefaultedRegistry<Fluid> FLUID = registerDefaultedWithIntrusiveHolders(Registries.FLUID, "empty", $$0 -> Fluids.EMPTY);
/* 130 */   public static final Registry<MobEffect> MOB_EFFECT = registerSimpleWithIntrusiveHolders(Registries.MOB_EFFECT, $$0 -> MobEffects.LUCK);
/* 131 */   public static final DefaultedRegistry<Block> BLOCK = registerDefaultedWithIntrusiveHolders(Registries.BLOCK, "air", $$0 -> Blocks.AIR);
/* 132 */   public static final Registry<Enchantment> ENCHANTMENT = registerSimpleWithIntrusiveHolders(Registries.ENCHANTMENT, $$0 -> Enchantments.BLOCK_FORTUNE);
/* 133 */   public static final DefaultedRegistry<EntityType<?>> ENTITY_TYPE = registerDefaultedWithIntrusiveHolders(Registries.ENTITY_TYPE, "pig", $$0 -> EntityType.PIG);
/* 134 */   public static final DefaultedRegistry<Item> ITEM = registerDefaultedWithIntrusiveHolders(Registries.ITEM, "air", $$0 -> Items.AIR);
/* 135 */   public static final DefaultedRegistry<Potion> POTION = registerDefaultedWithIntrusiveHolders(Registries.POTION, "empty", $$0 -> Potions.EMPTY);
/* 136 */   public static final Registry<ParticleType<?>> PARTICLE_TYPE = registerSimple(Registries.PARTICLE_TYPE, $$0 -> ParticleTypes.BLOCK);
/* 137 */   public static final Registry<BlockEntityType<?>> BLOCK_ENTITY_TYPE = registerSimpleWithIntrusiveHolders(Registries.BLOCK_ENTITY_TYPE, $$0 -> BlockEntityType.FURNACE);
/* 138 */   public static final DefaultedRegistry<PaintingVariant> PAINTING_VARIANT = registerDefaulted(Registries.PAINTING_VARIANT, "kebab", PaintingVariants::bootstrap);
/* 139 */   public static final Registry<ResourceLocation> CUSTOM_STAT = registerSimple(Registries.CUSTOM_STAT, $$0 -> Stats.JUMP);
/* 140 */   public static final DefaultedRegistry<ChunkStatus> CHUNK_STATUS = registerDefaulted(Registries.CHUNK_STATUS, "empty", $$0 -> ChunkStatus.EMPTY);
/* 141 */   public static final Registry<RuleTestType<?>> RULE_TEST = registerSimple(Registries.RULE_TEST, $$0 -> RuleTestType.ALWAYS_TRUE_TEST);
/* 142 */   public static final Registry<RuleBlockEntityModifierType<?>> RULE_BLOCK_ENTITY_MODIFIER = registerSimple(Registries.RULE_BLOCK_ENTITY_MODIFIER, $$0 -> RuleBlockEntityModifierType.PASSTHROUGH);
/* 143 */   public static final Registry<PosRuleTestType<?>> POS_RULE_TEST = registerSimple(Registries.POS_RULE_TEST, $$0 -> PosRuleTestType.ALWAYS_TRUE_TEST);
/* 144 */   public static final Registry<MenuType<?>> MENU = registerSimple(Registries.MENU, $$0 -> MenuType.ANVIL);
/* 145 */   public static final Registry<RecipeType<?>> RECIPE_TYPE = registerSimple(Registries.RECIPE_TYPE, $$0 -> RecipeType.CRAFTING);
/* 146 */   public static final Registry<RecipeSerializer<?>> RECIPE_SERIALIZER = registerSimple(Registries.RECIPE_SERIALIZER, $$0 -> RecipeSerializer.SHAPELESS_RECIPE);
/* 147 */   public static final Registry<Attribute> ATTRIBUTE = registerSimple(Registries.ATTRIBUTE, $$0 -> Attributes.LUCK);
/* 148 */   public static final Registry<PositionSourceType<?>> POSITION_SOURCE_TYPE = registerSimple(Registries.POSITION_SOURCE_TYPE, $$0 -> PositionSourceType.BLOCK);
/* 149 */   public static final Registry<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPE = registerSimple(Registries.COMMAND_ARGUMENT_TYPE, ArgumentTypeInfos::bootstrap);
/* 150 */   public static final Registry<StatType<?>> STAT_TYPE = registerSimple(Registries.STAT_TYPE, $$0 -> Stats.ITEM_USED);
/* 151 */   public static final DefaultedRegistry<VillagerType> VILLAGER_TYPE = registerDefaulted(Registries.VILLAGER_TYPE, "plains", $$0 -> VillagerType.PLAINS);
/* 152 */   public static final DefaultedRegistry<VillagerProfession> VILLAGER_PROFESSION = registerDefaulted(Registries.VILLAGER_PROFESSION, "none", $$0 -> VillagerProfession.NONE);
/* 153 */   public static final Registry<PoiType> POINT_OF_INTEREST_TYPE = registerSimple(Registries.POINT_OF_INTEREST_TYPE, PoiTypes::bootstrap);
/* 154 */   public static final DefaultedRegistry<MemoryModuleType<?>> MEMORY_MODULE_TYPE = registerDefaulted(Registries.MEMORY_MODULE_TYPE, "dummy", $$0 -> MemoryModuleType.DUMMY);
/* 155 */   public static final DefaultedRegistry<SensorType<?>> SENSOR_TYPE = registerDefaulted(Registries.SENSOR_TYPE, "dummy", $$0 -> SensorType.DUMMY);
/* 156 */   public static final Registry<Schedule> SCHEDULE = registerSimple(Registries.SCHEDULE, $$0 -> Schedule.EMPTY);
/* 157 */   public static final Registry<Activity> ACTIVITY = registerSimple(Registries.ACTIVITY, $$0 -> Activity.IDLE);
/* 158 */   public static final Registry<LootPoolEntryType> LOOT_POOL_ENTRY_TYPE = registerSimple(Registries.LOOT_POOL_ENTRY_TYPE, $$0 -> LootPoolEntries.EMPTY);
/* 159 */   public static final Registry<LootItemFunctionType> LOOT_FUNCTION_TYPE = registerSimple(Registries.LOOT_FUNCTION_TYPE, $$0 -> LootItemFunctions.SET_COUNT);
/* 160 */   public static final Registry<LootItemConditionType> LOOT_CONDITION_TYPE = registerSimple(Registries.LOOT_CONDITION_TYPE, $$0 -> LootItemConditions.INVERTED);
/* 161 */   public static final Registry<LootNumberProviderType> LOOT_NUMBER_PROVIDER_TYPE = registerSimple(Registries.LOOT_NUMBER_PROVIDER_TYPE, $$0 -> NumberProviders.CONSTANT);
/* 162 */   public static final Registry<LootNbtProviderType> LOOT_NBT_PROVIDER_TYPE = registerSimple(Registries.LOOT_NBT_PROVIDER_TYPE, $$0 -> NbtProviders.CONTEXT);
/* 163 */   public static final Registry<LootScoreProviderType> LOOT_SCORE_PROVIDER_TYPE = registerSimple(Registries.LOOT_SCORE_PROVIDER_TYPE, $$0 -> ScoreboardNameProviders.CONTEXT);
/* 164 */   public static final Registry<FloatProviderType<?>> FLOAT_PROVIDER_TYPE = registerSimple(Registries.FLOAT_PROVIDER_TYPE, $$0 -> FloatProviderType.CONSTANT);
/* 165 */   public static final Registry<IntProviderType<?>> INT_PROVIDER_TYPE = registerSimple(Registries.INT_PROVIDER_TYPE, $$0 -> IntProviderType.CONSTANT);
/* 166 */   public static final Registry<HeightProviderType<?>> HEIGHT_PROVIDER_TYPE = registerSimple(Registries.HEIGHT_PROVIDER_TYPE, $$0 -> HeightProviderType.CONSTANT);
/* 167 */   public static final Registry<BlockPredicateType<?>> BLOCK_PREDICATE_TYPE = registerSimple(Registries.BLOCK_PREDICATE_TYPE, $$0 -> BlockPredicateType.NOT);
/* 168 */   public static final Registry<WorldCarver<?>> CARVER = registerSimple(Registries.CARVER, $$0 -> WorldCarver.CAVE);
/* 169 */   public static final Registry<Feature<?>> FEATURE = registerSimple(Registries.FEATURE, $$0 -> Feature.ORE);
/* 170 */   public static final Registry<StructurePlacementType<?>> STRUCTURE_PLACEMENT = registerSimple(Registries.STRUCTURE_PLACEMENT, $$0 -> StructurePlacementType.RANDOM_SPREAD);
/* 171 */   public static final Registry<StructurePieceType> STRUCTURE_PIECE = registerSimple(Registries.STRUCTURE_PIECE, $$0 -> StructurePieceType.MINE_SHAFT_ROOM);
/* 172 */   public static final Registry<StructureType<?>> STRUCTURE_TYPE = registerSimple(Registries.STRUCTURE_TYPE, $$0 -> StructureType.JIGSAW);
/* 173 */   public static final Registry<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPE = registerSimple(Registries.PLACEMENT_MODIFIER_TYPE, $$0 -> PlacementModifierType.COUNT);
/* 174 */   public static final Registry<BlockStateProviderType<?>> BLOCKSTATE_PROVIDER_TYPE = registerSimple(Registries.BLOCK_STATE_PROVIDER_TYPE, $$0 -> BlockStateProviderType.SIMPLE_STATE_PROVIDER);
/* 175 */   public static final Registry<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPE = registerSimple(Registries.FOLIAGE_PLACER_TYPE, $$0 -> FoliagePlacerType.BLOB_FOLIAGE_PLACER);
/* 176 */   public static final Registry<TrunkPlacerType<?>> TRUNK_PLACER_TYPE = registerSimple(Registries.TRUNK_PLACER_TYPE, $$0 -> TrunkPlacerType.STRAIGHT_TRUNK_PLACER);
/* 177 */   public static final Registry<RootPlacerType<?>> ROOT_PLACER_TYPE = registerSimple(Registries.ROOT_PLACER_TYPE, $$0 -> RootPlacerType.MANGROVE_ROOT_PLACER);
/* 178 */   public static final Registry<TreeDecoratorType<?>> TREE_DECORATOR_TYPE = registerSimple(Registries.TREE_DECORATOR_TYPE, $$0 -> TreeDecoratorType.LEAVE_VINE);
/* 179 */   public static final Registry<FeatureSizeType<?>> FEATURE_SIZE_TYPE = registerSimple(Registries.FEATURE_SIZE_TYPE, $$0 -> FeatureSizeType.TWO_LAYERS_FEATURE_SIZE);
/* 180 */   public static final Registry<Codec<? extends BiomeSource>> BIOME_SOURCE = registerSimple(Registries.BIOME_SOURCE, Lifecycle.stable(), BiomeSources::bootstrap);
/* 181 */   public static final Registry<Codec<? extends ChunkGenerator>> CHUNK_GENERATOR = registerSimple(Registries.CHUNK_GENERATOR, Lifecycle.stable(), ChunkGenerators::bootstrap);
/* 182 */   public static final Registry<Codec<? extends SurfaceRules.ConditionSource>> MATERIAL_CONDITION = registerSimple(Registries.MATERIAL_CONDITION, SurfaceRules.ConditionSource::bootstrap);
/* 183 */   public static final Registry<Codec<? extends SurfaceRules.RuleSource>> MATERIAL_RULE = registerSimple(Registries.MATERIAL_RULE, SurfaceRules.RuleSource::bootstrap);
/* 184 */   public static final Registry<Codec<? extends DensityFunction>> DENSITY_FUNCTION_TYPE = registerSimple(Registries.DENSITY_FUNCTION_TYPE, DensityFunctions::bootstrap);
/* 185 */   public static final Registry<MapCodec<? extends Block>> BLOCK_TYPE = registerSimple(Registries.BLOCK_TYPE, BlockTypes::bootstrap);
/* 186 */   public static final Registry<StructureProcessorType<?>> STRUCTURE_PROCESSOR = registerSimple(Registries.STRUCTURE_PROCESSOR, $$0 -> StructureProcessorType.BLOCK_IGNORE);
/* 187 */   public static final Registry<StructurePoolElementType<?>> STRUCTURE_POOL_ELEMENT = registerSimple(Registries.STRUCTURE_POOL_ELEMENT, $$0 -> StructurePoolElementType.EMPTY);
/* 188 */   public static final Registry<Codec<? extends PoolAliasBinding>> POOL_ALIAS_BINDING_TYPE = registerSimple(Registries.POOL_ALIAS_BINDING, PoolAliasBindings::bootstrap);
/* 189 */   public static final Registry<CatVariant> CAT_VARIANT = registerSimple(Registries.CAT_VARIANT, CatVariant::bootstrap);
/* 190 */   public static final Registry<FrogVariant> FROG_VARIANT = registerSimple(Registries.FROG_VARIANT, $$0 -> FrogVariant.TEMPERATE);
/* 191 */   public static final Registry<BannerPattern> BANNER_PATTERN = registerSimple(Registries.BANNER_PATTERN, BannerPatterns::bootstrap);
/* 192 */   public static final Registry<Instrument> INSTRUMENT = registerSimple(Registries.INSTRUMENT, Instruments::bootstrap);
/* 193 */   public static final Registry<String> DECORATED_POT_PATTERNS = registerSimple(Registries.DECORATED_POT_PATTERNS, DecoratedPotPatterns::bootstrap);
/* 194 */   public static final Registry<CreativeModeTab> CREATIVE_MODE_TAB = registerSimple(Registries.CREATIVE_MODE_TAB, CreativeModeTabs::bootstrap);
/* 195 */   public static final Registry<CriterionTrigger<?>> TRIGGER_TYPES = registerSimple(Registries.TRIGGER_TYPE, CriteriaTriggers::bootstrap);
/* 196 */   public static final Registry<NumberFormatType<?>> NUMBER_FORMAT_TYPE = registerSimple(Registries.NUMBER_FORMAT_TYPE, NumberFormatTypes::bootstrap);
/*     */   
/* 198 */   public static final Registry<? extends Registry<?>> REGISTRY = (Registry)WRITABLE_REGISTRY;
/*     */   
/*     */   private static <T> Registry<T> registerSimple(ResourceKey<? extends Registry<T>> $$0, RegistryBootstrap<T> $$1) {
/* 201 */     return registerSimple($$0, Lifecycle.stable(), $$1);
/*     */   }
/*     */   
/*     */   private static <T> Registry<T> registerSimpleWithIntrusiveHolders(ResourceKey<? extends Registry<T>> $$0, RegistryBootstrap<T> $$1) {
/* 205 */     return (Registry<T>)internalRegister($$0, new MappedRegistry($$0, Lifecycle.stable(), true), $$1, Lifecycle.stable());
/*     */   }
/*     */   
/*     */   private static <T> DefaultedRegistry<T> registerDefaulted(ResourceKey<? extends Registry<T>> $$0, String $$1, RegistryBootstrap<T> $$2) {
/* 209 */     return registerDefaulted($$0, $$1, Lifecycle.stable(), $$2);
/*     */   }
/*     */   
/*     */   private static <T> DefaultedRegistry<T> registerDefaultedWithIntrusiveHolders(ResourceKey<? extends Registry<T>> $$0, String $$1, RegistryBootstrap<T> $$2) {
/* 213 */     return registerDefaultedWithIntrusiveHolders($$0, $$1, Lifecycle.stable(), $$2);
/*     */   }
/*     */   
/*     */   private static <T> Registry<T> registerSimple(ResourceKey<? extends Registry<T>> $$0, Lifecycle $$1, RegistryBootstrap<T> $$2) {
/* 217 */     return (Registry<T>)internalRegister($$0, new MappedRegistry($$0, $$1, false), $$2, $$1);
/*     */   }
/*     */   
/*     */   private static <T> DefaultedRegistry<T> registerDefaulted(ResourceKey<? extends Registry<T>> $$0, String $$1, Lifecycle $$2, RegistryBootstrap<T> $$3) {
/* 221 */     return (DefaultedRegistry<T>)internalRegister($$0, new DefaultedMappedRegistry($$1, $$0, $$2, false), $$3, $$2);
/*     */   }
/*     */   
/*     */   private static <T> DefaultedRegistry<T> registerDefaultedWithIntrusiveHolders(ResourceKey<? extends Registry<T>> $$0, String $$1, Lifecycle $$2, RegistryBootstrap<T> $$3) {
/* 225 */     return (DefaultedRegistry<T>)internalRegister($$0, new DefaultedMappedRegistry($$1, $$0, $$2, true), $$3, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T, R extends WritableRegistry<T>> R internalRegister(ResourceKey<? extends Registry<T>> $$0, R $$1, RegistryBootstrap<T> $$2, Lifecycle $$3) {
/* 230 */     Bootstrap.checkBootstrapCalled(() -> "registry " + $$0);
/* 231 */     ResourceLocation $$4 = $$0.location();
/* 232 */     LOADERS.put($$4, () -> $$0.run((Registry)$$1));
/*     */     
/* 234 */     WRITABLE_REGISTRY.register($$0, $$1, $$3);
/* 235 */     return $$1;
/*     */   }
/*     */   
/*     */   public static void bootStrap() {
/* 239 */     createContents();
/* 240 */     freeze();
/* 241 */     validate(REGISTRY);
/*     */   }
/*     */   
/*     */   private static void createContents() {
/* 245 */     LOADERS.forEach(($$0, $$1) -> {
/*     */           if ($$1.get() == null) {
/*     */             LOGGER.error("Unable to bootstrap registry '{}'", $$0);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static void freeze() {
/* 254 */     REGISTRY.freeze();
/* 255 */     for (Registry<?> $$0 : REGISTRY) {
/* 256 */       $$0.freeze();
/*     */     }
/*     */   }
/*     */   
/*     */   private static <T extends Registry<?>> void validate(Registry<T> $$0) {
/* 261 */     $$0.forEach($$1 -> {
/*     */           if ($$1.keySet().isEmpty())
/*     */             Util.logAndPauseIfInIde("Registry '" + $$0.getKey($$1) + "' was empty after loading"); 
/*     */           if ($$1 instanceof DefaultedRegistry) {
/*     */             ResourceLocation $$2 = ((DefaultedRegistry)$$1).getDefaultKey();
/*     */             Validate.notNull($$1.get($$2), "Missing default of DefaultedMappedRegistry: " + $$2, new Object[0]);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface RegistryBootstrap<T> {
/*     */     T run(Registry<T> param1Registry);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\registries\BuiltInRegistries.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */