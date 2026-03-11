/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.ShulkerModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.LivingEntityRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Shulker;
/*    */ 
/*    */ public class ShulkerHeadLayer extends RenderLayer<Shulker, ShulkerModel<Shulker>> {
/*    */   public ShulkerHeadLayer(RenderLayerParent<Shulker, ShulkerModel<Shulker>> $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Shulker $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 21 */     ResourceLocation $$10 = ShulkerRenderer.getTextureLocation($$3.getColor());
/* 22 */     VertexConsumer $$11 = $$1.getBuffer(RenderType.entitySolid($$10));
/*    */     
/* 24 */     getParentModel().getHead().render($$0, $$11, $$2, LivingEntityRenderer.getOverlayCoords((LivingEntity)$$3, 0.0F));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\ShulkerHeadLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */