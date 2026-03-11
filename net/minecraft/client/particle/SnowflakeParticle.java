/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class SnowflakeParticle
/*    */   extends TextureSheetParticle {
/*    */   protected SnowflakeParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, SpriteSet $$7) {
/* 10 */     super($$0, $$1, $$2, $$3);
/* 11 */     this.gravity = 0.225F;
/* 12 */     this.friction = 1.0F;
/* 13 */     this.sprites = $$7;
/* 14 */     this.xd = $$4 + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
/* 15 */     this.yd = $$5 + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
/* 16 */     this.zd = $$6 + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
/*    */     
/* 18 */     this.quadSize = 0.1F * (this.random.nextFloat() * this.random.nextFloat() * 1.0F + 1.0F);
/*    */     
/* 20 */     this.lifetime = (int)(16.0D / (this.random.nextFloat() * 0.8D + 0.2D)) + 2;
/* 21 */     setSpriteFromAge($$7);
/*    */   }
/*    */   private final SpriteSet sprites;
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 26 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 31 */     super.tick();
/* 32 */     setSpriteFromAge(this.sprites);
/*    */     
/* 34 */     this.xd *= 0.949999988079071D;
/* 35 */     this.yd *= 0.8999999761581421D;
/* 36 */     this.zd *= 0.949999988079071D;
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
/* 48 */       SnowflakeParticle $$8 = new SnowflakeParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprites);
/* 49 */       $$8.setColor(0.923F, 0.964F, 0.999F);
/* 50 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\SnowflakeParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */