/*    */ package net.minecraft.util.profiling.jfr.stats;
/*    */ public final class TickTimeStat extends Record { private final Instant timestamp; private final Duration currentAverage;
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/stats/TickTimeStat;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/TickTimeStat;
/*    */   }
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/stats/TickTimeStat;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/TickTimeStat;
/*    */   }
/*  9 */   public TickTimeStat(Instant $$0, Duration $$1) { this.timestamp = $$0; this.currentAverage = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/stats/TickTimeStat;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/TickTimeStat;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public Instant timestamp() { return this.timestamp; } public Duration currentAverage() { return this.currentAverage; }
/*    */    public static TickTimeStat from(RecordedEvent $$0) {
/* 11 */     return new TickTimeStat($$0.getStartTime(), $$0.getDuration("averageTickDuration"));
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\stats\TickTimeStat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */