/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public final class HiveInfo extends Record {
/*    */   private final BlockPos pos;
/*    */   private final String hiveType;
/*    */   private final int occupantCount;
/*    */   private final int honeyLevel;
/*    */   private final boolean sedated;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload$HiveInfo;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload$HiveInfo;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload$HiveInfo;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload$HiveInfo;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload$HiveInfo;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload$HiveInfo;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public HiveInfo(BlockPos $$0, String $$1, int $$2, int $$3, boolean $$4) {
/* 26 */     this.pos = $$0; this.hiveType = $$1; this.occupantCount = $$2; this.honeyLevel = $$3; this.sedated = $$4; } public BlockPos pos() { return this.pos; } public String hiveType() { return this.hiveType; } public int occupantCount() { return this.occupantCount; } public int honeyLevel() { return this.honeyLevel; } public boolean sedated() { return this.sedated; }
/*    */    public HiveInfo(FriendlyByteBuf $$0) {
/* 28 */     this($$0
/* 29 */         .readBlockPos(), $$0
/* 30 */         .readUtf(), $$0
/* 31 */         .readInt(), $$0
/* 32 */         .readInt(), $$0
/* 33 */         .readBoolean());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 38 */     $$0.writeBlockPos(this.pos);
/* 39 */     $$0.writeUtf(this.hiveType);
/* 40 */     $$0.writeInt(this.occupantCount);
/* 41 */     $$0.writeInt(this.honeyLevel);
/* 42 */     $$0.writeBoolean(this.sedated);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\HiveDebugPayload$HiveInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */