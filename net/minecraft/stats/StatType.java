/*    */ package net.minecraft.stats;
/*    */ 
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class StatType<T>
/*    */   implements Iterable<Stat<T>> {
/*    */   private final Registry<T> registry;
/* 12 */   private final Map<T, Stat<T>> map = new IdentityHashMap<>();
/*    */   private final Component displayName;
/*    */   
/*    */   public StatType(Registry<T> $$0, Component $$1) {
/* 16 */     this.registry = $$0;
/* 17 */     this.displayName = $$1;
/*    */   }
/*    */   
/*    */   public boolean contains(T $$0) {
/* 21 */     return this.map.containsKey($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Stat<T> get(T $$0, StatFormatter $$1) {
/* 26 */     return this.map.computeIfAbsent($$0, $$1 -> new Stat<>(this, (T)$$1, $$0));
/*    */   }
/*    */   
/*    */   public Registry<T> getRegistry() {
/* 30 */     return this.registry;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<Stat<T>> iterator() {
/* 35 */     return this.map.values().iterator();
/*    */   }
/*    */   
/*    */   public Stat<T> get(T $$0) {
/* 39 */     return get($$0, StatFormatter.DEFAULT);
/*    */   }
/*    */   
/*    */   public Component getDisplayName() {
/* 43 */     return this.displayName;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\stats\StatType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */