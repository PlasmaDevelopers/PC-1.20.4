/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.WitchModel;
/*    */ import net.minecraft.client.renderer.ItemInHandRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ 
/*    */ public class WitchItemLayer<T extends LivingEntity> extends CrossedArmsItemLayer<T, WitchModel<T>> {
/*    */   public WitchItemLayer(RenderLayerParent<T, WitchModel<T>> $$0, ItemInHandRenderer $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 20 */     ItemStack $$10 = $$3.getMainHandItem();
/*    */     
/* 22 */     $$0.pushPose();
/* 23 */     if ($$10.is(Items.POTION)) {
/* 24 */       getParentModel().getHead().translateAndRotate($$0);
/* 25 */       getParentModel().getNose().translateAndRotate($$0);
/* 26 */       $$0.translate(0.0625F, 0.25F, 0.0F);
/* 27 */       $$0.mulPose(Axis.ZP.rotationDegrees(180.0F));
/* 28 */       $$0.mulPose(Axis.XP.rotationDegrees(140.0F));
/* 29 */       $$0.mulPose(Axis.ZP.rotationDegrees(10.0F));
/* 30 */       $$0.translate(0.0F, -0.4F, 0.4F);
/*    */     } 
/*    */     
/* 33 */     super.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9);
/* 34 */     $$0.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\WitchItemLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */