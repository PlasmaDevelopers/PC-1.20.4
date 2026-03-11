package net.minecraft.network.protocol.login.custom;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface CustomQueryPayload {
  ResourceLocation id();
  
  void write(FriendlyByteBuf paramFriendlyByteBuf);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\login\custom\CustomQueryPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */