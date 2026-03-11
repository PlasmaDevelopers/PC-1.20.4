/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.AbstractSkeleton;
/*    */ 
/*    */ public class WitherSkeletonRenderer extends SkeletonRenderer {
/*  9 */   private static final ResourceLocation WITHER_SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
/*    */   
/*    */   public WitherSkeletonRenderer(EntityRendererProvider.Context $$0) {
/* 12 */     super($$0, ModelLayers.WITHER_SKELETON, ModelLayers.WITHER_SKELETON_INNER_ARMOR, ModelLayers.WITHER_SKELETON_OUTER_ARMOR);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(AbstractSkeleton $$0) {
/* 17 */     return WITHER_SKELETON_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(AbstractSkeleton $$0, PoseStack $$1, float $$2) {
/* 22 */     $$1.scale(1.2F, 1.2F, 1.2F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\WitherSkeletonRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */