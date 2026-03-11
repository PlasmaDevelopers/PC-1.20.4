/*     */ package net.minecraft.util.thread;
/*     */ 
/*     */ import com.google.common.collect.Queues;
/*     */ import java.util.Locale;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public interface StrictQueue<T, F> {
/*     */   @Nullable
/*     */   F pop();
/*     */   
/*     */   boolean push(T paramT);
/*     */   
/*     */   boolean isEmpty();
/*     */   
/*     */   int size();
/*     */   
/*     */   public static final class QueueStrictQueue<T>
/*     */     implements StrictQueue<T, T> {
/*     */     private final Queue<T> queue;
/*     */     
/*     */     public QueueStrictQueue(Queue<T> $$0) {
/*  24 */       this.queue = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public T pop() {
/*  30 */       return this.queue.poll();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean push(T $$0) {
/*  35 */       return this.queue.add($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/*  40 */       return this.queue.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/*  45 */       return this.queue.size();
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class IntRunnable implements Runnable {
/*     */     final int priority;
/*     */     private final Runnable task;
/*     */     
/*     */     public IntRunnable(int $$0, Runnable $$1) {
/*  54 */       this.priority = $$0;
/*  55 */       this.task = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*  60 */       this.task.run();
/*     */     }
/*     */     
/*     */     public int getPriority() {
/*  64 */       return this.priority;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class FixedPriorityQueue implements StrictQueue<IntRunnable, Runnable> {
/*     */     private final Queue<Runnable>[] queues;
/*  70 */     private final AtomicInteger size = new AtomicInteger();
/*     */ 
/*     */     
/*     */     public FixedPriorityQueue(int $$0) {
/*  74 */       this.queues = (Queue<Runnable>[])new Queue[$$0];
/*  75 */       for (int $$1 = 0; $$1 < $$0; $$1++) {
/*  76 */         this.queues[$$1] = Queues.newConcurrentLinkedQueue();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Runnable pop() {
/*  84 */       for (Queue<Runnable> $$0 : this.queues) {
/*  85 */         Runnable $$1 = $$0.poll();
/*  86 */         if ($$1 != null) {
/*  87 */           this.size.decrementAndGet();
/*  88 */           return $$1;
/*     */         } 
/*     */       } 
/*  91 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean push(StrictQueue.IntRunnable $$0) {
/*  96 */       int $$1 = $$0.priority;
/*     */       
/*  98 */       if ($$1 >= this.queues.length || $$1 < 0) {
/*  99 */         throw new IndexOutOfBoundsException(String.format(Locale.ROOT, "Priority %d not supported. Expected range [0-%d]", new Object[] { Integer.valueOf($$1), Integer.valueOf(this.queues.length - 1) }));
/*     */       }
/*     */       
/* 102 */       this.queues[$$1].add($$0);
/* 103 */       this.size.incrementAndGet();
/* 104 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 109 */       return (this.size.get() == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 114 */       return this.size.get();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\thread\StrictQueue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */