/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ 
/*    */ public final class PoiRemovedDebugPayload extends Record implements CustomPacketPayload {
/*    */   private final BlockPos pos;
/*    */   
/*  7 */   public PoiRemovedDebugPayload(BlockPos $$0) { this.pos = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/PoiRemovedDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/PoiRemovedDebugPayload; } public BlockPos pos() { return this.pos; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/PoiRemovedDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/PoiRemovedDebugPayload; } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/PoiRemovedDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/PoiRemovedDebugPayload;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/* 10 */   } public static final ResourceLocation ID = new ResourceLocation("debug/poi_removed");
/*    */   
/*    */   public PoiRemovedDebugPayload(FriendlyByteBuf $$0) {
/* 13 */     this($$0
/* 14 */         .readBlockPos());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     $$0.writeBlockPos(this.pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 25 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\PoiRemovedDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */