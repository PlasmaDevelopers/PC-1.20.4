/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class CritParticle extends TextureSheetParticle {
/*    */   CritParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/*  9 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/* 10 */     this.friction = 0.7F;
/* 11 */     this.gravity = 0.5F;
/* 12 */     this.xd *= 0.10000000149011612D;
/* 13 */     this.yd *= 0.10000000149011612D;
/* 14 */     this.zd *= 0.10000000149011612D;
/* 15 */     this.xd += $$4 * 0.4D;
/* 16 */     this.yd += $$5 * 0.4D;
/* 17 */     this.zd += $$6 * 0.4D;
/*    */     
/* 19 */     float $$7 = (float)(Math.random() * 0.30000001192092896D + 0.6000000238418579D);
/* 20 */     this.rCol = $$7;
/* 21 */     this.gCol = $$7;
/* 22 */     this.bCol = $$7;
/* 23 */     this.quadSize *= 0.75F;
/*    */     
/* 25 */     this.lifetime = Math.max((int)(6.0D / (Math.random() * 0.8D + 0.6D)), 1);
/*    */     
/* 27 */     this.hasPhysics = false;
/* 28 */     tick();
/*    */   }
/*    */ 
/*    */   
/*    */   public float getQuadSize(float $$0) {
/* 33 */     return this.quadSize * Mth.clamp((this.age + $$0) / this.lifetime * 32.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 38 */     super.tick();
/* 39 */     this.gCol *= 0.96F;
/* 40 */     this.bCol *= 0.9F;
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 45 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 52 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 57 */       CritParticle $$8 = new CritParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 58 */       $$8.pickSprite(this.sprite);
/* 59 */       return $$8;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class MagicProvider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public MagicProvider(SpriteSet $$0) {
/* 67 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 72 */       CritParticle $$8 = new CritParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 73 */       $$8.rCol *= 0.3F;
/* 74 */       $$8.gCol *= 0.8F;
/* 75 */       $$8.pickSprite(this.sprite);
/* 76 */       return $$8;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class DamageIndicatorProvider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public DamageIndicatorProvider(SpriteSet $$0) {
/* 84 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 89 */       CritParticle $$8 = new CritParticle($$1, $$2, $$3, $$4, $$5, $$6 + 1.0D, $$7);
/* 90 */       $$8.setLifetime(20);
/* 91 */       $$8.pickSprite(this.sprite);
/* 92 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\CritParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */