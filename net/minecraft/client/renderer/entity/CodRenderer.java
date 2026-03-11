/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.CodModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Cod;
/*    */ 
/*    */ public class CodRenderer extends MobRenderer<Cod, CodModel<Cod>> {
/* 12 */   private static final ResourceLocation COD_LOCATION = new ResourceLocation("textures/entity/fish/cod.png");
/*    */   
/*    */   public CodRenderer(EntityRendererProvider.Context $$0) {
/* 15 */     super($$0, new CodModel($$0.bakeLayer(ModelLayers.COD)), 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Cod $$0) {
/* 20 */     return COD_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupRotations(Cod $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 25 */     super.setupRotations($$0, $$1, $$2, $$3, $$4);
/*    */     
/* 27 */     float $$5 = 4.3F * Mth.sin(0.6F * $$2);
/* 28 */     $$1.mulPose(Axis.YP.rotationDegrees($$5));
/*    */     
/* 30 */     if (!$$0.isInWater()) {
/* 31 */       $$1.translate(0.1F, 0.1F, -0.1F);
/* 32 */       $$1.mulPose(Axis.ZP.rotationDegrees(90.0F));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\CodRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */