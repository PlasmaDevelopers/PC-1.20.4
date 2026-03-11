/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public abstract class EyesLayer<T extends Entity, M extends EntityModel<T>>
/*    */   extends RenderLayer<T, M> {
/*    */   public EyesLayer(RenderLayerParent<T, M> $$0) {
/* 15 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 20 */     VertexConsumer $$10 = $$1.getBuffer(renderType());
/* 21 */     getParentModel().renderToBuffer($$0, $$10, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */   
/*    */   public abstract RenderType renderType();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\EyesLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */