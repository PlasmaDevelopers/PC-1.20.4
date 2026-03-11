/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundContainerSetDataPacket implements Packet<ClientGamePacketListener> {
/*    */   private final int containerId;
/*    */   private final int id;
/*    */   private final int value;
/*    */   
/*    */   public ClientboundContainerSetDataPacket(int $$0, int $$1, int $$2) {
/* 13 */     this.containerId = $$0;
/* 14 */     this.id = $$1;
/* 15 */     this.value = $$2;
/*    */   }
/*    */   
/*    */   public ClientboundContainerSetDataPacket(FriendlyByteBuf $$0) {
/* 19 */     this.containerId = $$0.readUnsignedByte();
/* 20 */     this.id = $$0.readShort();
/* 21 */     this.value = $$0.readShort();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 27 */     $$0.writeByte(this.containerId);
/* 28 */     $$0.writeShort(this.id);
/* 29 */     $$0.writeShort(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 34 */     $$0.handleContainerSetData(this);
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 38 */     return this.containerId;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 42 */     return this.id;
/*    */   }
/*    */   
/*    */   public int getValue() {
/* 46 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundContainerSetDataPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */