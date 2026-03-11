/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.chunk.LevelChunk;
/*    */ import net.minecraft.world.level.chunk.LevelChunkSection;
/*    */ 
/*    */ public final class ClientboundChunksBiomesPacket extends Record implements Packet<ClientGamePacketListener> {
/*    */   private final List<ChunkBiomeData> chunkBiomeData;
/*    */   private static final int TWO_MEGABYTES = 2097152;
/*    */   
/* 13 */   public ClientboundChunksBiomesPacket(List<ChunkBiomeData> $$0) { this.chunkBiomeData = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundChunksBiomesPacket;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 13 */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundChunksBiomesPacket; } public List<ChunkBiomeData> chunkBiomeData() { return this.chunkBiomeData; } public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundChunksBiomesPacket;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundChunksBiomesPacket;
/*    */   } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundChunksBiomesPacket;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundChunksBiomesPacket;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   } public static final class ChunkBiomeData extends Record { private final ChunkPos pos; private final byte[] buffer; public ChunkBiomeData(ChunkPos $$0, byte[] $$1) {
/* 18 */       this.pos = $$0; this.buffer = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/game/ClientboundChunksBiomesPacket$ChunkBiomeData;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #18	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundChunksBiomesPacket$ChunkBiomeData; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/game/ClientboundChunksBiomesPacket$ChunkBiomeData;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #18	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/game/ClientboundChunksBiomesPacket$ChunkBiomeData; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/game/ClientboundChunksBiomesPacket$ChunkBiomeData;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #18	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/protocol/game/ClientboundChunksBiomesPacket$ChunkBiomeData;
/* 18 */       //   0	8	1	$$0	Ljava/lang/Object; } public ChunkPos pos() { return this.pos; } public byte[] buffer() { return this.buffer; }
/*    */ 
/*    */ 
/*    */     
/*    */     public ChunkBiomeData(LevelChunk $$0) {
/* 23 */       this($$0.getPos(), new byte[calculateChunkSize($$0)]);
/* 24 */       extractChunkData(new FriendlyByteBuf(getWriteBuffer()), $$0);
/*    */     }
/*    */     
/*    */     public ChunkBiomeData(FriendlyByteBuf $$0) {
/* 28 */       this($$0
/* 29 */           .readChunkPos(), $$0
/* 30 */           .readByteArray(2097152));
/*    */     }
/*    */ 
/*    */     
/*    */     private static int calculateChunkSize(LevelChunk $$0) {
/* 35 */       int $$1 = 0;
/*    */       
/* 37 */       for (LevelChunkSection $$2 : $$0.getSections()) {
/* 38 */         $$1 += $$2.getBiomes().getSerializedSize();
/*    */       }
/*    */       
/* 41 */       return $$1;
/*    */     }
/*    */     
/*    */     public FriendlyByteBuf getReadBuffer() {
/* 45 */       return new FriendlyByteBuf(Unpooled.wrappedBuffer(this.buffer));
/*    */     }
/*    */     
/*    */     private ByteBuf getWriteBuffer() {
/* 49 */       ByteBuf $$0 = Unpooled.wrappedBuffer(this.buffer);
/* 50 */       $$0.writerIndex(0);
/* 51 */       return $$0;
/*    */     }
/*    */     
/*    */     public static void extractChunkData(FriendlyByteBuf $$0, LevelChunk $$1) {
/* 55 */       for (LevelChunkSection $$2 : $$1.getSections()) {
/* 56 */         $$2.getBiomes().write($$0);
/*    */       }
/*    */     }
/*    */     
/*    */     public void write(FriendlyByteBuf $$0) {
/* 61 */       $$0.writeChunkPos(this.pos);
/* 62 */       $$0.writeByteArray(this.buffer);
/*    */     } }
/*    */ 
/*    */   
/*    */   public ClientboundChunksBiomesPacket(FriendlyByteBuf $$0) {
/* 67 */     this($$0.readList(ChunkBiomeData::new));
/*    */   }
/*    */   
/*    */   public static ClientboundChunksBiomesPacket forChunks(List<LevelChunk> $$0) {
/* 71 */     return new ClientboundChunksBiomesPacket($$0.stream().map(ChunkBiomeData::new).toList());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 76 */     $$0.writeCollection(this.chunkBiomeData, ($$0, $$1) -> $$1.write($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 81 */     $$0.handleChunksBiomes(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundChunksBiomesPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */