/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.HumanoidModel;
/*    */ import net.minecraft.client.model.SkeletonModel;
/*    */ import net.minecraft.client.model.geom.ModelLayerLocation;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.AbstractSkeleton;
/*    */ 
/*    */ public class SkeletonRenderer extends HumanoidMobRenderer<AbstractSkeleton, SkeletonModel<AbstractSkeleton>> {
/* 11 */   private static final ResourceLocation SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/skeleton.png");
/*    */   
/*    */   public SkeletonRenderer(EntityRendererProvider.Context $$0) {
/* 14 */     this($$0, ModelLayers.SKELETON, ModelLayers.SKELETON_INNER_ARMOR, ModelLayers.SKELETON_OUTER_ARMOR);
/*    */   }
/*    */   
/*    */   public SkeletonRenderer(EntityRendererProvider.Context $$0, ModelLayerLocation $$1, ModelLayerLocation $$2, ModelLayerLocation $$3) {
/* 18 */     super($$0, new SkeletonModel($$0.bakeLayer($$1)), 0.5F);
/*    */     
/* 20 */     addLayer((RenderLayer<AbstractSkeleton, SkeletonModel<AbstractSkeleton>>)new HumanoidArmorLayer(this, (HumanoidModel)new SkeletonModel($$0
/* 21 */             .bakeLayer($$2)), (HumanoidModel)new SkeletonModel($$0
/* 22 */             .bakeLayer($$3)), $$0
/* 23 */           .getModelManager()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(AbstractSkeleton $$0) {
/* 29 */     return SKELETON_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isShaking(AbstractSkeleton $$0) {
/* 34 */     return $$0.isShaking();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\SkeletonRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */