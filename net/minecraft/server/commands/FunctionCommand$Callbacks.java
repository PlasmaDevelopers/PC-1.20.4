package net.minecraft.server.commands;

import net.minecraft.resources.ResourceLocation;

public interface Callbacks<T> {
  void signalResult(T paramT, ResourceLocation paramResourceLocation, int paramInt);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\FunctionCommand$Callbacks.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */