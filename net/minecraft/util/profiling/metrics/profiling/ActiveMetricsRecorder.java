/*     */ package net.minecraft.util.profiling.metrics.profiling;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.nio.file.Path;
/*     */ import java.time.Instant;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.LongSupplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.profiling.ActiveProfiler;
/*     */ import net.minecraft.util.profiling.ContinuousProfiler;
/*     */ import net.minecraft.util.profiling.EmptyProfileResults;
/*     */ import net.minecraft.util.profiling.InactiveProfiler;
/*     */ import net.minecraft.util.profiling.ProfileCollector;
/*     */ import net.minecraft.util.profiling.ProfileResults;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.util.profiling.metrics.MetricSampler;
/*     */ import net.minecraft.util.profiling.metrics.MetricsSamplerProvider;
/*     */ import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
/*     */ import net.minecraft.util.profiling.metrics.storage.RecordedDeviation;
/*     */ 
/*     */ public class ActiveMetricsRecorder
/*     */   implements MetricsRecorder {
/*     */   public static final int PROFILING_MAX_DURATION_SECONDS = 10;
/*     */   @Nullable
/*  34 */   private static Consumer<Path> globalOnReportFinished = null;
/*     */   
/*  36 */   private final Map<MetricSampler, List<RecordedDeviation>> deviationsBySampler = (Map<MetricSampler, List<RecordedDeviation>>)new Object2ObjectOpenHashMap();
/*     */   
/*     */   private final ContinuousProfiler taskProfiler;
/*     */   
/*     */   private final Executor ioExecutor;
/*     */   private final MetricsPersister metricsPersister;
/*     */   private final Consumer<ProfileResults> onProfilingEnd;
/*     */   private final Consumer<Path> onReportFinished;
/*     */   private final MetricsSamplerProvider metricsSamplerProvider;
/*     */   private final LongSupplier wallTimeSource;
/*     */   private final long deadlineNano;
/*     */   private int currentTick;
/*     */   private ProfileCollector singleTickProfiler;
/*     */   private volatile boolean killSwitch;
/*  50 */   private Set<MetricSampler> thisTickSamplers = (Set<MetricSampler>)ImmutableSet.of();
/*     */   
/*     */   private ActiveMetricsRecorder(MetricsSamplerProvider $$0, LongSupplier $$1, Executor $$2, MetricsPersister $$3, Consumer<ProfileResults> $$4, Consumer<Path> $$5) {
/*  53 */     this.metricsSamplerProvider = $$0;
/*  54 */     this.wallTimeSource = $$1;
/*  55 */     this.taskProfiler = new ContinuousProfiler($$1, () -> this.currentTick);
/*  56 */     this.ioExecutor = $$2;
/*  57 */     this.metricsPersister = $$3;
/*  58 */     this.onProfilingEnd = $$4;
/*  59 */     this.onReportFinished = (globalOnReportFinished == null) ? $$5 : $$5.andThen(globalOnReportFinished);
/*  60 */     this.deadlineNano = $$1.getAsLong() + TimeUnit.NANOSECONDS.convert(10L, TimeUnit.SECONDS);
/*  61 */     this.singleTickProfiler = (ProfileCollector)new ActiveProfiler(this.wallTimeSource, () -> this.currentTick, false);
/*  62 */     this.taskProfiler.enable();
/*     */   }
/*     */   
/*     */   public static ActiveMetricsRecorder createStarted(MetricsSamplerProvider $$0, LongSupplier $$1, Executor $$2, MetricsPersister $$3, Consumer<ProfileResults> $$4, Consumer<Path> $$5) {
/*  66 */     return new ActiveMetricsRecorder($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void end() {
/*  71 */     if (!isRecording()) {
/*     */       return;
/*     */     }
/*  74 */     this.killSwitch = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void cancel() {
/*  79 */     if (!isRecording()) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     this.singleTickProfiler = (ProfileCollector)InactiveProfiler.INSTANCE;
/*  84 */     this.onProfilingEnd.accept(EmptyProfileResults.EMPTY);
/*     */     
/*  86 */     cleanup(this.thisTickSamplers);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startTick() {
/*  91 */     verifyStarted();
/*  92 */     this.thisTickSamplers = this.metricsSamplerProvider.samplers(() -> this.singleTickProfiler);
/*  93 */     for (MetricSampler $$0 : this.thisTickSamplers) {
/*  94 */       $$0.onStartTick();
/*     */     }
/*  96 */     this.currentTick++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void endTick() {
/* 101 */     verifyStarted();
/* 102 */     if (this.currentTick == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 107 */     for (MetricSampler $$0 : this.thisTickSamplers) {
/* 108 */       $$0.onEndTick(this.currentTick);
/* 109 */       if ($$0.triggersThreshold()) {
/* 110 */         RecordedDeviation $$1 = new RecordedDeviation(Instant.now(), this.currentTick, this.singleTickProfiler.getResults());
/* 111 */         ((List<RecordedDeviation>)this.deviationsBySampler.computeIfAbsent($$0, $$0 -> Lists.newArrayList())).add($$1);
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     if (this.killSwitch || this.wallTimeSource.getAsLong() > this.deadlineNano) {
/* 116 */       this.killSwitch = false;
/* 117 */       ProfileResults $$2 = this.taskProfiler.getResults();
/* 118 */       this.singleTickProfiler = (ProfileCollector)InactiveProfiler.INSTANCE;
/* 119 */       this.onProfilingEnd.accept($$2);
/* 120 */       scheduleSaveResults($$2);
/*     */       
/*     */       return;
/*     */     } 
/* 124 */     this.singleTickProfiler = (ProfileCollector)new ActiveProfiler(this.wallTimeSource, () -> this.currentTick, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRecording() {
/* 129 */     return this.taskProfiler.isEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public ProfilerFiller getProfiler() {
/* 134 */     return ProfilerFiller.tee(this.taskProfiler.getFiller(), (ProfilerFiller)this.singleTickProfiler);
/*     */   }
/*     */   
/*     */   private void verifyStarted() {
/* 138 */     if (!isRecording()) {
/* 139 */       throw new IllegalStateException("Not started!");
/*     */     }
/*     */   }
/*     */   
/*     */   private void scheduleSaveResults(ProfileResults $$0) {
/* 144 */     HashSet<MetricSampler> $$1 = new HashSet<>(this.thisTickSamplers);
/* 145 */     this.ioExecutor.execute(() -> {
/*     */           Path $$2 = this.metricsPersister.saveReports($$0, this.deviationsBySampler, $$1);
/*     */           cleanup($$0);
/*     */           this.onReportFinished.accept($$2);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void cleanup(Collection<MetricSampler> $$0) {
/* 154 */     for (MetricSampler $$1 : $$0) {
/* 155 */       $$1.onFinished();
/*     */     }
/*     */     
/* 158 */     this.deviationsBySampler.clear();
/* 159 */     this.taskProfiler.disable();
/*     */   }
/*     */   
/*     */   public static void registerGlobalCompletionCallback(Consumer<Path> $$0) {
/* 163 */     globalOnReportFinished = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\metrics\profiling\ActiveMetricsRecorder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */