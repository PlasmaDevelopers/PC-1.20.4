package net.minecraft.commands.execution;

import net.minecraft.resources.ResourceLocation;

public interface TraceCallbacks extends AutoCloseable {
  void onCommand(int paramInt, String paramString);
  
  void onReturn(int paramInt1, String paramString, int paramInt2);
  
  void onError(String paramString);
  
  void onCall(int paramInt1, ResourceLocation paramResourceLocation, int paramInt2);
  
  void close();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\TraceCallbacks.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */