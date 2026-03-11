/*     */ package net.minecraft.world.level.entity;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Queues;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.io.IOException;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.io.Writer;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.server.level.FullChunkStatus;
/*     */ import net.minecraft.util.CsvOutput;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class PersistentEntitySectionManager<T extends EntityAccess> implements AutoCloseable {
/*     */   private enum ChunkLoadStatus {
/*  35 */     FRESH,
/*  36 */     PENDING,
/*  37 */     LOADED;
/*     */   }
/*     */   
/*     */   private class Callback
/*     */     implements EntityInLevelCallback {
/*     */     private final T entity;
/*     */     private long currentSectionKey;
/*     */     private EntitySection<T> currentSection;
/*     */     
/*     */     Callback(T $$0, long $$1, EntitySection<T> $$2) {
/*  47 */       this.entity = $$0;
/*  48 */       this.currentSectionKey = $$1;
/*  49 */       this.currentSection = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onMove() {
/*  54 */       BlockPos $$0 = this.entity.blockPosition();
/*  55 */       long $$1 = SectionPos.asLong($$0);
/*  56 */       if ($$1 != this.currentSectionKey) {
/*  57 */         Visibility $$2 = this.currentSection.getStatus();
/*  58 */         if (!this.currentSection.remove(this.entity)) {
/*  59 */           PersistentEntitySectionManager.LOGGER.warn("Entity {} wasn't found in section {} (moving to {})", new Object[] { this.entity, SectionPos.of(this.currentSectionKey), Long.valueOf($$1) });
/*     */         }
/*  61 */         PersistentEntitySectionManager.this.removeSectionIfEmpty(this.currentSectionKey, this.currentSection);
/*     */         
/*  63 */         EntitySection<T> $$3 = PersistentEntitySectionManager.this.sectionStorage.getOrCreateSection($$1);
/*  64 */         $$3.add(this.entity);
/*  65 */         this.currentSection = $$3;
/*  66 */         this.currentSectionKey = $$1;
/*     */         
/*  68 */         updateStatus($$2, $$3.getStatus());
/*     */       } 
/*     */     }
/*     */     
/*     */     private void updateStatus(Visibility $$0, Visibility $$1) {
/*  73 */       Visibility $$2 = PersistentEntitySectionManager.getEffectiveStatus(this.entity, $$0);
/*  74 */       Visibility $$3 = PersistentEntitySectionManager.getEffectiveStatus(this.entity, $$1);
/*     */       
/*  76 */       if ($$2 == $$3) {
/*  77 */         if ($$3.isAccessible()) {
/*  78 */           PersistentEntitySectionManager.this.callbacks.onSectionChange(this.entity);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*  83 */       boolean $$4 = $$2.isAccessible();
/*  84 */       boolean $$5 = $$3.isAccessible();
/*  85 */       if ($$4 && !$$5) {
/*  86 */         PersistentEntitySectionManager.this.stopTracking(this.entity);
/*  87 */       } else if (!$$4 && $$5) {
/*  88 */         PersistentEntitySectionManager.this.startTracking(this.entity);
/*     */       } 
/*     */       
/*  91 */       boolean $$6 = $$2.isTicking();
/*  92 */       boolean $$7 = $$3.isTicking();
/*  93 */       if ($$6 && !$$7) {
/*  94 */         PersistentEntitySectionManager.this.stopTicking(this.entity);
/*  95 */       } else if (!$$6 && $$7) {
/*  96 */         PersistentEntitySectionManager.this.startTicking(this.entity);
/*     */       } 
/*     */       
/*  99 */       if ($$5) {
/* 100 */         PersistentEntitySectionManager.this.callbacks.onSectionChange(this.entity);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void onRemove(Entity.RemovalReason $$0) {
/* 106 */       if (!this.currentSection.remove(this.entity)) {
/* 107 */         PersistentEntitySectionManager.LOGGER.warn("Entity {} wasn't found in section {} (destroying due to {})", new Object[] { this.entity, SectionPos.of(this.currentSectionKey), $$0 });
/*     */       }
/*     */       
/* 110 */       Visibility $$1 = PersistentEntitySectionManager.getEffectiveStatus(this.entity, this.currentSection.getStatus());
/* 111 */       if ($$1.isTicking()) {
/* 112 */         PersistentEntitySectionManager.this.stopTicking(this.entity);
/*     */       }
/* 114 */       if ($$1.isAccessible()) {
/* 115 */         PersistentEntitySectionManager.this.stopTracking(this.entity);
/*     */       }
/* 117 */       if ($$0.shouldDestroy()) {
/* 118 */         PersistentEntitySectionManager.this.callbacks.onDestroyed(this.entity);
/*     */       }
/* 120 */       PersistentEntitySectionManager.this.knownUuids.remove(this.entity.getUUID());
/* 121 */       this.entity.setLevelCallback(NULL);
/*     */       
/* 123 */       PersistentEntitySectionManager.this.removeSectionIfEmpty(this.currentSectionKey, this.currentSection);
/*     */     }
/*     */   }
/*     */   
/* 127 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/* 129 */   final Set<UUID> knownUuids = Sets.newHashSet();
/*     */   
/*     */   final LevelCallback<T> callbacks;
/*     */   private final EntityPersistentStorage<T> permanentStorage;
/*     */   private final EntityLookup<T> visibleEntityStorage;
/*     */   final EntitySectionStorage<T> sectionStorage;
/*     */   private final LevelEntityGetter<T> entityGetter;
/* 136 */   private final Long2ObjectMap<Visibility> chunkVisibility = (Long2ObjectMap<Visibility>)new Long2ObjectOpenHashMap();
/* 137 */   private final Long2ObjectMap<ChunkLoadStatus> chunkLoadStatuses = (Long2ObjectMap<ChunkLoadStatus>)new Long2ObjectOpenHashMap();
/*     */   
/* 139 */   private final LongSet chunksToUnload = (LongSet)new LongOpenHashSet();
/* 140 */   private final Queue<ChunkEntities<T>> loadingInbox = Queues.newConcurrentLinkedQueue();
/*     */   
/*     */   public PersistentEntitySectionManager(Class<T> $$0, LevelCallback<T> $$1, EntityPersistentStorage<T> $$2) {
/* 143 */     this.visibleEntityStorage = new EntityLookup<>();
/* 144 */     this.sectionStorage = new EntitySectionStorage<>($$0, (Long2ObjectFunction<Visibility>)this.chunkVisibility);
/* 145 */     this.chunkVisibility.defaultReturnValue(Visibility.HIDDEN);
/* 146 */     this.chunkLoadStatuses.defaultReturnValue(ChunkLoadStatus.FRESH);
/* 147 */     this.callbacks = $$1;
/* 148 */     this.permanentStorage = $$2;
/* 149 */     this.entityGetter = new LevelEntityGetterAdapter<>(this.visibleEntityStorage, this.sectionStorage);
/*     */   }
/*     */   
/*     */   void removeSectionIfEmpty(long $$0, EntitySection<T> $$1) {
/* 153 */     if ($$1.isEmpty()) {
/* 154 */       this.sectionStorage.remove($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean addEntityUuid(T $$0) {
/* 159 */     if (!this.knownUuids.add($$0.getUUID())) {
/* 160 */       LOGGER.warn("UUID of added entity already exists: {}", $$0);
/* 161 */       return false;
/*     */     } 
/* 163 */     return true;
/*     */   }
/*     */   
/*     */   public boolean addNewEntity(T $$0) {
/* 167 */     return addEntity($$0, false);
/*     */   }
/*     */   
/*     */   private boolean addEntity(T $$0, boolean $$1) {
/* 171 */     if (!addEntityUuid($$0)) {
/* 172 */       return false;
/*     */     }
/*     */     
/* 175 */     long $$2 = SectionPos.asLong($$0.blockPosition());
/* 176 */     EntitySection<T> $$3 = this.sectionStorage.getOrCreateSection($$2);
/* 177 */     $$3.add($$0);
/*     */     
/* 179 */     $$0.setLevelCallback(new Callback($$0, $$2, $$3));
/* 180 */     if (!$$1) {
/* 181 */       this.callbacks.onCreated($$0);
/*     */     }
/*     */     
/* 184 */     Visibility $$4 = getEffectiveStatus($$0, $$3.getStatus());
/* 185 */     if ($$4.isAccessible()) {
/* 186 */       startTracking($$0);
/*     */     }
/* 188 */     if ($$4.isTicking()) {
/* 189 */       startTicking($$0);
/*     */     }
/* 191 */     return true;
/*     */   }
/*     */   
/*     */   static <T extends EntityAccess> Visibility getEffectiveStatus(T $$0, Visibility $$1) {
/* 195 */     return $$0.isAlwaysTicking() ? Visibility.TICKING : $$1;
/*     */   }
/*     */   
/*     */   public void addLegacyChunkEntities(Stream<T> $$0) {
/* 199 */     $$0.forEach($$0 -> addEntity((T)$$0, true));
/*     */   }
/*     */   
/*     */   public void addWorldGenChunkEntities(Stream<T> $$0) {
/* 203 */     $$0.forEach($$0 -> addEntity((T)$$0, false));
/*     */   }
/*     */   
/*     */   void startTicking(T $$0) {
/* 207 */     this.callbacks.onTickingStart($$0);
/*     */   }
/*     */   
/*     */   void stopTicking(T $$0) {
/* 211 */     this.callbacks.onTickingEnd($$0);
/*     */   }
/*     */   
/*     */   void startTracking(T $$0) {
/* 215 */     this.visibleEntityStorage.add($$0);
/* 216 */     this.callbacks.onTrackingStart($$0);
/*     */   }
/*     */   
/*     */   void stopTracking(T $$0) {
/* 220 */     this.callbacks.onTrackingEnd($$0);
/* 221 */     this.visibleEntityStorage.remove($$0);
/*     */   }
/*     */   
/*     */   public void updateChunkStatus(ChunkPos $$0, FullChunkStatus $$1) {
/* 225 */     Visibility $$2 = Visibility.fromFullChunkStatus($$1);
/* 226 */     updateChunkStatus($$0, $$2);
/*     */   }
/*     */   
/*     */   public void updateChunkStatus(ChunkPos $$0, Visibility $$1) {
/* 230 */     long $$2 = $$0.toLong();
/* 231 */     if ($$1 == Visibility.HIDDEN) {
/* 232 */       this.chunkVisibility.remove($$2);
/* 233 */       this.chunksToUnload.add($$2);
/*     */     } else {
/* 235 */       this.chunkVisibility.put($$2, $$1);
/* 236 */       this.chunksToUnload.remove($$2);
/* 237 */       ensureChunkQueuedForLoad($$2);
/*     */     } 
/*     */     
/* 240 */     this.sectionStorage.getExistingSectionsInChunk($$2).forEach($$1 -> {
/*     */           Visibility $$2 = $$1.updateChunkStatus($$0);
/*     */           boolean $$3 = $$2.isAccessible();
/*     */           boolean $$4 = $$0.isAccessible();
/*     */           boolean $$5 = $$2.isTicking();
/*     */           boolean $$6 = $$0.isTicking();
/*     */           if ($$5 && !$$6) {
/*     */             $$1.getEntities().filter(()).forEach(this::stopTicking);
/*     */           }
/*     */           if ($$3 && !$$4) {
/*     */             $$1.getEntities().filter(()).forEach(this::stopTracking);
/*     */           } else if (!$$3 && $$4) {
/*     */             $$1.getEntities().filter(()).forEach(this::startTracking);
/*     */           } 
/*     */           if (!$$5 && $$6) {
/*     */             $$1.getEntities().filter(()).forEach(this::startTicking);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureChunkQueuedForLoad(long $$0) {
/* 266 */     ChunkLoadStatus $$1 = (ChunkLoadStatus)this.chunkLoadStatuses.get($$0);
/*     */     
/* 268 */     if ($$1 == ChunkLoadStatus.FRESH) {
/* 269 */       requestChunkLoad($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean storeChunkSections(long $$0, Consumer<T> $$1) {
/* 274 */     ChunkLoadStatus $$2 = (ChunkLoadStatus)this.chunkLoadStatuses.get($$0);
/* 275 */     if ($$2 == ChunkLoadStatus.PENDING) {
/* 276 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 281 */     List<T> $$3 = (List<T>)this.sectionStorage.getExistingSectionsInChunk($$0).flatMap($$0 -> $$0.getEntities().filter(EntityAccess::shouldBeSaved)).collect(Collectors.toList());
/*     */     
/* 283 */     if ($$3.isEmpty()) {
/* 284 */       if ($$2 == ChunkLoadStatus.LOADED)
/*     */       {
/* 286 */         this.permanentStorage.storeEntities(new ChunkEntities<>(new ChunkPos($$0), (List<T>)ImmutableList.of()));
/*     */       }
/* 288 */       return true;
/*     */     } 
/*     */     
/* 291 */     if ($$2 == ChunkLoadStatus.FRESH) {
/*     */       
/* 293 */       requestChunkLoad($$0);
/* 294 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 299 */     this.permanentStorage.storeEntities(new ChunkEntities<>(new ChunkPos($$0), $$3));
/* 300 */     $$3.forEach($$1);
/* 301 */     return true;
/*     */   }
/*     */   
/*     */   private void requestChunkLoad(long $$0) {
/* 305 */     this.chunkLoadStatuses.put($$0, ChunkLoadStatus.PENDING);
/* 306 */     ChunkPos $$1 = new ChunkPos($$0);
/*     */     
/* 308 */     Objects.requireNonNull(this.loadingInbox); this.permanentStorage.loadEntities($$1).thenAccept(this.loadingInbox::add)
/* 309 */       .exceptionally($$1 -> {
/*     */           LOGGER.error("Failed to read chunk {}", $$0, $$1);
/*     */           return null;
/*     */         });
/*     */   }
/*     */   
/*     */   private boolean processChunkUnload(long $$0) {
/* 316 */     boolean $$1 = storeChunkSections($$0, $$0 -> $$0.getPassengersAndSelf().forEach(this::unloadEntity));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     if (!$$1)
/*     */     {
/* 324 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 328 */     this.chunkLoadStatuses.remove($$0);
/* 329 */     return true;
/*     */   }
/*     */   
/*     */   private void unloadEntity(EntityAccess $$0) {
/* 333 */     $$0.setRemoved(Entity.RemovalReason.UNLOADED_TO_CHUNK);
/* 334 */     $$0.setLevelCallback(EntityInLevelCallback.NULL);
/*     */   }
/*     */   
/*     */   private void processUnloads() {
/* 338 */     this.chunksToUnload.removeIf($$0 -> (this.chunkVisibility.get($$0) != Visibility.HIDDEN) ? true : processChunkUnload($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processPendingLoads() {
/*     */     ChunkEntities<T> $$0;
/* 349 */     while (($$0 = this.loadingInbox.poll()) != null) {
/*     */       
/* 351 */       $$0.getEntities().forEach($$0 -> addEntity((T)$$0, true));
/* 352 */       this.chunkLoadStatuses.put($$0.getPos().toLong(), ChunkLoadStatus.LOADED);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tick() {
/* 357 */     processPendingLoads();
/* 358 */     processUnloads();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private LongSet getAllChunksToSave() {
/* 364 */     LongSet $$0 = this.sectionStorage.getAllChunksWithExistingSections();
/* 365 */     for (ObjectIterator<Long2ObjectMap.Entry<ChunkLoadStatus>> objectIterator = Long2ObjectMaps.fastIterable(this.chunkLoadStatuses).iterator(); objectIterator.hasNext(); ) { Long2ObjectMap.Entry<ChunkLoadStatus> $$1 = objectIterator.next();
/* 366 */       if ($$1.getValue() == ChunkLoadStatus.LOADED) {
/* 367 */         $$0.add($$1.getLongKey());
/*     */       } }
/*     */     
/* 370 */     return $$0;
/*     */   }
/*     */   
/*     */   public void autoSave() {
/* 374 */     getAllChunksToSave().forEach($$0 -> {
/*     */           boolean $$1 = (this.chunkVisibility.get($$0) == Visibility.HIDDEN);
/*     */           if ($$1) {
/*     */             processChunkUnload($$0);
/*     */           } else {
/*     */             storeChunkSections($$0, ());
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAll() {
/* 387 */     LongSet $$0 = getAllChunksToSave();
/*     */     
/* 389 */     while (!$$0.isEmpty()) {
/* 390 */       this.permanentStorage.flush(false);
/* 391 */       processPendingLoads();
/* 392 */       $$0.removeIf($$0 -> {
/*     */             boolean $$1 = (this.chunkVisibility.get($$0) == Visibility.HIDDEN);
/*     */             return $$1 ? processChunkUnload($$0) : storeChunkSections($$0, ());
/*     */           });
/*     */     } 
/* 397 */     this.permanentStorage.flush(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 402 */     saveAll();
/* 403 */     this.permanentStorage.close();
/*     */   }
/*     */   
/*     */   public boolean isLoaded(UUID $$0) {
/* 407 */     return this.knownUuids.contains($$0);
/*     */   }
/*     */   
/*     */   public LevelEntityGetter<T> getEntityGetter() {
/* 411 */     return this.entityGetter;
/*     */   }
/*     */   
/*     */   public boolean canPositionTick(BlockPos $$0) {
/* 415 */     return ((Visibility)this.chunkVisibility.get(ChunkPos.asLong($$0))).isTicking();
/*     */   }
/*     */   
/*     */   public boolean canPositionTick(ChunkPos $$0) {
/* 419 */     return ((Visibility)this.chunkVisibility.get($$0.toLong())).isTicking();
/*     */   }
/*     */   
/*     */   public boolean areEntitiesLoaded(long $$0) {
/* 423 */     return (this.chunkLoadStatuses.get($$0) == ChunkLoadStatus.LOADED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dumpSections(Writer $$0) throws IOException {
/* 434 */     CsvOutput $$1 = CsvOutput.builder().addColumn("x").addColumn("y").addColumn("z").addColumn("visibility").addColumn("load_status").addColumn("entity_count").build($$0);
/*     */     
/* 436 */     this.sectionStorage.getAllChunksWithExistingSections().forEach($$1 -> {
/*     */           ChunkLoadStatus $$2 = (ChunkLoadStatus)this.chunkLoadStatuses.get($$1);
/*     */           this.sectionStorage.getExistingSectionPositionsInChunk($$1).forEach(());
/*     */         });
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
/*     */   @VisibleForDebug
/*     */   public String gatherStats() {
/* 462 */     return "" + this.knownUuids.size() + "," + this.knownUuids.size() + "," + this.visibleEntityStorage
/* 463 */       .count() + "," + this.sectionStorage
/* 464 */       .count() + "," + this.chunkLoadStatuses
/* 465 */       .size() + "," + this.chunkVisibility
/* 466 */       .size() + "," + this.loadingInbox
/* 467 */       .size();
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForDebug
/*     */   public int count() {
/* 473 */     return this.visibleEntityStorage.count();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\PersistentEntitySectionManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */