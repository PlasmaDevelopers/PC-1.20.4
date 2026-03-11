/*     */ package net.minecraft.util.profiling.metrics;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleSupplier;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ import javax.annotation.Nullable;
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
/*     */ 
/*     */ 
/*     */ public class MetricSamplerBuilder<T>
/*     */ {
/*     */   private final String name;
/*     */   private final MetricCategory category;
/*     */   private final DoubleSupplier sampler;
/*     */   private final T context;
/*     */   @Nullable
/*     */   private Runnable beforeTick;
/*     */   @Nullable
/*     */   private MetricSampler.ThresholdTest thresholdTest;
/*     */   
/*     */   public MetricSamplerBuilder(String $$0, MetricCategory $$1, ToDoubleFunction<T> $$2, T $$3) {
/* 199 */     this.name = $$0;
/* 200 */     this.category = $$1;
/* 201 */     this.sampler = (() -> $$0.applyAsDouble($$1));
/* 202 */     this.context = $$3;
/*     */   }
/*     */   
/*     */   public MetricSamplerBuilder<T> withBeforeTick(Consumer<T> $$0) {
/* 206 */     this.beforeTick = (() -> $$0.accept(this.context));
/* 207 */     return this;
/*     */   }
/*     */   
/*     */   public MetricSamplerBuilder<T> withThresholdAlert(MetricSampler.ThresholdTest $$0) {
/* 211 */     this.thresholdTest = $$0;
/* 212 */     return this;
/*     */   }
/*     */   
/*     */   public MetricSampler build() {
/* 216 */     return new MetricSampler(this.name, this.category, this.sampler, this.beforeTick, this.thresholdTest);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\metrics\MetricSampler$MetricSamplerBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */