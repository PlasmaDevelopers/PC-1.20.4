/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class ExplodeParticle
/*    */   extends TextureSheetParticle {
/*    */   protected ExplodeParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, SpriteSet $$7) {
/* 10 */     super($$0, $$1, $$2, $$3);
/* 11 */     this.gravity = -0.1F;
/* 12 */     this.friction = 0.9F;
/* 13 */     this.sprites = $$7;
/* 14 */     this.xd = $$4 + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
/* 15 */     this.yd = $$5 + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
/* 16 */     this.zd = $$6 + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
/*    */     
/* 18 */     float $$8 = this.random.nextFloat() * 0.3F + 0.7F;
/* 19 */     this.rCol = $$8;
/* 20 */     this.gCol = $$8;
/* 21 */     this.bCol = $$8;
/* 22 */     this.quadSize = 0.1F * (this.random.nextFloat() * this.random.nextFloat() * 6.0F + 1.0F);
/*    */     
/* 24 */     this.lifetime = (int)(16.0D / (this.random.nextFloat() * 0.8D + 0.2D)) + 2;
/* 25 */     setSpriteFromAge($$7);
/*    */   }
/*    */   private final SpriteSet sprites;
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 30 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 35 */     super.tick();
/* 36 */     setSpriteFromAge(this.sprites);
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 43 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 48 */       return new ExplodeParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprites);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\ExplodeParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */