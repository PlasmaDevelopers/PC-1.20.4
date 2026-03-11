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
/*    */ class null
/*    */   implements ResourceLocationSearchTree<T>
/*    */ {
/*    */   public List<T> searchNamespace(String $$0) {
/* 44 */     return namespaceTree.search($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<T> searchPath(String $$0) {
/* 49 */     return pathTree.search($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\searchtree\ResourceLocationSearchTree$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */