/*   */ package net.minecraft.network.protocol.login;
/*   */ 
/*   */ import net.minecraft.network.ConnectionProtocol;
/*   */ import net.minecraft.network.protocol.game.ServerPacketListener;
/*   */ 
/*   */ public interface ServerLoginPacketListener
/*   */   extends ServerPacketListener {
/*   */   default ConnectionProtocol protocol() {
/* 9 */     return ConnectionProtocol.LOGIN;
/*   */   }
/*   */   
/*   */   void handleHello(ServerboundHelloPacket paramServerboundHelloPacket);
/*   */   
/*   */   void handleKey(ServerboundKeyPacket paramServerboundKeyPacket);
/*   */   
/*   */   void handleCustomQueryPacket(ServerboundCustomQueryAnswerPacket paramServerboundCustomQueryAnswerPacket);
/*   */   
/*   */   void handleLoginAcknowledgement(ServerboundLoginAcknowledgedPacket paramServerboundLoginAcknowledgedPacket);
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\login\ServerLoginPacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */