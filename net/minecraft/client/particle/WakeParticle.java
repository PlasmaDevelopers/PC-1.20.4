/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class WakeParticle
/*    */   extends TextureSheetParticle {
/*    */   WakeParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, SpriteSet $$7) {
/* 10 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/* 11 */     this.sprites = $$7;
/* 12 */     this.xd *= 0.30000001192092896D;
/* 13 */     this.yd = Math.random() * 0.20000000298023224D + 0.10000000149011612D;
/* 14 */     this.zd *= 0.30000001192092896D;
/*    */     
/* 16 */     setSize(0.01F, 0.01F);
/*    */     
/* 18 */     this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/* 19 */     setSpriteFromAge($$7);
/* 20 */     this.gravity = 0.0F;
/* 21 */     this.xd = $$4;
/* 22 */     this.yd = $$5;
/* 23 */     this.zd = $$6;
/*    */   }
/*    */   private final SpriteSet sprites;
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 28 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 33 */     this.xo = this.x;
/* 34 */     this.yo = this.y;
/* 35 */     this.zo = this.z;
/*    */     
/* 37 */     int $$0 = 60 - this.lifetime;
/* 38 */     if (this.lifetime-- <= 0) {
/* 39 */       remove();
/*    */       
/*    */       return;
/*    */     } 
/* 43 */     this.yd -= this.gravity;
/* 44 */     move(this.xd, this.yd, this.zd);
/* 45 */     this.xd *= 0.9800000190734863D;
/* 46 */     this.yd *= 0.9800000190734863D;
/* 47 */     this.zd *= 0.9800000190734863D;
/*    */     
/* 49 */     float $$1 = $$0 * 0.001F;
/* 50 */     setSize($$1, $$1);
/*    */     
/* 52 */     setSprite(this.sprites.get($$0 % 4, 4));
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 59 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 64 */       return new WakeParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprites);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\WakeParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */