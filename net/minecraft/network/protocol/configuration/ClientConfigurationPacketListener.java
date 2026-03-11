/*   */ package net.minecraft.network.protocol.configuration;
/*   */ 
/*   */ import net.minecraft.network.ConnectionProtocol;
/*   */ import net.minecraft.network.protocol.common.ClientCommonPacketListener;
/*   */ 
/*   */ public interface ClientConfigurationPacketListener
/*   */   extends ClientCommonPacketListener {
/*   */   default ConnectionProtocol protocol() {
/* 9 */     return ConnectionProtocol.CONFIGURATION;
/*   */   }
/*   */   
/*   */   void handleConfigurationFinished(ClientboundFinishConfigurationPacket paramClientboundFinishConfigurationPacket);
/*   */   
/*   */   void handleRegistryData(ClientboundRegistryDataPacket paramClientboundRegistryDataPacket);
/*   */   
/*   */   void handleEnabledFeatures(ClientboundUpdateEnabledFeaturesPacket paramClientboundUpdateEnabledFeaturesPacket);
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\configuration\ClientConfigurationPacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */