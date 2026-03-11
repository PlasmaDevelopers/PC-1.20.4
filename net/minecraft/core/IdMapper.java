/*    */ package net.minecraft.core;
/*    */ 
/*    */ import com.google.common.collect.Iterators;
/*    */ import com.google.common.collect.Lists;
/*    */ import it.unimi.dsi.fastutil.objects.Reference2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class IdMapper<T>
/*    */   implements IdMap<T>
/*    */ {
/*    */   private int nextId;
/*    */   private final Reference2IntMap<T> tToId;
/*    */   private final List<T> idToT;
/*    */   
/*    */   public IdMapper() {
/* 20 */     this(512);
/*    */   }
/*    */   
/*    */   public IdMapper(int $$0) {
/* 24 */     this.idToT = Lists.newArrayListWithExpectedSize($$0);
/* 25 */     this.tToId = (Reference2IntMap<T>)new Reference2IntOpenHashMap($$0);
/* 26 */     this.tToId.defaultReturnValue(-1);
/*    */   }
/*    */   
/*    */   public void addMapping(T $$0, int $$1) {
/* 30 */     this.tToId.put($$0, $$1);
/*    */ 
/*    */     
/* 33 */     while (this.idToT.size() <= $$1) {
/* 34 */       this.idToT.add(null);
/*    */     }
/*    */     
/* 37 */     this.idToT.set($$1, $$0);
/*    */     
/* 39 */     if (this.nextId <= $$1) {
/* 40 */       this.nextId = $$1 + 1;
/*    */     }
/*    */   }
/*    */   
/*    */   public void add(T $$0) {
/* 45 */     addMapping($$0, this.nextId);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId(T $$0) {
/* 50 */     return this.tToId.getInt($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public final T byId(int $$0) {
/* 56 */     if ($$0 >= 0 && $$0 < this.idToT.size()) {
/* 57 */       return this.idToT.get($$0);
/*    */     }
/*    */     
/* 60 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<T> iterator() {
/* 65 */     return (Iterator<T>)Iterators.filter(this.idToT.iterator(), Objects::nonNull);
/*    */   }
/*    */   
/*    */   public boolean contains(int $$0) {
/* 69 */     return (byId($$0) != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 74 */     return this.tToId.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\IdMapper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */