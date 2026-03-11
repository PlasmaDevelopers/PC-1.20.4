/*      */ package net.minecraft.util.datafix;
/*      */ 
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*      */ import com.mojang.datafixers.DSL;
/*      */ import com.mojang.datafixers.DataFix;
/*      */ import com.mojang.datafixers.DataFixer;
/*      */ import com.mojang.datafixers.DataFixerBuilder;
/*      */ import com.mojang.datafixers.Typed;
/*      */ import com.mojang.datafixers.schemas.Schema;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.UnaryOperator;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.Stream;
/*      */ import net.minecraft.SharedConstants;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.util.datafix.fixes.AbstractArrowPickupFix;
/*      */ import net.minecraft.util.datafix.fixes.AddFlagIfNotPresentFix;
/*      */ import net.minecraft.util.datafix.fixes.AddNewChoices;
/*      */ import net.minecraft.util.datafix.fixes.AdvancementsFix;
/*      */ import net.minecraft.util.datafix.fixes.AdvancementsRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.AttributesRename;
/*      */ import net.minecraft.util.datafix.fixes.BedItemColorFix;
/*      */ import net.minecraft.util.datafix.fixes.BiomeFix;
/*      */ import net.minecraft.util.datafix.fixes.BitStorageAlignFix;
/*      */ import net.minecraft.util.datafix.fixes.BlendingDataFix;
/*      */ import net.minecraft.util.datafix.fixes.BlendingDataRemoveFromNetherEndFix;
/*      */ import net.minecraft.util.datafix.fixes.BlockEntityBannerColorFix;
/*      */ import net.minecraft.util.datafix.fixes.BlockEntityBlockStateFix;
/*      */ import net.minecraft.util.datafix.fixes.BlockEntityCustomNameToComponentFix;
/*      */ import net.minecraft.util.datafix.fixes.BlockEntityIdFix;
/*      */ import net.minecraft.util.datafix.fixes.BlockEntityJukeboxFix;
/*      */ import net.minecraft.util.datafix.fixes.BlockEntityKeepPacked;
/*      */ import net.minecraft.util.datafix.fixes.BlockEntityRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.BlockEntityShulkerBoxColorFix;
/*      */ import net.minecraft.util.datafix.fixes.BlockEntitySignDoubleSidedEditableTextFix;
/*      */ import net.minecraft.util.datafix.fixes.BlockEntitySignTextStrictJsonFix;
/*      */ import net.minecraft.util.datafix.fixes.BlockEntityUUIDFix;
/*      */ import net.minecraft.util.datafix.fixes.BlockNameFlatteningFix;
/*      */ import net.minecraft.util.datafix.fixes.BlockRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.BlockRenameFixWithJigsaw;
/*      */ import net.minecraft.util.datafix.fixes.BlockStateStructureTemplateFix;
/*      */ import net.minecraft.util.datafix.fixes.CatTypeFix;
/*      */ import net.minecraft.util.datafix.fixes.CauldronRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.CavesAndCliffsRenames;
/*      */ import net.minecraft.util.datafix.fixes.ChunkBedBlockEntityInjecterFix;
/*      */ import net.minecraft.util.datafix.fixes.ChunkBiomeFix;
/*      */ import net.minecraft.util.datafix.fixes.ChunkDeleteIgnoredLightDataFix;
/*      */ import net.minecraft.util.datafix.fixes.ChunkDeleteLightFix;
/*      */ import net.minecraft.util.datafix.fixes.ChunkHeightAndBiomeFix;
/*      */ import net.minecraft.util.datafix.fixes.ChunkLightRemoveFix;
/*      */ import net.minecraft.util.datafix.fixes.ChunkPalettedStorageFix;
/*      */ import net.minecraft.util.datafix.fixes.ChunkProtoTickListFix;
/*      */ import net.minecraft.util.datafix.fixes.ChunkRenamesFix;
/*      */ import net.minecraft.util.datafix.fixes.ChunkStatusFix;
/*      */ import net.minecraft.util.datafix.fixes.ChunkStatusFix2;
/*      */ import net.minecraft.util.datafix.fixes.ChunkStructuresTemplateRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.ChunkToProtochunkFix;
/*      */ import net.minecraft.util.datafix.fixes.ColorlessShulkerEntityFix;
/*      */ import net.minecraft.util.datafix.fixes.CriteriaRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.DecoratedPotFieldRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.DropInvalidSignDataFix;
/*      */ import net.minecraft.util.datafix.fixes.DyeItemRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.EffectDurationFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityArmorStandSilentFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityBlockStateFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityBrushableBlockFieldsRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityCatSplitFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityCodSalmonFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityCustomNameToComponentFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityElderGuardianSplitFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityEquipmentToArmorAndHandFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityGoatMissingStateFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityHealthFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityHorseSaddleFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityHorseSplitFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityIdFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityItemFrameDirectionFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityMinecartIdentifiersFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityPaintingFieldsRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityPaintingItemFrameDirectionFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityPaintingMotiveFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityProjectileOwnerFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityPufferfishRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityRavagerRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityRedundantChanceTagsFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityRidingToPassengersFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityShulkerColorFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityShulkerRotationFix;
/*      */ import net.minecraft.util.datafix.fixes.EntitySkeletonSplitFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityStringUuidFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityTheRenameningFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityTippedArrowFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityUUIDFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityVariantFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityWolfColorFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityZombieSplitFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityZombieVillagerTypeFix;
/*      */ import net.minecraft.util.datafix.fixes.EntityZombifiedPiglinRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.FeatureFlagRemoveFix;
/*      */ import net.minecraft.util.datafix.fixes.FilteredBooksFix;
/*      */ import net.minecraft.util.datafix.fixes.FilteredSignsFix;
/*      */ import net.minecraft.util.datafix.fixes.FixProjectileStoredItem;
/*      */ import net.minecraft.util.datafix.fixes.ForcePoiRebuild;
/*      */ import net.minecraft.util.datafix.fixes.FurnaceRecipeFix;
/*      */ import net.minecraft.util.datafix.fixes.GoatHornIdFix;
/*      */ import net.minecraft.util.datafix.fixes.GossipUUIDFix;
/*      */ import net.minecraft.util.datafix.fixes.HeightmapRenamingFix;
/*      */ import net.minecraft.util.datafix.fixes.IglooMetadataRemovalFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemBannerColorFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemCustomNameToComponentFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemIdFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemLoreFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemPotionFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemRemoveBlockEntityTagFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemShulkerBoxColorFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemSpawnEggFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemStackEnchantmentNamesFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemStackMapIdFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemStackSpawnEggFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemStackTheFlatteningFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemStackUUIDFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemWaterPotionFix;
/*      */ import net.minecraft.util.datafix.fixes.ItemWrittenBookPagesStrictJsonFix;
/*      */ import net.minecraft.util.datafix.fixes.JigsawPropertiesFix;
/*      */ import net.minecraft.util.datafix.fixes.JigsawRotationFix;
/*      */ import net.minecraft.util.datafix.fixes.LeavesFix;
/*      */ import net.minecraft.util.datafix.fixes.LegacyDragonFightFix;
/*      */ import net.minecraft.util.datafix.fixes.LevelDataGeneratorOptionsFix;
/*      */ import net.minecraft.util.datafix.fixes.LevelFlatGeneratorInfoFix;
/*      */ import net.minecraft.util.datafix.fixes.LevelLegacyWorldGenSettingsFix;
/*      */ import net.minecraft.util.datafix.fixes.LevelUUIDFix;
/*      */ import net.minecraft.util.datafix.fixes.MapIdFix;
/*      */ import net.minecraft.util.datafix.fixes.MemoryExpiryDataFix;
/*      */ import net.minecraft.util.datafix.fixes.MissingDimensionFix;
/*      */ import net.minecraft.util.datafix.fixes.MobEffectIdFix;
/*      */ import net.minecraft.util.datafix.fixes.MobSpawnerEntityIdentifiersFix;
/*      */ import net.minecraft.util.datafix.fixes.NamedEntityFix;
/*      */ import net.minecraft.util.datafix.fixes.NamespacedTypeRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.NewVillageFix;
/*      */ import net.minecraft.util.datafix.fixes.ObjectiveDisplayNameFix;
/*      */ import net.minecraft.util.datafix.fixes.ObjectiveRenderTypeFix;
/*      */ import net.minecraft.util.datafix.fixes.OminousBannerBlockEntityRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.OminousBannerRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.OptionsAccessibilityOnboardFix;
/*      */ import net.minecraft.util.datafix.fixes.OptionsAddTextBackgroundFix;
/*      */ import net.minecraft.util.datafix.fixes.OptionsAmbientOcclusionFix;
/*      */ import net.minecraft.util.datafix.fixes.OptionsForceVBOFix;
/*      */ import net.minecraft.util.datafix.fixes.OptionsKeyLwjgl3Fix;
/*      */ import net.minecraft.util.datafix.fixes.OptionsKeyTranslationFix;
/*      */ import net.minecraft.util.datafix.fixes.OptionsLowerCaseLanguageFix;
/*      */ import net.minecraft.util.datafix.fixes.OptionsProgrammerArtFix;
/*      */ import net.minecraft.util.datafix.fixes.OptionsRenameFieldFix;
/*      */ import net.minecraft.util.datafix.fixes.OverreachingTickFix;
/*      */ import net.minecraft.util.datafix.fixes.PlayerUUIDFix;
/*      */ import net.minecraft.util.datafix.fixes.PoiTypeRemoveFix;
/*      */ import net.minecraft.util.datafix.fixes.PoiTypeRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.PrimedTntBlockStateFixer;
/*      */ import net.minecraft.util.datafix.fixes.RandomSequenceSettingsFix;
/*      */ import net.minecraft.util.datafix.fixes.RecipesFix;
/*      */ import net.minecraft.util.datafix.fixes.RecipesRenameningFix;
/*      */ import net.minecraft.util.datafix.fixes.RedstoneWireConnectionsFix;
/*      */ import net.minecraft.util.datafix.fixes.References;
/*      */ import net.minecraft.util.datafix.fixes.RemapChunkStatusFix;
/*      */ import net.minecraft.util.datafix.fixes.RemoveGolemGossipFix;
/*      */ import net.minecraft.util.datafix.fixes.RenamedCoralFansFix;
/*      */ import net.minecraft.util.datafix.fixes.RenamedCoralFix;
/*      */ import net.minecraft.util.datafix.fixes.ReorganizePoi;
/*      */ import net.minecraft.util.datafix.fixes.SavedDataFeaturePoolElementFix;
/*      */ import net.minecraft.util.datafix.fixes.SavedDataUUIDFix;
/*      */ import net.minecraft.util.datafix.fixes.ScoreboardDisplaySlotFix;
/*      */ import net.minecraft.util.datafix.fixes.SpawnerDataFix;
/*      */ import net.minecraft.util.datafix.fixes.StatsCounterFix;
/*      */ import net.minecraft.util.datafix.fixes.StatsRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.StriderGravityFix;
/*      */ import net.minecraft.util.datafix.fixes.StructureReferenceCountFix;
/*      */ import net.minecraft.util.datafix.fixes.StructureSettingsFlattenFix;
/*      */ import net.minecraft.util.datafix.fixes.StructuresBecomeConfiguredFix;
/*      */ import net.minecraft.util.datafix.fixes.TeamDisplayNameFix;
/*      */ import net.minecraft.util.datafix.fixes.TrappedChestBlockEntityFix;
/*      */ import net.minecraft.util.datafix.fixes.VariantRenameFix;
/*      */ import net.minecraft.util.datafix.fixes.VillagerDataFix;
/*      */ import net.minecraft.util.datafix.fixes.VillagerFollowRangeFix;
/*      */ import net.minecraft.util.datafix.fixes.VillagerRebuildLevelAndXpFix;
/*      */ import net.minecraft.util.datafix.fixes.VillagerTradeFix;
/*      */ import net.minecraft.util.datafix.fixes.WallPropertyFix;
/*      */ import net.minecraft.util.datafix.fixes.WeaponSmithChestLootTableFix;
/*      */ import net.minecraft.util.datafix.fixes.WorldGenSettingsDisallowOldCustomWorldsFix;
/*      */ import net.minecraft.util.datafix.fixes.WorldGenSettingsFix;
/*      */ import net.minecraft.util.datafix.fixes.WorldGenSettingsHeightAndBiomeFix;
/*      */ import net.minecraft.util.datafix.fixes.WriteAndReadFix;
/*      */ import net.minecraft.util.datafix.fixes.ZombieVillagerRebuildXpFix;
/*      */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DataFixers
/*      */ {
/*  280 */   private static final BiFunction<Integer, Schema, Schema> SAME = Schema::new;
/*  281 */   private static final BiFunction<Integer, Schema, Schema> SAME_NAMESPACED = NamespacedSchema::new;
/*  282 */   private static final DataFixer dataFixer = createFixerUpper(SharedConstants.DATA_FIX_TYPES_TO_OPTIMIZE);
/*      */ 
/*      */   
/*      */   public static final int BLENDING_VERSION = 3441;
/*      */ 
/*      */   
/*      */   public static DataFixer getDataFixer() {
/*  289 */     return dataFixer;
/*      */   }
/*      */   
/*      */   private static synchronized DataFixer createFixerUpper(Set<DSL.TypeReference> $$0) {
/*  293 */     DataFixerBuilder $$1 = new DataFixerBuilder(SharedConstants.getCurrentVersion().getDataVersion().getVersion());
/*  294 */     addFixers($$1);
/*      */     
/*  296 */     if ($$0.isEmpty()) {
/*  297 */       return $$1.buildUnoptimized();
/*      */     }
/*      */     
/*  300 */     Executor $$2 = Executors.newSingleThreadExecutor((new ThreadFactoryBuilder())
/*  301 */         .setNameFormat("Datafixer Bootstrap")
/*  302 */         .setDaemon(true)
/*  303 */         .setPriority(1)
/*  304 */         .build());
/*  305 */     return $$1.buildOptimized($$0, $$2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addFixers(DataFixerBuilder $$0) {
/*  313 */     $$0.addSchema(99, net.minecraft.util.datafix.schemas.V99::new);
/*      */ 
/*      */     
/*  316 */     Schema $$1 = $$0.addSchema(100, net.minecraft.util.datafix.schemas.V100::new);
/*  317 */     $$0.addFixer((DataFix)new EntityEquipmentToArmorAndHandFix($$1, true));
/*      */     
/*  319 */     Schema $$2 = $$0.addSchema(101, SAME);
/*  320 */     $$0.addFixer((DataFix)new BlockEntitySignTextStrictJsonFix($$2, false));
/*      */     
/*  322 */     Schema $$3 = $$0.addSchema(102, net.minecraft.util.datafix.schemas.V102::new);
/*  323 */     $$0.addFixer((DataFix)new ItemIdFix($$3, true));
/*  324 */     $$0.addFixer((DataFix)new ItemPotionFix($$3, false));
/*      */     
/*  326 */     Schema $$4 = $$0.addSchema(105, SAME);
/*  327 */     $$0.addFixer((DataFix)new ItemSpawnEggFix($$4, true));
/*      */ 
/*      */     
/*  330 */     Schema $$5 = $$0.addSchema(106, net.minecraft.util.datafix.schemas.V106::new);
/*  331 */     $$0.addFixer((DataFix)new MobSpawnerEntityIdentifiersFix($$5, true));
/*      */     
/*  333 */     Schema $$6 = $$0.addSchema(107, net.minecraft.util.datafix.schemas.V107::new);
/*  334 */     $$0.addFixer((DataFix)new EntityMinecartIdentifiersFix($$6, true));
/*      */     
/*  336 */     Schema $$7 = $$0.addSchema(108, SAME);
/*  337 */     $$0.addFixer((DataFix)new EntityStringUuidFix($$7, true));
/*      */     
/*  339 */     Schema $$8 = $$0.addSchema(109, SAME);
/*  340 */     $$0.addFixer((DataFix)new EntityHealthFix($$8, true));
/*      */     
/*  342 */     Schema $$9 = $$0.addSchema(110, SAME);
/*  343 */     $$0.addFixer((DataFix)new EntityHorseSaddleFix($$9, true));
/*      */     
/*  345 */     Schema $$10 = $$0.addSchema(111, SAME);
/*  346 */     $$0.addFixer((DataFix)new EntityPaintingItemFrameDirectionFix($$10, true));
/*      */     
/*  348 */     Schema $$11 = $$0.addSchema(113, SAME);
/*  349 */     $$0.addFixer((DataFix)new EntityRedundantChanceTagsFix($$11, true));
/*      */     
/*  351 */     Schema $$12 = $$0.addSchema(135, net.minecraft.util.datafix.schemas.V135::new);
/*  352 */     $$0.addFixer((DataFix)new EntityRidingToPassengersFix($$12, true));
/*      */     
/*  354 */     Schema $$13 = $$0.addSchema(143, net.minecraft.util.datafix.schemas.V143::new);
/*  355 */     $$0.addFixer((DataFix)new EntityTippedArrowFix($$13, true));
/*      */     
/*  357 */     Schema $$14 = $$0.addSchema(147, SAME);
/*  358 */     $$0.addFixer((DataFix)new EntityArmorStandSilentFix($$14, true));
/*      */     
/*  360 */     Schema $$15 = $$0.addSchema(165, SAME);
/*  361 */     $$0.addFixer((DataFix)new ItemWrittenBookPagesStrictJsonFix($$15, true));
/*      */ 
/*      */     
/*  364 */     Schema $$16 = $$0.addSchema(501, net.minecraft.util.datafix.schemas.V501::new);
/*  365 */     $$0.addFixer((DataFix)new AddNewChoices($$16, "Add 1.10 entities fix", References.ENTITY));
/*      */     
/*  367 */     Schema $$17 = $$0.addSchema(502, SAME);
/*  368 */     $$0.addFixer(ItemRenameFix.create($$17, "cooked_fished item renamer", $$0 -> Objects.equals(NamespacedSchema.ensureNamespaced($$0), "minecraft:cooked_fished") ? "minecraft:cooked_fish" : $$0));
/*      */ 
/*      */     
/*  371 */     $$0.addFixer((DataFix)new EntityZombieVillagerTypeFix($$17, false));
/*      */     
/*  373 */     Schema $$18 = $$0.addSchema(505, SAME);
/*  374 */     $$0.addFixer((DataFix)new OptionsForceVBOFix($$18, false));
/*      */ 
/*      */     
/*  377 */     Schema $$19 = $$0.addSchema(700, net.minecraft.util.datafix.schemas.V700::new);
/*  378 */     $$0.addFixer((DataFix)new EntityElderGuardianSplitFix($$19, true));
/*      */     
/*  380 */     Schema $$20 = $$0.addSchema(701, net.minecraft.util.datafix.schemas.V701::new);
/*  381 */     $$0.addFixer((DataFix)new EntitySkeletonSplitFix($$20, true));
/*      */     
/*  383 */     Schema $$21 = $$0.addSchema(702, net.minecraft.util.datafix.schemas.V702::new);
/*  384 */     $$0.addFixer((DataFix)new EntityZombieSplitFix($$21, true));
/*      */     
/*  386 */     Schema $$22 = $$0.addSchema(703, net.minecraft.util.datafix.schemas.V703::new);
/*  387 */     $$0.addFixer((DataFix)new EntityHorseSplitFix($$22, true));
/*      */ 
/*      */     
/*  390 */     Schema $$23 = $$0.addSchema(704, net.minecraft.util.datafix.schemas.V704::new);
/*  391 */     $$0.addFixer((DataFix)new BlockEntityIdFix($$23, true));
/*      */     
/*  393 */     Schema $$24 = $$0.addSchema(705, net.minecraft.util.datafix.schemas.V705::new);
/*  394 */     $$0.addFixer((DataFix)new EntityIdFix($$24, true));
/*      */     
/*  396 */     Schema $$25 = $$0.addSchema(804, SAME_NAMESPACED);
/*  397 */     $$0.addFixer((DataFix)new ItemBannerColorFix($$25, true));
/*      */     
/*  399 */     Schema $$26 = $$0.addSchema(806, SAME_NAMESPACED);
/*  400 */     $$0.addFixer((DataFix)new ItemWaterPotionFix($$26, false));
/*      */ 
/*      */     
/*  403 */     Schema $$27 = $$0.addSchema(808, net.minecraft.util.datafix.schemas.V808::new);
/*  404 */     $$0.addFixer((DataFix)new AddNewChoices($$27, "added shulker box", References.BLOCK_ENTITY));
/*      */     
/*  406 */     Schema $$28 = $$0.addSchema(808, 1, SAME_NAMESPACED);
/*  407 */     $$0.addFixer((DataFix)new EntityShulkerColorFix($$28, false));
/*      */     
/*  409 */     Schema $$29 = $$0.addSchema(813, SAME_NAMESPACED);
/*  410 */     $$0.addFixer((DataFix)new ItemShulkerBoxColorFix($$29, false));
/*  411 */     $$0.addFixer((DataFix)new BlockEntityShulkerBoxColorFix($$29, false));
/*      */     
/*  413 */     Schema $$30 = $$0.addSchema(816, SAME_NAMESPACED);
/*  414 */     $$0.addFixer((DataFix)new OptionsLowerCaseLanguageFix($$30, false));
/*      */ 
/*      */     
/*  417 */     Schema $$31 = $$0.addSchema(820, SAME_NAMESPACED);
/*  418 */     $$0.addFixer(ItemRenameFix.create($$31, "totem item renamer", createRenamer("minecraft:totem", "minecraft:totem_of_undying")));
/*      */ 
/*      */     
/*  421 */     Schema $$32 = $$0.addSchema(1022, net.minecraft.util.datafix.schemas.V1022::new);
/*  422 */     $$0.addFixer((DataFix)new WriteAndReadFix($$32, "added shoulder entities to players", References.PLAYER));
/*      */     
/*  424 */     Schema $$33 = $$0.addSchema(1125, net.minecraft.util.datafix.schemas.V1125::new);
/*  425 */     $$0.addFixer((DataFix)new ChunkBedBlockEntityInjecterFix($$33, true));
/*  426 */     $$0.addFixer((DataFix)new BedItemColorFix($$33, false));
/*      */ 
/*      */     
/*  429 */     Schema $$34 = $$0.addSchema(1344, SAME_NAMESPACED);
/*  430 */     $$0.addFixer((DataFix)new OptionsKeyLwjgl3Fix($$34, false));
/*      */     
/*  432 */     Schema $$35 = $$0.addSchema(1446, SAME_NAMESPACED);
/*  433 */     $$0.addFixer((DataFix)new OptionsKeyTranslationFix($$35, false));
/*      */     
/*  435 */     Schema $$36 = $$0.addSchema(1450, SAME_NAMESPACED);
/*  436 */     $$0.addFixer((DataFix)new BlockStateStructureTemplateFix($$36, false));
/*      */     
/*  438 */     Schema $$37 = $$0.addSchema(1451, net.minecraft.util.datafix.schemas.V1451::new);
/*  439 */     $$0.addFixer((DataFix)new AddNewChoices($$37, "AddTrappedChestFix", References.BLOCK_ENTITY));
/*      */     
/*  441 */     Schema $$38 = $$0.addSchema(1451, 1, net.minecraft.util.datafix.schemas.V1451_1::new);
/*  442 */     $$0.addFixer((DataFix)new ChunkPalettedStorageFix($$38, true));
/*      */ 
/*      */     
/*  445 */     Schema $$39 = $$0.addSchema(1451, 2, net.minecraft.util.datafix.schemas.V1451_2::new);
/*  446 */     $$0.addFixer((DataFix)new BlockEntityBlockStateFix($$39, true));
/*      */     
/*  448 */     Schema $$40 = $$0.addSchema(1451, 3, net.minecraft.util.datafix.schemas.V1451_3::new);
/*  449 */     $$0.addFixer((DataFix)new EntityBlockStateFix($$40, true));
/*  450 */     $$0.addFixer((DataFix)new ItemStackMapIdFix($$40, false));
/*      */     
/*  452 */     Schema $$41 = $$0.addSchema(1451, 4, net.minecraft.util.datafix.schemas.V1451_4::new);
/*  453 */     $$0.addFixer((DataFix)new BlockNameFlatteningFix($$41, true));
/*  454 */     $$0.addFixer((DataFix)new ItemStackTheFlatteningFix($$41, false));
/*      */ 
/*      */     
/*  457 */     Schema $$42 = $$0.addSchema(1451, 5, net.minecraft.util.datafix.schemas.V1451_5::new);
/*  458 */     $$0.addFixer((DataFix)new ItemRemoveBlockEntityTagFix($$42, false, Set.of(new String[] { "minecraft:note_block", "minecraft:flower_pot", "minecraft:dandelion", "minecraft:poppy", "minecraft:blue_orchid", "minecraft:allium", "minecraft:azure_bluet", "minecraft:red_tulip", "minecraft:orange_tulip", "minecraft:white_tulip", "minecraft:pink_tulip", "minecraft:oxeye_daisy", "minecraft:cactus", "minecraft:brown_mushroom", "minecraft:red_mushroom", "minecraft:oak_sapling", "minecraft:spruce_sapling", "minecraft:birch_sapling", "minecraft:jungle_sapling", "minecraft:acacia_sapling", "minecraft:dark_oak_sapling", "minecraft:dead_bush", "minecraft:fern" })));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  483 */     $$0.addFixer((DataFix)new AddNewChoices($$42, "RemoveNoteBlockFlowerPotFix", References.BLOCK_ENTITY));
/*  484 */     $$0.addFixer((DataFix)new ItemStackSpawnEggFix($$42, false, "minecraft:spawn_egg"));
/*  485 */     $$0.addFixer((DataFix)new EntityWolfColorFix($$42, false));
/*  486 */     $$0.addFixer((DataFix)new BlockEntityBannerColorFix($$42, false));
/*  487 */     $$0.addFixer((DataFix)new LevelFlatGeneratorInfoFix($$42, false));
/*      */     
/*  489 */     Schema $$43 = $$0.addSchema(1451, 6, net.minecraft.util.datafix.schemas.V1451_6::new);
/*  490 */     $$0.addFixer((DataFix)new StatsCounterFix($$43, true));
/*  491 */     $$0.addFixer((DataFix)new BlockEntityJukeboxFix($$43, false));
/*      */     
/*  493 */     Schema $$44 = $$0.addSchema(1451, 7, SAME_NAMESPACED);
/*  494 */     $$0.addFixer((DataFix)new VillagerTradeFix($$44, false));
/*      */     
/*  496 */     Schema $$45 = $$0.addSchema(1456, SAME_NAMESPACED);
/*  497 */     $$0.addFixer((DataFix)new EntityItemFrameDirectionFix($$45, false));
/*      */     
/*  499 */     Schema $$46 = $$0.addSchema(1458, SAME_NAMESPACED);
/*  500 */     $$0.addFixer((DataFix)new EntityCustomNameToComponentFix($$46, false));
/*  501 */     $$0.addFixer((DataFix)new ItemCustomNameToComponentFix($$46, false));
/*  502 */     $$0.addFixer((DataFix)new BlockEntityCustomNameToComponentFix($$46, false));
/*      */     
/*  504 */     Schema $$47 = $$0.addSchema(1460, net.minecraft.util.datafix.schemas.V1460::new);
/*  505 */     $$0.addFixer((DataFix)new EntityPaintingMotiveFix($$47, false));
/*      */     
/*  507 */     Schema $$48 = $$0.addSchema(1466, net.minecraft.util.datafix.schemas.V1466::new);
/*  508 */     $$0.addFixer((DataFix)new AddNewChoices($$48, "Add DUMMY block entity", References.BLOCK_ENTITY));
/*  509 */     $$0.addFixer((DataFix)new ChunkToProtochunkFix($$48, true));
/*      */ 
/*      */     
/*  512 */     Schema $$49 = $$0.addSchema(1470, net.minecraft.util.datafix.schemas.V1470::new);
/*  513 */     $$0.addFixer((DataFix)new AddNewChoices($$49, "Add 1.13 entities fix", References.ENTITY));
/*      */     
/*  515 */     Schema $$50 = $$0.addSchema(1474, SAME_NAMESPACED);
/*  516 */     $$0.addFixer((DataFix)new ColorlessShulkerEntityFix($$50, false));
/*  517 */     $$0.addFixer(BlockRenameFix.create($$50, "Colorless shulker block fixer", $$0 -> Objects.equals(NamespacedSchema.ensureNamespaced($$0), "minecraft:purple_shulker_box") ? "minecraft:shulker_box" : $$0));
/*  518 */     $$0.addFixer(ItemRenameFix.create($$50, "Colorless shulker item fixer", $$0 -> Objects.equals(NamespacedSchema.ensureNamespaced($$0), "minecraft:purple_shulker_box") ? "minecraft:shulker_box" : $$0));
/*      */     
/*  520 */     Schema $$51 = $$0.addSchema(1475, SAME_NAMESPACED);
/*  521 */     $$0.addFixer(BlockRenameFix.create($$51, "Flowing fixer", createRenamer(
/*  522 */             (Map<String, String>)ImmutableMap.of("minecraft:flowing_water", "minecraft:water", "minecraft:flowing_lava", "minecraft:lava"))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  528 */     Schema $$52 = $$0.addSchema(1480, SAME_NAMESPACED);
/*  529 */     $$0.addFixer(BlockRenameFix.create($$52, "Rename coral blocks", createRenamer(RenamedCoralFix.RENAMED_IDS)));
/*  530 */     $$0.addFixer(ItemRenameFix.create($$52, "Rename coral items", createRenamer(RenamedCoralFix.RENAMED_IDS)));
/*      */     
/*  532 */     Schema $$53 = $$0.addSchema(1481, net.minecraft.util.datafix.schemas.V1481::new);
/*  533 */     $$0.addFixer((DataFix)new AddNewChoices($$53, "Add conduit", References.BLOCK_ENTITY));
/*      */     
/*  535 */     Schema $$54 = $$0.addSchema(1483, net.minecraft.util.datafix.schemas.V1483::new);
/*  536 */     $$0.addFixer((DataFix)new EntityPufferfishRenameFix($$54, true));
/*  537 */     $$0.addFixer(ItemRenameFix.create($$54, "Rename pufferfish egg item", createRenamer(EntityPufferfishRenameFix.RENAMED_IDS)));
/*      */     
/*  539 */     Schema $$55 = $$0.addSchema(1484, SAME_NAMESPACED);
/*  540 */     $$0.addFixer(ItemRenameFix.create($$55, "Rename seagrass items", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:sea_grass", "minecraft:seagrass", "minecraft:tall_sea_grass", "minecraft:tall_seagrass"))));
/*      */ 
/*      */ 
/*      */     
/*  544 */     $$0.addFixer(BlockRenameFix.create($$55, "Rename seagrass blocks", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:sea_grass", "minecraft:seagrass", "minecraft:tall_sea_grass", "minecraft:tall_seagrass"))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  550 */     $$0.addFixer((DataFix)new HeightmapRenamingFix($$55, false));
/*      */ 
/*      */     
/*  553 */     Schema $$56 = $$0.addSchema(1486, net.minecraft.util.datafix.schemas.V1486::new);
/*  554 */     $$0.addFixer((DataFix)new EntityCodSalmonFix($$56, true));
/*  555 */     $$0.addFixer(ItemRenameFix.create($$56, "Rename cod/salmon egg items", createRenamer(EntityCodSalmonFix.RENAMED_EGG_IDS)));
/*      */     
/*  557 */     Schema $$57 = $$0.addSchema(1487, SAME_NAMESPACED);
/*  558 */     $$0.addFixer(ItemRenameFix.create($$57, "Rename prismarine_brick(s)_* blocks", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:prismarine_bricks_slab", "minecraft:prismarine_brick_slab", "minecraft:prismarine_bricks_stairs", "minecraft:prismarine_brick_stairs"))));
/*      */ 
/*      */ 
/*      */     
/*  562 */     $$0.addFixer(BlockRenameFix.create($$57, "Rename prismarine_brick(s)_* items", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:prismarine_bricks_slab", "minecraft:prismarine_brick_slab", "minecraft:prismarine_bricks_stairs", "minecraft:prismarine_brick_stairs"))));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  567 */     Schema $$58 = $$0.addSchema(1488, SAME_NAMESPACED);
/*  568 */     $$0.addFixer(BlockRenameFix.create($$58, "Rename kelp/kelptop", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:kelp_top", "minecraft:kelp", "minecraft:kelp", "minecraft:kelp_plant"))));
/*      */ 
/*      */ 
/*      */     
/*  572 */     $$0.addFixer(ItemRenameFix.create($$58, "Rename kelptop", createRenamer("minecraft:kelp_top", "minecraft:kelp")));
/*  573 */     $$0.addFixer((DataFix)new NamedEntityFix($$58, false, "Command block block entity custom name fix", References.BLOCK_ENTITY, "minecraft:command_block")
/*      */         {
/*      */           protected Typed<?> fix(Typed<?> $$0) {
/*  576 */             return $$0.update(DSL.remainderFinder(), EntityCustomNameToComponentFix::fixTagCustomName);
/*      */           }
/*      */         });
/*  579 */     $$0.addFixer((DataFix)new NamedEntityFix($$58, false, "Command block minecart custom name fix", References.ENTITY, "minecraft:commandblock_minecart")
/*      */         {
/*      */           protected Typed<?> fix(Typed<?> $$0) {
/*  582 */             return $$0.update(DSL.remainderFinder(), EntityCustomNameToComponentFix::fixTagCustomName);
/*      */           }
/*      */         });
/*  585 */     $$0.addFixer((DataFix)new IglooMetadataRemovalFix($$58, false));
/*      */     
/*  587 */     Schema $$59 = $$0.addSchema(1490, SAME_NAMESPACED);
/*  588 */     $$0.addFixer(BlockRenameFix.create($$59, "Rename melon_block", createRenamer("minecraft:melon_block", "minecraft:melon")));
/*  589 */     $$0.addFixer(ItemRenameFix.create($$59, "Rename melon_block/melon/speckled_melon", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:melon_block", "minecraft:melon", "minecraft:melon", "minecraft:melon_slice", "minecraft:speckled_melon", "minecraft:glistering_melon_slice"))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  595 */     Schema $$60 = $$0.addSchema(1492, SAME_NAMESPACED);
/*  596 */     $$0.addFixer((DataFix)new ChunkStructuresTemplateRenameFix($$60, false));
/*      */     
/*  598 */     Schema $$61 = $$0.addSchema(1494, SAME_NAMESPACED);
/*  599 */     $$0.addFixer((DataFix)new ItemStackEnchantmentNamesFix($$61, false));
/*      */     
/*  601 */     Schema $$62 = $$0.addSchema(1496, SAME_NAMESPACED);
/*  602 */     $$0.addFixer((DataFix)new LeavesFix($$62, false));
/*      */     
/*  604 */     Schema $$63 = $$0.addSchema(1500, SAME_NAMESPACED);
/*  605 */     $$0.addFixer((DataFix)new BlockEntityKeepPacked($$63, false));
/*      */     
/*  607 */     Schema $$64 = $$0.addSchema(1501, SAME_NAMESPACED);
/*  608 */     $$0.addFixer((DataFix)new AdvancementsFix($$64, false));
/*      */     
/*  610 */     Schema $$65 = $$0.addSchema(1502, SAME_NAMESPACED);
/*  611 */     $$0.addFixer((DataFix)new NamespacedTypeRenameFix($$65, "Recipes fix", References.RECIPE, createRenamer(RecipesFix.RECIPES)));
/*      */     
/*  613 */     Schema $$66 = $$0.addSchema(1506, SAME_NAMESPACED);
/*  614 */     $$0.addFixer((DataFix)new LevelDataGeneratorOptionsFix($$66, false));
/*      */     
/*  616 */     Schema $$67 = $$0.addSchema(1510, net.minecraft.util.datafix.schemas.V1510::new);
/*  617 */     $$0.addFixer(BlockRenameFix.create($$67, "Block renamening fix", createRenamer(EntityTheRenameningFix.RENAMED_BLOCKS)));
/*  618 */     $$0.addFixer(ItemRenameFix.create($$67, "Item renamening fix", createRenamer(EntityTheRenameningFix.RENAMED_ITEMS)));
/*  619 */     $$0.addFixer((DataFix)new NamespacedTypeRenameFix($$67, "Recipes renamening fix", References.RECIPE, createRenamer(RecipesRenameningFix.RECIPES)));
/*  620 */     $$0.addFixer((DataFix)new EntityTheRenameningFix($$67, true));
/*  621 */     $$0.addFixer((DataFix)new StatsRenameFix($$67, "SwimStatsRenameFix", (Map)ImmutableMap.of("minecraft:swim_one_cm", "minecraft:walk_on_water_one_cm", "minecraft:dive_one_cm", "minecraft:walk_under_water_one_cm")));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  626 */     Schema $$68 = $$0.addSchema(1514, SAME_NAMESPACED);
/*  627 */     $$0.addFixer((DataFix)new ObjectiveDisplayNameFix($$68, false));
/*  628 */     $$0.addFixer((DataFix)new TeamDisplayNameFix($$68, false));
/*  629 */     $$0.addFixer((DataFix)new ObjectiveRenderTypeFix($$68, false));
/*      */     
/*  631 */     Schema $$69 = $$0.addSchema(1515, SAME_NAMESPACED);
/*  632 */     $$0.addFixer(BlockRenameFix.create($$69, "Rename coral fan blocks", createRenamer(RenamedCoralFansFix.RENAMED_IDS)));
/*      */     
/*  634 */     Schema $$70 = $$0.addSchema(1624, SAME_NAMESPACED);
/*  635 */     $$0.addFixer((DataFix)new TrappedChestBlockEntityFix($$70, false));
/*      */     
/*  637 */     Schema $$71 = $$0.addSchema(1800, net.minecraft.util.datafix.schemas.V1800::new);
/*  638 */     $$0.addFixer((DataFix)new AddNewChoices($$71, "Added 1.14 mobs fix", References.ENTITY));
/*  639 */     $$0.addFixer(ItemRenameFix.create($$71, "Rename dye items", createRenamer(DyeItemRenameFix.RENAMED_IDS)));
/*      */     
/*  641 */     Schema $$72 = $$0.addSchema(1801, net.minecraft.util.datafix.schemas.V1801::new);
/*  642 */     $$0.addFixer((DataFix)new AddNewChoices($$72, "Added Illager Beast", References.ENTITY));
/*      */     
/*  644 */     Schema $$73 = $$0.addSchema(1802, SAME_NAMESPACED);
/*  645 */     $$0.addFixer(BlockRenameFix.create($$73, "Rename sign blocks & stone slabs", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:stone_slab", "minecraft:smooth_stone_slab", "minecraft:sign", "minecraft:oak_sign", "minecraft:wall_sign", "minecraft:oak_wall_sign"))));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  650 */     $$0.addFixer(ItemRenameFix.create($$73, "Rename sign item & stone slabs", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:stone_slab", "minecraft:smooth_stone_slab", "minecraft:sign", "minecraft:oak_sign"))));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  655 */     Schema $$74 = $$0.addSchema(1803, SAME_NAMESPACED);
/*  656 */     $$0.addFixer((DataFix)new ItemLoreFix($$74, false));
/*      */     
/*  658 */     Schema $$75 = $$0.addSchema(1904, net.minecraft.util.datafix.schemas.V1904::new);
/*  659 */     $$0.addFixer((DataFix)new AddNewChoices($$75, "Added Cats", References.ENTITY));
/*  660 */     $$0.addFixer((DataFix)new EntityCatSplitFix($$75, false));
/*      */     
/*  662 */     Schema $$76 = $$0.addSchema(1905, SAME_NAMESPACED);
/*  663 */     $$0.addFixer((DataFix)new ChunkStatusFix($$76, false));
/*      */     
/*  665 */     Schema $$77 = $$0.addSchema(1906, net.minecraft.util.datafix.schemas.V1906::new);
/*  666 */     $$0.addFixer((DataFix)new AddNewChoices($$77, "Add POI Blocks", References.BLOCK_ENTITY));
/*      */     
/*  668 */     Schema $$78 = $$0.addSchema(1909, net.minecraft.util.datafix.schemas.V1909::new);
/*  669 */     $$0.addFixer((DataFix)new AddNewChoices($$78, "Add jigsaw", References.BLOCK_ENTITY));
/*      */     
/*  671 */     Schema $$79 = $$0.addSchema(1911, SAME_NAMESPACED);
/*  672 */     $$0.addFixer((DataFix)new ChunkStatusFix2($$79, false));
/*      */     
/*  674 */     Schema $$80 = $$0.addSchema(1914, SAME_NAMESPACED);
/*  675 */     $$0.addFixer((DataFix)new WeaponSmithChestLootTableFix($$80, false));
/*      */     
/*  677 */     Schema $$81 = $$0.addSchema(1917, SAME_NAMESPACED);
/*  678 */     $$0.addFixer((DataFix)new CatTypeFix($$81, false));
/*      */     
/*  680 */     Schema $$82 = $$0.addSchema(1918, SAME_NAMESPACED);
/*  681 */     $$0.addFixer((DataFix)new VillagerDataFix($$82, "minecraft:villager"));
/*  682 */     $$0.addFixer((DataFix)new VillagerDataFix($$82, "minecraft:zombie_villager"));
/*      */     
/*  684 */     Schema $$83 = $$0.addSchema(1920, net.minecraft.util.datafix.schemas.V1920::new);
/*  685 */     $$0.addFixer((DataFix)new NewVillageFix($$83, false));
/*  686 */     $$0.addFixer((DataFix)new AddNewChoices($$83, "Add campfire", References.BLOCK_ENTITY));
/*      */     
/*  688 */     Schema $$84 = $$0.addSchema(1925, SAME_NAMESPACED);
/*  689 */     $$0.addFixer((DataFix)new MapIdFix($$84, false));
/*      */     
/*  691 */     Schema $$85 = $$0.addSchema(1928, net.minecraft.util.datafix.schemas.V1928::new);
/*  692 */     $$0.addFixer((DataFix)new EntityRavagerRenameFix($$85, true));
/*  693 */     $$0.addFixer(ItemRenameFix.create($$85, "Rename ravager egg item", createRenamer(EntityRavagerRenameFix.RENAMED_IDS)));
/*      */     
/*  695 */     Schema $$86 = $$0.addSchema(1929, net.minecraft.util.datafix.schemas.V1929::new);
/*  696 */     $$0.addFixer((DataFix)new AddNewChoices($$86, "Add Wandering Trader and Trader Llama", References.ENTITY));
/*      */     
/*  698 */     Schema $$87 = $$0.addSchema(1931, net.minecraft.util.datafix.schemas.V1931::new);
/*  699 */     $$0.addFixer((DataFix)new AddNewChoices($$87, "Added Fox", References.ENTITY));
/*      */     
/*  701 */     Schema $$88 = $$0.addSchema(1936, SAME_NAMESPACED);
/*  702 */     $$0.addFixer((DataFix)new OptionsAddTextBackgroundFix($$88, false));
/*      */     
/*  704 */     Schema $$89 = $$0.addSchema(1946, SAME_NAMESPACED);
/*  705 */     $$0.addFixer((DataFix)new ReorganizePoi($$89, false));
/*      */     
/*  707 */     Schema $$90 = $$0.addSchema(1948, SAME_NAMESPACED);
/*  708 */     $$0.addFixer((DataFix)new OminousBannerRenameFix($$90));
/*      */     
/*  710 */     Schema $$91 = $$0.addSchema(1953, SAME_NAMESPACED);
/*  711 */     $$0.addFixer((DataFix)new OminousBannerBlockEntityRenameFix($$91, false));
/*      */     
/*  713 */     Schema $$92 = $$0.addSchema(1955, SAME_NAMESPACED);
/*  714 */     $$0.addFixer((DataFix)new VillagerRebuildLevelAndXpFix($$92, false));
/*  715 */     $$0.addFixer((DataFix)new ZombieVillagerRebuildXpFix($$92, false));
/*      */     
/*  717 */     Schema $$93 = $$0.addSchema(1961, SAME_NAMESPACED);
/*  718 */     $$0.addFixer((DataFix)new ChunkLightRemoveFix($$93, false));
/*      */     
/*  720 */     Schema $$94 = $$0.addSchema(1963, SAME_NAMESPACED);
/*  721 */     $$0.addFixer((DataFix)new RemoveGolemGossipFix($$94, false));
/*      */     
/*  723 */     Schema $$95 = $$0.addSchema(2100, net.minecraft.util.datafix.schemas.V2100::new);
/*  724 */     $$0.addFixer((DataFix)new AddNewChoices($$95, "Added Bee and Bee Stinger", References.ENTITY));
/*  725 */     $$0.addFixer((DataFix)new AddNewChoices($$95, "Add beehive", References.BLOCK_ENTITY));
/*  726 */     $$0.addFixer((DataFix)new NamespacedTypeRenameFix($$95, "Rename sugar recipe", References.RECIPE, createRenamer("minecraft:sugar", "sugar_from_sugar_cane")));
/*  727 */     $$0.addFixer((DataFix)new AdvancementsRenameFix($$95, false, "Rename sugar recipe advancement", createRenamer("minecraft:recipes/misc/sugar", "minecraft:recipes/misc/sugar_from_sugar_cane")));
/*      */     
/*  729 */     Schema $$96 = $$0.addSchema(2202, SAME_NAMESPACED);
/*  730 */     $$0.addFixer((DataFix)new ChunkBiomeFix($$96, false));
/*      */     
/*  732 */     Schema $$97 = $$0.addSchema(2209, SAME_NAMESPACED);
/*  733 */     UnaryOperator<String> $$98 = createRenamer("minecraft:bee_hive", "minecraft:beehive");
/*  734 */     $$0.addFixer(ItemRenameFix.create($$97, "Rename bee_hive item to beehive", $$98));
/*  735 */     $$0.addFixer((DataFix)new PoiTypeRenameFix($$97, "Rename bee_hive poi to beehive", $$98));
/*  736 */     $$0.addFixer(BlockRenameFix.create($$97, "Rename bee_hive block to beehive", $$98));
/*      */     
/*  738 */     Schema $$99 = $$0.addSchema(2211, SAME_NAMESPACED);
/*  739 */     $$0.addFixer((DataFix)new StructureReferenceCountFix($$99, false));
/*      */     
/*  741 */     Schema $$100 = $$0.addSchema(2218, SAME_NAMESPACED);
/*  742 */     $$0.addFixer((DataFix)new ForcePoiRebuild($$100, false));
/*      */     
/*  744 */     Schema $$101 = $$0.addSchema(2501, net.minecraft.util.datafix.schemas.V2501::new);
/*  745 */     $$0.addFixer((DataFix)new FurnaceRecipeFix($$101, true));
/*      */     
/*  747 */     Schema $$102 = $$0.addSchema(2502, net.minecraft.util.datafix.schemas.V2502::new);
/*  748 */     $$0.addFixer((DataFix)new AddNewChoices($$102, "Added Hoglin", References.ENTITY));
/*      */     
/*  750 */     Schema $$103 = $$0.addSchema(2503, SAME_NAMESPACED);
/*  751 */     $$0.addFixer((DataFix)new WallPropertyFix($$103, false));
/*  752 */     $$0.addFixer((DataFix)new AdvancementsRenameFix($$103, false, "Composter category change", createRenamer("minecraft:recipes/misc/composter", "minecraft:recipes/decorations/composter")));
/*      */     
/*  754 */     Schema $$104 = $$0.addSchema(2505, net.minecraft.util.datafix.schemas.V2505::new);
/*  755 */     $$0.addFixer((DataFix)new AddNewChoices($$104, "Added Piglin", References.ENTITY));
/*  756 */     $$0.addFixer((DataFix)new MemoryExpiryDataFix($$104, "minecraft:villager"));
/*      */     
/*  758 */     Schema $$105 = $$0.addSchema(2508, SAME_NAMESPACED);
/*  759 */     $$0.addFixer(ItemRenameFix.create($$105, "Renamed fungi items to fungus", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:warped_fungi", "minecraft:warped_fungus", "minecraft:crimson_fungi", "minecraft:crimson_fungus"))));
/*      */ 
/*      */ 
/*      */     
/*  763 */     $$0.addFixer(BlockRenameFix.create($$105, "Renamed fungi blocks to fungus", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:warped_fungi", "minecraft:warped_fungus", "minecraft:crimson_fungi", "minecraft:crimson_fungus"))));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  768 */     Schema $$106 = $$0.addSchema(2509, net.minecraft.util.datafix.schemas.V2509::new);
/*  769 */     $$0.addFixer((DataFix)new EntityZombifiedPiglinRenameFix($$106));
/*  770 */     $$0.addFixer(ItemRenameFix.create($$106, "Rename zombie pigman egg item", createRenamer(EntityZombifiedPiglinRenameFix.RENAMED_IDS)));
/*      */     
/*  772 */     Schema $$107 = $$0.addSchema(2511, SAME_NAMESPACED);
/*  773 */     $$0.addFixer((DataFix)new EntityProjectileOwnerFix($$107));
/*      */     
/*  775 */     Schema $$108 = $$0.addSchema(2514, SAME_NAMESPACED);
/*  776 */     $$0.addFixer((DataFix)new EntityUUIDFix($$108));
/*  777 */     $$0.addFixer((DataFix)new BlockEntityUUIDFix($$108));
/*  778 */     $$0.addFixer((DataFix)new PlayerUUIDFix($$108));
/*  779 */     $$0.addFixer((DataFix)new LevelUUIDFix($$108));
/*  780 */     $$0.addFixer((DataFix)new SavedDataUUIDFix($$108));
/*  781 */     $$0.addFixer((DataFix)new ItemStackUUIDFix($$108));
/*      */     
/*  783 */     Schema $$109 = $$0.addSchema(2516, SAME_NAMESPACED);
/*  784 */     $$0.addFixer((DataFix)new GossipUUIDFix($$109, "minecraft:villager"));
/*  785 */     $$0.addFixer((DataFix)new GossipUUIDFix($$109, "minecraft:zombie_villager"));
/*      */     
/*  787 */     Schema $$110 = $$0.addSchema(2518, SAME_NAMESPACED);
/*  788 */     $$0.addFixer((DataFix)new JigsawPropertiesFix($$110, false));
/*  789 */     $$0.addFixer((DataFix)new JigsawRotationFix($$110, false));
/*      */     
/*  791 */     Schema $$111 = $$0.addSchema(2519, net.minecraft.util.datafix.schemas.V2519::new);
/*  792 */     $$0.addFixer((DataFix)new AddNewChoices($$111, "Added Strider", References.ENTITY));
/*      */     
/*  794 */     Schema $$112 = $$0.addSchema(2522, net.minecraft.util.datafix.schemas.V2522::new);
/*  795 */     $$0.addFixer((DataFix)new AddNewChoices($$112, "Added Zoglin", References.ENTITY));
/*      */     
/*  797 */     Schema $$113 = $$0.addSchema(2523, SAME_NAMESPACED);
/*  798 */     $$0.addFixer((DataFix)new AttributesRename($$113));
/*      */     
/*  800 */     Schema $$114 = $$0.addSchema(2527, SAME_NAMESPACED);
/*  801 */     $$0.addFixer((DataFix)new BitStorageAlignFix($$114));
/*      */     
/*  803 */     Schema $$115 = $$0.addSchema(2528, SAME_NAMESPACED);
/*  804 */     $$0.addFixer(ItemRenameFix.create($$115, "Rename soul fire torch and soul fire lantern", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:soul_fire_torch", "minecraft:soul_torch", "minecraft:soul_fire_lantern", "minecraft:soul_lantern"))));
/*      */ 
/*      */ 
/*      */     
/*  808 */     $$0.addFixer(BlockRenameFix.create($$115, "Rename soul fire torch and soul fire lantern", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:soul_fire_torch", "minecraft:soul_torch", "minecraft:soul_fire_wall_torch", "minecraft:soul_wall_torch", "minecraft:soul_fire_lantern", "minecraft:soul_lantern"))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  814 */     Schema $$116 = $$0.addSchema(2529, SAME_NAMESPACED);
/*  815 */     $$0.addFixer((DataFix)new StriderGravityFix($$116, false));
/*      */     
/*  817 */     Schema $$117 = $$0.addSchema(2531, SAME_NAMESPACED);
/*  818 */     $$0.addFixer((DataFix)new RedstoneWireConnectionsFix($$117));
/*      */     
/*  820 */     Schema $$118 = $$0.addSchema(2533, SAME_NAMESPACED);
/*  821 */     $$0.addFixer((DataFix)new VillagerFollowRangeFix($$118));
/*      */     
/*  823 */     Schema $$119 = $$0.addSchema(2535, SAME_NAMESPACED);
/*  824 */     $$0.addFixer((DataFix)new EntityShulkerRotationFix($$119));
/*      */     
/*  826 */     Schema $$120 = $$0.addSchema(2538, SAME_NAMESPACED);
/*  827 */     $$0.addFixer((DataFix)new LevelLegacyWorldGenSettingsFix($$120));
/*      */     
/*  829 */     Schema $$121 = $$0.addSchema(2550, SAME_NAMESPACED);
/*  830 */     $$0.addFixer((DataFix)new WorldGenSettingsFix($$121));
/*      */     
/*  832 */     Schema $$122 = $$0.addSchema(2551, net.minecraft.util.datafix.schemas.V2551::new);
/*  833 */     $$0.addFixer((DataFix)new WriteAndReadFix($$122, "add types to WorldGenData", References.WORLD_GEN_SETTINGS));
/*      */     
/*  835 */     Schema $$123 = $$0.addSchema(2552, SAME_NAMESPACED);
/*  836 */     $$0.addFixer((DataFix)new NamespacedTypeRenameFix($$123, "Nether biome rename", References.BIOME, createRenamer("minecraft:nether", "minecraft:nether_wastes")));
/*      */ 
/*      */     
/*  839 */     Schema $$124 = $$0.addSchema(2553, SAME_NAMESPACED);
/*  840 */     $$0.addFixer((DataFix)new NamespacedTypeRenameFix($$124, "Biomes fix", References.BIOME, createRenamer(BiomeFix.BIOMES)));
/*      */     
/*  842 */     Schema $$125 = $$0.addSchema(2558, SAME_NAMESPACED);
/*  843 */     $$0.addFixer((DataFix)new MissingDimensionFix($$125, false));
/*  844 */     $$0.addFixer((DataFix)new OptionsRenameFieldFix($$125, false, "Rename swapHands setting", "key_key.swapHands", "key_key.swapOffhand"));
/*      */     
/*  846 */     Schema $$126 = $$0.addSchema(2568, net.minecraft.util.datafix.schemas.V2568::new);
/*  847 */     $$0.addFixer((DataFix)new AddNewChoices($$126, "Added Piglin Brute", References.ENTITY));
/*      */     
/*  849 */     Schema $$127 = $$0.addSchema(2571, net.minecraft.util.datafix.schemas.V2571::new);
/*  850 */     $$0.addFixer((DataFix)new AddNewChoices($$127, "Added Goat", References.ENTITY));
/*      */     
/*  852 */     Schema $$128 = $$0.addSchema(2679, SAME_NAMESPACED);
/*  853 */     $$0.addFixer((DataFix)new CauldronRenameFix($$128, false));
/*      */     
/*  855 */     Schema $$129 = $$0.addSchema(2680, SAME_NAMESPACED);
/*  856 */     $$0.addFixer(ItemRenameFix.create($$129, "Renamed grass path item to dirt path", createRenamer("minecraft:grass_path", "minecraft:dirt_path")));
/*  857 */     $$0.addFixer(BlockRenameFixWithJigsaw.create($$129, "Renamed grass path block to dirt path", createRenamer("minecraft:grass_path", "minecraft:dirt_path")));
/*      */     
/*  859 */     Schema $$130 = $$0.addSchema(2684, net.minecraft.util.datafix.schemas.V2684::new);
/*  860 */     $$0.addFixer((DataFix)new AddNewChoices($$130, "Added Sculk Sensor", References.BLOCK_ENTITY));
/*      */     
/*  862 */     Schema $$131 = $$0.addSchema(2686, net.minecraft.util.datafix.schemas.V2686::new);
/*  863 */     $$0.addFixer((DataFix)new AddNewChoices($$131, "Added Axolotl", References.ENTITY));
/*      */     
/*  865 */     Schema $$132 = $$0.addSchema(2688, net.minecraft.util.datafix.schemas.V2688::new);
/*  866 */     $$0.addFixer((DataFix)new AddNewChoices($$132, "Added Glow Squid", References.ENTITY));
/*  867 */     $$0.addFixer((DataFix)new AddNewChoices($$132, "Added Glow Item Frame", References.ENTITY));
/*      */     
/*  869 */     Schema $$133 = $$0.addSchema(2690, SAME_NAMESPACED);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  891 */     ImmutableMap<String, String> $$134 = ImmutableMap.builder().put("minecraft:weathered_copper_block", "minecraft:oxidized_copper_block").put("minecraft:semi_weathered_copper_block", "minecraft:weathered_copper_block").put("minecraft:lightly_weathered_copper_block", "minecraft:exposed_copper_block").put("minecraft:weathered_cut_copper", "minecraft:oxidized_cut_copper").put("minecraft:semi_weathered_cut_copper", "minecraft:weathered_cut_copper").put("minecraft:lightly_weathered_cut_copper", "minecraft:exposed_cut_copper").put("minecraft:weathered_cut_copper_stairs", "minecraft:oxidized_cut_copper_stairs").put("minecraft:semi_weathered_cut_copper_stairs", "minecraft:weathered_cut_copper_stairs").put("minecraft:lightly_weathered_cut_copper_stairs", "minecraft:exposed_cut_copper_stairs").put("minecraft:weathered_cut_copper_slab", "minecraft:oxidized_cut_copper_slab").put("minecraft:semi_weathered_cut_copper_slab", "minecraft:weathered_cut_copper_slab").put("minecraft:lightly_weathered_cut_copper_slab", "minecraft:exposed_cut_copper_slab").put("minecraft:waxed_semi_weathered_copper", "minecraft:waxed_weathered_copper").put("minecraft:waxed_lightly_weathered_copper", "minecraft:waxed_exposed_copper").put("minecraft:waxed_semi_weathered_cut_copper", "minecraft:waxed_weathered_cut_copper").put("minecraft:waxed_lightly_weathered_cut_copper", "minecraft:waxed_exposed_cut_copper").put("minecraft:waxed_semi_weathered_cut_copper_stairs", "minecraft:waxed_weathered_cut_copper_stairs").put("minecraft:waxed_lightly_weathered_cut_copper_stairs", "minecraft:waxed_exposed_cut_copper_stairs").put("minecraft:waxed_semi_weathered_cut_copper_slab", "minecraft:waxed_weathered_cut_copper_slab").put("minecraft:waxed_lightly_weathered_cut_copper_slab", "minecraft:waxed_exposed_cut_copper_slab").build();
/*      */     
/*  893 */     $$0.addFixer(ItemRenameFix.create($$133, "Renamed copper block items to new oxidized terms", createRenamer((Map<String, String>)$$134)));
/*  894 */     $$0.addFixer(BlockRenameFixWithJigsaw.create($$133, "Renamed copper blocks to new oxidized terms", createRenamer((Map<String, String>)$$134)));
/*      */     
/*  896 */     Schema $$135 = $$0.addSchema(2691, SAME_NAMESPACED);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  902 */     ImmutableMap<String, String> $$136 = ImmutableMap.builder().put("minecraft:waxed_copper", "minecraft:waxed_copper_block").put("minecraft:oxidized_copper_block", "minecraft:oxidized_copper").put("minecraft:weathered_copper_block", "minecraft:weathered_copper").put("minecraft:exposed_copper_block", "minecraft:exposed_copper").build();
/*      */     
/*  904 */     $$0.addFixer(ItemRenameFix.create($$135, "Rename copper item suffixes", createRenamer((Map<String, String>)$$136)));
/*  905 */     $$0.addFixer(BlockRenameFixWithJigsaw.create($$135, "Rename copper blocks suffixes", createRenamer((Map<String, String>)$$136)));
/*      */     
/*  907 */     Schema $$137 = $$0.addSchema(2693, SAME_NAMESPACED);
/*  908 */     $$0.addFixer((DataFix)new AddFlagIfNotPresentFix($$137, References.WORLD_GEN_SETTINGS, "has_increased_height_already", false));
/*      */     
/*  910 */     Schema $$138 = $$0.addSchema(2696, SAME_NAMESPACED);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  929 */     ImmutableMap<String, String> $$139 = ImmutableMap.builder().put("minecraft:grimstone", "minecraft:deepslate").put("minecraft:grimstone_slab", "minecraft:cobbled_deepslate_slab").put("minecraft:grimstone_stairs", "minecraft:cobbled_deepslate_stairs").put("minecraft:grimstone_wall", "minecraft:cobbled_deepslate_wall").put("minecraft:polished_grimstone", "minecraft:polished_deepslate").put("minecraft:polished_grimstone_slab", "minecraft:polished_deepslate_slab").put("minecraft:polished_grimstone_stairs", "minecraft:polished_deepslate_stairs").put("minecraft:polished_grimstone_wall", "minecraft:polished_deepslate_wall").put("minecraft:grimstone_tiles", "minecraft:deepslate_tiles").put("minecraft:grimstone_tile_slab", "minecraft:deepslate_tile_slab").put("minecraft:grimstone_tile_stairs", "minecraft:deepslate_tile_stairs").put("minecraft:grimstone_tile_wall", "minecraft:deepslate_tile_wall").put("minecraft:grimstone_bricks", "minecraft:deepslate_bricks").put("minecraft:grimstone_brick_slab", "minecraft:deepslate_brick_slab").put("minecraft:grimstone_brick_stairs", "minecraft:deepslate_brick_stairs").put("minecraft:grimstone_brick_wall", "minecraft:deepslate_brick_wall").put("minecraft:chiseled_grimstone", "minecraft:chiseled_deepslate").build();
/*      */     
/*  931 */     $$0.addFixer(ItemRenameFix.create($$138, "Renamed grimstone block items to deepslate", createRenamer((Map<String, String>)$$139)));
/*  932 */     $$0.addFixer(BlockRenameFixWithJigsaw.create($$138, "Renamed grimstone blocks to deepslate", createRenamer((Map<String, String>)$$139)));
/*      */     
/*  934 */     Schema $$140 = $$0.addSchema(2700, SAME_NAMESPACED);
/*  935 */     $$0.addFixer(BlockRenameFixWithJigsaw.create($$140, "Renamed cave vines blocks", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:cave_vines_head", "minecraft:cave_vines", "minecraft:cave_vines_body", "minecraft:cave_vines_plant"))));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  940 */     Schema $$141 = $$0.addSchema(2701, SAME_NAMESPACED);
/*  941 */     $$0.addFixer((DataFix)new SavedDataFeaturePoolElementFix($$141));
/*      */     
/*  943 */     Schema $$142 = $$0.addSchema(2702, SAME_NAMESPACED);
/*  944 */     $$0.addFixer((DataFix)new AbstractArrowPickupFix($$142));
/*      */     
/*  946 */     Schema $$143 = $$0.addSchema(2704, net.minecraft.util.datafix.schemas.V2704::new);
/*  947 */     $$0.addFixer((DataFix)new AddNewChoices($$143, "Added Goat", References.ENTITY));
/*      */     
/*  949 */     Schema $$144 = $$0.addSchema(2707, net.minecraft.util.datafix.schemas.V2707::new);
/*  950 */     $$0.addFixer((DataFix)new AddNewChoices($$144, "Added Marker", References.ENTITY));
/*  951 */     $$0.addFixer((DataFix)new AddFlagIfNotPresentFix($$144, References.WORLD_GEN_SETTINGS, "has_increased_height_already", true));
/*      */     
/*  953 */     Schema $$145 = $$0.addSchema(2710, SAME_NAMESPACED);
/*  954 */     $$0.addFixer((DataFix)new StatsRenameFix($$145, "Renamed play_one_minute stat to play_time", (Map)ImmutableMap.of("minecraft:play_one_minute", "minecraft:play_time")));
/*      */     
/*  956 */     Schema $$146 = $$0.addSchema(2717, SAME_NAMESPACED);
/*  957 */     $$0.addFixer(ItemRenameFix.create($$146, "Rename azalea_leaves_flowers", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:azalea_leaves_flowers", "minecraft:flowering_azalea_leaves"))));
/*      */ 
/*      */     
/*  960 */     $$0.addFixer(BlockRenameFix.create($$146, "Rename azalea_leaves_flowers items", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:azalea_leaves_flowers", "minecraft:flowering_azalea_leaves"))));
/*      */ 
/*      */ 
/*      */     
/*  964 */     Schema $$147 = $$0.addSchema(2825, SAME_NAMESPACED);
/*  965 */     $$0.addFixer((DataFix)new AddFlagIfNotPresentFix($$147, References.WORLD_GEN_SETTINGS, "has_increased_height_already", false));
/*      */     
/*  967 */     Schema $$148 = $$0.addSchema(2831, net.minecraft.util.datafix.schemas.V2831::new);
/*  968 */     $$0.addFixer((DataFix)new SpawnerDataFix($$148));
/*      */     
/*  970 */     Schema $$149 = $$0.addSchema(2832, net.minecraft.util.datafix.schemas.V2832::new);
/*  971 */     $$0.addFixer((DataFix)new WorldGenSettingsHeightAndBiomeFix($$149));
/*  972 */     $$0.addFixer((DataFix)new ChunkHeightAndBiomeFix($$149));
/*      */     
/*  974 */     Schema $$150 = $$0.addSchema(2833, SAME_NAMESPACED);
/*  975 */     $$0.addFixer((DataFix)new WorldGenSettingsDisallowOldCustomWorldsFix($$150));
/*      */     
/*  977 */     Schema $$151 = $$0.addSchema(2838, SAME_NAMESPACED);
/*  978 */     $$0.addFixer((DataFix)new NamespacedTypeRenameFix($$151, "Caves and Cliffs biome renames", References.BIOME, createRenamer((Map<String, String>)CavesAndCliffsRenames.RENAMES)));
/*      */     
/*  980 */     Schema $$152 = $$0.addSchema(2841, SAME_NAMESPACED);
/*  981 */     $$0.addFixer((DataFix)new ChunkProtoTickListFix($$152));
/*      */     
/*  983 */     Schema $$153 = $$0.addSchema(2842, net.minecraft.util.datafix.schemas.V2842::new);
/*  984 */     $$0.addFixer((DataFix)new ChunkRenamesFix($$153));
/*      */     
/*  986 */     Schema $$154 = $$0.addSchema(2843, SAME_NAMESPACED);
/*  987 */     $$0.addFixer((DataFix)new OverreachingTickFix($$154));
/*  988 */     $$0.addFixer((DataFix)new NamespacedTypeRenameFix($$154, "Remove Deep Warm Ocean", References.BIOME, createRenamer("minecraft:deep_warm_ocean", "minecraft:warm_ocean")));
/*      */     
/*  990 */     Schema $$155 = $$0.addSchema(2846, SAME_NAMESPACED);
/*  991 */     $$0.addFixer((DataFix)new AdvancementsRenameFix($$155, false, "Rename some C&C part 2 advancements", createRenamer((Map<String, String>)ImmutableMap.of("minecraft:husbandry/play_jukebox_in_meadows", "minecraft:adventure/play_jukebox_in_meadows", "minecraft:adventure/caves_and_cliff", "minecraft:adventure/fall_from_world_height", "minecraft:adventure/ride_strider_in_overworld_lava", "minecraft:nether/ride_strider_in_overworld_lava"))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  997 */     Schema $$156 = $$0.addSchema(2852, SAME_NAMESPACED);
/*  998 */     $$0.addFixer((DataFix)new WorldGenSettingsDisallowOldCustomWorldsFix($$156));
/*      */     
/* 1000 */     Schema $$157 = $$0.addSchema(2967, SAME_NAMESPACED);
/* 1001 */     $$0.addFixer((DataFix)new StructureSettingsFlattenFix($$157));
/*      */     
/* 1003 */     Schema $$158 = $$0.addSchema(2970, SAME_NAMESPACED);
/* 1004 */     $$0.addFixer((DataFix)new StructuresBecomeConfiguredFix($$158));
/*      */     
/* 1006 */     Schema $$159 = $$0.addSchema(3076, net.minecraft.util.datafix.schemas.V3076::new);
/* 1007 */     $$0.addFixer((DataFix)new AddNewChoices($$159, "Added Sculk Catalyst", References.BLOCK_ENTITY));
/*      */     
/* 1009 */     Schema $$160 = $$0.addSchema(3077, SAME_NAMESPACED);
/* 1010 */     $$0.addFixer((DataFix)new ChunkDeleteIgnoredLightDataFix($$160));
/*      */     
/* 1012 */     Schema $$161 = $$0.addSchema(3078, net.minecraft.util.datafix.schemas.V3078::new);
/* 1013 */     $$0.addFixer((DataFix)new AddNewChoices($$161, "Added Frog", References.ENTITY));
/* 1014 */     $$0.addFixer((DataFix)new AddNewChoices($$161, "Added Tadpole", References.ENTITY));
/* 1015 */     $$0.addFixer((DataFix)new AddNewChoices($$161, "Added Sculk Shrieker", References.BLOCK_ENTITY));
/*      */     
/* 1017 */     Schema $$162 = $$0.addSchema(3081, net.minecraft.util.datafix.schemas.V3081::new);
/* 1018 */     $$0.addFixer((DataFix)new AddNewChoices($$162, "Added Warden", References.ENTITY));
/*      */     
/* 1020 */     Schema $$163 = $$0.addSchema(3082, net.minecraft.util.datafix.schemas.V3082::new);
/* 1021 */     $$0.addFixer((DataFix)new AddNewChoices($$163, "Added Chest Boat", References.ENTITY));
/*      */     
/* 1023 */     Schema $$164 = $$0.addSchema(3083, net.minecraft.util.datafix.schemas.V3083::new);
/* 1024 */     $$0.addFixer((DataFix)new AddNewChoices($$164, "Added Allay", References.ENTITY));
/*      */     
/* 1026 */     Schema $$165 = $$0.addSchema(3084, SAME_NAMESPACED);
/* 1027 */     $$0.addFixer((DataFix)new NamespacedTypeRenameFix($$165, "game_event_renames_3084", References.GAME_EVENT_NAME, createRenamer((Map<String, String>)ImmutableMap.builder()
/* 1028 */             .put("minecraft:block_press", "minecraft:block_activate")
/* 1029 */             .put("minecraft:block_switch", "minecraft:block_activate")
/* 1030 */             .put("minecraft:block_unpress", "minecraft:block_deactivate")
/* 1031 */             .put("minecraft:block_unswitch", "minecraft:block_deactivate")
/* 1032 */             .put("minecraft:drinking_finish", "minecraft:drink")
/* 1033 */             .put("minecraft:elytra_free_fall", "minecraft:elytra_glide")
/* 1034 */             .put("minecraft:entity_damaged", "minecraft:entity_damage")
/* 1035 */             .put("minecraft:entity_dying", "minecraft:entity_die")
/* 1036 */             .put("minecraft:entity_killed", "minecraft:entity_die")
/* 1037 */             .put("minecraft:mob_interact", "minecraft:entity_interact")
/* 1038 */             .put("minecraft:ravager_roar", "minecraft:entity_roar")
/* 1039 */             .put("minecraft:ring_bell", "minecraft:block_change")
/* 1040 */             .put("minecraft:shulker_close", "minecraft:container_close")
/* 1041 */             .put("minecraft:shulker_open", "minecraft:container_open")
/* 1042 */             .put("minecraft:wolf_shaking", "minecraft:entity_shake")
/* 1043 */             .build())));
/*      */ 
/*      */     
/* 1046 */     Schema $$166 = $$0.addSchema(3086, SAME_NAMESPACED);
/* 1047 */     Objects.requireNonNull((Int2ObjectOpenHashMap)Util.make(new Int2ObjectOpenHashMap(), $$0 -> { $$0.defaultReturnValue("minecraft:tabby"); $$0.put(0, "minecraft:tabby"); $$0.put(1, "minecraft:black"); $$0.put(2, "minecraft:red"); $$0.put(3, "minecraft:siamese"); $$0.put(4, "minecraft:british"); $$0.put(5, "minecraft:calico"); $$0.put(6, "minecraft:persian"); $$0.put(7, "minecraft:ragdoll"); $$0.put(8, "minecraft:white"); $$0.put(9, "minecraft:jellie"); $$0.put(10, "minecraft:all_black"); })); $$0.addFixer((DataFix)new EntityVariantFix($$166, "Change cat variant type", References.ENTITY, "minecraft:cat", "CatType", (Int2ObjectOpenHashMap)Util.make(new Int2ObjectOpenHashMap(), $$0 -> { $$0.defaultReturnValue("minecraft:tabby"); $$0.put(0, "minecraft:tabby"); $$0.put(1, "minecraft:black"); $$0.put(2, "minecraft:red"); $$0.put(3, "minecraft:siamese"); $$0.put(4, "minecraft:british"); $$0.put(5, "minecraft:calico"); $$0.put(6, "minecraft:persian"); $$0.put(7, "minecraft:ragdoll"); $$0.put(8, "minecraft:white"); $$0.put(9, "minecraft:jellie"); $$0.put(10, "minecraft:all_black"); })::get));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1074 */     ImmutableMap<String, String> $$167 = ImmutableMap.builder().put("textures/entity/cat/tabby.png", "minecraft:tabby").put("textures/entity/cat/black.png", "minecraft:black").put("textures/entity/cat/red.png", "minecraft:red").put("textures/entity/cat/siamese.png", "minecraft:siamese").put("textures/entity/cat/british_shorthair.png", "minecraft:british").put("textures/entity/cat/calico.png", "minecraft:calico").put("textures/entity/cat/persian.png", "minecraft:persian").put("textures/entity/cat/ragdoll.png", "minecraft:ragdoll").put("textures/entity/cat/white.png", "minecraft:white").put("textures/entity/cat/jellie.png", "minecraft:jellie").put("textures/entity/cat/all_black.png", "minecraft:all_black").build();
/* 1075 */     $$0.addFixer((DataFix)new CriteriaRenameFix($$166, "Migrate cat variant advancement", "minecraft:husbandry/complete_catalogue", $$1 -> (String)$$0.getOrDefault($$1, $$1)));
/*      */     
/* 1077 */     Schema $$168 = $$0.addSchema(3087, SAME_NAMESPACED);
/* 1078 */     Objects.requireNonNull((Int2ObjectOpenHashMap)Util.make(new Int2ObjectOpenHashMap(), $$0 -> { $$0.put(0, "minecraft:temperate"); $$0.put(1, "minecraft:warm"); $$0.put(2, "minecraft:cold"); })); $$0.addFixer((DataFix)new EntityVariantFix($$168, "Change frog variant type", References.ENTITY, "minecraft:frog", "Variant", (Int2ObjectOpenHashMap)Util.make(new Int2ObjectOpenHashMap(), $$0 -> { $$0.put(0, "minecraft:temperate"); $$0.put(1, "minecraft:warm"); $$0.put(2, "minecraft:cold"); })::get));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1084 */     Schema $$169 = $$0.addSchema(3090, SAME_NAMESPACED);
/* 1085 */     $$0.addFixer((DataFix)new EntityPaintingFieldsRenameFix($$169));
/*      */     
/* 1087 */     Schema $$170 = $$0.addSchema(3093, SAME_NAMESPACED);
/* 1088 */     $$0.addFixer((DataFix)new EntityGoatMissingStateFix($$170));
/*      */     
/* 1090 */     Schema $$171 = $$0.addSchema(3094, SAME_NAMESPACED);
/* 1091 */     $$0.addFixer((DataFix)new GoatHornIdFix($$171));
/*      */     
/* 1093 */     Schema $$172 = $$0.addSchema(3097, SAME_NAMESPACED);
/* 1094 */     $$0.addFixer((DataFix)new FilteredBooksFix($$172));
/* 1095 */     $$0.addFixer((DataFix)new FilteredSignsFix($$172));
/* 1096 */     Map<String, String> $$173 = Map.of("minecraft:british", "minecraft:british_shorthair");
/* 1097 */     $$0.addFixer((DataFix)new VariantRenameFix($$172, "Rename british shorthair", References.ENTITY, "minecraft:cat", $$173));
/* 1098 */     $$0.addFixer((DataFix)new CriteriaRenameFix($$172, "Migrate cat variant advancement for british shorthair", "minecraft:husbandry/complete_catalogue", $$1 -> (String)$$0.getOrDefault($$1, $$1)));
/* 1099 */     Objects.requireNonNull(Set.of("minecraft:unemployed", "minecraft:nitwit")); $$0.addFixer((DataFix)new PoiTypeRemoveFix($$172, "Remove unpopulated villager PoI types", Set.of("minecraft:unemployed", "minecraft:nitwit")::contains));
/*      */     
/* 1101 */     Schema $$174 = $$0.addSchema(3108, SAME_NAMESPACED);
/* 1102 */     $$0.addFixer((DataFix)new BlendingDataRemoveFromNetherEndFix($$174));
/*      */     
/* 1104 */     Schema $$175 = $$0.addSchema(3201, SAME_NAMESPACED);
/* 1105 */     $$0.addFixer((DataFix)new OptionsProgrammerArtFix($$175));
/*      */     
/* 1107 */     Schema $$176 = $$0.addSchema(3202, net.minecraft.util.datafix.schemas.V3202::new);
/* 1108 */     $$0.addFixer((DataFix)new AddNewChoices($$176, "Added Hanging Sign", References.BLOCK_ENTITY));
/*      */     
/* 1110 */     Schema $$177 = $$0.addSchema(3203, net.minecraft.util.datafix.schemas.V3203::new);
/* 1111 */     $$0.addFixer((DataFix)new AddNewChoices($$177, "Added Camel", References.ENTITY));
/*      */     
/* 1113 */     Schema $$178 = $$0.addSchema(3204, net.minecraft.util.datafix.schemas.V3204::new);
/* 1114 */     $$0.addFixer((DataFix)new AddNewChoices($$178, "Added Chiseled Bookshelf", References.BLOCK_ENTITY));
/*      */     
/* 1116 */     Schema $$179 = $$0.addSchema(3209, SAME_NAMESPACED);
/*      */     
/* 1118 */     $$0.addFixer((DataFix)new ItemStackSpawnEggFix($$179, false, "minecraft:pig_spawn_egg"));
/*      */     
/* 1120 */     Schema $$180 = $$0.addSchema(3214, SAME_NAMESPACED);
/* 1121 */     $$0.addFixer((DataFix)new OptionsAmbientOcclusionFix($$180));
/*      */     
/* 1123 */     Schema $$181 = $$0.addSchema(3319, SAME_NAMESPACED);
/* 1124 */     $$0.addFixer((DataFix)new OptionsAccessibilityOnboardFix($$181));
/*      */     
/* 1126 */     Schema $$182 = $$0.addSchema(3322, SAME_NAMESPACED);
/* 1127 */     $$0.addFixer((DataFix)new EffectDurationFix($$182));
/*      */     
/* 1129 */     Schema $$183 = $$0.addSchema(3325, net.minecraft.util.datafix.schemas.V3325::new);
/* 1130 */     $$0.addFixer((DataFix)new AddNewChoices($$183, "Added displays", References.ENTITY));
/*      */     
/* 1132 */     Schema $$184 = $$0.addSchema(3326, net.minecraft.util.datafix.schemas.V3326::new);
/* 1133 */     $$0.addFixer((DataFix)new AddNewChoices($$184, "Added Sniffer", References.ENTITY));
/*      */     
/* 1135 */     Schema $$185 = $$0.addSchema(3327, net.minecraft.util.datafix.schemas.V3327::new);
/* 1136 */     $$0.addFixer((DataFix)new AddNewChoices($$185, "Archaeology", References.BLOCK_ENTITY));
/*      */     
/* 1138 */     Schema $$186 = $$0.addSchema(3328, net.minecraft.util.datafix.schemas.V3328::new);
/* 1139 */     $$0.addFixer((DataFix)new AddNewChoices($$186, "Added interaction", References.ENTITY));
/*      */     
/* 1141 */     Schema $$187 = $$0.addSchema(3438, net.minecraft.util.datafix.schemas.V3438::new);
/* 1142 */     $$0.addFixer(BlockEntityRenameFix.create($$187, "Rename Suspicious Sand to Brushable Block", createRenamer("minecraft:suspicious_sand", "minecraft:brushable_block")));
/* 1143 */     $$0.addFixer((DataFix)new EntityBrushableBlockFieldsRenameFix($$187));
/* 1144 */     $$0.addFixer(ItemRenameFix.create($$187, "Pottery shard renaming", createRenamer(
/* 1145 */             (Map<String, String>)ImmutableMap.of("minecraft:pottery_shard_archer", "minecraft:archer_pottery_shard", "minecraft:pottery_shard_prize", "minecraft:prize_pottery_shard", "minecraft:pottery_shard_arms_up", "minecraft:arms_up_pottery_shard", "minecraft:pottery_shard_skull", "minecraft:skull_pottery_shard"))));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1151 */     $$0.addFixer((DataFix)new AddNewChoices($$187, "Added calibrated sculk sensor", References.BLOCK_ENTITY));
/*      */     
/* 1153 */     Schema $$188 = $$0.addSchema(3439, SAME_NAMESPACED);
/* 1154 */     $$0.addFixer((DataFix)new BlockEntitySignDoubleSidedEditableTextFix($$188, "Updated sign text format for Signs", "minecraft:sign"));
/* 1155 */     $$0.addFixer((DataFix)new BlockEntitySignDoubleSidedEditableTextFix($$188, "Updated sign text format for Hanging Signs", "minecraft:hanging_sign"));
/*      */     
/* 1157 */     Schema $$189 = $$0.addSchema(3440, SAME_NAMESPACED);
/* 1158 */     $$0.addFixer((DataFix)new NamespacedTypeRenameFix($$189, "Replace experimental 1.20 overworld", References.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, createRenamer("minecraft:overworld_update_1_20", "minecraft:overworld")));
/* 1159 */     $$0.addFixer((DataFix)new FeatureFlagRemoveFix($$189, "Remove 1.20 feature toggle", Set.of("minecraft:update_1_20")));
/*      */     
/* 1161 */     Schema $$190 = $$0.addSchema(3441, SAME_NAMESPACED);
/*      */     
/* 1163 */     $$0.addFixer((DataFix)new BlendingDataFix($$190));
/*      */     
/* 1165 */     Schema $$191 = $$0.addSchema(3447, SAME_NAMESPACED);
/* 1166 */     $$0.addFixer(ItemRenameFix.create($$191, "Pottery shard item renaming to Pottery sherd", createRenamer(
/* 1167 */             (Map<String, String>)Stream.<String>of(new String[] {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 "minecraft:angler_pottery_shard", "minecraft:archer_pottery_shard", "minecraft:arms_up_pottery_shard", "minecraft:blade_pottery_shard", "minecraft:brewer_pottery_shard", "minecraft:burn_pottery_shard", "minecraft:danger_pottery_shard", "minecraft:explorer_pottery_shard", "minecraft:friend_pottery_shard", "minecraft:heart_pottery_shard",
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 "minecraft:heartbreak_pottery_shard", "minecraft:howl_pottery_shard", "minecraft:miner_pottery_shard", "minecraft:mourner_pottery_shard", "minecraft:plenty_pottery_shard", "minecraft:prize_pottery_shard", "minecraft:sheaf_pottery_shard", "minecraft:shelter_pottery_shard", "minecraft:skull_pottery_shard", "minecraft:snort_pottery_shard"
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1188 */               }).collect(Collectors.toMap(
/* 1189 */                 Function.identity(), $$0 -> $$0.replace("_pottery_shard", "_pottery_sherd"))))));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1194 */     Schema $$192 = $$0.addSchema(3448, net.minecraft.util.datafix.schemas.V3448::new);
/* 1195 */     $$0.addFixer((DataFix)new DecoratedPotFieldRenameFix($$192));
/*      */     
/* 1197 */     Schema $$193 = $$0.addSchema(3450, SAME_NAMESPACED);
/* 1198 */     $$0.addFixer((DataFix)new RemapChunkStatusFix($$193, "Remove liquid_carvers and heightmap chunk statuses", createRenamer(Map.of("minecraft:liquid_carvers", "minecraft:carvers", "minecraft:heightmaps", "minecraft:spawn"))));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1203 */     Schema $$194 = $$0.addSchema(3451, SAME_NAMESPACED);
/* 1204 */     $$0.addFixer((DataFix)new ChunkDeleteLightFix($$194));
/*      */     
/* 1206 */     Schema $$195 = $$0.addSchema(3459, SAME_NAMESPACED);
/* 1207 */     $$0.addFixer((DataFix)new LegacyDragonFightFix($$195));
/*      */     
/* 1209 */     Schema $$196 = $$0.addSchema(3564, SAME_NAMESPACED);
/* 1210 */     $$0.addFixer((DataFix)new DropInvalidSignDataFix($$196, "Drop invalid sign datafix data", "minecraft:sign"));
/* 1211 */     $$0.addFixer((DataFix)new DropInvalidSignDataFix($$196, "Drop invalid hanging sign datafix data", "minecraft:hanging_sign"));
/*      */     
/* 1213 */     Schema $$197 = $$0.addSchema(3565, SAME_NAMESPACED);
/* 1214 */     $$0.addFixer((DataFix)new RandomSequenceSettingsFix($$197));
/*      */     
/* 1216 */     Schema $$198 = $$0.addSchema(3566, SAME_NAMESPACED);
/* 1217 */     $$0.addFixer((DataFix)new ScoreboardDisplaySlotFix($$198));
/*      */     
/* 1219 */     Schema $$199 = $$0.addSchema(3568, SAME_NAMESPACED);
/* 1220 */     $$0.addFixer((DataFix)new MobEffectIdFix($$199));
/*      */     
/* 1222 */     Schema $$200 = $$0.addSchema(3682, net.minecraft.util.datafix.schemas.V3682::new);
/* 1223 */     $$0.addFixer((DataFix)new AddNewChoices($$200, "Added Crafter", References.BLOCK_ENTITY));
/*      */     
/* 1225 */     Schema $$201 = $$0.addSchema(3683, net.minecraft.util.datafix.schemas.V3683::new);
/* 1226 */     $$0.addFixer((DataFix)new PrimedTntBlockStateFixer($$201));
/*      */     
/* 1228 */     Schema $$202 = $$0.addSchema(3685, net.minecraft.util.datafix.schemas.V3685::new);
/* 1229 */     $$0.addFixer((DataFix)new FixProjectileStoredItem($$202));
/*      */     
/* 1231 */     Schema $$203 = $$0.addSchema(3689, net.minecraft.util.datafix.schemas.V3689::new);
/* 1232 */     $$0.addFixer((DataFix)new AddNewChoices($$203, "Added Breeze", References.ENTITY));
/* 1233 */     $$0.addFixer((DataFix)new AddNewChoices($$203, "Added Trial Spawner", References.BLOCK_ENTITY));
/*      */     
/* 1235 */     Schema $$204 = $$0.addSchema(3692, SAME_NAMESPACED);
/* 1236 */     UnaryOperator<String> $$205 = createRenamer(Map.of("minecraft:grass", "minecraft:short_grass"));
/* 1237 */     $$0.addFixer(BlockRenameFixWithJigsaw.create($$204, "Rename grass block to short_grass", $$205));
/* 1238 */     $$0.addFixer(ItemRenameFix.create($$204, "Rename grass item to short_grass", $$205));
/*      */   }
/*      */   
/*      */   private static UnaryOperator<String> createRenamer(Map<String, String> $$0) {
/* 1242 */     return $$1 -> (String)$$0.getOrDefault($$1, $$1);
/*      */   }
/*      */   
/*      */   private static UnaryOperator<String> createRenamer(String $$0, String $$1) {
/* 1246 */     return $$2 -> Objects.equals($$2, $$0) ? $$1 : $$2;
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\DataFixers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */