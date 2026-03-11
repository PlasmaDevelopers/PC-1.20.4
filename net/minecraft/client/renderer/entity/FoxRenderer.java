/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.FoxModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.FoxHeldItemLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Fox;
/*    */ 
/*    */ public class FoxRenderer extends MobRenderer<Fox, FoxModel<Fox>> {
/* 13 */   private static final ResourceLocation RED_FOX_TEXTURE = new ResourceLocation("textures/entity/fox/fox.png");
/* 14 */   private static final ResourceLocation RED_FOX_SLEEP_TEXTURE = new ResourceLocation("textures/entity/fox/fox_sleep.png");
/* 15 */   private static final ResourceLocation SNOW_FOX_TEXTURE = new ResourceLocation("textures/entity/fox/snow_fox.png");
/* 16 */   private static final ResourceLocation SNOW_FOX_SLEEP_TEXTURE = new ResourceLocation("textures/entity/fox/snow_fox_sleep.png");
/*    */   
/*    */   public FoxRenderer(EntityRendererProvider.Context $$0) {
/* 19 */     super($$0, new FoxModel($$0.bakeLayer(ModelLayers.FOX)), 0.4F);
/*    */     
/* 21 */     addLayer((RenderLayer<Fox, FoxModel<Fox>>)new FoxHeldItemLayer(this, $$0.getItemInHandRenderer()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupRotations(Fox $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 26 */     super.setupRotations($$0, $$1, $$2, $$3, $$4);
/*    */     
/* 28 */     if ($$0.isPouncing() || $$0.isFaceplanted()) {
/* 29 */       float $$5 = -Mth.lerp($$4, $$0.xRotO, $$0.getXRot());
/* 30 */       $$1.mulPose(Axis.XP.rotationDegrees($$5));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Fox $$0) {
/* 36 */     if ($$0.getVariant() == Fox.Type.RED) {
/* 37 */       return $$0.isSleeping() ? RED_FOX_SLEEP_TEXTURE : RED_FOX_TEXTURE;
/*    */     }
/* 39 */     return $$0.isSleeping() ? SNOW_FOX_SLEEP_TEXTURE : SNOW_FOX_TEXTURE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\FoxRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */