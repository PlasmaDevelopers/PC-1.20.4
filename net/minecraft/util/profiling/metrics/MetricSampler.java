/*     */ package net.minecraft.util.profiling.metrics;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.DoubleSupplier;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetricSampler
/*     */ {
/*     */   private final String name;
/*     */   private final MetricCategory category;
/*     */   private final DoubleSupplier sampler;
/*     */   private final ByteBuf ticks;
/*     */   private final ByteBuf values;
/*     */   private volatile boolean isRunning;
/*     */   @Nullable
/*     */   private final Runnable beforeTick;
/*     */   @Nullable
/*     */   final ThresholdTest thresholdTest;
/*     */   private double currentValue;
/*     */   
/*     */   protected MetricSampler(String $$0, MetricCategory $$1, DoubleSupplier $$2, @Nullable Runnable $$3, @Nullable ThresholdTest $$4) {
/*  31 */     this.name = $$0;
/*  32 */     this.category = $$1;
/*  33 */     this.beforeTick = $$3;
/*  34 */     this.sampler = $$2;
/*  35 */     this.thresholdTest = $$4;
/*  36 */     this.values = ByteBufAllocator.DEFAULT.buffer();
/*  37 */     this.ticks = ByteBufAllocator.DEFAULT.buffer();
/*  38 */     this.isRunning = true;
/*     */   }
/*     */   
/*     */   public static MetricSampler create(String $$0, MetricCategory $$1, DoubleSupplier $$2) {
/*  42 */     return new MetricSampler($$0, $$1, $$2, null, null);
/*     */   }
/*     */   
/*     */   public static <T> MetricSampler create(String $$0, MetricCategory $$1, T $$2, ToDoubleFunction<T> $$3) {
/*  46 */     return builder($$0, $$1, $$3, $$2).build();
/*     */   }
/*     */   
/*     */   public static <T> MetricSamplerBuilder<T> builder(String $$0, MetricCategory $$1, ToDoubleFunction<T> $$2, T $$3) {
/*  50 */     return new MetricSamplerBuilder<>($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public void onStartTick() {
/*  54 */     if (!this.isRunning) {
/*  55 */       throw new IllegalStateException("Not running");
/*     */     }
/*  57 */     if (this.beforeTick != null) {
/*  58 */       this.beforeTick.run();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEndTick(int $$0) {
/*  68 */     verifyRunning();
/*  69 */     this.currentValue = this.sampler.getAsDouble();
/*  70 */     this.values.writeDouble(this.currentValue);
/*  71 */     this.ticks.writeInt($$0);
/*     */   }
/*     */   
/*     */   public void onFinished() {
/*  75 */     verifyRunning();
/*  76 */     this.values.release();
/*  77 */     this.ticks.release();
/*  78 */     this.isRunning = false;
/*     */   }
/*     */   
/*     */   private void verifyRunning() {
/*  82 */     if (!this.isRunning) {
/*  83 */       throw new IllegalStateException(String.format(Locale.ROOT, "Sampler for metric %s not started!", new Object[] { this.name }));
/*     */     }
/*     */   }
/*     */   
/*     */   DoubleSupplier getSampler() {
/*  88 */     return this.sampler;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  92 */     return this.name;
/*     */   }
/*     */   
/*     */   public MetricCategory getCategory() {
/*  96 */     return this.category;
/*     */   }
/*     */   
/*     */   public SamplerResult result() {
/* 100 */     Int2DoubleOpenHashMap int2DoubleOpenHashMap = new Int2DoubleOpenHashMap();
/* 101 */     int $$1 = Integer.MIN_VALUE;
/* 102 */     int $$2 = Integer.MIN_VALUE;
/*     */     
/* 104 */     while (this.values.isReadable(8)) {
/* 105 */       int $$3 = this.ticks.readInt();
/* 106 */       if ($$1 == Integer.MIN_VALUE) {
/* 107 */         $$1 = $$3;
/*     */       }
/* 109 */       int2DoubleOpenHashMap.put($$3, this.values.readDouble());
/* 110 */       $$2 = $$3;
/*     */     } 
/* 112 */     return new SamplerResult($$1, $$2, (Int2DoubleMap)int2DoubleOpenHashMap);
/*     */   }
/*     */   
/*     */   public boolean triggersThreshold() {
/* 116 */     return (this.thresholdTest != null && this.thresholdTest.test(this.currentValue));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 121 */     if (this == $$0) {
/* 122 */       return true;
/*     */     }
/* 124 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 125 */       return false;
/*     */     }
/* 127 */     MetricSampler $$1 = (MetricSampler)$$0;
/* 128 */     return (this.name.equals($$1.name) && this.category.equals($$1.category));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 133 */     return this.name.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SamplerResult
/*     */   {
/*     */     private final Int2DoubleMap recording;
/*     */     
/*     */     private final int firstTick;
/*     */     
/*     */     private final int lastTick;
/*     */     
/*     */     public SamplerResult(int $$0, int $$1, Int2DoubleMap $$2) {
/* 146 */       this.firstTick = $$0;
/* 147 */       this.lastTick = $$1;
/* 148 */       this.recording = $$2;
/*     */     }
/*     */     
/*     */     public double valueAtTick(int $$0) {
/* 152 */       return this.recording.get($$0);
/*     */     }
/*     */     
/*     */     public int getFirstTick() {
/* 156 */       return this.firstTick;
/*     */     }
/*     */     
/*     */     public int getLastTick() {
/* 160 */       return this.lastTick;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ValueIncreasedByPercentage implements ThresholdTest {
/*     */     private final float percentageIncreaseThreshold;
/* 166 */     private double previousValue = Double.MIN_VALUE;
/*     */     
/*     */     public ValueIncreasedByPercentage(float $$0) {
/* 169 */       this.percentageIncreaseThreshold = $$0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean test(double $$0) {
/*     */       boolean $$2;
/* 176 */       if (this.previousValue == Double.MIN_VALUE || $$0 <= this.previousValue) {
/* 177 */         boolean $$1 = false;
/*     */       } else {
/* 179 */         $$2 = (($$0 - this.previousValue) / this.previousValue >= this.percentageIncreaseThreshold);
/*     */       } 
/*     */       
/* 182 */       this.previousValue = $$0;
/* 183 */       return $$2;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MetricSamplerBuilder<T>
/*     */   {
/*     */     private final String name;
/*     */     private final MetricCategory category;
/*     */     private final DoubleSupplier sampler;
/*     */     private final T context;
/*     */     @Nullable
/*     */     private Runnable beforeTick;
/*     */     @Nullable
/*     */     private MetricSampler.ThresholdTest thresholdTest;
/*     */     
/*     */     public MetricSamplerBuilder(String $$0, MetricCategory $$1, ToDoubleFunction<T> $$2, T $$3) {
/* 199 */       this.name = $$0;
/* 200 */       this.category = $$1;
/* 201 */       this.sampler = (() -> $$0.applyAsDouble($$1));
/* 202 */       this.context = $$3;
/*     */     }
/*     */     
/*     */     public MetricSamplerBuilder<T> withBeforeTick(Consumer<T> $$0) {
/* 206 */       this.beforeTick = (() -> $$0.accept(this.context));
/* 207 */       return this;
/*     */     }
/*     */     
/*     */     public MetricSamplerBuilder<T> withThresholdAlert(MetricSampler.ThresholdTest $$0) {
/* 211 */       this.thresholdTest = $$0;
/* 212 */       return this;
/*     */     }
/*     */     
/*     */     public MetricSampler build() {
/* 216 */       return new MetricSampler(this.name, this.category, this.sampler, this.beforeTick, this.thresholdTest);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface ThresholdTest {
/*     */     boolean test(double param1Double);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\metrics\MetricSampler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */