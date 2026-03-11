/*    */ package net.minecraft.util.profiling.jfr.stats;
/*    */ public final class TimedStatSummary<T extends TimedStat> extends Record {
/*    */   private final T fastest;
/*    */   private final T slowest;
/*    */   @Nullable
/*    */   private final T secondSlowest;
/*    */   private final int count;
/*    */   private final Map<Integer, Double> percentilesNanos;
/*    */   private final Duration totalDuration;
/*    */   
/* 11 */   public TimedStatSummary(T $$0, T $$1, @Nullable T $$2, int $$3, Map<Integer, Double> $$4, Duration $$5) { this.fastest = $$0; this.slowest = $$1; this.secondSlowest = $$2; this.count = $$3; this.percentilesNanos = $$4; this.totalDuration = $$5; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 11 */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary<TT;>; } public T fastest() { return this.fastest; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 11 */     //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/TimedStatSummary<TT;>; } public T slowest() { return this.slowest; } @Nullable public T secondSlowest() { return this.secondSlowest; } public int count() { return this.count; } public Map<Integer, Double> percentilesNanos() { return this.percentilesNanos; } public Duration totalDuration() { return this.totalDuration; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T extends TimedStat> TimedStatSummary<T> summary(List<T> $$0) {
/* 20 */     if ($$0.isEmpty()) {
/* 21 */       throw new IllegalArgumentException("No values");
/*    */     }
/* 23 */     List<T> $$1 = $$0.stream().sorted(Comparator.comparing(TimedStat::duration)).toList();
/* 24 */     Duration $$2 = $$1.stream().map(TimedStat::duration).reduce(Duration::plus).orElse(Duration.ZERO);
/* 25 */     TimedStat timedStat1 = (TimedStat)$$1.get(0);
/* 26 */     TimedStat timedStat2 = (TimedStat)$$1.get($$1.size() - 1);
/* 27 */     TimedStat timedStat3 = ($$1.size() > 1) ? (TimedStat)$$1.get($$1.size() - 2) : null;
/* 28 */     int $$6 = $$1.size();
/* 29 */     Map<Integer, Double> $$7 = Percentiles.evaluate($$1.stream().mapToLong($$0 -> $$0.duration().toNanos()).toArray());
/* 30 */     return new TimedStatSummary<>((T)timedStat1, (T)timedStat2, (T)timedStat3, $$6, $$7, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\stats\TimedStatSummary.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */