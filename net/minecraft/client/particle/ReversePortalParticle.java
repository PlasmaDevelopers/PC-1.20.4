/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class ReversePortalParticle extends PortalParticle {
/*    */   ReversePortalParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/*  8 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*    */     
/* 10 */     this.quadSize *= 1.5F;
/* 11 */     this.lifetime = (int)(Math.random() * 2.0D) + 60;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getQuadSize(float $$0) {
/* 16 */     float $$1 = 1.0F - (this.age + $$0) / this.lifetime * 1.5F;
/* 17 */     return this.quadSize * $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 22 */     this.xo = this.x;
/* 23 */     this.yo = this.y;
/* 24 */     this.zo = this.z;
/*    */     
/* 26 */     if (this.age++ >= this.lifetime) {
/* 27 */       remove();
/*    */       
/*    */       return;
/*    */     } 
/* 31 */     float $$0 = this.age / this.lifetime;
/*    */     
/* 33 */     this.x += this.xd * $$0;
/* 34 */     this.y += this.yd * $$0;
/* 35 */     this.z += this.zd * $$0;
/*    */   }
/*    */   
/*    */   public static class ReversePortalProvider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public ReversePortalProvider(SpriteSet $$0) {
/* 42 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 47 */       ReversePortalParticle $$8 = new ReversePortalParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 48 */       $$8.pickSprite(this.sprite);
/* 49 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\ReversePortalParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */