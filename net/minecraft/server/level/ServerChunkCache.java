/*     */ package net.minecraft.server.level;
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.server.level.progress.ChunkProgressListener;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.util.thread.BlockableEventLoop;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.LocalMobCapCalculator;
/*     */ import net.minecraft.world.level.NaturalSpawner;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
/*     */ import net.minecraft.world.level.chunk.ChunkSource;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.chunk.LightChunk;
/*     */ import net.minecraft.world.level.chunk.LightChunkGetter;
/*     */ import net.minecraft.world.level.chunk.storage.ChunkScanAccess;
/*     */ import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
/*     */ import net.minecraft.world.level.levelgen.RandomState;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import net.minecraft.world.level.storage.DimensionDataStorage;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ 
/*     */ public class ServerChunkCache extends ChunkSource {
/*  50 */   private static final List<ChunkStatus> CHUNK_STATUSES = ChunkStatus.getStatusList();
/*     */   
/*     */   private final DistanceManager distanceManager;
/*     */   
/*     */   final ServerLevel level;
/*     */   
/*     */   final Thread mainThread;
/*     */   
/*     */   final ThreadedLevelLightEngine lightEngine;
/*     */   
/*     */   private final MainThreadExecutor mainThreadProcessor;
/*     */   
/*     */   public final ChunkMap chunkMap;
/*     */   private final DimensionDataStorage dataStorage;
/*     */   private long lastInhabitedUpdate;
/*     */   private boolean spawnEnemies = true;
/*     */   private boolean spawnFriendlies = true;
/*     */   private static final int CACHE_SIZE = 4;
/*  68 */   private final long[] lastChunkPos = new long[4];
/*  69 */   private final ChunkStatus[] lastChunkStatus = new ChunkStatus[4];
/*  70 */   private final ChunkAccess[] lastChunk = new ChunkAccess[4];
/*     */   
/*     */   @Nullable
/*     */   @VisibleForDebug
/*     */   private NaturalSpawner.SpawnState lastSpawnState;
/*     */   
/*     */   public ServerChunkCache(ServerLevel $$0, LevelStorageSource.LevelStorageAccess $$1, DataFixer $$2, StructureTemplateManager $$3, Executor $$4, ChunkGenerator $$5, int $$6, int $$7, boolean $$8, ChunkProgressListener $$9, ChunkStatusUpdateListener $$10, Supplier<DimensionDataStorage> $$11) {
/*  77 */     this.level = $$0;
/*  78 */     this.mainThreadProcessor = new MainThreadExecutor($$0);
/*  79 */     this.mainThread = Thread.currentThread();
/*     */     
/*  81 */     File $$12 = $$1.getDimensionPath($$0.dimension()).resolve("data").toFile();
/*  82 */     $$12.mkdirs();
/*  83 */     this.dataStorage = new DimensionDataStorage($$12, $$2);
/*     */     
/*  85 */     this.chunkMap = new ChunkMap($$0, $$1, $$2, $$3, $$4, this.mainThreadProcessor, (LightChunkGetter)this, $$5, $$9, $$10, $$11, $$6, $$8);
/*  86 */     this.lightEngine = this.chunkMap.getLightEngine();
/*  87 */     this.distanceManager = this.chunkMap.getDistanceManager();
/*  88 */     this.distanceManager.updateSimulationDistance($$7);
/*  89 */     clearCache();
/*     */   }
/*     */ 
/*     */   
/*     */   public ThreadedLevelLightEngine getLightEngine() {
/*  94 */     return this.lightEngine;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ChunkHolder getVisibleChunkIfPresent(long $$0) {
/*  99 */     return this.chunkMap.getVisibleChunkIfPresent($$0);
/*     */   }
/*     */   
/*     */   public int getTickingGenerated() {
/* 103 */     return this.chunkMap.getTickingGenerated();
/*     */   }
/*     */   
/*     */   private void storeInCache(long $$0, ChunkAccess $$1, ChunkStatus $$2) {
/* 107 */     for (int $$3 = 3; $$3 > 0; $$3--) {
/* 108 */       this.lastChunkPos[$$3] = this.lastChunkPos[$$3 - 1];
/* 109 */       this.lastChunkStatus[$$3] = this.lastChunkStatus[$$3 - 1];
/* 110 */       this.lastChunk[$$3] = this.lastChunk[$$3 - 1];
/*     */     } 
/* 112 */     this.lastChunkPos[0] = $$0;
/* 113 */     this.lastChunkStatus[0] = $$2;
/* 114 */     this.lastChunk[0] = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ChunkAccess getChunk(int $$0, int $$1, ChunkStatus $$2, boolean $$3) {
/* 120 */     if (Thread.currentThread() != this.mainThread) {
/* 121 */       return CompletableFuture.<ChunkAccess>supplyAsync(() -> getChunk($$0, $$1, $$2, $$3), (Executor)this.mainThreadProcessor).join();
/*     */     }
/* 123 */     ProfilerFiller $$4 = this.level.getProfiler();
/* 124 */     $$4.incrementCounter("getChunk");
/*     */     
/* 126 */     long $$5 = ChunkPos.asLong($$0, $$1);
/* 127 */     for (int $$6 = 0; $$6 < 4; $$6++) {
/* 128 */       if ($$5 == this.lastChunkPos[$$6] && $$2 == this.lastChunkStatus[$$6]) {
/* 129 */         ChunkAccess $$7 = this.lastChunk[$$6];
/* 130 */         if ($$7 != null || !$$3) {
/* 131 */           return $$7;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 136 */     $$4.incrementCounter("getChunkCacheMiss");
/* 137 */     CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> $$8 = getChunkFutureMainThread($$0, $$1, $$2, $$3);
/* 138 */     Objects.requireNonNull($$8); this.mainThreadProcessor.managedBlock($$8::isDone);
/*     */     
/* 140 */     ChunkAccess $$9 = (ChunkAccess)((Either)$$8.join()).map($$0 -> $$0, $$1 -> {
/*     */           if ($$0) {
/*     */             throw (IllegalStateException)Util.pauseInIde(new IllegalStateException("Chunk not there when requested: " + $$1));
/*     */           }
/*     */           
/*     */           return null;
/*     */         });
/* 147 */     storeInCache($$5, $$9, $$2);
/* 148 */     return $$9;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LevelChunk getChunkNow(int $$0, int $$1) {
/* 154 */     if (Thread.currentThread() != this.mainThread)
/*     */     {
/* 156 */       return null;
/*     */     }
/* 158 */     this.level.getProfiler().incrementCounter("getChunkNow");
/*     */     
/* 160 */     long $$2 = ChunkPos.asLong($$0, $$1);
/* 161 */     for (int $$3 = 0; $$3 < 4; $$3++) {
/* 162 */       if ($$2 == this.lastChunkPos[$$3] && this.lastChunkStatus[$$3] == ChunkStatus.FULL) {
/* 163 */         ChunkAccess $$4 = this.lastChunk[$$3];
/* 164 */         return ($$4 instanceof LevelChunk) ? (LevelChunk)$$4 : null;
/*     */       } 
/*     */     } 
/*     */     
/* 168 */     ChunkHolder $$5 = getVisibleChunkIfPresent($$2);
/* 169 */     if ($$5 == null) {
/* 170 */       return null;
/*     */     }
/* 172 */     Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure> $$6 = $$5.getFutureIfPresent(ChunkStatus.FULL).getNow(null);
/* 173 */     if ($$6 == null) {
/* 174 */       return null;
/*     */     }
/* 176 */     ChunkAccess $$7 = $$6.left().orElse(null);
/* 177 */     if ($$7 != null) {
/* 178 */       storeInCache($$2, $$7, ChunkStatus.FULL);
/* 179 */       if ($$7 instanceof LevelChunk) {
/* 180 */         return (LevelChunk)$$7;
/*     */       }
/*     */     } 
/* 183 */     return null;
/*     */   }
/*     */   
/*     */   private void clearCache() {
/* 187 */     Arrays.fill(this.lastChunkPos, ChunkPos.INVALID_CHUNK_POS);
/* 188 */     Arrays.fill((Object[])this.lastChunkStatus, (Object)null);
/* 189 */     Arrays.fill((Object[])this.lastChunk, (Object)null);
/*     */   }
/*     */   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> getChunkFuture(int $$0, int $$1, ChunkStatus $$2, boolean $$3) {
/*     */     CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> $$6;
/* 193 */     boolean $$4 = (Thread.currentThread() == this.mainThread);
/*     */     
/* 195 */     if ($$4) {
/* 196 */       CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> $$5 = getChunkFutureMainThread($$0, $$1, $$2, $$3);
/* 197 */       Objects.requireNonNull($$5); this.mainThreadProcessor.managedBlock($$5::isDone);
/*     */     } else {
/* 199 */       $$6 = CompletableFuture.supplyAsync(() -> getChunkFutureMainThread($$0, $$1, $$2, $$3), (Executor)this.mainThreadProcessor).thenCompose($$0 -> $$0);
/*     */     } 
/* 201 */     return $$6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> getChunkFutureMainThread(int $$0, int $$1, ChunkStatus $$2, boolean $$3) {
/* 208 */     ChunkPos $$4 = new ChunkPos($$0, $$1);
/* 209 */     long $$5 = $$4.toLong();
/* 210 */     int $$6 = ChunkLevel.byStatus($$2);
/*     */     
/* 212 */     ChunkHolder $$7 = getVisibleChunkIfPresent($$5);
/* 213 */     if ($$3) {
/*     */       
/* 215 */       this.distanceManager.addTicket(TicketType.UNKNOWN, $$4, $$6, $$4);
/*     */       
/* 217 */       if (chunkAbsent($$7, $$6)) {
/* 218 */         ProfilerFiller $$8 = this.level.getProfiler();
/* 219 */         $$8.push("chunkLoad");
/* 220 */         runDistanceManagerUpdates();
/* 221 */         $$7 = getVisibleChunkIfPresent($$5);
/* 222 */         $$8.pop();
/* 223 */         if (chunkAbsent($$7, $$6)) {
/* 224 */           throw (IllegalStateException)Util.pauseInIde(new IllegalStateException("No chunk holder after ticket has been added"));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 229 */     if (chunkAbsent($$7, $$6)) {
/* 230 */       return ChunkHolder.UNLOADED_CHUNK_FUTURE;
/*     */     }
/*     */     
/* 233 */     return $$7.getOrScheduleFuture($$2, this.chunkMap);
/*     */   }
/*     */   
/*     */   private boolean chunkAbsent(@Nullable ChunkHolder $$0, int $$1) {
/* 237 */     return ($$0 == null || $$0.getTicketLevel() > $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasChunk(int $$0, int $$1) {
/* 242 */     ChunkHolder $$2 = getVisibleChunkIfPresent((new ChunkPos($$0, $$1)).toLong());
/* 243 */     int $$3 = ChunkLevel.byStatus(ChunkStatus.FULL);
/*     */     
/* 245 */     return !chunkAbsent($$2, $$3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LightChunk getChunkForLighting(int $$0, int $$1) {
/* 252 */     long $$2 = ChunkPos.asLong($$0, $$1);
/* 253 */     ChunkHolder $$3 = getVisibleChunkIfPresent($$2);
/* 254 */     if ($$3 == null) {
/* 255 */       return null;
/*     */     }
/*     */     
/* 258 */     for (int $$4 = CHUNK_STATUSES.size() - 1;; $$4--) {
/* 259 */       ChunkStatus $$5 = CHUNK_STATUSES.get($$4);
/*     */       
/* 261 */       Optional<ChunkAccess> $$6 = ((Either)$$3.getFutureIfPresentUnchecked($$5).getNow(ChunkHolder.UNLOADED_CHUNK)).left();
/* 262 */       if ($$6.isPresent()) {
/* 263 */         return (LightChunk)$$6.get();
/*     */       }
/*     */       
/* 266 */       if ($$5 == ChunkStatus.INITIALIZE_LIGHT.getParent()) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 271 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Level getLevel() {
/* 276 */     return this.level;
/*     */   }
/*     */   
/*     */   public boolean pollTask() {
/* 280 */     return this.mainThreadProcessor.pollTask();
/*     */   }
/*     */   
/*     */   boolean runDistanceManagerUpdates() {
/* 284 */     boolean $$0 = this.distanceManager.runAllUpdates(this.chunkMap);
/* 285 */     boolean $$1 = this.chunkMap.promoteChunkMap();
/* 286 */     if ($$0 || $$1) {
/* 287 */       clearCache();
/* 288 */       return true;
/*     */     } 
/* 290 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isPositionTicking(long $$0) {
/* 294 */     ChunkHolder $$1 = getVisibleChunkIfPresent($$0);
/* 295 */     if ($$1 == null) {
/* 296 */       return false;
/*     */     }
/* 298 */     if (!this.level.shouldTickBlocksAt($$0)) {
/* 299 */       return false;
/*     */     }
/* 301 */     Either<LevelChunk, ChunkHolder.ChunkLoadingFailure> $$2 = $$1.getTickingChunkFuture().getNow(null);
/* 302 */     return ($$2 != null && $$2.left().isPresent());
/*     */   }
/*     */   
/*     */   public void save(boolean $$0) {
/* 306 */     runDistanceManagerUpdates();
/* 307 */     this.chunkMap.saveAllChunks($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 312 */     save(true);
/* 313 */     this.lightEngine.close();
/* 314 */     this.chunkMap.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BooleanSupplier $$0, boolean $$1) {
/* 319 */     this.level.getProfiler().push("purge");
/* 320 */     this.distanceManager.purgeStaleTickets();
/* 321 */     runDistanceManagerUpdates();
/* 322 */     this.level.getProfiler().popPush("chunks");
/* 323 */     if ($$1) {
/* 324 */       tickChunks();
/* 325 */       this.chunkMap.tick();
/*     */     } 
/* 327 */     this.level.getProfiler().popPush("unload");
/* 328 */     this.chunkMap.tick($$0);
/* 329 */     this.level.getProfiler().pop();
/* 330 */     clearCache();
/*     */   }
/*     */   
/*     */   private void tickChunks() {
/* 334 */     long $$0 = this.level.getGameTime();
/* 335 */     long $$1 = $$0 - this.lastInhabitedUpdate;
/* 336 */     this.lastInhabitedUpdate = $$0;
/*     */     
/* 338 */     if (this.level.isDebug()) {
/*     */       return;
/*     */     }
/*     */     
/* 342 */     ProfilerFiller $$2 = this.level.getProfiler();
/*     */     
/* 344 */     $$2.push("pollingChunks");
/*     */     
/* 346 */     $$2.push("filteringLoadedChunks");
/* 347 */     List<ChunkAndHolder> $$3 = Lists.newArrayListWithCapacity(this.chunkMap.size());
/* 348 */     for (ChunkHolder $$4 : this.chunkMap.getChunks()) {
/* 349 */       LevelChunk $$5 = $$4.getTickingChunk();
/* 350 */       if ($$5 != null) {
/* 351 */         $$3.add(new ChunkAndHolder($$5, $$4));
/*     */       }
/*     */     } 
/*     */     
/* 355 */     if (this.level.getServer().tickRateManager().runsNormally()) {
/* 356 */       $$2.popPush("naturalSpawnCount");
/* 357 */       int $$6 = this.distanceManager.getNaturalSpawnChunkCount();
/* 358 */       NaturalSpawner.SpawnState $$7 = NaturalSpawner.createState($$6, this.level.getAllEntities(), this::getFullChunk, new LocalMobCapCalculator(this.chunkMap));
/* 359 */       this.lastSpawnState = $$7;
/*     */       
/* 361 */       $$2.popPush("spawnAndTick");
/*     */       
/* 363 */       boolean $$8 = this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING);
/*     */ 
/*     */       
/* 366 */       Util.shuffle($$3, this.level.random);
/* 367 */       int $$9 = this.level.getGameRules().getInt(GameRules.RULE_RANDOMTICKING);
/* 368 */       boolean $$10 = (this.level.getLevelData().getGameTime() % 400L == 0L);
/* 369 */       for (ChunkAndHolder $$11 : $$3) {
/* 370 */         LevelChunk $$12 = $$11.chunk;
/* 371 */         ChunkPos $$13 = $$12.getPos();
/* 372 */         if (this.level.isNaturalSpawningAllowed($$13) && this.chunkMap.anyPlayerCloseEnoughForSpawning($$13)) {
/* 373 */           $$12.incrementInhabitedTime($$1);
/*     */           
/* 375 */           if ($$8 && (this.spawnEnemies || this.spawnFriendlies) && this.level.getWorldBorder().isWithinBounds($$13)) {
/* 376 */             NaturalSpawner.spawnForChunk(this.level, $$12, $$7, this.spawnFriendlies, this.spawnEnemies, $$10);
/*     */           }
/* 378 */           if (this.level.shouldTickBlocksAt($$13.toLong())) {
/* 379 */             this.level.tickChunk($$12, $$9);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 384 */       $$2.popPush("customSpawners");
/* 385 */       if ($$8) {
/* 386 */         this.level.tickCustomSpawners(this.spawnEnemies, this.spawnFriendlies);
/*     */       }
/*     */     } 
/*     */     
/* 390 */     $$2.popPush("broadcast");
/* 391 */     $$3.forEach($$0 -> $$0.holder.broadcastChanges($$0.chunk));
/*     */     
/* 393 */     $$2.pop();
/*     */     
/* 395 */     $$2.pop();
/*     */   }
/*     */   
/*     */   private void getFullChunk(long $$0, Consumer<LevelChunk> $$1) {
/* 399 */     ChunkHolder $$2 = getVisibleChunkIfPresent($$0);
/*     */     
/* 401 */     if ($$2 != null) {
/* 402 */       ((Either)$$2.getFullChunkFuture().getNow(ChunkHolder.UNLOADED_LEVEL_CHUNK)).left().ifPresent($$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String gatherStats() {
/* 408 */     return Integer.toString(getLoadedChunksCount());
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public int getPendingTasksCount() {
/* 413 */     return this.mainThreadProcessor.getPendingTasksCount();
/*     */   }
/*     */   
/*     */   public ChunkGenerator getGenerator() {
/* 417 */     return this.chunkMap.generator();
/*     */   }
/*     */   
/*     */   public ChunkGeneratorStructureState getGeneratorState() {
/* 421 */     return this.chunkMap.generatorState();
/*     */   }
/*     */   
/*     */   public RandomState randomState() {
/* 425 */     return this.chunkMap.randomState();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLoadedChunksCount() {
/* 430 */     return this.chunkMap.size();
/*     */   }
/*     */   
/*     */   public void blockChanged(BlockPos $$0) {
/* 434 */     int $$1 = SectionPos.blockToSectionCoord($$0.getX());
/* 435 */     int $$2 = SectionPos.blockToSectionCoord($$0.getZ());
/* 436 */     ChunkHolder $$3 = getVisibleChunkIfPresent(ChunkPos.asLong($$1, $$2));
/* 437 */     if ($$3 != null) {
/* 438 */       $$3.blockChanged($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLightUpdate(LightLayer $$0, SectionPos $$1) {
/* 444 */     this.mainThreadProcessor.execute(() -> {
/*     */           ChunkHolder $$2 = getVisibleChunkIfPresent($$0.chunk().toLong());
/*     */           if ($$2 != null) {
/*     */             $$2.sectionLightChanged($$1, $$0.y());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public <T> void addRegionTicket(TicketType<T> $$0, ChunkPos $$1, int $$2, T $$3) {
/* 453 */     this.distanceManager.addRegionTicket($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public <T> void removeRegionTicket(TicketType<T> $$0, ChunkPos $$1, int $$2, T $$3) {
/* 457 */     this.distanceManager.removeRegionTicket($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateChunkForced(ChunkPos $$0, boolean $$1) {
/* 462 */     this.distanceManager.updateChunkForced($$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void move(ServerPlayer $$0) {
/* 469 */     if (!$$0.isRemoved()) {
/* 470 */       this.chunkMap.move($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeEntity(Entity $$0) {
/* 477 */     this.chunkMap.removeEntity($$0);
/*     */   }
/*     */   
/*     */   public void addEntity(Entity $$0) {
/* 481 */     this.chunkMap.addEntity($$0);
/*     */   }
/*     */   
/*     */   public void broadcastAndSend(Entity $$0, Packet<?> $$1) {
/* 485 */     this.chunkMap.broadcastAndSend($$0, $$1);
/*     */   }
/*     */   
/*     */   public void broadcast(Entity $$0, Packet<?> $$1) {
/* 489 */     this.chunkMap.broadcast($$0, $$1);
/*     */   }
/*     */   
/*     */   public void setViewDistance(int $$0) {
/* 493 */     this.chunkMap.setServerViewDistance($$0);
/*     */   }
/*     */   
/*     */   public void setSimulationDistance(int $$0) {
/* 497 */     this.distanceManager.updateSimulationDistance($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSpawnSettings(boolean $$0, boolean $$1) {
/* 502 */     this.spawnEnemies = $$0;
/* 503 */     this.spawnFriendlies = $$1;
/*     */   }
/*     */   
/*     */   public String getChunkDebugData(ChunkPos $$0) {
/* 507 */     return this.chunkMap.getChunkDebugData($$0);
/*     */   }
/*     */   
/*     */   public DimensionDataStorage getDataStorage() {
/* 511 */     return this.dataStorage;
/*     */   }
/*     */   
/*     */   public PoiManager getPoiManager() {
/* 515 */     return this.chunkMap.getPoiManager();
/*     */   }
/*     */   
/*     */   public ChunkScanAccess chunkScanner() {
/* 519 */     return this.chunkMap.chunkScanner();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @VisibleForDebug
/*     */   public NaturalSpawner.SpawnState getLastSpawnState() {
/* 525 */     return this.lastSpawnState;
/*     */   }
/*     */   
/*     */   public void removeTicketsOnClosing() {
/* 529 */     this.distanceManager.removeTicketsOnClosing();
/*     */   }
/*     */   
/*     */   private final class MainThreadExecutor extends BlockableEventLoop<Runnable> {
/*     */     MainThreadExecutor(Level $$0) {
/* 534 */       super("Chunk source main thread executor for " + $$0.dimension().location());
/*     */     }
/*     */ 
/*     */     
/*     */     protected Runnable wrapRunnable(Runnable $$0) {
/* 539 */       return $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean shouldRun(Runnable $$0) {
/* 544 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean scheduleExecutables() {
/* 550 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Thread getRunningThread() {
/* 555 */       return ServerChunkCache.this.mainThread;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void doRunTask(Runnable $$0) {
/* 560 */       ServerChunkCache.this.level.getProfiler().incrementCounter("runTask");
/* 561 */       super.doRunTask($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean pollTask() {
/* 566 */       if (ServerChunkCache.this.runDistanceManagerUpdates()) {
/* 567 */         return true;
/*     */       }
/* 569 */       ServerChunkCache.this.lightEngine.tryScheduleUpdate();
/* 570 */       return super.pollTask();
/*     */     } }
/*     */   private static final class ChunkAndHolder extends Record { final LevelChunk chunk; final ChunkHolder holder;
/*     */     
/* 574 */     ChunkAndHolder(LevelChunk $$0, ChunkHolder $$1) { this.chunk = $$0; this.holder = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/level/ServerChunkCache$ChunkAndHolder;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #574	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 574 */       //   0	7	0	this	Lnet/minecraft/server/level/ServerChunkCache$ChunkAndHolder; } public LevelChunk chunk() { return this.chunk; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/level/ServerChunkCache$ChunkAndHolder;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #574	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/level/ServerChunkCache$ChunkAndHolder; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/level/ServerChunkCache$ChunkAndHolder;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #574	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/level/ServerChunkCache$ChunkAndHolder;
/* 574 */       //   0	8	1	$$0	Ljava/lang/Object; } public ChunkHolder holder() { return this.holder; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ServerChunkCache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */