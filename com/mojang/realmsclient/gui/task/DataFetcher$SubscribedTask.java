/*     */ package com.mojang.realmsclient.gui.task;
/*     */ 
/*     */ import java.util.function.Consumer;
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
/*     */ class SubscribedTask<T>
/*     */ {
/*     */   private final DataFetcher.Task<T> task;
/*     */   private final Consumer<T> output;
/* 115 */   private long lastCheckTime = -1L;
/*     */   
/*     */   SubscribedTask(DataFetcher.Task<T> $$0, Consumer<T> $$1) {
/* 118 */     this.task = $$0;
/* 119 */     this.output = $$1;
/*     */   }
/*     */   
/*     */   void update(long $$0) {
/* 123 */     this.task.updateIfNeeded($$0);
/* 124 */     runCallbackIfNeeded();
/*     */   }
/*     */   
/*     */   void runCallbackIfNeeded() {
/* 128 */     DataFetcher.SuccessfulComputationResult<T> $$0 = this.task.lastResult;
/* 129 */     if ($$0 != null && this.lastCheckTime < $$0.time) {
/* 130 */       this.output.accept($$0.value);
/* 131 */       this.lastCheckTime = $$0.time;
/*     */     } 
/*     */   }
/*     */   
/*     */   void runCallback() {
/* 136 */     DataFetcher.SuccessfulComputationResult<T> $$0 = this.task.lastResult;
/* 137 */     if ($$0 != null) {
/* 138 */       this.output.accept($$0.value);
/* 139 */       this.lastCheckTime = $$0.time;
/*     */     } 
/*     */   }
/*     */   
/*     */   void reset() {
/* 144 */     this.task.reset();
/* 145 */     this.lastCheckTime = -1L;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\task\DataFetcher$SubscribedTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */