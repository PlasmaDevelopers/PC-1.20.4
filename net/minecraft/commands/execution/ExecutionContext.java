/*     */ package net.minecraft.commands.execution;
/*     */ 
/*     */ import com.google.common.collect.Queues;
/*     */ import com.mojang.brigadier.context.ContextChain;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandResultCallback;
/*     */ import net.minecraft.commands.ExecutionCommandSource;
/*     */ import net.minecraft.commands.execution.tasks.BuildContexts;
/*     */ import net.minecraft.commands.execution.tasks.CallFunction;
/*     */ import net.minecraft.commands.functions.InstantiatedFunction;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ExecutionContext<T>
/*     */   implements AutoCloseable {
/*     */   private static final int MAX_QUEUE_DEPTH = 10000000;
/*  22 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final int commandLimit;
/*     */   
/*     */   private final int forkLimit;
/*     */   private final ProfilerFiller profiler;
/*     */   @Nullable
/*     */   private TraceCallbacks tracer;
/*     */   private int commandQuota;
/*     */   private boolean queueOverflow;
/*  32 */   private final Deque<CommandQueueEntry<T>> commandQueue = Queues.newArrayDeque();
/*  33 */   private final List<CommandQueueEntry<T>> newTopCommands = (List<CommandQueueEntry<T>>)new ObjectArrayList();
/*     */   private int currentFrameDepth;
/*     */   
/*     */   public ExecutionContext(int $$0, int $$1, ProfilerFiller $$2) {
/*  37 */     this.commandLimit = $$0;
/*  38 */     this.forkLimit = $$1;
/*  39 */     this.profiler = $$2;
/*     */     
/*  41 */     this.commandQuota = $$0;
/*     */   }
/*     */   
/*     */   private static <T extends ExecutionCommandSource<T>> Frame createTopFrame(ExecutionContext<T> $$0, CommandResultCallback $$1) {
/*  45 */     if ($$0.currentFrameDepth == 0) {
/*  46 */       Objects.requireNonNull($$0.commandQueue); return new Frame(0, $$1, $$0.commandQueue::clear);
/*     */     } 
/*  48 */     int $$2 = $$0.currentFrameDepth + 1;
/*  49 */     return new Frame($$2, $$1, $$0.frameControlForDepth($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T extends ExecutionCommandSource<T>> void queueInitialFunctionCall(ExecutionContext<T> $$0, InstantiatedFunction<T> $$1, T $$2, CommandResultCallback $$3) {
/*  54 */     $$0.queueNext(new CommandQueueEntry<>(createTopFrame($$0, $$3), (new CallFunction($$1, $$2.callback(), false)).bind($$2)));
/*     */   }
/*     */   
/*     */   public static <T extends ExecutionCommandSource<T>> void queueInitialCommandExecution(ExecutionContext<T> $$0, String $$1, ContextChain<T> $$2, T $$3, CommandResultCallback $$4) {
/*  58 */     $$0.queueNext(new CommandQueueEntry<>(createTopFrame($$0, $$4), (EntryAction<T>)new BuildContexts.TopLevel($$1, $$2, (ExecutionCommandSource)$$3)));
/*     */   }
/*     */   
/*     */   private void handleQueueOverflow() {
/*  62 */     this.queueOverflow = true;
/*     */     
/*  64 */     this.newTopCommands.clear();
/*  65 */     this.commandQueue.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void queueNext(CommandQueueEntry<T> $$0) {
/*  71 */     if (this.newTopCommands.size() + this.commandQueue.size() > 10000000) {
/*  72 */       handleQueueOverflow();
/*     */     }
/*     */     
/*  75 */     if (!this.queueOverflow) {
/*  76 */       this.newTopCommands.add($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void discardAtDepthOrHigher(int $$0) {
/*  82 */     while (!this.commandQueue.isEmpty() && ((CommandQueueEntry)this.commandQueue.peek()).frame().depth() >= $$0) {
/*  83 */       this.commandQueue.removeFirst();
/*     */     }
/*     */   }
/*     */   
/*     */   public Frame.FrameControl frameControlForDepth(int $$0) {
/*  88 */     return () -> discardAtDepthOrHigher($$0);
/*     */   }
/*     */   
/*     */   public void runCommandQueue() {
/*  92 */     pushNewCommands();
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/*  97 */       if (this.commandQuota <= 0) {
/*  98 */         LOGGER.info("Command execution stopped due to limit (executed {} commands)", Integer.valueOf(this.commandLimit));
/*     */         
/*     */         break;
/*     */       } 
/* 102 */       CommandQueueEntry<T> $$0 = this.commandQueue.pollFirst();
/* 103 */       if ($$0 == null) {
/*     */         return;
/*     */       }
/* 106 */       this.currentFrameDepth = $$0.frame().depth();
/* 107 */       $$0.execute(this);
/*     */       
/* 109 */       if (this.queueOverflow) {
/* 110 */         LOGGER.error("Command execution stopped due to command queue overflow (max {})", Integer.valueOf(10000000));
/*     */         
/*     */         break;
/*     */       } 
/* 114 */       pushNewCommands();
/*     */     } 
/* 116 */     this.currentFrameDepth = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private void pushNewCommands() {
/* 121 */     for (int $$0 = this.newTopCommands.size() - 1; $$0 >= 0; $$0--) {
/* 122 */       this.commandQueue.addFirst(this.newTopCommands.get($$0));
/*     */     }
/* 124 */     this.newTopCommands.clear();
/*     */   }
/*     */   
/*     */   public void tracer(@Nullable TraceCallbacks $$0) {
/* 128 */     this.tracer = $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public TraceCallbacks tracer() {
/* 133 */     return this.tracer;
/*     */   }
/*     */   
/*     */   public ProfilerFiller profiler() {
/* 137 */     return this.profiler;
/*     */   }
/*     */   
/*     */   public int forkLimit() {
/* 141 */     return this.forkLimit;
/*     */   }
/*     */   
/*     */   public void incrementCost() {
/* 145 */     this.commandQuota--;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 150 */     if (this.tracer != null)
/* 151 */       this.tracer.close(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\ExecutionContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */