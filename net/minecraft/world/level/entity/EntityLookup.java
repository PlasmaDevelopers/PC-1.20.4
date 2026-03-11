/*    */ package net.minecraft.world.level.entity;
/*    */ 
/*    */ import com.google.common.collect.Iterables;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.AbortableIterationConsumer;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class EntityLookup<T extends EntityAccess> {
/* 16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 18 */   private final Int2ObjectMap<T> byId = (Int2ObjectMap<T>)new Int2ObjectLinkedOpenHashMap();
/* 19 */   private final Map<UUID, T> byUuid = Maps.newHashMap();
/*    */   
/*    */   public <U extends T> void getEntities(EntityTypeTest<T, U> $$0, AbortableIterationConsumer<U> $$1) {
/* 22 */     for (ObjectIterator<EntityAccess> objectIterator = this.byId.values().iterator(); objectIterator.hasNext(); ) { EntityAccess entityAccess1 = objectIterator.next();
/* 23 */       EntityAccess entityAccess2 = (EntityAccess)$$0.tryCast((T)entityAccess1);
/* 24 */       if (entityAccess2 != null && 
/* 25 */         $$1.accept(entityAccess2).shouldAbort()) {
/*    */         return;
/*    */       } }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<T> getAllEntities() {
/* 33 */     return Iterables.unmodifiableIterable((Iterable)this.byId.values());
/*    */   }
/*    */   
/*    */   public void add(T $$0) {
/* 37 */     UUID $$1 = $$0.getUUID();
/* 38 */     if (this.byUuid.containsKey($$1)) {
/* 39 */       LOGGER.warn("Duplicate entity UUID {}: {}", $$1, $$0);
/*    */       return;
/*    */     } 
/* 42 */     this.byUuid.put($$1, $$0);
/* 43 */     this.byId.put($$0.getId(), $$0);
/*    */   }
/*    */   
/*    */   public void remove(T $$0) {
/* 47 */     this.byUuid.remove($$0.getUUID());
/* 48 */     this.byId.remove($$0.getId());
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public T getEntity(int $$0) {
/* 53 */     return (T)this.byId.get($$0);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public T getEntity(UUID $$0) {
/* 58 */     return this.byUuid.get($$0);
/*    */   }
/*    */   
/*    */   public int count() {
/* 62 */     return this.byUuid.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\EntityLookup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */