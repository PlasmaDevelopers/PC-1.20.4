/*    */ package net.minecraft.world.level.entity;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class EntityTickList
/*    */ {
/* 13 */   private Int2ObjectMap<Entity> active = (Int2ObjectMap<Entity>)new Int2ObjectLinkedOpenHashMap();
/* 14 */   private Int2ObjectMap<Entity> passive = (Int2ObjectMap<Entity>)new Int2ObjectLinkedOpenHashMap();
/*    */   @Nullable
/*    */   private Int2ObjectMap<Entity> iterated;
/*    */   
/*    */   private void ensureActiveIsNotIterated() {
/* 19 */     if (this.iterated == this.active) {
/* 20 */       this.passive.clear();
/* 21 */       for (ObjectIterator<Int2ObjectMap.Entry<Entity>> objectIterator = Int2ObjectMaps.fastIterable(this.active).iterator(); objectIterator.hasNext(); ) { Int2ObjectMap.Entry<Entity> $$0 = objectIterator.next();
/* 22 */         this.passive.put($$0.getIntKey(), $$0.getValue()); }
/*    */       
/* 24 */       Int2ObjectMap<Entity> $$1 = this.active;
/* 25 */       this.active = this.passive;
/* 26 */       this.passive = $$1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void add(Entity $$0) {
/* 31 */     ensureActiveIsNotIterated();
/* 32 */     this.active.put($$0.getId(), $$0);
/*    */   }
/*    */   
/*    */   public void remove(Entity $$0) {
/* 36 */     ensureActiveIsNotIterated();
/* 37 */     this.active.remove($$0.getId());
/*    */   }
/*    */   
/*    */   public boolean contains(Entity $$0) {
/* 41 */     return this.active.containsKey($$0.getId());
/*    */   }
/*    */   
/*    */   public void forEach(Consumer<Entity> $$0) {
/* 45 */     if (this.iterated != null)
/*    */     {
/* 47 */       throw new UnsupportedOperationException("Only one concurrent iteration supported");
/*    */     }
/*    */     
/* 50 */     this.iterated = this.active;
/*    */     
/*    */     try {
/* 53 */       for (ObjectIterator<Entity> objectIterator = this.active.values().iterator(); objectIterator.hasNext(); ) { Entity $$1 = objectIterator.next();
/* 54 */         $$0.accept($$1); }
/*    */     
/*    */     } finally {
/* 57 */       this.iterated = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\EntityTickList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */