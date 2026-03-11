package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;

interface Action {
  ServerboundInteractPacket.ActionType getType();
  
  void dispatch(ServerboundInteractPacket.Handler paramHandler);
  
  void write(FriendlyByteBuf paramFriendlyByteBuf);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundInteractPacket$Action.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */