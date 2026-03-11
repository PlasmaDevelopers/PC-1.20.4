/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class WhiteSmokeParticle extends BaseAshSmokeParticle {
/*    */   private static final int COLOR_RGB24 = 12235202;
/*    */   
/*    */   protected WhiteSmokeParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, float $$7, SpriteSet $$8) {
/* 11 */     super($$0, $$1, $$2, $$3, 0.1F, 0.1F, 0.1F, $$4, $$5, $$6, $$7, $$8, 0.3F, 8, -0.1F, true);
/* 12 */     this.rCol = 0.7294118F;
/* 13 */     this.gCol = 0.69411767F;
/* 14 */     this.bCol = 0.7607843F;
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 21 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 26 */       return new WhiteSmokeParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, 1.0F, this.sprites);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\WhiteSmokeParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */