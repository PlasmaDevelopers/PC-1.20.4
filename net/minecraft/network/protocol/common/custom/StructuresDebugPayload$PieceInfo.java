/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PieceInfo
/*    */   extends Record
/*    */ {
/*    */   private final BoundingBox boundingBox;
/*    */   private final boolean isStart;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload$PieceInfo;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #52	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload$PieceInfo;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload$PieceInfo;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #52	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload$PieceInfo;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload$PieceInfo;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #52	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/StructuresDebugPayload$PieceInfo;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public PieceInfo(BoundingBox $$0, boolean $$1) {
/* 52 */     this.boundingBox = $$0; this.isStart = $$1; } public BoundingBox boundingBox() { return this.boundingBox; } public boolean isStart() { return this.isStart; }
/*    */    public PieceInfo(FriendlyByteBuf $$0) {
/* 54 */     this(
/* 55 */         StructuresDebugPayload.readBoundingBox($$0), $$0
/* 56 */         .readBoolean());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 61 */     StructuresDebugPayload.writeBoundingBox($$0, this.boundingBox);
/* 62 */     $$0.writeBoolean(this.isStart);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\StructuresDebugPayload$PieceInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */