/*     */ package net.minecraft.util.thread;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.profiling.metrics.MetricCategory;
/*     */ import net.minecraft.util.profiling.metrics.MetricSampler;
/*     */ import net.minecraft.util.profiling.metrics.MetricsRegistry;
/*     */ import net.minecraft.util.profiling.metrics.ProfilerMeasured;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProcessorMailbox<T>
/*     */   implements ProfilerMeasured, ProcessorHandle<T>, AutoCloseable, Runnable
/*     */ {
/*  23 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int CLOSED_BIT = 1;
/*     */   private static final int SCHEDULED_BIT = 2;
/*  27 */   private final AtomicInteger status = new AtomicInteger(0);
/*     */   private final StrictQueue<? super T, ? extends Runnable> queue;
/*     */   private final Executor dispatcher;
/*     */   private final String name;
/*     */   
/*     */   public static ProcessorMailbox<Runnable> create(Executor $$0, String $$1) {
/*  33 */     return new ProcessorMailbox<>(new StrictQueue.QueueStrictQueue<>(new ConcurrentLinkedQueue<>()), $$0, $$1);
/*     */   }
/*     */   
/*     */   public ProcessorMailbox(StrictQueue<? super T, ? extends Runnable> $$0, Executor $$1, String $$2) {
/*  37 */     this.dispatcher = $$1;
/*  38 */     this.queue = $$0;
/*  39 */     this.name = $$2;
/*  40 */     MetricsRegistry.INSTANCE.add(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean setAsScheduled() {
/*     */     while (true) {
/*  46 */       int $$0 = this.status.get();
/*  47 */       if (($$0 & 0x3) != 0) {
/*  48 */         return false;
/*     */       }
/*  50 */       if (this.status.compareAndSet($$0, $$0 | 0x2))
/*  51 */         return true; 
/*     */     } 
/*     */   }
/*     */   private void setAsIdle() {
/*     */     int $$0;
/*     */     do {
/*  57 */       $$0 = this.status.get();
/*  58 */     } while (!this.status.compareAndSet($$0, $$0 & 0xFFFFFFFD));
/*     */   }
/*     */   
/*     */   private boolean canBeScheduled() {
/*  62 */     if ((this.status.get() & 0x1) != 0) {
/*  63 */       return false;
/*     */     }
/*     */     
/*  66 */     return !this.queue.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     int $$0;
/*     */     do {
/*  73 */       $$0 = this.status.get();
/*  74 */     } while (!this.status.compareAndSet($$0, $$0 | 0x1));
/*     */   }
/*     */   
/*     */   private boolean shouldProcess() {
/*  78 */     return ((this.status.get() & 0x2) != 0);
/*     */   }
/*     */   
/*     */   private boolean pollTask() {
/*  82 */     if (!shouldProcess()) {
/*  83 */       return false;
/*     */     }
/*     */     
/*  86 */     Runnable $$0 = this.queue.pop();
/*  87 */     if ($$0 == null) {
/*  88 */       return false;
/*     */     }
/*     */     
/*  91 */     Util.wrapThreadWithTaskName(this.name, $$0).run();
/*     */     
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 102 */       pollUntil($$0 -> ($$0 == 0));
/*     */     } finally {
/* 104 */       setAsIdle();
/* 105 */       registerForExecution();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void runAll() {
/*     */     try {
/* 114 */       pollUntil($$0 -> true);
/*     */     } finally {
/* 116 */       setAsIdle();
/* 117 */       registerForExecution();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tell(T $$0) {
/* 123 */     this.queue.push($$0);
/* 124 */     registerForExecution();
/*     */   }
/*     */   
/*     */   private void registerForExecution() {
/* 128 */     if (canBeScheduled() && 
/* 129 */       setAsScheduled()) {
/*     */       try {
/* 131 */         this.dispatcher.execute(this);
/* 132 */       } catch (RejectedExecutionException $$0) {
/*     */         
/*     */         try {
/* 135 */           this.dispatcher.execute(this);
/* 136 */         } catch (RejectedExecutionException $$1) {
/* 137 */           LOGGER.error("Cound not schedule mailbox", $$1);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private int pollUntil(Int2BooleanFunction $$0) {
/* 145 */     int $$1 = 0;
/* 146 */     while ($$0.get($$1) && pollTask()) {
/* 147 */       $$1++;
/*     */     }
/* 149 */     return $$1;
/*     */   }
/*     */   
/*     */   public int size() {
/* 153 */     return this.queue.size();
/*     */   }
/*     */   
/*     */   public boolean hasWork() {
/* 157 */     return (shouldProcess() && !this.queue.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 162 */     return this.name + " " + this.name + " " + this.status.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public String name() {
/* 167 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<MetricSampler> profiledMetrics() {
/* 172 */     return (List<MetricSampler>)ImmutableList.of(
/* 173 */         MetricSampler.create(this.name + "-queue-size", MetricCategory.MAIL_BOXES, this::size));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\thread\ProcessorMailbox.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */