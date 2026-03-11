/*    */ package net.minecraft.util.profiling;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.profiling.metrics.MetricCategory;
/*    */ import org.apache.commons.lang3.tuple.Pair;
/*    */ 
/*    */ public class InactiveProfiler
/*    */   implements ProfileCollector {
/* 12 */   public static final InactiveProfiler INSTANCE = new InactiveProfiler();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startTick() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void endTick() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void push(String $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void push(Supplier<String> $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void markForCharting(MetricCategory $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void pop() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void popPush(String $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void popPush(Supplier<String> $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void incrementCounter(String $$0, int $$1) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void incrementCounter(Supplier<String> $$0, int $$1) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public ProfileResults getResults() {
/* 59 */     return EmptyProfileResults.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ActiveProfiler.PathEntry getEntry(String $$0) {
/* 65 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Pair<String, MetricCategory>> getChartedPaths() {
/* 70 */     return (Set<Pair<String, MetricCategory>>)ImmutableSet.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\InactiveProfiler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */