/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class HugeExplosionParticle extends TextureSheetParticle {
/*    */   private final SpriteSet sprites;
/*    */   
/*    */   protected HugeExplosionParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, SpriteSet $$5) {
/* 11 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/* 12 */     this.lifetime = 6 + this.random.nextInt(4);
/* 13 */     float $$6 = this.random.nextFloat() * 0.6F + 0.4F;
/* 14 */     this.rCol = $$6;
/* 15 */     this.gCol = $$6;
/* 16 */     this.bCol = $$6;
/* 17 */     this.quadSize = 2.0F * (1.0F - (float)$$4 * 0.5F);
/* 18 */     this.sprites = $$5;
/* 19 */     setSpriteFromAge($$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightColor(float $$0) {
/* 24 */     return 15728880;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 29 */     this.xo = this.x;
/* 30 */     this.yo = this.y;
/* 31 */     this.zo = this.z;
/*    */     
/* 33 */     if (this.age++ >= this.lifetime) {
/* 34 */       remove();
/*    */       
/*    */       return;
/*    */     } 
/* 38 */     setSpriteFromAge(this.sprites);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 43 */     return ParticleRenderType.PARTICLE_SHEET_LIT;
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 50 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 55 */       return new HugeExplosionParticle($$1, $$2, $$3, $$4, $$5, this.sprites);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\HugeExplosionParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */