/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.PolarBearModel;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.PolarBear;
/*    */ 
/*    */ public class PolarBearRenderer extends MobRenderer<PolarBear, PolarBearModel<PolarBear>> {
/* 10 */   private static final ResourceLocation BEAR_LOCATION = new ResourceLocation("textures/entity/bear/polarbear.png");
/*    */   
/*    */   public PolarBearRenderer(EntityRendererProvider.Context $$0) {
/* 13 */     super($$0, new PolarBearModel($$0.bakeLayer(ModelLayers.POLAR_BEAR)), 0.9F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(PolarBear $$0) {
/* 18 */     return BEAR_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(PolarBear $$0, PoseStack $$1, float $$2) {
/* 23 */     $$1.scale(1.2F, 1.2F, 1.2F);
/* 24 */     super.scale($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\PolarBearRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */