/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ServerboundContainerClosePacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   public ServerboundContainerClosePacket(int $$0) {
/* 10 */     this.containerId = $$0;
/*    */   }
/*    */   private final int containerId;
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 15 */     $$0.handleContainerClose(this);
/*    */   }
/*    */   
/*    */   public ServerboundContainerClosePacket(FriendlyByteBuf $$0) {
/* 19 */     this.containerId = $$0.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 24 */     $$0.writeByte(this.containerId);
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 28 */     return this.containerId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundContainerClosePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */