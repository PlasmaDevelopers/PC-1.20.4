/*     */ package net.minecraft.util.profiling.jfr.parse;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.io.IOException;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.nio.file.Path;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Spliterators;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import javax.annotation.Nullable;
/*     */ import jdk.jfr.consumer.RecordedEvent;
/*     */ import jdk.jfr.consumer.RecordingFile;
/*     */ import net.minecraft.util.profiling.jfr.stats.ChunkGenStat;
/*     */ import net.minecraft.util.profiling.jfr.stats.CpuLoadStat;
/*     */ import net.minecraft.util.profiling.jfr.stats.FileIOStat;
/*     */ import net.minecraft.util.profiling.jfr.stats.GcHeapStat;
/*     */ import net.minecraft.util.profiling.jfr.stats.NetworkPacketSummary;
/*     */ import net.minecraft.util.profiling.jfr.stats.ThreadAllocationStat;
/*     */ import net.minecraft.util.profiling.jfr.stats.TickTimeStat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JfrStatsParser
/*     */ {
/*  38 */   private Instant recordingStarted = Instant.EPOCH;
/*  39 */   private Instant recordingEnded = Instant.EPOCH;
/*     */   
/*  41 */   private final List<ChunkGenStat> chunkGenStats = Lists.newArrayList();
/*  42 */   private final List<CpuLoadStat> cpuLoadStat = Lists.newArrayList();
/*  43 */   private final Map<NetworkPacketSummary.PacketIdentification, MutableCountAndSize> receivedPackets = Maps.newHashMap();
/*  44 */   private final Map<NetworkPacketSummary.PacketIdentification, MutableCountAndSize> sentPackets = Maps.newHashMap();
/*  45 */   private final List<FileIOStat> fileWrites = Lists.newArrayList();
/*  46 */   private final List<FileIOStat> fileReads = Lists.newArrayList();
/*     */   private int garbageCollections;
/*  48 */   private Duration gcTotalDuration = Duration.ZERO;
/*  49 */   private final List<GcHeapStat> gcHeapStats = Lists.newArrayList();
/*  50 */   private final List<ThreadAllocationStat> threadAllocationStats = Lists.newArrayList();
/*     */   
/*  52 */   private final List<TickTimeStat> tickTimes = Lists.newArrayList();
/*     */   @Nullable
/*  54 */   private Duration worldCreationDuration = null;
/*     */ 
/*     */   
/*     */   private JfrStatsParser(Stream<RecordedEvent> $$0) {
/*  58 */     capture($$0);
/*     */   }
/*     */   public static JfrStatsResult parse(Path $$0) {
/*     */     
/*  62 */     try { final RecordingFile recordingFile = new RecordingFile($$0); 
/*  63 */       try { Iterator<RecordedEvent> $$2 = new Iterator<RecordedEvent>()
/*     */           {
/*     */             public boolean hasNext() {
/*  66 */               return recordingFile.hasMoreEvents();
/*     */             }
/*     */ 
/*     */             
/*     */             public RecordedEvent next() {
/*  71 */               if (!hasNext()) {
/*  72 */                 throw new NoSuchElementException();
/*     */               }
/*     */               try {
/*  75 */                 return recordingFile.readEvent();
/*  76 */               } catch (IOException $$0) {
/*  77 */                 throw new UncheckedIOException($$0);
/*     */               } 
/*     */             }
/*     */           };
/*  81 */         Stream<RecordedEvent> $$3 = StreamSupport.stream(Spliterators.spliteratorUnknownSize($$2, 1297), false);
/*  82 */         JfrStatsResult jfrStatsResult = (new JfrStatsParser($$3)).results();
/*  83 */         $$1.close(); return jfrStatsResult; } catch (Throwable throwable) { try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException $$4)
/*  84 */     { throw new UncheckedIOException($$4); }
/*     */   
/*     */   }
/*     */   
/*     */   private JfrStatsResult results() {
/*  89 */     Duration $$0 = Duration.between(this.recordingStarted, this.recordingEnded);
/*  90 */     return new JfrStatsResult(this.recordingStarted, this.recordingEnded, $$0, this.worldCreationDuration, this.tickTimes, this.cpuLoadStat, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  97 */         GcHeapStat.summary($$0, this.gcHeapStats, this.gcTotalDuration, this.garbageCollections), 
/*  98 */         ThreadAllocationStat.summary(this.threadAllocationStats), 
/*  99 */         collectPacketStats($$0, this.receivedPackets), 
/* 100 */         collectPacketStats($$0, this.sentPackets), 
/* 101 */         FileIOStat.summary($$0, this.fileWrites), 
/* 102 */         FileIOStat.summary($$0, this.fileReads), this.chunkGenStats);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void capture(Stream<RecordedEvent> $$0) {
/* 108 */     $$0.forEach($$0 -> {
/*     */           if ($$0.getEndTime().isAfter(this.recordingEnded) || this.recordingEnded.equals(Instant.EPOCH))
/*     */             this.recordingEnded = $$0.getEndTime();  if ($$0.getStartTime().isBefore(this.recordingStarted) || this.recordingStarted.equals(Instant.EPOCH))
/*     */             this.recordingStarted = $$0.getStartTime();  switch ($$0.getEventType().getName()) {
/*     */             case "minecraft.ChunkGeneration":
/*     */               this.chunkGenStats.add(ChunkGenStat.from($$0)); break;
/*     */             case "minecraft.LoadWorld":
/*     */               this.worldCreationDuration = $$0.getDuration(); break;
/*     */             case "minecraft.ServerTickTime":
/*     */               this.tickTimes.add(TickTimeStat.from($$0)); break;
/*     */             case "minecraft.PacketReceived":
/*     */               incrementPacket($$0, $$0.getInt("bytes"), this.receivedPackets); break;
/*     */             case "minecraft.PacketSent":
/*     */               incrementPacket($$0, $$0.getInt("bytes"), this.sentPackets); break;
/*     */             case "jdk.ThreadAllocationStatistics":
/*     */               this.threadAllocationStats.add(ThreadAllocationStat.from($$0)); break;
/*     */             case "jdk.GCHeapSummary":
/*     */               this.gcHeapStats.add(GcHeapStat.from($$0)); break;
/*     */             case "jdk.CPULoad":
/*     */               this.cpuLoadStat.add(CpuLoadStat.from($$0)); break;
/*     */             case "jdk.FileWrite":
/*     */               appendFileIO($$0, this.fileWrites, "bytesWritten"); break;
/*     */             case "jdk.FileRead":
/*     */               appendFileIO($$0, this.fileReads, "bytesRead");
/*     */               break;
/*     */             case "jdk.GarbageCollection":
/*     */               this.garbageCollections++;
/*     */               this.gcTotalDuration = this.gcTotalDuration.plus($$0.getDuration());
/*     */               break;
/*     */           } 
/* 138 */         }); } private void incrementPacket(RecordedEvent $$0, int $$1, Map<NetworkPacketSummary.PacketIdentification, MutableCountAndSize> $$2) { ((MutableCountAndSize)$$2.computeIfAbsent(NetworkPacketSummary.PacketIdentification.from($$0), $$0 -> new MutableCountAndSize())).increment($$1); }
/*     */ 
/*     */   
/*     */   private void appendFileIO(RecordedEvent $$0, List<FileIOStat> $$1, String $$2) {
/* 142 */     $$1.add(new FileIOStat($$0.getDuration(), $$0.getString("path"), $$0.getLong($$2)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static NetworkPacketSummary collectPacketStats(Duration $$0, Map<NetworkPacketSummary.PacketIdentification, MutableCountAndSize> $$1) {
/* 148 */     List<Pair<NetworkPacketSummary.PacketIdentification, NetworkPacketSummary.PacketCountAndSize>> $$2 = $$1.entrySet().stream().map($$0 -> Pair.of($$0.getKey(), ((MutableCountAndSize)$$0.getValue()).toCountAndSize())).toList();
/* 149 */     return new NetworkPacketSummary($$0, $$2);
/*     */   }
/*     */   
/*     */   public static final class MutableCountAndSize {
/*     */     private long count;
/*     */     private long totalSize;
/*     */     
/*     */     public void increment(int $$0) {
/* 157 */       this.totalSize += $$0;
/* 158 */       this.count++;
/*     */     }
/*     */     
/*     */     public NetworkPacketSummary.PacketCountAndSize toCountAndSize() {
/* 162 */       return new NetworkPacketSummary.PacketCountAndSize(this.count, this.totalSize);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\parse\JfrStatsParser.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */