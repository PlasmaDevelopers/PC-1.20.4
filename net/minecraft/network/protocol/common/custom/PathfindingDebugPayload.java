/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ public final class PathfindingDebugPayload extends Record implements CustomPacketPayload {
/*    */   private final int entityId;
/*    */   private final Path path;
/*    */   private final float maxNodeDistance;
/*    */   
/*  7 */   public PathfindingDebugPayload(int $$0, Path $$1, float $$2) { this.entityId = $$0; this.path = $$1; this.maxNodeDistance = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/PathfindingDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/PathfindingDebugPayload; } public int entityId() { return this.entityId; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/PathfindingDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/PathfindingDebugPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/PathfindingDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/PathfindingDebugPayload;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public Path path() { return this.path; } public float maxNodeDistance() { return this.maxNodeDistance; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 12 */   public static final ResourceLocation ID = new ResourceLocation("debug/path");
/*    */   
/*    */   public PathfindingDebugPayload(FriendlyByteBuf $$0) {
/* 15 */     this($$0
/* 16 */         .readInt(), 
/* 17 */         Path.createFromStream($$0), $$0
/* 18 */         .readFloat());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 24 */     $$0.writeInt(this.entityId);
/* 25 */     this.path.writeToStream($$0);
/* 26 */     $$0.writeFloat(this.maxNodeDistance);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 31 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\PathfindingDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */