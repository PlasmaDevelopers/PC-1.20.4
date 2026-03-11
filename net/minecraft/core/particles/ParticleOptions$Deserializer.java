package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.FriendlyByteBuf;

@Deprecated
public interface Deserializer<T extends ParticleOptions> {
  T fromCommand(ParticleType<T> paramParticleType, StringReader paramStringReader) throws CommandSyntaxException;
  
  T fromNetwork(ParticleType<T> paramParticleType, FriendlyByteBuf paramFriendlyByteBuf);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\ParticleOptions$Deserializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */