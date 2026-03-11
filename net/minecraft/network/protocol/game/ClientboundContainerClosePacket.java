/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundContainerClosePacket implements Packet<ClientGamePacketListener> {
/*    */   private final int containerId;
/*    */   
/*    */   public ClientboundContainerClosePacket(int $$0) {
/* 11 */     this.containerId = $$0;
/*    */   }
/*    */   
/*    */   public ClientboundContainerClosePacket(FriendlyByteBuf $$0) {
/* 15 */     this.containerId = $$0.readUnsignedByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 20 */     $$0.writeByte(this.containerId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 25 */     $$0.handleContainerClose(this);
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 29 */     return this.containerId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundContainerClosePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */