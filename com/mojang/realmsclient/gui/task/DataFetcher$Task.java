/*     */ package com.mojang.realmsclient.gui.task;
/*     */ 
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.CompletableFuture;
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
/*     */ public class Task<T>
/*     */ {
/*     */   private final String id;
/*     */   private final Callable<T> updater;
/*     */   private final long period;
/*     */   private final RepeatedDelayStrategy repeatStrategy;
/*     */   @Nullable
/*     */   private CompletableFuture<DataFetcher.ComputationResult<T>> pendingTask;
/*     */   @Nullable
/*     */   DataFetcher.SuccessfulComputationResult<T> lastResult;
/*  61 */   private long nextUpdate = -1L;
/*     */   
/*     */   Task(String $$1, Callable<T> $$2, long $$3, RepeatedDelayStrategy $$4) {
/*  64 */     this.id = $$1;
/*  65 */     this.updater = $$2;
/*  66 */     this.period = $$3;
/*  67 */     this.repeatStrategy = $$4;
/*     */   }
/*     */   
/*     */   void updateIfNeeded(long $$0) {
/*  71 */     if (this.pendingTask != null) {
/*  72 */       DataFetcher.ComputationResult<T> $$1 = this.pendingTask.getNow(null);
/*  73 */       if ($$1 == null) {
/*     */         return;
/*     */       }
/*     */       
/*  77 */       this.pendingTask = null;
/*  78 */       long $$2 = $$1.time;
/*  79 */       $$1.value()
/*  80 */         .ifLeft($$1 -> {
/*     */             this.lastResult = new DataFetcher.SuccessfulComputationResult<>((T)$$1, $$0);
/*     */             
/*     */             this.nextUpdate = $$0 + this.period * this.repeatStrategy.delayCyclesAfterSuccess();
/*  84 */           }).ifRight($$1 -> {
/*     */             long $$2 = this.repeatStrategy.delayCyclesAfterFailure();
/*     */             
/*     */             DataFetcher.LOGGER.warn("Failed to process task {}, will repeat after {} cycles", new Object[] { this.id, Long.valueOf($$2), $$1 });
/*     */             this.nextUpdate = $$0 + this.period * $$2;
/*     */           });
/*     */     } 
/*  91 */     if (this.nextUpdate <= $$0) {
/*  92 */       this.pendingTask = CompletableFuture.supplyAsync(() -> {
/*     */             try {
/*     */               T $$0 = this.updater.call();
/*     */               long $$1 = DataFetcher.this.timeSource.get(DataFetcher.this.resolution);
/*     */               return new DataFetcher.ComputationResult(Either.left($$0), $$1);
/*  97 */             } catch (Exception $$2) {
/*     */               long $$3 = DataFetcher.this.timeSource.get(DataFetcher.this.resolution);
/*     */               return new DataFetcher.ComputationResult(Either.right($$2), $$3);
/*     */             } 
/*     */           }DataFetcher.this.executor);
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset() {
/* 106 */     this.pendingTask = null;
/* 107 */     this.lastResult = null;
/* 108 */     this.nextUpdate = -1L;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\task\DataFetcher$Task.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */