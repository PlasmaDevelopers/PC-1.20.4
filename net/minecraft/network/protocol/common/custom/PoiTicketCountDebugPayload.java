/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ public final class PoiTicketCountDebugPayload extends Record implements CustomPacketPayload {
/*    */   private final BlockPos pos;
/*    */   private final int freeTicketCount;
/*    */   
/*  7 */   public PoiTicketCountDebugPayload(BlockPos $$0, int $$1) { this.pos = $$0; this.freeTicketCount = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/PoiTicketCountDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/PoiTicketCountDebugPayload; } public BlockPos pos() { return this.pos; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/PoiTicketCountDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/PoiTicketCountDebugPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/PoiTicketCountDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/PoiTicketCountDebugPayload;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public int freeTicketCount() { return this.freeTicketCount; }
/*  8 */    public static final ResourceLocation ID = new ResourceLocation("debug/poi_ticket_count");
/*    */   
/*    */   public PoiTicketCountDebugPayload(FriendlyByteBuf $$0) {
/* 11 */     this($$0
/* 12 */         .readBlockPos(), $$0
/* 13 */         .readInt());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 19 */     $$0.writeBlockPos(this.pos);
/* 20 */     $$0.writeInt(this.freeTicketCount);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 25 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\PoiTicketCountDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */