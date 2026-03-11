/*     */ package net.minecraft.server.level;
/*     */ 
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntSupplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
/*     */ import net.minecraft.util.DebugBuffer;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.chunk.ImposterProtoChunk;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.chunk.LevelChunkSection;
/*     */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkHolder
/*     */ {
/*  42 */   public static final Either<ChunkAccess, ChunkLoadingFailure> UNLOADED_CHUNK = Either.right(ChunkLoadingFailure.UNLOADED);
/*  43 */   public static final CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> UNLOADED_CHUNK_FUTURE = CompletableFuture.completedFuture(UNLOADED_CHUNK);
/*     */   
/*  45 */   public static final Either<LevelChunk, ChunkLoadingFailure> UNLOADED_LEVEL_CHUNK = Either.right(ChunkLoadingFailure.UNLOADED);
/*     */   
/*  47 */   private static final Either<ChunkAccess, ChunkLoadingFailure> NOT_DONE_YET = Either.right(ChunkLoadingFailure.UNLOADED);
/*     */   
/*  49 */   private static final CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> UNLOADED_LEVEL_CHUNK_FUTURE = CompletableFuture.completedFuture(UNLOADED_LEVEL_CHUNK);
/*     */   
/*  51 */   private static final List<ChunkStatus> CHUNK_STATUSES = ChunkStatus.getStatusList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private final AtomicReferenceArray<CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>>> futures = new AtomicReferenceArray<>(CHUNK_STATUSES.size());
/*     */   
/*     */   private final LevelHeightAccessor levelHeightAccessor;
/*  59 */   private volatile CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> fullChunkFuture = UNLOADED_LEVEL_CHUNK_FUTURE;
/*  60 */   private volatile CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> tickingChunkFuture = UNLOADED_LEVEL_CHUNK_FUTURE;
/*  61 */   private volatile CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> entityTickingChunkFuture = UNLOADED_LEVEL_CHUNK_FUTURE;
/*     */   
/*  63 */   private CompletableFuture<ChunkAccess> chunkToSave = CompletableFuture.completedFuture(null); @Nullable
/*  64 */   private final DebugBuffer<ChunkSaveDebug> chunkToSaveHistory = null;
/*     */   
/*     */   private int oldTicketLevel;
/*     */   
/*     */   private int ticketLevel;
/*     */   
/*     */   private int queueLevel;
/*     */   
/*     */   final ChunkPos pos;
/*     */   private boolean hasChangedSections;
/*     */   private final ShortSet[] changedBlocksPerSection;
/*  75 */   private final BitSet blockChangedLightSectionFilter = new BitSet();
/*  76 */   private final BitSet skyChangedLightSectionFilter = new BitSet();
/*     */   
/*     */   private final LevelLightEngine lightEngine;
/*     */   private final LevelChangeListener onLevelChange;
/*     */   private final PlayerProvider playerProvider;
/*     */   private boolean wasAccessibleSinceLastSave;
/*  82 */   private CompletableFuture<Void> pendingFullStateConfirmation = CompletableFuture.completedFuture(null);
/*  83 */   private CompletableFuture<?> sendSync = CompletableFuture.completedFuture(null);
/*     */   
/*     */   public ChunkHolder(ChunkPos $$0, int $$1, LevelHeightAccessor $$2, LevelLightEngine $$3, LevelChangeListener $$4, PlayerProvider $$5) {
/*  86 */     this.pos = $$0;
/*  87 */     this.levelHeightAccessor = $$2;
/*  88 */     this.lightEngine = $$3;
/*  89 */     this.onLevelChange = $$4;
/*  90 */     this.playerProvider = $$5;
/*  91 */     this.oldTicketLevel = ChunkLevel.MAX_LEVEL + 1;
/*  92 */     this.ticketLevel = this.oldTicketLevel;
/*  93 */     this.queueLevel = this.oldTicketLevel;
/*  94 */     setTicketLevel($$1);
/*  95 */     this.changedBlocksPerSection = new ShortSet[$$2.getSectionsCount()];
/*     */   }
/*     */   
/*     */   public CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> getFutureIfPresentUnchecked(ChunkStatus $$0) {
/*  99 */     CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$1 = this.futures.get($$0.getIndex());
/* 100 */     return ($$1 == null) ? UNLOADED_CHUNK_FUTURE : $$1;
/*     */   }
/*     */   
/*     */   public CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> getFutureIfPresent(ChunkStatus $$0) {
/* 104 */     if (ChunkLevel.generationStatus(this.ticketLevel).isOrAfter($$0)) {
/* 105 */       return getFutureIfPresentUnchecked($$0);
/*     */     }
/* 107 */     return UNLOADED_CHUNK_FUTURE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> getTickingChunkFuture() {
/* 114 */     return this.tickingChunkFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> getEntityTickingChunkFuture() {
/* 121 */     return this.entityTickingChunkFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> getFullChunkFuture() {
/* 128 */     return this.fullChunkFuture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LevelChunk getTickingChunk() {
/* 136 */     CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> $$0 = getTickingChunkFuture();
/* 137 */     Either<LevelChunk, ChunkLoadingFailure> $$1 = $$0.getNow(null);
/* 138 */     if ($$1 == null) {
/* 139 */       return null;
/*     */     }
/* 141 */     return $$1.left().orElse(null);
/*     */   }
/*     */   
/*     */   public CompletableFuture<?> getChunkSendSyncFuture() {
/* 145 */     return this.sendSync;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public LevelChunk getChunkToSend() {
/* 150 */     if (!this.sendSync.isDone()) {
/* 151 */       return null;
/*     */     }
/* 153 */     return getTickingChunk();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LevelChunk getFullChunk() {
/* 161 */     CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> $$0 = getFullChunkFuture();
/* 162 */     Either<LevelChunk, ChunkLoadingFailure> $$1 = $$0.getNow(null);
/* 163 */     if ($$1 == null) {
/* 164 */       return null;
/*     */     }
/* 166 */     return $$1.left().orElse(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ChunkStatus getLastAvailableStatus() {
/* 175 */     for (int $$0 = CHUNK_STATUSES.size() - 1; $$0 >= 0; $$0--) {
/* 176 */       ChunkStatus $$1 = CHUNK_STATUSES.get($$0);
/* 177 */       CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$2 = getFutureIfPresentUnchecked($$1);
/* 178 */       if (((Either)$$2.getNow(UNLOADED_CHUNK)).left().isPresent()) {
/* 179 */         return $$1;
/*     */       }
/*     */     } 
/* 182 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ChunkAccess getLastAvailable() {
/* 192 */     for (int $$0 = CHUNK_STATUSES.size() - 1; $$0 >= 0; $$0--) {
/* 193 */       ChunkStatus $$1 = CHUNK_STATUSES.get($$0);
/* 194 */       CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$2 = getFutureIfPresentUnchecked($$1);
/* 195 */       if (!$$2.isCompletedExceptionally()) {
/*     */ 
/*     */         
/* 198 */         Optional<ChunkAccess> $$3 = ((Either)$$2.getNow(UNLOADED_CHUNK)).left();
/* 199 */         if ($$3.isPresent())
/* 200 */           return $$3.get(); 
/*     */       } 
/*     */     } 
/* 203 */     return null;
/*     */   }
/*     */   
/*     */   public CompletableFuture<ChunkAccess> getChunkToSave() {
/* 207 */     return this.chunkToSave;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void blockChanged(BlockPos $$0) {
/* 213 */     LevelChunk $$1 = getTickingChunk();
/* 214 */     if ($$1 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 218 */     int $$2 = this.levelHeightAccessor.getSectionIndex($$0.getY());
/* 219 */     if (this.changedBlocksPerSection[$$2] == null) {
/* 220 */       this.hasChangedSections = true;
/* 221 */       this.changedBlocksPerSection[$$2] = (ShortSet)new ShortOpenHashSet();
/*     */     } 
/* 223 */     this.changedBlocksPerSection[$$2].add(SectionPos.sectionRelativePos($$0));
/*     */   }
/*     */   
/*     */   public void sectionLightChanged(LightLayer $$0, int $$1) {
/* 227 */     Either<ChunkAccess, ChunkLoadingFailure> $$2 = getFutureIfPresent(ChunkStatus.INITIALIZE_LIGHT).getNow(null);
/* 228 */     if ($$2 == null) {
/*     */       return;
/*     */     }
/* 231 */     ChunkAccess $$3 = $$2.left().orElse(null);
/* 232 */     if ($$3 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 236 */     $$3.setUnsaved(true);
/*     */     
/* 238 */     LevelChunk $$4 = getTickingChunk();
/* 239 */     if ($$4 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 243 */     int $$5 = this.lightEngine.getMinLightSection();
/* 244 */     int $$6 = this.lightEngine.getMaxLightSection();
/* 245 */     if ($$1 < $$5 || $$1 > $$6) {
/*     */       return;
/*     */     }
/*     */     
/* 249 */     int $$7 = $$1 - $$5;
/* 250 */     if ($$0 == LightLayer.SKY) {
/* 251 */       this.skyChangedLightSectionFilter.set($$7);
/*     */     } else {
/* 253 */       this.blockChangedLightSectionFilter.set($$7);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void broadcastChanges(LevelChunk $$0) {
/* 258 */     if (!this.hasChangedSections && this.skyChangedLightSectionFilter.isEmpty() && this.blockChangedLightSectionFilter.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 262 */     Level $$1 = $$0.getLevel();
/*     */     
/* 264 */     if (!this.skyChangedLightSectionFilter.isEmpty() || !this.blockChangedLightSectionFilter.isEmpty()) {
/* 265 */       List<ServerPlayer> $$2 = this.playerProvider.getPlayers(this.pos, true);
/* 266 */       if (!$$2.isEmpty()) {
/* 267 */         ClientboundLightUpdatePacket $$3 = new ClientboundLightUpdatePacket($$0.getPos(), this.lightEngine, this.skyChangedLightSectionFilter, this.blockChangedLightSectionFilter);
/* 268 */         broadcast($$2, (Packet<?>)$$3);
/*     */       } 
/* 270 */       this.skyChangedLightSectionFilter.clear();
/* 271 */       this.blockChangedLightSectionFilter.clear();
/*     */     } 
/*     */     
/* 274 */     if (!this.hasChangedSections) {
/*     */       return;
/*     */     }
/*     */     
/* 278 */     List<ServerPlayer> $$4 = this.playerProvider.getPlayers(this.pos, false);
/* 279 */     for (int $$5 = 0; $$5 < this.changedBlocksPerSection.length; $$5++) {
/* 280 */       ShortSet $$6 = this.changedBlocksPerSection[$$5];
/* 281 */       if ($$6 != null) {
/*     */ 
/*     */         
/* 284 */         this.changedBlocksPerSection[$$5] = null;
/*     */         
/* 286 */         if (!$$4.isEmpty()) {
/*     */ 
/*     */           
/* 289 */           int $$7 = this.levelHeightAccessor.getSectionYFromSectionIndex($$5);
/* 290 */           SectionPos $$8 = SectionPos.of($$0.getPos(), $$7);
/*     */           
/* 292 */           if ($$6.size() == 1)
/* 293 */           { BlockPos $$9 = $$8.relativeToBlockPos($$6.iterator().nextShort());
/* 294 */             BlockState $$10 = $$1.getBlockState($$9);
/*     */             
/* 296 */             broadcast($$4, (Packet<?>)new ClientboundBlockUpdatePacket($$9, $$10));
/* 297 */             broadcastBlockEntityIfNeeded($$4, $$1, $$9, $$10); }
/*     */           else
/* 299 */           { LevelChunkSection $$11 = $$0.getSection($$5);
/* 300 */             ClientboundSectionBlocksUpdatePacket $$12 = new ClientboundSectionBlocksUpdatePacket($$8, $$6, $$11);
/*     */             
/* 302 */             broadcast($$4, (Packet<?>)$$12);
/* 303 */             $$12.runUpdates(($$2, $$3) -> broadcastBlockEntityIfNeeded($$0, $$1, $$2, $$3)); } 
/*     */         } 
/*     */       } 
/* 306 */     }  this.hasChangedSections = false;
/*     */   }
/*     */   
/*     */   private void broadcastBlockEntityIfNeeded(List<ServerPlayer> $$0, Level $$1, BlockPos $$2, BlockState $$3) {
/* 310 */     if ($$3.hasBlockEntity()) {
/* 311 */       broadcastBlockEntity($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   private void broadcastBlockEntity(List<ServerPlayer> $$0, Level $$1, BlockPos $$2) {
/* 316 */     BlockEntity $$3 = $$1.getBlockEntity($$2);
/* 317 */     if ($$3 != null) {
/* 318 */       Packet<?> $$4 = $$3.getUpdatePacket();
/* 319 */       if ($$4 != null) {
/* 320 */         broadcast($$0, $$4);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void broadcast(List<ServerPlayer> $$0, Packet<?> $$1) {
/* 326 */     $$0.forEach($$1 -> $$1.connection.send($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> getOrScheduleFuture(ChunkStatus $$0, ChunkMap $$1) {
/* 333 */     int $$2 = $$0.getIndex();
/* 334 */     CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$3 = this.futures.get($$2);
/* 335 */     if ($$3 != null) {
/* 336 */       Either<ChunkAccess, ChunkLoadingFailure> $$4 = $$3.getNow(NOT_DONE_YET);
/* 337 */       if ($$4 == null) {
/* 338 */         String $$5 = "value in future for status: " + $$0 + " was incorrectly set to null at chunk: " + this.pos;
/* 339 */         throw $$1.debugFuturesAndCreateReportedException(new IllegalStateException("null value previously set for chunk status"), $$5);
/*     */       } 
/* 341 */       if ($$4 == NOT_DONE_YET || $$4.right().isEmpty()) {
/* 342 */         return $$3;
/*     */       }
/*     */     } 
/* 345 */     if (ChunkLevel.generationStatus(this.ticketLevel).isOrAfter($$0)) {
/* 346 */       CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$6 = $$1.schedule(this, $$0);
/* 347 */       updateChunkToSave($$6, "schedule " + $$0);
/* 348 */       this.futures.set($$2, $$6);
/* 349 */       return $$6;
/*     */     } 
/* 351 */     return ($$3 == null) ? UNLOADED_CHUNK_FUTURE : $$3;
/*     */   }
/*     */   
/*     */   protected void addSaveDependency(String $$0, CompletableFuture<?> $$1) {
/* 355 */     if (this.chunkToSaveHistory != null) {
/* 356 */       this.chunkToSaveHistory.push(new ChunkSaveDebug(Thread.currentThread(), $$1, $$0));
/*     */     }
/* 358 */     this.chunkToSave = this.chunkToSave.thenCombine($$1, ($$0, $$1) -> $$0);
/*     */   }
/*     */   
/*     */   private void updateChunkToSave(CompletableFuture<? extends Either<? extends ChunkAccess, ChunkLoadingFailure>> $$0, String $$1) {
/* 362 */     if (this.chunkToSaveHistory != null) {
/* 363 */       this.chunkToSaveHistory.push(new ChunkSaveDebug(Thread.currentThread(), $$0, $$1));
/*     */     }
/* 365 */     this.chunkToSave = this.chunkToSave.thenCombine($$0, ($$0, $$1) -> (ChunkAccess)$$1.map((), ()));
/*     */   }
/*     */   
/*     */   public void addSendDependency(CompletableFuture<?> $$0) {
/* 369 */     if (this.sendSync.isDone()) {
/* 370 */       this.sendSync = $$0;
/*     */     } else {
/* 372 */       this.sendSync = this.sendSync.thenCombine($$0, ($$0, $$1) -> null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public FullChunkStatus getFullStatus() {
/* 377 */     return ChunkLevel.fullStatus(this.ticketLevel);
/*     */   }
/*     */   
/*     */   public ChunkPos getPos() {
/* 381 */     return this.pos;
/*     */   }
/*     */   
/*     */   public int getTicketLevel() {
/* 385 */     return this.ticketLevel;
/*     */   }
/*     */   
/*     */   public int getQueueLevel() {
/* 389 */     return this.queueLevel;
/*     */   }
/*     */   
/*     */   private void setQueueLevel(int $$0) {
/* 393 */     this.queueLevel = $$0;
/*     */   }
/*     */   
/*     */   public void setTicketLevel(int $$0) {
/* 397 */     this.ticketLevel = $$0;
/*     */   }
/*     */   
/*     */   private void scheduleFullChunkPromotion(ChunkMap $$0, CompletableFuture<Either<LevelChunk, ChunkLoadingFailure>> $$1, Executor $$2, FullChunkStatus $$3) {
/* 401 */     this.pendingFullStateConfirmation.cancel(false);
/* 402 */     CompletableFuture<Void> $$4 = new CompletableFuture<>();
/* 403 */     $$4.thenRunAsync(() -> $$0.onFullChunkStatusChange(this.pos, $$1), $$2);
/* 404 */     this.pendingFullStateConfirmation = $$4;
/* 405 */     $$1.thenAccept($$1 -> $$1.ifLeft(()));
/*     */   }
/*     */   
/*     */   private void demoteFullChunk(ChunkMap $$0, FullChunkStatus $$1) {
/* 409 */     this.pendingFullStateConfirmation.cancel(false);
/* 410 */     $$0.onFullChunkStatusChange(this.pos, $$1);
/*     */   }
/*     */   
/*     */   protected void updateFutures(ChunkMap $$0, Executor $$1) {
/* 414 */     ChunkStatus $$2 = ChunkLevel.generationStatus(this.oldTicketLevel);
/* 415 */     ChunkStatus $$3 = ChunkLevel.generationStatus(this.ticketLevel);
/*     */     
/* 417 */     boolean $$4 = ChunkLevel.isLoaded(this.oldTicketLevel);
/* 418 */     boolean $$5 = ChunkLevel.isLoaded(this.ticketLevel);
/*     */     
/* 420 */     FullChunkStatus $$6 = ChunkLevel.fullStatus(this.oldTicketLevel);
/* 421 */     FullChunkStatus $$7 = ChunkLevel.fullStatus(this.ticketLevel);
/*     */     
/* 423 */     if ($$4) {
/* 424 */       Either<ChunkAccess, ChunkLoadingFailure> $$8 = Either.right(new ChunkLoadingFailure()
/*     */           {
/*     */             public String toString() {
/* 427 */               return "Unloaded ticket level " + ChunkHolder.this.pos;
/*     */             }
/*     */           });
/* 430 */       for (int $$9 = $$5 ? ($$3.getIndex() + 1) : 0; $$9 <= $$2.getIndex(); $$9++) {
/* 431 */         CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$10 = this.futures.get($$9);
/* 432 */         if ($$10 == null)
/*     */         {
/*     */ 
/*     */           
/* 436 */           this.futures.set($$9, CompletableFuture.completedFuture($$8));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 441 */     boolean $$11 = $$6.isOrAfter(FullChunkStatus.FULL);
/* 442 */     boolean $$12 = $$7.isOrAfter(FullChunkStatus.FULL);
/* 443 */     this.wasAccessibleSinceLastSave |= $$12;
/*     */     
/* 445 */     if (!$$11 && $$12) {
/* 446 */       this.fullChunkFuture = $$0.prepareAccessibleChunk(this);
/* 447 */       scheduleFullChunkPromotion($$0, this.fullChunkFuture, $$1, FullChunkStatus.FULL);
/* 448 */       updateChunkToSave((CompletableFuture)this.fullChunkFuture, "full");
/*     */     } 
/* 450 */     if ($$11 && !$$12) {
/* 451 */       this.fullChunkFuture.complete(UNLOADED_LEVEL_CHUNK);
/* 452 */       this.fullChunkFuture = UNLOADED_LEVEL_CHUNK_FUTURE;
/*     */     } 
/*     */     
/* 455 */     boolean $$13 = $$6.isOrAfter(FullChunkStatus.BLOCK_TICKING);
/* 456 */     boolean $$14 = $$7.isOrAfter(FullChunkStatus.BLOCK_TICKING);
/*     */     
/* 458 */     if (!$$13 && $$14) {
/* 459 */       this.tickingChunkFuture = $$0.prepareTickingChunk(this);
/* 460 */       scheduleFullChunkPromotion($$0, this.tickingChunkFuture, $$1, FullChunkStatus.BLOCK_TICKING);
/* 461 */       updateChunkToSave((CompletableFuture)this.tickingChunkFuture, "ticking");
/*     */     } 
/* 463 */     if ($$13 && !$$14) {
/* 464 */       this.tickingChunkFuture.complete(UNLOADED_LEVEL_CHUNK);
/* 465 */       this.tickingChunkFuture = UNLOADED_LEVEL_CHUNK_FUTURE;
/*     */     } 
/*     */     
/* 468 */     boolean $$15 = $$6.isOrAfter(FullChunkStatus.ENTITY_TICKING);
/* 469 */     boolean $$16 = $$7.isOrAfter(FullChunkStatus.ENTITY_TICKING);
/*     */     
/* 471 */     if (!$$15 && $$16) {
/* 472 */       if (this.entityTickingChunkFuture != UNLOADED_LEVEL_CHUNK_FUTURE) {
/* 473 */         throw (IllegalStateException)Util.pauseInIde(new IllegalStateException());
/*     */       }
/* 475 */       this.entityTickingChunkFuture = $$0.prepareEntityTickingChunk(this);
/* 476 */       scheduleFullChunkPromotion($$0, this.entityTickingChunkFuture, $$1, FullChunkStatus.ENTITY_TICKING);
/* 477 */       updateChunkToSave((CompletableFuture)this.entityTickingChunkFuture, "entity ticking");
/*     */     } 
/* 479 */     if ($$15 && !$$16) {
/* 480 */       this.entityTickingChunkFuture.complete(UNLOADED_LEVEL_CHUNK);
/* 481 */       this.entityTickingChunkFuture = UNLOADED_LEVEL_CHUNK_FUTURE;
/*     */     } 
/*     */     
/* 484 */     if (!$$7.isOrAfter($$6)) {
/* 485 */       demoteFullChunk($$0, $$7);
/*     */     }
/*     */     
/* 488 */     this.onLevelChange.onLevelChange(this.pos, this::getQueueLevel, this.ticketLevel, this::setQueueLevel);
/* 489 */     this.oldTicketLevel = this.ticketLevel;
/*     */   }
/*     */   
/*     */   public boolean wasAccessibleSinceLastSave() {
/* 493 */     return this.wasAccessibleSinceLastSave;
/*     */   }
/*     */   
/*     */   public void refreshAccessibility() {
/* 497 */     this.wasAccessibleSinceLastSave = ChunkLevel.fullStatus(this.ticketLevel).isOrAfter(FullChunkStatus.FULL);
/*     */   }
/*     */   
/*     */   public void replaceProtoChunk(ImposterProtoChunk $$0) {
/* 501 */     for (int $$1 = 0; $$1 < this.futures.length(); $$1++) {
/* 502 */       CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>> $$2 = this.futures.get($$1);
/* 503 */       if ($$2 != null) {
/*     */ 
/*     */         
/* 506 */         Optional<ChunkAccess> $$3 = ((Either)$$2.getNow(UNLOADED_CHUNK)).left();
/* 507 */         if (!$$3.isEmpty() && $$3.get() instanceof net.minecraft.world.level.chunk.ProtoChunk)
/*     */         {
/*     */           
/* 510 */           this.futures.set($$1, CompletableFuture.completedFuture(Either.left($$0))); } 
/*     */       } 
/* 512 */     }  updateChunkToSave(CompletableFuture.completedFuture(Either.left($$0.getWrapped())), "replaceProto");
/*     */   }
/*     */   
/*     */   public List<Pair<ChunkStatus, CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>>>> getAllFutures() {
/* 516 */     List<Pair<ChunkStatus, CompletableFuture<Either<ChunkAccess, ChunkLoadingFailure>>>> $$0 = new ArrayList<>();
/*     */     
/* 518 */     for (int $$1 = 0; $$1 < CHUNK_STATUSES.size(); $$1++) {
/* 519 */       $$0.add(Pair.of(CHUNK_STATUSES.get($$1), this.futures.get($$1)));
/*     */     }
/* 521 */     return $$0;
/*     */   }
/*     */   
/*     */   public static interface ChunkLoadingFailure {
/* 525 */     public static final ChunkLoadingFailure UNLOADED = new ChunkLoadingFailure()
/*     */       {
/*     */         public String toString() {
/* 528 */           return "UNLOADED"; } }; } class null implements ChunkLoadingFailure { public String toString() { return "UNLOADED"; }
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ChunkSaveDebug
/*     */   {
/*     */     private final Thread thread;
/*     */ 
/*     */     
/*     */     private final CompletableFuture<?> future;
/*     */ 
/*     */     
/*     */     private final String source;
/*     */ 
/*     */ 
/*     */     
/*     */     ChunkSaveDebug(Thread $$0, CompletableFuture<?> $$1, String $$2) {
/* 548 */       this.thread = $$0;
/* 549 */       this.future = $$1;
/* 550 */       this.source = $$2;
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface LevelChangeListener {
/*     */     void onLevelChange(ChunkPos param1ChunkPos, IntSupplier param1IntSupplier, int param1Int, IntConsumer param1IntConsumer);
/*     */   }
/*     */   
/*     */   public static interface PlayerProvider {
/*     */     List<ServerPlayer> getPlayers(ChunkPos param1ChunkPos, boolean param1Boolean);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ChunkHolder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */