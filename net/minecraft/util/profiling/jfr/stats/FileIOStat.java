/*    */ package net.minecraft.util.profiling.jfr.stats;
/*    */ 
/*    */ import java.time.Duration;
/*    */ 
/*    */ public final class FileIOStat extends Record {
/*    */   private final Duration duration;
/*    */   @Nullable
/*    */   private final String path;
/*    */   private final long bytes;
/*    */   
/* 11 */   public FileIOStat(Duration $$0, @Nullable String $$1, long $$2) { this.duration = $$0; this.path = $$1; this.bytes = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/stats/FileIOStat;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/FileIOStat; } public Duration duration() { return this.duration; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/stats/FileIOStat;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/FileIOStat; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/stats/FileIOStat;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/FileIOStat;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } @Nullable public String path() { return this.path; } public long bytes() { return this.bytes; }
/*    */   
/*    */   public static Summary summary(Duration $$0, List<FileIOStat> $$1) {
/* 14 */     long $$2 = $$1.stream().mapToLong($$0 -> $$0.bytes).sum();
/* 15 */     return new Summary($$2, $$2 / $$0
/*    */         
/* 17 */         .getSeconds(), $$1
/* 18 */         .size(), $$1
/* 19 */         .size() / $$0.getSeconds(), $$1
/* 20 */         .stream().map(FileIOStat::duration).reduce(Duration.ZERO, Duration::plus), ((Map)$$1
/* 21 */         .stream().filter($$0 -> ($$0.path != null)).collect(Collectors.groupingBy($$0 -> $$0.path, Collectors.summingLong($$0 -> $$0.bytes))))
/* 22 */         .entrySet().stream()
/* 23 */         .sorted(Map.Entry.comparingByValue().reversed())
/* 24 */         .map($$0 -> Pair.of($$0.getKey(), $$0.getValue()))
/* 25 */         .limit(10L)
/* 26 */         .toList());
/*    */   }
/*    */   public static final class Summary extends Record { private final long totalBytes; private final double bytesPerSecond; private final long counts; private final double countsPerSecond; private final Duration timeSpentInIO; private final List<Pair<String, Long>> topTenContributorsByTotalBytes;
/*    */     
/* 30 */     public Summary(long $$0, double $$1, long $$2, double $$3, Duration $$4, List<Pair<String, Long>> $$5) { this.totalBytes = $$0; this.bytesPerSecond = $$1; this.counts = $$2; this.countsPerSecond = $$3; this.timeSpentInIO = $$4; this.topTenContributorsByTotalBytes = $$5; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/FileIOStat$Summary;
/* 30 */       //   0	8	1	$$0	Ljava/lang/Object; } public long totalBytes() { return this.totalBytes; } public double bytesPerSecond() { return this.bytesPerSecond; } public long counts() { return this.counts; } public double countsPerSecond() { return this.countsPerSecond; } public Duration timeSpentInIO() { return this.timeSpentInIO; } public List<Pair<String, Long>> topTenContributorsByTotalBytes() { return this.topTenContributorsByTotalBytes; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\stats\FileIOStat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */