/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ 
/*    */ public class BubbleColumnUpParticle extends TextureSheetParticle {
/*    */   BubbleColumnUpParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/* 10 */     super($$0, $$1, $$2, $$3);
/* 11 */     this.gravity = -0.125F;
/* 12 */     this.friction = 0.85F;
/* 13 */     setSize(0.02F, 0.02F);
/*    */     
/* 15 */     this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
/*    */     
/* 17 */     this.xd = $$4 * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
/* 18 */     this.yd = $$5 * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
/* 19 */     this.zd = $$6 * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
/*    */     
/* 21 */     this.lifetime = (int)(40.0D / (Math.random() * 0.8D + 0.2D));
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 26 */     super.tick();
/* 27 */     if (!this.removed && !this.level.getFluidState(BlockPos.containing(this.x, this.y, this.z)).is(FluidTags.WATER)) {
/* 28 */       remove();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 34 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 41 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 46 */       BubbleColumnUpParticle $$8 = new BubbleColumnUpParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 47 */       $$8.pickSprite(this.sprite);
/* 48 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\BubbleColumnUpParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */