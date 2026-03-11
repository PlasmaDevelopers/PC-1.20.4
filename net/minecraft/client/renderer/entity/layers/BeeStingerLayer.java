/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.PlayerModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.LivingEntityRenderer;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import org.joml.Matrix3f;
/*    */ import org.joml.Matrix4f;
/*    */ 
/*    */ public class BeeStingerLayer<T extends LivingEntity, M extends PlayerModel<T>> extends StuckInBodyLayer<T, M> {
/* 19 */   private static final ResourceLocation BEE_STINGER_LOCATION = new ResourceLocation("textures/entity/bee/bee_stinger.png");
/*    */   
/*    */   public BeeStingerLayer(LivingEntityRenderer<T, M> $$0) {
/* 22 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int numStuck(T $$0) {
/* 27 */     return $$0.getStingerCount();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderStuckItem(PoseStack $$0, MultiBufferSource $$1, int $$2, Entity $$3, float $$4, float $$5, float $$6, float $$7) {
/* 32 */     float $$8 = Mth.sqrt($$4 * $$4 + $$6 * $$6);
/* 33 */     float $$9 = (float)(Math.atan2($$4, $$6) * 57.2957763671875D);
/* 34 */     float $$10 = (float)(Math.atan2($$5, $$8) * 57.2957763671875D);
/*    */     
/* 36 */     $$0.translate(0.0F, 0.0F, 0.0F);
/* 37 */     $$0.mulPose(Axis.YP.rotationDegrees($$9 - 90.0F));
/* 38 */     $$0.mulPose(Axis.ZP.rotationDegrees($$10));
/*    */     
/* 40 */     float $$11 = 0.0F;
/* 41 */     float $$12 = 0.125F;
/* 42 */     float $$13 = 0.0F;
/* 43 */     float $$14 = 0.0625F;
/*    */     
/* 45 */     float $$15 = 0.03125F;
/*    */     
/* 47 */     $$0.mulPose(Axis.XP.rotationDegrees(45.0F));
/* 48 */     $$0.scale(0.03125F, 0.03125F, 0.03125F);
/*    */     
/* 50 */     $$0.translate(2.5F, 0.0F, 0.0F);
/*    */     
/* 52 */     VertexConsumer $$16 = $$1.getBuffer(RenderType.entityCutoutNoCull(BEE_STINGER_LOCATION));
/* 53 */     for (int $$17 = 0; $$17 < 4; $$17++) {
/* 54 */       $$0.mulPose(Axis.XP.rotationDegrees(90.0F));
/* 55 */       PoseStack.Pose $$18 = $$0.last();
/* 56 */       Matrix4f $$19 = $$18.pose();
/* 57 */       Matrix3f $$20 = $$18.normal();
/*    */       
/* 59 */       vertex($$16, $$19, $$20, -4.5F, -1, 0.0F, 0.0F, $$2);
/* 60 */       vertex($$16, $$19, $$20, 4.5F, -1, 0.125F, 0.0F, $$2);
/* 61 */       vertex($$16, $$19, $$20, 4.5F, 1, 0.125F, 0.0625F, $$2);
/* 62 */       vertex($$16, $$19, $$20, -4.5F, 1, 0.0F, 0.0625F, $$2);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void vertex(VertexConsumer $$0, Matrix4f $$1, Matrix3f $$2, float $$3, int $$4, float $$5, float $$6, int $$7) {
/* 67 */     $$0.vertex($$1, $$3, $$4, 0.0F).color(255, 255, 255, 255).uv($$5, $$6).overlayCoords(OverlayTexture.NO_OVERLAY).uv2($$7).normal($$2, 0.0F, 1.0F, 0.0F).endVertex();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\BeeStingerLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */