package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.FriendlyByteBuf;

public interface ParticleOptions {
  ParticleType<?> getType();
  
  void writeToNetwork(FriendlyByteBuf paramFriendlyByteBuf);
  
  String writeToString();
  
  @Deprecated
  public static interface Deserializer<T extends ParticleOptions> {
    T fromCommand(ParticleType<T> param1ParticleType, StringReader param1StringReader) throws CommandSyntaxException;
    
    T fromNetwork(ParticleType<T> param1ParticleType, FriendlyByteBuf param1FriendlyByteBuf);
  }
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\ParticleOptions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */