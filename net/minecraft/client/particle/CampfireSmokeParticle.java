/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class CampfireSmokeParticle extends TextureSheetParticle {
/*    */   CampfireSmokeParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, boolean $$7) {
/*  8 */     super($$0, $$1, $$2, $$3);
/*  9 */     scale(3.0F);
/* 10 */     setSize(0.25F, 0.25F);
/* 11 */     if ($$7) {
/* 12 */       this.lifetime = this.random.nextInt(50) + 280;
/*    */     } else {
/* 14 */       this.lifetime = this.random.nextInt(50) + 80;
/*    */     } 
/* 16 */     this.gravity = 3.0E-6F;
/* 17 */     this.xd = $$4;
/* 18 */     this.yd = $$5 + (this.random.nextFloat() / 500.0F);
/* 19 */     this.zd = $$6;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 24 */     this.xo = this.x;
/* 25 */     this.yo = this.y;
/* 26 */     this.zo = this.z;
/*    */     
/* 28 */     if (this.age++ >= this.lifetime || this.alpha <= 0.0F) {
/* 29 */       remove();
/*    */       
/*    */       return;
/*    */     } 
/* 33 */     this.xd += (this.random.nextFloat() / 5000.0F * (this.random.nextBoolean() ? true : -1));
/* 34 */     this.zd += (this.random.nextFloat() / 5000.0F * (this.random.nextBoolean() ? true : -1));
/* 35 */     this.yd -= this.gravity;
/*    */     
/* 37 */     move(this.xd, this.yd, this.zd);
/*    */     
/* 39 */     if (this.age >= this.lifetime - 60 && this.alpha > 0.01F) {
/* 40 */       this.alpha -= 0.015F;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 46 */     return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
/*    */   }
/*    */   
/*    */   public static class CosyProvider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public CosyProvider(SpriteSet $$0) {
/* 53 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 58 */       CampfireSmokeParticle $$8 = new CampfireSmokeParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, false);
/* 59 */       $$8.setAlpha(0.9F);
/* 60 */       $$8.pickSprite(this.sprites);
/* 61 */       return $$8;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class SignalProvider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public SignalProvider(SpriteSet $$0) {
/* 69 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 74 */       CampfireSmokeParticle $$8 = new CampfireSmokeParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, true);
/* 75 */       $$8.setAlpha(0.95F);
/* 76 */       $$8.pickSprite(this.sprites);
/* 77 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\CampfireSmokeParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */