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
/*     */ public class ElectricSparkProvider
/*     */   implements ParticleProvider<SimpleParticleType>
/*     */ {
/* 130 */   private final double SPEED_FACTOR = 0.25D;
/*     */   private final SpriteSet sprite;
/*     */   
/*     */   public ElectricSparkProvider(SpriteSet $$0) {
/* 134 */     this.sprite = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 139 */     GlowParticle $$8 = new GlowParticle($$1, $$2, $$3, $$4, 0.0D, 0.0D, 0.0D, this.sprite);
/* 140 */     $$8.setColor(1.0F, 0.9F, 1.0F);
/*     */     
/* 142 */     $$8.setParticleSpeed($$5 * 0.25D, $$6 * 0.25D, $$7 * 0.25D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     int $$9 = 2;
/* 149 */     int $$10 = 4;
/* 150 */     $$8.setLifetime($$1.random.nextInt(2) + 2);
/* 151 */     return $$8;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\GlowParticle$ElectricSparkProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */