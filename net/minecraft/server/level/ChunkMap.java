/*      */ package net.minecraft.server.level;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Queues;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.gson.JsonElement;
/*      */ import com.mojang.datafixers.DataFixer;
/*      */ import com.mojang.datafixers.util.Either;
/*      */ import com.mojang.datafixers.util.Pair;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import com.mojang.serialization.DataResult;
/*      */ import com.mojang.serialization.DynamicOps;
/*      */ import com.mojang.serialization.JsonOps;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.longs.Long2ByteMap;
/*      */ import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.longs.Long2LongMap;
/*      */ import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*      */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*      */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*      */ import it.unimi.dsi.fastutil.longs.LongSet;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import java.io.IOException;
/*      */ import java.io.Writer;
/*      */ import java.nio.file.Path;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.CancellationException;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.CompletionException;
/*      */ import java.util.concurrent.CompletionStage;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.function.BooleanSupplier;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.IntFunction;
/*      */ import java.util.function.IntSupplier;
/*      */ import java.util.function.Supplier;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.CrashReport;
/*      */ import net.minecraft.CrashReportCategory;
/*      */ import net.minecraft.ReportedException;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.core.HolderGetter;
/*      */ import net.minecraft.core.HolderLookup;
/*      */ import net.minecraft.core.RegistryAccess;
/*      */ import net.minecraft.core.SectionPos;
/*      */ import net.minecraft.core.registries.Registries;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.network.protocol.game.ClientboundChunksBiomesPacket;
/*      */ import net.minecraft.server.level.progress.ChunkProgressListener;
/*      */ import net.minecraft.server.network.ServerPlayerConnection;
/*      */ import net.minecraft.util.CsvOutput;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.profiling.ProfilerFiller;
/*      */ import net.minecraft.util.thread.BlockableEventLoop;
/*      */ import net.minecraft.util.thread.ProcessorHandle;
/*      */ import net.minecraft.util.thread.ProcessorMailbox;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*      */ import net.minecraft.world.level.ChunkPos;
/*      */ import net.minecraft.world.level.GameRules;
/*      */ import net.minecraft.world.level.LevelHeightAccessor;
/*      */ import net.minecraft.world.level.chunk.ChunkAccess;
/*      */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*      */ import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
/*      */ import net.minecraft.world.level.chunk.ChunkStatus;
/*      */ import net.minecraft.world.level.chunk.ImposterProtoChunk;
/*      */ import net.minecraft.world.level.chunk.LevelChunk;
/*      */ import net.minecraft.world.level.chunk.LightChunkGetter;
/*      */ import net.minecraft.world.level.chunk.ProtoChunk;
/*      */ import net.minecraft.world.level.chunk.UpgradeData;
/*      */ import net.minecraft.world.level.chunk.storage.ChunkSerializer;
/*      */ import net.minecraft.world.level.chunk.storage.ChunkStorage;
/*      */ import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
/*      */ import net.minecraft.world.level.entity.EntityAccess;
/*      */ import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
/*      */ import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
/*      */ import net.minecraft.world.level.levelgen.RandomState;
/*      */ import net.minecraft.world.level.levelgen.structure.StructureStart;
/*      */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*      */ import net.minecraft.world.level.storage.DimensionDataStorage;
/*      */ import net.minecraft.world.level.storage.LevelStorageSource;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public class ChunkMap
/*      */   extends ChunkStorage implements ChunkHolder.PlayerProvider {
/*      */   private static final byte CHUNK_TYPE_REPLACEABLE = -1;
/*      */   private static final byte CHUNK_TYPE_UNKNOWN = 0;
/*      */   private static final byte CHUNK_TYPE_FULL = 1;
/*  108 */   private static final Logger LOGGER = LogUtils.getLogger();
/*      */   
/*      */   private static final int CHUNK_SAVED_PER_TICK = 200;
/*      */   
/*      */   private static final int CHUNK_SAVED_EAGERLY_PER_TICK = 20;
/*      */   
/*      */   private static final int EAGER_CHUNK_SAVE_COOLDOWN_IN_MILLIS = 10000;
/*      */   public static final int MIN_VIEW_DISTANCE = 2;
/*      */   public static final int MAX_VIEW_DISTANCE = 32;
/*  117 */   public static final int FORCED_TICKET_LEVEL = ChunkLevel.byStatus(FullChunkStatus.ENTITY_TICKING);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  122 */   private final Long2ObjectLinkedOpenHashMap<ChunkHolder> updatingChunkMap = new Long2ObjectLinkedOpenHashMap();
/*  123 */   private volatile Long2ObjectLinkedOpenHashMap<ChunkHolder> visibleChunkMap = this.updatingChunkMap.clone();
/*      */ 
/*      */ 
/*      */   
/*  127 */   private final Long2ObjectLinkedOpenHashMap<ChunkHolder> pendingUnloads = new Long2ObjectLinkedOpenHashMap();
/*      */ 
/*      */ 
/*      */   
/*  131 */   private final LongSet entitiesInLevel = (LongSet)new LongOpenHashSet();
/*      */   
/*      */   final ServerLevel level;
/*      */   
/*      */   private final ThreadedLevelLightEngine lightEngine;
/*      */   private final BlockableEventLoop<Runnable> mainThreadExecutor;
/*      */   private ChunkGenerator generator;
/*      */   private final RandomState randomState;
/*      */   private final ChunkGeneratorStructureState chunkGeneratorState;
/*      */   private final Supplier<DimensionDataStorage> overworldDataStorage;
/*      */   private final PoiManager poiManager;
/*  142 */   final LongSet toDrop = (LongSet)new LongOpenHashSet();
/*      */   
/*      */   private boolean modified;
/*      */   
/*      */   private final ChunkTaskPriorityQueueSorter queueSorter;
/*      */   
/*      */   private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> worldgenMailbox;
/*      */   private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> mainThreadMailbox;
/*      */   private final ChunkProgressListener progressListener;
/*      */   private final ChunkStatusUpdateListener chunkStatusListener;
/*      */   private final DistanceManager distanceManager;
/*  153 */   private final AtomicInteger tickingGenerated = new AtomicInteger();
/*      */   
/*      */   private final StructureTemplateManager structureTemplateManager;
/*      */   private final String storageName;
/*  157 */   private final PlayerMap playerMap = new PlayerMap();
/*  158 */   private final Int2ObjectMap<TrackedEntity> entityMap = (Int2ObjectMap<TrackedEntity>)new Int2ObjectOpenHashMap();
/*      */   
/*  160 */   private final Long2ByteMap chunkTypeCache = (Long2ByteMap)new Long2ByteOpenHashMap();
/*  161 */   private final Long2LongMap chunkSaveCooldowns = (Long2LongMap)new Long2LongOpenHashMap();
/*      */   
/*  163 */   private final Queue<Runnable> unloadQueue = Queues.newConcurrentLinkedQueue();
/*      */   
/*      */   private int serverViewDistance;
/*      */   
/*      */   public ChunkMap(ServerLevel $$0, LevelStorageSource.LevelStorageAccess $$1, DataFixer $$2, StructureTemplateManager $$3, Executor $$4, BlockableEventLoop<Runnable> $$5, LightChunkGetter $$6, ChunkGenerator $$7, ChunkProgressListener $$8, ChunkStatusUpdateListener $$9, Supplier<DimensionDataStorage> $$10, int $$11, boolean $$12) {
/*  168 */     super($$1.getDimensionPath($$0.dimension()).resolve("region"), $$2, $$12);
/*  169 */     this.structureTemplateManager = $$3;
/*  170 */     Path $$13 = $$1.getDimensionPath($$0.dimension());
/*  171 */     this.storageName = $$13.getFileName().toString();
/*  172 */     this.level = $$0;
/*  173 */     this.generator = $$7;
/*  174 */     RegistryAccess $$14 = $$0.registryAccess();
/*  175 */     long $$15 = $$0.getSeed();
/*  176 */     if ($$7 instanceof NoiseBasedChunkGenerator) { NoiseBasedChunkGenerator $$16 = (NoiseBasedChunkGenerator)$$7;
/*  177 */       this.randomState = RandomState.create((NoiseGeneratorSettings)$$16.generatorSettings().value(), (HolderGetter)$$14.lookupOrThrow(Registries.NOISE), $$15); }
/*      */     
/*      */     else
/*      */     
/*  181 */     { this.randomState = RandomState.create(NoiseGeneratorSettings.dummy(), (HolderGetter)$$14.lookupOrThrow(Registries.NOISE), $$15); }
/*      */     
/*  183 */     this.chunkGeneratorState = $$7.createState((HolderLookup)$$14.lookupOrThrow(Registries.STRUCTURE_SET), this.randomState, $$15);
/*  184 */     this.mainThreadExecutor = $$5;
/*      */     
/*  186 */     ProcessorMailbox<Runnable> $$17 = ProcessorMailbox.create($$4, "worldgen");
/*      */     
/*  188 */     Objects.requireNonNull($$5); ProcessorHandle<Runnable> $$18 = ProcessorHandle.of("main", $$5::tell);
/*  189 */     this.progressListener = $$8;
/*  190 */     this.chunkStatusListener = $$9;
/*  191 */     ProcessorMailbox<Runnable> $$19 = ProcessorMailbox.create($$4, "light");
/*      */     
/*  193 */     this.queueSorter = new ChunkTaskPriorityQueueSorter((List<ProcessorHandle<?>>)ImmutableList.of($$17, $$18, $$19), $$4, 2147483647);
/*      */     
/*  195 */     this.worldgenMailbox = this.queueSorter.getProcessor((ProcessorHandle<Runnable>)$$17, false);
/*  196 */     this.mainThreadMailbox = this.queueSorter.getProcessor($$18, false);
/*  197 */     this.lightEngine = new ThreadedLevelLightEngine($$6, this, this.level.dimensionType().hasSkyLight(), $$19, this.queueSorter.getProcessor((ProcessorHandle<Runnable>)$$19, false));
/*      */     
/*  199 */     this.distanceManager = new DistanceManager($$4, (Executor)$$5);
/*  200 */     this.overworldDataStorage = $$10;
/*  201 */     this.poiManager = new PoiManager($$13.resolve("poi"), $$2, $$12, $$14, (LevelHeightAccessor)$$0);
/*      */     
/*  203 */     setServerViewDistance($$11);
/*      */   }
/*      */   
/*      */   protected ChunkGenerator generator() {
/*  207 */     return this.generator;
/*      */   }
/*      */   
/*      */   protected ChunkGeneratorStructureState generatorState() {
/*  211 */     return this.chunkGeneratorState;
/*      */   }
/*      */   
/*      */   protected RandomState randomState() {
/*  215 */     return this.randomState;
/*      */   }
/*      */   
/*      */   public void debugReloadGenerator() {
/*  219 */     DataResult<JsonElement> $$0 = ChunkGenerator.CODEC.encodeStart((DynamicOps)JsonOps.INSTANCE, this.generator);
/*  220 */     DataResult<ChunkGenerator> $$1 = $$0.flatMap($$0 -> ChunkGenerator.CODEC.parse((DynamicOps)JsonOps.INSTANCE, $$0));
/*  221 */     $$1.result().ifPresent($$0 -> this.generator = $$0);
/*      */   }
/*      */   
/*      */   private static double euclideanDistanceSquared(ChunkPos $$0, Entity $$1) {
/*  225 */     double $$2 = SectionPos.sectionToBlockCoord($$0.x, 8);
/*  226 */     double $$3 = SectionPos.sectionToBlockCoord($$0.z, 8);
/*      */     
/*  228 */     double $$4 = $$2 - $$1.getX();
/*  229 */     double $$5 = $$3 - $$1.getZ();
/*      */     
/*  231 */     return $$4 * $$4 + $$5 * $$5;
/*      */   }
/*      */   
/*      */   boolean isChunkTracked(ServerPlayer $$0, int $$1, int $$2) {
/*  235 */     return ($$0.getChunkTrackingView().contains($$1, $$2) && !$$0.connection.chunkSender.isPending(ChunkPos.asLong($$1, $$2)));
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isChunkOnTrackedBorder(ServerPlayer $$0, int $$1, int $$2) {
/*  240 */     if (!isChunkTracked($$0, $$1, $$2)) {
/*  241 */       return false;
/*      */     }
/*  243 */     for (int $$3 = -1; $$3 <= 1; $$3++) {
/*  244 */       for (int $$4 = -1; $$4 <= 1; $$4++) {
/*  245 */         if ($$3 != 0 || $$4 != 0)
/*      */         {
/*      */           
/*  248 */           if (!isChunkTracked($$0, $$1 + $$3, $$2 + $$4))
/*  249 */             return true; 
/*      */         }
/*      */       } 
/*      */     } 
/*  253 */     return false;
/*      */   }
/*      */   
/*      */   protected ThreadedLevelLightEngine getLightEngine() {
/*  257 */     return this.lightEngine;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   protected ChunkHolder getUpdatingChunkIfPresent(long $$0) {
/*  262 */     return (ChunkHolder)this.updatingChunkMap.get($$0);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   protected ChunkHolder getVisibleChunkIfPresent(long $$0) {
/*  267 */     return (ChunkHolder)this.visibleChunkMap.get($$0);
/*      */   }
/*      */   
/*      */   protected IntSupplier getChunkQueueLevel(long $$0) {
/*  271 */     return () -> {
/*      */         ChunkHolder $$1 = getVisibleChunkIfPresent($$0);
/*      */         return ($$1 == null) ? (ChunkTaskPriorityQueue.PRIORITY_LEVEL_COUNT - 1) : Math.min($$1.getQueueLevel(), ChunkTaskPriorityQueue.PRIORITY_LEVEL_COUNT - 1);
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getChunkDebugData(ChunkPos $$0) {
/*  281 */     ChunkHolder $$1 = getVisibleChunkIfPresent($$0.toLong());
/*  282 */     if ($$1 == null) {
/*  283 */       return "null";
/*      */     }
/*  285 */     String $$2 = "" + $$1.getTicketLevel() + "\n";
/*  286 */     ChunkStatus $$3 = $$1.getLastAvailableStatus();
/*  287 */     ChunkAccess $$4 = $$1.getLastAvailable();
/*  288 */     if ($$3 != null) {
/*  289 */       $$2 = $$2 + "St: §" + $$2 + $$3.getIndex() + "§r\n";
/*      */     }
/*  291 */     if ($$4 != null) {
/*  292 */       $$2 = $$2 + "Ch: §" + $$2 + $$4.getStatus().getIndex() + "§r\n";
/*      */     }
/*  294 */     FullChunkStatus $$5 = $$1.getFullStatus();
/*  295 */     $$2 = $$2 + $$2 + String.valueOf('§') + $$5.ordinal();
/*  296 */     return $$2 + "§r";
/*      */   }
/*      */   
/*      */   private CompletableFuture<Either<List<ChunkAccess>, ChunkHolder.ChunkLoadingFailure>> getChunkRangeFuture(final ChunkHolder startX, final int range, final IntFunction<ChunkStatus> startZ) {
/*  300 */     if (range == 0) {
/*  301 */       ChunkStatus $$3 = startZ.apply(0);
/*  302 */       return startX.getOrScheduleFuture($$3, this).thenApply($$0 -> $$0.mapLeft(List::of));
/*      */     } 
/*      */     
/*  305 */     List<CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> $$4 = new ArrayList<>();
/*  306 */     List<ChunkHolder> $$5 = new ArrayList<>();
/*      */     
/*  308 */     final ChunkPos either = startX.getPos();
/*  309 */     int $$7 = $$6.x;
/*  310 */     final int finalI = $$6.z;
/*  311 */     for (int $$9 = -range; $$9 <= range; $$9++) {
/*  312 */       for (int $$10 = -range; $$10 <= range; $$10++) {
/*  313 */         int $$11 = Math.max(Math.abs($$10), Math.abs($$9));
/*  314 */         final ChunkPos k = new ChunkPos($$7 + $$10, $$8 + $$9);
/*  315 */         long $$13 = $$12.toLong();
/*  316 */         ChunkHolder $$14 = getUpdatingChunkIfPresent($$13);
/*  317 */         if ($$14 == null) {
/*  318 */           return CompletableFuture.completedFuture(Either.right(new ChunkHolder.ChunkLoadingFailure()
/*      */                 {
/*      */                   public String toString() {
/*  321 */                     return "Unloaded " + k;
/*      */                   }
/*      */                 }));
/*      */         }
/*  325 */         ChunkStatus $$15 = startZ.apply($$11);
/*  326 */         CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> $$16 = $$14.getOrScheduleFuture($$15, this);
/*  327 */         $$5.add($$14);
/*  328 */         $$4.add($$16);
/*      */       } 
/*      */     } 
/*  331 */     CompletableFuture<List<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> $$17 = Util.sequence($$4);
/*  332 */     CompletableFuture<Either<List<ChunkAccess>, ChunkHolder.ChunkLoadingFailure>> $$18 = $$17.thenApply($$3 -> {
/*      */           List<ChunkAccess> $$4 = Lists.newArrayList();
/*      */           int $$5 = 0;
/*      */           for (Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure> $$6 : (Iterable<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>)$$3) {
/*      */             if ($$6 == null) {
/*      */               throw debugFuturesAndCreateReportedException(new IllegalStateException("At least one of the chunk futures were null"), "n/a");
/*      */             }
/*      */             Optional<ChunkAccess> $$7 = $$6.left();
/*      */             if ($$7.isEmpty()) {
/*      */               int $$8 = $$5;
/*      */               return Either.right(new ChunkHolder.ChunkLoadingFailure()
/*      */                   {
/*      */                     public String toString() {
/*  345 */                       return "Unloaded " + new ChunkPos(startX + finalI % (range * 2 + 1), startZ + finalI / (range * 2 + 1)) + " " + either.right().get();
/*      */                     }
/*      */                   });
/*      */             } 
/*      */             
/*      */             $$4.add($$7.get());
/*      */             $$5++;
/*      */           } 
/*      */           return Either.left($$4);
/*      */         });
/*  355 */     for (ChunkHolder $$19 : $$5) {
/*  356 */       $$19.addSaveDependency("getChunkRangeFuture " + $$6 + " " + range, $$18);
/*      */     }
/*  358 */     return $$18;
/*      */   }
/*      */   
/*      */   public ReportedException debugFuturesAndCreateReportedException(IllegalStateException $$0, String $$1) {
/*  362 */     StringBuilder $$2 = new StringBuilder();
/*  363 */     Consumer<ChunkHolder> $$3 = $$1 -> $$1.getAllFutures().forEach(());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  372 */     $$2.append("Updating:").append(System.lineSeparator());
/*  373 */     this.updatingChunkMap.values().forEach($$3);
/*      */     
/*  375 */     $$2.append("Visible:").append(System.lineSeparator());
/*  376 */     this.visibleChunkMap.values().forEach($$3);
/*      */     
/*  378 */     CrashReport $$4 = CrashReport.forThrowable($$0, "Chunk loading");
/*  379 */     CrashReportCategory $$5 = $$4.addCategory("Chunk loading");
/*  380 */     $$5.setDetail("Details", $$1);
/*  381 */     $$5.setDetail("Futures", $$2);
/*  382 */     return new ReportedException($$4);
/*      */   }
/*      */   
/*      */   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> prepareEntityTickingChunk(ChunkHolder $$0) {
/*  386 */     return getChunkRangeFuture($$0, 2, $$0 -> ChunkStatus.FULL).thenApplyAsync($$0 -> $$0.mapLeft(()), (Executor)this.mainThreadExecutor);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   ChunkHolder updateChunkScheduling(long $$0, int $$1, @Nullable ChunkHolder $$2, int $$3) {
/*  391 */     if (!ChunkLevel.isLoaded($$3) && !ChunkLevel.isLoaded($$1)) {
/*  392 */       return $$2;
/*      */     }
/*      */     
/*  395 */     if ($$2 != null) {
/*  396 */       $$2.setTicketLevel($$1);
/*      */     }
/*      */     
/*  399 */     if ($$2 != null) {
/*  400 */       if (!ChunkLevel.isLoaded($$1)) {
/*  401 */         this.toDrop.add($$0);
/*      */       } else {
/*  403 */         this.toDrop.remove($$0);
/*      */       } 
/*      */     }
/*      */     
/*  407 */     if (ChunkLevel.isLoaded($$1) && 
/*  408 */       $$2 == null) {
/*  409 */       $$2 = (ChunkHolder)this.pendingUnloads.remove($$0);
/*      */       
/*  411 */       if ($$2 != null) {
/*  412 */         $$2.setTicketLevel($$1);
/*      */       } else {
/*  414 */         $$2 = new ChunkHolder(new ChunkPos($$0), $$1, (LevelHeightAccessor)this.level, this.lightEngine, this.queueSorter, this);
/*      */       } 
/*  416 */       this.updatingChunkMap.put($$0, $$2);
/*  417 */       this.modified = true;
/*      */     } 
/*      */     
/*  420 */     return $$2;
/*      */   }
/*      */ 
/*      */   
/*      */   public void close() throws IOException {
/*      */     try {
/*  426 */       this.queueSorter.close();
/*  427 */       this.poiManager.close();
/*      */     } finally {
/*  429 */       super.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void saveAllChunks(boolean $$0) {
/*  435 */     if ($$0) {
/*      */ 
/*      */ 
/*      */       
/*  439 */       List<ChunkHolder> $$1 = this.visibleChunkMap.values().stream().filter(ChunkHolder::wasAccessibleSinceLastSave).peek(ChunkHolder::refreshAccessibility).toList();
/*      */ 
/*      */       
/*  442 */       MutableBoolean $$2 = new MutableBoolean();
/*      */       do {
/*  444 */         $$2.setFalse();
/*  445 */         $$1.stream()
/*  446 */           .map($$0 -> {
/*      */               while (true) {
/*      */                 CompletableFuture<ChunkAccess> $$1 = $$0.getChunkToSave();
/*      */                 Objects.requireNonNull($$1);
/*      */                 this.mainThreadExecutor.managedBlock($$1::isDone);
/*      */                 if ($$1 == $$0.getChunkToSave())
/*      */                   return $$1.join(); 
/*      */               } 
/*  454 */             }).filter($$0 -> ($$0 instanceof ImposterProtoChunk || $$0 instanceof LevelChunk))
/*  455 */           .filter(this::save)
/*  456 */           .forEach($$1 -> $$0.setTrue());
/*  457 */       } while ($$2.isTrue());
/*      */       
/*  459 */       processUnloads(() -> true);
/*  460 */       flushWorker();
/*      */     } else {
/*      */       
/*  463 */       this.visibleChunkMap.values().forEach(this::saveChunkIfNeeded);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void tick(BooleanSupplier $$0) {
/*  468 */     ProfilerFiller $$1 = this.level.getProfiler();
/*  469 */     $$1.push("poi");
/*  470 */     this.poiManager.tick($$0);
/*  471 */     $$1.popPush("chunk_unload");
/*  472 */     if (!this.level.noSave()) {
/*  473 */       processUnloads($$0);
/*      */     }
/*  475 */     $$1.pop();
/*      */   }
/*      */   
/*      */   public boolean hasWork() {
/*  479 */     return (this.lightEngine.hasLightWork() || 
/*  480 */       !this.pendingUnloads.isEmpty() || 
/*  481 */       !this.updatingChunkMap.isEmpty() || this.poiManager
/*  482 */       .hasWork() || 
/*  483 */       !this.toDrop.isEmpty() || 
/*  484 */       !this.unloadQueue.isEmpty() || this.queueSorter
/*  485 */       .hasWork() || this.distanceManager
/*  486 */       .hasTickets());
/*      */   }
/*      */   
/*      */   private void processUnloads(BooleanSupplier $$0) {
/*  490 */     LongIterator $$1 = this.toDrop.iterator();
/*  491 */     int $$2 = 0;
/*  492 */     while ($$1.hasNext() && ($$0.getAsBoolean() || $$2 < 200 || this.toDrop.size() > 2000)) {
/*  493 */       long $$3 = $$1.nextLong();
/*      */       
/*  495 */       ChunkHolder $$4 = (ChunkHolder)this.updatingChunkMap.remove($$3);
/*  496 */       if ($$4 != null) {
/*  497 */         this.pendingUnloads.put($$3, $$4);
/*  498 */         this.modified = true;
/*  499 */         $$2++;
/*  500 */         scheduleUnload($$3, $$4);
/*      */       } 
/*  502 */       $$1.remove();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  507 */     int $$5 = Math.max(0, this.unloadQueue.size() - 2000); Runnable $$6;
/*  508 */     while (($$0.getAsBoolean() || $$5 > 0) && ($$6 = this.unloadQueue.poll()) != null) {
/*  509 */       $$5--;
/*      */       
/*  511 */       $$6.run();
/*      */     } 
/*      */ 
/*      */     
/*  515 */     int $$7 = 0;
/*  516 */     ObjectIterator<ChunkHolder> $$8 = this.visibleChunkMap.values().iterator();
/*  517 */     while ($$7 < 20 && $$0.getAsBoolean() && $$8.hasNext()) {
/*  518 */       if (saveChunkIfNeeded((ChunkHolder)$$8.next())) {
/*  519 */         $$7++;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void scheduleUnload(long $$0, ChunkHolder $$1) {
/*  525 */     CompletableFuture<ChunkAccess> $$2 = $$1.getChunkToSave();
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
/*  549 */     Objects.requireNonNull(this.unloadQueue); $$2.thenAcceptAsync($$3 -> { CompletableFuture<ChunkAccess> $$4 = $$0.getChunkToSave(); if ($$4 != $$1) { scheduleUnload($$2, $$0); return; }  if (this.pendingUnloads.remove($$2, $$0) && $$3 != null) { if ($$3 instanceof LevelChunk) ((LevelChunk)$$3).setLoaded(false);  save($$3); if (this.entitiesInLevel.remove($$2) && $$3 instanceof LevelChunk) { LevelChunk $$5 = (LevelChunk)$$3; this.level.unload($$5); }  this.lightEngine.updateChunkStatus($$3.getPos()); this.lightEngine.tryScheduleUpdate(); this.progressListener.onStatusChange($$3.getPos(), null); this.chunkSaveCooldowns.remove($$3.getPos().toLong()); }  }this.unloadQueue::add).whenComplete(($$1, $$2) -> {
/*      */           if ($$2 != null) {
/*      */             LOGGER.error("Failed to save chunk {}", $$0.getPos(), $$2);
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   protected boolean promoteChunkMap() {
/*  557 */     if (!this.modified) {
/*  558 */       return false;
/*      */     }
/*      */     
/*  561 */     this.visibleChunkMap = this.updatingChunkMap.clone();
/*  562 */     this.modified = false;
/*  563 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> schedule(ChunkHolder $$0, ChunkStatus $$1) {
/*  571 */     ChunkPos $$2 = $$0.getPos();
/*  572 */     if ($$1 == ChunkStatus.EMPTY) {
/*  573 */       return scheduleChunkLoad($$2);
/*      */     }
/*      */     
/*  576 */     if ($$1 == ChunkStatus.LIGHT)
/*      */     {
/*      */ 
/*      */       
/*  580 */       this.distanceManager.addTicket(TicketType.LIGHT, $$2, ChunkLevel.byStatus(ChunkStatus.LIGHT), $$2);
/*      */     }
/*      */     
/*  583 */     if (!$$1.hasLoadDependencies()) {
/*  584 */       Optional<ChunkAccess> $$3 = ((Either)$$0.getOrScheduleFuture($$1.getParent(), this).getNow(ChunkHolder.UNLOADED_CHUNK)).left();
/*      */       
/*  586 */       if ($$3.isPresent() && ((ChunkAccess)$$3.get()).getStatus().isOrAfter($$1)) {
/*      */         
/*  588 */         CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> $$4 = $$1.load(this.level, this.structureTemplateManager, this.lightEngine, $$1 -> protoChunkToFullChunk($$0), $$3.get());
/*  589 */         this.progressListener.onStatusChange($$2, $$1);
/*  590 */         return $$4;
/*      */       } 
/*      */     } 
/*      */     
/*  594 */     return scheduleChunkGeneration($$0, $$1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> scheduleChunkLoad(ChunkPos $$0) {
/*  601 */     return readChunk($$0)
/*  602 */       .thenApply($$1 -> $$1.filter(()))
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  609 */       .thenApplyAsync($$1 -> {
/*      */           this.level.getProfiler().incrementCounter("chunkLoad");
/*      */           
/*      */           if ($$1.isPresent()) {
/*      */             ProtoChunk protoChunk = ChunkSerializer.read(this.level, this.poiManager, $$0, $$1.get());
/*      */             markPosition($$0, protoChunk.getStatus().getChunkType());
/*      */             return Either.left(protoChunk);
/*      */           } 
/*      */           return Either.left(createEmptyChunk($$0));
/*  618 */         }(Executor)this.mainThreadExecutor).exceptionallyAsync($$1 -> handleChunkLoadFailure($$1, $$0), (Executor)this.mainThreadExecutor);
/*      */   }
/*      */   
/*      */   private static boolean isChunkDataValid(CompoundTag $$0) {
/*  622 */     return $$0.contains("Status", 8);
/*      */   }
/*      */   
/*      */   private Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure> handleChunkLoadFailure(Throwable $$0, ChunkPos $$1) {
/*  626 */     if ($$0 instanceof ReportedException) { ReportedException $$2 = (ReportedException)$$0;
/*  627 */       Throwable $$3 = $$2.getCause();
/*  628 */       if ($$3 instanceof IOException) {
/*  629 */         LOGGER.error("Couldn't load chunk {}", $$1, $$3);
/*      */       } else {
/*  631 */         markPositionReplaceable($$1);
/*  632 */         throw $$2;
/*      */       }  }
/*  634 */     else if ($$0 instanceof IOException)
/*  635 */     { LOGGER.error("Couldn't load chunk {}", $$1, $$0); }
/*      */ 
/*      */     
/*  638 */     return Either.left(createEmptyChunk($$1));
/*      */   }
/*      */   
/*      */   private ChunkAccess createEmptyChunk(ChunkPos $$0) {
/*  642 */     markPositionReplaceable($$0);
/*  643 */     return (ChunkAccess)new ProtoChunk($$0, UpgradeData.EMPTY, (LevelHeightAccessor)this.level, this.level.registryAccess().registryOrThrow(Registries.BIOME), null);
/*      */   }
/*      */   
/*      */   private void markPositionReplaceable(ChunkPos $$0) {
/*  647 */     this.chunkTypeCache.put($$0.toLong(), (byte)-1);
/*      */   }
/*      */   
/*      */   private byte markPosition(ChunkPos $$0, ChunkStatus.ChunkType $$1) {
/*  651 */     return this.chunkTypeCache.put($$0.toLong(), ($$1 == ChunkStatus.ChunkType.PROTOCHUNK) ? -1 : 1);
/*      */   }
/*      */   
/*      */   private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> scheduleChunkGeneration(ChunkHolder $$0, ChunkStatus $$1) {
/*  655 */     ChunkPos $$2 = $$0.getPos();
/*      */     
/*  657 */     CompletableFuture<Either<List<ChunkAccess>, ChunkHolder.ChunkLoadingFailure>> $$3 = getChunkRangeFuture($$0, $$1.getRange(), $$1 -> getDependencyStatus($$0, $$1));
/*  658 */     this.level.getProfiler().incrementCounter(() -> "chunkGenerate " + $$0);
/*  659 */     Executor $$4 = $$1 -> this.worldgenMailbox.tell(ChunkTaskPriorityQueueSorter.message($$0, $$1));
/*  660 */     return $$3.thenComposeAsync($$4 -> (CompletionStage)$$4.map((), ()), $$4);
/*      */   }
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
/*      */   protected void releaseLightTicket(ChunkPos $$0) {
/*  699 */     this.mainThreadExecutor.tell(Util.name(() -> this.distanceManager.removeTicket(TicketType.LIGHT, $$0, ChunkLevel.byStatus(ChunkStatus.LIGHT), $$0), () -> "release light ticket " + $$0));
/*      */   }
/*      */   
/*      */   private ChunkStatus getDependencyStatus(ChunkStatus $$0, int $$1) {
/*      */     ChunkStatus $$3;
/*  704 */     if ($$1 == 0) {
/*  705 */       ChunkStatus $$2 = $$0.getParent();
/*      */     } else {
/*  707 */       $$3 = ChunkStatus.getStatusAroundFullChunk(ChunkStatus.getDistance($$0) + $$1);
/*      */     } 
/*  709 */     return $$3;
/*      */   }
/*      */   
/*      */   private static void postLoadProtoChunk(ServerLevel $$0, List<CompoundTag> $$1) {
/*  713 */     if (!$$1.isEmpty()) {
/*  714 */       $$0.addWorldGenChunkEntities(EntityType.loadEntitiesRecursive($$1, $$0));
/*      */     }
/*      */   }
/*      */   
/*      */   private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> protoChunkToFullChunk(ChunkHolder $$0) {
/*  719 */     CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> $$1 = $$0.getFutureIfPresentUnchecked(ChunkStatus.FULL.getParent());
/*  720 */     return $$1.thenApplyAsync($$1 -> {
/*      */           ChunkStatus $$2 = ChunkLevel.generationStatus($$0.getTicketLevel());
/*      */           return !$$2.isOrAfter(ChunkStatus.FULL) ? ChunkHolder.UNLOADED_CHUNK : $$1.mapLeft(());
/*      */         }$$1 -> {
/*      */           Objects.requireNonNull($$0);
/*      */           this.mainThreadMailbox.tell(ChunkTaskPriorityQueueSorter.message($$1, $$0.getPos().toLong(), $$0::getTicketLevel));
/*      */         });
/*      */   }
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
/*      */   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> prepareTickingChunk(ChunkHolder $$0) {
/*  750 */     CompletableFuture<Either<List<ChunkAccess>, ChunkHolder.ChunkLoadingFailure>> $$1 = getChunkRangeFuture($$0, 1, $$0 -> ChunkStatus.FULL);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  757 */     CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> $$2 = $$1.thenApplyAsync($$0 -> $$0.mapLeft(()), $$1 -> this.mainThreadMailbox.tell(ChunkTaskPriorityQueueSorter.message($$0, $$1))).thenApplyAsync($$1 -> $$1.ifLeft(()), (Executor)this.mainThreadExecutor);
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
/*  771 */     $$2.handle(($$0, $$1) -> {
/*      */           this.tickingGenerated.getAndIncrement();
/*      */ 
/*      */           
/*      */           return null;
/*      */         });
/*      */ 
/*      */     
/*  779 */     return $$2;
/*      */   }
/*      */   
/*      */   private void onChunkReadyToSend(LevelChunk $$0) {
/*  783 */     ChunkPos $$1 = $$0.getPos();
/*  784 */     for (ServerPlayer $$2 : this.playerMap.getAllPlayers()) {
/*  785 */       if ($$2.getChunkTrackingView().contains($$1)) {
/*  786 */         markChunkPendingToSend($$2, $$0);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> prepareAccessibleChunk(ChunkHolder $$0) {
/*  792 */     return getChunkRangeFuture($$0, 1, ChunkStatus::getStatusAroundFullChunk)
/*  793 */       .thenApplyAsync($$0 -> $$0.mapLeft(()), $$1 -> this.mainThreadMailbox.tell(ChunkTaskPriorityQueueSorter.message($$0, $$1)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTickingGenerated() {
/*  800 */     return this.tickingGenerated.get();
/*      */   }
/*      */   
/*      */   private boolean saveChunkIfNeeded(ChunkHolder $$0) {
/*  804 */     if (!$$0.wasAccessibleSinceLastSave()) {
/*  805 */       return false;
/*      */     }
/*      */     
/*  808 */     ChunkAccess $$1 = $$0.getChunkToSave().getNow(null);
/*  809 */     if ($$1 instanceof ImposterProtoChunk || $$1 instanceof LevelChunk) {
/*  810 */       long $$2 = $$1.getPos().toLong();
/*  811 */       long $$3 = this.chunkSaveCooldowns.getOrDefault($$2, -1L);
/*  812 */       long $$4 = System.currentTimeMillis();
/*  813 */       if ($$4 < $$3) {
/*  814 */         return false;
/*      */       }
/*  816 */       boolean $$5 = save($$1);
/*  817 */       $$0.refreshAccessibility();
/*  818 */       if ($$5) {
/*  819 */         this.chunkSaveCooldowns.put($$2, $$4 + 10000L);
/*      */       }
/*  821 */       return $$5;
/*      */     } 
/*  823 */     return false;
/*      */   }
/*      */   
/*      */   private boolean save(ChunkAccess $$0) {
/*  827 */     this.poiManager.flush($$0.getPos());
/*      */     
/*  829 */     if (!$$0.isUnsaved()) {
/*  830 */       return false;
/*      */     }
/*      */     
/*  833 */     $$0.setUnsaved(false);
/*      */     
/*  835 */     ChunkPos $$1 = $$0.getPos();
/*      */     try {
/*  837 */       ChunkStatus $$2 = $$0.getStatus();
/*      */       
/*  839 */       if ($$2.getChunkType() != ChunkStatus.ChunkType.LEVELCHUNK) {
/*  840 */         if (isExistingChunkFull($$1))
/*      */         {
/*  842 */           return false;
/*      */         }
/*      */ 
/*      */         
/*  846 */         if ($$2 == ChunkStatus.EMPTY && $$0.getAllStarts().values().stream().noneMatch(StructureStart::isValid)) {
/*  847 */           return false;
/*      */         }
/*      */       } 
/*      */       
/*  851 */       this.level.getProfiler().incrementCounter("chunkSave");
/*  852 */       CompoundTag $$3 = ChunkSerializer.write(this.level, $$0);
/*  853 */       write($$1, $$3);
/*  854 */       markPosition($$1, $$2.getChunkType());
/*  855 */       return true;
/*  856 */     } catch (Exception $$4) {
/*  857 */       LOGGER.error("Failed to save chunk {},{}", new Object[] { Integer.valueOf($$1.x), Integer.valueOf($$1.z), $$4 });
/*      */       
/*  859 */       return false;
/*      */     } 
/*      */   } private boolean isExistingChunkFull(ChunkPos $$0) {
/*      */     CompoundTag $$2;
/*  863 */     byte $$1 = this.chunkTypeCache.get($$0.toLong());
/*  864 */     if ($$1 != 0) {
/*  865 */       return ($$1 == 1);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  870 */       $$2 = ((Optional<CompoundTag>)readChunk($$0).join()).orElse(null);
/*  871 */       if ($$2 == null) {
/*  872 */         markPositionReplaceable($$0);
/*  873 */         return false;
/*      */       } 
/*  875 */     } catch (Exception $$3) {
/*  876 */       LOGGER.error("Failed to read chunk {}", $$0, $$3);
/*  877 */       markPositionReplaceable($$0);
/*  878 */       return false;
/*      */     } 
/*      */     
/*  881 */     ChunkStatus.ChunkType $$5 = ChunkSerializer.getChunkTypeFromTag($$2);
/*  882 */     return (markPosition($$0, $$5) == 1);
/*      */   }
/*      */   
/*      */   protected void setServerViewDistance(int $$0) {
/*  886 */     int $$1 = Mth.clamp($$0, 2, 32);
/*  887 */     if ($$1 != this.serverViewDistance) {
/*  888 */       this.serverViewDistance = $$1;
/*  889 */       this.distanceManager.updatePlayerTickets(this.serverViewDistance);
/*  890 */       for (ServerPlayer $$2 : this.playerMap.getAllPlayers()) {
/*  891 */         updateChunkTracking($$2);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   int getPlayerViewDistance(ServerPlayer $$0) {
/*  897 */     return Mth.clamp($$0.requestedViewDistance(), 2, this.serverViewDistance);
/*      */   }
/*      */   
/*      */   private void markChunkPendingToSend(ServerPlayer $$0, ChunkPos $$1) {
/*  901 */     LevelChunk $$2 = getChunkToSend($$1.toLong());
/*  902 */     if ($$2 != null) {
/*  903 */       markChunkPendingToSend($$0, $$2);
/*      */     }
/*      */   }
/*      */   
/*      */   private static void markChunkPendingToSend(ServerPlayer $$0, LevelChunk $$1) {
/*  908 */     $$0.connection.chunkSender.markChunkPendingToSend($$1);
/*      */   }
/*      */   
/*      */   private static void dropChunk(ServerPlayer $$0, ChunkPos $$1) {
/*  912 */     $$0.connection.chunkSender.dropChunk($$0, $$1);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public LevelChunk getChunkToSend(long $$0) {
/*  917 */     ChunkHolder $$1 = getVisibleChunkIfPresent($$0);
/*  918 */     if ($$1 == null) {
/*  919 */       return null;
/*      */     }
/*  921 */     return $$1.getChunkToSend();
/*      */   }
/*      */   
/*      */   public int size() {
/*  925 */     return this.visibleChunkMap.size();
/*      */   }
/*      */   
/*      */   public DistanceManager getDistanceManager() {
/*  929 */     return this.distanceManager;
/*      */   }
/*      */   
/*      */   protected Iterable<ChunkHolder> getChunks() {
/*  933 */     return Iterables.unmodifiableIterable((Iterable)this.visibleChunkMap.values());
/*      */   }
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
/*      */   void dumpChunks(Writer $$0) throws IOException {
/*  954 */     CsvOutput $$1 = CsvOutput.builder().addColumn("x").addColumn("z").addColumn("level").addColumn("in_memory").addColumn("status").addColumn("full_status").addColumn("accessible_ready").addColumn("ticking_ready").addColumn("entity_ticking_ready").addColumn("ticket").addColumn("spawning").addColumn("block_entity_count").addColumn("ticking_ticket").addColumn("ticking_level").addColumn("block_ticks").addColumn("fluid_ticks").build($$0);
/*      */     
/*  956 */     TickingTracker $$2 = this.distanceManager.tickingTracker();
/*      */     
/*  958 */     for (ObjectBidirectionalIterator<Long2ObjectMap.Entry<ChunkHolder>> objectBidirectionalIterator = this.visibleChunkMap.long2ObjectEntrySet().iterator(); objectBidirectionalIterator.hasNext(); ) { Long2ObjectMap.Entry<ChunkHolder> $$3 = objectBidirectionalIterator.next();
/*  959 */       long $$4 = $$3.getLongKey();
/*  960 */       ChunkPos $$5 = new ChunkPos($$4);
/*  961 */       ChunkHolder $$6 = (ChunkHolder)$$3.getValue();
/*  962 */       Optional<ChunkAccess> $$7 = Optional.ofNullable($$6.getLastAvailable());
/*  963 */       Optional<LevelChunk> $$8 = $$7.flatMap($$0 -> ($$0 instanceof LevelChunk) ? Optional.<LevelChunk>of((LevelChunk)$$0) : Optional.empty());
/*  964 */       $$1.writeRow(new Object[] { 
/*  965 */             Integer.valueOf($$5.x), 
/*  966 */             Integer.valueOf($$5.z), 
/*  967 */             Integer.valueOf($$6.getTicketLevel()), 
/*  968 */             Boolean.valueOf($$7.isPresent()), $$7
/*  969 */             .map(ChunkAccess::getStatus).orElse(null), $$8
/*  970 */             .map(LevelChunk::getFullStatus).orElse(null), 
/*  971 */             printFuture($$6.getFullChunkFuture()), 
/*  972 */             printFuture($$6.getTickingChunkFuture()), 
/*  973 */             printFuture($$6.getEntityTickingChunkFuture()), this.distanceManager
/*  974 */             .getTicketDebugString($$4), 
/*  975 */             Boolean.valueOf(anyPlayerCloseEnoughForSpawning($$5)), $$8
/*  976 */             .<Integer>map($$0 -> Integer.valueOf($$0.getBlockEntities().size())).orElse(Integer.valueOf(0)), $$2
/*  977 */             .getTicketDebugString($$4), 
/*  978 */             Integer.valueOf($$2.getLevel($$4)), $$8
/*  979 */             .<Integer>map($$0 -> Integer.valueOf($$0.getBlockTicks().count())).orElse(Integer.valueOf(0)), $$8
/*  980 */             .<Integer>map($$0 -> Integer.valueOf($$0.getFluidTicks().count())).orElse(Integer.valueOf(0)) }); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   private static String printFuture(CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> $$0) {
/*      */     try {
/*  987 */       Either<LevelChunk, ChunkHolder.ChunkLoadingFailure> $$1 = $$0.getNow(null);
/*  988 */       if ($$1 != null) {
/*  989 */         return (String)$$1.map($$0 -> "done", $$0 -> "unloaded");
/*      */       }
/*  991 */       return "not completed";
/*      */     }
/*  993 */     catch (CompletionException $$2) {
/*  994 */       return "failed " + $$2.getCause().getMessage();
/*  995 */     } catch (CancellationException $$3) {
/*  996 */       return "cancelled";
/*      */     } 
/*      */   }
/*      */   
/*      */   private CompletableFuture<Optional<CompoundTag>> readChunk(ChunkPos $$0) {
/* 1001 */     return read($$0).thenApplyAsync($$0 -> $$0.map(this::upgradeChunkTag), Util.backgroundExecutor());
/*      */   }
/*      */   
/*      */   private CompoundTag upgradeChunkTag(CompoundTag $$0) {
/* 1005 */     return upgradeChunkTag(this.level.dimension(), this.overworldDataStorage, $$0, this.generator.getTypeNameForDataFixer());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   boolean anyPlayerCloseEnoughForSpawning(ChunkPos $$0) {
/* 1012 */     if (!this.distanceManager.hasPlayersNearby($$0.toLong())) {
/* 1013 */       return false;
/*      */     }
/* 1015 */     for (ServerPlayer $$1 : this.playerMap.getAllPlayers()) {
/* 1016 */       if (playerIsCloseEnoughForSpawning($$1, $$0)) {
/* 1017 */         return true;
/*      */       }
/*      */     } 
/* 1020 */     return false;
/*      */   }
/*      */   
/*      */   public List<ServerPlayer> getPlayersCloseForSpawning(ChunkPos $$0) {
/* 1024 */     long $$1 = $$0.toLong();
/*      */ 
/*      */     
/* 1027 */     if (!this.distanceManager.hasPlayersNearby($$1)) {
/* 1028 */       return List.of();
/*      */     }
/*      */     
/* 1031 */     ImmutableList.Builder<ServerPlayer> $$2 = ImmutableList.builder();
/* 1032 */     for (ServerPlayer $$3 : this.playerMap.getAllPlayers()) {
/* 1033 */       if (playerIsCloseEnoughForSpawning($$3, $$0)) {
/* 1034 */         $$2.add($$3);
/*      */       }
/*      */     } 
/* 1037 */     return (List<ServerPlayer>)$$2.build();
/*      */   }
/*      */   
/*      */   private boolean playerIsCloseEnoughForSpawning(ServerPlayer $$0, ChunkPos $$1) {
/* 1041 */     if ($$0.isSpectator()) {
/* 1042 */       return false;
/*      */     }
/* 1044 */     double $$2 = euclideanDistanceSquared($$1, (Entity)$$0);
/* 1045 */     return ($$2 < 16384.0D);
/*      */   }
/*      */   
/*      */   private boolean skipPlayer(ServerPlayer $$0) {
/* 1049 */     return ($$0.isSpectator() && !this.level.getGameRules().getBoolean(GameRules.RULE_SPECTATORSGENERATECHUNKS));
/*      */   }
/*      */   
/*      */   void updatePlayerStatus(ServerPlayer $$0, boolean $$1) {
/* 1053 */     boolean $$2 = skipPlayer($$0);
/* 1054 */     boolean $$3 = this.playerMap.ignoredOrUnknown($$0);
/* 1055 */     if ($$1) {
/* 1056 */       this.playerMap.addPlayer($$0, $$2);
/* 1057 */       updatePlayerPos($$0);
/*      */       
/* 1059 */       if (!$$2) {
/* 1060 */         this.distanceManager.addPlayer(SectionPos.of((EntityAccess)$$0), $$0);
/*      */       }
/* 1062 */       $$0.setChunkTrackingView(ChunkTrackingView.EMPTY);
/* 1063 */       updateChunkTracking($$0);
/*      */     } else {
/* 1065 */       SectionPos $$4 = $$0.getLastSectionPos();
/* 1066 */       this.playerMap.removePlayer($$0);
/* 1067 */       if (!$$3) {
/* 1068 */         this.distanceManager.removePlayer($$4, $$0);
/*      */       }
/* 1070 */       applyChunkTrackingView($$0, ChunkTrackingView.EMPTY);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updatePlayerPos(ServerPlayer $$0) {
/* 1075 */     SectionPos $$1 = SectionPos.of((EntityAccess)$$0);
/* 1076 */     $$0.setLastSectionPos($$1);
/*      */   }
/*      */   
/*      */   public void move(ServerPlayer $$0) {
/* 1080 */     for (ObjectIterator<TrackedEntity> objectIterator = this.entityMap.values().iterator(); objectIterator.hasNext(); ) { TrackedEntity $$1 = objectIterator.next();
/* 1081 */       if ($$1.entity == $$0) {
/* 1082 */         $$1.updatePlayers(this.level.players()); continue;
/*      */       } 
/* 1084 */       $$1.updatePlayer($$0); }
/*      */ 
/*      */ 
/*      */     
/* 1088 */     SectionPos $$2 = $$0.getLastSectionPos();
/* 1089 */     SectionPos $$3 = SectionPos.of((EntityAccess)$$0);
/*      */     
/* 1091 */     boolean $$4 = this.playerMap.ignored($$0);
/* 1092 */     boolean $$5 = skipPlayer($$0);
/* 1093 */     boolean $$6 = ($$2.asLong() != $$3.asLong());
/* 1094 */     if ($$6 || $$4 != $$5) {
/* 1095 */       updatePlayerPos($$0);
/*      */       
/* 1097 */       if (!$$4) {
/* 1098 */         this.distanceManager.removePlayer($$2, $$0);
/*      */       }
/*      */       
/* 1101 */       if (!$$5) {
/* 1102 */         this.distanceManager.addPlayer($$3, $$0);
/*      */       }
/*      */       
/* 1105 */       if (!$$4 && $$5) {
/* 1106 */         this.playerMap.ignorePlayer($$0);
/*      */       }
/*      */       
/* 1109 */       if ($$4 && !$$5) {
/* 1110 */         this.playerMap.unIgnorePlayer($$0);
/*      */       }
/*      */       
/* 1113 */       updateChunkTracking($$0);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateChunkTracking(ServerPlayer $$0) {
/* 1118 */     ChunkPos $$1 = $$0.chunkPosition();
/* 1119 */     int $$2 = getPlayerViewDistance($$0);
/* 1120 */     ChunkTrackingView chunkTrackingView = $$0.getChunkTrackingView(); if (chunkTrackingView instanceof ChunkTrackingView.Positioned) { ChunkTrackingView.Positioned $$3 = (ChunkTrackingView.Positioned)chunkTrackingView; if ($$3.center().equals($$1) && $$3.viewDistance() == $$2)
/*      */         return;  }
/*      */     
/* 1123 */     applyChunkTrackingView($$0, ChunkTrackingView.of($$1, $$2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void applyChunkTrackingView(ServerPlayer $$0, ChunkTrackingView $$1) {
/*      */     // Byte code:
/*      */     //   0: aload_1
/*      */     //   1: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*      */     //   4: aload_0
/*      */     //   5: getfield level : Lnet/minecraft/server/level/ServerLevel;
/*      */     //   8: if_acmpeq -> 12
/*      */     //   11: return
/*      */     //   12: aload_1
/*      */     //   13: invokevirtual getChunkTrackingView : ()Lnet/minecraft/server/level/ChunkTrackingView;
/*      */     //   16: astore_3
/*      */     //   17: aload_2
/*      */     //   18: instanceof net/minecraft/server/level/ChunkTrackingView$Positioned
/*      */     //   21: ifeq -> 89
/*      */     //   24: aload_2
/*      */     //   25: checkcast net/minecraft/server/level/ChunkTrackingView$Positioned
/*      */     //   28: astore #4
/*      */     //   30: aload_3
/*      */     //   31: instanceof net/minecraft/server/level/ChunkTrackingView$Positioned
/*      */     //   34: ifeq -> 59
/*      */     //   37: aload_3
/*      */     //   38: checkcast net/minecraft/server/level/ChunkTrackingView$Positioned
/*      */     //   41: astore #5
/*      */     //   43: aload #5
/*      */     //   45: invokevirtual center : ()Lnet/minecraft/world/level/ChunkPos;
/*      */     //   48: aload #4
/*      */     //   50: invokevirtual center : ()Lnet/minecraft/world/level/ChunkPos;
/*      */     //   53: invokevirtual equals : (Ljava/lang/Object;)Z
/*      */     //   56: ifne -> 89
/*      */     //   59: aload_1
/*      */     //   60: getfield connection : Lnet/minecraft/server/network/ServerGamePacketListenerImpl;
/*      */     //   63: new net/minecraft/network/protocol/game/ClientboundSetChunkCacheCenterPacket
/*      */     //   66: dup
/*      */     //   67: aload #4
/*      */     //   69: invokevirtual center : ()Lnet/minecraft/world/level/ChunkPos;
/*      */     //   72: getfield x : I
/*      */     //   75: aload #4
/*      */     //   77: invokevirtual center : ()Lnet/minecraft/world/level/ChunkPos;
/*      */     //   80: getfield z : I
/*      */     //   83: invokespecial <init> : (II)V
/*      */     //   86: invokevirtual send : (Lnet/minecraft/network/protocol/Packet;)V
/*      */     //   89: aload_3
/*      */     //   90: aload_2
/*      */     //   91: aload_0
/*      */     //   92: aload_1
/*      */     //   93: <illegal opcode> accept : (Lnet/minecraft/server/level/ChunkMap;Lnet/minecraft/server/level/ServerPlayer;)Ljava/util/function/Consumer;
/*      */     //   98: aload_1
/*      */     //   99: <illegal opcode> accept : (Lnet/minecraft/server/level/ServerPlayer;)Ljava/util/function/Consumer;
/*      */     //   104: invokestatic difference : (Lnet/minecraft/server/level/ChunkTrackingView;Lnet/minecraft/server/level/ChunkTrackingView;Ljava/util/function/Consumer;Ljava/util/function/Consumer;)V
/*      */     //   107: aload_1
/*      */     //   108: aload_2
/*      */     //   109: invokevirtual setChunkTrackingView : (Lnet/minecraft/server/level/ChunkTrackingView;)V
/*      */     //   112: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1130	-> 0
/*      */     //   #1131	-> 11
/*      */     //   #1133	-> 12
/*      */     //   #1134	-> 17
/*      */     //   #1135	-> 59
/*      */     //   #1137	-> 89
/*      */     //   #1142	-> 107
/*      */     //   #1143	-> 112
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	113	0	this	Lnet/minecraft/server/level/ChunkMap;
/*      */     //   0	113	1	$$0	Lnet/minecraft/server/level/ServerPlayer;
/*      */     //   0	113	2	$$1	Lnet/minecraft/server/level/ChunkTrackingView;
/*      */     //   17	96	3	$$2	Lnet/minecraft/server/level/ChunkTrackingView;
/*      */     //   30	59	4	$$3	Lnet/minecraft/server/level/ChunkTrackingView$Positioned;
/*      */     //   43	16	5	$$4	Lnet/minecraft/server/level/ChunkTrackingView$Positioned;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ServerPlayer> getPlayers(ChunkPos $$0, boolean $$1) {
/* 1147 */     Set<ServerPlayer> $$2 = this.playerMap.getAllPlayers();
/*      */     
/* 1149 */     ImmutableList.Builder<ServerPlayer> $$3 = ImmutableList.builder();
/* 1150 */     for (ServerPlayer $$4 : $$2) {
/* 1151 */       if (($$1 && isChunkOnTrackedBorder($$4, $$0.x, $$0.z)) || (!$$1 && 
/* 1152 */         isChunkTracked($$4, $$0.x, $$0.z))) {
/* 1153 */         $$3.add($$4);
/*      */       }
/*      */     } 
/* 1156 */     return (List<ServerPlayer>)$$3.build();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addEntity(Entity $$0) {
/* 1161 */     if ($$0 instanceof net.minecraft.world.entity.boss.EnderDragonPart) {
/*      */       return;
/*      */     }
/* 1164 */     EntityType<?> $$1 = $$0.getType();
/* 1165 */     int $$2 = $$1.clientTrackingRange() * 16;
/* 1166 */     if ($$2 == 0) {
/*      */       return;
/*      */     }
/* 1169 */     int $$3 = $$1.updateInterval();
/* 1170 */     if (this.entityMap.containsKey($$0.getId())) {
/* 1171 */       throw (IllegalStateException)Util.pauseInIde(new IllegalStateException("Entity is already tracked!"));
/*      */     }
/* 1173 */     TrackedEntity $$4 = new TrackedEntity($$0, $$2, $$3, $$1.trackDeltas());
/* 1174 */     this.entityMap.put($$0.getId(), $$4);
/* 1175 */     $$4.updatePlayers(this.level.players());
/*      */     
/* 1177 */     if ($$0 instanceof ServerPlayer) { ServerPlayer $$5 = (ServerPlayer)$$0;
/* 1178 */       updatePlayerStatus($$5, true);
/* 1179 */       for (ObjectIterator<TrackedEntity> objectIterator = this.entityMap.values().iterator(); objectIterator.hasNext(); ) { TrackedEntity $$6 = objectIterator.next();
/* 1180 */         if ($$6.entity != $$5) {
/* 1181 */           $$6.updatePlayer($$5);
/*      */         } }
/*      */        }
/*      */   
/*      */   }
/*      */   
/*      */   protected void removeEntity(Entity $$0) {
/* 1188 */     if ($$0 instanceof ServerPlayer) { ServerPlayer $$1 = (ServerPlayer)$$0;
/* 1189 */       updatePlayerStatus($$1, false);
/* 1190 */       for (ObjectIterator<TrackedEntity> objectIterator = this.entityMap.values().iterator(); objectIterator.hasNext(); ) { TrackedEntity $$2 = objectIterator.next();
/* 1191 */         $$2.removePlayer($$1); }
/*      */        }
/*      */     
/* 1194 */     TrackedEntity $$3 = (TrackedEntity)this.entityMap.remove($$0.getId());
/* 1195 */     if ($$3 != null) {
/* 1196 */       $$3.broadcastRemoved();
/*      */     }
/*      */   }
/*      */   
/*      */   protected void tick() {
/* 1201 */     for (ServerPlayer $$0 : this.playerMap.getAllPlayers()) {
/* 1202 */       updateChunkTracking($$0);
/*      */     }
/*      */ 
/*      */     
/* 1206 */     List<ServerPlayer> $$1 = Lists.newArrayList();
/* 1207 */     List<ServerPlayer> $$2 = this.level.players();
/*      */     ObjectIterator<TrackedEntity> objectIterator;
/* 1209 */     for (objectIterator = this.entityMap.values().iterator(); objectIterator.hasNext(); ) { TrackedEntity $$3 = objectIterator.next();
/* 1210 */       SectionPos $$4 = $$3.lastSectionPos;
/* 1211 */       SectionPos $$5 = SectionPos.of((EntityAccess)$$3.entity);
/* 1212 */       boolean $$6 = !Objects.equals($$4, $$5);
/* 1213 */       if ($$6) {
/* 1214 */         $$3.updatePlayers($$2);
/* 1215 */         Entity $$7 = $$3.entity;
/* 1216 */         if ($$7 instanceof ServerPlayer) {
/* 1217 */           $$1.add((ServerPlayer)$$7);
/*      */         }
/* 1219 */         $$3.lastSectionPos = $$5;
/*      */       } 
/* 1221 */       if ($$6 || this.distanceManager.inEntityTickingRange($$5.chunk().toLong())) {
/* 1222 */         $$3.serverEntity.sendChanges();
/*      */       } }
/*      */ 
/*      */     
/* 1226 */     if (!$$1.isEmpty()) {
/* 1227 */       for (objectIterator = this.entityMap.values().iterator(); objectIterator.hasNext(); ) { TrackedEntity $$8 = objectIterator.next();
/* 1228 */         $$8.updatePlayers($$1); }
/*      */     
/*      */     }
/*      */   }
/*      */   
/*      */   public void broadcast(Entity $$0, Packet<?> $$1) {
/* 1234 */     TrackedEntity $$2 = (TrackedEntity)this.entityMap.get($$0.getId());
/* 1235 */     if ($$2 != null) {
/* 1236 */       $$2.broadcast($$1);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void broadcastAndSend(Entity $$0, Packet<?> $$1) {
/* 1241 */     TrackedEntity $$2 = (TrackedEntity)this.entityMap.get($$0.getId());
/* 1242 */     if ($$2 != null) {
/* 1243 */       $$2.broadcastAndSend($$1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void resendBiomesForChunks(List<ChunkAccess> $$0) {
/* 1248 */     Map<ServerPlayer, List<LevelChunk>> $$1 = new HashMap<>();
/* 1249 */     for (ChunkAccess $$2 : $$0) {
/* 1250 */       LevelChunk $$6; ChunkPos $$3 = $$2.getPos();
/*      */       
/* 1252 */       if ($$2 instanceof LevelChunk) { LevelChunk $$4 = (LevelChunk)$$2;
/* 1253 */         LevelChunk $$5 = $$4; }
/*      */       else
/* 1255 */       { $$6 = this.level.getChunk($$3.x, $$3.z); }
/*      */       
/* 1257 */       for (ServerPlayer $$7 : getPlayers($$3, false)) {
/* 1258 */         ((List<LevelChunk>)$$1.computeIfAbsent($$7, $$0 -> new ArrayList())).add($$6);
/*      */       }
/*      */     } 
/*      */     
/* 1262 */     $$1.forEach(($$0, $$1) -> $$0.connection.send((Packet)ClientboundChunksBiomesPacket.forChunks($$1)));
/*      */   }
/*      */   
/*      */   protected PoiManager getPoiManager() {
/* 1266 */     return this.poiManager;
/*      */   }
/*      */   
/*      */   public String getStorageName() {
/* 1270 */     return this.storageName;
/*      */   }
/*      */   
/*      */   void onFullChunkStatusChange(ChunkPos $$0, FullChunkStatus $$1) {
/* 1274 */     this.chunkStatusListener.onChunkStatusChange($$0, $$1);
/*      */   }
/*      */   
/*      */   public void waitForLightBeforeSending(ChunkPos $$0, int $$1) {
/* 1278 */     int $$2 = $$1 + 1;
/* 1279 */     ChunkPos.rangeClosed($$0, $$2).forEach($$0 -> {
/*      */           ChunkHolder $$1 = getVisibleChunkIfPresent($$0.toLong());
/*      */           if ($$1 != null)
/*      */             $$1.addSendDependency(this.lightEngine.waitForPendingTasks($$0.x, $$0.z)); 
/*      */         });
/*      */   }
/*      */   
/*      */   private class DistanceManager
/*      */     extends DistanceManager {
/*      */     protected DistanceManager(Executor $$0, Executor $$1) {
/* 1289 */       super($$0, $$1);
/*      */     }
/*      */ 
/*      */     
/*      */     protected boolean isChunkToRemove(long $$0) {
/* 1294 */       return ChunkMap.this.toDrop.contains($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     protected ChunkHolder getChunk(long $$0) {
/* 1300 */       return ChunkMap.this.getUpdatingChunkIfPresent($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     protected ChunkHolder updateChunkScheduling(long $$0, int $$1, @Nullable ChunkHolder $$2, int $$3) {
/* 1306 */       return ChunkMap.this.updateChunkScheduling($$0, $$1, $$2, $$3);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private class TrackedEntity
/*      */   {
/*      */     final ServerEntity serverEntity;
/*      */     
/*      */     final Entity entity;
/*      */     private final int range;
/*      */     SectionPos lastSectionPos;
/* 1318 */     private final Set<ServerPlayerConnection> seenBy = Sets.newIdentityHashSet();
/*      */     
/*      */     public TrackedEntity(Entity $$0, int $$1, int $$2, boolean $$3) {
/* 1321 */       this.serverEntity = new ServerEntity(ChunkMap.this.level, $$0, $$2, $$3, this::broadcast);
/* 1322 */       this.entity = $$0;
/* 1323 */       this.range = $$1;
/* 1324 */       this.lastSectionPos = SectionPos.of((EntityAccess)$$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object $$0) {
/* 1329 */       if ($$0 instanceof TrackedEntity) {
/* 1330 */         return (((TrackedEntity)$$0).entity.getId() == this.entity.getId());
/*      */       }
/*      */       
/* 1333 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1338 */       return this.entity.getId();
/*      */     }
/*      */     
/*      */     public void broadcast(Packet<?> $$0) {
/* 1342 */       for (ServerPlayerConnection $$1 : this.seenBy) {
/* 1343 */         $$1.send($$0);
/*      */       }
/*      */     }
/*      */     
/*      */     public void broadcastAndSend(Packet<?> $$0) {
/* 1348 */       broadcast($$0);
/* 1349 */       if (this.entity instanceof ServerPlayer) {
/* 1350 */         ((ServerPlayer)this.entity).connection.send($$0);
/*      */       }
/*      */     }
/*      */     
/*      */     public void broadcastRemoved() {
/* 1355 */       for (ServerPlayerConnection $$0 : this.seenBy) {
/* 1356 */         this.serverEntity.removePairing($$0.getPlayer());
/*      */       }
/*      */     }
/*      */     
/*      */     public void removePlayer(ServerPlayer $$0) {
/* 1361 */       if (this.seenBy.remove($$0.connection)) {
/* 1362 */         this.serverEntity.removePairing($$0);
/*      */       }
/*      */     }
/*      */     
/*      */     public void updatePlayer(ServerPlayer $$0) {
/* 1367 */       if ($$0 == this.entity) {
/*      */         return;
/*      */       }
/*      */       
/* 1371 */       Vec3 $$1 = $$0.position().subtract(this.entity.position());
/* 1372 */       int $$2 = ChunkMap.this.getPlayerViewDistance($$0);
/* 1373 */       double $$3 = Math.min(getEffectiveRange(), $$2 * 16);
/* 1374 */       double $$4 = $$1.x * $$1.x + $$1.z * $$1.z;
/* 1375 */       double $$5 = $$3 * $$3;
/*      */       
/* 1377 */       boolean $$6 = ($$4 <= $$5 && this.entity.broadcastToPlayer($$0) && ChunkMap.this.isChunkTracked($$0, (this.entity.chunkPosition()).x, (this.entity.chunkPosition()).z));
/*      */       
/* 1379 */       if ($$6) {
/* 1380 */         if (this.seenBy.add($$0.connection)) {
/* 1381 */           this.serverEntity.addPairing($$0);
/*      */         }
/*      */       }
/* 1384 */       else if (this.seenBy.remove($$0.connection)) {
/* 1385 */         this.serverEntity.removePairing($$0);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     private int scaledRange(int $$0) {
/* 1391 */       return ChunkMap.this.level.getServer().getScaledTrackingDistance($$0);
/*      */     }
/*      */     
/*      */     private int getEffectiveRange() {
/* 1395 */       int $$0 = this.range;
/* 1396 */       for (Entity $$1 : this.entity.getIndirectPassengers()) {
/* 1397 */         int $$2 = $$1.getType().clientTrackingRange() * 16;
/* 1398 */         if ($$2 > $$0) {
/* 1399 */           $$0 = $$2;
/*      */         }
/*      */       } 
/* 1402 */       return scaledRange($$0);
/*      */     }
/*      */     
/*      */     public void updatePlayers(List<ServerPlayer> $$0) {
/* 1406 */       for (ServerPlayer $$1 : $$0)
/* 1407 */         updatePlayer($$1); 
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ChunkMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */