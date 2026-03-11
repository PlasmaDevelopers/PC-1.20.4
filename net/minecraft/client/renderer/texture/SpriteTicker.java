package net.minecraft.client.renderer.texture;

public interface SpriteTicker extends AutoCloseable {
  void tickAndUpload(int paramInt1, int paramInt2);
  
  void close();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\SpriteTicker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */