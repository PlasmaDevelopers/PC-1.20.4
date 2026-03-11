/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ 
/*    */ public final class HiveDebugPayload extends Record implements CustomPacketPayload {
/*    */   private final HiveInfo hiveInfo;
/*    */   
/*  7 */   public HiveDebugPayload(HiveInfo $$0) { this.hiveInfo = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload; } public HiveInfo hiveInfo() { return this.hiveInfo; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } public static final ResourceLocation ID = new ResourceLocation("debug/hive");
/*    */   
/*    */   public HiveDebugPayload(FriendlyByteBuf $$0) {
/* 11 */     this(new HiveInfo($$0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 18 */     this.hiveInfo.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 23 */     return ID;
/*    */   }
/*    */   public static final class HiveInfo extends Record { private final BlockPos pos; private final String hiveType; private final int occupantCount; private final int honeyLevel; private final boolean sedated;
/* 26 */     public HiveInfo(BlockPos $$0, String $$1, int $$2, int $$3, boolean $$4) { this.pos = $$0; this.hiveType = $$1; this.occupantCount = $$2; this.honeyLevel = $$3; this.sedated = $$4; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload$HiveInfo;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #26	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload$HiveInfo; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload$HiveInfo;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #26	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload$HiveInfo; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload$HiveInfo;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #26	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/HiveDebugPayload$HiveInfo;
/* 26 */       //   0	8	1	$$0	Ljava/lang/Object; } public BlockPos pos() { return this.pos; } public String hiveType() { return this.hiveType; } public int occupantCount() { return this.occupantCount; } public int honeyLevel() { return this.honeyLevel; } public boolean sedated() { return this.sedated; }
/*    */      public HiveInfo(FriendlyByteBuf $$0) {
/* 28 */       this($$0
/* 29 */           .readBlockPos(), $$0
/* 30 */           .readUtf(), $$0
/* 31 */           .readInt(), $$0
/* 32 */           .readInt(), $$0
/* 33 */           .readBoolean());
/*    */     }
/*    */ 
/*    */     
/*    */     public void write(FriendlyByteBuf $$0) {
/* 38 */       $$0.writeBlockPos(this.pos);
/* 39 */       $$0.writeUtf(this.hiveType);
/* 40 */       $$0.writeInt(this.occupantCount);
/* 41 */       $$0.writeInt(this.honeyLevel);
/* 42 */       $$0.writeBoolean(this.sedated);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\HiveDebugPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */