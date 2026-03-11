/*     */ package net.minecraft.util.profiling;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.longs.LongArrayList;
/*     */ import it.unimi.dsi.fastutil.longs.LongList;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongMaps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArraySet;
/*     */ import java.time.Duration;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.IntSupplier;
/*     */ import java.util.function.LongSupplier;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.profiling.metrics.MetricCategory;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ActiveProfiler
/*     */   implements ProfileCollector {
/*  27 */   private static final long WARNING_TIME_NANOS = Duration.ofMillis(100L).toNanos();
/*  28 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  30 */   private final List<String> paths = Lists.newArrayList();
/*  31 */   private final LongList startTimes = (LongList)new LongArrayList();
/*  32 */   private final Map<String, PathEntry> entries = Maps.newHashMap();
/*     */   private final IntSupplier getTickTime;
/*     */   private final LongSupplier getRealTime;
/*     */   private final long startTimeNano;
/*     */   private final int startTimeTicks;
/*  37 */   private String path = "";
/*     */   
/*     */   private boolean started;
/*     */   
/*     */   @Nullable
/*     */   private PathEntry currentEntry;
/*     */   private final boolean warn;
/*  44 */   private final Set<Pair<String, MetricCategory>> chartedPaths = (Set<Pair<String, MetricCategory>>)new ObjectArraySet();
/*     */   
/*     */   public ActiveProfiler(LongSupplier $$0, IntSupplier $$1, boolean $$2) {
/*  47 */     this.startTimeNano = $$0.getAsLong();
/*  48 */     this.getRealTime = $$0;
/*  49 */     this.startTimeTicks = $$1.getAsInt();
/*  50 */     this.getTickTime = $$1;
/*  51 */     this.warn = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void startTick() {
/*  56 */     if (this.started) {
/*  57 */       LOGGER.error("Profiler tick already started - missing endTick()?");
/*     */       
/*     */       return;
/*     */     } 
/*  61 */     this.started = true;
/*  62 */     this.path = "";
/*  63 */     this.paths.clear();
/*  64 */     push("root");
/*     */   }
/*     */ 
/*     */   
/*     */   public void endTick() {
/*  69 */     if (!this.started) {
/*  70 */       LOGGER.error("Profiler tick already ended - missing startTick()?");
/*     */       
/*     */       return;
/*     */     } 
/*  74 */     pop();
/*  75 */     this.started = false;
/*     */     
/*  77 */     if (!this.path.isEmpty()) {
/*  78 */       LOGGER.error("Profiler tick ended before path was fully popped (remainder: '{}'). Mismatched push/pop?", LogUtils.defer(() -> ProfileResults.demanglePath(this.path)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(String $$0) {
/*  84 */     if (!this.started) {
/*  85 */       LOGGER.error("Cannot push '{}' to profiler if profiler tick hasn't started - missing startTick()?", $$0);
/*     */       
/*     */       return;
/*     */     } 
/*  89 */     if (!this.path.isEmpty()) {
/*  90 */       this.path += "\036";
/*     */     }
/*  92 */     this.path += this.path;
/*  93 */     this.paths.add(this.path);
/*  94 */     this.startTimes.add(Util.getNanos());
/*  95 */     this.currentEntry = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(Supplier<String> $$0) {
/* 100 */     push($$0.get());
/*     */   }
/*     */ 
/*     */   
/*     */   public void markForCharting(MetricCategory $$0) {
/* 105 */     this.chartedPaths.add(Pair.of(this.path, $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void pop() {
/* 110 */     if (!this.started) {
/* 111 */       LOGGER.error("Cannot pop from profiler if profiler tick hasn't started - missing startTick()?");
/*     */       return;
/*     */     } 
/* 114 */     if (this.startTimes.isEmpty()) {
/* 115 */       LOGGER.error("Tried to pop one too many times! Mismatched push() and pop()?");
/*     */       return;
/*     */     } 
/* 118 */     long $$0 = Util.getNanos();
/* 119 */     long $$1 = this.startTimes.removeLong(this.startTimes.size() - 1);
/* 120 */     this.paths.remove(this.paths.size() - 1);
/* 121 */     long $$2 = $$0 - $$1;
/*     */     
/* 123 */     PathEntry $$3 = getCurrentEntry();
/* 124 */     $$3.accumulatedDuration += $$2;
/* 125 */     $$3.count++;
/* 126 */     $$3.maxDuration = Math.max($$3.maxDuration, $$2);
/* 127 */     $$3.minDuration = Math.min($$3.minDuration, $$2);
/*     */     
/* 129 */     if (this.warn && $$2 > WARNING_TIME_NANOS) {
/* 130 */       LOGGER.warn("Something's taking too long! '{}' took aprox {} ms", LogUtils.defer(() -> ProfileResults.demanglePath(this.path)), LogUtils.defer(() -> Double.valueOf($$0 / 1000000.0D)));
/*     */     }
/*     */     
/* 133 */     this.path = this.paths.isEmpty() ? "" : this.paths.get(this.paths.size() - 1);
/* 134 */     this.currentEntry = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void popPush(String $$0) {
/* 139 */     pop();
/* 140 */     push($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void popPush(Supplier<String> $$0) {
/* 145 */     pop();
/* 146 */     push($$0);
/*     */   }
/*     */   
/*     */   private PathEntry getCurrentEntry() {
/* 150 */     if (this.currentEntry == null) {
/* 151 */       this.currentEntry = this.entries.computeIfAbsent(this.path, $$0 -> new PathEntry());
/*     */     }
/*     */     
/* 154 */     return this.currentEntry;
/*     */   }
/*     */ 
/*     */   
/*     */   public void incrementCounter(String $$0, int $$1) {
/* 159 */     (getCurrentEntry()).counters.addTo($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void incrementCounter(Supplier<String> $$0, int $$1) {
/* 164 */     (getCurrentEntry()).counters.addTo($$0.get(), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ProfileResults getResults() {
/* 169 */     return new FilledProfileResults((Map)this.entries, this.startTimeNano, this.startTimeTicks, this.getRealTime.getAsLong(), this.getTickTime.getAsInt());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public PathEntry getEntry(String $$0) {
/* 175 */     return this.entries.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Pair<String, MetricCategory>> getChartedPaths() {
/* 180 */     return this.chartedPaths;
/*     */   }
/*     */   
/*     */   public static class PathEntry implements ProfilerPathEntry {
/* 184 */     long maxDuration = Long.MIN_VALUE;
/* 185 */     long minDuration = Long.MAX_VALUE;
/*     */     long accumulatedDuration;
/*     */     long count;
/* 188 */     final Object2LongOpenHashMap<String> counters = new Object2LongOpenHashMap();
/*     */ 
/*     */     
/*     */     public long getDuration() {
/* 192 */       return this.accumulatedDuration;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getMaxDuration() {
/* 197 */       return this.maxDuration;
/*     */     }
/*     */ 
/*     */     
/*     */     public long getCount() {
/* 202 */       return this.count;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object2LongMap<String> getCounters() {
/* 207 */       return Object2LongMaps.unmodifiable((Object2LongMap)this.counters);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\ActiveProfiler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */