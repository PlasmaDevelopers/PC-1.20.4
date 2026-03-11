/*    */ package net.minecraft.util.profiling.jfr.stats;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import java.util.Map;
/*    */ import jdk.jfr.consumer.RecordedEvent;
/*    */ import net.minecraft.network.ConnectionProtocol;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.PacketFlow;
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
/*    */ public final class PacketIdentification
/*    */   extends Record
/*    */ {
/*    */   private final PacketFlow direction;
/*    */   private final String protocolId;
/*    */   private final int packetId;
/*    */   private static final Map<PacketIdentification, String> PACKET_NAME_BY_ID;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketIdentification;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #56	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketIdentification;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketIdentification;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #56	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketIdentification;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketIdentification;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #56	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/NetworkPacketSummary$PacketIdentification;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public PacketIdentification(PacketFlow $$0, String $$1, int $$2) {
/* 56 */     this.direction = $$0; this.protocolId = $$1; this.packetId = $$2; } public PacketFlow direction() { return this.direction; } public String protocolId() { return this.protocolId; } public int packetId() { return this.packetId; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 62 */     ImmutableMap.Builder<PacketIdentification, String> $$0 = ImmutableMap.builder();
/* 63 */     for (ConnectionProtocol $$1 : ConnectionProtocol.values()) {
/* 64 */       for (PacketFlow $$2 : PacketFlow.values()) {
/* 65 */         Int2ObjectMap<Class<? extends Packet<?>>> $$3 = $$1.getPacketsByIds($$2);
/* 66 */         $$3.forEach(($$3, $$4) -> $$0.put(new PacketIdentification($$1, $$2.id(), $$3.intValue()), $$4.getSimpleName()));
/*    */       } 
/*    */     } 
/* 69 */     PACKET_NAME_BY_ID = (Map<PacketIdentification, String>)$$0.build();
/*    */   }
/*    */   
/*    */   public String packetName() {
/* 73 */     return PACKET_NAME_BY_ID.getOrDefault(this, "unknown");
/*    */   }
/*    */   
/*    */   public static PacketIdentification from(RecordedEvent $$0) {
/* 77 */     return new PacketIdentification($$0.getEventType().getName().equals("minecraft.PacketSent") ? PacketFlow.CLIENTBOUND : PacketFlow.SERVERBOUND, $$0
/* 78 */         .getString("protocolId"), $$0
/* 79 */         .getInt("packetId"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\stats\NetworkPacketSummary$PacketIdentification.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */