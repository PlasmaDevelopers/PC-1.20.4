/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.EndermanModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.monster.EnderMan;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class CarriedBlockLayer
/*    */   extends RenderLayer<EnderMan, EndermanModel<EnderMan>> {
/*    */   public CarriedBlockLayer(RenderLayerParent<EnderMan, EndermanModel<EnderMan>> $$0, BlockRenderDispatcher $$1) {
/* 17 */     super($$0);
/* 18 */     this.blockRenderer = $$1;
/*    */   }
/*    */   private final BlockRenderDispatcher blockRenderer;
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, EnderMan $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 23 */     BlockState $$10 = $$3.getCarriedBlock();
/* 24 */     if ($$10 == null) {
/*    */       return;
/*    */     }
/*    */     
/* 28 */     $$0.pushPose();
/*    */     
/* 30 */     $$0.translate(0.0F, 0.6875F, -0.75F);
/* 31 */     $$0.mulPose(Axis.XP.rotationDegrees(20.0F));
/* 32 */     $$0.mulPose(Axis.YP.rotationDegrees(45.0F));
/* 33 */     $$0.translate(0.25F, 0.1875F, 0.25F);
/* 34 */     float $$11 = 0.5F;
/* 35 */     $$0.scale(-0.5F, -0.5F, 0.5F);
/* 36 */     $$0.mulPose(Axis.YP.rotationDegrees(90.0F));
/*    */     
/* 38 */     this.blockRenderer.renderSingleBlock($$10, $$0, $$1, $$2, OverlayTexture.NO_OVERLAY);
/* 39 */     $$0.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\CarriedBlockLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */