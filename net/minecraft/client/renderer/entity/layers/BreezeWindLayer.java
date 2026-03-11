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
/*    */ public class BreezeWindLayer extends RenderLayer<Breeze, BreezeModel<Breeze>> {
/*    */   private static final float TOP_PART_ALPHA = 1.0F;
/*    */   private static final float MIDDLE_PART_ALPHA = 1.0F;
/*    */   private static final float BOTTOM_PART_ALPHA = 1.0F;
/*    */   private final ResourceLocation textureLoc;
/*    */   private final BreezeModel<Breeze> model;
/*    */   
/*    */   public BreezeWindLayer(RenderLayerParent<Breeze, BreezeModel<Breeze>> $$0, EntityModelSet $$1, ResourceLocation $$2) {
/* 25 */     super($$0);
/* 26 */     this.model = new BreezeModel($$1.bakeLayer(ModelLayers.BREEZE_WIND));
/* 27 */     this.textureLoc = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Breeze $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 32 */     float $$10 = $$3.tickCount + $$6;
/*    */     
/* 34 */     this.model.prepareMobModel((Entity)$$3, $$4, $$5, $$6);
/* 35 */     getParentModel().copyPropertiesTo((EntityModel)this.model);
/*    */     
/* 37 */     VertexConsumer $$11 = $$1.getBuffer(RenderType.breezeWind(getTextureLocation($$3), xOffset($$10) % 1.0F, 0.0F));
/* 38 */     this.model.setupAnim($$3, $$4, $$5, $$7, $$8, $$9);
/*    */ 
/*    */     
/* 41 */     (this.model.windTop()).skipDraw = true;
/* 42 */     (this.model.windMiddle()).skipDraw = true;
/* 43 */     (this.model.windBottom()).skipDraw = false;
/* 44 */     this.model.root().render($$0, $$11, $$2, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */ 
/*    */     
/* 47 */     (this.model.windTop()).skipDraw = true;
/* 48 */     (this.model.windMiddle()).skipDraw = false;
/* 49 */     (this.model.windBottom()).skipDraw = true;
/* 50 */     this.model.root().render($$0, $$11, $$2, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */ 
/*    */     
/* 53 */     (this.model.windTop()).skipDraw = false;
/* 54 */     (this.model.windMiddle()).skipDraw = true;
/* 55 */     (this.model.windBottom()).skipDraw = true;
/* 56 */     this.model.root().render($$0, $$11, $$2, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */   
/*    */   private float xOffset(float $$0) {
/* 60 */     return $$0 * 0.02F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ResourceLocation getTextureLocation(Breeze $$0) {
/* 65 */     return this.textureLoc;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\BreezeWindLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */