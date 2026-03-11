/*    */ package net.minecraft.world.level.entity;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.Collection;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.util.AbortableIterationConsumer;
/*    */ import net.minecraft.util.ClassInstanceMultiMap;
/*    */ import net.minecraft.util.VisibleForDebug;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class EntitySection<T extends EntityAccess>
/*    */ {
/* 14 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final ClassInstanceMultiMap<T> storage;
/*    */   private Visibility chunkStatus;
/*    */   
/*    */   public EntitySection(Class<T> $$0, Visibility $$1) {
/* 20 */     this.chunkStatus = $$1;
/* 21 */     this.storage = new ClassInstanceMultiMap($$0);
/*    */   }
/*    */   
/*    */   public void add(T $$0) {
/* 25 */     this.storage.add($$0);
/*    */   }
/*    */   
/*    */   public boolean remove(T $$0) {
/* 29 */     return this.storage.remove($$0);
/*    */   }
/*    */   
/*    */   public AbortableIterationConsumer.Continuation getEntities(AABB $$0, AbortableIterationConsumer<T> $$1) {
/* 33 */     for (EntityAccess entityAccess : this.storage) {
/* 34 */       if (entityAccess.getBoundingBox().intersects($$0) && 
/* 35 */         $$1.accept(entityAccess).shouldAbort()) {
/* 36 */         return AbortableIterationConsumer.Continuation.ABORT;
/*    */       }
/*    */     } 
/*    */     
/* 40 */     return AbortableIterationConsumer.Continuation.CONTINUE;
/*    */   }
/*    */   
/*    */   public <U extends T> AbortableIterationConsumer.Continuation getEntities(EntityTypeTest<T, U> $$0, AABB $$1, AbortableIterationConsumer<? super U> $$2) {
/* 44 */     Collection<? extends T> $$3 = this.storage.find($$0.getBaseClass());
/* 45 */     if ($$3.isEmpty()) {
/* 46 */       return AbortableIterationConsumer.Continuation.CONTINUE;
/*    */     }
/* 48 */     for (EntityAccess entityAccess1 : $$3) {
/* 49 */       EntityAccess entityAccess2 = (EntityAccess)$$0.tryCast((T)entityAccess1);
/* 50 */       if (entityAccess2 != null && entityAccess1.getBoundingBox().intersects($$1) && 
/* 51 */         $$2.accept(entityAccess2).shouldAbort()) {
/* 52 */         return AbortableIterationConsumer.Continuation.ABORT;
/*    */       }
/*    */     } 
/*    */     
/* 56 */     return AbortableIterationConsumer.Continuation.CONTINUE;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 60 */     return this.storage.isEmpty();
/*    */   }
/*    */   
/*    */   public Stream<T> getEntities() {
/* 64 */     return this.storage.stream();
/*    */   }
/*    */   
/*    */   public Visibility getStatus() {
/* 68 */     return this.chunkStatus;
/*    */   }
/*    */   
/*    */   public Visibility updateChunkStatus(Visibility $$0) {
/* 72 */     Visibility $$1 = this.chunkStatus;
/* 73 */     this.chunkStatus = $$0;
/* 74 */     return $$1;
/*    */   }
/*    */   
/*    */   @VisibleForDebug
/*    */   public int size() {
/* 79 */     return this.storage.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\EntitySection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */