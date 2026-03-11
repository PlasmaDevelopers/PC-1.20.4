/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.LeashKnotModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
/*    */ 
/*    */ public class LeashKnotRenderer extends EntityRenderer<LeashFenceKnotEntity> {
/* 13 */   private static final ResourceLocation KNOT_LOCATION = new ResourceLocation("textures/entity/lead_knot.png");
/*    */   
/*    */   private final LeashKnotModel<LeashFenceKnotEntity> model;
/*    */   
/*    */   public LeashKnotRenderer(EntityRendererProvider.Context $$0) {
/* 18 */     super($$0);
/* 19 */     this.model = new LeashKnotModel($$0.bakeLayer(ModelLayers.LEASH_KNOT));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(LeashFenceKnotEntity $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 24 */     $$3.pushPose();
/*    */     
/* 26 */     $$3.scale(-1.0F, -1.0F, 1.0F);
/*    */     
/* 28 */     this.model.setupAnim((Entity)$$0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/*    */     
/* 30 */     VertexConsumer $$6 = $$4.getBuffer(this.model.renderType(KNOT_LOCATION));
/* 31 */     this.model.renderToBuffer($$3, $$6, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 33 */     $$3.popPose();
/*    */     
/* 35 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(LeashFenceKnotEntity $$0) {
/* 40 */     return KNOT_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\LeashKnotRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */