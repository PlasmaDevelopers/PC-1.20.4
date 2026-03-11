/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class TotemParticle extends SimpleAnimatedParticle {
/*    */   TotemParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, SpriteSet $$7) {
/*  8 */     super($$0, $$1, $$2, $$3, $$7, 1.25F);
/*    */     
/* 10 */     this.friction = 0.6F;
/*    */     
/* 12 */     this.xd = $$4;
/* 13 */     this.yd = $$5;
/* 14 */     this.zd = $$6;
/*    */     
/* 16 */     this.quadSize *= 0.75F;
/*    */     
/* 18 */     this.lifetime = 60 + this.random.nextInt(12);
/* 19 */     setSpriteFromAge($$7);
/*    */     
/* 21 */     if (this.random.nextInt(4) == 0) {
/* 22 */       setColor(0.6F + this.random.nextFloat() * 0.2F, 0.6F + this.random.nextFloat() * 0.3F, this.random.nextFloat() * 0.2F);
/*    */     } else {
/* 24 */       setColor(0.1F + this.random.nextFloat() * 0.2F, 0.4F + this.random.nextFloat() * 0.3F, this.random.nextFloat() * 0.2F);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 32 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 37 */       return new TotemParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprites);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\TotemParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */