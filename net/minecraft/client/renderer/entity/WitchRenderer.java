/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.WitchModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.monster.Witch;
/*    */ 
/*    */ public class WitchRenderer extends MobRenderer<Witch, WitchModel<Witch>> {
/* 12 */   private static final ResourceLocation WITCH_LOCATION = new ResourceLocation("textures/entity/witch.png");
/*    */   
/*    */   public WitchRenderer(EntityRendererProvider.Context $$0) {
/* 15 */     super($$0, new WitchModel($$0.bakeLayer(ModelLayers.WITCH)), 0.5F);
/*    */     
/* 17 */     addLayer((RenderLayer<Witch, WitchModel<Witch>>)new WitchItemLayer(this, $$0.getItemInHandRenderer()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(Witch $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 22 */     this.model.setHoldingItem(!$$0.getMainHandItem().isEmpty());
/*    */     
/* 24 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Witch $$0) {
/* 29 */     return WITCH_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(Witch $$0, PoseStack $$1, float $$2) {
/* 34 */     float $$3 = 0.9375F;
/* 35 */     $$1.scale(0.9375F, 0.9375F, 0.9375F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\WitchRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */