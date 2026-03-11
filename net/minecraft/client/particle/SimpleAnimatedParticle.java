/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ 
/*    */ public class SimpleAnimatedParticle
/*    */   extends TextureSheetParticle
/*    */ {
/*    */   protected final SpriteSet sprites;
/*    */   private float fadeR;
/*    */   private float fadeG;
/*    */   private float fadeB;
/*    */   private boolean hasFade;
/*    */   
/*    */   protected SimpleAnimatedParticle(ClientLevel $$0, double $$1, double $$2, double $$3, SpriteSet $$4, float $$5) {
/* 15 */     super($$0, $$1, $$2, $$3);
/* 16 */     this.friction = 0.91F;
/* 17 */     this.gravity = $$5;
/* 18 */     this.sprites = $$4;
/*    */   }
/*    */   
/*    */   public void setColor(int $$0) {
/* 22 */     float $$1 = (($$0 & 0xFF0000) >> 16) / 255.0F;
/* 23 */     float $$2 = (($$0 & 0xFF00) >> 8) / 255.0F;
/* 24 */     float $$3 = (($$0 & 0xFF) >> 0) / 255.0F;
/* 25 */     float $$4 = 1.0F;
/* 26 */     setColor($$1 * 1.0F, $$2 * 1.0F, $$3 * 1.0F);
/*    */   }
/*    */   
/*    */   public void setFadeColor(int $$0) {
/* 30 */     this.fadeR = (($$0 & 0xFF0000) >> 16) / 255.0F;
/* 31 */     this.fadeG = (($$0 & 0xFF00) >> 8) / 255.0F;
/* 32 */     this.fadeB = (($$0 & 0xFF) >> 0) / 255.0F;
/* 33 */     this.hasFade = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 38 */     return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 43 */     super.tick();
/* 44 */     setSpriteFromAge(this.sprites);
/* 45 */     if (this.age > this.lifetime / 2) {
/* 46 */       setAlpha(1.0F - (this.age - (this.lifetime / 2)) / this.lifetime);
/*    */       
/* 48 */       if (this.hasFade) {
/* 49 */         this.rCol += (this.fadeR - this.rCol) * 0.2F;
/* 50 */         this.gCol += (this.fadeG - this.gCol) * 0.2F;
/* 51 */         this.bCol += (this.fadeB - this.bCol) * 0.2F;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightColor(float $$0) {
/* 58 */     return 15728880;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\SimpleAnimatedParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */