/*     */ package net.minecraft.util.thread;
/*     */ 
/*     */ import com.google.common.collect.Queues;
/*     */ import java.util.Locale;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FixedPriorityQueue
/*     */   implements StrictQueue<StrictQueue.IntRunnable, Runnable>
/*     */ {
/*     */   private final Queue<Runnable>[] queues;
/*  70 */   private final AtomicInteger size = new AtomicInteger();
/*     */ 
/*     */   
/*     */   public FixedPriorityQueue(int $$0) {
/*  74 */     this.queues = (Queue<Runnable>[])new Queue[$$0];
/*  75 */     for (int $$1 = 0; $$1 < $$0; $$1++) {
/*  76 */       this.queues[$$1] = Queues.newConcurrentLinkedQueue();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Runnable pop() {
/*  84 */     for (Queue<Runnable> $$0 : this.queues) {
/*  85 */       Runnable $$1 = $$0.poll();
/*  86 */       if ($$1 != null) {
/*  87 */         this.size.decrementAndGet();
/*  88 */         return $$1;
/*     */       } 
/*     */     } 
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean push(StrictQueue.IntRunnable $$0) {
/*  96 */     int $$1 = $$0.priority;
/*     */     
/*  98 */     if ($$1 >= this.queues.length || $$1 < 0) {
/*  99 */       throw new IndexOutOfBoundsException(String.format(Locale.ROOT, "Priority %d not supported. Expected range [0-%d]", new Object[] { Integer.valueOf($$1), Integer.valueOf(this.queues.length - 1) }));
/*     */     }
/*     */     
/* 102 */     this.queues[$$1].add($$0);
/* 103 */     this.size.incrementAndGet();
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 109 */     return (this.size.get() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 114 */     return this.size.get();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\thread\StrictQueue$FixedPriorityQueue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */