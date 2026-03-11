package net.minecraft.client.resources.sounds;

public interface TickableSoundInstance extends SoundInstance {
  boolean isStopped();
  
  void tick();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\TickableSoundInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */