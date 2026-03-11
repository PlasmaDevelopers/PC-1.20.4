/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ public final class PoiAddedDebugPayload extends Record implements CustomPacketPayload {
/*    */   private final BlockPos pos;
/*    */   private final String type;
/*    */   private final int freeTicketCount;
/*    */   
/*  7 */   public PoiAddedDebugPayload(BlockPos $$0, String $$1, int $$2) { this.pos = $$0; this.type = $$1; this.freeTicketCount = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/PoiAddedDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/PoiAddedDebugPayload; } public BlockPos pos() { return this.pos; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/PoiAddedDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/PoiAddedDebugPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/PoiAddedDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/PoiAddedDebugPayload;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public String type() { return this.type; } public int freeTicketCount() { return this.freeTicketCount; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 12 */   public static final ResourceLocation ID = new ResourceLocation("debug/poi_added");
/*    */   
/*    */   public PoiAddedDebugPayload(FriendlyByteBuf $$0) {
/* 15 */     this($$0
/* 16 */         .readBlockPos(), $$0
/* 17 */         .readUtf(), $$0
/* 18 */         .readInt());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 24 */     $$0.writeBlockPos(this.pos);
/* 25 */     $$0.writeUtf(this.type);
/* 26 */     $$0.writeInt(this.freeTicketCount);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 31 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\PoiAddedDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */