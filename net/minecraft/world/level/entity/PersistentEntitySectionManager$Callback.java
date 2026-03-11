/*     */ package net.minecraft.world.level.entity;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.world.entity.Entity;
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
/*     */ class Callback
/*     */   implements EntityInLevelCallback
/*     */ {
/*     */   private final T entity;
/*     */   private long currentSectionKey;
/*     */   private EntitySection<T> currentSection;
/*     */   
/*     */   Callback(T $$0, long $$1, EntitySection<T> $$2) {
/*  47 */     this.entity = $$0;
/*  48 */     this.currentSectionKey = $$1;
/*  49 */     this.currentSection = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMove() {
/*  54 */     BlockPos $$0 = this.entity.blockPosition();
/*  55 */     long $$1 = SectionPos.asLong($$0);
/*  56 */     if ($$1 != this.currentSectionKey) {
/*  57 */       Visibility $$2 = this.currentSection.getStatus();
/*  58 */       if (!this.currentSection.remove(this.entity)) {
/*  59 */         PersistentEntitySectionManager.LOGGER.warn("Entity {} wasn't found in section {} (moving to {})", new Object[] { this.entity, SectionPos.of(this.currentSectionKey), Long.valueOf($$1) });
/*     */       }
/*  61 */       PersistentEntitySectionManager.this.removeSectionIfEmpty(this.currentSectionKey, this.currentSection);
/*     */       
/*  63 */       EntitySection<T> $$3 = PersistentEntitySectionManager.this.sectionStorage.getOrCreateSection($$1);
/*  64 */       $$3.add(this.entity);
/*  65 */       this.currentSection = $$3;
/*  66 */       this.currentSectionKey = $$1;
/*     */       
/*  68 */       updateStatus($$2, $$3.getStatus());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateStatus(Visibility $$0, Visibility $$1) {
/*  73 */     Visibility $$2 = PersistentEntitySectionManager.getEffectiveStatus((EntityAccess)this.entity, $$0);
/*  74 */     Visibility $$3 = PersistentEntitySectionManager.getEffectiveStatus((EntityAccess)this.entity, $$1);
/*     */     
/*  76 */     if ($$2 == $$3) {
/*  77 */       if ($$3.isAccessible()) {
/*  78 */         PersistentEntitySectionManager.this.callbacks.onSectionChange(this.entity);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*  83 */     boolean $$4 = $$2.isAccessible();
/*  84 */     boolean $$5 = $$3.isAccessible();
/*  85 */     if ($$4 && !$$5) {
/*  86 */       PersistentEntitySectionManager.this.stopTracking(this.entity);
/*  87 */     } else if (!$$4 && $$5) {
/*  88 */       PersistentEntitySectionManager.this.startTracking(this.entity);
/*     */     } 
/*     */     
/*  91 */     boolean $$6 = $$2.isTicking();
/*  92 */     boolean $$7 = $$3.isTicking();
/*  93 */     if ($$6 && !$$7) {
/*  94 */       PersistentEntitySectionManager.this.stopTicking(this.entity);
/*  95 */     } else if (!$$6 && $$7) {
/*  96 */       PersistentEntitySectionManager.this.startTicking(this.entity);
/*     */     } 
/*     */     
/*  99 */     if ($$5) {
/* 100 */       PersistentEntitySectionManager.this.callbacks.onSectionChange(this.entity);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(Entity.RemovalReason $$0) {
/* 106 */     if (!this.currentSection.remove(this.entity)) {
/* 107 */       PersistentEntitySectionManager.LOGGER.warn("Entity {} wasn't found in section {} (destroying due to {})", new Object[] { this.entity, SectionPos.of(this.currentSectionKey), $$0 });
/*     */     }
/*     */     
/* 110 */     Visibility $$1 = PersistentEntitySectionManager.getEffectiveStatus((EntityAccess)this.entity, this.currentSection.getStatus());
/* 111 */     if ($$1.isTicking()) {
/* 112 */       PersistentEntitySectionManager.this.stopTicking(this.entity);
/*     */     }
/* 114 */     if ($$1.isAccessible()) {
/* 115 */       PersistentEntitySectionManager.this.stopTracking(this.entity);
/*     */     }
/* 117 */     if ($$0.shouldDestroy()) {
/* 118 */       PersistentEntitySectionManager.this.callbacks.onDestroyed(this.entity);
/*     */     }
/* 120 */     PersistentEntitySectionManager.this.knownUuids.remove(this.entity.getUUID());
/* 121 */     this.entity.setLevelCallback(NULL);
/*     */     
/* 123 */     PersistentEntitySectionManager.this.removeSectionIfEmpty(this.currentSectionKey, this.currentSection);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\PersistentEntitySectionManager$Callback.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */