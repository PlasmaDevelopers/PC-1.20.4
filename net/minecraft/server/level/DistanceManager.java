/*     */ package net.minecraft.server.level;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntMaps;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.util.SortedArraySet;
/*     */ import net.minecraft.util.thread.ProcessorHandle;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public abstract class DistanceManager
/*     */ {
/*  43 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  45 */   static final int PLAYER_TICKET_LEVEL = ChunkLevel.byStatus(FullChunkStatus.ENTITY_TICKING);
/*     */   
/*     */   private static final int INITIAL_TICKET_LIST_CAPACITY = 4;
/*     */   
/*  49 */   final Long2ObjectMap<ObjectSet<ServerPlayer>> playersPerChunk = (Long2ObjectMap<ObjectSet<ServerPlayer>>)new Long2ObjectOpenHashMap();
/*  50 */   final Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> tickets = new Long2ObjectOpenHashMap();
/*     */   
/*  52 */   private final ChunkTicketTracker ticketTracker = new ChunkTicketTracker();
/*     */   
/*  54 */   private final FixedPlayerDistanceChunkTracker naturalSpawnChunkCounter = new FixedPlayerDistanceChunkTracker(8);
/*  55 */   private final TickingTracker tickingTicketsTracker = new TickingTracker();
/*  56 */   private final PlayerTicketTracker playerTicketManager = new PlayerTicketTracker(32);
/*     */   
/*  58 */   final Set<ChunkHolder> chunksToUpdateFutures = Sets.newHashSet();
/*     */   final ChunkTaskPriorityQueueSorter ticketThrottler;
/*     */   final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> ticketThrottlerInput;
/*     */   final ProcessorHandle<ChunkTaskPriorityQueueSorter.Release> ticketThrottlerReleaser;
/*  62 */   final LongSet ticketsToRelease = (LongSet)new LongOpenHashSet();
/*     */   
/*     */   final Executor mainThreadExecutor;
/*     */   private long ticketTickCounter;
/*  66 */   private int simulationDistance = 10;
/*     */ 
/*     */ 
/*     */   
/*     */   protected DistanceManager(Executor $$0, Executor $$1) {
/*  71 */     Objects.requireNonNull($$1); ProcessorHandle<Runnable> $$2 = ProcessorHandle.of("player ticket throttler", $$1::execute);
/*     */     
/*  73 */     ChunkTaskPriorityQueueSorter $$3 = new ChunkTaskPriorityQueueSorter((List<ProcessorHandle<?>>)ImmutableList.of($$2), $$0, 4);
/*  74 */     this.ticketThrottler = $$3;
/*  75 */     this.ticketThrottlerInput = $$3.getProcessor($$2, true);
/*  76 */     this.ticketThrottlerReleaser = $$3.getReleaseProcessor($$2);
/*  77 */     this.mainThreadExecutor = $$1;
/*     */   }
/*     */   
/*     */   protected void purgeStaleTickets() {
/*  81 */     this.ticketTickCounter++;
/*  82 */     ObjectIterator<Long2ObjectMap.Entry<SortedArraySet<Ticket<?>>>> $$0 = this.tickets.long2ObjectEntrySet().fastIterator();
/*  83 */     while ($$0.hasNext()) {
/*  84 */       Long2ObjectMap.Entry<SortedArraySet<Ticket<?>>> $$1 = (Long2ObjectMap.Entry<SortedArraySet<Ticket<?>>>)$$0.next();
/*  85 */       Iterator<Ticket<?>> $$2 = ((SortedArraySet)$$1.getValue()).iterator();
/*  86 */       boolean $$3 = false;
/*  87 */       while ($$2.hasNext()) {
/*  88 */         Ticket<?> $$4 = $$2.next();
/*  89 */         if ($$4.timedOut(this.ticketTickCounter)) {
/*  90 */           $$2.remove();
/*  91 */           $$3 = true;
/*  92 */           this.tickingTicketsTracker.removeTicket($$1.getLongKey(), $$4);
/*     */         } 
/*     */       } 
/*  95 */       if ($$3) {
/*  96 */         this.ticketTracker.update($$1.getLongKey(), getTicketLevelAt((SortedArraySet<Ticket<?>>)$$1.getValue()), false);
/*     */       }
/*  98 */       if (((SortedArraySet)$$1.getValue()).isEmpty()) {
/*  99 */         $$0.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getTicketLevelAt(SortedArraySet<Ticket<?>> $$0) {
/* 105 */     return !$$0.isEmpty() ? ((Ticket)$$0.first()).getTicketLevel() : (ChunkLevel.MAX_LEVEL + 1);
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
/*     */   public boolean runAllUpdates(ChunkMap $$0) {
/* 117 */     this.naturalSpawnChunkCounter.runAllUpdates();
/* 118 */     this.tickingTicketsTracker.runAllUpdates();
/* 119 */     this.playerTicketManager.runAllUpdates();
/*     */     
/* 121 */     int $$1 = Integer.MAX_VALUE - this.ticketTracker.runDistanceUpdates(2147483647);
/* 122 */     boolean $$2 = ($$1 != 0);
/* 123 */     if ($$2);
/*     */ 
/*     */     
/* 126 */     if (!this.chunksToUpdateFutures.isEmpty()) {
/* 127 */       this.chunksToUpdateFutures.forEach($$1 -> $$1.updateFutures($$0, this.mainThreadExecutor));
/* 128 */       this.chunksToUpdateFutures.clear();
/* 129 */       return true;
/*     */     } 
/* 131 */     if (!this.ticketsToRelease.isEmpty()) {
/* 132 */       LongIterator $$3 = this.ticketsToRelease.iterator();
/* 133 */       while ($$3.hasNext()) {
/* 134 */         long $$4 = $$3.nextLong();
/* 135 */         if (getTickets($$4).stream().anyMatch($$0 -> ($$0.getType() == TicketType.PLAYER))) {
/* 136 */           ChunkHolder $$5 = $$0.getUpdatingChunkIfPresent($$4);
/* 137 */           if ($$5 == null) {
/* 138 */             throw new IllegalStateException();
/*     */           }
/* 140 */           CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> $$6 = $$5.getEntityTickingChunkFuture();
/* 141 */           $$6.thenAccept($$1 -> this.mainThreadExecutor.execute(()));
/*     */         } 
/*     */       } 
/* 144 */       this.ticketsToRelease.clear();
/*     */     } 
/* 146 */     return $$2;
/*     */   }
/*     */   
/*     */   void addTicket(long $$0, Ticket<?> $$1) {
/* 150 */     SortedArraySet<Ticket<?>> $$2 = getTickets($$0);
/* 151 */     int $$3 = getTicketLevelAt($$2);
/*     */     
/* 153 */     Ticket<?> $$4 = (Ticket)$$2.addOrGet($$1);
/*     */ 
/*     */ 
/*     */     
/* 157 */     $$4.setCreatedTick(this.ticketTickCounter);
/* 158 */     if ($$1.getTicketLevel() < $$3) {
/* 159 */       this.ticketTracker.update($$0, $$1.getTicketLevel(), true);
/*     */     }
/*     */   }
/*     */   
/*     */   void removeTicket(long $$0, Ticket<?> $$1) {
/* 164 */     SortedArraySet<Ticket<?>> $$2 = getTickets($$0);
/* 165 */     if ($$2.remove($$1));
/*     */ 
/*     */     
/* 168 */     if ($$2.isEmpty()) {
/* 169 */       this.tickets.remove($$0);
/*     */     }
/* 171 */     this.ticketTracker.update($$0, getTicketLevelAt($$2), false);
/*     */   }
/*     */   
/*     */   public <T> void addTicket(TicketType<T> $$0, ChunkPos $$1, int $$2, T $$3) {
/* 175 */     addTicket($$1.toLong(), new Ticket($$0, $$2, $$3));
/*     */   }
/*     */   
/*     */   public <T> void removeTicket(TicketType<T> $$0, ChunkPos $$1, int $$2, T $$3) {
/* 179 */     Ticket<T> $$4 = new Ticket<>($$0, $$2, $$3);
/* 180 */     removeTicket($$1.toLong(), $$4);
/*     */   }
/*     */   
/*     */   public <T> void addRegionTicket(TicketType<T> $$0, ChunkPos $$1, int $$2, T $$3) {
/* 184 */     Ticket<T> $$4 = new Ticket<>($$0, ChunkLevel.byStatus(FullChunkStatus.FULL) - $$2, $$3);
/* 185 */     long $$5 = $$1.toLong();
/* 186 */     addTicket($$5, $$4);
/* 187 */     this.tickingTicketsTracker.addTicket($$5, $$4);
/*     */   }
/*     */   
/*     */   public <T> void removeRegionTicket(TicketType<T> $$0, ChunkPos $$1, int $$2, T $$3) {
/* 191 */     Ticket<T> $$4 = new Ticket<>($$0, ChunkLevel.byStatus(FullChunkStatus.FULL) - $$2, $$3);
/* 192 */     long $$5 = $$1.toLong();
/* 193 */     removeTicket($$5, $$4);
/* 194 */     this.tickingTicketsTracker.removeTicket($$5, $$4);
/*     */   }
/*     */   
/*     */   private SortedArraySet<Ticket<?>> getTickets(long $$0) {
/* 198 */     return (SortedArraySet<Ticket<?>>)this.tickets.computeIfAbsent($$0, $$0 -> SortedArraySet.create(4));
/*     */   }
/*     */   
/*     */   protected void updateChunkForced(ChunkPos $$0, boolean $$1) {
/* 202 */     Ticket<ChunkPos> $$2 = new Ticket<>(TicketType.FORCED, ChunkMap.FORCED_TICKET_LEVEL, $$0);
/* 203 */     long $$3 = $$0.toLong();
/* 204 */     if ($$1) {
/* 205 */       addTicket($$3, $$2);
/* 206 */       this.tickingTicketsTracker.addTicket($$3, $$2);
/*     */     } else {
/* 208 */       removeTicket($$3, $$2);
/* 209 */       this.tickingTicketsTracker.removeTicket($$3, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addPlayer(SectionPos $$0, ServerPlayer $$1) {
/* 214 */     ChunkPos $$2 = $$0.chunk();
/* 215 */     long $$3 = $$2.toLong();
/* 216 */     ((ObjectSet)this.playersPerChunk.computeIfAbsent($$3, $$0 -> new ObjectOpenHashSet())).add($$1);
/* 217 */     this.naturalSpawnChunkCounter.update($$3, 0, true);
/* 218 */     this.playerTicketManager.update($$3, 0, true);
/* 219 */     this.tickingTicketsTracker.addTicket(TicketType.PLAYER, $$2, getPlayerTicketLevel(), $$2);
/*     */   }
/*     */   
/*     */   public void removePlayer(SectionPos $$0, ServerPlayer $$1) {
/* 223 */     ChunkPos $$2 = $$0.chunk();
/* 224 */     long $$3 = $$2.toLong();
/* 225 */     ObjectSet<ServerPlayer> $$4 = (ObjectSet<ServerPlayer>)this.playersPerChunk.get($$3);
/* 226 */     $$4.remove($$1);
/* 227 */     if ($$4.isEmpty()) {
/* 228 */       this.playersPerChunk.remove($$3);
/* 229 */       this.naturalSpawnChunkCounter.update($$3, 2147483647, false);
/* 230 */       this.playerTicketManager.update($$3, 2147483647, false);
/* 231 */       this.tickingTicketsTracker.removeTicket(TicketType.PLAYER, $$2, getPlayerTicketLevel(), $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getPlayerTicketLevel() {
/* 236 */     return Math.max(0, ChunkLevel.byStatus(FullChunkStatus.ENTITY_TICKING) - this.simulationDistance);
/*     */   }
/*     */   
/*     */   public boolean inEntityTickingRange(long $$0) {
/* 240 */     return ChunkLevel.isEntityTicking(this.tickingTicketsTracker.getLevel($$0));
/*     */   }
/*     */   
/*     */   public boolean inBlockTickingRange(long $$0) {
/* 244 */     return ChunkLevel.isBlockTicking(this.tickingTicketsTracker.getLevel($$0));
/*     */   }
/*     */   
/*     */   protected String getTicketDebugString(long $$0) {
/* 248 */     SortedArraySet<Ticket<?>> $$1 = (SortedArraySet<Ticket<?>>)this.tickets.get($$0);
/* 249 */     if ($$1 == null || $$1.isEmpty()) {
/* 250 */       return "no_ticket";
/*     */     }
/* 252 */     return ((Ticket)$$1.first()).toString();
/*     */   }
/*     */   
/*     */   protected void updatePlayerTickets(int $$0) {
/* 256 */     this.playerTicketManager.updateViewDistance($$0);
/*     */   }
/*     */   
/*     */   public void updateSimulationDistance(int $$0) {
/* 260 */     if ($$0 != this.simulationDistance) {
/* 261 */       this.simulationDistance = $$0;
/* 262 */       this.tickingTicketsTracker.replacePlayerTicketsLevel(getPlayerTicketLevel());
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getNaturalSpawnChunkCount() {
/* 267 */     this.naturalSpawnChunkCounter.runAllUpdates();
/* 268 */     return this.naturalSpawnChunkCounter.chunks.size();
/*     */   }
/*     */   
/*     */   public boolean hasPlayersNearby(long $$0) {
/* 272 */     this.naturalSpawnChunkCounter.runAllUpdates();
/* 273 */     return this.naturalSpawnChunkCounter.chunks.containsKey($$0);
/*     */   }
/*     */   
/*     */   public String getDebugStatus() {
/* 277 */     return this.ticketThrottler.getDebugStatus();
/*     */   }
/*     */   
/*     */   private void dumpTickets(String $$0) {
/*     */     
/* 282 */     try { FileOutputStream $$1 = new FileOutputStream(new File($$0)); 
/* 283 */       try { for (ObjectIterator<Long2ObjectMap.Entry<SortedArraySet<Ticket<?>>>> objectIterator = this.tickets.long2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Long2ObjectMap.Entry<SortedArraySet<Ticket<?>>> $$2 = objectIterator.next();
/* 284 */           ChunkPos $$3 = new ChunkPos($$2.getLongKey());
/* 285 */           for (Ticket<?> $$4 : (Iterable<Ticket<?>>)$$2.getValue()) {
/* 286 */             $$1.write(("" + $$3.x + "\t" + $$3.x + "\t" + $$3.z + "\t" + $$4
/*     */ 
/*     */                 
/* 289 */                 .getType() + "\t\n")
/*     */                 
/* 291 */                 .getBytes(StandardCharsets.UTF_8));
/*     */           } }
/*     */         
/* 294 */         $$1.close(); } catch (Throwable throwable) { try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException $$5)
/* 295 */     { LOGGER.error("Failed to dump tickets to {}", $$0, $$5); }
/*     */   
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   TickingTracker tickingTracker() {
/* 301 */     return this.tickingTicketsTracker;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeTicketsOnClosing() {
/* 306 */     ImmutableSet<TicketType<?>> $$0 = ImmutableSet.of(TicketType.UNKNOWN, TicketType.POST_TELEPORT, TicketType.LIGHT);
/* 307 */     ObjectIterator<Long2ObjectMap.Entry<SortedArraySet<Ticket<?>>>> $$1 = this.tickets.long2ObjectEntrySet().fastIterator();
/* 308 */     while ($$1.hasNext()) {
/* 309 */       Long2ObjectMap.Entry<SortedArraySet<Ticket<?>>> $$2 = (Long2ObjectMap.Entry<SortedArraySet<Ticket<?>>>)$$1.next();
/* 310 */       Iterator<Ticket<?>> $$3 = ((SortedArraySet)$$2.getValue()).iterator();
/* 311 */       boolean $$4 = false;
/* 312 */       while ($$3.hasNext()) {
/* 313 */         Ticket<?> $$5 = $$3.next();
/* 314 */         if (!$$0.contains($$5.getType())) {
/* 315 */           $$3.remove();
/* 316 */           $$4 = true;
/* 317 */           this.tickingTicketsTracker.removeTicket($$2.getLongKey(), $$5);
/*     */         } 
/*     */       } 
/* 320 */       if ($$4) {
/* 321 */         this.ticketTracker.update($$2.getLongKey(), getTicketLevelAt((SortedArraySet<Ticket<?>>)$$2.getValue()), false);
/*     */       }
/* 323 */       if (((SortedArraySet)$$2.getValue()).isEmpty()) {
/* 324 */         $$1.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasTickets() {
/* 330 */     return !this.tickets.isEmpty();
/*     */   } protected abstract boolean isChunkToRemove(long paramLong); @Nullable
/*     */   protected abstract ChunkHolder getChunk(long paramLong); @Nullable
/*     */   protected abstract ChunkHolder updateChunkScheduling(long paramLong, int paramInt1, @Nullable ChunkHolder paramChunkHolder, int paramInt2);
/* 334 */   private class FixedPlayerDistanceChunkTracker extends ChunkTracker { protected final Long2ByteMap chunks = (Long2ByteMap)new Long2ByteOpenHashMap();
/*     */     protected final int maxDistance;
/*     */     
/*     */     protected FixedPlayerDistanceChunkTracker(int $$0) {
/* 338 */       super($$0 + 2, 16, 256);
/* 339 */       this.maxDistance = $$0;
/* 340 */       this.chunks.defaultReturnValue((byte)($$0 + 2));
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getLevel(long $$0) {
/* 345 */       return this.chunks.get($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void setLevel(long $$0, int $$1) {
/*     */       byte $$3;
/* 351 */       if ($$1 > this.maxDistance) {
/* 352 */         byte $$2 = this.chunks.remove($$0);
/*     */       } else {
/* 354 */         $$3 = this.chunks.put($$0, (byte)$$1);
/*     */       } 
/* 356 */       onLevelChange($$0, $$3, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onLevelChange(long $$0, int $$1, int $$2) {}
/*     */ 
/*     */     
/*     */     protected int getLevelFromSource(long $$0) {
/* 364 */       return havePlayer($$0) ? 0 : Integer.MAX_VALUE;
/*     */     }
/*     */     
/*     */     private boolean havePlayer(long $$0) {
/* 368 */       ObjectSet<ServerPlayer> $$1 = (ObjectSet<ServerPlayer>)DistanceManager.this.playersPerChunk.get($$0);
/* 369 */       return ($$1 != null && !$$1.isEmpty());
/*     */     }
/*     */     
/*     */     public void runAllUpdates() {
/* 373 */       runUpdates(2147483647);
/*     */     }
/*     */     
/*     */     private void dumpChunks(String $$0) {
/*     */       
/* 378 */       try { FileOutputStream $$1 = new FileOutputStream(new File($$0)); 
/* 379 */         try { for (ObjectIterator<Long2ByteMap.Entry> objectIterator = this.chunks.long2ByteEntrySet().iterator(); objectIterator.hasNext(); ) { Long2ByteMap.Entry $$2 = objectIterator.next();
/* 380 */             ChunkPos $$3 = new ChunkPos($$2.getLongKey());
/* 381 */             String $$4 = Byte.toString($$2.getByteValue());
/* 382 */             $$1.write(("" + $$3.x + "\t" + $$3.x + "\t" + $$3.z + "\n")
/*     */ 
/*     */ 
/*     */                 
/* 386 */                 .getBytes(StandardCharsets.UTF_8)); }
/*     */           
/* 388 */           $$1.close(); } catch (Throwable throwable) { try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException $$5)
/* 389 */       { DistanceManager.LOGGER.error("Failed to dump chunks to {}", $$0, $$5); }
/*     */     
/*     */     } }
/*     */ 
/*     */   
/*     */   private class PlayerTicketTracker extends FixedPlayerDistanceChunkTracker {
/*     */     private int viewDistance;
/* 396 */     private final Long2IntMap queueLevels = Long2IntMaps.synchronize((Long2IntMap)new Long2IntOpenHashMap());
/* 397 */     private final LongSet toUpdate = (LongSet)new LongOpenHashSet();
/*     */     
/*     */     protected PlayerTicketTracker(int $$0) {
/* 400 */       super($$0);
/* 401 */       this.viewDistance = 0;
/* 402 */       this.queueLevels.defaultReturnValue($$0 + 2);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onLevelChange(long $$0, int $$1, int $$2) {
/* 407 */       this.toUpdate.add($$0);
/*     */     }
/*     */     
/*     */     public void updateViewDistance(int $$0) {
/* 411 */       for (ObjectIterator<Long2ByteMap.Entry> objectIterator = this.chunks.long2ByteEntrySet().iterator(); objectIterator.hasNext(); ) { Long2ByteMap.Entry $$1 = objectIterator.next();
/* 412 */         byte $$2 = $$1.getByteValue();
/* 413 */         long $$3 = $$1.getLongKey();
/* 414 */         onLevelChange($$3, $$2, haveTicketFor($$2), ($$2 <= $$0)); }
/*     */       
/* 416 */       this.viewDistance = $$0;
/*     */     }
/*     */     
/*     */     private void onLevelChange(long $$0, int $$1, boolean $$2, boolean $$3) {
/* 420 */       if ($$2 != $$3) {
/* 421 */         Ticket<?> $$4 = new Ticket(TicketType.PLAYER, DistanceManager.PLAYER_TICKET_LEVEL, new ChunkPos($$0));
/* 422 */         if ($$3) {
/* 423 */           DistanceManager.this.ticketThrottlerInput.tell(ChunkTaskPriorityQueueSorter.message(() -> DistanceManager.this.mainThreadExecutor.execute(()), $$0, () -> $$0));
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */           
/* 432 */           DistanceManager.this.ticketThrottlerReleaser.tell(ChunkTaskPriorityQueueSorter.release(() -> DistanceManager.this.mainThreadExecutor.execute(()), $$0, true));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void runAllUpdates() {
/* 439 */       super.runAllUpdates();
/* 440 */       if (!this.toUpdate.isEmpty()) {
/* 441 */         LongIterator $$0 = this.toUpdate.iterator();
/* 442 */         while ($$0.hasNext()) {
/* 443 */           long $$1 = $$0.nextLong();
/* 444 */           int $$2 = this.queueLevels.get($$1);
/* 445 */           int $$3 = getLevel($$1);
/* 446 */           if ($$2 != $$3) {
/* 447 */             DistanceManager.this.ticketThrottler.onLevelChange(new ChunkPos($$1), () -> this.queueLevels.get($$0), $$3, $$1 -> {
/*     */                   if ($$1 >= this.queueLevels.defaultReturnValue()) {
/*     */                     this.queueLevels.remove($$0);
/*     */                   } else {
/*     */                     this.queueLevels.put($$0, $$1);
/*     */                   } 
/*     */                 });
/* 454 */             onLevelChange($$1, $$3, haveTicketFor($$2), haveTicketFor($$3));
/*     */           } 
/*     */         } 
/* 457 */         this.toUpdate.clear();
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean haveTicketFor(int $$0) {
/* 462 */       return ($$0 <= this.viewDistance);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ChunkTicketTracker extends ChunkTracker {
/* 467 */     private static final int MAX_LEVEL = ChunkLevel.MAX_LEVEL + 1;
/*     */     
/*     */     public ChunkTicketTracker() {
/* 470 */       super(MAX_LEVEL + 1, 16, 256);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getLevelFromSource(long $$0) {
/* 475 */       SortedArraySet<Ticket<?>> $$1 = (SortedArraySet<Ticket<?>>)DistanceManager.this.tickets.get($$0);
/* 476 */       if ($$1 == null) {
/* 477 */         return Integer.MAX_VALUE;
/*     */       }
/* 479 */       if ($$1.isEmpty()) {
/* 480 */         return Integer.MAX_VALUE;
/*     */       }
/* 482 */       return ((Ticket)$$1.first()).getTicketLevel();
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getLevel(long $$0) {
/* 487 */       if (!DistanceManager.this.isChunkToRemove($$0)) {
/* 488 */         ChunkHolder $$1 = DistanceManager.this.getChunk($$0);
/* 489 */         if ($$1 != null) {
/* 490 */           return $$1.getTicketLevel();
/*     */         }
/*     */       } 
/* 493 */       return MAX_LEVEL;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void setLevel(long $$0, int $$1) {
/* 498 */       ChunkHolder $$2 = DistanceManager.this.getChunk($$0);
/* 499 */       int $$3 = ($$2 == null) ? MAX_LEVEL : $$2.getTicketLevel();
/* 500 */       if ($$3 == $$1) {
/*     */         return;
/*     */       }
/* 503 */       $$2 = DistanceManager.this.updateChunkScheduling($$0, $$1, $$2, $$3);
/* 504 */       if ($$2 != null) {
/* 505 */         DistanceManager.this.chunksToUpdateFutures.add($$2);
/*     */       }
/*     */     }
/*     */     
/*     */     public int runDistanceUpdates(int $$0) {
/* 510 */       return runUpdates($$0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\DistanceManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */