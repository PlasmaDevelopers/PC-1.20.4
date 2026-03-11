/*    */ package net.minecraft.world.level.entity;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.AbortableIterationConsumer;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ public class LevelEntityGetterAdapter<T extends EntityAccess>
/*    */   implements LevelEntityGetter<T> {
/*    */   private final EntityLookup<T> visibleEntities;
/*    */   private final EntitySectionStorage<T> sectionStorage;
/*    */   
/*    */   public LevelEntityGetterAdapter(EntityLookup<T> $$0, EntitySectionStorage<T> $$1) {
/* 15 */     this.visibleEntities = $$0;
/* 16 */     this.sectionStorage = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public T get(int $$0) {
/* 22 */     return this.visibleEntities.getEntity($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public T get(UUID $$0) {
/* 28 */     return this.visibleEntities.getEntity($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<T> getAll() {
/* 33 */     return this.visibleEntities.getAllEntities();
/*    */   }
/*    */ 
/*    */   
/*    */   public <U extends T> void get(EntityTypeTest<T, U> $$0, AbortableIterationConsumer<U> $$1) {
/* 38 */     this.visibleEntities.getEntities($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void get(AABB $$0, Consumer<T> $$1) {
/* 43 */     this.sectionStorage.getEntities($$0, AbortableIterationConsumer.forConsumer($$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public <U extends T> void get(EntityTypeTest<T, U> $$0, AABB $$1, AbortableIterationConsumer<U> $$2) {
/* 48 */     this.sectionStorage.getEntities($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\LevelEntityGetterAdapter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */