/*    */ package net.minecraft.util.profiling.jfr.stats;
/*    */ 
/*    */ import java.time.Duration;
/*    */ import java.util.List;
/*    */ 
/*    */ public final class GcHeapStat extends Record {
/*    */   private final Instant timestamp;
/*    */   private final long heapUsed;
/*    */   private final Timing timing;
/*    */   
/* 11 */   public GcHeapStat(Instant $$0, long $$1, Timing $$2) { this.timestamp = $$0; this.heapUsed = $$1; this.timing = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat; } public Instant timestamp() { return this.timestamp; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public long heapUsed() { return this.heapUsed; } public Timing timing() { return this.timing; }
/*    */   
/*    */   public static GcHeapStat from(RecordedEvent $$0) {
/* 14 */     return new GcHeapStat($$0.getStartTime(), $$0
/* 15 */         .getLong("heapUsed"), 
/* 16 */         $$0.getString("when").equalsIgnoreCase("before gc") ? 
/* 17 */         Timing.BEFORE_GC : 
/* 18 */         Timing.AFTER_GC);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Summary summary(Duration $$0, List<GcHeapStat> $$1, Duration $$2, int $$3) {
/* 23 */     return new Summary($$0, $$2, $$3, 
/*    */ 
/*    */ 
/*    */         
/* 27 */         calculateAllocationRatePerSecond($$1));
/*    */   }
/*    */ 
/*    */   
/*    */   private static double calculateAllocationRatePerSecond(List<GcHeapStat> $$0) {
/* 32 */     long $$1 = 0L;
/* 33 */     Map<Timing, List<GcHeapStat>> $$2 = (Map<Timing, List<GcHeapStat>>)$$0.stream().collect(Collectors.groupingBy($$0 -> $$0.timing));
/* 34 */     List<GcHeapStat> $$3 = $$2.get(Timing.BEFORE_GC);
/* 35 */     List<GcHeapStat> $$4 = $$2.get(Timing.AFTER_GC);
/*    */     
/* 37 */     for (int $$5 = 1; $$5 < $$3.size(); $$5++) {
/* 38 */       GcHeapStat $$6 = $$3.get($$5);
/* 39 */       GcHeapStat $$7 = $$4.get($$5 - 1);
/* 40 */       $$1 += $$6.heapUsed - $$7.heapUsed;
/*    */     } 
/*    */     
/* 43 */     Duration $$8 = Duration.between(((GcHeapStat)$$0.get(1)).timestamp, ((GcHeapStat)$$0.get($$0.size() - 1)).timestamp);
/*    */     
/* 45 */     return $$1 / $$8.getSeconds();
/*    */   }
/*    */   public static final class Summary extends Record { private final Duration duration; private final Duration gcTotalDuration; private final int totalGCs; private final double allocationRateBytesPerSecond;
/* 48 */     public Summary(Duration $$0, Duration $$1, int $$2, double $$3) { this.duration = $$0; this.gcTotalDuration = $$1; this.totalGCs = $$2; this.allocationRateBytesPerSecond = $$3; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #48	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #48	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #48	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/GcHeapStat$Summary;
/* 48 */       //   0	8	1	$$0	Ljava/lang/Object; } public Duration duration() { return this.duration; } public Duration gcTotalDuration() { return this.gcTotalDuration; } public int totalGCs() { return this.totalGCs; } public double allocationRateBytesPerSecond() { return this.allocationRateBytesPerSecond; }
/*    */      public float gcOverHead() {
/* 50 */       return (float)this.gcTotalDuration.toMillis() / (float)this.duration.toMillis();
/*    */     } }
/*    */ 
/*    */   
/*    */   enum Timing {
/* 55 */     BEFORE_GC, AFTER_GC;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\stats\GcHeapStat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */