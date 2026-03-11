/*    */ package net.minecraft.client.searchtree;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public interface ResourceLocationSearchTree<T>
/*    */ {
/*    */   static <T> ResourceLocationSearchTree<T> empty() {
/* 12 */     return new ResourceLocationSearchTree<T>()
/*    */       {
/*    */         public List<T> searchNamespace(String $$0) {
/* 15 */           return List.of();
/*    */         }
/*    */ 
/*    */         
/*    */         public List<T> searchPath(String $$0) {
/* 20 */           return List.of();
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   static <T> ResourceLocationSearchTree<T> create(List<T> $$0, Function<T, Stream<ResourceLocation>> $$1) {
/* 26 */     if ($$0.isEmpty()) {
/* 27 */       return empty();
/*    */     }
/*    */     
/* 30 */     final SuffixArray<T> namespaceTree = new SuffixArray<>();
/* 31 */     final SuffixArray<T> pathTree = new SuffixArray<>();
/* 32 */     for (T $$4 : $$0) {
/* 33 */       ((Stream)$$1.apply($$4)).forEach($$3 -> {
/*    */             $$0.add($$1, $$3.getNamespace().toLowerCase(Locale.ROOT));
/*    */             $$2.add($$1, $$3.getPath().toLowerCase(Locale.ROOT));
/*    */           });
/*    */     } 
/* 38 */     $$2.generate();
/* 39 */     $$3.generate();
/*    */     
/* 41 */     return new ResourceLocationSearchTree<T>()
/*    */       {
/*    */         public List<T> searchNamespace(String $$0) {
/* 44 */           return namespaceTree.search($$0);
/*    */         }
/*    */ 
/*    */         
/*    */         public List<T> searchPath(String $$0) {
/* 49 */           return pathTree.search($$0);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   List<T> searchNamespace(String paramString);
/*    */   
/*    */   List<T> searchPath(String paramString);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\searchtree\ResourceLocationSearchTree.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */