/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.IllagerModel;
/*    */ import net.minecraft.client.renderer.ItemInHandRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Illusioner;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class IllusionerRenderer extends IllagerRenderer<Illusioner> {
/* 14 */   private static final ResourceLocation ILLUSIONER = new ResourceLocation("textures/entity/illager/illusioner.png");
/*    */   
/*    */   public IllusionerRenderer(EntityRendererProvider.Context $$0) {
/* 17 */     super($$0, new IllagerModel($$0.bakeLayer(ModelLayers.ILLUSIONER)), 0.5F);
/*    */     
/* 19 */     addLayer((RenderLayer<Illusioner, IllagerModel<Illusioner>>)new ItemInHandLayer<Illusioner, IllagerModel<Illusioner>>(this, $$0.getItemInHandRenderer())
/*    */         {
/*    */           public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Illusioner $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 22 */             if ($$3.isCastingSpell() || $$3.isAggressive()) {
/* 23 */               super.render($$0, $$1, $$2, (LivingEntity)$$3, $$4, $$5, $$6, $$7, $$8, $$9);
/*    */             }
/*    */           }
/*    */         });
/* 27 */     (this.model.getHat()).visible = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Illusioner $$0) {
/* 32 */     return ILLUSIONER;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(Illusioner $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 37 */     if ($$0.isInvisible()) {
/* 38 */       Vec3[] $$6 = $$0.getIllusionOffsets($$2);
/* 39 */       float $$7 = getBob($$0, $$2);
/* 40 */       for (int $$8 = 0; $$8 < $$6.length; $$8++) {
/* 41 */         $$3.pushPose();
/* 42 */         $$3.translate(($$6[$$8]).x + Mth.cos($$8 + $$7 * 0.5F) * 0.025D, ($$6[$$8]).y + Mth.cos($$8 + $$7 * 0.75F) * 0.0125D, ($$6[$$8]).z + Mth.cos($$8 + $$7 * 0.7F) * 0.025D);
/* 43 */         super.render($$0, $$1, $$2, $$3, $$4, $$5);
/* 44 */         $$3.popPose();
/*    */       } 
/*    */     } else {
/* 47 */       super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isBodyVisible(Illusioner $$0) {
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\IllusionerRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */