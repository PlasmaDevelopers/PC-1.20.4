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
/*    */ import net.minecraft.world.entity.PowerableMob;
/*    */ 
/*    */ public abstract class EnergySwirlLayer<T extends Entity & PowerableMob, M extends EntityModel<T>> extends RenderLayer<T, M> {
/*    */   public EnergySwirlLayer(RenderLayerParent<T, M> $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 21 */     if (!((PowerableMob)$$3).isPowered()) {
/*    */       return;
/*    */     }
/*    */     
/* 25 */     float $$10 = ((Entity)$$3).tickCount + $$6;
/* 26 */     EntityModel<T> $$11 = model();
/*    */     
/* 28 */     $$11.prepareMobModel((Entity)$$3, $$4, $$5, $$6);
/* 29 */     getParentModel().copyPropertiesTo($$11);
/*    */     
/* 31 */     VertexConsumer $$12 = $$1.getBuffer(RenderType.energySwirl(getTextureLocation(), xOffset($$10) % 1.0F, $$10 * 0.01F % 1.0F));
/*    */     
/* 33 */     $$11.setupAnim((Entity)$$3, $$4, $$5, $$7, $$8, $$9);
/* 34 */     $$11.renderToBuffer($$0, $$12, $$2, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
/*    */   }
/*    */   
/*    */   protected abstract float xOffset(float paramFloat);
/*    */   
/*    */   protected abstract ResourceLocation getTextureLocation();
/*    */   
/*    */   protected abstract EntityModel<T> model();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\EnergySwirlLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */