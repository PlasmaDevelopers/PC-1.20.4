/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.HoglinModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.monster.Zoglin;
/*    */ 
/*    */ public class ZoglinRenderer extends MobRenderer<Zoglin, HoglinModel<Zoglin>> {
/*  9 */   private static final ResourceLocation ZOGLIN_LOCATION = new ResourceLocation("textures/entity/hoglin/zoglin.png");
/*    */   
/*    */   public ZoglinRenderer(EntityRendererProvider.Context $$0) {
/* 12 */     super($$0, new HoglinModel($$0.bakeLayer(ModelLayers.ZOGLIN)), 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Zoglin $$0) {
/* 17 */     return ZOGLIN_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ZoglinRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */