package net.minecraft.network.protocol.common;

import net.minecraft.network.protocol.game.ServerPacketListener;

public interface ServerCommonPacketListener extends ServerPacketListener {
  void handleKeepAlive(ServerboundKeepAlivePacket paramServerboundKeepAlivePacket);
  
  void handlePong(ServerboundPongPacket paramServerboundPongPacket);
  
  void handleCustomPayload(ServerboundCustomPayloadPacket paramServerboundCustomPayloadPacket);
  
  void handleResourcePackResponse(ServerboundResourcePackPacket paramServerboundResourcePackPacket);
  
  void handleClientInformation(ServerboundClientInformationPacket paramServerboundClientInformationPacket);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\ServerCommonPacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */