/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.BitSet;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.chunk.LevelChunk;
/*    */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*    */ 
/*    */ public class ClientboundLevelChunkWithLightPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private final int x;
/*    */   private final int z;
/*    */   private final ClientboundLevelChunkPacketData chunkData;
/*    */   private final ClientboundLightUpdatePacketData lightData;
/*    */   
/*    */   public ClientboundLevelChunkWithLightPacket(LevelChunk $$0, LevelLightEngine $$1, @Nullable BitSet $$2, @Nullable BitSet $$3) {
/* 20 */     ChunkPos $$4 = $$0.getPos();
/* 21 */     this.x = $$4.x;
/* 22 */     this.z = $$4.z;
/*    */     
/* 24 */     this.chunkData = new ClientboundLevelChunkPacketData($$0);
/* 25 */     this.lightData = new ClientboundLightUpdatePacketData($$4, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public ClientboundLevelChunkWithLightPacket(FriendlyByteBuf $$0) {
/* 29 */     this.x = $$0.readInt();
/* 30 */     this.z = $$0.readInt();
/* 31 */     this.chunkData = new ClientboundLevelChunkPacketData($$0, this.x, this.z);
/* 32 */     this.lightData = new ClientboundLightUpdatePacketData($$0, this.x, this.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 37 */     $$0.writeInt(this.x);
/* 38 */     $$0.writeInt(this.z);
/* 39 */     this.chunkData.write($$0);
/* 40 */     this.lightData.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 45 */     $$0.handleLevelChunkWithLight(this);
/*    */   }
/*    */   
/*    */   public int getX() {
/* 49 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getZ() {
/* 53 */     return this.z;
/*    */   }
/*    */   
/*    */   public ClientboundLevelChunkPacketData getChunkData() {
/* 57 */     return this.chunkData;
/*    */   }
/*    */   
/*    */   public ClientboundLightUpdatePacketData getLightData() {
/* 61 */     return this.lightData;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundLevelChunkWithLightPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */