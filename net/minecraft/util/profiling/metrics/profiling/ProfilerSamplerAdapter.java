/*    */ package net.minecraft.util.profiling.metrics.profiling;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*    */ import java.util.Set;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.util.TimeUtil;
/*    */ import net.minecraft.util.profiling.ActiveProfiler;
/*    */ import net.minecraft.util.profiling.ProfileCollector;
/*    */ import net.minecraft.util.profiling.metrics.MetricCategory;
/*    */ import net.minecraft.util.profiling.metrics.MetricSampler;
/*    */ import org.apache.commons.lang3.tuple.Pair;
/*    */ 
/*    */ public class ProfilerSamplerAdapter {
/* 15 */   private final Set<String> previouslyFoundSamplerNames = (Set<String>)new ObjectOpenHashSet();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Set<MetricSampler> newSamplersFoundInProfiler(Supplier<ProfileCollector> $$0) {
/* 21 */     Set<MetricSampler> $$1 = (Set<MetricSampler>)((ProfileCollector)$$0.get()).getChartedPaths().stream().filter($$0 -> !this.previouslyFoundSamplerNames.contains($$0.getLeft())).map($$1 -> samplerForProfilingPath($$0, (String)$$1.getLeft(), (MetricCategory)$$1.getRight())).collect(Collectors.toSet());
/*    */     
/* 23 */     for (MetricSampler $$2 : $$1) {
/* 24 */       this.previouslyFoundSamplerNames.add($$2.getName());
/*    */     }
/*    */     
/* 27 */     return $$1;
/*    */   }
/*    */   
/*    */   private static MetricSampler samplerForProfilingPath(Supplier<ProfileCollector> $$0, String $$1, MetricCategory $$2) {
/* 31 */     return MetricSampler.create($$1, $$2, () -> {
/*    */           ActiveProfiler.PathEntry $$2 = ((ProfileCollector)$$0.get()).getEntry($$1);
/*    */           return ($$2 == null) ? 0.0D : ($$2.getMaxDuration() / TimeUtil.NANOSECONDS_PER_MILLISECOND);
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\metrics\profiling\ProfilerSamplerAdapter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */