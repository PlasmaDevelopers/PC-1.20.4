/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.LivingEntityRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ public abstract class RenderLayer<T extends Entity, M extends EntityModel<T>> {
/*    */   private final RenderLayerParent<T, M> renderer;
/*    */   
/*    */   public RenderLayer(RenderLayerParent<T, M> $$0) {
/* 18 */     this.renderer = $$0;
/*    */   }
/*    */   
/*    */   protected static <T extends LivingEntity> void coloredCutoutModelCopyLayerRender(EntityModel<T> $$0, EntityModel<T> $$1, ResourceLocation $$2, PoseStack $$3, MultiBufferSource $$4, int $$5, T $$6, float $$7, float $$8, float $$9, float $$10, float $$11, float $$12, float $$13, float $$14, float $$15) {
/* 22 */     if (!$$6.isInvisible()) {
/* 23 */       $$0.copyPropertiesTo($$1);
/* 24 */       $$1.prepareMobModel((Entity)$$6, $$7, $$8, $$12);
/* 25 */       $$1.setupAnim((Entity)$$6, $$7, $$8, $$9, $$10, $$11);
/* 26 */       renderColoredCutoutModel($$1, $$2, $$3, $$4, $$5, $$6, $$13, $$14, $$15);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected static <T extends LivingEntity> void renderColoredCutoutModel(EntityModel<T> $$0, ResourceLocation $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, T $$5, float $$6, float $$7, float $$8) {
/* 31 */     VertexConsumer $$9 = $$3.getBuffer(RenderType.entityCutoutNoCull($$1));
/* 32 */     $$0.renderToBuffer($$2, $$9, $$4, LivingEntityRenderer.getOverlayCoords((LivingEntity)$$5, 0.0F), $$6, $$7, $$8, 1.0F);
/*    */   }
/*    */   
/*    */   public M getParentModel() {
/* 36 */     return (M)this.renderer.getModel();
/*    */   }
/*    */   
/*    */   protected ResourceLocation getTextureLocation(T $$0) {
/* 40 */     return this.renderer.getTextureLocation((Entity)$$0);
/*    */   }
/*    */   
/*    */   public abstract void render(PoseStack paramPoseStack, MultiBufferSource paramMultiBufferSource, int paramInt, T paramT, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\RenderLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */