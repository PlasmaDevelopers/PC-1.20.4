/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.renderer.ItemInHandRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.ItemDisplayContext;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class CrossedArmsItemLayer<T extends LivingEntity, M extends EntityModel<T>>
/*    */   extends RenderLayer<T, M> {
/*    */   public CrossedArmsItemLayer(RenderLayerParent<T, M> $$0, ItemInHandRenderer $$1) {
/* 18 */     super($$0);
/* 19 */     this.itemInHandRenderer = $$1;
/*    */   }
/*    */   private final ItemInHandRenderer itemInHandRenderer;
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 24 */     $$0.pushPose();
/* 25 */     $$0.translate(0.0F, 0.4F, -0.4F);
/* 26 */     $$0.mulPose(Axis.XP.rotationDegrees(180.0F));
/*    */     
/* 28 */     ItemStack $$10 = $$3.getItemBySlot(EquipmentSlot.MAINHAND);
/* 29 */     this.itemInHandRenderer.renderItem((LivingEntity)$$3, $$10, ItemDisplayContext.GROUND, false, $$0, $$1, $$2);
/*    */     
/* 31 */     $$0.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\CrossedArmsItemLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */