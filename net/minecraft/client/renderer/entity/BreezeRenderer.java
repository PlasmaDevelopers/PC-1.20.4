/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.BreezeModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.BreezeEyesLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.BreezeWindLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.monster.breeze.Breeze;
/*    */ 
/*    */ public class BreezeRenderer extends MobRenderer<Breeze, BreezeModel<Breeze>> {
/* 12 */   private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/breeze/breeze.png");
/* 13 */   private static final ResourceLocation WIND_TEXTURE_LOCATION = new ResourceLocation("textures/entity/breeze/breeze_wind.png");
/*    */   
/*    */   public BreezeRenderer(EntityRendererProvider.Context $$0) {
/* 16 */     super($$0, new BreezeModel($$0.bakeLayer(ModelLayers.BREEZE)), 0.8F);
/* 17 */     addLayer((RenderLayer<Breeze, BreezeModel<Breeze>>)new BreezeWindLayer(this, $$0.getModelSet(), WIND_TEXTURE_LOCATION));
/* 18 */     addLayer((RenderLayer<Breeze, BreezeModel<Breeze>>)new BreezeEyesLayer(this, $$0.getModelSet(), TEXTURE_LOCATION));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Breeze $$0) {
/* 23 */     return TEXTURE_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\BreezeRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */