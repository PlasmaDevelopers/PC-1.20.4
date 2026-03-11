/*     */ package net.minecraft.util.profiling;
/*     */ 
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.util.profiling.metrics.MetricCategory;
/*     */ 
/*     */ public interface ProfilerFiller
/*     */ {
/*     */   public static final String ROOT = "root";
/*     */   
/*     */   void startTick();
/*     */   
/*     */   void endTick();
/*     */   
/*     */   void push(String paramString);
/*     */   
/*     */   void push(Supplier<String> paramSupplier);
/*     */   
/*     */   void pop();
/*     */   
/*     */   void popPush(String paramString);
/*     */   
/*     */   void popPush(Supplier<String> paramSupplier);
/*     */   
/*     */   void markForCharting(MetricCategory paramMetricCategory);
/*     */   
/*     */   default void incrementCounter(String $$0) {
/*  27 */     incrementCounter($$0, 1);
/*     */   }
/*     */   
/*     */   void incrementCounter(String paramString, int paramInt);
/*     */   
/*     */   default void incrementCounter(Supplier<String> $$0) {
/*  33 */     incrementCounter($$0, 1);
/*     */   }
/*     */   
/*     */   void incrementCounter(Supplier<String> paramSupplier, int paramInt);
/*     */   
/*     */   static ProfilerFiller tee(final ProfilerFiller first, final ProfilerFiller second) {
/*  39 */     if (first == InactiveProfiler.INSTANCE) {
/*  40 */       return second;
/*     */     }
/*  42 */     if (second == InactiveProfiler.INSTANCE) {
/*  43 */       return first;
/*     */     }
/*  45 */     return new ProfilerFiller()
/*     */       {
/*     */         public void startTick() {
/*  48 */           first.startTick();
/*  49 */           second.startTick();
/*     */         }
/*     */ 
/*     */         
/*     */         public void endTick() {
/*  54 */           first.endTick();
/*  55 */           second.endTick();
/*     */         }
/*     */ 
/*     */         
/*     */         public void push(String $$0) {
/*  60 */           first.push($$0);
/*  61 */           second.push($$0);
/*     */         }
/*     */ 
/*     */         
/*     */         public void push(Supplier<String> $$0) {
/*  66 */           first.push($$0);
/*  67 */           second.push($$0);
/*     */         }
/*     */ 
/*     */         
/*     */         public void markForCharting(MetricCategory $$0) {
/*  72 */           first.markForCharting($$0);
/*  73 */           second.markForCharting($$0);
/*     */         }
/*     */ 
/*     */         
/*     */         public void pop() {
/*  78 */           first.pop();
/*  79 */           second.pop();
/*     */         }
/*     */ 
/*     */         
/*     */         public void popPush(String $$0) {
/*  84 */           first.popPush($$0);
/*  85 */           second.popPush($$0);
/*     */         }
/*     */ 
/*     */         
/*     */         public void popPush(Supplier<String> $$0) {
/*  90 */           first.popPush($$0);
/*  91 */           second.popPush($$0);
/*     */         }
/*     */ 
/*     */         
/*     */         public void incrementCounter(String $$0, int $$1) {
/*  96 */           first.incrementCounter($$0, $$1);
/*  97 */           second.incrementCounter($$0, $$1);
/*     */         }
/*     */ 
/*     */         
/*     */         public void incrementCounter(Supplier<String> $$0, int $$1) {
/* 102 */           first.incrementCounter($$0, $$1);
/* 103 */           second.incrementCounter($$0, $$1);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\ProfilerFiller.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */