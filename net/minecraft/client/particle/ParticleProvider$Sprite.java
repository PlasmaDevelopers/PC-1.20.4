package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;

public interface Sprite<T extends net.minecraft.core.particles.ParticleOptions> {
  @Nullable
  TextureSheetParticle createParticle(T paramT, ClientLevel paramClientLevel, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\ParticleProvider$Sprite.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */