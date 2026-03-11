/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.SpiderModel;
/*    */ import net.minecraft.client.model.geom.ModelLayerLocation;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.SpiderEyesLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ public class SpiderRenderer<T extends Spider> extends MobRenderer<T, SpiderModel<T>> {
/* 11 */   private static final ResourceLocation SPIDER_LOCATION = new ResourceLocation("textures/entity/spider/spider.png");
/*    */   
/*    */   public SpiderRenderer(EntityRendererProvider.Context $$0) {
/* 14 */     this($$0, ModelLayers.SPIDER);
/*    */   }
/*    */   
/*    */   public SpiderRenderer(EntityRendererProvider.Context $$0, ModelLayerLocation $$1) {
/* 18 */     super($$0, new SpiderModel($$0.bakeLayer($$1)), 0.8F);
/*    */     
/* 20 */     addLayer((RenderLayer<T, SpiderModel<T>>)new SpiderEyesLayer(this));
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getFlipDegrees(T $$0) {
/* 25 */     return 180.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(T $$0) {
/* 30 */     return SPIDER_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\SpiderRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */