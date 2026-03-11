/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.ShulkerBulletModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.ShulkerBullet;
/*    */ 
/*    */ public class ShulkerBulletRenderer extends EntityRenderer<ShulkerBullet> {
/* 18 */   private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/shulker/spark.png");
/* 19 */   private static final RenderType RENDER_TYPE = RenderType.entityTranslucent(TEXTURE_LOCATION);
/*    */   
/*    */   private final ShulkerBulletModel<ShulkerBullet> model;
/*    */   
/*    */   public ShulkerBulletRenderer(EntityRendererProvider.Context $$0) {
/* 24 */     super($$0);
/* 25 */     this.model = new ShulkerBulletModel($$0.bakeLayer(ModelLayers.SHULKER_BULLET));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlockLightLevel(ShulkerBullet $$0, BlockPos $$1) {
/* 30 */     return 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(ShulkerBullet $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 35 */     $$3.pushPose();
/*    */     
/* 37 */     float $$6 = Mth.rotLerp($$2, $$0.yRotO, $$0.getYRot());
/* 38 */     float $$7 = Mth.lerp($$2, $$0.xRotO, $$0.getXRot());
/* 39 */     float $$8 = $$0.tickCount + $$2;
/*    */     
/* 41 */     $$3.translate(0.0F, 0.15F, 0.0F);
/* 42 */     $$3.mulPose(Axis.YP.rotationDegrees(Mth.sin($$8 * 0.1F) * 180.0F));
/* 43 */     $$3.mulPose(Axis.XP.rotationDegrees(Mth.cos($$8 * 0.1F) * 180.0F));
/* 44 */     $$3.mulPose(Axis.ZP.rotationDegrees(Mth.sin($$8 * 0.15F) * 360.0F));
/*    */     
/* 46 */     $$3.scale(-0.5F, -0.5F, 0.5F);
/*    */     
/* 48 */     this.model.setupAnim((Entity)$$0, 0.0F, 0.0F, 0.0F, $$6, $$7);
/*    */     
/* 50 */     VertexConsumer $$9 = $$4.getBuffer(this.model.renderType(TEXTURE_LOCATION));
/* 51 */     this.model.renderToBuffer($$3, $$9, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 53 */     $$3.scale(1.5F, 1.5F, 1.5F);
/*    */     
/* 55 */     VertexConsumer $$10 = $$4.getBuffer(RENDER_TYPE);
/* 56 */     this.model.renderToBuffer($$3, $$10, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.15F);
/*    */     
/* 58 */     $$3.popPose();
/*    */     
/* 60 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(ShulkerBullet $$0) {
/* 65 */     return TEXTURE_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ShulkerBulletRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */