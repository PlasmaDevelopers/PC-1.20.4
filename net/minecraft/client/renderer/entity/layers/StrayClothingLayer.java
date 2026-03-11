/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.SkeletonModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.monster.RangedAttackMob;
/*    */ 
/*    */ public class StrayClothingLayer<T extends Mob & RangedAttackMob, M extends EntityModel<T>> extends RenderLayer<T, M> {
/* 15 */   private static final ResourceLocation STRAY_CLOTHES_LOCATION = new ResourceLocation("textures/entity/skeleton/stray_overlay.png");
/*    */   
/*    */   private final SkeletonModel<T> layerModel;
/*    */   
/*    */   public StrayClothingLayer(RenderLayerParent<T, M> $$0, EntityModelSet $$1) {
/* 20 */     super($$0);
/* 21 */     this.layerModel = new SkeletonModel($$1.bakeLayer(ModelLayers.STRAY_OUTER_LAYER));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 26 */     coloredCutoutModelCopyLayerRender((EntityModel<T>)getParentModel(), (EntityModel<T>)this.layerModel, STRAY_CLOTHES_LOCATION, $$0, $$1, $$2, $$3, $$4, $$5, $$7, $$8, $$9, $$6, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\StrayClothingLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */