/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.TridentModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.ThrownTrident;
/*    */ 
/*    */ public class ThrownTridentRenderer extends EntityRenderer<ThrownTrident> {
/* 15 */   public static final ResourceLocation TRIDENT_LOCATION = new ResourceLocation("textures/entity/trident.png");
/*    */   
/*    */   private final TridentModel model;
/*    */   
/*    */   public ThrownTridentRenderer(EntityRendererProvider.Context $$0) {
/* 20 */     super($$0);
/* 21 */     this.model = new TridentModel($$0.bakeLayer(ModelLayers.TRIDENT));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(ThrownTrident $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 26 */     $$3.pushPose();
/*    */     
/* 28 */     $$3.mulPose(Axis.YP.rotationDegrees(Mth.lerp($$2, $$0.yRotO, $$0.getYRot()) - 90.0F));
/* 29 */     $$3.mulPose(Axis.ZP.rotationDegrees(Mth.lerp($$2, $$0.xRotO, $$0.getXRot()) + 90.0F));
/*    */     
/* 31 */     VertexConsumer $$6 = ItemRenderer.getFoilBufferDirect($$4, this.model.renderType(getTextureLocation($$0)), false, $$0.isFoil());
/* 32 */     this.model.renderToBuffer($$3, $$6, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 34 */     $$3.popPose();
/*    */     
/* 36 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(ThrownTrident $$0) {
/* 41 */     return TRIDENT_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ThrownTridentRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */