package net.minecraft.client.sounds;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface AudioStreamProvider {
  AudioStream create(InputStream paramInputStream) throws IOException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\LoopingAudioStream$AudioStreamProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */