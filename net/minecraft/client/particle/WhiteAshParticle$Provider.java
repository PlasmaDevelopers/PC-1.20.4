/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Provider
/*    */   implements ParticleProvider<SimpleParticleType>
/*    */ {
/*    */   private final SpriteSet sprites;
/*    */   
/*    */   public Provider(SpriteSet $$0) {
/* 22 */     this.sprites = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 27 */     RandomSource $$8 = $$1.random;
/* 28 */     double $$9 = $$8.nextFloat() * -1.9D * $$8.nextFloat() * 0.1D;
/* 29 */     double $$10 = $$8.nextFloat() * -0.5D * $$8.nextFloat() * 0.1D * 5.0D;
/* 30 */     double $$11 = $$8.nextFloat() * -1.9D * $$8.nextFloat() * 0.1D;
/* 31 */     return new WhiteAshParticle($$1, $$2, $$3, $$4, $$9, $$10, $$11, 1.0F, this.sprites);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\WhiteAshParticle$Provider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */