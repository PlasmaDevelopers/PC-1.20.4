/*     */ package net.minecraft.world.level.entity;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class TransientEntitySectionManager<T extends EntityAccess> {
/*  14 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   final LevelCallback<T> callbacks;
/*     */   final EntityLookup<T> entityStorage;
/*     */   final EntitySectionStorage<T> sectionStorage;
/*     */   
/*     */   private class Callback implements EntityInLevelCallback { private final T entity;
/*     */     
/*     */     Callback(T $$0, long $$1, EntitySection<T> $$2) {
/*  22 */       this.entity = $$0;
/*  23 */       this.currentSectionKey = $$1;
/*  24 */       this.currentSection = $$2;
/*     */     }
/*     */     private long currentSectionKey; private EntitySection<T> currentSection;
/*     */     
/*     */     public void onMove() {
/*  29 */       BlockPos $$0 = this.entity.blockPosition();
/*  30 */       long $$1 = SectionPos.asLong($$0);
/*  31 */       if ($$1 != this.currentSectionKey) {
/*  32 */         Visibility $$2 = this.currentSection.getStatus();
/*  33 */         if (!this.currentSection.remove(this.entity)) {
/*  34 */           TransientEntitySectionManager.LOGGER.warn("Entity {} wasn't found in section {} (moving to {})", new Object[] { this.entity, SectionPos.of(this.currentSectionKey), Long.valueOf($$1) });
/*     */         }
/*  36 */         TransientEntitySectionManager.this.removeSectionIfEmpty(this.currentSectionKey, this.currentSection);
/*     */         
/*  38 */         EntitySection<T> $$3 = TransientEntitySectionManager.this.sectionStorage.getOrCreateSection($$1);
/*  39 */         $$3.add(this.entity);
/*  40 */         this.currentSection = $$3;
/*  41 */         this.currentSectionKey = $$1;
/*     */         
/*  43 */         TransientEntitySectionManager.this.callbacks.onSectionChange(this.entity);
/*     */         
/*  45 */         if (!this.entity.isAlwaysTicking()) {
/*  46 */           boolean $$4 = $$2.isTicking();
/*  47 */           boolean $$5 = $$3.getStatus().isTicking();
/*  48 */           if ($$4 && !$$5) {
/*  49 */             TransientEntitySectionManager.this.callbacks.onTickingEnd(this.entity);
/*  50 */           } else if (!$$4 && $$5) {
/*  51 */             TransientEntitySectionManager.this.callbacks.onTickingStart(this.entity);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onRemove(Entity.RemovalReason $$0) {
/*  59 */       if (!this.currentSection.remove(this.entity)) {
/*  60 */         TransientEntitySectionManager.LOGGER.warn("Entity {} wasn't found in section {} (destroying due to {})", new Object[] { this.entity, SectionPos.of(this.currentSectionKey), $$0 });
/*     */       }
/*  62 */       Visibility $$1 = this.currentSection.getStatus();
/*  63 */       if ($$1.isTicking() || this.entity.isAlwaysTicking()) {
/*  64 */         TransientEntitySectionManager.this.callbacks.onTickingEnd(this.entity);
/*     */       }
/*  66 */       TransientEntitySectionManager.this.callbacks.onTrackingEnd(this.entity);
/*  67 */       TransientEntitySectionManager.this.callbacks.onDestroyed(this.entity);
/*  68 */       TransientEntitySectionManager.this.entityStorage.remove(this.entity);
/*  69 */       this.entity.setLevelCallback(NULL);
/*     */       
/*  71 */       TransientEntitySectionManager.this.removeSectionIfEmpty(this.currentSectionKey, this.currentSection);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private final LongSet tickingChunks = (LongSet)new LongOpenHashSet();
/*     */   private final LevelEntityGetter<T> entityGetter;
/*     */   
/*     */   public TransientEntitySectionManager(Class<T> $$0, LevelCallback<T> $$1) {
/*  82 */     this.entityStorage = new EntityLookup<>();
/*     */     
/*  84 */     this.sectionStorage = new EntitySectionStorage<>($$0, $$0 -> this.tickingChunks.contains($$0) ? Visibility.TICKING : Visibility.TRACKED);
/*  85 */     this.callbacks = $$1;
/*  86 */     this.entityGetter = new LevelEntityGetterAdapter<>(this.entityStorage, this.sectionStorage);
/*     */   }
/*     */   
/*     */   public void startTicking(ChunkPos $$0) {
/*  90 */     long $$1 = $$0.toLong();
/*  91 */     this.tickingChunks.add($$1);
/*  92 */     this.sectionStorage.getExistingSectionsInChunk($$1).forEach($$0 -> {
/*     */           Visibility $$1 = $$0.updateChunkStatus(Visibility.TICKING);
/*     */           if (!$$1.isTicking()) {
/*     */             Objects.requireNonNull(this.callbacks);
/*     */             $$0.getEntities().filter(()).forEach(this.callbacks::onTickingStart);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public void stopTicking(ChunkPos $$0) {
/* 102 */     long $$1 = $$0.toLong();
/* 103 */     this.tickingChunks.remove($$1);
/* 104 */     this.sectionStorage.getExistingSectionsInChunk($$1).forEach($$0 -> {
/*     */           Visibility $$1 = $$0.updateChunkStatus(Visibility.TRACKED);
/*     */           if ($$1.isTicking()) {
/*     */             Objects.requireNonNull(this.callbacks);
/*     */             $$0.getEntities().filter(()).forEach(this.callbacks::onTickingEnd);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public LevelEntityGetter<T> getEntityGetter() {
/* 114 */     return this.entityGetter;
/*     */   }
/*     */   
/*     */   public void addEntity(T $$0) {
/* 118 */     this.entityStorage.add($$0);
/*     */     
/* 120 */     long $$1 = SectionPos.asLong($$0.blockPosition());
/* 121 */     EntitySection<T> $$2 = this.sectionStorage.getOrCreateSection($$1);
/* 122 */     $$2.add($$0);
/*     */     
/* 124 */     $$0.setLevelCallback(new Callback($$0, $$1, $$2));
/* 125 */     this.callbacks.onCreated($$0);
/* 126 */     this.callbacks.onTrackingStart($$0);
/* 127 */     if ($$0.isAlwaysTicking() || $$2.getStatus().isTicking()) {
/* 128 */       this.callbacks.onTickingStart($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public int count() {
/* 134 */     return this.entityStorage.count();
/*     */   }
/*     */   
/*     */   void removeSectionIfEmpty(long $$0, EntitySection<T> $$1) {
/* 138 */     if ($$1.isEmpty()) {
/* 139 */       this.sectionStorage.remove($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public String gatherStats() {
/* 145 */     return "" + this.entityStorage.count() + "," + this.entityStorage.count() + "," + this.sectionStorage
/* 146 */       .count();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\TransientEntitySectionManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */