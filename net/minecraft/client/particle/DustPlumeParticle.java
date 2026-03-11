/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.util.FastColor;
/*    */ 
/*    */ public class DustPlumeParticle
/*    */   extends BaseAshSmokeParticle {
/*    */   protected DustPlumeParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, float $$7, SpriteSet $$8) {
/* 11 */     super($$0, $$1, $$2, $$3, 0.7F, 0.6F, 0.7F, $$4, $$5 + 0.15000000596046448D, $$6, $$7, $$8, 0.5F, 7, 0.5F, false);
/* 12 */     float $$9 = (float)Math.random() * 0.2F;
/* 13 */     this.rCol = FastColor.ARGB32.red(12235202) / 255.0F - $$9;
/* 14 */     this.gCol = FastColor.ARGB32.green(12235202) / 255.0F - $$9;
/* 15 */     this.bCol = FastColor.ARGB32.blue(12235202) / 255.0F - $$9;
/*    */   }
/*    */   private static final int COLOR_RGB24 = 12235202;
/*    */   
/*    */   public void tick() {
/* 20 */     this.gravity = 0.88F * this.gravity;
/* 21 */     this.friction = 0.92F * this.friction;
/* 22 */     super.tick();
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 29 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 34 */       return new DustPlumeParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, 1.0F, this.sprites);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\DustPlumeParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */