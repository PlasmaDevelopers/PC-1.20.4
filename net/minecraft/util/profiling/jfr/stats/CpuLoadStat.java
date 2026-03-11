/*    */ package net.minecraft.util.profiling.jfr.stats;public final class CpuLoadStat extends Record { private final double jvm;
/*    */   private final double userJvm;
/*    */   private final double system;
/*    */   
/*  5 */   public CpuLoadStat(double $$0, double $$1, double $$2) { this.jvm = $$0; this.userJvm = $$1; this.system = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/stats/CpuLoadStat;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  5 */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/CpuLoadStat; } public double jvm() { return this.jvm; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/stats/CpuLoadStat;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/CpuLoadStat; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/stats/CpuLoadStat;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/CpuLoadStat;
/*  5 */     //   0	8	1	$$0	Ljava/lang/Object; } public double userJvm() { return this.userJvm; } public double system() { return this.system; }
/*    */   
/*    */   public static CpuLoadStat from(RecordedEvent $$0) {
/*  8 */     return new CpuLoadStat($$0.getFloat("jvmSystem"), $$0
/*  9 */         .getFloat("jvmUser"), $$0
/* 10 */         .getFloat("machineTotal"));
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\stats\CpuLoadStat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */