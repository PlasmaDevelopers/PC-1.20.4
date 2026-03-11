/*    */ package net.minecraft.client.searchtree;
/*    */ 
/*    */ import com.google.common.collect.AbstractIterator;
/*    */ import com.google.common.collect.Iterators;
/*    */ import com.google.common.collect.PeekingIterator;
/*    */ import java.util.Comparator;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MergingUniqueIterator<T>
/*    */   extends AbstractIterator<T>
/*    */ {
/*    */   private final PeekingIterator<T> firstIterator;
/*    */   private final PeekingIterator<T> secondIterator;
/*    */   private final Comparator<T> comparator;
/*    */   
/*    */   public MergingUniqueIterator(Iterator<T> $$0, Iterator<T> $$1, Comparator<T> $$2) {
/* 20 */     this.firstIterator = Iterators.peekingIterator($$0);
/* 21 */     this.secondIterator = Iterators.peekingIterator($$1);
/*    */     
/* 23 */     this.comparator = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected T computeNext() {
/* 28 */     boolean $$0 = !this.firstIterator.hasNext();
/* 29 */     boolean $$1 = !this.secondIterator.hasNext();
/* 30 */     if ($$0 && $$1) {
/* 31 */       return (T)endOfData();
/*    */     }
/*    */     
/* 34 */     if ($$0) {
/* 35 */       return (T)this.secondIterator.next();
/*    */     }
/* 37 */     if ($$1) {
/* 38 */       return (T)this.firstIterator.next();
/*    */     }
/*    */     
/* 41 */     int $$2 = this.comparator.compare((T)this.firstIterator.peek(), (T)this.secondIterator.peek());
/* 42 */     if ($$2 == 0) {
/* 43 */       this.secondIterator.next();
/*    */     }
/*    */     
/* 46 */     return ($$2 <= 0) ? (T)this.firstIterator.next() : (T)this.secondIterator.next();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\searchtree\MergingUniqueIterator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */