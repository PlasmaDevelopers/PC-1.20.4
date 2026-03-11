/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaxOffProvider
/*     */   implements ParticleProvider<SimpleParticleType>
/*     */ {
/* 104 */   private final double SPEED_FACTOR = 0.01D;
/*     */   private final SpriteSet sprite;
/*     */   
/*     */   public WaxOffProvider(SpriteSet $$0) {
/* 108 */     this.sprite = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 113 */     GlowParticle $$8 = new GlowParticle($$1, $$2, $$3, $$4, 0.0D, 0.0D, 0.0D, this.sprite);
/* 114 */     $$8.setColor(1.0F, 0.9F, 1.0F);
/*     */     
/* 116 */     $$8.setParticleSpeed($$5 * 0.01D / 2.0D, $$6 * 0.01D, $$7 * 0.01D / 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     int $$9 = 10;
/* 123 */     int $$10 = 40;
/* 124 */     $$8.setLifetime($$1.random.nextInt(30) + 10);
/* 125 */     return $$8;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\GlowParticle$WaxOffProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */