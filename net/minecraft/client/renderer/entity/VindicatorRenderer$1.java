/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.IllagerModel;
/*    */ import net.minecraft.client.renderer.ItemInHandRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Vindicator;
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends ItemInHandLayer<Vindicator, IllagerModel<Vindicator>>
/*    */ {
/*    */   null(RenderLayerParent<Vindicator, IllagerModel<Vindicator>> $$1, ItemInHandRenderer $$2) {
/* 17 */     super($$1, $$2);
/*    */   }
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Vindicator $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 20 */     if ($$3.isAggressive())
/* 21 */       super.render($$0, $$1, $$2, (LivingEntity)$$3, $$4, $$5, $$6, $$7, $$8, $$9); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\VindicatorRenderer$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */