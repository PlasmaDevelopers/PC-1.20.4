/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class BaseAshSmokeParticle extends TextureSheetParticle {
/*    */   private final SpriteSet sprites;
/*    */   
/*    */   protected BaseAshSmokeParticle(ClientLevel $$0, double $$1, double $$2, double $$3, float $$4, float $$5, float $$6, double $$7, double $$8, double $$9, float $$10, SpriteSet $$11, float $$12, int $$13, float $$14, boolean $$15) {
/* 10 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/* 11 */     this.friction = 0.96F;
/* 12 */     this.gravity = $$14;
/* 13 */     this.speedUpWhenYMotionIsBlocked = true;
/*    */     
/* 15 */     this.sprites = $$11;
/* 16 */     this.xd *= $$4;
/* 17 */     this.yd *= $$5;
/* 18 */     this.zd *= $$6;
/* 19 */     this.xd += $$7;
/* 20 */     this.yd += $$8;
/* 21 */     this.zd += $$9;
/*    */     
/* 23 */     float $$16 = $$0.random.nextFloat() * $$12;
/* 24 */     this.rCol = $$16;
/* 25 */     this.gCol = $$16;
/* 26 */     this.bCol = $$16;
/* 27 */     this.quadSize *= 0.75F * $$10;
/*    */     
/* 29 */     this.lifetime = (int)($$13 / ($$0.random.nextFloat() * 0.8D + 0.2D) * $$10);
/* 30 */     this.lifetime = Math.max(this.lifetime, 1);
/* 31 */     setSpriteFromAge($$11);
/* 32 */     this.hasPhysics = $$15;
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 37 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getQuadSize(float $$0) {
/* 42 */     return this.quadSize * Mth.clamp((this.age + $$0) / this.lifetime * 32.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 47 */     super.tick();
/* 48 */     setSpriteFromAge(this.sprites);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\BaseAshSmokeParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */