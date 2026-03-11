/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.WindChargeModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.WindCharge;
/*    */ 
/*    */ public class WindChargeRenderer extends EntityRenderer<WindCharge> {
/* 15 */   private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/projectiles/wind_charge.png");
/*    */   
/*    */   private final WindChargeModel model;
/*    */   
/*    */   public WindChargeRenderer(EntityRendererProvider.Context $$0) {
/* 20 */     super($$0);
/* 21 */     this.model = new WindChargeModel($$0.bakeLayer(ModelLayers.WIND_CHARGE));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(WindCharge $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 26 */     float $$6 = $$0.tickCount + $$2;
/*    */     
/* 28 */     VertexConsumer $$7 = $$4.getBuffer(RenderType.breezeWind(TEXTURE_LOCATION, xOffset($$6) % 1.0F, 0.0F));
/* 29 */     this.model.setupAnim($$0, 0.0F, 0.0F, $$6, 0.0F, 0.0F);
/* 30 */     this.model.renderToBuffer($$3, $$7, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.5F);
/*    */     
/* 32 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */   
/*    */   protected float xOffset(float $$0) {
/* 36 */     return $$0 * 0.03F;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(WindCharge $$0) {
/* 41 */     return TEXTURE_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\WindChargeRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */