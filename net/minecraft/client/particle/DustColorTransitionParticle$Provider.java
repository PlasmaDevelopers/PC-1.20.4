/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.DustColorTransitionOptions;
/*    */ import net.minecraft.core.particles.ParticleOptions;
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
/*    */ public class Provider
/*    */   implements ParticleProvider<DustColorTransitionOptions>
/*    */ {
/*    */   private final SpriteSet sprites;
/*    */   
/*    */   public Provider(SpriteSet $$0) {
/* 44 */     this.sprites = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Particle createParticle(DustColorTransitionOptions $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 49 */     return new DustColorTransitionParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, $$0, this.sprites);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\DustColorTransitionParticle$Provider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */