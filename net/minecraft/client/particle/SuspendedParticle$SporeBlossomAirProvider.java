/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleGroup;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.util.Mth;
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
/*    */ public class SporeBlossomAirProvider
/*    */   implements ParticleProvider<SimpleParticleType>
/*    */ {
/*    */   private final SpriteSet sprite;
/*    */   
/*    */   public SporeBlossomAirProvider(SpriteSet $$0) {
/* 63 */     this.sprite = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 68 */     SuspendedParticle $$8 = new SuspendedParticle($$1, this.sprite, $$2, $$3, $$4, 0.0D, -0.800000011920929D, 0.0D)
/*    */       {
/*    */         public Optional<ParticleGroup> getParticleGroup() {
/* 71 */           return Optional.of(ParticleGroup.SPORE_BLOSSOM);
/*    */         }
/*    */       };
/* 74 */     $$8.lifetime = Mth.randomBetweenInclusive($$1.random, 500, 1000);
/* 75 */     $$8.gravity = 0.01F;
/* 76 */     $$8.setColor(0.32F, 0.5F, 0.22F);
/* 77 */     return $$8;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\SuspendedParticle$SporeBlossomAirProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */