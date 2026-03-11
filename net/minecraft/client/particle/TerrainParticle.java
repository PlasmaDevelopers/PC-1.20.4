/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.renderer.LevelRenderer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.BlockParticleOption;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.world.level.BlockAndTintGetter;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class TerrainParticle
/*    */   extends TextureSheetParticle {
/*    */   private final BlockPos pos;
/*    */   
/*    */   public TerrainParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, BlockState $$7) {
/* 18 */     this($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, BlockPos.containing($$1, $$2, $$3));
/*    */   }
/*    */   private final float uo; private final float vo;
/*    */   public TerrainParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, BlockState $$7, BlockPos $$8) {
/* 22 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/* 23 */     this.pos = $$8;
/* 24 */     setSprite(Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon($$7));
/* 25 */     this.gravity = 1.0F;
/* 26 */     this.rCol = 0.6F;
/* 27 */     this.gCol = 0.6F;
/* 28 */     this.bCol = 0.6F;
/*    */     
/* 30 */     if (!$$7.is(Blocks.GRASS_BLOCK)) {
/* 31 */       int $$9 = Minecraft.getInstance().getBlockColors().getColor($$7, (BlockAndTintGetter)$$0, $$8, 0);
/* 32 */       this.rCol *= ($$9 >> 16 & 0xFF) / 255.0F;
/* 33 */       this.gCol *= ($$9 >> 8 & 0xFF) / 255.0F;
/* 34 */       this.bCol *= ($$9 & 0xFF) / 255.0F;
/*    */     } 
/*    */     
/* 37 */     this.quadSize /= 2.0F;
/*    */     
/* 39 */     this.uo = this.random.nextFloat() * 3.0F;
/* 40 */     this.vo = this.random.nextFloat() * 3.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 45 */     return ParticleRenderType.TERRAIN_SHEET;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getU0() {
/* 50 */     return this.sprite.getU((this.uo + 1.0F) / 4.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getU1() {
/* 55 */     return this.sprite.getU(this.uo / 4.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getV0() {
/* 60 */     return this.sprite.getV(this.vo / 4.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getV1() {
/* 65 */     return this.sprite.getV((this.vo + 1.0F) / 4.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightColor(float $$0) {
/* 70 */     int $$1 = super.getLightColor($$0);
/*    */ 
/*    */     
/* 73 */     if ($$1 == 0 && this.level.hasChunkAt(this.pos)) {
/* 74 */       return LevelRenderer.getLightColor((BlockAndTintGetter)this.level, this.pos);
/*    */     }
/*    */     
/* 77 */     return $$1;
/*    */   }
/*    */   
/*    */   public static class Provider
/*    */     implements ParticleProvider<BlockParticleOption> {
/*    */     public Particle createParticle(BlockParticleOption $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 83 */       BlockState $$8 = $$0.getState();
/* 84 */       if ($$8.isAir() || $$8.is(Blocks.MOVING_PISTON) || !$$8.shouldSpawnTerrainParticles()) {
/* 85 */         return null;
/*    */       }
/* 87 */       return new TerrainParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\TerrainParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */