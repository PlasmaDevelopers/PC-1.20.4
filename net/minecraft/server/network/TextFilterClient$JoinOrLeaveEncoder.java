package net.minecraft.server.network;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;

@FunctionalInterface
interface JoinOrLeaveEncoder {
  JsonObject encode(GameProfile paramGameProfile);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\TextFilterClient$JoinOrLeaveEncoder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */