/*     */ package net.minecraft.util.profiling.metrics.profiling;
/*     */ 
/*     */ import oshi.SystemInfo;
/*     */ import oshi.hardware.CentralProcessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CpuStats
/*     */ {
/*  96 */   private final SystemInfo systemInfo = new SystemInfo();
/*  97 */   private final CentralProcessor processor = this.systemInfo.getHardware().getProcessor();
/*  98 */   public final int nrOfCpus = this.processor.getLogicalProcessorCount();
/*     */   
/* 100 */   private long[][] previousCpuLoadTick = this.processor.getProcessorCpuLoadTicks();
/* 101 */   private double[] currentLoad = this.processor.getProcessorCpuLoadBetweenTicks(this.previousCpuLoadTick);
/*     */   private long lastPollMs;
/*     */   
/*     */   public double loadForCpu(int $$0) {
/* 105 */     long $$1 = System.currentTimeMillis();
/* 106 */     if (this.lastPollMs == 0L || this.lastPollMs + 501L < $$1) {
/* 107 */       this.currentLoad = this.processor.getProcessorCpuLoadBetweenTicks(this.previousCpuLoadTick);
/* 108 */       this.previousCpuLoadTick = this.processor.getProcessorCpuLoadTicks();
/* 109 */       this.lastPollMs = $$1;
/*     */     } 
/*     */     
/* 112 */     return this.currentLoad[$$0] * 100.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\metrics\profiling\ServerMetricsSamplersProvider$CpuStats.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */