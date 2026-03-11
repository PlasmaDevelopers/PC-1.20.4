package net.minecraft.client.particle;

@FunctionalInterface
interface SpriteParticleRegistration<T extends net.minecraft.core.particles.ParticleOptions> {
  ParticleProvider<T> create(SpriteSet paramSpriteSet);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\ParticleEngine$SpriteParticleRegistration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */