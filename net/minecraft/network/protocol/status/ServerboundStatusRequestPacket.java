/*    */ package net.minecraft.network.protocol.status;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerboundStatusRequestPacket
/*    */   implements Packet<ServerStatusPacketListener>
/*    */ {
/*    */   public ServerboundStatusRequestPacket() {}
/*    */   
/*    */   public ServerboundStatusRequestPacket(FriendlyByteBuf $$0) {}
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {}
/*    */   
/*    */   public void handle(ServerStatusPacketListener $$0) {
/* 19 */     $$0.handleStatusRequest(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\status\ServerboundStatusRequestPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */