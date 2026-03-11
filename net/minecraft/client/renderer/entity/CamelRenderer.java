/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.CamelModel;
/*    */ import net.minecraft.client.model.geom.ModelLayerLocation;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.camel.Camel;
/*    */ 
/*    */ public class CamelRenderer extends MobRenderer<Camel, CamelModel<Camel>> {
/*  9 */   private static final ResourceLocation CAMEL_LOCATION = new ResourceLocation("textures/entity/camel/camel.png");
/*    */   
/*    */   public CamelRenderer(EntityRendererProvider.Context $$0, ModelLayerLocation $$1) {
/* 12 */     super($$0, new CamelModel($$0.bakeLayer($$1)), 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Camel $$0) {
/* 17 */     return CAMEL_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\CamelRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */