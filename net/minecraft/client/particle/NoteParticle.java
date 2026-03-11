/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class NoteParticle extends TextureSheetParticle {
/*    */   NoteParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4) {
/*  9 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/* 10 */     this.friction = 0.66F;
/* 11 */     this.speedUpWhenYMotionIsBlocked = true;
/* 12 */     this.xd *= 0.009999999776482582D;
/* 13 */     this.yd *= 0.009999999776482582D;
/* 14 */     this.zd *= 0.009999999776482582D;
/* 15 */     this.yd += 0.2D;
/*    */     
/* 17 */     this.rCol = Math.max(0.0F, Mth.sin(((float)$$4 + 0.0F) * 6.2831855F) * 0.65F + 0.35F);
/* 18 */     this.gCol = Math.max(0.0F, Mth.sin(((float)$$4 + 0.33333334F) * 6.2831855F) * 0.65F + 0.35F);
/* 19 */     this.bCol = Math.max(0.0F, Mth.sin(((float)$$4 + 0.6666667F) * 6.2831855F) * 0.65F + 0.35F);
/*    */     
/* 21 */     this.quadSize *= 1.5F;
/* 22 */     this.lifetime = 6;
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 27 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getQuadSize(float $$0) {
/* 32 */     return this.quadSize * Mth.clamp((this.age + $$0) / this.lifetime * 32.0F, 0.0F, 1.0F);
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 39 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 44 */       NoteParticle $$8 = new NoteParticle($$1, $$2, $$3, $$4, $$5);
/* 45 */       $$8.pickSprite(this.sprite);
/* 46 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\NoteParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */