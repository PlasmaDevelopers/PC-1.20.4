/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ public final class NeighborUpdatesDebugPayload extends Record implements CustomPacketPayload {
/*    */   private final long time;
/*    */   private final BlockPos pos;
/*    */   
/*  7 */   public NeighborUpdatesDebugPayload(long $$0, BlockPos $$1) { this.time = $$0; this.pos = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/NeighborUpdatesDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/NeighborUpdatesDebugPayload; } public long time() { return this.time; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/NeighborUpdatesDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/NeighborUpdatesDebugPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/NeighborUpdatesDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/NeighborUpdatesDebugPayload;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public BlockPos pos() { return this.pos; }
/*  8 */    public static final ResourceLocation ID = new ResourceLocation("debug/neighbors_update");
/*    */   
/*    */   public NeighborUpdatesDebugPayload(FriendlyByteBuf $$0) {
/* 11 */     this($$0
/* 12 */         .readVarLong(), $$0
/* 13 */         .readBlockPos());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 19 */     $$0.writeVarLong(this.time);
/* 20 */     $$0.writeBlockPos(this.pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 25 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\NeighborUpdatesDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */