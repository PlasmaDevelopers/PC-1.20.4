/*     */ package com.mojang.realmsclient.gui.task;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class Subscription
/*     */ {
/* 150 */   private final List<DataFetcher.SubscribedTask<?>> subscriptions = new ArrayList<>();
/*     */   
/*     */   public <T> void subscribe(DataFetcher.Task<T> $$0, Consumer<T> $$1) {
/* 153 */     DataFetcher.SubscribedTask<T> $$2 = new DataFetcher.SubscribedTask<>(DataFetcher.this, $$0, $$1);
/* 154 */     this.subscriptions.add($$2);
/* 155 */     $$2.runCallbackIfNeeded();
/*     */   }
/*     */   
/*     */   public void forceUpdate() {
/* 159 */     for (DataFetcher.SubscribedTask<?> $$0 : this.subscriptions) {
/* 160 */       $$0.runCallback();
/*     */     }
/*     */   }
/*     */   
/*     */   public void tick() {
/* 165 */     for (DataFetcher.SubscribedTask<?> $$0 : this.subscriptions) {
/* 166 */       $$0.update(DataFetcher.this.timeSource.get(DataFetcher.this.resolution));
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset() {
/* 171 */     for (DataFetcher.SubscribedTask<?> $$0 : this.subscriptions)
/* 172 */       $$0.reset(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\task\DataFetcher$Subscription.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */