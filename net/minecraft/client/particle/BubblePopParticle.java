/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class BubblePopParticle
/*    */   extends TextureSheetParticle {
/*    */   BubblePopParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, SpriteSet $$7) {
/* 10 */     super($$0, $$1, $$2, $$3);
/* 11 */     this.sprites = $$7;
/* 12 */     this.lifetime = 4;
/* 13 */     this.gravity = 0.008F;
/* 14 */     this.xd = $$4;
/* 15 */     this.yd = $$5;
/* 16 */     this.zd = $$6;
/* 17 */     setSpriteFromAge($$7);
/*    */   }
/*    */   private final SpriteSet sprites;
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
/* 31 */     this.yd -= this.gravity;
/* 32 */     move(this.xd, this.yd, this.zd);
/*    */     
/* 34 */     setSpriteFromAge(this.sprites);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 39 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 46 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 51 */       return new BubblePopParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprites);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\BubblePopParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */