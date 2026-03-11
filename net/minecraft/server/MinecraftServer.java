/*      */ package net.minecraft.server;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Splitter;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.GameProfileRepository;
/*      */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import com.mojang.datafixers.DataFixer;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.Writer;
/*      */ import java.lang.management.ManagementFactory;
/*      */ import java.lang.management.ThreadInfo;
/*      */ import java.lang.management.ThreadMXBean;
/*      */ import java.net.Proxy;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.security.KeyPair;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.CompletionStage;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import java.util.function.BooleanSupplier;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.LongSupplier;
/*      */ import java.util.stream.Collectors;
/*      */ import javax.annotation.Nullable;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.CrashReport;
/*      */ import net.minecraft.ReportedException;
/*      */ import net.minecraft.SharedConstants;
/*      */ import net.minecraft.SystemReport;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.commands.CommandSource;
/*      */ import net.minecraft.commands.CommandSourceStack;
/*      */ import net.minecraft.commands.Commands;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.HolderGetter;
/*      */ import net.minecraft.core.HolderLookup;
/*      */ import net.minecraft.core.LayeredRegistryAccess;
/*      */ import net.minecraft.core.Registry;
/*      */ import net.minecraft.core.RegistryAccess;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.core.registries.Registries;
/*      */ import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
/*      */ import net.minecraft.gametest.framework.GameTestTicker;
/*      */ import net.minecraft.network.chat.ChatDecorator;
/*      */ import net.minecraft.network.chat.ChatType;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
/*      */ import net.minecraft.network.protocol.status.ServerStatus;
/*      */ import net.minecraft.obfuscate.DontObfuscate;
/*      */ import net.minecraft.resources.ResourceKey;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.bossevents.CustomBossEvents;
/*      */ import net.minecraft.server.level.DemoMode;
/*      */ import net.minecraft.server.level.PlayerRespawnLogic;
/*      */ import net.minecraft.server.level.ServerChunkCache;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.server.level.ServerPlayer;
/*      */ import net.minecraft.server.level.ServerPlayerGameMode;
/*      */ import net.minecraft.server.level.TicketType;
/*      */ import net.minecraft.server.level.progress.ChunkProgressListener;
/*      */ import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
/*      */ import net.minecraft.server.network.ServerConnectionListener;
/*      */ import net.minecraft.server.network.TextFilter;
/*      */ import net.minecraft.server.packs.PackType;
/*      */ import net.minecraft.server.packs.repository.Pack;
/*      */ import net.minecraft.server.packs.repository.PackRepository;
/*      */ import net.minecraft.server.packs.resources.CloseableResourceManager;
/*      */ import net.minecraft.server.packs.resources.MultiPackResourceManager;
/*      */ import net.minecraft.server.packs.resources.ResourceManager;
/*      */ import net.minecraft.server.players.PlayerList;
/*      */ import net.minecraft.server.players.ServerOpListEntry;
/*      */ import net.minecraft.server.players.UserWhiteList;
/*      */ import net.minecraft.util.Crypt;
/*      */ import net.minecraft.util.CryptException;
/*      */ import net.minecraft.util.ModCheck;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.NativeModuleLister;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.util.TimeUtil;
/*      */ import net.minecraft.util.Unit;
/*      */ import net.minecraft.util.profiling.EmptyProfileResults;
/*      */ import net.minecraft.util.profiling.ProfileResults;
/*      */ import net.minecraft.util.profiling.ProfilerFiller;
/*      */ import net.minecraft.util.profiling.ResultField;
/*      */ import net.minecraft.util.profiling.SingleTickProfiler;
/*      */ import net.minecraft.util.profiling.jfr.JvmProfiler;
/*      */ import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
/*      */ import net.minecraft.util.profiling.metrics.MetricsSamplerProvider;
/*      */ import net.minecraft.util.profiling.metrics.profiling.ActiveMetricsRecorder;
/*      */ import net.minecraft.util.profiling.metrics.profiling.InactiveMetricsRecorder;
/*      */ import net.minecraft.util.profiling.metrics.profiling.MetricsRecorder;
/*      */ import net.minecraft.util.profiling.metrics.profiling.ServerMetricsSamplersProvider;
/*      */ import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
/*      */ import net.minecraft.util.thread.ReentrantBlockableEventLoop;
/*      */ import net.minecraft.world.Difficulty;
/*      */ import net.minecraft.world.RandomSequences;
/*      */ import net.minecraft.world.entity.ai.village.VillageSiege;
/*      */ import net.minecraft.world.entity.npc.CatSpawner;
/*      */ import net.minecraft.world.entity.npc.WanderingTraderSpawner;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.flag.FeatureFlagSet;
/*      */ import net.minecraft.world.flag.FeatureFlags;
/*      */ import net.minecraft.world.item.crafting.RecipeManager;
/*      */ import net.minecraft.world.level.ChunkPos;
/*      */ import net.minecraft.world.level.DataPackConfig;
/*      */ import net.minecraft.world.level.ForcedChunksSavedData;
/*      */ import net.minecraft.world.level.GameRules;
/*      */ import net.minecraft.world.level.GameType;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelHeightAccessor;
/*      */ import net.minecraft.world.level.LevelSettings;
/*      */ import net.minecraft.world.level.WorldDataConfiguration;
/*      */ import net.minecraft.world.level.WorldGenLevel;
/*      */ import net.minecraft.world.level.biome.BiomeManager;
/*      */ import net.minecraft.world.level.border.BorderChangeListener;
/*      */ import net.minecraft.world.level.border.WorldBorder;
/*      */ import net.minecraft.world.level.dimension.LevelStem;
/*      */ import net.minecraft.world.level.levelgen.Heightmap;
/*      */ import net.minecraft.world.level.levelgen.PatrolSpawner;
/*      */ import net.minecraft.world.level.levelgen.PhantomSpawner;
/*      */ import net.minecraft.world.level.levelgen.WorldOptions;
/*      */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*      */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*      */ import net.minecraft.world.level.storage.CommandStorage;
/*      */ import net.minecraft.world.level.storage.DerivedLevelData;
/*      */ import net.minecraft.world.level.storage.DimensionDataStorage;
/*      */ import net.minecraft.world.level.storage.LevelData;
/*      */ import net.minecraft.world.level.storage.LevelResource;
/*      */ import net.minecraft.world.level.storage.LevelStorageSource;
/*      */ import net.minecraft.world.level.storage.PlayerDataStorage;
/*      */ import net.minecraft.world.level.storage.ServerLevelData;
/*      */ import net.minecraft.world.level.storage.WorldData;
/*      */ import net.minecraft.world.level.storage.loot.LootDataManager;
/*      */ import net.minecraft.world.phys.Vec2;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public abstract class MinecraftServer extends ReentrantBlockableEventLoop<TickTask> implements ServerInfo, CommandSource, AutoCloseable {
/*  166 */   private static final Logger LOGGER = LogUtils.getLogger();
/*      */   public static final String VANILLA_BRAND = "vanilla";
/*      */   private static final float AVERAGE_TICK_TIME_SMOOTHING = 0.8F;
/*      */   private static final int TICK_STATS_SPAN = 100;
/*  170 */   private static final long OVERLOADED_THRESHOLD_NANOS = 20L * TimeUtil.NANOSECONDS_PER_SECOND / 20L;
/*      */   private static final int OVERLOADED_TICKS_THRESHOLD = 20;
/*  172 */   private static final long OVERLOADED_WARNING_INTERVAL_NANOS = 10L * TimeUtil.NANOSECONDS_PER_SECOND;
/*      */   private static final int OVERLOADED_TICKS_WARNING_INTERVAL = 100;
/*  174 */   private static final long STATUS_EXPIRE_TIME_NANOS = 5L * TimeUtil.NANOSECONDS_PER_SECOND;
/*  175 */   private static final long PREPARE_LEVELS_DEFAULT_DELAY_NANOS = 10L * TimeUtil.NANOSECONDS_PER_MILLISECOND;
/*      */   
/*      */   private static final int MAX_STATUS_PLAYER_SAMPLE = 12;
/*      */   
/*      */   public static final int START_CHUNK_RADIUS = 11;
/*      */   
/*      */   private static final int START_TICKING_CHUNK_COUNT = 441;
/*      */   
/*      */   private static final int AUTOSAVE_INTERVAL = 6000;
/*      */   private static final int MIMINUM_AUTOSAVE_TICKS = 100;
/*      */   private static final int MAX_TICK_LATENCY = 3;
/*      */   public static final int ABSOLUTE_MAX_WORLD_SIZE = 29999984;
/*  187 */   public static final LevelSettings DEMO_SETTINGS = new LevelSettings("Demo World", GameType.SURVIVAL, false, Difficulty.NORMAL, false, new GameRules(), WorldDataConfiguration.DEFAULT);
/*  188 */   public static final GameProfile ANONYMOUS_PLAYER_PROFILE = new GameProfile(Util.NIL_UUID, "Anonymous Player");
/*      */   
/*      */   protected final LevelStorageSource.LevelStorageAccess storageSource;
/*      */   
/*      */   protected final PlayerDataStorage playerDataStorage;
/*  193 */   private final List<Runnable> tickables = Lists.newArrayList();
/*  194 */   private MetricsRecorder metricsRecorder = InactiveMetricsRecorder.INSTANCE;
/*  195 */   private ProfilerFiller profiler = this.metricsRecorder.getProfiler(); private Consumer<ProfileResults> onMetricsRecordingStopped = $$0 -> stopRecordingMetrics();
/*      */   private Consumer<Path> onMetricsRecordingFinished = $$0 -> {
/*      */     
/*      */     };
/*      */   private boolean willStartRecordingMetrics;
/*      */   @Nullable
/*      */   private TimeProfiler debugCommandProfiler;
/*      */   private boolean debugCommandProfilerDelayStart;
/*      */   private final ServerConnectionListener connection;
/*      */   private final ChunkProgressListenerFactory progressListenerFactory;
/*      */   @Nullable
/*      */   private ServerStatus status;
/*      */   @Nullable
/*      */   private ServerStatus.Favicon statusIcon;
/*  209 */   private final RandomSource random = RandomSource.create();
/*      */   
/*      */   private final DataFixer fixerUpper;
/*      */   private String localIp;
/*  213 */   private int port = -1;
/*      */   private final LayeredRegistryAccess<RegistryLayer> registries;
/*  215 */   private final Map<ResourceKey<Level>, ServerLevel> levels = Maps.newLinkedHashMap();
/*      */   private PlayerList playerList;
/*      */   private volatile boolean running = true;
/*      */   private boolean stopped;
/*      */   private int tickCount;
/*  220 */   private int ticksUntilAutosave = 6000;
/*      */   
/*      */   protected final Proxy proxy;
/*      */   
/*      */   private boolean onlineMode;
/*      */   private boolean preventProxyConnections;
/*      */   private boolean pvp;
/*      */   private boolean allowFlight;
/*      */   @Nullable
/*      */   private String motd;
/*      */   private int playerIdleTimeout;
/*  231 */   private final long[] tickTimesNanos = new long[100];
/*  232 */   private long aggregatedTickTimesNanos = 0L;
/*      */   
/*      */   @Nullable
/*      */   private KeyPair keyPair;
/*      */   @Nullable
/*      */   private GameProfile singleplayerProfile;
/*      */   private boolean isDemo;
/*      */   private volatile boolean isReady;
/*      */   private long lastOverloadWarningNanos;
/*      */   protected final Services services;
/*      */   private long lastServerStatus;
/*      */   private final Thread serverThread;
/*  244 */   private long nextTickTimeNanos = Util.getNanos();
/*      */   private long delayedTasksMaxNextTickTimeNanos;
/*      */   private boolean mayHaveDelayedTasks;
/*      */   private final PackRepository packRepository;
/*  248 */   private final ServerScoreboard scoreboard = new ServerScoreboard(this);
/*      */   @Nullable
/*      */   private CommandStorage commandStorage;
/*  251 */   private final CustomBossEvents customBossEvents = new CustomBossEvents();
/*      */   
/*      */   private final ServerFunctionManager functionManager;
/*      */   
/*      */   private boolean enforceWhitelist;
/*      */   
/*      */   private float smoothedTickTimeMillis;
/*      */   private final Executor executor;
/*      */   @Nullable
/*      */   private String serverId;
/*      */   private ReloadableResources resources;
/*      */   private final StructureTemplateManager structureTemplateManager;
/*      */   private final ServerTickRateManager tickRateManager;
/*      */   protected final WorldData worldData;
/*      */   private volatile boolean isSaving;
/*      */   
/*      */   public static <S extends MinecraftServer> S spin(Function<Thread, S> $$0) {
/*  268 */     AtomicReference<S> $$1 = new AtomicReference<>();
/*      */     
/*  270 */     Thread $$2 = new Thread(() -> ((MinecraftServer)$$0.get()).runServer(), "Server thread");
/*  271 */     $$2.setUncaughtExceptionHandler(($$0, $$1) -> LOGGER.error("Uncaught exception in server thread", $$1));
/*      */     
/*  273 */     if (Runtime.getRuntime().availableProcessors() > 4) {
/*  274 */       $$2.setPriority(8);
/*      */     }
/*      */     
/*  277 */     MinecraftServer minecraftServer = (MinecraftServer)$$0.apply($$2);
/*  278 */     $$1.set((S)minecraftServer);
/*  279 */     $$2.start();
/*  280 */     return (S)minecraftServer;
/*      */   }
/*      */   
/*      */   public MinecraftServer(Thread $$0, LevelStorageSource.LevelStorageAccess $$1, PackRepository $$2, WorldStem $$3, Proxy $$4, DataFixer $$5, Services $$6, ChunkProgressListenerFactory $$7) {
/*  284 */     super("Server");
/*  285 */     this.registries = $$3.registries();
/*  286 */     this.worldData = $$3.worldData();
/*      */ 
/*      */     
/*  289 */     if (!this.registries.compositeAccess().registryOrThrow(Registries.LEVEL_STEM).containsKey(LevelStem.OVERWORLD)) {
/*  290 */       throw new IllegalStateException("Missing Overworld dimension data");
/*      */     }
/*  292 */     this.proxy = $$4;
/*  293 */     this.packRepository = $$2;
/*  294 */     this.resources = new ReloadableResources($$3.resourceManager(), $$3.dataPackResources());
/*  295 */     this.services = $$6;
/*  296 */     if ($$6.profileCache() != null) {
/*  297 */       $$6.profileCache().setExecutor((Executor)this);
/*      */     }
/*  299 */     this.connection = new ServerConnectionListener(this);
/*  300 */     this.tickRateManager = new ServerTickRateManager(this);
/*  301 */     this.progressListenerFactory = $$7;
/*  302 */     this.storageSource = $$1;
/*  303 */     this.playerDataStorage = $$1.createPlayerStorage();
/*  304 */     this.fixerUpper = $$5;
/*  305 */     this.functionManager = new ServerFunctionManager(this, this.resources.managers.getFunctionLibrary());
/*  306 */     HolderLookup holderLookup = this.registries.compositeAccess().registryOrThrow(Registries.BLOCK).asLookup().filterFeatures(this.worldData.enabledFeatures());
/*  307 */     this.structureTemplateManager = new StructureTemplateManager((ResourceManager)$$3.resourceManager(), $$1, $$5, (HolderGetter)holderLookup);
/*  308 */     this.serverThread = $$0;
/*  309 */     this.executor = Util.backgroundExecutor();
/*      */   }
/*      */ 
/*      */   
/*      */   private void readScoreboard(DimensionDataStorage $$0) {
/*  314 */     $$0.computeIfAbsent(getScoreboard().dataFactory(), "scoreboard");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void loadLevel() {
/*  320 */     if (!JvmProfiler.INSTANCE.isRunning()); boolean $$0 = false;
/*      */ 
/*      */ 
/*      */     
/*  324 */     ProfiledDuration $$1 = JvmProfiler.INSTANCE.onWorldLoadedStarted();
/*      */     
/*  326 */     this.worldData.setModdedInfo(getServerModName(), getModdedStatus().shouldReportAsModified());
/*      */     
/*  328 */     ChunkProgressListener $$2 = this.progressListenerFactory.create(11);
/*      */     
/*  330 */     createLevels($$2);
/*      */     
/*  332 */     forceDifficulty();
/*  333 */     prepareLevels($$2);
/*  334 */     if ($$1 != null) {
/*  335 */       $$1.finish();
/*      */     }
/*      */     
/*  338 */     if ($$0) {
/*      */       try {
/*  340 */         JvmProfiler.INSTANCE.stop();
/*  341 */       } catch (Throwable $$3) {
/*  342 */         LOGGER.warn("Failed to stop JFR profiling", $$3);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void forceDifficulty() {}
/*      */   
/*      */   protected void createLevels(ChunkProgressListener $$0) {
/*  351 */     ServerLevelData $$1 = this.worldData.overworldData();
/*  352 */     boolean $$2 = this.worldData.isDebugWorld();
/*  353 */     Registry<LevelStem> $$3 = this.registries.compositeAccess().registryOrThrow(Registries.LEVEL_STEM);
/*  354 */     WorldOptions $$4 = this.worldData.worldGenOptions();
/*  355 */     long $$5 = $$4.seed();
/*  356 */     long $$6 = BiomeManager.obfuscateSeed($$5);
/*  357 */     ImmutableList immutableList = ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new VillageSiege(), new WanderingTraderSpawner($$1));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  364 */     LevelStem $$8 = (LevelStem)$$3.get(LevelStem.OVERWORLD);
/*  365 */     ServerLevel $$9 = new ServerLevel(this, this.executor, this.storageSource, $$1, Level.OVERWORLD, $$8, $$0, $$2, $$6, (List)immutableList, true, null);
/*  366 */     this.levels.put(Level.OVERWORLD, $$9);
/*  367 */     DimensionDataStorage $$10 = $$9.getDataStorage();
/*  368 */     readScoreboard($$10);
/*  369 */     this.commandStorage = new CommandStorage($$10);
/*  370 */     WorldBorder $$11 = $$9.getWorldBorder();
/*      */     
/*  372 */     if (!$$1.isInitialized()) {
/*      */       try {
/*  374 */         setInitialSpawn($$9, $$1, $$4.generateBonusChest(), $$2);
/*  375 */         $$1.setInitialized(true);
/*  376 */         if ($$2) {
/*  377 */           setupDebugLevel(this.worldData);
/*      */         }
/*  379 */       } catch (Throwable $$12) {
/*  380 */         CrashReport $$13 = CrashReport.forThrowable($$12, "Exception initializing level");
/*      */         try {
/*  382 */           $$9.fillReportDetails($$13);
/*  383 */         } catch (Throwable throwable) {}
/*      */ 
/*      */         
/*  386 */         throw new ReportedException($$13);
/*      */       } 
/*  388 */       $$1.setInitialized(true);
/*      */     } 
/*      */     
/*  391 */     getPlayerList().addWorldborderListener($$9);
/*      */     
/*  393 */     if (this.worldData.getCustomBossEvents() != null) {
/*  394 */       getCustomBossEvents().load(this.worldData.getCustomBossEvents());
/*      */     }
/*      */     
/*  397 */     RandomSequences $$14 = $$9.getRandomSequences();
/*  398 */     for (Map.Entry<ResourceKey<LevelStem>, LevelStem> $$15 : (Iterable<Map.Entry<ResourceKey<LevelStem>, LevelStem>>)$$3.entrySet()) {
/*  399 */       ResourceKey<LevelStem> $$16 = $$15.getKey();
/*  400 */       if ($$16 == LevelStem.OVERWORLD) {
/*      */         continue;
/*      */       }
/*  403 */       ResourceKey<Level> $$17 = ResourceKey.create(Registries.DIMENSION, $$16.location());
/*  404 */       DerivedLevelData $$18 = new DerivedLevelData(this.worldData, $$1);
/*  405 */       ServerLevel $$19 = new ServerLevel(this, this.executor, this.storageSource, (ServerLevelData)$$18, $$17, $$15.getValue(), $$0, $$2, $$6, (List)ImmutableList.of(), false, $$14);
/*  406 */       $$11.addListener((BorderChangeListener)new BorderChangeListener.DelegateBorderChangeListener($$19.getWorldBorder()));
/*  407 */       this.levels.put($$17, $$19);
/*      */     } 
/*      */     
/*  410 */     $$11.applySettings($$1.getWorldBorder());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setInitialSpawn(ServerLevel $$0, ServerLevelData $$1, boolean $$2, boolean $$3) {
/*  419 */     if ($$3) {
/*  420 */       $$1.setSpawn(BlockPos.ZERO.above(80), 0.0F);
/*      */       
/*      */       return;
/*      */     } 
/*  424 */     ServerChunkCache $$4 = $$0.getChunkSource();
/*  425 */     ChunkPos $$5 = new ChunkPos($$4.randomState().sampler().findSpawnPosition());
/*      */     
/*  427 */     int $$6 = $$4.getGenerator().getSpawnHeight((LevelHeightAccessor)$$0);
/*      */     
/*  429 */     if ($$6 < $$0.getMinBuildHeight()) {
/*  430 */       BlockPos $$7 = $$5.getWorldPosition();
/*  431 */       $$6 = $$0.getHeight(Heightmap.Types.WORLD_SURFACE, $$7.getX() + 8, $$7.getZ() + 8);
/*      */     } 
/*  433 */     $$1.setSpawn($$5.getWorldPosition().offset(8, $$6, 8), 0.0F);
/*      */     
/*  435 */     int $$8 = 0;
/*  436 */     int $$9 = 0;
/*  437 */     int $$10 = 0;
/*  438 */     int $$11 = -1;
/*  439 */     int $$12 = 5;
/*  440 */     for (int $$13 = 0; $$13 < Mth.square(11); $$13++) {
/*  441 */       if ($$8 >= -5 && $$8 <= 5 && $$9 >= -5 && $$9 <= 5) {
/*  442 */         BlockPos $$14 = PlayerRespawnLogic.getSpawnPosInChunk($$0, new ChunkPos($$5.x + $$8, $$5.z + $$9));
/*  443 */         if ($$14 != null) {
/*  444 */           $$1.setSpawn($$14, 0.0F);
/*      */           break;
/*      */         } 
/*      */       } 
/*  448 */       if ($$8 == $$9 || ($$8 < 0 && $$8 == -$$9) || ($$8 > 0 && $$8 == 1 - $$9)) {
/*  449 */         int $$15 = $$10;
/*  450 */         $$10 = -$$11;
/*  451 */         $$11 = $$15;
/*      */       } 
/*  453 */       $$8 += $$10;
/*  454 */       $$9 += $$11;
/*      */     } 
/*      */     
/*  457 */     if ($$2)
/*      */     {
/*      */       
/*  460 */       $$0.registryAccess()
/*  461 */         .registry(Registries.CONFIGURED_FEATURE)
/*  462 */         .flatMap($$0 -> $$0.getHolder(MiscOverworldFeatures.BONUS_CHEST))
/*  463 */         .ifPresent($$3 -> ((ConfiguredFeature)$$3.value()).place((WorldGenLevel)$$0, $$1.getGenerator(), $$0.random, new BlockPos($$2.getXSpawn(), $$2.getYSpawn(), $$2.getZSpawn())));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupDebugLevel(WorldData $$0) {
/*  470 */     $$0.setDifficulty(Difficulty.PEACEFUL);
/*  471 */     $$0.setDifficultyLocked(true);
/*      */     
/*  473 */     ServerLevelData $$1 = $$0.overworldData();
/*  474 */     $$1.setRaining(false);
/*  475 */     $$1.setThundering(false);
/*  476 */     $$1.setClearWeatherTime(1000000000);
/*  477 */     $$1.setDayTime(6000L);
/*  478 */     $$1.setGameType(GameType.SPECTATOR);
/*      */   }
/*      */   
/*      */   private void prepareLevels(ChunkProgressListener $$0) {
/*  482 */     ServerLevel $$1 = overworld();
/*  483 */     LOGGER.info("Preparing start region for dimension {}", $$1.dimension().location());
/*  484 */     BlockPos $$2 = $$1.getSharedSpawnPos();
/*  485 */     $$0.updateSpawnPos(new ChunkPos($$2));
/*      */     
/*  487 */     ServerChunkCache $$3 = $$1.getChunkSource();
/*  488 */     this.nextTickTimeNanos = Util.getNanos();
/*      */     
/*  490 */     $$3.addRegionTicket(TicketType.START, new ChunkPos($$2), 11, Unit.INSTANCE);
/*      */     
/*  492 */     while ($$3.getTickingGenerated() != 441) {
/*  493 */       this.nextTickTimeNanos = Util.getNanos() + PREPARE_LEVELS_DEFAULT_DELAY_NANOS;
/*  494 */       waitUntilNextTick();
/*      */     } 
/*      */     
/*  497 */     this.nextTickTimeNanos = Util.getNanos() + PREPARE_LEVELS_DEFAULT_DELAY_NANOS;
/*  498 */     waitUntilNextTick();
/*      */     
/*  500 */     for (ServerLevel $$4 : this.levels.values()) {
/*  501 */       ForcedChunksSavedData $$5 = (ForcedChunksSavedData)$$4.getDataStorage().get(ForcedChunksSavedData.factory(), "chunks");
/*  502 */       if ($$5 != null) {
/*  503 */         LongIterator $$6 = $$5.getChunks().iterator();
/*  504 */         while ($$6.hasNext()) {
/*  505 */           long $$7 = $$6.nextLong();
/*  506 */           ChunkPos $$8 = new ChunkPos($$7);
/*  507 */           $$4.getChunkSource().updateChunkForced($$8, true);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  512 */     this.nextTickTimeNanos = Util.getNanos() + PREPARE_LEVELS_DEFAULT_DELAY_NANOS;
/*  513 */     waitUntilNextTick();
/*      */ 
/*      */     
/*  516 */     $$0.stop();
/*      */ 
/*      */     
/*  519 */     updateMobSpawningFlags();
/*      */   }
/*      */   
/*      */   public GameType getDefaultGameType() {
/*  523 */     return this.worldData.getGameType();
/*      */   }
/*      */   
/*      */   public boolean isHardcore() {
/*  527 */     return this.worldData.isHardcore();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean saveAllChunks(boolean $$0, boolean $$1, boolean $$2) {
/*  537 */     boolean $$3 = false;
/*  538 */     for (ServerLevel $$4 : getAllLevels()) {
/*  539 */       if (!$$0) {
/*  540 */         LOGGER.info("Saving chunks for level '{}'/{}", $$4, $$4.dimension().location());
/*      */       }
/*  542 */       $$4.save(null, $$1, ($$4.noSave && !$$2));
/*  543 */       $$3 = true;
/*      */     } 
/*  545 */     ServerLevel $$5 = overworld();
/*  546 */     ServerLevelData $$6 = this.worldData.overworldData();
/*  547 */     $$6.setWorldBorder($$5.getWorldBorder().createSettings());
/*  548 */     this.worldData.setCustomBossEvents(getCustomBossEvents().save());
/*  549 */     this.storageSource.saveDataTag((RegistryAccess)registryAccess(), this.worldData, getPlayerList().getSingleplayerData());
/*      */     
/*  551 */     if ($$1) {
/*      */       
/*  553 */       for (ServerLevel $$7 : getAllLevels()) {
/*  554 */         LOGGER.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", ($$7.getChunkSource()).chunkMap.getStorageName());
/*      */       }
/*  556 */       LOGGER.info("ThreadedAnvilChunkStorage: All dimensions are saved");
/*      */     } 
/*      */     
/*  559 */     return $$3;
/*      */   }
/*      */   
/*      */   public boolean saveEverything(boolean $$0, boolean $$1, boolean $$2) {
/*      */     try {
/*  564 */       this.isSaving = true;
/*  565 */       getPlayerList().saveAll();
/*  566 */       return saveAllChunks($$0, $$1, $$2);
/*      */     } finally {
/*  568 */       this.isSaving = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void close() {
/*  574 */     stopServer();
/*      */   }
/*      */   
/*      */   public void stopServer() {
/*  578 */     if (this.metricsRecorder.isRecording()) {
/*  579 */       cancelRecordingMetrics();
/*      */     }
/*      */     
/*  582 */     LOGGER.info("Stopping server");
/*  583 */     getConnection().stop();
/*  584 */     this.isSaving = true;
/*  585 */     if (this.playerList != null) {
/*  586 */       LOGGER.info("Saving players");
/*  587 */       this.playerList.saveAll();
/*  588 */       this.playerList.removeAll();
/*      */     } 
/*  590 */     LOGGER.info("Saving worlds");
/*  591 */     for (ServerLevel $$0 : getAllLevels()) {
/*  592 */       if ($$0 != null) {
/*  593 */         $$0.noSave = false;
/*      */       }
/*      */     } 
/*      */     
/*  597 */     while (this.levels.values().stream().anyMatch($$0 -> ($$0.getChunkSource()).chunkMap.hasWork())) {
/*  598 */       this.nextTickTimeNanos = Util.getNanos() + TimeUtil.NANOSECONDS_PER_MILLISECOND;
/*  599 */       for (ServerLevel $$1 : getAllLevels()) {
/*  600 */         $$1.getChunkSource().removeTicketsOnClosing();
/*  601 */         $$1.getChunkSource().tick(() -> true, false);
/*      */       } 
/*  603 */       waitUntilNextTick();
/*      */     } 
/*      */     
/*  606 */     saveAllChunks(false, true, false);
/*  607 */     for (ServerLevel $$2 : getAllLevels()) {
/*  608 */       if ($$2 != null) {
/*      */         try {
/*  610 */           $$2.close();
/*  611 */         } catch (IOException $$3) {
/*  612 */           LOGGER.error("Exception closing the level", $$3);
/*      */         } 
/*      */       }
/*      */     } 
/*  616 */     this.isSaving = false;
/*  617 */     this.resources.close();
/*      */     try {
/*  619 */       this.storageSource.close();
/*  620 */     } catch (IOException $$4) {
/*  621 */       LOGGER.error("Failed to unlock level {}", this.storageSource.getLevelId(), $$4);
/*      */     } 
/*      */   }
/*      */   
/*      */   public String getLocalIp() {
/*  626 */     return this.localIp;
/*      */   }
/*      */   
/*      */   public void setLocalIp(String $$0) {
/*  630 */     this.localIp = $$0;
/*      */   }
/*      */   
/*      */   public boolean isRunning() {
/*  634 */     return this.running;
/*      */   }
/*      */   
/*      */   public void halt(boolean $$0) {
/*  638 */     this.running = false;
/*  639 */     if ($$0) {
/*      */       try {
/*  641 */         this.serverThread.join();
/*  642 */       } catch (InterruptedException $$1) {
/*  643 */         LOGGER.error("Error while shutting down", $$1);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected void runServer() {
/*      */     try {
/*  650 */       if (initServer()) {
/*  651 */         this.nextTickTimeNanos = Util.getNanos();
/*      */         
/*  653 */         this.statusIcon = loadStatusIcon().orElse(null);
/*  654 */         this.status = buildServerStatus();
/*      */         
/*  656 */         while (this.running) {
/*      */           long $$1;
/*  658 */           if (!isPaused() && this.tickRateManager.isSprinting() && this.tickRateManager.checkShouldSprintThisTick()) {
/*  659 */             long $$0 = 0L;
/*      */             
/*  661 */             this.nextTickTimeNanos = Util.getNanos();
/*  662 */             this.lastOverloadWarningNanos = this.nextTickTimeNanos;
/*      */           } else {
/*  664 */             $$1 = this.tickRateManager.nanosecondsPerTick();
/*  665 */             long $$2 = Util.getNanos() - this.nextTickTimeNanos;
/*  666 */             if ($$2 > OVERLOADED_THRESHOLD_NANOS + 20L * $$1 && this.nextTickTimeNanos - this.lastOverloadWarningNanos >= OVERLOADED_WARNING_INTERVAL_NANOS + 100L * $$1) {
/*  667 */               long $$3 = $$2 / $$1;
/*  668 */               LOGGER.warn("Can't keep up! Is the server overloaded? Running {}ms or {} ticks behind", Long.valueOf($$2 / TimeUtil.NANOSECONDS_PER_MILLISECOND), Long.valueOf($$3));
/*  669 */               this.nextTickTimeNanos += $$3 * $$1;
/*  670 */               this.lastOverloadWarningNanos = this.nextTickTimeNanos;
/*      */             } 
/*      */           } 
/*  673 */           boolean $$4 = ($$1 == 0L);
/*      */           
/*  675 */           if (this.debugCommandProfilerDelayStart) {
/*  676 */             this.debugCommandProfilerDelayStart = false;
/*  677 */             this.debugCommandProfiler = new TimeProfiler(Util.getNanos(), this.tickCount);
/*      */           } 
/*      */           
/*  680 */           this.nextTickTimeNanos += $$1;
/*  681 */           startMetricsRecordingTick();
/*  682 */           this.profiler.push("tick");
/*  683 */           tickServer($$4 ? (() -> false) : this::haveTime);
/*  684 */           this.profiler.popPush("nextTickWait");
/*  685 */           this.mayHaveDelayedTasks = true;
/*  686 */           this.delayedTasksMaxNextTickTimeNanos = Math.max(Util.getNanos() + $$1, this.nextTickTimeNanos);
/*  687 */           waitUntilNextTick();
/*  688 */           if ($$4) {
/*  689 */             this.tickRateManager.endTickWork();
/*      */           }
/*  691 */           this.profiler.pop();
/*  692 */           endMetricsRecordingTick();
/*      */           
/*  694 */           this.isReady = true;
/*      */           
/*  696 */           JvmProfiler.INSTANCE.onServerTick(this.smoothedTickTimeMillis);
/*      */         } 
/*      */       } else {
/*  699 */         throw new IllegalStateException("Failed to initialize server");
/*      */       } 
/*  701 */     } catch (Throwable $$6) {
/*  702 */       LOGGER.error("Encountered an unexpected exception", $$6);
/*  703 */       CrashReport $$7 = constructOrExtractCrashReport($$6);
/*  704 */       fillSystemReport($$7.getSystemReport());
/*      */       
/*  706 */       File $$8 = new File(new File(getServerDirectory(), "crash-reports"), "crash-" + Util.getFilenameFormattedDateTime() + "-server.txt");
/*      */       
/*  708 */       if ($$7.saveToFile($$8)) {
/*  709 */         LOGGER.error("This crash report has been saved to: {}", $$8.getAbsolutePath());
/*      */       } else {
/*  711 */         LOGGER.error("We were unable to save this crash report to disk.");
/*      */       } 
/*      */       
/*  714 */       onServerCrash($$7);
/*      */     } finally {
/*      */       try {
/*  717 */         this.stopped = true;
/*  718 */         stopServer();
/*  719 */       } catch (Throwable $$10) {
/*  720 */         LOGGER.error("Exception stopping the server", $$10);
/*      */       } finally {
/*  722 */         if (this.services.profileCache() != null) {
/*  723 */           this.services.profileCache().clearExecutor();
/*      */         }
/*  725 */         onServerExit();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static CrashReport constructOrExtractCrashReport(Throwable $$0) {
/*      */     CrashReport $$5;
/*  731 */     ReportedException $$1 = null;
/*  732 */     Throwable $$2 = $$0;
/*  733 */     while ($$2 != null) {
/*  734 */       if ($$2 instanceof ReportedException) { ReportedException $$3 = (ReportedException)$$2;
/*  735 */         $$1 = $$3; }
/*      */       
/*  737 */       $$2 = $$2.getCause();
/*      */     } 
/*      */ 
/*      */     
/*  741 */     if ($$1 != null) {
/*  742 */       CrashReport $$4 = $$1.getReport();
/*  743 */       if ($$1 != $$0) {
/*  744 */         $$4.addCategory("Wrapped in").setDetailError("Wrapping exception", $$0);
/*      */       }
/*      */     } else {
/*  747 */       $$5 = new CrashReport("Exception in server tick loop", $$0);
/*      */     } 
/*  749 */     return $$5;
/*      */   }
/*      */   
/*      */   private boolean haveTime() {
/*  753 */     return (runningTask() || Util.getNanos() < (this.mayHaveDelayedTasks ? this.delayedTasksMaxNextTickTimeNanos : this.nextTickTimeNanos));
/*      */   }
/*      */   
/*      */   protected void waitUntilNextTick() {
/*  757 */     runAllTasks();
/*  758 */     managedBlock(() -> !haveTime());
/*      */   }
/*      */ 
/*      */   
/*      */   protected TickTask wrapRunnable(Runnable $$0) {
/*  763 */     return new TickTask(this.tickCount, $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean shouldRun(TickTask $$0) {
/*  768 */     return ($$0.getTick() + 3 < this.tickCount || haveTime());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean pollTask() {
/*  773 */     boolean $$0 = pollTaskInternal();
/*  774 */     this.mayHaveDelayedTasks = $$0;
/*  775 */     return $$0;
/*      */   }
/*      */   
/*      */   private boolean pollTaskInternal() {
/*  779 */     if (super.pollTask()) {
/*  780 */       return true;
/*      */     }
/*      */     
/*  783 */     if (this.tickRateManager.isSprinting() || haveTime()) {
/*  784 */       for (ServerLevel $$0 : getAllLevels()) {
/*  785 */         if ($$0.getChunkSource().pollTask()) {
/*  786 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  791 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doRunTask(TickTask $$0) {
/*  796 */     getProfiler().incrementCounter("runTask");
/*  797 */     super.doRunTask($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   private Optional<ServerStatus.Favicon> loadStatusIcon() {
/*  802 */     Optional<Path> $$0 = Optional.<Path>of(getFile("server-icon.png").toPath()).filter($$0 -> Files.isRegularFile($$0, new java.nio.file.LinkOption[0])).or(() -> this.storageSource.getIconFile().filter(()));
/*      */     
/*  804 */     return $$0.flatMap($$0 -> {
/*      */           try {
/*      */             BufferedImage $$1 = ImageIO.read($$0.toFile());
/*      */             Preconditions.checkState(($$1.getWidth() == 64), "Must be 64 pixels wide");
/*      */             Preconditions.checkState(($$1.getHeight() == 64), "Must be 64 pixels high");
/*      */             ByteArrayOutputStream $$2 = new ByteArrayOutputStream();
/*      */             ImageIO.write($$1, "PNG", $$2);
/*      */             return Optional.of(new ServerStatus.Favicon($$2.toByteArray()));
/*  812 */           } catch (Exception $$3) {
/*      */             LOGGER.error("Couldn't load server icon", $$3);
/*      */             return Optional.empty();
/*      */           } 
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public Optional<Path> getWorldScreenshotFile() {
/*  821 */     return this.storageSource.getIconFile();
/*      */   }
/*      */   
/*      */   public File getServerDirectory() {
/*  825 */     return new File(".");
/*      */   }
/*      */ 
/*      */   
/*      */   public void onServerCrash(CrashReport $$0) {}
/*      */ 
/*      */   
/*      */   public void onServerExit() {}
/*      */   
/*      */   public boolean isPaused() {
/*  835 */     return false;
/*      */   }
/*      */   
/*      */   public void tickServer(BooleanSupplier $$0) {
/*  839 */     long $$1 = Util.getNanos();
/*      */     
/*  841 */     this.tickCount++;
/*      */     
/*  843 */     this.tickRateManager.tick();
/*      */     
/*  845 */     tickChildren($$0);
/*      */     
/*  847 */     if ($$1 - this.lastServerStatus >= STATUS_EXPIRE_TIME_NANOS) {
/*  848 */       this.lastServerStatus = $$1;
/*  849 */       this.status = buildServerStatus();
/*      */     } 
/*      */     
/*  852 */     this.ticksUntilAutosave--;
/*  853 */     if (this.ticksUntilAutosave <= 0) {
/*  854 */       this.ticksUntilAutosave = computeNextAutosaveInterval();
/*  855 */       LOGGER.debug("Autosave started");
/*  856 */       this.profiler.push("save");
/*  857 */       saveEverything(true, false, false);
/*  858 */       this.profiler.pop();
/*  859 */       LOGGER.debug("Autosave finished");
/*      */     } 
/*      */     
/*  862 */     this.profiler.push("tallying");
/*  863 */     long $$2 = Util.getNanos() - $$1;
/*  864 */     int $$3 = this.tickCount % 100;
/*  865 */     this.aggregatedTickTimesNanos -= this.tickTimesNanos[$$3];
/*  866 */     this.aggregatedTickTimesNanos += $$2;
/*  867 */     this.tickTimesNanos[$$3] = $$2;
/*  868 */     this.smoothedTickTimeMillis = this.smoothedTickTimeMillis * 0.8F + (float)$$2 / (float)TimeUtil.NANOSECONDS_PER_MILLISECOND * 0.19999999F;
/*      */     
/*  870 */     long $$4 = Util.getNanos();
/*  871 */     logTickTime($$4 - $$1);
/*      */     
/*  873 */     this.profiler.pop();
/*      */   }
/*      */   
/*      */   private int computeNextAutosaveInterval() {
/*      */     float $$2;
/*  878 */     if (this.tickRateManager.isSprinting()) {
/*  879 */       long $$0 = getAverageTickTimeNanos() + 1L;
/*  880 */       float $$1 = (float)TimeUtil.NANOSECONDS_PER_SECOND / (float)$$0;
/*      */     } else {
/*  882 */       $$2 = this.tickRateManager.tickrate();
/*      */     } 
/*  884 */     int $$3 = 300;
/*      */     
/*  886 */     return Math.max(100, (int)($$2 * 300.0F));
/*      */   }
/*      */   
/*      */   public void onTickRateChanged() {
/*  890 */     int $$0 = computeNextAutosaveInterval();
/*  891 */     if ($$0 < this.ticksUntilAutosave) {
/*  892 */       this.ticksUntilAutosave = $$0;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void logTickTime(long $$0) {}
/*      */   
/*      */   private ServerStatus buildServerStatus() {
/*  900 */     ServerStatus.Players $$0 = buildPlayerStatus();
/*  901 */     return new ServerStatus(
/*  902 */         Component.nullToEmpty(this.motd), 
/*  903 */         Optional.of($$0), 
/*  904 */         Optional.of(ServerStatus.Version.current()), 
/*  905 */         Optional.ofNullable(this.statusIcon), 
/*  906 */         enforceSecureProfile());
/*      */   }
/*      */ 
/*      */   
/*      */   private ServerStatus.Players buildPlayerStatus() {
/*  911 */     List<ServerPlayer> $$0 = this.playerList.getPlayers();
/*  912 */     int $$1 = getMaxPlayers();
/*  913 */     if (hidesOnlinePlayers()) {
/*  914 */       return new ServerStatus.Players($$1, $$0.size(), List.of());
/*      */     }
/*      */     
/*  917 */     int $$2 = Math.min($$0.size(), 12);
/*      */     
/*  919 */     ObjectArrayList<GameProfile> $$3 = new ObjectArrayList($$2);
/*  920 */     int $$4 = Mth.nextInt(this.random, 0, $$0.size() - $$2);
/*  921 */     for (int $$5 = 0; $$5 < $$2; $$5++) {
/*  922 */       ServerPlayer $$6 = $$0.get($$4 + $$5);
/*  923 */       $$3.add($$6.allowsListing() ? $$6.getGameProfile() : ANONYMOUS_PLAYER_PROFILE);
/*      */     } 
/*      */     
/*  926 */     Util.shuffle((List)$$3, this.random);
/*      */     
/*  928 */     return new ServerStatus.Players($$1, $$0.size(), (List)$$3);
/*      */   }
/*      */   
/*      */   public void tickChildren(BooleanSupplier $$0) {
/*  932 */     getPlayerList().getPlayers().forEach($$0 -> $$0.connection.suspendFlushing());
/*  933 */     this.profiler.push("commandFunctions");
/*  934 */     getFunctions().tick();
/*      */     
/*  936 */     this.profiler.popPush("levels");
/*  937 */     for (ServerLevel $$1 : getAllLevels()) {
/*  938 */       this.profiler.push(() -> "" + $$0 + " " + $$0);
/*      */       
/*  940 */       if (this.tickCount % 20 == 0) {
/*  941 */         this.profiler.push("timeSync");
/*  942 */         synchronizeTime($$1);
/*  943 */         this.profiler.pop();
/*      */       } 
/*      */       
/*  946 */       this.profiler.push("tick");
/*      */       try {
/*  948 */         $$1.tick($$0);
/*  949 */       } catch (Throwable $$2) {
/*  950 */         CrashReport $$3 = CrashReport.forThrowable($$2, "Exception ticking world");
/*  951 */         $$1.fillReportDetails($$3);
/*  952 */         throw new ReportedException($$3);
/*      */       } 
/*  954 */       this.profiler.pop();
/*  955 */       this.profiler.pop();
/*      */     } 
/*      */     
/*  958 */     this.profiler.popPush("connection");
/*  959 */     getConnection().tick();
/*  960 */     this.profiler.popPush("players");
/*  961 */     this.playerList.tick();
/*      */     
/*  963 */     if (SharedConstants.IS_RUNNING_IN_IDE && this.tickRateManager.runsNormally()) {
/*  964 */       GameTestTicker.SINGLETON.tick();
/*      */     }
/*  966 */     this.profiler.popPush("server gui refresh");
/*  967 */     for (int $$4 = 0; $$4 < this.tickables.size(); $$4++) {
/*  968 */       ((Runnable)this.tickables.get($$4)).run();
/*      */     }
/*  970 */     this.profiler.popPush("send chunks");
/*  971 */     for (ServerPlayer $$5 : this.playerList.getPlayers()) {
/*  972 */       $$5.connection.chunkSender.sendNextChunks($$5);
/*  973 */       $$5.connection.resumeFlushing();
/*      */     } 
/*  975 */     this.profiler.pop();
/*      */   }
/*      */   
/*      */   private void synchronizeTime(ServerLevel $$0) {
/*  979 */     this.playerList.broadcastAll((Packet)new ClientboundSetTimePacket($$0.getGameTime(), $$0.getDayTime(), $$0.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)), $$0.dimension());
/*      */   }
/*      */   
/*      */   public void forceTimeSynchronization() {
/*  983 */     this.profiler.push("timeSync");
/*  984 */     for (ServerLevel $$0 : getAllLevels()) {
/*  985 */       synchronizeTime($$0);
/*      */     }
/*  987 */     this.profiler.pop();
/*      */   }
/*      */   
/*      */   public boolean isNetherEnabled() {
/*  991 */     return true;
/*      */   }
/*      */   
/*      */   public void addTickable(Runnable $$0) {
/*  995 */     this.tickables.add($$0);
/*      */   }
/*      */   
/*      */   protected void setId(String $$0) {
/*  999 */     this.serverId = $$0;
/*      */   }
/*      */   
/*      */   public boolean isShutdown() {
/* 1003 */     return !this.serverThread.isAlive();
/*      */   }
/*      */   
/*      */   public File getFile(String $$0) {
/* 1007 */     return new File(getServerDirectory(), $$0);
/*      */   }
/*      */   
/*      */   public final ServerLevel overworld() {
/* 1011 */     return this.levels.get(Level.OVERWORLD);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ServerLevel getLevel(ResourceKey<Level> $$0) {
/* 1016 */     return this.levels.get($$0);
/*      */   }
/*      */   
/*      */   public Set<ResourceKey<Level>> levelKeys() {
/* 1020 */     return this.levels.keySet();
/*      */   }
/*      */   
/*      */   public Iterable<ServerLevel> getAllLevels() {
/* 1024 */     return this.levels.values();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getServerVersion() {
/* 1029 */     return SharedConstants.getCurrentVersion().getName();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getPlayerCount() {
/* 1034 */     return this.playerList.getPlayerCount();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxPlayers() {
/* 1039 */     return this.playerList.getMaxPlayers();
/*      */   }
/*      */   
/*      */   public String[] getPlayerNames() {
/* 1043 */     return this.playerList.getPlayerNamesArray();
/*      */   }
/*      */   
/*      */   @DontObfuscate
/*      */   public String getServerModName() {
/* 1048 */     return "vanilla";
/*      */   }
/*      */   
/*      */   public SystemReport fillSystemReport(SystemReport $$0) {
/* 1052 */     $$0.setDetail("Server Running", () -> Boolean.toString(this.running));
/*      */     
/* 1054 */     if (this.playerList != null) {
/* 1055 */       $$0.setDetail("Player Count", () -> "" + this.playerList.getPlayerCount() + " / " + this.playerList.getPlayerCount() + "; " + this.playerList.getMaxPlayers());
/*      */     }
/*      */     
/* 1058 */     $$0.setDetail("Data Packs", () -> (String)this.packRepository.getSelectedPacks().stream().map(()).collect(Collectors.joining(", ")));
/*      */ 
/*      */ 
/*      */     
/* 1062 */     $$0.setDetail("Enabled Feature Flags", () -> (String)FeatureFlags.REGISTRY.toNames(this.worldData.enabledFeatures()).stream().map(ResourceLocation::toString).collect(Collectors.joining(", ")));
/*      */ 
/*      */     
/* 1065 */     $$0.setDetail("World Generation", () -> this.worldData.worldGenSettingsLifecycle().toString());
/*      */     
/* 1067 */     if (this.serverId != null) {
/* 1068 */       $$0.setDetail("Server Id", () -> this.serverId);
/*      */     }
/*      */     
/* 1071 */     return fillServerSystemReport($$0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ModCheck getModdedStatus() {
/* 1077 */     return ModCheck.identify("vanilla", this::getServerModName, "Server", MinecraftServer.class);
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendSystemMessage(Component $$0) {
/* 1082 */     LOGGER.info($$0.getString());
/*      */   }
/*      */   
/*      */   public KeyPair getKeyPair() {
/* 1086 */     return this.keyPair;
/*      */   }
/*      */   
/*      */   public int getPort() {
/* 1090 */     return this.port;
/*      */   }
/*      */   
/*      */   public void setPort(int $$0) {
/* 1094 */     this.port = $$0;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public GameProfile getSingleplayerProfile() {
/* 1099 */     return this.singleplayerProfile;
/*      */   }
/*      */   
/*      */   public void setSingleplayerProfile(@Nullable GameProfile $$0) {
/* 1103 */     this.singleplayerProfile = $$0;
/*      */   }
/*      */   
/*      */   public boolean isSingleplayer() {
/* 1107 */     return (this.singleplayerProfile != null);
/*      */   }
/*      */   
/*      */   protected void initializeKeyPair() {
/* 1111 */     LOGGER.info("Generating keypair");
/*      */     try {
/* 1113 */       this.keyPair = Crypt.generateKeyPair();
/* 1114 */     } catch (CryptException $$0) {
/* 1115 */       throw new IllegalStateException("Failed to generate key pair", $$0);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setDifficulty(Difficulty $$0, boolean $$1) {
/* 1120 */     if (!$$1 && this.worldData.isDifficultyLocked()) {
/*      */       return;
/*      */     }
/*      */     
/* 1124 */     this.worldData.setDifficulty(this.worldData.isHardcore() ? Difficulty.HARD : $$0);
/*      */     
/* 1126 */     updateMobSpawningFlags();
/* 1127 */     getPlayerList().getPlayers().forEach(this::sendDifficultyUpdate);
/*      */   }
/*      */   
/*      */   public int getScaledTrackingDistance(int $$0) {
/* 1131 */     return $$0;
/*      */   }
/*      */   
/*      */   private void updateMobSpawningFlags() {
/* 1135 */     for (ServerLevel $$0 : getAllLevels()) {
/* 1136 */       $$0.setSpawnSettings(isSpawningMonsters(), isSpawningAnimals());
/*      */     }
/*      */   }
/*      */   
/*      */   public void setDifficultyLocked(boolean $$0) {
/* 1141 */     this.worldData.setDifficultyLocked($$0);
/* 1142 */     getPlayerList().getPlayers().forEach(this::sendDifficultyUpdate);
/*      */   }
/*      */   
/*      */   private void sendDifficultyUpdate(ServerPlayer $$0) {
/* 1146 */     LevelData $$1 = $$0.level().getLevelData();
/* 1147 */     $$0.connection.send((Packet)new ClientboundChangeDifficultyPacket($$1.getDifficulty(), $$1.isDifficultyLocked()));
/*      */   }
/*      */   
/*      */   public boolean isSpawningMonsters() {
/* 1151 */     return (this.worldData.getDifficulty() != Difficulty.PEACEFUL);
/*      */   }
/*      */   
/*      */   public boolean isDemo() {
/* 1155 */     return this.isDemo;
/*      */   }
/*      */   
/*      */   public void setDemo(boolean $$0) {
/* 1159 */     this.isDemo = $$0;
/*      */   }
/*      */   
/*      */   public Optional<ServerResourcePackInfo> getServerResourcePack() {
/* 1163 */     return Optional.empty();
/*      */   }
/*      */   
/*      */   public boolean isResourcePackRequired() {
/* 1167 */     return getServerResourcePack().filter(ServerResourcePackInfo::isRequired).isPresent();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean usesAuthentication() {
/* 1175 */     return this.onlineMode;
/*      */   }
/*      */   
/*      */   public void setUsesAuthentication(boolean $$0) {
/* 1179 */     this.onlineMode = $$0;
/*      */   }
/*      */   
/*      */   public boolean getPreventProxyConnections() {
/* 1183 */     return this.preventProxyConnections;
/*      */   }
/*      */   
/*      */   public void setPreventProxyConnections(boolean $$0) {
/* 1187 */     this.preventProxyConnections = $$0;
/*      */   }
/*      */   
/*      */   public boolean isSpawningAnimals() {
/* 1191 */     return true;
/*      */   }
/*      */   
/*      */   public boolean areNpcsEnabled() {
/* 1195 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPvpAllowed() {
/* 1201 */     return this.pvp;
/*      */   }
/*      */   
/*      */   public void setPvpAllowed(boolean $$0) {
/* 1205 */     this.pvp = $$0;
/*      */   }
/*      */   
/*      */   public boolean isFlightAllowed() {
/* 1209 */     return this.allowFlight;
/*      */   }
/*      */   
/*      */   public void setFlightAllowed(boolean $$0) {
/* 1213 */     this.allowFlight = $$0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMotd() {
/* 1220 */     return this.motd;
/*      */   }
/*      */   
/*      */   public void setMotd(String $$0) {
/* 1224 */     this.motd = $$0;
/*      */   }
/*      */   
/*      */   public boolean isStopped() {
/* 1228 */     return this.stopped;
/*      */   }
/*      */   
/*      */   public PlayerList getPlayerList() {
/* 1232 */     return this.playerList;
/*      */   }
/*      */   
/*      */   public void setPlayerList(PlayerList $$0) {
/* 1236 */     this.playerList = $$0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultGameType(GameType $$0) {
/* 1242 */     this.worldData.setGameType($$0);
/*      */   }
/*      */   
/*      */   public ServerConnectionListener getConnection() {
/* 1246 */     return this.connection;
/*      */   }
/*      */   
/*      */   public boolean isReady() {
/* 1250 */     return this.isReady;
/*      */   }
/*      */   
/*      */   public boolean hasGui() {
/* 1254 */     return false;
/*      */   }
/*      */   
/*      */   public boolean publishServer(@Nullable GameType $$0, boolean $$1, int $$2) {
/* 1258 */     return false;
/*      */   }
/*      */   
/*      */   public int getTickCount() {
/* 1262 */     return this.tickCount;
/*      */   }
/*      */   
/*      */   public int getSpawnProtectionRadius() {
/* 1266 */     return 16;
/*      */   }
/*      */   
/*      */   public boolean isUnderSpawnProtection(ServerLevel $$0, BlockPos $$1, Player $$2) {
/* 1270 */     return false;
/*      */   }
/*      */   
/*      */   public boolean repliesToStatus() {
/* 1274 */     return true;
/*      */   }
/*      */   
/*      */   public boolean hidesOnlinePlayers() {
/* 1278 */     return false;
/*      */   }
/*      */   
/*      */   public Proxy getProxy() {
/* 1282 */     return this.proxy;
/*      */   }
/*      */   
/*      */   public int getPlayerIdleTimeout() {
/* 1286 */     return this.playerIdleTimeout;
/*      */   }
/*      */   
/*      */   public void setPlayerIdleTimeout(int $$0) {
/* 1290 */     this.playerIdleTimeout = $$0;
/*      */   }
/*      */   
/*      */   public MinecraftSessionService getSessionService() {
/* 1294 */     return this.services.sessionService();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public SignatureValidator getProfileKeySignatureValidator() {
/* 1299 */     return this.services.profileKeySignatureValidator();
/*      */   }
/*      */   
/*      */   public GameProfileRepository getProfileRepository() {
/* 1303 */     return this.services.profileRepository();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public GameProfileCache getProfileCache() {
/* 1308 */     return this.services.profileCache();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ServerStatus getStatus() {
/* 1313 */     return this.status;
/*      */   }
/*      */   
/*      */   public void invalidateStatus() {
/* 1317 */     this.lastServerStatus = 0L;
/*      */   }
/*      */   
/*      */   public int getAbsoluteMaxWorldSize() {
/* 1321 */     return 29999984;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean scheduleExecutables() {
/* 1326 */     return (super.scheduleExecutables() && !isStopped());
/*      */   }
/*      */ 
/*      */   
/*      */   public void executeIfPossible(Runnable $$0) {
/* 1331 */     if (isStopped()) {
/* 1332 */       throw new RejectedExecutionException("Server already shutting down");
/*      */     }
/* 1334 */     super.executeIfPossible($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public Thread getRunningThread() {
/* 1339 */     return this.serverThread;
/*      */   }
/*      */   
/*      */   public int getCompressionThreshold() {
/* 1343 */     return 256;
/*      */   }
/*      */   
/*      */   public boolean enforceSecureProfile() {
/* 1347 */     return false;
/*      */   }
/*      */   
/*      */   public long getNextTickTime() {
/* 1351 */     return this.nextTickTimeNanos;
/*      */   }
/*      */   
/*      */   public DataFixer getFixerUpper() {
/* 1355 */     return this.fixerUpper;
/*      */   }
/*      */   
/*      */   public int getSpawnRadius(@Nullable ServerLevel $$0) {
/* 1359 */     if ($$0 != null) {
/* 1360 */       return $$0.getGameRules().getInt(GameRules.RULE_SPAWN_RADIUS);
/*      */     }
/* 1362 */     return 10;
/*      */   }
/*      */   
/*      */   public ServerAdvancementManager getAdvancements() {
/* 1366 */     return this.resources.managers.getAdvancements();
/*      */   }
/*      */   
/*      */   public ServerFunctionManager getFunctions() {
/* 1370 */     return this.functionManager;
/*      */   }
/*      */   
/*      */   public CompletableFuture<Void> reloadResources(Collection<String> $$0) {
/* 1374 */     RegistryAccess.Frozen $$1 = this.registries.getAccessForLoading(RegistryLayer.RELOADABLE);
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
/* 1387 */     CompletableFuture<Void> $$2 = CompletableFuture.supplyAsync(() -> { Objects.requireNonNull(this.packRepository); return (ImmutableList)$$0.stream().map(this.packRepository::getPack).filter(Objects::nonNull).map(Pack::open).collect(ImmutableList.toImmutableList()); }(Executor)this).thenCompose($$1 -> { MultiPackResourceManager multiPackResourceManager = new MultiPackResourceManager(PackType.SERVER_DATA, (List)$$1); return ReloadableServerResources.loadResources((ResourceManager)multiPackResourceManager, $$0, this.worldData.enabledFeatures(), isDedicatedServer() ? Commands.CommandSelection.DEDICATED : Commands.CommandSelection.INTEGRATED, getFunctionCompilationLevel(), this.executor, (Executor)this).whenComplete(()).thenApply(()); }).thenAcceptAsync($$1 -> {
/*      */           this.resources.close();
/*      */           
/*      */           this.resources = $$1;
/*      */           
/*      */           this.packRepository.setSelected($$0);
/*      */           
/*      */           WorldDataConfiguration $$2 = new WorldDataConfiguration(getSelectedPacks(this.packRepository), this.worldData.enabledFeatures());
/*      */           
/*      */           this.worldData.setDataConfiguration($$2);
/*      */           this.resources.managers.updateRegistryTags((RegistryAccess)registryAccess());
/*      */           getPlayerList().saveAll();
/*      */           getPlayerList().reloadResources();
/*      */           this.functionManager.replaceLibrary(this.resources.managers.getFunctionLibrary());
/*      */           this.structureTemplateManager.onResourceManagerReload((ResourceManager)this.resources.resourceManager);
/*      */         }(Executor)this);
/* 1403 */     if (isSameThread()) {
/* 1404 */       Objects.requireNonNull($$2); managedBlock($$2::isDone);
/*      */     } 
/* 1406 */     return $$2;
/*      */   }
/*      */   
/*      */   public static WorldDataConfiguration configurePackRepository(PackRepository $$0, DataPackConfig $$1, boolean $$2, FeatureFlagSet $$3) {
/* 1410 */     $$0.reload();
/* 1411 */     if ($$2) {
/* 1412 */       $$0.setSelected(Collections.singleton("vanilla"));
/* 1413 */       return WorldDataConfiguration.DEFAULT;
/*      */     } 
/*      */     
/* 1416 */     Set<String> $$4 = Sets.newLinkedHashSet();
/*      */     
/* 1418 */     for (String $$5 : $$1.getEnabled()) {
/* 1419 */       if ($$0.isAvailable($$5)) {
/* 1420 */         $$4.add($$5); continue;
/*      */       } 
/* 1422 */       LOGGER.warn("Missing data pack {}", $$5);
/*      */     } 
/*      */ 
/*      */     
/* 1426 */     for (Pack $$6 : $$0.getAvailablePacks()) {
/* 1427 */       String $$7 = $$6.getId();
/* 1428 */       if ($$1.getDisabled().contains($$7)) {
/*      */         continue;
/*      */       }
/* 1431 */       FeatureFlagSet $$8 = $$6.getRequestedFeatures();
/* 1432 */       boolean $$9 = $$4.contains($$7);
/* 1433 */       if (!$$9 && $$6.getPackSource().shouldAddAutomatically()) {
/* 1434 */         if ($$8.isSubsetOf($$3)) {
/* 1435 */           LOGGER.info("Found new data pack {}, loading it automatically", $$7);
/* 1436 */           $$4.add($$7);
/*      */         } else {
/* 1438 */           LOGGER.info("Found new data pack {}, but can't load it due to missing features {}", $$7, FeatureFlags.printMissingFlags($$3, $$8));
/*      */         } 
/*      */       }
/* 1441 */       if ($$9 && !$$8.isSubsetOf($$3)) {
/* 1442 */         LOGGER.warn("Pack {} requires features {} that are not enabled for this world, disabling pack.", $$7, FeatureFlags.printMissingFlags($$3, $$8));
/* 1443 */         $$4.remove($$7);
/*      */       } 
/*      */     } 
/*      */     
/* 1447 */     if ($$4.isEmpty()) {
/* 1448 */       LOGGER.info("No datapacks selected, forcing vanilla");
/* 1449 */       $$4.add("vanilla");
/*      */     } 
/*      */     
/* 1452 */     $$0.setSelected($$4);
/*      */ 
/*      */     
/* 1455 */     DataPackConfig $$10 = getSelectedPacks($$0);
/* 1456 */     FeatureFlagSet $$11 = $$0.getRequestedFeatureFlags();
/* 1457 */     return new WorldDataConfiguration($$10, $$11);
/*      */   }
/*      */   
/*      */   private static DataPackConfig getSelectedPacks(PackRepository $$0) {
/* 1461 */     Collection<String> $$1 = $$0.getSelectedIds();
/* 1462 */     ImmutableList immutableList = ImmutableList.copyOf($$1);
/* 1463 */     List<String> $$3 = (List<String>)$$0.getAvailableIds().stream().filter($$1 -> !$$0.contains($$1)).collect(ImmutableList.toImmutableList());
/* 1464 */     return new DataPackConfig((List)immutableList, $$3);
/*      */   }
/*      */   
/*      */   public void kickUnlistedPlayers(CommandSourceStack $$0) {
/* 1468 */     if (!isEnforceWhitelist()) {
/*      */       return;
/*      */     }
/*      */     
/* 1472 */     PlayerList $$1 = $$0.getServer().getPlayerList();
/* 1473 */     UserWhiteList $$2 = $$1.getWhiteList();
/*      */     
/* 1475 */     List<ServerPlayer> $$3 = Lists.newArrayList($$1.getPlayers());
/* 1476 */     for (ServerPlayer $$4 : $$3) {
/* 1477 */       if (!$$2.isWhiteListed($$4.getGameProfile())) {
/* 1478 */         $$4.connection.disconnect((Component)Component.translatable("multiplayer.disconnect.not_whitelisted"));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public PackRepository getPackRepository() {
/* 1484 */     return this.packRepository;
/*      */   }
/*      */   
/*      */   public Commands getCommands() {
/* 1488 */     return this.resources.managers.getCommands();
/*      */   }
/*      */   
/*      */   public CommandSourceStack createCommandSourceStack() {
/* 1492 */     ServerLevel $$0 = overworld();
/* 1493 */     return new CommandSourceStack(this, ($$0 == null) ? Vec3.ZERO : Vec3.atLowerCornerOf((Vec3i)$$0.getSharedSpawnPos()), Vec2.ZERO, $$0, 4, "Server", (Component)Component.literal("Server"), this, null);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean acceptsSuccess() {
/* 1498 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean acceptsFailure() {
/* 1503 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RecipeManager getRecipeManager() {
/* 1510 */     return this.resources.managers.getRecipeManager();
/*      */   }
/*      */   
/*      */   public ServerScoreboard getScoreboard() {
/* 1514 */     return this.scoreboard;
/*      */   }
/*      */   
/*      */   public CommandStorage getCommandStorage() {
/* 1518 */     if (this.commandStorage == null)
/*      */     {
/* 1520 */       throw new NullPointerException("Called before server init");
/*      */     }
/* 1522 */     return this.commandStorage;
/*      */   }
/*      */   
/*      */   public LootDataManager getLootData() {
/* 1526 */     return this.resources.managers.getLootData();
/*      */   }
/*      */   
/*      */   public GameRules getGameRules() {
/* 1530 */     return overworld().getGameRules();
/*      */   }
/*      */   
/*      */   public CustomBossEvents getCustomBossEvents() {
/* 1534 */     return this.customBossEvents;
/*      */   }
/*      */   
/*      */   public boolean isEnforceWhitelist() {
/* 1538 */     return this.enforceWhitelist;
/*      */   }
/*      */   
/*      */   public void setEnforceWhitelist(boolean $$0) {
/* 1542 */     this.enforceWhitelist = $$0;
/*      */   }
/*      */   
/*      */   public float getCurrentSmoothedTickTime() {
/* 1546 */     return this.smoothedTickTimeMillis;
/*      */   }
/*      */   
/*      */   public ServerTickRateManager tickRateManager() {
/* 1550 */     return this.tickRateManager;
/*      */   }
/*      */   
/*      */   public long getAverageTickTimeNanos() {
/* 1554 */     return this.aggregatedTickTimesNanos / Math.min(100, Math.max(this.tickCount, 1));
/*      */   }
/*      */   
/*      */   public long[] getTickTimesNanos() {
/* 1558 */     return this.tickTimesNanos;
/*      */   }
/*      */   
/*      */   public int getProfilePermissions(GameProfile $$0) {
/* 1562 */     if (getPlayerList().isOp($$0)) {
/* 1563 */       ServerOpListEntry $$1 = (ServerOpListEntry)getPlayerList().getOps().get($$0);
/* 1564 */       if ($$1 != null) {
/* 1565 */         return $$1.getLevel();
/*      */       }
/* 1567 */       if (isSingleplayerOwner($$0)) {
/* 1568 */         return 4;
/*      */       }
/* 1570 */       if (isSingleplayer()) {
/* 1571 */         return getPlayerList().isAllowCheatsForAllPlayers() ? 4 : 0;
/*      */       }
/* 1573 */       return getOperatorUserPermissionLevel();
/*      */     } 
/* 1575 */     return 0;
/*      */   }
/*      */   
/*      */   public ProfilerFiller getProfiler() {
/* 1579 */     return this.profiler;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dumpServerProperties(Path $$0) throws IOException {}
/*      */ 
/*      */   
/*      */   private void saveDebugReport(Path $$0) {
/* 1588 */     Path $$1 = $$0.resolve("levels");
/*      */     
/*      */     try {
/* 1591 */       for (Map.Entry<ResourceKey<Level>, ServerLevel> $$2 : this.levels.entrySet()) {
/* 1592 */         ResourceLocation $$3 = ((ResourceKey)$$2.getKey()).location();
/* 1593 */         Path $$4 = $$1.resolve($$3.getNamespace()).resolve($$3.getPath());
/* 1594 */         Files.createDirectories($$4, (FileAttribute<?>[])new FileAttribute[0]);
/* 1595 */         ((ServerLevel)$$2.getValue()).saveDebugReport($$4);
/*      */       } 
/*      */       
/* 1598 */       dumpGameRules($$0.resolve("gamerules.txt"));
/* 1599 */       dumpClasspath($$0.resolve("classpath.txt"));
/* 1600 */       dumpMiscStats($$0.resolve("stats.txt"));
/* 1601 */       dumpThreads($$0.resolve("threads.txt"));
/* 1602 */       dumpServerProperties($$0.resolve("server.properties.txt"));
/* 1603 */       dumpNativeModules($$0.resolve("modules.txt"));
/* 1604 */     } catch (IOException $$5) {
/* 1605 */       LOGGER.warn("Failed to save debug report", $$5);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void dumpMiscStats(Path $$0) throws IOException {
/* 1610 */     Writer $$1 = Files.newBufferedWriter($$0, new java.nio.file.OpenOption[0]); 
/* 1611 */     try { $$1.write(String.format(Locale.ROOT, "pending_tasks: %d\n", new Object[] { Integer.valueOf(getPendingTasksCount()) }));
/* 1612 */       $$1.write(String.format(Locale.ROOT, "average_tick_time: %f\n", new Object[] { Float.valueOf(getCurrentSmoothedTickTime()) }));
/* 1613 */       $$1.write(String.format(Locale.ROOT, "tick_times: %s\n", new Object[] { Arrays.toString(this.tickTimesNanos) }));
/* 1614 */       $$1.write(String.format(Locale.ROOT, "queue: %s\n", new Object[] { Util.backgroundExecutor() }));
/* 1615 */       if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null)
/*      */         try { $$1.close(); }
/*      */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */           throw throwable; }
/* 1619 */      } private void dumpGameRules(Path $$0) throws IOException { Writer $$1 = Files.newBufferedWriter($$0, new java.nio.file.OpenOption[0]); 
/* 1620 */     try { final List<String> entries = Lists.newArrayList();
/* 1621 */       final GameRules gameRules = getGameRules();
/* 1622 */       GameRules.visitGameRuleTypes(new GameRules.GameRuleTypeVisitor()
/*      */           {
/*      */             public <T extends GameRules.Value<T>> void visit(GameRules.Key<T> $$0, GameRules.Type<T> $$1) {
/* 1625 */               entries.add(String.format(Locale.ROOT, "%s=%s\n", new Object[] { $$0.getId(), this.val$gameRules.getRule($$0) }));
/*      */             }
/*      */           });
/* 1628 */       for (String $$4 : $$2) {
/* 1629 */         $$1.write($$4);
/*      */       }
/* 1631 */       if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null)
/*      */         try { $$1.close(); }
/*      */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */           throw throwable; }
/* 1635 */      } private void dumpClasspath(Path $$0) throws IOException { Writer $$1 = Files.newBufferedWriter($$0, new java.nio.file.OpenOption[0]); 
/* 1636 */     try { String $$2 = System.getProperty("java.class.path");
/* 1637 */       String $$3 = System.getProperty("path.separator");
/* 1638 */       for (String $$4 : Splitter.on($$3).split($$2)) {
/* 1639 */         $$1.write($$4);
/* 1640 */         $$1.write("\n");
/*      */       } 
/* 1642 */       if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null)
/*      */         try { $$1.close(); }
/*      */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */           throw throwable; }
/* 1646 */      } private void dumpThreads(Path $$0) throws IOException { ThreadMXBean $$1 = ManagementFactory.getThreadMXBean();
/* 1647 */     ThreadInfo[] $$2 = $$1.dumpAllThreads(true, true);
/* 1648 */     Arrays.sort($$2, Comparator.comparing(ThreadInfo::getThreadName));
/*      */     
/* 1650 */     Writer $$3 = Files.newBufferedWriter($$0, new java.nio.file.OpenOption[0]); 
/* 1651 */     try { for (ThreadInfo $$4 : $$2) {
/* 1652 */         $$3.write($$4.toString());
/* 1653 */         $$3.write(10);
/*      */       } 
/* 1655 */       if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null)
/*      */         try { $$3.close(); }
/*      */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */           throw throwable; }
/* 1659 */      } private void dumpNativeModules(Path $$0) throws IOException { Writer $$1 = Files.newBufferedWriter($$0, new java.nio.file.OpenOption[0]); 
/*      */     try { List<NativeModuleLister.NativeModuleInfo> $$2;
/*      */       
/* 1662 */       try { $$2 = Lists.newArrayList(NativeModuleLister.listModules()); }
/* 1663 */       catch (Throwable $$3)
/* 1664 */       { LOGGER.warn("Failed to list native modules", $$3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1672 */         if ($$1 != null) $$1.close();  return; }  $$2.sort(Comparator.comparing($$0 -> $$0.name)); for (NativeModuleLister.NativeModuleInfo $$5 : $$2) { $$1.write($$5.toString()); $$1.write(10); }  if ($$1 != null) $$1.close();  } catch (Throwable $$4) { if ($$1 != null)
/*      */         try { $$1.close(); }
/*      */         catch (Throwable throwable) { $$4.addSuppressed(throwable); }
/*      */           throw $$4; }
/* 1676 */      } private void startMetricsRecordingTick() { if (this.willStartRecordingMetrics) {
/* 1677 */       this.metricsRecorder = (MetricsRecorder)ActiveMetricsRecorder.createStarted((MetricsSamplerProvider)new ServerMetricsSamplersProvider((LongSupplier)Util.timeSource, 
/* 1678 */             isDedicatedServer()), (LongSupplier)Util.timeSource, 
/*      */           
/* 1680 */           Util.ioPool(), new MetricsPersister("server"), this.onMetricsRecordingStopped, $$0 -> {
/*      */             executeBlocking(());
/*      */ 
/*      */ 
/*      */             
/*      */             this.onMetricsRecordingFinished.accept($$0);
/*      */           });
/*      */ 
/*      */ 
/*      */       
/* 1690 */       this.willStartRecordingMetrics = false;
/*      */     } 
/*      */     
/* 1693 */     this.profiler = SingleTickProfiler.decorateFiller(this.metricsRecorder.getProfiler(), SingleTickProfiler.createTickProfiler("Server"));
/*      */     
/* 1695 */     this.metricsRecorder.startTick();
/* 1696 */     this.profiler.startTick(); }
/*      */ 
/*      */   
/*      */   private void endMetricsRecordingTick() {
/* 1700 */     this.profiler.endTick();
/* 1701 */     this.metricsRecorder.endTick();
/*      */   }
/*      */   
/*      */   public boolean isRecordingMetrics() {
/* 1705 */     return this.metricsRecorder.isRecording();
/*      */   }
/*      */   
/*      */   public void startRecordingMetrics(Consumer<ProfileResults> $$0, Consumer<Path> $$1) {
/* 1709 */     this.onMetricsRecordingStopped = ($$1 -> {
/*      */         stopRecordingMetrics();
/*      */         $$0.accept($$1);
/*      */       });
/* 1713 */     this.onMetricsRecordingFinished = $$1;
/* 1714 */     this.willStartRecordingMetrics = true;
/*      */   }
/*      */   
/*      */   public void stopRecordingMetrics() {
/* 1718 */     this.metricsRecorder = InactiveMetricsRecorder.INSTANCE;
/*      */   }
/*      */   
/*      */   public void finishRecordingMetrics() {
/* 1722 */     this.metricsRecorder.end();
/*      */   }
/*      */   
/*      */   public void cancelRecordingMetrics() {
/* 1726 */     this.metricsRecorder.cancel();
/* 1727 */     this.profiler = this.metricsRecorder.getProfiler();
/*      */   }
/*      */   
/*      */   public Path getWorldPath(LevelResource $$0) {
/* 1731 */     return this.storageSource.getLevelPath($$0);
/*      */   }
/*      */   
/*      */   public boolean forceSynchronousWrites() {
/* 1735 */     return true;
/*      */   }
/*      */   
/*      */   public StructureTemplateManager getStructureManager() {
/* 1739 */     return this.structureTemplateManager;
/*      */   }
/*      */   
/*      */   public WorldData getWorldData() {
/* 1743 */     return this.worldData;
/*      */   }
/*      */   
/*      */   public RegistryAccess.Frozen registryAccess() {
/* 1747 */     return this.registries.compositeAccess();
/*      */   }
/*      */   
/*      */   public LayeredRegistryAccess<RegistryLayer> registries() {
/* 1751 */     return this.registries;
/*      */   }
/*      */   
/*      */   public TextFilter createTextFilterForPlayer(ServerPlayer $$0) {
/* 1755 */     return TextFilter.DUMMY;
/*      */   }
/*      */   
/*      */   public ServerPlayerGameMode createGameModeForPlayer(ServerPlayer $$0) {
/* 1759 */     return isDemo() ? (ServerPlayerGameMode)new DemoMode($$0) : new ServerPlayerGameMode($$0);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public GameType getForcedGameType() {
/* 1764 */     return null;
/*      */   }
/*      */   
/*      */   public ResourceManager getResourceManager() {
/* 1768 */     return (ResourceManager)this.resources.resourceManager;
/*      */   }
/*      */   
/*      */   public boolean isCurrentlySaving() {
/* 1772 */     return this.isSaving;
/*      */   }
/*      */   
/*      */   public boolean isTimeProfilerRunning() {
/* 1776 */     return (this.debugCommandProfilerDelayStart || this.debugCommandProfiler != null);
/*      */   }
/*      */   
/*      */   public void startTimeProfiler() {
/* 1780 */     this.debugCommandProfilerDelayStart = true;
/*      */   }
/*      */   
/*      */   public ProfileResults stopTimeProfiler() {
/* 1784 */     if (this.debugCommandProfiler == null) {
/* 1785 */       return (ProfileResults)EmptyProfileResults.EMPTY;
/*      */     }
/* 1787 */     ProfileResults $$0 = this.debugCommandProfiler.stop(Util.getNanos(), this.tickCount);
/* 1788 */     this.debugCommandProfiler = null;
/* 1789 */     return $$0;
/*      */   }
/*      */   
/*      */   public int getMaxChainedNeighborUpdates() {
/* 1793 */     return 1000000;
/*      */   }
/*      */   
/*      */   public void logChatMessage(Component $$0, ChatType.Bound $$1, @Nullable String $$2) {
/* 1797 */     String $$3 = $$1.decorate($$0).getString();
/* 1798 */     if ($$2 != null) {
/* 1799 */       LOGGER.info("[{}] {}", $$2, $$3);
/*      */     } else {
/* 1801 */       LOGGER.info("{}", $$3);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ChatDecorator getChatDecorator() {
/* 1807 */     return ChatDecorator.PLAIN;
/*      */   } protected abstract boolean initServer() throws IOException; public abstract int getOperatorUserPermissionLevel(); public abstract int getFunctionCompilationLevel(); public abstract boolean shouldRconBroadcast(); public abstract SystemReport fillServerSystemReport(SystemReport paramSystemReport);
/*      */   public abstract boolean isDedicatedServer();
/*      */   public boolean logIPs() {
/* 1811 */     return true;
/*      */   } public abstract int getRateLimitPacketsPerSecond(); public abstract boolean isEpollEnabled(); public abstract boolean isCommandBlockEnabled(); public abstract boolean isPublished(); public abstract boolean shouldInformAdmins(); public abstract boolean isSingleplayerOwner(GameProfile paramGameProfile); public static final class ServerResourcePackInfo extends Record { private final UUID id; private final String url; private final String hash; private final boolean isRequired; @Nullable
/*      */     private final Component prompt; public ServerResourcePackInfo(UUID $$0, String $$1, String $$2, boolean $$3, @Nullable Component $$4) {
/* 1814 */       this.id = $$0; this.url = $$1; this.hash = $$2; this.isRequired = $$3; this.prompt = $$4; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/MinecraftServer$ServerResourcePackInfo;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1814	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/server/MinecraftServer$ServerResourcePackInfo; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/MinecraftServer$ServerResourcePackInfo;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1814	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/server/MinecraftServer$ServerResourcePackInfo; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/MinecraftServer$ServerResourcePackInfo;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1814	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/server/MinecraftServer$ServerResourcePackInfo;
/* 1814 */       //   0	8	1	$$0	Ljava/lang/Object; } public UUID id() { return this.id; } public String url() { return this.url; } public String hash() { return this.hash; } public boolean isRequired() { return this.isRequired; } @Nullable public Component prompt() { return this.prompt; }
/*      */      } private static final class ReloadableResources extends Record implements AutoCloseable { final CloseableResourceManager resourceManager; final ReloadableServerResources managers;
/* 1816 */     ReloadableResources(CloseableResourceManager $$0, ReloadableServerResources $$1) { this.resourceManager = $$0; this.managers = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/MinecraftServer$ReloadableResources;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1816	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/server/MinecraftServer$ReloadableResources; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/MinecraftServer$ReloadableResources;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1816	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/server/MinecraftServer$ReloadableResources; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/MinecraftServer$ReloadableResources;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1816	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/server/MinecraftServer$ReloadableResources;
/* 1816 */       //   0	8	1	$$0	Ljava/lang/Object; } public CloseableResourceManager resourceManager() { return this.resourceManager; } public ReloadableServerResources managers() { return this.managers; }
/*      */     
/*      */     public void close() {
/* 1819 */       this.resourceManager.close();
/*      */     } }
/*      */ 
/*      */   
/*      */   private static class TimeProfiler {
/*      */     final long startNanos;
/*      */     final int startTick;
/*      */     
/*      */     TimeProfiler(long $$0, int $$1) {
/* 1828 */       this.startNanos = $$0;
/* 1829 */       this.startTick = $$1;
/*      */     }
/*      */     
/*      */     ProfileResults stop(final long stopNanos, final int stopTick) {
/* 1833 */       return new ProfileResults()
/*      */         {
/*      */           public List<ResultField> getTimes(String $$0) {
/* 1836 */             return Collections.emptyList();
/*      */           }
/*      */ 
/*      */           
/*      */           public boolean saveResults(Path $$0) {
/* 1841 */             return false;
/*      */           }
/*      */ 
/*      */           
/*      */           public long getStartTimeNano() {
/* 1846 */             return MinecraftServer.TimeProfiler.this.startNanos;
/*      */           }
/*      */ 
/*      */           
/*      */           public int getStartTimeTicks() {
/* 1851 */             return MinecraftServer.TimeProfiler.this.startTick;
/*      */           }
/*      */ 
/*      */           
/*      */           public long getEndTimeNano() {
/* 1856 */             return stopNanos;
/*      */           }
/*      */ 
/*      */           
/*      */           public int getEndTimeTicks() {
/* 1861 */             return stopTick;
/*      */           }
/*      */           
/*      */           public String getProfilerResults()
/*      */           {
/* 1866 */             return ""; } }; } } class null implements ProfileResults { public List<ResultField> getTimes(String $$0) { return Collections.emptyList(); } public boolean saveResults(Path $$0) { return false; } public long getStartTimeNano() { return MinecraftServer.TimeProfiler.this.startNanos; } public int getStartTimeTicks() { return MinecraftServer.TimeProfiler.this.startTick; } public long getEndTimeNano() { return stopNanos; } public int getEndTimeTicks() { return stopTick; } public String getProfilerResults() { return ""; }
/*      */      }
/*      */ 
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\MinecraftServer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */