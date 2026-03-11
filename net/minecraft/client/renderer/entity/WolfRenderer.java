/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.WolfModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.animal.Wolf;
/*    */ 
/*    */ public class WolfRenderer extends MobRenderer<Wolf, WolfModel<Wolf>> {
/* 12 */   private static final ResourceLocation WOLF_LOCATION = new ResourceLocation("textures/entity/wolf/wolf.png");
/* 13 */   private static final ResourceLocation WOLF_TAME_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
/* 14 */   private static final ResourceLocation WOLF_ANGRY_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_angry.png");
/*    */   
/*    */   public WolfRenderer(EntityRendererProvider.Context $$0) {
/* 17 */     super($$0, new WolfModel($$0.bakeLayer(ModelLayers.WOLF)), 0.5F);
/*    */     
/* 19 */     addLayer((RenderLayer<Wolf, WolfModel<Wolf>>)new WolfCollarLayer(this));
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getBob(Wolf $$0, float $$1) {
/* 24 */     return $$0.getTailAngle();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Wolf $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 30 */     if ($$0.isWet()) {
/* 31 */       float $$6 = $$0.getWetShade($$2);
/* 32 */       this.model.setColor($$6, $$6, $$6);
/*    */     } 
/*    */     
/* 35 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */     
/* 37 */     if ($$0.isWet()) {
/* 38 */       this.model.setColor(1.0F, 1.0F, 1.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Wolf $$0) {
/* 44 */     if ($$0.isTame()) {
/* 45 */       return WOLF_TAME_LOCATION;
/*    */     }
/* 47 */     if ($$0.isAngry()) {
/* 48 */       return WOLF_ANGRY_LOCATION;
/*    */     }
/* 50 */     return WOLF_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\WolfRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */