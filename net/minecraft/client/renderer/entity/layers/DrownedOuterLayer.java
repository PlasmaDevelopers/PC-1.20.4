/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.DrownedModel;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class DrownedOuterLayer<T extends Drowned> extends RenderLayer<T, DrownedModel<T>> {
/* 13 */   private static final ResourceLocation DROWNED_OUTER_LAYER_LOCATION = new ResourceLocation("textures/entity/zombie/drowned_outer_layer.png");
/*    */   
/*    */   private final DrownedModel<T> model;
/*    */   
/*    */   public DrownedOuterLayer(RenderLayerParent<T, DrownedModel<T>> $$0, EntityModelSet $$1) {
/* 18 */     super($$0);
/* 19 */     this.model = new DrownedModel($$1.bakeLayer(ModelLayers.DROWNED_OUTER_LAYER));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 24 */     coloredCutoutModelCopyLayerRender((EntityModel<T>)getParentModel(), (EntityModel<T>)this.model, DROWNED_OUTER_LAYER_LOCATION, $$0, $$1, $$2, $$3, $$4, $$5, $$7, $$8, $$9, $$6, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\DrownedOuterLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */