/*    */ package net.minecraft.client.searchtree;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class FullTextSearchTree<T>
/*    */   extends IdSearchTree<T>
/*    */ {
/*    */   private final List<T> contents;
/*    */   private final Function<T, Stream<String>> filler;
/* 15 */   private PlainTextSearchTree<T> plainTextSearchTree = PlainTextSearchTree.empty();
/*    */   
/*    */   public FullTextSearchTree(Function<T, Stream<String>> $$0, Function<T, Stream<ResourceLocation>> $$1, List<T> $$2) {
/* 18 */     super($$1, $$2);
/* 19 */     this.contents = $$2;
/* 20 */     this.filler = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void refresh() {
/* 25 */     super.refresh();
/* 26 */     this.plainTextSearchTree = PlainTextSearchTree.create(this.contents, this.filler);
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<T> searchPlainText(String $$0) {
/* 31 */     return this.plainTextSearchTree.search($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<T> searchResourceLocation(String $$0, String $$1) {
/* 36 */     List<T> $$2 = this.resourceLocationSearchTree.searchNamespace($$0);
/* 37 */     List<T> $$3 = this.resourceLocationSearchTree.searchPath($$1);
/* 38 */     List<T> $$4 = this.plainTextSearchTree.search($$1);
/*    */     
/* 40 */     MergingUniqueIterator<T> mergingUniqueIterator = new MergingUniqueIterator<>($$3.iterator(), $$4.iterator(), this.additionOrder);
/* 41 */     return (List<T>)ImmutableList.copyOf((Iterator)new IntersectionIterator<>($$2.iterator(), (Iterator<T>)mergingUniqueIterator, this.additionOrder));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\searchtree\FullTextSearchTree.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */