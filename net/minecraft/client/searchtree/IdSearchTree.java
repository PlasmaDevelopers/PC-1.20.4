/*    */ package net.minecraft.client.searchtree;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.Comparator;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.ToIntFunction;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class IdSearchTree<T> implements RefreshableSearchTree<T> {
/*    */   protected final Comparator<T> additionOrder;
/*    */   protected final ResourceLocationSearchTree<T> resourceLocationSearchTree;
/*    */   
/*    */   public IdSearchTree(Function<T, Stream<ResourceLocation>> $$0, List<T> $$1) {
/* 18 */     ToIntFunction<T> $$2 = Util.createIndexLookup($$1);
/* 19 */     this.additionOrder = Comparator.comparingInt($$2);
/* 20 */     this.resourceLocationSearchTree = ResourceLocationSearchTree.create($$1, $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<T> search(String $$0) {
/* 27 */     int $$1 = $$0.indexOf(':');
/* 28 */     if ($$1 == -1) {
/* 29 */       return searchPlainText($$0);
/*    */     }
/* 31 */     return searchResourceLocation($$0.substring(0, $$1).trim(), $$0.substring($$1 + 1).trim());
/*    */   }
/*    */   
/*    */   protected List<T> searchPlainText(String $$0) {
/* 35 */     return this.resourceLocationSearchTree.searchPath($$0);
/*    */   }
/*    */   
/*    */   protected List<T> searchResourceLocation(String $$0, String $$1) {
/* 39 */     List<T> $$2 = this.resourceLocationSearchTree.searchNamespace($$0);
/* 40 */     List<T> $$3 = this.resourceLocationSearchTree.searchPath($$1);
/* 41 */     return (List<T>)ImmutableList.copyOf((Iterator)new IntersectionIterator<>($$2.iterator(), $$3.iterator(), this.additionOrder));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\searchtree\IdSearchTree.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */