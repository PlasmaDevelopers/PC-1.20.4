/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.EvokerFangsModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.EvokerFangs;
/*    */ 
/*    */ public class EvokerFangsRenderer extends EntityRenderer<EvokerFangs> {
/* 14 */   private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/illager/evoker_fangs.png");
/*    */   
/*    */   private final EvokerFangsModel<EvokerFangs> model;
/*    */   
/*    */   public EvokerFangsRenderer(EntityRendererProvider.Context $$0) {
/* 19 */     super($$0);
/*    */     
/* 21 */     this.model = new EvokerFangsModel($$0.bakeLayer(ModelLayers.EVOKER_FANGS));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(EvokerFangs $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 26 */     float $$6 = $$0.getAnimationProgress($$2);
/* 27 */     if ($$6 == 0.0F) {
/*    */       return;
/*    */     }
/* 30 */     float $$7 = 2.0F;
/* 31 */     if ($$6 > 0.9F) {
/* 32 */       $$7 *= (1.0F - $$6) / 0.1F;
/*    */     }
/*    */     
/* 35 */     $$3.pushPose();
/*    */     
/* 37 */     $$3.mulPose(Axis.YP.rotationDegrees(90.0F - $$0.getYRot()));
/* 38 */     $$3.scale(-$$7, -$$7, $$7);
/* 39 */     float $$8 = 0.03125F;
/* 40 */     $$3.translate(0.0D, -0.626D, 0.0D);
/* 41 */     $$3.scale(0.5F, 0.5F, 0.5F);
/*    */     
/* 43 */     this.model.setupAnim((Entity)$$0, $$6, 0.0F, 0.0F, $$0.getYRot(), $$0.getXRot());
/*    */     
/* 45 */     VertexConsumer $$9 = $$4.getBuffer(this.model.renderType(TEXTURE_LOCATION));
/* 46 */     this.model.renderToBuffer($$3, $$9, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/* 47 */     $$3.popPose();
/*    */     
/* 49 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(EvokerFangs $$0) {
/* 54 */     return TEXTURE_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\EvokerFangsRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */