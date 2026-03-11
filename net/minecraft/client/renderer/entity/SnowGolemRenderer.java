/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.SnowGolemModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.SnowGolem;
/*    */ 
/*    */ public class SnowGolemRenderer extends MobRenderer<SnowGolem, SnowGolemModel<SnowGolem>> {
/* 10 */   private static final ResourceLocation SNOW_GOLEM_LOCATION = new ResourceLocation("textures/entity/snow_golem.png");
/*    */   
/*    */   public SnowGolemRenderer(EntityRendererProvider.Context $$0) {
/* 13 */     super($$0, new SnowGolemModel($$0.bakeLayer(ModelLayers.SNOW_GOLEM)), 0.5F);
/*    */     
/* 15 */     addLayer((RenderLayer<SnowGolem, SnowGolemModel<SnowGolem>>)new SnowGolemHeadLayer(this, $$0.getBlockRenderDispatcher(), $$0.getItemRenderer()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(SnowGolem $$0) {
/* 20 */     return SNOW_GOLEM_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\SnowGolemRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */