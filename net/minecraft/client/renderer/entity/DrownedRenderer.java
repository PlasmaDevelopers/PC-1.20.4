/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.DrownedModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.DrownedOuterLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Drowned;
/*    */ import net.minecraft.world.entity.monster.Zombie;
/*    */ 
/*    */ public class DrownedRenderer extends AbstractZombieRenderer<Drowned, DrownedModel<Drowned>> {
/* 14 */   private static final ResourceLocation DROWNED_LOCATION = new ResourceLocation("textures/entity/zombie/drowned.png");
/*    */   
/*    */   public DrownedRenderer(EntityRendererProvider.Context $$0) {
/* 17 */     super($$0, new DrownedModel($$0
/* 18 */           .bakeLayer(ModelLayers.DROWNED)), new DrownedModel($$0
/* 19 */           .bakeLayer(ModelLayers.DROWNED_INNER_ARMOR)), new DrownedModel($$0
/* 20 */           .bakeLayer(ModelLayers.DROWNED_OUTER_ARMOR)));
/*    */     
/* 22 */     addLayer((RenderLayer<Drowned, DrownedModel<Drowned>>)new DrownedOuterLayer(this, $$0.getModelSet()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Zombie $$0) {
/* 27 */     return DROWNED_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupRotations(Drowned $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 32 */     super.setupRotations($$0, $$1, $$2, $$3, $$4);
/*    */     
/* 34 */     float $$5 = $$0.getSwimAmount($$4);
/* 35 */     if ($$5 > 0.0F) {
/* 36 */       float $$6 = -10.0F - $$0.getXRot();
/* 37 */       float $$7 = Mth.lerp($$5, 0.0F, $$6);
/* 38 */       $$1.rotateAround(Axis.XP.rotationDegrees($$7), 0.0F, $$0.getBbHeight() / 2.0F, 0.0F);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\DrownedRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */