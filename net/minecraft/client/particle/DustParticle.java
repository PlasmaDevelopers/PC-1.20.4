/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.DustParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ 
/*    */ public class DustParticle extends DustParticleBase<DustParticleOptions> {
/*    */   protected DustParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, DustParticleOptions $$7, SpriteSet $$8) {
/*  8 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<DustParticleOptions> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 15 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(DustParticleOptions $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 20 */       return new DustParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, $$0, this.sprites);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\DustParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */