/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.ElytraModel;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.player.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.client.resources.PlayerSkin;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.PlayerModelPart;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ 
/*    */ public class ElytraLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
/* 24 */   private static final ResourceLocation WINGS_LOCATION = new ResourceLocation("textures/entity/elytra.png");
/*    */   
/*    */   private final ElytraModel<T> elytraModel;
/*    */   
/*    */   public ElytraLayer(RenderLayerParent<T, M> $$0, EntityModelSet $$1) {
/* 29 */     super($$0);
/* 30 */     this.elytraModel = new ElytraModel($$1.bakeLayer(ModelLayers.ELYTRA));
/*    */   }
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/*    */     ResourceLocation $$16;
/* 35 */     ItemStack $$10 = $$3.getItemBySlot(EquipmentSlot.CHEST);
/* 36 */     if (!$$10.is(Items.ELYTRA)) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 42 */     if ($$3 instanceof AbstractClientPlayer) { AbstractClientPlayer $$11 = (AbstractClientPlayer)$$3;
/* 43 */       PlayerSkin $$12 = $$11.getSkin();
/* 44 */       if ($$12.elytraTexture() != null) {
/* 45 */         ResourceLocation $$13 = $$12.elytraTexture();
/* 46 */       } else if ($$12.capeTexture() != null && $$11.isModelPartShown(PlayerModelPart.CAPE)) {
/* 47 */         ResourceLocation $$14 = $$12.capeTexture();
/*    */       } else {
/* 49 */         ResourceLocation $$15 = WINGS_LOCATION;
/*    */       }  }
/*    */     else
/* 52 */     { $$16 = WINGS_LOCATION; }
/*    */ 
/*    */     
/* 55 */     $$0.pushPose();
/* 56 */     $$0.translate(0.0F, 0.0F, 0.125F);
/*    */     
/* 58 */     getParentModel().copyPropertiesTo((EntityModel)this.elytraModel);
/* 59 */     this.elytraModel.setupAnim((LivingEntity)$$3, $$4, $$5, $$7, $$8, $$9);
/*    */     
/* 61 */     VertexConsumer $$17 = ItemRenderer.getArmorFoilBuffer($$1, RenderType.armorCutoutNoCull($$16), false, $$10.hasFoil());
/* 62 */     this.elytraModel.renderToBuffer($$0, $$17, $$2, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 64 */     $$0.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\ElytraLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */