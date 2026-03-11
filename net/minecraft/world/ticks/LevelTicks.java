/*     */ package net.minecraft.world.ticks;
/*     */ import it.unimi.dsi.fastutil.longs.Long2LongMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.LongSummaryStatistics;
/*     */ import java.util.Objects;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.LongPredicate;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ 
/*     */ public class LevelTicks<T> implements LevelTickAccess<T> {
/*     */   private static final Comparator<LevelChunkTicks<?>> CONTAINER_DRAIN_ORDER;
/*     */   private final LongPredicate tickCheck;
/*     */   private final Supplier<ProfilerFiller> profiler;
/*     */   
/*     */   static {
/*  32 */     CONTAINER_DRAIN_ORDER = (($$0, $$1) -> ScheduledTick.INTRA_TICK_DRAIN_ORDER.compare($$0.peek(), $$1.peek()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  37 */   private final Long2ObjectMap<LevelChunkTicks<T>> allContainers = (Long2ObjectMap<LevelChunkTicks<T>>)new Long2ObjectOpenHashMap(); private final Long2LongMap nextTickForContainer; private final Queue<LevelChunkTicks<T>> containersToTick; private final Queue<ScheduledTick<T>> toRunThisTick; public LevelTicks(LongPredicate $$0, Supplier<ProfilerFiller> $$1) {
/*  38 */     this.nextTickForContainer = (Long2LongMap)Util.make(new Long2LongOpenHashMap(), $$0 -> $$0.defaultReturnValue(Long.MAX_VALUE));
/*     */     
/*  40 */     this.containersToTick = (Queue)new PriorityQueue<>(CONTAINER_DRAIN_ORDER);
/*  41 */     this.toRunThisTick = new ArrayDeque<>();
/*  42 */     this.alreadyRunThisTick = new ArrayList<>();
/*     */ 
/*     */     
/*  45 */     this.toRunThisTickSet = (Set<ScheduledTick<?>>)new ObjectOpenCustomHashSet(ScheduledTick.UNIQUE_TICK_HASH);
/*     */     
/*  47 */     this.chunkScheduleUpdater = (($$0, $$1) -> {
/*     */         if ($$1.equals($$0.peek())) {
/*     */           updateContainerScheduling($$1);
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */     
/*  55 */     this.tickCheck = $$0;
/*  56 */     this.profiler = $$1;
/*     */   }
/*     */   private final List<ScheduledTick<T>> alreadyRunThisTick; private final Set<ScheduledTick<?>> toRunThisTickSet; private final BiConsumer<LevelChunkTicks<T>, ScheduledTick<T>> chunkScheduleUpdater;
/*     */   
/*     */   public void addContainer(ChunkPos $$0, LevelChunkTicks<T> $$1) {
/*  61 */     long $$2 = $$0.toLong();
/*  62 */     this.allContainers.put($$2, $$1);
/*  63 */     ScheduledTick<T> $$3 = $$1.peek();
/*  64 */     if ($$3 != null) {
/*  65 */       this.nextTickForContainer.put($$2, $$3.triggerTick());
/*     */     }
/*     */     
/*  68 */     $$1.setOnTickAdded(this.chunkScheduleUpdater);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeContainer(ChunkPos $$0) {
/*  73 */     long $$1 = $$0.toLong();
/*  74 */     LevelChunkTicks<T> $$2 = (LevelChunkTicks<T>)this.allContainers.remove($$1);
/*  75 */     this.nextTickForContainer.remove($$1);
/*  76 */     if ($$2 != null) {
/*  77 */       $$2.setOnTickAdded(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void schedule(ScheduledTick<T> $$0) {
/*  83 */     long $$1 = ChunkPos.asLong($$0.pos());
/*  84 */     LevelChunkTicks<T> $$2 = (LevelChunkTicks<T>)this.allContainers.get($$1);
/*  85 */     if ($$2 == null) {
/*  86 */       Util.pauseInIde(new IllegalStateException("Trying to schedule tick in not loaded position " + $$0.pos()));
/*     */       return;
/*     */     } 
/*  89 */     $$2.schedule($$0);
/*     */   }
/*     */   
/*     */   public void tick(long $$0, int $$1, BiConsumer<BlockPos, T> $$2) {
/*  93 */     ProfilerFiller $$3 = this.profiler.get();
/*  94 */     $$3.push("collect");
/*  95 */     collectTicks($$0, $$1, $$3);
/*  96 */     $$3.popPush("run");
/*  97 */     $$3.incrementCounter("ticksToRun", this.toRunThisTick.size());
/*  98 */     runCollectedTicks($$2);
/*  99 */     $$3.popPush("cleanup");
/* 100 */     cleanupAfterTick();
/* 101 */     $$3.pop();
/*     */   }
/*     */   
/*     */   private void collectTicks(long $$0, int $$1, ProfilerFiller $$2) {
/* 105 */     sortContainersToTick($$0);
/* 106 */     $$2.incrementCounter("containersToTick", this.containersToTick.size());
/* 107 */     drainContainers($$0, $$1);
/* 108 */     rescheduleLeftoverContainers();
/*     */   }
/*     */   
/*     */   private void sortContainersToTick(long $$0) {
/* 112 */     ObjectIterator<Long2LongMap.Entry> $$1 = Long2LongMaps.fastIterator(this.nextTickForContainer);
/* 113 */     while ($$1.hasNext()) {
/* 114 */       Long2LongMap.Entry $$2 = (Long2LongMap.Entry)$$1.next();
/* 115 */       long $$3 = $$2.getLongKey();
/* 116 */       long $$4 = $$2.getLongValue();
/* 117 */       if ($$4 <= $$0) {
/* 118 */         LevelChunkTicks<T> $$5 = (LevelChunkTicks<T>)this.allContainers.get($$3);
/* 119 */         if ($$5 == null) {
/*     */           
/* 121 */           $$1.remove(); continue;
/*     */         } 
/* 123 */         ScheduledTick<T> $$6 = $$5.peek();
/* 124 */         if ($$6 == null) {
/*     */           
/* 126 */           $$1.remove(); continue;
/* 127 */         }  if ($$6.triggerTick() > $$0) {
/*     */           
/* 129 */           $$2.setValue($$6.triggerTick()); continue;
/* 130 */         }  if (this.tickCheck.test($$3)) {
/*     */           
/* 132 */           $$1.remove();
/* 133 */           this.containersToTick.add($$5);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void drainContainers(long $$0, int $$1) {
/*     */     LevelChunkTicks<T> $$2;
/* 143 */     while (canScheduleMoreTicks($$1) && ($$2 = this.containersToTick.poll()) != null) {
/* 144 */       ScheduledTick<T> $$3 = $$2.poll();
/*     */       
/* 146 */       scheduleForThisTick($$3);
/*     */ 
/*     */       
/* 149 */       drainFromCurrentContainer(this.containersToTick, $$2, $$0, $$1);
/* 150 */       ScheduledTick<T> $$4 = $$2.peek();
/* 151 */       if ($$4 != null) {
/* 152 */         if ($$4.triggerTick() <= $$0 && canScheduleMoreTicks($$1)) {
/*     */           
/* 154 */           this.containersToTick.add($$2);
/*     */           continue;
/*     */         } 
/* 157 */         updateContainerScheduling($$4);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void rescheduleLeftoverContainers() {
/* 165 */     for (LevelChunkTicks<T> $$0 : this.containersToTick)
/*     */     {
/* 167 */       updateContainerScheduling($$0.peek());
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateContainerScheduling(ScheduledTick<T> $$0) {
/* 172 */     this.nextTickForContainer.put(ChunkPos.asLong($$0.pos()), $$0.triggerTick());
/*     */   }
/*     */   
/*     */   private void drainFromCurrentContainer(Queue<LevelChunkTicks<T>> $$0, LevelChunkTicks<T> $$1, long $$2, int $$3) {
/* 176 */     if (!canScheduleMoreTicks($$3)) {
/*     */       return;
/*     */     }
/*     */     
/* 180 */     LevelChunkTicks<T> $$4 = $$0.peek();
/* 181 */     ScheduledTick<T> $$5 = ($$4 != null) ? $$4.peek() : null;
/*     */     
/* 183 */     while (canScheduleMoreTicks($$3)) {
/* 184 */       ScheduledTick<T> $$6 = $$1.peek();
/* 185 */       if ($$6 == null || $$6.triggerTick() > $$2) {
/*     */         break;
/*     */       }
/*     */       
/* 189 */       if ($$5 != null && ScheduledTick.INTRA_TICK_DRAIN_ORDER.compare($$6, $$5) > 0) {
/*     */         break;
/*     */       }
/*     */       
/* 193 */       $$1.poll();
/* 194 */       scheduleForThisTick($$6);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void scheduleForThisTick(ScheduledTick<T> $$0) {
/* 199 */     this.toRunThisTick.add($$0);
/*     */   }
/*     */   
/*     */   private boolean canScheduleMoreTicks(int $$0) {
/* 203 */     return (this.toRunThisTick.size() < $$0);
/*     */   }
/*     */   
/*     */   private void runCollectedTicks(BiConsumer<BlockPos, T> $$0) {
/* 207 */     while (!this.toRunThisTick.isEmpty()) {
/*     */ 
/*     */       
/* 210 */       ScheduledTick<T> $$1 = this.toRunThisTick.poll();
/* 211 */       if (!this.toRunThisTickSet.isEmpty()) {
/* 212 */         this.toRunThisTickSet.remove($$1);
/*     */       }
/* 214 */       this.alreadyRunThisTick.add($$1);
/* 215 */       $$0.accept($$1.pos(), $$1.type());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void cleanupAfterTick() {
/* 220 */     this.toRunThisTick.clear();
/* 221 */     this.containersToTick.clear();
/* 222 */     this.alreadyRunThisTick.clear();
/* 223 */     this.toRunThisTickSet.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasScheduledTick(BlockPos $$0, T $$1) {
/* 230 */     LevelChunkTicks<T> $$2 = (LevelChunkTicks<T>)this.allContainers.get(ChunkPos.asLong($$0));
/* 231 */     return ($$2 != null && $$2.hasScheduledTick($$0, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean willTickThisTick(BlockPos $$0, T $$1) {
/* 237 */     calculateTickSetIfNeeded();
/* 238 */     return this.toRunThisTickSet.contains(ScheduledTick.probe($$1, $$0));
/*     */   }
/*     */   
/*     */   private void calculateTickSetIfNeeded() {
/* 242 */     if (this.toRunThisTickSet.isEmpty() && !this.toRunThisTick.isEmpty()) {
/* 243 */       this.toRunThisTickSet.addAll(this.toRunThisTick);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void forContainersInArea(BoundingBox $$0, PosAndContainerConsumer<T> $$1) {
/* 253 */     int $$2 = SectionPos.posToSectionCoord($$0.minX());
/* 254 */     int $$3 = SectionPos.posToSectionCoord($$0.minZ());
/*     */     
/* 256 */     int $$4 = SectionPos.posToSectionCoord($$0.maxX());
/* 257 */     int $$5 = SectionPos.posToSectionCoord($$0.maxZ());
/*     */     
/* 259 */     for (int $$6 = $$2; $$6 <= $$4; $$6++) {
/* 260 */       for (int $$7 = $$3; $$7 <= $$5; $$7++) {
/* 261 */         long $$8 = ChunkPos.asLong($$6, $$7);
/* 262 */         LevelChunkTicks<T> $$9 = (LevelChunkTicks<T>)this.allContainers.get($$8);
/* 263 */         if ($$9 != null) {
/* 264 */           $$1.accept($$8, $$9);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearArea(BoundingBox $$0) {
/* 271 */     Predicate<ScheduledTick<T>> $$1 = $$1 -> $$0.isInside((Vec3i)$$1.pos());
/* 272 */     forContainersInArea($$0, ($$1, $$2) -> {
/*     */           ScheduledTick<T> $$3 = $$2.peek();
/*     */           
/*     */           $$2.removeIf($$0);
/*     */           ScheduledTick<T> $$4 = $$2.peek();
/*     */           if ($$4 != $$3) {
/*     */             if ($$4 != null) {
/*     */               updateContainerScheduling($$4);
/*     */             } else {
/*     */               this.nextTickForContainer.remove($$1);
/*     */             } 
/*     */           }
/*     */         });
/* 285 */     this.alreadyRunThisTick.removeIf($$1);
/* 286 */     this.toRunThisTick.removeIf($$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyArea(BoundingBox $$0, Vec3i $$1) {
/* 297 */     copyAreaFrom(this, $$0, $$1);
/*     */   }
/*     */   
/*     */   public void copyAreaFrom(LevelTicks<T> $$0, BoundingBox $$1, Vec3i $$2) {
/* 301 */     List<ScheduledTick<T>> $$3 = new ArrayList<>();
/*     */     
/* 303 */     Predicate<ScheduledTick<T>> $$4 = $$1 -> $$0.isInside((Vec3i)$$1.pos());
/*     */     
/* 305 */     Objects.requireNonNull($$3); $$0.alreadyRunThisTick.stream().filter($$4).forEach($$3::add);
/* 306 */     Objects.requireNonNull($$3); $$0.toRunThisTick.stream().filter($$4).forEach($$3::add);
/*     */     
/* 308 */     $$0.forContainersInArea($$1, ($$2, $$3) -> {
/*     */           Objects.requireNonNull($$1); $$3.getAll().filter($$0).forEach($$1::add);
/* 310 */         }); LongSummaryStatistics $$5 = $$3.stream().mapToLong(ScheduledTick::subTickOrder).summaryStatistics();
/* 311 */     long $$6 = $$5.getMin();
/* 312 */     long $$7 = $$5.getMax();
/*     */     
/* 314 */     $$3.forEach($$3 -> schedule(new ScheduledTick<>($$3.type(), $$3.pos().offset($$0), $$3.triggerTick(), $$3.priority(), $$3.subTickOrder() - $$1 + $$2 + 1L)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int count() {
/* 319 */     return this.allContainers.values().stream().mapToInt(TickAccess::count).sum();
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface PosAndContainerConsumer<T> {
/*     */     void accept(long param1Long, LevelChunkTicks<T> param1LevelChunkTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\LevelTicks.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */