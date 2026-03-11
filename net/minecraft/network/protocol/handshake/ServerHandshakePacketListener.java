/*   */ package net.minecraft.network.protocol.handshake;
/*   */ 
/*   */ import net.minecraft.network.ConnectionProtocol;
/*   */ import net.minecraft.network.protocol.game.ServerPacketListener;
/*   */ 
/*   */ public interface ServerHandshakePacketListener
/*   */   extends ServerPacketListener {
/*   */   default ConnectionProtocol protocol() {
/* 9 */     return ConnectionProtocol.HANDSHAKING;
/*   */   }
/*   */   
/*   */   void handleIntention(ClientIntentionPacket paramClientIntentionPacket);
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\handshake\ServerHandshakePacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */