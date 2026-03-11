package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;

public interface Writer {
  void write(FriendlyByteBuf paramFriendlyByteBuf, ClientboundPlayerInfoUpdatePacket.Entry paramEntry);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundPlayerInfoUpdatePacket$Action$Writer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */