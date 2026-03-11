/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.Saddleable;
/*    */ 
/*    */ public class SaddleLayer<T extends Entity & Saddleable, M extends EntityModel<T>>
/*    */   extends RenderLayer<T, M> {
/*    */   private final ResourceLocation textureLocation;
/*    */   private final M model;
/*    */   
/*    */   public SaddleLayer(RenderLayerParent<T, M> $$0, M $$1, ResourceLocation $$2) {
/* 20 */     super($$0);
/* 21 */     this.model = $$1;
/* 22 */     this.textureLocation = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 27 */     if (!((Saddleable)$$3).isSaddled()) {
/*    */       return;
/*    */     }
/*    */     
/* 31 */     getParentModel().copyPropertiesTo((EntityModel)this.model);
/* 32 */     this.model.prepareMobModel((Entity)$$3, $$4, $$5, $$6);
/* 33 */     this.model.setupAnim((Entity)$$3, $$4, $$5, $$7, $$8, $$9);
/* 34 */     VertexConsumer $$10 = $$1.getBuffer(RenderType.entityCutoutNoCull(this.textureLocation));
/* 35 */     this.model.renderToBuffer($$0, $$10, $$2, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\SaddleLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */