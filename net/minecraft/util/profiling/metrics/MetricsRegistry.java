/*    */ package net.minecraft.util.profiling.metrics;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.WeakHashMap;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class MetricsRegistry {
/* 11 */   public static final MetricsRegistry INSTANCE = new MetricsRegistry();
/*    */ 
/*    */   
/* 14 */   private final WeakHashMap<ProfilerMeasured, Void> measuredInstances = new WeakHashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(ProfilerMeasured $$0) {
/* 20 */     this.measuredInstances.put($$0, null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<MetricSampler> getRegisteredSamplers() {
/* 26 */     Map<String, List<MetricSampler>> $$0 = (Map<String, List<MetricSampler>>)this.measuredInstances.keySet().stream().flatMap($$0 -> $$0.profiledMetrics().stream()).collect(Collectors.groupingBy(MetricSampler::getName));
/*    */     
/* 28 */     return aggregateDuplicates($$0);
/*    */   }
/*    */   
/*    */   private static List<MetricSampler> aggregateDuplicates(Map<String, List<MetricSampler>> $$0) {
/* 32 */     return (List<MetricSampler>)$$0.entrySet().stream()
/* 33 */       .map($$0 -> {
/*    */           String $$1 = (String)$$0.getKey();
/*    */           
/*    */           List<MetricSampler> $$2 = (List<MetricSampler>)$$0.getValue();
/*    */           return ($$2.size() > 1) ? new AggregatedMetricSampler($$1, $$2) : $$2.get(0);
/* 38 */         }).collect(Collectors.toList());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static class AggregatedMetricSampler
/*    */     extends MetricSampler
/*    */   {
/*    */     private final List<MetricSampler> delegates;
/*    */ 
/*    */     
/*    */     AggregatedMetricSampler(String $$0, List<MetricSampler> $$1) {
/* 50 */       super($$0, ((MetricSampler)$$1.get(0)).getCategory(), () -> averageValueFromDelegates($$0), () -> beforeTick($$0), thresholdTest($$1));
/* 51 */       this.delegates = $$1;
/*    */     }
/*    */     
/*    */     private static MetricSampler.ThresholdTest thresholdTest(List<MetricSampler> $$0) {
/* 55 */       return $$1 -> $$0.stream().anyMatch(());
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private static void beforeTick(List<MetricSampler> $$0) {
/* 64 */       for (MetricSampler $$1 : $$0) {
/* 65 */         $$1.onStartTick();
/*    */       }
/*    */     }
/*    */     
/*    */     private static double averageValueFromDelegates(List<MetricSampler> $$0) {
/* 70 */       double $$1 = 0.0D;
/*    */       
/* 72 */       for (MetricSampler $$2 : $$0) {
/* 73 */         $$1 += $$2.getSampler().getAsDouble();
/*    */       }
/*    */       
/* 76 */       return $$1 / $$0.size();
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean equals(@Nullable Object $$0) {
/* 81 */       if (this == $$0) {
/* 82 */         return true;
/*    */       }
/* 84 */       if ($$0 == null || getClass() != $$0.getClass()) {
/* 85 */         return false;
/*    */       }
/* 87 */       if (!super.equals($$0)) {
/* 88 */         return false;
/*    */       }
/* 90 */       AggregatedMetricSampler $$1 = (AggregatedMetricSampler)$$0;
/* 91 */       return this.delegates.equals($$1.delegates);
/*    */     }
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 96 */       return Objects.hash(new Object[] { Integer.valueOf(super.hashCode()), this.delegates });
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\metrics\MetricsRegistry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */