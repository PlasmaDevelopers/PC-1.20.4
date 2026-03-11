/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.StrayClothingLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.monster.AbstractSkeleton;
/*    */ 
/*    */ public class StrayRenderer extends SkeletonRenderer {
/*  9 */   private static final ResourceLocation STRAY_SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/stray.png");
/*    */   
/*    */   public StrayRenderer(EntityRendererProvider.Context $$0) {
/* 12 */     super($$0, ModelLayers.STRAY, ModelLayers.STRAY_INNER_ARMOR, ModelLayers.STRAY_OUTER_ARMOR);
/*    */     
/* 14 */     addLayer((RenderLayer<AbstractSkeleton, SkeletonModel<AbstractSkeleton>>)new StrayClothingLayer(this, $$0.getModelSet()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(AbstractSkeleton $$0) {
/* 19 */     return STRAY_SKELETON_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\StrayRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */