package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;

interface Operation {
  ClientboundBossEventPacket.OperationType getType();
  
  void dispatch(UUID paramUUID, ClientboundBossEventPacket.Handler paramHandler);
  
  void write(FriendlyByteBuf paramFriendlyByteBuf);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundBossEventPacket$Operation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */