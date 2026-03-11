/*     */ package net.minecraft.server.packs.resources;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.Unit;
/*     */ import net.minecraft.util.profiling.InactiveProfiler;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ 
/*     */ public class SimpleReloadInstance<S> implements ReloadInstance {
/*     */   private static final int PREPARATION_PROGRESS_WEIGHT = 2;
/*     */   private static final int EXTRA_RELOAD_PROGRESS_WEIGHT = 2;
/*     */   private static final int LISTENER_PROGRESS_WEIGHT = 1;
/*  20 */   protected final CompletableFuture<Unit> allPreparations = new CompletableFuture<>();
/*     */   
/*     */   protected CompletableFuture<List<S>> allDone;
/*     */   
/*     */   final Set<PreparableReloadListener> preparingListeners;
/*     */   private final int listenerCount;
/*     */   private int startedReloads;
/*     */   private int finishedReloads;
/*  28 */   private final AtomicInteger startedTaskCounter = new AtomicInteger();
/*  29 */   private final AtomicInteger doneTaskCounter = new AtomicInteger();
/*     */   
/*     */   public static SimpleReloadInstance<Void> of(ResourceManager $$0, List<PreparableReloadListener> $$1, Executor $$2, Executor $$3, CompletableFuture<Unit> $$4) {
/*  32 */     return new SimpleReloadInstance<>($$2, $$3, $$0, $$1, ($$1, $$2, $$3, $$4, $$5) -> $$3.reload($$1, $$2, (ProfilerFiller)InactiveProfiler.INSTANCE, (ProfilerFiller)InactiveProfiler.INSTANCE, $$0, $$5), $$4);
/*     */   }
/*     */   
/*     */   protected SimpleReloadInstance(Executor $$0, final Executor mainThreadExecutor, ResourceManager $$2, List<PreparableReloadListener> $$3, StateFactory<S> $$4, CompletableFuture<Unit> $$5) {
/*  36 */     this.listenerCount = $$3.size();
/*  37 */     this.startedTaskCounter.incrementAndGet();
/*  38 */     Objects.requireNonNull(this.doneTaskCounter); $$5.thenRun(this.doneTaskCounter::incrementAndGet);
/*  39 */     List<CompletableFuture<S>> $$6 = Lists.newArrayList();
/*  40 */     CompletableFuture<?> $$7 = $$5;
/*  41 */     this.preparingListeners = Sets.newHashSet($$3);
/*  42 */     for (PreparableReloadListener $$8 : $$3) {
/*  43 */       final CompletableFuture<?> previousTask = $$7;
/*  44 */       CompletableFuture<S> $$10 = $$4.create(new PreparableReloadListener.PreparationBarrier()
/*     */           {
/*     */             public <T> CompletableFuture<T> wait(T $$0)
/*     */             {
/*  48 */               mainThreadExecutor.execute(() -> {
/*     */                     SimpleReloadInstance.this.preparingListeners.remove($$0);
/*     */                     if (SimpleReloadInstance.this.preparingListeners.isEmpty()) {
/*     */                       SimpleReloadInstance.this.allPreparations.complete(Unit.INSTANCE);
/*     */                     }
/*     */                   });
/*  54 */               return SimpleReloadInstance.this.allPreparations.thenCombine(previousTask, ($$1, $$2) -> $$0);
/*     */             }
/*     */           }$$2, $$8, $$1 -> {
/*     */             this.startedTaskCounter.incrementAndGet();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             $$0.execute(());
/*     */           }$$1 -> {
/*     */             this.startedReloads++;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             $$0.execute(());
/*     */           });
/*     */ 
/*     */ 
/*     */       
/*  74 */       $$6.add($$10);
/*  75 */       $$7 = $$10;
/*     */     } 
/*  77 */     this.allDone = Util.sequenceFailFast($$6);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<?> done() {
/*  82 */     return this.allDone;
/*     */   }
/*     */   protected static interface StateFactory<S> {
/*     */     CompletableFuture<S> create(PreparableReloadListener.PreparationBarrier param1PreparationBarrier, ResourceManager param1ResourceManager, PreparableReloadListener param1PreparableReloadListener, Executor param1Executor1, Executor param1Executor2); }
/*     */   public float getActualProgress() {
/*  87 */     int $$0 = this.listenerCount - this.preparingListeners.size();
/*  88 */     float $$1 = (this.doneTaskCounter.get() * 2 + this.finishedReloads * 2 + $$0 * 1);
/*  89 */     float $$2 = (this.startedTaskCounter.get() * 2 + this.startedReloads * 2 + this.listenerCount * 1);
/*  90 */     return $$1 / $$2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReloadInstance create(ResourceManager $$0, List<PreparableReloadListener> $$1, Executor $$2, Executor $$3, CompletableFuture<Unit> $$4, boolean $$5) {
/*  98 */     if ($$5) {
/*  99 */       return new ProfiledReloadInstance($$0, $$1, $$2, $$3, $$4);
/*     */     }
/* 101 */     return of($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\SimpleReloadInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */