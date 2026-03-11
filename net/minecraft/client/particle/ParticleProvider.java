package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;

public interface ParticleProvider<T extends net.minecraft.core.particles.ParticleOptions> {
  @Nullable
  Particle createParticle(T paramT, ClientLevel paramClientLevel, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
  
  public static interface Sprite<T extends net.minecraft.core.particles.ParticleOptions> {
    @Nullable
    TextureSheetParticle createParticle(T param1T, ClientLevel param1ClientLevel, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6);
  }
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\ParticleProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */