/*     */ package net.minecraft.util.thread;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Queues;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.locks.LockSupport;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.util.profiling.metrics.MetricCategory;
/*     */ import net.minecraft.util.profiling.metrics.MetricSampler;
/*     */ import net.minecraft.util.profiling.metrics.MetricsRegistry;
/*     */ import net.minecraft.util.profiling.metrics.ProfilerMeasured;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public abstract class BlockableEventLoop<R extends Runnable>
/*     */   implements ProfilerMeasured, ProcessorHandle<R>, Executor
/*     */ {
/*     */   private final String name;
/*  24 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  26 */   private final Queue<R> pendingRunnables = Queues.newConcurrentLinkedQueue();
/*     */   private int blockingCount;
/*     */   
/*     */   protected BlockableEventLoop(String $$0) {
/*  30 */     this.name = $$0;
/*  31 */     MetricsRegistry.INSTANCE.add(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSameThread() {
/*  39 */     return (Thread.currentThread() == getRunningThread());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean scheduleExecutables() {
/*  45 */     return !isSameThread();
/*     */   }
/*     */   
/*     */   public int getPendingTasksCount() {
/*  49 */     return this.pendingRunnables.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/*  54 */     return this.name;
/*     */   }
/*     */   
/*     */   public <V> CompletableFuture<V> submit(Supplier<V> $$0) {
/*  58 */     if (scheduleExecutables()) {
/*  59 */       return CompletableFuture.supplyAsync($$0, this);
/*     */     }
/*  61 */     return CompletableFuture.completedFuture($$0.get());
/*     */   }
/*     */ 
/*     */   
/*     */   private CompletableFuture<Void> submitAsync(Runnable $$0) {
/*  66 */     return CompletableFuture.supplyAsync(() -> { $$0.run(); return null; }this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<Void> submit(Runnable $$0) {
/*  75 */     if (scheduleExecutables()) {
/*  76 */       return submitAsync($$0);
/*     */     }
/*  78 */     $$0.run();
/*  79 */     return CompletableFuture.completedFuture(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void executeBlocking(Runnable $$0) {
/*  84 */     if (!isSameThread()) {
/*  85 */       submitAsync($$0).join();
/*     */     } else {
/*  87 */       $$0.run();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tell(R $$0) {
/*  93 */     this.pendingRunnables.add($$0);
/*  94 */     LockSupport.unpark(getRunningThread());
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(Runnable $$0) {
/*  99 */     if (scheduleExecutables()) {
/* 100 */       tell(wrapRunnable($$0));
/*     */     } else {
/* 102 */       $$0.run();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void executeIfPossible(Runnable $$0) {
/* 107 */     execute($$0);
/*     */   }
/*     */   
/*     */   protected void dropAllTasks() {
/* 111 */     this.pendingRunnables.clear();
/*     */   }
/*     */   
/*     */   protected void runAllTasks() {
/* 115 */     while (pollTask());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean pollTask() {
/* 121 */     Runnable runnable = (Runnable)this.pendingRunnables.peek();
/* 122 */     if (runnable == null) {
/* 123 */       return false;
/*     */     }
/*     */     
/* 126 */     if (this.blockingCount == 0 && !shouldRun((R)runnable)) {
/* 127 */       return false;
/*     */     }
/*     */     
/* 130 */     doRunTask(this.pendingRunnables.remove());
/*     */     
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   public void managedBlock(BooleanSupplier $$0) {
/* 136 */     this.blockingCount++;
/*     */     try {
/* 138 */       while (!$$0.getAsBoolean()) {
/* 139 */         if (!pollTask())
/*     */         {
/* 141 */           waitForTasks();
/*     */         }
/*     */       } 
/*     */     } finally {
/* 145 */       this.blockingCount--;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void waitForTasks() {
/* 150 */     Thread.yield();
/* 151 */     LockSupport.parkNanos("waiting for tasks", 100000L);
/*     */   }
/*     */   
/*     */   protected void doRunTask(R $$0) {
/*     */     try {
/* 156 */       $$0.run();
/* 157 */     } catch (Exception $$1) {
/* 158 */       LOGGER.error(LogUtils.FATAL_MARKER, "Error executing task on {}", name(), $$1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<MetricSampler> profiledMetrics() {
/* 167 */     return (List<MetricSampler>)ImmutableList.of(
/* 168 */         MetricSampler.create(this.name + "-pending-tasks", MetricCategory.EVENT_LOOPS, this::getPendingTasksCount));
/*     */   }
/*     */   
/*     */   protected abstract R wrapRunnable(Runnable paramRunnable);
/*     */   
/*     */   protected abstract boolean shouldRun(R paramR);
/*     */   
/*     */   protected abstract Thread getRunningThread();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\thread\BlockableEventLoop.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */