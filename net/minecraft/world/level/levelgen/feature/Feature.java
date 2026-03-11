/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.LevelSimulatedReader;
/*     */ import net.minecraft.world.level.LevelWriter;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.LayerConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.TwistingVinesConfig;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.UnderwaterMagmaConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
/*     */ 
/*     */ public abstract class Feature<FC extends FeatureConfiguration> {
/*  58 */   public static final Feature<NoneFeatureConfiguration> NO_OP = register("no_op", new NoOpFeature(NoneFeatureConfiguration.CODEC));
/*  59 */   public static final Feature<TreeConfiguration> TREE = register("tree", new TreeFeature(TreeConfiguration.CODEC));
/*     */   
/*  61 */   public static final Feature<RandomPatchConfiguration> FLOWER = register("flower", new RandomPatchFeature(RandomPatchConfiguration.CODEC));
/*  62 */   public static final Feature<RandomPatchConfiguration> NO_BONEMEAL_FLOWER = register("no_bonemeal_flower", new RandomPatchFeature(RandomPatchConfiguration.CODEC));
/*  63 */   public static final Feature<RandomPatchConfiguration> RANDOM_PATCH = register("random_patch", new RandomPatchFeature(RandomPatchConfiguration.CODEC));
/*  64 */   public static final Feature<BlockPileConfiguration> BLOCK_PILE = register("block_pile", new BlockPileFeature(BlockPileConfiguration.CODEC));
/*  65 */   public static final Feature<SpringConfiguration> SPRING = register("spring_feature", new SpringFeature(SpringConfiguration.CODEC));
/*     */   
/*  67 */   public static final Feature<NoneFeatureConfiguration> CHORUS_PLANT = register("chorus_plant", new ChorusPlantFeature(NoneFeatureConfiguration.CODEC));
/*  68 */   public static final Feature<ReplaceBlockConfiguration> REPLACE_SINGLE_BLOCK = register("replace_single_block", new ReplaceBlockFeature(ReplaceBlockConfiguration.CODEC));
/*     */   
/*  70 */   public static final Feature<NoneFeatureConfiguration> VOID_START_PLATFORM = register("void_start_platform", new VoidStartPlatformFeature(NoneFeatureConfiguration.CODEC));
/*  71 */   public static final Feature<NoneFeatureConfiguration> DESERT_WELL = register("desert_well", new DesertWellFeature(NoneFeatureConfiguration.CODEC));
/*  72 */   public static final Feature<FossilFeatureConfiguration> FOSSIL = register("fossil", new FossilFeature(FossilFeatureConfiguration.CODEC));
/*  73 */   public static final Feature<HugeMushroomFeatureConfiguration> HUGE_RED_MUSHROOM = register("huge_red_mushroom", new HugeRedMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
/*  74 */   public static final Feature<HugeMushroomFeatureConfiguration> HUGE_BROWN_MUSHROOM = register("huge_brown_mushroom", new HugeBrownMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));
/*  75 */   public static final Feature<NoneFeatureConfiguration> ICE_SPIKE = register("ice_spike", new IceSpikeFeature(NoneFeatureConfiguration.CODEC));
/*  76 */   public static final Feature<NoneFeatureConfiguration> GLOWSTONE_BLOB = register("glowstone_blob", new GlowstoneFeature(NoneFeatureConfiguration.CODEC));
/*  77 */   public static final Feature<NoneFeatureConfiguration> FREEZE_TOP_LAYER = register("freeze_top_layer", new SnowAndFreezeFeature(NoneFeatureConfiguration.CODEC));
/*  78 */   public static final Feature<NoneFeatureConfiguration> VINES = register("vines", new VinesFeature(NoneFeatureConfiguration.CODEC));
/*  79 */   public static final Feature<BlockColumnConfiguration> BLOCK_COLUMN = register("block_column", new BlockColumnFeature(BlockColumnConfiguration.CODEC));
/*  80 */   public static final Feature<VegetationPatchConfiguration> VEGETATION_PATCH = register("vegetation_patch", new VegetationPatchFeature(VegetationPatchConfiguration.CODEC));
/*  81 */   public static final Feature<VegetationPatchConfiguration> WATERLOGGED_VEGETATION_PATCH = register("waterlogged_vegetation_patch", new WaterloggedVegetationPatchFeature(VegetationPatchConfiguration.CODEC));
/*  82 */   public static final Feature<RootSystemConfiguration> ROOT_SYSTEM = register("root_system", new RootSystemFeature(RootSystemConfiguration.CODEC));
/*  83 */   public static final Feature<MultifaceGrowthConfiguration> MULTIFACE_GROWTH = register("multiface_growth", new MultifaceGrowthFeature(MultifaceGrowthConfiguration.CODEC));
/*  84 */   public static final Feature<UnderwaterMagmaConfiguration> UNDERWATER_MAGMA = register("underwater_magma", new UnderwaterMagmaFeature(UnderwaterMagmaConfiguration.CODEC));
/*  85 */   public static final Feature<NoneFeatureConfiguration> MONSTER_ROOM = register("monster_room", new MonsterRoomFeature(NoneFeatureConfiguration.CODEC));
/*  86 */   public static final Feature<NoneFeatureConfiguration> BLUE_ICE = register("blue_ice", new BlueIceFeature(NoneFeatureConfiguration.CODEC));
/*  87 */   public static final Feature<BlockStateConfiguration> ICEBERG = register("iceberg", new IcebergFeature(BlockStateConfiguration.CODEC));
/*  88 */   public static final Feature<BlockStateConfiguration> FOREST_ROCK = register("forest_rock", new BlockBlobFeature(BlockStateConfiguration.CODEC));
/*  89 */   public static final Feature<DiskConfiguration> DISK = register("disk", new DiskFeature(DiskConfiguration.CODEC));
/*  90 */   public static final Feature<LakeFeature.Configuration> LAKE = register("lake", new LakeFeature(LakeFeature.Configuration.CODEC));
/*  91 */   public static final Feature<OreConfiguration> ORE = register("ore", new OreFeature(OreConfiguration.CODEC));
/*  92 */   public static final Feature<SpikeConfiguration> END_SPIKE = register("end_spike", new SpikeFeature(SpikeConfiguration.CODEC));
/*  93 */   public static final Feature<NoneFeatureConfiguration> END_ISLAND = register("end_island", new EndIslandFeature(NoneFeatureConfiguration.CODEC));
/*  94 */   public static final Feature<EndGatewayConfiguration> END_GATEWAY = register("end_gateway", new EndGatewayFeature(EndGatewayConfiguration.CODEC));
/*  95 */   public static final SeagrassFeature SEAGRASS = register("seagrass", new SeagrassFeature(ProbabilityFeatureConfiguration.CODEC));
/*  96 */   public static final Feature<NoneFeatureConfiguration> KELP = register("kelp", new KelpFeature(NoneFeatureConfiguration.CODEC));
/*  97 */   public static final Feature<NoneFeatureConfiguration> CORAL_TREE = register("coral_tree", new CoralTreeFeature(NoneFeatureConfiguration.CODEC));
/*  98 */   public static final Feature<NoneFeatureConfiguration> CORAL_MUSHROOM = register("coral_mushroom", new CoralMushroomFeature(NoneFeatureConfiguration.CODEC));
/*  99 */   public static final Feature<NoneFeatureConfiguration> CORAL_CLAW = register("coral_claw", new CoralClawFeature(NoneFeatureConfiguration.CODEC));
/* 100 */   public static final Feature<CountConfiguration> SEA_PICKLE = register("sea_pickle", new SeaPickleFeature(CountConfiguration.CODEC));
/* 101 */   public static final Feature<SimpleBlockConfiguration> SIMPLE_BLOCK = register("simple_block", new SimpleBlockFeature(SimpleBlockConfiguration.CODEC));
/* 102 */   public static final Feature<ProbabilityFeatureConfiguration> BAMBOO = register("bamboo", new BambooFeature(ProbabilityFeatureConfiguration.CODEC));
/*     */   
/* 104 */   public static final Feature<HugeFungusConfiguration> HUGE_FUNGUS = register("huge_fungus", new HugeFungusFeature(HugeFungusConfiguration.CODEC));
/* 105 */   public static final Feature<NetherForestVegetationConfig> NETHER_FOREST_VEGETATION = register("nether_forest_vegetation", new NetherForestVegetationFeature(NetherForestVegetationConfig.CODEC));
/* 106 */   public static final Feature<NoneFeatureConfiguration> WEEPING_VINES = register("weeping_vines", new WeepingVinesFeature(NoneFeatureConfiguration.CODEC));
/* 107 */   public static final Feature<TwistingVinesConfig> TWISTING_VINES = register("twisting_vines", new TwistingVinesFeature(TwistingVinesConfig.CODEC));
/*     */   
/* 109 */   public static final Feature<ColumnFeatureConfiguration> BASALT_COLUMNS = register("basalt_columns", new BasaltColumnsFeature(ColumnFeatureConfiguration.CODEC));
/* 110 */   public static final Feature<DeltaFeatureConfiguration> DELTA_FEATURE = register("delta_feature", new DeltaFeature(DeltaFeatureConfiguration.CODEC));
/* 111 */   public static final Feature<ReplaceSphereConfiguration> REPLACE_BLOBS = register("netherrack_replace_blobs", new ReplaceBlobsFeature(ReplaceSphereConfiguration.CODEC));
/*     */   
/* 113 */   public static final Feature<LayerConfiguration> FILL_LAYER = register("fill_layer", new FillLayerFeature(LayerConfiguration.CODEC));
/* 114 */   public static final BonusChestFeature BONUS_CHEST = register("bonus_chest", new BonusChestFeature(NoneFeatureConfiguration.CODEC));
/* 115 */   public static final Feature<NoneFeatureConfiguration> BASALT_PILLAR = register("basalt_pillar", new BasaltPillarFeature(NoneFeatureConfiguration.CODEC));
/* 116 */   public static final Feature<OreConfiguration> SCATTERED_ORE = register("scattered_ore", new ScatteredOreFeature(OreConfiguration.CODEC));
/*     */   
/* 118 */   public static final Feature<RandomFeatureConfiguration> RANDOM_SELECTOR = register("random_selector", new RandomSelectorFeature(RandomFeatureConfiguration.CODEC));
/* 119 */   public static final Feature<SimpleRandomFeatureConfiguration> SIMPLE_RANDOM_SELECTOR = register("simple_random_selector", new SimpleRandomSelectorFeature(SimpleRandomFeatureConfiguration.CODEC));
/* 120 */   public static final Feature<RandomBooleanFeatureConfiguration> RANDOM_BOOLEAN_SELECTOR = register("random_boolean_selector", new RandomBooleanSelectorFeature(RandomBooleanFeatureConfiguration.CODEC));
/*     */   
/* 122 */   public static final Feature<GeodeConfiguration> GEODE = register("geode", new GeodeFeature(GeodeConfiguration.CODEC));
/*     */   
/* 124 */   public static final Feature<DripstoneClusterConfiguration> DRIPSTONE_CLUSTER = register("dripstone_cluster", new DripstoneClusterFeature(DripstoneClusterConfiguration.CODEC));
/* 125 */   public static final Feature<LargeDripstoneConfiguration> LARGE_DRIPSTONE = register("large_dripstone", new LargeDripstoneFeature(LargeDripstoneConfiguration.CODEC));
/* 126 */   public static final Feature<PointedDripstoneConfiguration> POINTED_DRIPSTONE = register("pointed_dripstone", new PointedDripstoneFeature(PointedDripstoneConfiguration.CODEC));
/*     */   
/* 128 */   public static final Feature<SculkPatchConfiguration> SCULK_PATCH = register("sculk_patch", new SculkPatchFeature(SculkPatchConfiguration.CODEC)); private final Codec<ConfiguredFeature<FC, Feature<FC>>> configuredCodec;
/*     */   
/*     */   private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String $$0, F $$1) {
/* 131 */     return (F)Registry.register(BuiltInRegistries.FEATURE, $$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Feature(Codec<FC> $$0) {
/* 137 */     this.configuredCodec = $$0.fieldOf("config").xmap($$0 -> new ConfiguredFeature<>(this, $$0), ConfiguredFeature::config).codec();
/*     */   }
/*     */   
/*     */   public Codec<ConfiguredFeature<FC, Feature<FC>>> configuredCodec() {
/* 141 */     return this.configuredCodec;
/*     */   }
/*     */   
/*     */   protected void setBlock(LevelWriter $$0, BlockPos $$1, BlockState $$2) {
/* 145 */     $$0.setBlock($$1, $$2, 3);
/*     */   }
/*     */   
/*     */   public static Predicate<BlockState> isReplaceable(TagKey<Block> $$0) {
/* 149 */     return $$1 -> !$$1.is($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void safeSetBlock(WorldGenLevel $$0, BlockPos $$1, BlockState $$2, Predicate<BlockState> $$3) {
/* 157 */     if ($$3.test($$0.getBlockState($$1))) {
/* 158 */       $$0.setBlock($$1, $$2, 2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean place(FC $$0, WorldGenLevel $$1, ChunkGenerator $$2, RandomSource $$3, BlockPos $$4) {
/* 165 */     if ($$1.ensureCanWrite($$4)) {
/* 166 */       return place(new FeaturePlaceContext<>(Optional.empty(), $$1, $$2, $$3, $$4, $$0));
/*     */     }
/* 168 */     return false;
/*     */   }
/*     */   
/*     */   protected static boolean isStone(BlockState $$0) {
/* 172 */     return $$0.is(BlockTags.BASE_STONE_OVERWORLD);
/*     */   }
/*     */   
/*     */   public static boolean isDirt(BlockState $$0) {
/* 176 */     return $$0.is(BlockTags.DIRT);
/*     */   }
/*     */   
/*     */   public static boolean isGrassOrDirt(LevelSimulatedReader $$0, BlockPos $$1) {
/* 180 */     return $$0.isStateAtPosition($$1, Feature::isDirt);
/*     */   }
/*     */   
/*     */   public static boolean checkNeighbors(Function<BlockPos, BlockState> $$0, BlockPos $$1, Predicate<BlockState> $$2) {
/* 184 */     BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();
/* 185 */     for (Direction $$4 : Direction.values()) {
/* 186 */       $$3.setWithOffset((Vec3i)$$1, $$4);
/* 187 */       if ($$2.test($$0.apply($$3))) {
/* 188 */         return true;
/*     */       }
/*     */     } 
/* 191 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isAdjacentToAir(Function<BlockPos, BlockState> $$0, BlockPos $$1) {
/* 195 */     return checkNeighbors($$0, $$1, BlockBehaviour.BlockStateBase::isAir);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void markAboveForPostProcessing(WorldGenLevel $$0, BlockPos $$1) {
/* 203 */     BlockPos.MutableBlockPos $$2 = $$1.mutable();
/* 204 */     for (int $$3 = 0; $$3 < 2; $$3++) {
/* 205 */       $$2.move(Direction.UP);
/* 206 */       if ($$0.getBlockState((BlockPos)$$2).isAir()) {
/*     */         return;
/*     */       }
/* 209 */       $$0.getChunk((BlockPos)$$2).markPosForPostprocessing((BlockPos)$$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract boolean place(FeaturePlaceContext<FC> paramFeaturePlaceContext);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\Feature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */