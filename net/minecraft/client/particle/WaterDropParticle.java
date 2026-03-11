/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ 
/*    */ public class WaterDropParticle extends TextureSheetParticle {
/*    */   protected WaterDropParticle(ClientLevel $$0, double $$1, double $$2, double $$3) {
/* 10 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/* 11 */     this.xd *= 0.30000001192092896D;
/* 12 */     this.yd = Math.random() * 0.20000000298023224D + 0.10000000149011612D;
/* 13 */     this.zd *= 0.30000001192092896D;
/*    */     
/* 15 */     setSize(0.01F, 0.01F);
/* 16 */     this.gravity = 0.06F;
/*    */     
/* 18 */     this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 23 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 28 */     this.xo = this.x;
/* 29 */     this.yo = this.y;
/* 30 */     this.zo = this.z;
/*    */     
/* 32 */     if (this.lifetime-- <= 0) {
/* 33 */       remove();
/*    */       
/*    */       return;
/*    */     } 
/* 37 */     this.yd -= this.gravity;
/* 38 */     move(this.xd, this.yd, this.zd);
/* 39 */     this.xd *= 0.9800000190734863D;
/* 40 */     this.yd *= 0.9800000190734863D;
/* 41 */     this.zd *= 0.9800000190734863D;
/*    */     
/* 43 */     if (this.onGround) {
/* 44 */       if (Math.random() < 0.5D) {
/* 45 */         remove();
/*    */       }
/* 47 */       this.xd *= 0.699999988079071D;
/* 48 */       this.zd *= 0.699999988079071D;
/*    */     } 
/*    */     
/* 51 */     BlockPos $$0 = BlockPos.containing(this.x, this.y, this.z);
/* 52 */     double $$1 = Math.max(this.level
/* 53 */         .getBlockState($$0).getCollisionShape((BlockGetter)this.level, $$0).max(Direction.Axis.Y, this.x - $$0.getX(), this.z - $$0.getZ()), this.level
/* 54 */         .getFluidState($$0).getHeight((BlockGetter)this.level, $$0));
/*    */ 
/*    */     
/* 57 */     if ($$1 > 0.0D && this.y < $$0.getY() + $$1)
/* 58 */       remove(); 
/*    */   }
/*    */   
/*    */   public static class Provider
/*    */     implements ParticleProvider<SimpleParticleType> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 66 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 71 */       WaterDropParticle $$8 = new WaterDropParticle($$1, $$2, $$3, $$4);
/* 72 */       $$8.pickSprite(this.sprite);
/* 73 */       return $$8;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\WaterDropParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */