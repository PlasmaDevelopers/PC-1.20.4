/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class GustParticle extends TextureSheetParticle {
/*    */   private final SpriteSet sprites;
/*    */   
/*    */   protected GustParticle(ClientLevel $$0, double $$1, double $$2, double $$3, SpriteSet $$4) {
/* 11 */     super($$0, $$1, $$2, $$3);
/* 12 */     this.sprites = $$4;
/* 13 */     setSpriteFromAge($$4);
/* 14 */     this.lifetime = 12 + this.random.nextInt(4);
/* 15 */     this.quadSize = 1.0F;
/* 16 */     setSize(1.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 21 */     return ParticleRenderType.PARTICLE_SHEET_LIT;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightColor(float $$0) {
/* 26 */     return 15728880;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 31 */     if (this.age++ >= this.lifetime) {
/* 32 */       remove();
/*    */       return;
/*    */     } 
/* 35 */     setSpriteFromAge(this.sprites);
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprites;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 42 */       this.sprites = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 47 */       return new GustParticle($$1, $$2, $$3, $$4, this.sprites);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\GustParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */