/*     */ package net.minecraft.server.level;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntConsumer;
/*     */ import java.util.function.IntSupplier;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.Unit;
/*     */ import net.minecraft.util.thread.ProcessorHandle;
/*     */ import net.minecraft.util.thread.ProcessorMailbox;
/*     */ import net.minecraft.util.thread.StrictQueue;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ChunkTaskPriorityQueueSorter
/*     */   implements ChunkHolder.LevelChangeListener, AutoCloseable {
/*  29 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private final Map<ProcessorHandle<?>, ChunkTaskPriorityQueue<? extends Function<ProcessorHandle<Unit>, ?>>> queues;
/*     */   private final Set<ProcessorHandle<?>> sleeping;
/*     */   private final ProcessorMailbox<StrictQueue.IntRunnable> mailbox;
/*     */   
/*     */   public ChunkTaskPriorityQueueSorter(List<ProcessorHandle<?>> $$0, Executor $$1, int $$2) {
/*  35 */     this.queues = (Map<ProcessorHandle<?>, ChunkTaskPriorityQueue<? extends Function<ProcessorHandle<Unit>, ?>>>)$$0.stream().collect(Collectors.toMap(Function.identity(), $$1 -> new ChunkTaskPriorityQueue($$1.name() + "_queue", $$0)));
/*  36 */     this.sleeping = Sets.newHashSet($$0);
/*  37 */     this.mailbox = new ProcessorMailbox((StrictQueue)new StrictQueue.FixedPriorityQueue(4), $$1, "sorter");
/*     */   }
/*     */   
/*     */   public static final class Message<T> {
/*     */     final Function<ProcessorHandle<Unit>, T> task;
/*     */     final long pos;
/*     */     final IntSupplier level;
/*     */     
/*     */     Message(Function<ProcessorHandle<Unit>, T> $$0, long $$1, IntSupplier $$2) {
/*  46 */       this.task = $$0;
/*  47 */       this.pos = $$1;
/*  48 */       this.level = $$2;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasWork() {
/*  53 */     return (this.mailbox.hasWork() || this.queues.values().stream().anyMatch(ChunkTaskPriorityQueue::hasWork));
/*     */   }
/*     */   
/*     */   public static <T> Message<T> message(Function<ProcessorHandle<Unit>, T> $$0, long $$1, IntSupplier $$2) {
/*  57 */     return new Message<>($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static Message<Runnable> message(Runnable $$0, long $$1, IntSupplier $$2) {
/*  61 */     return new Message<>($$1 -> (), $$1, $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message<Runnable> message(ChunkHolder $$0, Runnable $$1) {
/*  68 */     Objects.requireNonNull($$0); return message($$1, $$0.getPos().toLong(), $$0::getQueueLevel);
/*     */   }
/*     */   
/*     */   public static <T> Message<T> message(ChunkHolder $$0, Function<ProcessorHandle<Unit>, T> $$1) {
/*  72 */     Objects.requireNonNull($$0); return message($$1, $$0.getPos().toLong(), $$0::getQueueLevel);
/*     */   }
/*     */   
/*     */   public static final class Release {
/*     */     final Runnable task;
/*     */     final long pos;
/*     */     final boolean clearQueue;
/*     */     
/*     */     Release(Runnable $$0, long $$1, boolean $$2) {
/*  81 */       this.task = $$0;
/*  82 */       this.pos = $$1;
/*  83 */       this.clearQueue = $$2;
/*     */     }
/*     */   }
/*     */   
/*     */   public static Release release(Runnable $$0, long $$1, boolean $$2) {
/*  88 */     return new Release($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public <T> ProcessorHandle<Message<T>> getProcessor(ProcessorHandle<T> $$0, boolean $$1) {
/*  92 */     return this.mailbox.ask($$2 -> new StrictQueue.IntRunnable(0, ()))
/*     */ 
/*     */ 
/*     */       
/*  96 */       .join();
/*     */   }
/*     */   
/*     */   public ProcessorHandle<Release> getReleaseProcessor(ProcessorHandle<Runnable> $$0) {
/* 100 */     return this.mailbox.ask($$1 -> new StrictQueue.IntRunnable(0, ()))
/*     */       
/* 102 */       .join();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLevelChange(ChunkPos $$0, IntSupplier $$1, int $$2, IntConsumer $$3) {
/* 107 */     this.mailbox.tell(new StrictQueue.IntRunnable(0, () -> {
/*     */             int $$4 = $$0.getAsInt();
/*     */             this.queues.values().forEach(());
/*     */             $$3.accept($$2);
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> void release(ProcessorHandle<T> $$0, long $$1, Runnable $$2, boolean $$3) {
/* 118 */     this.mailbox.tell(new StrictQueue.IntRunnable(1, () -> {
/*     */             ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> $$4 = getQueue($$0);
/*     */             $$4.release($$1, $$2);
/*     */             if (this.sleeping.remove($$0)) {
/*     */               pollTask($$4, $$0);
/*     */             }
/*     */             $$3.run();
/*     */           }));
/*     */   }
/*     */   
/*     */   private <T> void submit(ProcessorHandle<T> $$0, Function<ProcessorHandle<Unit>, T> $$1, long $$2, IntSupplier $$3, boolean $$4) {
/* 129 */     this.mailbox.tell(new StrictQueue.IntRunnable(2, () -> {
/*     */             ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> $$5 = getQueue($$0);
/*     */             int $$6 = $$1.getAsInt();
/*     */             $$5.submit(Optional.of($$3), $$2, $$6);
/*     */             if ($$4) {
/*     */               $$5.submit(Optional.empty(), $$2, $$6);
/*     */             }
/*     */             if (this.sleeping.remove($$0)) {
/*     */               pollTask($$5, $$0);
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> void pollTask(ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> $$0, ProcessorHandle<T> $$1) {
/* 146 */     this.mailbox.tell(new StrictQueue.IntRunnable(3, () -> {
/*     */             Stream<Either<Function<ProcessorHandle<Unit>, T>, Runnable>> $$2 = $$0.pop();
/*     */             if ($$2 == null) {
/*     */               this.sleeping.add($$1);
/*     */             } else {
/*     */               CompletableFuture.allOf((CompletableFuture<?>[])$$2.map(()).toArray(())).thenAccept(());
/*     */             } 
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> ChunkTaskPriorityQueue<Function<ProcessorHandle<Unit>, T>> getQueue(ProcessorHandle<T> $$0) {
/* 161 */     ChunkTaskPriorityQueue<? extends Function<ProcessorHandle<Unit>, ?>> $$1 = this.queues.get($$0);
/* 162 */     if ($$1 == null) {
/* 163 */       throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException("No queue for: " + $$0));
/*     */     }
/* 165 */     return (ChunkTaskPriorityQueue)$$1;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public String getDebugStatus() {
/* 170 */     return (String)this.queues.entrySet().stream()
/* 171 */       .map($$0 -> ((ProcessorHandle)$$0.getKey()).name() + "=[" + ((ProcessorHandle)$$0.getKey()).name() + "]")
/* 172 */       .collect(Collectors.joining(",")) + ", s=" + (String)this.queues.entrySet().stream().map($$0 -> ((ProcessorHandle)$$0.getKey()).name() + "=[" + ((ProcessorHandle)$$0.getKey()).name() + "]").collect(Collectors.joining(","));
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 177 */     this.queues.keySet().forEach(ProcessorHandle::close);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ChunkTaskPriorityQueueSorter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */