/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ 
/*    */ public final class ClientboundForgetLevelChunkPacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final ChunkPos pos;
/*    */   
/*  7 */   public ClientboundForgetLevelChunkPacket(ChunkPos $$0) { this.pos = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundForgetLevelChunkPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundForgetLevelChunkPacket; } public ChunkPos pos() { return this.pos; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundForgetLevelChunkPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundForgetLevelChunkPacket; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundForgetLevelChunkPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundForgetLevelChunkPacket;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public ClientboundForgetLevelChunkPacket(FriendlyByteBuf $$0) { this($$0.readChunkPos()); }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 14 */     $$0.writeChunkPos(this.pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 19 */     $$0.handleForgetLevelChunk(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundForgetLevelChunkPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */