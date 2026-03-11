/*     */ package net.minecraft.util.profiling.jfr.serialize;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonNull;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.LongSerializationPolicy;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.time.Duration;
/*     */ import java.util.DoubleSummaryStatistics;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ import java.util.stream.DoubleStream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.profiling.jfr.Percentiles;
/*     */ import net.minecraft.util.profiling.jfr.parse.JfrStatsResult;
/*     */ import net.minecraft.util.profiling.jfr.stats.ChunkGenStat;
/*     */ import net.minecraft.util.profiling.jfr.stats.CpuLoadStat;
/*     */ import net.minecraft.util.profiling.jfr.stats.FileIOStat;
/*     */ import net.minecraft.util.profiling.jfr.stats.GcHeapStat;
/*     */ import net.minecraft.util.profiling.jfr.stats.NetworkPacketSummary;
/*     */ import net.minecraft.util.profiling.jfr.stats.ThreadAllocationStat;
/*     */ import net.minecraft.util.profiling.jfr.stats.TickTimeStat;
/*     */ import net.minecraft.util.profiling.jfr.stats.TimedStatSummary;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ 
/*     */ public class JfrResultJsonSerializer
/*     */ {
/*     */   private static final String BYTES_PER_SECOND = "bytesPerSecond";
/*     */   private static final String COUNT = "count";
/*     */   private static final String DURATION_NANOS_TOTAL = "durationNanosTotal";
/*     */   private static final String TOTAL_BYTES = "totalBytes";
/*     */   private static final String COUNT_PER_SECOND = "countPerSecond";
/*  40 */   final Gson gson = (new GsonBuilder())
/*  41 */     .setPrettyPrinting()
/*  42 */     .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
/*  43 */     .create();
/*     */   
/*     */   public String format(JfrStatsResult $$0) {
/*  46 */     JsonObject $$1 = new JsonObject();
/*     */     
/*  48 */     $$1.addProperty("startedEpoch", Long.valueOf($$0.recordingStarted().toEpochMilli()));
/*  49 */     $$1.addProperty("endedEpoch", Long.valueOf($$0.recordingEnded().toEpochMilli()));
/*  50 */     $$1.addProperty("durationMs", Long.valueOf($$0.recordingDuration().toMillis()));
/*  51 */     Duration $$2 = $$0.worldCreationDuration();
/*  52 */     if ($$2 != null) {
/*  53 */       $$1.addProperty("worldGenDurationMs", Long.valueOf($$2.toMillis()));
/*     */     }
/*  55 */     $$1.add("heap", heap($$0.heapSummary()));
/*  56 */     $$1.add("cpuPercent", cpu($$0.cpuLoadStats()));
/*  57 */     $$1.add("network", network($$0));
/*  58 */     $$1.add("fileIO", fileIO($$0));
/*  59 */     $$1.add("serverTick", serverTicks($$0.tickTimes()));
/*  60 */     $$1.add("threadAllocation", threadAllocations($$0.threadAllocationSummary()));
/*  61 */     $$1.add("chunkGen", chunkGen($$0.chunkGenSummary()));
/*     */     
/*  63 */     return this.gson.toJson((JsonElement)$$1);
/*     */   }
/*     */   
/*     */   private JsonElement heap(GcHeapStat.Summary $$0) {
/*  67 */     JsonObject $$1 = new JsonObject();
/*  68 */     $$1.addProperty("allocationRateBytesPerSecond", Double.valueOf($$0.allocationRateBytesPerSecond()));
/*  69 */     $$1.addProperty("gcCount", Integer.valueOf($$0.totalGCs()));
/*  70 */     $$1.addProperty("gcOverHeadPercent", Float.valueOf($$0.gcOverHead()));
/*  71 */     $$1.addProperty("gcTotalDurationMs", Long.valueOf($$0.gcTotalDuration().toMillis()));
/*  72 */     return (JsonElement)$$1;
/*     */   }
/*     */   
/*     */   private JsonElement chunkGen(List<Pair<ChunkStatus, TimedStatSummary<ChunkGenStat>>> $$0) {
/*  76 */     JsonObject $$1 = new JsonObject();
/*  77 */     $$1.addProperty("durationNanosTotal", Double.valueOf($$0.stream().mapToDouble($$0 -> ((TimedStatSummary)$$0.getSecond()).totalDuration().toNanos()).sum()));
/*  78 */     JsonArray $$2 = (JsonArray)Util.make(new JsonArray(), $$1 -> $$0.add("status", (JsonElement)$$1));
/*     */     
/*  80 */     for (Pair<ChunkStatus, TimedStatSummary<ChunkGenStat>> $$3 : $$0) {
/*  81 */       TimedStatSummary<ChunkGenStat> $$4 = (TimedStatSummary<ChunkGenStat>)$$3.getSecond();
/*  82 */       Objects.requireNonNull($$2); JsonObject $$5 = (JsonObject)Util.make(new JsonObject(), $$2::add);
/*  83 */       $$5.addProperty("state", ((ChunkStatus)$$3.getFirst()).toString());
/*  84 */       $$5.addProperty("count", Integer.valueOf($$4.count()));
/*  85 */       $$5.addProperty("durationNanosTotal", Long.valueOf($$4.totalDuration().toNanos()));
/*  86 */       $$5.addProperty("durationNanosAvg", Long.valueOf($$4.totalDuration().toNanos() / $$4.count()));
/*  87 */       JsonObject $$6 = (JsonObject)Util.make(new JsonObject(), $$1 -> $$0.add("durationNanosPercentiles", (JsonElement)$$1));
/*  88 */       $$4.percentilesNanos().forEach(($$1, $$2) -> $$0.addProperty("p" + $$1, $$2));
/*     */       
/*  90 */       Function<ChunkGenStat, JsonElement> $$7 = $$0 -> {
/*     */           JsonObject $$1 = new JsonObject();
/*     */           $$1.addProperty("durationNanos", Long.valueOf($$0.duration().toNanos()));
/*     */           $$1.addProperty("level", $$0.level());
/*     */           $$1.addProperty("chunkPosX", Integer.valueOf(($$0.chunkPos()).x));
/*     */           $$1.addProperty("chunkPosZ", Integer.valueOf(($$0.chunkPos()).z));
/*     */           $$1.addProperty("worldPosX", Integer.valueOf($$0.worldPos().x()));
/*     */           $$1.addProperty("worldPosZ", Integer.valueOf($$0.worldPos().z()));
/*     */           return (JsonElement)$$1;
/*     */         };
/* 100 */       $$5.add("fastest", $$7.apply((ChunkGenStat)$$4.fastest()));
/* 101 */       $$5.add("slowest", $$7.apply((ChunkGenStat)$$4.slowest()));
/* 102 */       $$5.add("secondSlowest", ($$4.secondSlowest() != null) ? 
/* 103 */           $$7.apply((ChunkGenStat)$$4.secondSlowest()) : 
/* 104 */           (JsonElement)JsonNull.INSTANCE);
/*     */     } 
/*     */     
/* 107 */     return (JsonElement)$$1;
/*     */   }
/*     */   
/*     */   private JsonElement threadAllocations(ThreadAllocationStat.Summary $$0) {
/* 111 */     JsonArray $$1 = new JsonArray();
/* 112 */     $$0.allocationsPerSecondByThread().forEach(($$1, $$2) -> $$0.add((JsonElement)Util.make(new JsonObject(), ())));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     return (JsonElement)$$1;
/*     */   }
/*     */   
/*     */   private JsonElement serverTicks(List<TickTimeStat> $$0) {
/* 122 */     if ($$0.isEmpty()) {
/* 123 */       return (JsonElement)JsonNull.INSTANCE;
/*     */     }
/* 125 */     JsonObject $$1 = new JsonObject();
/* 126 */     double[] $$2 = $$0.stream().mapToDouble($$0 -> $$0.currentAverage().toNanos() / 1000000.0D).toArray();
/*     */     
/* 128 */     DoubleSummaryStatistics $$3 = DoubleStream.of($$2).summaryStatistics();
/* 129 */     $$1.addProperty("minMs", Double.valueOf($$3.getMin()));
/* 130 */     $$1.addProperty("averageMs", Double.valueOf($$3.getAverage()));
/* 131 */     $$1.addProperty("maxMs", Double.valueOf($$3.getMax()));
/* 132 */     Map<Integer, Double> $$4 = Percentiles.evaluate($$2);
/* 133 */     $$4.forEach(($$1, $$2) -> $$0.addProperty("p" + $$1, $$2));
/* 134 */     return (JsonElement)$$1;
/*     */   }
/*     */   
/*     */   private JsonElement fileIO(JfrStatsResult $$0) {
/* 138 */     JsonObject $$1 = new JsonObject();
/* 139 */     $$1.add("write", fileIoSummary($$0.fileWrites()));
/* 140 */     $$1.add("read", fileIoSummary($$0.fileReads()));
/*     */     
/* 142 */     return (JsonElement)$$1;
/*     */   }
/*     */   
/*     */   private JsonElement fileIoSummary(FileIOStat.Summary $$0) {
/* 146 */     JsonObject $$1 = new JsonObject();
/* 147 */     $$1.addProperty("totalBytes", Long.valueOf($$0.totalBytes()));
/* 148 */     $$1.addProperty("count", Long.valueOf($$0.counts()));
/* 149 */     $$1.addProperty("bytesPerSecond", Double.valueOf($$0.bytesPerSecond()));
/* 150 */     $$1.addProperty("countPerSecond", Double.valueOf($$0.countsPerSecond()));
/* 151 */     JsonArray $$2 = new JsonArray();
/* 152 */     $$1.add("topContributors", (JsonElement)$$2);
/* 153 */     $$0.topTenContributorsByTotalBytes().forEach($$1 -> {
/*     */           JsonObject $$2 = new JsonObject();
/*     */           $$0.add((JsonElement)$$2);
/*     */           $$2.addProperty("path", (String)$$1.getFirst());
/*     */           $$2.addProperty("totalBytes", (Number)$$1.getSecond());
/*     */         });
/* 159 */     return (JsonElement)$$1;
/*     */   }
/*     */   
/*     */   private JsonElement network(JfrStatsResult $$0) {
/* 163 */     JsonObject $$1 = new JsonObject();
/* 164 */     $$1.add("sent", packets($$0.sentPacketsSummary()));
/* 165 */     $$1.add("received", packets($$0.receivedPacketsSummary()));
/* 166 */     return (JsonElement)$$1;
/*     */   }
/*     */   
/*     */   private JsonElement packets(NetworkPacketSummary $$0) {
/* 170 */     JsonObject $$1 = new JsonObject();
/* 171 */     $$1.addProperty("totalBytes", Long.valueOf($$0.getTotalSize()));
/* 172 */     $$1.addProperty("count", Long.valueOf($$0.getTotalCount()));
/* 173 */     $$1.addProperty("bytesPerSecond", Double.valueOf($$0.getSizePerSecond()));
/* 174 */     $$1.addProperty("countPerSecond", Double.valueOf($$0.getCountsPerSecond()));
/* 175 */     JsonArray $$2 = new JsonArray();
/* 176 */     $$1.add("topContributors", (JsonElement)$$2);
/* 177 */     $$0.largestSizeContributors().forEach($$1 -> {
/*     */           JsonObject $$2 = new JsonObject();
/*     */           $$0.add((JsonElement)$$2);
/*     */           NetworkPacketSummary.PacketIdentification $$3 = (NetworkPacketSummary.PacketIdentification)$$1.getFirst();
/*     */           NetworkPacketSummary.PacketCountAndSize $$4 = (NetworkPacketSummary.PacketCountAndSize)$$1.getSecond();
/*     */           $$2.addProperty("protocolId", $$3.protocolId());
/*     */           $$2.addProperty("packetId", Integer.valueOf($$3.packetId()));
/*     */           $$2.addProperty("packetName", $$3.packetName());
/*     */           $$2.addProperty("totalBytes", Long.valueOf($$4.totalSize()));
/*     */           $$2.addProperty("count", Long.valueOf($$4.totalCount()));
/*     */         });
/* 188 */     return (JsonElement)$$1;
/*     */   }
/*     */   
/*     */   private JsonElement cpu(List<CpuLoadStat> $$0) {
/* 192 */     JsonObject $$1 = new JsonObject();
/* 193 */     BiFunction<List<CpuLoadStat>, ToDoubleFunction<CpuLoadStat>, JsonObject> $$2 = ($$0, $$1) -> {
/*     */         JsonObject $$2 = new JsonObject();
/*     */         
/*     */         DoubleSummaryStatistics $$3 = $$0.stream().mapToDouble($$1).summaryStatistics();
/*     */         $$2.addProperty("min", Double.valueOf($$3.getMin()));
/*     */         $$2.addProperty("average", Double.valueOf($$3.getAverage()));
/*     */         $$2.addProperty("max", Double.valueOf($$3.getMax()));
/*     */         return $$2;
/*     */       };
/* 202 */     $$1.add("jvm", (JsonElement)$$2.apply($$0, CpuLoadStat::jvm));
/* 203 */     $$1.add("userJvm", (JsonElement)$$2.apply($$0, CpuLoadStat::userJvm));
/* 204 */     $$1.add("system", (JsonElement)$$2.apply($$0, CpuLoadStat::system));
/*     */     
/* 206 */     return (JsonElement)$$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\serialize\JfrResultJsonSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */