/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.model.ArmorStandArmorModel;
/*    */ import net.minecraft.client.model.ArmorStandModel;
/*    */ import net.minecraft.client.model.HumanoidModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.decoration.ArmorStand;
/*    */ 
/*    */ public class ArmorStandRenderer extends LivingEntityRenderer<ArmorStand, ArmorStandArmorModel> {
/* 20 */   public static final ResourceLocation DEFAULT_SKIN_LOCATION = new ResourceLocation("textures/entity/armorstand/wood.png");
/*    */   
/*    */   public ArmorStandRenderer(EntityRendererProvider.Context $$0) {
/* 23 */     super($$0, new ArmorStandModel($$0.bakeLayer(ModelLayers.ARMOR_STAND)), 0.0F);
/* 24 */     addLayer((RenderLayer<ArmorStand, ArmorStandArmorModel>)new HumanoidArmorLayer(this, (HumanoidModel)new ArmorStandArmorModel($$0
/* 25 */             .bakeLayer(ModelLayers.ARMOR_STAND_INNER_ARMOR)), (HumanoidModel)new ArmorStandArmorModel($$0
/* 26 */             .bakeLayer(ModelLayers.ARMOR_STAND_OUTER_ARMOR)), $$0
/* 27 */           .getModelManager()));
/*    */     
/* 29 */     addLayer((RenderLayer<ArmorStand, ArmorStandArmorModel>)new ItemInHandLayer(this, $$0.getItemInHandRenderer()));
/* 30 */     addLayer((RenderLayer<ArmorStand, ArmorStandArmorModel>)new ElytraLayer(this, $$0.getModelSet()));
/* 31 */     addLayer((RenderLayer<ArmorStand, ArmorStandArmorModel>)new CustomHeadLayer(this, $$0.getModelSet(), $$0.getItemInHandRenderer()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(ArmorStand $$0) {
/* 36 */     return DEFAULT_SKIN_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupRotations(ArmorStand $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 41 */     $$1.mulPose(Axis.YP.rotationDegrees(180.0F - $$3));
/*    */     
/* 43 */     float $$5 = (float)($$0.level().getGameTime() - $$0.lastHit) + $$4;
/* 44 */     if ($$5 < 5.0F) {
/* 45 */       $$1.mulPose(Axis.YP.rotationDegrees(Mth.sin($$5 / 1.5F * 3.1415927F) * 3.0F));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldShowName(ArmorStand $$0) {
/* 51 */     double $$1 = this.entityRenderDispatcher.distanceToSqr((Entity)$$0);
/* 52 */     float $$2 = $$0.isCrouching() ? 32.0F : 64.0F;
/* 53 */     if ($$1 >= ($$2 * $$2)) {
/* 54 */       return false;
/*    */     }
/*    */     
/* 57 */     return $$0.isCustomNameVisible();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected RenderType getRenderType(ArmorStand $$0, boolean $$1, boolean $$2, boolean $$3) {
/* 63 */     if (!$$0.isMarker()) {
/* 64 */       return super.getRenderType($$0, $$1, $$2, $$3);
/*    */     }
/*    */     
/* 67 */     ResourceLocation $$4 = getTextureLocation($$0);
/* 68 */     if ($$2) {
/* 69 */       return RenderType.entityTranslucent($$4, false);
/*    */     }
/* 71 */     if ($$1) {
/* 72 */       return RenderType.entityCutoutNoCull($$4, false);
/*    */     }
/* 74 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ArmorStandRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */