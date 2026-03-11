package net.minecraft.client.sounds;

import net.minecraft.util.RandomSource;

public interface Weighted<T> {
  int getWeight();
  
  T getSound(RandomSource paramRandomSource);
  
  void preloadIfRequired(SoundEngine paramSoundEngine);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\Weighted.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */