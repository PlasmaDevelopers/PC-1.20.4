/*    */ package net.minecraft.util.profiling.metrics;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class AggregatedMetricSampler
/*    */   extends MetricSampler
/*    */ {
/*    */   private final List<MetricSampler> delegates;
/*    */   
/*    */   AggregatedMetricSampler(String $$0, List<MetricSampler> $$1) {
/* 50 */     super($$0, ((MetricSampler)$$1.get(0)).getCategory(), () -> averageValueFromDelegates($$0), () -> beforeTick($$0), thresholdTest($$1));
/* 51 */     this.delegates = $$1;
/*    */   }
/*    */   
/*    */   private static MetricSampler.ThresholdTest thresholdTest(List<MetricSampler> $$0) {
/* 55 */     return $$1 -> $$0.stream().anyMatch(());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void beforeTick(List<MetricSampler> $$0) {
/* 64 */     for (MetricSampler $$1 : $$0) {
/* 65 */       $$1.onStartTick();
/*    */     }
/*    */   }
/*    */   
/*    */   private static double averageValueFromDelegates(List<MetricSampler> $$0) {
/* 70 */     double $$1 = 0.0D;
/*    */     
/* 72 */     for (MetricSampler $$2 : $$0) {
/* 73 */       $$1 += $$2.getSampler().getAsDouble();
/*    */     }
/*    */     
/* 76 */     return $$1 / $$0.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(@Nullable Object $$0) {
/* 81 */     if (this == $$0) {
/* 82 */       return true;
/*    */     }
/* 84 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 85 */       return false;
/*    */     }
/* 87 */     if (!super.equals($$0)) {
/* 88 */       return false;
/*    */     }
/* 90 */     AggregatedMetricSampler $$1 = (AggregatedMetricSampler)$$0;
/* 91 */     return this.delegates.equals($$1.delegates);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 96 */     return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.delegates });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\metrics\MetricsRegistry$AggregatedMetricSampler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */