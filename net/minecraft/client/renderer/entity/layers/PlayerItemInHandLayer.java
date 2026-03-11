/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.ArmedModel;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.HeadedModel;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.renderer.ItemInHandRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.HumanoidArm;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemDisplayContext;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ 
/*    */ public class PlayerItemInHandLayer<T extends Player, M extends EntityModel<T> & ArmedModel & HeadedModel>
/*    */   extends ItemInHandLayer<T, M> {
/*    */   private final ItemInHandRenderer itemInHandRenderer;
/*    */   private static final float X_ROT_MIN = -0.5235988F;
/*    */   private static final float X_ROT_MAX = 1.5707964F;
/*    */   
/*    */   public PlayerItemInHandLayer(RenderLayerParent<T, M> $$0, ItemInHandRenderer $$1) {
/* 26 */     super($$0, $$1);
/* 27 */     this.itemInHandRenderer = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderArmWithItem(LivingEntity $$0, ItemStack $$1, ItemDisplayContext $$2, HumanoidArm $$3, PoseStack $$4, MultiBufferSource $$5, int $$6) {
/* 32 */     if ($$1.is(Items.SPYGLASS) && $$0.getUseItem() == $$1 && $$0.swingTime == 0) {
/* 33 */       renderArmWithSpyglass($$0, $$1, $$3, $$4, $$5, $$6);
/*    */     } else {
/* 35 */       super.renderArmWithItem($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void renderArmWithSpyglass(LivingEntity $$0, ItemStack $$1, HumanoidArm $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 40 */     $$3.pushPose();
/*    */     
/* 42 */     ModelPart $$6 = ((HeadedModel)getParentModel()).getHead();
/* 43 */     float $$7 = $$6.xRot;
/* 44 */     $$6.xRot = Mth.clamp($$6.xRot, -0.5235988F, 1.5707964F);
/* 45 */     $$6.translateAndRotate($$3);
/* 46 */     $$6.xRot = $$7;
/*    */     
/* 48 */     CustomHeadLayer.translateToHead($$3, false);
/*    */     
/* 50 */     boolean $$8 = ($$2 == HumanoidArm.LEFT);
/* 51 */     $$3.translate(($$8 ? -2.5F : 2.5F) / 16.0F, -0.0625F, 0.0F);
/*    */     
/* 53 */     this.itemInHandRenderer.renderItem($$0, $$1, ItemDisplayContext.HEAD, false, $$3, $$4, $$5);
/*    */     
/* 55 */     $$3.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\PlayerItemInHandLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */