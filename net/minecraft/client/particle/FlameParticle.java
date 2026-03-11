/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class FlameParticle extends RisingParticle {
/*    */   FlameParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/*  9 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 14 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void move(double $$0, double $$1, double $$2) {
/* 19 */     setBoundingBox(getBoundingBox().move($$0, $$1, $$2));
/* 20 */     setLocationFromBoundingbox();
/*    */   }
/*    */ 
/*    */   
/*    */   public float getQuadSize(float $$0) {
/* 25 */     float $$1 = (this.age + $$0) / this.lifetime;
/* 26 */     return this.quadSize * (1.0F - $$1 * $$1 * 0.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightColor(float $$0) {
/* 31 */     float $$1 = (this.age + $$0) / this.lifetime;
/* 32 */     $$1 = Mth.clamp($$1, 0.0F, 1.0F);
/* 33 */     int $$2 = super.getLightColor($$0);
/*    */     
/* 35 */     int $$3 = $$2 & 0xFF;
/* 36 */     int $$4 = $$2 >> 16 & 0xFF;
/* 37 */     $$3 += (int)($$1 * 15.0F * 16.0F);
/* 38 */     if ($$3 > 240) {
/* 39 */       $$3 = 240;
/*    */     }
/* 41 */     return $$3 | $$4 << 16;
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 48 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 53 */       FlameParticle $$8 = new FlameParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 54 */       $$8.pickSprite(this.sprite);
/* 55 */       return $$8;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class SmallFlameProvider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public SmallFlameProvider(SpriteSet $$0) {
/* 63 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 68 */       FlameParticle $$8 = new FlameParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 69 */       $$8.pickSprite(this.sprite);
/* 70 */       $$8.scale(0.5F);
/* 71 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\FlameParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */