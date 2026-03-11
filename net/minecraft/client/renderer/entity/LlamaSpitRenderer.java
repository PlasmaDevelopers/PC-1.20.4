/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.LlamaSpitModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.LlamaSpit;
/*    */ 
/*    */ public class LlamaSpitRenderer extends EntityRenderer<LlamaSpit> {
/* 15 */   private static final ResourceLocation LLAMA_SPIT_LOCATION = new ResourceLocation("textures/entity/llama/spit.png");
/*    */   
/*    */   private final LlamaSpitModel<LlamaSpit> model;
/*    */   
/*    */   public LlamaSpitRenderer(EntityRendererProvider.Context $$0) {
/* 20 */     super($$0);
/* 21 */     this.model = new LlamaSpitModel($$0.bakeLayer(ModelLayers.LLAMA_SPIT));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(LlamaSpit $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 26 */     $$3.pushPose();
/*    */     
/* 28 */     $$3.translate(0.0F, 0.15F, 0.0F);
/* 29 */     $$3.mulPose(Axis.YP.rotationDegrees(Mth.lerp($$2, $$0.yRotO, $$0.getYRot()) - 90.0F));
/* 30 */     $$3.mulPose(Axis.ZP.rotationDegrees(Mth.lerp($$2, $$0.xRotO, $$0.getXRot())));
/*    */     
/* 32 */     this.model.setupAnim((Entity)$$0, $$2, 0.0F, -0.1F, 0.0F, 0.0F);
/*    */     
/* 34 */     VertexConsumer $$6 = $$4.getBuffer(this.model.renderType(LLAMA_SPIT_LOCATION));
/* 35 */     this.model.renderToBuffer($$3, $$6, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 37 */     $$3.popPose();
/*    */     
/* 39 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(LlamaSpit $$0) {
/* 44 */     return LLAMA_SPIT_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\LlamaSpitRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */