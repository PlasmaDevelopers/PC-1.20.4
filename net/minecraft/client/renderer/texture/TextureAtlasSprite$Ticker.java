package net.minecraft.client.renderer.texture;

public interface Ticker extends AutoCloseable {
  void tickAndUpload();
  
  void close();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\TextureAtlasSprite$Ticker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */