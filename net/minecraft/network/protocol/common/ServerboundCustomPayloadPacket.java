/*    */ package net.minecraft.network.protocol.common;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
/*    */ import net.minecraft.network.protocol.common.custom.DiscardedPayload;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public final class ServerboundCustomPayloadPacket extends Record implements Packet<ServerCommonPacketListener> {
/*    */   private final CustomPacketPayload payload;
/*    */   private static final int MAX_PAYLOAD_SIZE = 32767;
/*    */   
/* 13 */   public ServerboundCustomPayloadPacket(CustomPacketPayload $$0) { this.payload = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/ServerboundCustomPayloadPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 13 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/ServerboundCustomPayloadPacket; } public CustomPacketPayload payload() { return this.payload; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/ServerboundCustomPayloadPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/ServerboundCustomPayloadPacket; } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/ServerboundCustomPayloadPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/ServerboundCustomPayloadPacket;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/* 16 */   } private static final Map<ResourceLocation, FriendlyByteBuf.Reader<? extends CustomPacketPayload>> KNOWN_TYPES = (Map<ResourceLocation, FriendlyByteBuf.Reader<? extends CustomPacketPayload>>)ImmutableMap.builder()
/* 17 */     .put(BrandPayload.ID, BrandPayload::new)
/* 18 */     .build();
/*    */   
/*    */   public ServerboundCustomPayloadPacket(FriendlyByteBuf $$0) {
/* 21 */     this(
/* 22 */         readPayload($$0
/* 23 */           .readResourceLocation(), $$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static CustomPacketPayload readPayload(ResourceLocation $$0, FriendlyByteBuf $$1) {
/* 30 */     FriendlyByteBuf.Reader<? extends CustomPacketPayload> $$2 = KNOWN_TYPES.get($$0);
/* 31 */     if ($$2 != null) {
/* 32 */       return (CustomPacketPayload)$$2.apply($$1);
/*    */     }
/* 34 */     return (CustomPacketPayload)readUnknownPayload($$0, $$1);
/*    */   }
/*    */   
/*    */   private static DiscardedPayload readUnknownPayload(ResourceLocation $$0, FriendlyByteBuf $$1) {
/* 38 */     int $$2 = $$1.readableBytes();
/* 39 */     if ($$2 < 0 || $$2 > 32767) {
/* 40 */       throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
/*    */     }
/* 42 */     $$1.skipBytes($$2);
/* 43 */     return new DiscardedPayload($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 48 */     $$0.writeResourceLocation(this.payload.id());
/* 49 */     this.payload.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerCommonPacketListener $$0) {
/* 54 */     $$0.handleCustomPayload(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ServerboundCustomPayloadPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */