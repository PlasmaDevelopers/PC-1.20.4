/*    */ package net.minecraft.network.protocol.common.custom;public final class WorldGenAttemptDebugPayload extends Record implements CustomPacketPayload { private final BlockPos pos; private final float scale;
/*    */   private final float red;
/*    */   private final float green;
/*    */   private final float blue;
/*    */   private final float alpha;
/*    */   
/*  7 */   public WorldGenAttemptDebugPayload(BlockPos $$0, float $$1, float $$2, float $$3, float $$4, float $$5) { this.pos = $$0; this.scale = $$1; this.red = $$2; this.green = $$3; this.blue = $$4; this.alpha = $$5; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/WorldGenAttemptDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/WorldGenAttemptDebugPayload; } public BlockPos pos() { return this.pos; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/WorldGenAttemptDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/WorldGenAttemptDebugPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/WorldGenAttemptDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/WorldGenAttemptDebugPayload;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public float scale() { return this.scale; } public float red() { return this.red; } public float green() { return this.green; } public float blue() { return this.blue; } public float alpha() { return this.alpha; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 15 */   public static final ResourceLocation ID = new ResourceLocation("debug/worldgen_attempt");
/*    */   
/*    */   public WorldGenAttemptDebugPayload(FriendlyByteBuf $$0) {
/* 18 */     this($$0
/* 19 */         .readBlockPos(), $$0
/* 20 */         .readFloat(), $$0
/* 21 */         .readFloat(), $$0
/* 22 */         .readFloat(), $$0
/* 23 */         .readFloat(), $$0
/* 24 */         .readFloat());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 30 */     $$0.writeBlockPos(this.pos);
/* 31 */     $$0.writeFloat(this.scale);
/* 32 */     $$0.writeFloat(this.red);
/* 33 */     $$0.writeFloat(this.green);
/* 34 */     $$0.writeFloat(this.blue);
/* 35 */     $$0.writeFloat(this.alpha);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 40 */     return ID;
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\WorldGenAttemptDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */