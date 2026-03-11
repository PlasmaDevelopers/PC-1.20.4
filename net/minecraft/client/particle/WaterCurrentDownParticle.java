/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class WaterCurrentDownParticle
/*    */   extends TextureSheetParticle {
/*    */   WaterCurrentDownParticle(ClientLevel $$0, double $$1, double $$2, double $$3) {
/* 13 */     super($$0, $$1, $$2, $$3);
/*    */     
/* 15 */     this.lifetime = (int)(Math.random() * 60.0D) + 30;
/*    */     
/* 17 */     this.hasPhysics = false;
/*    */     
/* 19 */     this.xd = 0.0D;
/* 20 */     this.yd = -0.05D;
/* 21 */     this.zd = 0.0D;
/*    */     
/* 23 */     setSize(0.02F, 0.02F);
/* 24 */     this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
/* 25 */     this.gravity = 0.002F;
/*    */   }
/*    */   private float angle;
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 30 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 35 */     this.xo = this.x;
/* 36 */     this.yo = this.y;
/* 37 */     this.zo = this.z;
/*    */     
/* 39 */     if (this.age++ >= this.lifetime) {
/* 40 */       remove();
/*    */       
/*    */       return;
/*    */     } 
/* 44 */     float $$0 = 0.6F;
/* 45 */     this.xd += (0.6F * Mth.cos(this.angle));
/* 46 */     this.zd += (0.6F * Mth.sin(this.angle));
/* 47 */     this.xd *= 0.07D;
/* 48 */     this.zd *= 0.07D;
/* 49 */     move(this.xd, this.yd, this.zd);
/*    */     
/* 51 */     if (!this.level.getFluidState(BlockPos.containing(this.x, this.y, this.z)).is(FluidTags.WATER) || this.onGround) {
/* 52 */       remove();
/*    */     }
/*    */     
/* 55 */     this.angle += 0.08F;
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 62 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 67 */       WaterCurrentDownParticle $$8 = new WaterCurrentDownParticle($$1, $$2, $$3, $$4);
/* 68 */       $$8.pickSprite(this.sprite);
/* 69 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\WaterCurrentDownParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */