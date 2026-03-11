/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.WardenModel;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.WardenEmissiveLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.monster.warden.Warden;
/*    */ 
/*    */ public class WardenRenderer extends MobRenderer<Warden, WardenModel<Warden>> {
/* 11 */   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/warden/warden.png");
/* 12 */   private static final ResourceLocation BIOLUMINESCENT_LAYER_TEXTURE = new ResourceLocation("textures/entity/warden/warden_bioluminescent_layer.png");
/* 13 */   private static final ResourceLocation HEART_TEXTURE = new ResourceLocation("textures/entity/warden/warden_heart.png");
/* 14 */   private static final ResourceLocation PULSATING_SPOTS_TEXTURE_1 = new ResourceLocation("textures/entity/warden/warden_pulsating_spots_1.png");
/* 15 */   private static final ResourceLocation PULSATING_SPOTS_TEXTURE_2 = new ResourceLocation("textures/entity/warden/warden_pulsating_spots_2.png");
/*    */   
/*    */   public WardenRenderer(EntityRendererProvider.Context $$0) {
/* 18 */     super($$0, new WardenModel($$0.bakeLayer(ModelLayers.WARDEN)), 0.9F);
/*    */     
/* 20 */     addLayer((RenderLayer<Warden, WardenModel<Warden>>)new WardenEmissiveLayer(this, BIOLUMINESCENT_LAYER_TEXTURE, ($$0, $$1, $$2) -> 1.0F, WardenModel::getBioluminescentLayerModelParts));
/* 21 */     addLayer((RenderLayer<Warden, WardenModel<Warden>>)new WardenEmissiveLayer(this, PULSATING_SPOTS_TEXTURE_1, ($$0, $$1, $$2) -> Math.max(0.0F, Mth.cos($$2 * 0.045F) * 0.25F), WardenModel::getPulsatingSpotsLayerModelParts));
/* 22 */     addLayer((RenderLayer<Warden, WardenModel<Warden>>)new WardenEmissiveLayer(this, PULSATING_SPOTS_TEXTURE_2, ($$0, $$1, $$2) -> Math.max(0.0F, Mth.cos($$2 * 0.045F + 3.1415927F) * 0.25F), WardenModel::getPulsatingSpotsLayerModelParts));
/* 23 */     addLayer((RenderLayer<Warden, WardenModel<Warden>>)new WardenEmissiveLayer(this, TEXTURE, ($$0, $$1, $$2) -> $$0.getTendrilAnimation($$1), WardenModel::getTendrilsLayerModelParts));
/* 24 */     addLayer((RenderLayer<Warden, WardenModel<Warden>>)new WardenEmissiveLayer(this, HEART_TEXTURE, ($$0, $$1, $$2) -> $$0.getHeartAnimation($$1), WardenModel::getHeartLayerModelParts));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Warden $$0) {
/* 29 */     return TEXTURE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\WardenRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */