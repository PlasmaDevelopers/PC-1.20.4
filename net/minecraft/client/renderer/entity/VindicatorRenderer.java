/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.IllagerModel;
/*    */ import net.minecraft.client.renderer.ItemInHandRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Vindicator;
/*    */ 
/*    */ public class VindicatorRenderer extends IllagerRenderer<Vindicator> {
/* 12 */   private static final ResourceLocation VINDICATOR = new ResourceLocation("textures/entity/illager/vindicator.png");
/*    */   
/*    */   public VindicatorRenderer(EntityRendererProvider.Context $$0) {
/* 15 */     super($$0, new IllagerModel($$0.bakeLayer(ModelLayers.VINDICATOR)), 0.5F);
/*    */     
/* 17 */     addLayer((RenderLayer<Vindicator, IllagerModel<Vindicator>>)new ItemInHandLayer<Vindicator, IllagerModel<Vindicator>>(this, $$0.getItemInHandRenderer())
/*    */         {
/*    */           public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Vindicator $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 20 */             if ($$3.isAggressive()) {
/* 21 */               super.render($$0, $$1, $$2, (LivingEntity)$$3, $$4, $$5, $$6, $$7, $$8, $$9);
/*    */             }
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Vindicator $$0) {
/* 29 */     return VINDICATOR;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\VindicatorRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */