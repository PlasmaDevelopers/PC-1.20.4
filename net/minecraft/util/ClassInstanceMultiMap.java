/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.Iterators;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.AbstractCollection;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.stream.Collectors;
/*    */ 
/*    */ public class ClassInstanceMultiMap<T> extends AbstractCollection<T> {
/* 17 */   private final Map<Class<?>, List<T>> byClass = Maps.newHashMap();
/*    */   
/*    */   private final Class<T> baseClass;
/* 20 */   private final List<T> allInstances = Lists.newArrayList();
/*    */   
/*    */   public ClassInstanceMultiMap(Class<T> $$0) {
/* 23 */     this.baseClass = $$0;
/* 24 */     this.byClass.put($$0, this.allInstances);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean add(T $$0) {
/* 29 */     boolean $$1 = false;
/* 30 */     for (Map.Entry<Class<?>, List<T>> $$2 : this.byClass.entrySet()) {
/* 31 */       if (((Class)$$2.getKey()).isInstance($$0)) {
/* 32 */         $$1 |= ((List<T>)$$2.getValue()).add($$0);
/*    */       }
/*    */     } 
/* 35 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean remove(Object $$0) {
/* 40 */     boolean $$1 = false;
/* 41 */     for (Map.Entry<Class<?>, List<T>> $$2 : this.byClass.entrySet()) {
/* 42 */       if (((Class)$$2.getKey()).isInstance($$0)) {
/* 43 */         List<T> $$3 = $$2.getValue();
/* 44 */         $$1 |= $$3.remove($$0);
/*    */       } 
/*    */     } 
/* 47 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean contains(Object $$0) {
/* 52 */     return find($$0.getClass()).contains($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> Collection<S> find(Class<S> $$0) {
/* 57 */     if (!this.baseClass.isAssignableFrom($$0)) {
/* 58 */       throw new IllegalArgumentException("Don't know how to search for " + $$0);
/*    */     }
/* 60 */     List<? extends T> $$1 = this.byClass.computeIfAbsent($$0, $$0 -> { Objects.requireNonNull($$0); return (List)this.allInstances.stream().filter($$0::isInstance).collect(Collectors.toList());
/* 61 */         }); return Collections.unmodifiableCollection($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<T> iterator() {
/* 66 */     if (this.allInstances.isEmpty()) {
/* 67 */       return Collections.emptyIterator();
/*    */     }
/* 69 */     return (Iterator<T>)Iterators.unmodifiableIterator(this.allInstances.iterator());
/*    */   }
/*    */   
/*    */   public List<T> getAllInstances() {
/* 73 */     return (List<T>)ImmutableList.copyOf(this.allInstances);
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 78 */     return this.allInstances.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ClassInstanceMultiMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */