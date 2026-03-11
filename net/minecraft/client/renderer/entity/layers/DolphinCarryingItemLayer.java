/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.DolphinModel;
/*    */ import net.minecraft.client.renderer.ItemInHandRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.HumanoidArm;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Dolphin;
/*    */ import net.minecraft.world.item.ItemDisplayContext;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class DolphinCarryingItemLayer extends RenderLayer<Dolphin, DolphinModel<Dolphin>> {
/*    */   public DolphinCarryingItemLayer(RenderLayerParent<Dolphin, DolphinModel<Dolphin>> $$0, ItemInHandRenderer $$1) {
/* 18 */     super($$0);
/* 19 */     this.itemInHandRenderer = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Dolphin $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 24 */     boolean $$10 = ($$3.getMainArm() == HumanoidArm.RIGHT);
/*    */     
/* 26 */     $$0.pushPose();
/*    */     
/* 28 */     float $$11 = 1.0F;
/* 29 */     float $$12 = -1.0F;
/* 30 */     float $$13 = Mth.abs($$3.getXRot()) / 60.0F;
/* 31 */     if ($$3.getXRot() < 0.0F) {
/* 32 */       $$0.translate(0.0F, 1.0F - $$13 * 0.5F, -1.0F + $$13 * 0.5F);
/*    */     } else {
/* 34 */       $$0.translate(0.0F, 1.0F + $$13 * 0.8F, -1.0F + $$13 * 0.2F);
/*    */     } 
/*    */     
/* 37 */     ItemStack $$14 = $$10 ? $$3.getMainHandItem() : $$3.getOffhandItem();
/* 38 */     this.itemInHandRenderer.renderItem((LivingEntity)$$3, $$14, ItemDisplayContext.GROUND, false, $$0, $$1, $$2);
/*    */     
/* 40 */     $$0.popPose();
/*    */   }
/*    */   
/*    */   private final ItemInHandRenderer itemInHandRenderer;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\DolphinCarryingItemLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */