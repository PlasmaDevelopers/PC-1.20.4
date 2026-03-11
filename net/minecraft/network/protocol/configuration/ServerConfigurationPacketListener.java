/*   */ package net.minecraft.network.protocol.configuration;
/*   */ 
/*   */ import net.minecraft.network.ConnectionProtocol;
/*   */ import net.minecraft.network.protocol.common.ServerCommonPacketListener;
/*   */ 
/*   */ public interface ServerConfigurationPacketListener
/*   */   extends ServerCommonPacketListener {
/*   */   default ConnectionProtocol protocol() {
/* 9 */     return ConnectionProtocol.CONFIGURATION;
/*   */   }
/*   */   
/*   */   void handleConfigurationFinished(ServerboundFinishConfigurationPacket paramServerboundFinishConfigurationPacket);
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\configuration\ServerConfigurationPacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */