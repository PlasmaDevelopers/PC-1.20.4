/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*    */ import org.joml.Matrix3f;
/*    */ import org.joml.Matrix4f;
/*    */ 
/*    */ public abstract class ArrowRenderer<T extends AbstractArrow> extends EntityRenderer<T> {
/*    */   public ArrowRenderer(EntityRendererProvider.Context $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(T $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 21 */     $$3.pushPose();
/*    */     
/* 23 */     $$3.mulPose(Axis.YP.rotationDegrees(Mth.lerp($$2, ((AbstractArrow)$$0).yRotO, $$0.getYRot()) - 90.0F));
/* 24 */     $$3.mulPose(Axis.ZP.rotationDegrees(Mth.lerp($$2, ((AbstractArrow)$$0).xRotO, $$0.getXRot())));
/*    */     
/* 26 */     int $$6 = 0;
/*    */     
/* 28 */     float $$7 = 0.0F;
/* 29 */     float $$8 = 0.5F;
/* 30 */     float $$9 = 0.0F;
/* 31 */     float $$10 = 0.15625F;
/*    */     
/* 33 */     float $$11 = 0.0F;
/* 34 */     float $$12 = 0.15625F;
/* 35 */     float $$13 = 0.15625F;
/* 36 */     float $$14 = 0.3125F;
/* 37 */     float $$15 = 0.05625F;
/* 38 */     float $$16 = ((AbstractArrow)$$0).shakeTime - $$2;
/* 39 */     if ($$16 > 0.0F) {
/* 40 */       float $$17 = -Mth.sin($$16 * 3.0F) * $$16;
/* 41 */       $$3.mulPose(Axis.ZP.rotationDegrees($$17));
/*    */     } 
/* 43 */     $$3.mulPose(Axis.XP.rotationDegrees(45.0F));
/* 44 */     $$3.scale(0.05625F, 0.05625F, 0.05625F);
/*    */     
/* 46 */     $$3.translate(-4.0F, 0.0F, 0.0F);
/*    */     
/* 48 */     VertexConsumer $$18 = $$4.getBuffer(RenderType.entityCutout(getTextureLocation($$0)));
/*    */     
/* 50 */     PoseStack.Pose $$19 = $$3.last();
/* 51 */     Matrix4f $$20 = $$19.pose();
/* 52 */     Matrix3f $$21 = $$19.normal();
/*    */     
/* 54 */     vertex($$20, $$21, $$18, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, $$5);
/* 55 */     vertex($$20, $$21, $$18, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, $$5);
/* 56 */     vertex($$20, $$21, $$18, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, $$5);
/* 57 */     vertex($$20, $$21, $$18, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, $$5);
/*    */     
/* 59 */     vertex($$20, $$21, $$18, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, $$5);
/* 60 */     vertex($$20, $$21, $$18, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, $$5);
/* 61 */     vertex($$20, $$21, $$18, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, $$5);
/* 62 */     vertex($$20, $$21, $$18, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, $$5);
/*    */     
/* 64 */     for (int $$22 = 0; $$22 < 4; $$22++) {
/* 65 */       $$3.mulPose(Axis.XP.rotationDegrees(90.0F));
/* 66 */       vertex($$20, $$21, $$18, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, $$5);
/* 67 */       vertex($$20, $$21, $$18, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, $$5);
/* 68 */       vertex($$20, $$21, $$18, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, $$5);
/* 69 */       vertex($$20, $$21, $$18, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, $$5);
/*    */     } 
/*    */     
/* 72 */     $$3.popPose();
/*    */     
/* 74 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */   
/*    */   public void vertex(Matrix4f $$0, Matrix3f $$1, VertexConsumer $$2, int $$3, int $$4, int $$5, float $$6, float $$7, int $$8, int $$9, int $$10, int $$11) {
/* 78 */     $$2.vertex($$0, $$3, $$4, $$5).color(255, 255, 255, 255).uv($$6, $$7).overlayCoords(OverlayTexture.NO_OVERLAY).uv2($$11).normal($$1, $$8, $$10, $$9).endVertex();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ArrowRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */