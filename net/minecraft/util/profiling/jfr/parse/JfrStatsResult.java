/*    */ package net.minecraft.util.profiling.jfr.parse;
/*    */ 
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.time.Duration;
/*    */ import java.time.Instant;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.profiling.jfr.stats.ChunkGenStat;
/*    */ import net.minecraft.util.profiling.jfr.stats.FileIOStat;
/*    */ import net.minecraft.util.profiling.jfr.stats.NetworkPacketSummary;
/*    */ import net.minecraft.util.profiling.jfr.stats.TimedStatSummary;
/*    */ import net.minecraft.world.level.chunk.ChunkStatus;
/*    */ 
/*    */ public final class JfrStatsResult extends Record {
/*    */   private final Instant recordingStarted;
/*    */   private final Instant recordingEnded;
/*    */   private final Duration recordingDuration;
/*    */   @Nullable
/*    */   private final Duration worldCreationDuration;
/*    */   private final List<TickTimeStat> tickTimes;
/*    */   private final List<CpuLoadStat> cpuLoadStats;
/*    */   
/* 23 */   public JfrStatsResult(Instant $$0, Instant $$1, Duration $$2, @Nullable Duration $$3, List<TickTimeStat> $$4, List<CpuLoadStat> $$5, GcHeapStat.Summary $$6, ThreadAllocationStat.Summary $$7, NetworkPacketSummary $$8, NetworkPacketSummary $$9, FileIOStat.Summary $$10, FileIOStat.Summary $$11, List<ChunkGenStat> $$12) { this.recordingStarted = $$0; this.recordingEnded = $$1; this.recordingDuration = $$2; this.worldCreationDuration = $$3; this.tickTimes = $$4; this.cpuLoadStats = $$5; this.heapSummary = $$6; this.threadAllocationSummary = $$7; this.receivedPacketsSummary = $$8; this.sentPacketsSummary = $$9; this.fileWrites = $$10; this.fileReads = $$11; this.chunkGenStats = $$12; } private final GcHeapStat.Summary heapSummary; private final ThreadAllocationStat.Summary threadAllocationSummary; private final NetworkPacketSummary receivedPacketsSummary; private final NetworkPacketSummary sentPacketsSummary; private final FileIOStat.Summary fileWrites; private final FileIOStat.Summary fileReads; private final List<ChunkGenStat> chunkGenStats; public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/parse/JfrStatsResult;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #23	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/parse/JfrStatsResult; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/parse/JfrStatsResult;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #23	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/parse/JfrStatsResult; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/parse/JfrStatsResult;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #23	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/parse/JfrStatsResult;
/* 23 */     //   0	8	1	$$0	Ljava/lang/Object; } public Instant recordingStarted() { return this.recordingStarted; } public Instant recordingEnded() { return this.recordingEnded; } public Duration recordingDuration() { return this.recordingDuration; } @Nullable public Duration worldCreationDuration() { return this.worldCreationDuration; } public List<TickTimeStat> tickTimes() { return this.tickTimes; } public List<CpuLoadStat> cpuLoadStats() { return this.cpuLoadStats; } public GcHeapStat.Summary heapSummary() { return this.heapSummary; } public ThreadAllocationStat.Summary threadAllocationSummary() { return this.threadAllocationSummary; } public NetworkPacketSummary receivedPacketsSummary() { return this.receivedPacketsSummary; } public NetworkPacketSummary sentPacketsSummary() { return this.sentPacketsSummary; } public FileIOStat.Summary fileWrites() { return this.fileWrites; } public FileIOStat.Summary fileReads() { return this.fileReads; } public List<ChunkGenStat> chunkGenStats() { return this.chunkGenStats; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Pair<ChunkStatus, TimedStatSummary<ChunkGenStat>>> chunkGenSummary() {
/* 39 */     Map<ChunkStatus, List<ChunkGenStat>> $$0 = (Map<ChunkStatus, List<ChunkGenStat>>)this.chunkGenStats.stream().collect(Collectors.groupingBy(ChunkGenStat::status));
/* 40 */     return $$0.entrySet().stream()
/* 41 */       .map($$0 -> Pair.of($$0.getKey(), TimedStatSummary.summary((List)$$0.getValue())))
/* 42 */       .sorted(Comparator.<Pair<ChunkStatus, TimedStatSummary<ChunkGenStat>>, Comparable>comparing($$0 -> ((TimedStatSummary)$$0.getSecond()).totalDuration()).reversed())
/* 43 */       .toList();
/*    */   }
/*    */   
/*    */   public String asJson() {
/* 47 */     return (new JfrResultJsonSerializer()).format(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\parse\JfrStatsResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */