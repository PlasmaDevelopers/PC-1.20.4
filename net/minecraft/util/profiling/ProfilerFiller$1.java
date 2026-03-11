/*     */ package net.minecraft.util.profiling;
/*     */ 
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.util.profiling.metrics.MetricCategory;
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
/*     */ class null
/*     */   implements ProfilerFiller
/*     */ {
/*     */   public void startTick() {
/*  48 */     first.startTick();
/*  49 */     second.startTick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endTick() {
/*  54 */     first.endTick();
/*  55 */     second.endTick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(String $$0) {
/*  60 */     first.push($$0);
/*  61 */     second.push($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(Supplier<String> $$0) {
/*  66 */     first.push($$0);
/*  67 */     second.push($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void markForCharting(MetricCategory $$0) {
/*  72 */     first.markForCharting($$0);
/*  73 */     second.markForCharting($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void pop() {
/*  78 */     first.pop();
/*  79 */     second.pop();
/*     */   }
/*     */ 
/*     */   
/*     */   public void popPush(String $$0) {
/*  84 */     first.popPush($$0);
/*  85 */     second.popPush($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void popPush(Supplier<String> $$0) {
/*  90 */     first.popPush($$0);
/*  91 */     second.popPush($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void incrementCounter(String $$0, int $$1) {
/*  96 */     first.incrementCounter($$0, $$1);
/*  97 */     second.incrementCounter($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void incrementCounter(Supplier<String> $$0, int $$1) {
/* 102 */     first.incrementCounter($$0, $$1);
/* 103 */     second.incrementCounter($$0, $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\ProfilerFiller$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */