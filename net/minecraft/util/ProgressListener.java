package net.minecraft.util;

import net.minecraft.network.chat.Component;

public interface ProgressListener {
  void progressStartNoAbort(Component paramComponent);
  
  void progressStart(Component paramComponent);
  
  void progressStage(Component paramComponent);
  
  void progressStagePercentage(int paramInt);
  
  void stop();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ProgressListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */