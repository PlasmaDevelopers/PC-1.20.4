package net.minecraft.util;

import java.util.OptionalLong;

public interface DownloadProgressListener {
  void requestStart();
  
  void downloadStart(OptionalLong paramOptionalLong);
  
  void downloadedBytes(long paramLong);
  
  void requestFinished(boolean paramBoolean);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\HttpUtil$DownloadProgressListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */