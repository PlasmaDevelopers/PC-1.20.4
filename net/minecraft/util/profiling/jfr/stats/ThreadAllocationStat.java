/*    */ package net.minecraft.util.profiling.jfr.stats;
/*    */ 
/*    */ import java.time.Instant;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import jdk.jfr.consumer.RecordedEvent;
/*    */ 
/*    */ public final class ThreadAllocationStat extends Record {
/*    */   private final Instant timestamp;
/*    */   private final String threadName;
/*    */   private final long totalBytes;
/*    */   private static final String UNKNOWN_THREAD = "unknown";
/*    */   
/* 14 */   public ThreadAllocationStat(Instant $$0, String $$1, long $$2) { this.timestamp = $$0; this.threadName = $$1; this.totalBytes = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/stats/ThreadAllocationStat;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 14 */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/ThreadAllocationStat; } public Instant timestamp() { return this.timestamp; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/stats/ThreadAllocationStat;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/ThreadAllocationStat; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/stats/ThreadAllocationStat;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/ThreadAllocationStat;
/* 14 */     //   0	8	1	$$0	Ljava/lang/Object; } public String threadName() { return this.threadName; } public long totalBytes() { return this.totalBytes; }
/*    */ 
/*    */   
/*    */   public static ThreadAllocationStat from(RecordedEvent $$0) {
/* 18 */     RecordedThread $$1 = $$0.getThread("thread");
/*    */ 
/*    */ 
/*    */     
/* 22 */     String $$2 = ($$1 == null) ? "unknown" : (String)MoreObjects.firstNonNull($$1.getJavaName(), "unknown");
/*    */     
/* 24 */     return new ThreadAllocationStat($$0.getStartTime(), $$2, $$0.getLong("allocated"));
/*    */   }
/*    */   
/*    */   public static Summary summary(List<ThreadAllocationStat> $$0) {
/* 28 */     Map<String, Double> $$1 = new TreeMap<>();
/* 29 */     Map<String, List<ThreadAllocationStat>> $$2 = (Map<String, List<ThreadAllocationStat>>)$$0.stream().collect(Collectors.groupingBy($$0 -> $$0.threadName));
/*    */     
/* 31 */     $$2.forEach(($$1, $$2) -> {
/*    */           if ($$2.size() < 2) {
/*    */             return;
/*    */           }
/*    */           
/*    */           ThreadAllocationStat $$3 = $$2.get(0);
/*    */           
/*    */           ThreadAllocationStat $$4 = $$2.get($$2.size() - 1);
/*    */           
/*    */           long $$5 = Duration.between($$3.timestamp, $$4.timestamp).getSeconds();
/*    */           long $$6 = $$4.totalBytes - $$3.totalBytes;
/*    */           $$0.put($$1, Double.valueOf($$6 / $$5));
/*    */         });
/* 44 */     return new Summary($$1);
/*    */   }
/*    */   public static final class Summary extends Record { private final Map<String, Double> allocationsPerSecondByThread;
/* 47 */     public Summary(Map<String, Double> $$0) { this.allocationsPerSecondByThread = $$0; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/stats/ThreadAllocationStat$Summary;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #47	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/ThreadAllocationStat$Summary; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/stats/ThreadAllocationStat$Summary;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #47	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/ThreadAllocationStat$Summary; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/stats/ThreadAllocationStat$Summary;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #47	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/ThreadAllocationStat$Summary;
/* 47 */       //   0	8	1	$$0	Ljava/lang/Object; } public Map<String, Double> allocationsPerSecondByThread() { return this.allocationsPerSecondByThread; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\stats\ThreadAllocationStat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */