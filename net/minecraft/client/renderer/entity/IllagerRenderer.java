/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.IllagerModel;
/*    */ import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ public abstract class IllagerRenderer<T extends AbstractIllager> extends MobRenderer<T, IllagerModel<T>> {
/*    */   protected IllagerRenderer(EntityRendererProvider.Context $$0, IllagerModel<T> $$1, float $$2) {
/* 10 */     super($$0, $$1, $$2);
/* 11 */     addLayer((RenderLayer<T, IllagerModel<T>>)new CustomHeadLayer(this, $$0.getModelSet(), $$0.getItemInHandRenderer()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(T $$0, PoseStack $$1, float $$2) {
/* 16 */     float $$3 = 0.9375F;
/* 17 */     $$1.scale(0.9375F, 0.9375F, 0.9375F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\IllagerRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */