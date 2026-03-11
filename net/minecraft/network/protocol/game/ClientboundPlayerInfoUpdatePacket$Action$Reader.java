package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;

public interface Reader {
  void read(ClientboundPlayerInfoUpdatePacket.EntryBuilder paramEntryBuilder, FriendlyByteBuf paramFriendlyByteBuf);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundPlayerInfoUpdatePacket$Action$Reader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */