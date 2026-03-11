/*    */ package net.minecraft.util.profiling.jfr.stats;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import java.time.Duration;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import jdk.jfr.consumer.RecordedEvent;
/*    */ import net.minecraft.network.ConnectionProtocol;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.PacketFlow;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NetworkPacketSummary
/*    */ {
/*    */   private final PacketCountAndSize totalPacketCountAndSize;
/*    */   private final List<Pair<PacketIdentification, PacketCountAndSize>> largestSizeContributors;
/*    */   private final Duration recordingDuration;
/*    */   
/*    */   public NetworkPacketSummary(Duration $$0, List<Pair<PacketIdentification, PacketCountAndSize>> $$1) {
/* 24 */     this.recordingDuration = $$0;
/* 25 */     this
/*    */ 
/*    */       
/* 28 */       .totalPacketCountAndSize = $$1.stream().map(Pair::getSecond).reduce(PacketCountAndSize::add).orElseGet(() -> new PacketCountAndSize(0L, 0L));
/*    */     
/* 30 */     this
/*    */ 
/*    */       
/* 33 */       .largestSizeContributors = $$1.stream().sorted(Comparator.comparing(Pair::getSecond, PacketCountAndSize.SIZE_THEN_COUNT)).limit(10L).toList();
/*    */   }
/*    */   
/*    */   public double getCountsPerSecond() {
/* 37 */     return this.totalPacketCountAndSize.totalCount / this.recordingDuration.getSeconds();
/*    */   }
/*    */   
/*    */   public double getSizePerSecond() {
/* 41 */     return this.totalPacketCountAndSize.totalSize / this.recordingDuration.getSeconds();
/*    */   }
/*    */   
/*    */   public long getTotalCount() {
/* 45 */     return this.totalPacketCountAndSize.totalCount;
/*    */   }
/*    */   
/*    */   public long getTotalSize() {
/* 49 */     return this.totalPacketCountAndSize.totalSize;
/*    */   }
/*    */   
/*    */   public List<Pair<PacketIdentification, PacketCountAndSize>> largestSizeContributors() {
/* 53 */     return this.largestSizeContributors;
/*    */   }
/*    */   public static final class PacketIdentification extends Record { private final PacketFlow direction; private final String protocolId; private final int packetId; private static final Map<PacketIdentification, String> PACKET_NAME_BY_ID;
/* 56 */     public PacketIdentification(PacketFlow $$0, String $$1, int $$2) { this.direction = $$0; this.protocolId = $$1; this.packetId = $$2; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketIdentification;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #56	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 56 */       //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketIdentification; } public PacketFlow direction() { return this.direction; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketIdentification;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #56	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketIdentification; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketIdentification;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #56	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketIdentification;
/* 56 */       //   0	8	1	$$0	Ljava/lang/Object; } public String protocolId() { return this.protocolId; } public int packetId() { return this.packetId; }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 62 */       ImmutableMap.Builder<PacketIdentification, String> $$0 = ImmutableMap.builder();
/* 63 */       for (ConnectionProtocol $$1 : ConnectionProtocol.values()) {
/* 64 */         for (PacketFlow $$2 : PacketFlow.values()) {
/* 65 */           Int2ObjectMap<Class<? extends Packet<?>>> $$3 = $$1.getPacketsByIds($$2);
/* 66 */           $$3.forEach(($$3, $$4) -> $$0.put(new PacketIdentification($$1, $$2.id(), $$3.intValue()), $$4.getSimpleName()));
/*    */         } 
/*    */       } 
/* 69 */       PACKET_NAME_BY_ID = (Map<PacketIdentification, String>)$$0.build();
/*    */     }
/*    */     
/*    */     public String packetName() {
/* 73 */       return PACKET_NAME_BY_ID.getOrDefault(this, "unknown");
/*    */     }
/*    */     
/*    */     public static PacketIdentification from(RecordedEvent $$0) {
/* 77 */       return new PacketIdentification($$0.getEventType().getName().equals("minecraft.PacketSent") ? PacketFlow.CLIENTBOUND : PacketFlow.SERVERBOUND, $$0
/* 78 */           .getString("protocolId"), $$0
/* 79 */           .getInt("packetId"));
/*    */     } }
/*    */   public static final class PacketCountAndSize extends Record { final long totalCount; final long totalSize;
/*    */     
/* 83 */     public PacketCountAndSize(long $$0, long $$1) { this.totalCount = $$0; this.totalSize = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketCountAndSize;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #83	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketCountAndSize; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketCountAndSize;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #83	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketCountAndSize; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketCountAndSize;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #83	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketCountAndSize;
/* 83 */       //   0	8	1	$$0	Ljava/lang/Object; } public long totalCount() { return this.totalCount; } public long totalSize() { return this.totalSize; }
/* 84 */      static final Comparator<PacketCountAndSize> SIZE_THEN_COUNT = Comparator.comparing(PacketCountAndSize::totalSize).thenComparing(PacketCountAndSize::totalCount).reversed();
/*    */     
/*    */     PacketCountAndSize add(PacketCountAndSize $$0) {
/* 87 */       return new PacketCountAndSize(this.totalCount + $$0.totalCount, this.totalSize + $$0.totalSize);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\stats\NetworkPacketSummary.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */