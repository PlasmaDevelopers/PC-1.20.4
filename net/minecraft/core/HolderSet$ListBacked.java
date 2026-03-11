/*    */ package net.minecraft.core;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.Spliterator;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.util.RandomSource;
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
/*    */ public abstract class ListBacked<T>
/*    */   implements HolderSet<T>
/*    */ {
/*    */   protected abstract List<Holder<T>> contents();
/*    */   
/*    */   public int size() {
/* 41 */     return contents().size();
/*    */   }
/*    */ 
/*    */   
/*    */   public Spliterator<Holder<T>> spliterator() {
/* 46 */     return contents().spliterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<Holder<T>> iterator() {
/* 51 */     return contents().iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<Holder<T>> stream() {
/* 56 */     return contents().stream();
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Holder<T>> getRandomElement(RandomSource $$0) {
/* 61 */     return Util.getRandomSafe(contents(), $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Holder<T> get(int $$0) {
/* 66 */     return contents().get($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSerializeIn(HolderOwner<T> $$0) {
/* 71 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\HolderSet$ListBacked.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */