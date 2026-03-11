/*     */ package net.minecraft.world.ticks;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ 
/*     */ public class LevelChunkTicks<T> implements SerializableTickContainer<T>, TickContainerAccess<T> {
/*  22 */   private final Queue<ScheduledTick<T>> tickQueue = (Queue)new PriorityQueue<>(ScheduledTick.DRAIN_ORDER);
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private List<SavedTick<T>> pendingTicks;
/*     */ 
/*     */   
/*  29 */   private final Set<ScheduledTick<?>> ticksPerPosition = (Set<ScheduledTick<?>>)new ObjectOpenCustomHashSet(ScheduledTick.UNIQUE_TICK_HASH);
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private BiConsumer<LevelChunkTicks<T>, ScheduledTick<T>> onTickAdded;
/*     */ 
/*     */ 
/*     */   
/*     */   public LevelChunkTicks(List<SavedTick<T>> $$0) {
/*  38 */     this.pendingTicks = $$0;
/*  39 */     for (SavedTick<T> $$1 : $$0) {
/*  40 */       this.ticksPerPosition.add(ScheduledTick.probe($$1.type(), $$1.pos()));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setOnTickAdded(@Nullable BiConsumer<LevelChunkTicks<T>, ScheduledTick<T>> $$0) {
/*  45 */     this.onTickAdded = $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ScheduledTick<T> peek() {
/*  53 */     return this.tickQueue.peek();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ScheduledTick<T> poll() {
/*  58 */     ScheduledTick<T> $$0 = this.tickQueue.poll();
/*  59 */     if ($$0 != null) {
/*  60 */       this.ticksPerPosition.remove($$0);
/*     */     }
/*  62 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void schedule(ScheduledTick<T> $$0) {
/*  67 */     if (this.ticksPerPosition.add($$0)) {
/*  68 */       scheduleUnchecked($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private void scheduleUnchecked(ScheduledTick<T> $$0) {
/*  73 */     this.tickQueue.add($$0);
/*     */     
/*  75 */     if (this.onTickAdded != null) {
/*  76 */       this.onTickAdded.accept(this, $$0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasScheduledTick(BlockPos $$0, T $$1) {
/*  83 */     return this.ticksPerPosition.contains(ScheduledTick.probe($$1, $$0));
/*     */   }
/*     */   
/*     */   public void removeIf(Predicate<ScheduledTick<T>> $$0) {
/*  87 */     for (Iterator<ScheduledTick<T>> $$1 = this.tickQueue.iterator(); $$1.hasNext(); ) {
/*  88 */       ScheduledTick<T> $$2 = $$1.next();
/*  89 */       if ($$0.test($$2)) {
/*  90 */         $$1.remove();
/*  91 */         this.ticksPerPosition.remove($$2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Stream<ScheduledTick<T>> getAll() {
/*  97 */     return this.tickQueue.stream();
/*     */   }
/*     */ 
/*     */   
/*     */   public int count() {
/* 102 */     return this.tickQueue.size() + ((this.pendingTicks != null) ? this.pendingTicks.size() : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ListTag save(long $$0, Function<T, String> $$1) {
/* 107 */     ListTag $$2 = new ListTag();
/* 108 */     if (this.pendingTicks != null) {
/* 109 */       for (SavedTick<T> $$3 : this.pendingTicks) {
/* 110 */         $$2.add($$3.save($$1));
/*     */       }
/*     */     }
/*     */     
/* 114 */     for (ScheduledTick<T> $$4 : this.tickQueue) {
/* 115 */       $$2.add(SavedTick.saveTick($$4, $$1, $$0));
/*     */     }
/*     */     
/* 118 */     return $$2;
/*     */   }
/*     */   
/*     */   public void unpack(long $$0) {
/* 122 */     if (this.pendingTicks != null) {
/* 123 */       int $$1 = -this.pendingTicks.size();
/* 124 */       for (SavedTick<T> $$2 : this.pendingTicks)
/*     */       {
/* 126 */         scheduleUnchecked($$2.unpack($$0, $$1++));
/*     */       }
/*     */     } 
/*     */     
/* 130 */     this.pendingTicks = null;
/*     */   }
/*     */   
/*     */   public static <T> LevelChunkTicks<T> load(ListTag $$0, Function<String, Optional<T>> $$1, ChunkPos $$2) {
/* 134 */     ImmutableList.Builder<SavedTick<T>> $$3 = ImmutableList.builder();
/* 135 */     Objects.requireNonNull($$3); SavedTick.loadTickList($$0, $$1, $$2, $$3::add);
/* 136 */     return new LevelChunkTicks<>((List<SavedTick<T>>)$$3.build());
/*     */   }
/*     */   
/*     */   public LevelChunkTicks() {}
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\LevelChunkTicks.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */