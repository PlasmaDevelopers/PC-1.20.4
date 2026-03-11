/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundHorseScreenOpenPacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   private final int containerId;
/*    */   
/*    */   public ClientboundHorseScreenOpenPacket(int $$0, int $$1, int $$2) {
/* 12 */     this.containerId = $$0;
/* 13 */     this.size = $$1;
/* 14 */     this.entityId = $$2;
/*    */   }
/*    */   private final int size; private final int entityId;
/*    */   public ClientboundHorseScreenOpenPacket(FriendlyByteBuf $$0) {
/* 18 */     this.containerId = $$0.readUnsignedByte();
/* 19 */     this.size = $$0.readVarInt();
/* 20 */     this.entityId = $$0.readInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 25 */     $$0.writeByte(this.containerId);
/* 26 */     $$0.writeVarInt(this.size);
/* 27 */     $$0.writeInt(this.entityId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 32 */     $$0.handleHorseScreenOpen(this);
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 36 */     return this.containerId;
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 40 */     return this.size;
/*    */   }
/*    */   
/*    */   public int getEntityId() {
/* 44 */     return this.entityId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundHorseScreenOpenPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */