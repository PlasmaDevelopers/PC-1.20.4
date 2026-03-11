package net.minecraft.network.protocol.common;

import net.minecraft.network.ClientboundPacketListener;

public interface ClientCommonPacketListener extends ClientboundPacketListener {
  void handleKeepAlive(ClientboundKeepAlivePacket paramClientboundKeepAlivePacket);
  
  void handlePing(ClientboundPingPacket paramClientboundPingPacket);
  
  void handleCustomPayload(ClientboundCustomPayloadPacket paramClientboundCustomPayloadPacket);
  
  void handleDisconnect(ClientboundDisconnectPacket paramClientboundDisconnectPacket);
  
  void handleResourcePackPush(ClientboundResourcePackPushPacket paramClientboundResourcePackPushPacket);
  
  void handleResourcePackPop(ClientboundResourcePackPopPacket paramClientboundResourcePackPopPacket);
  
  void handleUpdateTags(ClientboundUpdateTagsPacket paramClientboundUpdateTagsPacket);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ClientCommonPacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */