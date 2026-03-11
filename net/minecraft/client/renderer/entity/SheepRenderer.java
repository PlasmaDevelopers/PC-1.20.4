/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.SheepModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.SheepFurLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.Sheep;
/*    */ 
/*    */ public class SheepRenderer extends MobRenderer<Sheep, SheepModel<Sheep>> {
/* 10 */   private static final ResourceLocation SHEEP_LOCATION = new ResourceLocation("textures/entity/sheep/sheep.png");
/*    */   
/*    */   public SheepRenderer(EntityRendererProvider.Context $$0) {
/* 13 */     super($$0, new SheepModel($$0.bakeLayer(ModelLayers.SHEEP)), 0.7F);
/*    */     
/* 15 */     addLayer((RenderLayer<Sheep, SheepModel<Sheep>>)new SheepFurLayer(this, $$0.getModelSet()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Sheep $$0) {
/* 20 */     return SHEEP_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\SheepRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */