package net.minecraft.network;

import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;

public interface ClientPongPacketListener extends PacketListener {
  void handlePongResponse(ClientboundPongResponsePacket paramClientboundPongResponsePacket);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\ClientPongPacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */