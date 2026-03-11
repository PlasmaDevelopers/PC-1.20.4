/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.BitSet;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*    */ 
/*    */ public class ClientboundLightUpdatePacket implements Packet<ClientGamePacketListener> {
/*    */   private final int x;
/*    */   private final int z;
/*    */   private final ClientboundLightUpdatePacketData lightData;
/*    */   
/*    */   public ClientboundLightUpdatePacket(ChunkPos $$0, LevelLightEngine $$1, @Nullable BitSet $$2, @Nullable BitSet $$3) {
/* 17 */     this.x = $$0.x;
/* 18 */     this.z = $$0.z;
/* 19 */     this.lightData = new ClientboundLightUpdatePacketData($$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public ClientboundLightUpdatePacket(FriendlyByteBuf $$0) {
/* 23 */     this.x = $$0.readVarInt();
/* 24 */     this.z = $$0.readVarInt();
/* 25 */     this.lightData = new ClientboundLightUpdatePacketData($$0, this.x, this.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 30 */     $$0.writeVarInt(this.x);
/* 31 */     $$0.writeVarInt(this.z);
/* 32 */     this.lightData.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 37 */     $$0.handleLightUpdatePacket(this);
/*    */   }
/*    */   
/*    */   public int getX() {
/* 41 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getZ() {
/* 45 */     return this.z;
/*    */   }
/*    */   
/*    */   public ClientboundLightUpdatePacketData getLightData() {
/* 49 */     return this.lightData;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundLightUpdatePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */