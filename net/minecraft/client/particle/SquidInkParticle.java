/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.util.FastColor;
/*    */ 
/*    */ public class SquidInkParticle extends SimpleAnimatedParticle {
/*    */   SquidInkParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, int $$7, SpriteSet $$8) {
/* 10 */     super($$0, $$1, $$2, $$3, $$8, 0.0F);
/* 11 */     this.friction = 0.92F;
/* 12 */     this.quadSize = 0.5F;
/*    */     
/* 14 */     setAlpha(1.0F);
/* 15 */     setColor(FastColor.ARGB32.red($$7), FastColor.ARGB32.green($$7), FastColor.ARGB32.blue($$7));
/*    */     
/* 17 */     this.lifetime = (int)((this.quadSize * 12.0F) / (Math.random() * 0.800000011920929D + 0.20000000298023224D));
/* 18 */     setSpriteFromAge($$8);
/*    */     
/* 20 */     this.hasPhysics = false;
/*    */     
/* 22 */     this.xd = $$4;
/* 23 */     this.yd = $$5;
/* 24 */     this.zd = $$6;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 29 */     super.tick();
/* 30 */     if (!this.removed) {
/* 31 */       setSpriteFromAge(this.sprites);
/* 32 */       if (this.age > this.lifetime / 2) {
/* 33 */         setAlpha(1.0F - (this.age - (this.lifetime / 2)) / this.lifetime);
/*    */       }
/* 35 */       if (this.level.getBlockState(BlockPos.containing(this.x, this.y, this.z)).isAir())
/* 36 */         this.yd -= 0.007400000002235174D; 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Provider
/*    */     implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 45 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 50 */       return new SquidInkParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, FastColor.ARGB32.color(255, 255, 255, 255), this.sprites);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class GlowInkProvider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public GlowInkProvider(SpriteSet $$0) {
/* 58 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 63 */       return new SquidInkParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, FastColor.ARGB32.color(255, 204, 31, 102), this.sprites);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\SquidInkParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */