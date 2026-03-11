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
/*    */ public class IntersectionIterator<T>
/*    */   extends AbstractIterator<T>
/*    */ {
/*    */   private final PeekingIterator<T> firstIterator;
/*    */   private final PeekingIterator<T> secondIterator;
/*    */   private final Comparator<T> comparator;
/*    */   
/*    */   public IntersectionIterator(Iterator<T> $$0, Iterator<T> $$1, Comparator<T> $$2) {
/* 19 */     this.firstIterator = Iterators.peekingIterator($$0);
/* 20 */     this.secondIterator = Iterators.peekingIterator($$1);
/*    */     
/* 22 */     this.comparator = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected T computeNext() {
/*    */     while (true) {
/* 28 */       if (!this.firstIterator.hasNext() || !this.secondIterator.hasNext()) {
/* 29 */         return (T)endOfData();
/*    */       }
/*    */       
/* 32 */       int $$0 = this.comparator.compare((T)this.firstIterator.peek(), (T)this.secondIterator.peek());
/* 33 */       if ($$0 == 0) {
/* 34 */         this.secondIterator.next();
/* 35 */         return (T)this.firstIterator.next();
/*    */       } 
/*    */       
/* 38 */       if ($$0 < 0) {
/* 39 */         this.firstIterator.next(); continue;
/*    */       } 
/* 41 */       this.secondIterator.next();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\searchtree\IntersectionIterator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */