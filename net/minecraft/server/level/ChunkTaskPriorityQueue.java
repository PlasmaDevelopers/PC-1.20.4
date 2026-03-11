/*     */ package net.minecraft.server.level;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongCollection;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ 
/*     */ public class ChunkTaskPriorityQueue<T> {
/*  19 */   public static final int PRIORITY_LEVEL_COUNT = ChunkLevel.MAX_LEVEL + 2;
/*  20 */   private final List<Long2ObjectLinkedOpenHashMap<List<Optional<T>>>> taskQueue = (List<Long2ObjectLinkedOpenHashMap<List<Optional<T>>>>)IntStream.range(0, PRIORITY_LEVEL_COUNT).mapToObj($$0 -> new Long2ObjectLinkedOpenHashMap()).collect(Collectors.toList());
/*  21 */   private volatile int firstQueue = PRIORITY_LEVEL_COUNT;
/*     */   
/*     */   private final String name;
/*     */   
/*  25 */   private final LongSet acquired = (LongSet)new LongOpenHashSet();
/*     */   private final int maxTasks;
/*     */   
/*     */   public ChunkTaskPriorityQueue(String $$0, int $$1) {
/*  29 */     this.name = $$0;
/*  30 */     this.maxTasks = $$1;
/*     */   }
/*     */   
/*     */   protected void resortChunkTasks(int $$0, ChunkPos $$1, int $$2) {
/*  34 */     if ($$0 >= PRIORITY_LEVEL_COUNT) {
/*     */       return;
/*     */     }
/*  37 */     Long2ObjectLinkedOpenHashMap<List<Optional<T>>> $$3 = this.taskQueue.get($$0);
/*  38 */     List<Optional<T>> $$4 = (List<Optional<T>>)$$3.remove($$1.toLong());
/*  39 */     if ($$0 == this.firstQueue) {
/*  40 */       while (hasWork() && ((Long2ObjectLinkedOpenHashMap)this.taskQueue.get(this.firstQueue)).isEmpty()) {
/*  41 */         this.firstQueue++;
/*     */       }
/*     */     }
/*  44 */     if ($$4 != null && !$$4.isEmpty()) {
/*  45 */       ((List<Optional<T>>)((Long2ObjectLinkedOpenHashMap)this.taskQueue.get($$2)).computeIfAbsent($$1.toLong(), $$0 -> Lists.newArrayList())).addAll($$4);
/*  46 */       this.firstQueue = Math.min(this.firstQueue, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void submit(Optional<T> $$0, long $$1, int $$2) {
/*  51 */     ((List<Optional<T>>)((Long2ObjectLinkedOpenHashMap)this.taskQueue.get($$2)).computeIfAbsent($$1, $$0 -> Lists.newArrayList())).add($$0);
/*  52 */     this.firstQueue = Math.min(this.firstQueue, $$2);
/*     */   }
/*     */   
/*     */   protected void release(long $$0, boolean $$1) {
/*  56 */     for (Long2ObjectLinkedOpenHashMap<List<Optional<T>>> $$2 : this.taskQueue) {
/*  57 */       List<Optional<T>> $$3 = (List<Optional<T>>)$$2.get($$0);
/*  58 */       if ($$3 == null) {
/*     */         continue;
/*     */       }
/*  61 */       if ($$1) {
/*  62 */         $$3.clear();
/*     */       } else {
/*  64 */         $$3.removeIf($$0 -> $$0.isEmpty());
/*     */       } 
/*  66 */       if ($$3.isEmpty()) {
/*  67 */         $$2.remove($$0);
/*     */       }
/*     */     } 
/*  70 */     while (hasWork() && ((Long2ObjectLinkedOpenHashMap)this.taskQueue.get(this.firstQueue)).isEmpty()) {
/*  71 */       this.firstQueue++;
/*     */     }
/*  73 */     this.acquired.remove($$0);
/*     */   }
/*     */   
/*     */   private Runnable acquire(long $$0) {
/*  77 */     return () -> this.acquired.add($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Stream<Either<T, Runnable>> pop() {
/*  82 */     if (this.acquired.size() >= this.maxTasks) {
/*  83 */       return null;
/*     */     }
/*  85 */     if (hasWork()) {
/*  86 */       int $$0 = this.firstQueue;
/*  87 */       Long2ObjectLinkedOpenHashMap<List<Optional<T>>> $$1 = this.taskQueue.get($$0);
/*  88 */       long $$2 = $$1.firstLongKey();
/*  89 */       List<Optional<T>> $$3 = (List<Optional<T>>)$$1.removeFirst();
/*  90 */       while (hasWork() && ((Long2ObjectLinkedOpenHashMap)this.taskQueue.get(this.firstQueue)).isEmpty()) {
/*  91 */         this.firstQueue++;
/*     */       }
/*  93 */       return $$3.stream().map($$1 -> (Either)$$1.map(Either::left).orElseGet(()));
/*     */     } 
/*  95 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasWork() {
/*  99 */     return (this.firstQueue < PRIORITY_LEVEL_COUNT);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 104 */     return this.name + " " + this.name + "...";
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   LongSet getAcquired() {
/* 109 */     return (LongSet)new LongOpenHashSet((LongCollection)this.acquired);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ChunkTaskPriorityQueue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */