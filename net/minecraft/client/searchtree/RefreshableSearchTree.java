/*   */ package net.minecraft.client.searchtree;
/*   */ 
/*   */ import java.util.List;
/*   */ 
/*   */ public interface RefreshableSearchTree<T> extends SearchTree<T> {
/*   */   static <T> RefreshableSearchTree<T> empty() {
/* 7 */     return $$0 -> List.of();
/*   */   }
/*   */   
/*   */   default void refresh() {}
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\searchtree\RefreshableSearchTree.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */