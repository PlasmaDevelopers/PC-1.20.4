/*    */ package net.minecraft.util.thread;
/*    */ 
/*    */ import java.util.Queue;
/*    */ import javax.annotation.Nullable;
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
/*    */ public final class QueueStrictQueue<T>
/*    */   implements StrictQueue<T, T>
/*    */ {
/*    */   private final Queue<T> queue;
/*    */   
/*    */   public QueueStrictQueue(Queue<T> $$0) {
/* 24 */     this.queue = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public T pop() {
/* 30 */     return this.queue.poll();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean push(T $$0) {
/* 35 */     return this.queue.add($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 40 */     return this.queue.isEmpty();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 45 */     return this.queue.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\thread\StrictQueue$QueueStrictQueue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */