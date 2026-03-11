/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.IronGolemModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.IronGolemFlowerLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.IronGolem;
/*    */ 
/*    */ public class IronGolemRenderer extends MobRenderer<IronGolem, IronGolemModel<IronGolem>> {
/* 13 */   private static final ResourceLocation GOLEM_LOCATION = new ResourceLocation("textures/entity/iron_golem/iron_golem.png");
/*    */   
/*    */   public IronGolemRenderer(EntityRendererProvider.Context $$0) {
/* 16 */     super($$0, new IronGolemModel($$0.bakeLayer(ModelLayers.IRON_GOLEM)), 0.7F);
/* 17 */     addLayer((RenderLayer<IronGolem, IronGolemModel<IronGolem>>)new IronGolemCrackinessLayer(this));
/* 18 */     addLayer((RenderLayer<IronGolem, IronGolemModel<IronGolem>>)new IronGolemFlowerLayer(this, $$0.getBlockRenderDispatcher()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(IronGolem $$0) {
/* 23 */     return GOLEM_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupRotations(IronGolem $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 28 */     super.setupRotations($$0, $$1, $$2, $$3, $$4);
/* 29 */     if ($$0.walkAnimation.speed() < 0.01D) {
/*    */       return;
/*    */     }
/*    */     
/* 33 */     float $$5 = 13.0F;
/* 34 */     float $$6 = $$0.walkAnimation.position($$4) + 6.0F;
/* 35 */     float $$7 = (Math.abs($$6 % 13.0F - 6.5F) - 3.25F) / 3.25F;
/* 36 */     $$1.mulPose(Axis.ZP.rotationDegrees(6.5F * $$7));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\IronGolemRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */