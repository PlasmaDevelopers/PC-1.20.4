package net.minecraft.client.resources.server;

import java.util.List;

public interface Callbacks {
  void onSuccess();
  
  void onFailure(boolean paramBoolean);
  
  List<PackReloadConfig.IdAndPath> packsToLoad();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\server\PackReloadConfig$Callbacks.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */