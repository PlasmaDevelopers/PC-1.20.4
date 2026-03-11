/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.HashMultimap;
/*    */ import com.google.common.collect.Multimap;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.Consumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DependencySorter<K, V extends DependencySorter.Entry<K>>
/*    */ {
/* 19 */   private final Map<K, V> contents = new HashMap<>();
/*    */   
/*    */   public DependencySorter<K, V> addEntry(K $$0, V $$1) {
/* 22 */     this.contents.put($$0, $$1);
/* 23 */     return this;
/*    */   }
/*    */   
/*    */   private void visitDependenciesAndElement(Multimap<K, K> $$0, Set<K> $$1, K $$2, BiConsumer<K, V> $$3) {
/* 27 */     if (!$$1.add($$2)) {
/*    */       return;
/*    */     }
/*    */     
/* 31 */     $$0.get($$2).forEach($$3 -> visitDependenciesAndElement($$0, $$1, (K)$$3, $$2));
/*    */     
/* 33 */     Entry entry = (Entry)this.contents.get($$2);
/* 34 */     if (entry != null) {
/* 35 */       $$3.accept($$2, (V)entry);
/*    */     }
/*    */   }
/*    */   
/*    */   private static <K> boolean isCyclic(Multimap<K, K> $$0, K $$1, K $$2) {
/* 40 */     Collection<K> $$3 = $$0.get($$2);
/* 41 */     if ($$3.contains($$1)) {
/* 42 */       return true;
/*    */     }
/* 44 */     return $$3.stream().anyMatch($$2 -> isCyclic($$0, $$1, $$2));
/*    */   }
/*    */   
/*    */   private static <K> void addDependencyIfNotCyclic(Multimap<K, K> $$0, K $$1, K $$2) {
/* 48 */     if (!isCyclic($$0, $$1, $$2)) {
/* 49 */       $$0.put($$1, $$2);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void orderByDependencies(BiConsumer<K, V> $$0) {
/* 59 */     HashMultimap hashMultimap = HashMultimap.create();
/*    */ 
/*    */ 
/*    */     
/* 63 */     this.contents.forEach(($$1, $$2) -> $$2.visitRequiredDependencies(()));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 70 */     this.contents.forEach(($$1, $$2) -> $$2.visitOptionalDependencies(()));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 77 */     Set<K> $$2 = new HashSet<>();
/* 78 */     this.contents.keySet().forEach($$3 -> visitDependenciesAndElement($$0, $$1, (K)$$3, $$2));
/*    */   }
/*    */   
/*    */   public static interface Entry<K> {
/*    */     void visitRequiredDependencies(Consumer<K> param1Consumer);
/*    */     
/*    */     void visitOptionalDependencies(Consumer<K> param1Consumer);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\DependencySorter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */