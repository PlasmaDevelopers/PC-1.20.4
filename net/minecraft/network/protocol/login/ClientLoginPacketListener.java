/*   */ package net.minecraft.network.protocol.login;
/*   */ 
/*   */ import net.minecraft.network.ClientboundPacketListener;
/*   */ import net.minecraft.network.ConnectionProtocol;
/*   */ 
/*   */ public interface ClientLoginPacketListener
/*   */   extends ClientboundPacketListener {
/*   */   default ConnectionProtocol protocol() {
/* 9 */     return ConnectionProtocol.LOGIN;
/*   */   }
/*   */   
/*   */   void handleHello(ClientboundHelloPacket paramClientboundHelloPacket);
/*   */   
/*   */   void handleGameProfile(ClientboundGameProfilePacket paramClientboundGameProfilePacket);
/*   */   
/*   */   void handleDisconnect(ClientboundLoginDisconnectPacket paramClientboundLoginDisconnectPacket);
/*   */   
/*   */   void handleCompression(ClientboundLoginCompressionPacket paramClientboundLoginCompressionPacket);
/*   */   
/*   */   void handleCustomQuery(ClientboundCustomQueryPacket paramClientboundCustomQueryPacket);
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\login\ClientLoginPacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */