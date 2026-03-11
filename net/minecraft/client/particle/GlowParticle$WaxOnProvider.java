/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WaxOnProvider
/*    */   implements ParticleProvider<SimpleParticleType>
/*    */ {
/* 78 */   private final double SPEED_FACTOR = 0.01D;
/*    */   private final SpriteSet sprite;
/*    */   
/*    */   public WaxOnProvider(SpriteSet $$0) {
/* 82 */     this.sprite = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 87 */     GlowParticle $$8 = new GlowParticle($$1, $$2, $$3, $$4, 0.0D, 0.0D, 0.0D, this.sprite);
/* 88 */     $$8.setColor(0.91F, 0.55F, 0.08F);
/*    */     
/* 90 */     $$8.setParticleSpeed($$5 * 0.01D / 2.0D, $$6 * 0.01D, $$7 * 0.01D / 2.0D);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 96 */     int $$9 = 10;
/* 97 */     int $$10 = 40;
/* 98 */     $$8.setLifetime($$1.random.nextInt(30) + 10);
/* 99 */     return $$8;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\GlowParticle$WaxOnProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */