/*    */ package net.minecraft.world.level.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Callback
/*    */   implements EntityInLevelCallback
/*    */ {
/*    */   private final T entity;
/*    */   private long currentSectionKey;
/*    */   private EntitySection<T> currentSection;
/*    */   
/*    */   Callback(T $$0, long $$1, EntitySection<T> $$2) {
/* 22 */     this.entity = $$0;
/* 23 */     this.currentSectionKey = $$1;
/* 24 */     this.currentSection = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onMove() {
/* 29 */     BlockPos $$0 = this.entity.blockPosition();
/* 30 */     long $$1 = SectionPos.asLong($$0);
/* 31 */     if ($$1 != this.currentSectionKey) {
/* 32 */       Visibility $$2 = this.currentSection.getStatus();
/* 33 */       if (!this.currentSection.remove(this.entity)) {
/* 34 */         TransientEntitySectionManager.LOGGER.warn("Entity {} wasn't found in section {} (moving to {})", new Object[] { this.entity, SectionPos.of(this.currentSectionKey), Long.valueOf($$1) });
/*    */       }
/* 36 */       TransientEntitySectionManager.this.removeSectionIfEmpty(this.currentSectionKey, this.currentSection);
/*    */       
/* 38 */       EntitySection<T> $$3 = TransientEntitySectionManager.this.sectionStorage.getOrCreateSection($$1);
/* 39 */       $$3.add(this.entity);
/* 40 */       this.currentSection = $$3;
/* 41 */       this.currentSectionKey = $$1;
/*    */       
/* 43 */       TransientEntitySectionManager.this.callbacks.onSectionChange(this.entity);
/*    */       
/* 45 */       if (!this.entity.isAlwaysTicking()) {
/* 46 */         boolean $$4 = $$2.isTicking();
/* 47 */         boolean $$5 = $$3.getStatus().isTicking();
/* 48 */         if ($$4 && !$$5) {
/* 49 */           TransientEntitySectionManager.this.callbacks.onTickingEnd(this.entity);
/* 50 */         } else if (!$$4 && $$5) {
/* 51 */           TransientEntitySectionManager.this.callbacks.onTickingStart(this.entity);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRemove(Entity.RemovalReason $$0) {
/* 59 */     if (!this.currentSection.remove(this.entity)) {
/* 60 */       TransientEntitySectionManager.LOGGER.warn("Entity {} wasn't found in section {} (destroying due to {})", new Object[] { this.entity, SectionPos.of(this.currentSectionKey), $$0 });
/*    */     }
/* 62 */     Visibility $$1 = this.currentSection.getStatus();
/* 63 */     if ($$1.isTicking() || this.entity.isAlwaysTicking()) {
/* 64 */       TransientEntitySectionManager.this.callbacks.onTickingEnd(this.entity);
/*    */     }
/* 66 */     TransientEntitySectionManager.this.callbacks.onTrackingEnd(this.entity);
/* 67 */     TransientEntitySectionManager.this.callbacks.onDestroyed(this.entity);
/* 68 */     TransientEntitySectionManager.this.entityStorage.remove(this.entity);
/* 69 */     this.entity.setLevelCallback(NULL);
/*    */     
/* 71 */     TransientEntitySectionManager.this.removeSectionIfEmpty(this.currentSectionKey, this.currentSection);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\TransientEntitySectionManager$Callback.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */