/*    */ package net.minecraft.network.protocol.status;
/*    */ 
/*    */ import net.minecraft.network.ConnectionProtocol;
/*    */ import net.minecraft.network.protocol.game.ServerPacketListener;
/*    */ import net.minecraft.network.protocol.game.ServerPingPacketListener;
/*    */ 
/*    */ public interface ServerStatusPacketListener
/*    */   extends ServerPacketListener, ServerPingPacketListener {
/*    */   default ConnectionProtocol protocol() {
/* 10 */     return ConnectionProtocol.STATUS;
/*    */   }
/*    */   
/*    */   void handleStatusRequest(ServerboundStatusRequestPacket paramServerboundStatusRequestPacket);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\status\ServerStatusPacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */