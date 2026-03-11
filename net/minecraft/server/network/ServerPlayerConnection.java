package net.minecraft.server.network;

import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;

public interface ServerPlayerConnection {
  ServerPlayer getPlayer();
  
  void send(Packet<?> paramPacket);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\ServerPlayerConnection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */