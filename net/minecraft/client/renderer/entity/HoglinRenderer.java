/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.HoglinModel;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.hoglin.Hoglin;
/*    */ 
/*    */ public class HoglinRenderer extends MobRenderer<Hoglin, HoglinModel<Hoglin>> {
/*  9 */   private static final ResourceLocation HOGLIN_LOCATION = new ResourceLocation("textures/entity/hoglin/hoglin.png");
/*    */   
/*    */   public HoglinRenderer(EntityRendererProvider.Context $$0) {
/* 12 */     super($$0, new HoglinModel($$0.bakeLayer(ModelLayers.HOGLIN)), 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Hoglin $$0) {
/* 17 */     return HOGLIN_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isShaking(Hoglin $$0) {
/* 22 */     return (super.isShaking($$0) || $$0.isConverting());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\HoglinRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */