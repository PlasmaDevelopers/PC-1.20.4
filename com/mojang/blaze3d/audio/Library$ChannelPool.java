package com.mojang.blaze3d.audio;

import javax.annotation.Nullable;

interface ChannelPool {
  @Nullable
  Channel acquire();
  
  boolean release(Channel paramChannel);
  
  void cleanup();
  
  int getMaxCount();
  
  int getUsedCount();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\audio\Library$ChannelPool.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */