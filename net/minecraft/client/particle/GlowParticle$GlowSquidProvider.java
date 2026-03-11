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
/*    */ public class GlowSquidProvider
/*    */   implements ParticleProvider<SimpleParticleType>
/*    */ {
/*    */   private final SpriteSet sprite;
/*    */   
/*    */   public GlowSquidProvider(SpriteSet $$0) {
/* 56 */     this.sprite = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 61 */     GlowParticle $$8 = new GlowParticle($$1, $$2, $$3, $$4, 0.5D - GlowParticle.RANDOM.nextDouble(), $$6, 0.5D - GlowParticle.RANDOM.nextDouble(), this.sprite);
/* 62 */     if ($$1.random.nextBoolean()) {
/* 63 */       $$8.setColor(0.6F, 1.0F, 0.8F);
/*    */     } else {
/* 65 */       $$8.setColor(0.08F, 0.4F, 0.4F);
/*    */     } 
/* 67 */     $$8.yd *= 0.20000000298023224D;
/* 68 */     if ($$5 == 0.0D && $$7 == 0.0D) {
/* 69 */       $$8.xd *= 0.10000000149011612D;
/* 70 */       $$8.zd *= 0.10000000149011612D;
/*    */     } 
/* 72 */     $$8.setLifetime((int)(8.0D / ($$1.random.nextDouble() * 0.8D + 0.2D)));
/* 73 */     return $$8;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\GlowParticle$GlowSquidProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */