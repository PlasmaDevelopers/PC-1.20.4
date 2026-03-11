/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class HeartParticle extends TextureSheetParticle {
/*    */   HeartParticle(ClientLevel $$0, double $$1, double $$2, double $$3) {
/*  9 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/* 10 */     this.speedUpWhenYMotionIsBlocked = true;
/* 11 */     this.friction = 0.86F;
/* 12 */     this.xd *= 0.009999999776482582D;
/* 13 */     this.yd *= 0.009999999776482582D;
/* 14 */     this.zd *= 0.009999999776482582D;
/* 15 */     this.yd += 0.1D;
/*    */     
/* 17 */     this.quadSize *= 1.5F;
/* 18 */     this.lifetime = 16;
/* 19 */     this.hasPhysics = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 24 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getQuadSize(float $$0) {
/* 29 */     return this.quadSize * Mth.clamp((this.age + $$0) / this.lifetime * 32.0F, 0.0F, 1.0F);
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 36 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 41 */       HeartParticle $$8 = new HeartParticle($$1, $$2, $$3, $$4);
/* 42 */       $$8.pickSprite(this.sprite);
/* 43 */       return $$8;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class AngryVillagerProvider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public AngryVillagerProvider(SpriteSet $$0) {
/* 51 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 56 */       HeartParticle $$8 = new HeartParticle($$1, $$2, $$3 + 0.5D, $$4);
/* 57 */       $$8.pickSprite(this.sprite);
/* 58 */       $$8.setColor(1.0F, 1.0F, 1.0F);
/* 59 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\HeartParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */