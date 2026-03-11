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
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.ExperienceOrb;
/*    */ import org.joml.Matrix3f;
/*    */ import org.joml.Matrix4f;
/*    */ 
/*    */ public class ExperienceOrbRenderer extends EntityRenderer<ExperienceOrb> {
/* 18 */   private static final ResourceLocation EXPERIENCE_ORB_LOCATION = new ResourceLocation("textures/entity/experience_orb.png");
/* 19 */   private static final RenderType RENDER_TYPE = RenderType.itemEntityTranslucentCull(EXPERIENCE_ORB_LOCATION);
/*    */   
/*    */   public ExperienceOrbRenderer(EntityRendererProvider.Context $$0) {
/* 22 */     super($$0);
/* 23 */     this.shadowRadius = 0.15F;
/* 24 */     this.shadowStrength = 0.75F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlockLightLevel(ExperienceOrb $$0, BlockPos $$1) {
/* 29 */     return Mth.clamp(super.getBlockLightLevel($$0, $$1) + 7, 0, 15);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(ExperienceOrb $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 34 */     $$3.pushPose();
/*    */     
/* 36 */     int $$6 = $$0.getIcon();
/* 37 */     float $$7 = ($$6 % 4 * 16 + 0) / 64.0F;
/* 38 */     float $$8 = ($$6 % 4 * 16 + 16) / 64.0F;
/* 39 */     float $$9 = ($$6 / 4 * 16 + 0) / 64.0F;
/* 40 */     float $$10 = ($$6 / 4 * 16 + 16) / 64.0F;
/*    */     
/* 42 */     float $$11 = 1.0F;
/* 43 */     float $$12 = 0.5F;
/* 44 */     float $$13 = 0.25F;
/*    */     
/* 46 */     float $$14 = 255.0F;
/* 47 */     float $$15 = ($$0.tickCount + $$2) / 2.0F;
/* 48 */     int $$16 = (int)((Mth.sin($$15 + 0.0F) + 1.0F) * 0.5F * 255.0F);
/* 49 */     int $$17 = 255;
/* 50 */     int $$18 = (int)((Mth.sin($$15 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
/*    */     
/* 52 */     $$3.translate(0.0F, 0.1F, 0.0F);
/* 53 */     $$3.mulPose(this.entityRenderDispatcher.cameraOrientation());
/* 54 */     $$3.mulPose(Axis.YP.rotationDegrees(180.0F));
/*    */     
/* 56 */     float $$19 = 0.3F;
/* 57 */     $$3.scale(0.3F, 0.3F, 0.3F);
/*    */     
/* 59 */     VertexConsumer $$20 = $$4.getBuffer(RENDER_TYPE);
/*    */     
/* 61 */     PoseStack.Pose $$21 = $$3.last();
/* 62 */     Matrix4f $$22 = $$21.pose();
/* 63 */     Matrix3f $$23 = $$21.normal();
/*    */     
/* 65 */     vertex($$20, $$22, $$23, -0.5F, -0.25F, $$16, 255, $$18, $$7, $$10, $$5);
/* 66 */     vertex($$20, $$22, $$23, 0.5F, -0.25F, $$16, 255, $$18, $$8, $$10, $$5);
/* 67 */     vertex($$20, $$22, $$23, 0.5F, 0.75F, $$16, 255, $$18, $$8, $$9, $$5);
/* 68 */     vertex($$20, $$22, $$23, -0.5F, 0.75F, $$16, 255, $$18, $$7, $$9, $$5);
/*    */     
/* 70 */     $$3.popPose();
/*    */     
/* 72 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */   
/*    */   private static void vertex(VertexConsumer $$0, Matrix4f $$1, Matrix3f $$2, float $$3, float $$4, int $$5, int $$6, int $$7, float $$8, float $$9, int $$10) {
/* 76 */     $$0.vertex($$1, $$3, $$4, 0.0F).color($$5, $$6, $$7, 128).uv($$8, $$9).overlayCoords(OverlayTexture.NO_OVERLAY).uv2($$10).normal($$2, 0.0F, 1.0F, 0.0F).endVertex();
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(ExperienceOrb $$0) {
/* 81 */     return EXPERIENCE_ORB_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ExperienceOrbRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */