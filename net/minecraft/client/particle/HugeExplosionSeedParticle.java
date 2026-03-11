/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class HugeExplosionSeedParticle extends NoRenderParticle {
/*    */   HugeExplosionSeedParticle(ClientLevel $$0, double $$1, double $$2, double $$3) {
/*  9 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/* 10 */     this.lifetime = 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 15 */     for (int $$0 = 0; $$0 < 6; $$0++) {
/* 16 */       double $$1 = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 4.0D;
/* 17 */       double $$2 = this.y + (this.random.nextDouble() - this.random.nextDouble()) * 4.0D;
/* 18 */       double $$3 = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 4.0D;
/* 19 */       this.level.addParticle((ParticleOptions)ParticleTypes.EXPLOSION, $$1, $$2, $$3, (this.age / this.lifetime), 0.0D, 0.0D);
/*    */     } 
/* 21 */     this.age++;
/* 22 */     if (this.age == this.lifetime)
/* 23 */       remove(); 
/*    */   }
/*    */   
/*    */   public static class Provider
/*    */     implements ParticleProvider<SimpleParticleType>
/*    */   {
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 30 */       return new HugeExplosionSeedParticle($$1, $$2, $$3, $$4);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\HugeExplosionSeedParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */