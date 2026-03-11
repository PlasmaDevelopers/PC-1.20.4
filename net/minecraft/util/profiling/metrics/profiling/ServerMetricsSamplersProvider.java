/*     */ package net.minecraft.util.profiling.metrics.profiling;
/*     */ 
/*     */ import com.google.common.base.Stopwatch;
/*     */ import com.google.common.base.Ticker;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.LongSupplier;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ import java.util.stream.IntStream;
/*     */ import net.minecraft.util.profiling.ProfileCollector;
/*     */ import net.minecraft.util.profiling.metrics.MetricCategory;
/*     */ import net.minecraft.util.profiling.metrics.MetricSampler;
/*     */ import net.minecraft.util.profiling.metrics.MetricsRegistry;
/*     */ import net.minecraft.util.profiling.metrics.MetricsSamplerProvider;
/*     */ import org.slf4j.Logger;
/*     */ import oshi.SystemInfo;
/*     */ import oshi.hardware.CentralProcessor;
/*     */ 
/*     */ public class ServerMetricsSamplersProvider
/*     */   implements MetricsSamplerProvider {
/*  26 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  28 */   private final Set<MetricSampler> samplers = (Set<MetricSampler>)new ObjectOpenHashSet();
/*  29 */   private final ProfilerSamplerAdapter samplerFactory = new ProfilerSamplerAdapter();
/*     */   
/*     */   public ServerMetricsSamplersProvider(LongSupplier $$0, boolean $$1) {
/*  32 */     this.samplers.add(tickTimeSampler($$0));
/*     */     
/*  34 */     if ($$1) {
/*  35 */       this.samplers.addAll(runtimeIndependentSamplers());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Set<MetricSampler> runtimeIndependentSamplers() {
/*  44 */     ImmutableSet.Builder<MetricSampler> $$0 = ImmutableSet.builder();
/*     */     
/*     */     try {
/*  47 */       CpuStats $$1 = new CpuStats();
/*     */ 
/*     */       
/*  50 */       Objects.requireNonNull($$0); IntStream.range(0, $$1.nrOfCpus).mapToObj($$1 -> MetricSampler.create("cpu#" + $$1, MetricCategory.CPU, ())).forEach($$0::add);
/*  51 */     } catch (Throwable $$2) {
/*  52 */       LOGGER.warn("Failed to query cpu, no cpu stats will be recorded", $$2);
/*     */     } 
/*     */     
/*  55 */     $$0.add(MetricSampler.create("heap MiB", MetricCategory.JVM, () -> ((float)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576.0F)));
/*  56 */     $$0.addAll(MetricsRegistry.INSTANCE.getRegisteredSamplers());
/*  57 */     return (Set<MetricSampler>)$$0.build();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<MetricSampler> samplers(Supplier<ProfileCollector> $$0) {
/*  62 */     this.samplers.addAll(this.samplerFactory.newSamplersFoundInProfiler($$0));
/*  63 */     return this.samplers;
/*     */   }
/*     */   
/*     */   public static MetricSampler tickTimeSampler(final LongSupplier timeSource) {
/*  67 */     Stopwatch $$1 = Stopwatch.createUnstarted(new Ticker()
/*     */         {
/*     */           public long read() {
/*  70 */             return timeSource.getAsLong();
/*     */           }
/*     */         });
/*     */     
/*  74 */     ToDoubleFunction<Stopwatch> $$2 = $$0 -> {
/*     */         if ($$0.isRunning()) {
/*     */           $$0.stop();
/*     */         }
/*     */         
/*     */         long $$1 = $$0.elapsed(TimeUnit.NANOSECONDS);
/*     */         $$0.reset();
/*     */         return $$1;
/*     */       };
/*  83 */     MetricSampler.ValueIncreasedByPercentage $$3 = new MetricSampler.ValueIncreasedByPercentage(2.0F);
/*     */     
/*  85 */     return MetricSampler.builder("ticktime", MetricCategory.TICK_LOOP, $$2, $$1)
/*  86 */       .withBeforeTick(Stopwatch::start)
/*  87 */       .withThresholdAlert((MetricSampler.ThresholdTest)$$3)
/*  88 */       .build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class CpuStats
/*     */   {
/*  96 */     private final SystemInfo systemInfo = new SystemInfo();
/*  97 */     private final CentralProcessor processor = this.systemInfo.getHardware().getProcessor();
/*  98 */     public final int nrOfCpus = this.processor.getLogicalProcessorCount();
/*     */     
/* 100 */     private long[][] previousCpuLoadTick = this.processor.getProcessorCpuLoadTicks();
/* 101 */     private double[] currentLoad = this.processor.getProcessorCpuLoadBetweenTicks(this.previousCpuLoadTick);
/*     */     private long lastPollMs;
/*     */     
/*     */     public double loadForCpu(int $$0) {
/* 105 */       long $$1 = System.currentTimeMillis();
/* 106 */       if (this.lastPollMs == 0L || this.lastPollMs + 501L < $$1) {
/* 107 */         this.currentLoad = this.processor.getProcessorCpuLoadBetweenTicks(this.previousCpuLoadTick);
/* 108 */         this.previousCpuLoadTick = this.processor.getProcessorCpuLoadTicks();
/* 109 */         this.lastPollMs = $$1;
/*     */       } 
/*     */       
/* 112 */       return this.currentLoad[$$0] * 100.0D;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\metrics\profiling\ServerMetricsSamplersProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */