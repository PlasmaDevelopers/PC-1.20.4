/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.renderer.ItemBlockRenderTypes;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.item.FallingBlockEntity;
/*    */ import net.minecraft.world.level.BlockAndTintGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.RenderShape;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class FallingBlockRenderer extends EntityRenderer<FallingBlockEntity> {
/*    */   public FallingBlockRenderer(EntityRendererProvider.Context $$0) {
/* 21 */     super($$0);
/* 22 */     this.shadowRadius = 0.5F;
/* 23 */     this.dispatcher = $$0.getBlockRenderDispatcher();
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(FallingBlockEntity $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 28 */     BlockState $$6 = $$0.getBlockState();
/* 29 */     if ($$6.getRenderShape() != RenderShape.MODEL) {
/*    */       return;
/*    */     }
/*    */     
/* 33 */     Level $$7 = $$0.level();
/* 34 */     if ($$6 == $$7.getBlockState($$0.blockPosition()) || $$6.getRenderShape() == RenderShape.INVISIBLE) {
/*    */       return;
/*    */     }
/*    */     
/* 38 */     $$3.pushPose();
/*    */ 
/*    */     
/* 41 */     BlockPos $$8 = BlockPos.containing($$0.getX(), ($$0.getBoundingBox()).maxY, $$0.getZ());
/*    */     
/* 43 */     $$3.translate(-0.5D, 0.0D, -0.5D);
/*    */     
/* 45 */     this.dispatcher.getModelRenderer().tesselateBlock((BlockAndTintGetter)$$7, this.dispatcher.getBlockModel($$6), $$6, $$8, $$3, $$4.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType($$6)), false, RandomSource.create(), $$6.getSeed($$0.getStartPos()), OverlayTexture.NO_OVERLAY);
/*    */     
/* 47 */     $$3.popPose();
/*    */     
/* 49 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */   private final BlockRenderDispatcher dispatcher;
/*    */   
/*    */   public ResourceLocation getTextureLocation(FallingBlockEntity $$0) {
/* 54 */     return TextureAtlas.LOCATION_BLOCKS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\FallingBlockRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */