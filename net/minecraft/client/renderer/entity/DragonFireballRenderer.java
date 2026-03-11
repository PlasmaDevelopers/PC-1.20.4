/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.DragonFireball;
/*    */ import org.joml.Matrix3f;
/*    */ import org.joml.Matrix4f;
/*    */ 
/*    */ public class DragonFireballRenderer extends EntityRenderer<DragonFireball> {
/* 17 */   private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");
/* 18 */   private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);
/*    */   
/*    */   public DragonFireballRenderer(EntityRendererProvider.Context $$0) {
/* 21 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlockLightLevel(DragonFireball $$0, BlockPos $$1) {
/* 26 */     return 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(DragonFireball $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 31 */     $$3.pushPose();
/* 32 */     $$3.scale(2.0F, 2.0F, 2.0F);
/*    */     
/* 34 */     $$3.mulPose(this.entityRenderDispatcher.cameraOrientation());
/* 35 */     $$3.mulPose(Axis.YP.rotationDegrees(180.0F));
/*    */     
/* 37 */     PoseStack.Pose $$6 = $$3.last();
/* 38 */     Matrix4f $$7 = $$6.pose();
/* 39 */     Matrix3f $$8 = $$6.normal();
/*    */     
/* 41 */     VertexConsumer $$9 = $$4.getBuffer(RENDER_TYPE);
/*    */     
/* 43 */     vertex($$9, $$7, $$8, $$5, 0.0F, 0, 0, 1);
/* 44 */     vertex($$9, $$7, $$8, $$5, 1.0F, 0, 1, 1);
/* 45 */     vertex($$9, $$7, $$8, $$5, 1.0F, 1, 1, 0);
/* 46 */     vertex($$9, $$7, $$8, $$5, 0.0F, 1, 0, 0);
/*    */     
/* 48 */     $$3.popPose();
/*    */     
/* 50 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */   
/*    */   private static void vertex(VertexConsumer $$0, Matrix4f $$1, Matrix3f $$2, int $$3, float $$4, int $$5, int $$6, int $$7) {
/* 54 */     $$0.vertex($$1, $$4 - 0.5F, $$5 - 0.25F, 0.0F).color(255, 255, 255, 255).uv($$6, $$7).overlayCoords(OverlayTexture.NO_OVERLAY).uv2($$3).normal($$2, 0.0F, 1.0F, 0.0F).endVertex();
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(DragonFireball $$0) {
/* 59 */     return TEXTURE_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\DragonFireballRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */