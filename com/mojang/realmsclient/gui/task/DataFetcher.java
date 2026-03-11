/*     */ package com.mojang.realmsclient.gui.task;
/*     */ 
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.time.Duration;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.TimeSource;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataFetcher
/*     */ {
/*  23 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   final Executor executor;
/*     */   final TimeUnit resolution;
/*     */   final TimeSource timeSource;
/*     */   
/*     */   public DataFetcher(Executor $$0, TimeUnit $$1, TimeSource $$2) {
/*  30 */     this.executor = $$0;
/*  31 */     this.resolution = $$1;
/*  32 */     this.timeSource = $$2;
/*     */   }
/*     */   
/*     */   public <T> Task<T> createTask(String $$0, Callable<T> $$1, Duration $$2, RepeatedDelayStrategy $$3) {
/*  36 */     long $$4 = this.resolution.convert($$2);
/*  37 */     if ($$4 == 0L) {
/*  38 */       throw new IllegalArgumentException("Period of " + $$2 + " too short for selected resolution of " + this.resolution);
/*     */     }
/*  40 */     return new Task<>($$0, $$1, $$4, $$3);
/*     */   }
/*     */   
/*     */   public Subscription createSubscription() {
/*  44 */     return new Subscription();
/*     */   }
/*     */   private static final class ComputationResult<T> extends Record { private final Either<T, Exception> value; final long time;
/*  47 */     ComputationResult(Either<T, Exception> $$0, long $$1) { this.value = $$0; this.time = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #47	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*  47 */       //   0	7	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult<TT;>; } public Either<T, Exception> value() { return this.value; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #47	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #47	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*  47 */       //   0	8	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$ComputationResult<TT;>; } public long time() { return this.time; }
/*     */      } private static final class SuccessfulComputationResult<T> extends Record { final T value; final long time;
/*  49 */     SuccessfulComputationResult(T $$0, long $$1) { this.value = $$0; this.time = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult<TT;>; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*  49 */       //   0	8	0	this	Lcom/mojang/realmsclient/gui/task/DataFetcher$SuccessfulComputationResult<TT;>; } public T value() { return this.value; } public long time() { return this.time; }
/*     */      }
/*     */   
/*     */   public class Task<T> {
/*     */     private final String id;
/*     */     private final Callable<T> updater;
/*     */     private final long period;
/*     */     private final RepeatedDelayStrategy repeatStrategy;
/*     */     @Nullable
/*     */     private CompletableFuture<DataFetcher.ComputationResult<T>> pendingTask;
/*     */     @Nullable
/*     */     DataFetcher.SuccessfulComputationResult<T> lastResult;
/*  61 */     private long nextUpdate = -1L;
/*     */     
/*     */     Task(String $$1, Callable<T> $$2, long $$3, RepeatedDelayStrategy $$4) {
/*  64 */       this.id = $$1;
/*  65 */       this.updater = $$2;
/*  66 */       this.period = $$3;
/*  67 */       this.repeatStrategy = $$4;
/*     */     }
/*     */     
/*     */     void updateIfNeeded(long $$0) {
/*  71 */       if (this.pendingTask != null) {
/*  72 */         DataFetcher.ComputationResult<T> $$1 = this.pendingTask.getNow(null);
/*  73 */         if ($$1 == null) {
/*     */           return;
/*     */         }
/*     */         
/*  77 */         this.pendingTask = null;
/*  78 */         long $$2 = $$1.time;
/*  79 */         $$1.value()
/*  80 */           .ifLeft($$1 -> {
/*     */               this.lastResult = new DataFetcher.SuccessfulComputationResult<>((T)$$1, $$0);
/*     */               
/*     */               this.nextUpdate = $$0 + this.period * this.repeatStrategy.delayCyclesAfterSuccess();
/*  84 */             }).ifRight($$1 -> {
/*     */               long $$2 = this.repeatStrategy.delayCyclesAfterFailure();
/*     */               
/*     */               DataFetcher.LOGGER.warn("Failed to process task {}, will repeat after {} cycles", new Object[] { this.id, Long.valueOf($$2), $$1 });
/*     */               this.nextUpdate = $$0 + this.period * $$2;
/*     */             });
/*     */       } 
/*  91 */       if (this.nextUpdate <= $$0) {
/*  92 */         this.pendingTask = CompletableFuture.supplyAsync(() -> {
/*     */               try {
/*     */                 T $$0 = this.updater.call();
/*     */                 long $$1 = DataFetcher.this.timeSource.get(DataFetcher.this.resolution);
/*     */                 return new DataFetcher.ComputationResult(Either.left($$0), $$1);
/*  97 */               } catch (Exception $$2) {
/*     */                 long $$3 = DataFetcher.this.timeSource.get(DataFetcher.this.resolution);
/*     */                 return new DataFetcher.ComputationResult(Either.right($$2), $$3);
/*     */               } 
/*     */             }DataFetcher.this.executor);
/*     */       }
/*     */     }
/*     */     
/*     */     public void reset() {
/* 106 */       this.pendingTask = null;
/* 107 */       this.lastResult = null;
/* 108 */       this.nextUpdate = -1L;
/*     */     }
/*     */   }
/*     */   
/*     */   private class SubscribedTask<T> {
/*     */     private final DataFetcher.Task<T> task;
/*     */     private final Consumer<T> output;
/* 115 */     private long lastCheckTime = -1L;
/*     */     
/*     */     SubscribedTask(DataFetcher.Task<T> $$0, Consumer<T> $$1) {
/* 118 */       this.task = $$0;
/* 119 */       this.output = $$1;
/*     */     }
/*     */     
/*     */     void update(long $$0) {
/* 123 */       this.task.updateIfNeeded($$0);
/* 124 */       runCallbackIfNeeded();
/*     */     }
/*     */     
/*     */     void runCallbackIfNeeded() {
/* 128 */       DataFetcher.SuccessfulComputationResult<T> $$0 = this.task.lastResult;
/* 129 */       if ($$0 != null && this.lastCheckTime < $$0.time) {
/* 130 */         this.output.accept($$0.value);
/* 131 */         this.lastCheckTime = $$0.time;
/*     */       } 
/*     */     }
/*     */     
/*     */     void runCallback() {
/* 136 */       DataFetcher.SuccessfulComputationResult<T> $$0 = this.task.lastResult;
/* 137 */       if ($$0 != null) {
/* 138 */         this.output.accept($$0.value);
/* 139 */         this.lastCheckTime = $$0.time;
/*     */       } 
/*     */     }
/*     */     
/*     */     void reset() {
/* 144 */       this.task.reset();
/* 145 */       this.lastCheckTime = -1L;
/*     */     } }
/*     */   public class Subscription { private final List<DataFetcher.SubscribedTask<?>> subscriptions;
/*     */     
/*     */     public Subscription() {
/* 150 */       this.subscriptions = new ArrayList<>();
/*     */     }
/*     */     public <T> void subscribe(DataFetcher.Task<T> $$0, Consumer<T> $$1) {
/* 153 */       DataFetcher.SubscribedTask<T> $$2 = new DataFetcher.SubscribedTask<>($$0, $$1);
/* 154 */       this.subscriptions.add($$2);
/* 155 */       $$2.runCallbackIfNeeded();
/*     */     }
/*     */     
/*     */     public void forceUpdate() {
/* 159 */       for (DataFetcher.SubscribedTask<?> $$0 : this.subscriptions) {
/* 160 */         $$0.runCallback();
/*     */       }
/*     */     }
/*     */     
/*     */     public void tick() {
/* 165 */       for (DataFetcher.SubscribedTask<?> $$0 : this.subscriptions) {
/* 166 */         $$0.update(DataFetcher.this.timeSource.get(DataFetcher.this.resolution));
/*     */       }
/*     */     }
/*     */     
/*     */     public void reset() {
/* 171 */       for (DataFetcher.SubscribedTask<?> $$0 : this.subscriptions)
/* 172 */         $$0.reset(); 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\task\DataFetcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */