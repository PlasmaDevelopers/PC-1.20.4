/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.FoxModel;
/*    */ import net.minecraft.client.renderer.ItemInHandRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Fox;
/*    */ import net.minecraft.world.item.ItemDisplayContext;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class FoxHeldItemLayer extends RenderLayer<Fox, FoxModel<Fox>> {
/*    */   public FoxHeldItemLayer(RenderLayerParent<Fox, FoxModel<Fox>> $$0, ItemInHandRenderer $$1) {
/* 18 */     super($$0);
/* 19 */     this.itemInHandRenderer = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Fox $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 24 */     boolean $$10 = $$3.isSleeping();
/* 25 */     boolean $$11 = $$3.isBaby();
/*    */     
/* 27 */     $$0.pushPose();
/*    */ 
/*    */     
/* 30 */     if ($$11) {
/* 31 */       float $$12 = 0.75F;
/* 32 */       $$0.scale(0.75F, 0.75F, 0.75F);
/* 33 */       $$0.translate(0.0F, 0.5F, 0.209375F);
/*    */     } 
/*    */     
/* 36 */     $$0.translate((getParentModel()).head.x / 16.0F, (getParentModel()).head.y / 16.0F, (getParentModel()).head.z / 16.0F);
/*    */ 
/*    */     
/* 39 */     float $$13 = $$3.getHeadRollAngle($$6);
/* 40 */     $$0.mulPose(Axis.ZP.rotation($$13));
/* 41 */     $$0.mulPose(Axis.YP.rotationDegrees($$8));
/* 42 */     $$0.mulPose(Axis.XP.rotationDegrees($$9));
/*    */ 
/*    */     
/* 45 */     if ($$3.isBaby()) {
/* 46 */       if ($$10) {
/* 47 */         $$0.translate(0.4F, 0.26F, 0.15F);
/*    */       } else {
/* 49 */         $$0.translate(0.06F, 0.26F, -0.5F);
/*    */       }
/*    */     
/* 52 */     } else if ($$10) {
/* 53 */       $$0.translate(0.46F, 0.26F, 0.22F);
/*    */     } else {
/* 55 */       $$0.translate(0.06F, 0.27F, -0.5F);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 60 */     $$0.mulPose(Axis.XP.rotationDegrees(90.0F));
/*    */     
/* 62 */     if ($$10) {
/* 63 */       $$0.mulPose(Axis.ZP.rotationDegrees(90.0F));
/*    */     }
/*    */     
/* 66 */     ItemStack $$14 = $$3.getItemBySlot(EquipmentSlot.MAINHAND);
/* 67 */     this.itemInHandRenderer.renderItem((LivingEntity)$$3, $$14, ItemDisplayContext.GROUND, false, $$0, $$1, $$2);
/*    */     
/* 69 */     $$0.popPose();
/*    */   }
/*    */   
/*    */   private final ItemInHandRenderer itemInHandRenderer;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\FoxHeldItemLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */