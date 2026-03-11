package net.minecraft.network.protocol.common.custom;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface CustomPacketPayload {
  void write(FriendlyByteBuf paramFriendlyByteBuf);
  
  ResourceLocation id();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\CustomPacketPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */