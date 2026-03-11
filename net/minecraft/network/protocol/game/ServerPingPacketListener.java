package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;

public interface ServerPingPacketListener extends PacketListener {
  void handlePingRequest(ServerboundPingRequestPacket paramServerboundPingRequestPacket);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerPingPacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */