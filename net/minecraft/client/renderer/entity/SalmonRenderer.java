/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.SalmonModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Salmon;
/*    */ 
/*    */ public class SalmonRenderer extends MobRenderer<Salmon, SalmonModel<Salmon>> {
/* 12 */   private static final ResourceLocation SALMON_LOCATION = new ResourceLocation("textures/entity/fish/salmon.png");
/*    */   
/*    */   public SalmonRenderer(EntityRendererProvider.Context $$0) {
/* 15 */     super($$0, new SalmonModel($$0.bakeLayer(ModelLayers.SALMON)), 0.4F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Salmon $$0) {
/* 20 */     return SALMON_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupRotations(Salmon $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 25 */     super.setupRotations($$0, $$1, $$2, $$3, $$4);
/*    */     
/* 27 */     float $$5 = 1.0F;
/* 28 */     float $$6 = 1.0F;
/* 29 */     if (!$$0.isInWater()) {
/* 30 */       $$5 = 1.3F;
/* 31 */       $$6 = 1.7F;
/*    */     } 
/*    */     
/* 34 */     float $$7 = $$5 * 4.3F * Mth.sin($$6 * 0.6F * $$2);
/* 35 */     $$1.mulPose(Axis.YP.rotationDegrees($$7));
/*    */     
/* 37 */     $$1.translate(0.0F, 0.0F, -0.4F);
/* 38 */     if (!$$0.isInWater()) {
/* 39 */       $$1.translate(0.2F, 0.1F, 0.0F);
/* 40 */       $$1.mulPose(Axis.ZP.rotationDegrees(90.0F));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\SalmonRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */