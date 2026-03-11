/*    */ package net.minecraft.client.searchtree;
/*    */ 
/*    */ import java.util.List;
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
/*    */ 
/*    */ class TreeEntry<T>
/*    */ {
/*    */   private final SearchRegistry.TreeBuilderSupplier<T> factory;
/* 53 */   RefreshableSearchTree<T> tree = RefreshableSearchTree.empty();
/*    */   
/*    */   TreeEntry(SearchRegistry.TreeBuilderSupplier<T> $$0) {
/* 56 */     this.factory = $$0;
/*    */   }
/*    */   
/*    */   void populate(List<T> $$0) {
/* 60 */     this.tree = this.factory.apply($$0);
/* 61 */     this.tree.refresh();
/*    */   }
/*    */   
/*    */   void refresh() {
/* 65 */     this.tree.refresh();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\searchtree\SearchRegistry$TreeEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */