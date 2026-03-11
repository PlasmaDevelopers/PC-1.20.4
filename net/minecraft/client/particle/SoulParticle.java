/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class SoulParticle extends RisingParticle {
/*    */   private final SpriteSet sprites;
/*    */   
/*    */   SoulParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, SpriteSet $$7) {
/* 11 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*    */     
/* 13 */     this.sprites = $$7;
/* 14 */     scale(1.5F);
/*    */     
/* 16 */     setSpriteFromAge($$7);
/*    */   }
/*    */   protected boolean isGlowing;
/*    */   
/*    */   public int getLightColor(float $$0) {
/* 21 */     if (this.isGlowing) {
/* 22 */       return 240;
/*    */     }
/*    */     
/* 25 */     return super.getLightColor($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 30 */     return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 35 */     super.tick();
/* 36 */     setSpriteFromAge(this.sprites);
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 43 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 48 */       SoulParticle $$8 = new SoulParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprite);
/* 49 */       $$8.setAlpha(1.0F);
/* 50 */       return $$8;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class EmissiveProvider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public EmissiveProvider(SpriteSet $$0) {
/* 58 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 63 */       SoulParticle $$8 = new SoulParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprite);
/* 64 */       $$8.setAlpha(1.0F);
/* 65 */       $$8.isGlowing = true;
/* 66 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\SoulParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */