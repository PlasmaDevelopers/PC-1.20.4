/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ public final class ServerboundChunkBatchReceivedPacket extends Record implements Packet<ServerGamePacketListener> {
/*    */   private final float desiredChunksPerTick;
/*    */   
/*  6 */   public ServerboundChunkBatchReceivedPacket(float $$0) { this.desiredChunksPerTick = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ServerboundChunkBatchReceivedPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  6 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ServerboundChunkBatchReceivedPacket; } public float desiredChunksPerTick() { return this.desiredChunksPerTick; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ServerboundChunkBatchReceivedPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ServerboundChunkBatchReceivedPacket; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ServerboundChunkBatchReceivedPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ServerboundChunkBatchReceivedPacket;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } public ServerboundChunkBatchReceivedPacket(FriendlyByteBuf $$0) { this($$0.readFloat()); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 13 */     $$0.writeFloat(this.desiredChunksPerTick);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 18 */     $$0.handleChunkBatchReceived(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundChunkBatchReceivedPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */