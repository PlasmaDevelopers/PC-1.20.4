/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ public final class GameTestAddMarkerDebugPayload extends Record implements CustomPacketPayload { private final BlockPos pos;
/*    */   private final int color;
/*    */   private final String text;
/*    */   private final int durationMs;
/*    */   
/*  7 */   public GameTestAddMarkerDebugPayload(BlockPos $$0, int $$1, String $$2, int $$3) { this.pos = $$0; this.color = $$1; this.text = $$2; this.durationMs = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/GameTestAddMarkerDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/GameTestAddMarkerDebugPayload; } public BlockPos pos() { return this.pos; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/GameTestAddMarkerDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/GameTestAddMarkerDebugPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/GameTestAddMarkerDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/GameTestAddMarkerDebugPayload;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public int color() { return this.color; } public String text() { return this.text; } public int durationMs() { return this.durationMs; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 13 */   public static final ResourceLocation ID = new ResourceLocation("debug/game_test_add_marker");
/*    */   
/*    */   public GameTestAddMarkerDebugPayload(FriendlyByteBuf $$0) {
/* 16 */     this($$0
/* 17 */         .readBlockPos(), $$0
/* 18 */         .readInt(), $$0
/* 19 */         .readUtf(), $$0
/* 20 */         .readInt());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 26 */     $$0.writeBlockPos(this.pos);
/* 27 */     $$0.writeInt(this.color);
/* 28 */     $$0.writeUtf(this.text);
/* 29 */     $$0.writeInt(this.durationMs);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 34 */     return ID;
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\GameTestAddMarkerDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */