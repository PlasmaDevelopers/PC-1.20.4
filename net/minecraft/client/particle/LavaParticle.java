/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class LavaParticle extends TextureSheetParticle {
/*    */   LavaParticle(ClientLevel $$0, double $$1, double $$2, double $$3) {
/*  9 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/* 10 */     this.gravity = 0.75F;
/* 11 */     this.friction = 0.999F;
/* 12 */     this.xd *= 0.800000011920929D;
/* 13 */     this.yd *= 0.800000011920929D;
/* 14 */     this.zd *= 0.800000011920929D;
/* 15 */     this.yd = (this.random.nextFloat() * 0.4F + 0.05F);
/*    */     
/* 17 */     this.quadSize *= this.random.nextFloat() * 2.0F + 0.2F;
/*    */     
/* 19 */     this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 24 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightColor(float $$0) {
/* 29 */     int $$1 = super.getLightColor($$0);
/*    */     
/* 31 */     int $$2 = 240;
/* 32 */     int $$3 = $$1 >> 16 & 0xFF;
/* 33 */     return 0xF0 | $$3 << 16;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getQuadSize(float $$0) {
/* 38 */     float $$1 = (this.age + $$0) / this.lifetime;
/* 39 */     return this.quadSize * (1.0F - $$1 * $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 44 */     super.tick();
/* 45 */     if (!this.removed) {
/* 46 */       float $$0 = this.age / this.lifetime;
/* 47 */       if (this.random.nextFloat() > $$0)
/* 48 */         this.level.addParticle((ParticleOptions)ParticleTypes.SMOKE, this.x, this.y, this.z, this.xd, this.yd, this.zd); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Provider
/*    */     implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 57 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 62 */       LavaParticle $$8 = new LavaParticle($$1, $$2, $$3, $$4);
/* 63 */       $$8.pickSprite(this.sprite);
/* 64 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\LavaParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */