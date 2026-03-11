/*      */ package net.minecraft.server.level;
/*      */ import com.google.common.annotations.VisibleForTesting;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.mojang.datafixers.DataFixer;
/*      */ import com.mojang.datafixers.util.Pair;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*      */ import it.unimi.dsi.fastutil.longs.LongSet;
/*      */ import it.unimi.dsi.fastutil.longs.LongSets;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*      */ import java.io.IOException;
/*      */ import java.io.Writer;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.function.BooleanSupplier;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.Stream;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.CrashReport;
/*      */ import net.minecraft.CrashReportCategory;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.HolderSet;
/*      */ import net.minecraft.core.Position;
/*      */ import net.minecraft.core.RegistryAccess;
/*      */ import net.minecraft.core.SectionPos;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.registries.BuiltInRegistries;
/*      */ import net.minecraft.core.registries.Registries;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundDamageEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundExplodePacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
/*      */ import net.minecraft.network.protocol.game.ClientboundSoundPacket;
/*      */ import net.minecraft.network.protocol.game.DebugPackets;
/*      */ import net.minecraft.resources.ResourceKey;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.ServerScoreboard;
/*      */ import net.minecraft.server.level.progress.ChunkProgressListener;
/*      */ import net.minecraft.server.players.SleepStatus;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.sounds.SoundSource;
/*      */ import net.minecraft.tags.TagKey;
/*      */ import net.minecraft.util.AbortableIterationConsumer;
/*      */ import net.minecraft.util.CsvOutput;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.ProgressListener;
/*      */ import net.minecraft.util.Unit;
/*      */ import net.minecraft.util.profiling.ProfilerFiller;
/*      */ import net.minecraft.util.valueproviders.IntProvider;
/*      */ import net.minecraft.util.valueproviders.UniformInt;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.RandomSequences;
/*      */ import net.minecraft.world.TickRateManager;
/*      */ import net.minecraft.world.damagesource.DamageSource;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.entity.LightningBolt;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.Mob;
/*      */ import net.minecraft.world.entity.MobCategory;
/*      */ import net.minecraft.world.entity.ReputationEventHandler;
/*      */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*      */ import net.minecraft.world.entity.ai.village.ReputationEventType;
/*      */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*      */ import net.minecraft.world.entity.ai.village.poi.PoiType;
/*      */ import net.minecraft.world.entity.ai.village.poi.PoiTypes;
/*      */ import net.minecraft.world.entity.animal.horse.SkeletonHorse;
/*      */ import net.minecraft.world.entity.boss.EnderDragonPart;
/*      */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.entity.raid.Raid;
/*      */ import net.minecraft.world.entity.raid.Raids;
/*      */ import net.minecraft.world.item.crafting.RecipeManager;
/*      */ import net.minecraft.world.level.BlockEventData;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.ChunkPos;
/*      */ import net.minecraft.world.level.CustomSpawner;
/*      */ import net.minecraft.world.level.Explosion;
/*      */ import net.minecraft.world.level.ExplosionDamageCalculator;
/*      */ import net.minecraft.world.level.ForcedChunksSavedData;
/*      */ import net.minecraft.world.level.GameRules;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelAccessor;
/*      */ import net.minecraft.world.level.LevelHeightAccessor;
/*      */ import net.minecraft.world.level.LevelReader;
/*      */ import net.minecraft.world.level.NaturalSpawner;
/*      */ import net.minecraft.world.level.StructureManager;
/*      */ import net.minecraft.world.level.biome.Biome;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.SnowLayerBlock;
/*      */ import net.minecraft.world.level.block.entity.TickingBlockEntity;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.level.chunk.ChunkAccess;
/*      */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*      */ import net.minecraft.world.level.chunk.ChunkSource;
/*      */ import net.minecraft.world.level.chunk.LevelChunk;
/*      */ import net.minecraft.world.level.chunk.LevelChunkSection;
/*      */ import net.minecraft.world.level.chunk.storage.EntityStorage;
/*      */ import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
/*      */ import net.minecraft.world.level.dimension.LevelStem;
/*      */ import net.minecraft.world.level.dimension.end.EndDragonFight;
/*      */ import net.minecraft.world.level.entity.EntityAccess;
/*      */ import net.minecraft.world.level.entity.EntityPersistentStorage;
/*      */ import net.minecraft.world.level.entity.EntityTickList;
/*      */ import net.minecraft.world.level.entity.EntityTypeTest;
/*      */ import net.minecraft.world.level.entity.LevelCallback;
/*      */ import net.minecraft.world.level.entity.LevelEntityGetter;
/*      */ import net.minecraft.world.level.entity.PersistentEntitySectionManager;
/*      */ import net.minecraft.world.level.gameevent.DynamicGameEventListener;
/*      */ import net.minecraft.world.level.gameevent.GameEvent;
/*      */ import net.minecraft.world.level.gameevent.GameEventDispatcher;
/*      */ import net.minecraft.world.level.levelgen.Heightmap;
/*      */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*      */ import net.minecraft.world.level.levelgen.structure.Structure;
/*      */ import net.minecraft.world.level.levelgen.structure.StructureCheck;
/*      */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*      */ import net.minecraft.world.level.material.Fluid;
/*      */ import net.minecraft.world.level.material.FluidState;
/*      */ import net.minecraft.world.level.portal.PortalForcer;
/*      */ import net.minecraft.world.level.saveddata.SavedData;
/*      */ import net.minecraft.world.level.saveddata.maps.MapIndex;
/*      */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*      */ import net.minecraft.world.level.storage.DimensionDataStorage;
/*      */ import net.minecraft.world.level.storage.LevelStorageSource;
/*      */ import net.minecraft.world.level.storage.ServerLevelData;
/*      */ import net.minecraft.world.level.storage.WritableLevelData;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraft.world.phys.shapes.BooleanOp;
/*      */ import net.minecraft.world.phys.shapes.Shapes;
/*      */ import net.minecraft.world.phys.shapes.VoxelShape;
/*      */ import net.minecraft.world.scores.Scoreboard;
/*      */ import net.minecraft.world.ticks.LevelTickAccess;
/*      */ import net.minecraft.world.ticks.LevelTicks;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public class ServerLevel extends Level implements WorldGenLevel {
/*  166 */   public static final BlockPos END_SPAWN_POINT = new BlockPos(100, 50, 0);
/*      */   
/*  168 */   public static final IntProvider RAIN_DELAY = (IntProvider)UniformInt.of(12000, 180000);
/*  169 */   public static final IntProvider RAIN_DURATION = (IntProvider)UniformInt.of(12000, 24000);
/*      */ 
/*      */   
/*  172 */   private static final IntProvider THUNDER_DELAY = (IntProvider)UniformInt.of(12000, 180000);
/*  173 */   public static final IntProvider THUNDER_DURATION = (IntProvider)UniformInt.of(3600, 15600);
/*      */   
/*  175 */   private static final Logger LOGGER = LogUtils.getLogger();
/*      */   
/*      */   private static final int EMPTY_TIME_NO_TICK = 300;
/*      */   
/*      */   private static final int MAX_SCHEDULED_TICKS_PER_TICK = 65536;
/*  180 */   final List<ServerPlayer> players = Lists.newArrayList();
/*      */   
/*      */   private final ServerChunkCache chunkSource;
/*      */   private final MinecraftServer server;
/*      */   private final ServerLevelData serverLevelData;
/*  185 */   final EntityTickList entityTickList = new EntityTickList();
/*      */   
/*      */   private final PersistentEntitySectionManager<Entity> entityManager;
/*      */   
/*      */   private final GameEventDispatcher gameEventDispatcher;
/*      */   public boolean noSave;
/*      */   private final SleepStatus sleepStatus;
/*      */   private int emptyTime;
/*      */   private final PortalForcer portalForcer;
/*  194 */   private final LevelTicks<Block> blockTicks = new LevelTicks(this::isPositionTickingWithEntitiesLoaded, getProfilerSupplier());
/*  195 */   private final LevelTicks<Fluid> fluidTicks = new LevelTicks(this::isPositionTickingWithEntitiesLoaded, getProfilerSupplier());
/*      */   
/*  197 */   final Set<Mob> navigatingMobs = (Set<Mob>)new ObjectOpenHashSet();
/*      */   
/*      */   volatile boolean isUpdatingNavigations;
/*      */   
/*      */   protected final Raids raids;
/*  202 */   private final ObjectLinkedOpenHashSet<BlockEventData> blockEvents = new ObjectLinkedOpenHashSet();
/*  203 */   private final List<BlockEventData> blockEventsToReschedule = new ArrayList<>(64);
/*      */   
/*      */   private boolean handlingTick;
/*      */   
/*      */   private final List<CustomSpawner> customSpawners;
/*      */   
/*      */   @Nullable
/*      */   private EndDragonFight dragonFight;
/*  211 */   final Int2ObjectMap<EnderDragonPart> dragonParts = (Int2ObjectMap<EnderDragonPart>)new Int2ObjectOpenHashMap();
/*      */   
/*      */   private final StructureManager structureManager;
/*      */   
/*      */   private final StructureCheck structureCheck;
/*      */   private final boolean tickTime;
/*      */   private final RandomSequences randomSequences;
/*      */   
/*      */   public ServerLevel(MinecraftServer $$0, Executor $$1, LevelStorageSource.LevelStorageAccess $$2, ServerLevelData $$3, ResourceKey<Level> $$4, LevelStem $$5, ChunkProgressListener $$6, boolean $$7, long $$8, List<CustomSpawner> $$9, boolean $$10, @Nullable RandomSequences $$11) {
/*  220 */     super((WritableLevelData)$$3, $$4, (RegistryAccess)$$0.registryAccess(), $$5.type(), $$0::getProfiler, false, $$7, $$8, $$0.getMaxChainedNeighborUpdates());
/*  221 */     this.tickTime = $$10;
/*  222 */     this.server = $$0;
/*  223 */     this.customSpawners = $$9;
/*  224 */     this.serverLevelData = $$3;
/*      */     
/*  226 */     ChunkGenerator $$12 = $$5.generator();
/*      */     
/*  228 */     boolean $$13 = $$0.forceSynchronousWrites();
/*  229 */     DataFixer $$14 = $$0.getFixerUpper();
/*  230 */     EntityStorage entityStorage = new EntityStorage(this, $$2.getDimensionPath($$4).resolve("entities"), $$14, $$13, (Executor)$$0);
/*  231 */     this.entityManager = new PersistentEntitySectionManager(Entity.class, new EntityCallbacks(), (EntityPersistentStorage)entityStorage);
/*      */     
/*  233 */     Objects.requireNonNull(this.entityManager); this.chunkSource = new ServerChunkCache(this, $$2, $$14, $$0.getStructureManager(), $$1, $$12, $$0.getPlayerList().getViewDistance(), $$0.getPlayerList().getSimulationDistance(), $$13, $$6, this.entityManager::updateChunkStatus, () -> $$0.overworld().getDataStorage());
/*  234 */     this.chunkSource.getGeneratorState().ensureStructuresGenerated();
/*      */     
/*  236 */     this.portalForcer = new PortalForcer(this);
/*      */     
/*  238 */     updateSkyBrightness();
/*  239 */     prepareWeather();
/*      */     
/*  241 */     getWorldBorder().setAbsoluteMaxSize($$0.getAbsoluteMaxWorldSize());
/*      */     
/*  243 */     this.raids = (Raids)getDataStorage().computeIfAbsent(Raids.factory(this), Raids.getFileId(dimensionTypeRegistration()));
/*      */     
/*  245 */     if (!$$0.isSingleplayer()) {
/*  246 */       $$3.setGameType($$0.getDefaultGameType());
/*      */     }
/*      */     
/*  249 */     long $$16 = $$0.getWorldData().worldGenOptions().seed();
/*      */     
/*  251 */     this.structureCheck = new StructureCheck(this.chunkSource.chunkScanner(), registryAccess(), $$0.getStructureManager(), $$4, $$12, this.chunkSource.randomState(), (LevelHeightAccessor)this, $$12.getBiomeSource(), $$16, $$14);
/*  252 */     this.structureManager = new StructureManager((LevelAccessor)this, $$0.getWorldData().worldGenOptions(), this.structureCheck);
/*      */ 
/*      */ 
/*      */     
/*  256 */     if (dimension() == Level.END && dimensionTypeRegistration().is(BuiltinDimensionTypes.END)) {
/*  257 */       this.dragonFight = new EndDragonFight(this, $$16, $$0.getWorldData().endDragonFightData());
/*      */     } else {
/*  259 */       this.dragonFight = null;
/*      */     } 
/*      */     
/*  262 */     this.sleepStatus = new SleepStatus();
/*  263 */     this.gameEventDispatcher = new GameEventDispatcher(this);
/*  264 */     this.randomSequences = Objects.<RandomSequences>requireNonNullElseGet($$11, () -> (RandomSequences)getDataStorage().computeIfAbsent(RandomSequences.factory($$0), "random_sequences"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @VisibleForTesting
/*      */   public void setDragonFight(@Nullable EndDragonFight $$0) {
/*  273 */     this.dragonFight = $$0;
/*      */   }
/*      */   
/*      */   public void setWeatherParameters(int $$0, int $$1, boolean $$2, boolean $$3) {
/*  277 */     this.serverLevelData.setClearWeatherTime($$0);
/*  278 */     this.serverLevelData.setRainTime($$1);
/*  279 */     this.serverLevelData.setThunderTime($$1);
/*  280 */     this.serverLevelData.setRaining($$2);
/*  281 */     this.serverLevelData.setThundering($$3);
/*      */   }
/*      */ 
/*      */   
/*      */   public Holder<Biome> getUncachedNoiseBiome(int $$0, int $$1, int $$2) {
/*  286 */     return getChunkSource().getGenerator().getBiomeSource().getNoiseBiome($$0, $$1, $$2, getChunkSource().randomState().sampler());
/*      */   }
/*      */   
/*      */   public StructureManager structureManager() {
/*  290 */     return this.structureManager;
/*      */   }
/*      */   
/*      */   public void tick(BooleanSupplier $$0) {
/*  294 */     ProfilerFiller $$1 = getProfiler();
/*      */     
/*  296 */     this.handlingTick = true;
/*  297 */     TickRateManager $$2 = tickRateManager();
/*  298 */     boolean $$3 = $$2.runsNormally();
/*  299 */     if ($$3) {
/*  300 */       $$1.push("world border");
/*  301 */       getWorldBorder().tick();
/*  302 */       $$1.popPush("weather");
/*  303 */       advanceWeatherCycle();
/*      */     } 
/*  305 */     int $$4 = getGameRules().getInt(GameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE);
/*  306 */     if (this.sleepStatus.areEnoughSleeping($$4) && this.sleepStatus.areEnoughDeepSleeping($$4, this.players)) {
/*  307 */       if (getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
/*      */         
/*  309 */         long $$5 = this.levelData.getDayTime() + 24000L;
/*  310 */         setDayTime($$5 - $$5 % 24000L);
/*      */       } 
/*      */       
/*  313 */       wakeUpAllPlayers();
/*      */       
/*  315 */       if (getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE) && isRaining()) {
/*  316 */         resetWeatherCycle();
/*      */       }
/*      */     } 
/*      */     
/*  320 */     updateSkyBrightness();
/*      */     
/*  322 */     if ($$3) {
/*  323 */       tickTime();
/*      */     }
/*      */     
/*  326 */     $$1.popPush("tickPending");
/*  327 */     if (!isDebug() && $$3) {
/*  328 */       long $$6 = getGameTime();
/*  329 */       $$1.push("blockTicks");
/*  330 */       this.blockTicks.tick($$6, 65536, this::tickBlock);
/*  331 */       $$1.popPush("fluidTicks");
/*  332 */       this.fluidTicks.tick($$6, 65536, this::tickFluid);
/*  333 */       $$1.pop();
/*      */     } 
/*      */     
/*  336 */     $$1.popPush("raid");
/*  337 */     if ($$3) {
/*  338 */       this.raids.tick();
/*      */     }
/*  340 */     $$1.popPush("chunkSource");
/*  341 */     getChunkSource().tick($$0, true);
/*      */     
/*  343 */     $$1.popPush("blockEvents");
/*  344 */     if ($$3) {
/*  345 */       runBlockEvents();
/*      */     }
/*  347 */     this.handlingTick = false;
/*  348 */     $$1.pop();
/*      */ 
/*      */     
/*  351 */     boolean $$7 = (!this.players.isEmpty() || !getForcedChunks().isEmpty());
/*      */     
/*  353 */     if ($$7) {
/*  354 */       resetEmptyTime();
/*      */     }
/*  356 */     if ($$7 || this.emptyTime++ < 300) {
/*  357 */       $$1.push("entities");
/*  358 */       if (this.dragonFight != null && $$3) {
/*  359 */         $$1.push("dragonFight");
/*  360 */         this.dragonFight.tick();
/*  361 */         $$1.pop();
/*      */       } 
/*  363 */       this.entityTickList.forEach($$2 -> {
/*      */             if ($$2.isRemoved()) {
/*      */               return;
/*      */             }
/*      */             
/*      */             if (shouldDiscardEntity($$2)) {
/*      */               $$2.discard();
/*      */               
/*      */               return;
/*      */             } 
/*      */             
/*      */             if ($$0.isEntityFrozen($$2)) {
/*      */               return;
/*      */             }
/*      */             
/*      */             $$1.push("checkDespawn");
/*      */             
/*      */             $$2.checkDespawn();
/*      */             
/*      */             $$1.pop();
/*      */             
/*      */             if (!this.chunkSource.chunkMap.getDistanceManager().inEntityTickingRange($$2.chunkPosition().toLong())) {
/*      */               return;
/*      */             }
/*      */             
/*      */             Entity $$3 = $$2.getVehicle();
/*      */             
/*      */             if ($$3 != null) {
/*      */               if ($$3.isRemoved() || !$$3.hasPassenger($$2)) {
/*      */                 $$2.stopRiding();
/*      */               } else {
/*      */                 return;
/*      */               } 
/*      */             }
/*      */             
/*      */             $$1.push("tick");
/*      */             guardEntityTick(this::tickNonPassenger, $$2);
/*      */             $$1.pop();
/*      */           });
/*  402 */       $$1.pop();
/*      */       
/*  404 */       tickBlockEntities();
/*      */     } 
/*      */     
/*  407 */     $$1.push("entityManagement");
/*  408 */     this.entityManager.tick();
/*  409 */     $$1.pop();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldTickBlocksAt(long $$0) {
/*  414 */     return this.chunkSource.chunkMap.getDistanceManager().inBlockTickingRange($$0);
/*      */   }
/*      */   
/*      */   protected void tickTime() {
/*  418 */     if (!this.tickTime) {
/*      */       return;
/*      */     }
/*  421 */     long $$0 = this.levelData.getGameTime() + 1L;
/*  422 */     this.serverLevelData.setGameTime($$0);
/*  423 */     this.serverLevelData.getScheduledEvents().tick(this.server, $$0);
/*  424 */     if (this.levelData.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
/*  425 */       setDayTime(this.levelData.getDayTime() + 1L);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setDayTime(long $$0) {
/*  430 */     this.serverLevelData.setDayTime($$0);
/*      */   }
/*      */   
/*      */   public void tickCustomSpawners(boolean $$0, boolean $$1) {
/*  434 */     for (CustomSpawner $$2 : this.customSpawners) {
/*  435 */       $$2.tick(this, $$0, $$1);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean shouldDiscardEntity(Entity $$0) {
/*  440 */     if (!this.server.isSpawningAnimals() && ($$0 instanceof net.minecraft.world.entity.animal.Animal || $$0 instanceof net.minecraft.world.entity.animal.WaterAnimal)) {
/*  441 */       return true;
/*      */     }
/*  443 */     if (!this.server.areNpcsEnabled() && $$0 instanceof net.minecraft.world.entity.npc.Npc) {
/*  444 */       return true;
/*      */     }
/*  446 */     return false;
/*      */   }
/*      */   
/*      */   private void wakeUpAllPlayers() {
/*  450 */     this.sleepStatus.removeAllSleepers();
/*      */     
/*  452 */     ((List)this.players.stream().filter(LivingEntity::isSleeping).collect(Collectors.toList())).forEach($$0 -> $$0.stopSleepInBed(false, false));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void tickChunk(LevelChunk $$0, int $$1) {
/*  458 */     ChunkPos $$2 = $$0.getPos();
/*  459 */     boolean $$3 = isRaining();
/*  460 */     int $$4 = $$2.getMinBlockX();
/*  461 */     int $$5 = $$2.getMinBlockZ();
/*      */     
/*  463 */     ProfilerFiller $$6 = getProfiler();
/*  464 */     $$6.push("thunder");
/*  465 */     if ($$3 && isThundering() && this.random.nextInt(100000) == 0) {
/*  466 */       BlockPos $$7 = findLightningTargetAround(getBlockRandomPos($$4, 0, $$5, 15));
/*  467 */       if (isRainingAt($$7)) {
/*  468 */         DifficultyInstance $$8 = getCurrentDifficultyAt($$7);
/*      */         
/*  470 */         boolean $$9 = (getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && this.random.nextDouble() < $$8.getEffectiveDifficulty() * 0.01D && !getBlockState($$7.below()).is(Blocks.LIGHTNING_ROD));
/*  471 */         if ($$9) {
/*  472 */           SkeletonHorse $$10 = (SkeletonHorse)EntityType.SKELETON_HORSE.create(this);
/*  473 */           if ($$10 != null) {
/*  474 */             $$10.setTrap(true);
/*  475 */             $$10.setAge(0);
/*  476 */             $$10.setPos($$7.getX(), $$7.getY(), $$7.getZ());
/*  477 */             addFreshEntity((Entity)$$10);
/*      */           } 
/*      */         } 
/*  480 */         LightningBolt $$11 = (LightningBolt)EntityType.LIGHTNING_BOLT.create(this);
/*  481 */         if ($$11 != null) {
/*  482 */           $$11.moveTo(Vec3.atBottomCenterOf((Vec3i)$$7));
/*  483 */           $$11.setVisualOnly($$9);
/*  484 */           addFreshEntity((Entity)$$11);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  489 */     $$6.popPush("iceandsnow");
/*  490 */     for (int $$12 = 0; $$12 < $$1; $$12++) {
/*  491 */       if (this.random.nextInt(48) == 0) {
/*  492 */         tickPrecipitation(getBlockRandomPos($$4, 0, $$5, 15));
/*      */       }
/*      */     } 
/*      */     
/*  496 */     $$6.popPush("tickBlocks");
/*  497 */     if ($$1 > 0) {
/*  498 */       LevelChunkSection[] $$13 = $$0.getSections();
/*  499 */       for (int $$14 = 0; $$14 < $$13.length; $$14++) {
/*  500 */         LevelChunkSection $$15 = $$13[$$14];
/*  501 */         if ($$15.isRandomlyTicking()) {
/*  502 */           int $$16 = $$0.getSectionYFromSectionIndex($$14);
/*  503 */           int $$17 = SectionPos.sectionToBlockCoord($$16);
/*  504 */           for (int $$18 = 0; $$18 < $$1; $$18++) {
/*  505 */             BlockPos $$19 = getBlockRandomPos($$4, $$17, $$5, 15);
/*      */             
/*  507 */             $$6.push("randomTick");
/*  508 */             BlockState $$20 = $$15.getBlockState($$19.getX() - $$4, $$19.getY() - $$17, $$19.getZ() - $$5);
/*  509 */             if ($$20.isRandomlyTicking()) {
/*  510 */               $$20.randomTick(this, $$19, this.random);
/*      */             }
/*  512 */             FluidState $$21 = $$20.getFluidState();
/*  513 */             if ($$21.isRandomlyTicking()) {
/*  514 */               $$21.randomTick(this, $$19, this.random);
/*      */             }
/*  516 */             $$6.pop();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  521 */     $$6.pop();
/*      */   }
/*      */   
/*      */   @VisibleForTesting
/*      */   public void tickPrecipitation(BlockPos $$0) {
/*  526 */     BlockPos $$1 = getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, $$0);
/*  527 */     BlockPos $$2 = $$1.below();
/*  528 */     Biome $$3 = (Biome)getBiome($$1).value();
/*      */     
/*  530 */     if ($$3.shouldFreeze((LevelReader)this, $$2)) {
/*  531 */       setBlockAndUpdate($$2, Blocks.ICE.defaultBlockState());
/*      */     }
/*      */     
/*  534 */     if (isRaining()) {
/*  535 */       int $$4 = getGameRules().getInt(GameRules.RULE_SNOW_ACCUMULATION_HEIGHT);
/*  536 */       if ($$4 > 0 && $$3.shouldSnow((LevelReader)this, $$1)) {
/*  537 */         BlockState $$5 = getBlockState($$1);
/*  538 */         if ($$5.is(Blocks.SNOW)) {
/*  539 */           int $$6 = ((Integer)$$5.getValue((Property)SnowLayerBlock.LAYERS)).intValue();
/*  540 */           if ($$6 < Math.min($$4, 8)) {
/*  541 */             BlockState $$7 = (BlockState)$$5.setValue((Property)SnowLayerBlock.LAYERS, Integer.valueOf($$6 + 1));
/*  542 */             Block.pushEntitiesUp($$5, $$7, (LevelAccessor)this, $$1);
/*  543 */             setBlockAndUpdate($$1, $$7);
/*      */           } 
/*      */         } else {
/*  546 */           setBlockAndUpdate($$1, Blocks.SNOW.defaultBlockState());
/*      */         } 
/*      */       } 
/*      */       
/*  550 */       Biome.Precipitation $$8 = $$3.getPrecipitationAt($$2);
/*  551 */       if ($$8 != Biome.Precipitation.NONE) {
/*  552 */         BlockState $$9 = getBlockState($$2);
/*  553 */         $$9.getBlock().handlePrecipitation($$9, this, $$2, $$8);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private Optional<BlockPos> findLightningRod(BlockPos $$0) {
/*  559 */     Optional<BlockPos> $$1 = getPoiManager().findClosest($$0 -> $$0.is(PoiTypes.LIGHTNING_ROD), $$0 -> ($$0.getY() == getHeight(Heightmap.Types.WORLD_SURFACE, $$0.getX(), $$0.getZ()) - 1), $$0, 128, PoiManager.Occupancy.ANY);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  566 */     return $$1.map($$0 -> $$0.above(1));
/*      */   }
/*      */   
/*      */   protected BlockPos findLightningTargetAround(BlockPos $$0) {
/*  570 */     BlockPos $$1 = getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, $$0);
/*      */     
/*  572 */     Optional<BlockPos> $$2 = findLightningRod($$1);
/*  573 */     if ($$2.isPresent()) {
/*  574 */       return $$2.get();
/*      */     }
/*      */     
/*  577 */     AABB $$3 = AABB.encapsulatingFullBlocks($$1, new BlockPos((Vec3i)$$1.atY(getMaxBuildHeight()))).inflate(3.0D);
/*      */     
/*  579 */     List<LivingEntity> $$4 = getEntitiesOfClass(LivingEntity.class, $$3, $$0 -> ($$0 != null && $$0.isAlive() && canSeeSky($$0.blockPosition())));
/*      */     
/*  581 */     if (!$$4.isEmpty()) {
/*  582 */       return ((LivingEntity)$$4.get(this.random.nextInt($$4.size()))).blockPosition();
/*      */     }
/*      */     
/*  585 */     if ($$1.getY() == getMinBuildHeight() - 1) {
/*  586 */       $$1 = $$1.above(2);
/*      */     }
/*      */     
/*  589 */     return $$1;
/*      */   }
/*      */   
/*      */   public boolean isHandlingTick() {
/*  593 */     return this.handlingTick;
/*      */   }
/*      */   
/*      */   public boolean canSleepThroughNights() {
/*  597 */     return (getGameRules().getInt(GameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE) <= 100);
/*      */   }
/*      */   private void announceSleepStatus() {
/*      */     MutableComponent mutableComponent;
/*  601 */     if (!canSleepThroughNights()) {
/*      */       return;
/*      */     }
/*      */     
/*  605 */     if (getServer().isSingleplayer() && !getServer().isPublished()) {
/*      */       return;
/*      */     }
/*  608 */     int $$0 = getGameRules().getInt(GameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE);
/*      */     
/*  610 */     if (this.sleepStatus.areEnoughSleeping($$0)) {
/*  611 */       mutableComponent = Component.translatable("sleep.skipping_night");
/*      */     } else {
/*  613 */       mutableComponent = Component.translatable("sleep.players_sleeping", new Object[] { Integer.valueOf(this.sleepStatus.amountSleeping()), Integer.valueOf(this.sleepStatus.sleepersNeeded($$0)) });
/*      */     } 
/*      */     
/*  616 */     for (ServerPlayer $$3 : this.players) {
/*  617 */       $$3.displayClientMessage((Component)mutableComponent, true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void updateSleepingPlayerList() {
/*  622 */     if (!this.players.isEmpty() && this.sleepStatus.update(this.players)) {
/*  623 */       announceSleepStatus();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerScoreboard getScoreboard() {
/*  629 */     return this.server.getScoreboard();
/*      */   }
/*      */   
/*      */   private void advanceWeatherCycle() {
/*  633 */     boolean $$0 = isRaining();
/*  634 */     if (dimensionType().hasSkyLight()) {
/*  635 */       if (getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)) {
/*  636 */         int $$1 = this.serverLevelData.getClearWeatherTime();
/*  637 */         int $$2 = this.serverLevelData.getThunderTime();
/*  638 */         int $$3 = this.serverLevelData.getRainTime();
/*  639 */         boolean $$4 = this.levelData.isThundering();
/*  640 */         boolean $$5 = this.levelData.isRaining();
/*      */ 
/*      */         
/*  643 */         if ($$1 > 0) {
/*  644 */           $$1--;
/*  645 */           $$2 = $$4 ? 0 : 1;
/*  646 */           $$3 = $$5 ? 0 : 1;
/*  647 */           $$4 = false;
/*  648 */           $$5 = false;
/*      */         } else {
/*      */           
/*  651 */           if ($$2 > 0) {
/*  652 */             $$2--;
/*  653 */             if ($$2 == 0)
/*      */             {
/*  655 */               $$4 = !$$4;
/*      */             
/*      */             }
/*      */           }
/*  659 */           else if ($$4) {
/*      */             
/*  661 */             $$2 = THUNDER_DURATION.sample(this.random);
/*      */           } else {
/*      */             
/*  664 */             $$2 = THUNDER_DELAY.sample(this.random);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  669 */           if ($$3 > 0) {
/*  670 */             $$3--;
/*  671 */             if ($$3 == 0) {
/*  672 */               $$5 = !$$5;
/*      */             }
/*      */           }
/*  675 */           else if ($$5) {
/*      */             
/*  677 */             $$3 = RAIN_DURATION.sample(this.random);
/*      */           }
/*      */           else {
/*      */             
/*  681 */             $$3 = RAIN_DELAY.sample(this.random);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  686 */         this.serverLevelData.setThunderTime($$2);
/*  687 */         this.serverLevelData.setRainTime($$3);
/*  688 */         this.serverLevelData.setClearWeatherTime($$1);
/*  689 */         this.serverLevelData.setThundering($$4);
/*  690 */         this.serverLevelData.setRaining($$5);
/*      */       } 
/*      */       
/*  693 */       this.oThunderLevel = this.thunderLevel;
/*  694 */       if (this.levelData.isThundering()) {
/*  695 */         this.thunderLevel += 0.01F;
/*      */       } else {
/*  697 */         this.thunderLevel -= 0.01F;
/*      */       } 
/*  699 */       this.thunderLevel = Mth.clamp(this.thunderLevel, 0.0F, 1.0F);
/*      */       
/*  701 */       this.oRainLevel = this.rainLevel;
/*  702 */       if (this.levelData.isRaining()) {
/*  703 */         this.rainLevel += 0.01F;
/*      */       } else {
/*  705 */         this.rainLevel -= 0.01F;
/*      */       } 
/*  707 */       this.rainLevel = Mth.clamp(this.rainLevel, 0.0F, 1.0F);
/*      */     } 
/*      */     
/*  710 */     if (this.oRainLevel != this.rainLevel) {
/*  711 */       this.server.getPlayerList().broadcastAll((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, this.rainLevel), dimension());
/*      */     }
/*  713 */     if (this.oThunderLevel != this.thunderLevel) {
/*  714 */       this.server.getPlayerList().broadcastAll((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, this.thunderLevel), dimension());
/*      */     }
/*      */     
/*  717 */     if ($$0 != isRaining()) {
/*  718 */       if ($$0) {
/*  719 */         this.server.getPlayerList().broadcastAll((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.STOP_RAINING, 0.0F));
/*      */       } else {
/*  721 */         this.server.getPlayerList().broadcastAll((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.START_RAINING, 0.0F));
/*      */       } 
/*  723 */       this.server.getPlayerList().broadcastAll((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.RAIN_LEVEL_CHANGE, this.rainLevel));
/*  724 */       this.server.getPlayerList().broadcastAll((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.THUNDER_LEVEL_CHANGE, this.thunderLevel));
/*      */     } 
/*      */   }
/*      */   
/*      */   @VisibleForTesting
/*      */   public void resetWeatherCycle() {
/*  730 */     this.serverLevelData.setRainTime(0);
/*  731 */     this.serverLevelData.setRaining(false);
/*  732 */     this.serverLevelData.setThunderTime(0);
/*  733 */     this.serverLevelData.setThundering(false);
/*      */   }
/*      */   
/*      */   public void resetEmptyTime() {
/*  737 */     this.emptyTime = 0;
/*      */   }
/*      */   
/*      */   private void tickFluid(BlockPos $$0, Fluid $$1) {
/*  741 */     FluidState $$2 = getFluidState($$0);
/*  742 */     if ($$2.is($$1)) {
/*  743 */       $$2.tick(this, $$0);
/*      */     }
/*      */   }
/*      */   
/*      */   private void tickBlock(BlockPos $$0, Block $$1) {
/*  748 */     BlockState $$2 = getBlockState($$0);
/*  749 */     if ($$2.is($$1)) {
/*  750 */       $$2.tick(this, $$0, this.random);
/*      */     }
/*      */   }
/*      */   
/*      */   public void tickNonPassenger(Entity $$0) {
/*  755 */     $$0.setOldPosAndRot();
/*      */     
/*  757 */     ProfilerFiller $$1 = getProfiler();
/*  758 */     $$0.tickCount++;
/*  759 */     getProfiler().push(() -> BuiltInRegistries.ENTITY_TYPE.getKey($$0.getType()).toString());
/*  760 */     $$1.incrementCounter("tickNonPassenger");
/*  761 */     $$0.tick();
/*  762 */     getProfiler().pop();
/*      */     
/*  764 */     for (Entity $$2 : $$0.getPassengers()) {
/*  765 */       tickPassenger($$0, $$2);
/*      */     }
/*      */   }
/*      */   
/*      */   private void tickPassenger(Entity $$0, Entity $$1) {
/*  770 */     if ($$1.isRemoved() || $$1.getVehicle() != $$0) {
/*  771 */       $$1.stopRiding();
/*      */       return;
/*      */     } 
/*  774 */     if (!($$1 instanceof Player) && !this.entityTickList.contains($$1)) {
/*      */       return;
/*      */     }
/*      */     
/*  778 */     $$1.setOldPosAndRot();
/*      */     
/*  780 */     $$1.tickCount++;
/*  781 */     ProfilerFiller $$2 = getProfiler();
/*  782 */     $$2.push(() -> BuiltInRegistries.ENTITY_TYPE.getKey($$0.getType()).toString());
/*  783 */     $$2.incrementCounter("tickPassenger");
/*  784 */     $$1.rideTick();
/*  785 */     $$2.pop();
/*      */     
/*  787 */     for (Entity $$3 : $$1.getPassengers()) {
/*  788 */       tickPassenger($$1, $$3);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean mayInteract(Player $$0, BlockPos $$1) {
/*  794 */     return (!this.server.isUnderSpawnProtection(this, $$1, $$0) && getWorldBorder().isWithinBounds($$1));
/*      */   }
/*      */   
/*      */   public void save(@Nullable ProgressListener $$0, boolean $$1, boolean $$2) {
/*  798 */     ServerChunkCache $$3 = getChunkSource();
/*  799 */     if ($$2) {
/*      */       return;
/*      */     }
/*      */     
/*  803 */     if ($$0 != null) {
/*  804 */       $$0.progressStartNoAbort((Component)Component.translatable("menu.savingLevel"));
/*      */     }
/*  806 */     saveLevelData();
/*      */     
/*  808 */     if ($$0 != null) {
/*  809 */       $$0.progressStage((Component)Component.translatable("menu.savingChunks"));
/*      */     }
/*  811 */     $$3.save($$1);
/*      */     
/*  813 */     if ($$1) {
/*  814 */       this.entityManager.saveAll();
/*      */     } else {
/*  816 */       this.entityManager.autoSave();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void saveLevelData() {
/*  821 */     if (this.dragonFight != null) {
/*  822 */       this.server.getWorldData().setEndDragonFightData(this.dragonFight.saveData());
/*      */     }
/*  824 */     getChunkSource().getDataStorage().save();
/*      */   }
/*      */   
/*      */   public <T extends Entity> List<? extends T> getEntities(EntityTypeTest<Entity, T> $$0, Predicate<? super T> $$1) {
/*  828 */     List<T> $$2 = Lists.newArrayList();
/*  829 */     getEntities($$0, $$1, $$2);
/*  830 */     return $$2;
/*      */   }
/*      */   
/*      */   public <T extends Entity> void getEntities(EntityTypeTest<Entity, T> $$0, Predicate<? super T> $$1, List<? super T> $$2) {
/*  834 */     getEntities($$0, $$1, $$2, 2147483647);
/*      */   }
/*      */   
/*      */   public <T extends Entity> void getEntities(EntityTypeTest<Entity, T> $$0, Predicate<? super T> $$1, List<? super T> $$2, int $$3) {
/*  838 */     getEntities().get($$0, $$3 -> {
/*      */           if ($$0.test($$3)) {
/*      */             $$1.add($$3);
/*      */             if ($$1.size() >= $$2) {
/*      */               return AbortableIterationConsumer.Continuation.ABORT;
/*      */             }
/*      */           } 
/*      */           return AbortableIterationConsumer.Continuation.CONTINUE;
/*      */         });
/*      */   }
/*      */   
/*      */   public List<? extends EnderDragon> getDragons() {
/*  850 */     return getEntities((EntityTypeTest<Entity, EnderDragon>)EntityType.ENDER_DRAGON, LivingEntity::isAlive);
/*      */   }
/*      */   
/*      */   public List<ServerPlayer> getPlayers(Predicate<? super ServerPlayer> $$0) {
/*  854 */     return getPlayers($$0, 2147483647);
/*      */   }
/*      */   
/*      */   public List<ServerPlayer> getPlayers(Predicate<? super ServerPlayer> $$0, int $$1) {
/*  858 */     List<ServerPlayer> $$2 = Lists.newArrayList();
/*  859 */     for (ServerPlayer $$3 : this.players) {
/*  860 */       if ($$0.test($$3)) {
/*  861 */         $$2.add($$3);
/*  862 */         if ($$2.size() >= $$1) {
/*  863 */           return $$2;
/*      */         }
/*      */       } 
/*      */     } 
/*  867 */     return $$2;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ServerPlayer getRandomPlayer() {
/*  872 */     List<ServerPlayer> $$0 = getPlayers(LivingEntity::isAlive);
/*  873 */     if ($$0.isEmpty()) {
/*  874 */       return null;
/*      */     }
/*  876 */     return $$0.get(this.random.nextInt($$0.size()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addFreshEntity(Entity $$0) {
/*  884 */     return addEntity($$0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addWithUUID(Entity $$0) {
/*  891 */     return addEntity($$0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDuringTeleport(Entity $$0) {
/*  898 */     addEntity($$0);
/*      */   }
/*      */   
/*      */   public void addDuringCommandTeleport(ServerPlayer $$0) {
/*  902 */     addPlayer($$0);
/*      */   }
/*      */   
/*      */   public void addDuringPortalTeleport(ServerPlayer $$0) {
/*  906 */     addPlayer($$0);
/*      */   }
/*      */   
/*      */   public void addNewPlayer(ServerPlayer $$0) {
/*  910 */     addPlayer($$0);
/*      */   }
/*      */   
/*      */   public void addRespawnedPlayer(ServerPlayer $$0) {
/*  914 */     addPlayer($$0);
/*      */   }
/*      */   
/*      */   private void addPlayer(ServerPlayer $$0) {
/*  918 */     Entity $$1 = (Entity)getEntities().get($$0.getUUID());
/*  919 */     if ($$1 != null) {
/*  920 */       LOGGER.warn("Force-added player with duplicate UUID {}", $$0.getUUID());
/*  921 */       $$1.unRide();
/*  922 */       removePlayerImmediately((ServerPlayer)$$1, Entity.RemovalReason.DISCARDED);
/*      */     } 
/*  924 */     this.entityManager.addNewEntity((EntityAccess)$$0);
/*      */   }
/*      */   
/*      */   private boolean addEntity(Entity $$0) {
/*  928 */     if ($$0.isRemoved()) {
/*  929 */       LOGGER.warn("Tried to add entity {} but it was marked as removed already", EntityType.getKey($$0.getType()));
/*  930 */       return false;
/*      */     } 
/*      */     
/*  933 */     return this.entityManager.addNewEntity((EntityAccess)$$0);
/*      */   }
/*      */   
/*      */   public boolean tryAddFreshEntityWithPassengers(Entity $$0) {
/*  937 */     Objects.requireNonNull(this.entityManager); if ($$0.getSelfAndPassengers().map(Entity::getUUID).anyMatch(this.entityManager::isLoaded)) {
/*  938 */       return false;
/*      */     }
/*      */     
/*  941 */     addFreshEntityWithPassengers($$0);
/*  942 */     return true;
/*      */   }
/*      */   
/*      */   public void unload(LevelChunk $$0) {
/*  946 */     $$0.clearAllBlockEntities();
/*  947 */     $$0.unregisterTickContainerFromLevel(this);
/*      */   }
/*      */   
/*      */   public void removePlayerImmediately(ServerPlayer $$0, Entity.RemovalReason $$1) {
/*  951 */     $$0.remove($$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void destroyBlockProgress(int $$0, BlockPos $$1, int $$2) {
/*  956 */     for (ServerPlayer $$3 : this.server.getPlayerList().getPlayers()) {
/*  957 */       if ($$3 == null || $$3.level() != this || $$3.getId() == $$0) {
/*      */         continue;
/*      */       }
/*  960 */       double $$4 = $$1.getX() - $$3.getX();
/*  961 */       double $$5 = $$1.getY() - $$3.getY();
/*  962 */       double $$6 = $$1.getZ() - $$3.getZ();
/*      */       
/*  964 */       if ($$4 * $$4 + $$5 * $$5 + $$6 * $$6 < 1024.0D) {
/*  965 */         $$3.connection.send((Packet)new ClientboundBlockDestructionPacket($$0, $$1, $$2));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSeededSound(@Nullable Player $$0, double $$1, double $$2, double $$3, Holder<SoundEvent> $$4, SoundSource $$5, float $$6, float $$7, long $$8) {
/*  972 */     this.server.getPlayerList().broadcast($$0, $$1, $$2, $$3, ((SoundEvent)$$4.value()).getRange($$6), dimension(), (Packet)new ClientboundSoundPacket($$4, $$5, $$1, $$2, $$3, $$6, $$7, $$8));
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSeededSound(@Nullable Player $$0, Entity $$1, Holder<SoundEvent> $$2, SoundSource $$3, float $$4, float $$5, long $$6) {
/*  977 */     this.server.getPlayerList().broadcast($$0, $$1.getX(), $$1.getY(), $$1.getZ(), ((SoundEvent)$$2.value()).getRange($$4), dimension(), (Packet)new ClientboundSoundEntityPacket($$2, $$3, $$1, $$4, $$5, $$6));
/*      */   }
/*      */ 
/*      */   
/*      */   public void globalLevelEvent(int $$0, BlockPos $$1, int $$2) {
/*  982 */     if (getGameRules().getBoolean(GameRules.RULE_GLOBAL_SOUND_EVENTS)) {
/*  983 */       this.server.getPlayerList().broadcastAll((Packet)new ClientboundLevelEventPacket($$0, $$1, $$2, true));
/*      */     } else {
/*  985 */       levelEvent((Player)null, $$0, $$1, $$2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void levelEvent(@Nullable Player $$0, int $$1, BlockPos $$2, int $$3) {
/*  991 */     this.server.getPlayerList().broadcast($$0, $$2.getX(), $$2.getY(), $$2.getZ(), 64.0D, dimension(), (Packet)new ClientboundLevelEventPacket($$1, $$2, $$3, false));
/*      */   }
/*      */   
/*      */   public int getLogicalHeight() {
/*  995 */     return dimensionType().logicalHeight();
/*      */   }
/*      */ 
/*      */   
/*      */   public void gameEvent(GameEvent $$0, Vec3 $$1, GameEvent.Context $$2) {
/* 1000 */     this.gameEventDispatcher.post($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendBlockUpdated(BlockPos $$0, BlockState $$1, BlockState $$2, int $$3) {
/* 1005 */     if (this.isUpdatingNavigations) {
/* 1006 */       String $$4 = "recursive call to sendBlockUpdated";
/* 1007 */       Util.logAndPauseIfInIde("recursive call to sendBlockUpdated", new IllegalStateException("recursive call to sendBlockUpdated"));
/*      */     } 
/* 1009 */     getChunkSource().blockChanged($$0);
/* 1010 */     VoxelShape $$5 = $$1.getCollisionShape((BlockGetter)this, $$0);
/* 1011 */     VoxelShape $$6 = $$2.getCollisionShape((BlockGetter)this, $$0);
/* 1012 */     if (!Shapes.joinIsNotEmpty($$5, $$6, BooleanOp.NOT_SAME)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1019 */     ObjectArrayList<PathNavigation> objectArrayList = new ObjectArrayList();
/*      */     
/* 1021 */     for (Mob $$8 : this.navigatingMobs) {
/* 1022 */       PathNavigation $$9 = $$8.getNavigation();
/* 1023 */       if ($$9.shouldRecomputePath($$0)) {
/* 1024 */         objectArrayList.add($$9);
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/* 1029 */       this.isUpdatingNavigations = true;
/* 1030 */       for (PathNavigation $$10 : objectArrayList) {
/* 1031 */         $$10.recomputePath();
/*      */       }
/*      */     } finally {
/* 1034 */       this.isUpdatingNavigations = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateNeighborsAt(BlockPos $$0, Block $$1) {
/* 1040 */     this.neighborUpdater.updateNeighborsAtExceptFromFacing($$0, $$1, null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateNeighborsAtExceptFromFacing(BlockPos $$0, Block $$1, Direction $$2) {
/* 1045 */     this.neighborUpdater.updateNeighborsAtExceptFromFacing($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void neighborChanged(BlockPos $$0, Block $$1, BlockPos $$2) {
/* 1050 */     this.neighborUpdater.neighborChanged($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public void neighborChanged(BlockState $$0, BlockPos $$1, Block $$2, BlockPos $$3, boolean $$4) {
/* 1055 */     this.neighborUpdater.neighborChanged($$0, $$1, $$2, $$3, $$4);
/*      */   }
/*      */ 
/*      */   
/*      */   public void broadcastEntityEvent(Entity $$0, byte $$1) {
/* 1060 */     getChunkSource().broadcastAndSend($$0, (Packet<?>)new ClientboundEntityEventPacket($$0, $$1));
/*      */   }
/*      */ 
/*      */   
/*      */   public void broadcastDamageEvent(Entity $$0, DamageSource $$1) {
/* 1065 */     getChunkSource().broadcastAndSend($$0, (Packet<?>)new ClientboundDamageEventPacket($$0, $$1));
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerChunkCache getChunkSource() {
/* 1070 */     return this.chunkSource;
/*      */   }
/*      */ 
/*      */   
/*      */   public Explosion explode(@Nullable Entity $$0, @Nullable DamageSource $$1, @Nullable ExplosionDamageCalculator $$2, double $$3, double $$4, double $$5, float $$6, boolean $$7, Level.ExplosionInteraction $$8, ParticleOptions $$9, ParticleOptions $$10, SoundEvent $$11) {
/* 1075 */     Explosion $$12 = explode($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, false, $$9, $$10, $$11);
/* 1076 */     if (!$$12.interactsWithBlocks()) {
/* 1077 */       $$12.clearToBlow();
/*      */     }
/*      */     
/* 1080 */     for (ServerPlayer $$13 : this.players) {
/* 1081 */       if ($$13.distanceToSqr($$3, $$4, $$5) < 4096.0D) {
/* 1082 */         $$13.connection.send((Packet)new ClientboundExplodePacket($$3, $$4, $$5, $$6, $$12.getToBlow(), (Vec3)$$12.getHitPlayers().get($$13), $$12.getBlockInteraction(), $$12.getSmallExplosionParticles(), $$12.getLargeExplosionParticles(), $$12.getExplosionSound()));
/*      */       }
/*      */     } 
/*      */     
/* 1086 */     return $$12;
/*      */   }
/*      */ 
/*      */   
/*      */   public void blockEvent(BlockPos $$0, Block $$1, int $$2, int $$3) {
/* 1091 */     this.blockEvents.add(new BlockEventData($$0, $$1, $$2, $$3));
/*      */   }
/*      */   
/*      */   private void runBlockEvents() {
/* 1095 */     this.blockEventsToReschedule.clear();
/* 1096 */     while (!this.blockEvents.isEmpty()) {
/* 1097 */       BlockEventData $$0 = (BlockEventData)this.blockEvents.removeFirst();
/* 1098 */       if (shouldTickBlocksAt($$0.pos())) {
/* 1099 */         if (doBlockEvent($$0))
/* 1100 */           this.server.getPlayerList().broadcast(null, $$0.pos().getX(), $$0.pos().getY(), $$0.pos().getZ(), 64.0D, dimension(), (Packet)new ClientboundBlockEventPacket($$0.pos(), $$0.block(), $$0.paramA(), $$0.paramB())); 
/*      */         continue;
/*      */       } 
/* 1103 */       this.blockEventsToReschedule.add($$0);
/*      */     } 
/*      */     
/* 1106 */     this.blockEvents.addAll(this.blockEventsToReschedule);
/*      */   }
/*      */   
/*      */   private boolean doBlockEvent(BlockEventData $$0) {
/* 1110 */     BlockState $$1 = getBlockState($$0.pos());
/* 1111 */     if ($$1.is($$0.block())) {
/* 1112 */       return $$1.triggerEvent(this, $$0.pos(), $$0.paramA(), $$0.paramB());
/*      */     }
/* 1114 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public LevelTicks<Block> getBlockTicks() {
/* 1119 */     return this.blockTicks;
/*      */   }
/*      */ 
/*      */   
/*      */   public LevelTicks<Fluid> getFluidTicks() {
/* 1124 */     return this.fluidTicks;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nonnull
/*      */   public MinecraftServer getServer() {
/* 1130 */     return this.server;
/*      */   }
/*      */   
/*      */   public PortalForcer getPortalForcer() {
/* 1134 */     return this.portalForcer;
/*      */   }
/*      */   
/*      */   public StructureTemplateManager getStructureManager() {
/* 1138 */     return this.server.getStructureManager();
/*      */   }
/*      */   
/*      */   public <T extends ParticleOptions> int sendParticles(T $$0, double $$1, double $$2, double $$3, int $$4, double $$5, double $$6, double $$7, double $$8) {
/* 1142 */     ClientboundLevelParticlesPacket $$9 = new ClientboundLevelParticlesPacket((ParticleOptions)$$0, false, $$1, $$2, $$3, (float)$$5, (float)$$6, (float)$$7, (float)$$8, $$4);
/* 1143 */     int $$10 = 0;
/*      */     
/* 1145 */     for (int $$11 = 0; $$11 < this.players.size(); $$11++) {
/* 1146 */       ServerPlayer $$12 = this.players.get($$11);
/*      */       
/* 1148 */       if (sendParticles($$12, false, $$1, $$2, $$3, (Packet<?>)$$9)) {
/* 1149 */         $$10++;
/*      */       }
/*      */     } 
/*      */     
/* 1153 */     return $$10;
/*      */   }
/*      */   
/*      */   public <T extends ParticleOptions> boolean sendParticles(ServerPlayer $$0, T $$1, boolean $$2, double $$3, double $$4, double $$5, int $$6, double $$7, double $$8, double $$9, double $$10) {
/* 1157 */     ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket((ParticleOptions)$$1, $$2, $$3, $$4, $$5, (float)$$7, (float)$$8, (float)$$9, (float)$$10, $$6);
/*      */     
/* 1159 */     return sendParticles($$0, $$2, $$3, $$4, $$5, (Packet<?>)clientboundLevelParticlesPacket);
/*      */   }
/*      */   
/*      */   private boolean sendParticles(ServerPlayer $$0, boolean $$1, double $$2, double $$3, double $$4, Packet<?> $$5) {
/* 1163 */     if ($$0.level() != this) {
/* 1164 */       return false;
/*      */     }
/*      */     
/* 1167 */     BlockPos $$6 = $$0.blockPosition();
/*      */     
/* 1169 */     if ($$6.closerToCenterThan((Position)new Vec3($$2, $$3, $$4), $$1 ? 512.0D : 32.0D)) {
/* 1170 */       $$0.connection.send($$5);
/* 1171 */       return true;
/*      */     } 
/*      */     
/* 1174 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Entity getEntity(int $$0) {
/* 1180 */     return (Entity)getEntities().get($$0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   @Nullable
/*      */   public Entity getEntityOrPart(int $$0) {
/* 1191 */     Entity $$1 = (Entity)getEntities().get($$0);
/* 1192 */     if ($$1 != null) {
/* 1193 */       return $$1;
/*      */     }
/* 1195 */     return (Entity)this.dragonParts.get($$0);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Entity getEntity(UUID $$0) {
/* 1200 */     return (Entity)getEntities().get($$0);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public BlockPos findNearestMapStructure(TagKey<Structure> $$0, BlockPos $$1, int $$2, boolean $$3) {
/* 1205 */     if (!this.server.getWorldData().worldGenOptions().generateStructures()) {
/* 1206 */       return null;
/*      */     }
/* 1208 */     Optional<HolderSet.Named<Structure>> $$4 = registryAccess().registryOrThrow(Registries.STRUCTURE).getTag($$0);
/* 1209 */     if ($$4.isEmpty()) {
/* 1210 */       return null;
/*      */     }
/* 1212 */     Pair<BlockPos, Holder<Structure>> $$5 = getChunkSource().getGenerator().findNearestMapStructure(this, (HolderSet)$$4.get(), $$1, $$2, $$3);
/* 1213 */     return ($$5 != null) ? (BlockPos)$$5.getFirst() : null;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Pair<BlockPos, Holder<Biome>> findClosestBiome3d(Predicate<Holder<Biome>> $$0, BlockPos $$1, int $$2, int $$3, int $$4) {
/* 1218 */     return getChunkSource().getGenerator().getBiomeSource().findClosestBiome3d($$1, $$2, $$3, $$4, $$0, getChunkSource().randomState().sampler(), (LevelReader)this);
/*      */   }
/*      */ 
/*      */   
/*      */   public RecipeManager getRecipeManager() {
/* 1223 */     return this.server.getRecipeManager();
/*      */   }
/*      */ 
/*      */   
/*      */   public TickRateManager tickRateManager() {
/* 1228 */     return (TickRateManager)this.server.tickRateManager();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean noSave() {
/* 1233 */     return this.noSave;
/*      */   }
/*      */   
/*      */   public DimensionDataStorage getDataStorage() {
/* 1237 */     return getChunkSource().getDataStorage();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public MapItemSavedData getMapData(String $$0) {
/* 1243 */     return (MapItemSavedData)getServer().overworld().getDataStorage().get(MapItemSavedData.factory(), $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMapData(String $$0, MapItemSavedData $$1) {
/* 1248 */     getServer().overworld().getDataStorage().set($$0, (SavedData)$$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getFreeMapId() {
/* 1253 */     return ((MapIndex)getServer().overworld().getDataStorage().computeIfAbsent(MapIndex.factory(), "idcounts")).getFreeAuxValueForMap();
/*      */   }
/*      */   
/*      */   public void setDefaultSpawnPos(BlockPos $$0, float $$1) {
/* 1257 */     ChunkPos $$2 = new ChunkPos(new BlockPos(this.levelData.getXSpawn(), 0, this.levelData.getZSpawn()));
/* 1258 */     this.levelData.setSpawn($$0, $$1);
/* 1259 */     getChunkSource().removeRegionTicket(TicketType.START, $$2, 11, Unit.INSTANCE);
/* 1260 */     getChunkSource().addRegionTicket(TicketType.START, new ChunkPos($$0), 11, Unit.INSTANCE);
/* 1261 */     getServer().getPlayerList().broadcastAll((Packet)new ClientboundSetDefaultSpawnPositionPacket($$0, $$1));
/*      */   }
/*      */   
/*      */   public LongSet getForcedChunks() {
/* 1265 */     ForcedChunksSavedData $$0 = (ForcedChunksSavedData)getDataStorage().get(ForcedChunksSavedData.factory(), "chunks");
/* 1266 */     return ($$0 != null) ? LongSets.unmodifiable($$0.getChunks()) : (LongSet)LongSets.EMPTY_SET;
/*      */   }
/*      */   public boolean setChunkForced(int $$0, int $$1, boolean $$2) {
/*      */     boolean $$7;
/* 1270 */     ForcedChunksSavedData $$3 = (ForcedChunksSavedData)getDataStorage().computeIfAbsent(ForcedChunksSavedData.factory(), "chunks");
/*      */     
/* 1272 */     ChunkPos $$4 = new ChunkPos($$0, $$1);
/* 1273 */     long $$5 = $$4.toLong();
/*      */     
/* 1275 */     if ($$2) {
/* 1276 */       boolean $$6 = $$3.getChunks().add($$5);
/* 1277 */       if ($$6) {
/* 1278 */         getChunk($$0, $$1);
/*      */       }
/*      */     } else {
/* 1281 */       $$7 = $$3.getChunks().remove($$5);
/*      */     } 
/* 1283 */     $$3.setDirty($$7);
/* 1284 */     if ($$7) {
/* 1285 */       getChunkSource().updateChunkForced($$4, $$2);
/*      */     }
/* 1287 */     return $$7;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<ServerPlayer> players() {
/* 1292 */     return this.players;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onBlockStateChange(BlockPos $$0, BlockState $$1, BlockState $$2) {
/* 1297 */     Optional<Holder<PoiType>> $$3 = PoiTypes.forState($$1);
/* 1298 */     Optional<Holder<PoiType>> $$4 = PoiTypes.forState($$2);
/*      */     
/* 1300 */     if (Objects.equals($$3, $$4)) {
/*      */       return;
/*      */     }
/*      */     
/* 1304 */     BlockPos $$5 = $$0.immutable();
/* 1305 */     $$3.ifPresent($$1 -> getServer().execute(()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1310 */     $$4.ifPresent($$1 -> getServer().execute(()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PoiManager getPoiManager() {
/* 1317 */     return getChunkSource().getPoiManager();
/*      */   }
/*      */   
/*      */   public boolean isVillage(BlockPos $$0) {
/* 1321 */     return isCloseToVillage($$0, 1);
/*      */   }
/*      */   
/*      */   public boolean isVillage(SectionPos $$0) {
/* 1325 */     return isVillage($$0.center());
/*      */   }
/*      */   
/*      */   public boolean isCloseToVillage(BlockPos $$0, int $$1) {
/* 1329 */     if ($$1 > 6) {
/* 1330 */       return false;
/*      */     }
/* 1332 */     return (sectionsToVillage(SectionPos.of($$0)) <= $$1);
/*      */   }
/*      */   
/*      */   public int sectionsToVillage(SectionPos $$0) {
/* 1336 */     return getPoiManager().sectionsToVillage($$0);
/*      */   }
/*      */   
/*      */   public Raids getRaids() {
/* 1340 */     return this.raids;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Raid getRaidAt(BlockPos $$0) {
/* 1348 */     return this.raids.getNearbyRaid($$0, 9216);
/*      */   }
/*      */   
/*      */   public boolean isRaided(BlockPos $$0) {
/* 1352 */     return (getRaidAt($$0) != null);
/*      */   }
/*      */   
/*      */   public void onReputationEvent(ReputationEventType $$0, Entity $$1, ReputationEventHandler $$2) {
/* 1356 */     $$2.onReputationEventFrom($$0, $$1);
/*      */   }
/*      */   
/*      */   public void saveDebugReport(Path $$0) throws IOException {
/* 1360 */     ChunkMap $$1 = (getChunkSource()).chunkMap;
/*      */     
/* 1362 */     Writer $$2 = Files.newBufferedWriter($$0.resolve("stats.txt"), new java.nio.file.OpenOption[0]); 
/* 1363 */     try { $$2.write(String.format(Locale.ROOT, "spawning_chunks: %d\n", new Object[] { Integer.valueOf($$1.getDistanceManager().getNaturalSpawnChunkCount()) }));
/* 1364 */       NaturalSpawner.SpawnState $$3 = getChunkSource().getLastSpawnState();
/* 1365 */       if ($$3 != null) {
/* 1366 */         for (ObjectIterator<Object2IntMap.Entry<MobCategory>> objectIterator = $$3.getMobCategoryCounts().object2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Object2IntMap.Entry<MobCategory> $$4 = objectIterator.next();
/* 1367 */           $$2.write(String.format(Locale.ROOT, "spawn_count.%s: %d\n", new Object[] { ((MobCategory)$$4.getKey()).getName(), Integer.valueOf($$4.getIntValue()) })); }
/*      */       
/*      */       }
/* 1370 */       $$2.write(String.format(Locale.ROOT, "entities: %s\n", new Object[] { this.entityManager.gatherStats() }));
/* 1371 */       $$2.write(String.format(Locale.ROOT, "block_entity_tickers: %d\n", new Object[] { Integer.valueOf(this.blockEntityTickers.size()) }));
/* 1372 */       $$2.write(String.format(Locale.ROOT, "block_ticks: %d\n", new Object[] { Integer.valueOf(getBlockTicks().count()) }));
/* 1373 */       $$2.write(String.format(Locale.ROOT, "fluid_ticks: %d\n", new Object[] { Integer.valueOf(getFluidTicks().count()) }));
/* 1374 */       $$2.write("distance_manager: " + $$1.getDistanceManager().getDebugStatus() + "\n");
/* 1375 */       $$2.write(String.format(Locale.ROOT, "pending_tasks: %d\n", new Object[] { Integer.valueOf(getChunkSource().getPendingTasksCount()) }));
/* 1376 */       if ($$2 != null) $$2.close();  } catch (Throwable throwable) { if ($$2 != null)
/*      */         try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 1378 */      CrashReport $$5 = new CrashReport("Level dump", new Exception("dummy"));
/* 1379 */     fillReportDetails($$5);
/* 1380 */     Writer $$6 = Files.newBufferedWriter($$0.resolve("example_crash.txt"), new java.nio.file.OpenOption[0]); 
/* 1381 */     try { $$6.write($$5.getFriendlyReport());
/* 1382 */       if ($$6 != null) $$6.close();  } catch (Throwable throwable) { if ($$6 != null)
/*      */         try { $$6.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 1384 */      Path $$7 = $$0.resolve("chunks.csv");
/* 1385 */     Writer $$8 = Files.newBufferedWriter($$7, new java.nio.file.OpenOption[0]); 
/* 1386 */     try { $$1.dumpChunks($$8);
/* 1387 */       if ($$8 != null) $$8.close();  } catch (Throwable throwable) { if ($$8 != null)
/*      */         try { $$8.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 1389 */      Path $$9 = $$0.resolve("entity_chunks.csv");
/* 1390 */     Writer $$10 = Files.newBufferedWriter($$9, new java.nio.file.OpenOption[0]); 
/* 1391 */     try { this.entityManager.dumpSections($$10);
/* 1392 */       if ($$10 != null) $$10.close();  } catch (Throwable throwable) { if ($$10 != null)
/*      */         try { $$10.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 1394 */      Path $$11 = $$0.resolve("entities.csv");
/* 1395 */     Writer $$12 = Files.newBufferedWriter($$11, new java.nio.file.OpenOption[0]); 
/* 1396 */     try { dumpEntities($$12, getEntities().getAll());
/* 1397 */       if ($$12 != null) $$12.close();  } catch (Throwable throwable) { if ($$12 != null)
/*      */         try { $$12.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 1399 */      Path $$13 = $$0.resolve("block_entities.csv");
/* 1400 */     Writer $$14 = Files.newBufferedWriter($$13, new java.nio.file.OpenOption[0]); try {
/* 1401 */       dumpBlockEntityTickers($$14);
/* 1402 */       if ($$14 != null) $$14.close(); 
/*      */     } catch (Throwable throwable) {
/*      */       if ($$14 != null)
/*      */         try {
/*      */           $$14.close();
/*      */         } catch (Throwable throwable1) {
/*      */           throwable.addSuppressed(throwable1);
/*      */         }  
/*      */       throw throwable;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void dumpEntities(Writer $$0, Iterable<Entity> $$1) throws IOException {
/* 1415 */     CsvOutput $$2 = CsvOutput.builder().addColumn("x").addColumn("y").addColumn("z").addColumn("uuid").addColumn("type").addColumn("alive").addColumn("display_name").addColumn("custom_name").build($$0);
/*      */     
/* 1417 */     for (Entity $$3 : $$1) {
/* 1418 */       Component $$4 = $$3.getCustomName();
/* 1419 */       Component $$5 = $$3.getDisplayName();
/* 1420 */       $$2.writeRow(new Object[] {
/* 1421 */             Double.valueOf($$3.getX()), 
/* 1422 */             Double.valueOf($$3.getY()), 
/* 1423 */             Double.valueOf($$3.getZ()), $$3
/* 1424 */             .getUUID(), BuiltInRegistries.ENTITY_TYPE
/* 1425 */             .getKey($$3.getType()), 
/* 1426 */             Boolean.valueOf($$3.isAlive()), $$5
/* 1427 */             .getString(), 
/* 1428 */             ($$4 != null) ? $$4.getString() : null
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void dumpBlockEntityTickers(Writer $$0) throws IOException {
/* 1439 */     CsvOutput $$1 = CsvOutput.builder().addColumn("x").addColumn("y").addColumn("z").addColumn("type").build($$0);
/*      */     
/* 1441 */     for (TickingBlockEntity $$2 : this.blockEntityTickers) {
/* 1442 */       BlockPos $$3 = $$2.getPos();
/* 1443 */       $$1.writeRow(new Object[] {
/* 1444 */             Integer.valueOf($$3.getX()), 
/* 1445 */             Integer.valueOf($$3.getY()), 
/* 1446 */             Integer.valueOf($$3.getZ()), $$2
/* 1447 */             .getType()
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   @VisibleForTesting
/*      */   public void clearBlockEvents(BoundingBox $$0) {
/* 1454 */     this.blockEvents.removeIf($$1 -> $$0.isInside((Vec3i)$$1.pos()));
/*      */   }
/*      */ 
/*      */   
/*      */   public void blockUpdated(BlockPos $$0, Block $$1) {
/* 1459 */     if (!isDebug()) {
/* 1460 */       updateNeighborsAt($$0, $$1);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public float getShade(Direction $$0, boolean $$1) {
/* 1466 */     return 1.0F;
/*      */   }
/*      */   
/*      */   public Iterable<Entity> getAllEntities() {
/* 1470 */     return getEntities().getAll();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1475 */     return "ServerLevel[" + this.serverLevelData.getLevelName() + "]";
/*      */   }
/*      */   
/*      */   public boolean isFlat() {
/* 1479 */     return this.server.getWorldData().isFlatWorld();
/*      */   }
/*      */ 
/*      */   
/*      */   public long getSeed() {
/* 1484 */     return this.server.getWorldData().worldGenOptions().seed();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public EndDragonFight getDragonFight() {
/* 1489 */     return this.dragonFight;
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerLevel getLevel() {
/* 1494 */     return this;
/*      */   }
/*      */   
/*      */   @VisibleForTesting
/*      */   public String getWatchdogStats() {
/* 1499 */     return String.format(Locale.ROOT, "players: %s, entities: %s [%s], block_entities: %d [%s], block_ticks: %d, fluid_ticks: %d, chunk_source: %s", new Object[] {
/* 1500 */           Integer.valueOf(this.players.size()), this.entityManager
/* 1501 */           .gatherStats(), 
/* 1502 */           getTypeCount(this.entityManager.getEntityGetter().getAll(), $$0 -> BuiltInRegistries.ENTITY_TYPE.getKey($$0.getType()).toString()), 
/* 1503 */           Integer.valueOf(this.blockEntityTickers.size()), 
/* 1504 */           getTypeCount(this.blockEntityTickers, TickingBlockEntity::getType), 
/* 1505 */           Integer.valueOf(getBlockTicks().count()), 
/* 1506 */           Integer.valueOf(getFluidTicks().count()), 
/* 1507 */           gatherChunkSourceStats()
/*      */         });
/*      */   }
/*      */   
/*      */   private static <T> String getTypeCount(Iterable<T> $$0, Function<T, String> $$1) {
/*      */     try {
/* 1513 */       Object2IntOpenHashMap<String> $$2 = new Object2IntOpenHashMap();
/* 1514 */       for (T $$3 : $$0) {
/* 1515 */         String $$4 = $$1.apply($$3);
/* 1516 */         $$2.addTo($$4, 1);
/*      */       } 
/* 1518 */       return $$2.object2IntEntrySet().stream()
/* 1519 */         .sorted(Comparator.comparing(Object2IntMap.Entry::getIntValue).reversed())
/* 1520 */         .limit(5L)
/* 1521 */         .map($$0 -> (String)$$0.getKey() + ":" + (String)$$0.getKey())
/* 1522 */         .collect(Collectors.joining(","));
/* 1523 */     } catch (Exception $$5) {
/* 1524 */       return "";
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void makeObsidianPlatform(ServerLevel $$0) {
/* 1529 */     BlockPos $$1 = END_SPAWN_POINT;
/* 1530 */     int $$2 = $$1.getX();
/* 1531 */     int $$3 = $$1.getY() - 2;
/* 1532 */     int $$4 = $$1.getZ();
/* 1533 */     BlockPos.betweenClosed($$2 - 2, $$3 + 1, $$4 - 2, $$2 + 2, $$3 + 3, $$4 + 2)
/* 1534 */       .forEach($$1 -> $$0.setBlockAndUpdate($$1, Blocks.AIR.defaultBlockState()));
/* 1535 */     BlockPos.betweenClosed($$2 - 2, $$3, $$4 - 2, $$2 + 2, $$3, $$4 + 2)
/* 1536 */       .forEach($$1 -> $$0.setBlockAndUpdate($$1, Blocks.OBSIDIAN.defaultBlockState()));
/*      */   }
/*      */ 
/*      */   
/*      */   protected LevelEntityGetter<Entity> getEntities() {
/* 1541 */     return this.entityManager.getEntityGetter();
/*      */   }
/*      */   
/*      */   public void addLegacyChunkEntities(Stream<Entity> $$0) {
/* 1545 */     this.entityManager.addLegacyChunkEntities($$0);
/*      */   }
/*      */   
/*      */   public void addWorldGenChunkEntities(Stream<Entity> $$0) {
/* 1549 */     this.entityManager.addWorldGenChunkEntities($$0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void startTickingChunk(LevelChunk $$0) {
/* 1556 */     $$0.unpackTicks(getLevelData().getGameTime());
/*      */   }
/*      */   
/*      */   public void onStructureStartsAvailable(ChunkAccess $$0) {
/* 1560 */     this.server.execute(() -> this.structureCheck.onStructureLoad($$0.getPos(), $$0.getAllStarts()));
/*      */   }
/*      */ 
/*      */   
/*      */   private final class EntityCallbacks
/*      */     implements LevelCallback<Entity>
/*      */   {
/*      */     public void onCreated(Entity $$0) {}
/*      */     
/*      */     public void onDestroyed(Entity $$0) {
/* 1570 */       ServerLevel.this.getScoreboard().entityRemoved($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onTickingStart(Entity $$0) {
/* 1575 */       ServerLevel.this.entityTickList.add($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onTickingEnd(Entity $$0) {
/* 1580 */       ServerLevel.this.entityTickList.remove($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onTrackingStart(Entity $$0) {
/* 1585 */       ServerLevel.this.getChunkSource().addEntity($$0);
/* 1586 */       if ($$0 instanceof ServerPlayer) { ServerPlayer $$1 = (ServerPlayer)$$0;
/* 1587 */         ServerLevel.this.players.add($$1);
/* 1588 */         ServerLevel.this.updateSleepingPlayerList(); }
/*      */       
/* 1590 */       if ($$0 instanceof Mob) { Mob $$2 = (Mob)$$0;
/* 1591 */         if (ServerLevel.this.isUpdatingNavigations) {
/* 1592 */           String $$3 = "onTrackingStart called during navigation iteration";
/* 1593 */           Util.logAndPauseIfInIde("onTrackingStart called during navigation iteration", new IllegalStateException("onTrackingStart called during navigation iteration"));
/*      */         } 
/* 1595 */         ServerLevel.this.navigatingMobs.add($$2); }
/*      */       
/* 1597 */       if ($$0 instanceof EnderDragon) { EnderDragon $$4 = (EnderDragon)$$0;
/* 1598 */         for (EnderDragonPart $$5 : $$4.getSubEntities()) {
/* 1599 */           ServerLevel.this.dragonParts.put($$5.getId(), $$5);
/*      */         } }
/*      */ 
/*      */       
/* 1603 */       $$0.updateDynamicGameEventListener(DynamicGameEventListener::add);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onTrackingEnd(Entity $$0) {
/* 1608 */       ServerLevel.this.getChunkSource().removeEntity($$0);
/* 1609 */       if ($$0 instanceof ServerPlayer) { ServerPlayer $$1 = (ServerPlayer)$$0;
/* 1610 */         ServerLevel.this.players.remove($$1);
/* 1611 */         ServerLevel.this.updateSleepingPlayerList(); }
/*      */       
/* 1613 */       if ($$0 instanceof Mob) { Mob $$2 = (Mob)$$0;
/* 1614 */         if (ServerLevel.this.isUpdatingNavigations) {
/* 1615 */           String $$3 = "onTrackingStart called during navigation iteration";
/* 1616 */           Util.logAndPauseIfInIde("onTrackingStart called during navigation iteration", new IllegalStateException("onTrackingStart called during navigation iteration"));
/*      */         } 
/* 1618 */         ServerLevel.this.navigatingMobs.remove($$2); }
/*      */       
/* 1620 */       if ($$0 instanceof EnderDragon) { EnderDragon $$4 = (EnderDragon)$$0;
/* 1621 */         for (EnderDragonPart $$5 : $$4.getSubEntities()) {
/* 1622 */           ServerLevel.this.dragonParts.remove($$5.getId());
/*      */         } }
/*      */ 
/*      */       
/* 1626 */       $$0.updateDynamicGameEventListener(DynamicGameEventListener::remove);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onSectionChange(Entity $$0) {
/* 1631 */       $$0.updateDynamicGameEventListener(DynamicGameEventListener::move);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/* 1637 */     super.close();
/* 1638 */     this.entityManager.close();
/*      */   }
/*      */ 
/*      */   
/*      */   public String gatherChunkSourceStats() {
/* 1643 */     return "Chunks[S] W: " + this.chunkSource.gatherStats() + " E: " + this.entityManager.gatherStats();
/*      */   }
/*      */   
/*      */   public boolean areEntitiesLoaded(long $$0) {
/* 1647 */     return this.entityManager.areEntitiesLoaded($$0);
/*      */   }
/*      */   
/*      */   private boolean isPositionTickingWithEntitiesLoaded(long $$0) {
/* 1651 */     return (areEntitiesLoaded($$0) && this.chunkSource.isPositionTicking($$0));
/*      */   }
/*      */   
/*      */   public boolean isPositionEntityTicking(BlockPos $$0) {
/* 1655 */     return (this.entityManager.canPositionTick($$0) && this.chunkSource.chunkMap.getDistanceManager().inEntityTickingRange(ChunkPos.asLong($$0)));
/*      */   }
/*      */   
/*      */   public boolean isNaturalSpawningAllowed(BlockPos $$0) {
/* 1659 */     return this.entityManager.canPositionTick($$0);
/*      */   }
/*      */   
/*      */   public boolean isNaturalSpawningAllowed(ChunkPos $$0) {
/* 1663 */     return this.entityManager.canPositionTick($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public FeatureFlagSet enabledFeatures() {
/* 1668 */     return this.server.getWorldData().enabledFeatures();
/*      */   }
/*      */   
/*      */   public RandomSource getRandomSequence(ResourceLocation $$0) {
/* 1672 */     return this.randomSequences.get($$0);
/*      */   }
/*      */   
/*      */   public RandomSequences getRandomSequences() {
/* 1676 */     return this.randomSequences;
/*      */   }
/*      */ 
/*      */   
/*      */   public CrashReportCategory fillReportDetails(CrashReport $$0) {
/* 1681 */     CrashReportCategory $$1 = super.fillReportDetails($$0);
/* 1682 */     $$1.setDetail("Loaded entity count", () -> String.valueOf(this.entityManager.count()));
/* 1683 */     return $$1;
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ServerLevel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */