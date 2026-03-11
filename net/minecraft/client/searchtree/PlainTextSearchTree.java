/*    */ package net.minecraft.client.searchtree;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ 
/*    */ public interface PlainTextSearchTree<T> {
/*    */   static <T> PlainTextSearchTree<T> empty() {
/* 10 */     return $$0 -> List.of();
/*    */   }
/*    */   
/*    */   static <T> PlainTextSearchTree<T> create(List<T> $$0, Function<T, Stream<String>> $$1) {
/* 14 */     if ($$0.isEmpty()) {
/* 15 */       return empty();
/*    */     }
/*    */     
/* 18 */     SuffixArray<T> $$2 = new SuffixArray<>();
/* 19 */     for (T $$3 : $$0) {
/* 20 */       ((Stream)$$1.apply($$3)).forEach($$2 -> $$0.add($$1, $$2.toLowerCase(Locale.ROOT)));
/*    */     }
/* 22 */     $$2.generate();
/*    */     
/* 24 */     Objects.requireNonNull($$2); return $$2::search;
/*    */   }
/*    */   
/*    */   List<T> search(String paramString);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\searchtree\PlainTextSearchTree.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */