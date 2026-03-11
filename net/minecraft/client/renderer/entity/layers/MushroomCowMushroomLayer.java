/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.CowModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*    */ import net.minecraft.client.renderer.entity.LivingEntityRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.client.resources.model.BakedModel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.MushroomCow;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class MushroomCowMushroomLayer<T extends MushroomCow> extends RenderLayer<T, CowModel<T>> {
/*    */   public MushroomCowMushroomLayer(RenderLayerParent<T, CowModel<T>> $$0, BlockRenderDispatcher $$1) {
/* 21 */     super($$0);
/* 22 */     this.blockRenderer = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 27 */     if ($$3.isBaby()) {
/*    */       return;
/*    */     }
/*    */     
/* 31 */     Minecraft $$10 = Minecraft.getInstance();
/* 32 */     boolean $$11 = ($$10.shouldEntityAppearGlowing((Entity)$$3) && $$3.isInvisible());
/*    */     
/* 34 */     if ($$3.isInvisible() && !$$11) {
/*    */       return;
/*    */     }
/*    */     
/* 38 */     BlockState $$12 = $$3.getVariant().getBlockState();
/* 39 */     int $$13 = LivingEntityRenderer.getOverlayCoords((LivingEntity)$$3, 0.0F);
/* 40 */     BakedModel $$14 = this.blockRenderer.getBlockModel($$12);
/*    */     
/* 42 */     $$0.pushPose();
/* 43 */     $$0.translate(0.2F, -0.35F, 0.5F);
/* 44 */     $$0.mulPose(Axis.YP.rotationDegrees(-48.0F));
/* 45 */     $$0.scale(-1.0F, -1.0F, 1.0F);
/* 46 */     $$0.translate(-0.5F, -0.5F, -0.5F);
/* 47 */     renderMushroomBlock($$0, $$1, $$2, $$11, $$12, $$13, $$14);
/* 48 */     $$0.popPose();
/*    */     
/* 50 */     $$0.pushPose();
/* 51 */     $$0.translate(0.2F, -0.35F, 0.5F);
/* 52 */     $$0.mulPose(Axis.YP.rotationDegrees(42.0F));
/* 53 */     $$0.translate(0.1F, 0.0F, -0.6F);
/* 54 */     $$0.mulPose(Axis.YP.rotationDegrees(-48.0F));
/* 55 */     $$0.scale(-1.0F, -1.0F, 1.0F);
/* 56 */     $$0.translate(-0.5F, -0.5F, -0.5F);
/* 57 */     renderMushroomBlock($$0, $$1, $$2, $$11, $$12, $$13, $$14);
/* 58 */     $$0.popPose();
/*    */     
/* 60 */     $$0.pushPose();
/* 61 */     getParentModel().getHead().translateAndRotate($$0);
/* 62 */     $$0.translate(0.0F, -0.7F, -0.2F);
/* 63 */     $$0.mulPose(Axis.YP.rotationDegrees(-78.0F));
/* 64 */     $$0.scale(-1.0F, -1.0F, 1.0F);
/* 65 */     $$0.translate(-0.5F, -0.5F, -0.5F);
/* 66 */     renderMushroomBlock($$0, $$1, $$2, $$11, $$12, $$13, $$14);
/* 67 */     $$0.popPose();
/*    */   }
/*    */   private final BlockRenderDispatcher blockRenderer;
/*    */   private void renderMushroomBlock(PoseStack $$0, MultiBufferSource $$1, int $$2, boolean $$3, BlockState $$4, int $$5, BakedModel $$6) {
/* 71 */     if ($$3) {
/* 72 */       this.blockRenderer.getModelRenderer().renderModel($$0.last(), $$1.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), $$4, $$6, 0.0F, 0.0F, 0.0F, $$2, $$5);
/*    */     } else {
/* 74 */       this.blockRenderer.renderSingleBlock($$4, $$0, $$1, $$2, $$5);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\MushroomCowMushroomLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */