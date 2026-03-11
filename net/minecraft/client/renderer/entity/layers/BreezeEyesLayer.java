/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.BreezeModel;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.monster.breeze.Breeze;
/*    */ 
/*    */ public class BreezeEyesLayer extends RenderLayer<Breeze, BreezeModel<Breeze>> {
/*    */   private final ResourceLocation textureLoc;
/*    */   
/*    */   public BreezeEyesLayer(RenderLayerParent<Breeze, BreezeModel<Breeze>> $$0, EntityModelSet $$1, ResourceLocation $$2) {
/* 21 */     super($$0);
/* 22 */     this.model = new BreezeModel($$1.bakeLayer(ModelLayers.BREEZE_EYES));
/* 23 */     this.textureLoc = $$2;
/*    */   }
/*    */   private final BreezeModel<Breeze> model;
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Breeze $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 28 */     this.model.prepareMobModel((Entity)$$3, $$4, $$5, $$6);
/* 29 */     getParentModel().copyPropertiesTo((EntityModel)this.model);
/*    */     
/* 31 */     VertexConsumer $$10 = $$1.getBuffer(RenderType.breezeEyes(this.textureLoc));
/* 32 */     this.model.setupAnim($$3, $$4, $$5, $$7, $$8, $$9);
/*    */     
/* 34 */     this.model.root().render($$0, $$10, $$2, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTextureLocation(Breeze $$0) {
/* 39 */     return this.textureLoc;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\BreezeEyesLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */