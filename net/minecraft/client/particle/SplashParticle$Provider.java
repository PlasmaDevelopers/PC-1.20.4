/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
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
/*    */   private final SpriteSet sprite;
/*    */   
/*    */   public Provider(SpriteSet $$0) {
/* 21 */     this.sprite = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 26 */     SplashParticle $$8 = new SplashParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 27 */     $$8.pickSprite(this.sprite);
/* 28 */     return $$8;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\SplashParticle$Provider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */