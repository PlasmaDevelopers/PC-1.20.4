/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.PlayerModel;
/*    */ import net.minecraft.client.player.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.client.resources.PlayerSkin;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.player.PlayerModelPart;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ 
/*    */ public class CapeLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
/*    */   public CapeLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> $$0) {
/* 21 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, AbstractClientPlayer $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 26 */     if ($$3.isInvisible() || !$$3.isModelPartShown(PlayerModelPart.CAPE)) {
/*    */       return;
/*    */     }
/*    */     
/* 30 */     PlayerSkin $$10 = $$3.getSkin();
/* 31 */     if ($$10.capeTexture() == null) {
/*    */       return;
/*    */     }
/*    */     
/* 35 */     ItemStack $$11 = $$3.getItemBySlot(EquipmentSlot.CHEST);
/* 36 */     if ($$11.is(Items.ELYTRA)) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     $$0.pushPose();
/* 41 */     $$0.translate(0.0F, 0.0F, 0.125F);
/*    */     
/* 43 */     double $$12 = Mth.lerp($$6, $$3.xCloakO, $$3.xCloak) - Mth.lerp($$6, $$3.xo, $$3.getX());
/* 44 */     double $$13 = Mth.lerp($$6, $$3.yCloakO, $$3.yCloak) - Mth.lerp($$6, $$3.yo, $$3.getY());
/* 45 */     double $$14 = Mth.lerp($$6, $$3.zCloakO, $$3.zCloak) - Mth.lerp($$6, $$3.zo, $$3.getZ());
/*    */     
/* 47 */     float $$15 = Mth.rotLerp($$6, $$3.yBodyRotO, $$3.yBodyRot);
/*    */     
/* 49 */     double $$16 = Mth.sin($$15 * 0.017453292F);
/* 50 */     double $$17 = -Mth.cos($$15 * 0.017453292F);
/*    */     
/* 52 */     float $$18 = (float)$$13 * 10.0F;
/* 53 */     $$18 = Mth.clamp($$18, -6.0F, 32.0F);
/* 54 */     float $$19 = (float)($$12 * $$16 + $$14 * $$17) * 100.0F;
/* 55 */     $$19 = Mth.clamp($$19, 0.0F, 150.0F);
/* 56 */     float $$20 = (float)($$12 * $$17 - $$14 * $$16) * 100.0F;
/* 57 */     $$20 = Mth.clamp($$20, -20.0F, 20.0F);
/* 58 */     if ($$19 < 0.0F) {
/* 59 */       $$19 = 0.0F;
/*    */     }
/*    */     
/* 62 */     float $$21 = Mth.lerp($$6, $$3.oBob, $$3.bob);
/*    */     
/* 64 */     $$18 += Mth.sin(Mth.lerp($$6, $$3.walkDistO, $$3.walkDist) * 6.0F) * 32.0F * $$21;
/* 65 */     if ($$3.isCrouching()) {
/* 66 */       $$18 += 25.0F;
/*    */     }
/*    */     
/* 69 */     $$0.mulPose(Axis.XP.rotationDegrees(6.0F + $$19 / 2.0F + $$18));
/* 70 */     $$0.mulPose(Axis.ZP.rotationDegrees($$20 / 2.0F));
/* 71 */     $$0.mulPose(Axis.YP.rotationDegrees(180.0F - $$20 / 2.0F));
/* 72 */     VertexConsumer $$22 = $$1.getBuffer(RenderType.entitySolid($$10.capeTexture()));
/* 73 */     getParentModel().renderCloak($$0, $$22, $$2, OverlayTexture.NO_OVERLAY);
/* 74 */     $$0.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\CapeLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */