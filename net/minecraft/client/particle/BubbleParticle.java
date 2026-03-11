/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ 
/*    */ public class BubbleParticle extends TextureSheetParticle {
/*    */   BubbleParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/* 10 */     super($$0, $$1, $$2, $$3);
/* 11 */     setSize(0.02F, 0.02F);
/*    */     
/* 13 */     this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
/*    */     
/* 15 */     this.xd = $$4 * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
/* 16 */     this.yd = $$5 * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
/* 17 */     this.zd = $$6 * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
/*    */     
/* 19 */     this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 24 */     this.xo = this.x;
/* 25 */     this.yo = this.y;
/* 26 */     this.zo = this.z;
/*    */     
/* 28 */     if (this.lifetime-- <= 0) {
/* 29 */       remove();
/*    */       
/*    */       return;
/*    */     } 
/* 33 */     this.yd += 0.002D;
/* 34 */     move(this.xd, this.yd, this.zd);
/* 35 */     this.xd *= 0.8500000238418579D;
/* 36 */     this.yd *= 0.8500000238418579D;
/* 37 */     this.zd *= 0.8500000238418579D;
/*    */     
/* 39 */     if (!this.level.getFluidState(BlockPos.containing(this.x, this.y, this.z)).is(FluidTags.WATER)) {
/* 40 */       remove();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 46 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 53 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 58 */       BubbleParticle $$8 = new BubbleParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 59 */       $$8.pickSprite(this.sprite);
/* 60 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\BubbleParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */