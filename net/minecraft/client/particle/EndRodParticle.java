/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class EndRodParticle extends SimpleAnimatedParticle {
/*    */   EndRodParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, SpriteSet $$7) {
/*  8 */     super($$0, $$1, $$2, $$3, $$7, 0.0125F);
/*    */     
/* 10 */     this.xd = $$4;
/* 11 */     this.yd = $$5;
/* 12 */     this.zd = $$6;
/*    */     
/* 14 */     this.quadSize *= 0.75F;
/*    */     
/* 16 */     this.lifetime = 60 + this.random.nextInt(12);
/*    */     
/* 18 */     setFadeColor(15916745);
/* 19 */     setSpriteFromAge($$7);
/*    */   }
/*    */ 
/*    */   
/*    */   public void move(double $$0, double $$1, double $$2) {
/* 24 */     setBoundingBox(getBoundingBox().move($$0, $$1, $$2));
/* 25 */     setLocationFromBoundingbox();
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 32 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 37 */       return new EndRodParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprites);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\EndRodParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */