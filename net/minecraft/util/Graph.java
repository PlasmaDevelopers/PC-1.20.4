/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.function.Consumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Graph
/*    */ {
/*    */   public static <T> boolean depthFirstSearch(Map<T, Set<T>> $$0, Set<T> $$1, Set<T> $$2, Consumer<T> $$3, T $$4) {
/* 25 */     if ($$1.contains($$4)) {
/* 26 */       return false;
/*    */     }
/* 28 */     if ($$2.contains($$4)) {
/* 29 */       return true;
/*    */     }
/* 31 */     $$2.add($$4);
/* 32 */     for (T $$5 : $$0.getOrDefault($$4, ImmutableSet.of())) {
/* 33 */       if (depthFirstSearch($$0, $$1, $$2, $$3, $$5)) {
/* 34 */         return true;
/*    */       }
/*    */     } 
/* 37 */     $$2.remove($$4);
/* 38 */     $$1.add($$4);
/* 39 */     $$3.accept($$4);
/* 40 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\Graph.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */