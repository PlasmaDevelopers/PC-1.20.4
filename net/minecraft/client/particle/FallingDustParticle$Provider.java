/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.BlockParticleOption;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.FallingBlock;
/*    */ import net.minecraft.world.level.block.RenderShape;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Provider
/*    */   implements ParticleProvider<BlockParticleOption>
/*    */ {
/*    */   private final SpriteSet sprite;
/*    */   
/*    */   public Provider(SpriteSet $$0) {
/* 76 */     this.sprite = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Particle createParticle(BlockParticleOption $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 82 */     BlockState $$8 = $$0.getState();
/* 83 */     if (!$$8.isAir() && $$8.getRenderShape() == RenderShape.INVISIBLE) {
/* 84 */       return null;
/*    */     }
/*    */     
/* 87 */     BlockPos $$9 = BlockPos.containing($$2, $$3, $$4);
/* 88 */     int $$10 = Minecraft.getInstance().getBlockColors().getColor($$8, (Level)$$1, $$9);
/* 89 */     if ($$8.getBlock() instanceof FallingBlock) {
/* 90 */       $$10 = ((FallingBlock)$$8.getBlock()).getDustColor($$8, (BlockGetter)$$1, $$9);
/*    */     }
/* 92 */     float $$11 = ($$10 >> 16 & 0xFF) / 255.0F;
/* 93 */     float $$12 = ($$10 >> 8 & 0xFF) / 255.0F;
/* 94 */     float $$13 = ($$10 & 0xFF) / 255.0F;
/*    */     
/* 96 */     return new FallingDustParticle($$1, $$2, $$3, $$4, $$11, $$12, $$13, this.sprite);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\FallingDustParticle$Provider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */