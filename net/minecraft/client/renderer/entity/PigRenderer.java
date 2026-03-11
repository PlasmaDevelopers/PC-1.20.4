/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.PigModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.Pig;
/*    */ 
/*    */ public class PigRenderer extends MobRenderer<Pig, PigModel<Pig>> {
/* 10 */   private static final ResourceLocation PIG_LOCATION = new ResourceLocation("textures/entity/pig/pig.png");
/*    */   
/*    */   public PigRenderer(EntityRendererProvider.Context $$0) {
/* 13 */     super($$0, new PigModel($$0.bakeLayer(ModelLayers.PIG)), 0.7F);
/*    */     
/* 15 */     addLayer((RenderLayer<Pig, PigModel<Pig>>)new SaddleLayer(this, (EntityModel)new PigModel($$0.bakeLayer(ModelLayers.PIG_SADDLE)), new ResourceLocation("textures/entity/pig/pig_saddle.png")));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Pig $$0) {
/* 20 */     return PIG_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\PigRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */