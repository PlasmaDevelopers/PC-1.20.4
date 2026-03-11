/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class TrialSpawnerDetectionParticle extends TextureSheetParticle {
/*    */   private final SpriteSet sprites;
/*    */   private static final int BASE_LIFETIME = 8;
/*    */   
/*    */   protected TrialSpawnerDetectionParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, float $$7, SpriteSet $$8) {
/* 13 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/* 14 */     this.sprites = $$8;
/* 15 */     this.friction = 0.96F;
/* 16 */     this.gravity = -0.1F;
/* 17 */     this.speedUpWhenYMotionIsBlocked = true;
/*    */     
/* 19 */     this.xd *= 0.0D;
/* 20 */     this.yd *= 0.9D;
/* 21 */     this.zd *= 0.0D;
/* 22 */     this.xd += $$4;
/* 23 */     this.yd += $$5;
/* 24 */     this.zd += $$6;
/*    */     
/* 26 */     this.quadSize *= 0.75F * $$7;
/*    */     
/* 28 */     this.lifetime = (int)(8.0F / Mth.randomBetween(this.random, 0.5F, 1.0F) * $$7);
/* 29 */     this.lifetime = Math.max(this.lifetime, 1);
/* 30 */     setSpriteFromAge($$8);
/* 31 */     this.hasPhysics = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 36 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightColor(float $$0) {
/* 41 */     return 240;
/*    */   }
/*    */ 
/*    */   
/*    */   public SingleQuadParticle.FacingCameraMode getFacingCameraMode() {
/* 46 */     return SingleQuadParticle.FacingCameraMode.LOOKAT_Y;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 51 */     super.tick();
/* 52 */     setSpriteFromAge(this.sprites);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getQuadSize(float $$0) {
/* 57 */     return this.quadSize * Mth.clamp((this.age + $$0) / this.lifetime * 32.0F, 0.0F, 1.0F);
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 64 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 69 */       return new TrialSpawnerDetectionParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, 1.5F, this.sprites);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\TrialSpawnerDetectionParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */