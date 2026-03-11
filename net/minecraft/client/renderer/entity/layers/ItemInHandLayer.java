/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.ArmedModel;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.renderer.ItemInHandRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.HumanoidArm;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.ItemDisplayContext;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ItemInHandLayer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel>
/*    */   extends RenderLayer<T, M> {
/*    */   public ItemInHandLayer(RenderLayerParent<T, M> $$0, ItemInHandRenderer $$1) {
/* 19 */     super($$0);
/* 20 */     this.itemInHandRenderer = $$1;
/*    */   }
/*    */   private final ItemInHandRenderer itemInHandRenderer;
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 25 */     boolean $$10 = ($$3.getMainArm() == HumanoidArm.RIGHT);
/*    */     
/* 27 */     ItemStack $$11 = $$10 ? $$3.getOffhandItem() : $$3.getMainHandItem();
/* 28 */     ItemStack $$12 = $$10 ? $$3.getMainHandItem() : $$3.getOffhandItem();
/*    */     
/* 30 */     if ($$11.isEmpty() && $$12.isEmpty()) {
/*    */       return;
/*    */     }
/*    */     
/* 34 */     $$0.pushPose();
/*    */     
/* 36 */     if (((EntityModel)getParentModel()).young) {
/* 37 */       float $$13 = 0.5F;
/* 38 */       $$0.translate(0.0F, 0.75F, 0.0F);
/* 39 */       $$0.scale(0.5F, 0.5F, 0.5F);
/*    */     } 
/*    */     
/* 42 */     renderArmWithItem((LivingEntity)$$3, $$12, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, $$0, $$1, $$2);
/* 43 */     renderArmWithItem((LivingEntity)$$3, $$11, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, $$0, $$1, $$2);
/*    */     
/* 45 */     $$0.popPose();
/*    */   }
/*    */   
/*    */   protected void renderArmWithItem(LivingEntity $$0, ItemStack $$1, ItemDisplayContext $$2, HumanoidArm $$3, PoseStack $$4, MultiBufferSource $$5, int $$6) {
/* 49 */     if ($$1.isEmpty()) {
/*    */       return;
/*    */     }
/*    */     
/* 53 */     $$4.pushPose();
/* 54 */     ((ArmedModel)getParentModel()).translateToHand($$3, $$4);
/*    */ 
/*    */     
/* 57 */     $$4.mulPose(Axis.XP.rotationDegrees(-90.0F));
/* 58 */     $$4.mulPose(Axis.YP.rotationDegrees(180.0F));
/*    */ 
/*    */     
/* 61 */     boolean $$7 = ($$3 == HumanoidArm.LEFT);
/* 62 */     $$4.translate(($$7 ? -1 : true) / 16.0F, 0.125F, -0.625F);
/*    */     
/* 64 */     this.itemInHandRenderer.renderItem($$0, $$1, $$2, $$7, $$4, $$5, $$6);
/*    */     
/* 66 */     $$4.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\ItemInHandLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */