package net.minecraft.client.sounds;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;

public interface AudioStream extends Closeable {
  AudioFormat getFormat();
  
  ByteBuffer read(int paramInt) throws IOException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\AudioStream.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */