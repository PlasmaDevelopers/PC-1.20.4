/*    */ package net.minecraft.util.profiling;
/*    */ 
/*    */ import java.util.function.IntSupplier;
/*    */ import java.util.function.LongSupplier;
/*    */ 
/*    */ public class ContinuousProfiler {
/*    */   private final LongSupplier realTime;
/*    */   private final IntSupplier tickCount;
/*  9 */   private ProfileCollector profiler = InactiveProfiler.INSTANCE;
/*    */   
/*    */   public ContinuousProfiler(LongSupplier $$0, IntSupplier $$1) {
/* 12 */     this.realTime = $$0;
/* 13 */     this.tickCount = $$1;
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 17 */     return (this.profiler != InactiveProfiler.INSTANCE);
/*    */   }
/*    */   
/*    */   public void disable() {
/* 21 */     this.profiler = InactiveProfiler.INSTANCE;
/*    */   }
/*    */   
/*    */   public void enable() {
/* 25 */     this.profiler = new ActiveProfiler(this.realTime, this.tickCount, true);
/*    */   }
/*    */   
/*    */   public ProfilerFiller getFiller() {
/* 29 */     return this.profiler;
/*    */   }
/*    */   
/*    */   public ProfileResults getResults() {
/* 33 */     return this.profiler.getResults();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\ContinuousProfiler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */