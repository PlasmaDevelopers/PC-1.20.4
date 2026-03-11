/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.BlockParticleOption;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class BlockMarker extends TextureSheetParticle {
/*    */   BlockMarker(ClientLevel $$0, double $$1, double $$2, double $$3, BlockState $$4) {
/* 10 */     super($$0, $$1, $$2, $$3);
/* 11 */     setSprite(Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon($$4));
/* 12 */     this.gravity = 0.0F;
/* 13 */     this.lifetime = 80;
/* 14 */     this.hasPhysics = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 19 */     return ParticleRenderType.TERRAIN_SHEET;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getQuadSize(float $$0) {
/* 24 */     return 0.5F;
/*    */   }
/*    */   
/*    */   public static class Provider
/*    */     implements ParticleProvider<BlockParticleOption> {
/*    */     public Particle createParticle(BlockParticleOption $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 30 */       return new BlockMarker($$1, $$2, $$3, $$4, $$0.getState());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\BlockMarker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */