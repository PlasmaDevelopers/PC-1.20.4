/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ public final class ClientboundChunkBatchFinishedPacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final int batchSize;
/*    */   
/*  6 */   public ClientboundChunkBatchFinishedPacket(int $$0) { this.batchSize = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundChunkBatchFinishedPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  6 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundChunkBatchFinishedPacket; } public int batchSize() { return this.batchSize; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundChunkBatchFinishedPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundChunkBatchFinishedPacket; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundChunkBatchFinishedPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundChunkBatchFinishedPacket;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } public ClientboundChunkBatchFinishedPacket(FriendlyByteBuf $$0) { this($$0.readVarInt()); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 13 */     $$0.writeVarInt(this.batchSize);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 18 */     $$0.handleChunkBatchFinished(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundChunkBatchFinishedPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */