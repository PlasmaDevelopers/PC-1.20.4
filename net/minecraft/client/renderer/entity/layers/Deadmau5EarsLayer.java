/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.PlayerModel;
/*    */ import net.minecraft.client.player.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ public class Deadmau5EarsLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
/*    */   public Deadmau5EarsLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, AbstractClientPlayer $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 21 */     if (!"deadmau5".equals($$3.getName().getString()) || $$3.isInvisible()) {
/*    */       return;
/*    */     }
/*    */     
/* 25 */     VertexConsumer $$10 = $$1.getBuffer(RenderType.entitySolid($$3.getSkin().texture()));
/* 26 */     int $$11 = LivingEntityRenderer.getOverlayCoords((LivingEntity)$$3, 0.0F);
/*    */     
/* 28 */     for (int $$12 = 0; $$12 < 2; $$12++) {
/* 29 */       float $$13 = Mth.lerp($$6, $$3.yRotO, $$3.getYRot()) - Mth.lerp($$6, $$3.yBodyRotO, $$3.yBodyRot);
/* 30 */       float $$14 = Mth.lerp($$6, $$3.xRotO, $$3.getXRot());
/* 31 */       $$0.pushPose();
/* 32 */       $$0.mulPose(Axis.YP.rotationDegrees($$13));
/* 33 */       $$0.mulPose(Axis.XP.rotationDegrees($$14));
/* 34 */       $$0.translate(0.375F * ($$12 * 2 - 1), 0.0F, 0.0F);
/* 35 */       $$0.translate(0.0F, -0.375F, 0.0F);
/* 36 */       $$0.mulPose(Axis.XP.rotationDegrees(-$$14));
/* 37 */       $$0.mulPose(Axis.YP.rotationDegrees(-$$13));
/*    */       
/* 39 */       float $$15 = 1.3333334F;
/* 40 */       $$0.scale(1.3333334F, 1.3333334F, 1.3333334F);
/* 41 */       getParentModel().renderEars($$0, $$10, $$2, $$11);
/* 42 */       $$0.popPose();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\Deadmau5EarsLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */