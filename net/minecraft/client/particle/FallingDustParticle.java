/*    */ package net.minecraft.client.particle;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.BlockParticleOption;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.FallingBlock;
/*    */ import net.minecraft.world.level.block.RenderShape;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class FallingDustParticle extends TextureSheetParticle {
/*    */   private final float rotSpeed;
/*    */   
/*    */   FallingDustParticle(ClientLevel $$0, double $$1, double $$2, double $$3, float $$4, float $$5, float $$6, SpriteSet $$7) {
/* 19 */     super($$0, $$1, $$2, $$3);
/* 20 */     this.sprites = $$7;
/*    */     
/* 22 */     this.rCol = $$4;
/* 23 */     this.gCol = $$5;
/* 24 */     this.bCol = $$6;
/*    */     
/* 26 */     float $$8 = 0.9F;
/*    */     
/* 28 */     this.quadSize *= 0.67499995F;
/*    */     
/* 30 */     int $$9 = (int)(32.0D / (Math.random() * 0.8D + 0.2D));
/* 31 */     this.lifetime = (int)Math.max($$9 * 0.9F, 1.0F);
/* 32 */     setSpriteFromAge($$7);
/*    */     
/* 34 */     this.rotSpeed = ((float)Math.random() - 0.5F) * 0.1F;
/* 35 */     this.roll = (float)Math.random() * 6.2831855F;
/*    */   }
/*    */   private final SpriteSet sprites;
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 40 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getQuadSize(float $$0) {
/* 45 */     return this.quadSize * Mth.clamp((this.age + $$0) / this.lifetime * 32.0F, 0.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 50 */     this.xo = this.x;
/* 51 */     this.yo = this.y;
/* 52 */     this.zo = this.z;
/*    */     
/* 54 */     if (this.age++ >= this.lifetime) {
/* 55 */       remove();
/*    */       
/*    */       return;
/*    */     } 
/* 59 */     setSpriteFromAge(this.sprites);
/*    */     
/* 61 */     this.oRoll = this.roll;
/* 62 */     this.roll += 3.1415927F * this.rotSpeed * 2.0F;
/* 63 */     if (this.onGround) {
/* 64 */       this.oRoll = this.roll = 0.0F;
/*    */     }
/*    */     
/* 67 */     move(this.xd, this.yd, this.zd);
/* 68 */     this.yd -= 0.003000000026077032D;
/* 69 */     this.yd = Math.max(this.yd, -0.14000000059604645D);
/*    */   }
/*    */   
/*    */   public static class Provider implements ParticleProvider<BlockParticleOption> {
/*    */     private final SpriteSet sprite;
/*    */     
/*    */     public Provider(SpriteSet $$0) {
/* 76 */       this.sprite = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     @Nullable
/*    */     public Particle createParticle(BlockParticleOption $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 82 */       BlockState $$8 = $$0.getState();
/* 83 */       if (!$$8.isAir() && $$8.getRenderShape() == RenderShape.INVISIBLE) {
/* 84 */         return null;
/*    */       }
/*    */       
/* 87 */       BlockPos $$9 = BlockPos.containing($$2, $$3, $$4);
/* 88 */       int $$10 = Minecraft.getInstance().getBlockColors().getColor($$8, (Level)$$1, $$9);
/* 89 */       if ($$8.getBlock() instanceof FallingBlock) {
/* 90 */         $$10 = ((FallingBlock)$$8.getBlock()).getDustColor($$8, (BlockGetter)$$1, $$9);
/*    */       }
/* 92 */       float $$11 = ($$10 >> 16 & 0xFF) / 255.0F;
/* 93 */       float $$12 = ($$10 >> 8 & 0xFF) / 255.0F;
/* 94 */       float $$13 = ($$10 & 0xFF) / 255.0F;
/*    */       
/* 96 */       return new FallingDustParticle($$1, $$2, $$3, $$4, $$11, $$12, $$13, this.sprite);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\FallingDustParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */