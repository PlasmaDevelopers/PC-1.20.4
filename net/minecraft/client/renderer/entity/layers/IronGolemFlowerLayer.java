/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.IronGolemModel;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.IronGolem;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ 
/*    */ public class IronGolemFlowerLayer
/*    */   extends RenderLayer<IronGolem, IronGolemModel<IronGolem>> {
/*    */   public IronGolemFlowerLayer(RenderLayerParent<IronGolem, IronGolemModel<IronGolem>> $$0, BlockRenderDispatcher $$1) {
/* 18 */     super($$0);
/* 19 */     this.blockRenderer = $$1;
/*    */   }
/*    */   private final BlockRenderDispatcher blockRenderer;
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, IronGolem $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 24 */     if ($$3.getOfferFlowerTick() == 0) {
/*    */       return;
/*    */     }
/*    */     
/* 28 */     $$0.pushPose();
/* 29 */     ModelPart $$10 = getParentModel().getFlowerHoldingArm();
/*    */ 
/*    */     
/* 32 */     $$10.translateAndRotate($$0);
/* 33 */     $$0.translate(-1.1875F, 1.0625F, -0.9375F);
/*    */     
/* 35 */     $$0.translate(0.5F, 0.5F, 0.5F);
/* 36 */     float $$11 = 0.5F;
/* 37 */     $$0.scale(0.5F, 0.5F, 0.5F);
/* 38 */     $$0.mulPose(Axis.XP.rotationDegrees(-90.0F));
/* 39 */     $$0.translate(-0.5F, -0.5F, -0.5F);
/*    */     
/* 41 */     this.blockRenderer.renderSingleBlock(Blocks.POPPY.defaultBlockState(), $$0, $$1, $$2, OverlayTexture.NO_OVERLAY);
/* 42 */     $$0.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\IronGolemFlowerLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */