/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.DustParticleOptionsBase;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class DustParticleBase<T extends DustParticleOptionsBase> extends TextureSheetParticle {
/*    */   private final SpriteSet sprites;
/*    */   
/*    */   protected DustParticleBase(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, T $$7, SpriteSet $$8) {
/* 11 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/* 12 */     this.friction = 0.96F;
/* 13 */     this.speedUpWhenYMotionIsBlocked = true;
/* 14 */     this.sprites = $$8;
/* 15 */     this.xd *= 0.10000000149011612D;
/* 16 */     this.yd *= 0.10000000149011612D;
/* 17 */     this.zd *= 0.10000000149011612D;
/*    */     
/* 19 */     float $$9 = this.random.nextFloat() * 0.4F + 0.6F;
/* 20 */     this.rCol = randomizeColor($$7.getColor().x(), $$9);
/* 21 */     this.gCol = randomizeColor($$7.getColor().y(), $$9);
/* 22 */     this.bCol = randomizeColor($$7.getColor().z(), $$9);
/* 23 */     this.quadSize *= 0.75F * $$7.getScale();
/*    */     
/* 25 */     int $$10 = (int)(8.0D / (this.random.nextDouble() * 0.8D + 0.2D));
/* 26 */     this.lifetime = (int)Math.max($$10 * $$7.getScale(), 1.0F);
/* 27 */     setSpriteFromAge($$8);
/*    */   }
/*    */   
/*    */   protected float randomizeColor(float $$0, float $$1) {
/* 31 */     return (this.random.nextFloat() * 0.2F + 0.8F) * $$0 * $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 36 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getQuadSize(float $$0) {
/* 41 */     return this.quadSize * Mth.clamp((this.age + $$0) / this.lifetime * 32.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 46 */     super.tick();
/* 47 */     setSpriteFromAge(this.sprites);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\DustParticleBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */