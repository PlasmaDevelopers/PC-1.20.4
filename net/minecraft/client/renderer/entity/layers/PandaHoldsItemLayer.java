/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.PandaModel;
/*    */ import net.minecraft.client.renderer.ItemInHandRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Panda;
/*    */ import net.minecraft.world.item.ItemDisplayContext;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class PandaHoldsItemLayer extends RenderLayer<Panda, PandaModel<Panda>> {
/*    */   public PandaHoldsItemLayer(RenderLayerParent<Panda, PandaModel<Panda>> $$0, ItemInHandRenderer $$1) {
/* 18 */     super($$0);
/* 19 */     this.itemInHandRenderer = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Panda $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 24 */     ItemStack $$10 = $$3.getItemBySlot(EquipmentSlot.MAINHAND);
/* 25 */     if (!$$3.isSitting() || $$3.isScared()) {
/*    */       return;
/*    */     }
/*    */     
/* 29 */     float $$11 = -0.6F;
/* 30 */     float $$12 = 1.4F;
/* 31 */     if ($$3.isEating()) {
/* 32 */       $$11 -= 0.2F * Mth.sin($$7 * 0.6F) + 0.2F;
/* 33 */       $$12 -= 0.09F * Mth.sin($$7 * 0.6F);
/*    */     } 
/* 35 */     $$0.pushPose();
/* 36 */     $$0.translate(0.1F, $$12, $$11);
/* 37 */     this.itemInHandRenderer.renderItem((LivingEntity)$$3, $$10, ItemDisplayContext.GROUND, false, $$0, $$1, $$2);
/* 38 */     $$0.popPose();
/*    */   }
/*    */   
/*    */   private final ItemInHandRenderer itemInHandRenderer;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\PandaHoldsItemLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */