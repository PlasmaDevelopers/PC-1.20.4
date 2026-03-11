/*    */ package net.minecraft.network.protocol.common;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.common.custom.BeeDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.BrainDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.BrandPayload;
/*    */ import net.minecraft.network.protocol.common.custom.BreezeDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
/*    */ import net.minecraft.network.protocol.common.custom.DiscardedPayload;
/*    */ import net.minecraft.network.protocol.common.custom.GameEventDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.GameEventListenerDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.GameTestAddMarkerDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.GameTestClearMarkersDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.GoalDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.HiveDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.NeighborUpdatesDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.PathfindingDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.PoiRemovedDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.PoiTicketCountDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.RaidsDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.StructuresDebugPayload;
/*    */ import net.minecraft.network.protocol.common.custom.WorldGenAttemptDebugPayload;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public final class ClientboundCustomPayloadPacket extends Record implements Packet<ClientCommonPacketListener> {
/*    */   private final CustomPacketPayload payload;
/*    */   private static final int MAX_PAYLOAD_SIZE = 1048576;
/*    */   
/* 32 */   public ClientboundCustomPayloadPacket(CustomPacketPayload $$0) { this.payload = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #32	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 32 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket; } public CustomPacketPayload payload() { return this.payload; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #32	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket; } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #32	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/ClientboundCustomPayloadPacket;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/* 35 */   } private static final Map<ResourceLocation, FriendlyByteBuf.Reader<? extends CustomPacketPayload>> KNOWN_TYPES = (Map<ResourceLocation, FriendlyByteBuf.Reader<? extends CustomPacketPayload>>)ImmutableMap.builder()
/*    */     
/* 37 */     .put(BrandPayload.ID, BrandPayload::new)
/*    */ 
/*    */     
/* 40 */     .put(BeeDebugPayload.ID, BeeDebugPayload::new)
/* 41 */     .put(BrainDebugPayload.ID, BrainDebugPayload::new)
/* 42 */     .put(BreezeDebugPayload.ID, BreezeDebugPayload::new)
/* 43 */     .put(GameEventDebugPayload.ID, GameEventDebugPayload::new)
/* 44 */     .put(GameEventListenerDebugPayload.ID, GameEventListenerDebugPayload::new)
/* 45 */     .put(GameTestAddMarkerDebugPayload.ID, GameTestAddMarkerDebugPayload::new)
/* 46 */     .put(GameTestClearMarkersDebugPayload.ID, GameTestClearMarkersDebugPayload::new)
/* 47 */     .put(GoalDebugPayload.ID, GoalDebugPayload::new)
/* 48 */     .put(HiveDebugPayload.ID, HiveDebugPayload::new)
/* 49 */     .put(NeighborUpdatesDebugPayload.ID, NeighborUpdatesDebugPayload::new)
/* 50 */     .put(PathfindingDebugPayload.ID, PathfindingDebugPayload::new)
/* 51 */     .put(PoiAddedDebugPayload.ID, PoiAddedDebugPayload::new)
/* 52 */     .put(PoiRemovedDebugPayload.ID, PoiRemovedDebugPayload::new)
/* 53 */     .put(PoiTicketCountDebugPayload.ID, PoiTicketCountDebugPayload::new)
/* 54 */     .put(RaidsDebugPayload.ID, RaidsDebugPayload::new)
/* 55 */     .put(StructuresDebugPayload.ID, StructuresDebugPayload::new)
/* 56 */     .put(VillageSectionsDebugPayload.ID, VillageSectionsDebugPayload::new)
/* 57 */     .put(WorldGenAttemptDebugPayload.ID, WorldGenAttemptDebugPayload::new)
/* 58 */     .build();
/*    */   
/*    */   public ClientboundCustomPayloadPacket(FriendlyByteBuf $$0) {
/* 61 */     this(
/* 62 */         readPayload($$0
/* 63 */           .readResourceLocation(), $$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static CustomPacketPayload readPayload(ResourceLocation $$0, FriendlyByteBuf $$1) {
/* 70 */     FriendlyByteBuf.Reader<? extends CustomPacketPayload> $$2 = KNOWN_TYPES.get($$0);
/* 71 */     if ($$2 != null) {
/* 72 */       return (CustomPacketPayload)$$2.apply($$1);
/*    */     }
/* 74 */     return (CustomPacketPayload)readUnknownPayload($$0, $$1);
/*    */   }
/*    */   
/*    */   private static DiscardedPayload readUnknownPayload(ResourceLocation $$0, FriendlyByteBuf $$1) {
/* 78 */     int $$2 = $$1.readableBytes();
/* 79 */     if ($$2 < 0 || $$2 > 1048576) {
/* 80 */       throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
/*    */     }
/* 82 */     $$1.skipBytes($$2);
/* 83 */     return new DiscardedPayload($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 88 */     $$0.writeResourceLocation(this.payload.id());
/* 89 */     this.payload.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientCommonPacketListener $$0) {
/* 94 */     $$0.handleCustomPayload(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ClientboundCustomPayloadPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */