/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.CatModel;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.Cat;
/*    */ 
/*    */ public class CatCollarLayer extends RenderLayer<Cat, CatModel<Cat>> {
/* 13 */   private static final ResourceLocation CAT_COLLAR_LOCATION = new ResourceLocation("textures/entity/cat/cat_collar.png");
/*    */   
/*    */   private final CatModel<Cat> catModel;
/*    */   
/*    */   public CatCollarLayer(RenderLayerParent<Cat, CatModel<Cat>> $$0, EntityModelSet $$1) {
/* 18 */     super($$0);
/* 19 */     this.catModel = new CatModel($$1.bakeLayer(ModelLayers.CAT_COLLAR));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Cat $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 24 */     if (!$$3.isTame()) {
/*    */       return;
/*    */     }
/*    */     
/* 28 */     float[] $$10 = $$3.getCollarColor().getTextureDiffuseColors();
/* 29 */     coloredCutoutModelCopyLayerRender((EntityModel<Cat>)getParentModel(), (EntityModel<Cat>)this.catModel, CAT_COLLAR_LOCATION, $$0, $$1, $$2, $$3, $$4, $$5, $$7, $$8, $$9, $$6, $$10[0], $$10[1], $$10[2]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\CatCollarLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */