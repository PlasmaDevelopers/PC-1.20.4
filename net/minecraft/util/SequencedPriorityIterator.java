/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.AbstractIterator;
/*    */ import com.google.common.collect.Queues;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import java.util.Comparator;
/*    */ import java.util.Deque;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SequencedPriorityIterator<T>
/*    */   extends AbstractIterator<T>
/*    */ {
/* 19 */   private final Int2ObjectMap<Deque<T>> valuesByPriority = (Int2ObjectMap<Deque<T>>)new Int2ObjectOpenHashMap();
/*    */   
/*    */   public void add(T $$0, int $$1) {
/* 22 */     ((Deque<T>)this.valuesByPriority.computeIfAbsent($$1, $$0 -> Queues.newArrayDeque())).addLast($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected T computeNext() {
/* 31 */     Optional<Deque<T>> $$0 = this.valuesByPriority.int2ObjectEntrySet().stream().filter($$0 -> !((Deque)$$0.getValue()).isEmpty()).max(Comparator.comparingInt(Map.Entry::getKey)).map(Map.Entry::getValue);
/*    */     
/* 33 */     return $$0.<T>map(Deque::removeFirst).orElseGet(() -> $$0.endOfData());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\SequencedPriorityIterator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */