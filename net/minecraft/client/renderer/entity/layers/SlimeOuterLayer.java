/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.SlimeModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.LivingEntityRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ public class SlimeOuterLayer<T extends LivingEntity>
/*    */   extends RenderLayer<T, SlimeModel<T>> {
/*    */   public SlimeOuterLayer(RenderLayerParent<T, SlimeModel<T>> $$0, EntityModelSet $$1) {
/* 20 */     super($$0);
/* 21 */     this.model = (EntityModel<T>)new SlimeModel($$1.bakeLayer(ModelLayers.SLIME_OUTER));
/*    */   }
/*    */   private final EntityModel<T> model;
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/*    */     VertexConsumer $$13;
/* 26 */     Minecraft $$10 = Minecraft.getInstance();
/* 27 */     boolean $$11 = ($$10.shouldEntityAppearGlowing((Entity)$$3) && $$3.isInvisible());
/*    */     
/* 29 */     if ($$3.isInvisible() && !$$11) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 34 */     if ($$11) {
/* 35 */       VertexConsumer $$12 = $$1.getBuffer(RenderType.outline(getTextureLocation($$3)));
/*    */     } else {
/* 37 */       $$13 = $$1.getBuffer(RenderType.entityTranslucent(getTextureLocation($$3)));
/*    */     } 
/*    */     
/* 40 */     getParentModel().copyPropertiesTo(this.model);
/* 41 */     this.model.prepareMobModel((Entity)$$3, $$4, $$5, $$6);
/* 42 */     this.model.setupAnim((Entity)$$3, $$4, $$5, $$7, $$8, $$9);
/* 43 */     this.model.renderToBuffer($$0, $$13, $$2, LivingEntityRenderer.getOverlayCoords((LivingEntity)$$3, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\SlimeOuterLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */